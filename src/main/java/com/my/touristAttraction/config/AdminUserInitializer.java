package com.my.touristAttraction.config;

import com.my.touristAttraction.dto.UserRole;
import com.my.touristAttraction.entity.UserEntity;
import com.my.touristAttraction.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserInitializer {
    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                UserEntity admin = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin1234"))
                        .email("admin@")
                        .nickname("관리자")
                        .name("시스템 관리자")
                        .role(UserRole.ROLE_ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("관리자 계정 생성 완료 -> ID : admin / PW : admin1234");
            }
        };
    }
}