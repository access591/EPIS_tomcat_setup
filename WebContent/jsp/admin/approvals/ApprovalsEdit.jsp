
<%@ page language="java"  import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>Admin - Approval Edit Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">			
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].approvalName.value==""){
		     alert("Approval Name is Mandatory");
		     document.forms[0].approvalName.focus();
		     return false;
		    }
		    return true; 
		 }
		
		</SCRIPT>
	</head>
	<body onload="document.forms[0].approvalName.focus();" >
	<html:form action="/editApproval.do?method=updateApproval" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("approvals.title")%>
	<table width="70%" border=0 align="center" >
		<tbody >
			<tr>
				<td colspan=4 align=center>
					<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="approvals.approvalname" bundle="common"/>:
				</td>
				<td width="20%" ><html:hidden property="approvalCode" name="approvals"/>
					<html:text size="12" property="approvalName" name="approvals" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="200" />		
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="approvals.description" bundle="common"/>:
				</td>
				<td width="20%" >
					<html:text size="12" property="description" name="approvals" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="300" />		
				</td>			
			</tr>			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center>
			<html:submit    tabindex="3"  styleClass="butt" > <bean:message key="button.save" bundle="common"/></html:submit>
			<html:reset  property="Reset"  styleClass="butt"  tabindex="4" ><bean:message key="button.reset" bundle="common"/></html:reset>
			<html:button  property="Cancel" tabindex="5" styleClass="butt" tabindex="5" onclick="javascript:window.location.href='searchApproval.do?method=searchApproval'" onblur="document.forms[0].approvalName.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>			
			</tbody>
	</table>
	</html:form>
	<%=ScreenUtilities.screenFooter()%>
	</body>
</html>
