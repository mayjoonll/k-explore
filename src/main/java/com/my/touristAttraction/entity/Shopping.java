package com.my.touristAttraction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shopping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shopping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB용 PK
    private String title;           // 숙소명
    private String addr1;           // 주소1
    private String addr2;           // 주소2
    private String zipcode;         // 우편번호
    private String tel;             // 전화번호
    private String areacode;        // 지역코드
    private String sigungucode;     // 시군구코드
    private String cat1;            // 대분류
    private String cat2;            // 중분류
    private String cat3;            // 소분류
    private String contentid;       // 콘텐츠 ID
    private String contenttypeid;   // 콘텐츠 타입 ID
    private String firstimage;      // 이미지1
    private String firstimage2;     // 이미지2
    private Double  mapx;            // 경도
    private Double  mapy;            // 위도
    private LocalDateTime createdtime;     // 생성일시
    private LocalDateTime modifiedtime;    // 수정일시
    private String cpyrhtDivCd;     // 저작권 구분
    private String mlevel;          // 지도레벨
    private String lDongRegnCd;     // 법정동 코드
    private String lDongSignguCd;   // 법정동 시군구 코드
    private String lclsSystm1;      // 분류 시스템1
    private String lclsSystm2;      // 분류 시스템2
    private String lclsSystm3;      // 분류 시스템3
}
