package utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CheckSunDayDates {
	
	public static ArrayList<Integer> checkSatDates(int year, int month){
		ArrayList<Integer> sunDayList = new ArrayList<Integer>();
		
		Calendar cal = new GregorianCalendar(year, month, 1);
		do {
		    // get the day of the week for the current day
		    int day = cal.get(Calendar.DAY_OF_WEEK);
		    // check if it is a Saturday or Sunday
		    //if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
		    if (day == Calendar.SUNDAY) {
		        // print the day - but you could add them to a list or whatever
		    	
		    	sunDayList.add(cal.get(Calendar.DAY_OF_MONTH));
		        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
		    }
		    // advance to the next day
		    cal.add(Calendar.DAY_OF_YEAR, 1);
		}  while (cal.get(Calendar.MONTH) == month);	
		
		
		return sunDayList;
	}
	
	
		
	}

