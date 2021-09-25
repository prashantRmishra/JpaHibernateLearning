package com.prashant.databasedemo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.prashant.databasedemo.jdbc.PersonJdbcDao;
import com.prashant.databasedemo.model.Person;

//@SpringBootApplication
public class JdbcApplication implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PersonJdbcDao dao;
	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}
	@Override
	public void run(String ... args) throws Exception {
		logger.info("All User -> {}",dao.findAll());
		logger.info("User -> {}",dao.findById(1));
		logger.info("No of rows deleted -> {}",dao.deleteById(1));
		logger.info("Inserting 23 -> {}",dao.insert(new Person(23,"saddam hussain","Afghanistan",new Date())));
		logger.info("Updating 23 -> {}",dao.update(new Person(23,"prashant","Thane",new Date())));
	}

}
