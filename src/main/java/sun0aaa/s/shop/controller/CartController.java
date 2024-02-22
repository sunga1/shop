package sun0aaa.s.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun0aaa.s.shop.dto.OrderDto;
import sun0aaa.s.shop.service.CartService;
import sun0aaa.s.shop.service.ItemService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final ItemService itemService;
    private final CartService cartService;
    @PostMapping("/create")
    public String createCart(@RequestParam("itemId1") Long itemId,
                                 @RequestParam("count1") int count,
                                 Model model, Authentication auth){

        OrderDto orderDto = new OrderDto(itemId, count);
        cartService.createCart(orderDto,auth.getName());

        model.addAttribute("message","상품이 장바구니에 담겼습니다.");
        model.addAttribute("nextUrl","/items/"+itemId+"/detail");
        return "printMessage";
    }

    @PostMapping("{cartId}/delete")
    public String deleteCart(@RequestParam("cartId") Long cartId, Model model, Authentication auth){
        cartService.deleteCart(cartId);
        model.addAttribute("message","상품이 삭제되었습니다.");
        model.addAttribute("nextUrl","/members/myPage/cart");
        return "printMessage";
    }



}
