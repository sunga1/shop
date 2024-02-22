package sun0aaa.s.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun0aaa.s.shop.Entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email); //email로 member 찾기 위해
    Boolean existsByEmail(String Email); //회원가입 시 중복 체크용으로 사용
}
