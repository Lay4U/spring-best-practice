package lay.learn.springbestpractice.jpa2.repository;

import lay.learn.springbestpractice.jpa2.entity.Member2;

import java.util.List;

public interface Member2RepositoryCustom {
    List<Member2> findMemberCustom();
}
