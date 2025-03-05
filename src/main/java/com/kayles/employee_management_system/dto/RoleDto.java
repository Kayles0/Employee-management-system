package com.kayles.employee_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kayles.employee_management_system.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private RoleEnum name;


}
