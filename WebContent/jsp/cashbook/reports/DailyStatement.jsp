<%@ page language="java" pageEncoding="UTF-8" buffer="32kb" %>
<%@ page import="com.epis.bean.cashbook.ReportBean" %>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/tags-fmt" prefix="fmt"%>
<%@ taglib uri="/tags-c" prefix="c" %>
<bean:define id="report" name="reportBean" property="reportType" type="java.lang.String"/>
<%	
	if("excel".equals(StringUtility.checknull(report))){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DailyStatement.xls");	
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="css/aai.css" type="text/css" />
		<link rel="stylesheet" href="css/epis.css" type="text/css" />
	</head>
	<body background="body">
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td align="center">
						<table border="0" cellpadding="3" cellspacing="0" align="center" >
							<tr>
								<td rowspan="3">
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
								<td class="tblabel" align="center" valign="middle" colspan="6">
									<bean:message key="com.aai" bundle="common" />
								</td>
							</tr>
							<tr>
								<td align="center" class="tblabel" colspan="6">
									<bean:message key="ds.empCon.title" bundle="common" />
								</td>
							</tr>
							<tr>
								<td align="center" class="tblabel" colspan="6">
									<bean:message key="dscap.title" bundle="common" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="height: 1cm">
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%">
							<tr>
								<td class="label" align="left" colspan=6>
									<bean:message key="ds.day" bundle="common" />
									: <bean:write name="reportBean" property="day"/>
								</td>
								<td class="label" align="right">
									<bean:message key="jv.date" bundle="common" />: <bean:write name="reportBean" property="fromDate"/>
									<bean:define id="totalRecords" name="reportBean" property="noOfRecords" type="java.lang.Integer"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="height: 1cm">
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="border">
							<tr>
								<td class="label" align="center" rowspan="2">
									<bean:message key="report.slno" bundle="common" />
								</td>
								<td class="label" align="center" rowspan="2">
									<bean:message key="report.particulars" bundle="common" />
								</td>
								<td class="label" align="center" colspan="<%=totalRecords%>">
									<bean:message key="ds.bank" bundle="common" />
								</td>
								<td class="label" align="center" rowspan="2">
									<bean:message key="ds.totdailyposition" bundle="common" />									
								</td>
							</tr>
							<tr>
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="label" align="center" >
										<bean:write name="dStatement"  property="bankName" />
										<BR>
										('<bean:write name="dStatement"  property="accountNo" />)
									</td>												
								</logic:iterate>
							</tr>
							<tr>
								<td class="label" align="left" colspan="<%=Integer.parseInt(String.valueOf(totalRecords))+3%>">&nbsp;</td>								
							</tr>	
							<tr>
								<td class="label" align="left">1.</td>
								<td class="ScreenSubHeading" align="left"><bean:message key="accCode.openingBalance" bundle="common" /></td>
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<bean:write name="dStatement"  property="openingBalance" />
									</td>												
								</logic:iterate>
								<td class="label" align="left"></td>
							</tr>
							<tr>
								<td class="label" align="left" colspan="<%=Integer.parseInt(String.valueOf(totalRecords))+3%>">&nbsp;</td>								
							</tr>	
							<tr>
								<td class="label" align="left">2.</td>
								<td class="ScreenSubHeading" align="left"><bean:message key="ds.addreceipt" bundle="common" /></td>
								<td class="label" align="left" colspan="<%=Integer.parseInt(String.valueOf(totalRecords))+1%>">&nbsp;</td>
								
							</tr>	
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="ds.mob" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.maturityOfBonds+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.maturityOfBonds}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>	
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="ds.iob" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.interstOnBonds+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.interstOnBonds}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>									
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="ds.rob" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.redemptionOfBonds+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.redemptionOfBonds}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>	
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="ds.acc" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.accretion+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.accretion}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="jv.other" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.otherReceipts+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.otherReceipts}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="report.total" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.totalReceipt+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.totalReceipt}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>	
							<tr>
								<td class="label" align="left" colspan="<%=Integer.parseInt(String.valueOf(totalRecords))+3%>">&nbsp;</td>								
							</tr>	
							<tr>
								<td class="label" align="left">3.</td>
								<td class="ScreenSubHeading" align="left"><bean:message key="ds.lesspayment" bundle="common" /></td>
								<td class="label" align="left" colspan="<%=Integer.parseInt(String.valueOf(totalRecords))+1%>">&nbsp;</td>					
							</tr>	
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="ds.final" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.finalPayment+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.finalPayment}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>	
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="ds.partfinal" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.partFinal+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.partFinal}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>									
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="ds.loansadvance" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.loansAndAdvance+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.loansAndAdvance}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>	
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="ds.investment" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.investment+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.investment}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="jv.other" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.otherPayment+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.otherPayment}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>
							<tr>
								<td class="label" align="left"></td>
								<td class="label" align="left"><bean:message key="report.total" bundle="common" /></td>
								<c:set var="total" scope="page" value="0"/> 
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<c:set var='total' value='${dStatement.totalPayment+total}'/>
										<fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${dStatement.totalPayment}'/>
									</td>												
								</logic:iterate>
								<td class="label" align="right"><fmt:formatNumber type="NUMBER" minFractionDigits="2" value='${total}'/>	</td>
							</tr>	
							<tr>
								<td class="label" align="left" colspan="<%=Integer.parseInt(String.valueOf(totalRecords))+3%>">&nbsp;</td>								
							</tr>
							<tr>
								<td class="label" align="left">4.</td>
								<td class="ScreenSubHeading" align="left"><bean:message key="ds.closingBal" bundle="common" /></td>
								<logic:iterate id="dStmt" name="dailyStmt">
									<bean:define id="dStatement" name="dStmt" property="value" type="com.epis.bean.cashbook.DailyStatement"/>
									<td class="Data" align="right" >
										<bean:write name="dStatement"  property="closingBalance" />
									</td>												
								</logic:iterate>
							</tr>												
						</table>
					</td>
				</tr>
			</thead>
		</table>
	</body>
</html>
		
						
			