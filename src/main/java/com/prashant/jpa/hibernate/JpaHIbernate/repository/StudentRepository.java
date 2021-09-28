package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.prashant.jpa.hibernate.JpaHIbernate.entity.Course;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Passport;
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
	
	
	
	
}
