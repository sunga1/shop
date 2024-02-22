package sun0aaa.s.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun0aaa.s.shop.Entity.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findAllByMemberEmail(String email);
    Boolean existsByMemberEmailAndItemId(String email,Long itemId);

    Optional<Cart> findByMemberEmailAndItemId(String email, Long itemId);
}
