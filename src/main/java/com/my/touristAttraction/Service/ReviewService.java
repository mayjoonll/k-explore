package com.my.touristAttraction.Service;

import com.my.touristAttraction.dto.ReviewDto;
import com.my.touristAttraction.entity.*;
import com.my.touristAttraction.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final AccommodationRepository accommodationRepository;
    private final LeportsRepository leportsRepository;
    private final ShoppingRepository shoppingRepository;
    private final RestaurantRepository restaurantRepository;
    private final TouristSpotRepository touristSpotRepository;

    // =================== 조회 ===================
    public Accommodation getAccommodation(Long id) {
        return accommodationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));
    }

    public Leports getLeports(Long id) {
        return leportsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leports not found"));
    }

    public Shopping getShopping(Long id) {
        return shoppingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping not found"));
    }

    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    public TouristSpot getTouristSpot(Long id) {
        return touristSpotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TouristSpot not found"));
    }

    // =================== 리뷰 작성 ===================
    @Transactional
    public Review AccommodationaddReview(Long accId, String username, String content) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Accommodation acc = getAccommodation(accId);

        Review review = Review.builder()
                .user(user)
                .accommodation(acc)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public Review LeportsaddReview(Long accId, String username, String content) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Leports acc = getLeports(accId);

        Review review = Review.builder()
                .user(user)
                .leports(acc)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public Review ShoppingaddReview(Long accId, String username, String content) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Shopping acc = getShopping(accId);

        Review review = Review.builder()
                .user(user)
                .shopping(acc)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public Review RestaurantaddReview(Long accId, String username, String content) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Restaurant acc = getRestaurant(accId);

        Review review = Review.builder()
                .user(user)
                .restaurant(acc)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public Review TouristSpotaddReview(Long accId, String username, String content) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        TouristSpot acc = getTouristSpot(accId);

        Review review = Review.builder()
                .user(user)
                .touristSpot(acc)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    // =================== 리뷰 조회 ===================
    public List<Review> AccommodationgetReviews(Long accId) {
        Accommodation acc = getAccommodation(accId);
        return reviewRepository.findByAccommodationOrderByCreatedAtDesc(acc);
    }

    public List<Review> LeportsgetReviews(Long accId) {
        Leports acc = getLeports(accId);
        return reviewRepository.findByLeportsOrderByCreatedAtDesc(acc);
    }

    public List<Review> ShoppinggetReviews(Long accId) {
        Shopping acc = getShopping(accId);
        return reviewRepository.findByShoppingOrderByCreatedAtDesc(acc);
    }

    public List<Review> RestaurantgetReviews(Long accId) {
        Restaurant acc = getRestaurant(accId);
        return reviewRepository.findByRestaurantOrderByCreatedAtDesc(acc);
    }

    public List<Review> TouristSpotgetReviews(Long accId) {
        TouristSpot acc = getTouristSpot(accId);
        return reviewRepository.findByTouristSpotOrderByCreatedAtDesc(acc);
    }

    // =================== 사용자별 리뷰 조회 ===================
    @Transactional(readOnly = true)
    public List<Review> getAllUserAccommodationReviews(String username) {
        return reviewRepository.findAll()
                .stream()
                .filter(r -> r.getUser().getUsername().equals(username) && r.getAccommodation() != null)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Review> getAllUserLeportsReviews(String username) {
        return reviewRepository.findAll()
                .stream()
                .filter(r -> r.getUser().getUsername().equals(username) && r.getLeports() != null)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Review> getAllUserShoppingReviews(String username) {
        return reviewRepository.findAll()
                .stream()
                .filter(r -> r.getUser().getUsername().equals(username) && r.getShopping() != null)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Review> getAllUserRestaurantReviews(String username) {
        return reviewRepository.findAll()
                .stream()
                .filter(r -> r.getUser().getUsername().equals(username) && r.getRestaurant() != null)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Review> getAllUserTouristSpotReviews(String username) {
        return reviewRepository.findAll()
                .stream()
                .filter(r -> r.getUser().getUsername().equals(username) && r.getTouristSpot() != null)
                .toList();
    }

    // =================== 리뷰 수정/삭제 ===================
    @Transactional
    public Review updateReview(Long reviewId, String username, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getUsername().equals(username)) {
            throw new RuntimeException("권한 없음: 본인 리뷰만 수정 가능");
        }

        review.setContent(content);
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId, String username) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getUsername().equals(username)) {
            throw new RuntimeException("권한 없음: 본인 리뷰만 삭제 가능");
        }

        reviewRepository.delete(review);
    }

    // =================== DTO 변환 ===================
    public ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getUser().getNickname(),
                review.getUser().getUsername(),
                review.getContent(),
                review.getCreatedAt()
        );
    }

}
