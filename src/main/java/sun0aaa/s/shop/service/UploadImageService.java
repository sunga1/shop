package sun0aaa.s.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.QnA;
import sun0aaa.s.shop.Entity.Review;
import sun0aaa.s.shop.Entity.UploadImage;
import sun0aaa.s.shop.repository.ItemRepository;
import sun0aaa.s.shop.repository.UploadImageRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadImageService {
    private final UploadImageRepository uploadImageRepository;
    private final ItemRepository itemRepository;
    private final String rootPath = System.getProperty("user.dir");
    private final String fileDir = rootPath + "/src/main/resources/static/upload-images/";

    public String getFullPath(String filename){
        return fileDir+filename;
    }
    public UploadImage saveImage(MultipartFile multipartFile, Item item) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        //원본 파일명 -> 서버에 저장된 파일명 (중복 x)
        // 파일명이 중복되지 않도록 UUID로 설정 + 확장자 유지
        String savedFilename = UUID.randomUUID() + "." + extractExt(originalFilename);

        //파일 저장
        multipartFile.transferTo(new File(getFullPath(savedFilename)));

        return uploadImageRepository.save(
                UploadImage.builder()
                        .originalFilename(originalFilename)
                        .savedFilename(savedFilename)
                        .item(item)
                        .build()
        );
    }

    public UploadImage saveReviewImage(MultipartFile multipartFile, Review review) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        //원본 파일명 -> 서버에 저장된 파일명 (중복 x)
        // 파일명이 중복되지 않도록 UUID로 설정 + 확장자 유지
        String savedFilename = UUID.randomUUID() + "." + extractExt(originalFilename);

        //파일 저장
        multipartFile.transferTo(new File(getFullPath(savedFilename)));

        return uploadImageRepository.save(
                UploadImage.builder()
                        .originalFilename(originalFilename)
                        .savedFilename(savedFilename)
                        .review(review)
                        .build()
        );
    }
    public UploadImage saveQnAImage(MultipartFile multipartFile, QnA qna) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        //원본 파일명 -> 서버에 저장된 파일명 (중복 x)
        // 파일명이 중복되지 않도록 UUID로 설정 + 확장자 유지
        String savedFilename = UUID.randomUUID() + "." + extractExt(originalFilename);

        //파일 저장
        multipartFile.transferTo(new File(getFullPath(savedFilename)));

        return uploadImageRepository.save(
                UploadImage.builder()
                        .originalFilename(originalFilename)
                        .savedFilename(savedFilename)
                        .qna(qna)
                        .build()
        );
    }
    @Transactional
    public void deleteImage(UploadImage uploadImage) throws IOException{
        uploadImageRepository.delete(uploadImage);
        Item item = uploadImage.getItem();
        item.getUploadImages().remove(uploadImage);
        Files.deleteIfExists(Paths.get(getFullPath(uploadImage.getSavedFilename())));
    }

    //확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }

    //아이디로 해당 이미지 찾기
    public UploadImage findById(Long id){
        UploadImage uploadImage = uploadImageRepository.findById(id).get();
        return uploadImage;
    }

}