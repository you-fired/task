package inhatc.cse.jaeseokshop.cart.entity;

import inhatc.cse.jaeseokshop.item.entity.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id//기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 자동 숫자 증가
    @Column(name = "cart_item_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;
}
