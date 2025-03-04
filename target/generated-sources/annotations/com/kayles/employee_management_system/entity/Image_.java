package com.kayles.employee_management_system.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Image.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Image_ extends com.kayles.employee_management_system.entity.SoftDeletableEntity_ {

	public static final String IMAGE = "image";

	
	/**
	 * @see com.kayles.employee_management_system.entity.Image#image
	 **/
	public static volatile SingularAttribute<Image, byte[]> image;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Image
	 **/
	public static volatile EntityType<Image> class_;

}

