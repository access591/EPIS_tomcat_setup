<%@ page language="java" import="com.epis.bean.investment.SecurityCategoryBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>
<%@ taglib uri="/tags-display" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>My JSP 'SecurityCategorySearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].categoryCd.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].categoryCd.value)){
		    		 alert("Enter Valid Security Category Type ");
		    		 document.forms[0].categoryCd.focus();
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
			
			 window.location.href='searchSCategory.do?method=showSecurityCategoryAdd';
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchSCategory.do?method=deleteSecurityCategory&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchSCategory.do?method=showEditSecurityCategory&categoryid="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
  
  
  function clearData(){
   document.forms[0].categoryCd.value='';
    document.forms[0].categoryCd.focus();
   
  }
		</script>
	</head>
	<body onload="document.forms[0].categoryCd.focus();">
		<html:form action="/searchSCategory.do?method=searchSCategory" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("category.newTitle")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="category.categorycd" bundle="common" />
						:
					</td>
					<td width="20%" align=left>
						<html:text size="12" property="categoryCd" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="7" />
					</td>
				</tr>
				<tr>
					<td colspan=4 align=center>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=4 align=center>
						<html:submit value="Search" tabindex="2"  styleClass="butt"/>&nbsp;
						<html:button property="button" styleClass="butt" tabindex="3" value="Clear" onclick="clearData();" />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,4)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="categoryList">
						<tr>
                          <td  align="left" valign="top">
							   <display:table   cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="categoryTable" name="requestScope.categoryList" requestURI="/searchSCategory.do?method=searchSCategory" pagesize="5" >		            		            	            
							    <display:column  media="html" style="width:10px"  headerClass="GridHBg" class="GridLCells">
							    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="categoryTable" property="categoryId"/>">
							     </display:column>
					            <display:column property="categoryCd" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Security Category Code" />
			            		<display:column headerClass="GridLTHCells" class="GridLTCells" property="description" sortable="true" title="Description" decorator="com.epis.utilities.StringFormatDecorator" />
					        	</display:table>
					       </td></tr>
		   	              </logic:present>                       
				 </table>
		<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
