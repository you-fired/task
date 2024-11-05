package inhatc.cse.jaeseokshop.cart.entity;

import inhatc.cse.jaeseokshop.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id//기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 자동 숫자 증가
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
