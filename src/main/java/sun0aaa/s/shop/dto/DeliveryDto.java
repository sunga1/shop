package sun0aaa.s.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun0aaa.s.shop.Entity.Address;
import sun0aaa.s.shop.Entity.Delivery;
import sun0aaa.s.shop.Entity.Member;

@Data
@Builder
public class DeliveryDto {
    private String zipcode;
    private String streetAddress;
    private String detailAddress;
    private String deliveryName;
    private String memberName;
    private String phoneNumber;

    public static DeliveryDto of(Delivery delivery){
        if(delivery==null){
            return DeliveryDto.builder().build();
        }
        return DeliveryDto.builder()
                .deliveryName(delivery.getDeliveryName())
                .memberName(delivery.getMemberName())
                .zipcode(delivery.getAddress().getZipcode())
                .streetAddress(delivery.getAddress().getStreetAddress())
                .detailAddress(delivery.getAddress().getDetailAddress())
                .phoneNumber(delivery.getPhoneNumber())
                .build();
    }

    public static Delivery toEntity(DeliveryDto dto){
        return Delivery.builder()
                .deliveryName(dto.getDeliveryName())
                .address(new Address(dto.getZipcode(), dto.getStreetAddress(), dto.getDetailAddress()))
                .memberName(dto.getMemberName())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
