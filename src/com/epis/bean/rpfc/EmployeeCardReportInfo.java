package com.epis.bean.rpfc;

import java.io.Serializable;
import java.util.ArrayList;



public class EmployeeCardReportInfo implements Serializable{
	EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
	ArrayList pensionCardList=new ArrayList();
    ArrayList ptwList=new ArrayList();
    ArrayList finalSettmentList=new ArrayList();
    String arrearInfo="",orderInfo="";
    int interNoOfMonths=0,noOfMonths=0,arrearNoOfMonths=0;
    public String getArrearInfo() {
		return arrearInfo;
	}
	public void setArrearInfo(String arrearInfo) {
		this.arrearInfo = arrearInfo;
	}
	public int getArrearNoOfMonths() {
		return arrearNoOfMonths;
	}
	public void setArrearNoOfMonths(int arrearNoOfMonths) {
		this.arrearNoOfMonths = arrearNoOfMonths;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public int getInterNoOfMonths() {
		return interNoOfMonths;
	}
	public void setInterNoOfMonths(int interNoOfMonths) {
		this.interNoOfMonths = interNoOfMonths;
	}
	public int getNoOfMonths() {
		return noOfMonths;
	}
	public void setNoOfMonths(int noOfMonths) {
		this.noOfMonths = noOfMonths;
	}
	
	public ArrayList getFinalSettmentList() {
		return finalSettmentList;
	}
	public void setFinalSettmentList(ArrayList finalSettmentList) {
		this.finalSettmentList = finalSettmentList;
	}
	public ArrayList getPtwList() {
        return ptwList;
    }
    public void setPtwList(ArrayList ptwList) {
        this.ptwList = ptwList;
    }
    public ArrayList getPensionCardList() {
		return pensionCardList;
	}
	public void setPensionCardList(ArrayList pensionCardList) {
		this.pensionCardList = pensionCardList;
	}
	public EmployeePersonalInfo getPersonalInfo() {
		return personalInfo;
	}
	public void setPersonalInfo(EmployeePersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}

}
