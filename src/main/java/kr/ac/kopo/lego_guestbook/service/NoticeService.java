package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {

    // 공지사항 목록 조회
    List<NoticeDTO> getAllNotices();

    // 공지사항 등록
    void createNotice(NoticeDTO noticeDTO);

    // 공지사항 조회 (ID로 찾기)
    NoticeDTO getNoticeById(Long id);

    // 공지사항 수정
    void updateNotice(Long id, NoticeDTO noticeDTO);

    // 공지사항 삭제
    void deleteNotice(Long id);

    // 조회수 증가
    void incrementViews(Long id);
}
