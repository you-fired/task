package inhatc.cse.task.member.controller;

import inhatc.cse.task.member.dto.MemberDto;
import inhatc.cse.task.member.dto.PasswordChangeDto;
import inhatc.cse.task.member.entity.Member;
import inhatc.cse.task.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final PasswordEncoder passwordEncoder; // 객체 생성
    private final MemberService memberService;

    @PostMapping("/add")
    public String MemeberNew(@Valid MemberDto memberDto , BindingResult bindingResult , Model model){
        // Valid 할 때 오류 발생시 처리
        if(bindingResult.hasErrors()){
            return "member/add";
        }
        try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            //예외 발생시 등록 페이지에 전달할 메세지
            model.addAttribute("errorMessage",e.getMessage());
            return "member/add";
        }
        return "redirect:/";
    }

    @GetMapping("/add")
    public String memebrAdd(Model model){
        model.addAttribute("memberDto" , new MemberDto());
        return "member/add";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호가 틀렸습니다.");
        return "member/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
    @GetMapping("/pwchange")
    public String showChangePasswordPage(Model model) {
        model.addAttribute("passwordChangeDto", new PasswordChangeDto());
        return "member/pwchange"; // 비밀번호 변경 폼을 띄운다.
    }

    @PostMapping("/pwchange")
    public String changePassword(@Valid PasswordChangeDto passwordChangeDto, Model model) {
        if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmPassword())) {
            model.addAttribute("errorMessage", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "member/pwchange";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인한 사용자의 이메일

        try {
            memberService.changePassword(email, passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/pwchange";
        }

        return "redirect:/"; // 성공적으로 비밀번호 변경 후 홈으로 리다이렉트
    }

}