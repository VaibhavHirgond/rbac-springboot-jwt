package com.rbac.rbac_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.rbac.rbac_backend.entity.Role;
import com.rbac.rbac_backend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role role) {

        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        existing.setName(role.getName());
        existing.setPermissions(role.getPermissions());

        return roleRepository.save(existing);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}