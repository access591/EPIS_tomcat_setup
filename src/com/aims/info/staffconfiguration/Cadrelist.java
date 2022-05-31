package com.aims.info.staffconfiguration;

public class Cadrelist {
	
	private String cadreCd;
	private String cadreName="Select";
	
	public Cadrelist(String cadreCd,String cadreName)
	{
		this.cadreName=cadreName;
		this.cadreCd=cadreCd;
	}

	

	public String getCadreCd() {
		return cadreCd;
	}



	public void setCadreCd(String cadreCd) {
		this.cadreCd = cadreCd;
	}



	public String getCadreName() {
		return cadreName;
	}

	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}

}
