package inhatc.cse.task.resume;

import inhatc.cse.task.profile.Profile;
import inhatc.cse.task.profile.ProfileDto;
import inhatc.cse.task.profile.ProfileService;
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

    public ResumeController(ProfileService profileService, ResumeService resumeService) {
        this.profileService = profileService;
        this.resumeService = resumeService;
    }

    @GetMapping("/list")
    public String getResumes(Model model) {
        List<Resume> resumes = resumeService.getAllResumes(); // Fetch all resumes
        model.addAttribute("resumes", resumes); // Add resumes to the model
        return "resume/list"; // Return the view for the resume list page
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
    public String saveResume(@ModelAttribute Resume resume, RedirectAttributes redirectAttributes) {
        resumeService.saveResume(resume);
        redirectAttributes.addFlashAttribute("message", "이력서가 성공적으로 저장되었습니다.");
        return "redirect:/resume/list";
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
    public String updateResume(@ModelAttribute Resume resume, RedirectAttributes redirectAttributes) {
        resumeService.updateResume(resume); // Save the updated resume
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
