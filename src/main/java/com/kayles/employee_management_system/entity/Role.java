package com.kayles.employee_management_system.entity;

import com.kayles.employee_management_system.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@EqualsAndHashCode(callSuper=true)
public class Role extends AbstractEntity {

    @NotNull
    @NotBlank
    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.name.name()));
    }
}
