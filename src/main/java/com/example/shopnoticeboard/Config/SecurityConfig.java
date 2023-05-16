package com.example.shopnoticeboard.Config;

import com.example.shopnoticeboard.JWT.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.httpBasic().disable() // HTTP Basic 인증을 비활성화
                .csrf().disable()// 세션을 사용하지 않고 JWT 토큰을 활용하여 진행, csrf토큰검사를 비활성화
                .cors().and() // CORS(Cross-Origin Resource Sharing) 설정을 활성화
                .authorizeRequests() // 인증절차에 대한 설정을 진행
                .antMatchers(HttpMethod.POST, "/users/signup/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/check").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                //.antMatchers(HttpMethod.POST, "/users/mail").permitAll()
                // 접근 허용된 사용자만 접근 가능
                .antMatchers(HttpMethod.GET,"/users/mypage").hasAnyAuthority("USER", "SHOPOWNER")
                .antMatchers(HttpMethod.PUT,"/users/mypage/update").hasAnyAuthority("USER","SHOPOWNER")
                // 나머지 요청에 대해서는 권한 제한 없이 호출 가능하도록 설정
                .anyRequest().permitAll()
                .and()
                .logout() // 로그아웃 설정
                .logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 주소
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, BasicAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

    }
}
