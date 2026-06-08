package com.rbac.rbac_backend.repository;

import com.rbac.rbac_backend.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}