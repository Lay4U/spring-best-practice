package lay.learn.springbestpractice.querydsl;

import lombok.Builder;
import lombok.Getter;


@Getter
public class MemberSearchCondition {
    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;

    @Builder
    public MemberSearchCondition(String username, String teamName, Integer ageGoe, Integer ageLoe) {
        this.username = username;
        this.teamName = teamName;
        this.ageGoe = ageGoe;
        this.ageLoe = ageLoe;
    }
}
