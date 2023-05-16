package com.example.shopnoticeboard.Dto.user;

import lombok.*;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserViewResponse {
    private String email, nickname;
}
