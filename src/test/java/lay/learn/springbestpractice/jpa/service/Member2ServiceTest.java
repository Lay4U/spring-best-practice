package lay.learn.springbestpractice.jpa.service;

import lay.learn.springbestpractice.jpa.entity.Member;
import lay.learn.springbestpractice.jpa.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class Member2ServiceTest {
    @Autowired
    MemberService service;

    @Autowired
    MemberRepository repository;

    Long memberId;

    @BeforeEach
    void beforeAll() {
        Member member = Member.builder().build();
        Member saveMember = repository.save(member);
        memberId = saveMember.getId();
    }

    @Test
    void update() {
        Member member = service.update(memberId, "abcd");
        System.out.println("member = " + member);
    }

    @Test
    void findAll() {
        List<Member> all = service.findAll();
        System.out.println("all = " + all);

    }
}