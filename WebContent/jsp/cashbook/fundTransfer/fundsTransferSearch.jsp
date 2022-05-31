<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.bean.cashbook.FundsTransferInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Funds Transfer</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />		
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/displaytagstyle.css" type="text/css" />
		<script language="javascript" >
		    function addRec(){
                  document.forms[0].action = "/FundsTransfer.do?method=fwdtoNew";
                  document.forms[0].submit();
		    }
		    function editRec(val){
                  document.forms[0].action = "/FundsTransfer.do?method=fwdtoEdit&&code="+val;
                  document.forms[0].submit();
		    }
		    function showRec(key,finapp,app){		    
                var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/FundsFinApproval.do?method=getReport&&keyno="+key+"&&approvedBy="+app+"&&finalApprovedBy="+finapp;
				window.open(url,"FinalApproval","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");				
				return false;
		    }
		  </script>
	</head>
	<body>
		<html:form action="/FundsTransfer.do?method=search" method="post">
			<%=ScreenUtilities.screenHeader("ft.title")%>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td height="15%">
									<table align="center" cellspacing="5" border="0">
										<tr>
											<td class="tableTextBold" align="right" nowrap="nowrap">
												<bean:message key="ft.frombank" bundle="common" />
												:
											</td>
											<td align="left">
												<html:select property="fromAccountNo" styleClass="TextField" style='width:200px'>
													<html:option value="">Select One</html:option>
													<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails" />
												</html:select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right" nowrap="nowrap">
												<bean:message key="ft.tobank" bundle="common" />
												:
											</td>
											<td align="left">
												<html:select property="toAccountNo" styleClass="TextField" style='width:200px'>
													<html:option value="">Select One</html:option>
													<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails" />
												</html:select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right" nowrap="nowrap">
												<bean:message key="ft.prepDate" bundle="common" />
												:
											</td>
											<td align="left">
												<html:text maxlength="11" property="preparationDate" styleId="preparationDate" styleClass='TextField' style="width:80 px" tabindex="9"/>
												<html:link href="javascript:showCalendar('preparationDate');">
													<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
												</html:link>
											</td>
										</tr>
										<tr>
											<td align="center" colspan="5">
												<input type="submit" class="butt" value="Submit" tabindex="4" />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" tabindex="5" />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="6" />
											</td>
										</tr>
										<tr>
											<td height="10px">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="29" align="left" valign="top">
						<table border="0" cellspacing="5" cellpadding="0">
							<tr>
								<td align="left">
									<input type="button" class="btn" value="Add" onclick="addRec()" />
								</td>								
							</tr>
						</table>
					</td>
				</tr>
				<%if(request.getAttribute("records")!=null){%>
				<tr>
					<td align="center">
						<display:table cellpadding="0" cellspacing="0" pagesize="5" class="GridBorder" style="width:100%;height:50px" keepStatus="true" export="true" sort="list" name="records" name="requestScope.records" id='searchData'
							requestURI="/FundsTransfer.do?method=search">
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="fromAccountNo" sortable="true" title="From Account No." />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="toAccountNo" sortable="true" title="To Account No." />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="preparationDate" sortable="true" title="Preparation Date" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="amount" sortable="true" title="Amount" />
							<display:column  style="width:10px;" media="html" headerClass="GridHBg" class="GridLTCells">
								<logic:equal value="N" property="approval" name="searchData">
									<img src='/images/icon-edit.gif' border='0' alt='Edit' style="cursor: hand" onclick="editRec('<%=((FundsTransferInfo)pageContext.getAttribute("searchData")).getKeyno()%>')"/> 
								</logic:equal>
								<logic:equal value="Y" property="approval" name="searchData">
									<img src='/images/lockIcon.gif' border='0' alt='Lock' /> 
								</logic:equal>								
							</display:column>
							<display:column  style="width:10px;" media="html" headerClass="GridHBg" class="GridLTCells">
							
									<img src='images/print.gif' border='0' alt='Report' style="cursor: hand" onclick="showRec('<%=((FundsTransferInfo)pageContext.getAttribute("searchData")).getKeyno()%>','<%=((FundsTransferInfo)pageContext.getAttribute("searchData")).getFinalApprovedBy()%>','<%=((FundsTransferInfo)pageContext.getAttribute("searchData")).getApprovedBy()%>')" />
						
							</display:column>
							
						</display:table>
					</td>
				</tr>
				<%}%>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
