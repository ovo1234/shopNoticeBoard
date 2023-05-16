package com.example.shopnoticeboard.Service;

import com.example.shopnoticeboard.Dto.user.UserDetailslmp;
import com.example.shopnoticeboard.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailslmp(userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."))
        ) {
        };
    }
}
