package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.EsiReportBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.EsiReportSql;

import utility.Util1;

public class EsiReportDao {

	public static ArrayList<String> getLvYrList(){
		ArrayList<String> lvYrList = new ArrayList<String>(); 
		if(lvYrList.size()>0){
			lvYrList.clear();
		}
		Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = Util1.createQuery(connection, EsiReportSql.selectLvYrQuery, null);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					lvYrList.add(resultSet.getString("leave_yr"));
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
		
		return lvYrList;
	}
	
	public static ArrayList<EsiReportBean> getUnitDesignationList(EsiReportBean bean){
		
		ArrayList<EsiReportBean> arrayList = new ArrayList<EsiReportBean>();
		
		if(arrayList.size()>0){
			arrayList.clear();
		}
		Connection connection = null;
		try {
		connection = DbConnection.createConnection();	
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = Util1.createQuery(connection, EsiReportSql.loadUnitDesignationQuery, Arrays.asList(bean.getCompanyId(), bean.getUnitId()));
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				EsiReportBean bean2 = new EsiReportBean();
				bean2.setUnitDesignationId(resultSet.getInt("id"));
				bean2.setUnitDesignation(resultSet.getString("designation"));
				
				arrayList.add(bean2);
			}
			
		} finally {
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
		
		return arrayList;
	}
	
	public static ArrayList<EsiReportBean> getEsiReport(EsiReportBean prmBean){
		
		
		
		ArrayList<EsiReportBean> list = new ArrayList<EsiReportBean>();
		if(list.size()>0){
			list.clear();
		}
		
		Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement= null;
			ResultSet resultSet = null;
			try {
				
			preparedStatement = Util1.createQuery(connection, EsiReportSql.loadEsiReportQuery, Arrays.asList(prmBean.getSalaryMonth(), prmBean.getLvYr(),
																				prmBean.getCompanyId(), prmBean.getUnitId(), prmBean.getUnitDesignationId()));
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				EsiReportBean bean = new EsiReportBean();
				bean.setIpNumber(resultSet.getString("esi_num"));
				bean.setEmpName(resultSet.getString("emp_name"));
				bean.setPreDays(resultSet.getDouble("presenet_days"));
				bean.setWages(resultSet.getDouble("wages"));
				bean.setEsiAmount(resultSet.getDouble("amount"));
				
				list.add(bean);
			}
			
			
			}finally{
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
