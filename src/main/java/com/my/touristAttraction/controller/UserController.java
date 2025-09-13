package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.JoinService;
import com.my.touristAttraction.Service.ReviewService;
import com.my.touristAttraction.Service.UserService;
import com.my.touristAttraction.dto.FavoriteDto;
import com.my.touristAttraction.dto.JoinDto;
import com.my.touristAttraction.dto.ReviewDto;
import com.my.touristAttraction.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Comparator;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JoinService joinService;
    private final ReviewService reviewService;

    public UserController(UserService userService, JoinService joinService, ReviewService reviewService) {
        this.userService = userService;
        this.joinService = joinService;
        this.reviewService = reviewService;
    }

    // ===================== 회원가입 =====================
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new JoinDto());
        return "/user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") JoinDto joinDto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/user/signup";
        }
        boolean ok = joinService.joinProcess(joinDto);
        if (!ok) {
            model.addAttribute("error", "이미 사용 중인 아이디/이메일/닉네임입니다.");
            return "/user/signup";
        }
        return "redirect:/login";
    }

    // ===================== 관리자 =====================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public String usersList(Model model) {
        List<UserDto> list = userService.findAllUsers();
        model.addAttribute("list", list);
        return "user/usersList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteByEmail")
    public String deleteByEmail(@RequestParam("email") String email) {
        userService.deleteUserByEmail(email);
        return "redirect:/user/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update")
    public String updateUsersForm(@RequestParam("email") String email, Model model) {
        UserDto users = userService.findOneUser(email);
        if (users == null) {
            model.addAttribute("error", "해당 이메일의 회원을 찾을 수 없습니다.");
            return "user/userList";
        }
        model.addAttribute("users", users);
        return "/user/usersUpdate";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String updateUsers(@ModelAttribute("users") UserDto users,
                              RedirectAttributes rttr) {
        boolean ok = userService.updateUserInfoByUsername(users.getUsername(), users);
        if (!ok) {
            rttr.addFlashAttribute("msg", "업데이트 실패: 회원을 찾을 수 없거나 입력값이 올바르지 않습니다.");
            return "redirect:/user/update?email=" + users.getEmail();
        }
        rttr.addFlashAttribute("msg", "회원 정보가 수정되었습니다.");
        return "redirect:/user/list";
    }

    // ===================== 마이페이지 =====================
    @GetMapping("/myInfo")
    public String myInfo(Model model, Authentication auth) {
        String username = auth.getName();
        UserDto user = userService.findOneByUsername(username);
        model.addAttribute("user", user);
        return "/user/myPage";
    }

    @GetMapping("/changePassword")
    public String changePasswordForm() {
        return "/user/changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(Authentication authentication,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "새 비밀번호와 확인이 일치하지 않습니다.");
            return "/user/changePassword";
        }
        String username = authentication.getName();
        boolean success = userService.changePasswordByUsername(username, oldPassword, newPassword);
        if (!success) {
            model.addAttribute("error", "기존 비밀번호가 올바르지 않습니다.");
            return "/user/changePassword";
        }
        return "redirect:/user/myInfo";
    }

    @GetMapping("/myPage")
    public String myPage(Model model, Authentication auth) {
        String username = auth.getName();
        UserDto user = userService.findOneByUsername(username);
        model.addAttribute("user", user);
        return "/user/myPage";
    }

    // ===================== 마이페이지 수정 =====================
    @PostMapping("/email/edit")
    public String updateEmail(Authentication auth,
                              @RequestParam String email,
                              RedirectAttributes rttr) {
        String username = auth.getName();
        boolean success = userService.updateUserInfoByUsername(username,
                UserDto.builder().email(email).build());
        rttr.addFlashAttribute("msg", success ? "이메일이 수정되었습니다." : "이메일 수정 실패");
        return "redirect:/user/myPage";
    }

    @PostMapping("/name/edit")
    public String updateName(Authentication auth,
                             @RequestParam String name,
                             RedirectAttributes rttr) {
        String username = auth.getName();
        boolean success = userService.updateUserInfoByUsername(username,
                UserDto.builder().name(name).build());
        rttr.addFlashAttribute("msg", success ? "이름이 수정되었습니다." : "이름 수정 실패");
        return "redirect:/user/myPage";
    }

    @PostMapping("/nickname/edit")
    public String updateNickname(Authentication auth,
                                 @RequestParam String nickname,
                                 RedirectAttributes rttr) {
        String username = auth.getName();
        boolean success = userService.updateUserInfoByUsername(username,
                UserDto.builder().nickname(nickname).build());
        rttr.addFlashAttribute("msg", success ? "닉네임이 수정되었습니다." : "닉네임 수정 실패");
        return "redirect:/user/myPage";
    }

    @PostMapping("/password/edit")
    public String updatePassword(Authentication auth,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes rttr) {
        if (!newPassword.equals(confirmPassword)) {
            rttr.addFlashAttribute("msg", "새 비밀번호와 확인이 일치하지 않습니다.");
            return "redirect:/user/myPage";
        }

        String username = auth.getName();
        boolean success = userService.changePasswordByUsername(username, currentPassword, newPassword);
        rttr.addFlashAttribute("msg", success ? "비밀번호가 변경되었습니다." : "비밀번호 변경 실패");
        return "redirect:/user/myPage";
    }

    // ===================== 회원탈퇴 =====================
    @PostMapping("/deleteMyAccount")
    public String deleteMyAccount(Authentication authentication,
                                  HttpServletRequest request,
                                  RedirectAttributes rttr) {
        if (authentication == null || authentication.getName() == null) {
            rttr.addFlashAttribute("msg", "로그인 후 이용해주세요.");
            return "redirect:/login";
        }

        String username = authentication.getName();
        boolean removed = userService.deleteByUsername(username);

        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        rttr.addFlashAttribute("msg", removed ? "회원탈퇴가 완료되었습니다."
                : "회원탈퇴 실패: 존재하지 않거나 연관 데이터 오류.");
        return "redirect:/";
    }

    // ===================== 중복 체크 =====================
    @GetMapping("/checkUsername")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.checkUsernameDuplicate(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/checkNickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        boolean exists = userService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok(exists);
    }

    // ===================== 리뷰 관련 =====================
    @GetMapping("/myReviews")
    public String myReviews(Model model, Authentication auth) {
        String username = auth.getName();

        List<ReviewDto> accommodationReviews = reviewService.getAllUserAccommodationReviews(username)
                .stream().map(reviewService::toDto).toList();
        List<ReviewDto> leportsReviews = reviewService.getAllUserLeportsReviews(username)
                .stream().map(reviewService::toDto).toList();
        List<ReviewDto> shoppingReviews = reviewService.getAllUserShoppingReviews(username)
                .stream().map(reviewService::toDto).toList();
        List<ReviewDto> restaurantReviews = reviewService.getAllUserRestaurantReviews(username)
                .stream().map(reviewService::toDto).toList();
        List<ReviewDto> touristSpotReviews = reviewService.getAllUserTouristSpotReviews(username)
                .stream().map(reviewService::toDto).toList();

        List<ReviewDto> allReviews = Stream.of(
                        accommodationReviews,
                        leportsReviews,
                        shoppingReviews,
                        restaurantReviews,
                        touristSpotReviews
                ).flatMap(List::stream)
                .sorted(Comparator.comparing(ReviewDto::getCreatedAt).reversed())
                .toList();

        model.addAttribute("reviews", allReviews);
        return "/user/myReviews";
    }

    @DeleteMapping("/myReviews/{reviewId}")
    @ResponseBody
    public ResponseEntity<?> deleteMyReview(@PathVariable Long reviewId, Authentication auth) {
        String username = auth.getName();
        reviewService.deleteReview(reviewId, username);
        return ResponseEntity.ok(Map.of("status", "deleted"));
    }

    @PutMapping("/myReviews/{reviewId}")
    @ResponseBody
    public ResponseEntity<?> updateMyReview(@PathVariable Long reviewId,
                                            @RequestBody Map<String,String> body,
                                            Authentication auth) {
        String username = auth.getName();
        ReviewDto updated = reviewService.toDto(
                reviewService.updateReview(reviewId, username, body.get("content"))
        );
        return ResponseEntity.ok(updated);
    }

    // ===================== 즐겨찾기 =====================
    @GetMapping("/favorites")
    public String myFavorites(Model model, Authentication auth) {
        String username = auth.getName();
        List<FavoriteDto> favorites = userService.getUserFavorites(username);
        model.addAttribute("favorites", favorites);
        return "/user/favorites";
    }

    @PostMapping("/favorites/{id}")
    @ResponseBody
    public ResponseEntity<?> addFavorite(
            @PathVariable Long id,
            @RequestParam String type,
            Authentication auth) {

        String username = auth.getName();
        userService.addFavorite(username, id, type);
        return ResponseEntity.ok(Map.of("status", "added"));
    }

    @DeleteMapping("/favorites/{id}")
    @ResponseBody
    public ResponseEntity<?> removeFavorite(
            @PathVariable Long id,
            @RequestParam String type,
            Authentication auth) {

        String username = auth.getName();
        userService.removeFavorite(username, id, type);
        return ResponseEntity.ok(Map.of("status", "removed"));
    }
}
