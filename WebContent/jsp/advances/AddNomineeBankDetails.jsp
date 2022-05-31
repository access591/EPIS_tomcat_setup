<!--
/*
  * File       : AddNomineeBankDetails.jsp
  * Date       : 01/04/2011
  * Author     : Radha P 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.lang.*,java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.CommonUtil"%>
<%@page import="com.epis.bean.advances.*"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			
			String srcName=(String)request.getAttribute("srcFrmName");
			System.out.println("=====jap======"+srcName);
		
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display"%>

<html>
  <head>
   
    
    <title>AAI</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/prototype.js"></script>
	
	
	<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
	<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
	
	 <script type="text/javascript">		
	 
function save(frmName,screenFlag,srcFrmName,arrearType,advanceType){ 
 //alert(screenFlag);
	var url,nomineeserialno='',nssanctionno='',pensionno='',seperationreason='',srcFrmName='',entryType='',empName='';
	var nomineeName='',nomineeId='';
	var bankname,empname,accno,rtgscode,paymentinfoflag="N"; 
	 	
	pensionno=document.forms[0].pensionNo.value; 
	seperationreason=document.forms[0].seperationreason.value;
	empName=document.forms[0].employeeName.value; 
	
	 srcFrmName ='<%=srcName%>';	
	 //alert(srcFrmName);
	if(srcFrmName=='FSArrearProcess'  ||  srcFrmName=='FSArrearRecommendation'  || srcFrmName=='FSFormII' || srcFrmName=='FSFormIII'){
	transid=document.forms[0].nssanctionno.value;
	entryType="FS/FSA";
	}else{
	transid=document.forms[0].advanceTransID.value;  
	entryType="CPF/PFW";
	}
	
	 bankname=document.forms[0].bankempname.value;
	 empname=document.forms[0].bankname.value;
	 accno=document.forms[0].banksavingaccno.value;
	 rtgscode=document.forms[0].bankemprtgsneftcode.value;
	
	if(bankname!=''||empname!=''||accno!=''||rtgscode!=''){
		 	paymentinfoflag='Y';
		 	}
	
	if(seperationreason=='Death'){
	nomineeserialno=document.forms[0].nomineename.value; 
	nomineeId=document.forms[0].nomineename.value;
	nomineeName=document.forms[0].nomineename[document.forms[0].nomineename.options.selectedIndex].text;
	 url="<%=basePath%>editNoteSheet.do?method=addNomineeBankDetails&pfid="+pensionno+"&transid="+transid+"&nomineeserialno="+nomineeserialno+"&frm_name="+frmName+"&frmNewScreen="+screenFlag+"&srcFrmName="+srcFrmName+"&frm_ArrearType="+arrearType;
		  }else{
		  if(entryType=="FS/FSA"){
		  url="<%=basePath%>editNoteSheet.do?method=addNomineeBankDetails&pfid="+pensionno+"&transid="+transid+"&frm_name="+frmName+"&frmNewScreen="+screenFlag+"&srcFrmName="+srcFrmName+"&frm_ArrearType="+arrearType+"&paymentinfoflag="+paymentinfoflag;
	      }else{
		  url="<%=basePath%>editNoteSheet.do?method=addNomineeBankDetails&pfid="+pensionno+"&transid="+transid+"&frm_name="+frmName+"&frmNewScreen="+screenFlag+"&srcFrmName="+srcFrmName+"&paymentinfoflag="+paymentinfoflag+"&frm_AdvanceType="+advanceType;
	      }
	  }
		 // alert(url);		
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit(); 
	}  
	
	function  deleteRecords(nomineeserialno,frmName,screenFlag,srcFrmName,arrearType){ 
	var url,frmName='BankDet',nssanctionno='',pensionno='',seperationreason='',empName='';
	  var str=new Array();
	nssanctionno= document.forms[0].nssanctionno.value; 
	pensionno=document.forms[0].pensionNo.value; 
	empName=document.forms[0].employeeName.value; 
	seperationreason=document.forms[0].seperationreason.value;
	//alert(nomineeserialno); 
		 	url="<%=basePath%>editNoteSheet.do?method=DeleteNomineeBankDetails&pfid="+pensionno+"&nssanctionno="+nssanctionno+"&nomineeserialno="+nomineeserialno+"&frm_name="+frmName+"&frmNewScreen="+screenFlag+"&srcFrmName="+srcFrmName+"&frm_ArrearType="+arrearType+"&seperationreason="+seperationreason+"&empName="+empName;
		    //alert(url);		
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
  
	}
	
	function exit(srcFrmName,screenFlag,arrearType,advanceType){
	var url=''; 
	if(srcFrmName=='FSArrearProcess'  || srcFrmName=='FSArrearRecommendation'|| srcFrmName=='FSFormII' || srcFrmName=='FSFormIII'){
	if(arrearType=='NON-ARREAR'){
	url="<%=basePath%>noteSheetSearch.do?method=searchNoteSheet&frm_name="+srcFrmName; 
	}else{
	url="<%=basePath%>noteSheetSearch.do?method=searchFinalSettlementArrearProcess&frm_name="+srcFrmName; 
	} 
	}else{
	 if(advanceType=='CPF'){
	url="<%=basePath%>loadAdvanceCPFForm.do?method=loadCPFFormApproval&frm_name="+srcFrmName;	 
	}else{
	url="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=form4";
	}  
	}  
	 	// alert(url);
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();	
	
	}
	
	 
	</script>
		
  </head>
  
  	<% Map map = new LinkedHashMap();
  	Iterator nomineeIterator=null;  	 
  	  String nomineeID="",nomineeNM=""; 
  	  
	if(request.getAttribute("nomineeHashmap")!=null){
  		map=(Map)request.getAttribute("nomineeHashmap");
  	  	}
  	 	Set nomineeSet=map.entrySet();
	  	nomineeIterator=nomineeSet.iterator();
  	%>
  <body  class="bodybackground">
  <html:form method="post" action="/editNoteSheet.do?method=addNomineeBankDetails">
  
  <logic:notEqual name="employeeInfo" property="seperationreason" value="Death"> 
  <%=ScreenUtilities.saearchHeader("Employee Bank Details[Add]")%>
      <table width="350" border="0" cellspacing="0" cellpadding="0"> 
    	<tr>
			<td height="15%" align="center">
				<table align="center">
					<tr>
						 
						<td class="tableTextBold" nowrap="nowrap">PF ID:   </td>
					
						<td>
						<html:text property="pensionNo"  styleClass="TextField" readonly="true"/>
						<html:hidden property="nssanctionno" />
						<html:hidden property="advanceTransID" />
					 	</td>	
						<td class="tableTextBold" nowrap="nowrap">Employee Name:</td>
						<td>
						<html:text property="employeeName" name="employeeInfo" styleClass="TextField" readonly="true"/>
						</td>	
						<td class="tableTextBold" nowrap="nowrap">Seperation Reason</td>
						<td>						 
						<html:text property="seperationreason" name="employeeInfo"  readonly="true"  styleClass="TextField"/>
						</td>		
					</tr>
					 
					</table> 
				 </td>
				 </tr>      
					<tr>
						<td colspan="4" align="center">
							<table>						
							<tr> 
								<td class="tableTextBold"  align="center">Name of Employee</td>
								<td class="tableTextBold"  align="center">Name Of Bank</td>
							 	<td class="tableTextBold" align="center">Bank A/c No</td>
							 	<td class="tableTextBold"  align="center">IFSC Code Of Bank</td>
							</tr>
							<tr> 								
								<td  align="center"><html:text styleClass="TextField"  property="bankempname" name="empBankInfo" /></td>
								<td  align="center"><html:text styleClass="TextField"  property="bankname"  name="empBankInfo" /></td>
								<td  align="center"><html:text styleClass="TextField"  property="banksavingaccno" name="empBankInfo"/></td>
								<td  align="center"><html:text styleClass="TextField"  property="bankemprtgsneftcode" name="empBankInfo"/></td>
								
								</tr>
								<tr><td>&nbsp;</td></tr>      
							<tr>
							<td colspan="6"  align="center">
							<input type="button" class="butt" value="Save"  onclick="javascript:save('<bean:write name="advanceBean" property="frmName"/>','<bean:write   name="seperateScreen" scope="request"/>','<bean:write name="srcFrmName"  scope="request"/>','<bean:write name="arrearType"  scope="request"/>','<bean:write name="advanceType"  scope="request"/>');" >
				  			<input type="button"  value="  Back  " class="butt" onclick="javascript:exit('<bean:write name="srcFrmName"  scope="request"/>','<bean:write   name="seperateScreen" scope="request"/>','<bean:write name="arrearType"  scope="request"/>','<bean:write name="advanceType"  scope="request"/>');"/>
							</td>	
				   			</tr>  
							</table> 
						</td> 
					</tr>
					
					
			</table> 
	  <%=ScreenUtilities.searchFooter()%>
 </logic:notEqual>
 <logic:equal name="employeeInfo" property="seperationreason" value="Death"> 
  
      <%=ScreenUtilities.saearchHeader("Nominee Bank Details[Add]")%>
      <table width="350" border="0" cellspacing="0" cellpadding="0"> 
    	<tr>
			<td height="15%" align="center">
				<table align="center">
					<tr>
						 
						<td class="tableTextBold">PF ID:   </td>
					
						<td>
						<html:text property="pensionNo"  styleClass="TextField"/>
						<html:hidden property="nssanctionno" />
						<html:hidden property="region"/>
						</td>	
						<td class="tableTextBold">Employee Name:</td>
						<td>
						<html:text property="employeeName"  name="employeeInfo" styleClass="TextField"/>
						</td>	
						<td class="tableTextBold">Seperation Reason</td>
						<td>						 
						<html:text property="seperationreason"  name="employeeInfo"  styleClass="TextField"/>
						</td>		
					</tr>
					 
					</table> 
				 </td>
				 
					<tr>
						<td colspan="4" align="center">
							<table>						
							<tr>
								<td class="tableTextBold">Nominees</td>
								<td class="tableTextBold"  align="center">Name of Nominee</td>
								<td class="tableTextBold"  align="center" >Name Of Bank</td>
							 	<td class="tableTextBold" align="center"  >Bank A/c No</td>
							 	<td class="tableTextBold"  align="center"  >IFSC Code Of Bank</td>
							</tr>
							<tr>
							<td>
							<select name="nomineename" style="width:120px" class="TextField"  >
						 
						<%while (nomineeIterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) nomineeIterator.next();
				nomineeID = mapEntry.getKey().toString();
				nomineeNM = mapEntry.getValue().toString();
		 
				%>
				<option value="<%=nomineeID%>"><%=nomineeNM%></option>
							<%}%>				    
						</select>
								</td>									
								<td  align="center"><html:text styleClass="TextField"  property="bankempname"  /></td>
								<td  align="center"><html:text styleClass="TextField"  property="bankname" /></td>
								<td  align="center"><html:text styleClass="TextField"  property="banksavingaccno" /></td>
								<td  align="center"><html:text styleClass="TextField"  property="bankemprtgsneftcode" /></td>
								<td  align="center"><input type="button" class="butt" value="Save" class="btn" onclick="javascript:save('<bean:write name="advanceBean" property="frmName"/>','<bean:write   name="seperateScreen" scope="request"/>','<bean:write name="srcFrmName"  scope="request"/>','<bean:write name="arrearType"  scope="request"/>');" ></td>
								</tr> 
							</table> 
						</td> 
					</tr>
					
					 
			</table>
		 
			
	  
				
<%=ScreenUtilities.searchSeperator()%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">	
  <tr>
           <td  height="29" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>  
							  <td>
								<input type="button"  value="  Back  " class="btn" onclick="javascript:exit('<bean:write name="srcFrmName"  scope="request"/>','<bean:write   name="seperateScreen" scope="request"/>','<bean:write name="arrearType"  scope="request"/>');"/>
							</td>						
							  
							<td>&nbsp;</td>
							 
                        </tr>
                        </table></td>     
                         
                        </tr>	
						<logic:present name="nomineeSearchList">
							<tr>							
								<td align="center" width="80%">
								
									<display:table cellspacing="0" cellpadding="0"   class="GridBorder" sort="list" id="nomineeList" sort="list" style="width:100%;height:50px" pagesize="10" name="requestScope.nomineeSearchList" requestURI="./noteSheetSearch.do?method=searchNomineeBankDetails">					
									<display:column  sortable="true"  property="bankempname"  headerClass="GridLTHCells" class="GridLTCells" title="Employee Name" />
    								<display:column  sortable="true"  property="bankname" headerClass="GridLTHCells" class="GridLTCells" title="Bank Name"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column  sortable="true"  property="banksavingaccno"  headerClass="GridLTHCells" class="GridLTCells" title="Account No"  decorator="com.epis.utilities.StringFormatDecorator"/>			
    								<display:column  sortable="true"  property="bankemprtgsneftcode" headerClass="GridLTHCells" class="GridLTCells" title="IRFC Code"  decorator="com.epis.utilities.StringFormatDecorator"/>	    									
									<display:column sortable="true" headerClass="GridLTHCells" class="GridLTCells" >
									<a href='#' onclick="javascript:deleteRecords('<%=((EmpBankMaster)pageContext.getAttribute("nomineeList")).getNomineeSerialNo()%>','<bean:write name="advanceBean" property="frmName"/>','<bean:write   name="seperateScreen" scope="request"/>','<bean:write name="srcFrmName"  scope="request"/>','<bean:write name="arrearType"  scope="request"/>')"><img src="<%=basePath%>/images/cancelIcon.gif"  border='0' alt='Delete' /> </a>
									 </display:column>													
									 
									</display:table>
								</td>
							</tr>
						</logic:present>
					 </table>
					 <%=ScreenUtilities.searchFooter()%>
					 
	 
				
 </logic:equal>
  </html:form>

