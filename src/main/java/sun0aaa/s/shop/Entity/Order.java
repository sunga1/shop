package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //주문한 회원

    private Integer totalPrice;

    @OneToMany(mappedBy = "order",orphanRemoval = true)
    private List<OrderItem> orderItems; //order과 item은 다대다 관계이므로 orderItem 만들어 일대다 관계로 변경

    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true)
    private Delivery delivery; //배송정보

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태(주문,취소)



}
