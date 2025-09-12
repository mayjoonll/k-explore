package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.*;
import com.my.touristAttraction.dto.PlaceDto;

import com.my.touristAttraction.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    private final AccommodationService accommodationService;
    private final LeportsService leportsService;
    private final RestaurantService restaurantService;
    private final TouristSpotService touristSpotService;
    private final ShoppingService shoppingService;

//    @GetMapping
//    public List<PlaceDto> getAllPlaces() {
//        List<PlaceDto> list = new ArrayList<>();
//        list.addAll(accommodationService.getAll().stream().map(PlaceDto::fromAccommodation).toList());
//        list.addAll(leportsService.getAll().stream().map(PlaceDto::fromLeports).toList());
//        list.addAll(restaurantService.getAll().stream().map(PlaceDto::fromRestaurant).toList());
//        list.addAll(touristSpotService.getAll().stream().map(PlaceDto::fromTouristSpot).toList());
//        list.addAll(shoppingService.getAll().stream().map(PlaceDto::fromShopping).toList());
//        return list;
//    }


    @GetMapping("")
    public List<PlaceDto> getPlaces() {
        List<PlaceDto> result = new ArrayList<>();

        List<Accommodation> accommodations = accommodationService.findAll();
        result.addAll(accommodations.stream()
                .map(PlaceDto::fromAccommodation)
                .toList());

        List<Shopping> shoppings = shoppingService.findAll();
        result.addAll(shoppings.stream()
                .map(PlaceDto::fromShopping)
                .toList());

        List<Restaurant> restaurants = restaurantService.findAll();
        result.addAll(restaurants.stream()
                .map(PlaceDto::fromRestaurant)
                .toList());


        List<Leports> leports = leportsService.findAll();
        result.addAll(leports.stream()
                .map(PlaceDto::fromLeports)
                .toList());


        List<TouristSpot> touristSpots = touristSpotService.findAll();
        result.addAll(touristSpots.stream()
                .map(PlaceDto::fromTouristSpot)
                .toList());

        return result;
    }

}
