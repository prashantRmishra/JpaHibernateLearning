package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.prashant.jpa.hibernate.JpaHIbernate.entity.Course;

@Repository
@Transactional
public class CourseRepository {
	
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
	
}
