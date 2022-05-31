
<%@ page language="java" import="com.epis.bean.admin.ScreenBean,com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
	    <meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />		
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT src="<%=basePath%>/js/overlib.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		
			    
		function validate(mode){
			    if(document.forms[0].screenName.value!=""){
			    if(!ValidateAlphaNumeric(document.forms[0].screenName.value)){
		    		 alert("Enter Valid Screen Name ");
		    		 document.forms[0].screenName.focus();
		   			  return false;
		    		 }
		    	}
		       if(document.forms[0].screenPath.value==''){
					 alert("Please Enter Screen Path ");
						document.forms[0].screenPath.select();
						return(false);
				}
				 if(document.forms[0].sortingOrder.value==''){
					 alert("Please Enter Sorting Order  ");
						document.forms[0].sortingOrder.focus();
						return(false);
				 }
				 if(document.forms[0].sortingOrder.value!=''){
					 if(!ValidateNum(document.forms[0].sortingOrder.value)){
					 alert('Please enter valid value for Sorting Order(Expecting Numeric value)');
						document.forms[0].sortingOrder.focus();
						return(false);
						}
				 }
			if(mode=='M')
				setValues();	
		     return true; 
		 }
		 function clearData(){
        	window.document.forms[0].reset();
        	loadValues();
         	document.forms[0].screenName.focus();
  		}
		var detailArray=new Array();
	function saveDetails()
	{
		
		TrimAll(0);
		if(!validate('D'))
			return false;
		save();
		showValues(-1);
		clearDetails();
	}
	function save()
	{
		var name="";
		var path="";
		if(document.forms[0].opionName.value==""){
		    		 alert("Please Enter Option Name");
		    		 document.forms[0].opionName.focus();
		   			  return false;
		 }
		if(document.forms[0].opionPath.value==""){
		    		 alert("Please Enter Option Path");
		    		 document.forms[0].opionPath.focus();
		   			  return false;
		}
		detailArray[detailArray.length]=[document.forms[0].opionName.value,document.forms[0].opionPath.value];
	}
	function showValues(index)
	{
		var str='<TABLE border=0 width="100%" cellpadding=4>';
		var i;
		var iTotal = 0;
		for(i=0;i<detailArray.length;i++)
		{
			str+='<TR>';
			if(i==index){
				str+='<Td class="tcn" align=left style="width:50px">';
				str+='<input type=text name="Ename" value=\''+detailArray[i][0]+'\' style="width:50px"></td>';
				str+='<Td class="tcn" align=center style="width:120px">';
				str+='<input type=text name="Epath"  value=\''+detailArray[i][1]+'\' ></td>';
				str+='<Td align=center><a href=# onclick="return editDetails('+i+');">';
				str+='<img src="images/saveIcon.gif" border=0 alt="<bean:message key="button.save" bundle="common"/>"></a>';
				str+='</Td></TR>';
			}
			else{
					if(detailArray[i][0].length<10)
					{	
					str+='<TD class=tb align=left width=100>'+detailArray[i][0]+'</TD>';
					}
					else
					{
						str+="<TD class=tb align=left width=100>"+detailArray[i][0].substring(0,10);
						str+="<a class=tbl href=javascript:void(0) onMouseOver=\"overlib('"+detailArray[i][0]+"\')\" ";
						str+="onMouseOut='nd()'>...</a></TD>";
					}
					if(detailArray[i][1].length<10)
					{	
						str+='<TD class=tb align=left width=120>'+detailArray[i][1]+'</TD>';
					}
					else
					{
						str+="<TD class=tb align=left width=120>"+detailArray[i][1].substring(0,10);
						str+="<a class=tbl href=javascript:void(0) onMouseOver=\"overlib('"+detailArray[i][1]+"\')\" ";
						str+="onMouseOut='nd()'>...</a></TD>";
					}
					str+='<TD><a href=javascript:void(0) onclick=showValues('+i+')>';
					str+='<img src=images/icon-edit.gif alt="<bean:message key="button.edit" bundle="common"/>" border=0 ></a> <a href=javascript:void(0) onclick=del('+i+')>';
					str+='<img src=images/deleteGridIcon.gif alt="<bean:message key="button.delete" bundle="common"/>" border=0></a></TD>';
					str+='</TR>';
				}
		}
		str+='</TABLE>';
		document.all['detailsTable'].innerHTML = str;
		i++;
	}
	function editDetails(index)
	{
 		if(document.forms[0].Ename.value==""){
		    		 alert("Please Enter Option Name");
		    		 document.forms[0].Ename.focus();
		   			  return false;
		 }
		if(document.forms[0].Epath.value==""){
		    		 alert("Please Enter Option Path");
		    		 document.forms[0].Epath.focus();
		   			  return false;
		}
		
		detailArray[index]=[document.forms[0].Ename.value,document.forms[0].Epath.value];
		showValues(-1);
		return true;
	}
	function del(index)
	{
		var temp=new Array();
		for(var i=0;i<detailArray.length;i++)
		{
			if(i!=index)
				temp[temp.length]=detailArray[i];
		}
		detailArray=temp;
		showValues();
		document.forms[0].opionName.focus();
		return false;
	}
	function clearDetails()
	{

		document.forms[0].opionName.value="";
		document.forms[0].opionPath.value="";
		document.forms[0].opionName.focus();
	}
	function setValues()
	{
		var temp;
		for(var i=0;i<detailArray.length;i++)
		{
			temp = detailArray[i][0]+'|'+detailArray[i][1];
			
			document.forms[0].optionDetails.options[document.forms[0].optionDetails.options.length]=new Option('x',temp);
			document.forms[0].optionDetails.options[document.forms[0].optionDetails.options.length-1].selected=true;
		}
		//alert("anil  "+document.forms[0].optionDetails.options.length);
	}
   	function loadValues()
   	{
   	detailArray=new Array();
   	var detailRecords=new Array();
   	<%
   	 ScreenBean bean=(ScreenBean) request.getAttribute("screen");
   	 String details[]=bean.getOptionDetails();
   	 for(int i=0;i<details.length;i++)
   	 {%>
   	 detailArray[detailArray.length]=['<%=details[i]%>','<%=details[i+1]%>'];
   	 <%i++;}%>
   	
   	showValues(-1);
   }	
      
	</script>
	</head >
	<body onload="loadValues();document.forms[0].screenName.focus();">
	<div id="overDiv" style="position:absolute; visibility:hide;z-index:1;"></div>
	<html:form action="/editScreen.do?method=editScreen"  onsubmit="return validate('M');">
		<%=ScreenUtilities.screenHeader("screen.title")%>
	<table width="70%" border=0 align="center">
		<tbody>
			<tr>
				<td colspan=4 align=center class="epis"><html:hidden property="screenCD" name="screen" />&nbsp;<html:errors  bundle="error"/>
				</td>
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="screen.module" bundle="common"/>:
				</td><td width="20%" align="left" class="tableText">
				<bean:write name="screen" property="moduleName"/>
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="screen.submodulename" bundle="common"/>:
				</td><td width="20%" align="left" class="tableText">
				<bean:write name="screen" property="subModuleName"/>
				</td>			
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="screen.screenname" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="screenName" name="screen" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="100" />
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font> <bean:message key="screen.path" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="screenPath" name="screen" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="200" />
				</td>			
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="screen.sortingorder" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="2" property="sortingOrder" styleClass="TextField" style="width:20px" tabindex="3" maxlength="2" name="screen" />
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  &nbsp;
				</td><td width="20%" >
					&nbsp;
				</td>			
			</tr>
			
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt"  tabindex="4" value="Save" />&nbsp;<html:button styleClass="butt"  tabindex="5" property="button" value="Reset"  onclick="clearData();"/>
			<html:button  property="Cancel"  styleClass="butt" tabindex="6" onclick="javascript:window.location.href='actionScreen.do?method=searchScreen'" onblur="document.forms[0].screenName.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
			</td>	
			</tr>		
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			
			<tr>
				<td colspan=6 align=center>
				<table border=0 width="70%" cellpadding=1>
				<tr>
				<td class="tableSideTextBold" align="center" nowrap width=100><bean:message key="screen.optionname" bundle="common"/></td>
				<td class="tableSideTextBold" align="center" nowrap width=150><bean:message key="screen.optionpath" bundle="common"/></td>
				<td>&nbsp;</td>
				</tr>
				<Tr>
				<Td colspan=4>
				<div id=detailsTable></div>
				</Td>
				</Tr>
				<tr>
				<td class="tbb" align=""center"" nowrap><html:text size="10" property="opionName"   maxlength="200" /></td>
				<td class="tbb" align=""center"" nowrap><html:text size="12" style="width:120 px" property="opionPath" /></td>
				<td>
				<a href=javascript:void(0) onclick="saveDetails();"><img src='images/saveIcon.gif' alt="<bean:message key="button.save" bundle="common"/>" border=0></a>
				<a href=javascript:void(0) onclick="clearDetails();"><img src='images/cancelIcon.gif' alt="<bean:message key="button.clear" bundle="common"/>" border=0></a>
				</td>
				</tr>
				<tr>
				<td class="tbb" align="center" nowrap></td>
				</tr>
				</table>
				</td>
				</tr>
				<TR style="display:none">
				<TD colspan=3><html:select  property="optionDetails" multiple="true"></html:select></TD>
				</TR>
				
			</tbody>
	</table><%=ScreenUtilities.screenFooter()%></html:form>
	</body>
</html>

