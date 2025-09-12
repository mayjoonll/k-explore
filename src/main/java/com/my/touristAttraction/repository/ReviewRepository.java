package com.my.touristAttraction.repository;

import com.my.touristAttraction.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAccommodationOrderByCreatedAtDesc(Accommodation accommodation);
    List<Review> findByLeportsOrderByCreatedAtDesc(Leports leports);
    List<Review> findByShoppingOrderByCreatedAtDesc(Shopping shopping);
    List<Review> findByRestaurantOrderByCreatedAtDesc(Restaurant restaurant);
    List<Review> findByTouristSpotOrderByCreatedAtDesc(TouristSpot touristSpot);
}
