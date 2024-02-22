package sun0aaa.s.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.ItemCategory;
import sun0aaa.s.shop.Entity.Like;
import sun0aaa.s.shop.Entity.UploadImage;
import sun0aaa.s.shop.dto.ItemDto;
import sun0aaa.s.shop.dto.ItemJoinRequest;
import sun0aaa.s.shop.repository.ItemRepository;
import sun0aaa.s.shop.repository.LikeRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UploadImageService uploadImageService;
    private final LikeRepository likeRepository;
    public BindingResult saveValid(ItemJoinRequest req, BindingResult bindingResult){
        if(req.getName().isEmpty()){
            bindingResult.addError(new FieldError("req","name","상품명이 비어있습니다."));
        }else if(itemRepository.existsByName(req.getName())){
            bindingResult.addError(new FieldError("req","name","상품명이 중복됩니다."));
        }
        if(req.getPrice()==null){
            bindingResult.addError(new FieldError("req","price","가격은 필수입력사항 입니다."));
        }else if(req.getPrice()<=0){
            bindingResult.addError(new FieldError("req","price","가격은 0원 이상이어야 합니다."));
        }
        if(req.getDelivery_price()==null){
            bindingResult.addError(new FieldError("req","delivery_price","배송비는 필수입력사항 입니다."));
        }else if(req.getDelivery_price()<=0){
            bindingResult.addError(new FieldError("req","delivery_price","배송비는 0원 이상이어야 합니다."));
        }if(req.getStock()==null) {
            bindingResult.addError(new FieldError("req", "size", "수량은 0개 이상이어야 합니다."));
        }else if(req.getStock()<=0) {
            bindingResult.addError(new FieldError("req", "size", "수량은 0개 이상이어야 합니다."));
        }
        if(req.getUploadImages()==null) {
            bindingResult.addError(new FieldError("req", "uploadImages", "상품 이미지는 1개 이상이어야 합니다."));
        }else if(req.getUploadImages().isEmpty()) {
            bindingResult.addError(new FieldError("req", "uploadImages", "상품 이미지는 1개 이상이어야 합니다."));
        }

        return bindingResult;
    }

    @Transactional
    public Item updateItemImages(List<MultipartFile> multipartFiles,Long itemId) throws IOException{
        Item item = itemRepository.findById(itemId).get();
        for (MultipartFile image: multipartFiles) {
            UploadImage uploadImage = this.uploadImageService.saveImage(image, item);
            item.getUploadImages().add(uploadImage);
        }
        System.out.println("success");
        return item;
    }

    @Transactional
    public Item saveItem(ItemJoinRequest req) throws IOException {
        Item saveItem = itemRepository.save(req.toEntity());
        // 업로드된 이미지들을 저장
        for (MultipartFile file : req.getUploadImages()) {
            UploadImage uploadImage = this.uploadImageService.saveImage(file, saveItem);
            saveItem.getUploadImages().add(uploadImage);
        }
        return saveItem;
    }

    public Item findItem(Long itemId){
        return itemRepository.findById(itemId).get();
    }
    public ItemDto findById(Long itemId){
        Optional<Item> findItem = itemRepository.findById(itemId);

        //id에 해당하는 상품 없으면 null return
        if(findItem.isEmpty()){
            return null;
        }
        return ItemDto.of(findItem.get());
    }

    public Page<Item> findItems(PageRequest pageRequest){
        Page<Item> allItems = itemRepository.findAll(pageRequest);
        return allItems;
    }

    public Page<Item> findAllByItemName(String name,PageRequest pageRequest){
        if(name!=null) {
            return itemRepository.findAllByNameContains(name, pageRequest);
        } else{
            return itemRepository.findAll(pageRequest);
        }
    }
    public Page<Item> findAllByItemCategory(ItemCategory itemCategory,PageRequest pageRequest){
        return itemRepository.findAllByItemCategory(itemCategory,pageRequest);
    }
    public Page<Item> findAllWishlist(Authentication auth,PageRequest pageRequest){
        Page<Like> likes = likeRepository.findAllByMemberEmail(pageRequest,auth.getName());
        List<Item> likeItems = new ArrayList<>();
        for(Like like:likes){
            likeItems.add(like.getItem());
        }
        return new PageImpl<>(likeItems, pageRequest, likes.getTotalElements());
    }

    @Transactional
    public Item itemEdit(Long itemId,ItemDto itemDto) {
        System.out.println(itemId);
        Item item = itemRepository.findById(itemId).get();

        item.edit(itemDto.getPrice(),itemDto.getItemCategory(),itemDto.getStock(),itemDto.getItemTip(),itemDto.getSizeTip());
        return item;
    }
}
