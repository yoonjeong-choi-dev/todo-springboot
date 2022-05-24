package com.yj.config.security;

import com.yj.config.jwt.JwtAuthenticationEntryPoint;
import com.yj.config.jwt.JwtAuthenticationRequestFilter;
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
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationRequestFilter jwtAuthenticationRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                .antMatchers("/localtest/**").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/member/register", "/member/list").permitAll()
                .antMatchers("/todo/all", "/todo/list").permitAll()
                .anyRequest().authenticated();

        // 인증 관련 에러 핸들러 및 필터 등록
        httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        httpSecurity.addFilterBefore(jwtAuthenticationRequestFilter, CorsFilter.class);
    }
}
