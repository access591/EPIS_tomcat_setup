/*
 * Created on Jun 23, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.aims.info.configuration;

/**
 * @author rajanin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StationInfo {
	  
	private String regioncd;
	private String regionname;
	private String stationcd;
	private String stationname;
	private String saustation;
	private String stationdescr;
	private String stationtype;
	private String status;
	private String userCd;
    private String prevStationCd;
    private String division;
    
    public String getSaustation() {
		return saustation;
	}
	public void setSaustation(String saustation) {
		this.saustation = saustation;
	}
	public String getPrevStationCd() {
		return prevStationCd;
	}
	public void setPrevStationCd(String prevStationCd) {
		this.prevStationCd = prevStationCd;
	}
	public String getRegioncd() {
		return regioncd;
	}
	public void setRegioncd(String regioncd) {
		this.regioncd = regioncd;
	}
	public String getStationcd() {
		return stationcd;
	}
	public void setStationcd(String stationcd) {
		this.stationcd = stationcd;
	}
	public String getStationdescr() {
		return stationdescr;
	}
	public void setStationdescr(String stationdescr) {
		this.stationdescr = stationdescr;
	}
	public String getStationname() {
		return stationname;
	}
	public void setStationname(String stationname) {
		this.stationname = stationname;
	}
	public String getStationtype() {
		return stationtype;
	}
	public void setStationtype(String stationtype) {
		this.stationtype = stationtype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserCd() {
		return userCd;
	}
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	
	
	/**
	 * @return Returns the division.
	 */
	public String getDivision() {
		return division;
	}
	/**
	 * @param division The division to set.
	 */
	public void setDivision(String division) {
		this.division = division;
	}
}
