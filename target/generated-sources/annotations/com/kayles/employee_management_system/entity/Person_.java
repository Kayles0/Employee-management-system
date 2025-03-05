package com.kayles.employee_management_system.entity;

import com.kayles.employee_management_system.enums.DepartmentEnum;
import com.kayles.employee_management_system.enums.GenderEnum;
import com.kayles.employee_management_system.enums.StatusEnum;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Person.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Person_ extends com.kayles.employee_management_system.entity.SoftDeletableEntity_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String IMAGE = "image";
	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String GENDER = "gender";
	public static final String GROUP_LIST = "groupList";
	public static final String LOGIN = "login";
	public static final String DEPARTMENT = "department";
	public static final String EMAIL = "email";
	public static final String STATUS = "status";

	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#firstName
	 **/
	public static volatile SingularAttribute<Person, String> firstName;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#lastName
	 **/
	public static volatile SingularAttribute<Person, String> lastName;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#image
	 **/
	public static volatile SingularAttribute<Person, Image> image;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#password
	 **/
	public static volatile SingularAttribute<Person, String> password;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#role
	 **/
	public static volatile SingularAttribute<Person, Role> role;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#gender
	 **/
	public static volatile SingularAttribute<Person, GenderEnum> gender;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#groupList
	 **/
	public static volatile ListAttribute<Person, Groups> groupList;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#login
	 **/
	public static volatile SingularAttribute<Person, String> login;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#department
	 **/
	public static volatile SingularAttribute<Person, DepartmentEnum> department;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person
	 **/
	public static volatile EntityType<Person> class_;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#email
	 **/
	public static volatile SingularAttribute<Person, String> email;
	
	/**
	 * @see com.kayles.employee_management_system.entity.Person#status
	 **/
	public static volatile SingularAttribute<Person, StatusEnum> status;

}

