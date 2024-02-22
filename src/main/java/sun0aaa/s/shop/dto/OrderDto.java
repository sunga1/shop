package sun0aaa.s.shop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {
    @NotNull(message="상품 아이디는 필수 입력값입니다.")
    private Long itemId;

    @Min(value = 1,message = "최소 수량은 1개입니다.")
    @Max(value= 100, message = "최대 주문 수량은 100개입니다.")
    private int count;
}
