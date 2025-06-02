package com.kayles.employee_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @NotNull
    @JsonProperty("name")
    private String name;

    @NotBlank
    @NotNull
    @JsonProperty("persons")
    private List<PersonShortDto> persons;
}