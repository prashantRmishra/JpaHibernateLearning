package com.prashant.databasedemo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.prashant.databasedemo.model.Person;

@Repository
public class PersonJdbcDao {
	
	/*RowMapper is used when name of the column in the table don't match with the bean properties 
	 * If they match we can simply use BeanPropertyRowMapper() like :
	 * jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<Person>(Person.class)); */
	class PersonRowMapper implements RowMapper<Person>{
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person p = new Person();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setlocation(rs.getString("location"));
			p.setBirth_date(rs.getTimestamp("birth_date"));
			return p;
		}
	}

	@Autowired
	JdbcTemplate jdbcTemplate;
	public List<Person> findAll(){
		return jdbcTemplate.query("select * from person", new PersonRowMapper()); 
	}
	public Person findById(int id){
		return jdbcTemplate.query("select * from person where id =?",
				new PersonRowMapper(),new Object[]{id}).get(0); 
	}
	public int deleteById(int id){
		return jdbcTemplate.update("delete from person where id =?",
				new Object[]{id}); 
	}
	public int insert(Person p) {
		return jdbcTemplate.update("insert into person(id,name,location,birth_date) values (?,?,?,?)",
				new Object[] {p.getId(),p.getName(),p.getlocation(),p.getBirth_date()});
	}
	public int update (Person p ) {
		return jdbcTemplate.update("update person set name = ?,location=?,birth_date= ?"
				+ " where id =?" ,
				new Object[] {p.getName(),p.getlocation(),p.getBirth_date(),p.getId()});
	}
}
