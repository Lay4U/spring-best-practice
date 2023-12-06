package lay.learn.springbestpractice.jpa.controller;

import lay.learn.springbestpractice.jpa.dto.OrderSimpleQueryDto;
import lay.learn.springbestpractice.jpa.dto.SimpleOrderDto;
import lay.learn.springbestpractice.jpa.entity.Order;
import lay.learn.springbestpractice.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> new SimpleOrderDto(order.getId(),
                        order.getMember().getName(),
                        order.getOrderDate(),
                        order.getStatus(),
                        order.getDelivery().getAddress()))
                .toList();
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberAndDelivery();
        return orders.stream()
                .map(order -> new SimpleOrderDto(
                        order.getId(),
                        order.getMember().getName(),
                        order.getOrderDate(),
                        order.getStatus(),
                        order.getDelivery().getAddress()
                )).toList();

    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }


}
