package com.rbac.rbac_backend.controller;

import com.rbac.rbac_backend.entity.Role;
import com.rbac.rbac_backend.service.RoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin("*")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
@PreAuthorize("hasAuthority('CREATE_ROLE')")
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @GetMapping
@PreAuthorize("hasAuthority('READ_USER')")
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }
}