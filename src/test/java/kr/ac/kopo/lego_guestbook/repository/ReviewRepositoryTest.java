package kr.ac.kopo.lego_guestbook.repository;

import kr.ac.kopo.lego_guestbook.entity.Member;
import kr.ac.kopo.lego_guestbook.entity.LEGO;
import kr.ac.kopo.lego_guestbook.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertReview() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            // 임의의 게시번호
            Long mno = (long)(Math.random()*100) + 1;
            // 임의의 회원번호
            Long mid = (long)(Math.random()*100) + 1;

            Member member = Member.builder()
                    .mid(mid)
                    .build();

            LEGO lego = LEGO.builder()
                    .mno(mno)
                    .build();

            int grade = (int)(Math.random()*5);

            Review review = Review.builder()
                    .member(member)
                    .lego(lego)
                    .grade(grade)
                    .text("영화 리뷰 텍스트 " + i)
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void testGetMovieReviews() {
        LEGO movie = LEGO.builder()
                .mno(91L)
                .build();

        List<Review> result = reviewRepository.findByLego(movie);

        result.forEach(review -> {
            System.out.println(review.getReviewnum() + "\t");
            System.out.println(review.getGrade() + "\t");
            System.out.println(review.getText() + "\t");
            System.out.println(review.getMember().getEmail() + "\t");
            System.out.println("--------------------------------------");
        });
    }
}
