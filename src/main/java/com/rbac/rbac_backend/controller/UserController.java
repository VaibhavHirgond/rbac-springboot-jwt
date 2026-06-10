package com.rbac.rbac_backend.controller;

import com.rbac.rbac_backend.entity.User;
import com.rbac.rbac_backend.service.UserService;
import jakarta.validation.Valid;
import com.rbac.rbac_backend.dto.CreateUserRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
@PreAuthorize("hasAuthority('CREATE_USER')")
public User createUser(
        @RequestBody CreateUserRequest request) {

    return userService.createUser(request);
}

    @GetMapping
    @PreAuthorize("hasAuthority('READ_USER')")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user) {

        return userService.updateUser(id, user);
    }
}