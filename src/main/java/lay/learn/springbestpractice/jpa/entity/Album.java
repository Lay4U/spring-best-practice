package lay.learn.springbestpractice.jpa.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("A")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item{

    private String artist;
    private String etc;

    @Builder
    public Album(String artist, String etc) {
        this.artist = artist;
        this.etc = etc;
    }
}
