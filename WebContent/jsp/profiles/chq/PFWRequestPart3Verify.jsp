
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
	<body  ><br><FIELDSET ><LEGEND class="epis"  >PFW  Part-III Verification</LEGEND>
	<table width="100%" border=0 align="center" ><form action="#" >
		<tbody >
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			  <tr>
							            <td class="epis" align="right" >
											Key No:
										</td>
										<td class="episdata" >
										
											<input type="text" name="advntrnsid" value="PFW/LIC/2431" class="textbox"  readonly="readonly">
																						
										</td>
										<td width="10%">&nbsp;</td>
							           
							         </tr> 
							         <tr>
							            <td class="epis" align="right">
											PF ID:
										</td>
										<td class="episdata">
											<input type="hidden" name="pensionNo" value="1993">
											<input type="text" name="pfid" value="211268AA1993" class="textbox" readonly="readonly">											
										</td>
							           
							         </tr> 
							         <tr>
									  <td class="epis" align="right">
											Date of Joining Fund:
										</td>
										<td>
											<input type="text" name="dateOfMembership" class="textbox" value="10-Aug-1992" readonly="readonly">
																					
										</td>
									</tr>	  
									<tr>
										<td class="epis" align="right">
											Emoluments:
										</td>
										<td>
											<input type="text" name="emoluments" value="40176" class="textbox" onchange="javascript:calRecomdedAmntStatus()" onfocus="javascript:calRecomdedAmntStatus()" readonly="readonly">											
										</td>									
									</tr>	
									
									<tr>
										<td class="epis" align="right">
											0.0
										</td>
										<td>
											<input type="text" name="mnthsemoluments" class="textbox" value="0.0" readonly="readonly">											
										</td>									
									</tr>	
										
									<tr>
										<td class="epis" align="right">
											Rule for PFW:
										</td>
										<td>
											<select name="prpsecvrdclse" style="width: 131px" class="listbox"><option value="25(A)">25(A)</option>
											  <option value="24(1)(a)">24(1)(a)</option>
											  <option value="24(1)(b)">24(1)(b)</option>
											  <option value="24(1)(c)">24(1)(c)</option>
											  <option value="24(1)">24(1)</option>
											  <option value="24(6)">24(6)</option>
											  <option value="25(B)">25(B)</option></select>													
										</td>									
									</tr>										
						           <tr>						            
						            <td class="epis" align="right">Employee Subscription:</td>
						           
						            <td class="episdata"><input type="text" name="subscriptionAmt" value="0.0" class="textbox" onchange="javascript:calRecomdedAmntStatus()" onblur="javascript:calRecomdedAmntStatus()"></td>
						          </tr>		
						           <tr>						            
						            <td class="epis" align="right">Employer Contribution:</td>
						           
						            <td class="episdata"><input type="text" name="contributionAmt" class="textbox" value="0.0" onblur="calCPFFund();"> </td>
						          </tr>		
						          <tr>						            
						            <td class="epis" align="right">Fund Available in CPF A/c:</td>
						           
						            <td class="episdata"><input type="text" name="CPFFund" class="textbox" value="0" onchange="javascript:calRecomdedAmnt()" onblur="javascript:calRecomdedAmnt()"> </td>
						          </tr>	
						          	
						          <tr>						            
						            <td class="epis" align="right">Amount recommended for sanction of PFW of Rs.:</td>
						           
						            <td class="episdata"><input type="text" name="amntrcdedrs" value="0.0" class="textbox"> </td>
						          </tr>	
						          
						         
						          
						          
						          
						       
						            <tr>						            
						            <td class="epis" align="right">Verified By:</td>
						           
						            <td class="episdata">
						            <select name="authorizedsts" style="width: 131px" class="listbox"><option value="A">Accept</option>
						            <option value="R">Reject</option></select>
						            </td>
						          </tr>	
						          
						       
						          
								

										
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><input type="button" value="Submit" onclick="javascript:window.location.href='<%=basePath%>jsp/profiles/chq/PFWRequestSearch.jsp'" tabindex="4"><input type="button" value="Reset" tabindex="5">
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
