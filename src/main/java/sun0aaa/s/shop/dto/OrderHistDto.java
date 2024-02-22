package sun0aaa.s.shop.dto;

import lombok.Data;
import sun0aaa.s.shop.Entity.DeliveryStatus;
import sun0aaa.s.shop.Entity.Order;
import sun0aaa.s.shop.Entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderHistDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;

    public OrderHistDto(Order order){
        this.orderId=order.getId();
        this.orderDate=order.getCreatedAt();
        this.deliveryStatus=order.getDeliveryStatus();
        orderStatus=order.getOrderStatus();
    }
}
