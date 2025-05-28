package com.kayles.employee_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kayles.employee_management_system.enums.GenderEnum;
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
public class PersonShortDto {
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
    @JsonProperty("imageId")
    private Long imageId;

    @NotNull
    @JsonProperty("is_deleted")
    private Boolean isDeleted;
}
