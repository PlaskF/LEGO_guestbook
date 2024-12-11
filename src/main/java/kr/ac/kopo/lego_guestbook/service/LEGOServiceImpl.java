package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.entity.LEGO;
import kr.ac.kopo.lego_guestbook.repository.LEGORepository;
import kr.ac.kopo.lego_guestbook.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class LEGOServiceImpl implements LEGOService{

    private final LEGORepository legoRepository; //final

    @Transactional
    @Override
    public Long register(LEGODTO legoDTO) {

        Map<String, Object> entityMap = dtoToEntity(legoDTO);
        LEGO lego = (LEGO) entityMap.get("lego");

        legoRepository.save(lego);


        return lego.getMno();
    }

    @Override
    public PageResultDTO<LEGODTO, Object[]> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = legoRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });


        Function<Object[], LEGODTO> fn = (arr -> entitiesToDTO(
                (LEGO) arr[0] ,
                (Double) arr[2],
                (Long)arr[3])
        );

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public LEGODTO getLEGO(Long mno) {

        List<Object[]> result = legoRepository.getLEGOWithAll(mno);
//        if (result == null || result.isEmpty()) {
//            log.warn("No LEGO data found for mno: {}", mno);
//            return null; // or throw a custom exception like throw new NotFoundException("LEGO not found");
//        }

        LEGO lego = (LEGO) result.get(0)[0];
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDTO(lego, avg, reviewCnt);
    }
}