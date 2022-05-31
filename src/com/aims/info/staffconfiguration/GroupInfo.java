package com.aims.info.staffconfiguration;

import java.io.Serializable;

public class GroupInfo implements Serializable{

	public Integer groupCd;
	public String groupName;
	public String groupDesc;
	public String status;
	/**
	 * @return Returns the groupCd.
	 */
	public Integer getGroupCd() {
		return groupCd;
	}
	/**
	 * @param groupCd The groupCd to set.
	 */
	public void setGroupCd(Integer groupCd) {
		this.groupCd = groupCd;
	}
	/**
	 * @return Returns the groupDesc.
	 */
	public String getGroupDesc() {
		return groupDesc;
	}
	/**
	 * @param groupDesc The groupDesc to set.
	 */
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	/**
	 * @return Returns the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName The groupName to set.
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
