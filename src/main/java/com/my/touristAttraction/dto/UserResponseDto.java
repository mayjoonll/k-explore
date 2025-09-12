package com.my.touristAttraction.dto;

import com.my.touristAttraction.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private String username;
    private String email;
    private String nickname;
    private String name;
    private UserRole role;
    private LocalDateTime createdAt; // rename
    private LocalDateTime updatedAt; // rename

    public static UserResponseDto fromEntity(UserEntity entity) {
        return UserResponseDto.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .name(entity.getName())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
