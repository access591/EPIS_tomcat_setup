package com.epis.bean.admin;

import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;
import java.io.Serializable;

public class RoleBean extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleCd="";
	private String roleName="";
	private String roleDescription="";
	private String modules="";
	/**
	 * @return Returns the roleCd.
	 */
	public String getRoleCd() {
		return roleCd;
	}
	/**
	 * @param roleCd The roleCd to set.
	 */
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	/**
	 * @return Returns the roleDescription.
	 */
	public String getRoleDescription() {
		return roleDescription;
	}
	/**
	 * @param roleDescription The roleDescription to set.
	 */
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	/**
	 * @return Returns the roleName.
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName The roleName to set.
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public RoleBean(){
	
	}
	public RoleBean(String roleCd,String roleName,String roleDescription,String modules) {
		this.roleCd=roleCd;
		this.roleName=roleName;
		this.roleDescription=roleDescription;
		this.modules=modules;
	}
	/**
	 * @return Returns the modules.
	 */
	public String getModules() {
		return modules;
	}
	/**
	 * @param modules The modules to set.
	 */
	public void setModules(String modules) {
		this.modules = modules;
	}
	public RoleBean(HttpServletRequest request){
		super(request);
		}
}