<%@ page language="java"  import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.Configurations" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">	
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
		<SCRIPT type="text/javascript" src="js/Ajax.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].user.value==""){
		     alert("Please Select User (Mandatory).");
		     document.forms[0].user.focus();
		     return false;
		    }
		    if(document.forms[0].modules.value==""){
			     alert("Please select Modules (Mandatory).");
			     document.forms[0].modules.focus();
			     return false;
		    }
		    
		    
		    return true; 
		 }
		 
		 function getMdoules()
		 {
			var userid=document.forms[0].user.value;
			if(userid!="")
			{
				var url;
				url="<%=basePath%>jsp/admin/user/LoadModules.jsp?mode=M&UserId="+document.forms[0].user.value;
				sendURL(url,"loadModules");
			}else{
			document.forms[0].modules.length=1;
			}
	    }
		function loadModules()
		{
			document.forms[0].modules.length=1;
			if (httpRequest.readyState == 4)
				{ 
					if(httpRequest.status == 200) 
						{ 			
							var node = httpRequest.responseXML.getElementsByTagName("Modules")[0];
							var trustType ="";
							var securityCategory ="";
		   					if(node) 
							{
								for (i = 0; i < parseInt(node.childNodes.length); i++) 
								{
									var mod = node.childNodes[i];						
									document.forms[0].modules.options[document.forms[0].modules.options.length]	=	new Option(mod.getElementsByTagName("name")[0].firstChild.nodeValue,mod.getElementsByTagName("code")[0].firstChild.nodeValue);
									
								}								
							}
						}
						
				}
				
		}
		function loadScreens(){
			var modules=document.forms[0].modules.value;
			
			//if(modules!=""){
				document.getElementById('accessrights').src='<%=basePath%>jsp/admin/user/ScreenCodes.jsp?modules='+modules+'&UserId='+document.forms[0].user.value;
			//}
		}
		
		</SCRIPT>
	</head>
	<body onload="document.forms[0].user.focus();" >
		
		
			<%=ScreenUtilities.screenHeader("accessrights.title")%>
			<table width="90%"  border="0" align="center">
				<html:form action="/accessRights.do?method=edit">
					<tbody>
						<tr>
							<td colspan="4" align="center">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width="20%" class="tableTextBold" align="right" nowrap>			
							<%if("USER".equals(Configurations.getAccessRightsType())){%>			
							<bean:message key="accessrights.userid" bundle="common" />:
							<%}else if("ROLE".equals(Configurations.getAccessRightsType())){%>			
							<bean:message key="role.rolename" bundle="common" />:
							<%}%>
							</td>
							<td width="20%" align="left">
								<html:select property="user" styleClass="TextField"  onchange="getMdoules();loadScreens();">
									<html:option value="">[Select One]</html:option>
									<html:options name="Bean" property="code" labelProperty="name"  collection="users" />
								</html:select>
							</td>
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="accessrights.modules" bundle="common" />:
							</td>
							<td width="20%" align="left">
								<html:select property="modules" styleClass="TextField"  onchange="loadScreens()">
									<html:option value="">[Select One]</html:option>									
								</html:select> 
							</td>
							
						</tr>
						<tr>
							<td colspan="4" align="center">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center" height="300">
								
									<IFrame name='accessrights' id='accessrights' src="/jsp/admin/user/ScreenCodes.jsp" width="100%" height="100%" scrolling="auto" frameborder=0 ALLOWTRANSPARENCY=TRUE>
									</IFrame>
							
							</td>
						</tr>
					</tbody>
				</html:form>
			</table>
	<%=ScreenUtilities.screenFooter()%>
	</body>
</html>
