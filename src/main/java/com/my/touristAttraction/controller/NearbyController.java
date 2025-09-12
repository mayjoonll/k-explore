package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.AccommodationService;
import com.my.touristAttraction.Service.LeportsService;
import com.my.touristAttraction.Service.ShoppingService;
import com.my.touristAttraction.Service.RestaurantService;
import com.my.touristAttraction.Service.TouristSpotService;
import com.my.touristAttraction.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NearbyController {

    private final AccommodationService accommodationService;
    private final LeportsService leportsService;
    private final ShoppingService shoppingService;
    private final RestaurantService restaurantService;
    private final TouristSpotService touristSpotService;

    @GetMapping("/accommodation/{id}/nearby")
    public Map<String, Object> AccommodationGetNearby(@PathVariable Long id) {
        Accommodation acc = accommodationService.getAccommodationById(id);
        Double lat = acc.getMapy();
        Double lng = acc.getMapx();

        Map<String, Object> result = new HashMap<>();
        result.put("accommodation", accommodationService.getNearby(lat, lng, 10.0)); // 주변 숙소
        result.put("leports", leportsService.getNearby(lat, lng, 10.0));
        result.put("shopping", shoppingService.getNearby(lat, lng, 10.0));
        result.put("restaurant", restaurantService.getNearby(lat, lng, 10.0));
        result.put("touristSpot", touristSpotService.getNearby(lat, lng, 10.0));

        return result;
    }

    @GetMapping("/leports/{id}/nearby")
    public Map<String, Object> LeportsGetNearby(@PathVariable Long id) {
        Leports acc = leportsService.getLeportsById(id);
        Double lat = acc.getMapy();
        Double lng = acc.getMapx();

        Map<String, Object> result = new HashMap<>();
        result.put("accommodation", accommodationService.getNearby(lat, lng, 10.0)); // 주변 숙소
        result.put("leports", leportsService.getNearby(lat, lng, 10.0));
        result.put("shopping", shoppingService.getNearby(lat, lng, 10.0));
        result.put("restaurant", restaurantService.getNearby(lat, lng, 10.0));
        result.put("touristSpot", touristSpotService.getNearby(lat, lng, 10.0));

        return result;
    }

    @GetMapping("/restaurant/{id}/nearby")
    public Map<String, Object> RestaurantGetNearby(@PathVariable Long id) {
        Restaurant acc = restaurantService.getRestaurantById(id);
        Double lat = acc.getMapy();
        Double lng = acc.getMapx();

        Map<String, Object> result = new HashMap<>();
        result.put("accommodation", accommodationService.getNearby(lat, lng, 10.0)); // 주변 숙소
        result.put("leports", leportsService.getNearby(lat, lng, 10.0));
        result.put("shopping", shoppingService.getNearby(lat, lng, 10.0));
        result.put("restaurant", restaurantService.getNearby(lat, lng, 10.0));
        result.put("touristSpot", touristSpotService.getNearby(lat, lng, 10.0));

        return result;
    }

    @GetMapping("/shopping/{id}/nearby")
    public Map<String, Object> ShoppingGetNearby(@PathVariable Long id) {
        Shopping acc = shoppingService.getShoppingById(id);
        Double lat = acc.getMapy();
        Double lng = acc.getMapx();

        Map<String, Object> result = new HashMap<>();
        result.put("accommodation", accommodationService.getNearby(lat, lng, 10.0)); // 주변 숙소
        result.put("leports", leportsService.getNearby(lat, lng, 10.0));
        result.put("shopping", shoppingService.getNearby(lat, lng, 10.0));
        result.put("restaurant", restaurantService.getNearby(lat, lng, 10.0));
        result.put("touristSpot", touristSpotService.getNearby(lat, lng, 10.0));

        return result;
    }

    @GetMapping("/touristSpot/{id}/nearby")
    public Map<String, Object> TouristSpotGetNearby(@PathVariable Long id) {
        TouristSpot acc = touristSpotService.getTouristSpotById(id);
        Double lat = acc.getMapy();
        Double lng = acc.getMapx();

        Map<String, Object> result = new HashMap<>();
        result.put("accommodation", accommodationService.getNearby(lat, lng, 10.0)); // 주변 숙소
        result.put("leports", leportsService.getNearby(lat, lng, 10.0));
        result.put("shopping", shoppingService.getNearby(lat, lng, 10.0));
        result.put("restaurant", restaurantService.getNearby(lat, lng, 10.0));
        result.put("touristSpot", touristSpotService.getNearby(lat, lng, 10.0));

        return result;
    }
}
