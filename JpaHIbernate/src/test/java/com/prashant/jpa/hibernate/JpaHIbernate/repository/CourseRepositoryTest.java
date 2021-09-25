package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.prashant.jpa.hibernate.JpaHIbernate.JpaHibernateApplication;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Course;
//@ExtendWith(SpringExtension.class) // in Junit5 and @RunWith(SpringRunner.class) with junit4 for AutoWiring
@SpringBootTest(classes = JpaHibernateApplication.class)
class CourseRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository repository;

	@Test
	void contextLoads() {
		Course course = repository.findById(10002L);
		logger.info("test response is for name :"+course.getName());
		assertEquals("JPA in 100 steps",course.getName());
	}

}
