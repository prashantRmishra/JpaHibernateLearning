package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.prashant.jpa.hibernate.JpaHIbernate.entity.Course;

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
	
	public void playWithEntityManager() {
		Course course1 = new Course("WebServices");
		em.persist(course1);
		Course course2 = new Course("Dragon ball z ");
		em.persist(course2);
		em.flush(); /*flush() is EntityManager method that upon calling changes are updated to db till that point
        So, due to 2 flush() method this transaction will have two operations to perform.*/
		
		course2.setName("One piece is the best anime ever");
		course1.setName("SpringBoot WeServices");
		
		em.refresh(course1); // refreash() updates the respective object to have the same values as the db, which is returned and hibernate select query is executed  
		em.flush();
		
		
	}
	
}
