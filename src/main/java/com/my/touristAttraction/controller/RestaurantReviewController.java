package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.ReviewService;
import com.my.touristAttraction.dto.ReviewDto;
import com.my.touristAttraction.entity.Restaurant;
import com.my.touristAttraction.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RestaurantReviewController {

    private final ReviewService reviewService;

    // 식당 상세 페이지
    @GetMapping("/restaurant/{id}")
    public String restaurantDetail(@PathVariable Long id, Model model) {
        Restaurant restaurant = reviewService.getRestaurant(id);
        model.addAttribute("restaurant", restaurant);
        return "restaurantDetail";
    }

    // 리뷰 전체 조회 (AJAX)
    @GetMapping("/restaurant/{id}/review")
    @ResponseBody
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable Long id) {
        List<ReviewDto> reviews = reviewService.RestaurantgetReviews(id)
                .stream()
                .map(reviewService::toDto)
                .toList();
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 작성 (AJAX)
    @PostMapping("/restaurant/{id}/review")
    @ResponseBody
    public ResponseEntity<?> addReview(@PathVariable Long id,
                                       @RequestBody Map<String, String> body,
                                       Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String content = body.get("content");
        Review review = reviewService.RestaurantaddReview(id, principal.getName(), content);
        return ResponseEntity.ok(reviewService.toDto(review));
    }

    // 리뷰 수정 (AJAX)
    @PutMapping("/restaurant/{id}/review/{reviewId}")
    @ResponseBody
    public ResponseEntity<?> updateReview(@PathVariable Long id,
                                          @PathVariable Long reviewId,
                                          @RequestBody Map<String, String> body,
                                          Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Review updated = reviewService.updateReview(reviewId, principal.getName(), body.get("content"));
        return ResponseEntity.ok(reviewService.toDto(updated));
    }

    // 리뷰 삭제 (AJAX)
    @DeleteMapping("/restaurant/{id}/review/{reviewId}")
    @ResponseBody
    public ResponseEntity<?> deleteReview(@PathVariable Long id,
                                          @PathVariable Long reviewId,
                                          Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        reviewService.deleteReview(reviewId, principal.getName());
        return ResponseEntity.ok(Map.of("status", "deleted"));
    }
}
