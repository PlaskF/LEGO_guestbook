package kr.ac.kopo.lego_guestbook.repository;

import jakarta.transaction.Transactional;
import kr.ac.kopo.lego_guestbook.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;


    @Test
    public void insertMember() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("r"+i+"@kopo.ac.kr")
                    .password("1234")
                    .name("reviewer"+i)
                    .build();

            memberRepository.save(member);
        });
    }

//    @Commit
//    @Transactional
//    @Test
//    public void testDeleteMember() {
//        Long mid = 1L;
//        Member member = Member.builder()
//                .mid(mid)
//                .build();
//
//        reviewRepository.deleteByMember(member);
//        memberRepository.deleteById(mid);
//    }
}
