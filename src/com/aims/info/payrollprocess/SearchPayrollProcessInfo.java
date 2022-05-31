package com.aims.info.payrollprocess;

import java.util.ArrayList;

public class SearchPayrollProcessInfo {

	String searchPayrollMonth = "";
	String searchProcessingType = "";
	String searchDisciplineName = "";
	String searchProcessStatus = "";
	String searchMonthlyStatus = "";

	String station = "";
	String payTransId = "";
	String payrollMonth = "";
	String discplineName = "";
	String staffCategory = "";
	String processStatus = "";
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	/**
	 * @return Returns the discplineName.
	 */
	public String getDiscplineName() {
		return discplineName;
	}
	/**
	 * @param discplineName The discplineName to set.
	 */
	public void setDiscplineName(String discplineName) {
		this.discplineName = discplineName;
	}
	/**
	 * @return Returns the payrollMonth.
	 */
	public String getPayrollMonth() {
		return payrollMonth;
	}
	/**
	 * @param payrollMonth The payrollMonth to set.
	 */
	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
	}
	/**
	 * @return Returns the processStatus.
	 */
	public String getProcessStatus() {
		return processStatus;
	}
	/**
	 * @param processStatus The processStatus to set.
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	/**
	 * @return Returns the staffCategory.
	 */
	public String getStaffCategory() {
		return staffCategory;
	}
	/**
	 * @param staffCategory The staffCategory to set.
	 */
	public void setStaffCategory(String staffCategory) {
		this.staffCategory = staffCategory;
	}
	/**
	 * @return Returns the payTransId.
	 */
	public String getPayTransId() {
		return payTransId;
	}
	/**
	 * @param payTransId The payTransId to set.
	 */
	public void setPayTransId(String payTransId) {
		this.payTransId = payTransId;
	}
	String searchDisciplineCd;
	int searchTransId;
	ArrayList disciplineList;
	
	 String sortfield="";
	   String sorttype="";
	   String totolRecords = "0";
	   int pageNo = 1;
	   String navigation = "";
	   boolean hasNext = false;
	   boolean hasPrevious = false;
	   ArrayList searchList = new ArrayList();
	   String heading = "";
	   boolean last;
	   boolean first;
	   String searchtype = "";	 
	   Integer id;
	
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public boolean isHasPrevious() {
		return hasPrevious;
	}
	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public String getNavigation() {
		return navigation;
	}
	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public ArrayList getSearchList() {
		return searchList;
	}
	public void setSearchList(ArrayList searchList) {
		this.searchList = searchList;
	}
	public String getSearchtype() {
		return searchtype;
	}
	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}
	public String getSortfield() {
		return sortfield;
	}
	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}
	public String getSorttype() {
		return sorttype;
	}
	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}
	public String getTotolRecords() {
		return totolRecords;
	}
	public void setTotolRecords(String totolRecords) {
		this.totolRecords = totolRecords;
	}
	public String getSearchDisciplineName() {
		return searchDisciplineName;
	}
	public void setSearchDisciplineName(String searchDisciplineName) {
		this.searchDisciplineName = searchDisciplineName;
	}
	public String getSearchMonthlyStatus() {
		return searchMonthlyStatus;
	}
	public void setSearchMonthlyStatus(String searchMonthlyStatus) {
		this.searchMonthlyStatus = searchMonthlyStatus;
	}
	public String getSearchPayrollMonth() {
		return searchPayrollMonth;
	}
	public void setSearchPayrollMonth(String searchPayrollMonth) {
		this.searchPayrollMonth = searchPayrollMonth;
	}
	public String getSearchProcessingType() {
		return searchProcessingType;
	}
	public void setSearchProcessingType(String searchProcessingType) {
		this.searchProcessingType = searchProcessingType;
	}
	public String getSearchProcessStatus() {
		return searchProcessStatus;
	}
	public void setSearchProcessStatus(String searchProcessStatus) {
		this.searchProcessStatus = searchProcessStatus;
	}
	public String getSearchDisciplineCd() {
		return searchDisciplineCd;
	}
	public void setSearchDisciplineCd(String searchDisciplineCd) {
		this.searchDisciplineCd = searchDisciplineCd;
	}
	public ArrayList getDisciplineList() {
		return disciplineList;
	}
	public void setDisciplineList(ArrayList disciplineList) {
		this.disciplineList = disciplineList;
	}
	public int getSearchTransId() {
		return searchTransId;
	}
	public void setSearchTransId(int searchTransId) {
		this.searchTransId = searchTransId;
	}
	
	
}
