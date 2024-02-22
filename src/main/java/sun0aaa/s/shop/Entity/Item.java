package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sun0aaa.s.shop.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Item {
    @Id @GeneratedValue
    private Long id;
    private String name; //상품 이름
    private String sizeTip; //사이즈 설명
    private String itemTip; //상품 설명
    private Integer delivery_price; //배송비
    private Integer price; //상품 가격
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory; //상품 종류

    private Integer stockQuantity; //재고

    @OneToMany(mappedBy = "item")
    private List<UploadImage> uploadImages; //상품을 설명하는 이미지


    //==비즈니스 로직==//
    public void addStock(int quantity){
        this.stockQuantity =quantity;
    }

    public void removeStock(int quantity){
        int restStock = stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


    public Item edit(Integer price,ItemCategory itemCategory,Integer stock ,String itemTip, String sizeTip) {
        this.price=price;
        this.itemTip=itemTip;
        this.sizeTip=sizeTip;
        this.stockQuantity=stock;
        return  this;
    }

}
