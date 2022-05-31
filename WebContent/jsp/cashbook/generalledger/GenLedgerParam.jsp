
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epis.bean.cashbookDummy.AccountingCodeInfo" %>
<%@ page import="com.epis.utilities.CommonUtil" %>
<%@ page import="com.epis.service.cashbookDummy.AccountingCodeService" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String basePath = basePathBuf.toString() ;
CommonUtil util = new CommonUtil();
int gridLength = util.gridLength();
AccountingCodeService service = new AccountingCodeService();
List accList = service.getAccountList(new AccountingCodeInfo(),"");
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
			function getToAccountCodes(particular)
			{
				createXMLHttpRequest();	
      	 		var url ="<%=basePathBuf%>AccountingCode?method=getAccountList&&type=ajax&&particular="+particular;
    			xmlHttp.open("post", url, true);
				xmlHttp.onreadystatechange = getToACCList;
				xmlHttp.send(null);
			}
			function getToACCList()
			{
			
					if(xmlHttp.readyState ==4){	
					if(xmlHttp.status == 200){ 
						var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
						if(stype.length==0){
					 		var code = document.getElementById("CODE");					   	  
					  	}else{
					  		tosuggestions.style.display="block";
							tosuggestions.innerHTML="";					
					  		var str="";
					  		str+="<table  class=border border=1 width=100% ><tr><td>Code</td><td>Name</td><td>Type</td></tr>"; 
					 		for(i=0;i<stype.length;i++){
						 		var code= getNodeValue(stype[i],'CODE').replace(/\$/g,"&");
						 		var name= getNodeValue(stype[i],'NAME').replace(/\$/g,"&");
						 		var type= getNodeValue(stype[i],'TYPE').replace(/\$/g,"&");
				 	 	 	 	str+="<tr><td><a href='javascript:void(0)' onclick=\"todetails('"+code+"','"+name+"','"+type+"');\" >"+code+"</a></td><td><a href='javascript:void(0)' onclick=\"todetails('"+code+"','"+name+"','"+type+"');\" >"+name+"</a></td><td><a href='javascript:void(0)' onclick=\"todetails('"+code+"','"+name+"','"+type+"');\" >"+(type=='null'?"":type)+"</a></td></tr>";
				 	 		}
							str+="</table>";		
			 	    		tosuggestions.innerHTML=str;				 	    	
						}
					}
				}
			
			}
			function getACCList(){			
				if(xmlHttp.readyState ==4){	
					if(xmlHttp.status == 200){ 
						var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
						if(stype.length==0){
					 		var code = document.getElementById("CODE");					   	  
					  	}else{
					  		suggestions.style.display="block";
							suggestions.innerHTML="";					
					  		var str="";
					  		str+="<table  class=border border=1 width=100% ><tr><td>Code</td><td>Name</td><td>Type</td></tr>"; 
					 		for(i=0;i<stype.length;i++){
						 		var code= getNodeValue(stype[i],'CODE').replace(/\$/g,"&");
						 		var name= getNodeValue(stype[i],'NAME').replace(/\$/g,"&");
						 		var type= getNodeValue(stype[i],'TYPE').replace(/\$/g,"&");
				 	 	 	 	str+="<tr><td><a href='javascript:void(0)' onclick=\"details('"+code+"','"+name+"','"+type+"');\" >"+code+"</a></td><td><a href='javascript:void(0)' onclick=\"details('"+code+"','"+name+"','"+type+"');\" >"+name+"</a></td><td><a href='javascript:void(0)' onclick=\"details('"+code+"','"+name+"','"+type+"');\" >"+(type=='null'?"":type)+"</a></td></tr>";
				 	 		}
							str+="</table>";		
			 	    		suggestions.innerHTML=str;				 	    	
						}
					}
				}
			}
			function details(accHead,particular){	
		   			document.forms[0].accHead.value=accHead;
		   			document.forms[0].particular.value=particular;	
		   			suggestions.style.display="none";	   		
			}
			function todetails(accHead,particular){	
		   			document.forms[0].toaccHead.value=accHead;
		   			document.forms[0].toParticular.value=particular;	
		   			tosuggestions.style.display="none";	   		
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
				if(document.forms[0].accHead.value !="" && document.forms[0].toaccHead.value!="")
				{
						if(parseFloat(document.forms[0].accHead.value)>parseFloat(document.forms[0].toaccHead.value))
						{
							alert("To Particulars Should be greater than From Particulars");
							document.forms[0].toaccHead.focus();
							return false;
						}
				}
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				if(document.forms[0].reportContent.checked)
					document.forms[0].reportContent.value = 'Y';
				else
					document.forms[0].reportContent.value = 'N';
				if(document.forms[0].zerotransaction.checked)
					document.forms[0].zerotransaction.value='Y';
				else
					document.forms[0].zerotransaction.value='N';
					
				var url = "<%=basePathBuf%>Voucher?method=getGenLedger&&fromDate="+document.forms[0].fromDate.value+
				"&&toDate="+document.forms[0].toDate.value+"&&trusttype="+document.forms[0].trusttype.value+
				"&&accHead="+document.forms[0].accHead.value+"&&toaccHead="+document.forms[0].toaccHead.value+"&&voucherType="+document.forms[0].voucherType.value+
				"&&reportContent="+document.forms[0].reportContent.value+"&&reportType="+
				document.forms[0].reportType.value+"&&reportbal="+document.forms[0].reportbal.value+"&zerotransaction="+document.forms[0].zerotransaction.value;
				var wind1 = window.open(url,"LedgerAccount","toolbar=yes,statusbar=no,scrollbars=yes,menubar=no,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
			}
			
			function restAccountHead()
			{
			suggestions.style.display='none';
			tosuggestions.style.display='none';
			}
		</script>
	</head>

	<body  onload="suggestions.style.display='none';tosuggestions.style.display='none';">
		<form  method="post" onsubmit="javascript:return report()">
			<%=ScreenUtilities.screenHeader("General Ledger [Search]")%><table width="70%" border="0" align="center" cellpadding="1" cellspacing="1">
				
				
										<tr>
											<td class="tableTextBold" align=right width="100px" >
												From Date : 
											</td>
											<td align="left" nowrap>
												<input class="TextField" name='fromDate' maxlength="11" size="15" onmouseout="restAccountHead();"/>
												&nbsp; <a href="javascript:show_calendar('forms[0].fromDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" src="" alt="" /></a>
												
											</td>
										
											<td class="tableTextBold" align=right width="100px"  nowrap>
												To Date : 
											</td>
											<td align="left" nowrap>
												<input class="TextField" name='toDate' maxlength="11" size="15" />
												&nbsp; <a href="javascript:show_calendar('forms[0].toDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" src="" alt="" /></a>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align=right width="100px"  nowrap>
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
											<td class="tableTextBold" align=right >
												Type of Voucher :
											</td>
											<td align="left">
												<select class="TextField" name='voucherType' style='width:80px'>
													<option value="">
														Select One
													</option>
													<option value='R'>
														Receipt
													</option>
													<option value='P'>
														Payment
													</option>
													<option value='C'>
														Contra
													</option>
													<option value='J'>
														Journal
													</option>
												</select>
											</td>
										</tr> 
										<tr>
											<td class="tableTextBold" align=right width="100px"  nowrap>
											From Particular : 
											</td>
											<td align="left">
												<input class="TextField" type="text" size="18" name="particular" maxlength="50"  onkeyup="getAccountCodes(this.value)"/>
												<div id="suggestions" class="containdiv1" style="left:auto ;top:auto;" ></div>
											</td>
											<td class="tableTextBold" align=right width="100px"  nowrap>
												Account Code : 
											</td>
											<td align="left" nowrap>															
												<input class="TextField" type="text" size="10" name="accHead" maxlength="50"  readonly="readonly" />
											</td>
										</tr>
										
										<tr>
											<td class="tableTextBold" align=right width="100px"  nowrap>
											To Particular : 
											</td>
											<td align="left">
												<input class="TextField" type="text" size="18" name="toParticular" maxlength="50"  onkeyup="getToAccountCodes(this.value)"/>
												<div id="tosuggestions" class="containdiv1" style="left:auto ;top:auto;" ></div>
											</td>
											<td class="tableTextBold" align=right width="100px"  nowrap>
												Account Code : 
											</td>
											<td align="left" nowrap>															
												<input class="TextField" type="text" size="10" name="toaccHead" maxlength="50"  readonly="readonly" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold"  align="right" nowrap>
												Report Type : 
											</td>
											<td align="left" nowrap >
												<select name='reportType' style="WIDTH:70px">
												<Option value="html">HTML</Option>
												<Option value="excel">MS-Excel</Option>
												</select>
											</td>
											<td class="tableTextBold"  align="right" nowrap="nowrap">
												Without Narration :
											</td>
											<td class="label" width="150px" align="left" nowrap>
												<input type="checkbox" name="reportContent" value='Y'/>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold"  align="right" nowrap>
												Closing Balances : 
											</td>
											<td align="left" nowrap >
												<select class="TextField" name='reportbal' style="WIDTH:70px">
												<Option value="D">Daily</Option>
												<Option value="M">Monthly</Option>
												<Option value="Y">Yearly</Option>
												</select>
											</td>
											
											<td class="tableTextBold"  align="right" nowrap="nowrap">
												Zero Transaction :
											</td>
											<td class="label" width="150px" align="left" nowrap>
												<input type="checkbox" name="zerotransaction"/>
											</td>											
										</tr>
										<tr>
											
										</tr>
										<tr>
											<td align="center" colspan="5">
												<input type="submit" class="butt" value="Submit" />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											</td>
										</tr>
									
			</table><%=ScreenUtilities.screenFooter()%>
		</form>
	</body>
</html>
		