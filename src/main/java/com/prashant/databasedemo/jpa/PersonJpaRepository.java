package com.prashant.databasedemo.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.prashant.databasedemo.model.Person;

@Repository
@Transactional
public class PersonJpaRepository {
	//Connect to database
	@PersistenceContext // stores the details of all db operations of the current or running session
	EntityManager entityManager; // interface to the PersistenceContext
	
//	public List<Person> findAll(){
//		return jdbcTemplate.query("select * from person", new PersonRowMapper()); 
//	}
	public Person findById(int id){
		return entityManager.find(Person.class, id); // find(class,primary_key);
	
	}
	
	/*Both update and insert have the same logic
	 * merge(p) check if p.id is set or not
	 * if p.id is set it will update on that id,
	 * else it will just insert the whole obje in the table*/
	public Person update(Person p) {
		return entityManager.merge(p);
	}
	public Person insert(Person p) {
		return entityManager.merge(p);
	}
	public void deleteById(int id) {
		Person p = findById(id);
		entityManager.remove(p);
	}
	public List<Person> findAll(){
		TypedQuery<Person> typedQuery =  entityManager.createNamedQuery("find_all_persons",Person.class); //(find_all_persons:query name,Persons.class: type of entity it will return)
		return typedQuery.getResultList();
	}
}
