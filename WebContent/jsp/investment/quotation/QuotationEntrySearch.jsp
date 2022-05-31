
<%@ page language="java" import="com.epis.bean.investment.QuotationBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator"  pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
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
		<title>Investemnt-Quoation Data Submission</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].letterNo.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].letterNo.value)){
		    		 alert("Enter Valid letterNo ");
		    		 document.forms[0].letterNo.focus();
		   			  return false;
		    		 }
		    
		     }
		    return true; 
		}
	  
	    function selectCheckboxes(optiontype) { 
		
		var recordcd = getCheckedValue(document.forms[0].selectall);
		
	   if(optiontype!='Add'){
		if(recordcd=='')
		{
			alert("select atleast one record");
			return;
		}
		}
		if(optiontype=='Add'){		 
			
			 window.location.href='searchQuotation.do?method=showQuotationAdd';
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchQuotation.do?method=deleteQuotation&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchQuotation.do?method=showEditQuotation&quotationcode="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
	  
	   function openReport(url){
  				
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"QuotationEntry","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
  
	  function clearData(){
	  		document.forms[0].letterNo.value='';
   			document.forms[0].securityCategory.value='';
   			document.forms[0].trustName.value='';
   			document.forms[0].securityName.value='';
   			document.forms[0].letterNo.focus();
	   
	  }
	</script>
	</head>
	<body onload="document.forms[0].letterNo.focus();">
	<html:form action="/searchQuotation.do?method=searchQuotation" >
	<%=ScreenUtilities.screenHeader("sq.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
			<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="sq.letterno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="letterNo" tabindex="1" styleClass="TextField" style="width:120 px" />
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.securityname" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="securityName" tabindex="2" styleClass="TextField" style="width:120 px" />
					
				</td>		
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="sq.trustname" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:select property="trustName" tabindex="3" style="width: 120px;" styleClass="TextField" >          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="trustList" property="trustType" labelProperty="trustType"/>
                    </html:select>					
				</td>			
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="sq.securitycategory" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:select property="securityCategory" tabindex="4" style="width: 120px;" styleClass="TextField" >         
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="securityList" property="categoryId" labelProperty="categoryCd"/>
                    </html:select>
				</td>							
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" tabindex="5" ><bean:message key="button.search" bundle="common"/></html:submit>&nbsp;<html:button styleClass="butt" tabindex="6" property="Clear"  onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button>
			
			</td>		
			</tr>
			</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,7)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td></tr>
				<logic:present name="quotationList">
				<tr>
                          <td  align="left" valign="top">
					   <display:table cellpadding="0" cellspacing="0"  class="GridBorder"  keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="quotationTable" name="requestScope.quotationList" requestURI="/searchQuotation.do?method=searchQuotation" pagesize="5" >		            		            	            
					   <logic:present name="quotationTable">
						<logic:equal value="Y" property="ytmVerified" name="quotationTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <img src='images/lockIcon.gif' border='0' alt='Lock' />									
					     </display:column>
					     </logic:equal>
					     <logic:notEqual value="Y" property="ytmVerified" name="quotationTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="quotationTable" property="quotationCd"/>">
					     </display:column>
					     </logic:notEqual>
			            <display:column property="letterNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Letter No."  />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="securityName" sortable="true" title="Security Name" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="ytm" sortable="true" title="YTM (%)" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="maturityDate" sortable="true" title="Maturity Date" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="trustName" sortable="true" title="Trust" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="securityCd" sortable="true" title="Category" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="faceValue" sortable="true" title="Interest Rate"/>
			            <display:column headerClass="GridHBg" class="GridLTCells" media="html">
						 <img src='images/print.gif' border='0' alt='Report' onclick="openReport('/searchQuotation.do?method=generateQuotationReport&&quotationCd=<%=((QuotationBean)pageContext.getAttribute("quotationTable")).getQuotationCd()%>');" />
						</display:column>
			            </logic:present>
			        	</display:table>
			      </td></tr>
		   	</logic:present>
			</table>
		<%=ScreenUtilities.searchFooter()%></html:form>
	</body>
</html>
