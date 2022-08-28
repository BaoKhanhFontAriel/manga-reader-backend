package com.mangapunch.mangareaderbackend.repositories;

import com.mangapunch.mangareaderbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
