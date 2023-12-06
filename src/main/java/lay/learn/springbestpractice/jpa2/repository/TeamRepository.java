package lay.learn.springbestpractice.jpa2.repository;

import lay.learn.springbestpractice.jpa2.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
