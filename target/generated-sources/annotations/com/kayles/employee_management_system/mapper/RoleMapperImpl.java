package com.kayles.employee_management_system.mapper;

import com.kayles.employee_management_system.dto.RoleDto;
import com.kayles.employee_management_system.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T15:56:41+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Role.RoleBuilder<?, ?> role = Role.builder();

        role.id( dto.getId() );
        role.name( dto.getName() );

        return role.build();
    }

    @Override
    public RoleDto toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDto.RoleDtoBuilder roleDto = RoleDto.builder();

        roleDto.id( entity.getId() );
        roleDto.name( entity.getName() );

        return roleDto.build();
    }
}
