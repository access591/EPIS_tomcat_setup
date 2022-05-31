package com.epis.bean.rpfc;

public class Form8RemittanceBean {
	String totalContr="",totalEmnts="";
    double  totalAprPFStatury = 0.0, totalAprVPF = 0.0, totalAprCPF = 0.0, totalAprContribution = 0.0;
    double  totalMayPFStatury = 0.0, totalMayVPF = 0.0, totalMayCPF = 0.0, totalMayContribution = 0.0;
    double  totalJunPFStatury = 0.0, totalJunVPF = 0.0, totalJunCPF = 0.0, totalJunContribution = 0.0;
    double  totalJulPFStatury = 0.0, totalJulVPF = 0.0, totalJulCPF = 0.0, totalJulContribution = 0.0;
    double  totalAugPFStatury = 0.0, totalAugVPF = 0.0, totalAugCPF = 0.0, totalAugContribution = 0.0;
    double  totalSepPFStatury = 0.0, totalSepVPF = 0.0, totalSepCPF = 0.0, totalSepContribution = 0.0;
    double  totalOctPFStatury = 0.0, totalOctVPF = 0.0, totalOctCPF = 0.0, totalOctContribution = 0.0;
    double  totalNovPFStatury = 0.0, totalNovVPF = 0.0, totalNovCPF = 0.0, totalNovContribution = 0.0;
    double  totalDecPFStatury = 0.0, totalDecVPF = 0.0, totalDecCPF = 0.0, totalDecContribution = 0.0;
    double  totalJanPFStatury = 0.0, totalJanVPF = 0.0, totalJanCPF = 0.0, totalJanContribution = 0.0;
    double  totalFebPFStatury = 0.0, totalFebVPF = 0.0, totalFebCPF = 0.0, totalFebContribution = 0.0;
    double  totalMarPFStatury = 0.0, totalMarVPF = 0.0, totalMarCPF = 0.0, totalMarContribution = 0.0;
    double totalAprDueContribution = 0.0,totalMayDueContribution = 0.0,totalJunDueContribution = 0.0,totalJulDueContribution = 0.0;
	double totalAugDueContribution = 0.0,totalSepDueContribution = 0.0,totalOctDueContribution = 0.0,totalNovDueContribution = 0.0,totalDecDueContribution = 0.0,totalJanDueContribution = 0.0,totalFebDueContribution = 0.0,totalMarDueContribution = 0.0;
     long totalAprEmoluments = 0;
	 long totalMayEmoluments = 0;
	 long totalJunEmoluments = 0 ;
	 long totalJulEmoluments = 0;
	 long totalAugEmoluments = 0;
	 long totalSepEmoluments = 0;
	 long totalOctEmoluments = 0;
	 long totalNovEmoluments = 0;
	 long totalDecEmoluments = 0;
	 long totalJanEmoluments = 0;
	 long totalFebEmoluments = 0;
	 long totalMarEmoluments = 0;
	 long totalNovOptionAEmoluments = 0;
	 long totalNovOptionBEmoluments = 0;
	 long totalAprOptionAEmoluments=0,totalAprOptionBEmoluments=0,totalMayOptionAEmoluments=0,totalMayOptionBEmoluments=0,totalJunOptionAEmoluments=0,totalJunOptionBEmoluments=0,totalJulOptionAEmoluments=0,totalJulOptionBEmoluments=0;
	 long totalAugOptionAEmoluments=0,totalAugOptionBEmoluments=0,totalSepOptionAEmoluments=0,totalSepOptionBEmoluments=0,totalOctOptionAEmoluments=0,totalOctOptionBEmoluments=0,totalDecOptionAEmoluments=0,totalDecOptionBEmoluments=0;
	 long totalJanOptionAEmoluments=0,totalJanOptionBEmoluments=0,totalFebOptionAEmoluments=0,totalFebOptionBEmoluments=0,totalMarOptionAEmoluments=0,totalMarOptionBEmoluments=0;
    int aprCnt = 0,mayCnt = 0,junCnt = 0 ,julCnt = 0,augCnt = 0,sepCnt = 0,octCnt = 0,novCnt = 0,decCnt = 0,janCnt = 0,febCnt = 0,marCnt = 0;
    String aprMnth = "",mayMnth = "",junMnth = "" ,julMnth = "",augMnth = "",sepMnth = "",octMnth = "",novMnth = "",decMnth = "",janMnth = "",febMnth = "",marMnth = "";
	public int getAprCnt() {
		return aprCnt;
	}
	
	public double getTotalAprDueContribution() {
		return totalAprDueContribution;
	}

	public void setTotalAprDueContribution(double totalAprDueContribution) {
		this.totalAprDueContribution = totalAprDueContribution;
	}

	public double getTotalAugDueContribution() {
		return totalAugDueContribution;
	}

	public void setTotalAugDueContribution(double totalAugDueContribution) {
		this.totalAugDueContribution = totalAugDueContribution;
	}

	public double getTotalDecDueContribution() {
		return totalDecDueContribution;
	}

	public void setTotalDecDueContribution(double totalDecDueContribution) {
		this.totalDecDueContribution = totalDecDueContribution;
	}

	public double getTotalFebDueContribution() {
		return totalFebDueContribution;
	}

	public void setTotalFebDueContribution(double totalFebDueContribution) {
		this.totalFebDueContribution = totalFebDueContribution;
	}

	public double getTotalJanDueContribution() {
		return totalJanDueContribution;
	}

	public void setTotalJanDueContribution(double totalJanDueContribution) {
		this.totalJanDueContribution = totalJanDueContribution;
	}

	public double getTotalJulDueContribution() {
		return totalJulDueContribution;
	}

	public void setTotalJulDueContribution(double totalJulDueContribution) {
		this.totalJulDueContribution = totalJulDueContribution;
	}

	public double getTotalJunDueContribution() {
		return totalJunDueContribution;
	}

	public void setTotalJunDueContribution(double totalJunDueContribution) {
		this.totalJunDueContribution = totalJunDueContribution;
	}

	public double getTotalMarDueContribution() {
		return totalMarDueContribution;
	}

	public void setTotalMarDueContribution(double totalMarDueContribution) {
		this.totalMarDueContribution = totalMarDueContribution;
	}

	public double getTotalMayDueContribution() {
		return totalMayDueContribution;
	}

	public void setTotalMayDueContribution(double totalMayDueContribution) {
		this.totalMayDueContribution = totalMayDueContribution;
	}

	public double getTotalNovDueContribution() {
		return totalNovDueContribution;
	}

	public void setTotalNovDueContribution(double totalNovDueContribution) {
		this.totalNovDueContribution = totalNovDueContribution;
	}

	public double getTotalOctDueContribution() {
		return totalOctDueContribution;
	}

	public void setTotalOctDueContribution(double totalOctDueContribution) {
		this.totalOctDueContribution = totalOctDueContribution;
	}

	public double getTotalSepDueContribution() {
		return totalSepDueContribution;
	}

	public void setTotalSepDueContribution(double totalSepDueContribution) {
		this.totalSepDueContribution = totalSepDueContribution;
	}

	public void setAprCnt(int aprCnt) {
		this.aprCnt = aprCnt;
	}
	public String getAprMnth() {
		return aprMnth;
	}
	public void setAprMnth(String aprMnth) {
		this.aprMnth = aprMnth;
	}
	public int getAugCnt() {
		return augCnt;
	}
	public void setAugCnt(int augCnt) {
		this.augCnt = augCnt;
	}
	public String getAugMnth() {
		return augMnth;
	}
	public void setAugMnth(String augMnth) {
		this.augMnth = augMnth;
	}
	public int getDecCnt() {
		return decCnt;
	}
	public void setDecCnt(int decCnt) {
		this.decCnt = decCnt;
	}
	public String getDecMnth() {
		return decMnth;
	}
	public void setDecMnth(String decMnth) {
		this.decMnth = decMnth;
	}
	public int getFebCnt() {
		return febCnt;
	}
	public void setFebCnt(int febCnt) {
		this.febCnt = febCnt;
	}
	public String getFebMnth() {
		return febMnth;
	}
	public void setFebMnth(String febMnth) {
		this.febMnth = febMnth;
	}
	public int getJanCnt() {
		return janCnt;
	}
	public void setJanCnt(int janCnt) {
		this.janCnt = janCnt;
	}
	public String getJanMnth() {
		return janMnth;
	}
	public void setJanMnth(String janMnth) {
		this.janMnth = janMnth;
	}
	public int getJulCnt() {
		return julCnt;
	}
	public void setJulCnt(int julCnt) {
		this.julCnt = julCnt;
	}
	public String getJulMnth() {
		return julMnth;
	}
	public void setJulMnth(String julMnth) {
		this.julMnth = julMnth;
	}
	public int getJunCnt() {
		return junCnt;
	}
	public void setJunCnt(int junCnt) {
		this.junCnt = junCnt;
	}
	public String getJunMnth() {
		return junMnth;
	}
	public void setJunMnth(String junMnth) {
		this.junMnth = junMnth;
	}
	public int getMarCnt() {
		return marCnt;
	}
	public void setMarCnt(int marCnt) {
		this.marCnt = marCnt;
	}
	public String getMarMnth() {
		return marMnth;
	}
	public void setMarMnth(String marMnth) {
		this.marMnth = marMnth;
	}
	public int getMayCnt() {
		return mayCnt;
	}
	public void setMayCnt(int mayCnt) {
		this.mayCnt = mayCnt;
	}
	public String getMayMnth() {
		return mayMnth;
	}
	public void setMayMnth(String mayMnth) {
		this.mayMnth = mayMnth;
	}
	public int getNovCnt() {
		return novCnt;
	}
	public void setNovCnt(int novCnt) {
		this.novCnt = novCnt;
	}
	public String getNovMnth() {
		return novMnth;
	}
	public void setNovMnth(String novMnth) {
		this.novMnth = novMnth;
	}
	public int getOctCnt() {
		return octCnt;
	}
	public void setOctCnt(int octCnt) {
		this.octCnt = octCnt;
	}
	public String getOctMnth() {
		return octMnth;
	}
	public void setOctMnth(String octMnth) {
		this.octMnth = octMnth;
	}
	public int getSepCnt() {
		return sepCnt;
	}
	public void setSepCnt(int sepCnt) {
		this.sepCnt = sepCnt;
	}
	public String getSepMnth() {
		return sepMnth;
	}
	public void setSepMnth(String sepMnth) {
		this.sepMnth = sepMnth;
	}
	public double getTotalAprContribution() {
		return totalAprContribution;
	}
	public void setTotalAprContribution(double totalAprContribution) {
		this.totalAprContribution = totalAprContribution;
	}
	public double getTotalAprCPF() {
		return totalAprCPF;
	}
	public void setTotalAprCPF(double totalAprCPF) {
		this.totalAprCPF = totalAprCPF;
	}
	public long getTotalAprEmoluments() {
		return totalAprEmoluments;
	}
	public void setTotalAprEmoluments(long totalAprEmoluments) {
		this.totalAprEmoluments = totalAprEmoluments;
	}
	public double getTotalAprPFStatury() {
		return totalAprPFStatury;
	}
	public void setTotalAprPFStatury(double totalAprPFStatury) {
		this.totalAprPFStatury = totalAprPFStatury;
	}
	public double getTotalAprVPF() {
		return totalAprVPF;
	}
	public void setTotalAprVPF(double totalAprVPF) {
		this.totalAprVPF = totalAprVPF;
	}
	public double getTotalAugContribution() {
		return totalAugContribution;
	}
	public void setTotalAugContribution(double totalAugContribution) {
		this.totalAugContribution = totalAugContribution;
	}
	public double getTotalAugCPF() {
		return totalAugCPF;
	}
	public void setTotalAugCPF(double totalAugCPF) {
		this.totalAugCPF = totalAugCPF;
	}
	public long getTotalAugEmoluments() {
		return totalAugEmoluments;
	}
	public void setTotalAugEmoluments(long totalAugEmoluments) {
		this.totalAugEmoluments = totalAugEmoluments;
	}
	public double getTotalAugPFStatury() {
		return totalAugPFStatury;
	}
	public void setTotalAugPFStatury(double totalAugPFStatury) {
		this.totalAugPFStatury = totalAugPFStatury;
	}
	public double getTotalAugVPF() {
		return totalAugVPF;
	}
	public void setTotalAugVPF(double totalAugVPF) {
		this.totalAugVPF = totalAugVPF;
	}
	public String getTotalContr() {
		return totalContr;
	}
	public void setTotalContr(String totalContr) {
		this.totalContr = totalContr;
	}
	public double getTotalDecContribution() {
		return totalDecContribution;
	}
	public void setTotalDecContribution(double totalDecContribution) {
		this.totalDecContribution = totalDecContribution;
	}
	public double getTotalDecCPF() {
		return totalDecCPF;
	}
	public void setTotalDecCPF(double totalDecCPF) {
		this.totalDecCPF = totalDecCPF;
	}
	public long getTotalDecEmoluments() {
		return totalDecEmoluments;
	}
	public void setTotalDecEmoluments(long totalDecEmoluments) {
		this.totalDecEmoluments = totalDecEmoluments;
	}
	public double getTotalDecPFStatury() {
		return totalDecPFStatury;
	}
	public void setTotalDecPFStatury(double totalDecPFStatury) {
		this.totalDecPFStatury = totalDecPFStatury;
	}
	public double getTotalDecVPF() {
		return totalDecVPF;
	}
	public void setTotalDecVPF(double totalDecVPF) {
		this.totalDecVPF = totalDecVPF;
	}
	public String getTotalEmnts() {
		return totalEmnts;
	}
	public void setTotalEmnts(String totalEmnts) {
		this.totalEmnts = totalEmnts;
	}
	public double getTotalFebContribution() {
		return totalFebContribution;
	}
	public void setTotalFebContribution(double totalFebContribution) {
		this.totalFebContribution = totalFebContribution;
	}
	public double getTotalFebCPF() {
		return totalFebCPF;
	}
	public void setTotalFebCPF(double totalFebCPF) {
		this.totalFebCPF = totalFebCPF;
	}
	public long getTotalFebEmoluments() {
		return totalFebEmoluments;
	}
	public void setTotalFebEmoluments(long totalFebEmoluments) {
		this.totalFebEmoluments = totalFebEmoluments;
	}
	public double getTotalFebPFStatury() {
		return totalFebPFStatury;
	}
	public void setTotalFebPFStatury(double totalFebPFStatury) {
		this.totalFebPFStatury = totalFebPFStatury;
	}
	public double getTotalFebVPF() {
		return totalFebVPF;
	}
	public void setTotalFebVPF(double totalFebVPF) {
		this.totalFebVPF = totalFebVPF;
	}
	public double getTotalJanContribution() {
		return totalJanContribution;
	}
	public void setTotalJanContribution(double totalJanContribution) {
		this.totalJanContribution = totalJanContribution;
	}
	public double getTotalJanCPF() {
		return totalJanCPF;
	}
	public void setTotalJanCPF(double totalJanCPF) {
		this.totalJanCPF = totalJanCPF;
	}
	public long getTotalJanEmoluments() {
		return totalJanEmoluments;
	}
	public void setTotalJanEmoluments(long totalJanEmoluments) {
		this.totalJanEmoluments = totalJanEmoluments;
	}
	public double getTotalJanPFStatury() {
		return totalJanPFStatury;
	}
	public void setTotalJanPFStatury(double totalJanPFStatury) {
		this.totalJanPFStatury = totalJanPFStatury;
	}
	public double getTotalJanVPF() {
		return totalJanVPF;
	}
	public void setTotalJanVPF(double totalJanVPF) {
		this.totalJanVPF = totalJanVPF;
	}
	public double getTotalJulContribution() {
		return totalJulContribution;
	}
	public void setTotalJulContribution(double totalJulContribution) {
		this.totalJulContribution = totalJulContribution;
	}
	public double getTotalJulCPF() {
		return totalJulCPF;
	}
	public void setTotalJulCPF(double totalJulCPF) {
		this.totalJulCPF = totalJulCPF;
	}
	public long getTotalJulEmoluments() {
		return totalJulEmoluments;
	}
	public void setTotalJulEmoluments(long totalJulEmoluments) {
		this.totalJulEmoluments = totalJulEmoluments;
	}
	public double getTotalJulPFStatury() {
		return totalJulPFStatury;
	}
	public void setTotalJulPFStatury(double totalJulPFStatury) {
		this.totalJulPFStatury = totalJulPFStatury;
	}
	public double getTotalJulVPF() {
		return totalJulVPF;
	}
	public void setTotalJulVPF(double totalJulVPF) {
		this.totalJulVPF = totalJulVPF;
	}
	public double getTotalJunContribution() {
		return totalJunContribution;
	}
	public void setTotalJunContribution(double totalJunContribution) {
		this.totalJunContribution = totalJunContribution;
	}
	public double getTotalJunCPF() {
		return totalJunCPF;
	}
	public void setTotalJunCPF(double totalJunCPF) {
		this.totalJunCPF = totalJunCPF;
	}
	public long getTotalJunEmoluments() {
		return totalJunEmoluments;
	}
	public void setTotalJunEmoluments(long totalJunEmoluments) {
		this.totalJunEmoluments = totalJunEmoluments;
	}
	public double getTotalJunPFStatury() {
		return totalJunPFStatury;
	}
	public void setTotalJunPFStatury(double totalJunPFStatury) {
		this.totalJunPFStatury = totalJunPFStatury;
	}
	public double getTotalJunVPF() {
		return totalJunVPF;
	}
	public void setTotalJunVPF(double totalJunVPF) {
		this.totalJunVPF = totalJunVPF;
	}
	public double getTotalMarContribution() {
		return totalMarContribution;
	}
	public void setTotalMarContribution(double totalMarContribution) {
		this.totalMarContribution = totalMarContribution;
	}
	public double getTotalMarCPF() {
		return totalMarCPF;
	}
	public void setTotalMarCPF(double totalMarCPF) {
		this.totalMarCPF = totalMarCPF;
	}
	public long getTotalMarEmoluments() {
		return totalMarEmoluments;
	}
	public void setTotalMarEmoluments(long totalMarEmoluments) {
		this.totalMarEmoluments = totalMarEmoluments;
	}
	public double getTotalMarPFStatury() {
		return totalMarPFStatury;
	}
	public void setTotalMarPFStatury(double totalMarPFStatury) {
		this.totalMarPFStatury = totalMarPFStatury;
	}
	public double getTotalMarVPF() {
		return totalMarVPF;
	}
	public void setTotalMarVPF(double totalMarVPF) {
		this.totalMarVPF = totalMarVPF;
	}
	public double getTotalMayContribution() {
		return totalMayContribution;
	}
	public void setTotalMayContribution(double totalMayContribution) {
		this.totalMayContribution = totalMayContribution;
	}
	public double getTotalMayCPF() {
		return totalMayCPF;
	}
	public void setTotalMayCPF(double totalMayCPF) {
		this.totalMayCPF = totalMayCPF;
	}
	public long getTotalMayEmoluments() {
		return totalMayEmoluments;
	}
	public void setTotalMayEmoluments(long totalMayEmoluments) {
		this.totalMayEmoluments = totalMayEmoluments;
	}
	public double getTotalMayPFStatury() {
		return totalMayPFStatury;
	}
	public void setTotalMayPFStatury(double totalMayPFStatury) {
		this.totalMayPFStatury = totalMayPFStatury;
	}
	public double getTotalMayVPF() {
		return totalMayVPF;
	}
	public void setTotalMayVPF(double totalMayVPF) {
		this.totalMayVPF = totalMayVPF;
	}
	public double getTotalNovContribution() {
		return totalNovContribution;
	}
	public void setTotalNovContribution(double totalNovContribution) {
		this.totalNovContribution = totalNovContribution;
	}
	public double getTotalNovCPF() {
		return totalNovCPF;
	}
	public void setTotalNovCPF(double totalNovCPF) {
		this.totalNovCPF = totalNovCPF;
	}
	public long getTotalNovEmoluments() {
		return totalNovEmoluments;
	}
	public void setTotalNovEmoluments(long totalNovEmoluments) {
		this.totalNovEmoluments = totalNovEmoluments;
	}
	public double getTotalNovPFStatury() {
		return totalNovPFStatury;
	}
	public void setTotalNovPFStatury(double totalNovPFStatury) {
		this.totalNovPFStatury = totalNovPFStatury;
	}
	public double getTotalNovVPF() {
		return totalNovVPF;
	}
	public void setTotalNovVPF(double totalNovVPF) {
		this.totalNovVPF = totalNovVPF;
	}
	public double getTotalOctContribution() {
		return totalOctContribution;
	}
	public void setTotalOctContribution(double totalOctContribution) {
		this.totalOctContribution = totalOctContribution;
	}
	public double getTotalOctCPF() {
		return totalOctCPF;
	}
	public void setTotalOctCPF(double totalOctCPF) {
		this.totalOctCPF = totalOctCPF;
	}
	public long getTotalOctEmoluments() {
		return totalOctEmoluments;
	}
	public void setTotalOctEmoluments(long totalOctEmoluments) {
		this.totalOctEmoluments = totalOctEmoluments;
	}
	public double getTotalOctPFStatury() {
		return totalOctPFStatury;
	}
	public void setTotalOctPFStatury(double totalOctPFStatury) {
		this.totalOctPFStatury = totalOctPFStatury;
	}
	public double getTotalOctVPF() {
		return totalOctVPF;
	}
	public void setTotalOctVPF(double totalOctVPF) {
		this.totalOctVPF = totalOctVPF;
	}
	public double getTotalSepContribution() {
		return totalSepContribution;
	}
	public void setTotalSepContribution(double totalSepContribution) {
		this.totalSepContribution = totalSepContribution;
	}
	public double getTotalSepCPF() {
		return totalSepCPF;
	}
	public void setTotalSepCPF(double totalSepCPF) {
		this.totalSepCPF = totalSepCPF;
	}
	public long getTotalSepEmoluments() {
		return totalSepEmoluments;
	}
	public void setTotalSepEmoluments(long totalSepEmoluments) {
		this.totalSepEmoluments = totalSepEmoluments;
	}
	public double getTotalSepPFStatury() {
		return totalSepPFStatury;
	}
	public void setTotalSepPFStatury(double totalSepPFStatury) {
		this.totalSepPFStatury = totalSepPFStatury;
	}
	public double getTotalSepVPF() {
		return totalSepVPF;
	}
	public void setTotalSepVPF(double totalSepVPF) {
		this.totalSepVPF = totalSepVPF;
	}
	public long getTotalNovOptionAEmoluments() {
		return totalNovOptionAEmoluments;
	}
	public void setTotalNovOptionAEmoluments(long totalNovOptionAEmoluments) {
		this.totalNovOptionAEmoluments = totalNovOptionAEmoluments;
	}
	public long getTotalNovOptionBEmoluments() {
		return totalNovOptionBEmoluments;
	}
	public void setTotalNovOptionBEmoluments(long totalNovOptionBEmoluments) {
		this.totalNovOptionBEmoluments = totalNovOptionBEmoluments;
	}
	public long getTotalAprOptionAEmoluments() {
		return totalAprOptionAEmoluments;
	}
	public void setTotalAprOptionAEmoluments(long totalAprOptionAEmoluments) {
		this.totalAprOptionAEmoluments = totalAprOptionAEmoluments;
	}
	public long getTotalAprOptionBEmoluments() {
		return totalAprOptionBEmoluments;
	}
	public void setTotalAprOptionBEmoluments(long totalAprOptionBEmoluments) {
		this.totalAprOptionBEmoluments = totalAprOptionBEmoluments;
	}
	public long getTotalAugOptionAEmoluments() {
		return totalAugOptionAEmoluments;
	}
	public void setTotalAugOptionAEmoluments(long totalAugOptionAEmoluments) {
		this.totalAugOptionAEmoluments = totalAugOptionAEmoluments;
	}
	public long getTotalAugOptionBEmoluments() {
		return totalAugOptionBEmoluments;
	}
	public void setTotalAugOptionBEmoluments(long totalAugOptionBEmoluments) {
		this.totalAugOptionBEmoluments = totalAugOptionBEmoluments;
	}
	public long getTotalDecOptionAEmoluments() {
		return totalDecOptionAEmoluments;
	}
	public void setTotalDecOptionAEmoluments(long totalDecOptionAEmoluments) {
		this.totalDecOptionAEmoluments = totalDecOptionAEmoluments;
	}
	public long getTotalDecOptionBEmoluments() {
		return totalDecOptionBEmoluments;
	}
	public void setTotalDecOptionBEmoluments(long totalDecOptionBEmoluments) {
		this.totalDecOptionBEmoluments = totalDecOptionBEmoluments;
	}
	public long getTotalFebOptionAEmoluments() {
		return totalFebOptionAEmoluments;
	}
	public void setTotalFebOptionAEmoluments(long totalFebOptionAEmoluments) {
		this.totalFebOptionAEmoluments = totalFebOptionAEmoluments;
	}
	public long getTotalFebOptionBEmoluments() {
		return totalFebOptionBEmoluments;
	}
	public void setTotalFebOptionBEmoluments(long totalFebOptionBEmoluments) {
		this.totalFebOptionBEmoluments = totalFebOptionBEmoluments;
	}
	public long getTotalJanOptionAEmoluments() {
		return totalJanOptionAEmoluments;
	}
	public void setTotalJanOptionAEmoluments(long totalJanOptionAEmoluments) {
		this.totalJanOptionAEmoluments = totalJanOptionAEmoluments;
	}
	public long getTotalJanOptionBEmoluments() {
		return totalJanOptionBEmoluments;
	}
	public void setTotalJanOptionBEmoluments(long totalJanOptionBEmoluments) {
		this.totalJanOptionBEmoluments = totalJanOptionBEmoluments;
	}
	public long getTotalJulOptionAEmoluments() {
		return totalJulOptionAEmoluments;
	}
	public void setTotalJulOptionAEmoluments(long totalJulOptionAEmoluments) {
		this.totalJulOptionAEmoluments = totalJulOptionAEmoluments;
	}
	public long getTotalJulOptionBEmoluments() {
		return totalJulOptionBEmoluments;
	}
	public void setTotalJulOptionBEmoluments(long totalJulOptionBEmoluments) {
		this.totalJulOptionBEmoluments = totalJulOptionBEmoluments;
	}
	public long getTotalJunOptionAEmoluments() {
		return totalJunOptionAEmoluments;
	}
	public void setTotalJunOptionAEmoluments(long totalJunOptionAEmoluments) {
		this.totalJunOptionAEmoluments = totalJunOptionAEmoluments;
	}
	public long getTotalJunOptionBEmoluments() {
		return totalJunOptionBEmoluments;
	}
	public void setTotalJunOptionBEmoluments(long totalJunOptionBEmoluments) {
		this.totalJunOptionBEmoluments = totalJunOptionBEmoluments;
	}
	public long getTotalMarOptionAEmoluments() {
		return totalMarOptionAEmoluments;
	}
	public void setTotalMarOptionAEmoluments(long totalMarOptionAEmoluments) {
		this.totalMarOptionAEmoluments = totalMarOptionAEmoluments;
	}
	public long getTotalMarOptionBEmoluments() {
		return totalMarOptionBEmoluments;
	}
	public void setTotalMarOptionBEmoluments(long totalMarOptionBEmoluments) {
		this.totalMarOptionBEmoluments = totalMarOptionBEmoluments;
	}
	public long getTotalMayOptionAEmoluments() {
		return totalMayOptionAEmoluments;
	}
	public void setTotalMayOptionAEmoluments(long totalMayOptionAEmoluments) {
		this.totalMayOptionAEmoluments = totalMayOptionAEmoluments;
	}
	public long getTotalMayOptionBEmoluments() {
		return totalMayOptionBEmoluments;
	}
	public void setTotalMayOptionBEmoluments(long totalMayOptionBEmoluments) {
		this.totalMayOptionBEmoluments = totalMayOptionBEmoluments;
	}
	public long getTotalOctOptionAEmoluments() {
		return totalOctOptionAEmoluments;
	}
	public void setTotalOctOptionAEmoluments(long totalOctOptionAEmoluments) {
		this.totalOctOptionAEmoluments = totalOctOptionAEmoluments;
	}
	public long getTotalOctOptionBEmoluments() {
		return totalOctOptionBEmoluments;
	}
	public void setTotalOctOptionBEmoluments(long totalOctOptionBEmoluments) {
		this.totalOctOptionBEmoluments = totalOctOptionBEmoluments;
	}
	public long getTotalSepOptionAEmoluments() {
		return totalSepOptionAEmoluments;
	}
	public void setTotalSepOptionAEmoluments(long totalSepOptionAEmoluments) {
		this.totalSepOptionAEmoluments = totalSepOptionAEmoluments;
	}
	public long getTotalSepOptionBEmoluments() {
		return totalSepOptionBEmoluments;
	}
	public void setTotalSepOptionBEmoluments(long totalSepOptionBEmoluments) {
		this.totalSepOptionBEmoluments = totalSepOptionBEmoluments;
	}
    
	
}
