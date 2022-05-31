<!--
/*
  * File       : SearchToDeleteRecord.jsp
  * Date       : 16/11/2010
  * Author     : Radha 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.CommonUtil"%>
<%@page import="com.epis.bean.advances.*"%>
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
	<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
	<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
	
    
		<script type="text/javascript">		
		 var appArr1='',appArr2='';
		 var frmName="FSDelete";
		 
		function search(frmName)
		{  		
		if (document.forms[0].requestType.selectedIndex<1)
			{
			alert('Please Select RequestType');
				document.forms[0].requestType.focus();
				return false;
			}
		 	    
			var url="<%=basePath%>noteSheetSearch.do?method=searchRecordsToDelete&frm_name="+frmName;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();	
		}
		
		function resetMaster(frmName){			
		
		    document.forms[0].pfid.value="";		  
		    document.forms[0].sanctionno.value="";		   
		    document.forms[0].requestType.value="";		  
			url="<%=basePath%>noteSheetSearch.do?method=loadSearchtoDeleteRecords&frm_name="+frmName;
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		 
		function deleteRecord(pensionno,sanctionno,advanceTransID,advanceType,purposeType,fsType,verifiedBy,verifiedByStatus,finalTransStatus)
		{		
		//alert(verifiedByStatus+'---'+finalTransStatus);
		
		 if((verifiedByStatus=='Completed') || (finalTransStatus=='Completed')){
			alert('You can not Delete the details.');	
			}else{
		    var chkstatus= confirm("Do you really want to delete  Request?");
			
			 if (chkstatus== true)
			 {		 
			  var frmName='FSDelete';
			    var url="<%=basePath%>loadNoteSheet.do?method=deleteRecords&frmPensionNo="+pensionno+"&frmNSSanctionNo="+sanctionno+"&frmAdvanceTransID="+advanceTransID+"&frmAdvanceType="+advanceType+"&frmPurposeType="+purposeType+"&frmFSType="+fsType+"&verifiedBy="+verifiedBy+"&frm_name="+frmName;
			    //alert(url);
			    document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();		
			 }	 		    
		
		}
		} 
		 
function anotherField(){		 

 if((document.forms[0].requestType.value=='FinalSettlement')){
	  finalsettlement.style.display="block";
	  advances.style.display="none";
	 
  }else{
	  finalsettlement.style.display="none";
	  advances.style.display="block";   
 	}
		
}

function onloadDts(){
 
var  requestType=document.forms[0].requestType.value;
if(requestType=='FinalSettlement'){
 finalsettlement.style.display="block";
}else if((requestType=='CPF') || (requestType=='PFW')){
 advances.style.display="block";   
}else{
finalsettlement.style.display="none";
	  advances.style.display="none";   
}

}
		</script>
		
  </head>
     
  <body onload="onloadDts();"> 
  <html:form method="post" action="/noteSheetSearch.do?method=loadNoteSheetSearchForm">
      <%=ScreenUtilities.saearchHeader("Search To Delete Records")%>
  <table width="550" border="0" cellspacing="3" cellpadding="0">
		<tr>
			<td height="15%">
				<table align="center">
				
				 
				
				<tr>
						<td class="tableTextBold">Request Type<font color=red>*</font></td>
						<td>
						<html:select property="requestType"  styleClass="TextField" tabindex="1" onchange="anotherField();">
						<html:option value="">Select One</html:option>					   
					    <html:option value="CPF">CPF</html:option>
						<html:option value="PFW">PFW</html:option>
						<html:option value="FinalSettlement">FinalSettlement</html:option>						 
						</html:select>
						</td>										 
						
						<td class="tableTextBold">PF ID:   </td>
						<td><html:text property="pfid" styleClass="TextField" tabindex="2"/></td>										
					 
				</tr>
				
				
				<tr>
				<td colspan="2" id="finalsettlement" style="display: none;">											
												<table  align="center" border="0">
												<tr>
												<td class="tableTextBold" width="54%">
												Application No:&nbsp;
												</td>
												<td>											
												<html:text property="sanctionno"  styleClass="TextField" tabindex="3" />
												</td>
												</tr>											
												</table>											
											</td>
											
											
											
				<td colspan="2" id="advances" style="display: none;">											
												<table  align="center" border="0">
												<tr>
												<td class="tableTextBold" width="54%">
												Advance TransId:&nbsp;
												</td>
												<td>											
												<html:text property="advanceTransID"  styleClass="TextField" tabindex="3" />								 
												</td>
												</tr>											
												</table>											
											</td>
				</tr>
				
				 
			 
					 
					 
					<tr>
						
						<td colspan="4" align="center">
								<input type="button" class="butt" value="Search" class="btn" onclick="javascript:search('<bean:write name="advanceBean" property="frmName"/>')" >
								<input type="button" class="butt" value="Reset" onclick="javascript:resetMaster('<bean:write name="advanceBean" property="frmName"/>')" class="btn">
								<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
						</td>
						
					</tr>
			</table>
		</td>
	</tr>
</table>
			
		
			
				
<%=ScreenUtilities.searchSeperator()%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		
                         <logic:present name="searchlist">
							<tr>							
								<td align="center" width="100%">
								
									<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="noteSheetList" sort="list" style="width:100%;height:50px" pagesize="10" name="requestScope.searchlist" requestURI="./noteSheetSearch.do?method=searchRecordsToDelete" >											
									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="NoteSheetSearch.xls" />
    								<display:setProperty name="export.pdf.filename" value="NoteSheetSearch.pdf" />
    								<display:setProperty name="export.rtf.filename" value="NoteSheetSearch.rtf" />
    								
    								<logic:equal name="AdvanceSearchBean" property="requestType" value="FinalSettlement">
    								<display:column  sortable="true"  property="sanctionno" sortable="true" title="Application No" headerClass="GridLTHCells" class="GridLTCells"/>
    								</logic:equal>
    								<logic:notEqual  name="AdvanceSearchBean"  property="requestType" value="FinalSettlement">
    								<display:column  sortable="true"  property="advanceTransIDDec" sortable="true" title="Advance TransID" headerClass="GridLTHCells" class="GridLTCells"/>
    								</logic:notEqual>
									<display:column  sortable="true"  property="pfid" sortable="true" title="PF ID" headerClass="GridLTHCells" class="GridLTCells"/>
    								<display:column  sortable="true"  property="employeeName"  headerClass="GridLTHCells" class="GridLTCells" title="Employee Name" />
    								 
    								
    								<logic:equal  name="AdvanceSearchBean"  property="requestType" value="FinalSettlement">
    								<display:column sortable="true"   property="station"  headerClass="GridLTHCells" class="GridLTCells" title="Station"  decorator="com.epis.utilities.StringFormatDecorator"/>			    								
    								<display:column  sortable="true"  property="seperationreason" headerClass="GridLTHCells" class="GridLTCells" title="Seperation Reason"  decorator="com.epis.utilities.StringFormatDecorator"/>	    									
    								<display:column  sortable="true"  property="fsType" headerClass="GridLTHCells" class="GridLTCells"  title="FinalSettlement Type"  decorator="com.epis.utilities.StringFormatDecorator"/>      
    								<display:column  sortable="true"  property="transdt" headerClass="GridLTHCells" class="GridLTCells"  title="Form Date"  decorator="com.epis.utilities.StringFormatDecorator"/>   
    								<display:column  sortable="true"  property="verifiedByStatus" headerClass="GridLTHCells" class="GridLTCells"  title="Status"  decorator="com.epis.utilities.StringFormatDecorator"/>   
									</logic:equal>
									
									<logic:notEqual  name="AdvanceSearchBean"  property="requestType" value="FinalSettlement">
									<display:column  sortable="true"  property="purposeType" headerClass="GridLTHCells" class="GridLTCells"  title="Advance Type"  decorator="com.epis.utilities.StringFormatDecorator"/>   
									<display:column  sortable="true"  property="transMnthYear" headerClass="GridLTHCells" class="GridLTCells"  title="Form Date"  decorator="com.epis.utilities.StringFormatDecorator"/>   
									<display:column  sortable="true"  property="finalTrnStatus" headerClass="GridLTHCells" class="GridLTCells"  title="Status"  decorator="com.epis.utilities.StringFormatDecorator"/>   
									</logic:notEqual>
									
									<display:column sortable="true" headerClass="GridLTHCells" class="GridLTCells"><img src="<%=basePath%>images/deleteGridIcon.gif" border="no" alt="Delete" onclick="javascript:deleteRecord('<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getPensionNo()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSanctionno()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getAdvanceTransID()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getAdvanceType()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getPurposeType()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getFsType()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getVerifiedBy()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getVerifiedByStatus()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getFinalTrnStatus()%>','<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getKeyNo()%>');"/></display:column>
									</display:table>
								</td>
							</tr>
							</logic:present>
                    
                    
             </table>    
             <%=ScreenUtilities.searchFooter()%>
  </html:form>
  </body>
</html>
