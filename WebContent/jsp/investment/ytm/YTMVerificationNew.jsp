
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
			    if(document.forms[0].letterNo.value==""){
			    alert("please Select  Letter No ");
			    document.forms[0].letterNo.focus();
			   	return false;
			    }
		}
		function clearData() { 
		window.document.forms[0].reset();
		}
	</SCRIPT>
	</head>
	<body onload="document.forms[0].letterNo.focus();" >
		<html:form action="/actionYTM.do?method=showYTMAddDetails" onsubmit="return validate();">
		<%=ScreenUtilities.screenHeader("ytm.newtitle")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="ytm.letterno" bundle="common"/>
				</td>
				<td align="left" width="20%" >
					<html:select  property="letterNo"  style="width:120 px" tabindex="1" >
						<html:option value="">[Select one]</html:option>
						<html:options collection="letters" property="letterNo" labelProperty="letterNo"/>
					</html:select>
					
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <html:checkbox  property="ytmflag"  value="Y" />
				</td><td align="left" nowrap="nowrap" >
					<bean:message key="ytm.notneeded" bundle="common"/></td>
				</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit  styleClass="butt" value="Ok"  tabindex="2" />&nbsp;<html:button   styleClass="butt" property="Clear" value="Clear" tabindex="3" onclick="clearData();"/>&nbsp;
			<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='/actionYTM.do?method=searchYTM'"  onblur="document.forms[0].letterNo.focus();"  tabindex="4"/>
			
			</td>	
			</tr>	
			</table>
				<%=ScreenUtilities.screenFooter()%>
	</html:form>
	</body>
</html>
