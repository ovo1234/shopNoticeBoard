package com.example.shopnoticeboard.Service;

import com.example.shopnoticeboard.Dto.user.UserLoginRequest;
import com.example.shopnoticeboard.Dto.user.UserSignupRequest;
import com.example.shopnoticeboard.Dto.user.UserUpdateRequest;
import com.example.shopnoticeboard.Dto.user.UserViewResponse;
import com.example.shopnoticeboard.Entity.User;
import com.example.shopnoticeboard.Error.ErrorCode;
import com.example.shopnoticeboard.Error.ErrorException;
import com.example.shopnoticeboard.JWT.JwtTokenProvider;
import com.example.shopnoticeboard.Repository.UserRepository;
import com.example.shopnoticeboard.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import static com.example.shopnoticeboard.Error.ErrorCode.ACCESS_DENIED_EXCEPTION;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public void signup(UserSignupRequest userSignupRequest, HttpServletResponse response) {
        if (userRepository.existsByEmail(userSignupRequest.getEmail())) {
            throw new ErrorException("401", ACCESS_DENIED_EXCEPTION);
        }
        userSignupRequest.setPassword(passwordEncoder.encode(userSignupRequest.getPassword()));
        userRepository.save(userSignupRequest.toEntity());
        this.setJwtTokenInHeader(userSignupRequest.getEmail(), response);
    }

    // 로그인
    public boolean login(UserLoginRequest userLoginRequest, HttpServletResponse response){
        User user = userRepository.findByEmail(userLoginRequest.getEmail());

        if(!userRepository.existsByEmail(userLoginRequest.getEmail())){ // 이메일이 존재하지 않으면
            return false;
        }
        if(!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())){ // 비밀번호 틀리면
            return false;
        }
        this.setJwtTokenInHeader(userLoginRequest.getEmail(), response);

        // 로그인 성공 시
        return true;
    }

    // 토큰 헤더에 저장
    public void setJwtTokenInHeader(String email, HttpServletResponse response) {
        UserRole userRole = userRepository.findByEmail(email).getUserRole();

        String accessToken = jwtTokenProvider.createAccessToken(email, userRole);
        String refreshToken = jwtTokenProvider.createRefreshToken(email, userRole);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        //redisService.setValues(refreshToken, email);
    }

    // 마이페이지
    public UserViewResponse viewUser(HttpServletRequest request) {
        User user = findUserByToken(request);
        UserViewResponse userViewResponse = UserViewResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
        System.out.println(user.getEmail());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getName());

        return userViewResponse;
    }

    // 마이페이지 수정
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByEmail(userUpdateRequest.getEmail());
        user.update(userUpdateRequest);
        userRepository.save(user);
    }

    // 토큰에서 정보 가져오기
    public User findUserByToken(HttpServletRequest request) {
        String email = jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveAccessToken(request));
        User user = userRepository.findByEmail(email);
        return user;
    }

}
