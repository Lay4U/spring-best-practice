package lay.learn.springbestpractice.jpa.repository;

import lay.learn.springbestpractice.jpa.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {"orders"})
    Optional<Member> findWithOrdersById(Long id);

    boolean findByName(String name);
}
