package org.appsquad.research;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleFormattor {

	private static DecimalFormat df2 = new DecimalFormat(".##");

	public static void main(String[] args) {

		/*double input = 721.532258064516;
		//System.out.println("double : " + input);
		double upInput,downInput;
		//System.out.println("double (default) : " + df2.format(input));

		df2.setRoundingMode(RoundingMode.UP);
		//System.out.println("double (UP) : " + df2.format(input));
		upInput = Double.valueOf(df2.format(input)) ;
		//System.out.println("Up : "+upInput);
		
		
		df2.setRoundingMode(RoundingMode.DOWN);
		//System.out.println("double (DOWN) : " + df2.format(input));
		downInput = Double.valueOf(df2.format(input)) ;
		//System.out.println("Down : "+downInput);
		if( upInput != downInput){
			//System.out.println("Converted : "+(upInput+1.0));
		}*/
		setDoubleFormatEsi(182.0010000000);
	}

	public static double setDoubleFormat(double value){
		//System.out.println("Double formatting...");
		int intValue = (int)value;
		//System.out.println("int part- - > "+intValue);
		double partValue = value - intValue;
		//System.out.println("Part val- - > "+partValue);
		DecimalFormat decim = new DecimalFormat("0.00");
		if(partValue > 0.50){
			value = (value+1.0)-partValue;
			value = Double.parseDouble(decim.format(value));
			//System.out.println("Part > 0.5- - >"+value);
		}else{
			value = value-partValue;
			value = Double.parseDouble(decim.format(value));
			//System.out.println("Part < 0.5- - >"+value);
		}
		//System.out.println("Returend: "+Double.parseDouble(decim.format(value)));
		return value;
	}
	
	public static double setDoubleFormatEsi(double value){
		System.out.println("Double formatting esi..."+value);
		DecimalFormat decim = new DecimalFormat("0.00");
		int intValue = (int)value;
		System.out.println("int part- - > "+intValue);
		double partValue = value - intValue;
		System.out.println("Part val- - > "+partValue);
		double covertValue = Double.parseDouble(decim.format(partValue));
		System.out.println("con:: "+covertValue);
		
		if(covertValue > 0.0){
			value = (value+1.0)-partValue;
			value = Double.parseDouble(decim.format(value));
			System.out.println("Part > 0.0- - >"+value);
		}else{
			value = Double.parseDouble(decim.format(value));
			System.out.println("Part < 0.5- - >"+value);
		}
		System.out.println("Returend: "+value);
		return value;
	}
}
