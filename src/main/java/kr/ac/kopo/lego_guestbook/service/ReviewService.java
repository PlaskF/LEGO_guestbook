package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.ReviewDTO;
import kr.ac.kopo.lego_guestbook.entity.Member;
import kr.ac.kopo.lego_guestbook.entity.LEGO;
import kr.ac.kopo.lego_guestbook.entity.Review;

import java.util.List;

public interface ReviewService {

    //영화의 모든 영화리뷰를 가져온다.
    List<ReviewDTO> getListOfLEGO(Long mno);

    //영화 리뷰를 추가
    Long register(ReviewDTO legoReviewDTO);

    //특정한 영화리뷰 수정
    void modify(ReviewDTO legoReviewDTO);

    //영화 리뷰 삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO legoReviewDTO){

        Review legoReview = Review.builder()
                .reviewnum(legoReviewDTO.getReviewnum())
                .lego(LEGO.builder().mno(legoReviewDTO.getMno()).build())
                .member(Member.builder().mid(legoReviewDTO.getMid()).build())
                .grade(legoReviewDTO.getGrade())
                .text(legoReviewDTO.getText())
                .build();

        return legoReview;
    }

    default ReviewDTO entityToDto(Review legoReview){

        ReviewDTO legoReviewDTO = ReviewDTO.builder()
                .reviewnum(legoReview.getReviewnum())
                .mno(legoReview.getLego().getMno())
                .mid(legoReview.getMember().getMid())
                .name(legoReview.getMember().getName())
                .email(legoReview.getMember().getEmail())
                .grade(legoReview.getGrade())
                .text(legoReview.getText())
                .regDate(legoReview.getRegDate())
                .modDate(legoReview.getModDate())
                .build();

        return legoReviewDTO;
    }
}