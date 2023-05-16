package com.example.shopnoticeboard.Dto.user;

import com.example.shopnoticeboard.Entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserUpdateRequest {
    private String nickname,email;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .build();
    }
}
