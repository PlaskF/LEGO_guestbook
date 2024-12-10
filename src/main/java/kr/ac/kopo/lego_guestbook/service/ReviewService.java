package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.ReviewDTO;
import kr.ac.kopo.lego_guestbook.entity.Member;
import kr.ac.kopo.lego_guestbook.entity.LEGO;
import kr.ac.kopo.lego_guestbook.entity.Review;

import java.util.List;

public interface ReviewService {

    //영화의 모든 영화리뷰를 가져온다.
    List<ReviewDTO> getListOfMovie(Long mno);

    //영화 리뷰를 추가
    Long register(ReviewDTO movieReviewDTO);

    //특정한 영화리뷰 수정
    void modify(ReviewDTO movieReviewDTO);

    //영화 리뷰 삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO movieReviewDTO){

        Review movieReview = Review.builder()
                .reviewnum(movieReviewDTO.getReviewnum())
                .lego(LEGO.builder().mno(movieReviewDTO.getMno()).build())
                .member(Member.builder().mid(movieReviewDTO.getMid()).build())
                .grade(movieReviewDTO.getGrade())
                .text(movieReviewDTO.getText())
                .build();

        return movieReview;
    }

    default ReviewDTO entityToDto(Review movieReview){

        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getLego().getMno())
                .mid(movieReview.getMember().getMid())
                .name(movieReview.getMember().getName())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();

        return movieReviewDTO;
    }
}