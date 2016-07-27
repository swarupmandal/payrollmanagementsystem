package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.*;
import org.zkoss.zhtml.Messagebox;

import utility.Util1;

public class CompanyDao {

	public static ArrayList<CompanyMasterBean> loadCompanyList(){
		ArrayList<CompanyMasterBean> companyMasterBeanList = new ArrayList<CompanyMasterBean>();
		Connection connection = DbConnection.createConnection();
		try {
			SQL:{
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
					    preparedStatement =	Util1.createQuery(connection, CompanyMasterSqlQuery.loadCompanyListQuery, null);
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							int companyId = resultSet.getInt("company_id");
							String companyName = resultSet.getString("company_name");
							companyMasterBeanList.add(new CompanyMasterBean(companyName, companyId));
						}
					} finally {
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
		return companyMasterBeanList;
	}
	
	
	public static CompanyMasterBean editCompany(int companyId){
		CompanyMasterBean companyMasterBean = new CompanyMasterBean();
		Connection connection = DbConnection.createConnection();
		try {
			SQL:{
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					try {
					    preparedStatement =	Util1.createQuery(connection, CompanyMasterSqlQuery.editCompanyListQuery, Arrays.asList(companyId));
						resultSet = preparedStatement.executeQuery();
						while (resultSet.next()) {
							companyMasterBean.setCompanyId(resultSet.getInt("company_id")); 
							companyMasterBean.setCompanyName(resultSet.getString("company_name")); 
							companyMasterBean.setCity(resultSet.getString("city"));
							companyMasterBean.setAddress(resultSet.getString("address")); 
							companyMasterBean.setCompanyEmail(resultSet.getString("company_email"));; 
							companyMasterBean.setStateName(resultSet.getString("state_name"));
							companyMasterBean.setWebsite(resultSet.getString("company_website"));
							companyMasterBean.setPhoneNumber(resultSet.getString("company_ph_no"));
							companyMasterBean.setPincode(resultSet.getString("pincode"));
							companyMasterBean.setContactPerson(resultSet.getString("contact_person_name"));
							companyMasterBean.setContactPersonEmail(resultSet.getString("contact_person_email"));
							companyMasterBean.setContactPersonPhone(resultSet.getString("contact_person_phone_no"));
							companyMasterBean.setCompanyPfNumber(resultSet.getString("company_pf_number"));
							companyMasterBean.setPfSignatoryName(resultSet.getString("company_pf_signatory_name"));
							companyMasterBean.setPfRegistrationDate(resultSet.getDate("company_pf_registration_date"));
							companyMasterBean.setCompanyEsiNumber(resultSet.getString("company_esi_number"));
							companyMasterBean.setEsiSignatoryName(resultSet.getString("company_esi_signatory_name"));
							companyMasterBean.setEsiRegistrationDate(resultSet.getDate("company_esi_registration_date"));
							companyMasterBean.setCompanyPtNumber(resultSet.getString("company_pt_number"));
							companyMasterBean.setPtSignatoryName(resultSet.getString("company_pt_signatory_name"));
							companyMasterBean.setPtRegistrationDate(resultSet.getDate("company_pt_registration_date"));
							companyMasterBean.setPanNumber(resultSet.getString("company_pan"));
							companyMasterBean.setTan(resultSet.getString("company_tan"));
							companyMasterBean.setTanCircle(resultSet.getString("company_tan_cercle"));
						}
					} finally {
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
		return companyMasterBean;
	}
	
	public static void updateCompanyInfo(CompanyMasterBean companyMasterBean){
		Connection connection = DbConnection.createConnection();
		boolean masterinfoUpdate=false, contactinfoUpdate = false,pfinfoUpdate = false,esiinfoUpdate = false,ptinfoUpdate = false,itinfoUpdate = false;
		try {
			SQL:{
					PreparedStatement preparedStatement = null;
					try {
						preparedStatement = Util1.createQuery(connection, SqlQuery.updateCompanyMasterQuery, 
								Arrays.asList(companyMasterBean.getCompanyName(), 
								companyMasterBean.getAddress(), companyMasterBean.getCity(), 
								companyMasterBean.getPincode(),companyMasterBean.getCompanyStateId(),
								companyMasterBean.getCompanyEmail(), companyMasterBean.getPhoneNumber(), 
								companyMasterBean.getWebsite(), companyMasterBean.getUserId(), companyMasterBean.getCompanyId() ));
						int row = preparedStatement.executeUpdate();
						if(row>0){
							masterinfoUpdate = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						//connection.rollback();
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
					}
				}
		
			if(masterinfoUpdate){
					SQL:{
						PreparedStatement preparedStatement = null;
						try {
							preparedStatement = Util1.createQuery(connection, SqlQuery.updateCompanyContactInfo, 
						 			Arrays.asList(companyMasterBean.getContactPerson(),
						 			companyMasterBean.getContactPersonEmail(),
						 			companyMasterBean.getContactPersonPhone(), companyMasterBean.getUserId(), 
						 			companyMasterBean.getCompanyId()));
							int row = preparedStatement.executeUpdate();
							if(row>0){
								contactinfoUpdate = true;
							}
						} catch (Exception e) {
							e.printStackTrace();
							//connection.rollback();
						}finally{
							if(preparedStatement!=null){
								preparedStatement.close();
							}
						}
						
					}
			}
			
			if(contactinfoUpdate){
				SQL:{
					PreparedStatement preparedStatement = null;
					try {
						preparedStatement = Util1.createQuery(connection, SqlQuery.updatePfInfo, 
								Arrays.asList(companyMasterBean.getCompanyPfNumber(), 
								companyMasterBean.getPfRegistrationDate(), companyMasterBean.getPfSignatoryName(), 
							   companyMasterBean.getUserId(), companyMasterBean.getCompanyId()));
						int row = preparedStatement.executeUpdate();
						if(row>0){
							pfinfoUpdate = true;
						}
					}catch (Exception e) {
						e.printStackTrace();
						//connection.rollback();
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
					}
				}
			}
			
			if(pfinfoUpdate){
				SQL:{
					 PreparedStatement preparedStatement = null;
					 try {	
							 preparedStatement = Util1.createQuery(connection, SqlQuery.updateEsiInfo, 
									 Arrays.asList(companyMasterBean.getCompanyEsiNumber(), 
									 companyMasterBean.getEsiRegistrationDate(), companyMasterBean.getEsiSignatoryName(), 
									 companyMasterBean.getUserId(), companyMasterBean.getCompanyId()));
							 int row = preparedStatement.executeUpdate();
							 if(row>0){
									esiinfoUpdate = true;
							 }	
						} catch (Exception e) {
							e.printStackTrace();
							//connection.rollback();
						}finally{
							if(preparedStatement!=null){
								preparedStatement.close();
							}
						}
					 
				}
			}
			
			if(esiinfoUpdate){
				SQL:{	
					PreparedStatement preparedStatement = null;
					try {
						preparedStatement = Util1.createQuery(connection, SqlQuery.updatePtInfo,
								Arrays.asList(companyMasterBean.getCompanyPtNumber(), 
								companyMasterBean.getPtRegistrationDate(), companyMasterBean.getPtSignatoryName(), 
							    companyMasterBean.getUserId(), companyMasterBean.getCompanyId()));
						 int row = preparedStatement.executeUpdate();
						 if(row>0){
								ptinfoUpdate = true;
						 }
					} catch (Exception e) {
						e.printStackTrace();
						//connection.rollback();
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
					}
					
				}
			}
			
			if(ptinfoUpdate){
				SQL:{
					PreparedStatement preparedStatement = null;
					try {
						preparedStatement = Util1.createQuery(connection, SqlQuery.updateItInfo, 
								Arrays.asList(companyMasterBean.getPanNumber(), 
								companyMasterBean.getTan(), companyMasterBean.getTanCircle(), 
							    companyMasterBean.getUserId(), companyMasterBean.getCompanyId()));
						 int row = preparedStatement.executeUpdate();
						 if(row>0){
								itinfoUpdate = true;
						 }
					} catch (Exception e) {
						e.printStackTrace();
						//connection.rollback();
					}finally{
						if(preparedStatement!=null){
							preparedStatement.close();
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			Messagebox.show("Error due to: "+e.getMessage(),"ERROR",Messagebox.OK,Messagebox.ERROR);
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(itinfoUpdate){
			Messagebox.show("Company data updated successfully!","Successful",Messagebox.OK,Messagebox.INFORMATION);
		}
	}
}
