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
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(35000);
        itemList.forEach(System.out::println);
    }
    @Test
    public void findByItemNmLike(){
        List<Item> itemList = itemRepository.findByItemNmLike("%2%");
        itemList.forEach(System.out::println);
    }
}