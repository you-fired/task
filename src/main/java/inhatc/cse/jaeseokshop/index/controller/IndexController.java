package inhatc.cse.jaeseokshop.index.controller;

import inhatc.cse.jaeseokshop.index.dto.IndexDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model){
        IndexDto indexDto = IndexDto.builder()
                .dept("컴시과")
                .name("홍길동")
                .build();

        model.addAttribute("data",indexDto);
        return "index";
    }
}
