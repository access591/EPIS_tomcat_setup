
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].trustType.value==""){
		     alert(" Please Enter The Trust Type (Mandatory)");
		     document.forms[0].trustType.focus();
		     return false;
		    }
		    if(document.forms[0].trustType.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].trustType.value)){
		    		 alert("Please Enter Valid Trust Type (Expecting Alpha Numeric Value) ");
		    		 document.forms[0].trustType.focus();
		   			  return false;
		    		 }
		     }
		     if(document.forms[0].description.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].description.value)){
		    		 alert("Please Enter Valid Description (Expecting Alpha Numeric Value) ");
		    		 document.forms[0].description.focus();
		   			  return false;
		    		 }
		     }
		    return true; 
		 }
		function clearData(){
        window.document.forms[0].reset();
  }
		</SCRIPT>
	</head>
	<body onload="document.forms[0].trustType.focus();" >
	<html:form action="/editTrustType.do?method=updateTrustType" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("trust.newTitle")%>
	<table width="550" border=0 align="center" >
	        <tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="trust.trustType" bundle="common"/>:
				</td><td width="20%" align="left">
				     <html:hidden property="trustCd" name="trust" />
					<html:text size="12" property="trustType" name="trust" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="20"/>
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="trust.desc" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="description" name="trust" styleClass="TextField" style="width:180 px" tabindex="2" maxlength="100"/>
					
				</td>					
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" value="Save"  tabindex="3"/>&nbsp;<html:button styleClass="butt" property="Clear" value="Clear" tabindex="4" onclick="clearData();"/>
			<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchTrustType.do?method=searchTrustType'" onblur="document.forms[0].trustType.focus();"  tabindex="5"/>
			
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>			
	</table>
	<%=ScreenUtilities.screenFooter()%>
	</html:form>
	</body>
</html>
