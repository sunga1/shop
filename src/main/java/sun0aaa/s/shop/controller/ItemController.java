package sun0aaa.s.shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun0aaa.s.shop.Entity.ItemCategory;
import sun0aaa.s.shop.Entity.UploadImage;
import sun0aaa.s.shop.dto.ItemDto;
import sun0aaa.s.shop.dto.ItemJoinRequest;
import sun0aaa.s.shop.dto.ItemSearchRequest;
import sun0aaa.s.shop.dto.OrderDto;
import sun0aaa.s.shop.service.ItemService;
import sun0aaa.s.shop.service.LikeService;
import sun0aaa.s.shop.service.UploadImageService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("keyword")
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final UploadImageService uploadImageService;
    private final LikeService likeService;
    @GetMapping(value={"","/"})
    public String itemListPage(Model model,
                                @RequestParam(required = false,defaultValue = "1") int page,
                                @RequestParam(name = "keyword", required = false) String keyword){

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        System.out.println(keyword);
        model.addAttribute("items",itemService.findAllByItemName(keyword,pageRequest));
        return "items/list";
    }
    @GetMapping("/{category}")
    public String itemListPage(@PathVariable String category, Model model,
                                @RequestParam(required = false,defaultValue = "1") int page
                                ){
        ItemCategory itemCategory = ItemCategory.of(category);
        if(itemCategory==null){
            model.addAttribute("message","카테고리가 존재하지 않습니다.");
            model.addAttribute("nextUrl","/");
            return "printMessage";
        }

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        model.addAttribute("items",itemService.findAllByItemCategory(itemCategory,pageRequest));
        model.addAttribute("category",category);
        return "items/list";
    }

    @GetMapping("/{itemId}/detail")
    public String itemDetailPage(@PathVariable Long itemId, Model model,Authentication auth){
        if(auth!=null){
            model.addAttribute("likeCheck",likeService.checkLike(auth.getName(),itemId));
            model.addAttribute("loginUserLoginId",auth.getName());
        }
        ItemDto itemDto = itemService.findById(itemId);
        System.out.println(itemDto.getUploadImages().size());
        //id에 해당하는 게시글이 없거나 카테고리가 일치하지 않는경우
        if(itemDto==null){
            model.addAttribute("message","해당 게시글이 존재하지 않습니다.");
            model.addAttribute("nextUrl","/items");
            return "printMessage";
        }
        model.addAttribute("itemDto",itemDto);
        return "items/detail";
    }

    @GetMapping("/{itemId}/edit")
    public String itemEditPage(@PathVariable Long itemId, Model model){
        ItemDto itemDto = itemService.findById(itemId);
        model.addAttribute("itemDto",itemDto);
        return "items/edit";
    }

    @PostMapping("/deleteImage/{itemId}/{delImageId}")
    public String deleteImage(@PathVariable Long itemId,@PathVariable Long delImageId, Model model) throws IOException{
        UploadImage delImage = uploadImageService.findById(delImageId);
        uploadImageService.deleteImage(delImage);
        model.addAttribute("message","해당 이미지가 삭제되었습니다.");
        model.addAttribute("nextUrl","/items/"+itemId+"/edit");
        return "printMessage";
    }
    @PostMapping("/uploadImages/{itemId}")
    public String updateImages(@PathVariable Long itemId, @RequestParam("uploadImages") MultipartFile[] files,Model model) throws IOException{
        List<MultipartFile> fileList = Arrays.asList(files);
        itemService.updateItemImages(fileList,itemId);
        ItemDto itemDto = itemService.findById(itemId);
        model.addAttribute("itemDto",itemDto);
        return "items/edit";
    }

    @PostMapping("/{itemId}/edit")
    public String itemEdit(@PathVariable Long itemId,@Valid @ModelAttribute ItemDto itemDto, Model model){
        itemService.itemEdit(itemId,itemDto);
        model.addAttribute("message","상품이 수정되었습니다!");
        model.addAttribute("nextUrl","/items/"+itemId+"/detail");
        return "printMessage";
    }

    @GetMapping("/create")
    public String itemCreatePage(Model model){
        model.addAttribute("itemJoinRequest",new ItemJoinRequest());
        return "items/create";
    }

    @PostMapping("/create")
    public String itemCreate(@Valid @ModelAttribute ItemJoinRequest req, BindingResult bindingResult,Model model) throws IOException {
        if(itemService.saveValid(req,bindingResult).hasErrors()){
            return "items/create";
        }
        itemService.saveItem(req);
        model.addAttribute("message","상품이 등록되었습니다!");
        model.addAttribute("nextUrl","/items");
        return "printMessage";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+uploadImageService.getFullPath(filename));
    }

}
