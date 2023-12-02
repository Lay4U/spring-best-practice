package lay.learn.springbestpractice.lombok;

import lombok.Builder;

//@Builder
public class BuilderAntiPattern {
    private Long id;
    private String description;
    private String name;
    private Double price;

    @Builder
    public BuilderAntiPattern(final Long id, final String description, final String name, final Double price) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
    }
}
