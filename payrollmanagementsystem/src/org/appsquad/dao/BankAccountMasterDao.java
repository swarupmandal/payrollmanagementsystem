package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


import org.appsquad.bean.BankAccountMasterBean;

import org.appsquad.database.DbConnection;
import org.appsquad.sql.BankAccountMasterSql;

import org.zkoss.zul.Messagebox;

import utility.Util1;

public class BankAccountMasterDao {
	
	public static void insertBankData(BankAccountMasterBean bean){
		boolean isInserted = false;
		Connection connection = DbConnection.createConnection();
		try {
			sql:{
				
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = Util1.createQuery(connection, 
							BankAccountMasterSql.saveBankMasterQuery, Arrays.asList(bean.getBankName().toUpperCase(), bean.getUserName(), bean.getUserName()) );
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
		if( isInserted){
			Messagebox.show("Bank Name Saved successfully!","Information",Messagebox.OK,Messagebox.INFORMATION);
		}else{
			Messagebox.show("Bank Name Saving failed due to internal error!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	
	public static ArrayList<BankAccountMasterBean> onLoad(){
		ArrayList<BankAccountMasterBean> beanList = new ArrayList<BankAccountMasterBean>();
		if(beanList.size()>0){
			beanList.clear();
		}
		Connection connection =null;
		try {		
			  sql:{	
				connection = DbConnection.createConnection();
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
			
				try {
					preparedStatement = Util1.createQuery(connection, BankAccountMasterSql.loadBankMasterData, null);
					
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						BankAccountMasterBean bean = new BankAccountMasterBean();
						bean.setBankName(resultSet.getString("bank_name").toUpperCase());
						bean.setBankId(resultSet.getInt("id"));
						beanList.add(bean);
						
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
		return beanList;
	}
	
	



}
