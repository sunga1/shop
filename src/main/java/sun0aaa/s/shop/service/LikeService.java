package sun0aaa.s.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun0aaa.s.shop.Entity.Item;
import sun0aaa.s.shop.Entity.Like;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.repository.ItemRepository;
import sun0aaa.s.shop.repository.LikeRepository;
import sun0aaa.s.shop.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    public boolean checkLike(String loginEmail, Long itemId) {
        return likeRepository.existsByMemberEmailAndItemId(loginEmail,itemId);
    }

    @Transactional
    public void createLike(String email, Long itemId){
        Item item = itemRepository.findById(itemId).get();
        Member loginMember = memberRepository.findByEmail(email).get();

        likeRepository.save(Like.builder()
                .member(loginMember)
                .item(item)
                .build()
        );
    }

    @Transactional
    public void deleteLike(String email, Long itemId){
        Item item = itemRepository.findById(itemId).get();
        Member loginMember = memberRepository.findByEmail(email).get();

        likeRepository.deleteByMemberEmailAndItemId(email,itemId);
    }

}
