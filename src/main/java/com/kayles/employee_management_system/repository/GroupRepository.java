package com.kayles.employee_management_system.repository;

import com.kayles.employee_management_system.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select distinct g from Group g left join fetch g.persons")
    List<Group> findAllWithPersons();

    Optional<Group> findByName(String name);
}
