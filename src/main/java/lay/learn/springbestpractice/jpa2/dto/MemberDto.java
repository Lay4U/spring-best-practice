package lay.learn.springbestpractice.jpa2.dto;

public record MemberDto(
        Long id,
        String username,
        String teamName
) {
}
