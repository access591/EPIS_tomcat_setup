<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.ScreenUtilities" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.EmployeeValidateInfo,com.epis.bean.rpfc.EmployeePersonalInfo,com.epis.bean.rpfc.RegionBean,com.epis.bean.rpfc.form3Bean"%>
<%@ page import="com.epis.services.rpfc.FinancialService"%>
<%@ page import="java.util.ArrayList"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<html>
	<HEAD>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
		<script type="text/javascript">
    function redirectPageNav(navButton,index,region,totalValue){   
    	
		document.forms[0].action="<%=basePath%>validatefinance?method=missingMonthsReportNavigation&navButton="+navButton+"&strtindx="+index+"&total="+totalValue+"&frm_region="+region;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
</script>
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
			String region = "";
			ArrayList regionList = new ArrayList();
			RegionBean regionBean=new RegionBean();
			ArrayList airportList = new ArrayList();
			if (request.getAttribute("region") != null) {
				regionList = (ArrayList) request.getAttribute("region");
			}
			if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
					if (regionList.size() > 1) {
						tempFileName = "All_Regions";
					} else {
						regionBean = (RegionBean) regionList.get(0);
						tempFileName=regionBean.getRegion();
					}
					fileName = "form-3(PS)_" + tempFileName + "_report.xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
			boolean consolidateDsgFlg = false;
			FinancialService financeService = new FinancialService();
			if (request.getAttribute("sortingOrder") != null) {
				sortingOrder = (String) request.getAttribute("sortingOrder");
			}
			if (request.getAttribute("airportcode") != null) {
				frmAirportCode = (String) request.getAttribute("airportcode");
			}
			if (regionList.size() > 1) {
				consolidateDsgFlg = true;
			} else {
				consolidateDsgFlg = false;
			}
			String empNameFlag="false";
			String empName="";
			if (request.getAttribute("empNameFlag") != null) {
				empNameFlag = request.getAttribute("empNameFlag").toString();
			}
			if (request.getAttribute("empName") != null) {
				empName = (String) request.getAttribute("empName");
			}

			for (int rglist = 0; rglist < regionList.size(); rglist++) {
				regionBean = (RegionBean) regionList.get(rglist);
			
				if (frmAirportCode.equals("NO-SELECT")) {
					if(regionBean.getAaiCategory().equals("METRO AIRPORT")){
					
						region = regionBean.getRegion();
						airportList=null;
						airportList=new ArrayList();
						if(rglist==16){
							airportList.add("retired");
						}
						airportList.add(regionBean.getAirportcode());
					}else{
						region = regionBean.getRegion();
						airportList = financeService.getPensionAirportList(region,"");
					
					}
				
					//				New Code Snippet Added
					

				} else {
					region = regionBean.getRegion();
					airportList.add(frmAirportCode);
					//				New Code Snippet Added
					airportList.add("retired");

				}
				
				ArrayList list = new ArrayList();
				ArrayList dataList = new ArrayList();
				form3Bean formsetBean = null;
				String formDate = "", retiredDate = "";
				
				formDate = (String) request.getAttribute("selectedDate");

				retiredDate = (String) request.getAttribute("displayDate");

				boolean flag = false;
					System.out.println("Region"+region+"Size"+airportList.size());
				for (int i = 0; i < airportList.size(); i++) {
					airportCode = (String) airportList.get(i);
					System.out.println("Region"+region+"Airportcode"+airportCode);
					dataList = financeService.financeForm3Report(airportCode,
							formDate, region, retiredDate, sortingOrder,empName,empNameFlag);

					if (dataList.size() != 0) {

						%>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">

			
									
										<%if (consolidateDsgFlg == true) {%>
										<tr>
											<%if (flag != true) {%>
											<td class="reportsublabel" width="10%">
												Month/Year:
											</td>
											<td class="reportdata" align="left">
												<%=request.getAttribute("displayDate")%>
											</td>
											<%}%>

										</tr>
										<%} else if (consolidateDsgFlg == false
								&& regionList.size() == 1) {%>
										<tr>
											<%if (flag != true) {%>
											<td class="reportsublabel" width="10%">
												Month/Year:
											</td>
											<td class="reportdata" align="left">
												<%=request.getAttribute("displayDate")%>
											</td>
											<%}%>

										</tr>
										<%}%>
									</table>
								</td>
							</tr>
							<%if (consolidateDsgFlg == true) {%>
							<%if (flag != true) {%>
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
							<%}%>
							<%} else if (consolidateDsgFlg == false
								&& regionList.size() == 1) {%>
							<%if (flag != true) {%>
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
							<%}
						}%>
						</table>
					</td>


				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="100%" border="1" bordercolor="gray" cellpadding="2" cellspacing="0">

							<%if (consolidateDsgFlg == true) {%>
							<jsp:include page="/jsp/rpfc/rpfcforms/PensionForm3PSReportSubHeading.jsp"></jsp:include>
							<%} else if (consolidateDsgFlg == false
								&& regionList.size() == 1) {%>
							<jsp:include page="/jsp/rpfc/rpfcforms/PensionForm3PSReportSubHeading.jsp"></jsp:include>
							<%}%>

							<%for (int k = 0; k < dataList.size(); k++) {
							formsetBean = (form3Bean) dataList.get(k);
							srlno++;
							
							%>
						<tr>
								<td class="Data" width="2%">
									<%=srlno%>
								</td>
								
								<td class="Data" width="12%">
									<%="DL/36478/"+formsetBean.getAccntNo()%>
								</td>
								
								<td class="Data" width="20%">
									<%=formsetBean.getEmployeeName()%>
								</td>
								
								<td class="Data" width="15%">
									<%=formsetBean.getFamilyMemberName().trim()%>
								</td>
								<td class="Data" width="10%">
									<%=formsetBean.getEmoluments()%>
								</td>
								<td class="Data" width="10%">
									<%=formsetBean.getAgeAtEntry()%>
								</td>
								<td class="Data" width="18%">
									<%=formsetBean.getDateOfBirth()%>
								</td>
							
								<td class="Data" width="3%">
									<%=formsetBean.getSex()%>
								</td>
								<td class="Data" width="15%">
									<%=formsetBean.getDateOfEntitle()%>
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


							<%consolidateDsgFlg = false;
						}%>
						</table>

					</td>
				</tr>
				<%if (consolidateDsgFlg == false
								&& regionList.size() == 1) {%>
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<%}%>
				<%flag = true;
					}
				}
			}%>
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
