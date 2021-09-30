package com.prashant.jpa.hibernate.JpaHIbernate.repository;

import java.util.List;

import javax.persistence.EntityManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prashant.jpa.hibernate.JpaHIbernate.entity.Employee;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.FullTimeEmployee;
import com.prashant.jpa.hibernate.JpaHIbernate.entity.PartTimeEmployee;

@Repository
@Transactional
public class EmployeeRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	EntityManager em;
	
	/*Find by id*/
	public void insert(Employee employee) {
		em.persist(employee);
	}
	public List<PartTimeEmployee> findAllPartTimeEmployees(){
		return em.createQuery("select e from PartTimeEmployee e",PartTimeEmployee.class).getResultList();
	}
	public List<FullTimeEmployee> findAllFullTimeEmployees(){
		return em.createQuery("select e from FullTimeEmployee e",FullTimeEmployee.class).getResultList();
	}
	
	
}
