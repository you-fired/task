package inhatc.cse.jaeseokshop.item.repository;

import inhatc.cse.jaeseokshop.item.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Test
    public void findByItemNm() {
    }

    @Test
    public void findByPriceLessThanOrderByPriceDesc() {
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(50000);
        itemList.forEach(System.out::println);
    }
    @Test
    public void findByItemNmLike(){
        List<Item> itemList = itemRepository.findByItemNmLike("%2%");
        itemList.forEach(System.out::println);
    }

    @Test
    public void findByItemNmOrItemDetail(){
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("신상품1","신상품 상세 설명2");
        itemList.forEach(System.out::println);
    }

    @Test
    public void findByPriceLessThan(){
        List<Item> itemList = itemRepository.findByPriceLessThan(25000);
        itemList.forEach(System.out::println);
    }

    @Test
    public void findByItemDetailTest(){
        List<Item> itemList = itemRepository.findItemDetail("신상품 상세 설명1");
        itemList.forEach(System.out::println);
    }
    @Test
    public void findItemDetailNative(){
        List<Item> itemList = itemRepository.findItemDetail("신상품 상세 설명1");
        itemList.forEach(System.out::println);
    }
}