package com.kayles.employee_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Employee extends Person {
    @ManyToOne
    @JoinColumn(name = "manager")
    private Manager manager;
}
