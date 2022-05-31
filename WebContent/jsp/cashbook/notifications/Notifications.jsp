<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Notifications</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script>
			function fwdtoPayment(){
				document.forms[0].action = "/Notifications.do?method=fwdToPayment";
				document.forms[0].submit();
			}
			function fwdtoPayment1(){
				document.forms[0].action = "/Notifications.do?method=getNotifications1";
				document.forms[0].submit();
			}
			function fwdtoPayment2(){
				document.forms[0].action = "/Notifications.do?method=getNotifications2";
				document.forms[0].submit();
			}
			function fwdtoPayment3(){
				document.forms[0].action = "/Notifications.do?method=getNotifications3";
				document.forms[0].submit();
			}
			function fwdtoPayment4(){
				document.forms[0].action = "/Notifications.do?method=getNotifications4";
				document.forms[0].submit();
			}
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			function openReport(url){
				var wind1 = window.open(url,"Voucher","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
			function OpenPendinAmt(querytype)
			{
			     var url="/Notifications.do?method=pendingAmt&queryType="+querytype;
			     
				var wind2=window.open(url,"Voucher","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
		</script>
	</head>
	<body class="BodyBackground1">
		<html:form method="post" action="/Notifications.do?method=getNotifications">
			<%=ScreenUtilities.screenHeader("Notifications")%>
			<table width="95%" cellspacing="0" cellpadding="0" >
				<tr>
					<td class="epis" align="center" width="10%">
						Raise Payment Voucher
					</td>
					<td class="epis" align="center" width="10%">
						PFID
					</td>
					<td class="epis" align="left" width="20%" >
						Employee Name
					</td>
					
					<td class="epis" align="left" width="20%" >
						Airport Name
					</td>
					<td class="epis" align="left" width="20%">
						Transaction ID
					</td>
					<td class="epis" width="20%">
						Approved Date
					</td>					
				</tr>
				<tr>
					<td colspan="6" align="left" height="8px"></td>
				</tr>
				<tr>
					<td colspan="3" align="left" class="crv_lineT">
						Advances
					</td>
					
					<td colspan="3" align="right" class="crv_lineT">
						<a href="javascript:void(0)" onclick="OpenPendinAmt('CPF')">Pending Amount</a>
					</td>
				</tr>
				<tr >
					<td colspan="6" >
						<div style="width:99.7%; height:2cm; overflow:auto;  " class="GridBorder">
							<table width="95%">
							<logic:empty name="cpf"><tr><td colspan=5>&nbsp;</td><tr><tr><td colspan=5 class="tableSideText"><html:submit  value="Load Data" onclick="return fwdtoPayment1();"/></td><tr><tr><td colspan=5>&nbsp;</td><tr></logic:empty>
							<logic:notEmpty name="cpf">
								<logic:iterate id="rec" name="cpf">
									<tr>
										<td width="10%">
											<bean:define id="keynoValue"><bean:write name="rec" property="transactionId" />|CPF</bean:define>											
											<html:radio  property="keyno" value="<%=keynoValue%>"  onclick="fwdtoPayment()"/>
										</td>
										<td class="tableText" align="left" width="10%">
											<bean:write name="rec" property="pensionNo" />
										</td>
										<td class="tableText" align="left" width="40%">
											<bean:write name="rec" property="employeeName" />
										</td>
										<td class="tableText" align="left" width="40%">
											<bean:write name="rec" property="airportCode" />
										</td>
										<td class="tableText" align="left" width="20%">
											<a href="javascript:void(0)" onclick="openReport('/advanceForm2.do?method=loadAdvanceForm2&frmPensionNo=<bean:write name="rec" property="pensionNo" />&frmTransID=<bean:write name="rec" property="transactionId" />&frmTransDate=<bean:write name="rec" property="transactionDate" />&frm_formType=N&frm_name=CPFApproved')"><bean:write name="rec" property="keyNo" /></a>
										</td>
										<td class="tableText" width="20%">
											<bean:write name="rec" property="approvedDate" />
										</td>
									</tr>
								</logic:iterate>
								</logic:notEmpty>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="left" >
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="3" align="left" class="crv_lineT">
						Part Final Withdrawal
					</td>
					<td colspan="3" align="right" class="crv_lineT">
						<a href="javascript:void(0)" onclick="OpenPendinAmt('PFW')">Pending Amount</a>
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<div style="width:99.7%; height:2cm; overflow:auto;" class="GridBorder">
							<table width="95%">
							<logic:empty name="pfw"><tr><td colspan=5>&nbsp;</td><tr><tr><td colspan=5 class="tableSideText"><html:submit  value="Load Data" onclick="return fwdtoPayment2();"/></td><tr><tr><td colspan=5>&nbsp;</td><tr></logic:empty>
								<logic:notEmpty name="pfw">
								<logic:iterate id="rec" name="pfw">
									<tr>
										<td width="10%">
											<bean:define id="keynoValue"><bean:write name="rec" property="transactionId" />|PFW</bean:define>											
											<html:radio  property="keyno" value="<%=keynoValue%>"  onclick="fwdtoPayment()"/>
										</td>
										<td class="tableText" align="left" width="10%">
											<bean:write name="rec" property="pensionNo" />
										</td>
										<td class="tableText" align="left" width="40%">
											<bean:write name="rec" property="employeeName" />
										</td>
										<td class="tableText" align="left" width="20%">
											<a href="javascript:void(0)" onclick="openReport('/form4Advance.do?method=advanceForm4Report&frmPensionNo=<bean:write name="rec" property="pensionNo" />&frmTransID=<bean:write name="rec" property="transactionId" />&frm_type=Form-4Report')"><bean:write name="rec" property="keyNo" /></a>
										</td>
										<td class="tableText" width="20%">
											<bean:write name="rec" property="approvedDate" />
										</td>										
									</tr>
								</logic:iterate>
								</logic:notEmpty>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="left" >
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="3" align="left" class="crv_lineT">
						Final Settlement
					</td>
					<td colspan="3" align="right" class="crv_lineT">
						<a href="javascript:void(0)" onclick="OpenPendinAmt('FS')">Pending Amount</a>
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<div style="width:99.7%; height:2cm; overflow:auto;" class="GridBorder">
							<table width="97%" >
								<logic:empty name="fs"><tr><td colspan=5>&nbsp;</td><tr><tr><td colspan=5 class="tableSideText"><html:submit  value="Load Data" onclick="return fwdtoPayment3();"/></td><tr><tr><td colspan=5>&nbsp;</td><tr></logic:empty>
								<logic:notEmpty name="fs">
								<logic:iterate id="rec" name="fs">
									<tr>
										<td width="10%">
											<bean:define id="keynoValue"><bean:write name="rec" property="transactionId" />|FS</bean:define>											
											<html:radio  property="keyno" value="<%=keynoValue%>"  onclick="fwdtoPayment()"/>
										</td>
										<td class="tableText" align="left" width="10%">
											<bean:write name="rec" property="pensionNo" />
										</td>
										<td class="tableText" align="left" width=35%">
											<bean:write name="rec" property="employeeName" />
										</td>
										<td class="tableText" align="left" width="20%">											
											<a href="javascript:void(0)" onclick="openReport('/loadNoteSheet.do?method=noteSheetReport&frmPensionNo=<bean:write name="rec" property="pensionNo" />&frmSanctionNo=<bean:write name="rec" property="transactionId" />&frm_name=FSApproved')"><bean:write name="rec" property="keyNo" /></a>
										</td>
										<td class="tableText" width="10%">
											<bean:write name="rec" property="approvedDate" />
										</td>										
									</tr>
								</logic:iterate>
								</logic:notEmpty>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="left" >
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="6" align="left" class="crv_lineT">
						Final Settlement - Arrears
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<div style="width:99.7%; height:2cm; overflow:auto;" class="GridBorder">
							<table width="97%" >
								<logic:empty name="fsa"><tr><td colspan=5>&nbsp;</td><tr><tr><td colspan=5 class="tableSideText"><html:submit  value="Load Data" onclick="return fwdtoPayment4();"/></td><tr><tr><td colspan=5>&nbsp;</td><tr></logic:empty>
								<logic:notEmpty name="fsa">
								<logic:iterate id="rec" name="fsa">
									<tr>
										<td width="10%">
											<bean:define id="keynoValue"><bean:write name="rec" property="transactionId" />|FSA</bean:define>											
											<html:radio  property="keyno" value="<%=keynoValue%>"  onclick="fwdtoPayment()"/>
										</td>
										<td class="tableText" align="left" width="10%">
											<bean:write name="rec" property="pensionNo" />
										</td>
										<td class="tableText" align="left" width=35%">
											<bean:write name="rec" property="employeeName" />
										</td>
										<td class="tableText" align="left" width="20%">											
											<a href="javascript:void(0)" onclick="openReport('/loadNoteSheet.do?method=noteSheetReport&frmPensionNo=<bean:write name="rec" property="pensionNo" />&frmSanctionNo=<bean:write name="rec" property="transactionId" />&frm_name=FSApproved')"><bean:write name="rec" property="keyNo" /></a>
										</td>
										<td class="tableText" width="10%">
											<bean:write name="rec" property="approvedDate" />
										</td>										
									</tr>
								</logic:iterate>
								</logic:notEmpty>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
