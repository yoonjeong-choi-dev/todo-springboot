package com.yj.config.security;

import com.yj.config.jwt.JwtAuthenticationEntryPoint;
import com.yj.config.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // API 서버 => cors 설정 및 csrf 토큰 비활성화
        httpSecurity.httpBasic().disable().cors().and().csrf().disable();

        // 시큐리티가 제공하는 기본 로그인/로그아웃 및 세션 비활성화
        httpSecurity.logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 접근 권한 설정 : 토큰 발급 요청은 허용
        httpSecurity.authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/member/register", "/member/list").permitAll()
                .antMatchers("/todo/all", "/todo/list").permitAll()
                .anyRequest().authenticated();

        // 인증 관련 에러 핸들러 및 필터 등록
        httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
