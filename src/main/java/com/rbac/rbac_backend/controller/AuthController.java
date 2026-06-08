package com.rbac.rbac_backend.controller;

import com.rbac.rbac_backend.entity.User;
import com.rbac.rbac_backend.repository.UserRepository;
import com.rbac.rbac_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
public String login(@RequestBody User loginUser) {

    User user = userRepository
            .findByUsername(loginUser.getUsername())
            .orElse(null);

    if (user == null) {
        return "User Not Found";
    }

    if (!passwordEncoder.matches(
        loginUser.getPassword(),
        user.getPassword())) {

    return "Invalid Password";
}

    String roleName = user.getRoles()
            .stream()
            .findFirst()
            .get()
            .getName();

    return jwtUtil.generateToken(
            user.getUsername(),
            roleName
    );
}
}