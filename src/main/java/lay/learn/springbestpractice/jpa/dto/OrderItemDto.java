package lay.learn.springbestpractice.jpa.dto;

public record OrderItemDto(
        String itemName,
        int orderPrice,
        int count
) {
}
