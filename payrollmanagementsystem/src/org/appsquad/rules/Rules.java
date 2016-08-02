package org.appsquad.rules;

public class Rules {

	public static double getBasic(double wages, int baseDays, int presentDays){
		return ( (wages/baseDays) * presentDays );
	}
	
	public static double getPf(double basic){
		return ( basic * 0.12 );
	}
	
	public static double getEsi(double gross, double washing){
		if( washing==0.0){
			return (gross * 0.0175);
		}
		if( washing > 0.0){
			return ( (gross - washing) * 0.0175 );
		}
		return washing;
	}
	
	
	
	public static double getHra(double hra, int baseDay , int presentDays){
		return ( (hra*presentDays)/baseDay ); 
	}
	
	public static double getConveyence(double conveyence, int baseDay , int presentDays){
		return ( (conveyence*presentDays)/baseDay ); 
	}
	
	public static double getMedicalAllowance(double medicalAllowance, int baseDay , int presentDays){
		return ( (medicalAllowance*presentDays)/baseDay ); 
	}
	
	public static double getAllowances(double allowances, int baseDay , int presentDays){
		return ( (allowances*presentDays)/baseDay ); 
	}
	
	public static double getWashing(double washing, int baseDay , int presentDays){
		return ( (washing*presentDays)/baseDay ); 
	}
	
	public static double getOtherAllowance(double otherAllowances, int baseDay , int presentDays){
		return ( (otherAllowances*presentDays)/baseDay ); 
	}
	
	public static double getcarWashing(double carWashing, int baseDay , int presentDays){
		return ( (carWashing*presentDays)/baseDay ); 
	}
	
	public static double getSpecialAllowance(double specialAllowance, int baseDay , int presentDays){
		return ( (specialAllowance*presentDays)/baseDay ); 
	}
	
	public static double getSpecialWorkAllowance(double basic, int baseDays, double workHours){
		return ( (basic / baseDays)/8 * workHours );
	}
	
	public static double getGeneral(double component, int baseDay , int presentDays){
		return ( (component*presentDays)/baseDay ); 
	}
	
	public static double getOtSalary(double wages, double otime , int baseDays){
		return ( (wages*otime)/baseDays ); 
	}
}
