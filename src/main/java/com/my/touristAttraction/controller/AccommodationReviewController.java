package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.ReviewService;
import com.my.touristAttraction.dto.ReviewDto;
import com.my.touristAttraction.entity.Accommodation;
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
public class AccommodationReviewController {

    private final ReviewService reviewService;

    // 숙소 상세 페이지
    @GetMapping("/accommodation/{id}")
    public String accommodationDetail(@PathVariable Long id, Model model) {
        Accommodation acc = reviewService.getAccommodation(id);
        model.addAttribute("accommodation", acc);
        return "accommodationDetail";
    }

    // 리뷰 전체 조회 (AJAX)
    @GetMapping("/accommodation/{id}/review")
    @ResponseBody
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable Long id) {
        List<ReviewDto> reviews = reviewService.AccommodationgetReviews(id)
                .stream()
                .map(reviewService::toDto)
                .toList();
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 작성 (AJAX)
    @PostMapping("/accommodation/{id}/review")
    @ResponseBody
    public ResponseEntity<?> addReview(@PathVariable Long id,
                                       @RequestBody Map<String, String> body,
                                       Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String content = body.get("content");
        Review review = reviewService.AccommodationaddReview(id, principal.getName(), content);
        return ResponseEntity.ok(reviewService.toDto(review));
    }

    // 리뷰 수정 (AJAX)
    @PutMapping("/accommodation/{id}/review/{reviewId}")
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
    @DeleteMapping("/accommodation/{id}/review/{reviewId}")
    @ResponseBody
    public ResponseEntity<?> deleteReview(@PathVariable Long id,
                                          @PathVariable Long reviewId,
                                          Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        reviewService.deleteReview(reviewId, principal.getName());
        return ResponseEntity.ok(Map.of("status", "deleted"));
    }
}
