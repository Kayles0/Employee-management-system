package com.kayles.employee_management_system.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Groups.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Groups_ extends com.kayles.employee_management_system.entity.SoftDeletableEntity_ {

	public static final String PERSONS = "persons";
	public static final String NAME = "name";

	
	/**
	 * @see com.kayles.employee_management_system.entity.Groups#persons
	 **/
	public static volatile ListAttribute<Groups, Person> persons;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Groups#name
	 **/
	public static volatile SingularAttribute<Groups, String> name;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Groups
	 **/
	public static volatile EntityType<Groups> class_;

}

