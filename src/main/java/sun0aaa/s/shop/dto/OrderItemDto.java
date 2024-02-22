package sun0aaa.s.shop.dto;

import lombok.Data;
import sun0aaa.s.shop.Entity.OrderItem;
import sun0aaa.s.shop.Entity.UploadImage;

@Data
public class OrderItemDto {
    private String itemName;
    private int count;
    private int orderPrice;
    private UploadImage uploadImage;
    private int totalPrice;

    public OrderItemDto(OrderItem orderItem, UploadImage uploadImage){
        this.itemName=orderItem.getItem().getName();
        this.count=orderItem.getCount();
        this.orderPrice=orderItem.getOrderPrice();
        this.uploadImage=uploadImage;
        this.totalPrice=orderItem.getTotalPrice();
    }

}
