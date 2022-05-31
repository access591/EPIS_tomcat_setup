package com.aims.info.staffconfiguration;

public class Desciplinelist {
	
	private int disciplineCd;
	private String disciplineNm="Select";
	
	public Desciplinelist(int disciplineCd,String disciplineNm)
	{
		this.disciplineNm=disciplineNm;
		this.disciplineCd=disciplineCd;
	}
	public int getDisciplineCd() {
		return disciplineCd;
	}

	public void setDisciplineCd(int disciplineCd) {
		this.disciplineCd = disciplineCd;
	}

	public String getDisciplineNm() {
		return disciplineNm;
	}

	public void setDisciplineNm(String disciplineNm) {
		this.disciplineNm = disciplineNm;
	}
	
}
