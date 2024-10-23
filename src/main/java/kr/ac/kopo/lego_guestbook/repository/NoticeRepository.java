package kr.ac.kopo.lego_guestbook.repository;

import kr.ac.kopo.lego_guestbook.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
