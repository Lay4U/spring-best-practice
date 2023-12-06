package lay.learn.springbestpractice.jpa.dto;

import lay.learn.springbestpractice.jpa.entity.Address;
import lay.learn.springbestpractice.jpa.entity.OrderStatus;
import java.time.LocalDateTime;

public record SimpleOrderDto(
        Long orderId,
        String name,
        LocalDateTime orderDate,
        OrderStatus orderStatus,
        Address address
) {

}
