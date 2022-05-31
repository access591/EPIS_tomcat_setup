
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.epis.utilities.*,com.epis.bean.rpfc.*,java.text.DecimalFormat"%>


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

		<LINK rel="stylesheet" href="<%=basePath%>PensionView/css/aai.css" type="text/css">
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
				
					fileName = "Form_7_Report_"+fileHead+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
	
	%>
	
		<table width="87%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="16%">&nbsp;</td>
    <td colspan="3" class="reportlabel"><table width="70%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" rowspan="3"><img src="<%=basePath%>PensionView/images/logoani.gif" width="87" height="50" align="right" /></td>
        <td width="80%" class="reportlabel"  align="center">FORM-7</td>
      </tr>
      <tr>
        <td nowrap="nowrap" align="center">(paragraph 20 of the employees pension scheme, 1995)</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
	<%	
		String dispYear="";
		CommonUtil commonUtil=new CommonUtil();
		if(request.getAttribute("dspYear")!=null){
		dispYear=request.getAttribute("dspYear").toString();
		}
		
	%>
	
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
			<%
						ArrayList dataList=new ArrayList();
						ArrayList pensionDataList=new ArrayList();
						int count=0;
						EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
						String remarks="",monthYear="",pensionRemarks="";
						DecimalFormat df = new DecimalFormat("#########0");
						
						double totalEmoluments=0.0,totalPension=0.0;
						if(request.getAttribute("form8List")!=null){
							dataList=(ArrayList)request.getAttribute("form8List");
							for(int i=0;i<dataList.size();i++){
							count++;
							personalInfo=(EmployeePersonalInfo)dataList.get(i);
							if(personalInfo.getRemarks().equals("")){
								remarks="---";
							}else{
								
								remarks=personalInfo.getRemarks();
								pensionRemarks=remarks;
								
							}
							
							pensionDataList=personalInfo.getPensionList();
							if(pensionDataList.size()!=0){
							
							
			%>
  <tr>
    <td colspan="2"><table width="140%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="45%" class="label">1. PF ID : </td>
        <td width="55%" class="Data"><%=personalInfo.getPfID()%></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="label">2. Name (in block capitals):</td>
        <td class="Data"><%=personalInfo.getEmployeeName().toUpperCase()%></td>
      </tr>
      <tr>
        <td class="label">3. Father's/Husband's Name:</td>
        <td class="Data"><%=personalInfo.getFhName().toUpperCase()%></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="label">4. Name &amp; Address of the Establishment:</td>
        <td class="Data">Airports Authority Of India,<br/>Rajiv Gandhi Bhawan,Safdarjung Airport,New Delhi-3</td>
      </tr>
      <tr>
        <td class="label">5. Statutory Rate of Contribution 8.33%</td>
        <td class="Data">&nbsp;</td>
      </tr>
      <tr>
        <td class="label">6.Region/Station:</td>
        <td class="Data"><%=personalInfo.getRegion().toUpperCase()%>/<%=personalInfo.getAirportCode().toUpperCase()%></td>
      </tr>
    </table></td>
    <td width="24%">&nbsp;</td>
    <td width="33%">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="1" bordercolor="gray" cellpadding="2" cellspacing="0">
      <tr>
        <td width="13%" align="center" class="label">Month</td>
        <td width="30%" align="center" class="label">Amount of wages, retaining allowance<br />
          if any &amp; DA paid during the month</td>
        <td width="23%" align="center" class="label">Contribution to Pension Fund  8.33%</td>
        <td width="34%" align="center" class="label">Remarks</td>
      </tr>
      
      <tr>
        <td class="label" align="center">1</td>
        <td class="label" align="center">2</td>
        <td class="label" align="center">3</td>
        <td class="label" align="center">4</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="1" bordercolor="gray" cellpadding="2"cellspacing="0" >
    <%
    	EmployeePensionCardInfo cardInfo=new EmployeePensionCardInfo();
    	for(int j=0;j<pensionDataList.size();j++){
    	cardInfo=(EmployeePensionCardInfo)pensionDataList.get(j);
    	monthYear=commonUtil.converDBToAppFormat(cardInfo.getMonthyear(),"dd-MMM-yyyy","MMM-yyyy");
    	System.out.println("Pension"+cardInfo.getPensionContribution()+"remarks"+remarks);
    	if(!cardInfo.getPensionContribution().equals("0.0")){
    		if(!remarks.equals("---") ){
    			remarks="---";
    		}
    	}else{
    			remarks=pensionRemarks;
    	}
    %>
   
      <tr>
        <td class="Data" width="13%"><%=monthYear%></td>
        <td class="Data" width="30%"><%=Math.round(Double.parseDouble(cardInfo.getEmoluments()))%></td>
        <td class="Data" width="23%"><%=Math.round(Double.parseDouble(cardInfo.getPensionContribution()))%></td>
        <td class="Data" width="34%"><%=remarks%></td>
      </tr>
      <%
     	 totalEmoluments=totalEmoluments+Math.round(Double.parseDouble(cardInfo.getEmoluments()));
  		 totalPension=totalPension+Math.round(Double.parseDouble(cardInfo.getPensionContribution()));
        
      
      }%>
   

  <tr>
    <td>&nbsp;</td>
    <td class="Data"><%=totalEmoluments%></td>
    <td class="Data"><%=totalPension%></td>
    <td>---</td>
  </tr>
   </table></td>
  </tr>
 <%totalEmoluments=0.0;totalPension=0.0;}}}%>
 <tr>
 <td colspan="4">Certified that the difference between the total contributions shown under Column (3) of the above and that<br/>
   	arrived at the total wages shown in column (2) at the prescribed rate is solely due to the rounding off of<br/>
   	contribution to the nearest Rs. Under the rules.</td>
    
  </tr>
  <tr>
  <td colspan="4">
 	Certified that the total amount of contributions indicated under Column (3) has already been remitted in full in <br/>
  	 Account No. 10 (Pension Fund Contribution).<br/>
  </td>
    
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
	</body>
</html>
