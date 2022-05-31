<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>
<jsp:useBean id="sh" class="com.epis.utilities.SQLHelper" scope="request"/>
<jsp:useBean id="cf" class="com.epis.utilities.CurrencyFormat" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/aai.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/GeneralFunctions.js"></script>
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript">
		winOpened = false;
			function openDocument(formCd,path)
			{
			
				
				
				if(path!="")
				{
					var file="/uploads/documents/"+formCd+path;
					
				
				wd = screen.availWidth - 10;
				ht = screen.availHeight - 35;
				winHandle = window.open(file,"docWin","left=0,top=0,width=" + wd + ",height=" + ht + ",toolbar=no,status=no,scrollbars=yes,");
				winOpened=true;
				}
			}
		
  		</script>
	</head>
	<body >
		
				<table width="80%" border=0 align="center" cellpadding="4">
				<tr>
				<td align="center" >
					<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan="5" >
								<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<th class="tblabel" align="center" valign="middle" colspan=7>
								<bean:message key="com.aai" bundle="common" />
							</th>
							</tr>
						</table>
						</td></tr>
						<tr>
					<td align="center" >
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="formfilling.report.tilte" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>						
						</td></tr>
				<tr>
					<td colspan=4 align=center>
						<html:errors />
					</td>
				</tr>
				<tr>					
					<td  align=right nowrap>
						<b><bean:message key="jv.date" bundle="common" /></b>
						:					
						<bean:write property="proposaldate" name="proposal" />
					</td>
				</tr>

				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="formfilling.to" bundle="common" /></b>
					</td>					
				</tr>
				<tr>
					<td align=left nowrap>
					<bean:write  name="proposal" property="mailingAddress"/>
					
				</tr>
				<tr>
					<td colspan=1>&nbsp;</td>
				</tr>
				<tr>
					<td align=left >I/We the undersigned here by wish to submit a bid under the non-competetive bidding facility of Reserve Bank of India for auction of Government Securities and furnish the necessary  information as below</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				
				<tr>
					<td align=center>
						<table border=1 class='border' width="100%">
							<tr>
								<td  align="left" class="label" width="10%">
									<b>1</b>
								</td>
								<td align="left" class="label" width="30%">
								Name</td>
								<td align="left" class="label" width="60%">
								<bean:message key="com.aai" bundle="common" />
								<bean:message key="com.employeeprovidentfund" bundle="common" />
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>2</b>
								</td>
								<td align="left" class="label" width="30%">
								Address</td>
								<td align="left" class="label" width="60%">
								<bean:message key="quotation.jasperaddress" bundle="common" />
								
								</td>
								
							</tr>
							<tr>
								<td  align="left" class="label" width="10%">
									<b>3</b>
								</td>
								<td align="left" class="label" width="30%">
								Status</td>
								<td align="left"  width="60%">
								<bean:write property="trustType" name="proposal"/> Trust
															
							</td>
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>4</b>
								</td>
								<td align="left" class="label" width="30%">
								<bean:message key="formfilling.dateofauction" bundle="common"/></td>
								<td align="left" class="label" width="60%">
								<bean:write name="proposal" property="auctionDate"/>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>5</b>
								</td>
								<td align="left" class="label" width="30%">
								TelePhone Number</td>
								<td align="left" class="label" width="60%">
								<bean:write name="proposal" property="contactNumber"/>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>6</b>
								</td>
								<td align="left" class="label" width="30%">
								Fax Number</td>
								<td align="left" class="label" width="60%">
								<bean:write name="proposal" property="faxNumber"/>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>7</b>
								</td>
								<td align="left" class="label" width="30%">
								<bean:message key="formfilling.securityname" bundle="common"/>
								</td>
								<td align="left" class="label" width="60%">
								<bean:write name="proposal" property="securityName"/>
								
								
								</td>
								
							</tr>
							
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>8</b>
								</td>
								<td align="left" class="label" width="30%">
								Bid Amount(in figures)
								</td>
								<td align="left" class="label" width="60%">
								<bean:define id="bidamount" name="proposal" property="investAmt" type="java.lang.String" />
								<%=cf.getCurrency(Double.parseDouble(bidamount))%>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>9</b>
								</td>
								<td align="left" class="label" width="30%">
								Bid Amount(in words)
								</td>
								<td align="left" class="label" width="60%">
								
								<%=sh.ConvertInWords(Double.parseDouble(bidamount))%>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>10</b>
								</td>
								<td align="left" class="label" width="30%">
								Mode Of Delivery
								</td>
								<td align="left" class="label" width="60%">
								
								RTGS
								
								
								</td>
								
							</tr>
							
							
							<tr>
								<td  align="left" class="label" width="10%">
									&nbsp;
								</td>
								<td align="left" class="label" width="30%">
								Dvp through SGL Account
								</td>
								<td align="left" class="label" width="60%">
								
								SGL Accoount No
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									&nbsp;
								</td>
								<td align="left" class="label" width="30%">
								Dvp through Consumer SGL Account
								</td>
								<td align="left" class="label" width="60%">
								
								CSGL Account With Syndicate Bank<br>CSGL Account No. of the Bank With RBI SG020092
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									&nbsp;
								</td>
								<td align="left" class="label" width="30%">
								Physical(Please specify the lot of size)<br> PDO Regno
								</td>
								<td align="left" class="label" width="60%">
								
								Not Applicable
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>11</b>
								</td>
								<td align="left" class="label" width="30%">
								Deatils of Bank & PANno
								</td>
								<td align="left" class="label" width="60%">
								
								<bean:write name="proposal" property="bankName"/>&nbsp;&nbsp;<bean:write name="proposal" property="accountType"/>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									&nbsp;
								</td>
								<td align="left" class="label" width="30%">
								Name of the Bank
								</td>
								<td align="left" class="label" width="60%">
								
								<bean:write name="proposal" property="bankName"/>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									&nbsp;
								</td>
								<td align="left" class="label" width="30%">
								Branch(Address & Telno)
								</td>
								<td align="left" class="label" width="60%">
								
								<bean:write name="proposal" property="bankAddress"/>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									&nbsp;
								</td>
								<td align="left" class="label" width="30%">
								Bidders Bank Account no
								</td>
								<td align="left" class="label" width="60%">
								
								<bean:write name="proposal" property="accountNo"/>
								
								
								</td>
								
							</tr>
							
							<tr>
								<td  align="left" class="label" width="10%">
									&nbsp;
								</td>
								<td align="left" class="label" width="30%">
								Bidders Permenant Account Number
								</td>
								<td align="left" class="label" width="60%">
								
								<bean:write name="proposal" property="panno"/>
								
								
								</td>
								
							</tr>
							
							
							<tr>
								<td  align="left" class="label" width="10%">
									<b>12</b>
								</td>
								<td align="left" class="label" width="30%">
								Commission Chargeable
								</td>
								<td align="left" class="label" width="60%">
								
								<b>Nil</b>
								
								
								</td>
								
							</tr>
							
							
							
								
						</table>
						
						
						<%com.epis.utilities.SQLHelper sql = new com.epis.utilities.SQLHelper();%>
						
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subplease" bundle="common" /> : </b>
					</td>					
				</tr>
<logic:iterate id="approval" name="proposal" property="approvals"> 
	<logic:equal value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.FormFillingAppBean" />
				<tr>
					<td align="right">
						<table border=0>
							<tr>
								<td align="right" class="label" >	
									<img  src="<bean:write name="invBean" property="signPath"/>" />
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									(<bean:write name="invBean" property="displayName"/>)
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									<bean:write name="invBean" property="designation"/>
								</td>										
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<hr style="height:1px; color: black" />
					</td>
				</tr>
			
	</logic:equal>
	<logic:notEqual value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.FormFillingAppBean" />
			<logic:equal value="A" name="invBean" property="approved">
				<tr>					
					<td  align=right nowrap>
						<b><bean:message key="jv.date" bundle="common" /></b>
						:					
						<bean:write property="date" name="invBean" />
					</td>
				</tr>
				<tr>
					<td  align="left" wrap>
						<bean:define id="rem1" name="invBean" property='remarks' type="String"></bean:define>
						<pre><%=sql.wrapTextArea(rem1,90)%></pre>
					</td>					
				</tr>
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subplease" bundle="common" /> : </b>
					</td>					
				</tr>
				<tr>
					<td align="right">
						<table border=0>
							<tr>
								<td align="right" class="label" >	
									<img  src="<bean:write name="invBean" property="signPath"/>" />
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									(<bean:write name="invBean" property="displayName"/>)
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									<bean:write name="invBean" property="designation"/>
								</td>										
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<hr style="height:1px; color: black" />
					</td>
				</tr>
			</logic:equal>	
		</logic:notEqual>		
</logic:iterate>
				<tr>
				<td>&nbsp;</td>
				</tr>
				<tr>
					<td align=center class="label" ><a href="javascript:void(0)" onclick="openDocument('<bean:write property="formCd" name="proposal"/>','<bean:write property="extName" name="proposal"/>');">Uploaded Document View</a></td>
				</tr>
												
			</table>

		
	</body>
</html>

