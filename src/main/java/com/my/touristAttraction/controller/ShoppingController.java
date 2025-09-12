package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.ShoppingService;
import com.my.touristAttraction.entity.Shopping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API
@RequestMapping("/shopping")
@RequiredArgsConstructor
public class ShoppingController {

    private final ShoppingService service;

    // ================= REST API =================
    @GetMapping
    public List<Shopping> getAll() {
        return service.getAllShopping();
    }

    @PostMapping
    public Shopping create(@RequestBody Shopping spot) {
        return service.saveShopping(spot);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteShopping(id);
    }

    // ================= 뷰 페이지 =================
    @Controller
    @RequestMapping("/shopping/view")
    @RequiredArgsConstructor
    public static class ShoppingViewController {
        private final ShoppingService service;

        @GetMapping("/{id}")
        public String getShoppingInfo(@PathVariable Long id, Model model) {
            Shopping shopping = service.getAllShopping().stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("쇼핑 정보를 찾을 수 없습니다. id=" + id));

            model.addAttribute("shopping", shopping);
            return "shopping"; // templates/shopping.html
        }
    }
}
