<%@ page language="java" import="com.epis.bean.investment.QuotationBean,com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT SRC="/js/DateTime.js"></SCRIPT>
			<SCRIPT type="text/javascript">
		 function validate(){
		    
		    return true; 
		 }
   function selectCheckboxes(optiontype) { 
		
		
		
		var optionval = getCheckedValue(document.forms[0].selectall);
		var val = optionval.split("|");
		var quotationCd = val[0];
		 
		if(optiontype!='Add'){
		if(quotationCd=='')
		{
			alert("select atleast one record");
			return;
		}
		}
		
		if(optiontype=='Add'){		 
			
			 window.location.href='searchinvestapproval.do?method=showInvestApprovalAdd';
		 }
		 
		 else if(optiontype=='Approval-1')
		 {
		 	
		 	if(val[2]=='A'){
		 			alert("The Level-2 Approval has already been Done.");
		 			return false;
		 	}else if(val[1]=='A'){
		 		var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&edit=Y&updatedFlag=1";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&updatedFlag=1";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-2')
		 {
		 	
		 	if(val[1]!='A'){
		 			alert("As Level-1 approval is not Completed, Level-2 Approval cannot be made");
		 			return false;
		 	}if(val[3]=='A'){
		 			alert("The Level-3 Approval has already been Done.");
		 			return false;
		 	}else if(val[2]=='A'){
		 		var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&edit=Y&updatedFlag=2";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&updatedFlag=2";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-3')
		 {	
		 	  
		 	if(val[2]!='A'){
		 			alert("As Level-2 approval is not Completed, Level-3 Approval cannot be made");
		 			return false;
		 	}if(val[4]=='A'){
		 			alert("The Level-4 Approval has already been Done.");
		 			return false;
		 	}else if(val[3]=='A'){
		 		var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&edit=Y&updatedFlag=3";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&updatedFlag=3";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-4')
		 {
		 			    
		 	if(val[3]!='A'){
		 			alert("As Level-3 approval is not Completed, Level-4 Approval cannot be made");
		 			return false;
		 	}if(val[5]=='A'){
		 			alert("The Level-5 Approval has already been Done.");
		 			return false;
		 	}else if(val[4]=='A'){
		 		var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&edit=Y&updatedFlag=4";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&updatedFlag=4";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-5')
		 {
		 			    
		 	if(val[4]!='A'){
		 			alert("As Level-4 approval is not Completed, Level-5 Approval cannot be made");
		 			return false;
		 	}
		 	else if(val[5]=='A'){
		 			var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&edit=Y&updatedFlag=5";
				 	document.forms[0].action=url;
				 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&updatedFlag=5";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-6')
		 {
		 			    
		 	if(val[4]!='A'){
		 			alert("As Level-4 approval is not Completed, Level-6 Approval cannot be made");
		 			return false;
		 	}else if(val[6]=='A'){
		 			var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&edit=Y&updatedFlag=6";
				 	document.forms[0].action=url;
				 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&updatedFlag=6";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 
		 else if(optiontype=='Approval-7')
		 {
		 			    
		 	if(val[4]!='A'){
		 			alert("As Level-4 approval is not Completed, Level-6 Approval cannot be made");
		 			return false;
		 	}else if(val[7]=='A'){
		 			var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&edit=Y&updatedFlag=7";
				 	document.forms[0].action=url;
				 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchinvestapproval.do?method=approvalinvestApproval&quotationCd="+quotationCd+"&updatedFlag=7";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
	
		 
		 
	  }
  function clearData(){
   document.forms[0].letterNo.value="";
  }
  
   function openReport(url){
  				
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"InvestmentApproval","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
		</script>
	</head>
	
	<body>
	<html:form action="/searchinvestapproval.do?method=searchInvestApproval" onsubmit="return validate();">
		<%=ScreenUtilities.screenHeader("app.title")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.letterno" bundle="common" />:
				</td><td align="left" width="20%" >
					<input  size="12" name="letterNo" class="TextField" style="width:120 px" tabindex="1"/>
				</td>												
			</tr>	
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt"  property="Search" value="Search" />&nbsp;<html:button styleClass="butt"  property="button" value="Clear" onclick="clearData();" />
			</td>	
			</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                        <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
               <logic:present name="appList">
               <tr>
				<td  align="left" valign="top">         
				<display:table  cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="true" style="width:710px;height:50px" export="true"  sort="list" id="applist" name="requestScope.appList" requestURI="/searchinvestapproval.do?method=searchInvestApproval" pagesize="5" >
				<logic:present name="applist">
				<logic:equal value="Y" property="qdata" name="applist">
				<display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
				<img src='images/lockIcon.gif' border='0' alt='Lock' />									
				</display:column>
				</logic:equal>
					     <logic:notEqual value="Y" property="qdata" name="applist">
						            		            	            
						<display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="applist" property="quotationCd"/>|<bean:write name="applist" property="flags"/>">
					     </display:column>
					     </logic:notEqual>
					     <display:column property="approvalStatus" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Status"  />
			            <display:column property="letterNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Letter No."  />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="opendate" sortable="true" title="Open Date" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="status" sortable="true" title="Comp. Stmt. Status" />
			            <display:column headerClass="GridHBg" class="GridLTCells" media="html">
						 <img src='images/print.gif' border='0' alt='Report' onclick="openReport('/investapprovalReport.do?method=getApprovalReport&quotationCd=<%=((QuotationBean)pageContext.getAttribute("applist")).getQuotationCd()%>');" />
						</display:column>
						</logic:present>
			        	</display:table>
			        	
				</td></tr>
			
			</logic:present>
			</table>
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
