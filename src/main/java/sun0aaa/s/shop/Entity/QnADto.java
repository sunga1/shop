package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QnADto {
    private Long id;

    private Long memberId; //글을 작성한 회원

    private Long itemId; //어떤 상품에 대한 글인지 확인하기 위해

    private String title; //제목
    private String content; //내용

    private String answer;

    private AnswerStatus answerStatus; //답변 상태

    private UploadImage uploadImage;

    public static QnADto of(QnA qna){
        return QnADto.builder()
                .id(qna.getId())
                .memberId(qna.getMember().getId())
                .itemId(qna.getItem().getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .answer(qna.getAnswer())
                .answerStatus(qna.getAnswerStatus())
                .uploadImage(qna.getUploadImage())
                .build();
    }

}
