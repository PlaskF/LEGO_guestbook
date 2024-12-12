package kr.ac.kopo.lego_guestbook.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;

    private String title;

    private String content;

    private String writer;

    @Builder.Default
    private List<LEGOImageDTO> imageDTOList = new ArrayList<>();

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
