package com.example.shopnoticeboard.Dto.user;

import com.example.shopnoticeboard.Entity.User;
import com.example.shopnoticeboard.enums.UserRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class UserSignupRequest {
    private String email, password, nickname;
    private UserRole userRole;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .userRole(userRole)
                .nickname(nickname)
                .build();
    }
}
