package sun0aaa.s.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import sun0aaa.s.shop.Entity.Delivery;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.dto.DeliveryDto;
import sun0aaa.s.shop.repository.DeliveryRepository;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public BindingResult editValid(DeliveryDto dto,BindingResult bindingResult){
        if(dto.getDeliveryName().isEmpty()){
            bindingResult.addError(new FieldError("dto","deliveryName","배송지명이 비어있습니다."));
        }
        if(dto.getMemberName().isEmpty()){
            bindingResult.addError(new FieldError("dto","memberName","이름이 비어있습니다."));
        }
        if(dto.getPhoneNumber()==null){
            bindingResult.addError(new FieldError("dto","phoneNumber","전화번호가 비어있습니다."));
        }
        if(dto.getZipcode().isEmpty()){
            bindingResult.addError(new FieldError("dto","zipcode","우편번호가 비어있습니다."));
        }
        return bindingResult;
    }

    @Transactional
    public Delivery editDelivery(Member member,DeliveryDto dto){
        Delivery newDelivery=DeliveryDto.toEntity(dto);
        member.deliveryCreate(newDelivery);

        return newDelivery;
    }

    @Transactional
    public Delivery orderDelivery(Delivery delivery){
        Delivery orderDelivery = Delivery.builder()
                .member(delivery.getMember())
                .deliveryName(delivery.getDeliveryName())
                .memberName(delivery.getMemberName())
                .address(delivery.getAddress())
                .phoneNumber(delivery.getPhoneNumber())
                .build();
        deliveryRepository.save(orderDelivery);
        return orderDelivery;
    }
}
