package com.example.shopnoticeboard.JWT;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println(path);

        if (path.contains("/users/login") || path.contains("/users/signup") || path.contains("/users/mail") || path.contains("/users/check") || path.contains("/users.check2")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveAccessToken(request);
        System.out.println("jwtF token: " + token);

        if(token == null){
            String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
            if (jwtTokenProvider.validateToken(refreshToken)) {
//                token = jwtTokenProvider.reissueAccessToken(refreshToken);
//                jwtTokenProvider.setHeaderAccessToken(response, token);
//                this.setAuthentication(token);
                System.out.println("null");
            }
        } else if(token != null){
            if(jwtTokenProvider.validateToken(token)){
                System.out.println("jwtF validate token : " + token);
                this.setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }


    // SecurityContext에 Authentication 저장
    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        System.out.println("권한 확인 : " + authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
