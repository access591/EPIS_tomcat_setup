<%@ page language="java" import="java.sql.*"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-c" prefix="c" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int cnt=0;

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
	</head>

	<body background="body">

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td align="center" >
					<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan="5" >
								<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<th class="tblabel" align="center" valign="middle" colspan=7>
								<bean:message key="com.aai" bundle="common" />
							</th>
							</tr>
						</table>
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="investmentRegister.title" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp</td>
							</tr>
						</table>
						</td></tr>
						<tr>
						<td>&nbsp;</td>
						</tr>
						<tr>
						<td>
						<table width="80%" border="1" align="center" cellpadding="0" cellspacing="2" class="border">
						<tr>
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="investmentRegister.proposalrefno" bundle="common"/>
							</td>
							<td align="left" width="25%">
							<bean:write property="refNo" name="registerBean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="investmentRegister.trusttype" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="trustType" name="registerBean"/>
							</td>
						</tr>
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="investmentRegister.securitycategory" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="securityCategory" name="registerBean"/>
							</td>
						
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="investmentRegister.securityname" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="securityName" name="registerBean"/>
							</td>
							
						</tr>
						
						
						<tr>
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="investmentRegister.letterno" bundle="common"/>
							</td>
							<td align="left"  width="25%" nowrap>
							<bean:write property="letterNo" name="registerBean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="investmentRegister.confirmed" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="confirmationDef" name="registerBean"/>
							</td>
							
						</tr>	
							
							
						
						
						<tr>
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="investmentRegister.dealdate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="dealDate" name="registerBean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="investmentRegister.settlementDate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="settlementDate" name="registerBean"/>
							</td>
							
							
							
						</tr>
						
						
						<tr>
						
						<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="investmentRegister.maturityDate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="maturityDate" name="registerBean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="investmentRegister.facevalue" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="faceValue" name="registerBean"/>
							</td>
						</tr>
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.cuponrate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="cuponRate" name="registerBean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.noofbonds" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="noofBonds" name="registerBean"/>
							</td>
							
							
						</tr>
						
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:write property="purchaseOptionDef" name="registerBean"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="premiumPaid" name="registerBean"/>
							</td>
							
							<td class="tblabel"  align="left" width="50%" nowrap>
							<bean:message key="confirmationcomapny.modeofinterest" bundle="common"/>
							</td>
							<td align="left"  width="25%" nowrap>
							<bean:write property="modeOfInterestDef" name="registerBean"/>
							</td>
							
							
							
						</tr>
						
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.offeredprice" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="offeredPrice" name="registerBean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.purchaseprice" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="purchasePrice" name="registerBean"/>
							</td>
							
							
						</tr>
						
						
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.ytm" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="ytmValue" name="registerBean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.putcalloption" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="callOptinDef" name="registerBean"/>
							</td>
							
							
						</tr>
						
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcompany.interestdate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="interestDate" name="registerBean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcompany.calldate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="callDate" name="registerBean"/>
							</td>
							
							
						</tr>
						<tr>
							<td class="tblabel" align="right" width="25%" nowrap>
							Full <bean:message key="lettertobank.securityname" bundle="common"/>
     					</td>
     					
						<td align="left"  width="25%">
							<bean:write property="securityFullName" name="registerBean"/>
							</td>
						<td class="tblabel" align="left" width="25%" nowrap>
							 <bean:message key="lettertobank.isin" bundle="common"/>
     					</td>
     					
						<td align="left"  width="25%">
							<bean:write property="ISIN" name="registerBean"/>
							</td>	
							
						
					</tr>
				
						
						
						
							
						
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
