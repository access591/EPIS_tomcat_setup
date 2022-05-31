<%@ page language="java" import="com.epis.bean.investment.RatioBean,java.util.*,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" %>
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
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT SRC="/js/GeneralFunctions.js"></SCRIPT>
		<SCRIPT SRC="/js/DateTime.js"></SCRIPT>
		<SCRIPT>
		   function validate(){
		if(document.forms[0].validFrom.value!=""){
			if(!convert_date(document.forms[0].validFrom))
			{
			return false;
			}
			document.forms[0].validFrom.value=FormatDate(document.forms[0].validFrom.value);
		} 
		return true;
  }

  function clearData(){
   document.forms[0].validFrom.value='';
   document.forms[0].validFrom.focus();
   
  }
 
     function selectCheckboxes(optiontype) { 
		
		var recordcd = getCheckedValue(document.forms[0].selectall);
	
	   if(optiontype!='Define Ratio' && optiontype!='Current Ratio'&& optiontype!='Edit'){
		if(recordcd=='')
		{
			alert("select atleast one record");
			return;
		}
		}
		if(optiontype=='Define Ratio'){		 
			
			 window.location.href='searchRatio.do?method=showRatioAdd';
		 }
		 
		if(optiontype=='Delete'){
			 
			 var state=window.confirm("Are you sure you want to delete ?");
			
			 if(state){
			    var url = "searchRatio.do?method=deleteRatio&ratiocd="+recordcd;
			   
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchRatio.do?method=showCurrentRatio&mode=E&ratiocd="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
		 }else if(optiontype=='Current Ratio'){	 			 
			    var url = "searchRatio.do?method=showCurrentRatio";
				document.forms[0].action=url;
				document.forms[0].submit();
		 }
	  }
  
  
		</SCRIPT>
	</head>
	<body onload="document.forms[0].validFrom.focus();">
	<html:form action="/searchRatio.do?method=searchRatio" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("ratio.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
			<td  width="20%" class="tableTextBold" align=right nowrap>
					&nbsp;
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="ratio.valid" bundle="common"/>:
				</td><td width="20%" nowrap >
					<html:text size="12" property="validFrom"  styleId="validfrom" styleClass="TextField" style="width:100 px" tabindex="1"/>
					<html:link href="javascript:showCalendar('validfrom');" tabindex="2">
						<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>
				</td>	
				
				<td width="20%" >&nbsp;</td>					
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" property="Search" value="Search" tabindex="3" />&nbsp;<html:button styleClass="butt" property="button" value="Clear"  onclick="clearData();" tabindex="4"/>
			</td>	
			</tr>
		</table>
			<%=ScreenUtilities.searchSeperator()%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="50%" height="29"  align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
			<logic:present name="ratioList">
				<tr>
                    <td  align="left" valign="top">
					   <display:table   cellpadding="0" cellspacing="0"  class="GridBorder"  style="width:710px;height:50px"  export="true"  sort="list" id="ratioTable" name="requestScope.ratioList" requestURI="/searchRatio.do?method=searchRatio" pagesize="5" >		            		            	            
					     <% List list = (List)request.getAttribute("ratioList");
			             if(list.size()>0){%>
					   <logic:equal  name="ratioTable" property="validTo" value="Till Date">
					   <display:column  media="html" style="width:10px"  headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="ratioTable" property="ratioCd"/>">
					     </display:column>
					   </logic:equal>
						<logic:notEqual name="ratioTable" property="validTo" value="Till Date" >
						<display:column media="html" headerClass="GridHBg" class="GridLCells" style="width:10px" title="">
			     			  	<img src='images/action/lockIcon.gif' border='0' alt='Lock'>
						</display:column>
					   </logic:notEqual>
			             <display:column property="validFrom" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Valid From"  />
			             <display:column property="validTo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Valid To"  />
			           
			             <% HashMap hm1 = (HashMap)request.getAttribute("categories");
			              String ratioCd = ((RatioBean)pageContext.getAttribute("ratioTable")).getRatioCd();
			             HashMap hm =(HashMap)hm1.get(ratioCd);
			             SortedSet sortedset= new TreeSet(hm.keySet());
						Iterator i = sortedset.iterator();
						while(i.hasNext()) {
							String key =(String) i.next();
						%>	
						<display:column  value="<%=hm.get(key)%>" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="<%=key%>"  decorator="com.epis.utilities.StringFormatDecorator"/>
					  <%} }%>
			        	</display:table>
			        </td>
			        </tr>
		   	</logic:present>  
	</table>
	<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
