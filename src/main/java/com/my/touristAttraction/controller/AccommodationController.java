package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.AccommodationService;
import com.my.touristAttraction.entity.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API
@RequestMapping("/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService service;

    // ================= REST API =================
    @GetMapping
    public List<Accommodation> getAll() {
        return service.getAllAccommodation();
    }

    @PostMapping
    public Accommodation create(@RequestBody Accommodation spot) {
        return service.saveAccommodation(spot);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteAccommodation(id);
    }

    // ================= 뷰 페이지 =================
    @Controller
    @RequestMapping("/accommodation/view")
    @RequiredArgsConstructor
    public static class AccommodationViewController {
        private final AccommodationService service;

        @GetMapping("/{id}")
        public String getAccommodationInfo(@PathVariable Long id, Model model) {
            Accommodation accommodation = service.getAllAccommodation().stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("숙소를 찾을 수 없습니다. id=" + id));

            model.addAttribute("accommodation", accommodation);
            return "accommodation"; // templates/accommodation.html
        }
    }
}
