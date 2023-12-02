# Builder Annotation Anti-Pattern

## Description

`@Builder`는 클래스나 생성자에 적용하여 빌더 패턴을 생성할 수 있습니다. 편리하지만 클래스에 적용할 경우 의도치 않게 모든 필드를 노출시킬 수 있으며 `@AllArgsConstructor`와 유사한 문제를 일으킬 수 있습니다.

## Example code

```java
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
```

## Problems

### Class-Level `@Builder`

1. **Over-Inclusiveness**:
    - 클래스 레벨에서 `@Builder`를 사용하면, 모든 필드를 빌더에 포함시킵니다. 이는 불필요한 필드까지 노출시킬 수 있으며, 객체의 무결성을 해칠 수 있습니다.

2. **Limited Flexibility**:
    - 클래스에 여러 생성자가 필요한 경우, 클래스 레벨의 `@Builder`는 이러한 유연성을 제한할 수 있습니다.

3. **Field Order Sensitivity**:
    - 클래스 레벨에서 `@Builder`를 사용할 경우, 필드의 순서 변경이 빌더 생성자의 매개변수 순서에 직접적인 영향을 미칩니다. 이는 `@AllArgsConstructor`에서 발생하는 문제와 유사하며, 필드 타입이 같을 경우 런타임에 오류를 유발할 수 있습니다.

### Constructor-Level `@Builder`

1. **Selective Building**:
    - 생성자 레벨에서 `@Builder`를 사용하면, 선택적으로 필드를 포함할 수 있습니다. 이는 객체 생성 시 필요한 필드만을 명시적으로 포함시키는 데 도움이 됩니다.

2. **Enhanced Flexibility**:
    - 특정 생성자에 `@Builder`를 적용함으로써 더 유연한 객체 생성이 가능해집니다. 다양한 시나리오에 맞는 여러 빌더를 구현할 수 있습니다.

## Solutions

- 객체의 무결성과 필요한 필드만을 노출시키기 위해, 가능한 한 생성자 레벨에서 `@Builder`를 사용합니다.
- 클래스에 여러 생성자가 필요한 경우, 각 생성자에 대해 별도의 `@Builder`를 적용하여 더 많은 유연성을 제공합니다.
- 필드 순서의 민감도를 고려하여, 생성자 레벨의 `@Builder` 사용을 권합니다.
