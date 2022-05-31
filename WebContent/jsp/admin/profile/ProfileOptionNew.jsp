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
		<title>Admin - Profile Option New</title>
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
		function clearData(){
       		 document.forms[0].optionName.value="";
        	 document.forms[0].description.value="";        	 
  		}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].optionName.focus();" >
	<html:form action="/addOption.do?method=addOption" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("profileoptions.title")%>
	<table width="70%" border=0 align="center" >
		<tbody >
			<tr>
				<td colspan=4 align=center><html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="profileoptions.optionname" bundle="common"/>:
				</td>
				<td width="20%" align="left">
					<html:text size="12" property="optionName" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="200" />		
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="profileoptions.description" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="description" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="300" />		
				</td>			
			</tr>	
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="profileoptions.path" bundle="common"/>:
				</td>
				<td width="20%" align="left">
					<html:text size="12" property="path" styleClass="TextField" style="width:120 px" tabindex="3" maxlength="200" />		
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="profileoptions.optiontype" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:select property="optionType" tabindex="4" styleClass="TextField" style="width:120px">
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
			<td  colspan=4 align=center>
			<html:submit    tabindex="5" styleClass="butt"> <bean:message key="button.save" bundle="common"/></html:submit>
			<html:button  property="Clear" styleClass="butt" tabindex="6" onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button>
			<html:button  property="Cancel" tabindex="7" styleClass="butt"  onclick="javascript:window.location.href='searchOption.do?method=searchOption'" onblur="document.forms[0].optionName.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>			
			</tbody>
	</table>
	<%=ScreenUtilities.screenFooter()%>
	</html:form>

	</body>
</html>
