package inhatc.cse.jaeseokshop.member.service;

import inhatc.cse.jaeseokshop.member.entity.Member;
import inhatc.cse.jaeseokshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    
    public Member saveMember(Member member) {
       validateDupMember(member); 
       Member m = memberRepository.save(member);
       return m;
    }

    private void validateDupMember(Member member) {
//        Optional<Member> mem = memberRepository.findByEmail(member.getEmail());
//        if(mem.isPresent()){
//            Member m1 = mem.get();
//            throw new IllegalStateException("이미 존재하는 사용자 입니다.");
//        } 아래와 비슷한 문장 이 문장은 Optional<T>로 리턴 받지만 아래는 T로 리턴 받음

        Member m2 = memberRepository.findByEmail(member.getEmail()).orElseThrow(() -> new IllegalStateException("이미 존재하는 사용자 입니다."));
    }

}
