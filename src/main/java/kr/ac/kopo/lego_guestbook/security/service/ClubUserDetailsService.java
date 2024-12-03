package kr.ac.kopo.lego_guestbook.security.service;


import kr.ac.kopo.lego_guestbook.entity.Member;
import kr.ac.kopo.lego_guestbook.repository.MemberRepository;
import kr.ac.kopo.lego_guestbook.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("♣ ClubUserDetailsService: " + username);
        Optional<Member> result = memberRepository.findByEmail(username, false);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Check Email or social");
        }

        Member clubMember = result.get();

        log.info(clubMember);

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
        );

        clubAuthMemberDTO.setName(clubMember.getName());

        return clubAuthMemberDTO;
    }
}
