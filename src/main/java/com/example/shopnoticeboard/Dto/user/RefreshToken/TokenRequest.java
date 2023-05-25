package com.example.shopnoticeboard.Dto.user.RefreshToken;

import com.example.shopnoticeboard.Entity.Token;
import com.example.shopnoticeboard.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    String email, refreshToken;

    public Token toEntity() {
        return Token.builder()
                .refreshToken(refreshToken)
                .build();
    }
}
