package com.prashant.jpa.hibernate.JpaHIbernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;


@Entity
public class Review {
	@Id
	@GeneratedValue
	private Long id;
	private String rating;
	
	private String description;
	
	@ManyToOne  /// as many review Obj could be associated to only one course
	private Course course; 
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Long getId() {
		return id;
	}
	protected Review() {
		
	}
	public Review(String rating,String description) {
		super();
		this.rating=rating;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return String.format("Review[%s,%s]",rating,description);
	}
	
	

}
