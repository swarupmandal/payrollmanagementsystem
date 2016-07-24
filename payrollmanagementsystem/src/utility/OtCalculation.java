package utility;

public class OtCalculation {

	public static Double otAmount(Double totaAmount,Integer totalWorkingDays ,Double hourPerday, Double otHour){
		Double otAmount;
		
		otAmount = (totaAmount/(totalWorkingDays * hourPerday))*otHour;
		
		/*if(otAmount != null){
			String str = null;
			str = new Double(otAmount).toString().substring(0, str.indexOf('0'));
			Double v = Double.valueOf(str);
			otAmount = v;
		}*/
		
		return  otAmount;
	}
	
	
}
