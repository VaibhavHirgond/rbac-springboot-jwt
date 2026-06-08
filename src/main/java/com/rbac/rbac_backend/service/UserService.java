package com.rbac.rbac_backend.service;

import com.rbac.rbac_backend.entity.User;
import com.rbac.rbac_backend.repository.RoleRepository;
import com.rbac.rbac_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User save(User user) {

    user.setPassword(
        passwordEncoder.encode(user.getPassword())
    );

    return userRepository.save(user);
}

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser) {

    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    existingUser.setUsername(updatedUser.getUsername());

    if(updatedUser.getPassword() != null &&
       !updatedUser.getPassword().isEmpty()) {

        existingUser.setPassword(
                passwordEncoder.encode(updatedUser.getPassword())
        );
    }

    if(updatedUser.getRoles() != null) {
        existingUser.setRoles(updatedUser.getRoles());
    }

    return userRepository.save(existingUser);
}
}