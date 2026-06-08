package com.rbac.rbac_backend.controller;

import com.rbac.rbac_backend.entity.Permission;
import com.rbac.rbac_backend.service.PermissionService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@CrossOrigin("*")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
@PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    public Permission createPermission(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }

    @GetMapping
@PreAuthorize("hasAuthority('READ_USER')")
    public List<Permission> getPermissions() {
        return permissionService.getAllPermissions();
    }
}