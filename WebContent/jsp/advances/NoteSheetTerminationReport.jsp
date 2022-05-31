<!--
/*
  * File       : NoteSheetTerminationReport.jsp
  * Date       : 08/Mar/2011
  * Author     : Radha P
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
  
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<title>AAI EMPLOYEES CONTRIBUTORY PROVIDENT FUND</title>
	<head>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript">
	</script>
	</HEAD>
	<body>
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="30%" rowspan="2">
									<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" />
								</td>
								<td>
									<p align="justify">
										<strong> AAI EMPLOYEES CONTRIBUTORY PROVIDENT FUND</strong>
									</p>
								</td>
							</tr>
							<tr>
								<td width="70%">
									<p align="left">
										<strong>RAJIV GANDHI BHAWAN, SAFDARJUNG AIRPORT</strong>
									</p>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="24%" rowspan="2">
									&nbsp;
								</td>
								<td width="57%" class="reportlabel" align="center">
									<strong>NEW DELHI</strong>
								<td width="30">
									&nbsp;
								</td>
								</td>
							</tr>
						</table>

					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
				</tr>

				<logic:present name="reportBean">
					<tr>
						<td colspan="2" align="center">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td width="12%">
										&nbsp;
									</td>
									<td width="14%">
										&nbsp;
									</td>
									<td width="10%">
										&nbsp;
									</td>
									<td width="10%" align="right">
										<strong>Trust&nbsp;:&nbsp;</strong>
										<bean:write name="reportBean" property="trust" />
									</td>
									<td width="10%">
										&nbsp;&nbsp;
									</td>

								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">

								<tr>
									<td width="17%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentlabel">
										PF ID
									</td>
									<td width="4%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentdata">
										<bean:write name="reportBean" property="pfid" />
									</td>
									<td width="10">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										CPF final Payment of Shri/ Smt./Miss
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										<bean:write name="reportBean" property="employeeName" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Designation
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										<bean:write name="reportBean" property="designation" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										CPF A/c No
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										<bean:write name="reportBean" property="cpfaccno" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Station Name
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">


										<logic:equal name="reportBean" property="airportcd" value="Office Complex">
             CHQ
            </logic:equal>
										<logic:equal name="reportBean" property="airportcd" value="Csia Iad">
            Mumbai Airport
            </logic:equal>
										<logic:equal name="reportBean" property="airportcd" value="Delhi">
            New Delhi
            </logic:equal>
										<logic:equal name="reportBean" property="airportcd" value="Igi Iad">
            IGI Airport New Delhi
            </logic:equal>

										<logic:equal name="reportBean" property="airportcd" value="Igicargo Iad">
            IGI Airport Cargo, New Delhi
            </logic:equal>

										<logic:equal name="reportBean" property="airportcd" value="Chennai Iad">
            Chennai
            </logic:equal>

										<logic:notEqual name="reportBean" property="airportcd" value="Office Complex">
											<logic:notEqual name="reportBean" property="airportcd" value="Csia Iad">
												<logic:notEqual name="reportBean" property="airportcd" value="Delhi">
													<logic:notEqual name="reportBean" property="airportcd" value="Igi Iad">
														<logic:notEqual name="reportBean" property="airportcd" value="Igicargo Iad">
															<logic:notEqual name="reportBean" property="airportcd" value="Chennai Iad">
																<bean:write name="reportBean" property="airportcd" />
															</logic:notEqual>
														</logic:notEqual>
													</logic:notEqual>
												</logic:notEqual>
											</logic:notEqual>
										</logic:notEqual>


									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Date of &nbsp;
										<bean:write name="reportBean" property="seperationreason" />
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										<bean:write name="reportBean" property="seperationdate" />
									</td>
								</tr>
								<logic:equal name="reportBean" property="seperationreason" value="Death">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentlabel">
											Nominee Name
										</td>
										<td>
											&nbsp;
										</td>
										<td class="reportContentdata">
											<bean:write name="reportBean" property="nomineename" />
										</td>
									</tr>
								</logic:equal>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Amount admitted for payment by CPF (Hqrs) with interest up to
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										<bean:write name="reportBean" property="amtadmtdate" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Employee Share (Subscription)(A)
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										Rs.&nbsp;
										<bean:write name="reportBean" property="emplshare" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Employer Share (Contribution)(B)
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										Rs.&nbsp;
										<bean:write name="reportBean" property="emplrshare" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Less Difference of Pension Contribution (C)
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										Rs.&nbsp;
										<bean:write name="reportBean" property="pensioncontribution" />
									</td>
								</tr>
					
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentlabel">
										Net Amount to be paid (A+B-C)
									</td>
									<td>
										&nbsp;
									</td>
									
									<td class="reportContentdata">Rs.&nbsp;<bean:write name="reportBean" property="netcontribution"/> &nbsp; 
            (Rs. <bean:write name="reportBean" property="amtinwords"/> Only)
										
									</td>
								</tr>
								<logic:notEqual name="reportBean" property="sanctionno" value="---">
									<tr>
										<td>
											&nbsp;
										</td>
										<td class="reportContentlabel">
											Sanction no
										</td>
										<td>
											&nbsp;
										</td>
										<td class="reportContentdata">
											<bean:write name="reportBean" property="sanctionno" />
										</td>
									</tr>
								</logic:notEqual>
 
	<logic:present name="bankMasterBean">
        		<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">Bank Details</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">Name of Employee as per Saving Bank Account</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata"><bean:write name="bankMasterBean" property="bankempname"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">Name Of Bank</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata"><bean:write name="bankMasterBean" property="bankname"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">Bank Account Number</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata"><bean:write name="bankMasterBean" property="banksavingaccno"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">IFSC Code</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata"><bean:write name="bankMasterBean" property="bankemprtgsneftcode"/></td>
            	<td>&nbsp;</td>
 				</tr>  
        	</logic:present>

								<tr>
									<td height="24">
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										&nbsp;
									</td>
								</tr>

								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;
									</td>
									<td height="24" colspan="3" class="reportContentdata">
										<p STYLE="text-indent: 1cm">
											CPF interest rate for financial year <strong><bean:write name="reportBean" property="finyear" /></strong> has been calculated provisionally @ <bean:write name="reportBean" property="rateOfInterest"/>% p.a. details of calculation have been placed in the file.
										</p>
									</td>
								</tr>
								 
								 	<logic:present name="bankMasterBean">		 
         	 						<tr>
         	 							<td>
										&nbsp;&nbsp;&nbsp;
										</td>
										<td  class="reportContent" colspan="3">
										<p STYLE="text-indent: 1cm">
 				   						  As per CHQ letter No AAI/NAD/CPF/FINAL PAYMENT dated 08.03.2011, the above payment will be released by CHQ through RTGS/NEFT in the above  mentioned Bank Account Number as per Bank details furnished by the employee.
 				   	 					</p>
	 									</td>
 									</tr>
 				     
 			 					</logic:present>
 			 					
 			 					
							 
							 <bean:define id="remarks" name="reportBean" property="remarks" />
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;
									</td>
									<td class="reportContentdata" colspan="3">
										<p STYLE="text-indent: 1cm;text-align: justify;word-spacing: 2px;">
											<%=remarks%>
										</p>
									</td>
								</tr>
							 
							 
								<!-- <tr>
            <td  >&nbsp;&nbsp;&nbsp;</td>
            <td height="24" colspan="3" class="reportContentdata"><p STYLE="text-indent: 1cm">On approval, RAU/we may release the payment to the
            <strong><bean:write name="reportBean" property="seperationfavour"/></strong> 
            on or after <strong><bean:write name="reportBean" property="paymentdate"/></strong></p> </td>           
          </tr>-->

								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;
									</td>
									<td height="24" colspan="3" class="reportContentdata">
										<p STYLE="text-indent: 1cm">
											President may please sanction the above payment subject to approval by the board of trustees.
										</p>
									</td>
								</tr>

								<tr>
									<td height="24">
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td height="24">
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportContentdata">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:present>
				
				
		<logic:present name="transList">		
				<tr>

					<td height="22" colspan="2" class="reportContentlabel">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
						<logic:iterate id="trans" name="transList" indexId="slno">
			<logic:equal name="trans" property="transCode"  value="22">
        
			<tr>            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
            </td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
            <tr>  
            
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" > <bean:write name="trans" property="transApprovedDate" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
          <tr>  
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation"/></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           
           <tr>
          <td>&nbsp;</td>          
          </tr>
          </logic:equal>
          </logic:iterate>
          
			 <logic:equal name="advanceBean" property="frmName" value="FSFormIII">
			 <logic:iterate id="trans" name="transList" indexId="slno">
		  	 <logic:equal name="trans" property="transCode"  value="23">
		  	 
		  	 <logic:notEqual name="trans" property="remarks"  value="---">
         <tr>
          
          <td></td>
				<td height="24" colspan="3" class="reportsublabel">
					<p STYLE="text-indent: 1cm">
						<bean:write  property="remarks" name="trans" />
												 
					</p>
				</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
         </logic:notEqual>
			<tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
            </td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
             <tr>  
            
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" > <bean:write name="trans" property="transApprovedDate" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
          
            <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation"/></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          </logic:equal>
          </logic:iterate>
							 
							 </logic:equal>
							 
							 <logic:equal name="advanceBean" property="frmName" value="FSFormIV">
							 
							 <logic:iterate id="trans" name="transList" indexId="slno">
		  	 <logic:equal name="trans" property="transCode"  value="23">
			
         <logic:notEqual name="trans" property="remarks"  value="---">
         <tr>
          
          <td></td>
				<td height="24" colspan="3" class="reportsublabel">
					<p STYLE="text-indent: 1cm">
						<bean:write  property="remarks" name="trans" />
												 
					</p>
				</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
         </logic:notEqual>
           <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
            </td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
             <tr>  
            
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" > <bean:write name="trans" property="transApprovedDate" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
           
          
            <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation"/></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          </logic:equal>
          
         <logic:equal name="trans" property="transCode"  value="24">
         <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
            </td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
             <tr>  
            
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" > <bean:write name="trans" property="transApprovedDate" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
          
          <tr>            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation"/></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          
          </logic:equal>
          </logic:iterate>
        
        </logic:equal>
        
           <logic:equal name="advanceBean" property="frmName" value="FSApproved">
          <logic:iterate id="trans" name="transList" indexId="slno">
		  	 <logic:equal name="trans" property="transCode"  value="23">
		  	 
		  	 <logic:notEqual name="trans" property="remarks"  value="---">
         <tr>
          
          <td></td>
				<td height="24" colspan="3" class="reportsublabel">
					<p STYLE="text-indent: 1cm">
						<bean:write  property="remarks" name="trans" />
												 
					</p>
				</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
         </logic:notEqual>
          <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
            </td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
             <tr>  
            
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" > <bean:write name="trans" property="transApprovedDate" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
          
            <tr>   
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation"/></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          </logic:equal>
          
          <logic:equal name="trans" property="transCode"  value="24">
        
         <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
            </td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
             <tr>  
            
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" > <bean:write name="trans" property="transApprovedDate" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
          
          <tr>  
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation"/></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          </logic:equal>
          <logic:equal name="trans" property="transCode"  value="25">
          
           <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
            </td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
             <tr>  
            
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" > <bean:write name="trans" property="transApprovedDate" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
          
          <tr>
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation"/></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          
          </logic:equal>
          </logic:iterate>
        
        </logic:equal>
							
						</table>
					</td>
				</tr>
				
			</logic:present>
			<logic:notPresent name="transList">
     <tr>
      
        <td height="22" colspan="2" class="reportContentlabel">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" >A.M. (F&A)</td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          <tr>
             <td width="12%">&nbsp;</td>
             <td class="reportContentlabel">Sr. Mgr.(Finance)</td>
            <td>&nbsp;</td>
            <td class="reportContentlabel" align="center">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          <tr>
             <td width="12%">&nbsp;</td>
             <td class="reportContentlabel">DGM (F&A)</td>
            <td>&nbsp;</td>
            <td class="reportContentlabel" align="center">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          <tr>
             <td width="12%">&nbsp;</td>
             <td class="reportContentlabel">ED (F&A)</td>
            <td>&nbsp;</td>
            <td class="reportContentlabel" align="center">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
     
     </logic:notPresent>
				<tr>
					<td class="screenlabel">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="50%" class="screenlabel">
						&nbsp;
					</td>
					<td width="50%">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
 