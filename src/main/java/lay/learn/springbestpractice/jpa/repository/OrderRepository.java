package lay.learn.springbestpractice.jpa.repository;

import lay.learn.springbestpractice.jpa.dto.OrderItemQueryDto;
import lay.learn.springbestpractice.jpa.dto.OrderQueryDto;
import lay.learn.springbestpractice.jpa.dto.OrderSimpleQueryDto;
import lay.learn.springbestpractice.jpa.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
                select o from Order o
                join fetch o.member m
                join fetch o.delivery d
            """
    )
    List<Order> findAllWithMemberAndDelivery();

    @Query("""
                select new lay.learn.springbestpractice.jpa.dto.OrderSimpleQueryDto(
                o.id,
                m.name,
                o.orderDate,
                o.status,
                d.address)
                from Order o 
                join o.member m 
                join o.delivery d
            """)
    List<OrderSimpleQueryDto> findOrderDtos();

    @Query("""
                select distinct o from Order o 
                join fetch o.member m 
                join fetch o.delivery d 
                join fetch o.orderItems oi 
                join fetch oi.item i 
            """)
    List<Order> findAllWithItem();

    @Query("""
                select o from Order o 
                join fetch o.member m 
                join fetch o.delivery d 
            """)
    List<Order> findAllWithMemberDelivery(Pageable pageable);

    @Query("""
                select new lay.learn.springbestpractice.jpa.dto.OrderQueryDto(
                    o.id,
                    m.name,
                    o.orderDate,
                    o.status,
                    d.address
                ) from Order o 
                join o.member m 
                join o.delivery d 
            """)
    List<OrderQueryDto> findOrders();

    @Query("""
                select new lay.learn.springbestpractice.jpa.dto.OrderItemQueryDto(
                    oi.order.id,
                    i.name,
                    oi.orderPrice,
                    oi.count
                ) from OrderItem oi 
                join oi.item i 
                where oi.order.id =:orderId
            """)
    List<OrderItemQueryDto> findOrderItems(@Param("orderId") Long orderId);

    @Query("""
                select new lay.learn.springbestpractice.jpa.dto.OrderItemQueryDto(
                oi.order.id,
                i.name,
                oi.orderPrice,
                oi.count)
                from OrderItem oi 
                join oi.item i 
                where oi.order.id in :orderIds
            """)
    List<OrderItemQueryDto> findOrderItemsByOrderIds(@Param("orderIds") List<Long> orderIds);
}