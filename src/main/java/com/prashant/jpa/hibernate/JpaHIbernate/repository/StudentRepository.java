package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prashant.jpa.hibernate.JpaHIbernate.entity.Course;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Passport;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Review;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.ReviewRating;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Student;

@Repository
@Transactional
public class StudentRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	EntityManager em;
	
	/*Find by id*/
	public Student findById(Long id) {
		return em.find(Student.class, id);
	}
	
	/*Delete by id method*/
	public void delete(Long id) {
		
		em.remove(findById(id));
	}
	
	public Student save(Student c) {
		
		if(c.getId()==null) {
			em.persist(c);    //Insert 
		}
		else em.merge(c);    // Update
		return c;
	}
	
	/*JPQL (Java Persistence Query Language)*/
	public List<Student> findAll(){
		TypedQuery<Student> query = em.createQuery("select s from Student s ", Student.class);
		return query.getResultList();
	}
	
	public void saveStudentWithPassport() {
		Passport passport = new Passport("TU3f1516029");
		em.persist(passport); // first insert passport in table else Exception will bee thrown for violating referential integrity constraint
		
		Student student   = new Student("Joe Biden");
		student.setPassport(passport);
		em.persist(student); // after persisting passport, we can easily persist student with any exception
		
	}
	
	public void insertStudentAndCourseHardCoded() {
		Student student = new Student("Abhmanyu");
		Course course1 = new Course("ONGC");
		Course course2 = new Course("BARC");
		em.persist(course2);
		em.persist(course1);
		em.persist(student);
		course2.addStudent(student);
		course1.addStudent(student);
		student.addCourse(course2);
		student.addCourse(course1);
	}
	
	public void insertStudentAndCourse(Student student,Course c) {
		student.addCourse(c);
		c.addStudent(student);
		em.persist(student);
		em.persist(c);
	}
	
	/*Student adding reviews*/
	public void addStudentReviewsHardCoded() {
		//Remember the owning side is review (one student many reviews)
		Review review =  new Review(ReviewRating.FOUR, "Man! what a couese it is !");
		em.persist(review);
		Student student = new Student("Dwight shroot");
		student.addReview(review);
		review.setStudent(student);
		em.persist(student);
		Passport passport = new Passport("EM23223");
		em.persist(passport);
		passport.setStudent(student);
		student.setPassport(passport);
		Course course  = new Course("this is a new course");
		em.persist(course);
		course.addStudent(student);
		student.addCourse(course);
		review.setCourse(course);
		course.addReviews(review);
		
	}
	
	
	
	
}
