package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.PersistenceContext;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    @ResponseBody
    public Order order() {
        // 1. 멤버생성
        Member member = new Member();
        member.setName("윤지수");
        member.setAddress(new Address("인천", "영종도", "10004"));
        Long memberId = memberService.join(member);

        // 2. 상품생성
        Book book = new Book();
        book.setName("해리포터");
        book.setPrice(5000);
        book.setStockQuantity(50);
        Long itemId = itemService.saveItem(book);

        // 주문 !
        Long orderId = orderService.order(memberId, itemId, 51);

        // 등록한 주문조회 -> 클라이언트한테 응답
        return orderService.findOne(orderId);
    }

}
