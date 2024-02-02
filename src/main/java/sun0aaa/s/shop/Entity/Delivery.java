package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Delivery {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,mappedBy ="delivery")
    private Member member; //회원

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "delivery")
    private Order order; //주문

    private String deliveryName; //배송지명
    private String memberName;

    @Embedded
    private Address address; //배송주소

    private Integer phoneNumber; //핸드폰 번호



}
