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
@Table(name = "member")
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String name; //유저이름

    private String password; //비밀번호

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Delivery delivery; //주소

    @Enumerated(EnumType.STRING)
    private Membership membership; //권한

    @OneToMany(mappedBy = "member",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Order> orders; //주문목록

    @OneToMany(mappedBy = "member",orphanRemoval = true)
    private List<QnA> QnAs; //작성한 문의 목록

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Review> reviews;//작성한 리뷰 목록

    @OneToMany(mappedBy = "member",orphanRemoval = true)
    private List<Like> likes; //위시리스트

    @OneToMany(mappedBy = "member",orphanRemoval = true)
    private List<Cart> cartItems; //장바구니목록

    //회원수정
    public void edit(String newPassword,String name){
        this.password=newPassword;
        this.name=name;
    }

    public void deliveryCreate(Delivery newDelivery){
        this.delivery=newDelivery;
    }
}
