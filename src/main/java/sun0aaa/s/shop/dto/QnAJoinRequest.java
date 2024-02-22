package sun0aaa.s.shop.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import sun0aaa.s.shop.Entity.AnswerStatus;
import sun0aaa.s.shop.Entity.QnA;

@Data
public class QnAJoinRequest {
    private String title;
    private String content;

    private MultipartFile uploadImage;

    public static QnA toEntity(QnAJoinRequest qnaJoinRequest){
        return QnA.builder()
                .title(qnaJoinRequest.getTitle())
                .content(qnaJoinRequest.getContent())
                .answerStatus(AnswerStatus.READY)
                .build();
    }
}
