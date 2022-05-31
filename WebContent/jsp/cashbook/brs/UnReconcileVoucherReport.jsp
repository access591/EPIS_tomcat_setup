<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<jsp:useBean id="cf" class="com.epis.utilities.CurrencyFormat" scope="request"/>
<%
	if("excel".equals(request.getParameter("reportType"))){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=PartyInfo.xls");
	}
	boolean isFound=false;
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
		<style type='text/css'> .breakhere{ page-break-after: always; }</style>
	</head>
	<body background="body">
	<logic:notEmpty  name="bankList">
	<logic:iterate id="bank" name="bankList">	
		<% isFound=false;%>	
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td align="center">
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
							<tr>
								<td rowspan="5">
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
								<td class="tblabel" align="center" valign="middle" colspan="7">
									<bean:message key="com.aai" bundle="common" />
								</td>
							</tr>
							
							
							
							<tr>
								<td align="center" class="tblabel" colspan="7">
									<bean:write name="bank"  property="trustType"/>TRUST
								</td>
							</tr>
							
							<tr>
								<td align="center" class="tblabel" colspan="7">
									<bean:write name="bank"  property="accountCode"/> &nbsp;
									<bean:write name="bank"  property="bankName"/> 
								</td>
							</tr>
					
							<tr>
								<td align="center" class="tblabel" colspan="7">
									Transaction In Company Book  Not in Bank Statement 					
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
						<table width="70%" border="0" cellpadding="0" cellspacing="0" align="center" class="border">
							<tr>
								<td class="label" align="center" width="10%" nowrap>
									<bean:message key="voucher.date" bundle="common" />
								</td>
								<td class="label" align="center" width="30%" nowrap>
									<bean:message key="voucher.no" bundle="common" />
								</td>								
								<td class="label" align="center" width="5%" nowrap>
									<bean:message key="brs.creditamount" bundle="common" />
								</td>
								<td class="label" align="center" width="10%" nowrap>
									<bean:message key="brs.debitamount" bundle="common" />
								</td>	
		
							</tr>
							<bean:define name="bank" id="blist" property="accountNo" type="java.lang.String"></bean:define>
							<%
								double creditAmt=0.0;
								double debitAmt=0.0;
							%>
							<logic:notEmpty  name="datalist">
							<logic:iterate id="brs" name="datalist">													
							<logic:equal name="brs" property="accountNo" value="<%=blist%>">						
								<% isFound=true;%>					
							<tr>
								<td class="Data" align="left" width="10%">
									<bean:write name="brs"  property="voucherDate"/>
								</td>
								<td class="Data" align="left" width="30%">
									<bean:write name="brs"  property="voucherNo"/>
								</td>
								
								<td class="Data" align="left" width="10%">
									<bean:write name="brs"  property="creditAmt"/>
									<bean:define id="creditprice" name="brs" property="creditAmt" type="java.lang.String"/>
									<bean:define id="debitprice" name="brs" property="debitAmt" type="java.lang.String"/>
								</td>
								<td class="Data" align="left" nowrap width="10%">
									<bean:write name="brs"  property="debitAmt"/>
								</td>	
							</tr>
							<%
								creditAmt+=Double.parseDouble(creditprice);
								debitAmt+=Double.parseDouble(debitprice);
							%>
							
							</logic:equal>							
							</logic:iterate>
							<%if(isFound==true){%>
							<tr>
								<td class="label" align="center" colspan=2>Total</td>
								<td class="Data" align="left"><%=cf.getDecimalCurrency(creditAmt)%></td>
								<td class="Data" align="left"><%=cf.getDecimalCurrency(debitAmt)%></td>
							</tr>
							<%}%>
							<%if(isFound==false){%>
							<tr>
								<td class="tbb" colspan="6" align="center" >
									<font color="red" size="4" >
										<bean:message key="common.norecords" bundle="common" />
									</font>
								</td>
							</tr>
							
							<%}%>	<p class='breakhere'>
							</logic:notEmpty>
							<logic:empty name="datalist">
							<tr>
								<td class="tbb" colspan="6" align="center" >
									<font color="red" size="4" >
										<bean:message key="common.norecords" bundle="common" />
									</font>
								</td>
							</tr>
							<p class='breakhere'>
							</logic:empty>														
						</table>
					</td>
				</tr>
				<tr>
					<td style="height: 1cm">
					</td>
				</tr>
			</thead>
		</table>
		</logic:iterate>	
		</logic:notEmpty>
	</body>
</html>
