package com.rbac.rbac_backend.controller;

import com.rbac.rbac_backend.entity.User;
import com.rbac.rbac_backend.repository.UserRepository;
import com.rbac.rbac_backend.security.JwtUtil;
import com.rbac.rbac_backend.entity.Role;
import com.rbac.rbac_backend.repository.RoleRepository;
import java.util.Set;
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
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
public String login(@RequestBody User loginUser) {

    User user = userRepository
            .findByUsernameOrEmail(
                    loginUser.getUsername(),
                    loginUser.getUsername()
            )
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

    @PostMapping("/signup")
public String signup(@RequestBody User user) {

    if (userRepository
            .findByUsername(user.getUsername())
            .isPresent()) {

        return "Username Already Exists";
    }

    if (userRepository
            .findByEmail(user.getEmail())
            .isPresent()) {

        return "Email Already Exists";
    }

    user.setPassword(
            passwordEncoder.encode(
                    user.getPassword()
            )
    );

    Role userRole = roleRepository
            .findById(2L)
            .orElseThrow(() ->
                    new RuntimeException("USER role not found"));

    user.setRoles(Set.of(userRole));

    userRepository.save(user);

    return "User Created Successfully";
}
}