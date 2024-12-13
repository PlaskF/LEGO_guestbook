package kr.ac.kopo.lego_guestbook.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.ac.kopo.lego_guestbook.dto.ReviewDTO;
import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.entity.Review;
import kr.ac.kopo.lego_guestbook.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReviewDTO> getListOfLEGO(Long bno){

        Board board = Board.builder().bno(bno).build();

        List<Review> result = reviewRepository.findByBoard(board);

        return result.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO boardReviewDTO) {
        // bno 필드가 null인지 확인
        Long bno = boardReviewDTO.getBno();
        if (bno == null) {
            throw new IllegalArgumentException("Board ID (bno)는 필수 값입니다.");
        }

        Review legoReview = dtoToEntity(boardReviewDTO);

        reviewRepository.save(legoReview);

        return legoReview.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO legoReviewDTO) {

        Optional<Review> result =
                reviewRepository.findById(legoReviewDTO.getReviewnum());

        if(result.isPresent()){

            Review legoReview = result.get();
            legoReview.changeGrade(legoReviewDTO.getGrade());
            legoReview.changeText(legoReviewDTO.getText());

            reviewRepository.save(legoReview);
        }

    }

    @Override
    public void remove(Long reviewnum) {

        reviewRepository.deleteById(reviewnum);

    }
}