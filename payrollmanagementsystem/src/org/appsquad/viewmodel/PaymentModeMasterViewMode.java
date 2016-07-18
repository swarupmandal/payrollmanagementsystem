package org.appsquad.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.appsquad.bean.PaymentModeMasterBean;
import org.appsquad.bean.StateMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.sql.SqlQuery;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

import utility.Util1;

public class PaymentModeMasterViewMode {

	PaymentModeMasterBean paymentModeMasterBean = new PaymentModeMasterBean();
	
	ArrayList<PaymentModeMasterBean> paymentmodeBeanList = new ArrayList<PaymentModeMasterBean>();
	
private Connection connection = null;
	
	Session session = null;

	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view) throws Exception{

		Selectors.wireComponents(view, this, false);
		
		session = Sessions.getCurrent();
		
		onLoad();
		
	}
	
	public void onLoad(){

		if(paymentmodeBeanList.size()>0){
			paymentmodeBeanList.clear();
		}
		
		try {
			
		  sql:{	
			connection = DbConnection.createConnection();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
		
			try {
				preparedStatement = Util1.createQuery(connection, SqlQuery.onLoadStateQuery, null);
				
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					PaymentModeMasterBean bean = new PaymentModeMasterBean();
					bean.setPaymentModeId(resultSet.getInt("id"));
					bean.setPaymentMode(resultSet.getString("payment_mode"));
					
					paymentmodeBeanList.add(bean);
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
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	@Command
	@NotifyChange("*")
	public void onClickSave(){
		
		if(fieldValidation()){
			
			if(paymentModeMasterBean.getPaymentModeId()>0){
				
				
				
				
			}else {

				
				try {
					
					sql:{
					connection = DbConnection.createConnection();
					PreparedStatement preparedStatement = null;
					try {
						preparedStatement = Util1.createQuery(connection, SqlQuery.insertPaymentQuery, Arrays.asList(paymentModeMasterBean.getPaymentMode()));
						int i = preparedStatement.executeUpdate();
						if(i>0){
							Messagebox.show("saved successfully");
							paymentModeMasterBean.setPaymentMode(null);
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
					
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		
		
		
	}
	
	public boolean fieldValidation(){
		if(paymentModeMasterBean.getPaymentMode()!=null){
			return true;
		}else {
			Messagebox.show("Enter payment mode");
			return false;
		}
	}
	
	
	
	
	
	public ArrayList<PaymentModeMasterBean> getPaymentmodeBeanList() {
		return paymentmodeBeanList;
	}

	public void setPaymentmodeBeanList(
			ArrayList<PaymentModeMasterBean> paymentmodeBeanList) {
		this.paymentmodeBeanList = paymentmodeBeanList;
	}

	public PaymentModeMasterBean getPaymentModeMasterBean() {
		return paymentModeMasterBean;
	}

	public void setPaymentModeMasterBean(PaymentModeMasterBean paymentModeMasterBean) {
		this.paymentModeMasterBean = paymentModeMasterBean;
	}
	
}
