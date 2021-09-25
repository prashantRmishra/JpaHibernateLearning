package com.prashant.databasedemo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;





@Entity
@NamedQuery(name = "find_all_persons",query = "select p from Person p")
@Table(name  ="person") // no need as the table name match
public class Person {

	@Column(name="id") // no need as the name of the column match
	@Id
	@GeneratedValue // for automatic generation of the id 
	private int id;
	private String name;
	private String location;
	private Date birthDate ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getlocation() {
		return location;
	}
	public void setlocation(String location) {
		this.location = location; 
	}
	public Person() {
		
	}
	public Person(int id, String name, String location, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.birthDate = birthDate;
	}
	public Person(String name, String location, Date birthDate) {
		super();
		this.name = name;
		this.location = location;
		this.birthDate = birthDate;
	}
	public Date getBirth_date() {
		return birthDate;
	}
	public void setBirth_date(Date birth_date) {
		this.birthDate = birth_date;
	}

	@Override
	public String toString() {
		return "\nPerson [id=" + id + ", name=" + name + ", location=" + location + ", birthDate=" + birthDate + "]";
	}
	
	
	
	
	
}
