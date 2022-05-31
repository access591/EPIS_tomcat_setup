<%@ page import="com.epis.bean.cashbookDummy.VoucherInfo,com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();
String type = (request.getParameter("type")==null || "null".equals(request.getParameter("type"))) ?"":request.getParameter("type");
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

		
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js"></script>
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
		<script type="text/javascript">
			function yearSelect(){
				var date = new Date();
				var year = parseInt(date.getYear());
				for(cnt=2003;cnt<=year;cnt++){
					var yearEnd = (""+(cnt+1)).substring(2,4);
					document.forms[0].year.options.add(new Option(cnt+"-"+yearEnd,cnt+"-"+yearEnd)) ;				
				}				
				document.forms[0].bankName.focus();
				document.forms[0].year.value='<%=request.getParameter("year")==null?"":request.getParameter("year")%>';
			}
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			function openReport(url){
				var wind1 = window.open(url,"Voucher","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
			function loadReport(){
				<%
					if(request.getParameter("keyNo")!=null && ! "".equals(request.getParameter("keyNo"))){
				%>
						openReport('<%=basePathBuf%>Voucher?method=getReport&&keyNo=<%=request.getParameter("keyNo")%>');
				<%
					}				
				%>
			}
			function checkDate(){
		    	if(document.forms[0].vDate.value !=''){
					if(!convert_date(document.forms[0].vDate)){
						return false;
					}
					convert_date(document.forms[0].vDate);		
				}
				if(document.forms[0].prepDate.value !=''){
					if(!convert_date(document.forms[0].prepDate)){
						return false;
					}
					convert_date(document.forms[0].prepDate);		
				}
			}
		</script>
	</head>

	<body onload="loadReport();yearSelect();">
		<form action="<%=basePathBuf%>Voucher?method=searchRecords" method="post" onsubmit="javascript:return checkDate()">
			<%=ScreenUtilities.screenHeader("Voucher [Search]")%><table width="100%" border="0" align="center" cellpadding="1" cellspacing=1">
				
				
										<tr>
											<td class="tableTextBold" align="right">
												Bank Name : 
											</td>
											<td align="left">
												<input class="TextField" type="text" size="18" name="bankName" maxlength="50"  value="<%=request.getParameter("bankName")==null?"":request.getParameter("bankName")%>"/>
											</td>
										   <td>&nbsp;</td>
											<td class="tableTextBold" align="right">
												Financial Year : 
											</td>
											<td align="left">
												<select class="TextField" name='year' style='width:80px'>
													<option value="">
														Select One
													</option>
												</select>
												<input type="hidden" name="voucherType" value='<%=type%>'/>
											</td>
										</tr>
										
										<tr>
											<td class="tableTextBold" align="right">
												Voucher Date : 
											</td>
											<td align="left">
												<input class="TextField" name='vDate' maxlength="11" size="13" tabindex=2 value="<%=request.getParameter("vDate")==null?"":request.getParameter("vDate")%>"/>
												&nbsp; <a href="javascript:show_calendar('forms[0].vDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif"  /></a>
											</td>
											 <td>&nbsp;</td>
											<td class="tableTextBold"  align="right">
												Preparation Date : 
											</td>
											<td align="left" >
												<input class="TextField" name='prepDate' maxlength="11" size="13" tabindex=3 value="<%=request.getParameter("prepDate")==null?"":request.getParameter("prepDate")%>"/>
												&nbsp; <a href="javascript:show_calendar('forms[0].prepDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif"  /></a>
												
											</td>	
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Voucher No. : 
											</td>
											<td align="left">
												<input class="TextField" type="text" size="18" name="vNo" maxlength="50"  value="<%=request.getParameter("vNo")==null?"":request.getParameter("vNo")%>"/>
											</td>
											<td></td>
											<td class="tableTextBold" align="right">
												Party /Emp. PFID /Bank Acc. 
											</td>
											<td align="left" >
												<input type="text" size="18" class="TextField" name="partyName" maxlength="50"  value="<%=request.getParameter("partyName")==null?"":request.getParameter("partyName")%>"/>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td align="center" colspan="5">
												
												<input type="submit" class="butt" value="Search" />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											</td>
										</tr>
									
							
							</table>   <%=ScreenUtilities.searchSeperator()%>
							<Table width="500" border="0" cellspacing="0" cellpadding="0" align="center">
							
							<tr>
                          <td  height="29" align="left" valign="top"><table width="113" border="0" cellspacing="0" cellpadding="0">
                            <tr>  
                           		<td nowrap="nowrap">
<%if(!"".equals(StringUtility.checknull(request.getParameter("type")))){%>                           		
                           			<input type="button" class="btn" value="Add" onclick="javascript:window.location.href='<%=basePath%>jsp/cashbook/voucher/<%="C".equals(request.getParameter("type"))?"Contra":""%>Voucher.jsp?type=<%=request.getParameter("type")%>'" />
<%}else{%>
									<input type="button" class="btn" value="Add Payment Voucher" onclick="javascript:window.location.href='<%=basePath%>jsp/cashbook/voucher/Voucher.jsp?type=P'" />
									<input type="button" class="btn" value="Add Receipt Voucher" onclick="javascript:window.location.href='<%=basePath%>jsp/cashbook/voucher/Voucher.jsp?type='" />
									<input type="button" class="btn" value="Add Contra Voucher" onclick="javascript:window.location.href='<%=basePath%>jsp/cashbook/voucher/ContraVoucher.jsp?type=C'" />
<%}%>                           			
                           		</td>
                              <td ></td>
                            </tr>
                          </table></td>
                          
                        </tr>
<%
	if(request.getAttribute("dataList")!=null ){
%>
							<tr>
								<td align="center" width="90%">
									<display:table cellpadding="0" cellspacing="0" class="GridBorder" style="width:710px;height:50px" keepStatus="true" export="true" sort="list"  name="requestScope.dataList" pagesize="15" id='searchData' requestURI="/Voucher?method=searchRecords" >
										<display:setProperty name="export.amount" value="list" />
										<display:setProperty name="export.excel.filename" value="Voucher.xls" />
										<display:setProperty name="export.pdf.filename" value="Voucher.pdf" />
										<display:setProperty name="export.rtf.filename" value="Voucher.rtf" />
										
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="bankName" sortable="true" headerClass="sortable" title="Bank Name" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="finYear" sortable="true" title="Financial Year" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherType" sortable="true" title="Voucher Type" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="partyType" sortable="true" title="Party Type" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherNo" sortable="true" title="Voucher No" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherDt" sortable="true" title="Voucher Date" decorator="com.epis.utilities.DateFormatDecorator" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="amount" sortable="true" title="Amount" />
										<logic:present name="searchData">
											<logic:equal property="voucherNo" name="searchData" value="">
												<logic:notEqual property="voucherType" name="searchData" value="Contra">
														<display:column headerClass="GridHBg" class="GridLTCells" media="html">
															<a href='<%=basePathBuf%>Voucher?method=editVoucher&&type=<%=request.getParameter("type")%>&&keyNo=<%=((VoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>'> <img src='images/icon-edit.gif' border='0' alt='Edit' /> </a>
														</display:column>
												</logic:notEqual>
												<logic:equal property="voucherType" name="searchData" value="Contra">
														<display:column headerClass="GridHBg" class="GridLTCells" media="html">
															<a href='<%=basePathBuf%>Voucher?method=editVoucher&&type=C&&keyNo=<%=((VoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>'> <img src='images/icon-edit.gif' border='0' alt='Edit' /> </a>
														</display:column>
												</logic:equal>																			
											</logic:equal>
											<logic:notEqual property="voucherNo" name="searchData" value="">
												<logic:equal property="voucherType" name="searchData" value="Payment">
													<logic:equal property="transactionId" name="searchData" value="">
														<display:column headerClass="GridHBg" class="GridLTCells" media="html">
															<a href='<%=basePathBuf%>Voucher?method=editNotification&&type=<%=request.getParameter("type")%>&&keyNo=<%=((VoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>'> <img src='images/icon-edit.gif' border='0' alt='Edit' /> </a>
														</display:column>
													</logic:equal>	
													<logic:notEqual property="transactionId" name="searchData" value="">
														<display:column headerClass="GridHBg" class="GridLTCells" media="html">
															<img src='images/lockIcon.gif' border='0' alt='Lock' />										
														</display:column>
													</logic:notEqual>	
												</logic:equal>					
												<logic:notEqual property="voucherType" name="searchData" value="Payment">
													<display:column headerClass="GridHBg" class="GridLTCells" media="html">
														<img src='images/lockIcon.gif' border='0' alt='Lock' />								
													</display:column>
												</logic:notEqual>
											</logic:notEqual>
										</logic:present>
										<display:column headerClass="GridHBg" class="GridLTCells" media="html">
											 <img src='images/print.gif' border='0' alt='Report' onclick="openReport('<%=basePathBuf%>Voucher?method=getReport&&keyNo=<%=((VoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>');"/> 
										</display:column>									
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

