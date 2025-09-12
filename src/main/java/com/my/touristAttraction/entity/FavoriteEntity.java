package com.my.touristAttraction.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;    // 즐겨찾기한 사용자

    private Long targetId;      // 즐겨찾기 대상 PK (숙소, 관광지 등)
    private String targetType;  // 카테고리: accommodation, leports, restaurant, shopping, touristSpot
    private String title;       // 이름
    private String addr1;       // 주소1
    private String addr2;       // 주소2
    private String firstImage;  // 대표 이미지
}
