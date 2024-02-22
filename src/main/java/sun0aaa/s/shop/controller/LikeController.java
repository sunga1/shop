package sun0aaa.s.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun0aaa.s.shop.service.LikeService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/create/{itemId}")
    public String addLike(@PathVariable Long itemId, Authentication auth){
        likeService.createLike(auth.getName(), itemId);
        return "redirect:/items/"+itemId+"/detail";
    }

    @GetMapping("/delete/{itemId}")
    public String deleteLike(@PathVariable Long itemId, Authentication auth){
        likeService.deleteLike(auth.getName(),itemId);
        return "redirect:/items/"+itemId+"/detail";
    }
}
