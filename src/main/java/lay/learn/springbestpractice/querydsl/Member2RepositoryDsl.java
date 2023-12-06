package lay.learn.springbestpractice.querydsl;

import lay.learn.springbestpractice.jpa2.entity.Member2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Member2RepositoryDsl extends JpaRepository<Member2, Long>, Member2RepositoryCustom {

    List<Member2> findByUsername(String username);
}
