package com.rbac.rbac_backend.security;

import com.rbac.rbac_backend.entity.Role;
import com.rbac.rbac_backend.entity.User;
import com.rbac.rbac_backend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            String token =
                    authHeader.substring(7);

            try {

                String username =
                        jwtUtil.extractUsername(token);

                User user =
                        userRepository
                                .findByUsername(username)
                                .orElse(null);

                if (user != null) {
                    System.out.println("User = " + user.getUsername());

user.getRoles().forEach(role -> {

    System.out.println("Role = " + role.getName());

    role.getPermissions().forEach(permission ->

            System.out.println(
                    "Permission = "
                            + permission.getName()
            )
    );
});

                    List<SimpleGrantedAuthority> authorities =
        user.getRoles()
                .stream()
                .flatMap(role -> {

                    List<SimpleGrantedAuthority> perms =
                            role.getPermissions()
                                    .stream()
                                    .map(permission ->
                                            new SimpleGrantedAuthority(
                                                    permission.getName()
                                            )
                                    )
                                    .collect(Collectors.toList());

                    perms.add(
                            new SimpleGrantedAuthority(
                                    "ROLE_" + role.getName()
                            )
                    );

                    return perms.stream();
                })
                .collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    authorities
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(auth);
                }

            } catch (Exception e) {

    e.printStackTrace();

    response.setStatus(
        HttpServletResponse.SC_UNAUTHORIZED
    );

    response.getWriter().write("Invalid Token");

    return;
}
        }

        filterChain.doFilter(request, response);
    }
}