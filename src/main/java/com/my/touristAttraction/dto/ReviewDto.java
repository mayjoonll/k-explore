package com.my.touristAttraction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String nickname;   // user.nickname
    private String username;   // 권한 확인용
    private String content;
    private LocalDateTime createdAt;
}
