package org.appsquad.research;

public class StringConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "BAsic";
		
		System.out.println("get"+toCamelCase(str));
	}

	public static String setAsFunctionName(String componentName){
		return "get"+toCamelCase(componentName);
	}
	
	public static String toCamelCase(String inputString) {
	       String result = "";
	       if (inputString.length() == 0) {
	           return result;
	       }
	       char firstChar = inputString.charAt(0);
	       char firstCharToUpperCase = Character.toUpperCase(firstChar);
	       result = result + firstCharToUpperCase;
	       for (int i = 1; i < inputString.length(); i++) {
	           char currentChar = inputString.charAt(i);
	           char previousChar = inputString.charAt(i - 1);
	           if (previousChar == ' ') {
	               char currentCharToUpperCase = Character.toUpperCase(currentChar);
	               result = result + currentCharToUpperCase;
	           } else {
	               char currentCharToLowerCase = Character.toLowerCase(currentChar);
	               result = result + currentCharToLowerCase;
	           }
	       }
	       return result;
	   }
}
