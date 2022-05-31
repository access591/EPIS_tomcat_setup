/*
 * Created on Jun 22, 2009
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

	import java.io.Serializable;
	import java.sql.Date;
import java.sql.Timestamp;

	public class RegionInfo implements Serializable{
		private String regionname;
		private String regioncd;
		private String regiondescr;
		private String status;
		private String userCd;
		
		
		public String getRegioncd() {
			return regioncd;
		}
		public void setRegioncd(String regioncd) {
			this.regioncd = regioncd;
		}
		
		public String getRegiondescr() {
			return regiondescr;
		}
		public void setRegiondescr(String regiondescr) {
			this.regiondescr = regiondescr;
		}
		public String getRegionname() {
			return regionname;
		}
		public void setRegionname(String regionname) {
			this.regionname = regionname;
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
	}

