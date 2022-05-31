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
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
			<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].letterNo.value ==""){
		    		 alert("Please Select Letter No. ");
		    		 document.forms[0].letterNo.focus();
		   			  return false;
		     }
		     if(document.forms[0].opendate.value!=''){
						if(!convert_date(document.forms[0].opendate)){
						document.forms[0].opendate.select();
						return(false);
						}
			 }
		      
		    return true; 
		 }
   
  function clearData(){
   document.forms[0].letterNo.value="";
   document.forms[0].opendate.value="";
   document.forms[0].remarks.value="";
   document.forms[0].status.value="A";
   document.forms[0].letterNo.focus();
  }
		</script>
	</head>
	<body onload="document.forms[0].letterNo.focus();" >
	<html:form action="/addinvestapproval.do?method=showInvestApproval" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("app.title")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font> <bean:message key="sq.letterno" bundle="common" />:
				</td><td width="20%" align="left">
					 <html:select property="letterNo" styleClass="ListField"   >
						 <html:option value="">[Select One]</html:option>
						 <html:options collection="approvalLettersList" property="letterNo" labelProperty="letterNo"/>
                    </html:select>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="quotation.approvedate" bundle="common" />:
				</td><td width="20%" align="left" nowrap>
					 <html:text property="opendate"  tabindex="2" maxlength="11" styleId="opendt"  size="9" />
					 <html:link href="javascript:showCalendar('opendt');" tabindex="3">
						<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>      				
				</td>	
						
			</tr>
			<tr>
			
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="quotation.remarks" bundle="common" />:
				</td>
				<td width="100%" align="left">
					 <html:textarea property="remarks"   cols="22" rows="3" />
				</td>
                 <td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="quotation.approvestatus" bundle="common" />:
				</td>
				<td width="20%" align="left">
					 <html:select property="status">
						 <option value="A">Approved</option>
						 <option value="R">Reject</option>
                    </html:select>
				</td>	
            </tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt"  property="OK" value="OK" />&nbsp;<html:button styleClass="butt" property="Clear" value="Clear" tabindex="5" onclick="clearData();" />
			<html:button property="Back" styleClass="butt"  value="Back" onclick="javascript:window.location.href='searchinvestapproval.do?method=searchInvestApproval'" />
		     </td>	
			</tr>
		</table>
				<%=ScreenUtilities.screenFooter()%>
			</html:form>
	</body>
</html>
