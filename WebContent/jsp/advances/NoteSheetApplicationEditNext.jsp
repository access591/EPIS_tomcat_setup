<!--
/*
  * File       : NoteSheetApplicationFormNext.jsp
  * Date       : 12/04/2009
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
		
		<SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime.js"></SCRIPT>
		
		<script type="text/javascript">
		
		function frmPrsnlBack(){
		
		var frmName;			
		
			frmName='advancesedit';
			
			url="<%=basePath%>loadNoteSheet.do?method=loadFinalSettlementAppBack&frm_sanctionno="+document.forms[0].nssanctionno.value+"&frm_pensionno="+document.forms[0].pensionNo.value;
			document.forms[0].action=url;
		    document.forms[0].method="post";
			document.forms[0].submit();
		}
		
	function moreRecords(windowname){	
	var pensionNo,url,flag;
	var exp=/^[0-9]+$/;			
		
	var href;
	   		href="loadAdvance.do?method=previousWithDrawalDetails";
			progress=window.open(href, windowname, 'width=550,height=300,statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
	}	
	
	function selectBankDetail(){		
				var bankDetailsInfo='';
				bankDetailsInfo=document.forms[0].bankdetail.options[document.forms[0].bankdetail.selectedIndex].value;
				
		  		if(bankDetailsInfo=='bank'){   		    
		   			document.getElementById("bankdet").style.display="block";		   		 
		   		}else{
		   			document.getElementById("bankdet").style.display="none";
		   		 
		   		} 	
		      
		}
	
	function updateFinalSettlementInfo(){
		 
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
		 	
		 	url="<%=basePath%>loadNoteSheet.do?method=updateFinalSettlementInfo&lodinfo="+total;
		 	//alert("----url----"+url);		
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		 		
	}

	
	function getWithDrawalDetails(ExpList){	 	
	   	 //alert(ExpList);
	   	 document.forms[0].postingdetails.value=ExpList;
	}  
	
	
	function selectQuarterDetail(){		
				var quarterallotment='';
				quarterallotment=document.forms[0].quarterallotment.options[document.forms[0].quarterallotment.selectedIndex].value;
				
		  		if(quarterallotment=='Y'){   		    
		   			document.getElementById("quarterdet").style.display="block";		   		 
		   		}else{
		   			document.getElementById("quarterdet").style.display="none";
		   		 
		   		} 	
		      
		}
	function chkBankDet(){	 
	   
		if(document.forms[0].bankdetail.value=='bank'){   		    
   			document.getElementById("bankdet").style.display="block";   		 	
   		}else{
   			document.getElementById("bankdet").style.display="none";   		 	
   		} 
   		   		
   		 if(document.forms[0].postingdetails.value=='Y'){
	         document.getElementById("editpostingdet").style.display="block";
	          document.getElementById("addpostingdet").style.display="none";
	      }else{
		     document.getElementById("editpostingdet").style.display="none";
		      document.getElementById("addpostingdet").style.display="block";
	      }   		   		
			
	    if(document.forms[0].seperationreason.value=='Death')	{	
		  if(document.forms[0].quarterallotment.value=='Y')	   	{
		  document.getElementById("quarterdet").style.display="block";
		  }else{
		  document.getElementById("quarterdet").style.display="none";
		  }
	  
	   }
	  
	  
	}
	
		
	function loadPostingDetails(windowname){
	  
	  var pensionNo,url,flag;
	  var exp=/^[0-9]+$/;		
	  var href;
	  var frmName='FSAppEdit';
	
	   		href="loadNoteSheet.do?method=detailsOfPosting&frm_pensionno="+document.forms[0].pensionNo.value+"&frm_name="+frmName;
			progress=window.open(href, windowname, 'width=650,height=300,statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
	}
	
	function loadQuaterOption(){
		
		if((document.forms[0].seperationreason.value=='Retirement') || (document.forms[0].seperationreason.value=='Death')){
			document.getElementById("quarterallot").style.display="block";	
		}
		
		if((document.forms[0].seperationreason.value=='Retirement') || (document.forms[0].seperationreason.value=='Death')){
		
		if(document.forms[0].quarterallotment.value=='Y'){
		document.getElementById("quarterdet").style.display="block";			 
		}
		
		}
		
		}
	
  				  		   	
 </script>
	</head>


	<body class="BodyBackground" onload="chkBankDet();loadQuaterOption();">
		<html:form  method="post" action="loadNoteSheet.do?method=saveFinalSettlement">
					
				<%=ScreenUtilities.screenHeader("loansadvances.finalsettlementappedit.screentitle")%>
				
					<table width="750" border="0" cellspacing="3" cellpadding="0">
					
										<tr>
											<td colspan="4">
											 <table border="0">
											 <tr>
											 <td class="tableTextBold">												
												DETAILS OF POSTINGS HELD BY THE MEMBER SINCE HIS DATE OF APPOINTMENT
												&nbsp;
												</td>
												
												<td>
											<table border="0">
												<tr>
												
													<td id="addpostingdet" style="display: none;">
														 <img alt="" src="<%=basePath%>/images/addIcon.gif" onclick="loadPostingDetails('WithDrawalDetails');" />
													</td>
												</tr>
											</table>

											
										</td>
										
												<td>
											<table border="0">
												<tr>
												
													<td id="editpostingdet" style="display: none;">
														<img alt="" src="<%=basePath%>images/icon-edit.gif" onclick="loadPostingDetails('WithDrawalDetails');" />
													</td>
												</tr>
											</table>

											
										</td>
												</tr>
												</table>
											</td>										
										</tr>
										
										<tr>
										<td>&nbsp;</td>
										</tr>
														  									
										<tr>
											<td class="tableTextBold" width="23%">
												Bank Detail For Payment:&nbsp;
											</td>
											<td>
											<html:hidden property="nssanctionno"/>	
											<html:hidden property="pensionNo"/>											
											<html:hidden property="dateOfBirth"/>								
											<html:hidden property="region"/>								
											<html:hidden property="designation"/>		
											<html:hidden property="cpfaccno"/>							
											<html:hidden property="dateOfJoining"/>
											<html:hidden property="station"/>	
											<html:hidden property="seperationreason"/>	
											<html:hidden property="seperationdate"/>	
											<html:hidden property="permenentaddress"/>	
											<html:hidden property="presentaddress"/>	
											<html:hidden property="phoneno"/>	
											<html:hidden property="empmailid"/>	
											<html:hidden property="chkbankinfo"/>
											<html:hidden property="postingdetails"/>
											<html:hidden property="paymentinfo"/>
											<html:hidden property="lodInfo"/>
											<html:hidden property="resignationreason"/>
											<html:hidden property="organizationname"/>
											<html:hidden property="organizationaddress"/>
											<html:hidden property="appointmentdate"/>
											<html:hidden property="postedas"/>
											<html:hidden property="workingplace"/>
											
											
											<html:select  styleClass="TextField"  property="bankdetail" name="advanceBean"  onchange="selectBankDetail();">
											<html:option value="NO-SELECT">Select One</html:option>
											<html:option value="bank">Yes</html:option>																
											</html:select>

											</td>
											<td>								
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr id="bankdet" style="display:none">
													<td colspan="4">
														<table  align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
															
															<tr>
															     <td width="25%" class="tableTextBold">1.</td>
																<td class="tableTextBold" width="20%">Name as per saving:</td>
																<td><html:text styleClass="TextField"  property="bankempname" maxlength="50"/></td>
																<td>&nbsp;</td>
															</tr>
															<tr>
															 <td class="tableTextBold">2.</td>
																<td class="tableTextBold">Bank A/C:</td>
																<td><html:text styleClass="TextField"  property="banksavingaccno" maxlength="50"/></td>
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">3.</td>
																<td class="tableTextBold">Name Of Bank:</td>
																<td><html:text styleClass="TextField"  property="bankname" maxlength="50"/></td>
																<td>&nbsp;</td>																																
															</tr>
															
															<tr>
															 <td class="tableTextBold">4.</td>
																<td class="tableTextBold">Branch(Full Address):</td>
																<td><html:text styleClass="TextField"  property="branchaddress" maxlength="150"/></td>
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">5.</td>
																<td class="tableTextBold">IFSC Code Of Bank :</td>
																<td><html:text styleClass="TextField"  property="bankemprtgsneftcode" maxlength="25"/></td>
																<td>&nbsp;</td>																
															</tr>	
															
															<tr>
															 <td class="tableTextBold">6.</td>
																<td class="tableTextBold">Mode Of Payment :</td>
																<td>
																
															
																<html:select  styleClass="TextField"  property="bankpaymentmode">																
																<html:option value="cheque">Cheque</html:option>
																<html:option value="internaltransfer">Internal Transfer</html:option>
																<html:option value="NEFT">NEFT</html:option>
																<html:option value="RTGS">RTGS</html:option>
																<html:option value="DD">DD</html:option>																
																</html:select>
																
																</td>
																<td>&nbsp;</td>																
															</tr>		
															
															<tr>
															 <td class="tableTextBold">7.</td>
																<td class="tableTextBold">Payment Desired At  :</td>
																<td>																												
																<html:text styleClass="TextField"  property="city" maxlength="25"/>
																</td>
																<td>&nbsp;</td>																
															</tr>		
															
														</table>
													</td>
												</tr>
												 
												 <tr>
												 <td colspan="4">&nbsp;</td>
												 </tr>		
												 
												 <logic:equal property="seperationreason" name="advanceBean" value="Death">										
												 
												 <tr>
											<td colspan="4">
											 <table border="0">
											 <tr>
											 <td class="tableTextBold">												
												PARTICULARS OF CLAIMANT (AS PER NOMINATION)
												</td>
												</tr>
												</table>
											</td>										
											</tr>
											
											
												 <tr>
											<td colspan="4">
											 <table border="0">
											 <tr>
											 <td class="tableTextBold">												
												(TO BE FILLED IN BY MAJOR NOMINEE/LEGAL HEIR OF DECEASED MEMBER)
												</td>
												</tr>
												</table>
											</td>										
											</tr>
											
											
												 <tr>
											<td colspan="4">
											 <table border="0">
											 <tr>
											 <td class="tableTextBold">												
												(COPY OF DEATH CARTIFICATE TO BE ATTACHED)
												</td>
												</tr>
												</table>
											</td>										
											</tr>
											</logic:equal>
											
												 								
											
											<tr>
													<td colspan="4">
														<table  align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
														<logic:equal property="seperationreason" name="advanceBean" value="Death">		
															
															<tr>
															     <td width="10%" class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold" width="40%">Name Of The  Claimant(IN BLOCK LETTERS):</td>
																<td><html:text styleClass="TextField"  property="employeeName" maxlength="50"/></td>
																<td>&nbsp;</td>
															</tr>
															<tr>
															 <td class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold">Father's/Husband's Name:</td>
																<td><html:text styleClass="TextField"  property="fhName" maxlength="50"/></td>
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold">Age(As On The Date Of Death Of The Member) Of Bank:</td>
																<td><html:text styleClass="TextField"  property="empage"   maxlength="3"/></td>
																<td>&nbsp;</td>																																
															</tr>
															
															<tr>
															 <td class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold">Marital Status ( On The Date Of Death Of Member):</td>
																<td><html:text styleClass="TextField"  property="maritalstatus"   maxlength="10"/></td>
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold">Relationship With Deceased:</td>
																<td><html:text styleClass="TextField"  property="emprelation"   maxlength="15"/></td>
																<td>&nbsp;</td>																
															</tr>		
															
															<tr>
															 <td class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold">Address:</td>
																<td><html:text styleClass="TextField"  property="empaddress"  maxlength="150"/></td>
																<td>&nbsp;</td>																
															</tr>	
															
														</logic:equal>	
															
															<tr id="quarterallot" style="display:none">
															 <td class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold">Whether member has been alloted the govt. quarter by the department:</td>
																<td>
																																
																<html:select  styleClass="TextField"  property="quarterallotment"    onchange="selectQuarterDetail();">
																<html:option value="Y">Yes</html:option>
																<html:option value="N">No</html:option>																
																</html:select>
																
																</td>
																<td>&nbsp;</td>																
															</tr>	
															
														<tr id="quarterdet" style="display:none">
														
														<td colspan="4">
														<table  align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
															
															<tr>
															    <td class="tableTextBold" width="10%">&nbsp;</td>
																<td class="tableTextBold" width="40%">Quarter No:</td>
																<td><html:text styleClass="TextField"  property="quarterno" maxlength="10"/></td>
																<td>&nbsp;</td>		
															</tr>
															
															<tr>
															 <td class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold">Name of colony:</td>
																<td><html:text styleClass="TextField"  property="colonyname" maxlength="25"/></td>
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">&nbsp;</td>
																<td class="tableTextBold">Station:</td>
																<td><html:text styleClass="TextField"  property="empstation" maxlength="20"/></td>
																<td>&nbsp;</td>																
															</tr>
																														
														</table>
														</td>
															 															
														</tr>
															
															
														</table>
													</td>
												</tr>
											
											
											
											
											
											<tr>
											<td colspan="4">&nbsp;</td>
											</tr>	
											<%
										  String chk1="",chk2="",chk3="",chk4="",chk5="",chk6="";
										%>
																				
												 
											<tr>
											<td colspan="4">
											 <table border="0">
											 <tr>
											 <td class="tableTextBold">												
												List of Documents to be enclosed in <bean:write property="seperationreason" name="advanceBean"/>
												</td>
												</tr>
												</table>
											</td>										
											</tr>
										
										<logic:notEqual property="seperationreason" name="advanceBean" value="Death">				
										<logic:notEqual property="seperationreason" name="advanceBean" value="Resignation">		
										<logic:notEqual property="seperationreason" name="advanceBean" value="VRS">									
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
														<table  align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
															
															<tr>
															     <td width="15%" class="tableTextBold">1.</td>
																<td class="tableSideText" width="45%">Pension option form</td>
																
																<td><input type="checkbox" name="LOD" value="POF" <%=chk1%>></td>																
																<td>&nbsp;</td>
															</tr>
															<tr>
															 <td class="tableTextBold">2.</td>
																<td class="tableSideText">All CPF statement for NAA employees from 1989</td>
																
																<td><input type="checkbox" name="LOD" value="CPFSTNAA" <%=chk2%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">3.</td>
																<td class="tableSideText">All CPF statement for IAI employees from last three year</td>
																
																<td><input type="checkbox" name="LOD" value="CPFSTIAI" <%=chk3%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															
														</table>
													</td>
												</tr>
										
										</logic:notEqual>
										</logic:notEqual>
										</logic:notEqual>
										
										
										<logic:equal property="seperationreason" name="advanceBean" value="Death">										
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
												
												<logic:equal name="lod" property="lodInfo" value="CDC">
													<%chk4="checked";%>
												</logic:equal>
												
												
												<logic:equal name="lod" property="lodInfo" value="CPFSTNAA">
													<%chk5="checked";%>
												</logic:equal>
												
												<logic:equal name="lod" property="lodInfo" value="CPFSTIAI">
													<%chk6="checked";%>
												</logic:equal>
												

												


											</logic:iterate>
										</logic:present>
														<table  align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
															
															<tr>
															     <td width="15%" class="tableTextBold">1.</td>
																<td class="tableSideText" width="45%">Nomination form</td>
																
																<td><input type="checkbox" name="LOD" value="NOF" <%=chk1%>></td>																
																<td>&nbsp;</td>
															</tr>
															<tr>
															 <td class="tableTextBold">2.</td>
																<td class="tableSideText">Required application form of all nominees</td>
																
																<td><input type="checkbox" name="LOD" value="NEAPPFORM" <%=chk2%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">3.</td>
																<td class="tableSideText">Pension option form</td>
																
																<td><input type="checkbox" name="LOD" value="POF" <%=chk3%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">4.</td>
																<td class="tableSideText">Copy of Death certificate to be attached with the application</td>
																
																<td><input type="checkbox" name="LOD" value="CDC" <%=chk4%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">5.</td>
																<td class="tableSideText">All CPF statement for NAA employees from 1989</td>
																
																<td><input type="checkbox" name="LOD" value="CPFSTNAA" <%=chk5%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">6.</td>
																<td class="tableSideText">All CPF statement for IAI employees from last three year</td>
																
																<td><input type="checkbox" name="LOD" value="CPFSTIAI" <%=chk6%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															
														</table>
													</td>
												</tr>
										
										</logic:equal>
																													
										
										<logic:equal property="seperationreason" name="advanceBean" value="VRS">										
										<tr>
													<td colspan="4">
													<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
												<logic:equal name="lod" property="lodInfo" value="CPFSTNAA">
													<%chk1="checked";%>
												</logic:equal>



												<logic:equal name="lod" property="lodInfo" value="CPFSTIAI">
													<%chk2="checked";%>
												</logic:equal>


												<logic:equal name="lod" property="lodInfo" value="NODUES">
													<%chk3="checked";%>
												</logic:equal>
												
												<logic:equal name="lod" property="lodInfo" value="RORDER">
													<%chk4="checked";%>
												</logic:equal>
											
																								

												


											</logic:iterate>
										</logic:present>
														<table  align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
															
															<tr>
															     <td width="15%" class="tableTextBold">1.</td>
																<td class="tableSideText" width="45%">All CPF statements for NAA employees from 1989</td>
																
																<td><input type="checkbox" name="LOD" value="CPFSTNAA" <%=chk1%>></td>																
																<td>&nbsp;</td>
															</tr>
															
															<tr>
															 <td class="tableTextBold">2.</td>
																<td class="tableSideText">All CPF statements for IAAI employees from Last three Year</td>
																
																<td><input type="checkbox" name="LOD" value="CPFSTIAI" <%=chk2%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
																														
															<tr>
															 <td class="tableTextBold">3.</td>
																<td class="tableSideText">No dues certificate</td>
																
																<td><input type="checkbox" name="LOD" value="NODUES" <%=chk3%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">4.</td>
																<td class="tableSideText">Reliving Order</td>
																
																<td><input type="checkbox" name="LOD" value="RORDER" <%=chk4%>></td>																
																<td>&nbsp;</td>																
															</tr>
																													
															
														</table>
													</td>
												</tr>
										
										</logic:equal>
										
										
										
										<logic:equal property="seperationreason" name="advanceBean" value="Resignation">										
										<tr>
													<td colspan="4">
													<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
												<logic:equal name="lod" property="lodInfo" value="RAL">
													<%chk1="checked";%>
												</logic:equal>



												<logic:equal name="lod" property="lodInfo" value="CPFSTNAA">
													<%chk2="checked";%>
												</logic:equal>


												<logic:equal name="lod" property="lodInfo" value="CPFSTIAI">
													<%chk3="checked";%>
												</logic:equal>
												
												<logic:equal name="lod" property="lodInfo" value="NODUES">
													<%chk4="checked";%>
												</logic:equal>
												
												<logic:equal name="lod" property="lodInfo" value="RORDER">
													<%chk5="checked";%>
												</logic:equal>
												
												<logic:equal name="lod" property="lodInfo" value="FORM13REQ">
													<%chk6="checked";%>
												</logic:equal>
																
																								

												


											</logic:iterate>
										</logic:present>
														<table  align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
															
															<tr>
															     <td width="15%" class="tableTextBold">1.</td>
																<td class="tableSideText" width="45%">Resignation acception letters from the authority</td>
																
																<td><input type="checkbox" name="LOD" value="RAL" <%=chk1%>></td>																
																<td>&nbsp;</td>
															</tr>
															
															<tr>
															 <td class="tableTextBold">2.</td>
																<td class="tableSideText">All CPF statement for NAA employees from 1989</td>
																
																<td><input type="checkbox" name="LOD" value="CPFSTNAA" <%=chk2%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
																														
															<tr>
															 <td class="tableTextBold">3.</td>
																<td class="tableSideText">All CPF statement for IAI employees from last three year</td>
																
																<td><input type="checkbox" name="LOD" value="CPFSTIAI" <%=chk3%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">4.</td>
																<td class="tableSideText">No dues certificate</td>
																
																<td><input type="checkbox" name="LOD" value="NODUES" <%=chk4%>></td>																
																<td>&nbsp;</td>																
															</tr>
															
															<tr>
															 <td class="tableTextBold">5.</td>
																<td class="tableSideText">Reliving Order</td>
																
																<td><input type="checkbox" name="LOD" value="RORDER" <%=chk5%>></td>																
																<td>&nbsp;</td>																
															</tr>
															<logic:equal property="resignationreason" name="advanceBean" value="otherdepartment">
															<tr>
															 <td class="tableTextBold">6.</td>
																<td class="tableSideText">Please Attached copy of form 13</td>
																
																<td><input type="checkbox" name="LOD" value="FORM13REQ" <%=chk6%>></td>																
																<td>&nbsp;</td>																
															</tr>
															</logic:equal>
															
														
															
															
														</table>
													</td>
												</tr>
										
										</logic:equal>
										
										
											
									</table>									
									<table align="center">

										<tr>																			
											<td align="left" id="submit">
												<input type="button" class="butt" value="Submit" onclick="javascript:updateFinalSettlementInfo();">
											</td>
											<td align="right">

											<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn">
											<input type="button" class="butt" value="Back" onclick="javascript:frmPrsnlBack();" class="btn">
											</td>
										</tr>

									</table>
												
<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

