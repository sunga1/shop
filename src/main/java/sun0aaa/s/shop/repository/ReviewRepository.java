package sun0aaa.s.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun0aaa.s.shop.Entity.Review;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findAllByItemId(Long itemId, PageRequest pageRequest);

}
