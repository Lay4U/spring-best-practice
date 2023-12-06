package lay.learn.springbestpractice.jpa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record OrderItemQueryDto(
        @JsonIgnore
        Long orderId,
        String itemName,
        int orderPrice,
        int count
) {
}
