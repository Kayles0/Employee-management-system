package com.kayles.employee_management_system.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Group.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Group_ extends com.kayles.employee_management_system.entity.SoftDeletableEntity_ {

	public static final String PERSONS = "persons";
	public static final String NAME = "name";

	
	/**
	 * @see com.kayles.employee_management_system.entity.Group#persons
	 **/
	public static volatile ListAttribute<Group, Person> persons;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Group#name
	 **/
	public static volatile SingularAttribute<Group, String> name;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Group
	 **/
	public static volatile EntityType<Group> class_;

}

