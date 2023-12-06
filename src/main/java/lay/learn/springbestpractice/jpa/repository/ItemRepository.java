package lay.learn.springbestpractice.jpa.repository;

import lay.learn.springbestpractice.jpa.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}