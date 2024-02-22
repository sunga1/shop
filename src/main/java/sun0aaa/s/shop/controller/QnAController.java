package sun0aaa.s.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun0aaa.s.shop.Entity.QnA;
import sun0aaa.s.shop.Entity.QnADto;
import sun0aaa.s.shop.dto.ItemDto;
import sun0aaa.s.shop.dto.QnAAnswerRequest;
import sun0aaa.s.shop.dto.QnAJoinRequest;
import sun0aaa.s.shop.repository.QnARepository;
import sun0aaa.s.shop.service.ItemService;
import sun0aaa.s.shop.service.MemberService;
import sun0aaa.s.shop.service.QnAService;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/qna")
public class QnAController {
    private final QnAService qnaService;
    private final QnARepository qnaRepository;
    private final MemberService memberService;
    private final ItemService itemService;
    @GetMapping("/{itemId}")
    public String QnAListPage(@PathVariable Long itemId, Model model,
                                 @RequestParam(required = false,defaultValue = "1")int page, Authentication auth){
        if(auth!=null){
            model.addAttribute("loginUserLoginId",auth.getName());
        }
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<QnA> qnas = qnaService.findQnAByItemId(itemId,pageRequest);
        ItemDto itemDto = itemService.findById(itemId);
        model.addAttribute("qnas",qnas);
        model.addAttribute("qnaAnswer",new QnAAnswerRequest());
        model.addAttribute("itemDto",itemDto);
        return "qna/list";
    }
    @GetMapping("/all")
    public String allQnAListPage(@PathVariable Long itemId, Model model,
                              @RequestParam(required = false,defaultValue = "1")int page, Authentication auth){
        if(auth!=null){
            model.addAttribute("loginUserLoginId",auth.getName());
        }
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<QnADto> qnas = qnaService.findAllQnA(pageRequest);
        model.addAttribute("qnaDtoList",qnas);
        model.addAttribute("qnaAnswer",new QnAAnswerRequest());
        return "qna/list";
    }
    @PostMapping("/{itemId}/{qnaId}/answer")
    public String AnswerJoin(@PathVariable Long itemId, @PathVariable Long qnaId, Model model, @ModelAttribute QnAAnswerRequest answerRequest){
        qnaService.saveQnAAnswer(qnaId,answerRequest);
        model.addAttribute("nextUrl","/qna/"+itemId);
        model.addAttribute("message","답변이 등록되었습니다!");
        return "printMessage";
    }

    @PostMapping("/{itemId}/{qnaId}/delete")
    public String QnADelete(@PathVariable Long itemId,@PathVariable Long qnaId, Model model){
        qnaService.deleteQnA(qnaId);
        model.addAttribute("nextUrl","/qna/"+itemId);
        model.addAttribute("message","QnA가 삭제되었습니다!");
        return "printMessage";
    }
    @GetMapping("/{itemId}/write")
    public String QnAWrite(@PathVariable Long itemId, Model model){
        ItemDto itemDto = itemService.findById(itemId);
        model.addAttribute("itemDto",itemDto);
        model.addAttribute("qnaJoinRequest",new QnAJoinRequest());
        return "qna/write";
    }
    @PostMapping("/{itemId}/write")
    public String QnACreate(@PathVariable Long itemId, @ModelAttribute QnAJoinRequest qnaJoinRequest,
                                Authentication auth, Model model) throws IOException {
        QnA qna = qnaService.saveQnA(itemId, qnaJoinRequest, auth);
        model.addAttribute("nextUrl","/qna/"+itemId);
        model.addAttribute("message","Q&A가 등록되었습니다!");
        return "printMessage";
    }
}
