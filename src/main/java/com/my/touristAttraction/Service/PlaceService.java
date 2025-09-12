package com.my.touristAttraction.Service;

import com.my.touristAttraction.entity.Accommodation;
import com.my.touristAttraction.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final AccommodationService accommodationService;
    private final LeportsService leportsService;
    private final TouristSpotService touristSpotService;
    private final ShoppingService shoppingService;
    private final RestaurantService restaurantService;


    private final AccommodationRepository accommodationRepository;

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public List<Map<String, Object>> getAllPlaces() {
        List<Map<String, Object>> allPlaces = new ArrayList<>();

        // 숙박
        accommodationService.getAll().forEach(a -> allPlaces.add(convertToMap(a.getTitle(), a.getAddr1(), a.getAddr2(),
                a.getCat1(), a.getCat2(), a.getCat3(), a.getMapx(), a.getMapy(), a.getFirstimage())));

        // 레저
        leportsService.getAll().forEach(l -> allPlaces.add(convertToMap(l.getTitle(), l.getAddr1(), l.getAddr2(),
                l.getCat1(), l.getCat2(), l.getCat3(), l.getMapx(), l.getMapy(), l.getFirstimage())));

        // 관광지
        touristSpotService.getAll().forEach(t -> allPlaces.add(convertToMap(t.getTitle(), t.getAddr1(), t.getAddr2(),
                t.getCat1(), t.getCat2(), t.getCat3(), t.getMapx(), t.getMapy(), t.getFirstimage())));

        // 쇼핑
        shoppingService.getAll().forEach(s -> allPlaces.add(convertToMap(s.getTitle(), s.getAddr1(), s.getAddr2(),
                s.getCat1(), s.getCat2(), s.getCat3(), s.getMapx(), s.getMapy(), s.getFirstimage())));

        // 음식점
        restaurantService.getAll().forEach(r -> allPlaces.add(convertToMap(r.getTitle(), r.getAddr1(), r.getAddr2(),
                r.getCat1(), r.getCat2(), r.getCat3(), r.getMapx(), r.getMapy(), r.getFirstimage())));

        return allPlaces;
    }

    private Map<String, Object> convertToMap(String title, String addr1, String addr2,
                                             String cat1, String cat2, String cat3,
                                             double mapx, double mapy, String firstimage) {
        return Map.of(
                "title", title,
                "addr1", addr1,
                "addr2", addr2,
                "cat1", cat1,
                "cat2", cat2,
                "cat3", cat3,
                "mapx", mapx,
                "mapy", mapy,
                "firstimage", firstimage
        );
    }
}
