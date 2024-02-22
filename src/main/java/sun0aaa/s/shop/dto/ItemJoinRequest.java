package sun0aaa.s.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.ItemCategory;
import sun0aaa.s.shop.Entity.UploadImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ItemJoinRequest {
    private String name; //상품 이름
    private Integer price; //상품 가격
    private Integer delivery_price; //배송비
    private ItemCategory itemCategory; //상품 종류
    private Integer stock; //재고
    private String itemTip; //상품 설명
    private String sizeTip; //사이즈 설명
    private List<MultipartFile> uploadImages;
    public Item toEntity(){
        return Item.builder()
                .name(name)
                .price(price)
                .itemCategory(itemCategory)
                .itemTip(itemTip)
                .sizeTip(sizeTip)
                .stockQuantity(stock)
                .uploadImages(new ArrayList<>())
                .delivery_price(delivery_price)
                .build();
    }

}
