
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
		<title>My JSP 'RegionSearch.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<script language="javascript">
		function validate(){
		    if(document.forms[0].userId.value==""){
		     alert("User Id is Mandatory");
		     document.forms[0].userId.focus();
		     return false;
		    }
		    if(document.forms[0].newPassword.value==""){
			     alert("New Password is Mandatory");
			     document.forms[0].newPassword.focus();
			     return false;
		    }
		    if(document.forms[0].confirmPassword.value==""){
		   		 alert("Confirm Password Mandatory");
			     document.forms[0].confirmPassword.focus();
			     return false;
		     }
		     if(document.forms[0].newPassword.value!= document.forms[0].confirmPassword.value){
		   		 alert("New Password and Confirm Password should be same.");
			     document.forms[0].confirmPassword.focus();
			     return false;
		     }
		    return true; 
		 }
		function clearData(){
       		 document.forms[0].userId.value="";
        	 document.forms[0].newPassword.value="";
        	 document.forms[0].confirmPassword.value="";
        	
  		}
		
		</script>
	</head>
	<body  onload="clearData();document.forms[0].userId.focus();">
	<html:form action="/resetPassword.do?method=resetPassword" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("changepassword.title")%>
	<table width="70%" border=0 align="center" >
		<tbody >
			<tr>
				<td colspan=2 align=center>&nbsp;<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="changepassword.userid" bundle="common" />:
				</td><td width="20%" align="left" >
				    <html:select  property="userId" styleClass="TextField" style="width:150 px" tabindex="1" >
						<html:option value="">[Select one]</html:option>
						<html:options collection="userList" property="code" labelProperty="name"/>
					</html:select>
				</td>								
			</tr>
			<tr>					
				<td class="tableTextBold" width="20%" align=right>
					<bean:message key="changepassword.newpassword" bundle="common" />:
				</td>
				<td width="20%" align="left">
					<html:password size="12" property="newPassword" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="30" />
				</td>				
			</tr>
			<tr>					
				<td class="tableTextBold" width="20%" align=right>
					<bean:message key="changepassword.confirmpassword" bundle="common" />:
				</td>
				<td width="20%" align="left">
					<html:password size="12" property="confirmPassword" styleClass="TextField" style="width:120 px" tabindex="3" maxlength="30" />
				</td>				
			</tr>
			<tr>
				<td colspan=2 align=center>&nbsp;
				</td>
			</tr>
			<tr>
				<td  colspan=2 align=center><html:submit   styleClass="butt" tabindex="4" > <bean:message key="button.save" bundle="common"/></html:submit>&nbsp;<html:button styleClass="butt" tabindex="5" property="Clear" tabindex="4" onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button>
				</td>	
			</tr>
			<tr>
				<td colspan=2 align=center>&nbsp;
				</td>
			</tr>			
			</tbody>
	</table>
	<%=ScreenUtilities.screenFooter()%>
	</html:form>
	</body>
</html>
