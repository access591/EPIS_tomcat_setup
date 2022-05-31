



<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>

<%@ page import="java.text.DecimalFormat" %>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%
	String headQuaters="",fileName="",reportType="";	
	DecimalFormat df=new DecimalFormat("#########0");
		if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					fileName = "Advances_Report.xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
		ArrayList dataList=new ArrayList();
		CommonUtil commonUtil=new CommonUtil();
	     if(request.getAttribute("advanceReportList")!=null){
	     dataList=(ArrayList)request.getAttribute("advanceReportList");
	     }			
	
  %>

<html>
	<head>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript">
	</script>
	</HEAD>
	<body>
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<logic:present name="advanceReportList">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="38%" rowspan="2">
										<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" />
									</td>
									<td>
										<p align="justify">
											<strong> AIRPORTS AUTHORITY OF INDIA</strong>
										</p>
									</td>
								</tr>
								<tr>
									<td class="reportsublabel">Part Final Withdrawal (PFW) Report</td>
					
								</tr>
								<tr>
									<td width="38%" colspan="2" class="reportsublabel" align="center">Summary Report</td>
					
								</tr>
								
							</table>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>

					
	

						<tr>
							<td colspan="2">

								<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
									

									<tr>
										<td class="reportsublabel">
											From : <%=request.getAttribute("FromDate")%>  To :  <%=request.getAttribute("ToDate")%>       
										</td>
										<td class="reportsublabel">Amount in <img src="<%=basePath%>images/rupee-symbol.gif" height="15">
										</td>
									</tr>

								
								</table>
				

								<table width="90%" border="1" class="border" align="center" cellspacing="0" cellpadding="0">
									
										<tr>
											<td align="center" class="label" width="15%">
												Region/Station
											</td>
											<td align="center" class="label" width="8%">
												Marriage 
											</td>
											<td align="center" class="label" width="8%">
												HBA
											</td>
											<td align="center" class="label" width="8%">
												Higher Education
											</td>
											<td align="center" class="label" width="8%">
												Total
											</td>										
										</tr>										
									
									<%
										String information="",region="",marriageinfo="",heinfo="",hbainfo="";
										double totalPFWRegion=0.00,totalMarriage=0.00,totalHe=0.00,totalHBA=0.00,grandTotalPFWRegion=0.00;
										if(dataList.size()!=0){
										for(int i=0;i<dataList.size();i++){
										information=(String)dataList.get(i);
										System.out.println("information"+information);
										String[]infolist=information.split(",");
										if(infolist.length!=0){
											region=infolist[0];
										}
										hbainfo=commonUtil.replaceAllWords2(infolist[1],"HB-","").trim();
										heinfo=commonUtil.replaceAllWords2(infolist[2],"HE-","").trim();
										marriageinfo=commonUtil.replaceAllWords2(infolist[3],"MA-","").trim();
										totalPFWRegion=Double.parseDouble(hbainfo)+Double.parseDouble(heinfo)+Double.parseDouble(marriageinfo);
										totalMarriage=totalMarriage+Double.parseDouble(marriageinfo);
										totalHe=totalHe+Double.parseDouble(heinfo);
										totalHBA=totalHBA+Double.parseDouble(hbainfo);
										grandTotalPFWRegion=grandTotalPFWRegion+totalPFWRegion;
									%>
									<tr>
											<td align="center" class="Data" width="5%">
												<%=region%>
											</td>
											<td align="center" class="Data" width="15%">
												<%=marriageinfo%> 
											</td>
											<td align="center" class="Data" width="5%">
												<%=hbainfo%> 
											</td>
											<td align="center" class="Data" width="5%">
												<%=heinfo%> 
											</td>
											<td align="center" class="Data" width="5%">
												<%=df.format(totalPFWRegion)%>
											</td>										
										</tr>			
									<%}}%>
									<tr>
											<td align="center" class="Data" width="5%">
												Total
											</td>
											<td align="center" class="Data" width="15%">
												<%=df.format(totalMarriage)%> 
											</td>
											<td align="center" class="Data" width="5%">
												<%=df.format(totalHBA)%>
											</td>
											<td align="center" class="Data" width="5%">
											 <%=df.format(totalHe)%> 
											</td>
											<td align="center" class="Data" width="5%">
												<%=df.format(grandTotalPFWRegion)%>
											</td>										
										</tr>	
								</table>
								
							
							</td>
						</tr>

						<tr>
							<td class="screenlabel">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width="50%" class="screenlabel">
								&nbsp;
							</td>
							<td width="50%">
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
						</tr>
				</table>
			
			</logic:present>

			<logic:notPresent name="advanceReportList">
				<table width="100%" height="60%" border="0" cellspacing="0" cellpadding="0" valign="middle">
					<tr>
						<td align="center">
							<strong>No Records Found</strong>
						</td>
					</tr>
				</table>
			</logic:notPresent>
		</html:form>
	</body>
</html>
