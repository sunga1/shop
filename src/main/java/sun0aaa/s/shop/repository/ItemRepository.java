package sun0aaa.s.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.ItemCategory;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Boolean existsByName(String name);
    Page<Item> findAllByNameContains(String name, PageRequest pageRequest);

    Page<Item> findAllByItemCategory(ItemCategory itemCategory,PageRequest pageRequest);


}
