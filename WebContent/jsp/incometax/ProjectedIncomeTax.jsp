<!--
/*
  * File       : ProjectedIncomeTax.jsp
  * Date       : 17/09/2009
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
-->


<%@ page language="java" %>

<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

<html:html>
<head>
	<title><bean:message key="common.pagetitle"/></title>
	<LINK rel="stylesheet" href="css/aimsfinancestyle.css" type="text/css">
	<LINK rel="stylesheet" href="css/aims.css" type="text/css">
	<link href="css/aims.css" rel="stylesheet" type="text/css">
	<style type="text/css"> body {background-color: transparent} </style>
	<style TYPE='text/css'> .breakhere{ page-break-after: always; }</style>
	<SCRIPT type="text/javascript">
		 function prnt(){
	      	document.getElementById("pr").style.display="none";
	      	window.print();
	      	document.getElementById("pr").style.display="block";
	     }
	</SCRIPT>
</head>
<body>
	<center>
	
	
 		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="20"></td>
				</tr>
				
				<tr class="screentitle">
					<td valign="top">
					
					<logic:iterate id="projIT" name="projITList">
					<bean:define id="empP" name="projIT" property="empPayList"/>
					<bean:define id="sec80" name="projIT" property="sectionList"/>
					<logic:iterate id="empPay" name="empP">
					<P CLASS='breakhere'>
						<table border="0" align="center" width="90%">
							<tr>
								<td colspan="3" >
									<table border=0 cellpadding=3 cellspacing=0 width="100%" align="center" >
										<tr>
											<td colspan=4 class="tbb" align=left width="30%"><img src="jsp/images/aailogo.gif" ></td>
											<td colspan=3 align=left>
											  	<table border=0 cellpadding=3 cellspacing=0 width="100%" align="left" >
													<tr>
														<td class="ReportName" align=center><bean:message key="common.pagetitle"/></td>
													</tr>
													<tr>
														<td align=center><bean:write name="stationName"/>&nbsp;<bean:message key="report.airport"/></td>
													</tr>
												</table>
											</td>
											<td width="1%" id="pr">
												<a href='#' >
												<img src='jsp/images/action/printIcon.gif' border='0' onclick="prnt();" alt='Print' /> </a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<TR>
								<td>&nbsp;</td>
							</tr>
							
							<bean:define id="emp" name="empPay" property="empInfo"/>
							<TR>
							 <TD>
							     <TABLE width=100% border=0 bordercolor="black" cellpadding=0 cellspacing=0 style="border-collapse: collapse;">
							<TR>
							 <TD colspan=7>
							  <TABLE width=100% border=0>
							<TR>
								<td colspan="3">
									<table border="0" align="center" width="100%" cellspacing="0" cellpadding="0" >
										<tr>
											<TD class="tcn"  align="left" nowrap>
												Name : <bean:write name="emp" property="empName"/>
											</TD>
											<TD class="tcn"  align="left" >
												<bean:write name="emp" property="empDesignation"/>
											</TD>
											<TD class="tcn"  align="left" >
												PAN No.: <bean:write name="emp" property="pan"/>
											</TD>
											<TD class="tcn" align="right" nowrap>
												Emp No.: <bean:write name="emp" property="empNo"/>
											</TD>
										</tr>
									</table>
								</td>
							</TR>
							<tr>
								<td align="center" class="tbb" colspan="3"><div style="border-bottom:dashed 1px #000000;"><bean:message key="prjitreport.title"/>&nbsp;<bean:write name="fyearnm"/></div></td>
							</tr>
							<tr>
								<td valign="top" colspan="3">
									<table border="0" align="center" width="100%" cellspacing="0" cellpadding="0" >
										<tr>
											<td  valign="top" width=40% >
												<table border="0" width="100%" >
													<tr>
														<td  class="tbb" align="center" width=30% colspan="2" ><div style="border-bottom:dashed 1px #000000;">PAY & ALLOWANCES</div></td>
													</tr>
													<bean:define id="earnLst" name="empPay" property="earnList"/>
													<logic:iterate id="earn" name="earnLst">
													<tr>
														<td class="tcn" align="left" ><bean:write name="earn" property="earnDeduName"/></td>
														<td class="tcn" align="right" ><bean:write name="earn" property="amount"/></td>
													</tr>
													</logic:iterate>
												</table>	
											</td>
											<td width="10%">&nbsp;</td>
											<td  valign="top" width=50% >
												<table border="0" width="100%">
													<tr>
														<td  class="tbb" align="center" width=30%><div style="border-bottom:dashed 1px #000000;">SAVINGS</div></td>
														<td  class="tbb" align="center" width=30%><div style="border-bottom:dashed 1px #000000;">U/SEC 80C</div></td>
													</tr>
													<logic:iterate id="sec" name="sec80">
													<tr>
														<td class="tcn" align="left" ><bean:write name="sec" property="savingNm"/></td>
														<td class="tcn" align="right" ><bean:write name="sec" property="savingAmt"/></td>
													</tr>
													</logic:iterate>
													<tr>
														<td CLASS="tbb" align="left">Total Savings</td>
														<td CLASS="tcn" align="right"><bean:write name="projIT" property="totalSavings"/></td>
													</tr>
												</table>	
											</td>
											
										</tr>
									</table>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td CLASS="tbb" align="left" width="36%">GROSS SALARY </TD>
								<td class="tcn" align="right" ><bean:write name="empPay" property="grossAmt"/> </TD>
								<td class="tcn" width="50%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Less:Exempted Allow u/s 10 </TD>
								<td class="tcn" align="right"><span style="border-bottom:dashed 1px #000000;width:80px"><bean:write name="projIT" property="exemAllUS10"/> </span> </TD>
								<td class="tcn" width="100%">{HRA:<bean:write name="projIT" property="hra"/>&nbsp;C.E.A: 
								<bean:write name="projIT" property="exempCEA"/>&nbsp;Hostal: 
								<bean:write name="projIT" property="exempHostal"/>&nbsp;<Br>Transport: 
								<bean:write name="projIT" property="exempTransport"/>&nbsp;Medical_Remd: 
								<bean:write name="projIT" property="exempMedicalRemb"/>}</TD>
							</tr>
							
							
							<tr>
								<td CLASS="tbb" align="left">NET SALARY BEFORE PROFL. TAX</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="netSalBeforePFTax"/>  </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Less:Profl Tax u/s 16(iii) </TD>
								<td class="tcn" align="right"><span style="width:80px"><bean:write name="projIT" property="yrlyPrfTax"/></span> </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Add:Accomadation Perquisite  </TD>
								<td class="tcn" align="right"><span style="width:80px"><bean:write name="projIT" property="grosssaladj"/></span> </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Less:Quarters Rent  </TD>
								<td class="tcn" align="right"><span style="width:80px"><bean:write name="projIT" property="qrtrsrent"/></span> </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Add:Vehicle Perquisite  </TD>
								<td class="tcn" align="right"><span style="width:80px"><bean:write name="projIT" property="vehiclePerquisite"/></span> </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Add:HBA Perquisite  </TD>
								<td class="tcn" align="right"><span style="width:80px"><bean:write name="projIT" property="hbaPerquisite"/></span> </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">TAXABLE SALARY </TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="taxableSal"/>  </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">ADD: Income from other sources :- </TD>
								<td class="tcn"> </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NSC INT for last 6 years </TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="nscIntrAmt"/> </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Govt.Pension. etc. </TD>
								<td class="tcn" align="right"> 0.0</TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<!--  <tr>
								<td CLASS="tbb" align="left">Income/Loss from House Property</TD>
								<td class="tcn" align="right">0.0</TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>-->
							<tr>
								<td CLASS="tbb" align="left">Other Income</TD>
								<td class="tcn" align="right"><span style="border-bottom:dashed 1px #000000; width:80px"><bean:write name="projIT" property="otherIncome"/></span></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">GROSS TOTAL INCOME</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="grossTotalIncome"/>  </TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">DEDUCTIONS:-</TD>
								<td class="tcn"></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;Deduction u/s 80DD/80G/80E/80U</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="tot80eud"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;Helpage</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="helpage"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;PM Relief Fund</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="pmReliefFund"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;TOTAL SAVINGS U/S 80C (AS ABOVE) <br>&nbsp;&nbsp;&nbsp;(Subject to Maxm of Rs. 1 lakh)</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="totalSec80ApplAmt"/></span></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;House Loan Interst Paid Under Section 24</TD>
								<td class="tcn" align="right"><span style="border-bottom:dashed 1px #000000;width:80px"><bean:write name="projIT" property="sec24tot"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">TOTAL INCOME (Rounded off)</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="taxableIncome" format="########.00"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">INCOME TAX for the year</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="totITLessEc" format="########.00"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">Add: Educn.Cess @ 3% thereon</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="ec" format="########.00"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">Less: AAI Beared Tax</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="AAIBearedTax" format="########.00"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;Total Tax Liability</TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="totIT" format="########.00"/></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">&nbsp;&nbsp;&nbsp;Less: IT recoverd 
								<logic:notEmpty name="projIT" property="currMonth">
								upto 
								</logic:notEmpty>
								<bean:write name="projIT" property="currMonth"/> </TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="recIT"  /></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr>
								<td CLASS="tbb" align="left">TAX PER MONTH </TD>
								<td class="tcn" align="right"><bean:write name="projIT" property="taxPerMonth" /></TD>
								<td class="tcn" width="30%">&nbsp;</TD>
							</tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td colspan="3">
									<table width="100%">
										<tr>
											<td class="tbb" align="left"><bean:message key="common.note" />:-</td>
											<td class="tcn" align="left"><bean:message key="prjitreport.note1" /></td>
										</tr>
										<tr>
											<td class="tbb" align="left">&nbsp;</td>
											<td class="tcn" align="left" ><bean:message key="prjitreport.note2" /></td>
										</tr>
									</table>
								</td>
							</tr>
							</TABLE >
							</td>
						  	</TR>
						  	<TR>
								<td>&nbsp;</td>
							</TR></TABLE>
						</TD>
					</TR>
				</table>
			</logic:iterate>
		</logic:iterate>
	</td>
	</tr>
	</table>
	
					
</center>
</body>
</html:html>
