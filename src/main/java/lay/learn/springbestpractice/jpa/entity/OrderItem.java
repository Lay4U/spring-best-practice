package lay.learn.springbestpractice.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    @Builder
    public OrderItem(int orderPrice, int count) {
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void changeOrder(Order order) {
        if(this.order != null) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        if(order != null && !order.getOrderItems().contains(this)) {
            order.getOrderItems().add(this);
        }
    }

    public void changeItem(Item item) {
        if(item != null) {
            this.item = item;
        }
    }
}
