package sun0aaa.s.shop.service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun0aaa.s.shop.Entity.*;
import sun0aaa.s.shop.dto.QnAAnswerRequest;
import sun0aaa.s.shop.dto.QnAJoinRequest;
import sun0aaa.s.shop.dto.ReviewJoinRequest;
import sun0aaa.s.shop.repository.QnARepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final QnARepository qnaRepository;
    private final MemberService memberService;
    private final ItemService itemService;
    private final UploadImageService uploadImageService;
    @Transactional
    public QnA saveQnA(Long itemId, QnAJoinRequest qnaJoinRequest, Authentication auth) throws IOException {
        QnA saveQnA = qnaRepository.save(QnAJoinRequest.toEntity(qnaJoinRequest));
        Member loginMember = memberService.myInfo(auth.getName());
        Item item = itemService.findItem(itemId);
        saveQnA.saveMemberItem(loginMember,item);
        UploadImage uploadImage = uploadImageService.saveQnAImage(qnaJoinRequest.getUploadImage(), saveQnA);
        if(uploadImage!=null){
            saveQnA.setUploadImage(uploadImage);
        }
        return saveQnA;
    }

    public Page<QnA> findQnAByItemId(Long itemId, PageRequest pageRequest) {
        Page<QnA> qnas = qnaRepository.findAllByItemId(itemId,pageRequest);
        return qnas;
    }

    public Page<QnADto> findAllQnA(PageRequest pageRequest){
        Page<QnA> allQnAs = qnaRepository.findAll(pageRequest);
        long count = qnaRepository.count();
        List<QnADto> qnaDtoList=new ArrayList<>();
        for(QnA qna:allQnAs){
            QnADto qnaDto = QnADto.of(qna);
            qnaDtoList.add(qnaDto);
        }
        return new PageImpl<>(qnaDtoList,pageRequest,count);
    }
    @Transactional
    public void deleteQnA(Long qnaId) {
        qnaRepository.deleteById(qnaId);
    }

    @Transactional
    public boolean saveQnAAnswer(Long qnaId, QnAAnswerRequest answerRequest) {
        QnA qna = qnaRepository.findById(qnaId).get();
        qna.createAnswer(answerRequest.getAnswer());
        return true;
    }
}
