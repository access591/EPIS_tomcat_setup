
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
	<body><br><FIELDSET ><LEGEND class="epis">Arrangers Information</LEGEND>
	<table width="80%" border=0 align="center">
		<tbody>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			 <tr>                       
			 			 <td  class="epis" align=right nowrap> Arranger Name:</td>
                        <td ><input size="25" class="textbox" name="arrangerName" /></td>
                        
                       <td  class="epis" align=right nowrap> Status:</td>
                       <td  align=left>
                       <select name="status" style="width: 100px;" class="listbox" >
          
                        <option value="A">Active</option>
                        <option value="I">Inactive</option>
                       
                        </select></td>

             </tr>



                      <tr>



                        <td  class="epis" align=right> Security Category: </td>



                  <td style="width: 145px;" align=left><select name="select" style="width: 120px;height:50 px" class="listbox" multiple=true size=3>
          
                        <option value="s">SDS Securities</option>
                        <option value="r">SDL Securities</option>
                        <option value="r">PSU Securities</option>
                        <option value="s">GOI Securities</option>
                        <option value="r">PB Securities</option>
                        </select></td>
                         <td class="epis" align=right> Registered /Membership with: </td>


                  <td  align=left><select name="select" style="width: 120px;height:50 px" class="listbox" multiple=true size=3>
          
                        <option value="s">SEBI</option>
                        <option value="r">RBI</option>
                        <option value="r">BSE</option>
                        <option value="s">NSE-WDM</option>                        
                        </select></td>
                       
                      </tr>
                      
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Search" onclick="javascript:window.location.href='<%=basePath%>jsp/investment/arrangers/ArrangersSearchResult.jsp'"><input type="button" value="Clear">
			<!-- 
				  <a class="buttons" href="<%=basePath%>jsp/admin/region/RegionSearchResult.jsp"><span>Search</span></a> <a class="buttons" href="index.html"><span>Clear</span></a> -->
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan=4 align=left><input type="button" value="Add" onclick="javascript:window.location.href='<%=basePath%>jsp/investment/arrangers/ArrangersNew.jsp'"><input type="button" value="Delete">
				</td>
			</tr>
				<tr>
				<td colspan=4 align=center>
				<TABLE width=100% border=0 cellpadding=0 cellspacing=0  bordercolor="#d6dbe2" >
				<tr bgcolor="#d6dbe2"><td width=20 >&nbsp;</td><td class="epis" width=100>Arranger Name</td><td width=100 class="epis">Registered With</td><td class="epis" width=100>Security Categories</td><td width=100 class="epis">Pramotor Name</td><td width=20 class="epis">Status</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#d6dbe2" height=5><td width=20> </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				</TABLE>
				</td>
			</tr>
			</tbody>
	</table></FIELDSET >
	</body>
</html>
