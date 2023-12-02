package lay.learn.springbestpractice.lombok;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AllArgsConstructAntiPattern {
    private Long id;
    private String description;
    private String name;
    private Double price;
}