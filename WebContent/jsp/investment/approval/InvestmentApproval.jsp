<%@ page language="java" import="java.util.*,com.epis.bean.investment.QuotationBean,com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.epis.action.investment.ArrangersAction" %>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ page import="java.util.SortedSet" %>
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
		<SCRIPT type="text/javascript">
		function validate(){
	var count = 0,appCount=0;
	var str="",app="";
	var single = false,appsingle=false;
	var arr = document.forms[0].shortlist;
	
	if(arr.length==undefined){//case for one record only
	    var che = document.forms[0].shortlist.checked;
	    if(che == true){
	        count = 1;
			str = arr.value;
			single = true;//single record in search			
	      }
	}else{	
		for(var i=0;i<arr.length;i=i+1)
		{
			if(arr[i].checked){
				count=count+1;

			}					
		}
   }
	if(count==0)
	{
		alert("select atleast one record");
		return false;
	}
	if(!single){
		for(var i=0;i<arr.length;i=i+1)
		{
			if(arr[i].checked)
				str = str+arr[i].value+",";
		}	
		 str = str.substring(0,str.length-1);	
	 }
	 document.forms[0].shortarrangers.value=str;
	 
	var apparr = document.forms[0].apparrangers;
	if(apparr.length==undefined){
	    var appche = document.forms[0].apparrangers.checked;
	    if(appche == true){
	        appCount = 1;
	        appsingle = true;		
			app = apparr.value;		
	      }
	}else{	
		for(var i=0;i<apparr.length;i=i+1)
		{
			if(apparr[i].checked){
				appCount=appCount+1;
			}					
		}
   }
   if(appCount==0)
	{
		alert("select atleast one Arranger");
		return false;
	}
	if(appCount==1){
		for(var i=0;i<apparr.length;i=i+1)
		{
			if(apparr[i].checked)
				app = apparr[i].value;
		}	
	 }
	document.forms[0].approvedarranger.value=app;
	 return true;
  }
  function disablingShortlisted()
  {
     var arrangerName;
     if(document.forms[0].apparrangers.length==undefined)
     {
     	var che = document.forms[0].apparrangers.checked;
     	if(che==true)
     	arrangerName=document.forms[0].apparrangers.value;
     	
     	for(i=0; i<document.forms[0].elements.length; ++i)
		{
		
			if (document.forms[0].elements[i].type=="checkbox" && document.forms[0].elements[i].id == arrangerName)
				document.forms[0].elements[i].disabled=false;
		}
     }
     
  	else
  	{
  	for(var i=0; i<document.forms[0].apparrangers.length; i++)
  	{
  		if(document.forms[0].apparrangers[i].checked==true)
  		arrangerName=document.forms[0].apparrangers[i].value;
  		
  	}
  	
  	for(i=0; i<document.forms[0].elements.length; ++i)
	{
		
		if (document.forms[0].elements[i].type=="checkbox" && document.forms[0].elements[i].id == arrangerName)
			document.forms[0].elements[i].disabled=false;
	}
	
	for(var i=0; i<document.forms[0].apparrangers.length; i++)
  	{
  		if(document.forms[0].apparrangers[i].checked==false)
  		arrangerName=document.forms[0].apparrangers[i].value;
  		
  	}
  	for(i=0; i<document.forms[0].elements.length; ++i)
	{
		
		if (document.forms[0].elements[i].type=="checkbox" && document.forms[0].elements[i].id == arrangerName)
		{
			document.forms[0].elements[i].checked=false;
			document.forms[0].elements[i].disabled=true;
		}
	} 
	} 
  	
   }
  function displayingArrangers()
  {
  	  for(i=0; i<document.forms[0].elements.length; ++i)
	{
		
		if (document.forms[0].elements[i].type=="checkbox")
					document.forms[0].elements[i].disabled=true;
	} 
  }
		</SCRIPT>
		
	</head>
	<body onload="displayingArrangers();">
	<html:form action="/addinvestapproval.do?method=saveInvestApproval" onsubmit="return validate();" >	
			<%=ScreenUtilities.screenHeader("app.title")%>
		<table width="550" border=0 align="center" >
             <tr>
             <td valign=middle align=center height=70>
                  <table border=0>
                      <tr>
                      <td align=center class="tableTextBold"><FONT size=4>
                       <bean:message key="com.aai" bundle="common" /> 
                     </b> </td>
                      </tr>
                   </table>
              </td>
              </tr>
              <tr>
             <td valign=middle align=center>
                  <table width=700 border=0>
                      <tr>
                      <td align=right class="tableTextBold">
                       <bean:message key="com.rate" bundle="common" /> :
                      </td>
                      <td  align="left">
                      <input type=hidden name="shortarrangers">
                       <input type=hidden name="approvedarranger">
                      <input  type="hidden" name='letterNo' value='<%=request.getParameter("letterNo")%>' >
                       <%=request.getParameter("letterNo")%>
                      </td>
                     
                      </tr>
                   </table>
              </td>
              </tr>    <Tr><td>&nbsp;</td></tr>
              <logic:present name="compArray">
                        <tr>
                        <td valign=top>
                         <table width=100% border=1 class="TableBorder" cellpadding=2 cellspacing=0 >
                        <%  int arrSize = ((Integer)session.getAttribute("arrSize")).intValue();
                            int secSize = ((Integer)session.getAttribute("secSize")).intValue();
                            String[][] comp =(String[][])session.getAttribute("compArray");
                        for(int i=0;i<secSize;i++){
                        %>
                        <tr>
                        <%
                         	for(int j=0;j<arrSize;j++){
                         	%>
                         	<td  class='<%=(i==0)?"tableTextBold":"tableText"%>' rowspan='<%=(i==0&&(j==0 || j==1 || j==arrSize-1))?2:1%>'>
                         		<%=StringUtility.checknull(comp[i][j]).equals("")?"--":StringUtility.checknull(comp[i][j])%>                         		
                         	</td>                         	
                         	<%                         	
                         	}
                         	%></tr>                         	
                         	<%
                         	if(i==0){
                         	%>
                         	<TR>
                         	<%
                         		for(int k=2;k<arrSize-1;k++){
                         			%>
                         			<td class="tableTextBold"> rates(Rs.)/YTM% </td>                         			
                         			<%
                         		}
                         		%>
                         	</TR>
                         	<%
                         	}
                         }
						%>
               </table>    	            
                        </td>   
                        </tr>
                        	</logic:present>  
                        	 <TR><td colspan=3 align=left class="tableTextBold" ><bean:message key="app.select" bundle="common"/> </td></tr>
                        	   <Tr><td>&nbsp;</td></tr>
                        <tr><td colspan=3 align=center>
                        <html:submit styleClass="butt" property="Save" value="Save"  />
                        <input type="button" class="butt"  name="Back" value="Back"  onclick="javascript:window.location.href='<%=basePath%>/searchinvestapproval.do?method=showInvestApprovalAdd'" >
                        </td></tr> 
                 </td>       
              </tr>
     </table>
				<%=ScreenUtilities.screenFooter()%></html:form>
	</body>
</html>
