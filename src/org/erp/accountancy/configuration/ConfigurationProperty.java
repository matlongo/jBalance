package org.erp.accountancy.configuration;

import java.util.Scanner;

public abstract class ConfigurationProperty {

	private String propertyName;
	@SuppressWarnings("rawtypes")
	private Comparable propertyValue;
	
	public ConfigurationProperty(String name, @SuppressWarnings("rawtypes") Comparable value) {
		this.propertyName = name;
		this.propertyValue = value;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@SuppressWarnings("rawtypes")
	public Comparable getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(@SuppressWarnings("rawtypes") Comparable propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	public abstract void extractValue(Scanner scanner);
	
}
