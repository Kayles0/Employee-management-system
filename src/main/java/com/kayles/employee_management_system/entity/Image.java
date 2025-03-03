package com.kayles.employee_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image extends SoftDeletableEntity{
    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;
}
