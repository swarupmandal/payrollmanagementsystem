package org.appsquad.service;

import java.util.ArrayList;

import org.appsquad.bean.CompanyMasterBean;
import org.appsquad.dao.CompanyDao;
import org.zkoss.zul.Messagebox;

public class CompanyService {

	public static ArrayList<CompanyMasterBean> loadCompanyList(){
		return CompanyDao.loadCompanyList();
	}
	
	public static CompanyMasterBean loadCompany(int companyId){
		return CompanyDao.editCompany(companyId);
	}
	
	public static void updateCompanyDetails(CompanyMasterBean companyMasterBean){
		if(fieldValidation(companyMasterBean))
			CompanyDao.updateCompanyInfo(companyMasterBean);
	}
	
	public static boolean fieldValidation(CompanyMasterBean companyMasterBean){
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
}
