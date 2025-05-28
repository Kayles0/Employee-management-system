package com.kayles.employee_management_system.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SoftDeletableEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class SoftDeletableEntity_ extends com.kayles.employee_management_system.entity.AbstractEntity_ {

	public static final String IS_DELETED = "isDeleted";

	
	/**
	 * @see com.kayles.employee_management_system.entity.SoftDeletableEntity#isDeleted
	 **/
	public static volatile SingularAttribute<SoftDeletableEntity, Boolean> isDeleted;
	
	/**
	 * @see com.kayles.employee_management_system.entity.SoftDeletableEntity
	 **/
	public static volatile MappedSuperclassType<SoftDeletableEntity> class_;

}

