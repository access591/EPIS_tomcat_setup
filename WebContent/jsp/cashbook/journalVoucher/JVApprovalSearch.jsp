<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.bean.cashbook.JournalVoucherInfo" %>

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
		   
		    function loadReport(){
                 <%
					if(request.getParameter("keyNo")!=null && ! "".equals(request.getParameter("keyNo"))){
				%>
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/journalVoucherApproval.do?method=getReport&&keyNo=<%=request.getParameter("keyNo")%>";
				window.open(url,"Approval","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");				
			<%}%>
		    }
		    
		  function  deleteRec(field){
		  	   var val = "";
				for (var i=0; i < document.forms[0].deleteRecord.length; i++) {  				 	
						if (document.forms[0].deleteRecord[i].checked)  {	
						  val= val + document.forms[0].deleteRecord[i].value + "|";
			     	}    		       
	            }
	            if(val == ""){
	            	alert("Select atleast one Record");
	            	return false;
	            }else {
	            	var state=window.confirm("Are you sure you want to delete ?");
					if(state){
						document.forms[0].keynos.value=val;                               
		                document.forms[0].action = "/journalVoucherApproval.do?method=deleteApprovals";
		                document.forms[0].submit();
					}else{
						return;
					}	                
                }
		  
		  }
		   
		  </script>
	</head>
	<body onload="loadReport();">
		<html:form action="/journalVoucherApproval.do?method=approvalSearch" method="post">
			<%=ScreenUtilities.screenHeader("jv.title")%>
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
														<input type="button" class="btn" value="Delete" onclick='deleteRec(document.forms[0].deleteRecord)' />
													</td>	
												</tr>
											</table>
										</td>
									</tr>
				<%if(request.getAttribute("records")!=null){%>
				<tr>
					<td align="center">
						<input type=hidden name="keynos" />
						<input type="checkbox" name="deleteRecord" value="" style="display:none;" />
						
									<display:table cellpadding="0" cellspacing="0" pagesize="5" class="GridBorder" style="width:100%;height:50px" keepStatus="true" export="true" sort="list" name="records" name="requestScope.records"  id='searchData' requestURI="/journalVoucherApproval.do?method=approvalSearch" >										
										<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">								
											<input type="checkbox" name="deleteRecord" value="<%=((JournalVoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>" />
										</display:column>										
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="partyType" sortable="true"  title="Party Type" /> 
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="trustType" sortable="true"  title="Trust Type" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherNo" sortable="true"  title="Voucher No." /> 
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherDate" sortable="true"  title="Voucher Date" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="preparationDate" sortable="true"  title="Preparation Date" /> 
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="approvedBy" sortable="true"  title="Approved BY" />										
										<display:column headerClass="GridHBg" class="GridLTCells" media="html">											
											<a href='/journalVoucherApproval.do?method=fwdtoapproval&&keyNo=<%=((JournalVoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>'> 										
										 		<img src='images/print.gif' border='0' alt='Report' onclick=""/> 
											</a>
										</display:column>
									</display:table>
								</td>					
				<%}%>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
