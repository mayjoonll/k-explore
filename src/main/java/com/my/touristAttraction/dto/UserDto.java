package com.my.touristAttraction.dto;

import com.my.touristAttraction.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto fromEntity(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getNickname(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserEntity toEntity(UserDto dto) {
        return UserEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}