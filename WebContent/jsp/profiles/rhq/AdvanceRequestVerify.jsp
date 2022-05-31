
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
	<body onload="document.forms[0].advntrnsid.focus();" ><br><FIELDSET ><LEGEND class="epis">CPF Advance Request</LEGEND>
	<table width="100%" border=0 align="center" ><form action="#" >
		<tbody >
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
						    <tr>
										
										<td class="epis" align=right>Key ID:</td>
										
										<td><input type="text" name="advntrnsid" value="CPF/OTHERS/2433" readonly="readonly" class="textbox">
										</td>
									</tr>
									
									<tr>
										
										<td class="epis" align=right>PF ID:</td>
									
										<td>
											
										
												
											<input type="text" name="pfid" tabindex="1" value="200752SC12" readonly="readonly" class="textbox">
										</td>
										
									</tr>						
								
									
									<tr>
										
										<td class="epis" align=right>Name(IN BLOCK LETTERS):</td>
										
										<td><input type="text" style="width:150 Px" name="empnamewthblk" value="SATISH C CHHATWAL" readonly="readonly" class="textbox">
										</td>
									</tr>
									<tr>
										
										<td class="epis" align=right>Designation:</td>
										
										<td><input type="text" name="designation" value="Member" readonly="readonly" class="textbox"></td>
									</tr>
																	
									<tr>
										
										<td class="epis" align=right>Date of Joining in Fund:</td>
										
										<td><input type="text" name="dateOfMembership" value="01-May-2006" readonly="readonly" class="textbox"></td>
									</tr>	
									
									<tr>
										
										<td class="epis" align=right>Previous advance outstanding:</td>
										
										<td><input type="text" name="outstndamount" value="" class="textbox"></td>
									</tr>	
									
									<tr>
										
										<td class="epis" align=right>Purpose of Advance:</td>
										
										<td><input type="text" name="purposeType" value="OTHERS" readonly="readonly" class="textbox"></td>
									</tr>	
									
									<tr>
										
										<td class="epis" align=right>Purpose covered clause:</td>
										
										<td>
										<select name="prpsecvrdclse" style="width:130px" class="listbox"><option value="29.1.1">29.1.1</option>  
											<option value="29.1.2a">29.1.2a</option>
											<option value="29.1.2b">29.1.2b</option>
											<option value="29.1.3">29.1.3</option>
											<option value="29.1.4">29.1.4</option>
											<option value="29.1.5">29.1.5</option></select>
										</td>
									</tr>
																
									<tr>
										
										<td class="epis" align=right>Authorized Status:</td>
										
										<td>
										<select name="authorizedsts" style="width:130px" class="listbox"><option value="A">Accept</option>  
											<option value="R">Reject</option></select>
										</td>
									</tr>
			
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Submit" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/rhq/AdvanceRequestSearch.jsp'" tabindex="4"><input type="button" value="Reset" tabindex="5">
			<input type="button" value="Cencel" tabindex="5"><!-- 
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
