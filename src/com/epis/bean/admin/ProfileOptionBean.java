package com.epis.bean.admin;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class ProfileOptionBean extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String optionCode="";
	private String optionName="";
	private String description="";
	private String memberAccessFlag="";
	private String unitAccessFlag="";
	private String regionAccessFlag="";
	private String chqAccessFlag="";
	private String path;
	private String optionType="";
	
	public ProfileOptionBean(HttpServletRequest request){
		super(request);
		}
	public String getOptionType() {
		return optionType;
	}
	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}
	public ProfileOptionBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProfileOptionBean(String optionCode,String optionName,String description) {
		this.optionCode=optionCode;
		this.optionName=optionName;
		this.description=description;
	}
	public ProfileOptionBean(String optionCode,String optionName,String description,String memberAccessFlag,
			String unitAccessFlag,String regionAccessFlag,String chqAccessFlag) {
		this.optionCode=optionCode;
		this.optionName=optionName;
		this.description=description;
		this.memberAccessFlag=memberAccessFlag;
		this.unitAccessFlag=unitAccessFlag;
		this.regionAccessFlag=regionAccessFlag;
		this.chqAccessFlag=chqAccessFlag;
	}
	
	public ProfileOptionBean(String optionCode,String optionName,String description,String path,String optionType) {
		this(optionCode,optionName,description);
		this.path=path;
		this.optionType=optionType;
	}
	
	public String getChqAccessFlag() {
		return chqAccessFlag;
	}
	public void setChqAccessFlag(String chqAccessFlag) {
		this.chqAccessFlag = chqAccessFlag;
	}
	public String getMemberAccessFlag() {
		return memberAccessFlag;
	}
	public void setMemberAccessFlag(String memberAccessFlag) {
		this.memberAccessFlag = memberAccessFlag;
	}
	public String getRegionAccessFlag() {
		return regionAccessFlag;
	}
	public void setRegionAccessFlag(String regionAccessFlag) {
		this.regionAccessFlag = regionAccessFlag;
	}
	public String getUnitAccessFlag() {
		return unitAccessFlag;
	}
	public void setUnitAccessFlag(String unitAccessFlag) {
		this.unitAccessFlag = unitAccessFlag;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public String toString(){
		return ("Option Code:"+this.optionCode+"###Option Name:"+this.optionName+"###Description:"+this.description);
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
