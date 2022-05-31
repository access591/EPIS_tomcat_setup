
<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - CashBook - Party Info</title>
		<meta http-equiv="pragma" content="no-cache" content="" />
		<meta http-equiv="cache-control" content="no-cache" content="" />
		<meta http-equiv="expires" content="0" content="" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" content="" />
		<meta http-equiv="description" content="This is my page" content="" />

		<link rel="stylesheet" href="css/aai.css" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/EMail and Password.js" type=""></script>
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/overlib.js"></script>
		<script type="text/javascript" type="text/javascript" type="">
		var len =0;
			function checkParty(){
				if(document.forms[0].partyName.value == ""){
					alert("Please Enter Party Name (Mandatory)");
					document.forms[0].partyName.focus();
					return false;
				}
				document.forms[0].bankInfo.options.length =0;
				var temp = '';
				len = detailsRec.length;
                if(len>0){ 
				for(var i=0;i<len;i++){
					temp = '';	
					for(var j=0;j<13;j++){
						temp += detailsRec[i][j]+'|';
					}					
					document.forms[0].bankInfo.options[document.forms[0].bankInfo.options.length]=new Option('x',temp);
					document.forms[0].bankInfo.options[document.forms[0].bankInfo.options.length-1].selected=true;
				}
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
			
			function details(accountCode,particular){	
		   		document.forms[0].accountCode.value=accountCode;
		   		document.forms[0].accountNo.focus();
			}

			function saveDetails(){
				if(document.forms[0].bankName.value == ""){
					alert("Please Enter Bank Name (Mandatory)");
					document.forms[0].bankName.focus();
					return false;
				}
				if(document.forms[0].branchName.value == ""){
					alert("Please Enter Branch Name (Mandatory)");
					document.forms[0].branchName.focus();
					return false;
				}
				if(document.forms[0].bankCode.value == ""){
					alert("Please Enter Bank Code (Mandatory)");
					document.forms[0].bankCode.focus();
					return false;
				}
				if(document.forms[0].address.value == ""){
					alert("Please Enter Address (Mandatory)");
					document.forms[0].address.focus();
					return false;
				}
				if(document.forms[0].phoneNo.value == ""){
					alert("Please Enter Phone No. (Mandatory)");
					document.forms[0].phoneNo.focus();
					return false;
				}
				if(!ValidatePh(document.forms[0].phoneNo.value)){
					alert("Please Enter Valid Phone No. ");
					document.forms[0].phoneNo.focus();
					return false;
				}
				if(document.forms[0].bFaxNo.value == ""){
					alert("Please Enter Bank Fax No. (Mandatory)");
					document.forms[0].bFaxNo.focus();
					return false;
				}
				if(!ValidatePh(document.forms[0].bFaxNo.value)){
					alert("Please Enter Valid Bank Fax No. ");
					document.forms[0].bFaxNo.focus();
					return false;
				}
				if(document.forms[0].accountNo.value == ""){
					alert("Please Enter Account No. (Mandatory)");
					document.forms[0].accountNo.focus();
					return false;
				}
				if(document.forms[0].ifscCode.value == ""){
					alert("Please Enter IFSC Code (Mandatory)");
					document.forms[0].ifscCode.focus();
					return false;
				}
				if(document.forms[0].neftCode.value == ""){
					alert("Please Enter NEFT/RTGS Code (Mandatory)");
					document.forms[0].neftCode.focus();
					return false;
				}
				if(document.forms[0].micrNo.value == ""){
					alert("Please Enter MICR Code (Mandatory)");
					document.forms[0].micrNo.focus();
					return false;
				}
				if(document.forms[0].contactPerson.value == ""){
					alert("Please Enter Contact Person Code (Mandatory)");
					document.forms[0].contactPerson.focus();
					return false;
				}
				if(document.forms[0].bMobileNo.value == ""){
					alert("Please Enter Bank Mobile No Code (Mandatory)");
					document.forms[0].bMobileNo.focus();
					return false;
				}
				if(!ValidatePh(document.forms[0].bMobileNo.value)){
					alert("Please Enter Bank Valid Mobile No. ");
					document.forms[0].bMobileNo.focus();
					return false;
				}
				if(editedNo == -1)
					editedNo = detailsRec.length;	
					detailsRec[editedNo]=[document.forms[0].bankCode.value,document.forms[0].accountNo.value,document.forms[0].ifscCode.value,document.forms[0].neftCode.value,document.forms[0].micrNo.value,document.forms[0].bankName.value,document.forms[0].branchName.value,document.forms[0].address.value,document.forms[0].phoneNo.value,document.forms[0].bFaxNo.value,document.forms[0].accountType.value,document.forms[0].contactPerson.value,document.forms[0].bMobileNo.value];
				editedNo = -1 ;
				showValues(-1);
				detailsClear();
			}
			var detailsRec = new Array();
			var editedNo = -1;
			function detailsClear(){
				document.forms[0].bankName.value = "";
				document.forms[0].branchName.value = "";
				document.forms[0].bankCode.value = "";
				document.forms[0].address.value = "";
				document.forms[0].phoneNo.value = "";
				document.forms[0].bFaxNo.value = "";
				document.forms[0].accountNo.value = "";
				document.forms[0].ifscCode.value = "";
				document.forms[0].neftCode.value = "";
				document.forms[0].micrNo.value = "";
				document.forms[0].contactPerson.value = "";
				document.forms[0].bMobileNo.value = "";
				document.forms[0].accountType.value = "S";
			}
			function showValues(index){
				var str='<TABLE border=1 cellpadding=0 cellspacing=0  bordercolor=snow width="100%">';
					str +='<TR><TD align=center   nowrap style=width:15%>Bank Code</TD>';
					str +='<TD align=center   nowrap style=width:15%>Bank A/c No </TD>';
					str +='<TD align=center   nowrap style=width:15%>IFSC Code  </TD>';
					str +='<TD align=center   nowrap style=width:15%>NEFT/RTGS Code </TD>';
					str +='<TD align=center   nowrap style=width:15%>MICR No  </TD></TR>';
				for(var i=0;i<detailsRec.length;i++){  
					str+='<TR>';
					if(i==index)	{ 
						document.forms[0].bankCode.value = detailsRec[i][0];
						document.forms[0].accountNo.value = detailsRec[i][1];
						document.forms[0].ifscCode.value = detailsRec[i][2];
						document.forms[0].neftCode.value = detailsRec[i][3];
						document.forms[0].micrNo.value = detailsRec[i][4];
						document.forms[0].bankName.value = detailsRec[i][5];
						document.forms[0].branchName.value = detailsRec[i][6];						
						document.forms[0].address.value = detailsRec[i][7];
						document.forms[0].phoneNo.value = detailsRec[i][8];
						document.forms[0].bFaxNo.value = detailsRec[i][9];						
						document.forms[0].accountType.value = detailsRec[i][10];						
						document.forms[0].contactPerson.value = detailsRec[i][11];
						document.forms[0].bMobileNo.value = detailsRec[i][12];
						
						editedNo = index;
					} else { 
						str +='<TR>';
						for(var j=0;j<5;j++){	
							if(detailsRec[i][j].length<10){
								str+='<TD align=center   nowrap style=width:15%>'+detailsRec[i][j]+'</TD>';
							}else{
								str+="<TD align=center nowrap style=width:15%>"+detailsRec[i][j].substring(0,10);
								str+="<a class=data href=# onMouseOver = \"overlib('"+detailsRec[i][j]+"\')\" onMouseOut='nd()'>...</a></TD>";				
							}						
						}
						str +='<TD><a href=# onclick=showValues('+i+')><img src="/images/action/editGridIcon.jpg" alt="edit" border=0></a><a href=# onclick=del('+i+')><img src="/images/cancelIcon.gif" alt=delete border=0 width=20 height=20></a></TD></TR>';								
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
					for(var cnt=2003;cnt<=year ;cnt++){					
						document.forms[0].dyear1.options.add(new Option(cnt,cnt)) ;				
					}	
					document.forms[0].dmonth1.value=detailsRec[index][2].substring(0,3);
					document.forms[0].dyear1.value=detailsRec[index][2].substring(4,8);
				}
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
			function load(){
				if(document.forms[0].ScreenType.value=="EDIT"){
					for(var i=0;i< document.forms[0].bankInfo.options.length;i++){
						detailsRec[i] = document.forms[0].bankInfo.options[i].value.split("|");
					}
					showValues(-1);
				}
			}
		</script>
	</head>
	<body onload="document.forms[0].partyName.focus();load()">
		<html:form action="/party.do?method=add" onsubmit="javascript: return checkParty()" method="post">
			<html:hidden property="partyCode"   />
			<html:select property="bankInfo" multiple="multiple" style="display:none;">
			<logic:equal value="EDIT"  name="ScreenType" scope="request">
				<html:options name="Bean" property="code" labelProperty="code" labelName="code" collection="bankInfo" />
			</logic:equal>						
			</html:select>
			<input type=hidden name="ScreenType" value="<bean:write name="ScreenType" scope="request"/>"/>
			<%=ScreenUtilities.screenHeader("party.title")%>
			<table width="90%" border="0" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<td align="center" class="label" colspan=5>
						<html:errors bundle="error"/>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="party.name" bundle="common" />
					</td>
					<td align="left">
					<logic:equal value="EDIT"  name="ScreenType" scope="request">
						<html:text property="partyName" maxlength="50" size="40" readonly="true"/>
					</logic:equal>		
					<logic:equal value="NEW"  name="ScreenType" scope="request">
						<html:text property="partyName" maxlength="50" size="40"/>
					</logic:equal>		
					
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<bean:message key="party.title" bundle="common" /> :
					</td>
					<td align="left">
						<html:textarea property="partyDetail"  cols="26" rows="" onblur="if(this.value.length>150){alert('Address length should be less than 150 characters');this.focus();}"></html:textarea>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="party.mobileNo" bundle="common" /> :
					</td>
					<td align="left">
						<html:text property="mobileNo" maxlength="10"  />
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<bean:message key="party.faxNo" bundle="common" /> :
					</td>
					<td align="left">
						<html:text property="faxNo" maxlength="15" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="party.contactNo" bundle="common" /> :
					</td>
					<td align="left">
						<html:text property="contactNo" maxlength="15"/>
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<bean:message key="party.email" bundle="common" /> :
					</td>
					<td align="left">
						<html:text property="emailId" maxlength="25"  />
					</td>
				</tr>
				<tr>
					<td height="5%" colspan="5" align="left" class="ScreenLeftHeading" bgcolor="#DBE4EC">
						<bean:message key="party.bankDetail" bundle="common" /> &nbsp;&nbsp;<font color="red"> </font>
					</td>

				</tr>
				<tr>
					<td colspan="5" id='addDetails' name='addDetails' width="100%"></td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.bankName" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="bankName" maxlength="25"  />
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.branchName" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="branchName" maxlength="25"  />
					</td>
				</tr>
				<tr></tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.bankCode" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="bankCode" maxlength="25"  />
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.Address" bundle="common" /> :
					</td>
					<td align="left">
						<textarea name="address" cols="26"  rows="" style='width=130px' onblur="if(this.value.length&gt;150){alert('Address length should be less than 150 characters');this.focus();}"></textarea>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.phoneNo" bundle="common" />  :
					</td>
					<td align="left">
						<input type="text" name="phoneNo" maxlength="15" />
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.faxNo" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="bFaxNo" maxlength="15"  />
					</td>
				</tr>
				<tr>

					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.bankAcno" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="accountNo" maxlength="20"  />
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<bean:message key="bankInfo.accountType" bundle="common" /> :
					</td>
					<td align="left">
						<select name="accountType"  style="Width:80px">
							<option value="S">
								Savings
							</option>
							<option value='C'>
								Current
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.IFSCCode" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="ifscCode" maxlength="20"  />
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font> <bean:message key="bankInfo.NEFTRTGSCode" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="neftCode" maxlength="20"  />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.MICRNo" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="micrNo" maxlength="20"  />
					</td>
					<td width="10px">

					</td>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.ContactPerson" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="contactPerson" maxlength="50"  />
					</td>

				</tr>
				<tr>
					<td class="tableTextBold" align="right" align="right">
						<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankInfo.MobileNo" bundle="common" /> :
					</td>
					<td align="left">
						<input type="text" name="bMobileNo" maxlength="10"  />
					</td>
					<td class="tableTextBold" align="right" align="right" colspan="2">
						<img style='cursor:hand' src="images/saveIcon.gif" onclick='saveDetails()'  />
						<img style='cursor:hand' src="images/cancelIcon.gif" onclick='detailsClear()'  />
					</td>
				</tr>
				<tr>
					<td align="center" colspan="5">
						<input type="submit" class="butt" value="Submit" tabindex="20" />
						<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()"  />
						<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)"  />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
