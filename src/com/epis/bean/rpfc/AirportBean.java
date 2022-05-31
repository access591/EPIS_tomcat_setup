/**
 * File       : AirportBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */

package com.epis.bean.rpfc;

import java.io.Serializable;

public class AirportBean implements Serializable{
	String airportCD="",airportName="",region="";

	public String getAirportCD() {
		return airportCD;
	}

	public void setAirportCD(String airportCD) {
		this.airportCD = airportCD;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
