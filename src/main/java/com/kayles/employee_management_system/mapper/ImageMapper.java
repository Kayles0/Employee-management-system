package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toEntity(ImageDto dto);

    ImageDto toDto(Image entity);

    @Mapping(target = "id", ignore = true)
    void updateImage(@MappingTarget Image exImage, Image newImage);
}
