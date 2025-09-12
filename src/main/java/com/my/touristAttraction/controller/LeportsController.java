package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.LeportsService;
import com.my.touristAttraction.entity.Leports;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API
@RequestMapping("/leports")
@RequiredArgsConstructor
public class LeportsController {

    private final LeportsService service;

    // ================= REST API =================
    @GetMapping
    public List<Leports> getAll() {
        return service.getAllLeports();
    }

    @PostMapping
    public Leports create(@RequestBody Leports spot) {
        return service.saveLeports(spot);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteLeports(id);
    }

    // ================= 뷰 페이지 =================
    @Controller
    @RequestMapping("/leports/view")
    @RequiredArgsConstructor
    public static class LeportsViewController {
        private final LeportsService service;

        @GetMapping("/{id}")
        public String getLeportsInfo(@PathVariable Long id, Model model) {
            Leports leports = service.getAllLeports().stream()
                    .filter(l -> l.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("레포츠를 찾을 수 없습니다. id=" + id));

            model.addAttribute("leports", leports);
            return "leports"; // templates/leports.html
        }
    }
}
