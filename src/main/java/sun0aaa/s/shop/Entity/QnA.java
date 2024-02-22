package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class QnA extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //글을 작성한 회원

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item; //어떤 상품에 대한 글인지 확인하기 위해

    private String title; //제목
    private String content; //내용

    private String answer;

    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus; //답변 상태

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "qna", orphanRemoval = true)
    private UploadImage uploadImage;

    public void saveMemberItem(Member loginMember, Item item) {
        this.member=loginMember;
        this.item=item;
        loginMember.getQnAs().add(this);
    }
    public QnA setUploadImage(UploadImage uploadImage) {
        this.uploadImage=uploadImage;
        return this;
    }

    public QnA createAnswer(String answer) {
        this.answer=answer;
        this.answerStatus=AnswerStatus.COMPLETE;
        return this;
    }
}
