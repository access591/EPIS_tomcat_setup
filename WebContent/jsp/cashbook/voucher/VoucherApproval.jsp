

<%@ page import="java.util.*,com.epis.utilities.ScreenUtilities"%>
<%@ page import="com.epis.bean.cashbookDummy.VoucherInfo" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="17kb"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();

String voucherType = request.getParameter("voucherType") ==null?"":request.getParameter("voucherType"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />

		<title>AAI - Cashbook - Forms - Voucher Approval</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js"></script>
		<link rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
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

		    function chkDelete(field){
			    var val = "";
				for (var i=0; i < document.forms[0].deleteRecord.length; i++) {  				 	
						if (document.forms[0].deleteRecord[i].checked)  {	
						  val= val + document.forms[0].deleteRecord[i].value + "|";
			     	}    		       
	            }
                document.forms[0].keynos.value=val;                  
                document.forms[0].action = "<%=basePathBuf%>Voucher?method=deleteVoucher";
                document.forms[0].submit();
		    }
		    function checkDate(){
		    	if(document.forms[0].prepDate.value !=''){
					if(!convert_date(document.forms[0].prepDate)){
						return false;
					}
					convert_date(document.forms[0].prepDate);		
				}
			}
						var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			function openReport(url){
				var wind1 = window.open(url,"Voucher","toolbar=yes,statusbar=No,scrollbars=yes,menubar=No,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
			function loadReport(){
				<%
					if(session.getAttribute("typeapp") != null
						&& "first".equals(session.getAttribute("typeapp"))){
						session.removeAttribute("typeapp");
				%>
						openReport('<%=basePathBuf%>Voucher?method=getReport&&keyNo=<%=request.getParameter("keyNo")%>');
				<%
					}				
				%>
			}
		  </script>
	</head>

	<body  onload="yearSelect();loadReport();">
		<form action="<%=basePathBuf%>Voucher?method=getVoucherAppRecords" method="post" onsubmit="javascript : return checkDate()">
			<%=ScreenUtilities.screenHeader("Voucher Approval [Search]")%><table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
				
				
							
							<tr>
											<td class="tableTextBold" align="right">
												Bank Name  : 
											</td>
											<td align="left">
												<input class="TextField" type="text" size="18" name="bankName" maxlength="50" value="<%=request.getParameter("bankName")==null?"":request.getParameter("bankName")%>" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Financial Year  : 
											</td>
											<td align="left">
												<select class="TextField" name='year' style='width:80px'>
													<option value="">
														Select One
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Type of Voucher : 
											</td>
											<td align="left">
												<select class="TextField" name='voucherType' onchange="checkParty();" style='width:80px'>
													<option value="">
														Select One
													</option>
													<option value='R' <%="R".equals(voucherType)?"Selected":""%>>
														Receipt
													</option>
													<option value='P' <%="P".equals(voucherType)?"Selected":""%>>
														Payment
													</option>
													<option value='C' <%="C".equals(voucherType)?"Selected":""%>>
														Contra
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Preparation No. : 
											</td>
											<td align="left">
												<input class="TextField" type="text" size="18" name="prepNo" maxlength="50"  value="<%=request.getParameter("prepNo")==null?"":request.getParameter("prepNo")%>"/>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Preparation Dt. : 
											</td>
											<td align="left">
												<input class="TextField" name='prepDate' maxlength="11" size="13" tabindex=2 value="<%=request.getParameter("prepDate")==null?"":request.getParameter("prepDate")%>"/>
												&nbsp; <a href="javascript:show_calendar('forms[0].prepDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif"  /></a>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td align="center" colspan="2">
												<input type="submit" class="butt" value="Search" />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											</td>
										</tr>
									
							
							<tr>
								<td align=center class=label colspan=2>
									<font color=red size="2"><%=(request.getParameter("error")==null || "null".equals(request.getParameter("error"))?"":"Error : "+request.getParameter("error"))%></font>
									
								</td>
							</tr>
							</Table>   <%=ScreenUtilities.searchSeperator()%>
							<Table width="480" border="0" cellspacing="0" cellpadding="0" align="center">
							<input type="checkbox" name="deleteRecord" value="" style="display:none;" >
							<tr>
                          <td  height="29" align="left" valign="top"><table width="113" border="0" cellspacing="0" cellpadding="0">
                            <tr>       
                               <td ><input type="button" class="btn"  value="Delete" onclick='chkDelete(document.forms[0].deleteRecord)'></td>
                            </tr>
                          </table></td>
                          
                        </tr>
<%
	if(request.getAttribute("dataList")!=null){
%>
							<tr>
								<td align="center" width="100%">
									<input type=hidden name="keynos" />
									<input type="checkbox" name="deleteRecord" value="" style="display:none;" />
									<display:table cellpadding="0" cellspacing="0" class="GridBorder"  style="width:710px;height:50px" keepStatus="false" export="true" sort="list"  name="requestScope.dataList" pagesize="5" id='searchData' requestURI="/Voucher?method=searchRecords" >
									
										<display:setProperty name="export.amount" value="list" />
										<display:setProperty name="export.excel.filename" value="Voucher.xls" />
										<display:setProperty name="export.pdf.filename" value="Voucher.pdf" />
										<display:setProperty name="export.rtf.filename" value="Voucher.rtf" />
										<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">
											<input type="checkbox" name="deleteRecord" value="<%=((VoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>" />
										</display:column>
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="bankName" sortable="true" headerClass="sortable" title="Bank Name" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="finYear" sortable="true" title="Financial Year" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="voucherType" sortable="true" title="Voucher Type" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="preparedDt" sortable="true" title="Prepared Date" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells"property="partyType" sortable="true" title="Party Type" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="amount" sortable="true" title="Amount" />
										<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">
											<a href='<%=basePathBuf%>Voucher?method=getReport&&keyNo=<%=((VoucherInfo)pageContext.getAttribute("searchData")).getKeyNo()%>&&type=approve'> 										
											 <img src='images/print.gif' border='0' alt='Report' onclick=""/> 
											</a>
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

