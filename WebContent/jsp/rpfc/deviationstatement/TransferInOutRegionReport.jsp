
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.*"%>
<%@ page import="com.epis.services.rpfc.FinancialReportService"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.ArrayList" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <LINK rel="stylesheet" href="<%=basePath%>PensionView/css/aai.css" type="text/css">
    <title>AAI</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
     
    <link rel="stylesheet" type="text/css" href="styles.css">
   <link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">   
   
 
  </head>
  
  <body>
<%
String dispYear="",chkStationString="",chkRegionString="",reportType="",fileName="";
FinancialReportService reportService=new FinancialReportService();
    if(request.getAttribute("region")!=null){
    	chkRegionString=(String)request.getAttribute("region");
    }
    if(request.getAttribute("airportCode")!=null){
      chkStationString=(String)request.getAttribute("airportCode");
    }
 if(request.getAttribute("date")!=null){
    dispYear=(String)request.getAttribute("date");
    }
 if (request.getAttribute("reportType") != null) {
		reportType = (String) request.getAttribute("reportType");
		if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					fileName = "PF_CARD_Report_FYI("+dispYear+").xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
	}
 %>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   <td>
   <table width="100%"  cellspacing="0" cellpadding="0">
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="3" class="reportlabel" style="text-decoration: underline" align="center">
Region-Wise Transfer In/Out </td>
  </tr>
<tr >
<td colspan="10" align="left" nowrap="nowrap" class="Data">REGION: <font style="text-decoration: underline"><%=chkRegionString%></td>
<td align="left" nowrap="nowrap" class="Data"><%if(chkRegionString.equals("CHQIAD")){%>
&nbsp;&nbsp;&nbsp;Airportcode :<%=chkStationString %>
<%}%> </td>
<td align="right" nowrap="nowrap" class="Data">&nbsp;Dt: <%=dispYear%></td>
</tr>

  </table>
</td>
</tr>
 <%EmployeePersonalInfo dbBeans = new EmployeePersonalInfo();

            if (request.getAttribute("empinfo") != null) {
                ArrayList datalist = new ArrayList();
                int totalData = 0;
                datalist = (ArrayList) request.getAttribute("empinfo");
                System.out.println("dataList " + datalist.size());
                

                if (datalist.size() == 0) {

                %>
			<tr>

				<td>
					<table align="center" id="norec">
						<tr>
							<br>
							<td>
								<b> No Records Found </b>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<%} else if (datalist.size() != 0) {%>       
			<tr>
				<td height="25%">
					<table width="100%" border="1"  align="center" cellspacing="0" cellpadding="0">
						<tr ><td class="label">
                          SR NO
                        </td>
						<td class="label">PENSION NO
							</td>
							<td class="label">
								EMPLOYEE NAME
							</td>
							<td  class="label">
								DATE OF BIRTH
							</td>
							<td class="label">
								DATE OF JOINING
							</td>
							<td class="label">
								WETHER OPTION
							</td>
							<td class="label">
								TRANSFER STATUS
							</td>
							<!-- <td class="label">
								REGION
							</td>	 -->	
</tr>

						<%}%>
						<%int count = 0;
               
                for (int i = 0; i < datalist.size(); i++) {
                    count++;
                   
                    EmployeePersonalInfo beans = (EmployeePersonalInfo) datalist.get(i);

                   String PENSIONNO = beans.getPfID();
                   String EMPLOYEENAME = beans.getEmployeeName();
                   String DATEOFBIRTH = beans.getDateOfBirth();
                   String DATEOFJOINING = beans.getDateOfJoining();
                   String WETHEROPTION = beans.getWetherOption();
                   String TRANSFERSTATUS = beans.getRemarks();
                   String REGION = beans.getRegion();
                   if (count % 2 == 0) {

                       %>
   						<tr>
   							<%} else {%>
   						<tr>
   							<%}%>
							<td class="Data" width="5%">
								<%=count%>
							</td>
							<td class="Data" width="8%">
								<%=PENSIONNO%>
							</td>
							<td class="Data" width="15%">
								<%=EMPLOYEENAME%>
							</td>
							<td class="Data" width="12%">
								<%=DATEOFBIRTH%>
							</td>
							<td class="Data" width="12%">
								<%=DATEOFJOINING%>
							</td>
							<td class="Data" width="8%">
                              <%=WETHEROPTION%>
                            </td>
							<td class="Data" width="12%">
								<%=TRANSFERSTATUS%>
							</td>
							<!-- <td class="Data">
								<%=REGION%>
							</td>	-->						

						</tr>
			<%}} %>							

					</table>
				</td>

			</tr>

	
					
</table>

  </body>
</html>
