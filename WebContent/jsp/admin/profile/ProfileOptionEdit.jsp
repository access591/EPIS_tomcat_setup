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
		<title>Admin - Profile Option Edit Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].optionName.value==""){
		     alert("Option Name is Mandatory");
		     document.forms[0].optionName.focus();
		     return false;
		    }
		    if(document.forms[0].path.value==""){
		     alert("Path is Mandatory");
		     document.forms[0].path.focus();
		     return false;
		    }
		    return true; 
		 }
		
		</SCRIPT>
	</head>
	<body onload="document.forms[0].optionName.focus();" >
	<%=ScreenUtilities.screenHeader("profileoptions.title")%>
	<table width="70%" border=0 align="center" ><html:form action="/editOption.do?method=updateOption" onsubmit="return validate();">
		<tbody >
			<tr>
				<td colspan=4 align=center>
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="common.stars" bundle="common"/> <bean:message key="profileoptions.optionname" bundle="common"/>:
				</td><td width="20%" ><html:hidden property="optionCode" name="option"/>
					<html:text size="12" property="optionName" name="option" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="200" />		
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="profileoptions.description" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="description" name="option" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="300" />		
				</td>			
			</tr>			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="profileoptions.path" bundle="common"/>:
				</td>
				<td width="20%" align="left">
					<html:text size="12" property="path" name="option" styleClass="TextField" style="width:120 px" tabindex="3" maxlength="200" />		
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="profileoptions.optiontype" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:select property="optionType" name="option" styleClass="TextField" style="width:120px" tabindex="4">
					<html:option value="S">Entry Screen</html:option>
					<html:option value="R">Report</html:option>
					<html:option value="P">Payroll</html:option>
					</html:select>
				</td>			
			</tr>	
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit  styleClass="butt"  tabindex="5" ><bean:message key="button.save" bundle="common"/></html:submit>
			<html:reset property="Reset" styleClass="butt" tabindex="6" ><bean:message key="button.reset" bundle="common"/></html:reset>
			<html:button  property="Cancel" tabindex="7" styleClass="butt"  onclick="javascript:window.location.href='searchOption.do?method=searchOption'" onblur="document.forms[0].optionName.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>			
			</tbody></html:form>
	</table>
	<%=ScreenUtilities.screenFooter()%>
	
	</body>
</html>
