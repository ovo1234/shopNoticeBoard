package com.example.shopnoticeboard.Dto.user.Email;

import com.example.shopnoticeboard.Entity.EmailAuth;
import com.example.shopnoticeboard.Entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class EmailSaveRequest {
    private String email, randomCode;

    public EmailAuth toEntity() {
        return EmailAuth.builder()
                .email(email)
                .randomCode(randomCode)
                .build();
    }
}
