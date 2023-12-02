# AllArgsConstructor Anti-Pattern

## Description

`@AllArgsConstructor`는 롬복(Lombok) 라이브러리에서 클래스의 모든 필드에 대한 생성자를 자동으로 생성하는 어노테이션입니다. 이 어노테이션은 코드를 간결하게 만들지만, 다음과 같은 몇 가지 중요한 문제점을 야기할 수 있습니다.

## Example Code

```java
@AllArgsConstructor
public class AllArgsConstructAntiPattern {
    private Long id;
    private String description;
    private String name;
    private Double price;
}
```

## Problems

1. **Field Order Sensitivity**:
    - `@AllArgsConstructor`는 클래스 내 필드의 정의 순서에 따라 생성자의 매개변수를 결정합니다. 필드 순서의 변경은 생성자 매개변수의 순서에 직접적인 영향을 미치며, 이는 필드 타입이 같을 경우 런타임에 심각한 오류를 유발할 수 있습니다. 예를 들어, 필드의 순서를 변경하면 기존 생성자를 사용하는 코드가 잘못된 데이터를 전달받게 되어 의도치 않은 동작이나 결과를 초래할 수 있습니다.
    - 이 문제는 특히 리팩토링 과정에서 필드 순서가 변경될 때 주의를 요합니다. 필드 순서의 변화가 생성자의 시그니처에 직접적으로 영향을 미치기 때문에, 기존에 생성자를 사용하던 코드가 예상치 못한 방식으로 동작할 위험이 있습니다.

2. **Refactoring Difficulty**:
    - 필드가 추가되거나 제거될 때마다 생성자의 시그니처가 변경됩니다. 이는 리팩토링을 어렵게 만들고, 기존 코드와의 호환성 문제를 발생시킬 수 있습니다.

3. **Encapsulation Breach**:
    - 모든 필드에 대한 접근을 허용하는 생성자는 클래스의 캡슐화를 위반할 수 있습니다. 불변성이 중요한 객체에는 부적합할 수 있습니다.


## Solutions

- 필요한 필드만을 포함하는 생성자를 명시적으로 선언합니다.
- `@AllArgsConstructor` 대신 `@RequiredArgsConstructor`나 `@NoArgsConstructor`를 사용하여 필요한 생성자만을 생성합니다. 이렇게 하면 필드 순서 변경에 따른 영향을 최소화할 수 있습니다.

