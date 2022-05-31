<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>

<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>
<bean:define  property="appDate" name="proposal" id="appDate" />
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
			cmp =compareDates('<%=appDate%>',document.forms[0].approveDate.value);
			if (cmp == "larger"){
				alert("Approval Date  Should Be Grater Than  Or Equal To The <%=appDate%> Date");
				document.forms[0].approveDate.focus();
				return false;
			}	
		}
  		</script>
	</head>
	<body onload="document.forms[0].approveDate.focus();">
		<html:form action="/approvalformfilling.do?method=approvalUpdate&mode=rbiauction" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("applicationform.title")%>
			
			<table width="550" border=0 align="center" cellpadding="4">
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
								<td colspan=6>
									<b><bean:write property="trustType" name="proposal" />
								</td>
							</tr>
							<tr>
								<td colspan=6>
									<b>Amount invested during <bean:write name="finYear" scope="request"/> As On <SCRIPT>var date = new Date(); document.write(FormatDate(date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getYear()))</SCRIPT>											
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
				<bean:define property="flags" name="proposal" id="flag" type="java.lang.String" />
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
						<hr style="height:1px; color: black">
					</td>
				</tr>
			
	</logic:equal>
	<logic:notEqual value="0" name="approval" property="key">
		<logic:notEqual value="3" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.FormFillingAppBean" />
			<logic:equal value="A" name="invBean" property="approved">
				
				<logic:notEqual value="<%=flag%>" name="approval" property="key">
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
						<hr style="height:1px; color: black">
					</td>
				</tr>
				</logic:notEqual>	
				<logic:equal value="<%=flag%>" name="approval" property="key">
				<logic:equal value="Y" name="edit" >
				<bean:define  property="date" name="invBean"  id="date" type="java.lang.String"/>
				<bean:define id="rem1" name="invBean" property='remarks' type="String"></bean:define>
				<tr>
					<td  align=left nowrap>
							<table>
								<tr class="tableTextBold">
									<td><bean:message key="proposal.approvedate" bundle="common" /> : </td>
									<td align="left">
										<html:text property="approveDate"  value="<%=date%>" styleClass="TextField" styleId="approveDate" style='width:150 px' tabindex="3" maxlength="12" />
											<html:link href="javascript:showCalendar('approveDate');" tabindex="4">
												<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
											</html:link>
									</td>
								</tr>
								<tr class="tableTextBold">
									<td><bean:message key="proposal.remarks" bundle="common" /> : </td>
									<td><html:textarea property="approvalRemarks"  cols="50" rows="3" onkeypress="if(!TxtAreaMaxLength(this.value,999)){this.focus()}" onblur="if(!TxtAreaMaxLength(this.value,999)){this.focus()}" value="<%=rem1%>"></html:textarea></td>
								</tr>
							</table>
						</td>					
					</tr>
				<tr>
				</logic:equal>
				<logic:notEqual value="Y" name="edit" >
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
						<hr style="height:1px; color: black">
					</td>
				</tr>
				</logic:notEqual>
				</logic:equal>
			</logic:equal>	
		</logic:notEqual>		
	</logic:notEqual>
	<logic:notEqual value="Y" name="edit" >
	<logic:equal value="3" name="approval" property="key">
		
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.FormFillingAppBean" />
		
				<tr>
					<td  align=left nowrap>
							<table>
								<tr class="tableTextBold">
									<td><bean:message key="proposal.approvedate" bundle="common" /> : </td>
									<td align="left">
										<html:text property="approveDate" styleClass="TextField" styleId="approveDate" style='width:150 px' tabindex="3" maxlength="12" />
											<html:link href="javascript:showCalendar('approveDate');" tabindex="4">
												<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
											</html:link>
									</td>
								</tr>
								<tr class="tableTextBold">
									<td><bean:message key="proposal.remarks" bundle="common" /> : </td>
									<td><html:textarea property="approvalRemarks"  cols="50" rows="3" onkeypress="if(!TxtAreaMaxLength(this.value,999)){this.focus()}" onblur="if(!TxtAreaMaxLength(this.value,999)){this.focus()}"></html:textarea></td>
								</tr>
							</table>
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
	</logic:equal>		</logic:notEqual>
</logic:iterate>
<html:hidden property="updatedFlag" name="proposal"/>
<html:hidden property="flags" name="proposal"/>
<html:hidden property="formCd" name="proposal"/>
<bean:define name="edit" id="edit" type="java.lang.String"/>
<html:hidden property="edit" value="<%=edit%>" />
				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" value="Approve" />
						&nbsp;
						<html:button styleClass="butt" property="Clear" value="Clear" onclick="clearData();" />
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchFormFilling.do?method=searchFormFilling'" onblur="document.forms[0].securityName.focus();" />
					</td>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

