<!--
/*
  * File       : PaySlipView.jsp
  * Date       : 13/07/2009
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
-->


<%@ page language="java" import="java.util.*" %>

<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

<html:html>
<head>
	<title><bean:message key="common.pagetitle" bundle="payroll"/></title>
	<LINK rel="stylesheet" href="/css/payrollstyle.css" type="text/css">
	<LINK rel="stylesheet" href="/css/payroll.css" type="text/css">
	<style type="text/css"> body {background-color: transparent} </style>
	<style TYPE='text/css'> .breakhere{ page-break-after: always; }</style>
</head>
<body>
	<center>
 		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				
				<tr class="screentitle">
					<td valign="top">
					<logic:notEmpty name="empPayList">
					<logic:iterate id="empPay" name="empPayList">
					<P CLASS='breakhere'>
						<table border="0" align="center" width="100%">
							<tr>
								<td colspan="7" >
									<table border=0 cellpadding=1 cellspacing=0 width="100%" align="center" >
										<tr>
											<td colspan=4 class="tbb" align=left width="20%"><img src="/images/logoani.gif" ></td>
											<td colspan=3 align=left>
											  	<table border=0 cellpadding=3 cellspacing=0 width="100%" align="left" >
													<tr>
														<td class="ReportName" align=center><bean:message key="common.pagetitle" bundle="payroll"/></td>
													</tr>
													<tr>
														<td align=center><b><bean:write name="stationName" bundle="payroll"/>&nbsp;<bean:message key="report.airport" bundle="payroll"/></b></td>
													</tr>
													
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
							<bean:define id="emp" name="empPay" property="empInfo"/>
							
							<TR>
							 <TD>
							     <TABLE width=100% border=1 bordercolor="black" cellpadding=0 cellspacing=0 style="border-collapse: collapse;">
							<TR>
							 	<TD colspan=7>
								  	<TABLE width=100% border=0 cellspacing="0" bgcolor="#FFD2D2">
									  	<tr>
									  	    <td align="left" width="30%">
									  	         <table border=0 cellspacing="0">
									  	         <tr>
									  	                   <td class="tbb" nowrap>
									  	                     PAYSLIP NO 
									  	                   </td>
									  	                  <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="emp" property="payslipno"/>
									  	                   </td>
									  	               </tr>
									  	                <tr>
									  	                   <td class="tbb" nowrap>
									  	                       EMPLOYEE NAME
									  	                   </td>
									  	                   <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" nowrap align="left">
									  	                     &nbsp;<bean:write name="emp" property="empName"/>
									  	                   </td>
									  	               </tr>
									  	               <tr>
									  	                   <td class="tbb">
									  	                       PF ID
									  	                   </td>
									  	                   <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="emp" property="cpfNo"/>
									  	                   </td>
									  	               </tr>
									  	               <tr>
									  	                   <td class="tbb">
									  	                       EMPLOYEE CODE
									  	                   </td>
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="emp" property="empNo"/>
									  	                   </td>
									  	               </tr>									  	              
									  	               <tr>
									  	                   <td class="tbb">
									  	                       DESIGNATION 
									  	                   </td>
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" nowrap align="left"> 
									  	                     &nbsp;<bean:write name="emp" property="empDesignation"/>
									  	                   </td>
									  	               </tr>
									  	               
									  	               
									  	               <tr>
									  	                   <td class="tbb">
									  	                       STATION 
									  	                   </td>
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                    &nbsp;CHQ-RGB
									  	                   </td>
									  	               </tr>
									  	         </table>
									  	    </td>
									  	    <td align="center" width="40%">
									  	         <table border=0 cellspacing="0">
									  	              <tr>
									  	                   <td class="tbb">
									  	                       MONTH
									  	                   </td>
									  	                   <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="empPay" property="month"/>-<bean:write name="empPay" property="year"/>
									  	                   </td>
									  	               </tr>
									  	               <tr>
									  	                   <td class="tbb" nowrap>
									  	                       DATE OF BIRTH
									  	                   </td>
									  	                     <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                    &nbsp;<bean:write name="emp" property="dateofbirth"/>
									  	                   </td>
									  	               </tr>
									  	               
									  	               <tr>
									  	                   <td class="tbb" nowrap>
									  	                       DATE OF JOINING
									  	                   </td>
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                    &nbsp;<bean:write name="emp" property="dateofjoin"/>
									  	                   </td>
									  	               </tr>
									  	               
									  	               <tr>
									  	                   <td class="tbb">
									  	                       BANK A/C No
									  	                   </td>
									  	                   <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" nowrap align="left">
									  	                     &nbsp;<bean:write name="emp" property="bacno"/>
									  	                   </td>
									  	               </tr>
									  	               <tr>
									  	                   <td class="tbb">
									  	                       BANK NAME
									  	                   </td>
									  	                  <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" nowrap align="left">
									  	                     &nbsp;<bean:write name="emp" property="bankId"/>
									  	                   </td>
									  	               </tr>
									  	               <tr>
									  	                   <td class="tbb">
									  	                      PAN NO
									  	                   </td>
									  	                   <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" nowrap align="left">
									  	                     &nbsp;<bean:write name="emp" property="pan"/>
									  	                   </td>
									  	               </tr>
									  	               
									  	         </table>
									  	    </td>
									  	    <td align="left" width="20%">
									  	       <table border=0 cellspacing="0">
									  	               <tr>
									  	                   <td class="tbb">
									  	                      PENSION OPTION
									  	                   </td>
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="emp" property="pensionoption"/>
									  	                   </td>
									  	               </tr>
									  	               <tr>
									  	                   <td class="tbb">
									  	                       DAYS 
									  	                   </td>
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="emp" property="days"/>
									  	                   </td>
									  	               </tr>
									  	               <tr>
									  	                   <td class="tbb">
									  	                       HPL
									  	                   </td>
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="emp" property="hpl"/>
									  	                   </td>
									  	               </tr>
									  	               <tr>
									  	                   <td class="tbb">
									  	                       EOL
									  	                   </td>
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="emp" property="eol"/>
									  	                   </td>
									  	               </tr>
									  	               
									  	               <tr>
									  	                   <td class="tbb" >
									  	                       UAA 
									  	                   </td>
									  	                   
									  	                    <td class="tbb">&nbsp;:</td>
									  	                   <td class="tb" align="left">
									  	                     &nbsp;<bean:write name="emp" property="uaa"/>
									  	                   </td>
									  	               </tr>
									  	         </table>
									  	    </td>
									  	</tr>
									</TABLE>
								</TD>
								
							</TR>
							<tr>
								<td valign="top" colspan="7">
									<table border="1" bordercolor=black FRAME="void" RULES="rows" align="center" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse">
										<tr bgcolor="#FFFFC6">
											<td  valign="top">
											 <table border="0" width="100%" bgcolor="">
											      <tr>											   
													<td class="tbb" align="left" width="40%">PAy & ALLOWANCE</td>
													<td class="tbb" align="right" width="20%">AMOUNT</td>
												 
												    <td class="tbb" align="right" width="20%">ARR</td>
												    <td class="tbb" align="right" width="20%">REC</td>
												 </tr>	
											</table>
											</td>
											<td  class="tbb" align="left">&nbsp;</td>
											<td  valign="top" width="20%">
											 <table border="0" width="100%">
											      <tr>											   
													<td class="tbb" align="left">DEDUCTIONS</td>
													<td  class="tbb" align="right">AMOUNT</td>
												 </tr>	
											</table>
											</td>
											<td  class="tbb" align="left">&nbsp;</td>
											<td  valign="top" width="40%">
											 <table border="0" width="100%">
											      <tr>											   
													<td class="tbb" align="left" >REALISATION</td>
													<td class="tbb" align="center">LOAN AMOUNT</td>
													<td class="tbb" align="left">RECOVERED</td>
													<td class="tbb" align="left">BAL AMOUNT</td>
													<td class="tbb" align="center">EMI</td>
												 </tr>	
											</table>
											</td>											
										</tr>
										<tr>
										<td  valign="top"  >
										<table border="0" width="100%" >
											<bean:define id="earnLst" name="empPay" property="earnList"/>
											<logic:iterate id="earn" name="earnLst">
											<tr>
												<td class="tcn" align="left" width="30%" nowrap><bean:write name="earn" property="earnDeduName"/></td>
												<td class="tcn" align="right" width="20%" nowrap><bean:write name="earn" property="amount"/></td>
												<td class="tcn" align="right" width="20%" nowrap><bean:write name="earn" property="adjustmentsArr"/></td>
												<td class="tcn" align="right" width="20%" nowrap><bean:write name="earn" property="adjustmentsRec"/></td>
											</tr>
											</logic:iterate>
											
											<tr bgcolor="#FFFFC6">
											<td colspan="4" class="tbb" align="left" ><U>PERKS </U></td>
											</tr>
											
											<bean:define id="perkList" name="empPay" property="perkList"/>
											<logic:iterate id="perk" name="perkList">
											<tr>
												<td class="tcn" align="left" width="30%" nowrap><bean:write name="perk" property="earnDeduName"/></td>
												<td class="tcn" align="right" width="20%" nowrap><bean:write name="perk" property="amount"/></td>
												<td class="tcn" align="right" width="20%" nowrap><bean:write name="perk" property="adjustmentsArr"/></td>
												<td class="tcn" align="right" width="20%" nowrap><bean:write name="perk" property="adjustmentsRec"/></td>
											</tr>
											</logic:iterate>
											<tr valign="bottom" bgcolor="#FFFFC6">
												<td class="tbb" nowrap >SUB TOTAL</td>
												<td class="tbb" align="right" style="border-top:inset 1px #000000;border-bottom:inset 1px #000000;" nowrap><bean:write name="empPay" property="earnTotal" format="#,##,##,##0.00"/></td>
					                            <td class="tbb" align="right" style="border-top:inset 1px #000000;border-bottom:inset 1px #000000;" nowrap><bean:write name="empPay" property="arrTotal" format="#,##,##,##0.00"/></td>
					                            <td class="tbb" align="right" style="border-top:inset 1px #000000;border-bottom:inset 1px #000000;" nowrap><bean:write name="empPay" property="recoTotal" format="#,##,##,##0.00"/></td>						
											
											</tr>
										</table>	
										</td>
										<td  class="tbb" align="center" width=1%>&nbsp;</td>
										<td  valign="top"  width="20%">
										<table border="0" width="100%">
											<bean:define id="deduLst" name="empPay" property="deducList"/>
											<logic:iterate id="dedu" name="deduLst">
											<tr>
												<td class="tcn" align="left" nowrap><bean:write name="dedu" property="earnDeduName"/></td>
												<td class="tcn" align="right" ><bean:write name="dedu" property="amount"/></td>
											</tr>
											</logic:iterate>
											<tr valign="bottom" bgcolor="#FFFFC6">
												<td class="tbb">SUB TOTAL</td>
												<td class="tbb" align="right" style="border-top:inset 1px #000000;border-bottom:inset 1px #000000;width:80px" ><bean:write name="empPay" property="deducTotal" format="#,##,##,##0.00"/></td>
											</tr>
										</table>	
										</td>
										<td  class="tbb" align="center" width=1%>&nbsp;</td>
										<td colspan=4 valign="top" width="40%">
										<bean:define id="advLst" name="empPay" property="advList"/>
										
										<table border="0" width="100%">
											<bean:define id="rdlist" name="empPay" property="rdinfoList"/>
											<logic:iterate id="rdi" name="rdlist">
											<tr>
											    <td class="tcn" align="left" width=20% nowrap><bean:write name="rdi" property="advName"/></td>
												<td class="tcn" align="right" nowrap="nowrap"width=15%><bean:write name="rdi" property="grantedAmt"/></td>
												<td class="tcn" align="right" nowrap="nowrap" width=15% ><bean:write name="rdi" property="recoveredAmt"/></td>
												<td class="tcn" align="right" nowrap="nowrap" width=15% ><bean:write name="rdi" property="balAmount"/></td>
												<td class="tcn" align="right" nowrap="nowrap" width=15% ><bean:write name="rdi" property="emi"/></td>
											</tr>
											</logic:iterate>
											<tr valign="bottom" bgcolor="#FFFFC6">
												<td class="tbb">SUB TOTAL</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td class="tbb" align="right" style="border-top:inset 1px #000000;border-bottom:inset 1px #000000;width:80px">
												<bean:write name="empPay" property="advtotal" format="#,##,##,##0.00"/>
												</td>
											</tr>
										</table>
										</td>
										</tr>
									</table>									
								</td>
							</tr>
							
							<tr bgcolor="#FFFFC6">
								<td class="tbb" align="left" width=30% style="border-right-width: 0">
									<bean:message key="payslip.grossamt" bundle="payroll"/> &nbsp;:&nbsp;<bean:write name="empPay" property="grossAmt" format="##,##,##,###.00"/>
								</td>
								<td  class="tbb" align="center" width=4% style="border-left-width: 0;border-right-width: 0">&nbsp;</td>
								<td class="tbb" align="left" width=30% style="border-left-width: 0;border-right-width: 0">
									<bean:message key="payslip.deducamt" bundle="payroll"/> &nbsp;:&nbsp;<bean:write name="empPay" property="deducAmt" format="##,##,##,###.00"/>
								</td>
								<td align="right" class="tbb" style="border-left-width: 0;border-right-width: 0">
								   <bean:message key="payslip.netamt" bundle="payroll"/> :<bean:write name="empPay" property="netAmt" format="##,##,##,###.00"/>
								</td>
								
							</tr>
							<logic:lessThan value="0" name="empPay" property="netAmt" >
							<tr bgcolor="yellow">
								<td class="tbb" align="center" colspan="7">
									<font size="3" color="red">
										<bean:message key="payslip.netamt" bundle="payroll"/> &nbsp;:&nbsp;<bean:write name="empPay" property="netAmt" format="##,##,##,###.00"/>
									</font>
								</td>
							</tr>
							</logic:lessThan>
							<logic:greaterThan value="0" name="empPay" property="netAmt" >
							<tr bgcolor="#FFFFC6">
								<td class="tbb" align="center" colspan="7">
									RUPEES: <bean:write name="empPay" property="netamtinwords" /> Only
								</td>
							</tr>
								</logic:greaterThan>

														
							<tr>
							<td>
								<table border=0 width="100%" height="50">
								<tr>
								
								<th colspan=3 class="tbb" align="center">INCOME TAX CALCULATION ( PROVISIONAL )</th>
															
								</tr>
								<tr>
								<td class="tbb" align="left">Gross Taxable Income &nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tbb" align="center">:</td>
								<td class="tbb" align="right">0.00</td>
								</tr>
								
								<tr>
								<td class="tb" align="left">&nbsp;&nbsp;Less:  Exemption under (Section 80)&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tb" align="center">:</td>
								<td class="tb" align="right">0.00</td>
								</tr>
								
									<tr>
								<td class="tb" align="left">&nbsp;&nbsp;Less:  Exemption under (Section 10)&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tb" align="center">:</td>
								<td class="tb" align="right">0.00</td>
								</tr>
								
									<tr>
								<td class="tb" align="left">&nbsp;&nbsp;Less:  Exemption under (section 10)13A - for house rent paid&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tb" align="center">:</td>
								<td class="tb" align="right">0.00</td>
								</tr>
									<tr>
								<td class="tb" align="left">&nbsp;&nbsp;Less:  Professional Tax&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tb" align="center">:</td>
								<td class="tb" align="right">0.00</td>
								</tr>
									<tr>
								<td class="tb" align="left">&nbsp;&nbsp;Less:  Loss from House property&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tb" align="center">:</td>
								<td class="tb" align="right">0.00</td>
								</tr>
									
								
									<tr>
								<td class="tbb" align="left">Net Taxable Income&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tbb" align="center">:</td>
								<td class="tbb" align="right">0.00</td>
								</tr>
									<tr>
								<td class="tbb" align="left">Income Tax&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tbb" align="center">:</td>
								<td class="tbb" align="right">0.00</td>
								</tr>
									<tr>
								<td class="tb" align="left">&nbsp;&nbsp;Add :  Cess theron&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tb" align="center">:</td>
								<td class="tb" align="right">0.00</td>
								</tr>
									<tr>
								<td class="tbb" align="left">Total Tax Payable&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tbb" align="center">:</td>
								<td class="tbb" align="right">0.00</td>
								</tr>
								
									<tr>
								<td class="tb" align="left">&nbsp;&nbsp;Less: Tax already deducted&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tb" align="center">:</td>
								<td class="tb" align="right">0.00</td>
								</tr>
								
									<tr>
								<td class="tb" align="left">&nbsp;&nbsp;Less: AAI Beared Tax&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tb" align="center">:</td>
								<td class="tb" align="right">0.00</td>
								</tr>
								
									<tr>
								<td class="tbb" align="left">Balance Income Tax&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tbb" align="center">:</td>
								<td class="tbb" align="right">0.00</td>
								</tr>
									<tr>
								<td class="tbb" align="left">Installement Amount from this month&nbsp;:&nbsp;..................................................................................................................................</td>
								<td class="tbb" align="center">:</td>
								<td class="tbb" align="right">0.00</td>
								</tr>
									
									<tr>
								<td colspan=3 align="left"><hr></td>
								</tr>
									<tr>
								
								<td colspan=3 class="tbb" align="left"> &nbsp;1. Please verify your pension option, PFID & date of birth. For entry/updation of PFID and Pension Option inform PENSION CELL section 
  or e-mail at nagaraj@aai.aero.<br>  &nbsp;&nbsp;&nbsp;For Date of Birth inform HR-EH section or email at rajeshchawala@aai.aero or dpmemgai@aai.aero within 15 days. 
  <br>
  								</td>
								</tr>
								
									<tr>
								
								<td colspan=3 class="tbb" align="left">&nbsp;  2.On the basis of financial year 2011-12, Perks Allocations have been incorporated in the salary of April 2012 on provisional basis. 
								<br></td>
								</tr>
									<tr>
								
								<td colspan=3 class="tbb" align="left">&nbsp;  
								  3. However , if the official desires to change the perks allocation, the same may be intimated to cash section by 10.05.12. 
								<br>
								</td>
								</tr>
								<tr>
								
								<td colspan=3 class="tbb" align="left">&nbsp;  
	  4. Please check your Permanent Account Number on the Pay Slip. 
								
								</td>
								</tr>
							</table>
							</tr>
							
							</TABLE >
							
							</td>
						  	</TR>
						</table>
					</logic:iterate>
					</logic:notEmpty>
					
				</td>
			</tr>
			<logic:empty name="empPayList">
				<tr>
					<td height="40">&nbsp;</td>
				</tr>
				<tr>
					<td class="tbb" colspan="2" align="center" >
						<font color="red" size="4">Salary has been not yet genereated for the selected Payroll Month</font>
					</td>
				</tr>
			</logic:empty>
		</table>
	</center>
</body>
</html:html>
