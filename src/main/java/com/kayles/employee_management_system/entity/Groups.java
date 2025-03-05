package com.kayles.employee_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Groups extends SoftDeletableEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "person_group",
            joinColumns = @JoinColumn(name = "group_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "person_login", referencedColumnName = "login")
    )
    private List<Person> persons;
}
