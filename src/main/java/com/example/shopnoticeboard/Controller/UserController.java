package com.example.shopnoticeboard.Controller;

import com.example.shopnoticeboard.Dto.user.*;
import com.example.shopnoticeboard.Dto.user.Email.EmailSaveRequest;
import com.example.shopnoticeboard.Dto.user.Email.EmailVerifyRequest;
import com.example.shopnoticeboard.Entity.User;
import com.example.shopnoticeboard.Repository.UserRepository;
import com.example.shopnoticeboard.Service.EmailService;
import com.example.shopnoticeboard.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    // check
    @PostMapping("/email")
    public ResponseEntity<String> check(@RequestBody EmailVerifyRequest emailVerifyRequest) throws MessagingException {
        userService.sendEmail(emailVerifyRequest.getEmail());
        return ResponseEntity.ok("이메일이 정상적으로 보내졌습니다.");
    }

    @PostMapping("/emailVerify")
    public ResponseEntity<String> verify(@RequestBody EmailVerifyRequest emailVerifyRequest){
        userService.verifyEmail(emailVerifyRequest);
        return ResponseEntity.ok("2차 인증이 완료되었습니다. 로그인이 가능합니다.");
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@RequestBody UserSignupRequest userSignupRequest, HttpServletResponse response) throws MessagingException {
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
