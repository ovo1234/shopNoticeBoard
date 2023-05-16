package com.example.shopnoticeboard.Controller;

import com.example.shopnoticeboard.Dto.user.*;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입
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
        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow();
        userService.login(userLoginRequest, response);

        return ResponseEntity.ok(user.getNickname()+"님 환영합니다.");
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

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("로그아웃 되었습니다");
    }

    // 탈퇴
    @DeleteMapping("/delete")
    public String delete(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        return userService.deleteUser(userDeleteRequest, request);
    }

    // 관리자 전용
    @GetMapping("/list")
    public List<User> viewBoardList() {
        return userService.viewUserList();
    }


}
