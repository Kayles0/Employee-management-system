package com.kayles.employee_management_system.repository;

import com.kayles.employee_management_system.entity.Role;
import com.kayles.employee_management_system.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
