

<%@ page import="java.util.*,com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherInfo" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherDetails" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

String basePath = basePathBuf.toString() ;
VoucherInfo voucherInfo = (VoucherInfo) request
					.getAttribute("einfo");
List vDetails = voucherInfo.getVoucherDetails();
VoucherDetails voucherDetails = (VoucherDetails)vDetails.get(0);
DecimalFormat df=new DecimalFormat("##########.####");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>AAI - Cashbook - Master -Contra Voucher</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js"></script>
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
		<script type="text/javascript">
			function checkVoucher(){
				
				if(document.forms[0].accountNo.value == ""){
					alert("Please Enter Bank Name (Mandatory)");
					document.forms[0].accountNo.focus();
					return false;
				}
				if(document.forms[0].year.value == ""){
					alert("Please Select Financial Year (Mandatory)");
					document.forms[0].year.focus();
					return false;
				}
				if(document.forms[0].trusttype.value == ""){
					alert("Please Select Trust type (Mandatory)");
					document.forms[0].trusttype.focus();
					return false;
				}
				if(document.forms[0].contraAccountNo.value == ""){
					alert("Please Select To Bank Name (Mandatory)");
					document.forms[0].contraAccountNo.focus();
					return false;
				}
				if(document.forms[0].accountNo.value == document.forms[0].contraAccountNo.value){
					alert("Please Select Another Bank Name \n The Accounts selected  are same");					
					document.forms[0].contraAccountNo.focus();
					return false;
				}
				if(document.forms[0].accHead.value == "" ){
					alert("Please Enter Account Head (Mandatory)");
					document.forms[0].accHead.focus();
					return false;
				}
				if(document.forms[0].amount.value == "" ){
					document.forms[0].amount.value = "0";
					alert("Please Enter Amount (Mandatory)");
					document.forms[0].amount.focus();
					return false;
				}
				if(!ValidateFloatPoint(document.forms[0].amount.value)){					
					document.forms[0].amount.focus();
					return false;
				}	
				if(document.forms[0].prepDate.value == ""){
					alert("Please Enter To Date (Mandatory)");
					document.forms[0].prepDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].prepDate)){
					return false;
				}
				convert_date(document.forms[0].prepDate);
				if(document.forms[0].dmonth.value =='' && document.forms[0].dyear.value !=''){
					alert("Please Select Month");
					document.forms[0].dmonth.focus();
					return false;
				}
				if(document.forms[0].dmonth.value !='' && document.forms[0].dyear.value ==''){
					alert("Please Select Year");
					document.forms[0].dyear.focus();
					return false;
				}
				if(document.forms[0].dmonth.value !='' && document.forms[0].dyear.value !=''){
					document.forms[0].monthYear.value = document.forms[0].dmonth.value+"/"+document.forms[0].dyear.value;
				}	
			}
			
			function popupWindow(mylink, windowname){
				if (! window.focus){
					return true;
				}
				var href;
				if (typeof(mylink) == 'string'){
				   href=mylink;
				} else {
					href=mylink.href;
				}
				progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
				return true;
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
				//document.forms[0].year.options[document.forms[0].year.options.length-1].selected=true;
				document.forms[0].year.value='<%=voucherInfo.getFinYear()%>';
				//document.forms[0].bankName.focus();
				var month_values = new Array ("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
				for(cnt=0;cnt<12 ;cnt++){					
					document.forms[0].dmonth.options.add(new Option(month_values[cnt],month_values[cnt])) ;	
				}					
				for(cnt=2003;cnt<=year ;cnt++){					
					document.forms[0].dyear.options.add(new Option(cnt,cnt)) ;				
				}		
				<%
					String monYear = voucherDetails.getMonthYear();
				if(monYear != null && !"".equals(monYear)){
				%>
					document.forms[0].dmonth.value='<%=monYear.substring(0,monYear.indexOf("/"))%>';
					document.forms[0].dyear.value='<%=monYear.substring(monYear.indexOf("/")+1)%>';
				<%
				}
				%>	getBankDts();					
			}	
			
			function checkBankName(){
				if(document.forms[0].contraAccountNo.value == ''){
					popupWindow('<%=basePath%>cashbook/BankInfo.jsp','AAI');
				}else {
					popupWindow('<%=basePath%>cashbook/BankInfo.jsp?bank=from&&type=other&&accountNo='+document.forms[0].contraAccountNo.value,'AAI');
				}
			}
			
			function otherBankDetails(bankName,accountNo,accHead,particular){
				document.forms[0].contraBankName.value=bankName;
				document.forms[0].contraAccountNo.value=accountNo;
				document.forms[0].accHead.value=accHead;
		   		//suggestions.style.display="none";	
			}
			function bankDetails(bankName,accountNo,trustType){
				document.forms[0].bankName.value=bankName;
				document.forms[0].accountNo.value=accountNo;	
				document.forms[0].trusttype.value=(trustType=="null"?"":trustType);	
				//suggestions.style.display="none";	
				
			}
			var banktype="";
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
			function getBankDetails(type){
				var bankName='';
				banktype = type;
				if(type=='B')
	      	 		bankName=document.forms[0].bankName.value; 
	      	 	else
	      	 		bankName=document.forms[0].contraBankName.value; 
      	 		createXMLHttpRequest();	
      	 		var url ="<%=basePathBuf%>BankMaster?method=getBankList&&type=ajax&&bankName="+bankName;
    			xmlHttp.open("post", url, true);
				xmlHttp.onreadystatechange = getBankList;
				xmlHttp.send(null);
			}
			function getBankList(){
				if(xmlHttp.readyState ==4){		
					if(xmlHttp.status == 200){ 
						var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
					  	if(stype.length==0){
					 		var bankName = document.getElementById("bankName");					   	  
					  	}else{
							//suggestions.style.display="block";
							//suggestions.innerHTML="";
					  		var str="";
					  		str+="<table  class=border border=1 width=100% ><tr><td>Account No.</td><td>Bank Name</td></tr>"; 
					 		for(i=0;i<stype.length;i++){
					 			if(banktype=='B'){
					 				if(document.forms[0].contraAccountNo.value!=getNodeValue(stype[i],'accountNo')){
						 				var bankName= getNodeValue(stype[i],'bankName');						 		
								 		var accountNo= getNodeValue(stype[i],'accountNo');
								 		var trustType= getNodeValue(stype[i],'trustType');
						 	 	 	 	str+="<tr style='cursor:hand'><td><a style='' onclick=\"bankDetails('"+bankName+"','"+accountNo+"','"+trustType+"');\" >"+accountNo+"</a></td><td><a  onclick=\"bankDetails('"+bankName+"','"+accountNo+"','"+trustType+"');\" >"+bankName+"</a></td></tr>";					 				
					 	 	 	 	}
					 	 	 	 }else if(document.forms[0].accountNo.value!=getNodeValue(stype[i],'accountNo')){
							 		var bankName= getNodeValue(stype[i],'bankName');						 		
							 		var accountNo= getNodeValue(stype[i],'accountNo');
							 		var accHead= getNodeValue(stype[i],'accHead');
							 		var particular= getNodeValue(stype[i],'particular');
					 	 	 	 	str+="<tr style='cursor:hand'><td><a  onclick=\"otherBankDetails('"+bankName+"','"+accountNo+"','"+accHead+"','"+particular+"');\" >"+accountNo+"</a></td><td><a  onclick=\"otherBankDetails('"+bankName+"','"+accountNo+"','"+accHead+"','"+particular+"');\" >"+bankName+"</a></td></tr>";					 					
					 	 	 	 }
				 	 		}
							str+="</table>";
				 	    	//suggestions.innerHTML=str;				 	    	
						}
					}
				}
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
					 		var accHead= getNodeValue(stype[i],'accHead');
					 		trustArray[trustArray.length]=[accountNo,trustType,accHead];
					 		document.forms[0].accountNo.options.add(new Option(bankName+" -- "+accountNo,accountNo)) ;
					 		document.forms[0].contraAccountNo.options.add(new Option(bankName+" -- "+accountNo,accountNo)) ;						 					 		
						}
						document.forms[0].accountNo.value = '<%=voucherInfo.getAccountNo()%>';						
						document.forms[0].contraAccountNo.value = '<%=voucherInfo.getPartyDetails()%>';	
						document.forms[0].trusttype.value = '<%=voucherInfo.getTrustType()%>';		
						document.forms[0].accHead.value = '<%=voucherDetails.getAccountHead()%>';	
										
					}
				}
			}
			
			function getTrusttype(type){
				for(i=0;i<trustArray.length;i++){
					if(trustArray[i][0] == document.forms[0].accountNo.value){
						if(type == 'B'){
							document.forms[0].trusttype.value = trustArray[i][1];
							document.forms[0].accHead.value = trustArray[i][2];
						} else if(type == 'C') {
							document.forms[0].accHead.value = trustArray[i][2];
						}
					}
				}
			}
		</script>
	</head>
	<body  onload="yearSelect();">
		<form name="account" action="<%=basePathBuf%>Voucher?method=updateContraRecord" onsubmit="javascript : return checkVoucher()" method="post">
		<input type="hidden" size="10" name="accHead" maxlength="50" value='<%=voucherDetails.getAccountHead()%>'/>
		
			<input type=hidden name=keyNo  value="<%=voucherInfo.getKeyNo() %>" />
			<input type="hidden" name='vouchertype' value='C' />
			<%=ScreenUtilities.screenHeader("Contra Voucher [Edit]")%><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				
				
				<tr>
					<td align="center" class="tableTextBold">
						<font color="red" size="2"><%=(request.getParameter("error")==null || "null".equals(request.getParameter("error"))?"":"Error : "+request.getParameter("error"))%> <br /> &nbsp; </font>
					</td>
				</tr>
				<tr>
					<td>
						<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" >
							
							
							<tr>
								<td height="15%">
									<table align="center" cellspacing="5" border="0">
										<tr>
											<td class="tableTextBold" colspan=2 align="right">
												FROM 
											</td>
											<td width="10px">
											</td>
											<td class="tableTextBold" colspan=2 align="right">
												TO 
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Bank Name :
											</td>
											<td align="left">
												<select name="accountNo" class="TextField" style='width:180px' onchange="getTrusttype('B')" ><option value="">Select One</option> </select>
											</td>
											<td width="10px">
											</td>
											<td class="tableTextBold" align="right" >												
												Bank Name : 
											</td>
											<td align="left">
												<select name="contraAccountNo" class="TextField" style='width:180px'  onchange="getTrusttype('C')" ><option value="">Select One</option> </select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right" >
												Trust Type :
											</td>
											<td align="left">
												<select name='trusttype' style='width:80px'>
													<option value="">
														Select One
													</option>
													<option value='IAAI ECPF' <%="IAAI ECPF".equals(voucherInfo.getTrustType())?"Selected":""%>>
														IAAI ECPF
													</option>
													<option value='NAA ECPF' <%="NAA ECPF".equals(voucherInfo.getTrustType())?"Selected":""%>>
														NAA ECPF
													</option>
													<option value='AAI EPF' <%="AAI EPF".equals(voucherInfo.getTrustType())?"Selected":""%>>
														AAI EPF
													</option>
												</select>
											</td>
											<td width="10px">
											</td>										
											<td class="tableTextBold" align="right" >
												Financial Year : 
											</td>
											<td align="left">
												<select name='year' style='width:80px'>
													<option value="">
														Select One
													</option>
												</select>
											</td>											
										</tr>
										<tr>
											<td class="tableTextBold" align="right" >
												Cheque No. : 
											</td>
											<td align="left">
												<input type="text" name="chequeNo" size="18" value='<%=voucherDetails.getChequeNo()%>'/>
											</td>
											<td width="10px">
											</td>
											<td class="tableTextBold" align="right" >												
												Amount : 
											</td>
											<td align="left">
												<input type="text" name="amount"  size="18" value='<%=df.format(voucherDetails.getCredit())%>'/>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right" >
												Month/Year : 
											</td>
											<td align="left">
												<input type="hidden" name="monthYear" />
												<select name=dmonth style='width:15mm'><option value=''>Select </option></select>
												<select name=dyear style='width:15mm'><option value=''>Select </option></select>

											</td>
											<td width="10px">
											</td>
											<td class="tableTextBold" align="right" >
												Details : 
											</td>
											<td align="left">
												<input type="text" name="details" size="18" value='<%=voucherDetails.getDetails().trim()%>'/>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right" >
												Preparation Date : 
											</td>
											<td align="left">
												<input type="text" name="prepDate"  size="13" value='<%=voucherInfo.getPreparedDt()%>'/>
												&nbsp; <a href="javascript:show_calendar('forms[0].prepDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif"  /></a>
											</td>
											<td width="10px">
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Narration : 
											</td>
											<td colspan="4" align="left">
												<textarea cols="85" rows="3" name="voucherDetails" ><%=voucherInfo.getDetails()%></textarea>
											</td>
										</tr>
										<tr>
											<td height="10px">
											</td>
										</tr>
										<tr>
											<td align="center" colspan="5">
												<input type="submit" class="butt" value="Submit" tabindex="4" />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" tabindex="5"/>
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="6"/>
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
			</table><%=ScreenUtilities.searchFooter()%>
		</form>
	</body>
</html>
