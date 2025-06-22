package minu.coffee.config.security;

import minu.coffee.memberInfo.repository.MemberInfoRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(MemberInfoRepo memberInfoRepo) {
        return memberId -> memberInfoRepo.findByMemberId(memberId)
                .map(member -> User.builder()
                        .username(member.getMemberId())
                        .password(member.getMemberPassword())
                        .roles("USER") // 필요시 권한 추가
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
