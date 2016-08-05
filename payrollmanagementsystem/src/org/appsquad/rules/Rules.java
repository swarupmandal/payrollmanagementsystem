package org.appsquad.rules;

import org.appsquad.research.DoubleFormattor;

public class Rules {

	public static double getBasic(double wages, int baseDays, int presentDays){
		//System.out.println("Basic: "+DoubleFormattor.setDoubleFormat((wages/baseDays) * presentDays));
		return ( DoubleFormattor.setDoubleFormat((wages/baseDays) * presentDays) );
	}
	
	public static double getPf(double basic){
		//System.out.println("Pf called. . .");
		return ( DoubleFormattor.setDoubleFormat(basic * 0.12) );
	}
	
	public static double getEsi(double gross, double washing){
		//System.out.println("Esi called. . .");
		System.out.println("gro " + gross);
		System.out.println("was " + washing);
		
		double esiVal=0.0;
		if( washing==0.0){
			esiVal =  ((gross * 0.0175));
		}
		if( washing > 0.0){
			esiVal = ((gross - washing) * 0.0175);
		}
		
		System.out.println("ESI ----- >>> >> > " + esiVal );
		return esiVal ;
	}
	
	public static double getOtSalary(double wages, int baseDays, double otHours){
		return ( DoubleFormattor.setDoubleFormat((wages*otHours)/baseDays) );  
	}
		
	public static double getHra(double hra, int baseDay , int presentDays){
		//System.out.println("Hra called. . .");
		return ( DoubleFormattor.setDoubleFormat( (hra*presentDays)/baseDay ) ); 
	}
	
	public static double getConveyence(double conveyence, int baseDay , int presentDays){
		//System.out.println("conveyence called. . .");
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
		return ( DoubleFormattor.setDoubleFormat((specialAllowance*presentDays)/baseDay) ); 
	}
	
	public static double getSpecialWorkAllowance(double basic, int baseDays, double specialHours){
		return ( DoubleFormattor.setDoubleFormat((basic * 8/ baseDays) * specialHours) );
	}
	
	public static double getGeneral(double component, int baseDay , int presentDays){
		//System.out.println("general called. . .");
		return ( DoubleFormattor.setDoubleFormat((component*presentDays)/baseDay) ); 
	}
	
	public static double getOtSalary(double wages, double otime , int baseDays){
		return ( DoubleFormattor.setDoubleFormat((wages*otime)/baseDays) ); 
	}
	
	public static double getPtAmount(double gross){
		if(gross > 8500 && gross < 10001 ){
			return 90.0;
		}else if (gross > 10000 && gross < 15001 ) {
			return 110.0;
		}else if (gross > 15000 && gross < 25001 ) {
			return 130.0; 
		}else if (gross > 25000 && gross < 40001 ) {
			return 150.0; 
		}else if (gross>40001) {
			return 200.0;
		}else {
			return 0.0;
		}
	}
	
	
	/******************************************************************* ITC *************************************************************************/
	

}
