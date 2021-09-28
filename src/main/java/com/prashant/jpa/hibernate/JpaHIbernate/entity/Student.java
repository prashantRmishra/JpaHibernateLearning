package com.prashant.jpa.hibernate.JpaHIbernate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;




@Entity

public class Student {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable=false)
	private String name;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Passport passport;
	
	@ManyToMany
	@JoinTable(name = "student_course",
	joinColumns = @JoinColumn(name="student_id"), //joinColumn: column name of this table
	inverseJoinColumns = @JoinColumn(name="course_id")  //inverseJoinColumn: column name of other table (non owning)
	)
	private List<Course> courses  = new ArrayList<>();
	
	public List<Course> getCourses() {
		return courses;
	}

	public void addCourse(Course c) {
		 courses.add(c);
	}
	
	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	public Long getId() {
		return id;
	}
	protected Student() {
		
	}
	public Student(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "\nStudent [name=" + name + "]";
	}
	
	

}
