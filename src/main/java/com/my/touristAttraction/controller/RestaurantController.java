package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.RestaurantService;
import com.my.touristAttraction.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService service;

    // ================= REST API =================
    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAllRestaurant();
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return service.saveRestaurant(restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteRestaurant(id);
    }

    // ================= 뷰 페이지 =================
    @Controller
    @RequestMapping("/restaurant/view")
    @RequiredArgsConstructor
    public static class RestaurantViewController {
        private final RestaurantService service;

        @GetMapping("/{id}")
        public String getRestaurantInfo(@PathVariable Long id, Model model) {
            Restaurant restaurant = service.getAllRestaurant().stream()
                    .filter(r -> r.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("레스토랑을 찾을 수 없습니다. id=" + id));

            model.addAttribute("restaurant", restaurant);
            return "restaurant"; // templates/restaurant.html
        }
    }
}
