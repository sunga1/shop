package sun0aaa.s.shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun0aaa.s.shop.Entity.*;
import sun0aaa.s.shop.dto.CartDto;
import sun0aaa.s.shop.dto.OrderDto;
import sun0aaa.s.shop.dto.OrderHistDto;
import sun0aaa.s.shop.dto.OrderItemDto;
import sun0aaa.s.shop.repository.ItemRepository;
import sun0aaa.s.shop.repository.MemberRepository;
import sun0aaa.s.shop.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final DeliveryService deliveryService;

    @Transactional
    public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email).get();
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        Delivery delivery = member.getDelivery();
        Delivery orderDelivery = deliveryService.orderDelivery(delivery);
        Order order = Order.createOrder(member, orderItemList,orderDelivery);
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public Long cartOrder(List<OrderDto> orderDtoList, String email){
        Member member = memberRepository.findByEmail(email).get();
        List<OrderItem> orderItemList = new ArrayList<>();
        for(OrderDto orderDto:orderDtoList){
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }
        Delivery delivery = member.getDelivery();
        Delivery orderDelivery = deliveryService.orderDelivery(delivery);
        Order order = Order.createOrder(member, orderItemList,orderDelivery);
        orderRepository.save(order);
        return order.getId();
    }

    public Page<OrderHistDto> getOrderList(String email, PageRequest pageRequest){
        Page<Order> orders = orderRepository.findAllByMemberEmail(email, pageRequest);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order: orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            //System.out.println(order.getId());
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<>(orderHistDtos,pageRequest,totalCount);
    }

    public Page<OrderHistDto> getAllOrderList(PageRequest pageRequest){
        Page<Order> allOrders = orderRepository.findAll(pageRequest);
        long totalCount = orderRepository.count();
        List<OrderHistDto> orderHistDtos = new ArrayList<>();
        for(Order order:allOrders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<>(orderHistDtos,pageRequest,totalCount);
    }

    public List<OrderItemDto> getOrderDetail(String email, Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        List<OrderItemDto> orderItemDtoList=new ArrayList<>();
        List<OrderItem> orderItems = order.getOrderItems();
        for(OrderItem orderItem:orderItems){
            UploadImage uploadImage = orderItem.getItem().getUploadImages().get(0);
            OrderItemDto orderItemDto = new OrderItemDto(orderItem, uploadImage);
            orderItemDtoList.add(orderItemDto);
        }
        return orderItemDtoList;
    }

    public LocalDateTime getOrderDate(Long orderId){
        Order order = orderRepository.findById(orderId).get();
        return order.getCreatedAt();
    }

    @Transactional
    public boolean cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        if(order.cancel()){
            return true;
        }
        else{
            return false;
        }
    }

}
