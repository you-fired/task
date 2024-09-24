package inhatc.cse.jaeseokshop.item.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id//기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 자동 숫자 증가
    @Column(name = "item_id")
    private Long id;
    @Column(nullable = false,length = 50)
    private String itemNm;
    private int price;
    @Column(name = "stock")
    private int stockNumber;
    @Lob
    @Column(nullable = false)
    private String itemDetail;
}
