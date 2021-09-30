package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.prashant.jpa.hibernate.JpaHIbernate.JpaHibernateApplication;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Passport;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Student;


@SpringBootTest(classes = JpaHibernateApplication.class)
class StudentRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	StudentRepository repository;

	@Autowired
	EntityManager entityManager;

	@Test
	@Transactional // Persistence Context will be created
	void someTest() {
		
		//Operation 1 : Retrive Student
		Student student = entityManager.find(Student.class, 20002L);
		//Persistence Context will have (Student)
		
		//Operation 2 : Retrive Passport
		Passport passport = student.getPassport();
		//Persistence Context will have (Student,Passport)
		
		//Operation 3 : Update Passport number
		passport.setNumber("JB007");
		//Persistence Context will have (Student,Passport++)
		//Operation 4 : Update Student Name
		
		student.setName("Sandeep-updated");
		//Persistence Context will have (Student++,Passport++)
	}
	
	
	
	@Test
	@Transactional
	void findStudentWithPassport() {
		Student student = entityManager.find(Student.class, 20001L);
		logger.info("Student -> {}",student);
		logger.info("Passport -> {}",student.getPassport());
	}
	
	@Test
	@Transactional
	void findPassportWithAssociatedStudent() {
		Passport passport = entityManager.find(Passport.class, 30002L);
		logger.info("Passport -> {}",passport);
		logger.info("Student -> {}",passport.getStudent());
	}
	
	@Test
	@Transactional
	void retrieveStudentAndCourse() {
		Student student = repository.findById(20001L);
		logger.info("Student with id 20001 -> {}",student);
		logger.info("Course student 20001 enrolled in -> {}",student.getCourses());
		
	}
	
	
}
