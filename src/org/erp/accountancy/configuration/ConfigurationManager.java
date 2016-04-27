package org.erp.accountancy.configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.erp.accountancy.utils.Constants;

public class ConfigurationManager {

	public final static Map<String, Integer> DEFAULT_PROPERTIES_MAP = ConfigurationManager.getDefaultMap();
	public static final String HONORARIOS_KEY = "Honorarios";
	public static final String ANIO_KEY = "Anio";
	public static final String MES_KEY = "Mes";
	
	private Map<String, Integer> properties;
	private static ConfigurationManager instance = null;
	private String filePath;

	private ConfigurationManager(String filePath, Map<String, Integer> properties) {
		this.properties = properties;
		if (properties == null) 
			properties = new HashMap<>();
		this.filePath = filePath;
		
		this.getInformation();
	}

	private static Map<String, Integer> getDefaultMap() {
		Map<String, Integer> properties = new HashMap<>();
		properties.put(HONORARIOS_KEY, 200);
		properties.put(ANIO_KEY, 2016);
		properties.put(MES_KEY, 1);
		return properties;
	}

	public static ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager(Constants.CONFIGURATION_FILE_PATH, DEFAULT_PROPERTIES_MAP);
		}
		return instance;
	}

	public void storeFile() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.CONFIGURATION_FILE_PATH)));
			for (String key: properties.keySet()){
				Integer property = properties.get(key);
				out.println(key+","+property);
			}
		} catch (IOException e) {
			System.out.println("The configuration file could not be correctly updated");
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	public void getInformation(){
		Scanner s = null;
		
		try {
			s = new Scanner(new File(filePath));
			
			while (s.hasNextLine()){
				String line = s.nextLine();
				String name = line.split(",")[0].trim();
				String value = line.split(",")[1].trim();
				properties.put(name, Integer.parseInt(value));
			}
		} catch (Exception ex) {
			System.out.println("The configuration file couldn't be read.");
		} finally {
			if (s != null){
				s.close();
			}
		}
	}
	
	public Integer getProperty(String configurationName){
		return properties.get(configurationName);
	}
	
	public void updateProperty(String name, Integer property){
		properties.put(name, property);
	}

}
