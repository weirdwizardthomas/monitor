package com.example.monitor.configuration;

import com.example.monitor.model.user.User;
import com.example.monitor.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccessTokenFilter extends OncePerRequestFilter {

    public static final String BEARER = "Bearer ";
    private final UserRepository userRepository;
    private static final ThreadLocal<User> currentUserThreadLocal = new ThreadLocal<>();

    @Autowired
    public AccessTokenFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = request.getHeader("Authorization");

        if (accessToken != null && accessToken.startsWith(BEARER)) {
            accessToken = accessToken.substring(BEARER.length());

            User user = userRepository.findByAccessToken(accessToken);

            if (user != null) {
                currentUserThreadLocal.set(user);
            }
        }

        filterChain.doFilter(request, response);
        currentUserThreadLocal.remove();
    }

    public static User getCurrentUser() {
        return currentUserThreadLocal.get();
    }
}
