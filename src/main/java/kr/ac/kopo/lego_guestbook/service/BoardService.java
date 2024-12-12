package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.BoardDTO;
import kr.ac.kopo.lego_guestbook.dto.LEGOImageDTO;
import kr.ac.kopo.lego_guestbook.dto.PageRequestDTO;
import kr.ac.kopo.lego_guestbook.dto.PageResultDTO;
import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.entity.LEGOImage;

import java.util.*;
import java.util.stream.Collectors;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void modify(BoardDTO dto);

    void remove(Long bno);

    default BoardDTO entitiesToDTO(Board board, List<LEGOImage> legoImages){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        // Null-safe 리스트 필터링 후 스트림 처리
        List<LEGOImageDTO> legoImageDTOList = legoImages.stream()
                .filter(Objects::nonNull) // legoImage가 null이 아닌 경우만 처리
                .map(legoImage -> LEGOImageDTO.builder()
                        .imgName(legoImage.getImgName())
                        .path(legoImage.getPath())
                        .uuid(legoImage.getUuid())
                        .build())
                .collect(Collectors.toList());

        boardDTO.setImageDTOList(legoImageDTOList);

        return boardDTO;

    }

    default Map<String, Object> dtoToEntity(BoardDTO boardDTO){

        Map<String, Object> entityMap = new HashMap<>();

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        entityMap.put("board", board);

        List<LEGOImageDTO> imageDTOList = boardDTO.getImageDTOList();

//        if(imageDTOList != null && !imageDTOList.isEmpty()) { //MovieImageDTO 처리
//
//            List<LEGOImage> legoImageList = imageDTOList.stream().map(legoImageDTO ->{
//
//                LEGOImage legoImage = LEGOImage.builder()
//                        .path(legoImageDTO.getPath())
//                        .imgName(legoImageDTO.getImgName())
//                        .uuid(legoImageDTO.getUuid())
//                        .board(board)
//                        .build();
//                return legoImage;
//            }).collect(Collectors.toList());
//
//            entityMap.put("imgList", legoImageList);
//        }
        if (imageDTOList != null && !imageDTOList.isEmpty()) {
            List<LEGOImage> imageList = new ArrayList<>();

            for (LEGOImageDTO imageDTO : imageDTOList) {
                LEGOImage legoImage = LEGOImage.builder()
                        .path(imageDTO.getPath())
                        .imgName(imageDTO.getImgName())
                        .board(board) // Board와 연관
                        .build();
                imageList.add(legoImage);
            } entityMap.put("imgList", imageList);
        } else {
            // imgList가 비어 있는 경우에도 안전하게 빈 리스트 생성
            entityMap.put("imgList", new ArrayList<LEGOImage>());
        }
        return entityMap;
    }
}