
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page import="com.epis.bean.cashbook.JournalVoucherInfo" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Account Code Type Search </title>
		
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Accounting Code Type" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/displaytagstyle.css" type="text/css" />
		
		<script language="javascript" >		    
			function addRec(){
                  document.forms[0].action = "/journalVoucher.do?method=fwdtoNew";
                  document.forms[0].submit();
		    }
		    function editRec(val){
                  document.forms[0].action = "/journalVoucher.do?method=fwdtoEdit&&keyNo="+val;
                  document.forms[0].submit();
		    }
		    function openReport(url){
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"JournalVoucher","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
		  </script>
	</head>

	<body >
	<html:form action="/journalVoucher.do?method=search" method="post">	
				<%=ScreenUtilities.screenHeader("jv.title")%><table width="70%" border="0" align="center">
					<table>
						<tr>
							<td colspan="4" align="center">
								&nbsp; <html:errors bundle="error" property="accountCodeType"/>
							</td>
						</tr>
						<tr>
							<td width="20%" class="tableTextBold" align="right" nowrap="nowrap">
								<bean:message key="voucher.date" bundle="common" />
								:
							</td>
							<td width="20%">
								<html:text maxlength="11" property="voucherDate" styleId="voucherDate" styleClass='TextField' style="width:30 px" />
								<html:link href="javascript:showCalendar('voucherDate');">
									<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
								</html:link>
							</td>
							<td width="20%" class="tableTextBold" align="right" nowrap="nowrap">
								<bean:message key="voucher.prepDate" bundle="common" />
								:
							</td>
							<td width="20%">
								<html:text maxlength="11" property="preparationDate" styleId="preparationDate" styleClass='TextField' style="width:30 px" />
								<html:link href="javascript:showCalendar('preparationDate');">
									<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
								</html:link>
							</td>
						</tr>		
						<tr>
							<td width="20%" class="tableTextBold" align="right" nowrap="nowrap">
								<bean:message key="voucher.no" bundle="common" />
								:
							</td>
							<td width="20%">
								<html:text property="voucherNo" maxlength="50" styleClass="tableText"/>
							</td>
							<td width="20%" class="tableTextBold" align="right" nowrap="nowrap">
								<bean:message key="voucher.partyType" bundle="common" />
								:
							</td>
							<td width="20%">
								<html:text property="partyType" maxlength="150" styleClass="tableText"/>
							</td>
						</tr>						
						<tr>
							<td colspan="4" align="center">
								<html:submit  styleClass="butt" ><bean:message key="button.search" bundle="common"/></html:submit>
                            	<html:button styleClass="butt" property="Clear"  onclick="javascript:document.forms[0].reset()"><bean:message key="button.clear" bundle="common"/></html:button>
							</td>
						</tr>
						
					</table>   <%=ScreenUtilities.searchSeperator()%>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">							
									<tr>
										<td height="29" align="left" valign="top">
											<table border="0" cellspacing="5" cellpadding="0" >
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
									<display:table cellpadding="0" cellspacing="0" pagesize="5" class="GridBorder" style="width:100%;height:50px" keepStatus="true" export="true" sort="list" name="records" name="requestScope.records"  id='searchData' requestURI="/journalVoucher.do?method=search" >										
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="partyType" sortable="true"  title="Party Type" /> 
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="trustType" sortable="true"  title="Trust Type" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherNo" sortable="true"  title="Voucher No." /> 
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherDate" sortable="true"  title="Voucher Date" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="preparationDate" sortable="true"  title="Preparation Date" /> 
										<display:column headerClass="GridLTHCells"   class="GridLTCells" property="amount" sortable="true"  title="Amount" />
										<display:column  style="width:10px;" media="html" headerClass="GridHBg" class="GridLTCells">
											
											<logic:notEqual value="Y" property="approval" name="searchData">
												<img src='/images/icon-edit.gif' border='0' alt='Edit' style="cursor: hand" onclick="editRec('<%=((JournalVoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>')"/> 
											</logic:notEqual>
											<logic:equal value="Y" property="approval" name="searchData">
												<img src='/images/lockIcon.gif' border='0' alt='Lock' /> 
											</logic:equal>		
										</display:column>
										<display:column headerClass="GridHBg" class="GridLTCells" media="html">											
												 <img src='images/print.gif' border='0' alt='Report' onclick="openReport('/journalVoucher.do?method=getReport&&keyNo=<%=((JournalVoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>');"/> 
																					
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
