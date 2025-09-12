package com.my.touristAttraction.Service;

import com.my.touristAttraction.dto.FavoriteDto;
import com.my.touristAttraction.dto.UserDto;
import com.my.touristAttraction.entity.*;
import com.my.touristAttraction.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final PasswordEncoder passwordEncoder;

    private final AccommodationRepository accommodationRepository;
    private final LeportsRepository leportsRepository;
    private final ShoppingRepository shoppingRepository;
    private final RestaurantRepository restaurantRepository;
    private final TouristSpotRepository touristSpotRepository;

    // ========================= 회원 관련 =========================
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    public UserDto findOneUser(String email) {
        return userRepository.findByEmail(email)
                .map(UserDto::fromEntity)
                .orElse(null);
    }

    public UserDto findOneByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserDto::fromEntity)
                .orElse(null);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public boolean deleteByUsername(String username) {
        int affected = userRepository.deleteByUsernameHard(username);
        return affected > 0;
    }

    public boolean changePasswordByUsername(String username, String oldPassword, String newPassword) {
        UserEntity entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(oldPassword, entity.getPassword())) {
            return false;
        }

        entity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(entity);
        return true;
    }

    public boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public boolean updateUserInfo(UserDto users) {
        UserEntity entity = userRepository.findByEmail(users.getEmail())
                .orElse(null);
        if (entity == null) return false;

        if (users.getName() != null && !users.getName().isBlank()) entity.setName(users.getName());
        if (users.getNickname() != null && !users.getNickname().isBlank()) entity.setNickname(users.getNickname());
        if (users.getPassword() != null && !users.getPassword().isBlank()) entity.setPassword(passwordEncoder.encode(users.getPassword()));

        return true;
    }

    public void updateMyInfo(String username, String name, String nickname) {
        UserEntity entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        entity.setName(name);
        entity.setNickname(nickname);
        userRepository.save(entity);
    }

    // ========================= 즐겨찾기 관련 =========================
    public List<FavoriteDto> getUserFavorites(String username) {
        List<FavoriteEntity> favorites = favoriteRepository.findByUsernameIgnoreCase(username);
        return favorites.stream().map(fav -> FavoriteDto.builder()
                .id(fav.getTargetId())
                .type(fav.getTargetType())
                .title(fav.getTitle() != null && !fav.getTitle().isBlank() ? fav.getTitle() : "제목 없음")
                .firstImage(fav.getFirstImage() != null && !fav.getFirstImage().isBlank() ? fav.getFirstImage() : "/images/default.png")
                .addr1(fav.getAddr1() != null ? fav.getAddr1() : "")
                .addr2(fav.getAddr2() != null ? fav.getAddr2() : "")
                .build()
        ).collect(Collectors.toList());
    }

    /** 즐겨찾기 추가: DB 조회 후 값 가져오기 (이미지 포함) */
    public void addFavorite(String username, Long targetId, String targetType) {
        String title = "제목 없음";
        String addr1 = "";
        String addr2 = "";
        String firstImage = "/images/default.png";

        switch (targetType.toLowerCase()) {
            case "accommodation":
                Accommodation a = accommodationRepository.findById(targetId).orElse(null);
                if (a != null) {
                    title = a.getTitle();
                    addr1 = a.getAddr1();
                    addr2 = a.getAddr2();
                    firstImage = a.getFirstimage();
                }
                break;
            case "leports":
                Leports l = leportsRepository.findById(targetId).orElse(null);
                if (l != null) {
                    title = l.getTitle();
                    addr1 = l.getAddr1();
                    addr2 = l.getAddr2();
                    firstImage = l.getFirstimage();
                }
                break;
            case "shopping":
                Shopping s = shoppingRepository.findById(targetId).orElse(null);
                if (s != null) {
                    title = s.getTitle();
                    addr1 = s.getAddr1();
                    addr2 = s.getAddr2();
                    firstImage = s.getFirstimage();
                }
                break;
            case "restaurant":
                Restaurant r = restaurantRepository.findById(targetId).orElse(null);
                if (r != null) {
                    title = r.getTitle();
                    addr1 = r.getAddr1();
                    addr2 = r.getAddr2();
                    firstImage = r.getFirstimage();
                }
                break;
            case "touristspot":
                TouristSpot t = touristSpotRepository.findById(targetId).orElse(null);
                if (t != null) {
                    title = t.getTitle();
                    addr1 = t.getAddr1();
                    addr2 = t.getAddr2();
                    firstImage = t.getFirstimage();
                }
                break;
        }

        FavoriteEntity favorite = FavoriteEntity.builder()
                .username(username)
                .targetId(targetId)
                .targetType(targetType)
                .title(title)
                .addr1(addr1)
                .addr2(addr2)
                .firstImage(firstImage)
                .build();
        favoriteRepository.save(favorite);
    }

    public void removeFavorite(String username, Long targetId, String targetType) {
        favoriteRepository.findByUsernameIgnoreCase(username).stream()
                .filter(fav -> fav.getTargetId().equals(targetId) && fav.getTargetType().equals(targetType))
                .findFirst()
                .ifPresent(favoriteRepository::delete);
    }
}
