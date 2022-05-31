
<%@ page language="java" import="com.epis.bean.admin.SMBean,com.epis.utilities.ScreenUtilities"%>
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
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime1.js" ></SCRIPT>
		<SCRIPT src="<%=basePath%>/js/overlib.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		
			    
		function validate(mode){
		
			if(document.forms[0].moduleCd.value==""){
		    		 alert("Please Select Module");
		    		 document.forms[0].moduleCd.focus();
		   			  return false;
		    		 }
		    if(document.forms[0].subModuleCd.value==""){
		    	alert("Please Select Sub Module");
		    	document.forms[0].subModuleCd.focus();
		   	    return false;
		    	}
		    if(document.forms[0].screenName.value==""){
		    	alert("Please Enter Screen Name");
		    	document.forms[0].screenName.focus();
		   	    return false;
		    	}
			 if(document.forms[0].screenName.value!=""){
			    if(!ValidateAlphaNumeric(document.forms[0].screenName.value)){
		    		 alert("Enter Valid Screen Name ");
		    		 document.forms[0].screenName.focus();
		   			  return false;
		    		 }
		    	}
		       if(document.forms[0].screenPath.value==''){
					 alert("Please Enter Screen Path ");
						document.forms[0].screenPath.focus();
						return(false);
				}
				
				 if(document.forms[0].sortingOrder.value==''){
					 alert("Please Enter Sorting Order  ");
						document.forms[0].sortingOrder.focus();
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
        	document.forms[0].moduleCd.value="";
         	document.forms[0].subModuleCd.options.length=1;
         	document.forms[0].screenName.value="";
         	document.forms[0].screenPath.value="";
         	document.forms[0].moduleCd.focus();
  		}
		
   		
      
      function sendURL(url,responseFunction)  
      {           
	  //alert(url);
	  if (window.ActiveXObject) {			// for IE  
		httpRequest = new ActiveXObject("Microsoft.XMLHTTP"); 
	  } else if (window.XMLHttpRequest) { // for other browsers  
		httpRequest = new XMLHttpRequest(); 
	  }

	  if(httpRequest) {
		
		httpRequest.open("GET", url, true);  //2nd arg is url with name/value pairs, 3 specify asynchronus communication
		httpRequest.onreadystatechange = eval(responseFunction) ; //which will handle the callback from the server side element
		httpRequest.send(null); 
	  }
    }

   function retNodeValue(req,node)
   {
 	if(req.responseXML.getElementsByTagName(node)[0].firstChild!=null)
		return req.responseXML.getElementsByTagName(node)[0].firstChild.nodeValue;
	else
		return ' ';
   }
      
      
    function getSubmodules()
    {
	var obj=document.forms[0];
	if(obj.moduleCd.value!=''){
		var url="<%=basePath%>jsp/admin/screen/GetSubModules.jsp?mode=A&MCode="+obj.moduleCd.value; 
		sendURL(url,"responseSubModule"); 
		}
	}   
  	function responseSubModule() 
	{ 
	document.forms[0].subModuleCd.options.length=1;
	if (httpRequest.readyState == 4) { 
        if(httpRequest.status == 200) {  
			
			var node = httpRequest.responseXML.getElementsByTagName("MODULE")[0];
			
			if(node) { 
				var count=parseInt(retNodeValue(httpRequest,"count"));
				if(count>1){
					var smcd="";
					var smn="";
					for(var i=1;i<count;i++){
						smcd='SMCD'+i;
						smn='SMN'+i;
						document.forms[0].subModuleCd.options[document.forms[0].subModuleCd.options.length]=new Option(retNodeValue(httpRequest,smn),retNodeValue(httpRequest,smcd)); 
                        }
                        document.forms[0].subModuleCd.focus();
				} 
         }} else {
            alert("Error loading Sub Module Details \n"+ httpRequest.status +":"+ httpRequest.statusText); 
		 }
    }         
} 
	var detailArray=new Array();
	function saveDetails()
	{
		
		TrimAll(0);
		if(!validate('D'))
			return false;
		save();
		showValues();
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
	function showValues()
	{
		var str='<TABLE border=0 width="200" cellpadding=4>';
		var i;
		var iTotal = 0;
		for(i=0;i<detailArray.length;i++)
		{
			str+='<TR>';
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
				str+='<TD class=tb align=left width=150>'+detailArray[i][1]+'</TD>';
			}
			else
			{
				str+="<TD class=tb align=left width=150>"+detailArray[i][1].substring(0,10);
				str+="<a class=tbl href=javascript:void(0) onMouseOver=\"overlib('"+detailArray[i][1]+"\')\" ";
				str+="onMouseOut='nd()'>...</a></TD>";
			}
			str+='<TD class=tb align=center><a href=javascript:void(0) onclick=del('+i+')>';
			str+='<img src=images/cancelIcon.gif alt="<bean:message key="button.delete" bundle="common"/>" border=0 width=20 height=20></TD>';
			str+='</TR>';
		}
		str+='</TABLE>';
		document.all['detailsTable'].innerHTML = str;
		i++;
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
	
	</script>
	</head>
	<body onload="document.forms[0].moduleCd.focus();">
	<div id="overDiv" style="position:absolute; visibility:hide;z-index:1;"></div>
	<html:form action="/addScreen.do?method=addScreen"  onsubmit="return validate('M');">
	<%=ScreenUtilities.screenHeader("screen.title")%>
	<table width="70%" border=0 align="center">
		<tbody>
			<tr>
				<td colspan=4 align=center class="epis">&nbsp;<html:errors  bundle="error"/>
				</td>
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="screen.module" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:select  property="moduleCd" styleClass="TextField" style="width:120 px" tabindex="1"  onchange="getSubmodules()">
						<html:option value="">[Select one]</html:option>
						<html:options collection="modules" property="code" labelProperty="name"/>
					</html:select>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="common.stars" bundle="common"/><bean:message key="screen.submodulename" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:select  property="subModuleCd" styleClass="TextField" style="width:120 px" tabindex="2" >
						<html:option value="">[Select one]</html:option>
						</html:select>
				</td>			
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="screen.screenname" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="screenName" styleClass="TextField" style="width:120 px" tabindex="3" maxlength="100" />
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="common.stars" bundle="common"/><bean:message key="screen.path" bundle="common"/>:
				</td><td width="20%" >
					<html:text size="12" property="screenPath" styleClass="TextField" style="width:120 px" tabindex="4" maxlength="200" />
				</td>			
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="common.stars" bundle="common"/><bean:message key="screen.sortingorder" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="2" property="sortingOrder" styleClass="TextField" style="width:20px" tabindex="5" maxlength="2" />
					
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
			<td  colspan=4 align=center><html:submit tabindex="6" styleClass="butt" value="Save" />&nbsp;<html:button tabindex="7" styleClass="butt"  property="button" value="Clear"  onclick="clearData();"/>
			<html:button  property="Cancel"  styleClass="butt" tabindex="8" onclick="javascript:window.location.href='actionScreen.do?method=searchScreen'" onblur="document.forms[0].moduleCd.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
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
				<td class="tbb" align="center" nowrap><html:text size="10" property="opionName" styleClass="TextField"  maxlength="200" /></td>
				<td class="tbb" align="center" nowrap><html:text size="12" property="opionPath" styleClass="TextField" style="width:120 px" tabindex="4" maxlength="200" /></td>
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

