package com.example.shopnoticeboard.Entity;

import com.example.shopnoticeboard.Dto.user.UserUpdateRequest;
import com.example.shopnoticeboard.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    @Type(type = "true_false")
    private boolean emailConfirmed;

    public void update(UserUpdateRequest userUpdateRequest) {
        this.nickname = userUpdateRequest.getNickname();
    }

}
