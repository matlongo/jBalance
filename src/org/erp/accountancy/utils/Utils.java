package org.erp.accountancy.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * This class provides the user with a set of static methods, that are useful
 * for the user.
 * @author mathias
 *
 */
public class Utils {

	private static int currentYear = -1; 
	
	
	/**
	 * Returns a Calendar instance with the first day of the current year (1/1/c. year).
	 * @return Calendar instance with the first day of the current year.
	 */
	public static Calendar getFirstDayYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar;
	}
	
	/**
	 * Returns a Calendar instance with the first day of a particular month. In order to get
	 * the integer corresponding to a ceratin month, Calendar class provides the static constants
	 * for each month of the year.
	 * @param month Month used in the Calendar.
	 * @return Calendar instance with the first day of a particular month.
	 */
	public static Calendar getFirstDayMonth(int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar;
	}
		
	/**
	 * Returns a Calendar instance with the current date.
	 * @return Calendar instance with the current date.
	 */
	public static Calendar getCurrentDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		return calendar;
	}
	
	
	/**
	 * Returns a String instance with the current date.
	 * @return String instance with the current date.
	 */
	public static String getCurrentDateAsString(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		String date = calendar.get(Calendar.DAY_OF_MONTH) + "-" +
						(calendar.get(Calendar.MONTH)+1) + "-" +
						calendar.get(Calendar.YEAR);
		return date;
	}
	
	
	/**
	 * Returns the current year.
	 * @return Current year.
	 */
	public static int getCurrentYear(){
		if (Utils.currentYear > 0)
			return Utils.currentYear;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * Returns the current month.
	 * @return Current month.
	 */
	public static int getCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * Returns the current day of month
	 * @return Current day of month
	 */
	public static int getCurrentDayOfMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Returns the integer that represents a specific month given by its name.
	 * @param month Month as String to be translated.
	 * @return Integer representing the given month.
	 */
	public static int getMonthByName(String month){
		switch (month.toUpperCase()){
		case "ENERO": return Calendar.JANUARY;
		case "FEBRERO": return Calendar.FEBRUARY;
		case "MARZO": return Calendar.MARCH;
		case "ABRIL": return Calendar.APRIL;
		case "MAYO": return Calendar.MAY;
		case "JUNIO": return Calendar.JUNE;
		case "JULIO": return Calendar.JULY;
		case "AGOSTO": return Calendar.AUGUST;
		case "SEPTIEMBRE": return Calendar.SEPTEMBER;
		case "OCTUBRE": return Calendar.OCTOBER;
		case "NOVIEMBRE": return Calendar.NOVEMBER;
		case "DICIEMBRE": return Calendar.DECEMBER;
		}
		return -1;
	}
	
	public static String getMonthByInt(int month){
		switch (month){
		case Calendar.JANUARY: return "ENERO";
		case Calendar.FEBRUARY: return "FEBRERO";
		case Calendar.MARCH: return "MARZO";
		case Calendar.APRIL: return "ABRIL";
		case Calendar.MAY: return "MAYO";
		case Calendar.JUNE: return "JUNIO";
		case Calendar.JULY: return "JULIO";
		case Calendar.AUGUST: return "AGOSTO";
		case Calendar.SEPTEMBER: return "SEPTIEMBRE";
		case Calendar.OCTOBER: return "OCTUBRE";
		case Calendar.NOVEMBER: return "NOVIEMBRE";
		case Calendar.DECEMBER: return "DICIEMBRE";
		}
		return "";
	}
	
	/**
	 * Returns the String that represents a particular date.
	 * @param calendar Calendar that represents the date.
	 * @return String representing the given date.
	 */
	public static String getDateAsString(Calendar calendar){
		String date = calendar.get(Calendar.DAY_OF_MONTH) + "-" +
				(calendar.get(Calendar.MONTH) + 1) + "-" +
				calendar.get(Calendar.YEAR);
		return date;
	}
	
	/**
	 * Returns the String that represents a particular date.
	 * @param calendar Calendar that represents the date.
	 * @return String representing the given date.
	 */
	public static String getDateAsString(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return getDateAsString(calendar);
	}
	
	
	public static void setCurrentYer(int year){
		Utils.currentYear = year;
	}
}
