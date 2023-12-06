package lay.learn.springbestpractice.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(LocalDateTime orderDate, OrderStatus status) {
        this.orderDate = orderDate;
        this.status = status;
    }

    public void changeMember(Member member) {
        if (this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        if (member != null && !member.getOrders().contains(this)) {
            member.getOrders().add(this);
        }
    }

    public void changeDelivery(Delivery delivery) {
        if (this.delivery != null) {
            this.delivery.changeOrder(null);
        }
        this.delivery = delivery;
        if (delivery != null && delivery.getOrder() != this) {
            delivery.changeOrder(this);
        }
    }

    public void changeOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }



}
