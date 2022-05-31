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
		<title>Admin - Sub Module Edit Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		  function validate(){
		 
		    if(document.forms[0].roleName.value==""){
		     alert("RoleName is Mandatory");
		     document.forms[0].roleName.focus();
		     return false;
		    }
		    if(!ValidateAlphaNumeric(document.forms[0].roleName.value)){
		     alert("Enter Valid Role Name ");
		     document.forms[0].roleName.focus();
		     return false;
		    }
		    if(document.forms[0].roleDescription.value!=""){
		    if(!ValidateAlphaNumeric(document.forms[0].roleDescription.value)){
		     alert("Enter Valid Role Description ");
		     document.forms[0].roleDescription.focus();
		     return false;
		    }
		    }
		    if(document.forms[0].modules.value==""){
		     alert("Select Module is Mandatory");
		     document.forms[0].modules.focus();
		     return false;
		    }
		    
		    var selmodules='';
		    for(i=0;i<document.forms[0].modules.length;i++)
			{   
				if(document.forms[0].modules[i].selected)
				{
					selmodules+=(document.forms[0].modules[i].value)+",";
		 		}
	    
	 			}
			var modulesSelected=selmodules.substr(0,selmodules.length-1);
			document.forms[0].selectedModules.value=modulesSelected;
			
		    return true;
		 }
		
		</SCRIPT>
	</head>
	<body onload="document.forms[0].roleName.focus();" >
	<html:form action="/editRole.do?method=updateRole" onsubmit="return validate();">
	<%=ScreenUtilities.saearchHeader("role.title")%>
	<table width="70%" border=0 align="center" >
		<tbody >
			<tr>
				<td colspan=4 align=center>
					<html:errors  bundle="error"/>
				</td>
			</tr>
				<html:hidden property="selectedModules"  />
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="common.stars" bundle="common"/><bean:message key="role.rolename" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="roleName" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="300"  name="role"/>		
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="role.roledescription" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="roleDescription" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="300"  name="role"/>		
				</td>			
			</tr>	
			<tr>
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="common.stars" bundle="common"/><bean:message key="user.modules" bundle="common" />
								:
							</td>
						<td width="20%" align="left">
					<html:select  property="modules" styleClass="TextField" style="width:120 px ;height:50 px" tabindex="3"  multiple="true">
						<html:options name="Bean" property="code" labelProperty="name" labelName="code" collection="modulelist" />
					</html:select>								
							</td>
		   </tr>		
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<html:hidden property="roleCd" name="role"/>
			<td  colspan=4 align=center>
			<html:submit  styleClass="butt"  tabindex="4" > <bean:message key="button.save" bundle="common"/></html:submit>
			<html:reset  styleClass="butt" property="Reset" tabindex="5" onclick="Reset();"><bean:message key="button.reset" bundle="common"/> </html:reset>
			<html:button  property="Cancel" styleClass="butt" tabindex="6" onclick="javascript:window.location.href='searchRole.do?method=searchRole'" onblur="document.forms[0].roleName.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;</td>
			</tr>			
			</tbody>
	</table>
	</html:form>
	<%=ScreenUtilities.screenFooter()%>
	</body>
</html>
