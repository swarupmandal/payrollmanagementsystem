package org.appsquad.bean;

public class PaymentModeMasterBean {

private String paymentMode;
	
	private int paymentModeId;
	
	private boolean saveButtonDesabled = false;
	
	private boolean updtateButtonDesabled = true;
	
	private String isExist;

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public int getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public boolean isSaveButtonDesabled() {
		return saveButtonDesabled;
	}

	public void setSaveButtonDesabled(boolean saveButtonDesabled) {
		this.saveButtonDesabled = saveButtonDesabled;
	}

	public boolean isUpdtateButtonDesabled() {
		return updtateButtonDesabled;
	}

	public void setUpdtateButtonDesabled(boolean updtateButtonDesabled) {
		this.updtateButtonDesabled = updtateButtonDesabled;
	}

	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}
	
}
