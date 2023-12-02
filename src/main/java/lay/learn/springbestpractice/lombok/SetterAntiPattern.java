package lay.learn.springbestpractice.lombok;

import lombok.Setter;

public class SetterAntiPattern {
//    @Setter
    private int level;

    public int levelUp(){
        return this.level++;
    }
}
