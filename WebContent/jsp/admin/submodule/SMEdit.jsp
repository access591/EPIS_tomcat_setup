<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
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
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		  function validate(){
		  	if(document.forms[0].moduleCode.value==""){
		     alert(" Module  is Mandatory");
		     document.forms[0].moduleCode.focus();
		     return false;
		    }
		    if(document.forms[0].subModuleName.value==""){
		     alert("Sub Module Name is Mandatory");
		     document.forms[0].subModuleName.focus();
		     return false;
		    }
		    if(document.forms[0].sortingOrder.value==''){
					 alert("Please Enter Sorting Order  ");
						document.forms[0].sortingOrder.focus();
						return(false);
			 }
			 if(document.forms[0].sortingOrder.value!=''){
				 if(!ValidateNum(document.forms[0].sortingOrder.value)){
				 alert('Please enter valid value for Sorting Order(Expecting Numeric value)');
					document.forms[0].sortingOrder.focus();
					return(false);
					}
			 }
		    return true; 
		 }
		function clearData(){
       		 document.forms[0].subModuleName.value="";
        	 document.forms[0].moduleCode.value="";        	 
  		}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].moduleCode.focus();" >
	<html:form action="/editSM.do?method=updateSM" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("submodule.title")%>
	<table width="70%" border=0 align="center" >
		<tbody >
			<tr>
				<td colspan=4 align=center>
					<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="submodule.module" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:hidden property="subModuleCode" name="submodule"/>
					<html:select  property="moduleCode" styleClass="TextField" style="width:120 px" tabindex="1" name="submodule">
					<html:option value="">[Select One]</html:option>
					<html:options collection="modules" property="code" labelProperty="name"/>	
						</html:select>	
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="common.stars" bundle="common"/><bean:message key="submodule.submodulename" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="subModuleName" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="30" name="submodule"/>		
				</td>			
			</tr>			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="submodule.sortingorder" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="2" name="submodule" property="sortingOrder" styleClass="TextField" style="width:20px" tabindex="3" maxlength="2" />
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  &nbsp;
				</td><td width="20%" >
					&nbsp;
				</td>			
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center>
			<html:submit    tabindex="3" styleClass="butt"> <bean:message key="button.save" bundle="common"/></html:submit>&nbsp;
			<html:reset  property="Reset" styleClass="butt" tabindex="4" ><bean:message key="button.reset" bundle="common"/></html:reset>
			<html:button  property="Cancel" tabindex="5" styleClass="butt"  onclick="javascript:window.location.href='searchSM.do?method=searchSM'" onblur="document.forms[0].moduleCode.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
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
