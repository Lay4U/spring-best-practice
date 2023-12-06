package lay.learn.springbestpractice.jpa2.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member2 extends JpaBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member2(String username) {
        this(username, 0);
    }

    public Member2(String username, int age) {
        this(username, age, null);
    }

    public Member2(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        if(this.team != null) {
            this.team.getMember2s().remove(this);
        }
        this.team = team;
        if(team != null && !team.getMember2s().contains(this)) {
            team.getMember2s().add(this);
        }
    }

    @Override
    public String toString() {
        return "Member{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", age=" + age +
               '}';
    }

    public void changeName(String username) {
        this.username = username;
    }
}
