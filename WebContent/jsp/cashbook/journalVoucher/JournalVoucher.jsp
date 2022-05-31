<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Account Code Type Search</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Accounting Code Type" />

		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script language="javascript">		
    		function getParty(){
    			if(document.forms[0].partyType.value == 'E'){
    				document.getElementById("EmpHeading").style.display="block";
    				document.getElementById("EmpValue").style.display="block";
    				document.getElementById("OthHeading").style.display="none";
    				document.getElementById("OthValue").style.display="none";
    				document.getElementById("PartyHeading").style.display="none";
    				document.getElementById("PartyValue").style.display="none";
    			}
    			if(document.forms[0].partyType.value == 'O'){
    				document.getElementById("OthHeading").style.display="block";
    				document.getElementById("OthValue").style.display="block";
    				document.getElementById("PartyHeading").style.display="none";
    				document.getElementById("PartyValue").style.display="none";
    				document.getElementById("EmpHeading").style.display="none";
    				document.getElementById("EmpValue").style.display="none";
    			}
    			if(document.forms[0].partyType.value == 'P'){
    				document.getElementById("PartyHeading").style.display="block";
    				document.getElementById("PartyValue").style.display="block";
    				document.getElementById("OthHeading").style.display="none";
    				document.getElementById("OthValue").style.display="none";
    				document.getElementById("EmpHeading").style.display="none";
    				document.getElementById("EmpValue").style.display="none";
    			}
    		}
			
			var byArray = new Array();
			var toArray = new Array();
			function saveByDetails(){
				if(document.forms[0].byAccHead.value == ""){
					alert("Please Select Account Head (Mandatory)");
					document.forms[0].byAccHead.focus();
					return false;
				}
				if(document.forms[0].byAmount.value == "" ){
					alert("Please Enter Amount (Mandatory)");
					document.forms[0].byAmount.focus();
					return false;
				}
				if(!ValidateFloatPoint(document.forms[0].byAmount.value)){					
					document.forms[0].byAmount.focus();
					return false;
				}
				if(editBy==-1){
					byArray[byArray.length]=[document.forms[0].byAccHead.value,document.forms[0].byDetails.value,document.forms[0].byAmount.value];
				}else {
					byArray[editBy]=[document.forms[0].byAccHead.value,document.forms[0].byDetails.value,document.forms[0].byAmount.value];
					editBy=-1;
				}
				showByValues(-1);
				clearByValues();
			}
			
			function showByValues(index){
				var tableStr='<table width="100%" border=1 bordercolor="white" cellspacing=0 cellpadding=0>';
				for(var i=0;i<byArray.length;i++){
					if(index!=i){
						tableStr += '<tr>';
						tableStr += '<td align="center" style="width: 300px">'+byArray[i][0]+'</td>';
						tableStr += '<td align="center" style="width: 160px">'+byArray[i][1]+'</td>';
						tableStr += '<td align="center" style="width: 160px">'+byArray[i][2]+'</td>';
						tableStr += '<td><a href=# onclick="editByDetails('+i+');">&nbsp;<img src="/images/saveIcon.gif" border=0 alt=save></a>';
						tableStr += '<a href=# onclick=delBy('+i+')><img src="/images/cancelIcon.gif" alt=delete border=0 width=20 height=20></a></td>';
						tableStr += '</tr>';
					}
				}
				tableStr += '</table>';		
				document.all['by'].innerHTML = tableStr;		
			}
			
			function clearByValues(){
				document.forms[0].byAccHead.value ='';
				document.forms[0].byDetails.value = '';
				document.forms[0].byAmount.value = '';
			}
			
			var editBy = -1;
			function editByDetails(index){
				document.forms[0].byAccHead.value =byArray[index][0];
				document.forms[0].byDetails.value = byArray[index][1];
				document.forms[0].byAmount.value = byArray[index][2];
				editBy=index;
				showByValues(index);
			}
			
			function delBy(index) {
				var temp=new Array();
				for(var i=0;i<byArray.length;i++)
				{
					if(i!=index)
						temp[temp.length]=byArray[i];		
				}
				byArray=temp;
				showByValues(-1);				
				return false;
			}
			
			function saveToDetails(){
				if(document.forms[0].toAccHead.value == ""){
					alert("Please Select Account Head (Mandatory)");
					document.forms[0].toAccHead.focus();
					return false;
				}
				if(document.forms[0].toAmount.value == "" ){
					alert("Please Enter Amount (Mandatory)");
					document.forms[0].toAmount.focus();
					return false;
				}
				if(!ValidateFloatPoint(document.forms[0].toAmount.value)){					
					document.forms[0].toAmount.focus();
					return false;
				}
				if(editTo==-1){
					toArray[toArray.length]=[document.forms[0].toAccHead.value,document.forms[0].toDetails.value,document.forms[0].toAmount.value];
				}else {
					toArray[editTo]=[document.forms[0].toAccHead.value,document.forms[0].toDetails.value,document.forms[0].toAmount.value];
					editTo=-1;
				}				
				showToValues(-1);
				clearToValues();
			}
			
			function showToValues(index){
				var tableStr='<table width="100%" border=1 bordercolor="white" cellspacing=0 cellpadding=0>';
				for(var i=0;i<toArray.length;i++){
					if(index!=i){
						tableStr += '<tr>';
						tableStr += '<td align="center" style="width: 300px">'+toArray[i][0]+'</td>';
						tableStr += '<td align="center" style="width: 160px">'+toArray[i][1]+'</td>';
						tableStr += '<td align="center" style="width: 160px">'+toArray[i][2]+'</td>';
						tableStr += '<td><a href=# onclick="editToDetails('+i+');">&nbsp;<img src="/images/saveIcon.gif" border=0 alt=save></a>';
						tableStr += '<a href=# onclick=delTo('+i+')><img src="/images/cancelIcon.gif" alt=delete border=0 width=20 height=20></a></td>';
						tableStr += '</tr>';
					}
				}
				tableStr += '</table>';		
				document.all['to'].innerHTML = tableStr;		
			}
			
			function clearToValues(){
				document.forms[0].toAccHead.value ='';
				document.forms[0].toDetails.value = '';
				document.forms[0].toAmount.value = '';
			}
			
			var editTo = -1;
			function editToDetails(index){
				document.forms[0].toAccHead.value =toArray[index][0];
				document.forms[0].toDetails.value = toArray[index][1];
				document.forms[0].toAmount.value = toArray[index][2];
				editTo=index;
				showToValues(index);
			}
			
			function delTo(index) {
				var temp=new Array();
				for(var i=0;i<toArray.length;i++)
				{
					if(i!=index)
						temp[temp.length]=toArray[i];		
				}
				toArray=temp;
				showToValues(-1);				
				return false;
			}
			
			function popupWindow(windowname){	
				if (! window.focus)return true;
				var href;
				if(windowname == 'AdvanceFormInfo'){
					href="loadAdvance.do?method=loadLookupPFID&frm_pensionno="+document.forms[0].pfid.value;
				}else if(windowname == 'PartyInfo'){
					href="/jsp/cashbook/party/PartyInfo.jsp";
				}
				progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
				return true;	
		    }
		    function test(pfids,empcode,empname,designation,fname,department,dojaai,dojcpf,dob,emolument,pensionNo,region,emppfstatuary,cpfaccno,airportcode){	   
		   	    document.forms[0].pfid.value=pensionNo;
	  		}	
	  		
	  		function partyDetails(pName,pCode) {   
 				document.forms[0].partyName.value=pName;
				document.forms[0].partyCode.value=pCode;
			}	
			
			function checkJournal(){
				if(document.forms[0].stationName.value == ""){
					alert("Please Select Station Name (Mandatory)");
					document.forms[0].stationName.focus();
					return false;
				}
				if(document.forms[0].preparationDate.value == ""){
					alert("Please Enter Date (Mandatory)");
					document.forms[0].preparationDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].preparationDate)){
						document.forms[0].preparationDate.select();
						return(false);
					   }
				if(document.forms[0].partyType.value == ""){
					alert("Please Select Party Type (Mandatory)");
					document.forms[0].partyType.focus();
					return false;
				}
				if(document.forms[0].partyType.value == "E"){
					if(document.forms[0].pfid.value == ""){
						alert("Please Select Employee (Mandatory)");
						document.forms[0].pfid.focus();
						return false;
					}
				}else if(document.forms[0].partyType.value == "P"){
					if(document.forms[0].partyName.value == ""){
						alert("Please Select Party (Mandatory)");
						document.forms[0].partyName.focus();
						return false;
					}
				}else if(document.forms[0].partyType.value == "O"){
					if(document.forms[0].other.value == ""){
						alert("Please Enter Other Details (Mandatory)");
						document.forms[0].other.focus();
						return false;
					}
				}
				if(document.forms[0].trustType.value == ""){
					alert("Please Select Trust Type (Mandatory)");
					document.forms[0].trustType.focus();
					return false;
				}
				if(document.forms[0].finyearcheck.value !=document.forms[0].finYear.value)
				{
					alert("The FinYear is Not Matching");
					document.forms[0].finYear.focus();
					return false;
				}
				if(byArray.length==0){
					alert("Please Enter Debit Details (Mandatory)");
					document.forms[0].byAccHead.focus();
					return false;
				}
				if(toArray.length==0){
					alert("Please Enter Credit Details (Mandatory)");
					document.forms[0].toAccHead.focus();
					return false;
				}
				var byAmount = 0;
				var toAmount = 0;
				document.forms[0].byRecords.options.length = 0;
				document.forms[0].toRecords.options.length = 0;
				for(var i=0;i<byArray.length;i++){
					if(byArray[i][1]==''){
						byArray[i][1]=' ';
					}
					document.forms[0].byRecords.options[document.forms[0].byRecords.options.length]=new Option('x',(byArray[i][0]+'|'+byArray[i][1]+'|'+byArray[i][2]));
					document.forms[0].byRecords.options[document.forms[0].byRecords.options.length-1].selected=true;
					byAmount =  parseFloat(byAmount) + parseFloat(byArray[i][2]);
				}
				for(var i=0;i<toArray.length;i++){
					if(toArray[i][1]==''){
						toArray[i][1]=' ';
					}
					document.forms[0].toRecords.options[document.forms[0].toRecords.options.length]=new Option('x',(toArray[i][0]+'|'+toArray[i][1]+'|'+toArray[i][2]));
					document.forms[0].toRecords.options[document.forms[0].toRecords.options.length-1].selected=true;
					toAmount =  parseFloat(toAmount) + parseFloat(toArray[i][2]);
				}
				if(parseFloat(byAmount) != parseFloat(toAmount) ){
					alert("The Credit Amount and Debit Amount Should be Equal");
					document.forms[0].toRecords.options.length = 0;
					document.forms[0].byRecords.options.length = 0;
					document.forms[0].toAccHead.focus();
					return false;
				}
			}
			function loading(){				
				for(var i=0;i<document.forms[0].toRecords.options.length;i++){
					var rec = document.forms[0].toRecords.options[i].value;
					var accCode = rec.substring(0,rec.indexOf("|"));
					rec = rec.substring(rec.indexOf("|")+1,rec.length);
					var desc = rec.substring(0,rec.indexOf("|"));
					rec = rec.substring(rec.indexOf("|")+1,rec.length);
					toArray[toArray.length]=[accCode,desc,rec];
				}
				showToValues(-1);
				for(var i=0;i<document.forms[0].byRecords.options.length;i++){
					var rec = document.forms[0].byRecords.options[i].value;
					var accCode = rec.substring(0,rec.indexOf("|"));
					rec = rec.substring(rec.indexOf("|")+1,rec.length);
					var desc = rec.substring(0,rec.indexOf("|"));
					rec = rec.substring(rec.indexOf("|")+1,rec.length);
					byArray[byArray.length]=[accCode,desc,rec];
				}
				showByValues(-1);
				getParty();
				if(document.forms[0].finyearcheck.value=="" ){
				document.forms[0].finyearcheck.value=document.forms[0].finYear.value;
				}
			}
			function checkingFinancialYear()
			{
				if(document.forms[0].preparationDate.value!="" && document.forms[0].finYear.value!="")
				{
					if(!convert_date(document.forms[0].preparationDate)){
						document.forms[0].preparationDate.select();
						return(false);
					   }
					   
					   var url;
			
			
				url="jsp/investment/quotation/ArrangersLoad.jsp?mode=jvfinyear&preperationdate="+document.forms[0].preparationDate.value;
			
			
				sendURL(url,"checkFinyear");
					   
				}
			}
			
			 function sendURL(url,responseFunction)
	{           
		if (window.ActiveXObject) {			// for IE  
			httpRequest = new ActiveXObject("Microsoft.XMLHTTP"); 
		} else if (window.XMLHttpRequest) { // for other browsers  
			httpRequest = new XMLHttpRequest(); 
		}

		if(httpRequest) {
			
			httpRequest.open("GET", url, true);  //2nd arg is url with name/value pairs, 3 specify asynchronus communication
			httpRequest.onreadystatechange = eval(responseFunction) ; //which will handle the callback from the server side element
			httpRequest.send(null); 
		}
	}
	
	function checkFinyear()
	{
			if (httpRequest.readyState == 4)
		{ 
			if(httpRequest.status == 200) 
				{ 			
					var node = httpRequest.responseXML.getElementsByTagName("master")[0];
					
					//alert("theletn"+httpRequest.responseXML.getElementsByTagName("QuotationData")[0].childNodes.length);
													
   					if(node) 
					{
						document.forms[0].finyearcheck.value=node.childNodes[0].getElementsByTagName("FinYear")[0].firstChild.nodeValue;
					}
					
					  
					
				}
		}
		//alert(document.forms[0].finyearcheck.value);
	}
		</script>
	</head>

	<body onload="loading()">
		<html:form action="/journalVoucherInfo?method=save" method="post" onsubmit="return checkJournal()">
			<html:select property='byRecords' multiple="multiple" style="display:none" >
			<html:options name="JournalVoucherDetails" property="append" labelProperty="append" labelName="append" collection="byArray" />
			</html:select>
			<html:select property='toRecords' multiple="multiple" style="display:none" >
			<html:options name="JournalVoucherDetails" property="append" labelProperty="append" labelName="append" collection="toArray" />
			</html:select>
			<html:hidden property="keyNo"/>
			<%=ScreenUtilities.screenHeader("jv.title")%>
			<table width="70%" border="0" align="center">
		
				<tr>
					<td colspan="4" align="center">
						&nbsp;
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap="nowrap">
						<bean:message key="jv.station" bundle="common" />
						:
					</td>
					<td width="20%" align="left">
						<html:select property="stationName" styleClass="tableText">
							<html:option value="">Select One</html:option>
							<html:options name="Bean" property="name" labelProperty="name" labelName="name" collection="stations" />
						</html:select>
					</td>
					<td width="20%" class="tableTextBold" align="right" nowrap="nowrap">
						<bean:message key="jv.date" bundle="common" />
						:
					</td>
					<td width="20%" nowrap="nowrap" align="left">
						<html:text maxlength="11" property="preparationDate" onblur="checkingFinancialYear();" styleId="preparationDate" styleClass='TextField' style="width:30 px" />
						<html:link href="javascript:showCalendar('preparationDate');">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="bankInfo.trustType" bundle="common" />
						:
					</td>
					<td align="left">
						<html:select property="trustType" styleClass="TextField" style="width:120 px ;height:50 px">
							<!-- <html:option value="" >Select One</html:option>-->
							<html:options name="TrustTypeBean"  property="trustType" labelProperty="trustType" labelName="trustType" collection="trustTypes" />
						</html:select>
					</td>
					<td class="tableTextBold" align="right"><input type="hidden" name="finyearcheck"/>
						<bean:message key="voucher.financialYear" bundle="common" />
						:
					</td>
					<td align="left">
						<html:select property="finYear" onchange="checkingFinancialYear();" styleClass="TextField"  >
							<html:options name="Bean" property="code" labelProperty="code" labelName="code" collection="finYear" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap="nowrap">
						<bean:message key="voucher.partyType" bundle="common" />
						:
					</td>
					<td width="20%" align="left">
						<html:select property="partyType" styleClass="tableText" onchange="getParty()">
							<html:option value="">Select One</html:option>
							<html:option value="E">Employee</html:option>
							<html:option value="P">Party</html:option>
							<html:option value="O">Other</html:option>
						</html:select>
					</td>
					<td width="20%" class="tableTextBold" align="right" nowrap="nowrap" id="EmpHeading" style="display: none;">
						<bean:message key="jv.employee" bundle="common" />
						:
					</td>
					<td width="20%" align="left" id="EmpValue" style="display: none;">
						<html:text property="pfid" maxlength="50" styleClass="tableText" readonly="true" />
						<img src="/images/search1.gif" onclick="popupWindow('AdvanceFormInfo');" alt="Click The Icon to Select Details" />
					</td>
					<td width="20%" class="tableTextBold" align="right" nowrap="nowrap" id="PartyHeading" style="display: none;">
						<bean:message key="jv.party" bundle="common" />
						:
					</td>
					<td width="20%" align="left" id="PartyValue" style="display: none;">
						<html:text property="partyName" maxlength="50" styleClass="tableText" readonly="true" />
						<html:hidden property="partyCode" />
						<input type="hidden" name="party" value="C"/>
						<img src="/images/search1.gif" onclick="popupWindow('PartyInfo');" alt="Click The Icon to Select Details" />
					</td>
					<td width="20%" class="tableTextBold" align="right" nowrap="nowrap" id="OthHeading" style="display: none;">
						<bean:message key="jv.other" bundle="common" />
						:
					</td>
					<td width="20%" align="left" id="OthValue" style="display: none;">
						<html:text property="other" maxlength="50" styleClass="tableText" />
					</td>
				</tr>				
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap" colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap" colspan="4">
						<table>
							<tr>
								<th class="label" align="center" nowrap="nowrap">
									<bean:message key="accCode.accCode" bundle="common" />
								</th>
								<th class="label" align="center" nowrap="nowrap">
									<bean:message key="jv.details" bundle="common" />
								</th>
								<th class="label" align="center" nowrap="nowrap">
									<bean:message key="voucher.debit" bundle="common" />
								</th>
								<th class="label" align="right" nowrap="nowrap">
									&nbsp;
								</th>
							</tr>
							<tr>
								<td id="by" colspan="4">
								</td>
							</tr>
							<tr>
								<th class="label" align="right" nowrap="nowrap">
									<html:select property="byAccHead" styleClass="tableText" style="width: 300px">
										<html:option value="">Select One</html:option>
										<html:options name="AccountingCodeInfo" property="accountHead" labelProperty="displayName" labelName="displayName" collection="accountHeads" />
									</html:select>
								</th>
								<th class="label" align="right" nowrap="nowrap">
									<html:text property="byDetails" maxlength="50" styleClass="tableText" style="width: 150px" />
								</th>
								<th class="label" align="right" nowrap="nowrap">
									<html:text property="byAmount" maxlength="15" styleClass="tableText" style="width: 150px" />
								</th>
								<th class="label" align="right" nowrap="nowrap">
									<img border="0" src="/images/saveIcon.gif" alt="save" onclick='saveByDetails()' style='cursor:hand' />
									<img border="0" style='cursor:hand' src="/images/cancelIcon.gif" alt="" />
								</th>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap" colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap" colspan="4">
						<table>
							<tr>
								<th class="label" align="center" nowrap="nowrap">
									<bean:message key="accCode.accCode" bundle="common" />
								</th>
								<th class="label" align="center" nowrap="nowrap">
									<bean:message key="jv.details" bundle="common" />
								</th>
								<th class="label" align="center" nowrap="nowrap">
									<bean:message key="voucher.credit" bundle="common" />
								</th>
								<th class="label" align="right" nowrap="nowrap">
									&nbsp;
								</th>
							</tr>
							<tr>
								<td id="to" colspan="4">
								</td>
							</tr>
							<tr>
								<th class="label" align="right" nowrap="nowrap">
									<html:select property="toAccHead" styleClass="tableText" style="width: 300px">
										<html:option value="">Select One</html:option>
										<html:options name="AccountingCodeInfo" property="accountHead" labelProperty="displayName" labelName="displayName" collection="accountHeads" />
									</html:select>
								</th>
								<th class="label" align="right" nowrap="nowrap">
									<html:text property="toDetails" maxlength="50" styleClass="tableText" style="width: 150px" />
								</th>
								<th class="label" align="right" nowrap="nowrap">
									<html:text property="toAmount" maxlength="15" styleClass="tableText" style="width: 150px" />
								</th>
								<th class="label" align="right" nowrap="nowrap">
									<img border="0" src="/images/saveIcon.gif" alt="save" onclick='saveToDetails()' style='cursor:hand' />
									<img border="0" style='cursor:hand' src="/images/cancelIcon.gif" alt="" />
								</th>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap" colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap" >
						<bean:message key="jv.narration" bundle="common" /> :
					</td>
					<td  align="left" nowrap="nowrap" colspan="3">
						<html:textarea property="details" cols="50"></html:textarea>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap" colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<html:submit styleClass="butt">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						<html:button styleClass="butt" property="Clear" onclick="javascript:document.forms[0].reset()">
							<bean:message key="button.clear" bundle="common" />
						</html:button>
					</td>
				</tr>

			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
