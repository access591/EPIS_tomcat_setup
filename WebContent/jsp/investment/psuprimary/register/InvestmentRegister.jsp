<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js"></SCRIPT>
		<SCRIPT SRC="/js/DateTime.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].letterNo.value ==""){
		    		 alert("Please Select Letter No. ");
		    		 document.forms[0].letterNo.focus();
		   			  return false;
		     }
		      
		    return true; 
		 }
   
  function clearData(){
   window.document.forms[0].reset(); 
  }
		</script>
	</head>
	<body onload="document.forms[0].letterNo.focus();">
		<table width="70%" border=0 align="center">
			<html:form action="/investmentregisternew.do?method=InvestmentRegister" onsubmit="return validate();">
				<%=ScreenUtilities.screenHeader("investmentRegister.title")%>
				<table width="550" border=0 align="center">
					<tr>
						<td class="tableTextBold" align=right nowrap>
							<font color=red>*</font>
							<bean:message key="investmentRegister.letterno" bundle="common" />
							:
						</td>
						<td align="left">
							<html:select property="letterNo" styleClass="TextField" style="width:300px">
								<html:option value="">[Select One]</html:option>
								<html:options collection="regLettersList" property="letterNo" labelProperty="securityName" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td colspan=4 align=center>
							&nbsp;
							<input type="hidden" name="mode" value="<bean:write name="modetype"/>"/>
						</td>
					</tr>
					<tr>
						<td colspan=4 align=center>
							<html:submit styleClass="butt" property="OK" value="OK" />
							&nbsp;
							<html:button styleClass="butt" property="Clear" value="Clear" tabindex="5" onclick="clearData();" />

						</td>
					</tr>
				</table>
				<%=ScreenUtilities.screenFooter()%>
			</html:form>
	</body>
</html>
