package kr.ac.kopo.lego_guestbook.repository.search;

import kr.ac.kopo.lego_guestbook.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    // 검색 관련 메서드 선언
    Page<Object[]> searchPage(String types, String keyword, Pageable pageable);
}
