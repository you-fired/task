package inhatc.cse.jaeseokshop.member.controller;

import inhatc.cse.jaeseokshop.member.dto.MemberDto;
import inhatc.cse.jaeseokshop.member.entity.Member;
import inhatc.cse.jaeseokshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String MemeberNew(MemberDto memberDto){
        Member member = Member.createMember(memberDto, passwordEncoder);
        memberService.saveMember(member);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String memebrAdd(Model model){
        model.addAttribute("memberDto" , new MemberDto());
        return "member/add";
    }
}
