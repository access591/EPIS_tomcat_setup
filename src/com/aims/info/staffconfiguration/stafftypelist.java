package com.aims.info.staffconfiguration;

public class stafftypelist {
	private int staffTypeCd;
	private String staffTypeName="Select";
	
	public stafftypelist(int cd, String name) {
		super();
		// TODO Auto-generated constructor stub
		staffTypeCd = cd;
		staffTypeName = name;
	}
	public int getStaffTypeCd() {
		return staffTypeCd;
	}
	public void setStaffTypeCd(int staffTypeCd) {
		this.staffTypeCd = staffTypeCd;
	}
	public String getStaffTypeName() {
		return staffTypeName;
	}
	public void setStaffTypeName(String staffTypeName) {
		this.staffTypeName = staffTypeName;
	}

}
