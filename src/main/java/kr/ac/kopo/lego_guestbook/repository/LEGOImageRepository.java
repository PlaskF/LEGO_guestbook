package kr.ac.kopo.lego_guestbook.repository;

import kr.ac.kopo.lego_guestbook.entity.LEGOImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LEGOImageRepository extends JpaRepository<LEGOImage, Long> {
    void deleteAllByBoard_Bno(Long bno);
}
