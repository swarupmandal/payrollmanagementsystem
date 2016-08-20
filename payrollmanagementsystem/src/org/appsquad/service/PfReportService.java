package org.appsquad.service;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.appsquad.bean.PfReportBean;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.Executions;

public class PfReportService {

	public static void printCSV(ArrayList<PfReportBean> pfReportBeanList){
		File f = null;  boolean bool = false;
		try{
	         // create new file
	    	 String realPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
	  		
	  		String reportNamewithPath = realPath + "pfreport.csv";
	  		System.out.println(reportNamewithPath);
	        f = new File(reportNamewithPath);
	         // tries to create new file in the system
	         bool = f.createNewFile();
	         
	         // prints
	         System.out.println("File created: "+bool);
	         if(f.exists())
	         // deletes file from the system
	         f.delete();
	         System.out.println("delete() method is invoked");
	         // delete() is invoked
	         f.createNewFile();
	       
	         // tries to create new file in the system
	         bool = f.createNewFile();
	         
	         // print
	         System.out.println("File created: "+bool);
	         FileOutputStream fileOutputStream = new FileOutputStream(f);
	         OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream);    
	            Writer w = new BufferedWriter(osw);
	            w.write("PF NUMBER, NAME,DAYS,WAGES,EMPLOYEES CON @12% of BASIC\n");
	            for(int i=0;i<pfReportBeanList.size();i++){
	            	w.write(pfReportBeanList.get(i).getPfNumber()+","+pfReportBeanList.get(i).getEmpName()+","+pfReportBeanList.get(i).getPreDays()
	            			+","+pfReportBeanList.get(i).getWages()+","+pfReportBeanList.get(i).getPfAmount()+"\n");
	            }
	            w.close();
	           Desktop.getDesktop().open(f);
	           
	            FileInputStream fis = new FileInputStream(new File(reportNamewithPath));
	    		byte[] ba1 = new byte[1024];
	    		int baLength;
	    		ByteArrayOutputStream bios = new ByteArrayOutputStream();
	    		try {
	    			try {
	    				while ((baLength = fis.read(ba1)) != -1) {
	    					bios.write(ba1, 0, baLength);
	    				}
	    			} catch (Exception e1) {
	    			} finally {
	    				fis.close();
	    			}
	    			final AMedia amedia = new AMedia("pfreport", "csv", "application/csv", bios.toByteArray());
	    			Filedownload.save(amedia);
	    		}catch(Exception exception){		
	    		}
	      }catch(Exception e){
	         e.printStackTrace();
	      }
	}
	
	
	
	
	
	
}
