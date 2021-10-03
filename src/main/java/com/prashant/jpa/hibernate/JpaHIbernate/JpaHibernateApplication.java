package com.prashant.jpa.hibernate.JpaHIbernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.prashant.jpa.hibernate.JpaHIbernate.entity.FullTimeEmployee;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.PartTimeEmployee;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Review;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.ReviewRating;
import com.prashant.jpa.hibernate.JpaHIbernate.repository.CourseRepository;
import com.prashant.jpa.hibernate.JpaHIbernate.repository.EmployeeRepository;
import com.prashant.jpa.hibernate.JpaHIbernate.repository.StudentRepository;

@SpringBootApplication
public class JpaHibernateApplication implements CommandLineRunner {
	

	@Autowired
	CourseRepository repo;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	StudentRepository studentRepository;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public static void main(String[] args) {
		SpringApplication.run(JpaHibernateApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		//Course findById = repo.findById(10001L);
		//logger.info("Find by id 10001->{}",findById);
		//repo.delete(10001L);
		//logger.info("Insert -> {}",repo.save(new Course("Hang in there")));
		//repo.playWithEntityManager();
		//repo.playWithEntityManagerCreatedAndUpdatedTimeStamp();
		//logger.info("All course are -> {}",repo.findAll());
		//----------------------------------------------------------------------
		
		//logger.info("All Students are-> {}",studentRepository.findAll());
		//studentRepository.saveStudentWithPassport();
		//-----------------------------------------------------------------------
		
		//repo.addReviewForCOurse();
		Long courseId = 10003L;
		List<Review> reviews = new ArrayList<>();
		Review review  = new Review(ReviewRating.FOUR,"Its a funny course");
		reviews.add(review);
		//repo.addReviewForCOurse(courseId, reviews);
		//----------------------------------------------------------------------
		
		//studentRepository.insertStudentAndCourseHardCoded();
		//studentRepository.insertStudentAndCourse(new Student("Ranganathan"),new Course("Republic TV"));
		//-----------------------------------------------------------------------
		//studentRepository.addStudentReviewsHardCoded();
		//----------------------------------------------------------------------
//		employeeRepository.insert(new PartTimeEmployee("Chadvick Boseman", new BigDecimal(50)));
//		employeeRepository.insert(new FullTimeEmployee("Chris Evans", new BigDecimal(500000)));
//		logger.info("All the part time Employees are -> {}",employeeRepository.findAllPartTimeEmployees());
//		logger.info("All the full time Employees are -> {}",employeeRepository.findAllFullTimeEmployees());
		//----------------------------------------------------------------------
		
		
	}

}
