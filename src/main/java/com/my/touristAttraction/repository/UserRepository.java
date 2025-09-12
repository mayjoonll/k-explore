package com.my.touristAttraction.repository;

import com.my.touristAttraction.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from UserEntity u where u.username = :username")
    int deleteByUsernameHard(@Param("username") String username);

    void deleteByEmail(String email);


    long deleteByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByUsernameOrEmail(String username, String email);

}