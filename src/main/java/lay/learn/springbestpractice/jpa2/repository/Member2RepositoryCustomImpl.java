package lay.learn.springbestpractice.jpa2.repository;

import jakarta.persistence.EntityManager;
import lay.learn.springbestpractice.jpa2.entity.Member2;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Member2RepositoryCustomImpl implements Member2RepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Member2> findMemberCustom() {
        return em.createQuery("select m from Member2 m")
                 .getResultList();
    }
}
