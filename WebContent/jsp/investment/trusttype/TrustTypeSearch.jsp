
<%@ page language="java" import="com.epis.utilities.ScreenUtilities,com.epis.bean.investment.TrustTypeBean,com.epis.utilities.StringFormatDecorator" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>


		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	    <link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].trustType.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].trustType.value)){
		    		 alert("Enter Valid Trust Type ");
		    		 document.forms[0].trustType.focus();
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
			
			 window.location.href='searchTrustType.do?method=showTrustTypeAdd';
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchTrustType.do?method=deleteTrustType&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchTrustType.do?method=showEditTrust&trustcd="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
  
  
  
  function clearData(){
   document.forms[0].trustType.value="";
   document.forms[0].trustType.focus();
   
  }
		</script>
	</head>
	<body onload="document.forms[0].trustType.focus();">
	<html:form action="/searchTrustType.do?method=searchTrustType" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("trust.newTitle")%>
	<table width="550" border="0" cellspacing="3" cellpadding="0">
                      <tr>
						<td colspan=4 align=center>
						<html:errors  bundle="error"/>
						</td>
			            </tr>
                      <tr><TD width="5%">&nbsp;</TD>
				<td  width="10%" class="tableTextBold" align=right nowrap>
					 <bean:message key="trust.trustType" bundle="common"/>:
				</td><td align="left" width="20%" >
					<html:text  size="12" property="trustType" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="20" />
				</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" value="Search" tabindex="2" />&nbsp;<html:button property="button" styleClass="butt" value="Clear" tabindex="3"  onclick="clearData();"/>
			</td>	
			</tr>
			</table>
                    <%=ScreenUtilities.searchSeperator()%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,4)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        
                        <logic:present name="trusttypeList">
				<tr>
                   <td  align="left" valign="top">
					   <display:table   cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="trustTable" name="requestScope.trusttypeList" requestURI="/searchTrustType.do?method=searchTrustType" pagesize="5" >		            		            	            
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:15px" name="selectall" value="<bean:write name="trustTable" property="trustCd"/>">
					     </display:column>
					     
			             <display:column property="trustType" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Trust Type"  />
			             <display:column property="description" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Description"  decorator="com.epis.utilities.StringFormatDecorator" />
			              </display:table>
			        </td>
			        </tr>
		   	</logic:present>                                            
                    </table>
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
