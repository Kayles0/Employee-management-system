package com.kayles.employee_management_system.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AbstractEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class AbstractEntity_ {

	public static final String ID = "id";

	
	/**
	 * @see com.kayles.employee_management_system.entity.AbstractEntity#id
	 **/
	public static volatile SingularAttribute<AbstractEntity, Long> id;
	
	/**
	 * @see com.kayles.employee_management_system.entity.AbstractEntity
	 **/
	public static volatile MappedSuperclassType<AbstractEntity> class_;

}

