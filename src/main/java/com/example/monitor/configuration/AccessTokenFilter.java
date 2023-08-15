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

// 03555ee7-94ab-4aa0-bb6d-13594159ffeb
@Component
public class AccessTokenFilter extends OncePerRequestFilter {

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

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7); // Remove "Bearer "

            // Look up the user by access token
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
