<%@ page  import="java.util.ArrayList,java.util.List"%>

<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.epis.utilities.CommonUtil" %>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo" %>
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page isErrorPage="true" language="java"%>
<%
StringBuffer basePath = new StringBuffer(request.getScheme());
	 basePath.append("://").append(request.getServerName()).append(":");
	 basePath.append(request.getServerPort()).append(request.getContextPath());
	 basePath.append("/");
	 
	CommonUtil util = new CommonUtil();
	HashMap regions = util.getRegion();	
	Set keys = regions.keySet();
	Iterator it = keys.iterator();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - CashBook - Bank Info</title>
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">    			
 	
		 	function testSS(){
		 	if(document.forms[0].pfid.value=="")
		 	{
		 		alert("Please Enter PfId [Mandatory]");
		 		document.forms[0].pfid.focus();
		 		return false;
		 	}
		 		var region=document.forms[0].region.value;
		 		if(region == 'AllRegions')
		   			region='';
		   		var empName=document.forms[0].empName.value;
		   		var url ="<%=basePath%>Employee?method=getEmployeeList&region="+region+"&empName="+empName;		   		
		   		document.getElementById("info").innerHTML="<img src='<%=basePath%>images/loading1.gif'>";
			    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
				return false;
			}
		 function sendInfo(pfid,name,desig,cpfacno,region,unitName){
		 		 window.opener.empDetails(pfid,name,desig,cpfacno,region,unitName);	 
				 window.close();			 
			}
	 </script>
	</head>

	<body>
		<form action="">
		<%=ScreenUtilities.screenHeader("employee.title")%>
			<table align="center" width="90%">
				<tr>
					<td align="center">
						<table align="center">
							<tr>
								<td class="label">
									Region Name:
								</td>
								<td>
									<select name="region" style="width:100px">
										<option value="">
											[Select One]
										</option>
										<option value="AllRegions">
											AllRegions
										</option>
<%
while (it.hasNext()) {
	String region = regions.get(it.next()).toString();
%>
										<option value="<%=region%>">
											<%=region%>
										</option>
<%
	}
%>
									</select>
								</td>
								<td class="label">
									Emp Name:
								</td>
								<td>
									<input type="text" name="empName" onkeyup="return limitlength(this, 20)">
									&nbsp;
								</td>
							</TR>
							<TR>
								<td class="label">
									PF ID:
								</td>
								<td>
									<input type="text" name="pfid" >
									&nbsp;
								</td>
								<td>
									<input type="button" class="butt" value="Search" class="btn" onclick="testSS();">
								</td>
								
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td id="info">
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<%
if (request.getAttribute("EmpList") != null) {
	List dataList = (ArrayList) request.getAttribute("EmpList");
    int listSize = dataList.size();
	if (listSize == 0) {
%>
				<tr>
					<td class="ScreenSubHeading">
						<h3>
							No Records Found
						</h3>
					</td>
				</tr>
				<%
	}else{
%>				
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="1" class="border" cellpadding="0" cellspacing="0">
							<tr>
								<td class="LABEL">
									Employee Name
								</td>
								<td class="LABEL">
									Old CpfAccno
								</td>
								<td class="LABEL">
									Employee Number
								</td>
								<td class="LABEL">
									Pension Number
								</td>
								<td class="LABEL">
									Designation
								</td>
								<td class="LABEL">
									Region
								</td>
								<td>
									
								</td>
							</tr>
							<%
	
	for(int cnt=0;cnt<listSize;cnt++){
		EmployeePersonalInfo info = (EmployeePersonalInfo)dataList.get(cnt);
%>
							<tr>
								<td class="Data">
									<%=info.getEmployeeName()%>
									&nbsp;
								</td>
								<td class="Data">
									<%=info.getCpfAccno()%>
									&nbsp;
								</td>
								<td class="Data">
									<%=info.getEmployeeNumber()%>
									&nbsp;
								</td>
								<td class="Data">
									<%=info.getPensionNo()%>
									&nbsp;
								</td>
								<td class="Data">
									<%=info.getDesignation()%>
									&nbsp;
								</td>
								<td class="Data">
									<%=info.getRegion()%>
									&nbsp;
								</td>
								<td class="Data">
									<%=info.getAirportCode()%>
									&nbsp;
								</td>
								<td>
									<input type="checkbox" name="pfid" value="<%=info.getPensionNo()%>" onclick="javascript:sendInfo('<%=info.getPensionNo()%>','<%=info.getEmployeeName()%>','<%=info.getDesignation()%>','<%=info.getCpfAccno()%>','<%=info.getRegion()%>','<%=info.getAirportCode()%>')" />
								</td>
							</tr>
<%		}
%>
			</TABLE>
					</TD>
				</tr>
<%
	}
}
%>
					
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</form>
	</body>
</html>
