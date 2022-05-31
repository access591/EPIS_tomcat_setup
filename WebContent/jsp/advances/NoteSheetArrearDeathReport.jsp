<!--
/*
  * File       : NoteSheetArrearDeathReport.jsp
  * ModifiedDate       : 28/07/2011
  * Author     : Radha P
  * Description: This is Old Format means before changes done on  02-Apr-2011  
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->  
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
	<head>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript">
	</script>
	</HEAD>
	<body>
	<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
            <td width="29%" rowspan="2"><img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" /></td>
            <td width="71%"><strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  AIRPORTS  AUTHORITY OF INDIA</strong></td>
          </tr>
          <tr>
            <td width="70%"><p align="left"><strong>RAJIV GANDHI BHAWAN, SAFDARJUNG AIRPORT</strong></p></td>
          </tr>
          
        </table></td>
      </tr>
     
      <tr>
      
        <td colspan="2" align="center">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="24%" rowspan="2">&nbsp;</td>
            <td width="57%" class="reportsublabel" align="center">  <strong>NEW DELHI</strong>
            <td width="30">&nbsp;</td>
            </td>
          </tr>
         </table>
      
        </td>
      </tr>
     
       <logic:present name="Form2ReportBean" >
      <tr>
        <td colspan="2" align="center">&nbsp;</td>
      </tr>       
      <tr>
      <td colspan="2">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>     
            
            <td width="14%">&nbsp;</td>
            <td width="10%">&nbsp;</td>
            <td width="6%">&nbsp;</td>
            <td width="10%" align="right"><strong>Trust&nbsp;:&nbsp;</strong><bean:write name="Form2ReportBean" property="trust"/></td>
            <td width="10%">&nbsp;&nbsp;</td>
          </tr>
        </table>  
        </td>
      </tr>
          
      <tr>
        <td colspan="2" >
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
         
           <tr>
            <td width="17%">&nbsp;</td>
            <td width="35%" class="reportContentlabel">PF ID </td>
            <td width="2%">:</td>
            <td width="30%" class="reportContentdata"><bean:write name="Form2ReportBean" property="pfid"/> </td>
            <td width="5">&nbsp;</td>
          </tr>      
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">CPF final Payment of Shri/ Smt./Miss </td>
            <td>:</td>
            <td class="reportContentdata"><bean:write name="Form2ReportBean" property="employeeName"/></td>
            <td>&nbsp;</td>
          </tr>     
              
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">CPF A/c No </td>
            <td>:</td>
            <td class="reportContentdata"><bean:write name="Form2ReportBean" property="cpfaccno"/> </td>
            <td>&nbsp;</td>
          </tr>     
             <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">Desiganation </td>
            <td>:</td>
            <td class="reportContentdata"><bean:write name="Form2ReportBean" property="designation"/> </td>
            <td>&nbsp;</td>
          </tr>              
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">Station Name</td>
            <td>:</td>
            <td class="reportContentdata">
                      
            
            <logic:equal name="Form2ReportBean" property="airportcd" value="Office Complex">
             CHQ
            </logic:equal>
            <logic:equal name="Form2ReportBean" property="airportcd" value="Csia Iad">
            Mumbai Airport
            </logic:equal>
              <logic:equal name="Form2ReportBean" property="airportcd" value="Delhi">
            New Delhi
            </logic:equal>
               <logic:equal name="Form2ReportBean" property="airportcd" value="Igi Iad">
            IGI Airport New Delhi
            </logic:equal>
            
             <logic:equal name="Form2ReportBean" property="airportcd" value="Igicargo Iad">
            IGI Airport Cargo, New Delhi
            </logic:equal>
            
            <logic:equal name="Form2ReportBean" property="airportcd" value="Chennai Iad">
            Chennai
            </logic:equal>
            
            <logic:notEqual name="Form2ReportBean" property="airportcd" value="Office Complex">
            <logic:notEqual name="Form2ReportBean" property="airportcd" value="Csia Iad">
             <logic:notEqual name="Form2ReportBean" property="airportcd" value="Delhi">
              <logic:notEqual name="Form2ReportBean" property="airportcd" value="Igi Iad">
               <logic:notEqual name="Form2ReportBean" property="airportcd" value="Igicargo Iad">
                 <logic:notEqual name="Form2ReportBean" property="airportcd" value="Chennai Iad">
               <bean:write name="Form2ReportBean" property="airportcd"/>
            </logic:notEqual>
            </logic:notEqual>
             </logic:notEqual>
            </logic:notEqual>
            </logic:notEqual>
            </logic:notEqual>
                         
            </td>
            <td>&nbsp;</td>
          </tr>        
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">Date of &nbsp;
            <logic:equal name="Form2ReportBean" property="seperationreason" value="VRS">
             Voluntary Retirement  
            </logic:equal>
            <logic:notEqual name="Form2ReportBean" property="seperationreason" value="VRS">
              <bean:write name="Form2ReportBean" property="seperationreason"/>
            </logic:notEqual>
          
            
            </td>
            <td>:</td>
            <td class="reportContentdata"><bean:write name="Form2ReportBean" property="seperationdate"/></td>
            <td>&nbsp;</td>
          </tr>        
           
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">Amount admitted for payment by CPF (Hqrs) with interest up to </td>
            <td>:</td>
            <td class="reportContentdata"><bean:write name="Form2ReportBean" property="amtadmtdate"/></td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">Employee Share (Subscription)(A)  </td>
            <td>:</td>
            <td class="reportContentdata">Rs.&nbsp;<bean:write name="Form2ReportBean" property="emplshare"/></td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">Employer Share (Contribution)(B) </td>
            <td>:</td>
            <td class="reportContentdata">Rs.&nbsp;<bean:write name="Form2ReportBean" property="emplrshare"/></td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">Less pension contribution (C)</td>
            <td>:</td>
            <td class="reportContentdata">Rs.&nbsp;<bean:write name="Form2ReportBean" property="pensioncontribution"/></td>
            <td>&nbsp;</td>
          </tr>
           <logic:equal name="Form2ReportBean" property="seperationreason" value="VRS">
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">Adhoc Amount already paid  (D)</td>
            <td>:</td>
            <td class="reportContentdata">Rs.&nbsp;<bean:write name="Form2ReportBean" property="adhocamt"/></td>
            <td>&nbsp;</td>
          </tr>
          
          </logic:equal>
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportContentlabel">
            
             <logic:equal name="Form2ReportBean" property="seperationreason" value="VRS">
            Net Amount to be paid (A+B-C-D)
            </logic:equal>
            
              <logic:notEqual name="Form2ReportBean" property="seperationreason" value="VRS">
            Net Amount to be paid (A+B-C)
            </logic:notEqual>
            
             </td>
            <td>:</td>
            <td class="reportContentlabel">Rs.&nbsp;<bean:write name="Form2ReportBean" property="netcontribution"/> &nbsp; 
            (Rs. <bean:write name="Form2ReportBean" property="amtinwords"/> Only)
            </td>
            <td>&nbsp;</td>
          </tr>
       
        <logic:notMatch name="Form2ReportBean" property="nomineename" value="<br/>">
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
        </logic:notMatch>
          <tr>
            <td height="24">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportContentdata">&nbsp;</td>
          </tr>
          
          <tr>
            <td height="24">&nbsp;</td>
            <td height="24" colspan="3" class="reportContent"><p STYLE="text-indent: 1cm">CPF interest rate for financial year
             <strong><bean:write name="Form2ReportBean" property="transMnthYear"/></strong> has been calculated provisionally @ <bean:write name="Form2ReportBean" property="rateOfInterest"/>% 
            p.a. details of calculation have been placed in the file.</p></td>           
          </tr>         
        
           
           <bean:define id="nomineedet" name="Form2ReportBean" property="nomineename" />
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;
									</td>
									 
									<logic:match name="Form2ReportBean" property="nomineename" value="<br/>">
										<td height="24" colspan="3" class="reportContentdata">
											<p STYLE="text-indent: 1cm">
												On approval, we may release the payment as details are given below:
											<!-- --For multilple nomines----->
								<logic:present name="multipleNomineeList">
								<tr>
								<td colspan="7">
								<table width="65%" border="1" bordercolor="black" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td class="reportContentlabel" align="center">
											Name of nominee
										</td>
										<td class="reportContentlabel" align="center">
											Relation with deceased employee
										</td>
										<td class="reportContentlabel" align="center">
											Amount
										</td>
										<td class="reportContentlabel" align="center">
											Name as per Saving Bank A/c No
										</td>
										<td class="reportContentlabel" align="center">
											Name Of Bank											 
										</td>
										<td class="reportContentlabel" align="center">
											BankA/c No.
										</td>
										 <td class="reportContentlabel" align="center">
											IFSC Code 
										</td>
										  
									</tr>  
									<logic:iterate id="multipleNominee" name="multipleNomineeList" indexId="slno">
									  
										<tr>										 
											<td class="reportContentdata" align="center">
												<bean:write name="multipleNominee" property="nomineename" /> 
											</td>
											<td class="reportContentdata" align="center">
												<bean:write name="multipleNominee" property="nomineerelation" /> 
											</td>
											<td class="reportContentdata" align="center">												 
												<bean:write name="multipleNominee" property="totalshare" />   
											</td> 
											<td align="center" class="reportContentdata">
												<bean:write name="multipleNominee" property="bankempname" />  
											</td>
											<td align="center" class="reportContentdata">
											<bean:write name="multipleNominee" property="bankname" /> 	 
											</td>											  
											<td align="center" class="reportContentdata">
											 	<bean:write name="multipleNominee" property="banksavingaccno" />  
											</td>
											<td align="center" class="reportContentdata">												 
												<bean:write name="multipleNominee" property="bankemprtgsneftcode" /> 
											</td> 
										</tr>
									</logic:iterate> 
								</table>
								</td>
								</tr>
								</logic:present>
								 <!-- ----->
								 </p>
								</td>
								 <tr>
				 	<td colspan="7">
				 		<table width="65%" border="0" align="center" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="7">
								&nbsp;
							</td>
						</tr>
         	 	<tr>
				<td  class="reportContent">
					<p STYLE="text-indent: 1cm">
 				     As per CHQ letter No AAI/NAD/CPF/FINAL PAYMENT dated 08.03.2011, the above payment will be released by CHQ through RTGS/NEFT in the above  mentioned Bank Account Number as per Bank details furnished by the 
 				     <logic:equal name="Form2ReportBean" property="seperationreason" value="Death">nominees.</logic:equal>
 				     <logic:notEqual name="Form2ReportBean" property="seperationreason" value="Death">employee.</logic:notEqual>
 					</p>
 				</td>
 				</tr>
 				</table>
 			</td>
 			</tr>	
 			</td> 			 	
		</logic:match>
		
		<logic:notMatch name="Form2ReportBean" property="nomineename" value="<br/>">
										<td height="24" colspan="3" class="reportContentdata">
											<p STYLE="text-indent: 1cm">
												On approval, we may release the payment to <%=nomineedet%> as per CHQ letter No AAI/NAD/CPF/FINAL PAYMENT dated 08.03.2011,
												 the above payment will be released by CHQ through RTGS/NEFT in the above  mentioned Bank Account Number as per Bank details furnished by the nominees.
												<logic:equal name="Form2ReportBean" property="nomineeAppointed" value="Y">
												as per succession certificate
												</logic:equal>
											</p>
										</td>
									</logic:notMatch>
									  
								</tr> 
          <tr>
            <td height="24">&nbsp;</td>
            <td height="24" colspan="3" class="reportContentdata">


            
             <logic:notEqual name="Form2ReportBean" property="remarks" value="">
              <tr>
          
          		<td></td>
				<td height="24" colspan="3" class="reportContentdata">
					<p STYLE="text-indent: 1cm">
						<bean:write  property="remarks" name="Form2ReportBean" />
												 
					</p>
				</td>
		</tr>
        </logic:notEqual>
       <logic:equal name="Form2ReportBean" property="remarks" value="">
       		
       		
      		 <logic:equal name="Form2ReportBean" property="arreartype" value="payscalerevision">
            <p STYLE="text-indent: 1cm">On approval, RAU/we may release the payment of CPF dues recieved from arrear of revision of Wage from
            <bean:write name="Form2ReportBean" property="arreardate"/>
             to the official. 
            </p>
            </logic:equal>
       		<logic:equal name="Form2ReportBean" property="arreartype" value="revisedinterestrate">
            <p STYLE="text-indent: 1cm">On approval, RAU/we may release the payment of CPF dues
            on revision of interest rate from 
            <bean:write name="Form2ReportBean" property="interestratefrom"/> %
            to   <bean:write name="Form2ReportBean" property="interestrateto"/> % during the year 
            <bean:write name="Form2ReportBean" property="fromfinyear"/> - 
            <bean:write name="Form2ReportBean" property="tofinyear"/>
             as details are given below:            
            </p>
            </logic:equal>
      </logic:equal>  
      
        <logic:equal name="Form2ReportBean" property="paymentdate" value="---">
        	<tr>
        	<td>&nbsp;</td>
        	<td height="24" colspan="3" class="reportContentdata">
             <p STYLE="text-indent: 1cm"> On approval we may release  the payment to the official.</p>
             </td>
             </tr>
             </logic:equal>      
             </td>           
          </tr>   
                    
           <tr>
            <td height="24">&nbsp;</td>
            <td height="24" colspan="3" class="reportContent"><p STYLE="text-indent: 1cm">
            President may please sanction the above payment subject to approval by the board of trustees.
                        
            </p></td>           
          </tr>
    
          <tr>
            <td height="24">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportContentdata">&nbsp;</td>
          </tr>
           <tr>
            <td height="24">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportContentdata">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      </logic:present>
      <logic:present name="transList">
      
       <tr>
        <td height="22" colspan="2" class="reportContentlabel">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
       
      
      <logic:notEqual name="advanceBean" property="frmName" value="FSArrForm">    
       <logic:iterate id="trans" name="transList" indexId="slno">
			<logic:equal name="trans" property="transCode"  value="41">
        
                  
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
          <td>&nbsp;</td>
          </tr>
          
          <tr>  
            
             <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
        
       </logic:equal>
        </logic:iterate>
         </logic:notEqual>
         
        <logic:equal name="advanceBean" property="frmName" value="FSArrearRecommendation">
          <logic:iterate id="trans" name="transList" indexId="slno">
		  	 <logic:equal name="trans" property="transCode"  value="42">
         
         
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
          <td>&nbsp;</td>
          </tr>
          
            <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation" /></td>
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
        
        <logic:equal name="advanceBean" property="frmName" value="FSArrearVerification">
         	 <logic:iterate id="trans" name="transList" indexId="slno">
		  	 <logic:equal name="trans" property="transCode"  value="42">
			
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
          <td>&nbsp;</td>
          </tr>
          
            <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          
        </logic:equal>
          
         <logic:equal name="trans" property="transCode"  value="43">
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
          <td>&nbsp;</td>
          </tr>
          
          <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation" /></td>
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
        
        <logic:equal name="advanceBean" property="frmName" value="FSArrearApproved">
          <logic:iterate id="trans" name="transList" indexId="slno">
		  	 <logic:equal name="trans" property="transCode"  value="42">
		  	 
		  	 
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
          <td>&nbsp;</td>
          </tr>
          
            <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
          
        </logic:equal>
          
          <logic:equal name="trans" property="transCode"  value="43">
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
          <td>&nbsp;</td>
          </tr>
          
          <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation" /></td>
            <td width="5%">&nbsp;</td>
            <td width="30%" class="reportContentlabel" align="center">&nbsp;</td>
            <td width="10%">&nbsp;</td>
          </tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
           </logic:equal>
          <logic:equal name="trans" property="transCode"  value="44">
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
          <td>&nbsp;</td>
          </tr>
          
          <tr>     
            
            <td width="15%">&nbsp;</td>
            <td width="30%"  class="reportContentlabel" ><bean:write name="trans" property="designation" /></td>
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
      
      <tr>
        <td class="screenlabel">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="50%" class="screenlabel">&nbsp;</td>
        <td width="50%">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
		</html:form>
	</body>
</html>