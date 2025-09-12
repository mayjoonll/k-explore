package com.my.touristAttraction.repository;


import com.my.touristAttraction.entity.Leports;
import com.my.touristAttraction.entity.TouristSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
    @Query(value = "SELECT * FROM leports l " +
            "WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(l.mapy)) " +
            "* cos(radians(l.mapx) - radians(:lng)) + sin(radians(:lat)) * sin(radians(l.mapy)))) <= :distance",
            nativeQuery = true)
    List<Leports> findNearby(@Param("lat") Double lat,
                             @Param("lng") Double lng,
                             @Param("distance") Double distance);
}