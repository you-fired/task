package inhatc.cse.task.resume;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/resume")
public class ResumeController {
    @GetMapping("/list")
    public String resume() {
        return "resume/list";  // templates/resume/resumeList.html 경로로 매핑
    }

}
