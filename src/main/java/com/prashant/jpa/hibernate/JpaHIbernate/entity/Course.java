package com.prashant.jpa.hibernate.JpaHIbernate.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQuery(name = "find_all_courses", query = "select c from Course c")
public class Course {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@OneToMany(mappedBy = "course") /// As this Course obj could have many review
	//@JsonIgnore
	List<Review> reviews = new ArrayList<>();
	
	
	@ManyToMany(mappedBy = "courses")
	//@JsonIgnore
	private List<Student> students  = new ArrayList<>();
	
	public List<Student> getStudent() {
		return this.students;
	}
	public void addStudent(Student student) {
		this.students.add(student);
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void addReviews(Review review) {
		this.reviews.add(review);
	}
	public void removeReviews(Review review) {
		this.reviews.remove(review);
	}
	public Long getId() {
		return id;
	}
	protected Course() {
		
	}
	public Course(String name) {
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
		return "\nCourse [id=" + id + ", name=" + name + "]";
	}
	
	

}
