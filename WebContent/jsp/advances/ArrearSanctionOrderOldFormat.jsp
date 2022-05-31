


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
	<title>AAI</title>
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

								<logic:equal name="st" property="region" value="CHQIAD">
									<td colspan="9">
										<strong>Ref. No. FA/CPF/FINAL PAYMENT/<bean:write name="advanceReportBean" property="finyear" /></strong>
									</td>
								</logic:equal>

								<logic:notEqual name="st" property="region" value="CHQIAD">
									<td colspan="9">
										<strong>Ref. No. AAI/CPF/FP/<bean:write name="st" property="regionLbl" /></strong>
									</td>
								</logic:notEqual>

								<td align="right">
									<strong>Date &nbsp;<bean:write name="st" property="sanctiondt" /> </strong>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="10" align="center">
									<strong>SANCTION ORDER</strong>
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
												Sanction of the president AAI EPF Trust is hereby conveyed for the final payment of  
												<logic:notEqual property="soremarks" name="st" value="">
													<bean:write property="soremarks" name="st"/>.
												</logic:notEqual>
												
												<logic:equal property="soremarks" name="st" value="">
												<logic:notEqual property="arreardate" name="st" value="">
												recovered from arrear of Wage revision from 
												<bean:write property="arreardate" name="st"/>
												</logic:notEqual>
												
												
												 in r/o of the following employee


												<logic:equal property="seperationreason" name="st" value="Resignation"> 
         resigned
        </logic:equal>
												<logic:equal property="seperationreason" name="st" value="Retirement"> 
         retiring/retired
        </logic:equal>
												<logic:equal property="seperationreason" name="st" value="VRS"> 
        voluntary retiring/retired
        </logic:equal>
												<logic:equal property="seperationreason" name="st" value="Death"> 
         expired
        </logic:equal>
												<logic:equal property="seperationreason" name="st" value="Termination"> 
         terminated
        </logic:equal>

					 					on &nbsp;<STRONG><bean:write name="st" property="seperationdate" />.</STRONG> 
					 					 </logic:equal>
					 					 The detail of CPF dues is as Follows:-
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
											PF ID
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
									
										






										<logic:equal property="seperationreason" name="st" value="VRS">
											<td class="reportContentlabel" align="center">
												Net Amount Payable
												<br>
												Rs
												<br>
												(5+6-7)
											</td>
										</logic:equal>

										<logic:notEqual property="seperationreason" name="st" value="VRS">
											<td class="reportContentlabel" align="center">
												Net Amount Payable
												<br>
												Rs
												<br>
												(5+6-7)
											</td>
										</logic:notEqual>

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





								
										

									</tr>
									<bean:define id="list" name="st" property="sanctionList" />

									<logic:iterate id="sorder" name="list" indexId="slno">
										<tr>
											<td class="reportContentdata" align="center">
												<%=slno.intValue()+1%>
											</td>
											<td class="reportContentdata">
												<bean:write name="sorder" property="pfid" />
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



												<logic:equal name="sorder" property="airportcd" value="Office Complex">
             CHQ
            </logic:equal>
												<logic:equal name="sorder" property="airportcd" value="CSIA IAD">
            Mumbai Airport
            </logic:equal>

												<logic:equal name="sorder" property="airportcd" value="Igi Iad">
            IGI Airport New Delhi
            </logic:equal>

												<logic:equal name="sorder" property="airportcd" value="Igicargo Iad">
            IGI Airport Cargo, New Delhi
            </logic:equal>

												<logic:equal name="sorder" property="airportcd" value="Chennai Iad">
            Chennai
            </logic:equal>
            <logic:equal name="sorder" property="airportcd" value="Cap Iad">
            Chennai Project
            </logic:equal>

												<logic:notEqual name="sorder" property="airportcd" value="Office Complex">
													<logic:notEqual name="sorder" property="airportcd" value="CSIA IAD">
														<logic:notEqual name="sorder" property="airportcd" value="Igi Iad">
															<logic:notEqual name="sorder" property="airportcd" value="Igicargo Iad">
																<logic:notEqual name="sorder" property="airportcd" value="Chennai Iad">
																<logic:notEqual name="sorder" property="airportcd" value="Cap Iad">
																	<bean:write name="sorder" property="airportcd" />
																</logic:notEqual>	
																</logic:notEqual>
															</logic:notEqual>
														</logic:notEqual>
													</logic:notEqual>
												</logic:notEqual>

												<bean:define id="station" name="sorder" property="airportcd" />
											</td>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="emplshare" />/-
											</td>
											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="emplrshare" />/-
											</td>
											
											
											




											<td align="right" class="reportContentdata">
												<bean:write name="sorder" property="netcontribution" />/-
											</td>
											<bean:define id="netContribution" name="sorder" property="netcontribution" />

										</tr>
									</logic:iterate>
								</table>
								<!-- <table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>

									<tr>
										<td colspan="9" class="reportContent">
											<p STYLE="text-indent: 1cm">
												Ad-hoc payment if any, already made may be deducted from this authorization
											</p>
										</td>
									</tr>

									<tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>

									<tr>
										<td colspan="9" class="reportContent" align="justify">
											<p STYLE="text-indent: 1cm">
												Outstanding dues of AAI/Govt., if any, are to be recovered/ adjusted at your end, with due approval of the competent authority subject to receipt of written consent of the ex-official/ Nominee in this regards.
											</p>
										</td>
									</tr>
			</table>-->
						 		 
		 <!-- - Bank Deatils for  Retirement  --->
							 
   		 <logic:equal name="reportBean" property="seperationreason" value="Retirement">   		   
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
 				 
 			 
			<table width="65%" border="0" align="center" cellspacing="0" cellpadding="0">        		 
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
 				 
 				
 				 
 			 <table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				<td colspan="9">
						&nbsp;
				</td>
				</tr>
         	 	<tr>
				<td  class="reportContent">
					<p STYLE="text-indent: 1cm">
 				     As per CHQ letter No AAI/NAD/CPF/FINAL PAYMENT dated 08.03.2011, the above payment will be released by CHQ through RTGS/NEFT in the above  mentioned Bank Account Number as per Bank details furnished by the 
 				     <logic:equal name="reportBean" property="seperationreason" value="Death">nominees.</logic:equal>
 				     <logic:notEqual name="reportBean" property="seperationreason" value="Death">employee.</logic:notEqual>
 					</p>
 				</td>
 				</tr>
 				</table>
 			   
 			  
 			   
 				<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
				<tr>
				<td colspan="9">
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
				  
 			   
        	 </logic:present>
        	</logic:equal>
        	 
             
         <!-- --------------->
          <table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
								
								
									 <tr>
										<td colspan="9">
											&nbsp;
										</td>
									</tr>
									
									<tr>
										<td colspan="9" class="reportContent">
											<p STYLE="text-indent: 1cm">
												Interest for the financial year
												<bean:write name="advanceReportBean" property="finyear" />
												has been calculated @ <bean:write name="reportBean" property="rateOfInterest" />% provisionally.
											</p>
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
														<bean:write name="sorder" property="netcontribution" />/-
														<bean:write name="st" property="nomineename" />
												</tr>
											</logic:notEqual>
										</logic:equal>

										<logic:notEqual name="st" property="nomineecount" value="1">
											<logic:present name="nomineeList">
												<tr>
													<td colspan="9" class="reportsublabel">
														Note: Net Amount payable may be released In favour of following Nominees:
													</td>
												</tr>
												<tr>
													<td>
														&nbsp;
													</td>
												</tr>
												<tr>
													<td colspan="9">
														<table border="0">

															<tr>
																<td class="reportContentdata" width="30%">
																	Name of nominee
																</td>
																<td class="reportContentdata" width="30%" align="center">
																	Relation with deceased employee
																</td>
																<td class="reportContentdata" width="30%" align="center">
																	Amount
																</td>
															</tr>

															<tr>
																<td class="reportContentdata">
																	Shri/Smt./Ms
																</td>
																<td colspan="2">
																	&nbsp;
																</td>
															</tr>

															<tr>
																<td colspan="3">
																	&nbsp;
																</td>
															</tr>

															<logic:iterate id="nominee" name="nomineeList" indexId="slno">

																<tr>
																	<td class="reportContentdata">
																		<bean:write name="nominee" property="nomineename" />
																	</td>
																	<td class="reportContentdata" align="center">
																		<bean:write name="nominee" property="nomineerelation" />
																	</td>
																	<td class="reportContentdata" align="center">
																		Rs
																		<bean:write name="nominee" property="totalshare" />
																	</td>
																</tr>

															</logic:iterate>
														</table>
													</td>
												</tr>

											</logic:present>
										</logic:notEqual>


									</logic:equal>


									<logic:notEqual name="st" property="approvedremarks" value="">
										<tr>
											<td colspan="9" class="reportContentdata">
												Note:&nbsp;
												<bean:write name="st" property="approvedremarks" />
											</td>
										</tr>
									</logic:notEqual>

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
								<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center">
								
								  
														<logic:iterate id="trans" name="transList" indexId="slno">
		  						 <logic:equal name="trans" property="transCode"  value="42">
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
									</logic:equal>
									
									</logic:iterate>
									
									

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
      	       
      	          
      	          <logic:equal  property="region" name="st" value="CHQNAD">      	          
      	          <logic:equal property="airportcd" name="sorder" value="Chqnad">       
												
							Sh.
					<bean:write name="sorder" property="employeeName" />
					,
					<bean:write name="sorder" property="designation" />
      	          </logic:equal>
      	          
      	          </logic:equal>
      	          
      	           <logic:notEqual  property="region" name="st" value="CHQNAD">      	          
      	          <logic:notEqual property="airportcd" name="sorder" value="Chqnad">       

										<logic:notEqual name="sorder" property="airportcd" value="Thiruvananthapuram Airport">

											

												<logic:notEqual name="sorder" property="airportcd" value="Kolkata Proj">
													<logic:notEqual name="sorder" property="airportcd" value="Chennai Iad">

														<logic:notEqual name="sorder" property="airportcd" value="Cap Iad">

															<logic:notEqual name="sorder" property="airportcd" value="CSIA IAD">

																<logic:notEqual name="sorder" property="airportcd" value="Igi Iad">
																	<logic:notEqual name="sorder" property="airportcd" value="Igicargo Iad">
																		<logic:notEqual name="sorder" property="airportcd" value="Operational Office">
																		<logic:notEqual name="sorder" property="airportcd" value="Office Complex">
																		
																			<logic:notEqual property="region" name="st" value="Eastern Region">
																			<logic:notEqual property="region" name="st" value="RAUSAP">
																				<td colspan="8" class="reportContentdata">
																					The General Manager (F&A), AAI,
																					<bean:write name="st" property="region" />
																					,
																						
																					<%
       		 
       		 System.out.println("=========headQuaters=========="+headQuaters);
       		 if(headQuaters.equals("Mumbai")){
       		 %>
																					C.S.I
																					<%
       		 }else if(headQuaters.equals("New Delhi")){
       		 %>
																					Operational Office,
																					<%
       		 }else{
       		 %>
																					<%=headQuaters%>
																					<%
       		 }
       		 if(!headQuaters.equals("New Delhi")){
       		  %>
																					Airport ,
																					<%}
       		  %>
																					<%=headQuaters%>
																				</td>
																			
																			</logic:notEqual>
																			</logic:notEqual>		
																			<logic:equal property="region" name="st" value="Eastern Region">
																			<td colspan="8" class="reportContentdata">
																				The General Manager (F&A), AAI, Eastern Region , <br/>Netaji Subhash Chandra Bose International Airport,Kolkata - 700052
																			</td>
																			</logic:equal>
																			<logic:equal property="region" name="st" value="RAUSAP">
																				<td colspan="8" class="reportContentdata">
																					The Deputy General Manager (F&A), AAI, RAU Safdarjung Airport, New Delhi
																					<br />
																					The OIC, AAI,
																					<bean:write name="station" />
																					, Safdarjung Airport, New Delhi
																					<br />

																				</td>
																			</logic:equal>
									</tr>
									<logic:notEqual name="station" value="<%=headQuaters%>">
										<logic:notEqual property="region" name="st" value="RAUSAP">
											<tr>
												<td colspan="8" class="reportContentdata">
													The OIC, AAI,
													<bean:write name="station" />
													Airport,
													<bean:write name="station" />
												</td>
											</tr>
										</logic:notEqual>
									</logic:notEqual>


									</logic:notEqual>
									</logic:notEqual>
									</logic:notEqual>
									</logic:notEqual>
									</logic:notEqual>
									</logic:notEqual>
									</logic:notEqual>
									</logic:notEqual>
									
									
									</logic:notEqual>
									</logic:notEqual>
									</logic:notEqual>

									<%
         String str="";
         String[] strarr=new String[2];
         %>


									<logic:equal name="sorder" property="airportcd" value="Thiruvananthapuram Airport">
										<tr>
											<td class="reportContentdata">
												<%         
          str=Constants.APPLICATION_THIRUVANANTHAPURAM_AIRPORT;          
          strarr=str.split(":");
         
          
          for(int i=0;i<strarr.length;i++){
         %>
												<%=strarr[i]%>
												<br>

												<%
         }
         %>
											</td>
										</tr>
									</logic:equal>



									<logic:equal name="sorder" property="airportcd" value="Kolkata Proj">
										<tr>
											<td class="reportContentdata">
												<%         
          str=Constants.APPLICATION_KOLKATA_PROJECT;
         strarr=str.split(":");
         
          
          for(int i=0;i<strarr.length;i++){
         %>
												<%=strarr[i]%>
												<br>

												<%
         }
         %>
											</td>
										</tr>
									</logic:equal>

									<logic:equal name="sorder" property="airportcd" value="Chennai Iad">
										<tr>
											<td class="reportContentdata">
												<%         
          str=Constants.APPLICATION_CHENNAI_AIRPORT;
          strarr=str.split(":");
         
          
          for(int i=0;i<strarr.length;i++){
         %>
												<%=strarr[i]%>
												<br>

												<%
         }
         %>
											</td>
										</tr>
									</logic:equal>


									<logic:equal name="sorder" property="airportcd" value="Cap Iad">
										<tr>
											<td class="reportContentdata">
												<%         
          str=Constants.APPLICATION_CHENNAI_PROJECT;
          strarr=str.split(":");
         
          
          for(int i=0;i<strarr.length;i++){
         %>
												<%=strarr[i]%>
												<br>

												<%
         }
         %>
											</td>
										</tr>
									</logic:equal>

									<logic:equal name="sorder" property="airportcd" value="CSIA IAD">
										<tr>
											<td class="reportContentdata">
												<%         
          str=Constants.APPLICATION_CSI_AIRPORT;
          strarr=str.split(":");
         
          
          for(int i=0;i<strarr.length;i++){
         %>
												<%=strarr[i]%>
												<br>

												<%
         }
         %>
											</td>
										</tr>
									</logic:equal>


									<logic:equal name="sorder" property="airportcd" value="Igi Iad">
										<tr>
											<td class="reportContentdata">
												<%         
          str=Constants.APPLICATION_OPERATIONAL_OFFICE;
         strarr=str.split(":");
         
          
          for(int i=0;i<strarr.length;i++){
         %>
												<%=strarr[i]%>
												<br>

												<%
         }
         %>
											</td>
										</tr>
									</logic:equal>

									<logic:equal name="sorder" property="airportcd" value="Igicargo Iad">
										<tr>
											<td class="reportContentdata">
												<%         
          str=Constants.APPLICATION_OPERATIONAL_OFFICE;
         strarr=str.split(":");
         
          
          for(int i=0;i<strarr.length;i++){
         %>
												<%=strarr[i]%>
												<br>

												<%
         }
         %>
											</td>
										</tr>
									</logic:equal>

									<logic:equal name="sorder" property="airportcd" value="Operational Office">
										<tr>
											<td class="reportContentdata">
											
											
											Sh. <bean:write name="sorder" property="employeeName" />,												
												<bean:write name="sorder" property="designation" />
												
											</td>
										</tr>
									</logic:equal>
									
									<logic:equal name="sorder" property="airportcd" value="Office Complex">
										<tr>
											<td class="reportContentdata">
											
											
											Sh. <bean:write name="sorder" property="employeeName" />,												
												<bean:write name="sorder" property="designation" />
												
											</td>
										</tr>
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