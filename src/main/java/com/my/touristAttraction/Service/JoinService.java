package com.my.touristAttraction.Service;

import com.my.touristAttraction.dto.JoinDto;
import com.my.touristAttraction.dto.UserRole;
import com.my.touristAttraction.entity.UserEntity;
import com.my.touristAttraction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean joinProcess(JoinDto joinDto) {

        if (userRepository.existsByUsername(joinDto.getUsername())
                || userRepository.existsByEmail(joinDto.getEmail())
                || userRepository.existsByNickname(joinDto.getNickname())) {
            return false;
        }

        UserEntity newUser = UserEntity.builder()
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .email(joinDto.getEmail())
                .nickname(joinDto.getNickname())
                .name(joinDto.getName())
                .role(joinDto.getRole() == null ? UserRole.ROLE_USER : joinDto.getRole())
                .build();

        userRepository.save(newUser);
        return true;
    }
}