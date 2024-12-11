package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.ReviewDTO;
import kr.ac.kopo.lego_guestbook.entity.LEGO;
import kr.ac.kopo.lego_guestbook.entity.Review;
import kr.ac.kopo.lego_guestbook.repository.LEGORepository;
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
    private final LEGORepository legoRepository;

    @Override
    public List<ReviewDTO> getListOfLEGO(Long mno){

        LEGO lego = LEGO.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByLego(lego);

        return result.stream().map(legoReview -> entityToDto(legoReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO legoReviewDTO) {

//        Review legoReview = dtoToEntity(legoReviewDTO);
        // LEGO 데이터 확인 및 조회
        Optional<LEGO> legoOptional = legoRepository.findById(legoReviewDTO.getMno());
        if (legoOptional.isEmpty()) {
            throw new IllegalArgumentException("해당 LEGO 데이터가 존재하지 않습니다. mno=" + legoReviewDTO.getMno());
        }

        LEGO lego = legoOptional.get();

        // Review 엔티티 생성 및 LEGO 매핑
        Review legoReview = dtoToEntity(legoReviewDTO);
//        legoReview.setLego(lego); // LEGO 객체를 Review에 매핑

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