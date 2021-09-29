package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.prashant.jpa.hibernate.JpaHIbernate.JpaHibernateApplication;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Course;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Student;

//@ExtendWith(SpringExtension.class) // in Junit5 and @RunWith(SpringRunner.class) 
//with junit4 for AutoWiring

@SpringBootTest(classes = JpaHibernateApplication.class)
class JpqlTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager manager;

	/* Generic Type */
	@Test
	void JpqlFindAll_basic() {
		Query createQuery = manager.createQuery("select c from Course c");
		List resultList = createQuery.getResultList();
		logger.info("Result list is -> {}", resultList);
	}

	/* Typed query */
	@Test
	void JpqlFindAll_Typed() {
		TypedQuery<Course> createQuery = manager.createQuery("select c from Course c", Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Typed Result list is -> {}", resultList);
	}

	/* where clause */
	@Test
	void JpqlFindAll_where() {
		TypedQuery<Course> createQuery = manager.createQuery("select c from Course c where name like '%100%' ",
				Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Typed Result list with where clause is -> {}", resultList);
	}

	/* NamedQuery */
	@Test
	void JpqlFindAll_NamedQuery() {
		TypedQuery<Course> createQuery = manager.createNamedQuery("find_all_courses", Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Typed Result list with NamedQuery is -> {}", resultList);
	}

	/* Finding course without students */
	@Test
	void findCoursesWithoutStudents() {
		TypedQuery<Course> typedQuery = manager.createQuery("select c from Course c where c.students is empty",
				Course.class);
		// students is the variable in Course class you know that
		logger.info("All the course where no students are enrolled are {}", typedQuery.getResultList());
	}

	/* Find Courses having more than 1 student */
	@Test
	void findCoursesWithAtleastTwoStudents() {
		TypedQuery<Course> typedQuery = manager.createQuery("select c from Course c where size(c.students) >= 2",
				Course.class);
		logger.info("All the course with atleast two students {}", typedQuery.getResultList());
	}

	/* Order by the size of students course has */
	@Test
	void findCoursesOrderByTheSizeOfStudents() {
		TypedQuery<Course> typedQuery = manager.createQuery("select c from Course c order by size(c.students)",
				Course.class);
		// you can add desc for descending ordering
		logger.info("Courses Ordered by the size of students {}", typedQuery.getResultList());
	}

	/* find students that have passport number of the form %23% */
	@Test
	void findStudentWithPatternPassportNumber() {
		TypedQuery<Student> query = manager.createQuery("select s from Student s where s.passport.number like '%23%'",
				Student.class);
		// passport is the variable instance of Passport in Student class
		logger.info("Student having passport number of the form %23% are -> {}", query.getResultList());
	}

}
