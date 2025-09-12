package com.my.touristAttraction.controller;

import com.my.touristAttraction.config.KakaoProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class MapController {
    private final KakaoProperties kakaoProperties;

    public MapController(KakaoProperties kakaoProperties) {
        this.kakaoProperties = kakaoProperties;
    }

    @GetMapping("/map")
    public String mapPage(Model model) {
        model.addAttribute("383bb530f578320cc36f3da78b6183ba", kakaoProperties.getKey());
        return "map";
    }

}
