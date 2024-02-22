package sun0aaa.s.shop.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.Entity.Review;
import sun0aaa.s.shop.Entity.UploadImage;

@Data
@Builder
public class ReviewDto {
    private Long id;

    private Member member;

    private Integer rating;

    private String title;
    private String content;

    private UploadImage uploadImage;

    public static ReviewDto of(Review review){
        return ReviewDto.builder()
                .id(review.getId())
                .member(review.getMember())
                .title(review.getTitle())
                .content(review.getContent())
                .uploadImage(review.getUploadImage())
                .build();
    }
}
