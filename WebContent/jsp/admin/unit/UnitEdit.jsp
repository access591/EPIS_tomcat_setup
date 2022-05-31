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
		<title>Admin - Unit Master</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" ></SCRIPT>	
		<SCRIPT type="text/javascript">
		 function validate(){
		    
		    if(document.forms[0].unitName.value==""){
			     alert("Unit Name is Mandatory");
			     document.forms[0].unitName.focus();
			     return false;
		    }
		     if(!ValidateAlphaNumeric(document.forms[0].unitName.value)){
		     alert("Enter Valid Unit Name ");
		     document.forms[0].unitName.focus();
		     return false;
		    }
		    if(document.forms[0].option.value==""){
		   		 alert("option is Mandatory");
			     document.forms[0].option.focus();
			     return false;
		     }
		     if(document.forms[0].region.value==""){
		   		 alert("Region is Mandatory");
			     document.forms[0].region.focus();
			     return false;
		     }
		    return true; 
		 }
		
		</SCRIPT>
	</head>
	<body onload="document.forms[0].unitName.focus();" >
	<html:form action="/editUnit.do?method=updateUnit" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("unit.title")%>
	<table width="70%" border=0 align="center" >	
	
		<tbody >
			<tr>
				<td colspan=4 align=center>
					<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="unit.unitcode" bundle="common"/>:
				</td><td width="20%" align="left">
				<html:text size="12" property="unitCode" name="unit" styleClass="TextField"  tabindex="1" maxlength="10" readonly="true"/>				
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="unit.unitname" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="unitName" name="unit" styleClass="TextField"  tabindex="2" maxlength="50" />
				</td>		
			</tr>
			<tr>
				<td class="tableTextBold" width="20%" align=right>
					<bean:message key="common.stars" bundle="common"/><bean:message key="unit.option" bundle="common"/>:
				</td>
				<td width="20%" align="left">
				<html:select property="option" styleClass="TextField" style="width:100 px" tabindex="3" name="unit">
						<html:option value="">[Select One]</html:option>
						<html:option value="Airport">Airport</html:option>
						<html:option value="RHQ">RHQ</html:option>
					</html:select>					
				</td>				
				<td class="tableTextBold" width="20%" align=right>
					<bean:message key="common.stars" bundle="common"/><bean:message key="unit.region" bundle="common"/>:
				</td>
				<td width="20%"  align="left">
					<html:select  property="region" name="unit" styleClass="TextField" style="width:150 px" tabindex="4" >
						<html:option value="">[Select one]</html:option>
						<html:options collection="regions" property="regionName" labelProperty="regionName"/>
					</html:select>
				</td>	
			</tr>			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
				<td  colspan=4 align=center>
				<html:submit   styleClass="butt"  tabindex="4" > <bean:message key="button.save" bundle="common"/></html:submit>
				<html:reset  styleClass="butt" property="Reset" tabindex="5" ><bean:message key="button.reset" bundle="common"/> </html:reset>
				<html:button  property="Cancel" styleClass="butt" tabindex="7" onclick="javascript:window.location.href='searchUnit.do?method=searchUnit'" onblur="document.forms[0].unitName.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
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
