package com.kayles.employee_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kayles.employee_management_system.entity.Group;
import com.kayles.employee_management_system.enums.DepartmentEnum;
import com.kayles.employee_management_system.enums.GenderEnum;
import com.kayles.employee_management_system.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @NotNull
    @JsonProperty("login")
    private String login;

    @NotNull
    @JsonProperty("gender")
    private GenderEnum gender;

    @NotNull
    @NotBlank
    @JsonProperty("firstName")
    private String firstName;

    @NotNull
    @NotBlank
    @JsonProperty("lastName")
    private String lastName;

    @NotNull
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotNull
    @NotBlank
    @JsonProperty("role")
    private RoleDto role;

    @NotNull
    @NotBlank
    @JsonProperty("status")
    private StatusEnum status;

    @NotNull
    @NotBlank
    @JsonProperty("department")
    private DepartmentEnum department;

    @NotNull
    @JsonProperty("imageId")
    private Long imageId;

    @NotNull
    @JsonProperty("groups")
    private List<GroupShortDto> groups;

    @NotNull
    @JsonProperty("is_deleted")
    private Boolean isDeleted;
}
