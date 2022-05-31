
<%@ page language="java"  import="com.epis.bean.admin.ProfileOptionBean" pageEncoding="UTF-8"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>Admin - Profile</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
			</head>
	<body  onload="document.forms[0].update.focus();">
	<html:form action="/showProfileOptions.do?method=updateProfileOptions" >
	<%=ScreenUtilities.screenHeader("profile.title")%>
	<table width="710" border=0 align="center" class="GridBorder">
		<tbody >
			<tr> 
              <td  align=center bgcolor="#F0FAFF" class="tableSideTextBold"><bean:message key="profile.profiles" bundle="common"/> </td>
              <td align=center align=right bgcolor="#F0FAFF" class="tableSideTextBold"><bean:message key="profile.member" bundle="common"/> </td>
              <td align=center  align=right bgcolor="#F0FAFF" class="tableSideTextBold"><bean:message key="profile.unit" bundle="common"/> </td>
              <td align="center" align=right bgcolor="#F0FAFF" class="tableSideTextBold"><bean:message key="profile.region" bundle="common"/></td>
              <td align="center"  align=right  bgcolor="#F0FAFF" class="tableSideTextBold"><bean:message key="profile.chq" bundle="common"/></td> 
            </tr>
            <tr> 
              <td  align=center bgcolor="#CEEBFF" class="tableSideTextBold"><bean:message key="profile.options" bundle="common"/></td>
               <td bgcolor="#F0FAFF">&nbsp; </td> 
               <td bgcolor="#F0FAFF">&nbsp; </td>
               <td bgcolor="#F0FAFF">&nbsp; </td>
               <td bgcolor="#F0FAFF">&nbsp; </td>                   
            </tr>
           <logic:iterate  name="profileOptionList" id="po" scope="request" type="ProfileOptionBean">
	            <tr> 
	              <td width="500" align=left nowrap bgcolor="#CEEBFF" class="tableSideTextBold"><bean:write property="optionName" name="po"/><input type="hidden" name="option" value='<bean:write property="optionCode" name="po"/>'  > </td>
	             <td align="center" width="50" align=right bgcolor="#F0FAFF"><input type="checkbox" name='<bean:write property="optionCode" name="po"/>M' value='Y' <logic:equal name="po" property="memberAccessFlag" value="Y">checked</logic:equal>></td>
	             <td align="center" width="50" align=right bgcolor="#F0FAFF"> <input type="checkbox" name='<bean:write property="optionCode" name="po"/>U' value='Y' <logic:equal name="po" property="unitAccessFlag" value="Y">checked</logic:equal>></td>
	             <td align="center" width="50" align=right bgcolor="#F0FAFF"><input type="checkbox" name='<bean:write property="optionCode" name="po"/>R' value='Y' <logic:equal name="po" property="regionAccessFlag" value="Y">checked</logic:equal>></td>
	             <td align="center" width="50" align=right bgcolor="#F0FAFF"><input type="checkbox" name='<bean:write property="optionCode" name="po"/>C' value='Y' <logic:equal name="po" property="chqAccessFlag" value="Y">checked</logic:equal>></td>
	            </tr>   
            </logic:iterate>                          
			<tr>
				<td colspan=5 align=center>&nbsp;</td>
			</tr>
			<tr>
				<td  colspan=5 align=center>
					<html:submit  property="update"  tabindex="1" styleClass="butt"> <bean:message key="button.update" bundle="common"/></html:submit>&nbsp;<html:reset styleClass="butt" tabindex="2"><bean:message key="button.reset" bundle="common"/></html:reset>
				<html:button  property="Cancel" tabindex="3" styleClass="butt"  onclick="javascript:window.location.href='showProfileOptions.do?method=showProfileOptions'" onblur="document.forms[0].update.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
				</td>
					
			</tr>
			<tr>
				<td colspan=5 align=center>&nbsp;</td>
			</tr>			
	</table>
	</html:form>
<%=ScreenUtilities.screenFooter()%>
	</body>
</html>
