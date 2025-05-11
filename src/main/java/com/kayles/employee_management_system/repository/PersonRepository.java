package com.kayles.employee_management_system.repository;

import com.kayles.employee_management_system.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByLogin(String login);

    @Query("select p from Person p WHERE p.isDeleted = false ")
    List<Person> findAllNotDeleted();

    @Query("SELECT DISTINCT p FROM Person p WHERE p.id = :id AND p.isDeleted = false ")
    Optional<Person> findPersonByIdAndIsNotDeleted(@Param("id") Long id);
}
