

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
					<td align="center" colspan="5">
						<table border=0 cellpadding=3 cellspacing=0 width="100%" align="center" valign="middle">
							<tr>
								<td>
									<img src="<%=basePath%>/images/logoani.gif">
								</td>
								<td class="label" align=center valign="top" nowrap="nowrap">
									<font color='black' size='4' face='Helvetica'> AIRPORTS AUTHORITY OF INDIA </font>
								</td>

							</tr>
						</table>
					</td>
				</tr>
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
									FORM-3(PS)
									<br />
									(paragraph 20(2) of the employees pension scheme, 1995)
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center" class="reportlabel">
									CONSOLIDATED RETURN OF EMPLOYEE WHO ARE ENTITLED AND REQUIRED TO BECOME
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

							<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
										
											<td class="reportsublabel" width="10%">
												Month/Year:
											</td>
											<td class="reportdata" align="left">
												<%=request.getAttribute("displayDate")%>
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
												&nbsp;&nbsp;&nbsp;T.N./DL/36478
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
								<td class="label" >Serial</br> Number</td>
								<td class="label">Pension A/c No</td>
								<td class="label" >CPF ACC No</td>
								<td class="label" >Employee</br> Number</td>
								<td class="label">Employee Name</td>
								<td class="label" >Designation</td>
								<td class="label" >Father's name or <br/>Husband name  <br/>( in case of married women)</td>
								<td class="label" >Basic wages, D.A.</td>
								<td class="label" >Age at Entry(Years)</td>
								<td class="label" nowrap="nowrap">Date of birth  </td>
								<td class="label" nowrap="nowrap">Date of Joining  </td>
								<td class="label" >Sex</td>
								<td class="label" >Date of entitlement<br> for membership</td>
								<td class="label" >Seperation Reason</td>
								<td class="label" >Date of Seperation</td>
								<td class="label" >Pension Option</td>
								<td class="label" >Remarks</td>
								<td class="label" >Airport</br> Name</td>
								<td class="label" >Region</td>
								
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
									<%=formsetBean.getPfID()%>
								</td>
								<td class="Data" width="12%">
									<%=formsetBean.getCpfAccno()%>
								</td>
								<td class="Data" width="9%">
									<%=formsetBean.getEmployeeNumber()%>
								</td>
								<td class="Data" width="20%">
									<%=formsetBean.getEmployeeName()%>
								</td>
								<td class="Data" width="12%">
									<%=formsetBean.getDesignation()%>
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
								<td class="Data" width="18%">
									<%=formsetBean.getDateOfJoining()%>
								</td>
								<td class="Data" width="3%">
									<%=formsetBean.getGender()%>
								</td>
								<td class="Data" width="15%">
									<%=formsetBean.getDateOfEntitle()%>
								</td>
								<td class="Data" width="18%">
									<%=formsetBean.getSeperationReason()%>
								</td>
								<td class="Data" width="18%">
									<%=formsetBean.getSeperationDate()%>
								</td>
								<td class="Data" width="3%">
									<%=formsetBean.getWetherOption()%>
								</td>
								<td class="Data" width="15%">
									<%=formsetBean.getRemarks()%>
								</td>
								<td class="Data" width="15%">
									<%=formsetBean.getAirportCode()%>
								</td>
								<td class="Data" width="15%">
									<%=formsetBean.getRegion()%>
								</td>
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
