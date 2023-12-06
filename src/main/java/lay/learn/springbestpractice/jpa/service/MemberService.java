package lay.learn.springbestpractice.jpa.service;

import lay.learn.springbestpractice.jpa.entity.Member;
import lay.learn.springbestpractice.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found by id"));
    }

    @Transactional
    public Member update(Long id, String name){
        Member member = memberRepository.findWithOrdersById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found by id"));
        member.changeName(name);
        return member;
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }


    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
