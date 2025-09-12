package com.my.touristAttraction.repository;

import com.my.touristAttraction.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    // 대소문자 구분 없이 username 조회
    @Query("select f from FavoriteEntity f where lower(f.username) = lower(:username)")
    List<FavoriteEntity> findByUsernameIgnoreCase(@Param("username") String username);
}
