# 롬복(Lombok) 사용법 및 안티패턴 가이드

이 프로젝트는 롬복(Lombok) 라이브러리를 사용할 때 발생할 수 있는 안티패턴과 이를 피하는 방법에 대해 설명합니다.

## 문서 목록

- [`AllArgsConstructorAntiPattern.md`](./AllArgsConstructorAntiPattern.md): `@AllArgsConstructor` 안티패턴에 대한 자세한 설명 및 해결책.
- [`BuilderAntiPattern.md`](./BuilderAntiPattern.md): `@Builder` 안티패턴에 대한 자세한 설명 및 해결책.
- [`DataAntipattern.md`](./DataAntipattern.md): `@Data` 안티패턴에 대한 자세한 설명 및 해결책.
- [`SetterAntipattern.md`](./SetterAntipattern.md): `@Setter` 안티패턴에 대한 자세한 설명 및 해결책.

각 문서는 롬복 어노테이션의 사용법과 함께 잠재적으로 문제가 될 수 있는 사용 패턴을 설명하고, 이에 대한 모범 사례를 제공합니다.

## 롬복 어노테이션 사용 시 주의사항

롬복은 자바 개발자들에게 많은 편의성을 제공하지만, 사용 시 주의가 필요합니다. 잘못된 사용은 불필요한 버그를 유발하고 코드의 유지보수성을 저하시킬 수 있습니다. 이 문서들은 다음과 같은 내용을 담고 있습니다:

- 필드 순서에 민감한 생성자의 문제점
- 불필요한 메소드 자동 생성에 대한 고려
- 객체의 불변성을 유지하는 방법
- 캡슐화와 데이터 무결성을 강화하는 방법

각 안티패턴 문서는 이와 같은 문제들을 식별하고, 실제 프로젝트에서 롬복을 효과적으로 사용하는 방법을 제안합니다.

## 추가 리소스

프로젝트에 대한 더 자세한 내용은 각 마크다운 문서를 참조하시거나, [롬복 공식 문서](https://projectlombok.org/)를 방문하세요.
