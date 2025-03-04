package com.kayles.employee_management_system.entity;

import com.kayles.employee_management_system.enums.RoleEnum;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Role_ extends com.kayles.employee_management_system.entity.AbstractEntity_ {

	public static final String NAME = "name";

	
	/**
	 * @see com.kayles.employee_management_system.entity.Role#name
	 **/
	public static volatile SingularAttribute<Role, RoleEnum> name;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Role
	 **/
	public static volatile EntityType<Role> class_;

}

