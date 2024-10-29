package inhatc.cse.jaeseokshop.member.service;

import inhatc.cse.jaeseokshop.member.entity.Member;
import inhatc.cse.jaeseokshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    
    public Member saveMember(Member member) {
       validateDupMember(member); 
       Member m = memberRepository.save(member);
       return m;
    }

    private void validateDupMember(Member member) {
        Optional<Member> optMember = memberRepository.findByEmail(member.getEmail());
        if(optMember.isPresent()){
            Member member1 = optMember.get();
            throw new IllegalStateException("이미 존재하는 사용자 입니다.");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + email));
        //위의 구문과 같은 구문
//        Optional<Member> optMember = memberRepository.findByEmail(email);
//        if(optMember.isPresent()){
//            Member member = optMember.get();
//        }else {
//            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + email);
//        }
        log.info(member.toString());

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
