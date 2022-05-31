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
			String postingFlag ="";
			if(request.getAttribute("postingFlag")!=null){
		  postingFlag=(String)request.getAttribute("postingFlag");
		}
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
	 
function save(frmName){ 

	var url ="",postingregion,postingstation,postingflag;
	postingregion= document.forms[0].posting_region.value; 
	postingstation=document.forms[0].posting_station.value; 	
	postingflag = document.forms[0].postingFlag.value; 
	// alert(postingflag);
	
	if(postingregion=="NO-SELECT"){
	alert("Please select Region");
	return false;
	}
	
	if(document.forms[0].postingFlag.checked==true){
	 alert('Your selected Posting Details are going to be Saved');
	 url="<%=basePath%>editNoteSheet.do?method=addEmployeePostingDetails&frm_name="+frmName+"&postingregion="+postingregion+"&postingstation="+postingstation;
		
	}
	if(document.forms[0].postingFlag.checked==false){
	 alert('Your selected Posting Details are going to be Deleted');
	 url="<%=basePath%>editNoteSheet.do?method=deleteEmployeePostingDetails&frm_name="+frmName;
		
	}
		 	    // alert(url);		
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit(); 
	}  
	
	function  deleteRecords(nomineeserialno,frmName,screenFlag,srcFrmName,arrearType){ 
	var url,frmName='FSBankDet',nssanctionno='',pensionno='';
	  var str=new Array();
	nssanctionno= document.forms[0].nssanctionno.value; 
	pensionno=document.forms[0].pensionNo.value; 
	//alert(nomineeserialno); 
		 	url="<%=basePath%>editNoteSheet.do?method=DeleteNomineeBankDetails&pfid="+pensionno+"&nssanctionno="+nssanctionno+"&nomineeserialno="+nomineeserialno+"&frm_name="+frmName+"&frmNewScreen="+screenFlag+"&srcFrmName="+srcFrmName+"&frm_ArrearType="+arrearType;
		  // alert(url);		
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
  
	}
	
	function exit(){
	var url='';
	var url,frmName="FSArrearRecommendation",nssanctionno='',pensionno='';
	  
	nssanctionno= document.forms[0].nssanctionno.value; 
	pensionno=document.forms[0].pensionNo.value; 
	 
	url="<%=basePath%>noteSheetSearch.do?method=searchFinalSettlementArrearProcess&frm_name="+frmName;
	  
	  
	 	//alert(url);
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();	
	
	}
	 function getStations(){ 
   
      var url = "<%=basePath%>editNoteSheet.do?method=loadStations";
	
	  var index=document.forms[0].posting_region.selectedIndex.value;
       var selectedIndex=document.forms[0].posting_region.options.selectedIndex;
	   var searchregion=document.forms[0].posting_region[selectedIndex].value;  
	 
	   
    var ajaxRequest = new Ajax.Request(url, {
	    method:       'get', 
	    parameters: {frm_region: searchregion,frm_loadfrmname: 'finsttlment'},
	    asynchronous: true,
	    onSuccess:   showResponse
	});    

 
 }
 
 function getNodeValue(obj,tag){    
  if(obj.getElementsByTagName(tag)[0].firstChild) {
		return unescape(obj.getElementsByTagName(tag)[0].firstChild.nodeValue);
	}else{
	  return "";
	}
 }
  function showResponse(xmlHttpRequest) {  	 
  
   
	 var obj1 = document.getElementById("posting_station");
	obj1.options.length = 0;
	 
	 
	  var xm = xmlHttpRequest.responseXML;    
     var stype = xmlHttpRequest.responseXML.getElementsByTagName('Detail');
      
   
       if(stype.length>=1){
	       for(var i=0;i<stype.length;i++){	      
	         var val = getNodeValue(stype[i],'Station');	      
	 		var nm = getNodeValue(stype[i],'Station'); 	 		
	 		obj1.options[obj1.options.length] = new Option(nm,val);
	       }
       }else{
       obj1.options[obj1.options.length] = new Option("---No Stations--","");
       }
	  
}
	 
	function loadDet(){
	var postingflag='<%=postingFlag%>';
	 
	if(postingflag=='Y'){
	document.forms[0].postingFlag.checked=true;
	} 
	
	}
	</script>
		
  </head>
  
  	<%  
  	  
	HashMap hashmap1=new HashMap();
Iterator regionIterator=null;
 String reg="";	 
  	 
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap1=(HashMap)request.getAttribute("regionHashmap");
  	Set keys1 = hashmap1.keySet();
	regionIterator = keys1.iterator();
  	
  	}
  	%>
  <body  class="bodybackground" onload="loadDet();">
  <html:form method="post" action="/editNoteSheet.do?method=addEmployeePostingDetails">
      <%=ScreenUtilities.saearchHeader("Employee Posting Details[Add]")%>
      <table width="350" border="0" cellspacing="0" cellpadding="0"> 
    	<tr>
			<td height="15%" align="center">
				<table align="center">
					<tr>
						 
						<td class="tableTextBold"   align="center">Application No&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   </td> 
						<td class="tableTextBold"   align="center">PF ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td> 
						<td class="tableTextBold" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Employee Name&nbsp;&nbsp;&nbsp;</td> 
						<td class="tableTextBold" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Region&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>						 
						<td class="tableTextBold" align="center">Station&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td> 
					</tr>
					<tr>
					
					    
					  	<td align="left"><html:text property="pensionNo"  styleClass="TextField" /> </td>
						<td align="left"> <html:text property="nssanctionno"  styleClass="TextField"/> </td>
						<td align="center"> <html:text property="employeeName"  styleClass="TextField"/> </td> 
						<td align="left"> <html:text property="region"   styleClass="TextField"/> </td> 
						<td align="center"> <html:text property="station"   styleClass="TextField"/> </td>	
					</tr>
					 
					</table> 
				 </td>
			</tr>
		 
			<tr>
						<td colspan="2" align="left">
							<table>						
							<tr>
								<td class="tableTextBold">Posting Details <input type="checkbox"  name="postingFlag"   /></td>
								  
							</tr>
							 
							</table> 
						</td> 
					</tr>
			 <%-- </logic:equal> --%>
		 
			 
			 
			 		<tr>
			 		    
						<td >
							<table>						
							<tr>
							 <td class="tableTextBold" align="center">&nbsp;</td>
								<td  class="tableTextBold" align="center">&nbsp;&nbsp;&nbsp;Posting Region&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td class="tableTextBold"    align="center">&nbsp;&nbsp;&nbsp;Posting Station&nbsp;&nbsp;&nbsp;</td>
								 
							</tr>
							<tr>
							  <td class="tableTextBold" align="center">&nbsp;</td>
								<td align="center">
										 
							 	<select name="posting_region" class="TextField" style="width:119px" onchange="getStations();">
								         <option value='NO-SELECT'>[Select One]</option>   
								            
								             <%while (regionIterator.hasNext()) {
													boolean exist = false;
											reg = hashmap1.get(regionIterator.next()).toString();%> 

									   <logic:equal property="postingRegion" name="employeeInfo" value="<%=reg%>">
									   <%
										  exist = true; 
									   %>
									    </logic:equal>
									  <%
										 									  
									  	if (exist) {
									  
								   %>
								     <option value="<bean:write property="postingRegion" name="employeeInfo"/>" <% out.println("selected");%>><bean:write property="postingRegion" name="employeeInfo"/></option>								
								   <% }else{
								     
								   %>
								     <option value="<%=reg%>"><%=reg%></option>
								
								<%} }%>	
								             
							 </select>
						  
								     </td>
						      
					<td align="center">
					 		
						<SELECT NAME="posting_station" class="TextField"> 
							<option value='NO-SELECT' >
								[Select One]
								<logic:notEqual property="postingStation" name="employeeInfo" value="---">	
								<option value='<bean:write property="postingStation" name="employeeInfo"/>' Selected>
								<bean:write property="postingStation" name="employeeInfo"/>
							</option>
							</logic:notEqual>
						 	 
						</SELECT>
						 
					</td> 		
								 </tr> 
							</table> 
						</td> 
						<td>&nbsp;</td>
					</tr>
			<tr><td>&nbsp;</td></tr>		
	<tr>
           <td    height="29" align="center" valign="top"  align="center">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                        <td  align="left"><input type="button" class="butt" value=" Save " class="btn" onclick="javascript:save('<bean:write name="advanceBean" property="frmName"/>');" > &nbsp;&nbsp;&nbsp;&nbsp;
					     <input type="button"  value="  Back " class="butt" onclick="javascript:exit();"/>
					    </td>						
							  
							<td>&nbsp;</td>
							 
                        </tr>
                        </table>
          </td>     
                         
                        </tr>		 
			</table>
		 
			
	  
				

   <%=ScreenUtilities.searchFooter()%>
  </html:form>

