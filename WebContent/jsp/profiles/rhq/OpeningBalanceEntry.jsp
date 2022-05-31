
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
	<body  ><br><FIELDSET ><LEGEND class="epis">Opening Balance Entry</LEGEND>
	<table width="70%" border=0 align="center" ><form action="#" >
		<tbody >
			<tr>
				<td colspan=4 align=center class="epis"><font color=blue><%="Y".equals(request.getParameter("status"))?"File OBJan2010.xls Uploaded Successfuly":"&nbsp;"%></font>
				</td>
			</tr>
			<tr>
				<td colspan=4 align=center class="epis">&nbsp;</td>
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
				<td class="epis" align="right" width="20%">
					
				</td>
				<td class="episdata" align="left" width="30%">
				</td>
				
			</tr>
			<tr>
				<td class="epis" align="right" width="20%" nowrap>
					File To Upload
				</td>
				<td class="episdata" align="left" width="30%">
				<input type="file"  class="textbox" name="uploadfile"  size="50">
				</td>
				<td class="epis" align="right" width="20%">
					
				</td>
				<td class="episdata" align="left" width="30%">							
				</td>
			</tr>						
									
									
			
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Upload" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/rhq/OpeningBalanceEntry.jsp?status=Y'" tabindex="4"><input type="button" value="Cancel" tabindex="5">
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
