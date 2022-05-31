
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
	<body onload="document.forms[0].advanceTransID.focus();" ><br><FIELDSET >
	<LEGEND class="epis"  >PFW Part-II Verification</LEGEND>
	<table width="100%" border=0 align="center" ><form action="#">
		<tbody >
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			 <tr>
											<td width="12%">
												&nbsp;
											</td>
											<td width="34%" class="epis" align=right>
												KEY NO:
											</td>
											
											<td width="53%" >																				
												<input type="text" name="advanceTransID" value="PFW/MARRIAGE/2457" readonly="readonly" class="textbox">
																
											</td>
										</tr>

										<tr>
											<td>
												&nbsp;
											</td>
											<td class="epis" align=right>
												Employee code:
											</td>
											
											<td >
												<input type="text" name="employeeNo" value="12317" readonly="readonly" class="textbox">											
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="epis" align=right>
												Employee Name:
											</td>
											
											<td >
											    <input type="text" name="employeeName" value="Shanti Devi" readonly="readonly" class="textbox">											
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="epis" align=right>
												Designation:
											</td>
											
											<td >
											     <input type="text" name="designation" value="JUNIOR ATTENDANT" readonly="readonly" class="textbox">												
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="epis" align=right>
												Father's Name:
											</td>
											
											<td >
												<input type="text" name="fhName" value="Malik Chand" readonly="readonly" class="textbox">												
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="epis" align=right>
												Department:
											</td>
											
											<td >
												<input type="text" name="department" value="" readonly="readonly" class="textbox">												
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="epis" align=right>
												Date of joining in AAI:
											</td>
											
											<td >
												<input type="text" name="dateOfJoining" value="19-Nov-2004" readonly="readonly" class="textbox">													
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td class="epis" align=right>
												Date of Birth:
											</td>
											
											<td >
												<input type="text" name="dateOfBirth" value="08-Dec-1964" readonly="readonly" class="textbox">												
											</td>
										</tr>
											<tr>
											<td>
												&nbsp;
											</td>
											<td class="epis" align=right>
												Taken Loan:
											</td>
											
											<td >
												<select name="takenloan"  class="listbox"><option value="Y">Yes</option>  
															<option value="N" selected="selected">No</option></select>
											</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td class="epis" align=right>Verified By:</td>
											
											<td class="episdata">Personnel
											</td>
										</tr>
										<tr>
										<td>&nbsp;</td>
										<td class="epis" align=right>Authorized Status:</td>
										
										<td>
										<select name="authorizedsts" style="width:130px" class="listbox"><option value="A">Accept</option>  
											<option value="R">Reject</option></select>
										</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td class="epis" align=right>Remarks:</td>
											
											<td><input type="text" name="authorizedrmrks" maxlength="150" value="" class="textbox">
											</td>
										</tr>
										
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Next>>" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/unit/PFWRequestPart3Verify.jsp'" tabindex="4"><input type="button" value="Reset" tabindex="5">
			<input type="button" value="Cencel" tabindex="5">
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
