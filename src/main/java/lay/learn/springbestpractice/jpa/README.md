# 프로젝트 README

## 개요
이 프로젝트는 김영한님의 인프런 강의를 기반으로 작성된 JPA 관련 소스 코드를 포함하고 있습니다. JPA 연관 관계에 집중하여 `dto record` 패키지를 별도로 구성하였고, 이를 통해 다양한 JPA 기능과 최적화 방법을 탐구합니다.

강의에서는 spring-data-jpa를 사용하지 않았지만, jpa 패키지에서는 spring-data-jpa를 사용했습니다.

## 기술 스택
- **JPA**: Java Persistence API를 사용하여 데이터베이스와의 상호 작용을 구현합니다.
- **Spring Data JPA**: 기본적인 강의에서는 사용되지 않았으나, `jpa` 패키지 내에서는 Spring Data JPA를 활용하여 보다 효율적인 데이터 접근과 관리를 가능하게 합니다.

## 주요 내용
이 프로젝트는 연관관계 매핑과 Lazy Loading에서의 N+1 문제 해결 방법에 중점을 두고 있습니다. JPA 연관 관계 매핑을 통해 데이터 모델 간의 관계를 효과적으로 구성하고, Lazy Loading을 적절히 활용하여 성능 최적화를 도모합니다. 특히 N+1 문제를 예방하고 해결하는 다양한 전략을 탐구하여, 데이터 접근 효율성을 높이고 있습니다.

## 참고 내용
- [ToOneFetchType.md](./ToOneFetchType.md): `ToOne` 연관관계 매핑에서 `EAGER`와 `LAZY` 로딩의 베스트 프랙티스와 안티 패턴에 대해 설명합니다.
- [CascadeTypes.md](./CascadeTypes.md): JPA에서 Cascade 옵션의 사용법, 그리고 이에 따른 베스트 프랙티스와 안티 패턴을 다룹니다.
- [ConstructorBuilder.md](./ConstructorBuilder.md): 생성자에서 원시형 타입만 포함하는 접근법의 장단점을 분석합니다.
- [NoArgsConstructorFundamental.md](./NoArgsConstructorFundamental.md): JPA 엔티티 클래스에서 기본 생성자(no-args constructor) 제공의 중요성과 성능 향상 측면을 설명합니다.

## 출처 및 참고자료
- 인프런 김영한님 강의: [김영한의 스프링 부트와 JPA 실무 완전 정복 로드맵](https://www.inflearn.com/roadmaps/149)
