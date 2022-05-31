package com.epis.bean.admin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class AccessRightsBean extends RequestBean {
	
	private String moduleCode;
	private String moduleName;
	private String subModuleCode;
	private String subModuleName;
	private String screenCode;
	private String screenName;
	private String accessRight;
	private String newScreen;
	private String editScreen;
	private String deleteScreen;
	private String viewScreen;
	private String reportScreen;
	private ArrayList screenOptions;
	
	public ArrayList getScreenOptions() {
		return screenOptions;
	}
	public void setScreenOptions(ArrayList screenOptions) {
		this.screenOptions = screenOptions;
	}
	public AccessRightsBean(HttpServletRequest request){
		super(request);
		}
	public AccessRightsBean() {
	}
	
	public AccessRightsBean(String moduleCode, String moduleName, String subModuleCode,
			String subModuleName,String screenCode,String screenName) {
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.subModuleCode = subModuleCode;
		this.subModuleName = subModuleName;
		this.screenCode = screenCode;
		this.screenName = screenName;
	}
	
	public AccessRightsBean(String screenCode,String accessRight,
			String newScreen,String editScreen,String deleteScreen,String viewScreen,String reportScreen) {		
		this.screenCode = screenCode;
		this.accessRight = accessRight;
		this.newScreen = newScreen;
		this.editScreen = editScreen;
		this.deleteScreen = deleteScreen;
		this.viewScreen = viewScreen;
		this.reportScreen = reportScreen;
	}
	
		
	public String toString(){
		StringBuffer buf = new StringBuffer("ModuleCode : ");
		buf.append(this.moduleCode).append(" # ModuleName : ").append(this.moduleName).append(" # SubModuleCode : ");
		buf.append(this.subModuleCode).append(" # SubModuleName : ").append(this.subModuleName);
		buf.append(" # ScreenCode : ").append(this.screenCode).append(" # ScreenName : ").append(this.screenName);
		buf.append(" # AccessRight : ").append(this.accessRight).append(" # NewScreen : ").append(this.newScreen);
		buf.append(" # EditScreen : ").append(this.editScreen).append(" # DeleteScreen : ").append(this.deleteScreen);
		buf.append(" # ViewScreen : ").append(this.viewScreen).append(" # ReportScreen : ").append(this.reportScreen);
		return buf.toString();
	}
	
	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getSubModuleCode() {
		return subModuleCode;
	}

	public void setSubModuleCode(String subModuleCode) {
		this.subModuleCode = subModuleCode;
	}

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	public String getAccessRight() {
		return accessRight;
	}

	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
	}

	public String getDeleteScreen() {
		return deleteScreen;
	}

	public void setDeleteScreen(String deleteScreen) {
		this.deleteScreen = deleteScreen;
	}

	public String getEditScreen() {
		return editScreen;
	}

	public void setEditScreen(String editScreen) {
		this.editScreen = editScreen;
	}

	public String getNewScreen() {
		return newScreen;
	}

	public void setNewScreen(String newScreen) {
		this.newScreen = newScreen;
	}

	public String getReportScreen() {
		return reportScreen;
	}

	public void setReportScreen(String reportScreen) {
		this.reportScreen = reportScreen;
	}

	public String getViewScreen() {
		return viewScreen;
	}

	public void setViewScreen(String viewScreen) {
		this.viewScreen = viewScreen;
	}
}
