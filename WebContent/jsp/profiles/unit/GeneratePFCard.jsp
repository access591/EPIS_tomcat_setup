
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
			<link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/buttons.css" rel="stylesheet" type="text/css" />
	</head>
	<body onload="document.forms[0].year.focus();" ><br><FIELDSET ><LEGEND class="epis">PF Card</LEGEND>
	<table width="70%" border=0 align="center" ><form action="#" >
		<tbody >
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td class="epis" align="right" width="20%">
					Region:
				</td>
				<td class="episdata" align="left" width="30%">NORTH										
				</td>
				
				<td class="epis" align="right" width="20%">
					
				</td>
				<td class="episdata" align="left" width="30%">									
				</td>
			</tr>
			<tr>
			<td class="epis" align="right" width="20%">
					Unit:
				</td>
				<td class="episdata" align="left" width="30%">VABB
				</td>
				<td class="epis" align="right" width="20%">
					
				</td>
				<td class="episdata" align="left" width="30%">
				</td>
				
			</tr>
			<tr>
				<td  width="20%" class="epis" align=right nowrap>
					 Year:
				</td><td width="20%" >
					<select  name="year" class="listbox" style="width:100 px" tabindex="2">
					<option>2009-10</option>
					<option>2008-09</option>
						<option>2007-08</option>
					<option>2006-07</option>						
					<option>2005-06</option>					
					<option>2004-05</option>						
					<option>2003-04</option>
					<option>2002-03</option>					
					<option>2001-02</option>						
					<option>2000-01</option>					
					</select>
					
				</td>				
				<tr>
						<td class="epis" align="right">
							Employee Name:
						</td>
						<td>
							<input type="text" name="empName" readonly="true" tabindex="3" class="textbox">
							<img src="<%=basePath%>images/search1.gif" onclick="popupWindow('http://nit4f57:8080/PensionFinal/PensionView/PensionContributionMappedListWithoutTransfer.jsp','AAI');" alt="Click The Icon to Select EmployeeName" />
						</td>

					</tr>
				<td class="epis" width="20%" align=right>
					
				</td>
				<td width="20%" class="epis">
					
				</td>	
							
			</tr>
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="OK" onclick="javascript:window.open('<%=basePath%>jsp/profiles/unit/PFCardReport.jsp')" tabindex="4"><input type="button" value="Reset" tabindex="5">
			<!-- 
				  <a class="buttons" href="<%=basePath%>jsp/admin/region/RegionSearchResult.jsp"><span>Search</span></a> <a class="buttons" href="index.html"><span>Clear</span></a> -->
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>			
			</tbody></form>
	</table>
	</FIELDSET>
	</body>
</html>
