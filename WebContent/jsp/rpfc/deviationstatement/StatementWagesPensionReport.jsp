<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo"%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AAI</title>
 <link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">  
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
   <%
   		  	ArrayList cardReportList=new ArrayList();
	  	String reportType="",fileName="",dispDesignation="",chkRegionString="",chkStationString="";
	  	String arrearInfo="",orderInfo="";
	  	int size=0,finalInterestCal=0,noOfmonthsForInterest=0;

	  	CommonUtil commonUtil=new CommonUtil();
	  	if(request.getAttribute("cardList")!=null){
		   
	    String dispYear="";
 	
    	
	   
   
    if(request.getAttribute("region")!=null){
    	chkRegionString=(String)request.getAttribute("region");
    }
    if(request.getAttribute("airportCode")!=null){
      chkStationString=(String)request.getAttribute("airportCode");
    }


   
	  	cardReportList=(ArrayList)request.getAttribute("cardList");
	  	EmployeeCardReportInfo cardReport=new EmployeeCardReportInfo();
	  	size=cardReportList.size();

	  	if (request.getAttribute("reportType") != null) {
			reportType = (String) request.getAttribute("reportType");
			if (reportType.equals("Excel Sheet")
							|| reportType.equals("ExcelSheet")) {
					
						fileName = "Statement_Wages_PC_Report_FYI("+dispYear+").xls";
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition",
								"attachment; filename=" + fileName);
					}
		}
	  
	  	ArrayList dateCardList=new ArrayList();
	
  		EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
  			if(size!=0){
		for(int cardList=0;cardList<cardReportList.size();cardList++){
		cardReport=(EmployeeCardReportInfo)cardReportList.get(cardList);
		personalInfo=cardReport.getPersonalInfo();
		
		dateCardList=cardReport.getPensionCardList();

		

       
   %>
   <tr>
   <td>
   <table width="100%" height="490" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   
    <td width="120" rowspan="3" align="center"><img src="<%=basePath%>/images/logoani.gif" width="88" height="50" align="right" /></td>
    <td class="reportlabel" nowrap="nowrap">AIRPORTS AUTHORITY OF INDIA</td>
    	<td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
     	<td width="96">&nbsp;</td>
     	<td width="95">&nbsp;</td>
     	<td width="85">&nbsp;</td>
  	 	<td width="384"  class="reportlabel">&nbsp;</td>
  	 	<td width="87">&nbsp;</td>
    	<td width="272">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>

    <td colspan="7" class="reportlabel"  align="center">Statement Of Wages & Pension Contribution For The Period Of 12 Months Preceding The Date Of Leaving </td>
  </tr>

  <tr>
    <td colspan="7">&nbsp;</td>
  </tr>

  <tr>
    <td colspan="7"><table width="90%" border="1"  align="center" cellspacing="0" cellpadding="0">
      <tr>
        <td  class="label">1. Account No.: </td>
        <td  class="Data">DL/36478/<%=personalInfo.getPensionNo()%></td>
       	<td  class="label">6. Emp No.: </td>
        <td  class="Data"><%=personalInfo.getEmployeeNumber()%></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="label">2. Name/Surname:</td>
        <td class="Data"><%=personalInfo.getEmployeeName().toUpperCase()%></td>
        <td class="label">7. Statutory Rate of Contribution </td>
		
        <td class="Data">1.16% and 8.33%</td>
		
      </tr>
      <tr>
        <td class="label">3. Father's/Husband's Name:</td>
        <td class="Data"><%=personalInfo.getFhName().toUpperCase()%></td>
        <td class="label">8. Date of Commencement of<br/> membership of EPS :</td>
		
        <td class="Data"><%=personalInfo.getDateOfEntitle()%></td>
		
      </tr>
       <tr>
        
        <td nowrap="nowrap" class="label">4. Date Of Birth:</td>
        <td class="Data"><%=personalInfo.getDateOfBirth()%></td>
         <td class="label">9.Unit:</td>
        <td class="Data" nowrap="nowrap"><%=personalInfo.getRegion().toUpperCase()%>/<%=personalInfo.getAirportCode().toUpperCase()%></td>
      </tr>
		
      <tr>
        
        <td nowrap="nowrap" class="label">5. Name &amp; Address of the Establishment:</td>
        <td class="Data">Airports Authority Of India,<br/>Rajiv Gandhi Bhawan,Safdarjung Airport,New Delhi-3</td>
         <td class="label">10. Voluntary Higher rate of employees'cont.,if any:</td> 
         <td class="label">&nbsp;</td> 
      </tr>
	
    </table></td>
  </tr>

  <tr>
    <td colspan="7"><table width="90%" align="center"  border="1" cellspacing="0" cellpadding="0">
    
      <tr>
        <td width="4%" rowspan="2" class="label">Year</td>
        <td width="8%" rowspan="2" class="label">Month</td>
        <td width="8%" colspan="2" class="label" align="center">Wages</td>
        <td width="3%" rowspan="2" class="label" align="center">Pension Contribution</td>
        <td width="10%" colspan="2" class="label">Details of period of non_contributory service,if thereis no such period indicate "NIL"</td>
        <td width="10%" rowspan="2" class="label">Remarks</td>
      </tr>
      <tr>
       
        <td  class="label" align="center">No.Of Days</td>
        <td class="label" align="center">Amount</td>
         <td class="label" align="center">Year</td>
        <td  class="label" align="center">No. of days for  which no wages were earned</td>
       
       
      </tr>
      <tr>
        <td class="Data" align="center">1</td>
        <td class="Data" align="center">2</td>
        <td class="Data" align="center">3</td>
        <td class="Data" align="center">4</td>
        <td class="Data" align="center">5</td>
        <td class="Data" align="center">6</td>
 		<td class="Data" align="center">7</td>
        <td class="Data" align="center">8</td>
      </tr>
    
        <%
       	DecimalFormat df = new DecimalFormat("#########0.00");
       	DecimalFormat df1 = new DecimalFormat("#########0");
		EmployeePensionCardInfo pensionCardInfo=new EmployeePensionCardInfo();
  		int month=0,year=0,tempYear=0;
		String shownYear="";
		double grandEmoluments=0.00,grandPension=0.00;
  		if(dateCardList.size()!=0){
  		for(int i=0;i<dateCardList.size();i++){
  		pensionCardInfo=(EmployeePensionCardInfo)dateCardList.get(i);
  		year=Integer.parseInt(commonUtil.converDBToAppFormat(pensionCardInfo.getMonthyear(),"dd-MMM-yyyy","yyyy"));
		month=Integer.parseInt(commonUtil.converDBToAppFormat(pensionCardInfo.getMonthyear(),"dd-MMM-yyyy","MM"));
				if(tempYear==0){
					tempYear=year;
					shownYear=Integer.toString(tempYear);
				}else if(tempYear==year){
					shownYear="&nbsp;";
				}else if(tempYear!=year){
					shownYear=Integer.toString(year);
					tempYear=year;
				}
		grandEmoluments=grandEmoluments+Double.parseDouble(pensionCardInfo.getEmoluments());
		grandPension=grandPension+Double.parseDouble(pensionCardInfo.getPensionContribution());
  	%>
<tr>
	<td align="center" class="Data"><%=shownYear%> </td>
	<td align="center" class="Data"><%=commonUtil.converDBToAppFormat(pensionCardInfo.getMonthyear(),"dd-MMM-yyyy","MMMM")%></td>
	<td align="center" class="Data"><%=commonUtil.GetDaysInMonth(month,year)%></td>
	<td align="center" class="Data"><%=pensionCardInfo.getEmoluments()%></td>
	<td align="center" class="Data"><%=Math.round(Double.parseDouble(pensionCardInfo.getPensionContribution()))%></td>
	<td align="center" class="Data">&nbsp;</td>
	<td align="center" class="Data">&nbsp;</td>
	<td align="center" class="Data">&nbsp;</td>
</tr>
  	
<%}%>
<tr>
	<td align="center" class="Data" colspan="3">Total</td>
	<td align="center"  class="Data"><%=df1.format(grandEmoluments)%></td>
	<td align="center" class="Data" ><%=df1.format(grandPension)%></td>
	<td align="center" class="Data" colspan="3">&nbsp;</td>
</tr>
<%}%>
	</table>
	</td>
	</tr>	
	</table>
	</td>
	</tr>		
   <%	}}}%>

	
					
</table>

</body>
</html>
