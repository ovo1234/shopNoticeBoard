package com.example.shopnoticeboard.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    public String getEmail(String refreshToken){
        String email = "";

        return email;
    }
}
