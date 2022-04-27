package com.yj.service.member;

import com.yj.domain.user.Member;
import com.yj.domain.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class JwtUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username).orElse(null);

        if (member == null) {
            throw new UsernameNotFoundException("User not found with username " + username);
        }

        return new User(member.getId(), member.getPassword(), new ArrayList<>());
    }
}
