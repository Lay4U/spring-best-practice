package lay.learn.springbestpractice.jpa.dto;

import lay.learn.springbestpractice.jpa.entity.Address;
import lay.learn.springbestpractice.jpa.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record OrderQueryDto(
        Long orderId,
        String name,
        LocalDateTime orderDate,
        OrderStatus orderStatus,
        Address address,
        List<OrderItemQueryDto> orderItems
) {

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this(orderId, name, orderDate, orderStatus, address, new ArrayList<>());
    }
}
