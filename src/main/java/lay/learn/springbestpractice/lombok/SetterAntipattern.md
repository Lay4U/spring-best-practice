# Setter Method Anti-Pattern in `SetterAntiPattern` Class

## Description

`SetterAntiPattern` 클래스는 `@Setter` 어노테이션을 사용하여 `level` 필드의 값을 변경할 수 있게 합니다. 하지만 이러한 접근 방식은 객체의 불변성, 캡슐화 및 데이터 무결성을 해치는 안티패턴으로 간주될 수 있습니다. 대신, `levelUp` 메소드를 사용하여 레벨을 관리하는 것이 더 바람직한 접근 방법입니다.

## Example Code

```java
public class SetterAntiPattern {
    //@Setter
    private int level;
    
    public int levelUp(){
        return this.level++;
    }
}
```

## Problems with Using `@Setter`

1. **Breaking Immutability**:
    - `@Setter`를 사용하면, 객체의 `level` 필드가 외부에서 변경될 수 있어 객체의 불변성이 깨집니다.

2. **Violating Encapsulation**:
    - `level` 필드에 직접 접근하여 값을 변경하는 것은 클래스의 캡슐화 원칙을 위반합니다.

3. **Unpredictable State Changes**:
    - `@Setter`를 통한 필드 값 변경은 객체의 상태를 예측 불가능하게 만들 수 있습니다.

4. **Data Integrity Issues**:
    - `@Setter`를 사용하면, `level` 필드를 임의의 값으로 설정할 수 있어 데이터의 무결성을 해칠 수 있습니다.


## Solutions

1. **Use Constructors for Initialization**:
    - 객체 생성 시 모든 필요한 데이터를 생성자를 통해 전달합니다. 이 방법을 사용하면 객체가 일관된 상태로 유지됩니다.

2. **Utilize Immutable Objects**:
    - 불변 객체를 사용하여 객체의 상태가 생성 후 변경되지 않도록 합니다. 이는 특히 동시성 문제가 중요한 멀티스레드 환경에서 유용합니다.

3. **Adopt Builder Pattern**:
    - 빌더 패턴을 사용하여 객체의 생성과정을 더 명확하게 만들고, 필요한 속성만 설정할 수 있게 합니다. 빌더 패턴은 객체의 불변성을 유지하는 데 도움이 됩니다.

4. **Limit Field Exposure**:
    - 필요한 경우에만 제한적으로 `getter` 메소드를 제공하고, `setter` 메소드는 제공하지 않아 객체의 내부 상태를 보호합니다.


## Recommended Approach

- **Use `levelUp` Method for Level Management**:
    - `level` 필드의 값을 변경하기 위해 `levelUp` 메소드를 사용합니다. 이 메소드는 레벨을 안전하게 관리하고, 클래스의 상태 변경을 제어합니다.

## Conclusion

`SetterAntiPattern` 클래스에서 `@Setter`를 사용하는 것은 여러 문제를 야기할 수 있습니다. 대신, `levelUp`과 같은 메소드를 사용하여 객체의 상태를 변경하는 것이 캡슐화, 데이터 무결성, 그리고 객체의 불변성을 유지하는 데 더욱 효과적입니다.
