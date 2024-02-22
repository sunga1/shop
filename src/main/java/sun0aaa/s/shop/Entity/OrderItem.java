package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sun0aaa.s.shop.exception.NotEnoughStockException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private Integer orderPrice; //주문했을때의 상품 가격
    private Integer count; //상품 개수

    public static OrderItem createOrderItem(Item item,int count){
        System.out.println(item.getId()+" "+count);
       item.removeStock(count);

        return OrderItem.builder()
                .item(item)
                .count(count)
                .orderPrice(item.getPrice())
                .build();

    }
    public int getTotalPrice(){
        return orderPrice*count;
    }

    public void setOrder(Order order) {
        this.order=order;
    }
}
