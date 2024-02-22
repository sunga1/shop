package sun0aaa.s.shop.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.Entity.Review;
import sun0aaa.s.shop.Entity.UploadImage;

@Data
public class ReviewJoinRequest {
    private Integer rating;

    private String title;
    private String content;

    private MultipartFile uploadImage;

    public static Review toEntity(ReviewJoinRequest reviewJoinRequest){
        return Review.builder()
                .rating(reviewJoinRequest.getRating())
                .title(reviewJoinRequest.getTitle())
                .content(reviewJoinRequest.getContent())
                .build();
    }
}
