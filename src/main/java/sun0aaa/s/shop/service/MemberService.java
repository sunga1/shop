package sun0aaa.s.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import sun0aaa.s.shop.Entity.Address;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.dto.MemberDto;
import sun0aaa.s.shop.dto.MemberJoinRequest;
import sun0aaa.s.shop.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final MailSendService mailSendService;

    public BindingResult joinValid(MemberJoinRequest req, BindingResult bindingResult){
        if(req.getEmail().isEmpty()){
            bindingResult.addError(new FieldError("req","email","아이디가 비어있습니다."));
        } else if(memberRepository.existsByEmail(req.getEmail())){
            bindingResult.addError(new FieldError("req","email","아이디가 중복됩니다."));
        }
        if(!mailSendService.checkAuthNum(req.getEmail(),req.getVerificationCode())){
            bindingResult.addError(new FieldError("req","verificationCode","인증번호를 확인하세요."));
        }
        if(req.getPassword().isEmpty()){
            bindingResult.addError(new FieldError("req","password","비밀번호가 비어있습니다."));
        } else if(!req.getPassword().equals(req.getPasswordCheck())){
            bindingResult.addError(new FieldError("req","passwordCheck","비밀번호가 일치하지 않습니다."));
        }
        if(req.getName().isEmpty()){
            bindingResult.addError(new FieldError("req","name","이름이 비어있습니다."));
        }
        return bindingResult;
    }

    public Member join(MemberJoinRequest req){
        Member member = memberRepository.save(req.toEntity(encoder.encode(req.getPassword())));
        return member;
    }

    public Member myInfo(String email){
        return memberRepository.findByEmail(email).get();
    }

    public BindingResult editValid(MemberDto dto, BindingResult bindingResult, String email){
        Member member = memberRepository.findByEmail(email).get();
        if(dto.getNowPassword().isEmpty()){
            bindingResult.addError(new FieldError("dto","nowPassword","현재 비밀번호가 비어있습니다."));
        } else if(!encoder.matches(dto.getNowPassword(),member.getPassword())){
            bindingResult.addError(new FieldError("dto","nowPassword","현재 비밀번호가 틀렸습니다."));
        }
        if(!dto.getNewPassword().equals(dto.getNewPasswordCheck())){
            bindingResult.addError(new FieldError("dto","newPasswordCheck","비밀번호가 일치하지 않습니다."));
        }
        if(dto.getName().isEmpty()){
            bindingResult.addError(new FieldError("dto","name","이름이 비어있습니다."));
        }
        return bindingResult;
    }

    @Transactional
    public void edit(MemberDto memberDto,String email){
        Member member = memberRepository.findByEmail(email).get();
        if(memberDto.getNewPassword().equals("")){
            member.edit(member.getPassword(),memberDto.getName());
        } else{
            member.edit(encoder.encode(memberDto.getNewPassword()),memberDto.getName());
        }
    }


    public boolean delete(String email, String nowPassword) {
        Member member = memberRepository.findByEmail(email).get();
        if(encoder.matches(nowPassword,member.getPassword())){
            memberRepository.delete(member);
            return true;
        }
        else return false;
    }


}
