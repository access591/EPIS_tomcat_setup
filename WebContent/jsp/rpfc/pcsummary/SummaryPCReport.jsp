
<%@ page language="java" import="java.util.*,java.text.DecimalFormat,com.epis.bean.rpfc.EmployeePersonalInfo,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" pageEncoding="UTF-8"%>


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
	   CommonUtil commonUtil=new CommonUtil();
		String region="",fileHead="",reportType="",fileName="",dispRemtSelYears="";
		String pensionInfo="",emoluments="",pension="",disPension="",pfval="",pfstat="";
		  DecimalFormat df1 = new DecimalFormat("#########0.00");
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
				
					fileName = "Form_PCSummary_Report_"+fileHead+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
	
	%>
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
  	 	<td width="382"  class="reportlabel" >PC Summary Report</td>
  	 	<td width="89">&nbsp;</td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="3" class="reportlabel" style="text-decoration: underline" align="center">Annual Statement Of Contribution For The Currency</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   

    <td colspan="3" align="center" nowrap="nowrap" class="reportsublabel">Period From <%=request.getAttribute("dspYear")%></td>
  
    
  </tr>
  <tr>
    <td colspan="7">&nbsp;</td>
  </tr>
			<tr>
				<td colspan="7">
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						
						<%
						ArrayList dataList=new ArrayList();
						int count=0;
						EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
					
						ArrayList form8List=new ArrayList();
						if(request.getAttribute("form8List")!=null){
							form8List=(ArrayList)request.getAttribute("form8List");%>
								<tr>
							<td class="label" colspan=12><font color=blue>Name Of Region :&nbsp;<%=request.getAttribute("region")%></font></td>
							
						</tr>
						<tr>
							<td width="6%" class="reportsublabel">
								Sl. No.
							</td>
						
							<td width="17%" class="reportsublabel">
								Account No.
							</td>
						
							<td width="22%" class="reportsublabel">
								Name of Member
								<br>
								(in block letters)</span>
							</td>
							<td width="25%" class="reportsublabel">
								Designation
							</td>
							<td width="25%" class="reportsublabel">
								Date Of Birth
							</td>
							<td width="25%" class="reportsublabel">
								Date Of Joining
							</td>
							<td width="15%" class="reportsublabel">
								Wether Option
							</td>
							<td width="10%" class="reportsublabel">
								Total Emoluments
							</td>
							<td width="10%" class="reportsublabel">
								EPF
							</td>
							<td width="10%" class="reportsublabel">
								Pension Contribution
							</td>
							<td width="10%" class="reportsublabel">
								PF
							</td>
							<td width="10%" class="reportsublabel">
								Station
							</td>
							
						</tr>
					<%
						
						    for(int j=0;j<form8List.size();j++){
						    
							
						
						
							personalInfo=(EmployeePersonalInfo)form8List.get(j);
							if(personalInfo!=null){
								
							pensionInfo=personalInfo.getPensionInfo();

							String [] pensionList=pensionInfo.split(",");
							if(pensionList.length>=2){
							if(!pensionList[0].equals("")){
								emoluments=pensionList[0];
							}else{
								emoluments="0";
							}
							if(!pensionList[1].equals("")){
								pension=pensionList[1];
								
							}else{
								pension="0";
							}
								if(!pensionList[2].equals("")){
								pfval=pensionList[2];
								
							}else{
								pfval="0";
							}
									if(!pensionList[2].equals("")){
								pfstat=pensionList[3];
								
							}else{
								pfstat="0";
							}
							
							}else{
							emoluments="0";
							pension="0";
							pfstat="0";
							pfval="0";
							}
						}
							
							
						%>
					
					
						<%
							count++;
						%>
						<tr>
							<td class="Data"><%=count%></td>
							<td class="Data" nowrap="nowrap"><%=personalInfo.getPfID().toUpperCase()%></td>
							<td class="Data" nowrap="nowrap"><%=personalInfo.getEmployeeName()%></td>
							<td class="Data" nowrap="nowrap"><%=personalInfo.getDesignation().toUpperCase()%></td>
							<td class="Data" nowrap="nowrap"><%=personalInfo.getDateOfBirth()%></td>
						    <td class="Data" nowrap="nowrap"><%=personalInfo.getDateOfJoining()%></td>
							<td class="Data" ><%=personalInfo.getWetherOption()%></td>
							<td class="NumData"><%=emoluments%></td>
							<td class="NumData"><%=pfstat%></td>
							<td class="NumData"><%=pension%></td>
							<td class="NumData"><%=pfval%></td>
							<td class="NumData" nowrap="nowrap"><%=personalInfo.getAirportCode()%></td>
						</tr>
						
						<%}}%>
						
					</table>
				</td>
				</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			
			
			
				
		</table>
	</body>
</html>
