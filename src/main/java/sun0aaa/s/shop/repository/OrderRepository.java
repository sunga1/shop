package sun0aaa.s.shop.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sun0aaa.s.shop.Entity.QnA;
import sun0aaa.s.shop.Entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select o from Order o "+
            "where o.member.email = :email "+
            "order by o.createdAt desc"
    )
    Page<Order> findAllByMemberEmail(String email, PageRequest pageRequest);

    @Query("select o from Order o join fetch o.delivery d where o.member.email = :email")
    List<Order> findAllWithDelivery(@Param("email") String email);

    @Query("select count(o) from Order o "+
            "where o.member.email = :email")
    Long countOrder(@Param("email") String email);



}

