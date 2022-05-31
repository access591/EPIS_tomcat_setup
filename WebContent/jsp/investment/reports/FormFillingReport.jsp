<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/aai.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/GeneralFunctions.js"></script>
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript">
		
  		</script>
	</head>
	<body >
		
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
							<th class="tblabel"><bean:message key="formfilling.title" bundle="common" /></th>
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
						<bean:write property="proposaldate" name="proposal" />
					</td>
				</tr>

				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subject" bundle="common" /></b>
						:					
						<bean:write property="subject" name="proposal" />
					</td>					
				</tr>
				<tr>
					<td>
						<table border=1 class='border' width="100%">
							<tr>
								<td colspan=6 align="center">
									<b><bean:write property="trustType" name="proposal" />
								</td>
							</tr>
							<tr>
								<td colspan=6 align="center">
									<b>Amount invested during <bean:write name="proposal" property="investAmt"/> As On <SCRIPT>var date = new Date(); document.write(FormatDate(date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getYear()))</SCRIPT>											
								</td>
							</tr>
							<tr>
								<td class="label" align="left"><bean:message key="formfilling.trusttype" bundle="common" /> </td>
								<td class="label" align="left"><bean:message key="formfilling.securitycategory" bundle="common" /> </td>
								<td class="label" align="left"><bean:message key="formfilling.markettype" bundle="common" /> </td>
								<td class="label" align="left"><bean:message key="formfilling.securityname" bundle="common" /> </td>
								<td class="label" align="left"><bean:message key="formfilling.noofbonds" bundle="common" /> </td>
								<td class="label" align="left"><bean:message key="formfilling.statueoftaxoption" bundle="common" /> </td>
							</tr>
							<tr>
								<td class="label" align="left"><bean:write property="trustType" name="proposal"/></td>
								<td class="label" align="left"><bean:write property="securityCategory" name="proposal"/></td>
								<td class="label" align="left"><bean:write property="marketType" name="proposal"/></td>
								<td class="label" align="left"><bean:write property="securityName" name="proposal"/></td>
								<td class="label" align="left"><bean:write property="noofBonds" name="proposal"/></td>
								<td class="label" align="left"><bean:write property="statueoftaxOption" name="proposal"/></td>
							</tr>
							
								
							
						</table>						
					</td>
				</tr>	
				<tr>
					<td  align="left" wrap>
						<%com.epis.utilities.SQLHelper sql = new com.epis.utilities.SQLHelper();%>
						<bean:define id="rem" name="proposal" property='remarks' type="String"></bean:define>
						<pre><%=sql.wrapTextArea(rem,90)%></pre>
					</td>					
				</tr>				
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subplease" bundle="common" /> : </b>
					</td>					
				</tr>
<logic:iterate id="approval" name="proposal" property="approvals"> 
	<logic:equal value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.FormFillingAppBean" />
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
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.FormFillingAppBean" />
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
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subplease" bundle="common" /> : </b>
					</td>					
				</tr>
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

