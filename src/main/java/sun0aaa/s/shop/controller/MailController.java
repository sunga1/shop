package sun0aaa.s.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun0aaa.s.shop.dto.EmailCheckDto;
import sun0aaa.s.shop.dto.EmailRequestDto;
import sun0aaa.s.shop.exception.AuthNumCheckException;
import sun0aaa.s.shop.service.MailSendService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailSendService mailService;
    @PostMapping("/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto){
        System.out.println("이메일 인증 요청이 들어옴");
        System.out.println("이메일 인증 이메일 :"+emailDto.getEmail());
        return mailService.joinEmail(emailDto.getEmail());
    }
    @PostMapping("/mailAuthCheck")
    public ResponseEntity<String> AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        boolean checked = mailService.checkAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
        try {
            if (checked) {
                return ResponseEntity.ok("ok");
            } else {
                return ResponseEntity.badRequest().body("인증번호 일치하지 않음");
            }
        }catch (AuthNumCheckException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AuthNum 검증 오류: "+e.getMessage());
        }

    }
}
