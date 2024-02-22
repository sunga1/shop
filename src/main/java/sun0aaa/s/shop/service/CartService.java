package sun0aaa.s.shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun0aaa.s.shop.Entity.*;
import sun0aaa.s.shop.dto.CartDto;
import sun0aaa.s.shop.dto.OrderDto;
import sun0aaa.s.shop.repository.CartRepository;
import sun0aaa.s.shop.repository.ItemRepository;
import sun0aaa.s.shop.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    @Transactional
    public void createCart(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email).get();
        if(cartRepository.existsByMemberEmailAndItemId(email, item.getId())){
            Cart cart = cartRepository.findByMemberEmailAndItemId(email, item.getId()).get();
            cart.increaseCount(orderDto.getCount());
        }
        else {
            Cart cart = Cart.create(item, orderDto.getCount(), member);
            cartRepository.save(cart);
        }
    }

    public List<CartDto> cartList(String email){
        List<Cart> cartList = cartRepository.findAllByMemberEmail(email);
        List<CartDto> cartDtoList=new ArrayList<>();
        for(Cart cart:cartList){
            CartDto cartDto = CartDto.of(cart);
            cartDtoList.add(cartDto);
        }
        return cartDtoList;
    }

    @Transactional
    public boolean deleteCart(Long cartId){
        cartRepository.deleteById(cartId);
        System.out.println("cartId: "+cartId+" 삭제완료");
        return true;
    }

    @Transactional
    public List<OrderDto> changeCartToOrderDto(List<Long> selectedCartIds) {
        if(selectedCartIds==null){
            return null;
        }
        List<OrderDto> orderDtoList=new ArrayList<>();
        for(Long id:selectedCartIds){
            Cart cart = cartRepository.findById(id).get();
            OrderDto orderDto = new OrderDto(cart.getItem().getId(), cart.getCount());
            orderDtoList.add(orderDto);
            cartRepository.delete(cart);
        }
        return orderDtoList;
    }
}
