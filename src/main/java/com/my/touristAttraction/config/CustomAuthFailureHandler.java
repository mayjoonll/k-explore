package com.my.touristAttraction.config;

import com.my.touristAttraction.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {
    private final UserRepository userRepository;

    public CustomAuthFailureHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        boolean exists = username != null && userRepository.existsByUsername(username);

        String msg = exists
                ? "아이디 또는 비밀번호가 올바르지 않습니다. 다시 확인해주세요."
                : "해당 아이디를 가진 회원이 존재하지 않습니다. 회원가입 후 다시 진행해주세요.";

        request.setAttribute("loginErrorMsg", msg);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}