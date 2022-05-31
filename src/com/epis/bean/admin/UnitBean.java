package com.epis.bean.admin;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class UnitBean extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unitCode="";
	private String unitName="";
	private String option="";
	private String region="";
	
	public UnitBean(HttpServletRequest request){
		super(request);
		}
	public UnitBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UnitBean(String unitCode,String unitName,String option,String region){
		this.unitCode=unitCode;
		this.unitName=unitName;
		this.option=option;
		this.region=region;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String toString(){
		return 		("Unit Code:"+this.unitCode+"###Unit Name:"+this.unitName+"###Option:"+this.option+"###Rrgion"+this.region);
	}
	

}
