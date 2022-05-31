

<%@ page  import="java.util.*,com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherInfo" %>
<%@ page import="com.epis.bean.cashbookDummy.VoucherDetails" %>
<%@ page language="java" buffer="16kb"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();
	VoucherInfo voucherInfo = (VoucherInfo) request
					.getAttribute("einfo");
	  List vDetails = voucherInfo.getVoucherDetails();
	  DecimalFormat df=new DecimalFormat("##########.####");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - CashBook - Voucher</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="Voucher Page" />
	    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlib.js"></script>
		<script type="text/javascript">
		
			function showParty(){			
				if(document.forms[0].party.value=="E"){ 
	    
		   		     divNomineeHead.style.display="block";

		   		     divNominee2.style.display="none";
		   		     divNominee3.style.display="none";
		   		}
		   		if(document.forms[0].party.value=="I"){   	
	  	    
					divNominee3.style.display="none";
					divNomineeHead.style.display="none";
					divNominee2.style.display="block"; 

	   		    }
	   		    if(document.forms[0].party.value=="C"){   	
	  	    
					divNominee2.style.display="block";
					divNomineeHead.style.display="none"; 
					divNominee3.style.display="none";

	   		    }
	   		    if(document.forms[0].party.value==""){
 
	   		    	divNominee2.style.display="none";
					divNomineeHead.style.display="none"; 

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
		
	
			function empDetails(pfid,name,desig,cpfacno,region,unitName){
			   		document.forms[0].eName.value=name;
					document.forms[0].epfid.value=pfid;
					//document.forms[0].edesignation.value=desig;
		
			}
			
			function partyDetails(pName,pCode,isin) {   	 
				document.forms[0].pName.value=pName;
				document.forms[0].pCode.value=pCode;
				document.forms[0].isin.value=isin;
			}
			
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
	
				if(document.forms[0].vouchertype.value != "C"){
					if(document.forms[0].party.value == ""){
						alert("Please Select Party (Mandatory)");
						document.forms[0].party.focus();
						return false;
					}
					if(document.forms[0].party.value == "E"){
						if(document.forms[0].eName.value == ""){
							alert("Please Enter Employee Name (Mandatory)");
							document.forms[0].eName.focus();
							return false;
						}
					}else if(document.forms[0].party.value == "I"){
					if(document.forms[0].isin.value == ""){
							alert("Please Enter ISIN (Mandatory)");
							document.forms[0].isin.focus();
							return false;
						}
						}else if(document.forms[0].party.value == "C"){
						if(document.forms[0].pName.value == ""){
							alert("Please Enter Party Name (Mandatory)");
							document.forms[0].pName.focus();
							return false;
						}
					}						
				}
				if(document.forms[0].prepDate.value == ""){
					alert("Please Enter Preparation Date (Mandatory)");
					document.forms[0].prepDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].prepDate)){
					return false;
				}
				
				convert_date(document.forms[0].prepDate);
				
				if(document.forms[0].finyearcheck.value !=document.forms[0].year.value)
				{
					alert("The FinYear is Not Matching");
					document.forms[0].year.focus();
					return false;
				}	
				if(document.forms[0].edit.value == 'Y'){
					alert("Please Click on the Save (Tick Icon) to save the edited details");
					return false;
				}
				if((document.forms[0].debit.value !='' && document.forms[0].debit.value !='0') || (document.forms[0].credit.value !='' && document.forms[0].credit.value !='0')){
					if(confirm("Would you Like to Save the Voucher Debit & Credit Details")){
						saveDetails();
						return false;
					}
				}
				var len = detailsRec.length;
				if(len==0){
					alert("Please Enter Account Head Details and Save (Mandatory)");
					return false;
				}
				var temp = '';	
				var debit = 0;
				var credit = 0;
				document.forms[0].detailRecords.options.length =0;
				for(var i=0;i<len;i++){
					if(detailsRec[i][2]=='')
						detailsRec[i][2] = ' ';
					if(detailsRec[i][3]=='')
						detailsRec[i][3] = ' ';
					if(detailsRec[i][4]=='')
						detailsRec[i][4] = ' ';
					temp = detailsRec[i][0]+'|'+detailsRec[i][2]+'|'+detailsRec[i][3]+'|'+detailsRec[i][4]+'|'+detailsRec[i][5]+'|'+detailsRec[i][6];
					debit += parseFloat(detailsRec[i][5]);
					credit += parseFloat(detailsRec[i][6]);
					document.forms[0].detailRecords.options[document.forms[0].detailRecords.options.length]=new Option('x',temp);
					document.forms[0].detailRecords.options[document.forms[0].detailRecords.options.length-1].selected=true;

				}
				if(document.forms[0].vouchertype.value=='P'){
					var amount = debit-credit;
					//alert(amount);
					if(amount < 0){
						alert("For Payment Voucher Debit Amount Should be Greater than Credit Amount");
						return false;
					}
				}else if(document.forms[0].vouchertype.value=='R'){
					var amount = credit - debit;
					if(amount < 0){
						alert("For Receipt Voucher Credit Amount Should be Greater than Debit Amount");
						return false;
					}
				}				
				
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
				document.forms[0].year.value='<%=voucherInfo.getFinYear()%>';
				//document.forms[0].year.options[document.forms[0].year.options.length-1].selected=true;
				document.forms[0].accountNo.focus();
				var month_values = new Array ("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
				for(cnt=0;cnt<12 ;cnt++){					
					document.forms[0].dmonth.options.add(new Option(month_values[cnt],month_values[cnt])) ;	
				/*	if(cnt+1==month){
						document.forms[0].dmonth.options[document.forms[0].dmonth.options.length-1].selected=true;
					}*/
				}					
				for(cnt=2009;cnt<=year ;cnt++){					
					document.forms[0].dyear.options.add(new Option(cnt,cnt)) ;				
				}
				//document.forms[0].dyear.options[document.forms[0].dyear.options.length-1].selected=true;				
			}	
			
			function otherBankDetails(bankName,accountNo,accHead,particular){
				document.forms[0].contraBankName.value=bankName;
				document.forms[0].contraAccountNo.value=accountNo;
				document.forms[0].accHead.value=accHead;
		   		document.forms[0].particular.value=particular;	
		   		
			}
			function bankDetails(bankName,accountNo,trustType){
				document.forms[0].bankName.value=bankName;
				document.forms[0].accountNo.value=accountNo;	
				document.forms[0].trusttype.value=(trustType=="null"?"":trustType);	
				
				
			}
		function details(accHead,particular){	
				accCodes[accCodes.length]=[accHead,particular];
				if(document.forms[0].accType.value=='Y'){
		   			document.forms[0].accHead1.value=accHead;
		   			document.forms[0].particular1.value=particular;
		   		}else{
		   			document.forms[0].accHead.options.add(new Option(accHead,accHead)) ;
		   			document.forms[0].accHead.value=accHead;
		   			document.forms[0].particular.value=particular;
		   		}
			}
			function partyDetails(partyName,detail,isin){
		   		//alert(partyName+isin);
		   		document.forms[0].pName.value=partyName;
		   		document.forms[0].isin.value=isin;
		   		
			}
			function validate_monyear(monYear){
				var monYear = document.forms[0].monthYear.value;
				var mon =  monYear.substr(0,monYear.indexOf("/"));
				var year = parseFloat(monYear.substr(monYear.indexOf("/")+1,monYear.length));
				if(mon.length<3){
					alert("Please Enter Month/Year in the format of 'Mon/YYYY'");
					document.forms[0].monthYear.focus();
					return false;
				}
				mon = mon.toUpperCase(); 
				var bool = false;
				var month_values = new Array ("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
				for ( var i=0; i<12; i++ ) {
   					if ( mon == month_values[i]){
   						bool = true;
   						break;
   					}
   				}   				
   				if(!bool){
					alert("Please Enter Valid Month");
					document.forms[0].monthYear.focus();
					return false;
				}
				if(year < 1900){
					alert("Please Enter Valid Year");
					document.forms[0].monthYear.focus();
					return false;
				}
				return true;
			}
			function detailsClear(){
				document.forms[0].accHead.value='';
				document.forms[0].particular.value='';
				document.forms[0].dmonth.value='';
				document.forms[0].dyear.value='';
				document.forms[0].debit.value='';
				document.forms[0].credit.value='';
				document.forms[0].details.value='';	
				document.forms[0].cheque.value='';			
				document.forms[0].credit.readOnly=false;
				document.forms[0].debit.readOnly=false;
			}
			var detailsRec = new Array();
			function saveDetails(){
				if(document.forms[0].accHead.value == ""){
					alert("Please Select Account Head (Mandatory)");
					document.forms[0].accHead.focus();
					return false;
				}
				if(document.forms[0].vouchertype.value !='C' && document.forms[0].party.value == ""){
					alert("Please Select Party (Mandatory)");
					document.forms[0].party.focus();
					return false;
				}
				if(document.forms[0].vouchertype.value !='C' && document.forms[0].party.value == "E"){
					if(document.forms[0].dmonth.value == ""){
						alert("Please Select Month (Mandatory)");
						document.forms[0].dmonth.focus();
						return false;
					}
					if(document.forms[0].dyear.value == ""){
						alert("Please Select Year (Mandatory)");
						document.forms[0].dyear.focus();
						return false;
					}
				}
				if(document.forms[0].debit.value == "" ){
					document.forms[0].debit.value = "0";
				}
				if(!ValidateFloatPoint(document.forms[0].debit.value,15,4)){
					alert("Please Enter A Valid Debit Amount");
					document.forms[0].debit.focus();
					return false;
				}
				if(document.forms[0].credit.value == "" ){
					document.forms[0].credit.value = "0";
				}
				if(!ValidateFloatPoint(document.forms[0].credit.value,15,4)){
					alert("Please Enter A Valid Credit Amount");
					document.forms[0].credit.focus();
					return false;
				}
				if(parseFloat(document.forms[0].credit.value) == 0 && parseFloat(document.forms[0].debit.value) == 0){
					alert("Please Enter Credit or Debit Amount");
					document.forms[0].debit.focus();
					return false;
				}
				
				detailsRec[detailsRec.length]=[document.forms[0].accHead.value,document.forms[0].particular.value,(document.forms[0].dmonth.value==document.forms[0].dyear.value?"":document.forms[0].dmonth.value+"/"+document.forms[0].dyear.value),document.forms[0].details.value,document.forms[0].cheque.value,document.forms[0].debit.value,document.forms[0].credit.value];
				showValues(-1);
				document.forms[0].edit.value='N';
				detailsClear();

			}
			
			function showValues(index){  
				var str='<TABLE border=1 cellpadding=0 cellspacing=0  bordercolor=snow width="100%">';
				for(var i=0;i<detailsRec.length;i++){  
					var code="";
					str+='<TR>';
					if(i==index)	{ 						
						str+='<TD align=center >';
						str+='<select name="accHead1"  style="width:58px"  onchange="getPerticular1(this.value);" ';
						str+='<option value="">	Select One</option>';
						for(var cnt=0;cnt<accCodes.length;cnt++){
							str+='<option value='+accCodes[cnt][0]+' '+(accCodes[cnt][0]==detailsRec[i][0]?"selected":"")+'>'+accCodes[cnt][0]+'</option>';
						}
						str+='</select>&nbsp;<img style="cursor:hand" src="<%=basePath%>images/add.gif" onclick="popupWindow(\'<%=basePath%>cashbook/AccountingCodePopup.jsp\',\'AAI\');" alt="Click The Icon to Select Bank Master Records" /></TD>';
						str+='<TD align=center >';
						str+='<input type=text name=particular1 value=\''+detailsRec[i][1]+'\' style="width:100px" maxlength=25 readonly="readonly" ></TD>';
						str+='<TD align=center>';
						str+='<select name=dmonth1 style="width:13mm"><option value="">Select </option></select><select name=dyear1 style="width:13mm"><option value="">Select </option></select></TD>';
						str+='<TD align=center >';
						str+='<input type=text name=details1 value=\''+detailsRec[i][3]+'\' style="width:25mm" maxlength=20></TD>';
						str+='<TD align=center >';
						str+='<input type=text name=cheque1 value=\''+detailsRec[i][4]+'\' style="width:20mm" maxlength=20></TD>';
						str+='<TD align=center >';
						str+='<input type=text name=debit1 value=\''+detailsRec[i][5]+'\' style="width:16mm" maxlength=15 ></TD>';
						str+='<TD align=center >';
						str+='<input type=text name=credit1 value=\''+detailsRec[i][6]+'\' style="width:16mm" maxlength=15 ></TD>';
						str+='<TD align=center nowrap><a href=# onclick="editDetails('+i+');">';
						str+='&nbsp;<img src="<%=basePath%>/images/saveIcon.gif" border=0 alt=save></a>';
						str+='<a href=# onclick=del('+i+')>';
						str+='<img src="<%=basePath%>/images/cancelIcon.gif" alt=delete border=0 width=15 height=15></a></TD>';
						document.all['addDetails'].innerHTML = str;	
						code=detailsRec[i][0];
						document.forms[0].edit.value='Y';						
					} else { 
						str +='<TR>';
						for(var j=0;j<7;j++){	
							if(detailsRec[i][j].length<10){
								str+='<TD align=center   nowrap style=width:15%>'+detailsRec[i][j]+'</TD>';
							}else{
								str+="<TD align=center nowrap style=width:15%>"+detailsRec[i][j].substring(0,10);
								str+="<a class=data href=# onMouseOver = \"overlib('"+detailsRec[i][j]+"\')\" onMouseOut='nd()'>...</a></TD>";				
							}						
						}
						str +='<TD><a href=# onclick=showValues('+i+')><img src="<%=basePath%>/images/icon-edit.gif" alt="edit" border=0></a><a href=# onclick=del('+i+')><img src="<%=basePath%>/images/cancelIcon.gif" alt=delete border=0 width=15 height=15></a></TD></TR>';								
					}	
				}
			 	str+='</TABLE>';      
				document.all['addDetails'].innerHTML = str;	
				if(index!=-1){
					var month_values = new Array ("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
					var date = new Date();
					var year = parseInt(date.getYear());
					for(var cnt=0;cnt<12 ;cnt++){					
						document.forms[0].dmonth1.options.add(new Option(month_values[cnt],month_values[cnt])) ;					
					}					
					for(var cnt=2009;cnt<=year ;cnt++){					
						document.forms[0].dyear1.options.add(new Option(cnt,cnt)) ;				
					}	
					document.forms[0].dmonth1.value=detailsRec[index][2].substring(0,3);
					document.forms[0].dyear1.value=detailsRec[index][2].substring(4,8);

				}
			}
			function editDetails(index) {
			 	if(document.forms[0].accHead1.value == ""){
					alert("Please Select Account Head (Mandatory)");
					document.forms[0].accHead.focus();
					return false;
				}
				if(document.forms[0].vouchertype.value !='C' && document.forms[0].party.value == ""){
					alert("Please Select Party (Mandatory)");
					document.forms[0].party.focus();
					return false;
				}
				if(document.forms[0].vouchertype.value !='C' && document.forms[0].party.value == "E"){
					if(document.forms[0].dmonth1.value == ""){
						alert("Please Select Month (Mandatory)");
						document.forms[0].dmonth.focus();
						return false;
					}
					if(document.forms[0].dyear1.value == ""){
						alert("Please Select Year (Mandatory)");
						document.forms[0].dyear.focus();
						return false;
					}
				}
				if(document.forms[0].debit1.value == "" ){
					document.forms[0].debit1.value = "0";
				}
				if(!ValidateFloatPoint(document.forms[0].debit1.value,15,4)){
					alert("Please Enter A Valid Debit Amount");
					document.forms[0].debit1.focus();
					return false;
				}
				if(document.forms[0].credit1.value == "" ){
					document.forms[0].credit1.value = "0";
				}
				if(!ValidateFloatPoint(document.forms[0].credit1.value,15,4)){
					alert("Please Enter A Valid Credit Amount");
					document.forms[0].credit1.focus();
					return false;
				}
				if(parseFloat(document.forms[0].credit1.value) == 0 && parseFloat(document.forms[0].debit1.value) == 0){
					alert("Please Enter Credit or Debit Amount");
					document.forms[0].debit1.focus();
					return false;
				}
				detailsRec[index]=[document.forms[0].accHead1.value,document.forms[0].particular1.value,(document.forms[0].dmonth1.value==document.forms[0].dyear1.value?"":document.forms[0].dmonth1.value+"/"+document.forms[0].dyear1.value),document.forms[0].details1.value,document.forms[0].cheque1.value,document.forms[0].debit1.value,document.forms[0].credit1.value];
				showValues(-1);
				document.forms[0].edit.value='N';
			}
			function del(index) {
				var temp=new Array();
				for(var i=0;i<detailsRec.length;i++)
				{
					if(i!=index)
						temp[temp.length]=detailsRec[i];
		
				}
				detailsRec=temp;
				showValues(-1);
				document.forms[0].edit.value='N';
				return false;
			}
			function popUpAcc(){
				var accHeads = '';
				var len = detailsRec.length;
				for(var i=0;i<len;i++){		
					accHeads += "|"+detailsRec[i][0]+"|";
				}
			//	if(len==0){
					popupWindow('<%=basePath%>cashbook/AccountInfo.jsp','AAI');
			/*	}else{
					popupWindow('<%=basePath%>cashbook/AccountInfo.jsp?type=rem&&AccHead='+accHeads,'AAI');
				}*/
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
			var banktype="";
			
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
			
					  		var str="";
					  		str+="<table  class=border border=1 width=100% ><tr><td>Account No.</td><td>Bank Name</td></tr>"; 
					 		for(i=0;i<stype.length;i++){
					 			if(banktype=='B'){
						 			var bankName= getNodeValue(stype[i],'bankName');						 		
							 		var accountNo= getNodeValue(stype[i],'accountNo');
							 		var trustType= getNodeValue(stype[i],'trustType');
					 	 	 	 	str+="<tr><td><a href='#' onclick=\"bankDetails('"+bankName+"','"+accountNo+"','"+trustType+"');\" >"+accountNo+"</a></td><td><a href='#' onclick=\"bankDetails('"+bankName+"','"+accountNo+"','"+trustType+"');\" >"+bankName+"</a></td></tr>";					 				
					 	 	 	 }else if(document.forms[0].accountNo.value!=getNodeValue(stype[i],'accountNo')){
							 		var bankName= getNodeValue(stype[i],'bankName');						 		
							 		var accountNo= getNodeValue(stype[i],'accountNo');
							 		var accHead= getNodeValue(stype[i],'accHead');
							 		var particular= getNodeValue(stype[i],'particular');
					 	 	 	 	str+="<tr><td><a href='#' onclick=\"otherBankDetails('"+bankName+"','"+accountNo+"','"+accHead+"','"+particular+"');\" >"+accountNo+"</a></td><td><a href='#' onclick=\"otherBankDetails('"+bankName+"','"+accountNo+"','"+accHead+"','"+particular+"');\" >"+bankName+"</a></td></tr>";					 					
					 	 	 	 }
				 	 		}
							str+="</table>";
				 	    }
					}
				}
			}
			var typeCodes ='';
				var accCodes = new Array();
			function getAccountCodes(particular,type){  
				typeCodes=type; 
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
					  	}
					  	else{					  		
							if(typeCodes == 'select'||typeCodes == 'select1'){
							accCodes.length=0;
								for(i=0;i<stype.length;i++){
							 		var code= getNodeValue(stype[i],'CODE').replace(/\$/g,"&");
							 		var name= getNodeValue(stype[i],'NAME').replace(/\$/g,"&");							 		
							 		
							 		accCodes[accCodes.length]=[code,name];
							 		if(typeCodes == 'select')
							 		{
							 		document.forms[0].accHead.options.add(new Option(code,code)) ;
							 			}
							 		else
							 		{
							 		document.forms[0].accHead1.options.add(new Option(code,code)) ;
							 			}
							 		
							 	}
							}
					  	else{
					  		
					  		var str="";
					  		str+="<table  class=border border=1 width=100% ><tr><td>Code</td><td>Name</td><td>Type</td></tr>"; 
					 		for(i=0;i<stype.length;i++){
						 		var code= getNodeValue(stype[i],'CODE').replace(/\$/g,"&");
						 		var name= getNodeValue(stype[i],'NAME').replace(/\$/g,"&");
						 		var type= getNodeValue(stype[i],'TYPE').replace(/\$/g,"&");
				 	 	 	 	str+="<tr><td><a href='#' onclick=\"details2('"+code+"','"+name+"','"+typeCodes+"');\" >"+code+"</a></td><td><a href='#' onclick=\"details2('"+code+"','"+name+"','"+typeCodes+"');\" >"+name+"</a></td><td><a href='#' onclick=\"details2('"+code+"','"+name+"','"+typeCodes+"');\" >"+(type=='null'?"":type)+"</a></td></tr>";
				 	 		}
							str+="</table>";		
							
						}
						getBankDts();
					}
				}
				}
			}
			function details2(accHead,particular,type){	
				if(type=='MT'){
		   			document.forms[0].accHead.value=accHead;
		   			document.forms[0].particular.value=particular;
		   			document.forms[0].dmonth.focus();
		   			
		   		}else{
		   			document.forms[0].accHead1.value=accHead;
		   			document.forms[0].particular1.value=particular;
		   			document.forms[0].dmonth1.focus();
		   			
		   		}
			}
			function loadValues(){  
<%
Iterator it = vDetails.iterator();
VoucherDetails info= new VoucherDetails();
while (it.hasNext()) {
	info= (VoucherDetails)it.next();
%>
				detailsRec[detailsRec.length]=['<%=info.getAccountHead()%>','<%=info.getParticular()%>','<%=info.getMonthYear()%>','<%=info.getDetails()%>','<%=info.getChequeNo()%>','<%=df.format( info.getDebit())%>','<%=df.format( info.getCredit())%>'];
<%
}
%>
				showValues(-1);
				
				
		}
		
		function checkAmount(type){
			if(type == 'D'){
				if(document.forms[0].debit.value !='' && document.forms[0].debit.value != '0' ){
					document.forms[0].credit.value=0;
					document.forms[0].credit.readOnly=true;
					document.forms[0].debit.readOnly=false;
				}else{
					document.forms[0].credit.readOnly=false;
					document.forms[0].debit.readOnly=true;
				}				
			} else if(type == 'C'){
				if(document.forms[0].credit.value !='' && document.forms[0].credit.value != '0' ){
					document.forms[0].debit.value=0;
					document.forms[0].debit.readOnly=true;
					document.forms[0].credit.readOnly=false;
				}else{
					document.forms[0].credit.readOnly=true;
					document.forms[0].debit.readOnly=false;
				}				
			} else if(type == 'D1'){
				if(document.forms[0].debit1.value !='' && document.forms[0].debit1.value != '0' ){
					document.forms[0].credit1.value=0;
					document.forms[0].credit1.readOnly=true;
					document.forms[0].debit1.readOnly=false;
				}else{
					document.forms[0].credit1.readOnly=false;
					document.forms[0].debit1.readOnly=true;
				}				
			} else if(type == 'C1'){
				if(document.forms[0].credit1.value !='' && document.forms[0].credit1.value != '0' ){
					document.forms[0].debit1.value=0;
					document.forms[0].debit1.readOnly=true;
					document.forms[0].credit1.readOnly=false;
				}else{
					document.forms[0].credit1.readOnly=true;
					document.forms[0].debit1.readOnly=false;
				}				
			} 
		}
		function getPerticular(val){
			     for(var k=0;k<accCodes.length;k++){
			    if(accCodes[k][0] == val )	{	
			      document.forms[0].particular.value=accCodes[k][1];
			      }

			  }
			}
			 function getPerticular1(val){
			     for(var k=0;k<accCodes.length;k++){
			    if(accCodes[k][0] == val )	{	
			      document.forms[0].particular1.value=accCodes[k][1];
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
					 		trustArray[trustArray.length]=[accountNo,trustType];
					 		document.forms[0].accountNo.options.add(new Option(bankName+" -- "+accountNo,accountNo)) ;						 					 		
						}
						document.forms[0].accountNo.value = '<%=voucherInfo.getAccountNo()%>';		
	
						document.forms[0].trusttype.value = '<%=voucherInfo.getTrustType()%>';	
						checkingFinancialYear();
					}
				}
			}
			
			function getTrusttype(){
				for(i=0;i<trustArray.length;i++){
					if(trustArray[i][0] == document.forms[0].accountNo.value){
						document.forms[0].trusttype.value = trustArray[i][1];
					}
				}
			}
			function checkingFinancialYear()
			{
				if(document.forms[0].prepDate.value!="" && document.forms[0].year.value!="")
				{
					if(!convert_date(document.forms[0].prepDate)){
						document.forms[0].prepDate.select();
						return(false);
					   }
					   
					   var url;
			
			
				url="<%=basePathBuf%>jsp/investment/quotation/ArrangersLoad.jsp?mode=jvfinyear&preperationdate="+document.forms[0].prepDate.value;
			
			
				//sendURL(url,"checkFinyear");
				
				xmlHttp.open("post", url, true);
				xmlHttp.onreadystatechange = checkFinyear;
				xmlHttp.send(null);
					   
				}
			}
			
			 
	
	function checkFinyear()
	{
	
	
			if(xmlHttp.readyState ==4){	
						if(xmlHttp.status == 200){ 
							var stype = xmlHttp.responseXML.getElementsByTagName('child');
							if(stype.length!=0){
									
							
								for(i=0;i<stype.length;i++){
									document.forms[0].finyearcheck.value=getNodeValue(stype[i],'FinYear');;
									}
								}	
		
								}								
							}
							
						}
					
			
	
	 	</script>
	</head>
	<body  onload="loadValues();yearSelect();showParty(); getAccountCodes('','select') ">
		<form name="vocher" action="<%=basePathBuf%>Voucher?method=updateVoucher" onsubmit="javascript : return checkVoucher()" method="post" action="">
			<input type=hidden name=edit value="N" />
			<input type=hidden name=accType value="N" />
			<input type="hidden" name='vouchertype' value='<%="Payment".equals(voucherInfo.getVoucherType())?"P":"Receipt".equals(voucherInfo.getVoucherType())?"R":"C" %>' />
			<input type=hidden name=keyNo  value="<%=voucherInfo.getKeyNo() %>" />
			<div id="overDiv" style="position:absolute; visibility:hide;z-index:1;"></div>
			<%=ScreenUtilities.screenHeader(voucherInfo.getVoucherType()+"Voucher[Edit] ")%>
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">	
				<tr>
					<td>
						<table  border="0" align="center" cellpadding="2" cellspacing="2" >
							<tr>
								<td class="tableTextBold" align="right" >
									Bank Name & Account No :
								</td>
								<td align="left">
								 	<select name="accountNo" class="TextField" style='width:200px' >
								 	<option value="">Select One</option> </select>
								</td>
								<td class="tableTextBold" align="right">
									Trust Type : 
								</td>
								<td align="left">
									<select class="TextField" name='trusttype' style='width:80px'>
										<option value="">
											Select One
										</option>
										<option value='IAAI ECPF' <%="IAAI ECPF".equals(voucherInfo.getTrustType())?"Selected":"" %>>
											IAAI ECPF
										</option>
										<option value='NAA ECPF' <%="NAA ECPF".equals(voucherInfo.getTrustType())?"Selected":"" %> >
											NAA ECPF
										</option>
										<option value='AAI EPF' <%="AAI EPF".equals(voucherInfo.getTrustType())?"Selected":"" %>>
											AAI EPF
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tableTextBold" align="right">
									Financial Year :
								</td>
								<td align="left">
									<select class="TextField" name='year' onchange="checkingFinancialYear();" style='width:80px'>
										<option value="">
											Select One
										</option>
									</select>
								</td>
								<td class="tableTextBold" align="right">
									Transaction Type : 
								</td>
								<td align="left">
									<select class="TextField" name='transType'  style='width:90px'>
										<option value="C" <%="C".equals(voucherInfo.getTransactionType())?"selected":""%>>
											Cheque
										</option>
										<option value='D' <%="D".equals(voucherInfo.getTransactionType())?"selected":""%>>
											DD
										</option>
										<option value='I' <%="I".equals(voucherInfo.getTransactionType())?"selected":""%>>
											Internal transfer
										</option>
										<option value='N' <%="N".equals(voucherInfo.getTransactionType())?"selected":""%>>
											NEFT
										</option>
										<option value='R' <%="R".equals(voucherInfo.getTransactionType())?"selected":""%>>
											RTGS
										</option>
									</select>
								</td>			
							</tr>		
							<tr>
								<td class="tableTextBold" align="right">
									Type of Party :
								</td>
								
								<td align="left">
									<select class="TextField" name='party' onchange="showParty();" style='width:80px'>
										<option value="">
											Select One
										</option>
										<option value='E' <%="E".equals(voucherInfo.getPartyType())?"Selected":"" %>>
											Employee
										</option>
										<option value='C' <%="C".equals(voucherInfo.getPartyType())?"Selected":"" %>>
											Cash Party
										</option>
																			
										<option value='I' <%="I".equals(voucherInfo.getPartyType())?"Selected":"" %>>
											Investment
										</option>
									</select>
								</td>										
								<td class="tableTextBold"  align="right">
									Preparation Date :
								</td>
								<td align="left">
									<input name='prepDate' value='<%=voucherInfo.getPreparedDt()%>' onblur="checkingFinancialYear();" maxlength="11" style="width: 80px" class="TextField" />
									&nbsp; <a href="javascript:show_calendar('forms[0].prepDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" /></a>

								</td>
							</tr>
							<tr>
								<td style="height: 5">
								
								</td>
							</tr>
							<tr id="divNomineeHead" style="display:none">
								<td class="tableTextBold" align="right">
									PF ID : 
								</td>
								<td align="left">
									<input class="TextField" type="text" size="13" value='<%="E".equals(voucherInfo.getPartyType())?voucherInfo.getPfid():""%>' name="epfid" maxlength="50"  width="150px" readonly="readonly" />

												<img border=0 src="<%=basePath%>images/search1.gif" onclick="popupWindow('<%=basePath%>jsp/cashbook/voucher/EmployeeInfo.jsp','AAI');" alt="Click The Icon to Select EmployeeName" src="" alt="" />
								</td>
								<td class="tableTextBold" align="right">
									Employee Name :
								</td>
								<td align="left">
									<input class="TextField" type="text" size="18" value='<%="E".equals(voucherInfo.getPartyType())?voucherInfo.getPartyDetails():""%>' name="eName" readonly="readonly" maxlength="50"  width="150px" />
								</td>
							</tr>
							<tr id="divNominee2" style="display:none">
								<td class="tableTextBold" align="right">
									Party Name : 
								</td>
								<td align="left">
									<input  class="TextField" type="text" size="13" name="pName" maxlength="50" readonly="readonly" value='<%=!"E".equals(voucherInfo.getPartyType())?voucherInfo.getEmpPartyCode():""%>' />
									<img src="<%=basePath%>images/search.gif" onclick="popupWindow('<%=basePath%>jsp/cashbook/party/PartyInfo.jsp','AAI');" alt="Click The Icon to Select EmployeeName" src="" alt="" />
								</td>
							</tr>
							<tr id="divNominee3" style="display:none">
								<td class="tableTextBold" align="right">
									ISIN : 
								</td>
								<td align="left">
									<input  class="TextField" type="text" size="13" name="isin" maxlength="50" readonly="readonly" value='<%=!"E".equals(voucherInfo.getPartyType())?voucherInfo.getISIN():""%>' />
									<img src="<%=basePath%>images/search.gif" onclick="popupWindow('<%=basePath%>jsp/cashbook/party/PartyInfo.jsp','AAI');" alt="Click The Icon to Select EmployeeName" src="" alt="" />
								</td>
							</tr>	
						</table>
					</td>
				</tr>
				<tr>
					<td style="height: 10px">
					<input type="hidden" name="finyearcheck"/>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label" align="center" >
									Account head
								</td>
								<td class="label" align="center" >
									Particular
								</td>
								<td class="label" align="center" >
									Month/Year									
								</td>
								<td class="label" align="center" >
									Details
								</td>
								<td class="label" align="center" >
									Cheque No.
								</td>
								<td class="label" align="center" >
									Debit (Rs.)
								</td>
								<td class="label" align="center" >
									Credit (Rs.)
								</td>
								<td class="label" align="center">
								</td>
							</tr>							
							<tr>
								<td nowrap="nowrap" align="center">
									<select class="TextField" name='accHead' style='width:58px' onchange="getPerticular(this.value);">
										<option value="">
											Select One
										</option>
									</select>
									<img style='cursor:hand' src="<%=basePath%>images/add.gif" onclick="popupWindow('<%=basePath%>cashbook/AccountingCodePopup.jsp','AAI');" alt="Click The Icon to Select Bank Master Records"  />
								</td>
								<td align="center">
									<input class="TextField" type="text" style='width:100px' name="particular" maxlength="50"   readonly="readonly"/>
								</td>
								<td class="tableTextBold" align="center" nowrap>
									
									<select class="TextField" name=dmonth style='width:13mm'><option value=''>Select </option></select>
									<select class="TextField" name=dyear style='width:13mm'><option value=''>Select </option></select>												
								</td>
								<td align="center">
									<input class="TextField" type="text" style='width:25mm' name="details" maxlength="50"  />
								</td>
								<td align="center">
									<input class="TextField" type="text" style='width:20mm' name="cheque" maxlength="20"  />
								</td>
								<td align="center">
									<input class="TextField" type="text" style='width:16mm' name="debit" maxlength="15"  />
								</td>
								<td align="center">
									<input class="TextField" type="text" style='width:16mm' name="credit" maxlength="15"  />
								</td>
								<td nowrap="nowrap">
									<a href=# onclick='saveDetails()'><img border=0 style='cursor:hand' src="<%=basePath%>images/saveIcon.gif"   /></A>
									<a href=# onclick='detailsClear()'><img border=0 style='cursor:hand' src="<%=basePath%>images/cancelIcon.gif"   /></A>
								</td>
							</tr>
							<tr>
								<td style="height: 10px">
								</td>
							</tr>
							<tr>
								<td colspan="8" id='addDetails' name='addDetails' width="100%"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<select name='detailRecords' multiple="multiple" style="DISPLAY:NONE"></select>
														</td>
				</tr>
				<tr>
					<td align="center">
						<table width="100%" border="0">
							<tr>
								<td class="tableTextBold" align="right">
									Narration : 
								</td>
								<td align="left" colspan="4">
									<textarea class="TextField" cols="90" rows="3" style="width: 400px" name="voucherDetails"><%=voucherInfo.getDetails()%></textarea>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table align="center">
							<tr>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td align="center">
									<input type="submit" class="butt" value="Submit" />
									<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" />
									<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table><%=ScreenUtilities.searchFooter()%>
		</form>
	</body>
</html>
