



<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%
	String headQuaters="",fileName="",reportType="";	
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
					</tr>

					<logic:present name="reportbean1">
						<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">

							<tr>
								<td colspan="10" align="center">
									<strong><bean:write name="reportbean" property="screenTitle" /></strong>
								</td>
							</tr>
							<tr>
								<td colspan="10" align="center">
									<strong>Part Final withdrawal sanctioned by exempted establishment during the year&nbsp;:&nbsp;&nbsp;<bean:write name="reportbean1" property="fromYear" /> - <bean:write name="reportbean1" property="toYear" /></strong>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>

						<tr>
							<td colspan="2">

								<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
									

									<tr>
										<td>
											&nbsp;
										</td>
									</tr>

									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
								</table>
				

								<table width="90%" border="1" class="border" align="center" cellspacing="0" cellpadding="0">
									<thead>
										<tr>
											<td align="center" class="reportContentlabel" width="5%">
												S.No
											</td>
											<td align="center" class="reportContentlabel" width="15%">
												Category 
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												No. of Cases
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												Amount(In Rs. Lakhs)
											</td>										
										</tr>										
									</thead>
									
									<logic:iterate id="sorder" name="advanceReportList" indexId="slno">
									<tr>
											<td align="right" class="reportContentlabel" width="5%">
												1
											</td>
											<td class="reportContentdata" width="15%">
												Illness of member / family members
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												NIL
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>										
										</tr>	
										<thead>
										<tr>
											<td align="right" class="reportContentlabel" width="5%">
												2
											</td>
											<td  class="reportContentlabel" width="15%">
												Housing Advances 
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>										
										</tr>										
									</thead>
									<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td  class="reportContentdata" width="15%">
												Purchasing of dweling site
											</td>
											
											<td align="center" class="reportContentlabel" width="5%">
											
											<bean:write name="sorder" property="purchasesitecount" />
																						
											</td>
											
											<td align="center" class="reportContentlabel" width="5%">
										
											<bean:write name="sorder" property="purchasesiteamount" />
											
											</td>	
																				
										</tr>		
											<tr>
											<td align="center" class="reportContentlabel" width="5%">
											&nbsp;
											</td>
											<td  class="reportContentdata" width="15%">
												Purchasing of dweling House
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="purchasehousecount" />
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="purchasehouseamount" />
											</td>										
										</tr>		
											<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td  class="reportContentdata" width="15%">
												Construction of dweling house
											</td>
											<td align="center" class="reportContentlabel" width="5%">												
												<bean:write name="sorder" property="constructionhousecount" />												
											</td>
											<td align="center" class="reportContentlabel" width="5%">											
												<bean:write name="sorder" property="constructionhouseamount" />
											</td>										
										</tr>		
										<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td  class="reportContentdata" width="15%">
												Renovation/Altration/Addition of dweling house
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="renovationhousecount" />	
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="renovationhouseamount" />	
											</td>										
										</tr>		
										<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td class="reportContentdata" width="15%">
												Repayment of HBA Loan taken from
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="repaymenthbacount" />	
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="repaymenthbaamount" />	
											</td>										
										</tr>		
										<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td  class="reportContentdata" width="15%">
												Acquiring a Flat
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="acquireflatcount" />
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="acquireflatamount" />
											</td>										
										</tr>		
										
										<thead>
										<tr>
											<td align="right" class="reportContentlabel" width="5%">
												3
											</td>
											<td  class="reportContentlabel" width="15%">
												Marriage
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>										
										</tr>										
									</thead>
									
									<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td  class="reportContentdata" width="15%">
												Member's own marriage
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="selfmarriagecount" />
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="selfmarriageamount" />
											</td>										
										</tr>		
										
										<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td  class="reportContentdata" width="15%">
												Member's Dependent marriage
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="dependentmarriagecount" />
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="dependentmarriageamount" />
											</td>										
										</tr>		
										
										<tr>
											<td align="right" class="reportContentlabel" width="5%">
												4
											</td>
											<td  class="reportContentdata" width="15%">
												Withdrawal one year before retirement
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												NIL
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>										
										</tr>		
										
										<tr>
											<td align="right" class="reportContentlabel" width="5%">
												5
											</td>
											<td  class="reportContentdata" width="15%">
												During Temporary closure
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												NIL
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>										
										</tr>		
										
										<tr>
											<td align="right" class="reportContentlabel" width="5%">
												6
											</td>
											<td  class="reportContentdata" width="15%">
												Financing LIC Policy
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												NIL
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>										
										</tr>	
										
										<tr>
											<td align="right" class="reportContentlabel" width="5%">
												7
											</td>
											<td  class="reportContentlabel" width="15%">
												Others
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>										
										</tr>	
										<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td  class="reportContentdata" width="15%">
												Higher Education
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="dependenteducationcount" />
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="dependenteducationamount" />
											</td>										
										</tr>	
										
																			
										<tr>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
											</td>
											<td  class="reportContentlabel" width="15%">
												Total
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												&nbsp;
												<bean:write name="sorder" property="reccount" />
											</td>
											<td align="center" class="reportContentlabel" width="5%">
												<bean:write name="sorder" property="approvedAmt" />
											</td>										
										</tr>	
										
														</logic:iterate>				

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
