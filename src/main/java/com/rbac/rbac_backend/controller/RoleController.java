package com.rbac.rbac_backend.controller;

import com.rbac.rbac_backend.entity.Role;
import com.rbac.rbac_backend.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin("*")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // CREATE ROLE
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    // READ ROLES
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }

    // UPDATE ROLE
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public Role updateRole(
            @PathVariable Long id,
            @RequestBody Role role) {

        return roleService.updateRole(id, role);
    }

    // DELETE ROLE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}