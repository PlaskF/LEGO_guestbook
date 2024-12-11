package kr.ac.kopo.lego_guestbook.controller;

import kr.ac.kopo.lego_guestbook.dto.ReviewDTO;
import kr.ac.kopo.lego_guestbook.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
        log.info("--------------list---------------");
        log.info("MNO: " + mno);

        List<ReviewDTO> reviewDTOList = reviewService.getListOfLEGO(mno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO legoReviewDTO){
        log.info("--------------add MovieReview---------------");
        log.info("reviewDTO: " + legoReviewDTO);

        Long reviewnum = reviewService.register(legoReviewDTO);

        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum,
                                             @RequestBody ReviewDTO legoReviewDTO){
        log.info("---------------modify MovieReview--------------" + reviewnum);
        log.info("reviewDTO: " + legoReviewDTO);

        reviewService.modify(legoReviewDTO);

        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview( @PathVariable Long reviewnum){
        log.info("---------------modify removeReview--------------");
        log.info("reviewnum: " + reviewnum);

        reviewService.remove(reviewnum);

        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

}