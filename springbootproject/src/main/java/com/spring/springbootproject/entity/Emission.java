package com.spring.springbootproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Emission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private static int count = 0;
//	private Integer emissionId = 0;
	@Column
	private String category;
	@Column
	private String gasUnits;
	@Column
	private String description;
	@Column
	private double value;
	
	public Emission() {}

//	public int getEmissionId() {
//		return emissionId;
//	}
//
//	public void setEmissionId() {
//		this.emissionId = count++;
//	}

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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
