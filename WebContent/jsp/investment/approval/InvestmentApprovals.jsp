<%@ page language="java" import="java.util.*,com.epis.bean.investment.QuotationBean,com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.epis.action.investment.ArrangersAction" %>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ page import="java.util.SortedSet" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>
<bean:define  property="appDate" name="quotationbean" id="appDate" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
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
		
		function clearData(){
	       		window.document.forms[0].reset();
	  		}
  		</script>
	</head>
	<body onload="document.forms[0].approveDate.focus();">
		<html:form action="/approvalinvestment.do?method=approvalUpdate" onsubmit="return validate();">
		<%=ScreenUtilities.screenHeader("app.title")%>
	      <%com.epis.utilities.SQLHelper sql = new com.epis.utilities.SQLHelper();%>	
		<table width="550" border=0 align="center" cellpadding="4">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="ytm.letterno" bundle="common" /></b>
						:					
						<bean:write property="letterNo" name="quotationbean" />
					</td>					
				</tr>
				<tr>
					<td>
					<logic:present name="compArray">
                        
                         <table width=100% border=1 cellpadding=2 cellspacing=0 >
                        <%  int arrSize = ((Integer)session.getAttribute("arrSize")).intValue();
                            int secSize = ((Integer)session.getAttribute("secSize")).intValue();
                            String[][] comp =(String[][])session.getAttribute("compArray");
                        for(int i=0;i<secSize;i++){
                        %>
                        <tr>
                       
                        <%
                         	for(int j=0;j<arrSize;j++){
                         	%>
                         	<td  class='<%=(i==0)?"tableSideTextBold":"tableText"%>' rowspan='<%=(i==0&&(j==0 || j==1 || j==arrSize-1))?2:1%>'>
                         		<%=StringUtility.checknull(comp[i][j]).equals("")?"--":StringUtility.checknull(comp[i][j])%>                         		
                         	</td>                         	
                         	<%                         	
                         	}
                         	%></tr>                         	
                         	<%
                         	if(i==0){
                         	%>
                         	<TR>
                         	<%
                         		int l=1;
                         		for(int k=2;k<arrSize-1;k++){
                         			%>
                         			<td class="tableSideTextBold" align="center">(<%=l++%>)<br> rates(Rs.)/YTM% </td>                         			
                         			<%
                         		}
                         		%>
                         	</TR>
                         	<%
                         	}
                         }
						%>
                            </table>
                           
												
					</td>
				</tr>
				</logic:present>	
							
				
<bean:define property="flags" name="quotationbean" id="flag" type="java.lang.String" />
<logic:iterate id="approval" name="quotationbean" property="approvals"> 
	<logic:equal value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.QuotationAppBean" />
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
		<logic:notEqual value="8" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.QuotationAppBean" />
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
	<logic:equal value="8" name="approval" property="key">
		
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.QuotationAppBean" />
		
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

<html:hidden property="updatedFlag" name="quotationbean"/>
<html:hidden property="quotationCd" name="quotationbean"/>
<bean:define name="edit" id="edit" type="java.lang.String"/>
<html:hidden property="edit" value="<%=edit%>" />
				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" value="Approve" />
						&nbsp;
						<html:button styleClass="butt" property="Clear" value="Clear" onclick="clearData();" />
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchConfirmationCompany.do?method=searchConfirmationCompany'" onblur="document.forms[0].letterNo.focus();" />
					</td>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>


