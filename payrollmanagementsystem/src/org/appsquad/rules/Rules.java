package org.appsquad.rules;

public class Rules {

	public static double getBasic(double wages, int baseDays, int presentDays){
		return ( (wages/baseDays) * presentDays );
	}
	
	public static double getPf(double basic){
		return ( basic * 0.12 );
	}
	
	public static double getEsi(double basic){
		return ( basic * 0.12 );
	}
	
	public static double getSpecialWorkAllowance(double basic, int baseDays, double workHours){
		return ( (basic / baseDays)/8 * workHours );
	}
	
	public static double getHra(double hra, int monthDay , int presentDays){
		return ( (hra*presentDays)/monthDay ); 
	}
}
