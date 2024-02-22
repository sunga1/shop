package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member; //주문한 회원

    private Integer totalPrice;

    @OneToMany(mappedBy = "order",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems; //order과 item은 다대다 관계이므로 orderItem 만들어 일대다 관계로 변경
    
    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery; //배송정보

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태(주문,취소)

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; //배송상태


    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public static Order createOrder(Member member,List<OrderItem> orderItemList,Delivery delivery){
        Order order = Order.builder()
                .member(member).
                orderStatus(OrderStatus.ORDER)
                .deliveryStatus(DeliveryStatus.READY)
                .delivery(delivery).build();
        order.orderItems=new ArrayList<>();
        for(OrderItem orderItem: orderItemList){
            order.addOrderItem(orderItem);
        }
        order.totalPrice=order.getTotalPrice(orderItemList);
        return order;
    }
    public int getTotalPrice(List<OrderItem>orderItems){
        int totalPrice=0;
        for(OrderItem orderItem:orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public boolean cancel() {
        if(this.getDeliveryStatus()==DeliveryStatus.READY){
            this.orderStatus=OrderStatus.CANCEL;
            return true;
        }
        else{
            return false;
        }
    }
}













