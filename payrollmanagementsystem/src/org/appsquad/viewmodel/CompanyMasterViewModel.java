package org.appsquad.viewmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.bean.StateMasterBean;
import org.appsquad.database.DbConnection;
import org.appsquad.service.CompanyService;
import org.appsquad.sql.SqlQuery;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import utility.Util1;

public class CompanyMasterViewModel {
	
	CompanyMasterBean companyMasterBean = new CompanyMasterBean();
	
	StateMasterBean stateMasterBean = new StateMasterBean();
	
	ArrayList<CompanyMasterBean> companyBeanList = new ArrayList<CompanyMasterBean>();
	
	ArrayList<StateMasterBean> stateBeanList = new ArrayList<StateMasterBean>();
	
	Session session = null;

	private String userId;

	private Connection connection = null;
	
	@AfterCompose
	public void initSetUp(@ContextParam(ContextType.VIEW) Component view)
			throws Exception {

		Selectors.wireComponents(view, this, false);
	
		session = Sessions.getCurrent();
		
		userId = (String) session.getAttribute("userId");
		
		fetchStateNameList();
		
		loadSavedCompanyList();
	}
	
	public void fetchStateNameList(){
		if(stateBeanList.size()>0){
			stateBeanList.clear();
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
					StateMasterBean bean = new StateMasterBean();
					bean.setStateName(resultSet.getString("state_name"));
					bean.setStateId(resultSet.getInt("id"));
					System.out.println(bean.getStateId());
					stateBeanList.add(bean);
					
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(preparedStatement !=null){
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
	
	public void loadSavedCompanyList(){
		companyBeanList = CompanyService.loadCompanyList();
	}
	
	
	@Command
	@NotifyChange("*")
	public void onSelectStateName(){
		System.out.println("stateName selected");
		System.out.println("State Name " + stateMasterBean.getStateName());
		System.out.println("state Id " + stateMasterBean.getStateId());
		companyMasterBean.setCompanyStateId(stateMasterBean.getStateId());
		
	}
	
	@Command
	@NotifyChange("*")
	public void onclickSave(){
		
		int i = 0;
		Integer maxcompanyId = 0;
		if(fieldValidation()){
		
		int contactinfoUpdate,pfinfoUpdate,esiinfoUpdate,ptinfoUpdate,itinfoUpdate;
		try {
			connection = DbConnection.createConnection();
			connection.setAutoCommit(false);  
			sqlcompanyinfo1:{
				PreparedStatement preparedStatement = null;
				PreparedStatement preparedStatement2 = null;
				
				   try {
					if(companyMasterBean.getCompanyName() != null){
					preparedStatement = Util1.createQuery(connection, SqlQuery.insertCompanyMasterQuery, Arrays.asList(companyMasterBean.getCompanyName(), 
															companyMasterBean.getAddress(), companyMasterBean.getCity(), 
															companyMasterBean.getPincode(),stateMasterBean.getStateId(),
															companyMasterBean.getCompanyEmail(), companyMasterBean.getPhoneNumber(), 
															companyMasterBean.getWebsite(), userId, userId ));
					
					i = preparedStatement.executeUpdate();
					if(i>0){
						
						try {
							
							preparedStatement2 = Util1.createQuery(connection, SqlQuery.getIdCompanyMasterQuery, null);
							ResultSet resultSet = preparedStatement2.executeQuery();
							if(resultSet.next()){
								maxcompanyId = resultSet.getInt("id");
								
							}
						} finally {
							if(preparedStatement2 != null){
								preparedStatement.close();
							}
						}
					}
					}else {
						Messagebox.show("Select all details");
					}
				} finally {
					if(preparedStatement != null){
						preparedStatement.close();
					}
					
				}
				
				
			 }
			
			sqlcontactpersoninfo2:{
				 PreparedStatement preparedStatement = null;
				 try {
					 preparedStatement = Util1.createQuery(connection, SqlQuery.insertCompanyContactInfo, 
							 			Arrays.asList(companyMasterBean.getContactPerson(),
							 			companyMasterBean.getContactPersonEmail(),
							 			companyMasterBean.getContactPersonPhone(),
							 			maxcompanyId, userId, userId));
					 
					 contactinfoUpdate  = preparedStatement.executeUpdate();
					 
					
				} finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
				 
				 
			 }
			
			sqlpfinfo3:{
			     PreparedStatement preparedStatement = null;
			     try {
					preparedStatement = Util1.createQuery(connection, SqlQuery.insertPfInfo, Arrays.asList(companyMasterBean.getCompanyPfNumber(), companyMasterBean.getPfRegistrationDate(), companyMasterBean.getPfSignatoryName(), 
									    maxcompanyId, userId, userId));
					pfinfoUpdate  = preparedStatement.executeUpdate(); 
			    	 
				}finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			 }
			
			 sqlesiinfo4:{
			     PreparedStatement preparedStatement = null;
			     try {
					preparedStatement = Util1.createQuery(connection, SqlQuery.insertEsiInfo, Arrays.asList(companyMasterBean.getCompanyEsiNumber(), companyMasterBean.getEsiRegistrationDate(), companyMasterBean.getEsiSignatoryName(), 
									    maxcompanyId, userId, userId));
					 esiinfoUpdate  = preparedStatement.executeUpdate(); 
			    	 
				}finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			 }
			
			 sqlptinfo5:{
			     PreparedStatement preparedStatement = null;
			     try {
					preparedStatement = Util1.createQuery(connection, SqlQuery.insertPtInfo, Arrays.asList(companyMasterBean.getCompanyPtNumber(), companyMasterBean.getPtRegistrationDate(), companyMasterBean.getPtSignatoryName(), 
									    maxcompanyId, userId, userId));
					 ptinfoUpdate  = preparedStatement.executeUpdate(); 
			    	 
				}finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			 } 
			 
			 sqlitinfo6:{
			     PreparedStatement preparedStatement = null;
			     try {
					preparedStatement = Util1.createQuery(connection, SqlQuery.insertItInfo, Arrays.asList(companyMasterBean.getPanNumber(), companyMasterBean.getTan(), companyMasterBean.getTanCircle(), 
									    maxcompanyId, userId, userId));
				 itinfoUpdate  = preparedStatement.executeUpdate(); 
			    	 
				}finally{
					if(preparedStatement != null){
						preparedStatement.close();
					}
				}
			 }
			connection.commit();
			if(itinfoUpdate>0 && contactinfoUpdate>0 && pfinfoUpdate>0 && esiinfoUpdate > 0 && ptinfoUpdate>0 && i>0){
				Messagebox.show("Saved Succesfully", "Information", Messagebox.OK, Messagebox.INFORMATION);
			}
			
			
		} catch (Exception e) {
			
			System.out.println(">>> >> > ");
			Messagebox.show("Already Exist","ERROR",Messagebox.OK,Messagebox.ERROR);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					System.out.println("--------------- ");
					Messagebox.show("Already Exist","ERROR",Messagebox.OK,Messagebox.ERROR);
					System.out.println("GET MESSAGE 2 " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	  }
		
	}
	
	
	@Command
	@NotifyChange("*")
	public void onClickEdit(@BindingParam("bean")CompanyMasterBean bean){
		System.out.println(bean.getCompanyId());
		Map<String, Integer> parentMap = new HashMap<String, Integer>();
		parentMap.put("parentBean", bean.getCompanyId());
		Window window = (Window) Executions.createComponents("/WEB-INF/view/companyedit.zul", null, parentMap);
		window.doModal();
	}
	
	
	public boolean fieldValidation(){
		if(companyMasterBean.getCompanyName() != null){
			
			if(companyMasterBean.getAddress() != null){
				
				if (companyMasterBean.getCity() != null) {
					
					if(companyMasterBean.getPincode() !=null){
						
						if(companyMasterBean.getStateName() != null){
							
							if(companyMasterBean.getPhoneNumber() !=null){
								
								if(companyMasterBean.getWebsite() != null){
									
									if(companyMasterBean.getCompanyEmail() != null){
										
										if(companyMasterBean.getContactPerson() != null){
											
											if(companyMasterBean.getContactPersonEmail() != null){
												
												if(companyMasterBean.getContactPersonPhone() != null){
													
													if(companyMasterBean.getCompanyPfNumber() != null){
														
														if(companyMasterBean.getPfRegistrationDate() != null){
															
															if(companyMasterBean.getPfSignatoryName() != null){
																
																if(companyMasterBean.getCompanyEsiNumber() != null){
																	
																	if(companyMasterBean.getCompanyEsiNumber() != null){
																		
																		if(companyMasterBean.getEsiRegistrationDate() != null){
																			
																			if(companyMasterBean.getEsiSignatoryName() != null){
																				
																				if(companyMasterBean.getCompanyPtNumber() != null){
																					
																					if(companyMasterBean.getPtRegistrationDate() != null){
																						
																						if(companyMasterBean.getPtSignatoryName() != null){
																							
																							if(companyMasterBean.getPanNumber() != null){
																								
																								if(companyMasterBean.getTan() != null){
																									
																									if(companyMasterBean.getTanCircle() != null){
																										
																										return true;
																										
																									}else {
																										Messagebox.show("Enter TAN Circle ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																										return false;
																									}
																									
																								}else {
																									Messagebox.show("Enter TAN ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																									return false;
																								}
																								
																							}else {
																								Messagebox.show("Enter Company Company PAN ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																								return false;
																							}
																							
																						}else {
																							Messagebox.show("Enter PT Signatory Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																							return false;
																						}
																						
																					}else {
																						Messagebox.show("Enter PT Registration Date ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																						return false;
																					}
																					
																				}else {
																					Messagebox.show("Enter Company PT Number ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																					return false;
																				}
																				
																			}else {
																				Messagebox.show("Enter ESI Signatory Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																				return false;
																			}
																				
																			
																		}else {
																			Messagebox.show("Enter ESI registration Date ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																			return false;
																		}
																		
																	}else {
																		Messagebox.show("Enter Company ESI Number ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																		return false;
																	}
																	
																}else {
																	Messagebox.show("Enter Company ESI Number ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																	return false;
																}
																
															}else {
																Messagebox.show("Enter Pf Signatory Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
																return false;
															}
															
															
														}else {
															Messagebox.show("Enter Pf Registration Date ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
															return false;
														}
														
													}else {
														Messagebox.show("Enter Company PF Number ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
														return false;
													}
													
												}else {
													Messagebox.show("Enter Contact Person Phone Number ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
													return false;
												}
												
											}else {
												Messagebox.show("Enter Contact Person Email ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
												return false;
											}
											
										}else {
											Messagebox.show("Enter Contact Person Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
											return false;
										}
										
									}else {
										Messagebox.show("Enter Email Address ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
										return false;
									}
									
									
								}else {
									Messagebox.show("Enter Company Website ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
									return false;
								}
								
							}else {
								Messagebox.show("Enter Company Phone Number ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
								return false;
							}
							
						}else {
							Messagebox.show("Select State ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
							return false;
						}
						
					}else {
						Messagebox.show("Enter Pincode ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
						return false;
					}
					
					
				}else {
					Messagebox.show("Enter City ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}
				
			}else {
				Messagebox.show("Enter Company Address ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}else {
			
			Messagebox.show("Enter Company Name ", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
	}
	

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CompanyMasterBean getCompanyMasterBean() {
		return companyMasterBean;
	}

	public void setCompanyMasterBean(CompanyMasterBean companyMasterBean) {
		this.companyMasterBean = companyMasterBean;
	}

	public ArrayList<StateMasterBean> getStateBeanList() {
		return stateBeanList;
	}

	public void setStateBeanList(ArrayList<StateMasterBean> stateBeanList) {
		this.stateBeanList = stateBeanList;
	}

	public StateMasterBean getStateMasterBean() {
		return stateMasterBean;
	}

	public void setStateMasterBean(StateMasterBean stateMasterBean) {
		this.stateMasterBean = stateMasterBean;
	}

	public ArrayList<CompanyMasterBean> getCompanyBeanList() {
		return companyBeanList;
	}

	public void setCompanyBeanList(ArrayList<CompanyMasterBean> companyBeanList) {
		this.companyBeanList = companyBeanList;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	

	
	

}
