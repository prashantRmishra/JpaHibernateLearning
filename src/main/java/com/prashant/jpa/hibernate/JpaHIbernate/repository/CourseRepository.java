package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.prashant.jpa.hibernate.JpaHIbernate.entity.Course;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.Review;

@Repository
@Transactional
public class CourseRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	EntityManager em;
	
	/*Find by id*/
	public Course findById(Long id) {
		return em.find(Course.class, id);
	}
	
	/*Delete by id method*/
	public void delete(Long id) {
		
		em.remove(findById(id));
	}
	
	public Course save(Course c) {
		
		if(c.getId()==null) {
			em.persist(c);    //Insert 
		}
		else em.merge(c);    // Update
		return c;
	}
	
	/*JPQL (Java Persistence Query Language)*/
	public List<Course> findAll(){
		TypedQuery<Course> query = em.createNamedQuery("find_all_courses",Course.class);
		return query.getResultList();
	}
	
	public void playWithEntityManager() {
		Course course1 = new Course("WebServices");
		em.persist(course1);
		Course course2 = new Course("Dragon ball z ");
		em.persist(course2);
		em.flush(); /*flush() is EntityManager method that upon calling changes are updated to db till that point
        So, due to 2 flush() method this transaction will have two operations to perform.*/
		
		course2.setName("One piece is the best anime ever");
		course1.setName("SpringBoot WeServices");
		
		em.refresh(course1); // refresh() updates the respective object to have the same values as the db, which is returned and hibernate select query is executed  
		em.flush();
		
		
	}
	public void playWithEntityManagerCreatedAndUpdatedTimeStamp() {
		Course course1 = new Course("WebServices");
		em.persist(course1);
		em.flush();
		Course course2  = findById(course1.getId());
		course2.setName("WebServices-Updated");
		
	}
	
	public void addReviewForCOurse() {
	
		//1.find course whose review is to be given
		Course course = findById(10002L);
		
		//Review given to that course
		logger.info("List of Reviews -> {}",course.getReviews());
		
		//2.create two more reviews
		Review review1 = new Review("****", "Its a very good course");
		Review review2 = new Review("***","Its all course");
		
		//3.Setting the Relationship
		//assign review1 to course that has id 10002
		course.addReviews(review1);
		/* We have to assign review to course as well 
		 * because of bidirectional relationship oneToMany (course->review)
		 * manyToOne (review->course)
		 * Owning side is review
		 * */
		
		//assign review a course   
		review1.setCourse(course);
		
		//repeating the same what we did above
		course.addReviews(review2);
		review2.setCourse(course);
		
		//persist review 
		/*We don't need to persist course as we are not changing anything in it
		 * we just need to persist the review1, review2 */
		em.persist(review1);
		em.persist(review2);
		
		
	}
	
	/*Generalization of add review for course*/
	public void addReviewForCOurse(Long courseId,List<Review> reviews) {

		
		//1.find course whose review is to be given
		Course course = findById(courseId);
		
		//Review given to that course
		logger.info("List of Reviews for course id "+courseId+" -> {}",course.getReviews());
		
		
		//2.Setting the Relationship
		//assign reviews to course that has courseId
		for(Review review : reviews) {
			
			course.addReviews(review);
			
			/* We have to assign review to course as well 
			 * because of bidirectional relationship oneToMany (course->review)
			 * manyToOne (review->course)
			 * Owning side is review
			 * */
			
			//3.assign  review a course
			review.setCourse(course);
			
			//4.persist review
			em.persist(review);
			
			//persist review 
			/*We don't need to persist course as we are not changing anything in it
			 * we just need to persist the review */
		}		
	
	}
	
}
