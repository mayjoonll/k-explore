package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.AccommodationService;
import com.my.touristAttraction.entity.Accommodation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccommodationApiController {
    private final AccommodationService service;

    public AccommodationApiController(AccommodationService service) {
        this.service = service;
    }

    @GetMapping("/accommodations")
    public List<Accommodation> getAll() {
        return service.getAll();
    }
}
