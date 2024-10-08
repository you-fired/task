package inhatc.cse.jaeseokshop.member.entity;

import inhatc.cse.jaeseokshop.member.constant.Role;
import inhatc.cse.jaeseokshop.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id//기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 자동 숫자 증가
    @Column(name = "member_id")
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(unique = true, length = 50)
    private String email;
    @Column(nullable = false , length = 200)
    private String password;
    @Column(length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder){
        Member member = Member.builder()
                .address(memberDto.getAddress())
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .role(Role.USER)
                .build();
        return member;
    }
}
