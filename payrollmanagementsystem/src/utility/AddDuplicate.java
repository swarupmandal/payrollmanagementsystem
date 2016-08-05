package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.appsquad.bean.EmployeeSalaryComponentAmountBean;

public class AddDuplicate {

	public static Map<String, Double> findTotalAmount(ArrayList<EmployeeSalaryComponentAmountBean> escList){
		ArrayList<EmployeeSalaryComponentAmountBean> returningKitchenItemList = new ArrayList<EmployeeSalaryComponentAmountBean>();
		Map<String, Double> empCompMap = new HashMap<String, Double>();	
		for(EmployeeSalaryComponentAmountBean empSalBean : escList){
			String compName = empSalBean.getComponentName();
			if(empCompMap.containsKey(compName)){
				Double stock = empCompMap.get(compName);
				empCompMap.put(compName, stock + empSalBean.getComponentAmount());
			}else{
				empCompMap.put(compName, empSalBean.getComponentAmount());
			}
		}
		for(Entry<String, Double> empSalCompEntry : empCompMap.entrySet()){
			returningKitchenItemList.add(new EmployeeSalaryComponentAmountBean(empSalCompEntry.getKey(), empSalCompEntry.getValue()));
		}
		for(EmployeeSalaryComponentAmountBean finalQty : returningKitchenItemList ){
			System.out.println(" "+finalQty.getComponentName()+"\t"+finalQty.getComponentAmount());
		}
		System.out.println("Returning map:: "+empCompMap);
		return empCompMap;
	}
}
