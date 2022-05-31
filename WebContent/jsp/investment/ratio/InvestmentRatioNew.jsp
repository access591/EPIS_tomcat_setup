<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

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
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT SRC="/js/GeneralFunctions.js"></SCRIPT>
		<SCRIPT SRC="/js/DateTime.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		function validate(){
		if(document.forms[0].validFrom.value==""){
		  alert("Please Enter Valid From Date (Mandatory)");
		  document.forms[0].validFrom.focus();
		 return false;
		}
		if(document.forms[0].validFrom.value!=""){
			if(!convert_date(document.forms[0].validFrom))
			{
			document.forms[0].validFrom.focus();
			return false;
			}
			document.forms[0].validFrom.value=FormatDate(document.forms[0].validFrom.value);
		} 
		
		 var arrTextBox = document.getElementsByTagName("input");   
	       var i=0,sum=0;
	       var count="",str="";
	      while(i<arrTextBox.length) {  
			if(arrTextBox[i].type == "text" && arrTextBox[i].value != ""){ 
	               val=i;
	              count +=arrTextBox[val].value+",";
	         }
	                 i++;
	           str= count.substring(12,count.length-1);      
	          }
	             sum= eval(str.replace(/,/gi,"+"));
	         if(sum!=100)
	         {
	           alert("Total Should be Equal to 100.");
	           return false;
	         }
		return true;
	}
	function clearData(){
        window.document.forms[0].reset();
  }
</SCRIPT>
	</head>
	<body onload="document.forms[0].validFrom.focus();" >
	<table width="70%" border=0 align="center" >
	<html:form action="/addRatio.do?method=addRatio" onsubmit="return validate();">
		<%=ScreenUtilities.screenHeader("ratio.title")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
			<td  width="20%" class="tableTextBold" align=right nowrap>
					&nbsp;
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="ratio.valid" bundle="common"/>:
				</td><td width="20%" nowrap>
					<html:text size="12" property="validFrom"  styleId="validfrom" styleClass="TextField" style="width:100 px" />
					<html:link href="javascript:showCalendar('validfrom');" >
						<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>
				</td>	
				<td width="20%" >
					&nbsp;
					
				</td>					
			</tr>
			<tr><td colspan=2>&nbsp;</td></tr>
			<tr>
				<td colspan=4 align=center>
                    <table border="1" width=70% cellpadding=0 cellspacing=0 bordercolor='#85BDEC' cellpadding=3>
                          
                           <tr bgcolor=#85BDEC>
                        		<td style="width: 300px;" class="tableSideTextBold" align=center> <bean:message key="ratio.category"  bundle="common"/></td>
                        		<td style="width: 150px;" class="tableSideTextBold" align=center><bean:message key="ratio.percent"  bundle="common"/></td>     
                          </tr>
                              	<logic:present name="secList">   
                              	 	<logic:iterate id="sec" name="secList" >       
                                        <tr> 
                                        <td style="width: 145px;" class="tableTextBold" align=right>
                                        <bean:write name="sec" property="categoryCd"/></td>
                                        <td style="width: 150px;" align=center>
                                        <input type="text"  class="TextField" style="width:30px"  name="SEC<bean:write name="sec" property="categoryId"/>" size="3"  maxlength="5">
                                        %</td>
                                        </tr>
                                     </logic:iterate>   
                                    </logic:present> 
                                    <tr> 
                                      <td  class="tableSideTextBold" align=left colspan=2><bean:message key="ratio.note" bundle="common"/>  </td>
                                   </tr>  
                      </table>
                   </td>
           </tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center>
				<html:submit value="Save"  styleClass="butt" />&nbsp;
				<html:button property="Clear" value="Clear" styleClass="butt" onclick="clearData();" />
				<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchRatio.do?method=searchRatio'" onblur="document.forms[0].validFrom.focus();"  />
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>			
			</table>
			<%=ScreenUtilities.screenFooter()%>
			</html:form>
	</body>
</html>
