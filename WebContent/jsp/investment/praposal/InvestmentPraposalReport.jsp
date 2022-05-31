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
		function validate()
		{
			if(document.forms[0].approveDate.value==''){
				alert("please Enter approveDate Date ");
				document.forms[0].approveDate.select();
				return(false);
			}
			if(!convert_date(document.forms[0].approveDate)){
				document.forms[0].approveDate.select();
				return(false);
			}
			var currentDate = new Date()
			var month = currentDate.getMonth() + 1
			var day = currentDate.getDate()
			var year = currentDate.getFullYear()
			var todaysdate = day + "/" + month + "/" + year ;
			var cmp =compareDates(document.forms[0].approveDate.value,todaysdate);
			if (cmp == "larger"){
				alert("Approval Date  Should Be Less Than  Or Equal To The Current Date");
				document.forms[0].approveDate.focus();
				return false;
			}	
		}
  		</script>
	</head>
	<body onload="document.forms[0].approveDate.focus();">
		<html:form action="/approvalProposal.do?method=approvalUpdate" onsubmit="return validate();">
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
							<th class="tblabel"><bean:message key="proposal.newtitle" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>						
						</td></tr>
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error"  property="proposal"/>
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
					<td  align=left >
						<b>	<bean:message key="proposal.subject" bundle="common" /></b>
						:					
						<bean:write property="subject" name="proposal" />
					</td>					
				</tr>
				<tr>
					<td  align="left" nowrap="nowrap">
						<%com.epis.utilities.SQLHelper sql = new com.epis.utilities.SQLHelper();%>
						<bean:define id="rem" name="proposal" property='remarks' type="String"></bean:define>
						<pre><%=sql.wrapTextArea(rem,90)%></pre>
					</td>					
				</tr>
				<%
				int i=1;
				%>
				
				<bean:define id="securitylist" name="proposal" property="securityDetails" type="java.util.ArrayList"/>
				<logic:iterate id="investdts" name="securitylist" >
				<bean:define id="investdt" name="investdts" type="com.epis.bean.investment.InvestmentProposalDt"/>
				<tr>
				<td align="left"><%=i%>)&nbsp;<bean:write name="investdts" property="securityName"/></td>
				</tr>
				<%
				i++;
				%>
				</logic:iterate>	
				<tr>	
				<tr>
					<td align=left nowrap><b>Investment Projection as follows</b></td>
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
									<b>Amount invested during <bean:write name="finYear" scope="request"/> As On <SCRIPT>var date = new Date(); document.write(FormatDate(date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getYear()))</SCRIPT>											
								</td>
							</tr>
							<logic:iterate id="invDet" name="InvestDetails" > 
								<tr>								
									<logic:iterate id="list" name="invDet" >
									<td align=right>
									&nbsp;<bean:write name="list"/>
									</td>
									</logic:iterate>									
								</tr>
						   	</logic:iterate> 	
						</table>						
					</td>
				</tr>	
							
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subplease" bundle="common" /> : </b>
					</td>					
				</tr>
<logic:iterate id="approval" name="proposal" property="approvals"> 
	<logic:equal value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.InvestProposalAppBean" />
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
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.InvestProposalAppBean" />
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
				<bean:define id="appCount1" name="proposal" property="appCount" type="java.lang.String"></bean:define>
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

		</html:form>
	</body>
</html>

