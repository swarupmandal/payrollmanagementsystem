package utility;

import org.appsquad.bean.RunPayRollBean;

public class OtCalculation {

	public static Double otAmount(Double totaAmount,Integer totalWorkingDays ,Double hourPerday, Double otHour){
		Double otAmount;
		
		otAmount = (totaAmount/(totalWorkingDays * hourPerday))*otHour;
		
		return  otAmount;
	}
	
	public static double otAmount(RunPayRollBean bean){
		double a=0.0;
		
		a = bean.getTotalSalary()/bean.getTotalOtHoursF();
		
		System.out.println("A A A " + a);
		
	    //a = (bean.getTotalSalary()/(bean.getTotalWorkingDays()*hourPerDay))*bean.getOtHoursF();
		
		return  a;
	}
	
	
	
	
	
}
