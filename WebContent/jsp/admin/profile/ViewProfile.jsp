
<%@ page language="java"  import="com.epis.bean.admin.ProfileOptionBean" pageEncoding="UTF-8"%>
<%@ page  import="com.epis.utilities.ScreenUtilities" %>
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
		<title>Admin - Profile View Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		
				function onFocus(){
				
				if(!document.forms[0].Edit.disabled)
					document.forms[0].Edit.focus();
				}
		
		
		</script>
	</head>
	<body  onload="onFocus();">
	<html:form action="/showProfileOptions.do?method=createProfileOptions" >
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
	              <td width="500" align=left  bgcolor="#CEEBFF" class="tableSideTextBold"><bean:write property="optionName" name="po"/></td>
	             <td align="center" width="50" bgcolor="#F0FAFF"> <logic:equal name="po" property="memberAccessFlag" value="N"><IMG src="<%=basePath%>/images/cancelIcon.gif"> </logic:equal><logic:equal name="po" property="memberAccessFlag" value="Y"><IMG src="<%=basePath%>/images/saveIcon.gif"> </logic:equal></td> 
	             <td align="center" width="50"  bgcolor="#F0FAFF"> <logic:equal name="po" property="unitAccessFlag" value="N"><IMG src="<%=basePath%>/images/cancelIcon.gif"> </logic:equal><logic:equal name="po" property="unitAccessFlag" value="Y"><IMG src="<%=basePath%>/images/saveIcon.gif"> </logic:equal></td> 
	             <td align="center" width="50"  bgcolor="#F0FAFF"> <logic:equal name="po" property="regionAccessFlag" value="N"><IMG src="<%=basePath%>/images/cancelIcon.gif"> </logic:equal><logic:equal name="po" property="regionAccessFlag" value="Y"><IMG src="<%=basePath%>/images/saveIcon.gif"> </logic:equal></td> 
	             <td align="center"  width="50" bgcolor="#F0FAFF"> <logic:equal name="po" property="chqAccessFlag" value="N"><IMG src="<%=basePath%>/images/cancelIcon.gif"> </logic:equal><logic:equal name="po" property="chqAccessFlag" value="Y"><IMG src="<%=basePath%>/images/saveIcon.gif"> </logic:equal></td> 	            
	            </tr>   
            </logic:iterate>                          
			<tr>
				<td colspan=5 align=center>&nbsp;</td>
			</tr>
			<tr>
				<td  colspan=5 align=center><%=ScreenUtilities.getAcessOptions(session,1,true)%>
					
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
