package sun0aaa.s.shop.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun0aaa.s.shop.Entity.Cart;
import sun0aaa.s.shop.dto.ItemDto;
import sun0aaa.s.shop.dto.OrderDto;
import sun0aaa.s.shop.dto.OrderHistDto;
import sun0aaa.s.shop.dto.OrderItemDto;
import sun0aaa.s.shop.exception.NotEnoughStockException;
import sun0aaa.s.shop.service.CartService;
import sun0aaa.s.shop.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    @PostMapping("/create")
    public String immediateOrder(@RequestParam("itemId") Long itemId,
                                 @RequestParam("count") int count,
                                 Model model, Authentication auth){

        OrderDto orderDto = new OrderDto(itemId, count);
        orderService.order(orderDto, auth.getName());

        model.addAttribute("message","성공적으로 주문이 완료되었습니다.");
        model.addAttribute("nextUrl","/items/"+itemId+"/detail");
        return "printMessage";
    }

    @PostMapping("/{orderId}/cancel")
    public String deleteOrder(@PathVariable Long orderId,Authentication auth,Model model){
        boolean checkDelete = orderService.cancelOrder(orderId);
        if(checkDelete){
            model.addAttribute("message","주문이 취소되었습니다.");
        }
        else{
            model.addAttribute("message","배송이 시작되어 주문을 취소할 수 없습니다.");
        }
        model.addAttribute("nextUrl","/members/myPage/order");
        return "printMessage";
    }

    @GetMapping("/{orderId}/detail")
    public String detailOrder(@PathVariable Long orderId, Authentication auth, Model model){
        List<OrderItemDto> orderItemDtoList = orderService.getOrderDetail(auth.getName(), orderId);
        LocalDateTime orderDate = orderService.getOrderDate(orderId);
        model.addAttribute("orderItemDtoList",orderItemDtoList);
        model.addAttribute("orderDate",orderDate);
        return "orders/detail";
    }

    @PostMapping("/createByCart")
    public String createOrderByCart(@RequestParam("selectedCartIds") List<Long> selectedCartIds,
                                    Authentication auth,Model model){
        if(selectedCartIds==null){
            model.addAttribute("message","선택된 상품이 없습니다.");
            model.addAttribute("nextUrl","/members/myPage/cart");
        }
        else {
            List<OrderDto> orderDtoList=cartService.changeCartToOrderDto(selectedCartIds);
            orderService.cartOrder(orderDtoList, auth.getName());

            model.addAttribute("message", "성공적으로 주문이 완료되었습니다.");
            model.addAttribute("nextUrl", "/members/myPage/order");
        }
        return "printMessage";
    }

}
