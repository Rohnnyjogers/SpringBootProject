package com.spring.springbootproject.entity;

public class Emission {
	
	private String category;
	private String gasUnits;
	private double value;
	
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
