package kr.ac.kopo.lego_guestbook.repository;

import kr.ac.kopo.lego_guestbook.entity.Member;
import kr.ac.kopo.lego_guestbook.entity.LEGO;
import kr.ac.kopo.lego_guestbook.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByLego(LEGO lego);

    @Modifying
    @Query("delete from Review rw where rw.member=:member")
    void deleteByMember(Member member);
}
