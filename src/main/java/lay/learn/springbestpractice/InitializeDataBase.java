package lay.learn.springbestpractice;

import jakarta.persistence.EntityManager;
import lay.learn.springbestpractice.jpa.entity.*;
import lay.learn.springbestpractice.jpa.repository.ItemRepository;
import lay.learn.springbestpractice.jpa.repository.MemberRepository;
import lay.learn.springbestpractice.jpa.repository.OrderRepository;
import lay.learn.springbestpractice.jpa2.entity.Member2;
import lay.learn.springbestpractice.jpa2.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitializeDataBase implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final EntityManager em;
    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        dbInit1();
//        dbInit2();
        dbInit_QueryDsl();
    }

    private void dbInit_QueryDsl() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        for (int i = 0; i < 100; i++) {
            Team selectedTeam = i % 2 == 0 ? teamA : teamB;
            em.persist(new Member2("member" + i, i, selectedTeam));
        }
    }

    private void dbInit2() {
        Member member = Member.builder()
                .name("userB")
                .address(
                        Address.builder()
                                .city("진주")
                                .street("2")
                                .zipcode("2222")
                                .build()
                )
                .build();

        memberRepository.save(member);

        Book book1 = Book.builder()
                .name("Spring1 BOOK")
                .price(20000)
                .stockQuantity(200)
                .build();
        itemRepository.save(book1);

        Book book2 = Book.builder()
                .name("JPA2 BOOK")
                .price(40000)
                .stockQuantity(300)
                .build();
        itemRepository.save(book2);

        OrderItem orderItem1 = OrderItem.builder()
                .orderPrice(book1.getPrice())
                .count(3)
                .build();
        orderItem1.changeItem(book1);

        OrderItem orderItem2 = OrderItem.builder()
                .orderPrice(book2.getPrice())
                .count(4)
                .build();
        orderItem2.changeItem(book2);

        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .build();

        Order order = Order.builder().build();
        orderRepository.save(order);
        order.changeMember(member);
        order.changeDelivery(delivery);
        orderItem1.changeOrder(order);
        orderItem2.changeOrder(order);

        em.flush();
        em.clear();
    }

    private void dbInit1() {
        Member member = Member.builder()
                .name("userA")
                .address(
                        Address.builder()
                                .city("서울")
                                .street("1")
                                .zipcode("1111")
                                .build()
                )
                .build();

        memberRepository.save(member);

        Book book1 = Book.builder()
                .name("JPA1 BOOK")
                .price(10000)
                .stockQuantity(100)
                .build();
        itemRepository.save(book1);

        Book book2 = Book.builder()
                .name("JPA2 BOOK")
                .price(20000)
                .stockQuantity(100)
                .build();
        itemRepository.save(book2);

        OrderItem orderItem1 = OrderItem.builder()
                .orderPrice(book1.getPrice())
                .count(1)
                .build();
        orderItem1.changeItem(book1);

        OrderItem orderItem2 = OrderItem.builder()
                .orderPrice(book2.getPrice())
                .count(2)
                .build();
        orderItem2.changeItem(book2);

        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .build();

        Order order = Order.builder().build();
        orderRepository.save(order);
        order.changeMember(member);
        order.changeDelivery(delivery);
        orderItem1.changeOrder(order);
        orderItem2.changeOrder(order);
        em.flush();
        em.clear();
    }


}
