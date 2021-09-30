package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.prashant.jpa.hibernate.JpaHIbernate.JpaHibernateApplication;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Course;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Review;

//@ExtendWith(SpringExtension.class) // in Junit5 and @RunWith(SpringRunner.class) 
//with junit4 for AutoWiring

@SpringBootTest(classes = JpaHibernateApplication.class)
class CourseRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository repository;
	
	@Autowired
	EntityManager em;

	@Test
	void findById() {
		Course course = repository.findById(10002L);
		logger.info("test response is for name :"+course.getName());
		assertEquals("Perseverance",course.getName());
	}
	@Test
	@DirtiesContext
	void deleteCourse(){
		try {
			repository.delete(10002L);
			assertNull(repository.findById(10002L));
		} catch (Exception e) {
			assertFalse(true);
		}
	}
	
	@Test
	@DirtiesContext
	void insertOrUpdate() {
		Course c = repository.findById(10001L);
		c.setName("Powerful people come from powerful places");
		//insert
		assertNotNull(repository.save(c));
		Course c1 = repository.findById(1000L);
		assertEquals("Powerful people come from powerful places",c.getName());
	}
	
	@Test
	@DirtiesContext
	void playWithEntityManager() {
		repository.playWithEntityManager();
	}
	
	@Test
	@Transactional
	void retrieveReviewsForCourse() {
		Course c  = repository.findById(10003L);
		logger.info("Reviews for the course id 10003 -> {}",c.getReviews());
		
	}
	
	@Test
	@Transactional
	void retieveCourceForReview() {
		//1.fetching review
		Review r = em.find(Review.class, 40004L);
		//2.eager fetching of courses
		logger.info("The courses for the given review are ->{} ",r.getCourse());
	}
	
	

}
