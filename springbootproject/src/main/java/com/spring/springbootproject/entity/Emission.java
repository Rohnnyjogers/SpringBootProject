package com.spring.springbootproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Emission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String category;
	private String gasUnits;
	private long value;
	
	public Emission() {}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGasUnits() {
		return gasUnits;
	}

	public void setGasUnits(String gasUnits) {
		this.gasUnits = gasUnits;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
}
