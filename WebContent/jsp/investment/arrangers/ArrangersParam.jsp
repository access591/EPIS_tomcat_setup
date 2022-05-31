<%@ page language="java" import="com.epis.bean.investment.ArrangersBean,com.epis.utilities.ScreenUtilities" %>
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
		 var vWinCal;
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
	var url = "searchArrangers.do?method=reportArrangers&arrangerName="+document.forms[0].arrangerName.value+"&categoryIds="+document.forms[0].categoryIds.value+"&reportType="+document.forms[0].reportType.value;
     var sWidth=screen.width-10;
	 var sHeight=screen.height-120;
    
var windSize="width=222,height=222,top=222,left=222,status=no,scrollbars=yes,resizable=yes";
wind1=window.open(url,"popup1","menubar=yes,toolbar = yes,status=no,scrollbars=yes,resizable=yes,width="+sWidth+",height="+sHeight+",top=0,left=0");

wind1.window.focus();
			return false;
		 }
   
  function clearData(){
   document.forms[0].reset();
   
  }
 function closeWin() {
		if( vWinCal != null ) vWinCal.close();
		if( wind1 != null ) wind1.close();
		//if( win_id != null ) win_id.close();
	}
  
		</script>
	</head>
	<body onunload="closeWin();" >
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
                 <td align=left><html:text size="25" styleClass="TextField" property="arrangerName" maxlength="25" /></td> 
                 <td>&nbsp;</td>
             </tr>
			<tr>
				<td  class="tableTextBold" width="50%" align=right><bean:message key="arranger.seccategory" bundle="common"/>:</td>
                  <td style="width: 145px;" align=left>
                    <html:hidden property="categoryIds" />
                  <html:select property="categoryId" style="width: 120px;height:50 px" styleClass="TextField" multiple="true" size="3" >
                      <html:options name="categorylist" property="categoryId"
						labelProperty="categoryCd"  labelName="categoryId" collection="categorylist"  />
                   </html:select></td>
                      </tr>  
                      <tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.reportType" bundle="common" /> :
					</td>
					<td align="left">
						<html:select property="reportType"  styleClass="TextField" style="width: 125px">
						<html:option value="html">
							HTML
						</html:option>
						<html:option value="excel">
							Excel
						</html:option>
							
						</html:select>
					</td>
				</tr>
                      
                        
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" property="Search" value="Ok" />&nbsp;<html:button styleClass="butt" property="button" value="Clear"  onclick="clearData();"/>
			
			</td>	
			</tr>
			</table>
			
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
