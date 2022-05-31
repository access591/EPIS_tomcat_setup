
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-c" prefix="c" %>
<%@ taglib uri="/tags-fmt" prefix="fmt"%>
<jsp:useBean id="cf" class="com.epis.utilities.CurrencyFormat" scope="request"/>
<jsp:useBean id="sq" class="com.epis.utilities.SQLHelper" scope="request"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int cnt=1;
double GTotalInterest=0.0;
double MaturityGrand=0.0;
String reportType = (String)request.getAttribute("reportType");

if(reportType.equals("html")) {
	response.setContentType("text/html");
}else{
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=InvestmentRegister.xls");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />		
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<title>Investment Register Report</title>
		<script type="text/javascript">
		function openinterestReceived(securityproposal)
		{
			securityproposal=securityproposal.replace(/%/g,'~');
			 securityproposal=securityproposal.replace(/#/g,'ß');
		
			var swidth=screen.Width-300;
				var sheight=screen.Height-400;
				var url = "/investmentReports.do?method=InterestReceivedRegister&&securityProposal="+securityproposal;
				window.open(url,"InterestReceivedReport","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
		}
		</script>
	</head>

	<body background="body">

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			
			<tr>
				<td>
					<table width=100%>
						<thead>
							<tr>
								<td align=center>
									<table>
										<tr>
											<td rowspan="5">
												<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
											</td>
											<td class="tblabel" align="center" valign="middle">
												AIRPORTS AUTHORITY OF INDIA 
											</td>
											<tr>
							<tr>
							<th class="tblabel"><bean:message key="report.investmentregister.title" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>
						
						</td>
							</tr>
						</thead>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
						<td>
						<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="border">
						<tr>
							<td  align="left" valign="middle" class="tblabel" >
								<bean:message key="ytm.srno" bundle="common" />
							</td>
							<td  align="left" valign="middle" class="tblabel" >
							<bean:message key="sq.investmentdate" bundle="common"/>
							</td>
							<td align="left" class="tblabel" >
								<bean:message key="report.nameofsecurity" bundle="common" />
							</td>
							<td align="left" class="tblabel">
							<bean:message key="sq.incentivepayable" bundle="common"/>
							</td>		
							<td align="left" class="tblabel">
							<bean:message key="accCode.openingBalance" bundle="common"/>
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="confirmationcomapny.cuponrate" bundle="common"/>
							</td>
						
							<td align="left" class="tblabel" >
							<bean:message key="confirmationcomapny.ytm" bundle="common" />(%)
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="quotation.facevalueinrs" bundle="common"/>
							</td>
							
							<td align="left" class="tblabel" >
							<bean:message key="sq.noofunits" bundle="common"/>
							</td>
							
							<td  align="left" class="tblabel" align="right">
								<bean:message key="sq.facevalueofinvestment" bundle="common" />(per Unit)
							</td>
							
							<td  align="left" class="tblabel" >
							<bean:message key="sq.purchaseprice" bundle="common" />
							</td>
						
							<td align="left" class="tblabel" >
							<bean:message key="confirmationcomapny.purchaseprice" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="lettertobank.accuredinterestamount" bundle="common"/>
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="report.total" bundle="common"/><br>(In Rs)
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="report.investmentmode" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="investmentRegister.interestdate" bundle="common" />
							</td>
						
							
							
							
							
						
							<td align="center" class="tblabel" colspan=2>
								<bean:message key="report.annualizedinterest" bundle="common" />
							</td>
							<td align="center" class="tblabel">
								<bean:message key="report.interestreceiveddate" bundle="common"/>
							</td>
							<td align="left" class="tblabel">
								<bean:message key="report.interestreceivedamount" bundle="common"/>
							</td>
							
							
							
							<td align="left" class="tblabel" >
								<bean:message key="confirmationcomapny.maturityDate" bundle="common" />
							</td>
							
							<td align="left" class="tblabel" >
								<bean:message key="report.maturityreceiveddate" bundle="common" />
							</td>
							
							<td align="left" class="tblabel">
								<bean:message key="report.maturityreceivedamount" bundle="common"/>
							</td>
							<td align="left" class="tblabel">
							<bean:message key="ds.closingBal" bundle="common"/>								
							</td>
							
							<td align="left" class="tblabel">
							<bean:message key="quotation.remarks" bundle="common"/>								
							</td>
										
						
						
						</tr>
						<c:set var="yearly" value="Yearly"/>
						<c:set var="half" value="Half-Yearly"/>
						
						 <c:forEach var='arranger' items='${arrangerList}'> 
                                 <tr >
                                 <td class="tableText" align="left" nowrap><%=cnt++%></td> 
                                 <td class="tableText" align="left" nowrap><bean:define id="invfacevalue" name="arranger" property="investmentFaceValue" type="java.lang.String"/><bean:define id="facevalue" name="arranger" property="faceValue" type="java.lang.String"/><c:out value='${arranger.investmentdate}'/></td> 
                                 <td class="tableText" align="left"  nowrap><c:out value='${arranger.securityName}'/></td> 
                                 <td class="tableText" align="right" nowrap><c:out value='${arranger.incentivepayable}'/></td>
                                 <td class="tableText" align="right" nowrap><%=cf.getDecimalCurrency(Double.parseDouble(facevalue))%></td>
                                 <td class="tableText" align="right" nowrap><c:out value='${arranger.interestRate}'/></td> 
                                 <td class="tableText" align="right" nowrap><c:out value='${arranger.ytm}'/></td> 
                                 <td class="tableText"  align="right" nowrap><%=cf.getDecimalCurrency(Double.parseDouble(facevalue))%></td> 
                                 <td class="tableText" align="right" nowrap><bean:define id="noofunits" name="arranger" property="numberOfUnits" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(noofunits))%></td> 
                                 <td class="tableText" align="right" nowrap><%=cf.getDecimalCurrency(Double.parseDouble(invfacevalue))%></td> 
                                 <td class="tableText" align="right" nowrap><bean:define id="priceoffer" name="arranger" property="priceoffered" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(priceoffer))%></td> 
                                 <td class="tableText" align="right" nowrap><bean:define id="purchaseprice" name="arranger" property="purchasePrice" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(purchaseprice))%></td> 
                                 <td class="tableText" align="right" nowrap><bean:define id="accuredint" name="arranger" property="accuredInterest" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(accuredint))%></td> 
                                 <td class="tableText" align="right" nowrap><bean:define id="settlementamt" name="arranger" property="settlementAmount" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(settlementamt))%></td>
                                 <td class="tableText" align="left" nowrap><c:out value='${arranger.investmode}'/></td>  
                                 <td class="tableText" align="left" nowrap><c:out value='${arranger.interestDate}'/></td>  
                                <c:if test="${(arranger.investmode==yearly)}">
                                 <td class="tableText"  align="right" colspan=2 nowrap><bean:define id="annualinterest" name="arranger" property="annualInterest" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(annualinterest))%></td>  
                                </c:if>
                                <c:if test="${(arranger.investmode==half)}">
                                <td class="tableText"  align="right"  nowrap><bean:define id="annualinterest" name="arranger" property="annualInterest" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(annualinterest)/2)%></td>  
                                <td class="tableText"  align="right"  nowrap><%=cf.getDecimalCurrency(Double.parseDouble(annualinterest)/2)%></td>  
                                </c:if>
                                
                                 <bean:define id="interestreceivetotal" name="arranger" property="totalInterest" type="java.lang.String"/>
                                 <bean:define id="maturityreceivetotal" name="arranger" property="totalMaturity" type="java.lang.String" />
                                 <%
                                 GTotalInterest=GTotalInterest+Double.parseDouble(interestreceivetotal);
                                 MaturityGrand=MaturityGrand+Double.parseDouble(maturityreceivetotal);
                                 %>
                                 
                                 <td class="tableText" align="right"><bean:define id="interestreceiveddate" name="arranger" property="interestrvincbdate" type="java.lang.String"/><%=sq.newLineAddress(interestreceiveddate)%></td>
                                 <td class="tableText" align="right"><bean:define id="interestreceivedamount" name="arranger" property="interestrvincb" type="java.lang.String"/><%=sq.newLineAddress(interestreceivedamount)%></td>
                                 <td class="tableText" align="left" nowrap><c:out value='${arranger.maturityDate}'/></td>  
                                                                 
                                 <td class="tableText" align="right"><bean:define id="maturityreceiveddate" name="arranger" property="maturityincbdate" type="java.lang.String"/><%=sq.newLineAddress(maturityreceiveddate)%></td>
                                 <td class="tableText" align="right"><bean:define id="maturityreceivedamount" name="arranger" property="maturityincb" type="java.lang.String"/><%=sq.newLineAddress(maturityreceivedamount)%></td> 
                                 <td class="tableText" align="right"><bean:define id="closingbalamt" name="arranger" property="closingBal" type="java.lang.String"/><%=sq.newLineAddress(closingbalamt)%></td> 
                                 <td class="tableText" align="left" nowrap="nowrap"><c:out value='${arranger.remarks}'/></td>      
                               </tr>
                               </c:forEach>
                                <%if(cnt==1){
                               %>
                                <tr>
                               <td class="tableText"  align=center nowrap colspan=25><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}
                               else{
                               %>
                               <tr>
                               <td class="tableText"  align=right nowrap colspan=19>Total</td>
                               <td class="tableText" align="right"><%=cf.getDecimalCurrency(GTotalInterest)%></td>
                               <td class="tableText">&nbsp;</td>
                               <td class="tableText">&nbsp;</td>
                               <td class="tableText"><%=cf.getDecimalCurrency(MaturityGrand)%></td>
                               <td>&nbsp;</td>
                               <td>&nbsp;</td>
                              
                               </tr>
                               <%
                               }
                               %>
					</table>
				</td>
			</tr>		
				
			
		</table>
	</body>
</html>
