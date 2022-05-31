 <%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants, com.epis.bean.advances.*" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%
	String headQuaters="",fileName="",reportType="",displayType="";	
	int count=0,srno=0;
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
			if(request.getAttribute("displayType")!=null){
			displayType = (String)request.getAttribute("displayType");
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
					
                    <tr>
                    <td colspan="2">
                    <table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">

						<tr>
							<td colspan="10" align="center">
								<strong> Directorate of Finance & Accounts</strong>
							</td>
						</tr>
						<tr>
							<td colspan="10" align="center">
								<strong> CPF Section</strong>
							</td>
						</tr>
						
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>                    
                    </td>
                    </tr>
					

					<tr>
						<td colspan="2">

							<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								
								

								<tr>
								
								
								    <td colspan="9">
								    <table width="84%" border="0" align="center" cellspacing="0" cellpadding="0">

										<tr>
										   
											<td colspan="12" class="reportdata" align="center">
												 Period From &nbsp;
										<bean:write name="reportbean1" property="fromYear" /> - <bean:write name="reportbean1" property="toYear" />
											</td>
										</tr>
										
										
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
								  </table>
										
									</td>
								</tr>
								<tr>
								<td>&nbsp;</td>
								</tr>
							</table>


							<table width="90%" border="1" class="border" align="center" cellspacing="0" cellpadding="0">
							
							 		<%
									                                    
									AdvanceBasicReportBean bean = new  AdvanceBasicReportBean();
									ArrayList  advanceReportList = new ArrayList();
									advanceReportList = (ArrayList)request.getAttribute("advanceReportList");
									String region="";
									for(int i=0;i<advanceReportList.size();i++){
			
									bean = (AdvanceBasicReportBean)advanceReportList.get(i); 
									srno++; 
									
									if(!region.equals(bean.getRegionLbl())){
									count=0; 
									srno=1;
									}
									
									 System.out.println("====displayType==="+displayType+"==count==="+count);
									if((displayType.equals("regionwise") && (count==0))  || (displayType.equals("notregionwise") && (count==0) )){									
									 count =1;
									 region = bean.getRegionLbl();
									%>
									
										<tr>
										<td align="left" class="reportsublabel" width="10%" colspan="20">
											Region:	 <%=bean.getRegionLbl()%>
											</td>
										 	 
									
										</tr>
									
							
								<tr>
									<td align="center" class="reportsublabel" width="5%">
										S.No
									</td>
									<td align="center" class="reportsublabel" width="15%">
										Date
									</td>
									<td align="center" class="reportsublabel" width="5%">
										Emp. No
									</td>
									<td align="center" class="reportsublabel" width="5%">
										PF ID
									</td>
									<td align="center" class="reportsublabel" width="5%">
										CPF No
									</td>
									<td align="center" class="reportsublabel" width="30%">
										Name & Designation
									</td>
									<td align="center" class="reportsublabel" width="5%">
										Place of Posting
									</td>
									<td align="center" class="reportsublabel" width="5%">
										Purpose(Sub Purpose)
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Amount Required
									</td>
									<td align="center" class="reportsublabel" width="10%">
										No. Of Installement
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Amount Per Installment
									</td>
									<td align="center" class="reportsublabel" width="10%">
										No. Of Interest Installement
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Interest Installement Amount
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Amount Sanctioned
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Name In Bank
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Bank Name
									</td>
									<td align="center" class="reportsublabel" width="10%">
										Bank Acc No
									</td>
									<td align="center" class="reportsublabel" width="10%">
										RTGS Code
									</td>
									
								</tr>
								<%}%>
							
									<tr>
										<td class="reportdata">
											<%=srno%>												 
										</td>
										<td class="reportdata" nowrap="nowrap">
										<%=bean.getAdvtransdt()%>
										 </td>
										<td class="reportdata">
										<%=bean.getEmployeeNo()%>
										 </td>
										<td class="reportdata">
										<%=bean.getPfid()%>
										 </td>
										<td class="reportdata" nowrap="nowrap">
										<%=bean.getCpfaccno()%>
										 </td>
										<td class="reportdata">
										<%=bean.getEmployeeName()%>
										 	,
											<label style="white-space: nowrap"><%=bean.getDesignation()%></label>
										</td>
										<td class="reportdata" nowrap="nowrap">
										<%=bean.getPlaceofposting()%>
										 </td>
										<td class="reportdata">
										<%=bean.getPurposeType()%>											 
											(<label style="white-space: nowrap"><%=bean.getPurposeOptionType()%> </label>)
										</td>
										<td align="right" class="reportdata">
										<%=bean.getRequiredAmt()%>
										 </td>
										<td align="right" class="reportdata">
										<%=bean.getCpfIntallments()%>
										 </td>
										<td align="right" class="reportdata">
										<%=bean.getMthinstallmentamt()%>
										 </td>
										<td align="right" class="reportdata">
										<%=bean.getInterestinstallments()%>
										 </td>
										<td align="right" class="reportdata">
										<%=bean.getIntinstallmentamt()%>
										 </td>
										<td align="right" class="reportdata">
										<%=bean.getApprovedAmt()%>										 
										</td>
										<td align="right" class="reportdata">
										<%=bean.getBankempname()%>											 
										</td>
										<td align="right" class="reportdata">
										<%=bean.getBankname()%>										 
										</td>
										<td align="right" class="reportdata">
										<%=bean.getBanksavingaccno()%>											 
										</td>
										<td align="right" class="reportdata">
										<%=bean.getBankemprtgsneftcode()%>											 
										</td>
										

									</tr>
								<%
								
								
								}%>
								
								<tr>
										<td class="reportdata" align="right" colspan="8">
											Grand Total
										</td>
										
										
										<td align="right" class="reportdata">
										<%=bean.getTotalrequiredamt()%>
											 
										</td>
										<td align="right" class="reportdata">
											&nbsp;
										</td>
										<td align="right" class="reportdata">											
										<%=bean.getTotalIntInstallAmt()%>
											 										
										</td>
										<td align="right" class="reportdata">
											&nbsp;
										</td>
										<td align="right" class="reportdata">											
										<%=bean.getTotalIntInstallAmt()%>
											 
										</td>
										<td align="right" class="reportdata">
										<%=bean.getTotalsanctionedamt()%>
											 
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

					<tr>
					<td colspan="2">
						<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="9" class="reportdata">
									It is requestes to recover the advanced from the salary as per details given above commencing from  &nbsp;
									<bean:write name="reportbean1" property="fromYear" /> - <bean:write name="reportbean1" property="toYear" />.
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
					
					<tr>
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
					</tr>
					<!--East REgion -->
			 
			 
				 <%if(displayType.equals("notregionwise")){%>			
				<logic:present  name="userbean">
				<logic:equal name="userbean"  property="profileType" value="R"> 	
					<tr>
					<td colspan="2">
						<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="8" class="reportdata">
									 &nbsp;
									</td>								
									<td width="30%" align="right"  class="reportContentlabel" >
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="userbean" property="esignatoryName" />" width="150" height="48" border="no" alt="" />
            						</td>
								</tr>
								<tr>
									<td colspan="8" class="reportdata">
									 &nbsp;
									</td>
									<td class="reportdata" align="right">
									<bean:write name="userbean" property="displayName"/>
									</td>
								</tr>
								
								<tr>
									<td colspan="8" class="reportdata">
									 &nbsp;
									</td>
									<td class="reportdata" align="right">
									<bean:write name="userbean" property="designation"/>
									</td>
								</tr>
								
								<tr>
									<td colspan="8" class="reportdata">
									&nbsp;
									</td>
									<td class="reportdata" align="right">
									&nbsp;
									</td>
								</tr>
								</table>
								</td>
					</tr>
					</logic:equal>
					
					<logic:equal name="userbean"  property="profileType" value="C"> 		
					<tr>
					<td colspan="2">
						<table width="90%" border="0" align="center" cellspacing="0" cellpadding="0">
								
								<tr>
									<td colspan="8" class="reportdata">
									 &nbsp;
									</td>								
									<td width="30%" align="right"  class="reportContentlabel" >
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="userbean" property="esignatoryName" />" width="150" height="48" border="no" alt="" />
            						</td>
								</tr>
								<tr>
									<td colspan="8" class="reportdata">
									To, The Asstt.General 
									</td>
									<td class="reportdata" align="right">
									<bean:write name="userbean" property="displayName"/>
									</td>
								</tr>
								
								<tr>
									<td colspan="8" class="reportdata">
									Manager(F&A) Cash Section, 
									</td>
									<td class="reportdata" align="right">
									<bean:write name="userbean" property="designation"/>
									</td>
								</tr>
								
								<tr>
									<td colspan="8" class="reportdata">
									Rajiv Gandhi Bhavan 
									</td>
									<td class="reportdata" align="right">
									&nbsp;
									</td>
								</tr>
								</table>
								</td>
					</tr>
					</logic:equal>
				</logic:present>
			<%}%>
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
