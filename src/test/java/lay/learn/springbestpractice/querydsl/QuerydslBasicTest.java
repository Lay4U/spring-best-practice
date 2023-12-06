package lay.learn.springbestpractice.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lay.learn.springbestpractice.jpa2.entity.Member2;
import lay.learn.springbestpractice.jpa2.entity.QMember2;
import lay.learn.springbestpractice.jpa2.entity.QTeam;
import lay.learn.springbestpractice.jpa2.entity.Team;
import lay.learn.springbestpractice.jpa2.repository.Member2Repository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lay.learn.springbestpractice.jpa2.entity.QMember2.member2;
import static lay.learn.springbestpractice.jpa2.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.isEmpty;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;
    @Autowired
    private Member2Repository member2Repository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member2 member1 = new Member2("member1", 10, teamA);
        Member2 member2 = new Member2("member2", 20, teamA);
        Member2 member3 = new Member2("member3", 30, teamB);
        Member2 member4 = new Member2("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

    }

    @Test
    void startJPQL() {
        String qlString = "select m from Member2 m " +
                          "where m.username = :username";

        Member2 findMember = em.createQuery(qlString, Member2.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQuerydsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember2 m = new QMember2("m");

        Member2 findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQueryDsl2() {
        QMember2 m = new QMember2("m");

        Member2 findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQuerydsl3() {
        Member2 findMember = queryFactory
                .select(member2)
                .from(member2)
                .where(member2.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() {
        Member2 findMember = queryFactory
                .selectFrom(member2)
                .where(member2.username.eq("member1")
                        .and(member2.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");

    }

    @Test
    void searchAndParam() {
        List<Member2> result1 = queryFactory
                .selectFrom(member2)
                .where(member2.username.eq("member1"),
                        member2.age.eq(10))
                .fetch();

        assertThat(result1.size()).isEqualTo(1);
    }

    @Test
    void fetch() {
        List<Member2> fetch = queryFactory
                .selectFrom(member2)
                .fetch();

        Member2 findMember1 = queryFactory
                .selectFrom(member2)
                .fetchOne();

        Member2 findMember2 = queryFactory
                .selectFrom(member2)
                .fetchFirst();

        QueryResults<Member2> results = queryFactory
                .selectFrom(member2)
                .fetchResults();

        long fetchCount = queryFactory
                .selectFrom(member2)
                .fetchCount();
    }

    @Test
    void sort() {
        em.persist(new Member2(null, 100));
        em.persist(new Member2("member5", 100));
        em.persist(new Member2("member6", 100));

        List<Member2> result = queryFactory
                .selectFrom(member2)
                .where(member2.age.eq(100))
                .orderBy(member2.age.desc(), member2.username.asc().nullsLast())
                .fetch();

        Member2 member5 = result.get(0);
        Member2 member6 = result.get(1);
        Member2 memberNull = result.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    void paging1() {
        List<Member2> result = queryFactory
                .selectFrom(member2)
                .orderBy(member2.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void paging2() {
        QueryResults<Member2> queryResults = queryFactory
                .selectFrom(member2)
                .orderBy(member2.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
    }

    @Test
    void aggregation() {
        List<Tuple> result = queryFactory.select(member2.count(),
                        member2.age.sum(),
                        member2.age.avg(),
                        member2.age.max(),
                        member2.age.min())
                .from(member2)
                .fetch();

        Tuple tuple = result.get(0);
        assertThat(tuple.get(member2.count())).isEqualTo(4);
        assertThat(tuple.get(member2.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member2.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member2.age.max())).isEqualTo(40);
        assertThat(tuple.get(member2.age.min())).isEqualTo(10);
    }

    @Test
    void group() {
        List<Tuple> result = queryFactory
                .select(team.name, member2.age.avg())
                .from(member2)
                .join(member2.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member2.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member2.age.avg())).isEqualTo(35);
    }

    @Test
    void groupByHaving() {
        List<Tuple> result = queryFactory
                .select(team.name, member2.age.avg())
                .from(member2)
                .join(member2.team, team)
                .groupBy(team.name)
                .having(member2.age.avg().gt(10))
                .fetch();

        Tuple teamB = result.get(0);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member2.age.avg())).isEqualTo(35);
    }

    @Test
    void join() {
        QMember2 member = member2;
        QTeam team = QTeam.team;

        List<Member2> result = queryFactory
                .select(member)
                .from(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    @Test
    void theta_join() {
        em.persist(new Member2("teamA"));
        em.persist(new Member2("teamB"));

        List<Member2> result = queryFactory
                .select(member2)
                .from(member2, team)
                .where(member2.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    @Test
    void join_on_filtering() {
        List<Tuple> result = queryFactory
                .select(member2, team)
                .from(member2)
                .leftJoin(member2.team, team).on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void join_on_no_relation() {
        em.persist(new Member2("teamA"));
        em.persist(new Member2("teamB"));

        List<Tuple> result = queryFactory
                .select(member2, team)
                .from(member2)
                .leftJoin(team).on(member2.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void fetchJoinNo() {
        em.flush();
        em.clear();

        Member2 findMember = queryFactory
                .selectFrom(member2)
                .where(member2.username.eq("member1"))
                .fetchOne();

        boolean loaded = Hibernate.isInitialized(findMember.getTeam());
        assertThat(loaded).as("패치 조인 미적용").isFalse();
    }

    @Test
    void fetchJoinUse() {
        em.flush();
        em.clear();

        Member2 findMember = queryFactory
                .selectFrom(member2)
                .join(member2.team, team).fetchJoin()
                .where(member2.username.eq("member1"))
                .fetchOne();

        boolean loaded = Hibernate.isInitialized(findMember.getTeam());
        assertThat(loaded).as("패치 조인 적용").isTrue();
    }

    @Test
    void subQuery() {
        QMember2 memberSub = new QMember2("memberSub");

        List<Member2> result = queryFactory
                .selectFrom(member2)
                .where(member2.age.eq(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(40);
    }

    @Test
    void subQueryGoe() {
        QMember2 memberSub = new QMember2("memberSub");

        List<Member2> result = queryFactory
                .selectFrom(member2)
                .where(member2.age.goe(
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(30, 40);
    }

    @Test
    void subQueryIn() {
        QMember2 memberSub = new QMember2("memberSub");

        List<Member2> result = queryFactory
                .selectFrom(member2)
                .where(member2.age.in(
                        JPAExpressions
                                .select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(20, 30, 40);
    }

    @Test
    void subQueryInSelect() {
        QMember2 memberSub = new QMember2("memberSub");

        List<Tuple> fetch = queryFactory
                .select(member2.username,
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ).from(member2)
                .fetch();

        for (Tuple tuple : fetch) {
            System.out.println("tuple = " + tuple);
            System.out.println("age = " + tuple.get(JPAExpressions.select(memberSub.age.avg()).from(memberSub)));
        }
    }

    @Test
    void simpleCase(){
        List<String> result = queryFactory
                .select(member2.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member2)
                .fetch();
    }

    @Test
    void complexCase() {
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member2.age.between(0, 20)).then("0~20살")
                        .when(member2.age.between(21, 30)).then("21~30살")
                        .otherwise("기타"))
                .from(member2)
                .fetch();
    }

    @Test
    void rankPath() {
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(member2.age.between(0, 20)).then(2)
                .when(member2.age.between(21, 30)).then(1)
                .otherwise(3);

        List<Tuple> result = queryFactory
                .select(member2.username, member2.age, rankPath)
                .from(member2)
                .orderBy(rankPath.desc())
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void constant() {
        Tuple result = queryFactory
                .select(member2.username, Expressions.constant("A"))
                .from(member2)
                .fetchFirst();

        System.out.println("result = " + result);
    }

    @Test
    void concat() {
        String result = queryFactory
                .select(member2.username.concat("_").concat(member2.age.stringValue()))
                .from(member2)
                .where(member2.username.eq("member1"))
                .fetchOne();

        assertThat(result).isEqualTo("member1_10");
    }

    @Test
    void projectionOne() {
        List<String> result = queryFactory
                .select(member2.username)
                .from(member2)
                .fetch();
    }

    @Test
    void projectionTuple() {
        List<Tuple> result = queryFactory
                .select(member2.username, member2.age)
                .from(member2)
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member2.username);
            Integer age = tuple.get(member2.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
    }

    @Test
    void projectionDto_setter() {
        List<MemberDto> result = queryFactory.select(Projections.bean(
                        MemberDto.class,
                        member2.username,
                        member2.age
                ))
                .from(member2)
                .fetch();
    }

    @Test
    void projection_access_field() {
        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member2.username,
                        member2.age))
                .from(member2)
                .fetch();
    }

    @Test
    void projection_cunstructor() {
        List<MemberDto> result = queryFactory
                .select(Projections.constructor(
                        MemberDto.class,
                        member2.username,
                        member2.age)
                )
                .from(member2)
                .fetch();
    }

    @Test
    void queryProjection() {
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member2.username, member2.age))
                .from(member2)
                .fetch();
    }

    @Test
    void dynamicQuery_BooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member2> result = searchMember(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member2> searchMember(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();
        if(usernameCond != null){
            builder.and(member2.username.eq(usernameCond));
        }
        if(ageCond != null){
            builder.and(member2.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member2)
                .where(builder)
                .fetch();
    }

    @Test
    void dynamicWhereParam() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member2> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member2> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member2)
                .where(usernameEqual(usernameCond), ageEq(ageCond))
                .fetch();
    }

    private BooleanExpression usernameEqual(String usernameCond){
        return usernameCond != null ? member2.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond){
        return ageCond != null ? member2.age.eq(ageCond) : null;
    }

    /*
    * where 조건의 null 값은 무시.
    * 메서드를 다른 쿼리에서 재활용 가능.
    * 쿼리 자체 가독성 높아짐.
    * */

    @Test
    void bulk_modify() {
        queryFactory
                .update(member2)
                .set(member2.username, "비회원")
                .where(member2.age.lt(28))
                .execute();

        queryFactory
                .update(member2)
                .set(member2.age, member2.age.add(1))
                .execute();

        queryFactory
                .update(member2)
                .set(member2.age, member2.age.multiply(2))
                .execute();

        queryFactory
                .delete(member2)
                .where(member2.age.gt(18))
                .execute();
    }

    @Test
    void SQLFunction() {
        queryFactory
                .select(Expressions.stringTemplate("function('replace', {0}, {1}, {2})",
                        member2.username, "member", "M"))
                .from(member2)
                .fetchFirst();

        queryFactory
                .select(member2.username)
                .from(member2)
                .where(member2.username.eq(Expressions.stringTemplate("function('lower', {0})", member2.username)))
//                .where(member2.username.eq(member2.username.lower()))
                .fetchFirst();
    }


    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(condition.getUsername())){
            builder.and(member2.username.eq(condition.getUsername()));
        }
        if(hasText(condition.getTeamName())){
            builder.and(team.name.eq(condition.getTeamName()));
        }
        if(condition.getAgeGoe() != null) {
            builder.and(member2.age.goe(condition.getAgeGoe()));
        }
        if(condition.getAgeLoe() != null) {
            builder.and(member2.age.loe(condition.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        member2.id,
                        member2.username,
                        member2.age,
                        team.id,
                        team.name
                ))
                .from(member2)
                .leftJoin(member2.team, team)
                .where(builder)
                .fetch();
    }

    @Test
    void searchTest() {
        MemberSearchCondition condition = MemberSearchCondition.builder()
                .ageGoe(35)
                .ageLoe(40)
                .teamName("teamB")
                .build();

        List<MemberTeamDto> result = searchByBuilder(condition);

        assertThat(result).extracting("username").containsExactly("member4");
    }

    public List<MemberTeamDto> search(MemberSearchCondition condition){
        return queryFactory
                .select(new QMemberTeamDto(
                        member2.id,
                        member2.username,
                        member2.age,
                        team.id,
                        team.name
                ))
                .from(member2)
                .leftJoin(member2.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetch();
    }

    private BooleanExpression usernameEq(String username){
        return isEmpty(username) ? null : member2.username.eq(username);
    }

    private BooleanExpression teamNameEq(String teamName){
        return isEmpty(teamName) ? null : team.name.eq(teamName);
    }

    private BooleanExpression ageGoe(Integer ageGoe){
        return ageGoe == null ? null : member2.age.goe(ageGoe);
    }

    private BooleanExpression ageLoe(Integer ageLoe){
        return ageLoe == null ? null : member2.age.loe(ageLoe);
    }



}
