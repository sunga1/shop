package sun0aaa.s.shop.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import sun0aaa.s.shop.Entity.Cart;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.Entity.UploadImage;

@Data
@Builder
public class CartDto {
    private Long id;
    private UploadImage itemImage;
    private String itemName;
    private Integer count;

    public static CartDto of(Cart cart){
        return CartDto.builder()
                .id(cart.getId())
                .itemImage(cart.getItem().getUploadImages().get(0))
                .itemName(cart.getItem().getName())
                .count(cart.getCount())
                .build();
    }
}
