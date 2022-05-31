
/**
  * File       : SearchStaffTypeInfo.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.info.staffconfiguration;

import java.io.Serializable;
import java.util.ArrayList;



public class SearchStaffTypeInfo implements Serializable
 {

	  private String searchstaffTypeCd;
	  private String searchstaffTypeName;
	  private String searchstaffTypeDesc;
	  private String status;

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

	public String getSearchstaffTypeCd() {
			return searchstaffTypeCd;
		}

		public void setSearchstaffTypeCd(String searchstaffTypeCd) {
			this.searchstaffTypeCd = searchstaffTypeCd;
		}

		public String getSearchstaffTypeDesc() {
			return searchstaffTypeDesc;
		}

		public void setSearchstaffTypeDesc(String searchstaffTypeDesc) {
			this.searchstaffTypeDesc = searchstaffTypeDesc;
		}

		public String getSearchstaffTypeName() {
			return searchstaffTypeName;
		}

		public void setSearchstaffTypeName(String searchstaffTypeName) {
			this.searchstaffTypeName = searchstaffTypeName;
		}

	
	
	
	

}

