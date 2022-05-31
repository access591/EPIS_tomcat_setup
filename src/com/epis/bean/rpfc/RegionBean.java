package com.epis.bean.rpfc;

import java.io.Serializable;

public class RegionBean implements Serializable{
 String region,airportcode="",aaiCategory="";

public String getAaiCategory() {
	return aaiCategory;
}

public void setAaiCategory(String aaiCategory) {
	this.aaiCategory = aaiCategory;
}

public String getAirportcode() {
	return airportcode;
}

public void setAirportcode(String airportcode) {
	this.airportcode = airportcode;
}

public String getRegion() {
	return region;
}

public void setRegion(String region) {
	this.region = region;
}
}
