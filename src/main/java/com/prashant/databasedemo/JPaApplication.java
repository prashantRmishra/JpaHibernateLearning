package com.prashant.databasedemo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.prashant.databasedemo.jdbc.PersonJdbcDao;
import com.prashant.databasedemo.jpa.PersonJpaRepository;
import com.prashant.databasedemo.model.Person;

@SpringBootApplication
public class JPaApplication implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PersonJpaRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(JPaApplication.class, args);
	}
	@Override
	public void run(String ... args) throws Exception {
		logger.info("User -> {}",repository.findById(1));
		logger.info("Inserting  -> {}",repository.insert(new Person("Kaustubh","Delhi",new Date())));
		logger.info("Update 23 -> {}",repository.insert(new Person(1,"Shantanu","Delhi",new Date())));
		//repository.deleteById(1);
		logger.info("All User -> {}",repository.findAll());
//		logger.info("All User -> {}",dao.findAll());
//	
//		logger.info("No of rows deleted -> {}",dao.deleteById(1));
//		logger.info("Inserting 23 -> {}",dao.insert(new Person(23,"saddam hussain","Afghanistan",new Date())));
//		logger.info("Updating 23 -> {}",dao.update(new Person(23,"prashant","Thane",new Date())));
	}

}
