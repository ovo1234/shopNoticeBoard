package com.example.shopnoticeboard.Entity;

import com.example.shopnoticeboard.Dto.user.UserUpdateRequest;
import com.example.shopnoticeboard.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class Token {
    @Id
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String refreshToken;

}
