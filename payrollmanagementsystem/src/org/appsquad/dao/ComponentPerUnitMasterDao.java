package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.ComponentMasterBean;
import org.appsquad.bean.ComponentPerUnitMasterBean;
import org.appsquad.bean.HolidayMasterBean;
import org.appsquad.bean.UnitMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.ComponentMasterSql;
import org.appsquad.sql.ComponentPerUnitMasterSql;
import org.appsquad.sql.EmployeeMasterSql;
import org.appsquad.sql.HolidayMasterSql;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class ComponentPerUnitMasterDao {
	
public static void onloadComponentDetails(ArrayList<ComponentMasterBean> beanList){
		
		if(beanList.size()>0){
			beanList.clear();
		}
		
		try {

			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, ComponentMasterSql.loadComponentDetailsQuery, null);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								ComponentMasterBean bean = new ComponentMasterBean();
								
								bean.setComponentName(resultSet.getString("component_name"));
								bean.setComponentType(resultSet.getString("component_type"));
								
								beanList.add(bean);
							}
						}finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show(e.getMessage());
					
					
				}finally{
					if(connection !=null){
						connection.close();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public static final ArrayList<UnitMasterBean> loadUnitList(Integer companyId){
		ArrayList<UnitMasterBean> list = new ArrayList<UnitMasterBean>();
		if(list.size()>0){
			list.clear();
		}
		try {


			Connection connection = DbConnection.createConnection();
			sql_connection:{
				
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.loadUnitQuery, Arrays.asList(companyId));
							
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								UnitMasterBean bean = new UnitMasterBean(); 
								bean.setUnitName(resultSet.getString("unit_name"));
								bean.setUnitId(resultSet.getInt("unit_id"));
								list.add(bean);
							}
						}finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(connection !=null){
						connection.close();
					}
				}
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}

	public static final ArrayList<ComponentPerUnitMasterBean> onLoadData(){
		ArrayList<ComponentPerUnitMasterBean> list = new ArrayList<ComponentPerUnitMasterBean>();
		if(list.size()>0){
			list.clear();
		}
		try {
			Connection connection = DbConnection.createConnection();
			sql_connection:{
				int count = 0;
				try {
					
					sql_block:{
					
						PreparedStatement preparedStatement = null;
						try {
						
							preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.loadComponentBeanListQuery, null);
							ResultSet resultSet = preparedStatement.executeQuery();
							
							while (resultSet.next()) {
								count = count+1;
								
								ComponentPerUnitMasterBean bean = new ComponentPerUnitMasterBean();
								bean.setCount(count);
								bean.setComponet(resultSet.getString("component_name"));
								bean.setComponentId(resultSet.getInt("component_id"));
								bean.setComponentType(resultSet.getString("component_type"));
								bean.setComponentTypeId(resultSet.getInt("component_type_id"));
								bean.setCheckVal(false);
								
								list.add(bean);
							}
						}finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(connection !=null){
						connection.close();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void insertComponentPerUnit(ArrayList<ComponentPerUnitMasterBean> list, Integer companyId, Integer unitId, String userName, int designationId){
		boolean falg = false;
		int c = 0;
		
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
					PreparedStatement preparedStatement = null;
					
					try {
						
						for(ComponentPerUnitMasterBean bean : list){
						
						if(bean.isCheckVal()==true){
						
						preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.insertComponentPerUnitQuery, Arrays.asList(bean.getComponentId(), bean.getComponet(),bean.getComponentTypeId(),companyId,unitId,userName,userName, designationId, bean.getDesCompoAmount()));
						System.out.println("Batch Query >>> >> > " + preparedStatement);
						//preparedStatement.addBatch(); 
						c = preparedStatement.executeUpdate();
						
						}
						
					}
					
					int count[] = preparedStatement.executeBatch();
					int insertCount = 0;
					for(int i : count){
						insertCount +=i;
					}
		
					if(c>0){
						Messagebox.show("Saved successfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
					}else {
						Messagebox.show("Data Not Saved", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
					}
					
					
					} finally{
						if(preparedStatement != null){
							preparedStatement.close();
						}
					}
				
			    }
			} catch (Exception e) {
				if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void saveHourPerDay(int companyId, int unitId ,int designationId, double workingHour, String userName){
		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
						try {
							preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.saveWorkingHour, 
									                Arrays.asList(companyId, unitId, workingHour, userName, userName, designationId));
							int i = preparedStatement.executeUpdate();
							if(i>0){
								//Messagebox.show("Saved SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
								
							}
							
							 
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
				
			}
			} catch (Exception e) {
				
				Messagebox.show("Already Exists","ERROR",Messagebox.OK,Messagebox.ERROR);
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
	
	public static void saveBaseDayPerUnit(int companyId, int unitId ,int baseDays, double workingHour, String userName){
		int id = 0;
		try {
			Connection connection = DbConnection.createConnection();
			try {
				sql:{
				   PreparedStatement preparedStatement = null;
				   
						try {
							preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.saveBaseDays, 
									                Arrays.asList(companyId, unitId, workingHour, userName, userName, baseDays));
							int i = preparedStatement.executeUpdate();
							if(i>0){
								//Messagebox.show("Saved SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
								
							}
							
							 
						} finally{
							if(preparedStatement != null){
								preparedStatement.close();
							}
						}
				
			}
			} catch (Exception e) {
				
				Messagebox.show("Already Exists","ERROR",Messagebox.OK,Messagebox.ERROR);
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

	public static void insertComponentPerUnit2(ArrayList<ComponentPerUnitMasterBean> list, ComponentPerUnitMasterBean bean){
		
			int i =0;
			int j = 0;
		
			Connection connection = DbConnection.createConnection();
			
		try {
			connection.setAutoCommit(false);
			
			
			sql_component:{

				PreparedStatement preparedStatement = null;
				
				try {
					
					for(ComponentPerUnitMasterBean bean1 : list){
					
					if(bean1.isCheckVal()){									
					
					preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.insertComponentPerUnitQuery, 
							Arrays.asList(bean1.getComponentId(), bean1.getComponet(),bean1.getComponentTypeId(),
									bean.getCompanyId(),bean.getUnitId(),bean.getUserName(),bean.getUserName(), 
									bean.getUnitDesignationId(), bean1.getDesCompoAmount()));
					
					
					
					i = preparedStatement.executeUpdate();
					
					}
					
				}

			
		} finally {
			if(preparedStatement != null){
				preparedStatement.close();
			}
		}
		
		
	}
	
		sql2:{
		/*PreparedStatement preparedStatement = null;
			   try {
				
				preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.saveWorkingHour,
						Arrays.asList(bean.getCompanyId(), bean.getUnitId(), bean.getWorkinghour(), bean.getUserName(),bean.getUserName(), 
								bean.getUnitDesignationId(), bean.getBaseDays()));
				j = preparedStatement.executeUpdate();
				
				
			} finally{
				   if(preparedStatement !=null){
					   	preparedStatement.close();
				 }
			}*/
		}
			
		connection.commit();
		if(i>0){
			Messagebox.show("Saved SuccessFully", "Information", Messagebox.OK, Messagebox.INFORMATION);
		}
		
		} catch (Exception e) {
			if(e.getMessage().contains("duplicate")){
			Messagebox.show("Already Exists","ERROR",Messagebox.OK,Messagebox.ERROR);
			}
			e.printStackTrace();
			
		}finally{
			if(connection != null){
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					
					
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static ArrayList<ComponentPerUnitMasterBean> onSelectExistingDesignation(int companyId, int unitId, int DesId){
		ArrayList<ComponentPerUnitMasterBean> list = new ArrayList<ComponentPerUnitMasterBean>();
		if(list.size()>0){
			list.clear();
		}
		
		int count = 0;
		try {
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			
			try {
				preparedStatement = Util1.createQuery(connection, ComponentPerUnitMasterSql.ldExistingComponent, Arrays.asList(companyId, unitId, DesId));
				
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					count = count+1;
					
					ComponentPerUnitMasterBean bean = new ComponentPerUnitMasterBean();
					
					bean.setCount(count);
					bean.setId(resultSet.getInt("id"));
					
					bean.setComponet(resultSet.getString("component_name"));
					bean.setComponentId(resultSet.getInt("component_id"));
					
					bean.setComponentType(resultSet.getString("component_type"));
					bean.setComponentTypeId(resultSet.getInt("component_type_id"));
					
					bean.setDesCompoAmount(resultSet.getDouble("amount"));
					
					bean.setCheckVal(false);
					
					
					System.out.println("ID -> " + bean.getId() + " -- COMPONENT NAME -- " + bean.getComponet() + " -- AMOUNT -> " + bean.getDesCompoAmount());
					
					
					list.add(bean);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(connection != null){
					connection.close();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public static void upDateExistingComponent(ArrayList<ComponentPerUnitMasterBean> list){
		
		try {
			Connection connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			
			try {
				preparedStatement = connection.prepareStatement(ComponentPerUnitMasterSql.upDateExistingAmountQuery);
				
				for(ComponentPerUnitMasterBean bean : list){
					if(bean.isCheckVal()){
						
						preparedStatement.setDouble(1, bean.getDesCompoAmount());
						preparedStatement.setInt(2, bean.getId());
						preparedStatement.addBatch();
					}
					
				}
				
				int[] i = preparedStatement.executeBatch();
				int count = 0;
				for(int j : i){
					count = count+1;
					
				}
				if(count>0){
					Messagebox.show("Updated SuccessFully", "INFORMATION", Messagebox.OK,Messagebox.INFORMATION);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(preparedStatement != null){
					preparedStatement.close();
				}if(connection != null){
					connection.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
		
		
	}
