package com.kayles.employee_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("image")
    private byte[] image;
}
