package inhatc.cse.jaeseokshop.item.controller;

import inhatc.cse.jaeseokshop.item.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {
    @GetMapping("/item/thymeleaf1")
    public String thymeleaf1(Model model){
        ItemDto itemDto = ItemDto.builder()
                .id(1L).itemNm("상품명").itemDetail("상품 상세 설명").price(50000).stockNumber(100)
                .build();

        model.addAttribute("itemDto",itemDto);
        return "item/thymeleaf1";
    }
}
