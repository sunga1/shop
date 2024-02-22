package sun0aaa.s.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun0aaa.s.shop.Entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
