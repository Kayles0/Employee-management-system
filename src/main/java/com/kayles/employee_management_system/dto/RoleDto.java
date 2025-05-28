package com.kayles.employee_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kayles.employee_management_system.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @NotNull
    @NotBlank
    @JsonProperty("id")
    private Long id;

    @NotNull
    @NotBlank
    @JsonProperty("name")
    private RoleEnum name;
}
