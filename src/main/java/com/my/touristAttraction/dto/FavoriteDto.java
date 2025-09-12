package com.my.touristAttraction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDto {
    private Long id;          // PK (각 DTO id)
    private String title;     // 이름 (title 컬럼)
    private String type;      // 카테고리: accommodation, leports, shopping, restaurant, touristSpot
    private String firstImage; // 썸네일 이미지
    private String addr1;     // 주소1
    private String addr2;     // 주소2 (선택)
}
