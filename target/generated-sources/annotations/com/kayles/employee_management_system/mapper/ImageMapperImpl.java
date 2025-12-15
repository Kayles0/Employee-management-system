package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.ImageDto;
import com.kayles.employee_management_system.entity.Image;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-15T18:58:39+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public Image toEntity(ImageDto dto) {
        if ( dto == null ) {
            return null;
        }

        Image.ImageBuilder<?, ?> image1 = Image.builder();

        image1.id( dto.getId() );
        byte[] image = dto.getImage();
        if ( image != null ) {
            image1.image( Arrays.copyOf( image, image.length ) );
        }

        return image1.build();
    }

    @Override
    public ImageDto toDto(Image entity) {
        if ( entity == null ) {
            return null;
        }

        ImageDto.ImageDtoBuilder imageDto = ImageDto.builder();

        imageDto.id( entity.getId() );
        byte[] image = entity.getImage();
        if ( image != null ) {
            imageDto.image( Arrays.copyOf( image, image.length ) );
        }

        return imageDto.build();
    }

    @Override
    public void updateImage(Image exImage, Image newImage) {
        if ( newImage == null ) {
            return;
        }

        exImage.setIsDeleted( newImage.getIsDeleted() );
        byte[] image = newImage.getImage();
        if ( image != null ) {
            exImage.setImage( Arrays.copyOf( image, image.length ) );
        }
        else {
            exImage.setImage( null );
        }
    }
}
