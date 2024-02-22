package sun0aaa.s.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sun0aaa.s.shop.Entity.Cart;
import sun0aaa.s.shop.Entity.Delivery;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.Entity.Order;
import sun0aaa.s.shop.dto.*;
import sun0aaa.s.shop.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final DeliveryService deliveryService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("memberJoinRequest",new MemberJoinRequest());
        return "members/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute MemberJoinRequest req, BindingResult bindingResult,Model model){
        //Validation
        if(memberService.joinValid(req,bindingResult).hasErrors()){
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "members/join";
        }
        memberService.join(req);
        model.addAttribute("message","회원가입에 성공했습니다!\n로그인 후 사용 가능합니다!");
        model.addAttribute("nextUrl","/members/login");
        return "printMessage";
    }
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        // 로그인 성공 시 이전 페이지로 redirect 되게 하기 위해 세션에 저장
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login") && !uri.contains("/join")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        model.addAttribute("memberLoginRequest", new MemberLoginRequest());
        return "members/login";
    }

    @GetMapping("/myPage/modify")
    public String memberEditPage(Authentication auth, Model model){
        Member member = memberService.myInfo(auth.getName());
        model.addAttribute("memberDto", MemberDto.of(member));
        return "members/modify";
    }

    @PostMapping("/myPage/modify")
    public String memberEdit(@Valid @ModelAttribute MemberDto dto, BindingResult bindingResult,
                             Authentication auth, Model model){
        //Validation
        if(memberService.editValid(dto,bindingResult, auth.getName()).hasErrors()){
            return "members/modify";
        }
        memberService.edit(dto, auth.getName());
        model.addAttribute("message","정보가 수정되었습니다");
        model.addAttribute("nextUrl","/members/myPage");
        return "printMessage";
    }
    @GetMapping("/myPage")
    public String myPage(Authentication auth,Model model){
        //model.addAttribute("order",orderService.findMyOrder(auth.getName()));
        //model.addAttribute("member",memberService.myInfo(auth.getName()));
        return "members/myPage";
    }

    @GetMapping("/myPage/wishlist")
    public String wishlistPage(Authentication auth, Model model,
                               @RequestParam(required = false,defaultValue = "1") int page){
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        model.addAttribute("wishlists",itemService.findAllWishlist(auth,pageRequest));
        return "members/myWishlist";
    }

    @GetMapping("/myPage/delivery")
    public String myDeliveryPage(Authentication auth, Model model){
        Delivery delivery = memberService.myInfo(auth.getName()).getDelivery();
        model.addAttribute("deliveryDto",DeliveryDto.of(delivery));

        return "members/myDelivery";
    }
    @PostMapping("/myPage/delivery")
    public String myDelivery(Authentication auth, Model model, @ModelAttribute DeliveryDto dto,BindingResult bindingResult){
        Member loginMember = memberService.myInfo(auth.getName());
        if(deliveryService.editValid(dto, bindingResult).hasErrors()){
            return "members/myDelivery";
        }
        deliveryService.editDelivery(loginMember,dto);
        model.addAttribute("message","배송지가 등록되었습니다");
        model.addAttribute("nextUrl","/members/myPage");
        return "printMessage";
    }

    @GetMapping("/myPage/order")
    public String myOrderPage(Authentication auth, Model model,
                              @RequestParam(required = false,defaultValue = "1") int page){
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(auth.getName(), pageRequest);

        model.addAttribute("orderHistDtoList",orderHistDtoList);
        return "members/myOrder";
    }

    @GetMapping("/myPage/cart")
    public String myCartPage(Authentication auth, Model model){
        List<CartDto> cartDtoList = cartService.cartList(auth.getName());
        model.addAttribute("cartDtoList",cartDtoList);
        return "members/myCart";
    }

    /*
    @GetMapping("delete")
    public String memberDeletePage(Authentication auth,Model model){
        Member member = memberService.myInfo(auth.getName());
        model.addAttribute("memberDto",MemberDto.of(member));
        return "members/delete";
    }

    @PostMapping("/delete")
    public String memberDelete(@ModelAttribute MemberDto dto, Authentication auth,Model model){
        boolean deleteSuccess = memberService.delete(auth.getName(), dto.getNowPassword());
        if(deleteSuccess){
            model.addAttribute("message","탈퇴 되었습니다.");
            model.addAttribute("nextUrl","/users/logout");
        }else{
            model.addAttribute("message","현재 비밀번호가 틀려 탈퇴에 실패하였습니다");
            model.addAttribute("mextUrl","/users/delete");
        }
        return "printMessage";
    }

 */
    /*
    @GetMapping("/admin")
    public String adminPage(@RequestParam(required=false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "") String keyword,
                            Model model){
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<User> users = userService.findAllByNickname(keyword, pageRequest);
        orderService.findAllByName(keyword,pageRequest);
        model.addAttribute("orders",orders);
        model.addAttribute("keyword",keyword);
    }
*/

}
