
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
	<body  ><br><FIELDSET ><LEGEND class="epis">Form-2</LEGEND>
	<table width="85%" border="0" align="center" cellpadding="2" cellspacing="0">
   				 <tr><td colspan=2>&nbsp;</td></tr>
    			<tr><td colspan=2>&nbsp;</td></tr>
  			 <tr>
			<td class="epis" align="right" width="20%">
					Region:
				</td>
				<td class="episdata" align="left" width="30%"><SELECT NAME="unit" class="listbox" style="width:88px">

								<option value="N">
									North
								</option>
								<option value="NE">
									North East
								</option>
								<option value="E">
									East
								</option>
								<option value="S">
									South
								</option>
								<option value="W">
									West
								</option>
							</SELECT>										
				</td>
				
				
			</tr>
			<tr>
			<td class="epis" align="right" width="20%">
					Unit:
				</td>
				<td class="episdata" align="left" width="30%"><SELECT NAME="unit" class="listbox" style="width:88px">

								<option value="VABB">
									VABB
								</option>
								<option value="VIDP">
									VIDP
								</option>
								<option value="VOMM">
									VOMM
								</option>
								<option value="VOHY">
									VOHY
								</option>
								<option value="VOBG">
									VOBG
								</option>
							</SELECT>
				</td>
				
				
			</tr>
							
				<tr>
						<td class="epis" align="right">
							Employee Name:
						</td>
						<td>
							<input type="text" name="empName" readonly="true" tabindex="3" class="textbox">
							<img src="<%=basePath%>images/search1.gif" onclick="popupWindow('http://nit4f57:8080/PensionFinal/PensionView/PensionContributionMappedListWithoutTransfer.jsp','AAI');" alt="Click The Icon to Select EmployeeName" />
						</td>

					</tr>
    <tr><td colspan=2>&nbsp;</td></tr>
    <tr><td colspan=2>&nbsp;</td></tr>
    <tr><td align="center" colspan=2><input type="button" value="Report" onclick="javascript:window.open('<%=basePath%>jsp/profiles/chq/Form2Report.jsp')" ><input type="button" value="Cancel"/></td></tr>
   </table>
	</FIELDSET >
	</body>
</html>
