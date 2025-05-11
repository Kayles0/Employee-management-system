package com.kayles.employee_management_system.repository;

import com.kayles.employee_management_system.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select p from Image p where p.isDeleted = false ")
    Optional<Image> findByIdIsNotDeleted(Long id);
}
