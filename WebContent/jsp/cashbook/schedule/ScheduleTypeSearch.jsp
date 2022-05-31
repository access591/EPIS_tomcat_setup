<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.bean.cashbook.ScheduleTypeInfo" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Schedule Type Search</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Schedule Type Search" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/displaytagstyle.css" type="text/css" />
		<script language="javascript" >
			function selectCheckboxes(optiontype) { 		
				var recordcd = getCheckedValue(document.forms[0].deleteRecord);		
				if(optiontype!='Add'){
					if(recordcd==''){
						alert("Select atleast one Record");
						return;
					}
				}
				if(optiontype=='Add'){						
					 window.location.href="/ScheduleTypeSearch.do?method=fwdtoNew";
				}
				if(optiontype=='Delete'){		 
					var state=window.confirm("Are you sure you want to delete ?");
					if(state){
						document.forms[0].action="/ScheduleTypeSearch.do?method=delete&&schType="+recordcd;
						document.forms[0].submit();
					}else{
						return;
					}
				}else if(optiontype=='Edit'){	 			 
					document.forms[0].action = "/ScheduleTypeSearch.do?method=fwdtoEdit&&schType="+recordcd;
					document.forms[0].submit();					 
				}
			}
		  </script>
	</head>

	<body class="BodyBackground1">
		<html:form action="/ScheduleTypeSearch.do?method=search" method="post">

			<%=ScreenUtilities.screenHeader("sch.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan="4" align="center">
						&nbsp;
						<html:errors bundle="error" property="schType" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="sch.type" bundle="common" />
					</td>
					<td align="left">
						<html:text property="schType" styleClass="tableText" /> 
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="sch.description" bundle="common" />
					</td>
					<td align="left">
					<html:text property="description" styleClass="tableText" /> 
					</td>
				</tr>				
				<tr>
					<td align="center" colspan="2">
						<html:submit value="Search" styleClass="butt" />
						<html:button value="Clear" property="clear" onclick="javascript:document.forms[0].reset()" styleClass="butt" />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<%=ScreenUtilities.getAcessOptions(session,1)%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th align="center"><html:errors  bundle="error"  property="deleteRecord"/>&nbsp;</th>					
				</tr>
				<html:radio property="deleteRecord" value="" style="display:none;" />
				<tr>
					<td align="center" width="100%">
						<display:table cellpadding="0" cellspacing="0" class="GridBorder" keepStatus="true" style="width:100%" sort="list" name="records" name="requestScope.records" pagesize="5" id='searchData' requestURI="/ScheduleTypeSearch.do?method=search">
							<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">
								<html:radio property="deleteRecord" value='<%=((ScheduleTypeInfo)pageContext.getAttribute("searchData")).getSchType()%>' />
							</display:column>
							<display:column property="schType" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Schedule No." decorator="com.epis.utilities.StringFormatDecorator" />							
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="description" sortable="true" title="Schedule Name"  decorator="com.epis.utilities.StringFormatDecorator"/>
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="type" sortable="true" title="Schedule Type"  decorator="com.epis.utilities.StringFormatDecorator"/>
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="accountHeads" sortable="true" title="AccountingHeads" decorator="com.epis.utilities.StringFormatDecorator"/>							
						</display:table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>