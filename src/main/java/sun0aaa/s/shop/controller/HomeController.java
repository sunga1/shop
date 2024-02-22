package sun0aaa.s.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sun0aaa.s.shop.dto.ItemSearchRequest;
import sun0aaa.s.shop.service.MemberService;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @GetMapping(value={"","/"})
    public String home(Model model){
        return "home";
    }
}
