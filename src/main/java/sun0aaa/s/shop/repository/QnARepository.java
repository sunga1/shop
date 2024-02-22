package sun0aaa.s.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun0aaa.s.shop.Entity.QnA;

@Repository
public interface QnARepository extends JpaRepository<QnA,Long> {
    Page<QnA> findAllByItemId(Long itemId, PageRequest pageRequest);
}
