package com.epis.bean.admin;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class RegionBean extends RequestBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String regionName;
	private String aaiCategory;
	private String remarks;
	private String regionCd;
	private String regionCode;
	
	
	public RegionBean(HttpServletRequest request){
		super(request);
		}
	public RegionBean(){
		}
	public String getRegionCd() {
		return regionCd;
	}
	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}
	
	public RegionBean(String regionCd,String regionName, String aaiCategory, String remarks) {
		super();	
		this.regionCd = regionCd;
		this.regionName = regionName;
		this.aaiCategory = aaiCategory;
		this.remarks = remarks;
	}
	
	public RegionBean(String regionCd,String regionName, String aaiCategory, String remarks,String regionCode) {
		this(regionCd,  regionName,   aaiCategory, remarks);
		this.regionCode = regionCode;
	}
	public String getAaiCategory() {
		return aaiCategory;
	}
	public void setAaiCategory(String aaiCategory) {
		this.aaiCategory = aaiCategory;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String toString(){
		return 		("Region Cd:"+regionCd+"###Region Name:"+this.regionName+"###AAI Category:"+this.aaiCategory+"###Remarks"+this.remarks);
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	

}
