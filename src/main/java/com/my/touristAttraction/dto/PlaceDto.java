package com.my.touristAttraction.dto;

import com.my.touristAttraction.entity.*;
import lombok.Data;

@Data
public class PlaceDto {
    private Long id;  // 반드시 id로 내려주기
    private String title;
    private String cat1;
    private String cat2;
    private String cat3;
    private double mapx;
    private double mapy;
    private String addr1;
    private String addr2;
    private String firstimage;

    public static PlaceDto fromAccommodation(Accommodation a) {
        PlaceDto dto = new PlaceDto();
        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setCat1("B02"); dto.setCat2(a.getCat2()); dto.setCat3(a.getCat3());
        dto.setMapx(a.getMapx()); dto.setMapy(a.getMapy());
        dto.setAddr1(a.getAddr1()); dto.setAddr2(a.getAddr2());
        dto.setFirstimage(a.getFirstimage());
        return dto;
    }

    public static PlaceDto fromLeports(Leports l) {
        PlaceDto dto = new PlaceDto();
        dto.setId(l.getId());
        dto.setTitle(l.getTitle());
        dto.setCat1("A03"); dto.setCat2(l.getCat2()); dto.setCat3(l.getCat3());
        dto.setMapx(l.getMapx()); dto.setMapy(l.getMapy());
        dto.setAddr1(l.getAddr1()); dto.setAddr2(l.getAddr2());
        dto.setFirstimage(l.getFirstimage());
        return dto;
    }

    public static PlaceDto fromRestaurant(Restaurant r) {
        PlaceDto dto = new PlaceDto();
        dto.setId(r.getId());
        dto.setTitle(r.getTitle());
        dto.setCat1("B02"); dto.setCat2(r.getCat2()); dto.setCat3(r.getCat3());
        dto.setMapx(r.getMapx()); dto.setMapy(r.getMapy());
        dto.setAddr1(r.getAddr1()); dto.setAddr2(r.getAddr2());
        dto.setFirstimage(r.getFirstimage());
        return dto;
    }

    public static PlaceDto fromShopping(Shopping s) {
        PlaceDto dto = new PlaceDto();
        dto.setId(s.getId());
        dto.setTitle(s.getTitle());
        dto.setCat1("B02"); dto.setCat2(s.getCat2()); dto.setCat3(s.getCat3());
        dto.setMapx(s.getMapx()); dto.setMapy(s.getMapy());
        dto.setAddr1(s.getAddr1()); dto.setAddr2(s.getAddr2());
        dto.setFirstimage(s.getFirstimage());
        return dto;
    }

    public static PlaceDto fromTouristSpot(TouristSpot t) {
        PlaceDto dto = new PlaceDto();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setCat1("B02"); dto.setCat2(t.getCat2()); dto.setCat3(t.getCat3());
        dto.setMapx(t.getMapx()); dto.setMapy(t.getMapy());
        dto.setAddr1(t.getAddr1()); dto.setAddr2(t.getAddr2());
        dto.setFirstimage(t.getFirstimage());
        return dto;
    }
}
