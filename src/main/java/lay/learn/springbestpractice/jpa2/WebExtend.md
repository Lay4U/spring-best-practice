### Spring Data JPA 웹 확장

1. **도메인 클래스 컨버터 사용**:
    - 도메인 클래스 컨버터를 사용하면 ID를 통해 해당 엔티티 객체를 반환할 수 있습니다.
    - 이 방법은 단순 조회용이며 트랜잭션이 필요하지 않기 때문에, 복잡한 처리가 필요 없는 경우에는 이 기능을 사용하지 않는 것이 좋습니다.

2. **Pageable을 요청 파라미터로 사용**:
    - 요청 파라미터로 `Pageable` 객체를 받아 페이징 처리를 할 수 있습니다.
    - 예시: `page=0&size=20&sort=username,desc&sort=createdDate,desc`
    - 전역적으로 설정하거나 `@PageDefault`를 사용하여 개별적으로 설정할 수 있습니다.

3. **페이징 정보의 접두사 사용**:
    - 페이징 정보가 둘 이상일 경우, 각 페이징에 접두사를 붙여 구분할 수 있습니다.
    - 예시: `/members?member_page=0&order_page=1`는 `@Qualifier("member") Pageable memberPageable`와 `@Qualifier("order") Pageable orderPageable`로 처리할 수 있습니다.

4. **페이지 내용의 DTO 변환**:
    - 페이지 결과는 엔티티가 아닌 DTO로 변환하여 반환하는 것이 좋습니다.
    - 예시: `memberRepository.findAll(pageable).map(MemberDto::new)`를 사용하여 엔티티를 DTO로 변환할 수 있습니다.
