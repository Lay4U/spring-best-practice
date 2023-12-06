package lay.learn.springbestpractice.querydsl;

import com.querydsl.core.annotations.QueryProjection;

public record MemberDto(
        String username,
        int age
) {

    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
