package inhatc.cse.jaeseokshop.member.service;

import inhatc.cse.jaeseokshop.member.dto.MemberDto;
import inhatc.cse.jaeseokshop.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("사용자 등록 테스트")
    void saveMember() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);
        assertEquals(member.getEmail(),savedMember.getEmail());


    }

    private Member createMember() {
        MemberDto memberDto = MemberDto.builder().address("인천 미추홀구").name("홍길동").email("hong3@test.com").password("1111").build();
        Member member = Member.createMember(memberDto, passwordEncoder);
        return member;
    }
}