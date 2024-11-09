package inhatc.cse.task.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String createProfile(ProfileDto profileDto, RedirectAttributes redirectAttributes) {
        try {
            profileService.createProfile(profileDto);
            return "redirect:/profile/profile";  // 프로필 생성 후 메인 페이지로 리디렉션
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());  // 오류 메시지 전달
            return "redirect:/profile/create";  // 오류 발생 시 create 페이지로 리디렉션
        }
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
    public String updateProfile(ProfileDto profileDto) {
        profileService.updateProfile(profileDto);
        return "redirect:/profile/profile";  // 수정된 프로필을 메인 페이지에 반영
    }

    // 예외 처리 (추후 다른 예외 추가 가능)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());  // 예외 메시지 전달
        return "profile/create";  // 프로필 생성 페이지로 이동
    }
}
