package com.epis.bean.cashbook;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class ScheduleTypeInfo extends RequestBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1596927996620032284L;
	private String schType;
	private String description;
	private String[] accHeadAdd;
	private String[] accHeadLess;
	private String accountHeads;
	private String accHeadsAdd;
	private String accHeadsLess;
	private String type;
	private String append;
	
	public ScheduleTypeInfo(){		
	}
	
	public ScheduleTypeInfo(HttpServletRequest request){
		super(request);
	}	

	public String[] getAccHeadAdd() {
		return accHeadAdd;
	}

	public void setAccHeadAdd(String[] accHeadAdd) {
		this.accHeadAdd = accHeadAdd;
	}

	public String[] getAccHeadLess() {
		return accHeadLess;
	}

	public void setAccHeadLess(String[] accHeadLess) {
		this.accHeadLess = accHeadLess;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSchType() {
		return schType;
	}

	public void setSchType(String schType) {
		this.schType = schType;
	}
	
	public String getAccHeadsAdd() {
		StringBuffer accHeadsAdd = new StringBuffer();
		int size = accHeadAdd.length;
		for(int i=0;i<size;i++){
			accHeadsAdd.append(accHeadAdd[i]).append(",");
		}
		if(accHeadsAdd.length()>0)
			this.accHeadsAdd = accHeadsAdd.substring(0,accHeadsAdd.length()-1);
		else
			this.accHeadsAdd = "";
		return this.accHeadsAdd;
	}
	
	public String getAccHeadsLess() {
		StringBuffer accHeadsLess = new StringBuffer();
		int size = accHeadLess.length;
		for(int i=0;i<size;i++){
			accHeadsLess.append(accHeadLess[i]).append(",");
		}
		if(accHeadsLess.length()>0)
			this.accHeadsLess = accHeadsLess.substring(0,accHeadsLess.length()-1);
		else
			this.accHeadsLess = "";
		return this.accHeadsLess;
	}
	
	public String getAccountHeads() {
		StringBuffer accountHeads = new StringBuffer();
		int size = 0;
		if(accHeadAdd != null){
			size = accHeadAdd.length;
			for(int i=0;i<size;i++){
				accountHeads.append(accHeadAdd[i]).append("+");				
			}
		}
		if(accountHeads.length()>0){		
			accountHeads = new StringBuffer(accountHeads.substring(0,accountHeads.length()-1));
		}
		if(accHeadLess != null){
			size = accHeadLess.length;
			for(int i=0;i<size;i++){
				accountHeads.append("-").append(accHeadLess[i]);
			}		
		}
		this.accountHeads = accountHeads.toString();
		return this.accountHeads;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppend() {
		return schType+" - "+description;
	}
}
