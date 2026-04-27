package com.klef.sdp.security;

import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.klef.sdp.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService service;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        List<String> publicPaths = List.of(
                "/auth",
                "/auth/",
                "/swagger-ui",
                "/v3/api-docs",
                "/swagger-ui.html",
                "/adminapi",
                "/artistapi",
                "/visitorapi");

        boolean isPublic = publicPaths.stream().anyMatch(path::contains);

        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            sendErrorResponse(response, 401, "Authorization header missing or invalid");
            return;
        }

        String token = header.substring(7).trim();

        try {
            String username = jwtUtil.extractUsername(token);

            if (username == null) {
                sendErrorResponse(response, 401, "Invalid token");
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = service.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    sendErrorResponse(response, 401, "Token expired or invalid");
                    return;
                }
            }
        } catch (Exception e) {
            sendErrorResponse(response, 401, "Invalid token: " + e.getMessage());
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = "{\"error\":\"Unauthorized\",\"message\":\"" + message + "\"}";
        response.getWriter().write(jsonResponse);
    }
}