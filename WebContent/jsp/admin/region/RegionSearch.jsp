<%@ page language="java" import="com.epis.bean.admin.RegionBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" pageEncoding="UTF-8"%>
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
		<title>Admin - Region Master</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css">
	
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].regionName.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].regionName.value)){
		    		 alert("Enter Valid Region ");
		    		 document.forms[0].regionName.focus();
		   			  return false;
		    		 }
		     }
		    return true; 
		 }
	   
	  function clearData(){
	   document.forms[0].regionName.value="";
	   document.forms[0].aaiCategory.value="";
	   
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
			
			 window.location.href='searchRegion.do?method=showRegionAdd';
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchRegion.do?method=deleteRegion&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchRegion.do?method=showEditRegion&regioncd="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
	</script>
	</head>
	<body onLoad="clearData();javascript:document.forms[0].regionName.focus();">
	<html:form action="/searchRegion.do?method=searchRegion" onsubmit="return validate();">
	<%=ScreenUtilities.saearchHeader("region.title")%><table width="550" border="0" cellspacing="3" cellpadding="0">
                      <tr>
						<td colspan=4 align=center>
						<html:errors  bundle="error"/>
						</td>
			            </tr>
                      <tr>
                        <td width="86" align="right" valign="middle" class="tableTextBold"><bean:message key="region.regionname" bundle="common"/>: </td>
                        <td width="178" align="left" valign="middle"><label>
                         <html:text  property="regionName" styleClass="TextField"   maxlength="30" tabindex="1"/>                          
                        </label></td>
                        <td width="82" align="right" valign="middle" class="tableTextBold"><bean:message key="region.aaicategory" bundle="common"/>: </td>
                        <td width="189" align="left" valign="middle">
                        <html:select property="aaiCategory" styleClass="TextField" tabindex="2">
						<html:option value="">[Select One]</html:option>
						<html:option value="METRO AIRPORT">METRO AIRPORT</html:option>
						<html:option value="NON-METRO AIRPORT">NON-METRO AIRPORT</html:option>
						</html:select>
                        </td>
                      </tr>
                      <tr>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td height="30" align="left" valign="bottom"><table width="150" border="0" cellspacing="0" cellpadding="0">
                          <tr>

                            <td width="67"> <html:submit  styleClass="butt" tabindex="3"><bean:message key="button.search" bundle="common"/></html:submit></td>
                            <td width="83">	<html:button styleClass="butt" tabindex="4" property="Clear"  onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <%=ScreenUtilities.searchSeperator()%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="regionList">
						<tr>
                          <td  align="left" valign="top">
							   <display:table   cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="true" style="width:710px;height:50px" export="true"  sort="list" id="regionTable" name="requestScope.regionList" requestURI="/searchRegion.do?method=searchRegion" pagesize="5" >		            		            	            
							   
							    <display:column  media="html" style="width:10px"  headerClass="GridHBg" class="GridLCells">
							    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="regionTable" property="regionCd"/>">
							     </display:column>
					            <display:column style="width:200px" property="regionName" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Region Name"  />
					            <display:column style="width:200px" headerClass="GridLTHCells" class="GridLTCells" property="aaiCategory" sortable="true" title="AAI Category"  />
					        	<display:column style="width:300px" headerClass="GridLTHCells" class="GridLTCells" property="remarks" sortable="true" title="Remarks"  decorator="com.epis.utilities.StringFormatDecorator"/>
					            
					        	</display:table>
					       </td></tr>
		   	              </logic:present>                       
                    </table><%=ScreenUtilities.searchFooter()%>
</html:form>
</body>
</html>
