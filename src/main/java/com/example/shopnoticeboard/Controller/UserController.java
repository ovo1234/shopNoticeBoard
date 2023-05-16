package com.example.shopnoticeboard.Controller;

import com.example.shopnoticeboard.Dto.user.UserLoginRequest;
import com.example.shopnoticeboard.Dto.user.UserSignupRequest;
import com.example.shopnoticeboard.Dto.user.UserUpdateRequest;
import com.example.shopnoticeboard.Dto.user.UserViewResponse;
import com.example.shopnoticeboard.Entity.User;
import com.example.shopnoticeboard.JWT.JwtTokenProvider;
import com.example.shopnoticeboard.Repository.UserRepository;
import com.example.shopnoticeboard.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // test 권한
    @GetMapping("/check")
    public String test(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse  response){
        String token = jwtTokenProvider.resolveAccessToken(request);
        String email = jwtTokenProvider.getUserEmail(token);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.equals("ROLE_USER")){
            return authentication + "권한 확인";
        }

        return authentication.toString() ;
    }

    // 회원가입 - user
    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@RequestBody UserSignupRequest userSignupRequest, HttpServletResponse response) {
        userService.signup(userSignupRequest, response);
        return ResponseEntity.ok("회원가입이 성공했습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response, HttpServletRequest request) {
        if(!userService.login(userLoginRequest, response)){
            return ResponseEntity.badRequest().body("로그인이 실패하였습니다. 다시 시도하세요.");
        }
        User user = userRepository.findByEmail(userLoginRequest.getEmail());
        userService.login(userLoginRequest, response);

        return ResponseEntity.ok(user.getNickname()+"님 환영합니다." + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
    }

    // 마이페이지
    @GetMapping("/mypage")
    public UserViewResponse viewUser(HttpServletRequest request) {
        return userService.viewUser(request);
    }

    // 마이페이지 수정
    @PutMapping("/mypage/update")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok("내 정보 업데이트 완료.");
    }

}
