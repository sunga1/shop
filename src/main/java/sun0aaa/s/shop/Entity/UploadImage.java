package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UploadImage {

    @Id @GeneratedValue
    private Long id;

    private String originalFilename; //원본 파일명
    private String savedFilename; //서버에 저장된 파일명

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    private Review review;

    @OneToOne(fetch = FetchType.LAZY)
    private QnA qna;

}