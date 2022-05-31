
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>

<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page import="com.epis.bean.cashbookDummy.VoucherInfo" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />

		<title>AAI - Cashbook - Forms - Voucher</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js"></script>
		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
		<script type="text/javascript">
		function chkTransfer(){
			    var val = "";
				for (var i=0; i < document.forms[0].transferRecord.length; i++) {  				 	
						if (document.forms[0].transferRecord[i].checked)  {	
						  val= val + document.forms[0].transferRecord[i].value + "|";
			     	}    		       
	            }
                document.forms[0].keynos.value=val;                  
                document.forms[0].action = "<%=basePathBuf%>Voucher?method=ePaymentTransfers";
                document.forms[0].submit();
		    }
		</script>
	</head>
	<body  >
		<form action="<%=basePathBuf%>Voucher?method=ePaymentSearch" method="post" onsubmit="javascript:return checkDate()">
			<%=ScreenUtilities.screenHeader("E-Payment Transfers [Search]")%><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				
				
						
										<tr>
											<td class="label" align="right">
												From Date
											</td>
											<td align="left">
												<input name='fromDate' maxlength="11" size="13" tabindex=2 value="<%=request.getParameter("vDate")==null?"":request.getParameter("vDate")%>"/>
												&nbsp; <a href="javascript:show_calendar('forms[0].fromDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif"  /></a>
											</td>
										</tr>
										<tr>
											<td class="label" align="right">
												To Date
											</td>
											<td align="left">
												<input name='toDate' maxlength="11" size="13" tabindex=2 value="<%=request.getParameter("vDate")==null?"":request.getParameter("vDate")%>"/>
												&nbsp; <a href="javascript:show_calendar('forms[0].toDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif"  /></a>
											</td>
										</tr>	
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>								
										<tr>
											<td align="center" colspan="2">
												<input type="submit" class="butt" value="OK" />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											
								</td>
							</tr>
							</Table>   <%=ScreenUtilities.searchSeperator()%>
							<Table width="406" border="0" cellspacing="0" cellpadding="0" align="center">
							<input type="checkbox" name="deleteRecord" value="" style="display:none;" >
							<tr>
                          <td  height="29" align="left" valign="top"><table width="113" border="0" cellspacing="0" cellpadding="0">
                            <tr>       
                               <td ><input type="button" class="butt"  value="E-Transfers" onclick='chkTransfer()'></td>
                            </tr>
                          </table></td>
                          
                        </tr>
<%
	if(request.getAttribute("voucherList")!=null ){
%>
							<tr>
								<td align="center" width="100%">
									<input type="checkbox" name="transferRecord" value="" style="display:none;" />
									<input type=hidden name="keynos" />
									<display:table cellpadding="0" cellspacing="0" class="GridBorder" style="width:710px;height:100px" keepStatus="true" export="true" sort="list"  name="requestScope.voucherList" pagesize="10" id='searchData' requestURI="/Voucher?method=ePaymentSearch" >
										<display:setProperty name="export.amount" value="list" />
										<display:setProperty name="export.excel.filename" value="Voucher.xls" />
										<display:setProperty name="export.pdf.filename" value="Voucher.pdf" />
										<display:setProperty name="export.rtf.filename" value="Voucher.rtf" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" media="html" headerClass="GridHBg" class="GridLCells" >
											<input type="checkbox" name="transferRecord" value="<%=((VoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>" />
										</display:column>
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="bankName" sortable="true" headerClass="sortable" title="Bank Name" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="finYear" sortable="true" title="Financial Year" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="partyType" sortable="true" title="Party Type" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherNo" sortable="true" title="Voucher No" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherDt" sortable="true" title="Voucher Date" />
										
									</display:table>
								</td>
							</tr>
<%
	}
%>							
										
			</table><%=ScreenUtilities.searchFooter()%>
		</form>
	</body>
</html>

