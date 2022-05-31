<!--
/*
  * File       : NoteSheetVerificationSearch.jsp
  * Date       : 13/04/2009
  * Author     : Suneetha V 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.info.login.LoginInfo,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>

<%@page import="com.epis.bean.advances.*"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			HttpSession httpsession = request.getSession();
			LoginInfo logInfo=new LoginInfo();
		//	 CommonUtil util = new CommonUtil();
         //int gridLength = util.gridLength();
            logInfo=(LoginInfo)httpsession.getAttribute("user");
         String userName=logInfo.getUserName();
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
	
		
		function resetMaster(frmName){		
		    document.forms[0].pfid.value="";
		    document.forms[0].employeeName.value="";		  
		    document.forms[0].sanctionno.value="";		    
		    document.forms[0].seperationreason.value="";
			url="<%=basePath%>noteSheetSearch.do?method=loadPrintSanctionOrder&frm_name="+frmName;
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
					
		function search(frmName)
		{		   		
			var url="<%=basePath%>noteSheetSearch.do?method=searchPrintSanctionOrder&frm_name="+frmName;
			//alert(url);
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();	
		}
		
		function selectSubMenuOptions(frmType,frmName,status){
			
		      var count=0;
		      var str=new Array();
		      var pensionno,nssanctionNo,status,verifiedby,sanctionDate,fsType;
		      
		      
		    if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	    
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select final settlement request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select  final settlement request');		     
		       				return false;
				 	}
		     }
		       for(var j=0;j<str.length;j++){			         
			         pensionno=str[0];
			          nssanctionNo=str[1];
			          status=str[2];			
			          verifiedby=str[3];	     
			          sanctionDate=str[4];   
			          fsType=str[5];          
			        }
			       	if(frmType=='Print'){
			       		 var flag='sanctionOrderReport1';
			        	 sanctionOrder(nssanctionNo,pensionno,status,verifiedby,flag,sanctionDate,fsType,frmName);
			        }
		   	
		}		
		

		
	  function sanctionOrder(nssanctionNo,pensionNo,status,verifiedby,flag,sanctionDate,fsType,frmName)
		{				    
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			if(frmName=='FSSO'){
			frmName='FSFormIII';
			}else if(frmName=='FSARREARSO'){
			frmName='FSArrearRecommendation';
			}
			var url="<%=basePath%>loadNoteSheet.do?method=sanctionOrder&frmPensionNo="+pensionNo+"&frmSanctionNo="+nssanctionNo+"&frmFlag="+flag+"&frmSanctionDate="+sanctionDate+"&frm_name="+frmName;
			 
			// alert(url);
		    wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
		   	winOpened = true;
			wind1.window.focus();
				
				
				
				
			
		}						
		</script>
		
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    
  </head>
  <body onload="document.forms[0].sanctionno.focus();">
   <html:form method="post" action="/noteSheetSearch.do?method=loadPrintSanctionOrder">
    <logic:equal name="advanceBean" property="frmName" value="FSSO">     
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementreportsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal>
     
     <logic:equal name="advanceBean" property="frmName" value="FSARREARSO">
     <%=ScreenUtilities.saearchHeader("loansadvances.finalsettlementarrearreportsearch.screentitle")%><table width="550" border="0" cellspacing="3" cellpadding="0"> 
     </logic:equal> 
   
  <table width="550" border="0" cellspacing="3" cellpadding="0">
		

		<tr>
			<td height="15%">
				<table align="center">
					<tr>
						<td class="tableTextBold">Application No:</td>
						<td><html:text property="sanctionno" styleClass="TextField"/></td>
						<td class="tableTextBold">PF ID:</td>
						<td><html:text property="pfid" styleClass="TextField"/></td>					
					</tr>
					<tr>
						<td class="tableTextBold">Employee Name:</td>
						<td><html:text property="employeeName" styleClass="TextField" maxlength="50"/></td>
							<td class="tableTextBold">Reason for seperation :</td>
						<td>
						<html:select property="seperationreason"  styleClass="TextField">
						<html:option value="">Select One</html:option>
					    <html:option value="Retirement">Retirement</html:option>
					    <html:option value="VRS">VRS</html:option>
						<html:option value="Death">Death</html:option>
						<html:option value="Resignation">Resignation</html:option>
						<html:option value="Termination">Termination</html:option>
						<html:option value="EarlyPensions">Option for Early Pensions</html:option>
						<html:option value="Others">Others</html:option>
						</html:select>
						</td>
												
					</tr>
					
					
					<tr>
						<td align="left">&nbsp;</td>
						<td>
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
		
               <tr>
                   <td  height="29" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                       <tr>
							
								<td width="40">
									<input type="button" value="Print" class="btn" onclick="javascript:selectSubMenuOptions('Print','<bean:write name="advanceBean" property="frmName"/>')">
								</td>

							</tr>
                        </table></td>
                         
                        </tr>
                        
                        <logic:present name="searchlist">
							<tr>							
								<td align="center" width="100%">
								
									<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="noteSheetList" sort="list" style="width:100%;height:50px" pagesize="10" name="requestScope.searchlist" requestURI="./noteSheetSearch.do?method=searchNoteSheet" >											
									
									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="NoteSheetSearch.xls" />
    								<display:setProperty name="export.pdf.filename" value="NoteSheetSearch.pdf" />
    								<display:setProperty name="export.rtf.filename" value="NoteSheetSearch.rtf" />
    								<display:column sortable="true" headerClass="GridHBg" class="GridLCells"><input type="radio" name="chk" value="<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getPensionNo()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSanctionno()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getVerifiedBy()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getTransactionStatus()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getSanctiondt()%>:<%=((AdvanceSearchBean)pageContext.getAttribute("noteSheetList")).getFsType()%>"/></display:column>
									<display:column  sortable="true"  property="sanctionno" sortable="true" title="Application No" headerClass="GridLTHCells" class="GridLTCells"/>
									<display:column  sortable="true"  property="pfid" sortable="true" title="PF ID" headerClass="GridLTHCells" class="GridLTCells"/>
    								<display:column  sortable="true"  property="employeeName"  headerClass="GridLTHCells" class="GridLTCells" title="Employee Name" />
    								<display:column  sortable="true"  property="designation" headerClass="GridLTHCells" class="GridLTCells" title="Designation"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column  sortable="true"  property="seperationreason" headerClass="GridLTHCells" class="GridLTCells" title="Seperation Reason"  decorator="com.epis.utilities.StringFormatDecorator"/>	    									
									<display:column sortable="true"   property="netcontribution" headerClass="GridLTHCells" class="GridLTCells" title="Net contribution"  decorator="com.epis.utilities.StringFormatDecorator"/>	
									<display:column  sortable="true"  property="transdt" headerClass="GridLTHCells" class="GridLTCells"  title="Form Date"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column  sortable="true"  property="paymentdt" headerClass="GridLTHCells" class="GridLTCells"  title="Payment Date"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column sortable="true"  property="sanctiondt"  title="Sanction Date" headerClass="GridLTHCells" class="GridLTCells"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column sortable="true"   property="station"  headerClass="GridLTHCells" class="GridLTCells" title="Station"  decorator="com.epis.utilities.StringFormatDecorator"/>			
									</display:table>
								</td>
							</tr>
						</logic:present>
                    
                    
             </table>     				
			<%=ScreenUtilities.searchFooter()%>
  </html:form>
  </body>
</html>
