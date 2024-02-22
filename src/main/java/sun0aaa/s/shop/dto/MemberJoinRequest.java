package sun0aaa.s.shop.dto;

import lombok.Data;
import sun0aaa.s.shop.Entity.Address;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.Entity.Membership;

@Data
public class MemberJoinRequest {
    private String email;
    private String name;
    private String password;
    private String passwordCheck;
    private String verificationCode;

    public Member toEntity(String encodedPassword){
        return Member.builder()
                .email(email)
                .name(name)
                .password(encodedPassword)
                .delivery(null)
                .membership(Membership.FRIEND)
                .build();
    }
}
