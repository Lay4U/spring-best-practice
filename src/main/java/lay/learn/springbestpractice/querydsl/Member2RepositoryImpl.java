package lay.learn.springbestpractice.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lay.learn.springbestpractice.jpa2.entity.Member2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static lay.learn.springbestpractice.jpa2.entity.QMember2.member2;
import static lay.learn.springbestpractice.jpa2.entity.QTeam.team;
import static org.springframework.util.StringUtils.isEmpty;

public class Member2RepositoryImpl implements  Member2RepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public Member2RepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition) {
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

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results = queryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content = queryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(member2)
                .from(member2)
                .leftJoin(member2.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetchCount();
        return new PageImpl<>(content, pageable, total);
    }

    public Page<MemberTeamDto> searchPageComplex2(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content = queryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Member2> countQuery = queryFactory
                .select(member2)
                .from(member2)
                .leftJoin(member2.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }


}
