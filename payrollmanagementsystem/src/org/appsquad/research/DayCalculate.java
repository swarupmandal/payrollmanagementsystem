package org.appsquad.research;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DayCalculate {

	public static void main(String[] args) {
		int iYear = 2016;
		int iMonth = 8; // 1 (months begin with 0)
		int iDay = 1;

		// Create a calendar object and set year and month
		Calendar mycal = new GregorianCalendar(iYear, (iMonth-1), iDay);

		// Get the number of days in that month
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(daysInMonth);
	}

	public static int getDaysOfMonth(int iYear, int iMonth, int iDay){
		// Create a calendar object and set year and month
		Calendar mycal = new GregorianCalendar(iYear, (iMonth-1), iDay);
		// Get the number of days in that month
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("Days in Month "+daysInMonth);
		return daysInMonth;
	}
}
