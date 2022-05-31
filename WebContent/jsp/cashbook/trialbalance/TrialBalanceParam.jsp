<%@ page import="com.epis.service.cashbookDummy.AccountingCodeService" %>
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String basePath = basePathBuf.toString() ;
AccountingCodeService service = new AccountingCodeService();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>AAI - Cashbook - Master - Account Code Search Master</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script type="text/javascript" src="<%=basePath%>scripts/CommonFunctions.js"></script>
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/aimsfinancestyle.css" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/calendar.js" ></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js" ></script>
		<script type="text/javascript" >
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
			function getAccountCodes(particular){  
       	 		createXMLHttpRequest();	
      	 		var url ="<%=basePathBuf%>AccountingCode?method=getAccountList&&type=ajax&&particular="+particular;
    			xmlHttp.open("post", url, true);
				xmlHttp.onreadystatechange = getACCList;
				xmlHttp.send(null);
			}
			function getACCList(){			
				if(xmlHttp.readyState ==4){	
					if(xmlHttp.status == 200){ 
						var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
						if(stype.length==0){
					 		var code = document.getElementById("CODE");					   	  
					  	}else{
					  		//suggestions.style.display="block";
							//suggestions.innerHTML="";					
					  		var str="";
					  		str+="<table  class=border border=1 width=100% ><tr><td>Code</td><td>Name</td><td>Type</td></tr>"; 
					 		for(i=0;i<stype.length;i++){
						 		var code= getNodeValue(stype[i],'CODE').replace(/\$/g,"&");
						 		var name= getNodeValue(stype[i],'NAME').replace(/\$/g,"&");
						 		var type= getNodeValue(stype[i],'TYPE').replace(/\$/g,"&");
				 	 	 	 	str+="<tr><td><a href='javascript:void(0)' onclick=\"details('"+code+"','"+name+"','"+type+"');\" >"+code+"</a></td><td><a href='javascript:void(0)' onclick=\"details('"+code+"','"+name+"','"+type+"');\" >"+name+"</a></td><td><a href='javascript:void(0)' onclick=\"details('"+code+"','"+name+"','"+type+"');\" >"+(type=='null'?"":type)+"</a></td></tr>";
				 	 		}
							str+="</table>";		
			 	    	//	suggestions.innerHTML=str;				 	    	
						}
					}
				}
			}
			function details(accHead,particular){	
		   			document.forms[0].accHead.value=accHead;
		   			document.forms[0].particular.value=particular;	
		   		//	suggestions.style.display="none";	   		
			}
			function report(){
			
				if(document.forms[0].fromDate.value != ""){					
					if(!convert_date(document.forms[0].fromDate))
						return false;				
					convert_date(document.forms[0].fromDate);
				}
				if(document.forms[0].toDate.value != ""){					
					if(!convert_date(document.forms[0].toDate))
						return false;				
					convert_date(document.forms[0].toDate);
				}
				var grouping = document.forms[0].grouping.value;
				if(document.forms[0].grouping.checked == false){
					grouping='N';
				}
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "<%=basePathBuf%>Voucher?method=getTrialBalance&&fromDate="+document.forms[0].fromDate.value+"&&toDate="+document.forms[0].toDate.value+"&&trusttype="+document.forms[0].trusttype.value+"&&reportType="+document.forms[0].reportType.value+"&&report="+document.forms[0].reportFormat.value+"&&grouping="+grouping;
				var wind1 = window.open(url,"TrailBalanceAccount","toolbar=yes,statusbar=no,scrollbars=yes,menubar=no,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
			}
		</script>
	</head>

	<body  >
		<form  method="post" onsubmit="javascript:return report()">
			<%=ScreenUtilities.screenHeader("Trail Balance [Search]")%><table width="70%" border="0" align="center" cellpadding="1" cellspacing="1">
				
				
							
										<tr>
											<td class="tableTextBold" width="100px" align="right">
												From Date : 
											</td>
											<td align="left" nowrap>
												<input class="TextField" name='fromDate' maxlength="11" size="15" />
												&nbsp; <a href="javascript:show_calendar('forms[0].fromDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" src="" alt="" /></a>
												
											</td>
										
											<td class="tableTextBold" width="100px" align="right" nowrap>
												To Date : 
											</td>
											<td align="left" nowrap>
												<input class="TextField" name='toDate' maxlength="11" size="15" />
												&nbsp; <a href="javascript:show_calendar('forms[0].toDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" src="" alt="" /></a>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" width="100px" align="right" nowrap>
												Trust Type : 
											</td>
											<td align="left" nowrap>
												<select class="TextField" name='trusttype' style='width:80px' onchange="getBankCodes()" >
													<option value="">
														Select One
													</option>
													<!-- <option value='IAAI ECPF'>
														IAAI ECPF
													</option>
													<option value='NAA ECPF'>
														NAA ECPF
													</option>-->
													<option value='AAI EPF' selected="selected">
														AAI EPF
													</option>
												</select>										
											</td>
											<td class="tableTextBold" width="100px" align="right" nowrap>
												Report Type : 
											</td>
											<td align="left" nowrap>
												<select class="TextField" name='reportType' style='width:130px' onchange="getBankCodes()" >
													<option value='1'>
														With Opening Balance
													</option>
													<option value='2'>
														WithOut Opening Balance
													</option>
													<option value='3'>
														Transaction Wise
													</option>
												</select>										
											</td>	
										</tr>
										<tr>	
											<td class="tableTextBold" width="100px" align="right" nowrap>
													Report Format : 
												</td>
												<td align="left" nowrap>
													<select class="TextField" name='reportFormat' style='width:130px' >
														<option value='html'>
															HTML
														</option>
														<option value='excel'>
															MS-Excel
														</option>
													</select>										
												</td>	
												<td class="tableTextBold" width="100px" align="right" nowrap>
													<input type="checkbox" name="grouping" value='Y'/>
												</td>
												<td align="left" nowrap  class="tableTextBold">
													Grouping based on Accounting Code Types
												</td>	
																		
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
									</table><%=ScreenUtilities.screenFooter()%>
								
		</form>
	</body>
</html>
		