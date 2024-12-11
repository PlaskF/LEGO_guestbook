package kr.ac.kopo.lego_guestbook.repository;

import kr.ac.kopo.lego_guestbook.entity.LEGO;
import kr.ac.kopo.lego_guestbook.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// // 오라클
//public interface LEGORepository extends JpaRepository<LEGO, Long> {
//
//    @Query("select m, (select mi from LEGOImage mi where mi.lego=m and ROWNUM=1), " +
//            "avg(coalesce(r.grade, 0)), count(r) from LEGO m " +
//            "left outer join Review r on r.lego=m group by m")
//    Page<Object[]> getListPage(Pageable pageable);
//
////    @Query("select m, mi from Movie m left outer join MovieImage mi on mi.movie=m where m.mno=:mno")
////    List<Object[]> getMovieWithAll(Long mno);
//
//    @Query("select m, mi ,avg(coalesce(r.grade,0)),  count(r)" +
//            " from LEGO m left outer join LEGOImage mi on mi.lego = m " +
//            " left outer join Review  r on r.lego = m "+
//            " where m.mno = :mno group by m, mi")
//    List<Object[]> getMovieWithAll(Long mno);
//}

//    마리아DB
public interface LEGORepository extends JpaRepository<LEGO, Long>, SearchBoardRepository {

    @Query("select m, " +
            "(select mi from LEGOImage mi where mi.lego = m order by mi.inum asc limit 1), " + // ROWNUM -> order by + limit
            "avg(coalesce(r.grade, 0)), count(r) " +
            "from LEGO m " +
            "left join Review r on r.lego = m " +
            "group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r) " +
            "from LEGO m " +
            "left join LEGOImage mi on mi.lego = m " +
            "left join Review r on r.lego = m " +
            "where m.mno = :mno " +
            "group by m, mi")
    List<Object[]> getLEGOWithAll(Long mno);
}
