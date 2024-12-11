package kr.ac.kopo.lego_guestbook.repository;

import kr.ac.kopo.lego_guestbook.entity.LEGO;
import kr.ac.kopo.lego_guestbook.entity.LEGOImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class LEGORepositoryTest {
    @Autowired
    private LEGORepository legoRepository;

    @Autowired
    private LEGOImageRepository legoImageRepository;

    @Test
    public  void insertMovies(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            LEGO lego = LEGO.builder()
                    .title("LEGO Test " + i)
                    .build();

            legoRepository.save(lego);

            int imgCount = (int)(Math.random() * 5) + 1;

            for (int j=0; j < imgCount; j++){
                LEGOImage legoImage = LEGOImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .lego(lego)
                        .imgName("test"+j+".jpg")
                        .build();
                legoImageRepository.save(legoImage);
            }
        });
    }

    @Test
    public void testListPage() {
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"mno"));
        Page<Object[]> result = legoRepository.getListPage(pageRequest);

        for (Object[] objects : result.getContent()) {
            System.out.println("♠" + Arrays.toString(objects));
        }
    }

    @Test
    public void testGetLEGOWithAll() {
        List<Object[]> result = legoRepository.getLEGOWithAll(95L);

        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }
}
