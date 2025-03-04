package com.kayles.employee_management_system.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Employee.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Employee_ extends com.kayles.employee_management_system.entity.Person_ {

	public static final String MANAGER = "manager";

	
	/**
	 * @see com.kayles.employee_management_system.entity.Employee#manager
	 **/
	public static volatile SingularAttribute<Employee, Manager> manager;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Employee
	 **/
	public static volatile EntityType<Employee> class_;

}

