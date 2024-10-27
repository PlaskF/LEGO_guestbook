package kr.ac.kopo.lego_guestbook.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList;
    private int totalPage;
    private int page;
    private int size;
    private int start;
    private int end;
    private boolean prev;
    private boolean next;
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getNumber(), result.getSize());
    }

    private void makePageList(int page, int size) {
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        start = tempEnd - 9;
        end = totalPage > tempEnd ? tempEnd : totalPage;

        if (start < 1) {
            start = 1;
        }

        prev = start > 1;
        next = totalPage > end;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
