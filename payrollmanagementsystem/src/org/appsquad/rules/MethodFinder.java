package org.appsquad.rules;

import java.lang.reflect.Method;

import org.appsquad.research.StringConverter;

public class MethodFinder {

	public static void main(String[] args) throws Exception {
		/*MethodFinder finder = new MethodFinder();
		Rules rules = new Rules();
		finder.getFuctionCall("getGenral", rules);*/
		String testMethodname= "pf";
		
		Rules obj = new Rules();
		Method[] method;
	    method=obj.getClass().getMethods();
	    try
	    {
	        for(int i=0;i<method.length;i++)
	        {
	            String name=method[i].getName();
	            if(name.contains(StringConverter.setAsFunctionName(testMethodname)))
	            {   
	            	System.out.println("Method matched- - - - ");
	                method[i].invoke(name,5000.0,26,25);
	            }/*else{
	            	 method[i].invoke("getGeneral",5000.0,26,25);
	            }*/
	        }
	    }
	    catch(Exception ex)
	    {
	        System.out.println(ex.getMessage());
	    }
	}

	public void getFuctionCall(String methodName, Object obj){  
		  Method method;   
		  try {
		   method = obj.getClass().getMethod(methodName);
		   method.invoke(obj, 1,2);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
		
}
