package kr.ac.kopo.lego_guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // BaseEntity의 regDate, modDate값이 자동으로 저장
public class LegoGuestbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegoGuestbookApplication.class, args);
    }

}
