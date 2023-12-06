# 프로젝트 README

## 개요
이 프로젝트는 김영한님의 인프런 강의를 기반으로 작성된 JPA 관련 소스 코드를 포함하고 있습니다. 본 프로젝트에서는 특히 QueryDSL을 활용한 고급 쿼리 구성 및 최적화에 중점을 두고 있습니다. QueryDSL은 강력한 타입 안전성을 제공하며, 복잡한 쿼리를 보다 명확하고 유지보수가 용이한 코드로 작성할 수 있게 합니다. 이를 통해 JPA를 사용하는 애플리케이션의 성능과 코드 품질을 향상시키는 방법을 탐구합니다.

## QueryDSL
주요 예시는 QuerydslBasicTest.java에 포함되어 있습니다. QueryDSL은 JPA의 Criteria API를 활용하여 작성되었습니다. QueryDSL은 다음과 같은 장점을 제공합니다.
- 동적 쿼리: 쿼리를 동적으로 작성할 수 있습니다. 쿼리를 별도의 클래스로 분리하여 관리할 수 있습니다.
- 타입 안전성: 컴파일 시점에 오류를 잡아줍니다.
- 코드 가독성: 복잡한 쿼리를 보다 명확하게 작성할 수 있습니다.
- 유지보수성: 쿼리를 별도의 클래스로 분리하여 관리할 수 있습니다.
- 통합: JPA, JDO, SQL, MongoDB, Lucene, JDBC 등 다양한 데이터 저장소를 지원합니다.
- Spring Data JPA와 통합: Spring Data JPA의 Repository 인터페이스를 확장하여 사용할 수 있습니다.
- Spring Data JPA의 Sort와 통합: Spring Data JPA의 Sort 인터페이스를 확장하여 사용할 수 있습니다.
- Spring Data JPA의 Pageable과 통합: Spring Data JPA의 Pageable 인터페이스를 확장하여 사용할 수 있습니다.


## 출처 및 참고자료
- 인프런 김영한님 강의: [김영한의 스프링 부트와 JPA 실무 완전 정복 로드맵](https://www.inflearn.com/roadmaps/149)
