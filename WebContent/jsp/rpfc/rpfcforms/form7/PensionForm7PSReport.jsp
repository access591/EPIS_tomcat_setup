
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.CommonUtil,com.epis.utilities.Constants,java.text.DecimalFormat,com.epis.bean.rpfc.EmployeePersonalInfo"%>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo,com.epis.bean.rpfc.EmployeePensionCardInfo"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
	</head>

	<body>
	<%
	
		String region="",fileHead="",reportType="",fileName="";
		if (request.getAttribute("region") != null) {
			region=(String)request.getAttribute("region");
			if(region.equals("NO-SELECT")){
			fileHead="ALL_REGIONS";
			}else{
			fileHead=region;
			}
		}
		
		if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					fileName = "Form_7(PS)_Report_"+fileHead+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
	
	%>
	<%	
		String dispYear="";
		CommonUtil commonUtil=new CommonUtil();
		if(request.getAttribute("dspYear")!=null){
		dispYear=request.getAttribute("dspYear").toString();
		}
		
	
						ArrayList dataList=new ArrayList();
						ArrayList pensionDataList=new ArrayList();
						boolean seperationFlag=false;
						int count=0,size=0;
						EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
						String remarks="",monthYear="",shnMonthYear="",pensionRemarks="",rstchkYear="",seperationMnth="";
						DecimalFormat df = new DecimalFormat("#########0");
						if(request.getAttribute("chkYear")!=null){
							rstchkYear=(String)request.getAttribute("chkYear");
						}else{
							rstchkYear="1995";
						}
						 
						double totalEmoluments=0.0,totalPension=0.0,dispTotalPension=0.0;
						if(request.getAttribute("form8List")!=null){
							dataList=(ArrayList)request.getAttribute("form8List");
							 size=dataList.size();
							 if(size!=0){
							 System.out.println("list size "+dataList.size());
							for(int i=0;i<dataList.size();i++){
							count++;
							personalInfo=(EmployeePersonalInfo)dataList.get(i);
							if(personalInfo.getRemarks().equals("")){
								remarks="---";
							}else{
								remarks=personalInfo.getRemarks();
								pensionRemarks=remarks;
								remarks="";
								
							}
							System.out.println("seperation date is : "+personalInfo.getSeperationDate());
							if(!personalInfo.getSeperationDate().equals("---")){
								seperationMnth=commonUtil.converDBToAppFormat(personalInfo.getSeperationDate(),"dd-MMM-yyyy","MMM-yyyy");
							}else{
								seperationMnth="---";
							}
							
							System.out.println("EmployeeName"+personalInfo.getEmployeeName()+"Date Of Seperation"+personalInfo.getSeperationDate()+"pensionRemarks=========================="+pensionRemarks);							
							pensionDataList=personalInfo.getPensionList();
							if(pensionDataList.size()!=0){
							
							
			%>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
	
  <tr>
    <td width="16%">&nbsp;</td>
    <td colspan="3" class="reportlabel"><table width="70%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" rowspan="3"><img src="<%=basePath%>/images/logoani.gif" width="87" height="50" align="right" /></td>
        <td width="80%" class="reportlabel"  align="center">FORM-7 PS</td>
      </tr>
      <tr>
        <td nowrap="nowrap" align="center" class="reportsublabel">(FOR EXEMPTED ESTABLISHMENT ONLY)</td>
      </tr>
      <tr>
        <td nowrap="nowrap" align="center" class="reportsublabel">THE EMPLOYEES' PENSION SCHEME, 1995</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>

    <tr>
    <td>&nbsp;</td>
	
    <td colspan="3"  align="center" class="reportsublabel">CONTRIBUTION CARDS FOR MEMBERS FOR THE YEAR <%=dispYear%></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td width="27%">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="2" bordercolor="gray" cellspacing="0" cellpadding="0">
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
		<%if(rstchkYear.equals("1995")){%> 
        <td class="Data">1.16% and 8.33%</td>
		<%}else if (!rstchkYear.equals("1995")){%>
		<td class="Data">8.33%</td>
		<%}%> 
      </tr>
      <tr>
        <td class="label">3. Father's/Husband's Name:</td>
        <td class="Data"><%=personalInfo.getFhName().toUpperCase()%></td>
        <td class="label">8. Date of Commencement of<br/> membership of EPS :</td>
		<%if(!personalInfo.getDateOfEntitle().equals("---")){%>
        <td class="Data"><%=personalInfo.getDateOfEntitle()%></td>
		<%}else{%>
 			<td class="Data">01-Apr-1995</td>

		<%}%>
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
 
      <tr>
       
      </tr>
    </table></td>
 
   
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="1" bordercolor="gray" cellpadding="2" cellspacing="0">
      <tr>
        <td width="13%" align="center" class="label">Month</td>
        <td width="30%" align="center" class="label">Amount of wages, retaining allowance<br />
          if any &amp; DA paid during the month</td>
       	 <td width="23%" align="center" class="label">Contribution to Pension Fund </td>
          <td width="23%" align="center" class="label"> No.of days/ period of non-contributing<br/>service, if any</td>
         <td width="34%" align="center" class="label">Remarks</td>
      </tr>
      
      <tr>
        <td class="label" width="13%" align="center">1</td>
        <td class="label" width="30%" align="center">2</td>
        <td class="label" width="23%" align="center">3</td>
        <td class="label" width="23%" align="center">4</td>
        <td class="label" width="34%"  align="center">5</td>
      </tr>
   
  
    <%
    	EmployeePensionCardInfo cardInfo=new EmployeePensionCardInfo();
    	String form7NarrationRemarks="",finalRemarks="";
    	for(int j=0;j<pensionDataList.size();j++){
    	cardInfo=(EmployeePensionCardInfo)pensionDataList.get(j);
    	monthYear=commonUtil.converDBToAppFormat(cardInfo.getMonthyear(),"dd-MMM-yyyy","MMM-yyyy");
		form7NarrationRemarks=cardInfo.getForm7Narration();
    	if(!seperationMnth.equals("---") && seperationMnth.equals(monthYear)){
	   		 seperationFlag=true;
	    }
       	System.out.println("seperationMnth============="+seperationMnth+"monthYear"+monthYear+seperationFlag+"Contribution Amount"+cardInfo.getPensionContribution());
    	shnMonthYear=cardInfo.getShnMnthYear();
    	if(!cardInfo.getPensionContribution().equals("0.0")){
    		if(!remarks.equals("---") && seperationFlag==true){
    				remarks=pensionRemarks;
    			}else{
    				remarks="---";
    			}
    	}else{
    			if(seperationFlag==true){
    				remarks=pensionRemarks;
    			}else if(seperationFlag==false && !remarks.equals("---")){
    				remarks=pensionRemarks;
    			
    			}else{
    				remarks="---";
    			}
    			
    	}
    	if(!remarks.equals("---")){
    		finalRemarks=form7NarrationRemarks+","+remarks;
    	}else{
    		finalRemarks=form7NarrationRemarks;
    	}
    %>

      <tr>
        <td class="Data" width="13%"><%=shnMonthYear%></td>
        <%if(monthYear.equals("Apr-1995")){%>
         <td class="Data" width="30%" align="right">0</td>
         <%}else{%>
        <td class="Data" width="30%" align="right"><%=Math.round(Double.parseDouble(cardInfo.getEmoluments()))%></td>
        <%}%>
        <td class="Data" width="23%" align="right"><%=Math.round(Double.parseDouble(cardInfo.getPensionContribution()))%></td>
         <td class="Data" width="23%"><%=0%></td>
        <td class="Data" width="34%" nowrap="nowrap"><%=finalRemarks%></td>
      </tr>
      <%
      	remarks="";
      	if(monthYear.equals("Apr-1995")){
      	   	 totalEmoluments=0;
      	}else{
      		 totalEmoluments=totalEmoluments+Math.round(Double.parseDouble(cardInfo.getEmoluments()));
      	}
  		 totalPension=totalPension+Math.round(Double.parseDouble(cardInfo.getPensionContribution()));
  		dispTotalPension=totalPension;
      
      }%>
   

  <tr>
    <td>&nbsp;</td>
    <td class="Data"><%=totalEmoluments%></td>
    <td class="Data"><%=totalPension%></td>
    <td><%=0%></td>
    <td>---</td>
  </tr>
  <tr  bordercolor="white">
  <td colspan="5">
 	Certified that the total amount of contribution indicated in  his / her  card's col.(3)i.e.Rs.<%=totalPension%>  has already been remitted in full in 
  	 F.P. Fund A/C No.10.<br/>
  </td>
    
  </tr>

 <tr  bordercolor="white">
 <td colspan="5">Certified that the difference between the total of the contribution shown under col.(3) of the above table and that
   	arrived at on the total wages shown in<br/> column (2) at the prescribed rate is solely due to the rounding off of
   	contribution to the nearest rupee Under the rules.</td>
    
  </tr>
   <tr bordercolor="white">
    <td  colspan="5">&nbsp;</td>

  
  </tr>
    <tr bordercolor="white">
    <td class="Data">Dated :</td>
    <td colspan="3">&nbsp;</td>
    <td align="right" class="Data" nowrap="nowrap">Signature of the employer<br/>with office seal</td>
  </tr>
   </table>
	
</td>
  </tr>


</table>

</td>

</tr>
  <%if(size-1!=i){%>
						<br style='page-break-after:always;'>
	<%}%>	
</table>

<%totalEmoluments=0.0;totalPension=0.0;seperationFlag=false;remarks="";}%>
<%}}}%>
	</body>
</html>
