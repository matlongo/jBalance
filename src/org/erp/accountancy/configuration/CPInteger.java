package org.erp.accountancy.configuration;

import java.util.Scanner;

public class CPInteger extends ConfigurationProperty {

	public CPInteger(String name, @SuppressWarnings("rawtypes") Comparable value) {
		super(name, value);
	}

	@Override
	public void extractValue(Scanner scanner) {
		super.setPropertyValue(scanner.nextInt());
	}

}
