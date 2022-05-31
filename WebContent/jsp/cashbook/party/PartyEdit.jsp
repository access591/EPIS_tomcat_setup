<%@ page import="com.epis.bean.cashbook.BankMasterInfo,java.util.List,com.epis.utilities.ScreenUtilities" %>
<%@ page import="com.epis.bean.cashbook.PartyInfo" %>
<%@ page language="java" buffer="16kb"%>


<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

String basePath = basePathBuf.toString() ;
PartyInfo partyInfo = (PartyInfo) request
					.getAttribute("einfo");
List info = partyInfo.getBankInfo();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>AAI</title>
		<meta http-equiv="pragma" content="no-cache"  />
		<meta http-equiv="cache-control" content="no-cache"  />
		<meta http-equiv="expires" content="0"  />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"  />
		<meta http-equiv="description" content="This is my page"  />
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/EMail and Password.js" ></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" type="text/javascript" >
			function checkParty(){
	/*			if(document.forms[0].mobileNo.value == ""){
					alert("Please Enter Mobile No. (Mandatory)");
					document.forms[0].mobileNo.focus();
					return false;
				}
				if(!ValidatePh(document.forms[0].mobileNo.value)){
					alert("Please Enter Valid Mobile No. ");
					document.forms[0].mobileNo.focus();
					return false;
				}
   		  		if(document.forms[0].faxNo.value == ""){
					alert("Please Enter Fax No. (Mandatory)");
					document.forms[0].faxNo.focus();
					return false;
				}
				if(!ValidatePh(document.forms[0].faxNo.value)){
					alert("Please Enter Valid Fax No. ");
					document.forms[0].faxNo.focus();
					return false;
				}
				if(document.forms[0].email.value == ""){
					alert("Please Enter Email ID (Mandatory)");
					document.forms[0].email.focus();
					return false;
				}
				if(!ValidateEMail(document.forms[0].email.value)){
					document.forms[0].email.focus();
					return false;
				}*/
				var len = detailsRec.length;
				if(len==0){
					alert("Please Enter Bank Details and click on Save (Tick Icon)  (Mandatory)");
					return false;
				}
				document.forms[0].detailRecords.options.length =0;
				var temp = '';	
				for(var i=0;i<len;i++){
					temp = '';	
					for(var j=0;j<13;j++){
						temp += detailsRec[i][j]+'|';
					}
					document.forms[0].detailRecords.options[document.forms[0].detailRecords.options.length]=new Option('x',temp);
					document.forms[0].detailRecords.options[document.forms[0].detailRecords.options.length-1].selected=true;
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
			/*	if(document.forms[0].accountCode.value == ""){
					alert("Please Enter Account Code (Mandatory)");
					document.forms[0].accountCode.focus();
					return false;
				}*/
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
					editedNo = detailsRec.length;	detailsRec[editedNo]=[document.forms[0].bankCode.value,document.forms[0].accountNo.value,document.forms[0].ifscCode.value,document.forms[0].neftCode.value,document.forms[0].micrNo.value,document.forms[0].bankName.value,document.forms[0].branchName.value,document.forms[0].address.value,document.forms[0].phoneNo.value,document.forms[0].bFaxNo.value,document.forms[0].accountType.value,document.forms[0].contactPerson.value,document.forms[0].bMobileNo.value];
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
<%	
	for(int i=0;i<info.size();i++){						
		BankMasterInfo bInfo = (BankMasterInfo)info.get(i);
%>
				detailsRec[<%=i%>]=['<%=bInfo.getBankCode()%>','<%=bInfo.getAccountNo()%>','<%=bInfo.getIFSCCode()%>','<%=bInfo.getNEFTRTGSCode()%>','<%=bInfo.getMICRNo()%>','<%=bInfo.getBankName()%>','<%=bInfo.getBranchName()%>','<%=bInfo.getAddress()%>','<%=bInfo.getPhoneNo()%>','<%=bInfo.getFaxNo()%>','<%=bInfo.getAccountType()%>','<%=bInfo.getContactPerson()%>','<%=bInfo.getMobileNo()%>'];
<%
	}
%>
			showValues(-1);
			}
		</script>
	</head>
	<body  onload="load();document.forms[0].partyDetail.focus();">
		<form action="<%=basePathBuf%>Party?method=updatePartyRecord" onsubmit="javascript: return checkParty()" method="post">
		<div id="overDiv" style="position:absolute; visibility:hide;z-index:1;"></div>
		<select name='detailRecords' multiple="multiple" style="display:none"></select>
			<%=ScreenUtilities.screenHeader("Party Master [Edit]")%><table width="90%" border="0" align="center" cellpadding="1" cellspacing="1">
				
				
				<tr>
					<td align="center" class="label">
						<font color="red" size="2"><%=(request.getParameter("error")==null || "null".equals(request.getParameter("error"))?"":"Error : "+request.getParameter("error"))%> <br /> &nbsp; </font>
					</td>
				</tr>
				
										<tr>
											<td class="tableTextBold" align="right">
												Party Name :
											</td>
											<td align="left">
												<input type="hidden" name="partyCode"  value="<%=partyInfo.getPartyCode()%>" />
												<input type="hidden" name="partyName"  value="<%=partyInfo.getPartyName().replaceAll("%","~")%>" />
												<%=partyInfo.getPartyName()%>
											</td>
											<td width="10px">
	
											</td>
											<td class="tableTextBold" align="right">
												Party Address :
											</td>
											<td align="left">
												<textarea class="TextField" name="partyDetail" cols="26" tabindex="1" rows="" cols="" rows=""><%=partyInfo.getPartyDetail().trim()%>
												</textarea>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Mobile No :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="mobileNo" maxlength="25" tabindex="2" value="<%=partyInfo.getMobileNo()%>" />
											</td>
											<td width="10px">
	
											</td>
											<td class="tableTextBold" align="right">
												Fax No :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="faxNo" maxlength="25" tabindex="3" value="<%=partyInfo.getFaxNo()%>" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Contact No :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="contactNo" maxlength="25" tabindex="3" value="<%=partyInfo.getContactNo()%>"/>
											</td>
											<td width="10px">
	
											</td>
											<td class="tableTextBold" align="right">
												Email :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="email" maxlength="25" tabindex="4" value="<%=partyInfo.getEmailId()%>" />
											</td>
										</tr>
										<tr>
											<td height="5%" colspan="5"  align="left" class="ScreenLeftHeading" bgcolor="#DBE4EC">			
												Party Bank Detail &nbsp;&nbsp;<font color="red"> </font>
											</td>
			
										</tr>
										<tr>
											<td colspan="5" id='addDetails' name='addDetails' width="100%"></td>

										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Bank Name :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="bankName" maxlength="25" tabindex="6" />
											</td>
											<td width="10px">

											</td>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Branch Name :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="branchName" maxlength="25" tabindex="7" />
											</td>
										</tr>
										<tr></tr>
										<tr>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Bank Code :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="bankCode" maxlength="25" tabindex="8" />
											</td>
											<td width="10px">

											</td>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Address :
											</td>
											<td align="left">
												<textarea class="TextField" name="address" cols="26" tabindex="9" rows="" style='width=130px' onblur="if(this.value.length>150){alert('Address length should be less than 150 characters');this.focus();}"></textarea>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Phone No :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="phoneNo" maxlength="15" tabindex="10" />
											</td>
											<td width="10px">

											</td>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Fax No :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="bFaxNo" maxlength="15" tabindex="11" />
											</td>
										</tr>
										<tr>
											
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Bank A/c No :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="accountNo" maxlength="20" tabindex="13" />
											</td>
											<td width="10px">

											</td>
											<td class="tableTextBold" align="right">
												Type of Account :
											</td>
											<td align="left">
												<select class="TextField" name="accountType" tabindex="14" style="Width:80px">
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
												<font color="red">&nbsp;*&nbsp;</font>IFSC Code :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="ifscCode" maxlength="20" tabindex="15" />
											</td>
											<td width="10px">

											</td>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font> NEFT/RTGS Code :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="neftCode" maxlength="20" tabindex="16" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>MICR No :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="micrNo" maxlength="20" tabindex="17" />
											</td>
											<td width="10px">

											</td>
											<td class="tableTextBold" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Contact Person Name :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="contactPerson" maxlength="50" tabindex="18" />
											</td>

										</tr>
										<tr>
											<td class="tableTextBold" align="right" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Mobile No :
											</td>
											<td align="left">
												<input class="TextField" type="text" name="bMobileNo" maxlength="10" tabindex="19" />
											</td>
											<td class="label" align="right" align="right" colspan=2>
												<img style='cursor:hand' src="<%=basePath%>images/saveIcon.gif" onclick='saveDetails()'  />
												<img style='cursor:hand' src="<%=basePath%>images/cancelIcon.gif" onclick='detailsClear()'  />
											</td>
										</tr>
										<tr>
											<td align="center" colspan="5">
												<input type="submit" class="butt" value="Submit" tabindex="20" />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" tabindex="21" />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" tabindex="22" />
											</td>
										</tr>
									</table><%=ScreenUtilities.searchFooter()%>
								
		</form>
	</body>
</html>
