
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
		<script language="javascript">
		function ShowStatus(){
		var status='<%="Y".equals(request.getParameter("pchngstaus"))?"Y":"N"%>';
		if(status=='Y'){
		alert("Password Changed successfully");
		}
		document.forms[0].userId.focus();
		}
		
		</script>
	</head>
	<body onload="ShowStatus();" ><br><FIELDSET ><LEGEND class="epis">Change Password</LEGEND>
	<table width="70%" border=0 align="center" ><form action="#" >
		<tbody >
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
				<td  width="20%" class="epis" align=right nowrap>
					 User Id :
				</td><td width="20%" >
				<select  name="userId" class="listbox" style="width:150 px" tabindex="2"><option>User 1</option><option>User 2</option><option>User 3</option><option>User 4</option></select>
					
					
				</td>	
				<td class="epis" width="20%" align=right>
					Password:
				</td>
				<td width="20%" class="epis">
					<input size="12" name="password" class="textbox" style="width:120 px" tabindex="1"/>
				</td>				
			
					
							
			</tr>
			
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Save" onclick="javascript:window.location.href='<%=basePath%>jsp/admin/user/ChangePassword.jsp?pchngstaus=Y'" tabindex="4"><input type="button" value="Clear" tabindex="5">
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
