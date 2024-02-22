package sun0aaa.s.shop.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.Entity.Review;
import sun0aaa.s.shop.dto.ItemDto;
import sun0aaa.s.shop.dto.ReviewJoinRequest;
import sun0aaa.s.shop.repository.ReviewRepository;
import sun0aaa.s.shop.service.ItemService;
import sun0aaa.s.shop.service.MemberService;
import sun0aaa.s.shop.service.ReviewService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final MemberService memberService;
    private final ItemService itemService;
    @GetMapping("/{itemId}")
    public String ReviewListPage(@PathVariable Long itemId, Model model,
                                 @RequestParam(required = false,defaultValue = "1")int page,Authentication auth){
        if(auth!=null){
            model.addAttribute("loginUserLoginId",auth.getName());
        }
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<Review> reviews = reviewService.findReviewByItemId(itemId,pageRequest);
        ItemDto itemDto = itemService.findById(itemId);
        model.addAttribute("reviews",reviews);
        model.addAttribute("itemDto",itemDto);
        return "reviews/list";
    }

    @PostMapping("/{itemId}/{reviewId}/delete")
    public String ReviewDelete(@PathVariable Long itemId,@PathVariable Long reviewId, Model model){
        reviewService.deleteReview(reviewId);
        model.addAttribute("nextUrl","/reviews/"+itemId);
        model.addAttribute("message","리뷰가 삭제되었습니다!");
        return "printMessage";
    }
    @GetMapping("/{itemId}/write")
    public String ReviewWrite(@PathVariable Long itemId, Model model){
        ItemDto itemDto = itemService.findById(itemId);
        model.addAttribute("itemDto",itemDto);
        model.addAttribute("reviewJoinRequest",new ReviewJoinRequest());
        return "reviews/write";
    }
    @PostMapping("/{itemId}/write")
    public String  CreateReview(@PathVariable Long itemId, @ModelAttribute ReviewJoinRequest reviewJoinRequest,
                                Authentication auth, Model model)throws IOException {
        Review review = reviewService.saveReview(itemId, reviewJoinRequest, auth);
        model.addAttribute("nextUrl","/reviews/"+itemId);
        model.addAttribute("message","리뷰가 등록되었습니다!");
        return "printMessage";
    }
}
