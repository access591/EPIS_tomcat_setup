<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.ScreenUtilities" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>

<%@ page import="com.epis.bean.rpfc.EmployeeValidateInfo"%>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo"%>
<%@ page import="com.epis.bean.rpfc.RegionBean"%>
<%@ page import="com.epis.services.rpfc.FinancialService"%>
<%@ page import="java.util.ArrayList"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<html>
	<HEAD>
	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	

	</HEAD>
	<body>
		<form action="method">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
					<td colspan="5" align="center" width="100%">
						<table border=0 cellpadding=0 cellspacing=0 width="100%" align="center" valign="middle">
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="4" align="center" class="reportlabel">
									<b>FORM-3(EPS)</b>
									
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center" class="reportlabel">
									(For Exempted Establishments only)
									
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center" class="reportlabel">
									THE EMPLOYEES PENSION SCHEME , 1995
									
								</td>
							</tr>
									<tr>
								<td colspan="4" align="center" class="reportlabel">
									[Paragraph 20(1)]
									
								</td>
							</tr>
							
							<tr>
								<td colspan="4" align="center" class="reportlabel">
									CONSOLIDATED RETURN OF EMPLOYEES WHO ARE ENTITLED AND REQUIRED TO BECOME
									<br />
									MEMBER OF THE PENSION FUND ON THE DATE THE PENSION SCHEME COMES INTO FORCE.
								</td>
							</tr>

						</table>
					</td>
				</tr>
			</table>
			<%String reportType = "", sortingOrder = "", airportCode = "", frmAirportCode = "", tempFileName = "", fileName = "";
			int srlno = 0;
			
			
			if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
						tempFileName = "All_Regions";
					fileName = "form-3(PS)_" + tempFileName + "_report.xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
			
			

			
			

				
				CommonUtil commonUtil=new CommonUtil();
				ArrayList dataList = new ArrayList();
				EmployeePersonalInfo formsetBean = null;
				String formDate = "", retiredDate = "";
				
				formDate = (String) request.getAttribute("selectedDate");

				retiredDate = (String) request.getAttribute("displayDate");
				dataList=(ArrayList)request.getAttribute("form3PSList");
				
			

					if (dataList.size() != 0) {

						%>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">

						<!-- 	<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
										
											<td class="reportsublabel" width="10%">
												Month/Year:
											</td>
											<td class="reportdata" align="left">
												<%--<%=request.getAttribute("displayDate")%>--%>
											</td>
										

										</tr>
									
									</table>
								</td>
							</tr>-->
					
							<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td class="reportsublabel" width="28%">
												Name & Address of Establishment
											</td>
											<td class="reportdata">
												Airports Authority Of India,
												<br />
												Rajiv Gandhi Bhawan,Safdarjung Airport,New Delhi-3
											</td>
											<td class="reportsublabel" align="right" width="20%">
												Date of Coverage:
											</td>
											<td class="reportdata">
												01-Apr-1995
											</td>
										</tr>

									</table>
								</td>
							</tr>

							<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td class="reportsublabel" width="28%">
												Industry in which the        
											</td>
											<td class="reportdata">
												Civil Aviation
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<br/>
											</td>
											<td class="reportsublabel" align="right" width="28%">
												Code No. of the
											</td>
											<td class="reportdata">
												&nbsp;&nbsp;&nbsp;DL/36478
												<br/>
											</td>
										</tr>
								
									
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td class="reportsublabel" width="28%">
												Establishment is engaged :
											</td>
											<td class="reportdata" width="51%">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											<td class="reportsublabel"  width="29%">
												Establishment :
											</td>
											<td class="reportdata">
												&nbsp;&nbsp;&nbsp;
												<br/>
											</td>
										</tr>
								
									</table>
								</td>
							</tr>
								<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td class="reportsublabel" width="28%">
												Registration No. of the
												<br>
												Establishment :
											</td>
											<td class="reportdata">
											</td>
											
										</tr>
								
									</table>
								</td>
							</tr>
							
						</table>
					</td>


				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="100%" border="1" bordercolor="gray" cellpadding="2" cellspacing="0">

						
							<tr>
								<td class="label" >Sl. No.</td>
								<td class="label">Account No</td>
								<td class="label">Name of the employee (in block capitals)</td>
								<td class="label" >Fathers name or <br/>Husband's name  <br/>( in case of married women)</td>
								<td class="label" >Basic wages, D.A. <br/>including cash value <br/>of food concession and retaining allowance,if any</td>
								<td class="label" >Age at Entry(Years)</td>
								<td class="label" nowrap="nowrap">Date of birth  </td>
								<td class="label" >Sex</td>
								<td class="label" >Date of entitlement<br> for membership</td>
								<td class="label" >Remarks</td>
								<!-- <td class="label" >Airport</br> Name</td>-->
								<!--<td class="label" >Region</td>-->
								
							</tr>
							

							<%for (int k = 0; k < dataList.size(); k++) {
							formsetBean = (EmployeePersonalInfo) dataList.get(k);
							srlno++;
							
							%>
						<tr>
								<td class="Data" width="2%">
									<%=srlno%>
								</td>
								<td class="Data" width="12%">
									<%="DL/36478/"+commonUtil.leadingZeros(5,formsetBean.getPensionNo())%>
								</td>

								<td class="Data" width="20%">
									<%=formsetBean.getEmployeeName().toUpperCase()%>
								</td>
								
								<td class="Data" width="15%">
									<%=formsetBean.getFhName().trim()%>
								</td>
								<td class="Data" width="10%">
									<%=formsetBean.getBasicWages()%>
								</td>
								<td class="Data" width="10%">
									<%=formsetBean.getAgeAtEntry()%>
								</td>
								<td class="Data" width="18%">
									<%=formsetBean.getDateOfBirth()%>
								</td>
							
								<td class="Data" width="3%">
									<%=formsetBean.getGender()%>
								</td>
								<td class="Data" width="15%">
									<%=formsetBean.getDateOfEntitle()%>
								</td>
							
								<td class="Data" width="15%">
									<%=formsetBean.getRemarks()%>
								</td>
								<!-- <td class="Data" width="15%">
									<%--<%=formsetBean.getAirportCode()%>--%>
								</td>-->
								<!--<td class="Data" width="15%">
								<%--	<%=formsetBean.getRegion()%>--%>
								</td>-->
							</tr>
								<%}%>
						</table>

					</td>
				</tr>
				
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<%}%>
			
				<tr>
					<td>
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label">
									Date:
									</br>
									Station :
								</td>
							</tr>
							<tr>
								<td colspan="4" align="right" class="label">
									Signature of the employer or other
									</br>
									authorised officer of the Establishment
								</td>
							</tr>
								<tr>
								<td colspan="4" align="right" class="label">
									Stamp of the Establishment
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>



			</table>
		</form>
	</body>
</html>
