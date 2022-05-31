package com.epis.bean.admin;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;
public class ScreenBean extends RequestBean{
	
	private String moduleName="";
	private String moduleCd="";
	private String subModuleCd="";
	private String subModuleName="";
	private String screenName="";
	private String screenPath="";
	private String screenCD="";
	private String sortingOrder="";
	private String[] optionDetails;
	
	public String[] getOptionDetails() {
		return optionDetails;
	}
	public void setOptionDetails(String[] optionDetails) {
		this.optionDetails = optionDetails;
	}
	public ScreenBean(HttpServletRequest request){
		super(request);
		}
	public ScreenBean(){
		}
	public String getModuleCd() {
		return moduleCd;
	}
	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getScreenPath() {
		return screenPath;
	}
	public void setScreenPath(String screenPath) {
		this.screenPath = screenPath;
	}
	public String getSubModuleCd() {
		return subModuleCd;
	}
	public void setSubModuleCd(String subModuleCd) {
		this.subModuleCd = subModuleCd;
	}
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}
	public String getScreenCD() {
		return screenCD;
	}
	public void setScreenCD(String screenCD) {
		this.screenCD = screenCD;
	}
	public String getSortingOrder() {
		return sortingOrder;
	}
	public void setSortingOrder(String sortingOrder) {
		this.sortingOrder = sortingOrder;
	}

}
