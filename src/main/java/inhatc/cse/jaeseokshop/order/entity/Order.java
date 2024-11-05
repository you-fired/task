package inhatc.cse.jaeseokshop.order.entity;

import inhatc.cse.jaeseokshop.item.entity.Item;
import inhatc.cse.jaeseokshop.member.entity.Member;
import inhatc.cse.jaeseokshop.order.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="orders")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id//기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 자동 숫자 증가
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;




}
