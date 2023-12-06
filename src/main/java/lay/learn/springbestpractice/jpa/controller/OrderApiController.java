package lay.learn.springbestpractice.jpa.controller;

import lay.learn.springbestpractice.jpa.dto.OrderDto;
import lay.learn.springbestpractice.jpa.dto.OrderItemDto;
import lay.learn.springbestpractice.jpa.dto.OrderItemQueryDto;
import lay.learn.springbestpractice.jpa.dto.OrderQueryDto;
import lay.learn.springbestpractice.jpa.entity.Order;
import lay.learn.springbestpractice.jpa.entity.OrderItem;
import lay.learn.springbestpractice.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    /*
     * 엔티티 직접 노출, LAZY=null, 양방향 관계 문제 발생*/
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    /*
     * order 1번
     * member, address N번
     * orderItem N번
     * item N번 조회*/
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll();
        return toOrderDtos(orders);
    }

    /*
     * 페치조인으로 SQL 1번만 실행
     * 페이징 불가능, 컬렉션 페치 조인은 1개만 사용 가능
     * 컬렉션을 페치 조인시 페이징 불가능 일대 다 관계에서 일을 기준으로 페이징을 해야 하는데 데이터는 다를 기준으로 생성이됨.
     * 이경우 하이버네이트는 모든 DB를 읽어 메모리에서 페이징 시도 -> 데이터 잘못불러올시 장애로 이어짐.*/
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        return toOrderDtos(orders);
    }

    /*
     * ToOne은 페치조인한다.
     * 컬렉션은 지연 로딩으로 조회한다.
     * 지연 로딩 최적화를 위해 default_batch_fetch_size를 설정한다.
     * 쿼리 호출 수가 1+n -> 1+1로 최적화
     * 컬렉션 페이조인이 없으므로 페이징 가능*/
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_Page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                       @RequestParam(value = "limit", defaultValue = "100") int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        List<Order> orders = orderRepository.findAllWithMemberDelivery(pageRequest);
        return toOrderDtos(orders);
    }

    /*
    * Query 루트 1번 실행, 컬렉션 N번 실행
    * To One 관계 먼저 조회, ToMany는 별도로 처리
    * ToOne은 조인해도 row수 증가하지 않는다.
    * ToMany는 조인하면 row수 증가한다.
    * findOrderItems에서 where에 in절로 조회하면 더 빨라질듯
    * */
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = orderRepository.findOrders();
        result.stream()
                .map(order -> new OrderQueryDto(
                order.orderId(),
                order.name(),
                order.orderDate(),
                order.orderStatus(),
                order.address(),
                orderRepository.findOrderItems(order.orderId())
        ));
        return result;
    }


    /*
    * Query 루트 1번, 컬렉션 1번
    * ToOne 먼저 조회하고 식별자인 orderId로 OrderItem을 한번에 조회
    * */
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> orderV5(){
        List<OrderQueryDto> result = orderRepository.findOrders();
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));
        result.stream()
                .map(order -> new OrderQueryDto(
                        order.orderId(),
                        order.name(),
                        order.orderDate(),
                        order.orderStatus(),
                        order.address(),
                        orderItemMap.get(order.orderId())
                ));
        return result;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(OrderQueryDto::orderId)
                .toList();
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds){
        List<OrderItemQueryDto> orderItems = orderRepository.findOrderItemsByOrderIds(orderIds);
        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::orderId));
    }




    private static List<OrderDto> toOrderDtos(List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getMember().getName(),
                        order.getOrderDate(),
                        order.getStatus(),
                        order.getDelivery().getAddress(),
                        toOrderItemDtos(order)
                )).toList();
    }

    private static List<OrderItemDto> toOrderItemDtos(Order order) {
        return order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(
                        orderItem.getItem().getName(),
                        orderItem.getOrderPrice(),
                        orderItem.getCount()
                )).toList();
    }
}
