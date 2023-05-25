package com.example.shopnoticeboard.Dto.user.Email;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class EmailVerifyRequest {
    private String email, randomCode;
}
