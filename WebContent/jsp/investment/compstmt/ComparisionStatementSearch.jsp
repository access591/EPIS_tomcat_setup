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
		    if(document.forms[0].letterNo.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].letterNo.value)){
		    		 alert("Enter Valid Letter No. ");
		    		 document.forms[0].letterNo.focus();
		   			  return false;
		    		 }
		     }
		    return true; 
		 }
    function selectCheckboxes(optiontype) { 
		var recordcd = getCheckedValue(document.forms[0].selectall);
		
		var letterNo=(recordcd.substring(0,(recordcd).indexOf("|",-1)));
		var opendate=(recordcd.substring((recordcd).indexOf("|",1)+2,recordcd.length));
		if(optiontype!='Add'){
		if(recordcd=='')
		{
			alert("select atleast one record");
			return;
		}
		}
		
		if(optiontype=='Add'){		 
			
			 window.location.href='searchcomparative.do?method=showComparativeAdd';
		 }
		 if(optiontype=='View Statement')
		 {
		 	//alert("the path is:"+'<%=basePath%>/addcomparative.do?method=showCompatativeStmt&mode=report&letterNo='+letterNo+'&opendate='+opendate);
		 	window.open('<%=basePath%>/addcomparative.do?method=showCompatativeStmt&mode=report&letterNo='+letterNo+'&opendate='+opendate);
		 }
	  }
  function clearData(){
   window.document.forms[0].reset();
   
  }
		</script>
	</head>
	
	<body onload="document.forms[0].letterNo.focus();">
	<html:form action="/searchcomparative.do?method=searchComparative" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("com.comstmt")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.letterno" bundle="common" />:
				</td><td width="20%" >
					<input size="12" name="letterNo" class="TextField" style="width:120 px" tabindex="1"/>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.status" bundle="common" /> :
				</td><td width="20%" >
					    <select name="status" style="width: 140px;" class="TextField" tabindex="2">
					 	<option value="">All</option>     
						<option value="S">Stmt generated</option>
                        <option value="N">Stmt Not Generated</option>                                           
                        </select>			
				</td>													
			</tr>	
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" property="Search" value="Search" tabindex="3" />&nbsp;<html:button styleClass="butt" property="button" tabindex="4" value="Clear" onclick="clearData();" />
			</td>	
			</table>
			<%=ScreenUtilities.searchSeperator()%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="comparativeList">
                        <tr>
                          <td  align="left" valign="top">
				<display:table cellpadding="0" cellspacing="0"  class="GridBorder"  keepStatus="true" style="width:710px;height:50px" export="true"  sort="list" id="comparative" name="requestScope.comparativeList" requestURI="/searchcomparative.do?method=searchComparative" pagesize="5" >
					<display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="comparative" property="letterNo"/>|<bean:write name="comparative" property="opendate"/>"  />
					     </display:column>	        		            	            
			            <display:column property="letterNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Letter No."  />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="opendate" sortable="true" title="Open Date" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="status" sortable="true" title="Comp. Stmt. Status" />
			        	</display:table>
				</td></tr>
			</logic:present>
			</table>
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
