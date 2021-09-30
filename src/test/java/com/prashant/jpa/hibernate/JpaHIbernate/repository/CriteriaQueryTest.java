package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
class CriteriaQueryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager manager;

	//Criteria query is used when you don't want to write complex JPQL query.
	
	

	//[Select c from Course c]
	@Test
	void criteriaQueryFindAll_basic() {
		/*Steps involved in creating criteria query
		 * 
		 * consider an example of [Select c from Course c] JPQL query
		 * 
		 * 1.Define Criteria builder to create criteria query returning the expected result */
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		//2.expected result Object is Course.class
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		//3.Define root of table which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		//4. Define predicates using criteria builder 
		
		//5 Add predicates to the criteria query
		
		//6.Build the TypedQuery using EntityManager and criteria query
		TypedQuery<Course> query = manager.createQuery(cq.select(courseRoot));
		logger.info("Courses -> {}",query.getResultList());
		
		
	}
	
	//[Select c from Course c where c.name like '%100%']
		@Test
		void criteriaQueryLike() {
			 //1.Define Criteria builder to create criteria query returning the expected result
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			//2.expected result Object is Course.class
			CriteriaQuery<Course> cq = cb.createQuery(Course.class);
			//3.Define root of table which are involved in the query
			Root<Course> courseRoot = cq.from(Course.class);
			//4. Define predicates using criteria builder 
			Predicate like100 = cb.like(courseRoot.get("name"),"%100%");  // name is the column name of table Course
			//5 Add predicates to the criteria query
			cq.where(like100);
			//6.Build the TypedQuery using EntityManager and criteria query
			TypedQuery<Course> query = manager.createQuery(cq.select(courseRoot));
			logger.info("Courses -> {}",query.getResultList());
		}
		
		//select c from Course c where c.students is empty
		@Test
		void criteriaQueryEmpty() {
			 //1.Define Criteria builder to create criteria query returning the expected result
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			//2.expected result Object is Course.class
			CriteriaQuery<Course> cq = cb.createQuery(Course.class);
			//3.Define root of table which are involved in the query
			Root<Course> courseRoot = cq.from(Course.class);
			//4. Define predicates using criteria builder 
			Predicate studentsIsEmpty = cb.isEmpty(courseRoot.get("students"));  // students is the column name of table Course
			//5 Add predicates to the criteria query
			cq.where(studentsIsEmpty);
			//6.Build the TypedQuery using EntityManager and criteria query
			TypedQuery<Course> query = manager.createQuery(cq.select(courseRoot));
			logger.info("Courses that do not have any students -> {}",query.getResultList());
		}
		
		//select c,s from Course c join c.students s
				@Test
				void join() {
					 //1.Define Criteria builder to create criteria query returning the expected result
					CriteriaBuilder cb = manager.getCriteriaBuilder();
					//2.expected result Object is Course.class
					CriteriaQuery<Course> cq = cb.createQuery(Course.class);
					//3.Define root of table which are involved in the query
					Root<Course> courseRoot = cq.from(Course.class);
					//4. Define predicates using criteria builder 
					Join<Object, Object> join = courseRoot.join("students");
					//6.Build the TypedQuery using EntityManager and criteria query
					TypedQuery<Course> query = manager.createQuery(cq.select(courseRoot));
					logger.info("Course join students -> {}",query.getResultList());
				}
		
				//select c,s from Course c left join c.students s
				@Test
				void leftJoin() {
					 //1.Define Criteria builder to create criteria query returning the expected result
					CriteriaBuilder cb = manager.getCriteriaBuilder();
					//2.expected result Object is Course.class
					CriteriaQuery<Course> cq = cb.createQuery(Course.class);
					//3.Define root of table which are involved in the query
					Root<Course> courseRoot = cq.from(Course.class);
					//4. Define predicates using criteria builder 
					Join<Object, Object> join = courseRoot.join("students",JoinType.LEFT);
					//6.Build the TypedQuery using EntityManager and criteria query
					TypedQuery<Course> query = manager.createQuery(cq.select(courseRoot));
					logger.info("Course join students -> {}",query.getResultList());
				}
		
		
	

	
}
