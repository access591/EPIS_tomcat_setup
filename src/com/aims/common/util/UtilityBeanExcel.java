package com.aims.common.util;

import java.io.Serializable;

public class UtilityBeanExcel  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -329521145566343586L;
	private String slno;
	private String tableName;
	private String earnedCd;
	private String tableColumn;
	private String excelColumnName;
	private String payrollmonthid;
	
	public String getExcelColumnName() {
		return excelColumnName;
	}
	public void setExcelColumnName(String excelColumnName) {
		this.excelColumnName = excelColumnName;
	}
	public String getEarnedCd() {
		return earnedCd;
	}
	public void setEarnedCd(String earnedCd) {
		this.earnedCd = earnedCd;
	}
	public String getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPayrollmonthid() {
		return payrollmonthid;
	}
	public void setPayrollmonthid(String payrollmonthid) {
		this.payrollmonthid = payrollmonthid;
	}
	public String getSlno() {
		return slno;
	}
	public void setSlno(String slno) {
		this.slno = slno;
	}
	

}
