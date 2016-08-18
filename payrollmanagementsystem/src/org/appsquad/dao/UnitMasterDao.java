package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.UnitMasterSqlQuery;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class UnitMasterDao {

	public static void onLoad(ArrayList<UnitMasterBean> unitMasterBeanList ){
		if(unitMasterBeanList.size()>0){
			unitMasterBeanList.clear();
		}
		Connection connection =null;
		try {		
			  sql:{	
				connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
			
				try {
					preparedStatement = Util1.createQuery(connection, UnitMasterSqlQuery.onLoadUnitMasterQuery, null);
					
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						int unitId = resultSet.getInt("unit_id");
						int companyId = resultSet.getInt("company_id");
						String unitName = resultSet.getString("unit_name");
						String company_Name = resultSet.getString("company_name");
						unitMasterBeanList.add(new UnitMasterBean(unitId, companyId, unitName, company_Name));
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
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
	
	public static ArrayList<UnitMasterBean> loadDayType(){
		ArrayList<UnitMasterBean> dayTypeList = new ArrayList<UnitMasterBean>();
		if(dayTypeList.size()>0){
			dayTypeList.clear();
		}
			Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, UnitMasterSqlQuery.loadDayTypeQuery, null);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					UnitMasterBean bean = new UnitMasterBean();
					bean.setBaseDaysTypeId(resultSet.getInt("id"));
					bean.setBaseDaysType(resultSet.getString("days_type"));
					
					dayTypeList.add(bean);
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		return dayTypeList;
	}
	
	
	public static void insertUnitMasterData(UnitMasterBean unitMasterBean, int bdid, int wgsId, int sunId){
		
		boolean isInserted = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							UnitMasterSqlQuery.insertUnitMasterQuery, Arrays.asList(unitMasterBean.getUnitName().toUpperCase(),unitMasterBean.getUnitAddress(),
									unitMasterBean.getCompanyId(),unitMasterBean.getUserName(), bdid, unitMasterBean.getWorkingHour(), wgsId, sunId));
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isInserted = true;	
					}					
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("Already Exsist","ERROR",Messagebox.OK,Messagebox.ERROR );
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		if( isInserted){
			Messagebox.show("Saved successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Saving failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public static void updateUnitMasterData(UnitMasterBean unitMasterBean){
		boolean isUpdated = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							UnitMasterSqlQuery.updateUnitMasterQuery, Arrays.asList(unitMasterBean.getUnitName(),unitMasterBean.getUnitAddress(),
									unitMasterBean.getUserName(),unitMasterBean.getUnitId()) );
					
					
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isUpdated = true;
						
					}					
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if( isUpdated){
			Messagebox.show("Updated successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Updation failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public static void deleteUnitMasterData(UnitMasterBean unitMasterBean){
		boolean isDeleted = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							UnitMasterSqlQuery.deleteUnitMasterQuery, Arrays.asList(unitMasterBean.getUserName(),unitMasterBean.getUnitId() ));
					int i = preparedStatement.executeUpdate();
					if(i>0){
						isDeleted = true;	
					}					
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if( isDeleted){
			Messagebox.show("Deleted successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Deletion failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	public static ArrayList<UnitMasterBean> fetchWagesType(){
		ArrayList<UnitMasterBean> list = new ArrayList<UnitMasterBean>();
		if(list.size()>0){
			list.clear();
		}
		Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, UnitMasterSqlQuery.loadWagesTypeQuery, null);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					UnitMasterBean ub = new UnitMasterBean();
					ub.setWagesType(resultSet.getString("wages_type"));
					ub.setWagesTypeId(resultSet.getInt("id"));
					
					list.add(ub);
					
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
		
	}
	
	public static ArrayList<UnitMasterBean> fetchSundaySelect(){
		ArrayList<UnitMasterBean> list = new ArrayList<UnitMasterBean>();
		if(list.size()>0){
			list.clear();
		}
		Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = Util1.createQuery(connection, UnitMasterSqlQuery.loadSundaySelectionQuery, null);
				
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					UnitMasterBean ub = new UnitMasterBean();
					ub.setSundaySelection(resultSet.getString("sunday_select"));
					ub.setSundaySelectionId(resultSet.getInt("id"));
					
					list.add(ub);
					
				}
				
			} finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	
}
