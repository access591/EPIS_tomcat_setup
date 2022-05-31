<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
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
	<body onload="document.forms[0].letterNo.focus();" >
	<table width="70%" border=0 align="center" >
	<html:form action="/addshortlist.do?method=showShortList" onsubmit="return validate();">
		<%=ScreenUtilities.screenHeader("short.title")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font> <bean:message key="sq.letterno" bundle="common" />:
				</td><td width="20%" align="left">
					 <html:select property="letterNo" styleClass="ListField" tabindex="1" style="width:200 px"  >
						 <html:option value="">[Select One]</html:option>
						 <html:options collection="shortlettersList" property="letterNo" labelProperty="letterNo"/>
                    </html:select>
					
				</td>	
					<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="quotation.remarks" bundle="common" />:
				</td><td width="100%" align="left" colspan="3">
					 <html:textarea property="remarks"   cols="22" rows="3" />
						 
                    </td>	
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <html:checkbox  property="slFlag"  value="Y" />
				</td><td align="left" nowrap="nowrap" >
					<bean:message key="ytm.notneeded" bundle="common"/></td>
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" property="OK" value="OK" tabindex="2" />&nbsp;<html:button  styleClass="butt" property="Clear" value="Clear" tabindex="3" onclick="clearData();" />
			&nbsp;<html:button styleClass="butt" property="Cancel" value="Cancel" tabindex="4" onclick="javascript:window.location.href='searchshortlist.do?method=searchShortlist'" />
		     </td>	
			</tr>
			</table>
				<%=ScreenUtilities.screenFooter()%>
			</html:form>
	</body>
</html>
