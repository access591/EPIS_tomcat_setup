<%@ page language="java" import="com.epis.bean.investment.ArrangersBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>My JSP 'ArrangersSearch.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
	
		    if(document.forms[0].arrangerName.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].arrangerName.value)){
		    		 alert("Enter Valid Arranger Name ");
		    		 document.forms[0].arrangerName.focus();
		   			  return false;
		    		 }
		     }
		     if(document.forms[0].categoryId.value !=""){
  				var category="";
			for(i=0;i<document.forms[0].categoryId.length;i++)
			{
				if(document.forms[0].categoryId[i].selected==true)
				category+="|"+document.forms[0].categoryId[i].value+"|";
			}
			category=category.substring(0,category.length);
			document.forms[0].categoryIds.value=category;  
			//alert(document.forms[0].categoryIds.value);
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
			
			 window.location.href='searchArrangers.do?method=showArrangersAdd';
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchArrangers.do?method=deleteArrangers&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchArrangers.do?method=showEditArrangers&arrangerCd="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
  
  function clearData(){
   document.forms[0].arrangerName.value='';
   document.forms[0].categoryId.value='';
   document.forms[0].arrangerName.focus();
   
  }
  
		</script>
	</head>
	<body onload="document.forms[0].arrangerName.focus();">
	<html:form action="/searchArrangers.do?method=searchArrangers" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("arranger.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			 <tr>                    
			 	 <td  class="tableTextBold" width="50%" align=right nowrap><bean:message key="arranger.name" bundle="common"/>:</td>
                 <td align=left><html:text size="25" styleClass="TextField" property="arrangerName" maxlength="25" tabindex="1" /></td> 
                 <td>&nbsp;</td>
             </tr>
			<tr>
				<td  class="tableTextBold" width="50%" align=right><bean:message key="arranger.seccategory" bundle="common"/>:</td>
                  <td style="width: 145px;" align=left>
                    <html:hidden property="categoryIds" />
                  <html:select property="categoryId" style="width: 120px;height:50 px" styleClass="TextField" tabindex="2" multiple="true" size="3">
                      <html:options name="categorylist" property="categoryId"
						labelProperty="categoryCd"  labelName="categoryId" collection="categorylist" />
                   </html:select></td>
                      </tr>    
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" property="Search" tabindex="3" value="Search" />&nbsp;<html:button styleClass="butt" property="button" value="Clear" tabindex="4" onclick="clearData();"/>
			
			</td>	
			</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
				<logic:present name="arrangerList">
				<tr>
                   <td  align="left" valign="top">
					   <display:table   cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="arrangersTable" name="requestScope.arrangerList" requestURI="/searchArrangers.do?method=searchArrangers" pagesize="5" >		            		            	            
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="arrangersTable" property="arrangerCd"/>">
					     </display:column>
			             <display:column property="arrangerName" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Arrangers Name"  />
			             <display:column property="categoryCd" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Security Categories"  />
			             <display:column property="pramotorname" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Pramotor Name" decorator="com.epis.utilities.StringFormatDecorator" />
			              <display:column property="status" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Status" decorator="com.epis.utilities.StringFormatDecorator"  />
			        	</display:table>
			        </td>
			        </tr>
		   	</logic:present>  
			 </table>
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
