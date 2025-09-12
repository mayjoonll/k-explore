package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.TouristSpotService;
import com.my.touristAttraction.entity.TouristSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API
@RequestMapping("/touristSpot")
@RequiredArgsConstructor
public class TouristSpotController {

    private final TouristSpotService service;

    // ================= REST API =================
    @GetMapping
    public List<TouristSpot> getAll() {
        return service.getAllTouristSpots();
    }

    @PostMapping
    public TouristSpot create(@RequestBody TouristSpot spot) {
        return service.saveTouristSpot(spot);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTouristSpot(id);
    }

    // ================= 뷰 페이지 =================
    @Controller
    @RequestMapping("/touristSpot/view")
    @RequiredArgsConstructor
    public static class TouristSpotViewController {
        private final TouristSpotService service;

        @GetMapping("/{id}")
        public String getTouristSpotInfo(@PathVariable Long id, Model model) {
            TouristSpot spot = service.getAllTouristSpots().stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("관광지를 찾을 수 없습니다. id=" + id));

            model.addAttribute("touristSpot", spot);
            return "touristSpot"; // templates/touristSpot.html
        }
    }
}
