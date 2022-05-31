
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
	<body onload="document.forms[0].placeofposting.focus();" ><br><FIELDSET ><LEGEND class="epis">CPF Advance</LEGEND>
	<table width="100%" border=0 align="center" ><form action="#" >
		<tbody >
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
										<td class="epis" align="right" width="20%">
											PF ID:
										</td>
										<td class="episdata" align="left" width="30%">200752SC00012
										</td>
										<td class="epis" align="right" width="20%">
											Old Employee No:
										</td>
										<td class="episdata" align="left" width="30%">1800										
										</td>
									</tr>
									<tr>
										<td class="epis" align="right" >
											Name:
										</td>
										<td class="episdata" align="left">SATISH C CHHATWAL
										</td>

										<td class="epis" align="right">
											Designation:
										</td>
										<td class="episdata" align="left">
										Member
										</td>
									</tr>
									<tr>
										<td class="epis" align="right">
											Father's Name:
										</td>
										<td class="episdata" align="left">
											LATE SH. M.R. CHHATWAL											
										</td>
										<td class="epis" align="right">
											Department:
										</td>
										<td class="episdata" align="left" nowrap>FINANCE AND ACCOUNTS/INTERNAL
										</td>
									</tr>
									<tr>
										<td class="epis" align="right">
											Date of Joining in AAI:
										</td>
										<td class="episdata" align="left">
										01-May-2006
										</td>

										<td class="epis" align="right">
											Date of Joining CPF:
										</td>
										<td class="episdata" align="left">
											01-May-2006
										</td>
									</tr>
									<tr>
										<td class="epis" align="right">
											Date of Birth:
										</td>
										<td class="episdata" align="left" >
											20-Jul-1952
											<!-- <input type="text" name="dbirth" />-->
										</td>
									<td class="epis" align="right">Place of posting</td>
										<td align=left><input type="text" class="textbox" name="placeofposting" maxlength="35" value=""></td>	
								
									</tr>
									<tr>
										<td class="epis" align="right">Station
										</td>															
																				
											<td class="episdata" align="left">
												CHQNAD
											</td>							
											<td class="epis" align="right">Region:</td>
										<td class="episdata" align="left">CHQNAD</td>
											
								
									</tr>
									
									
									
									<tr>									
										<td class="epis" align=right nowrap>Emoluments (Pay + DA):</td>
										<td align=left ><input type="text" class="textbox" name="emoluments" value="63858"></td>
										<td class="epis" align=right nowrap>Amount of PFW/<br>Advance Required<font color="red">*</font>:&nbsp;
										</td>
										<td align=left>
											<input type="text" class="textbox" name="advReqAmnt" maxlength="15"  value="">
										</td>	
								
									</tr>
									<tr>
										<td class="epis" align=right>Trust &nbsp;:</td>
										<td align=left><select name="trust" style="width:60px" class="listbox"><option value="NAA">NAA</option>
													<option value="AAI">AAI</option>
													<option value="IAAI">IAAI</option></select>
										</td>									
											
										<td>&nbsp;</td>
										<td>&nbsp;</td>
								
									</tr>
									<tr>
									<td class="epis" align=right>Purpose<font color="red">*</font>:</td>
											<td align=left>
												<select name='select_adv_purpose_dtl' onchange="purposes_nav();" style='width:130px' class="listbox">
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
											<td class="epis" align=right >Total No of Installment<font color="red">*</font>:</td>
											<td align=left>
												<select name='totalinstall' style='width:130px' class="listbox">
													<option value="NO-SELECT">
														Select one
													</option>
													
														<option value="1">1</option>
													
														<option value="2">2</option>
													
														<option value="3">3</option>
													
														<option value="4">4</option>
													
														<option value="5">5</option>
													
														<option value="6">6</option>
													
														<option value="7">7</option>
													
														<option value="8">8</option>
													
														<option value="9">9</option>
													
														<option value="10">10</option>
													
														<option value="11">11</option>
													
														<option value="12">12</option>
													
														<option value="13">13</option>
													
														<option value="14">14</option>
													
														<option value="15">15</option>
													
														<option value="16">16</option>
													
														<option value="17">17</option>
													
														<option value="18">18</option>
													
														<option value="19">19</option>
													
														<option value="20">20</option>
													
														<option value="21">21</option>
													
														<option value="22">22</option>
													
														<option value="23">23</option>
													
														<option value="24">24</option>
													
														<option value="25">25</option>
													
														<option value="26">26</option>
													
														<option value="27">27</option>
													
														<option value="28">28</option>
													
														<option value="29">29</option>
													
														<option value="30">30</option>
													
														<option value="31">31</option>
													
														<option value="32">32</option>
													
														<option value="33">33</option>
													
														<option value="34">34</option>
													
														<option value="35">35</option>
													
														<option value="36">36</option>
													
												</select>
											</td></tr>
			
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Next>>" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/member/AdvanceRequestPurpose.jsp'" tabindex="4"><input type="button" value="Reset" tabindex="5">
			<input type="button" value="<<Back" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/member/AdvanceRequestSearch.jsp'" tabindex="4"><!-- 
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
