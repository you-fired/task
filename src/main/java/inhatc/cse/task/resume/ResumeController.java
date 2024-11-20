package inhatc.cse.task.resume;

import inhatc.cse.task.profile.Profile;
import inhatc.cse.task.profile.ProfileDto;
import inhatc.cse.task.profile.ProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/resume")
public class ResumeController {

    private final ProfileService profileService;
    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;

    public ResumeController(ProfileService profileService, ResumeService resumeService, ResumeRepository resumeRepository) {
        this.profileService = profileService;
        this.resumeService = resumeService;
        this.resumeRepository = resumeRepository;
    }

    @GetMapping("/list")
    public String getResumes(Model model) {
        // 현재 로그인한 사용자 이름을 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        ProfileDto profile = profileService.getProfileByUsername(currentUsername);
        if (profile == null) {
            // 프로필이 없으면 /profile/create로 리다이렉트
            return "redirect:/profile/create";
        }// 로그인한 사용자의 username

        // 해당 사용자의 이력서만 가져오기
        List<Resume> resumes = resumeService.getResumesByUsername(currentUsername);

        // 이력서를 모델에 추가
        model.addAttribute("resumes", resumes);
        return "resume/list";  // 이력서 리스트 페이지 반환
    }

    @GetMapping("/create")
    public String createResume(Model model) {
        String username = profileService.getLoggedInUsername(); // 현재 로그인된 사용자의 이름을 가져와 설정
        ProfileDto profile = profileService.getProfileByUsername(username);
        model.addAttribute("profile", profile);
        return "resume/create";
    }

    @GetMapping("/save")
    public String saveResume(){
        return "resume/list";
    }

    @PostMapping("/save")
    public String saveResume(@ModelAttribute ResumeDto resumeDto) {
        // 현재 로그인된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();  // 로그인된 사용자의 username

        // 이력서 저장을 위해 ResumeDto와 현재 사용자의 username을 서비스 메서드로 전달
        resumeService.saveResume(resumeDto, currentUsername);  // DTO를 서비스 계층으로 전달

        return "redirect:/resume/list";  // 이력서 목록 페이지로 리디렉션
    }

    @GetMapping("/{id}")
    public String viewResume(@PathVariable Long id, Model model) {
        String username = profileService.getLoggedInUsername(); // 현재 로그인된 사용자의 이름을 가져와 설정
        ProfileDto profile = profileService.getProfileByUsername(username);
        Resume resume = resumeService.getResumeById(id);
        model.addAttribute("resume", resume);
        model.addAttribute("profile", profile);
        return "resume/view";  // This refers to the template: src/main/resources/templates/resume/view.html
    }

    @GetMapping("/edit/{id}")
    public String editResume(@PathVariable Long id, Model model) {
        Resume resume = resumeService.getResumeById(id); // Fetch resume by ID
        String username = profileService.getLoggedInUsername(); // 현재 로그인된 사용자의 이름을 가져와 설정
        ProfileDto profile = profileService.getProfileByUsername(username);
        model.addAttribute("resume", resume); // Add resume data to model
        model.addAttribute("profile", profile);
        return "resume/edit"; // Redirect to edit page (resume/edit.html)
    }
    @PostMapping("/update")
    public String updateResume(@ModelAttribute Resume resume, RedirectAttributes redirectAttributes,Profile profile,Model model) {
        resumeService.updateResume(resume); // Save the updated resume
        model.addAttribute("profile", profile);
        redirectAttributes.addFlashAttribute("message", "이력서가 성공적으로 업데이트되었습니다.");
        return "redirect:/resume/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteResume(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            resumeService.deleteResume(id);
            redirectAttributes.addFlashAttribute("successMessage", "이력서가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "이력서 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/resume/list";
    }
}
