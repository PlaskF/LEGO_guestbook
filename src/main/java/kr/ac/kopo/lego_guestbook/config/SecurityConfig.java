package kr.ac.kopo.lego_guestbook.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 비활성화 (필요에 따라 활성화 가능)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(new AntPathRequestMatcher("/sample/all")).permitAll() // 누구나 접근 가능
                        .requestMatchers(new AntPathRequestMatcher("/sample/member")).authenticated() // 로그인 사용자만 가능
                        .requestMatchers(new AntPathRequestMatcher("/sample/admin")).hasRole("ADMIN") // 관리자만 가능
                        .anyRequest().authenticated() // 그 외 요청은 로그인 필요
                )
                .formLogin(form -> form
                        .loginPage("/login") // 로그인 페이지 설정
                        .defaultSuccessUrl("/") // 로그인 성공 시 이동할 페이지
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // 로그아웃 성공 시 이동
                        .permitAll()
                );

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests((auth) -> {
//            //  /sample/all 은 아무나 다 접근할 수 있도록
//            auth.requestMatchers("/sample/all").permitAll();
//            auth.requestMatchers("/sample/member").authenticated();
//            auth.requestMatchers("/sample/admin").hasRole("ADMIN");  // 관리자만 가능
//        });
//
//        httpSecurity.formLogin();
//        httpSecurity.csrf().disable();
//        httpSecurity.logout();
//
//        return httpSecurity.build();
//    }
}
