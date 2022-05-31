<!--
/*
  * File       : NoteSheetVerificationForm.jsp
  * Date       : 13/04/2010
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();
String basePathWoView = basePathBuf.toString();
%>
<%
    String reg="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
	hashmap=common.getRegion();

	Set keys = hashmap.keySet();
	System.out.println(".............keys................"+keys);

	Iterator it = keys.iterator();
	

  %>
<!doctype html public "-//w3c//dtd xhtml 1.0 transitional//en" "http://www.w3.org/tr/xhtml1/dtd/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
	<head>
		<base href="<%=basePath%>" />

		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="this is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />		
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>
		<script type="text/javascript" src="<%=basePath%>scripts/prototype.js"></script>
	
		<script type="text/javascript">		
		function submitData(frmName){
			 
			 	var lodcheckedlngth='';
		 	var lodhba='',total='',url='';
		 	
		 			lodcheckedlngth=document.forms[0].LOD.length;
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LOD[i].checked==true){
		 					lodhba=document.forms[0].LOD[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 			
		 			
		 	var bankname,empname,accno,rtgscode;
		 	bankname=document.forms[0].bankempname.value;
		 	empname=document.forms[0].bankname.value;
		 	accno=document.forms[0].banksavingaccno.value;
		 	rtgscode=document.forms[0].bankemprtgsneftcode.value;
		 	
		 	//alert(bankname+'--'+empname+'--'+accno+'--'+rtgscode);
		 	
		 	if(bankname!=''||empname!=''||accno!=''||rtgscode!=''){
		 	document.forms[0].paymentinfo.value='Y';
		 	}
		 	 //alert(document.forms[0].paymentinfo.value);
		 	 
		 			
		 			
			var url ="<%=basePathWoView%>loadNoteSheet.do?method=saveFinalSettlementVerification&lodinfo="+total+"&frm_name="+frmName+"&bankflag="+bankFlag;
			//alert(url);
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();				   			
			  
		}
		
		function frmPrsnlReset(frmName){
			
		
		var url="<%=basePathWoView%>loadNoteSheet.do?method=finalSettlementVerificationApproval&frmPensionNo="+document.forms[0].pensionNo.value+"&frmSanctionNo="+document.forms[0].nssanctionno.value+"&frm_name="+frmName;
			
	   		document.forms[0].action=url;
		    document.forms[0].method="post";
			document.forms[0].submit();
	} 	
	
	
	var bankinfo;
function loadBankDetails(){
if(document.forms[0].seperationreason.value=="Death"){
document.getElementById("BankDetails").style.display = "none"; 
}else{
document.getElementById("BankDetails").style.display = "block"; 
bankinfo=document.forms[0].paymentinfo.value;
//alert(bankinfo);
if(bankinfo=='Y'){
  
document.getElementById("bankempname").readOnly=true;
document.getElementById("bankname").readOnly=true;
document.getElementById("banksavingaccno").readOnly=true;
document.getElementById("bankemprtgsneftcode").readOnly=true;
} else{ 
document.getElementById("BankDetails").style.display = 'block'; 
}
}
}
var bankFlag='N';
function editBankDetails(){
if(bankinfo=='N'){
alert('Nothing to Edit');
bankFlag='Y';
}else{
alert('You want to edit the Details');
document.getElementById("bankempname").readOnly=false;
document.getElementById("bankname").readOnly=false;
document.getElementById("banksavingaccno").readOnly=false;
document.getElementById("bankemprtgsneftcode").readOnly=false;
bankFlag='Y';
}
}
	
	
		</script>

	</head>

	
<body class="bodybackground" onload="loadBankDetails();hide('<%=request.getAttribute("focusFlag")%>');">
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
		<%=ScreenUtilities.screenHeader("loansadvances.finalsettlementverificationnew.screentitle")%>
		<table width="750" border="0" cellspacing="3" cellpadding="0">
					
									  
										<tr>
											<td class="tableTextBold" width="30%">												
												Application No:&nbsp;
											</td>
											<td width="25%">
											
												<html:text styleClass="TextField" property="nssanctionno"  name="Form2ReportBean" maxlength="15" tabindex="1" readonly="true"/>
											</td>
											<td  class="tableTextBold" width="20%">
												PF ID:&nbsp;
											</td>
											<td width="25%">
												<html:hidden property="pensionNo" name="Form2ReportBean"/>	
												<html:hidden property="paymentinfo" name="Form2ReportBean"/>	
												<html:text styleClass="TextField" property="pfid"  name="Form2ReportBean" maxlength="15" tabindex="1" readonly="true"/>

											</td>
										</tr>
										<tr>
											<td class="tableTextBold">												
												CPF A/c No:&nbsp;
											</td>
											<td>
												<html:text styleClass="TextField" property="cpfaccno"  name="Form2ReportBean" maxlength="15" tabindex="1" readonly="true"/>
											</td>
											<td  class="tableTextBold">
												Father's Name:&nbsp;
											</td>
											<td>
												<html:text styleClass="TextField" property="fhName"  name="Form2ReportBean" maxlength="15" tabindex="1" readonly="true"/>

											</td>
										</tr>
										<tr>
											
											<td class="tableTextBold">
												Employee Code:&nbsp;
											</td>
											<td>
												<html:text property="employeeNo" name="Form2ReportBean" styleClass="TextField" tabindex="4" readonly="true" />												
											</td>
											
											<td class="tableTextBold">
												Date of Joining in AAI:&nbsp;
											</td>
											<td>
												<html:text property="dateOfJoining" name="Form2ReportBean" styleClass="TextField" tabindex="9" readonly="true" />
											</td>										
											
										</tr>
										<tr>
										
										<td class="tableTextBold">
												Name:&nbsp;
											</td>
											<td>
												<html:text property="employeeName" name="Form2ReportBean" styleClass="TextField" tabindex="6" readonly="true" />
											</td>
											
										    <td class="tableTextBold">
												Date of Birth:&nbsp;
											</td>
											<td>
												<html:text property="dateOfBirth" name="Form2ReportBean" styleClass="TextField" tabindex="8" readonly="true" />
											</td>										
										
										</tr>
										
										<tr>
										
										<td class="tableTextBold">
												Designation:&nbsp;
											</td>
											<td>
												<html:text property="designation" name="Form2ReportBean" styleClass="TextField" tabindex="6" readonly="true" />
											</td>											
										  <td colspan="2">&nbsp;</td>						
										
										</tr>
										
											<tr>
											
											<td class="tableTextBold">
												Permanent Address:&nbsp;
											</td>
											<td>
												<html:text property="permenentaddress"  name="Form2ReportBean" styleClass="TextField" tabindex="6" readonly="true"/>
											</td>
											
											<td class="tableTextBold">
											Present Address/Postal Address:&nbsp;
												
											</td>
											<td>												
												<html:text property="presentaddress"  name="Form2ReportBean" styleClass="TextField" tabindex="10" readonly="true"/>
											</td>								
											
										</tr>
										
										<tr>
											
											<td class="tableTextBold">
												Telephone NO:&nbsp;
											</td>
											<td>
												<html:text property="phoneno"  styleClass="TextField" tabindex="6" readonly="true"/>
											</td>
											
											<td class="tableTextBold">
											E-Mail:&nbsp;
												
											</td>
											<td>												
												<html:text property="mailID"  name="Form2ReportBean" styleClass="TextField" tabindex="10" readonly="true"/>
											</td>								
											
										</tr>
										
										<tr>
																					
											<td class="tableTextBold">
												Station:&nbsp;
											</td>
											<td>												
												<html:text property="airportcd" name="Form2ReportBean" styleClass="TextField" tabindex="10" readonly="true"/>
											</td>
											
											<td class="tableTextBold">
												Region:&nbsp;
											</td>
											<td>
												<html:text property="region" name="Form2ReportBean" styleClass="TextField" tabindex="9" readonly="true" />
											</td>												
										</tr>
										
										<tr>
																					
											<td class="tableTextBold">
												Separation Reason:&nbsp;
											</td>
											<td>												
												<html:text property="seperationreason" name="Form2ReportBean" styleClass="TextField" tabindex="10" readonly="true"/>
											</td>
											
											<td class="tableTextBold">
												Date of separation reason:&nbsp;
											</td>
											<td>
												<html:text property="seperationdate" name="Form2ReportBean" styleClass="TextField" tabindex="9" readonly="true" />
											</td>												
										</tr>
										
										<tr>
																					
											<td class="tableTextBold">
												Verified By:&nbsp;
											</td>
											<td>												
												<html:select styleClass="TextField"  property="authorizedsts" >
									            <html:option value="A">Accept</html:option>
									            <html:option value="R">Reject</html:option>
									            </html:select>
											</td>
											
											<td>&nbsp;</td>										
										</tr>
										 			
										<tr id="BankDetails" style="display:none;">																						
													
												<td colspan="3">
												<table align="center" border="0">
												
										<tr>
												<td  class="tableTextBold" align="left">
												BANK DETAILS FOR PAYMENT: <img alt="" src="<%=basePath%>images/icon-edit.gif" onclick="javascript:editBankDetails();" >
												</td>
										</tr>	
																								
										<tr>										     
											<td class="tableTextBold">Name of Employee as per Saving Bank Account:</td>
											<td><html:text styleClass="TextField"  property="bankempname" name="bankMasterBean"/></td>
											 
											 <td class="tableTextBold">Name Of Bank:</td>
											<td><html:text styleClass="TextField"  property="bankname" name="bankMasterBean"/></td>
										</tr>
										
										
										<tr>					   
											<td class="tableTextBold">Bank A/c No:</td>
											<td><html:text styleClass="TextField"  property="banksavingaccno" name="bankMasterBean"/></td>
																						
											<td class="tableTextBold" width="20%">IFSC Code Of Bank:</td>
											<td><html:text styleClass="TextField"  property="bankemprtgsneftcode" name="bankMasterBean"/></td>
										</tr>
										 
										 
										<tr>
										<td>&nbsp;</td>
										</tr>
												</table>
											 </td>
											 </tr>
													
												
												          
						           <logic:present name="postingDetList">
						           
						               <tr>
						               <td>&nbsp;</td>
						               </tr>
						               
										<tr>																						
											<td class="tableTextBold" colspan="2">
											Details of Posting  His member since his  date of appointment &nbsp;
											</td>										
											<td colspan="2">&nbsp;</td>											
										</tr>
										
										  <tr>
						               <td>&nbsp;</td>
						               </tr>
										
										
		 
		  <tr>     
          
            <td colspan="5">
            <table width="90%" align="center"  border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td>      
         
            <td class="tableTextBold" width="5%" rowspan="2"  align="center">SNo</td>
            <td class="tableTextBold" width="25%" rowspan="2" align="center">OLD CPF A/C NO</td>
            <td class="tableTextBold" width="10%" colspan="2" align="center">PERIOD</td>
            <td class="tableTextBold" width="10%" rowspan="2" align="center">REGION </td>
            <td class="tableTextBold" width="10%" rowspan="2" align="center">STATION  </td>            
            <td class="tableTextBold" width="20%" rowspan="2" align="center">POST HELD &nbsp;</td>
            <td class="tableTextBold" width="20%" rowspan="2" align="center">REMARKS</td>                       
            </td>
         </tr>
            
         <tr>
                <td>&nbsp;</td>
				<td class="tableTextBold" width="17%">From Year</td>
				<td class="tableTextBold" width="15%">To Year</td>
				</tr>
            
            </table>
            </td>                                        
          </tr>      
          
          <tr>
			<td>&nbsp;</td>
			</tr>  
			
		 <logic:iterate id="sorder" name="postingDetList" indexId="slno">
          <tr>     
          
            <td colspan="5">
            <table width="90%" align="center"  border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td>        
            <td class="tableSideText"  width="5%"><%=slno.intValue()+1%></td>
            <td class="tableSideText" width="20%" align="center"><bean:write name="sorder" property="cpfaccno"/></td>
            <td class="tableSideText" width="15%" align="center" nowrap="nowrap"><bean:write name="sorder" property="fromYear"/></td>            
            <td class="tableSideText" width="15%" align="center" nowrap="nowrap"><bean:write name="sorder" property="toYear"/></td>            
            <td class="tableSideText" width="10%" align="center" ><bean:write name="sorder" property="region"/></td>
            <td class="tableSideText" width="10%" align="center"><bean:write name="sorder" property="airportcd"/></td>
            <td class="tableSideText" width="10%" align="center"><bean:write name="sorder" property="postheld"/></td>
            <td align="right" class="tableSideText" width="15%" align="center"><bean:write name="sorder" property="remarks"/></td>            
            </td>
            </tr>
            </table>
            </td>
                                        
          </tr>        
         </logic:iterate>
         
         </logic:present>
										
										<tr>
										<td>&nbsp;</td>
										</tr>
									 
										<logic:equal property="seperationreason" name="Form2ReportBean" value="Death">
										
										
										   
										
											
										<tr>					
									   		<td  class="tableTextBold" colspan="2">
											PARTICULARS OF CLAIMANT (AS PER NOMINATION) &nbsp;
											</td>		
											<td colspan="2">&nbsp;</td>										
										</tr>
									
										
										<tr>					
										
										    <td  class="tableTextBold" colspan="2">
											(TO BE FILLED IN BY MAJOR NOMINEE/LEGAL HEIR OF DECEASED MEMBER)&nbsp;
											</td>		
											<td colspan="2">&nbsp;</td>	
																			   										
										</tr>
										
										<tr>					
										
										    <td  class="tableTextBold" colspan="2">
											(COPY OF DEATH CARTIFICATE TO BE ATTACHED)&nbsp;
											</td>		
											<td colspan="2">&nbsp;</td>	
																														
										</tr>
										
										<tr>
											<td>&nbsp;</td>
										</tr>
										
										<tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Name Of The  Claimant(IN BLOCK LETTERS) :</td>
											<td><html:text styleClass="TextField"  property="employeeName" name="Form2ReportBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
									    <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Father's/Husband's Name :</td>
											<td><html:text styleClass="TextField"  property="fhName" name="Form2ReportBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										  <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Age(As On The Date Of Death Of The Member) Of Bank :</td>
											<td><html:text styleClass="TextField"  property="empage" name="empNoteSheetPersonalInfoBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										
										  <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Marital Status ( On The Date Of Death Of Member) :</td>
											<td><html:text styleClass="TextField"  property="maritalstatus" name="empNoteSheetPersonalInfoBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										
										  <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Relationship With Deceased  :</td>
											<td><html:text styleClass="TextField"  property="emprelation" name="empNoteSheetPersonalInfoBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										
										 <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Address  :</td>
											<td><html:text styleClass="TextField"  property="empage" name="empNoteSheetPersonalInfoBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										
										 <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Whether member has been alloted the govt. quarter by the department :</td>
											<td><html:text styleClass="TextField"  property="quarterallotment" name="empNoteSheetPersonalInfoBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										
										<logic:equal property="quarterallotment"  name="empNoteSheetPersonalInfoBean" value="Y">
										
										 <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Quarter No :</td>
											<td><html:text styleClass="TextField"  property="quarterno" name="empNoteSheetPersonalInfoBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										
										 <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Name of colony  :</td>
											<td><html:text styleClass="TextField"  property="colonyname" name="empNoteSheetPersonalInfoBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										 <tr>
										    
											<td class="tableTextBold" width="20%" colspan="2">Station  :</td>
											<td><html:text styleClass="TextField"  property="empstation" name="empNoteSheetPersonalInfoBean" readonly="true"/></td>
											<td>&nbsp;</td>
										</tr>
										
										</logic:equal>
										
									
										
										</logic:equal>
										
									
										
										<tr>																						
											<td  class="tableTextBold" colspan="1">
											List of document for <bean:write property="seperationreason" name="Form2ReportBean"/>&nbsp;
											</td>		
											<td colspan="3">&nbsp;</td>								
										</tr>
										
										<%
										  String chk1="",chk2="",chk3="",chk4="",chk5="";
										%>
										
										<logic:notEqual property="seperationreason" name="Form2ReportBean" value="Death">
										
										<logic:notEqual property="seperationreason" name="Form2ReportBean" value="Resignation">
										
										<tr>
											<td colspan="4">
											<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
											<logic:equal name="lod" property="lodInfo" value="POF">
													<%chk1="checked";%>
													</logic:equal>
															
													
													<logic:equal name="lod" property="lodInfo" value="CPFSTNAA">
														<%chk2="checked";%>
													</logic:equal>
													
													
													<logic:equal name="lod" property="lodInfo" value="CPFSTIAI">
														<%chk3="checked";%>
													</logic:equal>
													
																									
													
											</logic:iterate>
											</logic:present>
											<table align="center" cellpadding="1" cellspacing="1"  > 
												<tr>
													<td class="tableSideText">1.pension option form</td>
													<td>
													<input type="checkbox" name="LOD" value="POF" <%=chk1%>>
													
													</td>
												</tr>
												<tr>
													<td class="tableSideText">2.All CPF statement for NAA employees from 1989
													</td>
													<td>
													
													<input type="checkbox" name="LOD" value="CPFSTNAA" <%=chk2%>>
													
													</td>
												</tr>
												<tr>
													<td class="tableSideText">3.All CPF statement for IAI employees from last three year</td>
													<td>
													<input type="checkbox" name="LOD" value="CPFSTIAI" <%=chk3%>>
													
													</td>
												</tr>
												
											  </table>
										
											</td>
										</tr>
										
										</logic:notEqual>
										</logic:notEqual>
										
										
										<logic:equal property="seperationreason" name="Form2ReportBean" value="Death">
										
										<tr>
											<td colspan="4">
											<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
													<logic:equal name="lod" property="lodInfo" value="NOF">
													<%chk1="checked";%>
													</logic:equal>
															
													
													<logic:equal name="lod" property="lodInfo" value="NEAPPFORM">
														<%chk2="checked";%>
													</logic:equal>
													
													
													<logic:equal name="lod" property="lodInfo" value="POF">
														<%chk3="checked";%>
													</logic:equal>
													
													<logic:equal name="lod" property="lodInfo" value="CPFSTNAA">
														<%chk4="checked";%>
													</logic:equal>
													
													
													<logic:equal name="lod" property="lodInfo" value="CPFSTIAI">
														<%chk5="checked";%>
													</logic:equal>
													
																									
													
											</logic:iterate>
											</logic:present>
											<table align="center" cellpadding="1" cellspacing="1"  > 
												<tr>
													<td class="tableSideText">1.Nomination form</td>
													<td>
													<input type="checkbox" name="LOD" value="NOF" <%=chk1%>>
													
													</td>
												</tr>
												<tr>
													<td class="tableSideText">2.Required application form of all nominees
													</td>
													<td>
													
													<input type="checkbox" name="LOD" value="NEAPPFORM" <%=chk2%>>
													
													</td>
												</tr>
												<tr>
													<td class="tableSideText">3.pension option form</td>
													<td>
													<input type="checkbox" name="LOD" value="POF" <%=chk3%>>
													
													</td>
												</tr>
												
												<tr>
													<td class="tableSideText">4.All CPF statement for NAA employees from 1989</td>
													<td>
													<input type="checkbox" name="LOD" value="CPFSTNAA" <%=chk4%>>
													
													</td>
												</tr>
												
												<tr>
													<td class="tableSideText">5.All CPF statement for IAI employees from last three year</td>
													<td>
													<input type="checkbox" name="LOD" value="CPFSTIAI" <%=chk5%>>
													
													</td>
												</tr>
												
											  </table>
										
											</td>
										</tr>
										
										</logic:equal>
										
										
										<logic:equal property="seperationreason" name="Form2ReportBean" value="Resignation">
										
										<tr>
											<td colspan="4">
											<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
													<logic:equal name="lod" property="lodInfo" value="RAL">
													<%chk1="checked";%>
													</logic:equal>
															
													
													<logic:equal name="lod" property="lodInfo" value="FORM13REQ">
													<%chk2="checked";%>
													</logic:equal>
																							
													
													<logic:equal name="lod" property="lodInfo" value="CPFSTNAA">
													<%chk3="checked";%>
													</logic:equal>
													
													
													<logic:equal name="lod" property="lodInfo" value="CPFSTIAI">
													<%chk4="checked";%>
													</logic:equal>
													
																									
													
											</logic:iterate>
											</logic:present>
											<table align="center" cellpadding="1" cellspacing="1"  > 
												<tr>
													<td class="tableSideText">1.Resignation acception letters from the authority</td>
													<td>
													<input type="checkbox" name="LOD" value="RAL" <%=chk1%>>
													
													</td>
												</tr>
												<tr>
													<td class="tableSideText">2.If joined other department form 13 required
													</td>
													<td>
													
													<input type="checkbox" name="LOD" value="FORM13REQ" <%=chk2%>>
													
													</td>
												</tr>
												<tr>
													<td class="tableSideText">3.All CPF statement for NAA employees from 1989
													</td>
													<td>
													<input type="checkbox" name="LOD" value="CPFSTNAA" <%=chk3%>>
													
													</td>
												</tr>
												
												<tr>
													<td class="tableSideText">4.All CPF statement for IAI employees from last three year
													</td>
													<td>
													<input type="checkbox" name="LOD" value="CPFSTIAI" <%=chk4%>>
													
													</td>
												</tr>
													
												

												
											  </table>
										
											</td>
										</tr>
										
										</logic:equal>
										
										

									</table>
									<table align="center">

										<tr>									
										
											<td align="left">
												<input type="button" class="btn" value="Submit" onclick="submitData('<bean:write name="advanceBean" property="frmName"/>');" />
											</td>
											<td align="right">

												<input type="button" class="btn" value="Reset" onclick="javascript:frmPrsnlReset('<bean:write name="advanceBean" property="frmName"/>')" class="btn" />
												<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											</td>
										</tr>


									</table>
							
<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

