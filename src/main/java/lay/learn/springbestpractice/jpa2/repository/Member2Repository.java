package lay.learn.springbestpractice.jpa2.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import jakarta.validation.constraints.NotNull;
import lay.learn.springbestpractice.jpa2.dto.MemberDto;
import lay.learn.springbestpractice.jpa2.entity.Member2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface Member2Repository extends JpaRepository<Member2, Long>, Member2RepositoryCustom {

    Optional<Member2> findByUsername(@Param("username") String username);

    @Query("""
                select m from Member2 m 
                where m.username = :username 
                and m.age = :age
            """)
    List<Member2> findUser(@Param("username") String username, @Param("age") int age);

    @Query("""
                select m.username from Member2 m
            """)
    List<String> findUsernameList();

    @Query("""
                select new lay.learn.springbestpractice.jpa2.dto.MemberDto(m.id, m.username, t.name) from Member2 m 
                join m.team t
            """)
    List<MemberDto> findMemberDto();

    @Query("""
                select m from Member2 m where m.username in :names
            """)
    List<Member2> findByNames(@Param("names") List<String> names);

    Page<Member2> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member2 m left join m.team t",
            countQuery = "select count(m) from Member2 m")
    Page<Member2> findMember2AllCountBy(Pageable pageable);

    @Modifying
    @Query("update Member2 m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("""
                select m from Member2 m left join fetch m.team
            """)
    List<Member2> findMember2FetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    @NonNull
    List<Member2> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member2 m")
    List<Member2> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member2> findWithEntityGraphByUsername(String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member2 findReadOnlyByUsername(String username);

    @QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly", value = "true")},
            forCounting = true)
    Page<Member2> findByUsername(String name, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member2> findLockByUsername(String name);

}
