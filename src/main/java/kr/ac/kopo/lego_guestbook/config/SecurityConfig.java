package kr.ac.kopo.lego_guestbook.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests((auth) -> {
            //  /sample/all 은 아무나 다 접근할 수 있도록
            auth.requestMatchers("/sample/all").permitAll();
            auth.requestMatchers("/sample/member").hasRole("USER");
        });

        httpSecurity.formLogin();
        httpSecurity.csrf().disable();
        httpSecurity.logout();

        return httpSecurity.build();
    }
}
