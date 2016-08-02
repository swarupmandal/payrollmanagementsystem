package org.appsquad.rules;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflectionMainTest {


	


	public static void main(String[] args) {
		
		try {
			
			Class cls = Class.forName("org.appsquad.rules.Rules");
			Object obj = cls.newInstance();
			
			System.out.println("OBJECT: "+obj.getClass());
			ArrayList<String> empList = new ArrayList<>();
			empList.add("wages");
			empList.add("abc");
			empList.add("basic");
			
			Integer i=2;
			Integer j = 9;
			Method method;
			for(String str: empList){
				
				try {
					
					
						
						method = obj.getClass().getMethod(str, i.getClass(), j.getClass());
						Object returnValue = method.invoke(obj, i, j);
						
						System.out.println("RETURN: "+returnValue);
					
				} catch (NoSuchMethodException e) {
					
					System.out.println("No Such Method Found: "+e.getMessage());
					method = obj.getClass().getMethod("generalMethod", i.getClass(), j.getClass() );
					Object returnValue = method.invoke(obj, i, j);
					
					System.out.println("RETURN: "+returnValue);
				
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}



	
}
