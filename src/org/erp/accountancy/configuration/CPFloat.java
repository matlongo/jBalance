package org.erp.accountancy.configuration;

import java.util.Scanner;

public class CPFloat extends ConfigurationProperty {

	public CPFloat(String name, @SuppressWarnings("rawtypes") Comparable value) {
		super(name, value);
	}

	@Override
	public void extractValue(Scanner scanner) {
		super.setPropertyValue(scanner.nextFloat());
	}
}
