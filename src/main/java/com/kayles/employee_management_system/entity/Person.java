package com.kayles.employee_management_system.entity;

import com.kayles.employee_management_system.enums.DepartmentEnum;
import com.kayles.employee_management_system.enums.GenderEnum;
import com.kayles.employee_management_system.enums.RoleEnum;
import com.kayles.employee_management_system.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class Person extends SoftDeletableEntity{
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "department")
    @Enumerated(EnumType.STRING)
    private DepartmentEnum department;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "groups",
        joinColumns = @JoinColumn(name = "person_login", referencedColumnName = "login"),
        inverseJoinColumns = @JoinColumn(name = "group_name", referencedColumnName = "name"))
    private List<Group> groupList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image", referencedColumnName = "id")
    private Image image;
}
