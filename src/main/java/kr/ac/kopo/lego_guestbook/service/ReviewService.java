package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.ReviewDTO;
import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.entity.Member;
import kr.ac.kopo.lego_guestbook.entity.Review;

import java.util.List;

public interface ReviewService {

    //제품의 모든 리뷰를 가져온다.
    List<ReviewDTO> getListOfLEGO(Long mno);

    //제품 리뷰를 추가
    Long register(ReviewDTO legoReviewDTO);

    //특정한 영화리뷰 수정
    void modify(ReviewDTO legoReviewDTO);

    //영화 리뷰 삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO legoReviewDTO){

        if (legoReviewDTO.getBno() == null) {
            throw new IllegalArgumentException("Board ID (bno)는 null일 수 없습니다.");
        }

        return Review.builder()
                .reviewnum(legoReviewDTO.getReviewnum())
                .board(Board.builder().bno(legoReviewDTO.getBno()).build())
                .member(Member.builder().mid(legoReviewDTO.getMid()).build())
                .grade(legoReviewDTO.getGrade())
                .text(legoReviewDTO.getText())
                .build();
    }

    default ReviewDTO entityToDto(Review legoReview){

        return ReviewDTO.builder()
                .reviewnum(legoReview.getReviewnum())
                .bno(legoReview.getBoard().getBno())
                .mid(legoReview.getMember().getMid())
                .name(legoReview.getMember().getName())
                .email(legoReview.getMember().getEmail())
                .grade(legoReview.getGrade())
                .text(legoReview.getText())
                .regDate(legoReview.getRegDate())
                .modDate(legoReview.getModDate())
                .build();
    }
}