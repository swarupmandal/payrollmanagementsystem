package org.appsquad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.EsiReportBean;
import org.appsquad.bean.PfReportBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.EsiReportSql;
import org.appsquad.sql.PfReportSql;

import utility.Util1;

public class PfReportDao {

public static ArrayList<PfReportBean> getPfReport(PfReportBean prmBean){
		
		
		
		ArrayList<PfReportBean> list = new ArrayList<PfReportBean>();
		if(list.size()>0){
			list.clear();
		}
		
		Connection connection = null;
		try {
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement= null;
			ResultSet resultSet = null;
			try {
				
			preparedStatement = Util1.createQuery(connection, PfReportSql.loadPfReportQuery, Arrays.asList(prmBean.getSalaryMonth(), prmBean.getLvYr(),
																				prmBean.getCompanyId(), prmBean.getUnitId(), prmBean.getUnitDesignationId()));
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				PfReportBean bean = new PfReportBean();
				bean.setPfNumber(resultSet.getString("pf_num"));
				bean.setEmpName(resultSet.getString("emp_name"));
				bean.setPreDays(resultSet.getDouble("presenet_days"));
				bean.setWages(resultSet.getDouble("wages"));
				bean.setPfAmount(resultSet.getDouble("amount"));
				
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

public static ArrayList<PfReportBean> getPtReport(PfReportBean prmBean){
	
	
	
	ArrayList<PfReportBean> list = new ArrayList<PfReportBean>();
	if(list.size()>0){
		list.clear();
	}
	
	Connection connection = null;
	try {
		connection = DbConnection.createConnection();
		PreparedStatement preparedStatement= null;
		ResultSet resultSet = null;
		try {
			
		preparedStatement = Util1.createQuery(connection, PfReportSql.loadPtReportQuery, Arrays.asList(prmBean.getSalaryMonth(), prmBean.getLvYr(),
																			prmBean.getCompanyId(), prmBean.getUnitId(), prmBean.getUnitDesignationId()));
		
		
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			PfReportBean bean = new PfReportBean();
			//bean.setPfNumber(resultSet.getString("pf_num"));
			bean.setEmpName(resultSet.getString("emp_name"));
			bean.setPreDays(resultSet.getDouble("presenet_days"));
			bean.setWages(resultSet.getDouble("wages"));
			bean.setPtAmount(resultSet.getDouble("amount"));
			
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
