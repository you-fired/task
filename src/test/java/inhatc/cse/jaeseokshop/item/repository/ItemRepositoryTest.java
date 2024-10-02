package inhatc.cse.jaeseokshop.item.repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inhatc.cse.jaeseokshop.item.entity.Item;
import inhatc.cse.jaeseokshop.item.entity.QItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void findByItemNm() {
    }
    @Test
    public void findByPriceLessThanOrderByPriceDesc() {
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(35000);
        itemList.forEach(System.out::println);
    }
    @Test
    public void findByItemNmLike(){
        List<Item> itemList = itemRepository.findByItemNmLike("%2%");
        itemList.forEach(System.out::println);
    }
    @Test
    @DisplayName("querydsl 테스트2")
    public void querydslTest2(){
        BooleanBuilder builder = new BooleanBuilder();
        String itemDetail = "신상품";
        int price = 10004;
        QItem item = QItem.item;

        builder.and(item.itemDetail.like("%" + itemDetail +"%"));
        builder.and(item.price.gt(price));

        Pageable pageable = PageRequest.of(0,2);

        Page<Item> page = itemRepository.findAll(builder, pageable);
        List<Item> content = page.getContent();
        content.stream().forEach((e) -> {
            System.out.println(e);
        });

    }




    @Test
    @DisplayName("querydsl 테스트")
    public void querydslTest(){
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        List<Item> itemList = query.select(qItem)
                .from(qItem)
                .where(qItem.itemDetail.like("%상품%"))
                .orderBy(qItem.price.desc())
                .fetch();

        itemList.forEach(item -> System.out.println(item));
    }
}