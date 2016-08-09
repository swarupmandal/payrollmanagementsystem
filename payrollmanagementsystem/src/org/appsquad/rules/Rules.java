package org.appsquad.rules;

import org.appsquad.research.DoubleFormattor;

public class Rules {

	public static double getBasic(double wages, int baseDays, int presentDays){
		double basic = 0.0;
		basic = (wages/baseDays) * presentDays;
		basic = DoubleFormattor.setDoubleFormat(basic);
		System.out.println("Basic value with round up from Rules: "+basic);
		return basic;
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
		System.out.println("ESI Calulation from Rules without round up ----- >>> >> > " + esiVal );
		return esiVal ;
	}
	
	public static double getOtSalary(double wages, int baseDays, double otHours){
		
		System.out.println("wages----------------------------------------------------------------------------------------- " + wages);
		System.out.println("base ----------------------------------------------------------------------------------------- " + baseDays);
		System.out.println("ot h ----------------------------------------------------------------------------------------- " + otHours);
		return ( DoubleFormattor.setDoubleFormat((wages/baseDays)*otHours));  
	}
		
	public static double getHra(double hra, int baseDay , int presentDays){
		double returnedHra = 0.0;
		returnedHra = (hra/baseDay) * presentDays ;
		returnedHra = DoubleFormattor.setDoubleFormat(returnedHra);
		System.out.println("HRA value with round up from Rules: "+returnedHra);
		return returnedHra;
	}
	
	public static double getHraForOt(double otSalary){
		System.out.println("Ot Hra called with otsal. . ."+otSalary);
		double hra =0.0;
		hra = ( DoubleFormattor.setDoubleFormat(DoubleFormattor.setDoubleFormat(otSalary) * 0.15) );
		System.out.println("returned hra for ot :: "+hra);
		return hra; 
	}
	
	public static double getAllowanceForOt(double othours){
		System.out.println("Ot Allowance called with othours. . ."+othours);
		double allowance =0.0;
		allowance = othours * 50;
		allowance = DoubleFormattor.setDoubleFormat(allowance);
		System.out.println("Returned allowance for ot :: "+allowance);
		return allowance; 
	}
	
	
	public static double getConveyence(double conveyence, int baseDay , int presentDays){
		//System.out.println("conveyence called. . .");
		double retConveyence = 0.0;
		retConveyence = DoubleFormattor.setDoubleFormat((conveyence/baseDay)*presentDays);
		System.out.println("Conveyance: "+retConveyence);
		return retConveyence; 
	}
	
	public static double getConveyenceForOt(double otHours){
		double conveyence = 0.0;
		conveyence = otHours * 20;
		conveyence = DoubleFormattor.setDoubleFormat(conveyence);
		System.out.println("Returned Conveyence for OT: "+conveyence);
		return conveyence;
	}
	
	public static double getMedicalAllowance(double medicalAllowance, int baseDay , int presentDays){
		return ( (medicalAllowance/baseDay)*presentDays) ; 
	}
	
	public static double getAllowances(double allowances, int baseDay , int presentDays){
		return ( (allowances/baseDay) *presentDays); 
	}
	
	public static double getAllowancesOtOthers(double allowances, int baseDay , double otHours){
		double allowance =0.0;
		allowance = (allowances/baseDay) * otHours;
		allowance = DoubleFormattor.setDoubleFormat(allowance);
		return  allowance;
	}
	
	public static double getWashing(double washing, int baseDay , int presentDays){
		return ( (washing/baseDay)*presentDays ); 
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
