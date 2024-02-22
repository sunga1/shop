package sun0aaa.s.shop.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.ItemCategory;
import sun0aaa.s.shop.Entity.UploadImage;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ItemDto {
    private Long id;
    private String name; //상품 이름
    private Integer price; //상품 가격
    private Integer delivery_price; //배송비
    private ItemCategory itemCategory; //상품 종류
    private String itemTip; //상품 설명
    private String sizeTip; //크기 설명
    private Integer stock; //재고
    private List<UploadImage> uploadImages;

    public static ItemDto of(Item item){
        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .delivery_price(item.getDelivery_price())
                .itemTip(item.getItemTip())
                .itemCategory(item.getItemCategory())
                .stock(item.getStockQuantity())
                .sizeTip(item.getSizeTip())
                .uploadImages(new ArrayList<>(item.getUploadImages()))
                .build();
        return itemDto;
    }
}
