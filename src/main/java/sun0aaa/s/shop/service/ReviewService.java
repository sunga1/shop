package sun0aaa.s.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.Entity.Review;
import sun0aaa.s.shop.Entity.UploadImage;
import sun0aaa.s.shop.dto.ReviewJoinRequest;
import sun0aaa.s.shop.repository.ReviewRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private  final MemberService memberService;
    private final ItemService itemService;
    private final UploadImageService uploadImageService;

    public Page<Review> findReviewByItemId(Long itemId, PageRequest pageRequest){
        Page<Review> reviews = reviewRepository.findAllByItemId(itemId,pageRequest);
        return reviews;
    }

    @Transactional
    public Review saveReview(Long itemId, ReviewJoinRequest reviewJoinRequest, Authentication auth) throws IOException {
        Review saveReview = reviewRepository.save(ReviewJoinRequest.toEntity(reviewJoinRequest));
        Member loginMember = memberService.myInfo(auth.getName());
        Item item = itemService.findItem(itemId);
        saveReview.saveMemberItem(loginMember,item);
        UploadImage uploadImage = uploadImageService.saveReviewImage(reviewJoinRequest.getUploadImage(), saveReview);
        if(uploadImage!=null){
            saveReview.setUploadImage(uploadImage);
        }
        return saveReview;
    }

    @Transactional
    public boolean deleteReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId).get();
        reviewRepository.delete(review);
        return true;
    }
}
