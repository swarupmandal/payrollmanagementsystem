package org.appsquad.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;


import org.appsquad.bean.HolidayMasterBean;
import org.appsquad.bean.HolidayMasterGeneralHolidayBean;
import org.appsquad.bean.HolidayMasterWeekDayBean;
import org.appsquad.database.DbConnection;

import org.appsquad.sql.HolidayMasterSql;
import org.zkoss.zul.Messagebox;

import utility.DateFormatter;
import utility.Util1;



public class HolidayMasterDao {
	
	public static void loadLeaveYr(HolidayMasterBean bean){
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.loadLeaveYear, Arrays.asList());
							
							ResultSet resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								
								bean.setLeavYrStartDate(resultSet.getDate("start_date"));
								bean.setLeavYrStartDateValue(resultSet.getString("start_date"));
								
								bean.setLeavYrEndDate(resultSet.getDate("end_date"));
								bean.setLeavYrEndDateValue(resultSet.getString("end_date"));
								
								
								bean.setLeaveYrId(resultSet.getInt("id"));
							}
							
							if(resultSet.next()){
								
							}
							 
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
				
			}
			} catch (Exception e) {
				
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	public static int saveLeaveYr(HolidayMasterBean bean, String userName){
		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   String sql = "INSERT INTO pms_leave_year_master(start_date, end_date, created_by, updated_by) VALUES (?, ?, ?, ?) ";

				   try {
					   preparedStatement = connection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);
					   preparedStatement.setDate(1, bean.getLeavYrStartDate());
					   preparedStatement.setDate(2, bean.getLeavYrEndDate());
					   preparedStatement.setString(3, userName);
					   preparedStatement.setString(4, userName);
					   int i = preparedStatement.executeUpdate();
					   System.out.println("RES " + preparedStatement);
					   ResultSet resultSet = preparedStatement.getGeneratedKeys();
						
						if(resultSet.next()){
							id = resultSet.getInt(1);
							
							System.out.println("Returned Id is ====="+id);
						}
					   
					   
					   
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
				
				
			}
			} catch (Exception e) {

				
				Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public static void saveHourPerDay(HolidayMasterBean bean, String userName, int companyId, int unitId){
		

		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.saveWorkingHourPerDay, 
									                Arrays.asList(companyId, unitId, bean.getLeaveYrId(), bean.getHourPerDay(), userName, userName));
							int i = preparedStatement.executeUpdate();
							if(i>0){
								Messagebox.show("Saved SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
								
							}
							
							 
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
				
				
			}
			} catch (Exception e) {
				
				Messagebox.show("Already Exist","ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static ArrayList<HolidayMasterWeekDayBean> loadWeekDayData(){
		ArrayList<HolidayMasterWeekDayBean> beanList = new ArrayList<HolidayMasterWeekDayBean>();
        if(beanList.size()>0){
        	beanList.clear();
        }
		
		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
				   try {
						connection = DbConnection.createConnection();
						ResultSet resultSet = null;
					
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.loadWeekDayQuery, null);
							
							resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								HolidayMasterWeekDayBean bean = new HolidayMasterWeekDayBean();
								bean.setWeeklyHoliDayId(resultSet.getInt("id"));
								bean.setWeeklyHoliDayName(resultSet.getString("day"));
								beanList.add(bean);
								
							}  
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
					
					   
				   } finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
				
				
			}
			} catch (Exception e) {

				
				Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanList;
	
		
	}
	
	
	public static int saveWeekLeaveMasterData(String week,HolidayMasterBean bean, String userName, int companyId, int unitId){
		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.saveWeekLyHollyDayMasterData, 
									                Arrays.asList(week, bean.getWeeklyHoliDayName(), bean.getWeeklyHoliDayId(), 
									                		userName,userName, bean.getLeaveYrId(), companyId, unitId));
							int i = preparedStatement.executeUpdate();
							if(i>0){
								Messagebox.show("Saved SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
								
							}
							
							 
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
				
				
			}
			} catch (Exception e) {
				
				Messagebox.show("Already Exist","ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	
		
	}
	
	
	
	
	public static ArrayList<HolidayMasterBean> LoadWeekDayMasterData(){

		ArrayList<HolidayMasterBean> beanList = new ArrayList<HolidayMasterBean>();
        
		if(beanList.size()>0){
			beanList.clear();
		}
		
		int id = 0;
		Integer count = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
				  
						
						ResultSet resultSet = null;
					
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.loadWeeklyHollyDayMasterData, null);
							
							resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								HolidayMasterBean bean = new HolidayMasterBean();
								count = count+1;
								bean.setWeekDayCount(count);
								bean.setWeeklyHoliDayName(resultSet.getString("day"));
								bean.setWeeklyHoiDayMasterId(resultSet.getInt("id"));
								bean.setWeek(resultSet.getString("week"));
								bean.setWeeklyHoliDayId(resultSet.getInt("day_id"));
								bean.setLeaveYrId(resultSet.getInt("leave_year_id"));
								
								beanList.add(bean);
								
							}  
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
					
			}
			} catch (Exception e) {

				
				Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanList;
	
	}
	
	public static void deleteWeekDayMasterData( HolidayMasterBean bean){

		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.deleteWeekHoliDayMastetData, Arrays.asList(bean.getWeeklyHoiDayMasterId()));
							System.out.println("H I D " + preparedStatement);
							int i = preparedStatement.executeUpdate();
							if(i>0){
								Messagebox.show("Deleted SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
							}
							
							 
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
				
			}
			} catch (Exception e) {

				
				Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int saveGeneralHoliDayMasterData(Date date, String holiDayName, String userName, int leaveYrId, int companyId, int unitId){

		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.saveGeneralHoliDayMasterData, Arrays.asList(date, holiDayName, userName, userName, leaveYrId,companyId, unitId ));
							int i = preparedStatement.executeUpdate();
							if(i>0){
								Messagebox.show("Saved SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
							}
							
							 
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
				
			}
			} catch (Exception e) {

				System.out.println(">>> >> > " +e.getMessage());
				Messagebox.show("Already Exist","ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	
	public static ArrayList<HolidayMasterGeneralHolidayBean> loadGenerealHoliDayMasterData(){

		ArrayList<HolidayMasterGeneralHolidayBean> beanList = new ArrayList<HolidayMasterGeneralHolidayBean>();
        
		if(beanList.size()>0){
			beanList.clear();
		}
		
		int id = 0;
		Integer count = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
				  
						
						ResultSet resultSet = null;
					
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.loadGeneralHoliDayMasterData, null);
							
							resultSet = preparedStatement.executeQuery();
							while (resultSet.next()) {
								HolidayMasterGeneralHolidayBean bean = new HolidayMasterGeneralHolidayBean();
								count = count+1;
								bean.setGeneralCount(count);
								bean.setGeneralHolidayNameId(resultSet.getInt("id"));
								bean.setGeneralHolidayDate(resultSet.getDate("date"));
								bean.setGeneralHolidayDateValue(resultSet.getString("date"));
								bean.generalHolidayString = DateFormatter.toStringDate(bean.getGeneralHolidayDateValue());
								bean.setGeneralHolidayName(resultSet.getString("holiday_name"));
								bean.setLeaveYearId(resultSet.getInt("leave_year_id"));
								//System.out.println("G H N I >>> >> > " + bean.getGeneralHolidayNameId());
								beanList.add(bean);
								
							}  
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
					
			}
			} catch (Exception e) {

				
				Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanList;
	
	}
	
	public static void deleteGeneralHoliDayMasterData(HolidayMasterGeneralHolidayBean bean){

		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, HolidayMasterSql.deleteGeneralHoliDayMasterData, Arrays.asList(bean.getGeneralHolidayNameId()));
							System.out.println("prepared delete" + preparedStatement);
							int i = preparedStatement.executeUpdate();
							if(i>0){
								Messagebox.show("Deleted SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
							}
							
							 
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
				
			}
			} catch (Exception e) {

				Messagebox.show("Error due to:"+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
				e.printStackTrace();
			}finally{
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
