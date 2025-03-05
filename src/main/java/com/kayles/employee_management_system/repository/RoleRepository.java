package com.kayles.employee_management_system.repository;

import com.kayles.employee_management_system.entity.Role;
import com.kayles.employee_management_system.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnum name);
}
