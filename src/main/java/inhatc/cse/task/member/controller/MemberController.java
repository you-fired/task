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
    public String changePassword(@Valid PasswordChangeDto passwordChangeDto, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 새 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmPassword())) {
            model.addAttribute("errorMessage", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "member/pwchange";
        }

        // 현재 로그인한 사용자의 이메일 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try {
            // 비밀번호 변경 로직 실행
            memberService.changePassword(email, passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());

            // 로그아웃 처리
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, authentication);

            // 로그아웃 후 로그인 페이지로 리다이렉트
            return "redirect:/";
        } catch (IllegalStateException e) {
            // 비밀번호 변경 중 에러 발생 시
            model.addAttribute("errorMessage", e.getMessage());
            return "memebr/pwchange";
        }
    }


}