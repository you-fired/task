package inhatc.cse.task.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // 프로필 메인 페이지 (상세보기)
    @GetMapping("/profile")
    public String viewProfile(Model model) {
        ProfileDto profile = profileService.getProfile(); // DB에서 프로필 가져오기
        model.addAttribute("profile", profile);
        return "profile/profile"; // 프로필 메인 페이지
    }

    // 프로필 생성 페이지
    @GetMapping("/create")
    public String showCreateProfileForm(Model model) {
        model.addAttribute("profile", new ProfileDto());
        return "profile/create";  // create.html 템플릿 반환
    }

    // 프로필 생성 처리
    @PostMapping("/create")
    public String createProfile(@ModelAttribute ProfileDto profileDto,
                                @RequestParam("profileImage") MultipartFile profileImage,
                                RedirectAttributes redirectAttributes) {
        try {
            if (!profileImage.isEmpty()) {
                // 업로드 디렉터리 설정
                String uploadDir = "D:/img"; // 절대 경로로 설정
                Path uploadPath = Paths.get(uploadDir);

                // 디렉터리 생성
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 고유 파일 이름 생성
                String originalFileName = profileImage.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 파일 저장
                Path filePath = uploadPath.resolve(uniqueFileName);
                profileImage.transferTo(filePath.toFile());

                // ProfileDto에 이미지 경로 설정
                profileDto.setProfileImagePath("/uploads/" + uniqueFileName);
            }

            // 프로필 업데이트
            profileService.createProfile(profileDto);
            redirectAttributes.addFlashAttribute("message", "프로필이 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "프로필 업데이트 중 오류가 발생했습니다.");
        }

        return "redirect:/profile/profile";
    }

    // 프로필 수정 페이지
    @GetMapping("/edit")
    public String showEditProfileForm(Model model) {
        ProfileDto profile = profileService.getProfile(); // DB에서 프로필 가져오기
        model.addAttribute("profile", profile);
        return "profile/edit";  // edit.html 템플릿 반환
    }

    // 프로필 수정 처리
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute ProfileDto profileDto,
                                @RequestParam("profileImage") MultipartFile profileImage,
                                RedirectAttributes redirectAttributes) {
        try {
            if (!profileImage.isEmpty()) {
                // 업로드 디렉터리 설정
                String uploadDir = "D:/img"; // 절대 경로로 설정
                Path uploadPath = Paths.get(uploadDir);

                // 디렉터리 생성
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 고유 파일 이름 생성
                String originalFileName = profileImage.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 파일 저장
                Path filePath = uploadPath.resolve(uniqueFileName);
                profileImage.transferTo(filePath.toFile());

                // ProfileDto에 이미지 경로 설정
                profileDto.setProfileImagePath("/uploads/" + uniqueFileName);
            }

            // 프로필 업데이트
            profileService.updateProfile(profileDto);
            redirectAttributes.addFlashAttribute("message", "프로필이 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "프로필 업데이트 중 오류가 발생했습니다.");
        }

        return "redirect:/profile/profile";
    }


    // 예외 처리 (추후 다른 예외 추가 가능)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());  // 예외 메시지 전달
        return "profile/create";  // 프로필 생성 페이지로 이동
    }
}
