package utility;

public class LeaveCalculation {



	public static Double leaveAmount(Double totaAmount,Integer totalWorkingDays ,Double hourPerday, Integer day){
		
		Double leaveAmount;
		
		leaveAmount = (totaAmount/(totalWorkingDays * hourPerday))*day;
		
		/*if(leaveAmount != null){
			String str = null;
			str = new Double(leaveAmount).toString().substring(0, str.indexOf('0'));
			Double v = Double.valueOf(str);
			leaveAmount = v;
		}*/
		
		
		return  leaveAmount;
	}
	
	

	
}
