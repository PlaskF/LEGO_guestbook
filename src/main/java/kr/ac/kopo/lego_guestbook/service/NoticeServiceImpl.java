package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.NoticeDTO;
import kr.ac.kopo.lego_guestbook.entity.Notice;
import kr.ac.kopo.lego_guestbook.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 공지사항 목록 조회
    @Override
    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(this::convertToDTO)  // Entity -> DTO 변환
                .collect(Collectors.toList());
    }

    // 공지사항 등록
    @Override
    public void createNotice(NoticeDTO noticeDTO) {
        Notice notice = convertToEntity(noticeDTO);  // DTO -> Entity 변환
        noticeRepository.save(notice);
    }

    // 공지사항 조회 (ID로 찾기)
    @Override
    public NoticeDTO getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        return convertToDTO(notice);
    }

    // 공지사항 수정
    @Override
    public void updateNotice(Long id, NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());
        noticeRepository.save(notice);
    }

    // 공지사항 삭제
    @Override
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // 조회수 증가
    @Override
    public void incrementViews(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        notice.incrementViews();  // 조회수 증가
        noticeRepository.save(notice);
    }

    // Entity -> DTO 변환
    private NoticeDTO convertToDTO(Notice notice) {
        return new NoticeDTO(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getAuthor(),
                notice.getCreatedAt(),
                notice.getViews()
        );
    }

    // DTO -> Entity 변환
    private Notice convertToEntity(NoticeDTO noticeDTO) {
        return new Notice(
                noticeDTO.getTitle(),
                noticeDTO.getContent(),
                noticeDTO.getAuthor(),
                noticeDTO.getCreatedAt()
        );
    }
}
