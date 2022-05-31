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

	<table width="70%" border=0 align="center" cellpadding="4">
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
						</td></tr>
						<tr>
					<td align="center" >
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="confirmationcompany.report.title" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>						
						</td></tr>
				<tr>
					<td colspan=4 align=center>
						<html:errors />
					</td>
				</tr>
				<tr>					
					<td  align=right nowrap>
						<b><bean:message key="jv.date" bundle="common" /></b>
						:					
						<bean:write property="proposaldate" name="confirmationbean" />
					</td>
				</tr>

				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subject" bundle="common" /></b>
						:					
						<bean:write property="subject" name="confirmationbean" />
					</td>					
				</tr>
				<tr>
					<td>
						<table border=1 class='border' width="100%">
							<tr>
								<td colspan=4 align="center">
									<b><bean:write property="trustType" name="confirmationbean" />
								</td>
							</tr>
							<tr>
								<td colspan=4 align="center">
									<b>Amount invested during <bean:write name="confirmationbean" property="amountInv"/> As On <SCRIPT>var date = new Date(); document.write(FormatDate(date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getYear()))</SCRIPT>											
								</td>
							</tr>
							<tr>
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.proposalrefno" bundle="common"/>
							</td>
							<td align="left" width="25%">
							<bean:write property="refNo" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.trusttype" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="trustType" name="confirmationbean"/>
							</td>
						</tr>
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcompany.securitycategory" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="securityCategory" name="confirmationbean"/>
							</td>
						
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.markettype" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="marketTypedef" name="confirmationbean"/>
							</td>
							
						</tr>
						
						
						<tr>
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcompany.letterno" bundle="common"/>
							</td>
							<td align="left"  width="25%" nowrap>
							<bean:write property="letterNo" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.confirmed" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="confirmationDef" name="confirmationbean"/>
							</td>
							
						</tr>	
							
							
						
						
						<tr>
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.dealdate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="dealDate" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.settlementDate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="settlementDate" name="confirmationbean"/>
							</td>
							
							
							
						</tr>
						
						
						<tr>
						
						<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.maturityDate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="maturityDate" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.facevalue" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="faceValue" name="confirmationbean"/>
							</td>
						</tr>
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.cuponrate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="cuponRate" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.noofbonds" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="noofBonds" name="confirmationbean"/>
							</td>
							
							
						</tr>
						
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.purchasepriceoption" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="purchaseoptionDef" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.premiumpaidperunit" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="premiumPaid" name="confirmationbean"/>
							</td>
							
							
						</tr>
						
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.offeredprice" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="offeredPrice" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.purchaseprice" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="purchasePrice" name="confirmationbean"/>
							</td>
							
							
						</tr>
						
						
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcomapny.ytm" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="ytmValue" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcomapny.putcalloption" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="calloptionDef" name="confirmationbean"/>
							</td>
							
							
						</tr>
						
						
						<tr>
							
							<td class="tblabel" align="right" width="25%" nowrap>
							<bean:message key="confirmationcompany.interestdate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="interestDate" name="confirmationbean"/>
							</td>
							
							<td class="tblabel" align="left" width="25%" nowrap>
							<bean:message key="confirmationcompany.calldate" bundle="common"/>
							</td>
							<td align="left"  width="25%">
							<bean:write property="callDate" name="confirmationbean"/>
							</td>
							
							
						</tr>
							
								
							
						</table>						
					</td>
				</tr>	
				<tr>
					<td  align="left" wrap>
						<%com.epis.utilities.SQLHelper sql = new com.epis.utilities.SQLHelper();%>
						<bean:define id="rem" name="confirmationbean" property='remarks' type="String"></bean:define>
						<pre><%=sql.wrapTextArea(rem,90)%></pre>
					</td>					
				</tr>				
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subplease" bundle="common" /> : </b>
					</td>					
				</tr>
<logic:iterate id="approval" name="confirmationbean" property="approvals" > 
	<logic:equal value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.ConfirmationFromCompanyAppBean" />
				<tr>
					<td align="right">
						<table border=0>
							<tr>
								<td align="right" class="label" >	
									<img  src="<bean:write name="invBean" property="signPath"/>" />
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									(<bean:write name="invBean" property="displayName"/>)
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									<bean:write name="invBean" property="designation"/>
								</td>										
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<hr style="height:1px; color: black" />
					</td>
				</tr>
			
	</logic:equal>
	<logic:notEqual value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.ConfirmationFromCompanyAppBean" />
			<logic:equal value="A" name="invBean" property="approved">
				<tr>					
					<td  align=right nowrap>
						<b><bean:message key="jv.date" bundle="common" /></b>
						:					
						<bean:write property="date" name="invBean" />
					</td>
				</tr>
				<tr>
					<td  align="left" wrap>
						<bean:define id="rem1" name="invBean" property='remarks' type="String"></bean:define>
						<pre><%=sql.wrapTextArea(rem1,90)%></pre>
					</td>					
				</tr>
				<bean:define id="appCount1" name="confirmationbean" property="appCount" type="java.lang.String"></bean:define>
				<logic:notEqual value="<%=appCount1%>" name="approval" property="key">
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subplease" bundle="common" /> : </b>
					</td>					
				</tr>
				</logic:notEqual>
				<tr>
					<td align="right">
						<table border=0>
							<tr>
								<td align="right" class="label" >	
									<img  src="<bean:write name="invBean" property="signPath"/>" />
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									(<bean:write name="invBean" property="displayName"/>)
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									<bean:write name="invBean" property="designation"/>
								</td>										
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<hr style="height:1px; color: black" />
					</td>
				</tr>
			</logic:equal>	
		</logic:notEqual>		
</logic:iterate>
			</table>
		
	</body>
</html>
