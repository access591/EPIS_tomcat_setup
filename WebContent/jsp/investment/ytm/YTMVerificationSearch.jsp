
<%@ page language="java" import="com.epis.bean.investment.QuotationBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator"%>
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
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
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
			
			 window.location.href='actionYTM.do?method=showYTMAdd';
		 }
		else if(optiontype=='Edit'){	 
				if(recordcd.substring(0,recordcd.indexOf('|'))=='S'){
					alert("The comparision Statement has been Generated.So, YTM cannot be edited.");
					return false;
				}
				recordcd = 	recordcd.substring(recordcd.indexOf('|')+1);
			    var url = "actionYTM.do?method=showYTMAddDetails&letterNo="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
		 else if(optiontype=='Report')
		 {
		 	recordcd = 	recordcd.substring(recordcd.indexOf('|')+1);
		 	var url = "actionYTM.do?method=showYTMDetails&letterNo="+recordcd;
		 	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"InvestmentProposal","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
		 }
	  }
		function clearData() { 
		document.forms[0].letterNo.value='';
		document.forms[0].ytmVerified.value='';
		document.forms[0].letterNo.focus();
		}
      </script>
	</head>
	<body onload="document.forms[0].letterNo.focus();">
	<html:form action="/actionYTM.do?method=searchYTM"  onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("ytm.newtitle")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="ytm.letterno" bundle="common"/>:
				</td><td align="left" width="20%" >
					<html:select  property="letterNo" styleClass="ListField" style="width:120 px" tabindex="1" >
						<html:option value="">[Select one]</html:option>
						<html:options collection="letters" property="letterNo" labelProperty="letterNo"/>
					</html:select>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="ytm.status" bundle="common"/>:
				</td><td width="20%" align=left>
					   <html:select  property="ytmVerified" styleClass="TextField" style="width:120 px" tabindex="2" >
					 		<html:option value="">All</html:option>    
							<html:option value="Verified">Verified</html:option>
                        	<html:option value="Not Verified">Not Verified</html:option>                        
                       </html:select>
				</td>					
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" tabindex="3"  value="Search" />&nbsp;<html:button  styleClass="butt" property="button" tabindex="4" value="Clear"  onclick="clearData();"/>
			</td>	
				</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
			<logic:present name="ytmList">
				
                          <td  align="left" valign="top">			
					   <display:table  cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="ytmTable" name="requestScope.ytmList" requestURI="/actionYTM.do?method=searchYTM" pagesize="5" >		            		            	            
						     <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="ytmTable" property="status"/>|<bean:write name="ytmTable" property="letterNo"/>" />
					     </display:column>
						    <display:column property="letterNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Letter no."  />
						    
				            <display:column property="ytmVerified" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Staus"  />
				             </logic:present>
			        	</display:table>
			        </td>
			        </tr>
		   	<%-- </logic:present> --%>
			 </table>
		<%=ScreenUtilities.searchFooter()%>
	</table></html:form>
	</body>
</html>
