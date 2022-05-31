
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
	<body><br><FIELDSET ><LEGEND class="epis">CPF Advance Requests</LEGEND>
	<table width="85%" border=0 align="center">
		<tbody>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
				<td  width="20%" class="epis" align=right nowrap>
					 Request Id:
				</td><td width="20%" >
					<input size="12" name="userId" class="textbox" style="width:150 px" tabindex="1"/>
					
				</td>	
				<td  width="20%" class="epis" align=right nowrap>
					 
				</td><td width="20%" >
					
					
				</td>	
			
		
							
			</tr>
			
			
			<tr>
				<td class="epis" width="20%" align=right>
					Purpose:
				</td>
				<td width="20%" class="epis">
					<select name='select_adv_purpose_dtl' onchange="purposes_nav();" style='width:150px' class="listbox">
						<option value="NO-SELECT">
							Select one
						</option>
						<option value='cost'>
							Cost Of Passage
						</option>
						<option value='obligatory'>
							Obligatory Expanses
						</option>
						<option value='obMarriage'>
							Marriage Expanses
						</option>
						<option value='illness'>
							Illness Expanses
						</option>
						<option value='education'>
							Higher Education
						</option>
						<option value='defence'>
							Defense Of Court Case
						</option>
						<option value='others'>
							Others
						</option>
					</select>
				</td>				
			
				<td class="epis" width="20%" align=right>
					Status:
				</td>
				<td width="20%" class="epis">
					<select name='status' onchange="purposes_nav();" style='width:130px' class="listbox">
						<option value="NO-SELECT">
							All
						</option>
						<option value='cost'>
							New Request
						</option>
						<option value='obligatory'>
							Verified
						</option>
						<option value='obMarriage'>
							Approved
						</option>
						<option value='illness'>
							Rejected
						</option>						
					</select>
				</td>	
							
			</tr>
			
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Search" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/member/AdvanceRequestSearch.jsp'"><input type="button" value="Clear">
			<!-- 
				  <a class="buttons" href="<%=basePath%>jsp/admin/region/RegionSearchResult.jsp"><span>Search</span></a> <a class="buttons" href="index.html"><span>Clear</span></a> -->
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan=4 align=left><input type="button" value="New Request" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/member/AdvanceRequestNew.jsp'"><input type="button" value="Delete">
				</td>
			</tr>
				<tr>
				<td colspan=4 align=center>
				<TABLE width=100% border=0 cellpadding=0 cellspacing=0  bordercolor="#d6dbe2" >
				<tr bgcolor="#d6dbe2"><td width=20 >&nbsp;</td><td class="epis" width=150>Rquest Id</td><td width=150 class="epis">Request Date</td><td width=20>&nbsp;</td><td width=100 class="epis">Purpose</td><td class="epis" width=100>Amount</td><td width=200 class="epis">No. Of <br>Installments</td><td width=140 class="epis">Status</td><td width=100 class="epis"></td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100 class="episdata">CPFREQ/HE/0109001</td><td width=100 class="episdata">01-Jan-2009</td><td>&nbsp;</td><td width=20 class="episdata">Higher Education</td><td width=20 class="episdata"> 25000</td><td width=20 class="episdata">12</td><td width=20 class="episdata">Verified</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100 class="episdata">CPFREQ/ME/0110012</td><td width=100 class="episdata">01-Jan-2010</td><td>&nbsp;</td><td width=20 class="episdata">Marriage Expenses</td><td width=20 class="episdata">120000</td><td width=20 class="episdata">24</td><td width=20 class="episdata">New Request</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#d6dbe2" height=5><td width=20> </td><td width=100>&nbsp;</td><td width=100>&nbsp;</td><td>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				</TABLE>
				</td>
			</tr>
			</tbody>
	</table></FIELDSET >
	</body>
</html>
