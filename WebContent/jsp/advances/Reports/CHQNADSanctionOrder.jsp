


<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
String headQuaters="",fileName="",reportType="";					
%>
<%
		if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					fileName = "Consolidated_Report.xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<head>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript">
	</script>
	</HEAD>
	<body>
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<logic:present name="detailList">
				<tr>
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

				<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="11">
							<strong>Ref. No. AAI/CPF/FP/CHQ <!-- <bean:write name="sanctionOrderBean" property="regionLbl"/>--> </strong>
						</td>
						<td align="right">
							<strong>Date &nbsp;<bean:write name="sanctionOrderBean" property="sanctiondt" /> </strong>
						</td>
					</tr>
					<tr>
						<td colspan="12" align="center">
							<strong>SANCTION ORDER</strong>
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

						<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="11" class="reportContentlabel">
									Sub: Final payment of CPF dues.
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>

								<td colspan="11" class="reportContent">
									<p STYLE="text-indent: 1cm">
										Sanction of the president AAI EPF Trust is the hereby conveyed for the final payment of CPF dues in respect of the following employees retiring/retired on attaining the age of superannuation. The detail of CPF dues is as
										follows:
									</p>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>


						<table width="80%" border="1" bordercolor="black" align="center" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center" class="reportContentlabel">
									S.No
								</td>
								<td align="center" class="reportContentlabel">
									CPF A/C No
								</td>
								<td align="center" class="reportContentlabel">
									Name & Designation S/Ms./Sh .
								</td>
								<logic:equal name="sanctionOrderBean" property="seperationreason" value="Retirement">
									<td align="center" class="reportContentlabel">
										Date of Retirement
									</td>
								</logic:equal>
								<td align="center" class="reportContentlabel">
									Station
								</td>
								<td align="center" class="reportContentlabel">
									Employee Subscription
									<br>
									Rs
								</td>
								<td align="center" class="reportContentlabel">
									AAI Contribution
									<br>
									Rs
								</td>
								<td align="center" class="reportContentlabel">
									Pension Amount Deducted
									<br>
									Rs
								</td>

								<logic:equal name="sanctionOrderBean" property="seperationreason" value="Retirement">
									<td align="center" class="reportContentlabel">
										Net Amount Payable
										<br>
										Rs
										<br>
										(6+7-8)
									</td>
								</logic:equal>

								<logic:notEqual name="sanctionOrderBean" property="seperationreason" value="Retirement">
									<td align="center" class="reportContentlabel">
										Net Amount Payable
										<br>
										Rs
										<br>
										(5+6-7)
									</td>
								</logic:notEqual>
							</tr>

							<tr>
								<td align="center" class="reportContentlabel">
									1
								</td>
								<td align="center" class="reportContentlabel">
									2
								</td>
								<td align="center" class="reportContentlabel">
									3
								</td>
								<logic:equal name="sanctionOrderBean" property="seperationreason" value="Retirement">
									<td align="center" class="reportContentlabel">
										4
									</td>
									<td align="center" class="reportContentlabel">
										5
									</td>
									<td align="center" class="reportContentlabel">
										6
									</td>
									<td align="center" class="reportContentlabel">
										7
									</td>
									<td align="center" class="reportContentlabel">
										8
									</td>
									<td align="center" class="reportContentlabel">
										9
									</td>
								</logic:equal>
								<logic:notEqual name="sanctionOrderBean" property="seperationreason" value="Retirement">
									<td align="center" class="reportContentlabel">
										4
									</td>
									<td align="center" class="reportContentlabel">
										5
									</td>
									<td align="center" class="reportContentlabel">
										6
									</td>
									<td align="center" class="reportContentlabel">
										7
									</td>
									<td align="center" class="reportContentlabel">
										8
									</td>
								</logic:notEqual>

							</tr>


							<logic:iterate id="sorder" name="detailList" indexId="slno">
								<tr>
									<td class="reportContentdata" align="center">
										<%=slno.intValue()+1%>
									</td>
									<td class="reportContentdata">
										<bean:write name="sorder" property="cpfaccno" />
									</td>
									<td class="reportContentdata" align="center">
										<bean:write name="sorder" property="employeeName" />
										,
										<bean:write name="sorder" property="designation" />
									</td>

									<logic:equal name="sanctionOrderBean" property="seperationreason" value="Retirement">
										<td class="reportContentdata" align="center">
											<bean:write name="sorder" property="seperationdate" />
									</logic:equal>

									<td class="reportContentdata" align="center">
										<bean:write name="sorder" property="airportcd" />
										<bean:define id="station" name="sorder" property="airportcd" />
									</td>

									<td align="right" class="reportContentdata">
										<bean:write name="sorder" property="emplshare" />/-
									</td>
									<td align="right" class="reportContentdata">
										<bean:write name="sorder" property="emplrshare" />/-
									</td>
									<td align="right" class="reportContentdata">
										<bean:write name="sorder" property="pensioncontribution" />/-
									</td>
									<td align="right" class="reportContentdata">
										<bean:write name="sorder" property="netcontribution" />/-
									</td>
								</tr>
							</logic:iterate>

						</table>
						<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="11">
									&nbsp;
								</td>
							</tr>

							<!-- <tr>
       		 <td colspan="11" class="reportContentdata">Ad-hoc payment if any, already made may be deducted from this authorization</td>
      	  </tr> 
      	  
      	  <tr>
            <td  colspan="11">&nbsp;</td>            
          </tr>
          
          <tr>
            <td  colspan="11" class="reportContentdata"><p STYLE="text-indent: 1cm">Outstanding dues of AAI/Govt., if any, are to be recovered/ adjusted at your end,
            with due approval of the competent authority subject to receipt of written consent of the 
            ex-official/ Nominee in this regards.</p></td>            
          </tr>-->

							<tr>
								<td colspan="11">
									&nbsp;
								</td>
							</tr>

							<tr>
								<td colspan="11" class="reportContentdata">
									Interest for the financial year 2009-10 has been calculated @ 8.5% provisionally.
								</td>
							</tr>

							<tr>
								<td height="24">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td class="reportContentdata">
									&nbsp;
								</td>
							</tr>
							<logic:notEqual name="sanctionOrderBean" property="paymentdate" value="">
								<tr>
									<td colspan="11" class="reportContentdata">
										Note: Payable on or after
										<bean:write name="sanctionOrderBean" property="paymentdate" />
										.
									</td>
								</tr>
							</logic:notEqual>

						</table>
					</td>
				</tr>

				<tr>

					<td height="22" colspan="2" class="reportsublabel">
						<table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="25%">
									&nbsp;
								</td>
								<td width="25%">
									&nbsp;
								</td>
								<td width="21%">
									&nbsp;
								</td>

								<td width="29%">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td width="25%">
									&nbsp;
								</td>
								<td width="25%">
									&nbsp;
								</td>
								<td width="21%">
									&nbsp;
								</td>

								<td width="29%">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td width="25%">
									&nbsp;
								</td>
								<td width="25%">
									&nbsp;
								</td>
								<td width="21%">
									&nbsp;
								</td>
								<!-- <td width="29%" class="reportContentlabel" align="center">(Authorized Signatory)</td>-->
								<td width="29%" class="reportContentlabel" align="right">
										<img src="<%=basePath%>/images/signatures/CHQNAD_A.H. WADHAVA_00051.gif" border="no" />
								</td>
							</tr>
							<tr>
								<td width="25%">
									&nbsp;
								</td>
								<td width="25%">
									&nbsp;
								</td>
								<td width="21%">
									&nbsp;
								</td>
								<td width="29%" class="reportContentlabel" align="right">
									Sr. Manager (Finance)
								</td>
							</tr>


						</table>

						<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="11">
									&nbsp;
								</td>
							</tr>
							<logic:iterate id="sorder" name="detailList" indexId="slno">
								<tr>
									<td colspan="11" class="reportContentlabel">
										Sh.
										<bean:write name="sorder" property="employeeName" />
										,
										<bean:write name="sorder" property="designation" />
									</td>
								</tr>
							</logic:iterate>
						</table>

						<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="10">
									&nbsp;
								</td>
							</tr>
							<bean:define name="sanctionOrderBean" id="reg" property="region" />
							<%
      	       CommonUtil commonUtil=new CommonUtil();
      	       System.out.println("----reg----"+reg);
      	       String reg1=String.valueOf(reg);
      	       try{
      	         headQuaters=commonUtil.getRegionHeadQuarters(reg1);
      	         System.out.println("----headQuaters----"+headQuaters);
      	       }catch(Exception e){
      	         
      	       }
      	     %>

							<!-- <tr>
       		 <td colspan="10" class="reportContentdata">The General Manager (F&A), AAI,<bean:write name="sanctionOrderBean" property="region"/>,<%=headQuaters%> Airport,<%=headQuaters%> </td>
      	  </tr>        	  
      	  
      	   <bean:define id="airportcodes" property="airportcd" name="sanctionOrderBean"/>
           <tr>
            <td colspan="10" class="reportContentdata"><%=airportcodes%></td>
          </tr>-->




							<tr>
								<td height="24">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td class="reportContentdata">
									&nbsp;
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
				</logic:present>
				<logic:notPresent name="detailList">
					<table width="100%" border="0">
						<tr>
							<td colspan="12" align="center">
								<strong>No Records Found</strong>
							</td>
						</tr>
					</table>
				</logic:notPresent>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>

		</html:form>
	</body>
</html>
