package sun0aaa.s.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun0aaa.s.shop.Entity.Order;
import sun0aaa.s.shop.Entity.QnADto;
import sun0aaa.s.shop.dto.OrderHistDto;
import sun0aaa.s.shop.dto.QnAAnswerRequest;
import sun0aaa.s.shop.repository.OrderRepository;
import sun0aaa.s.shop.service.OrderService;
import sun0aaa.s.shop.service.QnAService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {
    private final OrderService orderService;
    private final QnAService qnAService;
    @GetMapping("/adminPage")
    public String adminPage(){
        return "admins/adminPage";
    }

    @GetMapping("/orders")
    public String AllOrderList(@RequestParam(required = false,defaultValue = "1") int page, Model model){
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<OrderHistDto> allOrderList = orderService.getAllOrderList(pageRequest);
        model.addAttribute("allOrderHistDtoList",allOrderList);
        return "admins/orders";
    }

    @GetMapping("/qna")
    public String AllQnaList(@RequestParam(required = false,defaultValue = "1") int page, Model model){
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<QnADto> allQnA = qnAService.findAllQnA(pageRequest);
        model.addAttribute("qnaDtoList",allQnA);
        model.addAttribute("qnaAnswer",new QnAAnswerRequest());
        return "admins/qnas";
    }


}
