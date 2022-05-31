
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epis.utilities.CommonUtil" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String basePath = basePathBuf.toString() ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MultipleVoucherParam.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <script type="text/javascript" src="<%=basePath%>scripts/CommonFunctions.js"></script>
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/aimsfinancestyle.css" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/calendar.js" ></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js" ></script>
		<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>
		<script type="text/javascript" >
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		function report(){
		if(document.forms[0].fromvoucherNo.value=="")
		{
			alert("Please Enter From Voucher No[Mandatory]");
			document.forms[0].fromvoucherNo.focus();
			return false;
		}
		
		if(document.forms[0].tovoucherNo.value=="")
		{
			alert("Please Enter To Voucher No[Mandatory]");
			document.forms[0].tovoucherNo.focus();
			return false;
		}
		
		
		var url = "<%=basePathBuf%>Voucher?method=getMultipleVouchers&&fromvoucherNo="+document.forms[0].fromvoucherNo.value+
				"&&tovoucherNo="+document.forms[0].tovoucherNo.value+"&&voucherType="+document.forms[0].voucherType.value+"&&finyear="+document.forms[0].year.value+"&&accountNo="+document.forms[0].accountNo.value;
		
		var wind1 = window.open(url,"MultipleVouchers","toolbar=yes,statusbar=no,scrollbars=yes,menubar=no,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
		return false;		
		}
		
		function yearSelect(){
				var date = new Date();
				var year = parseInt(date.getYear());				
				var month = parseInt(date.getMonth())+1;	
				var year1 = parseInt((month<=3)?(year-1):(year));				
				for(cnt=2009;cnt<=year1 ;cnt++){					
					var yearEnd = (""+(cnt+1)).substring(2,4);
					document.forms[0].year.options.add(new Option(cnt+"-"+yearEnd,cnt+"-"+yearEnd)) ;				
				}
				document.forms[0].year.options[document.forms[0].year.options.length-1].selected=true;
				/*
				document.forms[0].bankName.focus();
				var month_values = new Array ("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
				for(cnt=0;cnt<12 ;cnt++){					
					document.forms[0].dmonth.options.add(new Option(month_values[cnt],month_values[cnt])) ;	
					if(cnt+1==month){
						document.forms[0].dmonth.options[document.forms[0].dmonth.options.length-1].selected=true;
					}
				}					
				//for(cnt=2009;cnt<=year ;cnt++){					
					//document.forms[0].dyear.options.add(new Option(cnt,cnt)) ;				
				}
				document.forms[0].dyear.options[document.forms[0].dyear.options.length-1].selected=true;	
				 divregion.style.display="none";	*/		
			}
			var xmlHttp;
			function createXMLHttpRequest(){
				if(window.ActiveXObject) {
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			 	} else if (window.XMLHttpRequest) {
					xmlHttp = new XMLHttpRequest();
			 	}
			}
			function getNodeValue(obj,tag) { 
				if(obj.getElementsByTagName(tag)[0].firstChild){
					return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
				}else return "";
			}
			
			function getBankDts(){
      	 		createXMLHttpRequest();	
      	 		var url ="<%=basePathBuf%>BankMaster?method=getBankList&&type=ajax&&bankName=";
    			xmlHttp.open("post", url, true);
				xmlHttp.onreadystatechange = getBanksList;
				xmlHttp.send(null);
			}
			var trustArray = new Array();
			function getBanksList(){
				if(xmlHttp.readyState ==4){		
					if(xmlHttp.status == 200){ 
						var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
						trustArray.length = 0;
				  		for(i=0;i<stype.length;i++){
				 			var bankName= getNodeValue(stype[i],'bankName');						 		
					 		var accountNo= getNodeValue(stype[i],'accountNo');
					 		var trustType= getNodeValue(stype[i],'trustType');
					 		
					 		document.forms[0].accountNo.options.add(new Option(bankName+" -- "+accountNo,accountNo)) ;						 					 		
						}
						document.forms[0].accountNo.value = '<%=request.getParameter("accountNo")==null?"":request.getParameter("accountNo")%>';						
						
					}
				}
			}	
		</script>
  </head>
  
  <body onload="yearSelect();getBankDts();">
  <form  method="post" onsubmit="javascript:return report()">
  
  <%=ScreenUtilities.screenHeader("multiplevoucher.report.title")%><table width="70%" border="0" align="center" cellpadding="1" cellspacing="1">
				
				
										<tr>
											<td class="tableTextBold" align=right width="120px" >
												FROM VOUCHER NO : 
											</td>
											<td align="left" nowrap>
												<input class="TextField" name='fromvoucherNo' maxlength="4" size="15" />
												
												
											</td>
										
											<td class="tableTextBold" align=right width="100px"  nowrap>
												To VOUCHER NO : 
											</td>
											<td align="left" nowrap>
												<input class="TextField" name='tovoucherNo' maxlength="4" size="15" />
												
											</td>
										</tr>
										
										<tr>
											<td class="tableTextBold" align="right">
										Financial Year :
									</td>
									<td align="left">
										<select class="TextField" name='year' style='width:80px'>
											<option value="">
												Select One
											</option>
										</select>
									</td>
									
									<td class="tableTextBold" align="right">
										Voucher Type :
									</td>
									<td align="left">
										<select class="TextField" name='voucherType' style='width:120px'>
											<option value="P" selected>
												Payment Voucher
											</option>
											<option value="R" >
												Receipt Voucher
											</option>
											
											<option value="C" >
												Contra Voucher
											</option>
										</select>
									</td>		
									
									
								</tr>
								<tr>
								<td class="tableTextBold" align="right"  nowrap>
									Bank Name & Account No:

								</td>
								<td align="left" >
								 	<select name="accountNo" class="TextField" style='width:200px'  ><option value="">Select One</option> </select>
								</td>
								<td colspan=2>&nbsp;</td>
								</tr>
								<tr>
											<td align="center" colspan="4">
												<input type="submit" class="butt" value="Submit" />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											</td>
										</tr>
						</table><%=ScreenUtilities.screenFooter()%>
		</form>
														
				
    
  </body>
</html>
