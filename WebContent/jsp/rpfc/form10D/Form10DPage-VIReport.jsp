<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.*"%>

<%@ page import="java.util.ArrayList" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AAI</title>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
</head>
<%
	ArrayList formList=new ArrayList();
	ArrayList familyDetails=new ArrayList();
	ArrayList nomieeDetails=new ArrayList();
	ArrayList pensionDetails=new ArrayList();
	CommonUtil commonUtil=new CommonUtil();
	EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
	if(request.getAttribute("perForm10DList")!=null){
		formList=(ArrayList)request.getAttribute("perForm10DList");
	}
	personalInfo=(EmployeePersonalInfo)formList.get(0);
	familyDetails=(ArrayList)formList.get(1);
	nomieeDetails=(ArrayList)formList.get(2);
	pensionDetails=(ArrayList)formList.get(3);
%>
<body>
<table  width="100%"align="center" cellpadding="0" cellspacing="0" border="0"> 

	 <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
        <td>&nbsp;</td>
        <td width="7%" rowspan="2"><img src="<%=basePath%>/images/logoani.gif" width="100" height="50" /></td>
        <td>AIRPORTS AUTHORITY OF INDIA</td>
      </tr>
       <tr>
        <td width="38%">&nbsp;</td>
        <td width="55%">&nbsp;</td>
      </tr>
    
      <tr>
        <td colspan="3" align="center" class="reportlabel">(TO BE FILLED IN BY THE EMPLOYEER </td>
      </tr>
	  <tr>
        <td colspan="3" align="center" class="reportlabel">AUTHORISED OFFICER OF THE ESTABLISHMENT)</td>
      </tr>
	
    </table></td>
  </tr>
<tr>
	<td>&nbsp;</td>
</tr>
  <tr>
  	<td >
		<table align="center" width="100%" height="50" cellpadding="1" cellspacing="1" border="0">
			<tr>	
				<td colspan="2" class="reportsublabel">Certified that:</td>		
				
			</tr>
			
				<tr>	
				<td colspan="2" class="reportsublabel">(i)  The particulars of the members are correct</td>		
				
			</tr>
			<tr>	
				<td colspan="2" class="reportsublabel">(ii) The particulars of Wages and Pension Contribution for the period of 12 months preceding the date of leaving service are as under:-</td>		
				
			</tr>
					<tr>	
					<td width="2%">&nbsp;&nbsp;</td>
				<td width="98%" class="reportsublabel">(In case,the wages is not earned for all 12 months,the block of 12 months will commence backwards from the last drawn).</td>		
				
			</tr>
			
	  </table>	
	</td>
  </tr>
  <tr>
	<td>&nbsp;</td>
</tr>
  <tr>
  	<td>
		<table align="center" width="80%" height="50" cellpadding="1" cellspacing="1" border="1">
			<tr>
				<td align="center" class="reportsublabel"  width="7%" rowspan="2">Year</td>
				<td align="center" class="reportsublabel" width="8%" rowspan="2">Month</td>
				<td align="center" class="reportsublabel" colspan="2">Wages</td>
				<td align="center" class="reportsublabel" width="16%" rowspan="2">Pension Contribution Due</td>
				<td align="center" class="reportsublabel" colspan="2">Details of period of non-contributory service.If there is no such period</td>
			</tr>
			<tr>
				<td align="center" class="reportsublabel" width="11%">No.of Days</td>
				<td align="center"  class="reportsublabel"width="12%">Amount</td>
				<td align="center" class="reportsublabel" width="13%">Year</td>
				<td align="center" class="reportsublabel" width="33%">No.of days for which  no wages earned</td>
			</tr>
			<tr>
				<td align="center" class="reportsublabel">(1)</td>
				<td align="center" class="reportsublabel">(2)</td>
				<td align="center" class="reportsublabel">(3)</td>
				<td align="center" class="reportsublabel">(4)</td>
				<td align="center" class="reportsublabel">(5)</td>
				<td align="center" class="reportsublabel">(6)</td>
				<td align="center" class="reportsublabel">(7)</td>
			</tr>
			<%if(pensionDetails.size()!=0){
				EmployeePensionCardInfo pensionInfo=new EmployeePensionCardInfo();
				int month=0,year=0,tempYear=0;
				String shownYear="";
				for(int i=0;i<pensionDetails.size();i++){
				pensionInfo=(EmployeePensionCardInfo)pensionDetails.get(i);
				year=Integer.parseInt(commonUtil.converDBToAppFormat(pensionInfo.getMonthyear(),"dd-MMM-yyyy","yyyy"));
				month=Integer.parseInt(commonUtil.converDBToAppFormat(pensionInfo.getMonthyear(),"dd-MMM-yyyy","MM"));
				if(tempYear==0){
					tempYear=year;
					shownYear=Integer.toString(tempYear);
				}else if(tempYear==year){
					shownYear="&nbsp;";
				}else if(tempYear!=year){
					shownYear=Integer.toString(year);
					tempYear=year;
				}
			%>
			<tr>
				<td align="center" class="Data" nowrap="nowrap"><%=shownYear%></td>
				<td align="center" class="Data" nowrap="nowrap"><%=commonUtil.converDBToAppFormat(pensionInfo.getMonthyear(),"dd-MMM-yyyy","MMMM")%></td>
				<td align="center" class="Data"><%=commonUtil.GetDaysInMonth(month,year)%></td>
				<td align="center" class="Data" nowrap="nowrap"><%=pensionInfo.getEmoluments()%></td>
				<td align="center" class="Data" nowrap="nowrap"><%=Math.round(Double.parseDouble(pensionInfo.getPensionContribution()))%></td>
				<td align="center" class="Data">&nbsp;</td>
				<td align="center" class="Data">&nbsp;</td>
			</tr>
			<%year=0;month=0;}}else{%>
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<%}%>
		</table>
	</td>
  </tr>
  <tr>
  	<td colspan="2" class="reportsublabel">1) Documents as given in the Instructions.</td>
	
  </tr>
  <tr>
  	<td colspan="2" class="reportsublabel">2) Form of desctriptive roll and specimen signature.</td>
  </tr>
  <tr>
  	<td colspan="2">&nbsp;</td>
  </tr>
    <tr>
  	<td colspan="2">&nbsp;</td>
  </tr>
   <tr>
  	<td colspan="2" align="right" class="reportsublabel">Signature of Employer/Authorised Official of</td>
  </tr>
     <tr>
  	<td colspan="2" align="right" class="reportsublabel">The Establishment with Seal & Date</td>
  </tr>
</table>
</body>
</html>
