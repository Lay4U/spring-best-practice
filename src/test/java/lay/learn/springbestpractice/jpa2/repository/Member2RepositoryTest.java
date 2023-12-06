package lay.learn.springbestpractice.jpa2.repository;

import jakarta.persistence.EntityManager;
import lay.learn.springbestpractice.jpa.repository.MemberRepository;
import lay.learn.springbestpractice.jpa2.entity.Member2;
import lay.learn.springbestpractice.jpa2.entity.Team;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class Member2RepositoryTest {

    @Autowired
    Member2Repository member2Repository;

    @Autowired
    EntityManager em;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testMember() {
        Member2 member2 = new Member2("memberA");
        Member2 savedMember2 = member2Repository.save(member2);

        Member2 findMember2 = member2Repository.findById(savedMember2.getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        assertThat(findMember2.getId()).isEqualTo(member2.getId());
        assertThat(findMember2.getUsername()).isEqualTo(member2.getUsername());
        assertThat(findMember2).isEqualTo(member2);
    }

    @Test
    void basicCRUD() {
        Member2 member1 = new Member2("member1");
        Member2 member2 = new Member2("member2");
        member2Repository.save(member1);
        member2Repository.save(member2);

        Member2 findMember1 = member2Repository.findById(member1.getId()).get();
        Member2 findMember2 = member2Repository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member2> all = member2Repository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = member2Repository.count();
        assertThat(count).isEqualTo(2);

        member2Repository.delete(member1);
        member2Repository.delete(member2);

        long deletedCount = member2Repository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void page() {
        member2Repository.save(new Member2("member1", 10));
        member2Repository.save(new Member2("member2", 10));
        member2Repository.save(new Member2("member3", 10));
        member2Repository.save(new Member2("member4", 10));
        member2Repository.save(new Member2("member5", 10));

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Page<Member2> page = member2Repository.findByAge(10, pageRequest);

        List<Member2> content = page.getContent();
        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    void bulkUpdate() {
        member2Repository.save(new Member2("member1", 10));
        member2Repository.save(new Member2("member2", 19));
        member2Repository.save(new Member2("member3", 20));
        member2Repository.save(new Member2("member4", 21));
        member2Repository.save(new Member2("member5", 40));

        int resultCount = member2Repository.bulkAgePlus(20);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void findMemberLazy() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        member2Repository.save(new Member2("member1", 10, teamA));
        member2Repository.save(new Member2("member2", 20, teamB));

        em.flush();
        em.clear();

        List<Member2> members = member2Repository.findAll();
        for (Member2 member : members) {
            System.out.println(Hibernate.isInitialized(member.getTeam()));
        }
    }

    @Test
    void queryHint() {
        member2Repository.save(new Member2("member1", 10));
        em.flush();
        em.clear();

        Member2 member = member2Repository.findReadOnlyByUsername("member1");
        member.changeName("member2");
        em.flush();
        em.clear();

        Optional<Member2> member2 = member2Repository.findByUsername("member2");
        assertThat(member2).isEmpty();

    }
}