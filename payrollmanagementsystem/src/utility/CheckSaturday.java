package utility;

import java.util.Calendar;

public class CheckSaturday {

	/*public static void main(String[] args) {
		int a = countWeekendDays(2016, 7);
		
		System.out.println("A " + a);
	}*/
	
	
	public static int countWeekendDays(int year, int month) {
	    Calendar calendar = Calendar.getInstance();
	    // Note that month is 0-based in calendar, bizarrely.
	    calendar.set(year, month - 1, 1);
	    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	    int count = 0;
	    for (int day = 1; day <= daysInMonth; day++) {
	        calendar.set(year, month - 1, day);
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	        if (dayOfWeek == Calendar.SATURDAY) {
	            count++;
	            // Or do whatever you need to with the result.
	        }
	        
	        
	        /*if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
	            count++;
	            // Or do whatever you need to with the result.
	        }*/
	    }
	    return count;
	}
	
}
