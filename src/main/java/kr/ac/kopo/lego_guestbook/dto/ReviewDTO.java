package kr.ac.kopo.lego_guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    //review num
    private Long reviewnum;

    //lego mno
    private Long bno;

    //Membmer id
    private Long mid;
    //Member nickname
    private String name;
    //Member email
    private String email;

    private int grade;

    private String text;

    private LocalDateTime regDate, modDate;
}
