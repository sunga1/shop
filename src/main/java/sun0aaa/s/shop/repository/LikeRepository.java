package sun0aaa.s.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun0aaa.s.shop.Entity.Like;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    void deleteByMemberEmailAndItemId(String email,Long itemId);
    Boolean existsByMemberEmailAndItemId(String email,Long itemId);
    Page<Like> findAllByMemberEmail(PageRequest pageRequest,String email);
}
