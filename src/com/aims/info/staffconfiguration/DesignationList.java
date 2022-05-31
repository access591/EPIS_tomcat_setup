package com.aims.info.staffconfiguration;

public class DesignationList {
	private Integer cadreCd;
	private String cadreName="Select";

	public DesignationList(Integer cadrecode,String cadreNm) {
		super();
		this.cadreCd = cadrecode;
		this.cadreName = cadreNm;
		// TODO Auto-generated constructor stub
	}
	public Integer getCadreCd() {
		return cadreCd;
	}
	public void setCadreCd(Integer cadreCd) {
		this.cadreCd = cadreCd;
	}
	public String getCadreName() {
		return cadreName;
	}
	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}
}
