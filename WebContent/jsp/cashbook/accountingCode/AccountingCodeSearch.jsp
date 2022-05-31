<%@ page import="com.epis.bean.cashbook.AccountingCodeInfo"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Account Code Search</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Account Code Search" />
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
					 window.location.href="/accCodeSearchResult.do?method=fwdtoNew";
				}
				if(optiontype=='Delete'){		 
					var state=window.confirm("Are you sure you want to delete ?");
					if(state){
						document.forms[0].action="/accCodeSearchResult.do?method=delete";
						document.forms[0].submit();
					}else{
						return;
					}
				}else if(optiontype=='Edit'){	 			 
					document.forms[0].action = "/accCodeSearchResult.do?method=fwdtoEdit&&code="+recordcd;
					document.forms[0].submit();					 
				}
			}
		  </script>
	</head>

	<body class="BodyBackground1">
		<html:form action="/accCodeSearchResult.do?method=search" method="post">

			<%=ScreenUtilities.screenHeader("accCode.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan="4" align="center">
						&nbsp;
						<html:errors bundle="error" property="accountHead" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.accCode" bundle="common" />
					</td>
					<td align="left">
						<html:text property="accountHead" styleClass="tableText" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.name" bundle="common" />
					</td>
					<td align="left">
						<html:text property="particular" styleClass="tableText" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCodeType.accCodeType" bundle="common" />
					</td>
					<td align="left">
						<html:select property="type" styleClass="TextField" style="width:150px">
							<html:option value="">
							Select One
						</html:option>
						
						</html:select>
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
						<display:table cellpadding="0" cellspacing="0" class="GridBorder" keepStatus="true" style="width:100%" sort="list" name="records" name="requestScope.records" pagesize="5" id='searchData' requestURI="/accCodeSearchResult.do?method=search">
							<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">
								<html:radio property="deleteRecord" value='<%=((AccountingCodeInfo)pageContext.getAttribute("searchData")).getAccountHead()%>' />
							</display:column>
							<display:column property="accountHead" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Account Code" />							
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="particular" sortable="true" title="Account Name" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="type" sortable="true" title="Account Type" decorator="com.epis.utilities.StringFormatDecorator"/>							
						</display:table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>