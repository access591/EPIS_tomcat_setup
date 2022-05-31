<%@ page import="com.epis.utilities.ScreenUtilities,com.epis.bean.cashbook.PartyInfo"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Party Master</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="Party Master" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript">
		
			 function selectCheckboxes(optiontype) {
				var recordcd = getCheckedValue(document.forms[0].deleteRecord);				
				if(optiontype!='Add'){
					if(recordcd==''){
						alert("Select atleast one Record");
						return;
					}
				}
				if(optiontype=='Add'){						
					 window.location.href="/partySearchResult.do?method=fwdtoNew";
				}
				if(optiontype=='Delete'){		 
					var state=window.confirm("Are you sure you want to delete ?");
					if(state){
						document.forms[0].action="/partySearchResult.do?method=delete&deleteRecord="+recordcd;
						document.forms[0].submit();
					}else{
						return;
					}
				}else if(optiontype=='Edit'){	 			 
					document.forms[0].action = "/partySearchResult.do?method=fwdtoEdit&&code="+recordcd;
					document.forms[0].submit();					 
				}
			}		  
		</script>
	</head>

	<body>
		<html:form action="/partySearchResult.do?method=search" method="post">
			<%=ScreenUtilities.screenHeader("party.title")%>
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="party.name" bundle="common" /> :
					</td>
					<td align="left">
						<html:text styleClass="TextField" property="partyName" maxlength="25"  />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<html:submit  styleClass="butt" ><bean:message key="button.search" bundle="common"/></html:submit>
                        <html:button styleClass="butt" property="Clear"  onclick="javascript:document.forms[0].reset()"><bean:message key="button.clear" bundle="common"/></html:button>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<%=ScreenUtilities.getAcessOptions(session,1)%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<html:radio property="deleteRecord" value="" style="display:none;" />
				<tr>
					<th align="center"><html:errors  bundle="error"  property="deleteRecord"/>&nbsp;</th>					
				</tr>
<%
	if (request.getAttribute("PartyList") != null) {
%>
				<tr>
					<td align="center" width="100%">
						<display:table cellpadding="0" cellspacing="0" style="width:100%;height:50px" keepStatus="true" export="true" sort="list" name="PartyList" name="requestScope.PartyList" pagesize="5" id='searchData' requestURI="/partySearchResult.do?method=search">
							<display:setProperty name="export.amount" value="list" />
							<display:setProperty name="export.excel.filename" value="Party.xls" />
							<display:setProperty name="export.pdf.filename" value="Party.pdf" />
							<display:setProperty name="export.rtf.filename" value="Party.rtf" />
							<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">
								<html:radio property="deleteRecord" value='<%=((PartyInfo)pageContext.getAttribute("searchData")).getPartyCode()%>' />
							</display:column>
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="partyName" sortable="true"  title="Party Name">
							</display:column>
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="partyDetail" sortable="true" title="Party Detail" decorator="com.epis.utilities.StringFormatDecorator" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="mobileNo" sortable="true" title="Mobile No." decorator="com.epis.utilities.StringFormatDecorator" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="emailId" sortable="true" title="EmailId" decorator="com.epis.utilities.StringFormatDecorator" />
							
						</display:table>
					</td>
				</tr>
<%}%>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
