package sun0aaa.s.shop.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sun0aaa.s.shop.exception.NotEnoughStockException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotEnoughStockException.class)
    public String handleNotEnoughStockException(NotEnoughStockException e, Model model){
        model.addAttribute("message","재고수량보다 주문수량이 많습니다.");
        model.addAttribute("nextUrl","/");
        return "printMessage";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException e,Model model){
        model.addAttribute("message","상품이 존재하지 않습니다.");
        model.addAttribute("nextUrl","/items");
        return "printMessage";
    }
}
