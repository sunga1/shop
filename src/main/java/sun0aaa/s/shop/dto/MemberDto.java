package sun0aaa.s.shop.dto;

import lombok.Builder;
import lombok.Data;
import sun0aaa.s.shop.Entity.Member;

@Data
@Builder
public class MemberDto {
    private String email;
    private String nowPassword;
    private String newPassword;
    private String newPasswordCheck;
    private String name;
    private DeliveryDto deliveryDto;

    public static MemberDto of(Member member){
        DeliveryDto deliveryDto1 = DeliveryDto.of(member.getDelivery());
        return MemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .deliveryDto(deliveryDto1)
                .build();
    }
}
