package com.prashant.jpa.hibernate.JpaHIbernate.entity;



import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FullTimeEmployee extends Employee {
	protected FullTimeEmployee() {
		
	}
	public FullTimeEmployee(String name, BigDecimal hourlySalary) {
		super(name);
		this.hourlySalary=hourlySalary;
	
		
	}
	
	private BigDecimal hourlySalary;

	public BigDecimal getHourlySalary() {
		return hourlySalary;
	}
	public void setHourlySalary(BigDecimal hourlySalary) {
		this.hourlySalary = hourlySalary;
	}
}
