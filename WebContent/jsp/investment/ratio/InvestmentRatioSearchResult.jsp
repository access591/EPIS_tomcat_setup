
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
	<body><br><FIELDSET ><LEGEND class="epis">Investment Ratio Information</LEGEND>
	<table width="70%" border=0 align="center">
		<tbody>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
				<td  width="20%" class="epis" align=right nowrap>
					 Valid On:
				</td><td width="20%" class="epis">
					<input size="12" name="validOn" class="textbox" style="width:100 px" tabindex="1"/>&nbsp;(DD/Mon/YYYY)
					
				</td>	
				<td  width="20%" class="epis" align=right nowrap>
					&nbsp;
				</td><td width="20%" >
					&nbsp;
					
				</td>			
			
					
							
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Search" onclick="javascript:window.location.href='<%=basePath%>jsp/investment/ratio/InvestmentRatioSearchResult.jsp'"><input type="button" value="Clear">
			<!-- 
				  <a class="buttons" href="<%=basePath%>jsp/admin/region/RegionSearchResult.jsp"><span>Search</span></a> <a class="buttons" href="index.html"><span>Clear</span></a> -->
			</td>	
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan=4 align=left><input type="button" value="Define Ratio" onclick="javascript:window.location.href='<%=basePath%>jsp/investment/ratio/InvestmentRatioNew.jsp'"><input type="button" value="Current Ratio" onclick="javascript:window.location.href='<%=basePath%>jsp/investment/ratio/CurrentInvestmentRatio.jsp'"><input type="button" value="Delete">
				</td>
			</tr>
				<tr>
				<td colspan=4 align=center>
				<TABLE width=100% border=0 cellpadding=0 cellspacing=0  bordercolor="#d6dbe2" >
				<tr bgcolor="#d6dbe2"><td width=20 >&nbsp;</td><td width=150 class="epis">Valid From</td><td class="epis" width=150>Valid To</td><td width=100 class="epis">PSU</td><td width=100 class="epis">SDL</td><td width=100 class="epis">GOI</td><td width=100 class="epis">SDS</td><td width=100 class="epis">PB</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td class="epis" height="10">01-Apr-2009</td>
                                <td  class="epis" height="10">Till Date</td>
                                <td  class="epis" height="10">30 %</td>
                                <td class="epis"  height="10">30 %</td>
                                <td  class="epis" height="10">20 %</td>
                                <td  class="epis" height="10">20 %</td>
                                <td  class="epis" height="10">0 %</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#e1e6e8"><td width=20><input type=checkbox >  </td><td class="epis" height="10">01-Apr-2007</td>
                                <td  class="epis" height="10">31-Mar-2009</td>
                                <td  class="epis" height="10">35 %</td>
                                <td class="epis"  height="10">20 %</td>
                                <td  class="epis" height="10">20 %</td>
                                <td  class="epis" height="10">25 %</td>
                                <td  class="epis" height="10">0 %</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
				<tr bgcolor="#f4f4f4"><td width=20><input type=checkbox >  </td><td class="epis" height="10">01-Apr-2006</td>
                                <td  class="epis" height="10">31-Mar-2007</td>
                                <td  class="epis" height="10">30 %</td>
                                <td class="epis"  height="10">15 %</td>
                                <td  class="epis" height="10">20 %</td>
                                <td  class="epis" height="10">25 %</td>
                                <td  class="epis" height="10">10 %</td><td width=20>&nbsp;</td><td width=20>&nbsp;</td></tr>
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
