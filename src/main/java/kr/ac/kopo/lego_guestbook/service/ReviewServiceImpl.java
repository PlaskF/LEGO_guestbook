package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.ReviewDTO;
import kr.ac.kopo.lego_guestbook.entity.LEGO;
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


    @Override
    public List<ReviewDTO> getListOfMovie(Long mno){

        LEGO lego = LEGO.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByLego(lego);

        return result.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO movieReviewDTO) {

        Review movieReview = dtoToEntity(movieReviewDTO);

        reviewRepository.save(movieReview);

        return movieReview.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO movieReviewDTO) {

        Optional<Review> result =
                reviewRepository.findById(movieReviewDTO.getReviewnum());

        if(result.isPresent()){

            Review movieReview = result.get();
            movieReview.changeGrade(movieReviewDTO.getGrade());
            movieReview.changeText(movieReviewDTO.getText());

            reviewRepository.save(movieReview);
        }

    }

    @Override
    public void remove(Long reviewnum) {

        reviewRepository.deleteById(reviewnum);

    }
}