package inhatc.cse.jaeseokshop.order.entity;

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
public class OrderItem {
    @Id//기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 자동 숫자 증가
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="item_id")
    private Item item;

    private int order_price;

    private int count;
}
