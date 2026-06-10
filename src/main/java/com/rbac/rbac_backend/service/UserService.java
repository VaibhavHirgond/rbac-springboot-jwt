package com.rbac.rbac_backend.service;

import com.rbac.rbac_backend.dto.CreateUserRequest;
import com.rbac.rbac_backend.entity.Role;
import com.rbac.rbac_backend.entity.User;
import com.rbac.rbac_backend.repository.RoleRepository;
import com.rbac.rbac_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE USER
    public User createUser(CreateUserRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        Role role = roleRepository
                .findById(request.getRoleId())
                .orElseThrow(() ->
                        new RuntimeException("Role not found"));

        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }

    // OLD SAVE METHOD
    public User save(User user) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return userRepository.save(user);
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // DELETE USER
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // UPDATE USER
    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        existingUser.setUsername(
                updatedUser.getUsername()
        );

        if (updatedUser.getPassword() != null
                && !updatedUser.getPassword().isEmpty()) {

            existingUser.setPassword(
                    passwordEncoder.encode(
                            updatedUser.getPassword()
                    )
            );
        }

        if (updatedUser.getRoles() != null) {
            existingUser.setRoles(
                    updatedUser.getRoles()
            );
        }

        return userRepository.save(existingUser);
    }
}