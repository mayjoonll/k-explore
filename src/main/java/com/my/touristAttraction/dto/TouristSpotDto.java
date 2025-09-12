package com.my.touristAttraction.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TouristSpotDto {
    private Long id; // DB용 PK

    private String title;           // 관광지명
    private String addr1;           // 주소1
    private String addr2;           // 주소2
    private String zipcode;         // 우편번호
    private String tel;             // 전화번호
    private String areacode;        // 지역코드
    private String sigungucode;     // 시군구코드
    private String cat1;            // 대분류
    private String cat2;            // 중분류
    private String cat3;            // 소분류
    private String contentid;       // 관광지 ID
    private String contenttypeid;   // 콘텐츠 타입 ID
    private String firstimage;      // 이미지1
    private String firstimage2;     // 이미지2
    private String mapx;            // 경도
    private String mapy;            // 위도
    private String createdtime;     // 생성일시
    private String modifiedtime;    // 수정일시
    private String cpyrhtDivCd;     // 저작권 구분
    private String mlevel;          // 지도레벨
    private String lDongRegnCd;     // 법정동 코드
    private String lDongSignguCd;   // 법정동 시군구 코드
    private String lclsSystm1;      // 분류 시스템1
    private String lclsSystm2;      // 분류 시스템2
    private String lclsSystm3;      // 분류 시스템3
}
