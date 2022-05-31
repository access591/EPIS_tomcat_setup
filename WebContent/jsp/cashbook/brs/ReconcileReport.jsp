<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
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
	<% boolean bool=true;%>
	<body background="body">
	<logic:notEmpty  name="bankList">
	<logic:iterate id="bank" name="bankList" >
		<TABLE>
		<% if(!bool){%>
		<TR ><td class='breakhere'></td> </TR>
		<% }else {
		bool=false;
		}%>
		<TR>

	<% isFound=false;%>	
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			
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
									<bean:write name="bank"  property="trustType"/> TRUST
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
									Reconciliation Statement
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
						<table width="80%" border="0" cellpadding="0" cellspacing="0" align="center" class="border">
							<thead>
							<tr>
								<td class="label" align="center" width="10%" nowrap>
									<bean:message key="voucher.date" bundle="common" />
								</td>
								<td class="label" align="center" width="10%" nowrap>
									<bean:message key="voucher.no" bundle="common" />
								</td>
								<td class="label" align="center" width="30%" nowrap>
									Details
								</td>
								<td class="label" align="center" width="5%" nowrap>
									<bean:message key="voucher.type" bundle="common" />
								</td>
								<td class="label" align="center" width="10%" nowrap>
									<bean:message key="voucher.debit" bundle="common" />
								</td>
								
								<td class="label" align="center" width="10%" nowrap>
									<bean:message key="voucher.credit" bundle="common" />
								</td>
		
							</tr>
							</thead>
							<bean:define name="bank" id="blist" property="accountNo" type="java.lang.String"></bean:define>
							<logic:notEmpty  name="datalist">
							<logic:iterate id="voucher" name="datalist">	
							<logic:equal name="voucher" property="accountNo" value="<%=blist%>">						
							<% isFound=true;%>
							<tr>
								<td class="Data" align="left" width="10%">
									<bean:write name="voucher"  property="voucherDt"/>
								</td>
								<td class="Data" align="left" width="10%">
									<bean:write name="voucher"  property="voucherNo"/>
								</td>
								
								<td class="Data" align="left" width="30%">
									<bean:write name="voucher"  property="details"/>
								</td>
								<td class="Data" align="left" nowrap width="10%">
									<bean:write name="voucher"  property="voucherType"/>
								</td>
								
								<td class="Data" align="left" width="5%">
									<bean:write name="voucher"  property="debitAmount"/>
								</td>
								
								<td class="Data" align="left" width="10%">
									<bean:write name="voucher"  property="creditAmount"/>
								</td>
							
							</tr>	
							</logic:equal>							
							</logic:iterate>	
							<%if(isFound==false){%>
							<tr>
								<td class="tbb" colspan="6" align="center" >
									<font color="red" size="4" >
										<bean:message key="common.norecords" bundle="common" />
									</font>
								</td>
							</tr>
							<%}%>	
							</logic:notEmpty>
							<logic:empty name="datalist">
							<tr>
								<td class="tbb" colspan="6" align="center" >
									<font color="red" size="4" >
										<bean:message key="common.norecords" bundle="common" />
									</font>
								</td>
							</tr>
							</logic:empty>														
						</table>
					</td>
				</tr>
				<tr>
					<td style="height: 1cm">
					</td>
				</tr>
		</table>
		</TR>
		</TABLE>
		</logic:iterate>	
		</logic:notEmpty>
	</body>
</html>
