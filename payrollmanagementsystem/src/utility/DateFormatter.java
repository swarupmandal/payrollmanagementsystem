package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

	String date1;

	public static String formatdate(Date date) {
		
		if (date != null) {
			
			return new SimpleDateFormat("dd-MMM-yyyy").format(date);
		
		} else {
		
			return "";
		}
	}

	public static DateFormatter getInstance() {
		return new DateFormatter();
	}
	
}
