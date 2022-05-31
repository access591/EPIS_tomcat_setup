



<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

String headQuaters="";
%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<title>SANCTION ORDER</title>
	<head>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript">
	</script>
	</HEAD>
	<body>
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
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
				<logic:present name="sanctionOrderList">
					<logic:iterate id="st" name="sanctionOrderList">
						<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="9">
									<strong>Ref. No. AAI/CPF/FP/<bean:write name="st" property="regionLbl" /></strong>
								</td>
								<td align="right">
									<strong>Date &nbsp;<bean:write name="st" property="sanctiondt" /> </strong>
								</td>
							</tr>
							<tr>
								<td colspan="10" align="center">
									<strong>SANCTION ORDER<logic:notEqual  name="reportBean" property="sanctionOrderNo" value="---"> - <bean:write name="reportBean" property="sanctionOrderNo"/> </logic:notEqual></strong>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>

						<tr>
							<td colspan="2">

								<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="9" class="reportContentlabel">
											Sub: Final payment of CPF dues.
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td colspan="9" class="reportContent">
											<p STYLE="text-indent: 1cm">
												Sanction of the president AAI EPF Trust is hereby conveyed for the final payment of CPF dues in r/o of the following employee


												<logic:equal property="seperationreason" name="st" value="Resignation"> 
         resigned
        </logic:equal>
												<logic:equal property="seperationreason" name="st" value="Retirement"> 
         retiring/retired
        </logic:equal>
												<logic:equal property="seperationreason" name="st" value="Death"> 
         expired
        </logic:equal>
												<logic:equal property="seperationreason" name="st" value="Termination"> 
         terminated
        </logic:equal>

												on &nbsp;<STRONG><bean:write name="st" property="seperationdate" />.</STRONG> The detail of CPF dues is as Follows:-
											</p>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
								</table>


								<table width="80%" border="1" bordercolor="black" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td class="reportContentlabel" align="center">
											S.No
										</td>
										<td class="reportContentlabel" align="center">
											CPF A/C No
										</td>
										<td class="reportContentlabel" align="center">
											Name & Designation S/Ms./Sh .
										</td>
										<td class="reportContentlabel" align="center">
											Station
										</td>
										<td class="reportContentlabel" align="center">
											Employee Subscription
											<br>
											Rs
										</td>
										<td class="reportContentlabel" align="center">
											AAI Contribution
											<br>
											Rs
										</td>
										<logic:notEqual property="pensioncontribution" name="reportBean" value="0">
										<td class="reportContentlabel" align="center">
											Less Difference of Pension Contribution
											<br>
											Rs
										</td>
										</logic:notEqual>
										<td class="reportContentlabel" align="center">
											Net Amount Payable
											<br>
											Rs
											<br>
											(5+6-7)
										</td>
									</tr>

									<tr>
										<td align="center" class="reportContentlabel">
											1
										</td>
										<td align="center" class="reportContentlabel">
											2
										</td>
										<td align="center" class="reportContentlabel">
											3
										</td>
										<td align="center" class="reportContentlabel">
											4
										</td>
										<td align="center" class="reportContentlabel">
											5
										</td>
										<td align="center" class="reportContentlabel">
											6
										</td>
										<td align="center" class="reportContentlabel">
											7
										</td>
										<logic:notEqual property="pensioncontribution" name="reportBean" value="0">
										<td align="center" class="reportContentlabel">
											8
										</td>
										</logic:notEqual>
									</tr>
									<bean:define id="list" name="st" property="sanctionList" />

									<logic:iterate id="sorder" name="list" indexId="slno">
										<tr>
											<td class="reportContentdata" align="center">
												<%=slno.intValue()+1%>
											</td>
											<td class="reportContentdata">
												<bean:write name="sorder" property="cpfaccno" />
											</td>
											<td class="reportContentdata" align="center">

												<logic:equal property="seperationreason" name="st" value="Death"> 
            Late
      	    </logic:equal>

												<bean:write name="sorder" property="employeeName" />
												<br>
												<bean:write name="sorder" property="designation" />
											</td>
											<td class="reportContentdata" align="center">
												<bean:write name="sorder" property="airportcd" />
												<bean:define id="station" name="sorder" property="airportcd" />
											</td>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="emplshare" />
												/-
											</td>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="emplrshare" />
												/-
											</td>
											<logic:notEqual property="pensioncontribution" name="reportBean" value="0">
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="pensioncontribution" />
												/-
											</td>
											</logic:notEqual>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="netcontribution" />
												/-
											</td>
											<bean:define id="netContribution" name="sorder" property="netcontribution" />

										</tr>
									</logic:iterate>
								</table>
								
								<!-- - Bank Deatils for Death Case Single Nominee --->
         <logic:equal name="reportBean" property="seperationreason" value="Death">
        	 <logic:equal name="st" property="nomineecount" value="1"> 
   		 	 <logic:present name="bankMasterBean">
         		
         		 <table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
				 <tr>
					<td colspan="9">
					&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="9" class="reportContentlabel" align="justify">
						<p STYLE="text-indent: 1cm">
						Bank Details is given below :-</p>
					</td>
				</tr> 				 
 				</table>
 			
			<table width="60%" border="0" align="center" cellspacing="0" cellpadding="0">        		 
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">1. Name of Employee as per Saving Bank Account</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="bankempname"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">2. Name Of Bank</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="bankname"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">3. Bank Account Number</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="banksavingaccno"/></td>
            	<td>&nbsp;</td>
 				</tr>  
 				<tr>
        		<td>&nbsp;</td>
            	<td class="reportContentlabel">4. IFSC Code</td>
            	<td>&nbsp;</td>
            	<td class="reportContentdata">: <bean:write name="bankMasterBean" property="bankemprtgsneftcode"/></td>
            	<td>&nbsp;</td>
 				</tr>      
 			 </table>
 			  
 				 
        	 </logic:present>
        	</logic:equal>
        	</logic:equal>
        	 
        <!--  </logic:equal>-->        
         <!-- --------------->
         <!-- - Common for Single & multiple nominee   cases --->
          
        	  <logic:present name="bankMasterBean">
				<tr>
				 <td>
				 	<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				<td colspan="8">
						&nbsp;
				</td>
				</tr>
         	 	<tr>
				<td  class="reportContent">
					<p STYLE="text-indent: 1cm">
 				     As per CHQ letter No AAI/NAD/CPF/FINAL PAYMENT dated 08.03.2011, the above payment will be released by CHQ through RTGS/NEFT in the above  mentioned Bank Account Number as per Bank details furnished by the nominees. 
 				      
 					</p>
 				</td>
 				</tr>
 				</table>
 			</td>
 			</tr>
 			
 			<tr>
			<td>
 				<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				<td colspan="8">
						&nbsp;
				</td>
				</tr>
         	 	<tr>
				<td  class="reportContentlabel">
					 <p STYLE="text-indent: 1cm;font-size: 20px;font-weight: bold;">
 				      <U>No payment to be released by region/station against this sanction order.</U></p> 
 				</td>
 				</tr>
 				</table>
				 </td>
				</tr>
			</logic:present>
			 <!-- --------> 
								
								
								<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>


									<tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>

									<tr>
										<td colspan="9" class="reportContent">
											Interest for the financial year <STRONG><bean:write name="advanceReportBean" property="finyear" /></STRONG> has been calculated @ <bean:write name="reportBean" property="rateOfInterest" />% provisionally.
										</td>
									</tr>

									<tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>
									<logic:equal name="st" property="seperationreason" value="Death">
										<logic:equal name="st" property="nomineecount" value="1">
											<logic:notEqual name="st" property="nomineename" value="">
												<tr>
													<td colspan="9" class="reportsublabel">
														NOTE:- Net Amount payable for Rs.
														<bean:write name="sorder" property="netcontribution" />
														/- will be released by CHQ through RTGS/NEFT
														<bean:write name="st" property="nomineename" />
												</tr>
											</logic:notEqual>
										</logic:equal>

										<logic:notEqual name="st" property="nomineecount" value="1">
											<logic:present name="nomineeList">
												<tr>
													<td colspan="9" class="reportsublabel">
														Note: Net Amount payable will be released by CHQ through RTGS/NEFT in favour of following Nominees:
													</td>
												</tr>
												<tr>
													<td>
														&nbsp;
													</td>
												</tr>
												
												<!-- For Death case Multiple Nominees -->
						  
						<tr>
						<td>
						<table width="100%" border="1" bordercolor="black" align="center" cellspacing="0" cellpadding="0">
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
											<td class="reportContentdata">
												 
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
						 
						<!-- ------->
												 

											</logic:present>
										</logic:notEqual>


									</logic:equal>
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

									<logic:notEqual name="st" property="paymentdate" value="">
										<tr>
											<td colspan="9" class="reportContentdata">
												Note: Payable on or after
												<bean:write name="st" property="paymentdate" />
												.
											</td>
										</tr>
									</logic:notEqual>
									
									   <tr>
										<td colspan="9" class="reportContent">
											<p STYLE="text-indent: 1cm">
												<bean:define id="loddocument" property="remarks" name="reportBean" />
												<%=loddocument%>
											</p>
										</td>
									</tr> 
									 

								</table>
							</td>
						</tr>

						
						<tr>

							<td height="22" colspan="2" class="reportsublabel">
							<logic:present name="transList">
								<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center">
								
								  <logic:iterate id="trans" name="transList" indexId="slno">
		  						 <logic:equal name="trans" property="transCode"  value="23">
									<tr>
										<td width="25%">
											&nbsp;
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<td width="29%" class="reportContentlabel" align="right">
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="25%">
									<!-- 	<logic:notEqual name="st" property="sanctiondt" value="">
											&nbsp;Date : <bean:write name="st" property="sanctiondt" />
										</logic:notEqual>-->
										
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<td width="29%" class="reportContentlabel" align="right">
											<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
										</td>
									</tr>
									<tr>
										<td width="25%">
											&nbsp;
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<!-- <td width="29%" class="reportContentlabel" align="center">(Authorized Signatory)</td>-->
										<td width="29%" class="reportContentlabel" align="right">
										 (<bean:write name="trans" property="transDispSignName" />)
										</td> 
									</tr>
									<tr>
										<td width="25%">
											&nbsp;
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<td width="29%" class="reportContentlabel" align="right">
											<bean:write name="trans" property="designation" />
										</td>
									</tr>
									<tr>
										<td width="25%">
											&nbsp;
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<td width="29%" class="reportContentlabel" align="right">
										<%=Constants.APPLICATION_FINAL_SETTLEMENT_SANCTION_ORDER_TRUST%></td>
									</tr>
									
									</logic:equal>
									
									</logic:iterate>


								</table>
								
								
								</logic:present>
						<logic:notPresent name="transList">
      
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
										<td width="25%">
											&nbsp;
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<!-- <td width="29%" class="reportContentlabel" align="center">(Authorized Signatory)</td>-->
										<td width="29%" class="reportContentlabel" align="right">
											(A.H. WADHVA )
										</td>
									</tr>
									<tr>
										<td width="25%">
											&nbsp;
										</td>
										<td width="25%">
											&nbsp;
										</td>
										<td width="21%">
											&nbsp;
										</td>
										<td width="29%" class="reportContentlabel" align="right">
											Sr. Manager (Finance)
										</td>
									</tr>
           <tr>
          <td>&nbsp;</td>          
          </tr>
             
        </table> 
      
     
     </logic:notPresent>

								<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="11">
											&nbsp;
										</td>
									</tr>
									<tr>
										<td colspan="11" class="reportContentlabel">
											Sh.
											<bean:write name="sorder" property="employeeName" />
											,
											<bean:write name="sorder" property="designation" />
										</td>
									</tr>
								</table>

								<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="8">
											&nbsp;
										</td>
									</tr>
									<bean:define name="st" id="reg" property="region" />

									<tr>
										<%
      	       CommonUtil commonUtil=new CommonUtil();
      	       System.out.println("----reg----"+reg);
      	       String reg1=String.valueOf(reg);
      	       try{
      	         headQuaters=commonUtil.getRegionHeadQuarters(reg1);
      	         System.out.println("----headQuaters----"+headQuaters);
      	       }catch(Exception e){
      	         
      	       }
      	     %>
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
			</logic:iterate>
			</logic:present>
		</html:form>
	</body>
</html>
