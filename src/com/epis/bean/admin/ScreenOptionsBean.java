package com.epis.bean.admin;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class ScreenOptionsBean extends RequestBean {
	
	private String optionCode;
	private String optionName;
	private String path;
	private String accessRight;
	
	public ScreenOptionsBean(HttpServletRequest request){
		super(request);
		}
	public ScreenOptionsBean() {
	}
	public ScreenOptionsBean(String optionCode,String optionName,String path) {
		this.optionCode=optionCode;
		this.optionName=optionName;
		this.path=path;	
		
	}
	public ScreenOptionsBean(String optionCode,String optionName,String path,String accessRight) {
		this.optionCode=optionCode;
		this.optionName=optionName;
		this.path=path;	
		this.accessRight=accessRight;
	}
	public String getOptionCode() {
		return optionCode;
	}
	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAccessRight() {
		return accessRight;
	}
	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
	}
	public String toString(){
		StringBuffer buf = new StringBuffer("optionCode : ");
		buf.append(this.optionCode).append(" # optionName : ").append(this.optionName).append(" # path : ");
		buf.append(this.path).append(" # accessRight : ").append(this.accessRight);
		return buf.toString();
	}
	
}
