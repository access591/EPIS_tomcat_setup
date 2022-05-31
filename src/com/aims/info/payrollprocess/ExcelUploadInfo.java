package com.aims.info.payrollprocess;

import java.io.Serializable;
import java.util.List;

public class ExcelUploadInfo implements Serializable{
	
	
	private static final long serialVersionUID = -3197546188243049095L;
	private String tableName;
	private List tableNameList;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List getTableNameList() {
		return tableNameList;
	}
	public void setTableNameList(List tableNameList) {
		this.tableNameList = tableNameList;
	}

}
