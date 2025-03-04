package com.kayles.employee_management_system.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Manager.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Manager_ extends com.kayles.employee_management_system.entity.Person_ {

	public static final String EMPLOYEES = "employees";

	
	/**
	 * @see com.kayles.employee_management_system.entity.Manager#employees
	 **/
	public static volatile ListAttribute<Manager, Employee> employees;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Manager
	 **/
	public static volatile EntityType<Manager> class_;

}

