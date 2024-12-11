package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.LEGODTO;
import kr.ac.kopo.lego_guestbook.dto.PageRequestDTO;
import kr.ac.kopo.lego_guestbook.dto.PageResultDTO;
import kr.ac.kopo.lego_guestbook.entity.LEGO;

import java.util.HashMap;
import java.util.Map;

public interface LEGOService {

    Long register(LEGODTO legoDTO);

    PageResultDTO<LEGODTO, Object[]> getList(PageRequestDTO requestDTO); //목록 처리

    LEGODTO getLEGO(Long mno);

    default LEGODTO entitiesToDTO(LEGO lego, Double avg, Long reviewCnt){
        LEGODTO legoDTO = LEGODTO.builder()
                .mno(lego.getMno())
                .title(lego.getTitle())
                .regDate(lego.getRegDate())
                .modDate(lego.getModDate())
                .build();



        legoDTO.setAvg(avg);
        legoDTO.setReviewCnt(reviewCnt.intValue());



        return legoDTO;

    }

    default Map<String, Object> dtoToEntity(LEGODTO legoDTO){

        Map<String, Object> entityMap = new HashMap<>();

        LEGO lego = LEGO.builder()
                .mno(legoDTO.getMno())
                .title(legoDTO.getTitle())
                .build();

        entityMap.put("lego", lego);

        return entityMap;
    }

}