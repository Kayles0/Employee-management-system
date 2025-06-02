package com.kayles.employee_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "groups")
public class Group extends SoftDeletableEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "person_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<Person> persons;

}
