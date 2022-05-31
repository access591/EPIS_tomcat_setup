
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
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
	<body>
		<br>
		<FIELDSET>
			<LEGEND class="epis">
				Opening Balance Adjustments
			</LEGEND>
			<table width="70%" border=0 align="center">
				<form action="#">
				<tbody>
					<tr>
						<td colspan=4 align=center class="epis">
							<font color=blue><%="Y".equals(request.getParameter("status")) ? "Total  12 Records are inserted Successfuly"
							: "&nbsp;"%></font>
						</td>
					</tr>
					<tr>
						<td colspan=4 align=center class="epis">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="epis" align="right" width="20%">
							Region:
						</td>
						<td class="episdata" align="left" width="30%">
							<SELECT NAME="unit" class="listbox" style="width:88px">

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

						<td class="epis" align="right" width="20%">

						</td>
						<td class="episdata" align="left" width="30%">
						</td>
					</tr>
					<tr>
						<td class="epis" align="right" width="20%">
							Unit:
						</td>
						<td class="episdata" align="left" width="30%">
							<SELECT NAME="unit" class="listbox" style="width:88px">

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
						<td class="epis" align="right">
							Finance Year:
						</td>
						<td>
							<Select name='select_year' Style='width:100px' class="listbox" >
								<option value='NO-SELECT'>
									Select One
								</option>

								<option value='04,1995-96'>
									1995-96
								</option>

								<option value='04,1996-97'>
									1996-97
								</option>

								<option value='04,1997-98'>
									1997-98
								</option>

								<option value='04,1998-99'>
									1998-99
								</option>

								<option value='04,1999-00'>
									1999-00
								</option>

								<option value='04,2000-01'>
									2000-01
								</option>

								<option value='04,2001-02'>
									2001-02
								</option>

								<option value='04,2002-03'>
									2002-03
								</option>

								<option value='04,2003-04'>
									2003-04
								</option>

								<option value='04,2004-05'>
									2004-05
								</option>

								<option value='04,2005-06'>
									2005-06
								</option>

								<option value='04,2006-07'>
									2006-07
								</option>

								<option value='04,2007-08'>
									2007-08
								</option>

							</SELECT>
						</td>
					</tr>
					<tr>
						<td class="epis" align="right">
							Month:
						</td>
						<td>
							<select name="select_month" style="width:100px" class="listbox" >
								<Option Value='NO-SELECT'>
									Select One
								</Option>



								<option value="01">
									January
								</option>


								<option value="02">
									February
								</option>


								<option value="03">
									March
								</option>


								<option value="04">
									April
								</option>


								<option value="05">
									May
								</option>


								<option value="06">
									June
								</option>


								<option value="07">
									July
								</option>


								<option value="08">
									August
								</option>


								<option value="09">
									September
								</option>


								<option value="10">
									October
								</option>


								<option value="11">
									November
								</option>


								<option value="12">
									December
								</option>

							</select>
						</td>
					</tr>				
					
					<tr>
						<td class="epis" align="right" nowrap>
							Employee Transfer Status:
						</td>
						<td>
							<SELECT NAME="transferStatus" class="listbox" style="width:88px">

								<option value="Y">
									Yes
								</option>
								<option value="N">
									No
								</option>
							</SELECT>
						</td>

					</tr>

					<tr>
						<td class="epis" align="right">
							Employee Name:
						</td>
						<td>
							<input type="text" name="empName" readonly="true" " class="textbox">
							<img src="<%=basePath%>images/search1.gif" onclick="popupWindow('http://nit4f57:8080/PensionFinal/PensionView/PensionContributionMappedListWithoutTransfer.jsp','AAI');" alt="Click The Icon to Select EmployeeName" />
						</td>

					</tr>






					<tr>
						<td colspan=4 align=center>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan=4 align=center>
							<input type="button" value="Submit" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/chq/OpeningBalanceAdjustments.jsp?status=Y'" tabindex="4">
							<input type="button" value="Reset" tabindex="5">
							<!-- 
				  <a class="buttons" href="<%=basePath%>jsp/admin/region/RegionSearchResult.jsp"><span>Search</span></a> <a class="buttons" href="index.html"><span>Clear</span></a> -->
						</td>
					</tr>
					<tr>
						<td colspan=4 align=center>
							&nbsp;
						</td>
					</tr>
				</tbody>
				</form>
			</table>
		</FIELDSET>
	</body>
</html>
