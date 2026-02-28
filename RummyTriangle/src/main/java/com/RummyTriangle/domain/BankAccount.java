package com.RummyTriangle.domain;

public class BankAccount {
	
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIFSCode() {
		return IFSCode;
	}
	public void setIFSCode(String iFSCode) {
		IFSCode = iFSCode;
	}
	public String getMICRCode() {
		return MICRCode;
	}
	public void setMICRCode(String mICRCode) {
		MICRCode = mICRCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	String accountHolderName;
	String accountNumber;
	String IFSCode;
	String MICRCode;
	String bankName;
	String bankBranchName;

}
