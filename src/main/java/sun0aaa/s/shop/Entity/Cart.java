package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Cart {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private Integer count;

    public static Cart create(Item item, int count, Member member){
        return Cart.builder()
                .item(item)
                .count(count)
                .member(member)
                .build();
    }

    public boolean increaseCount(int cnt){
        this.count+=cnt;
        return true;
    }
}
