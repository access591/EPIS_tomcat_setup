

<%@ page language="java" import="com.epis.bean.rpfc.EmpMasterBean,com.epis.utilities.*,java.util.*" pageEncoding="UTF-8" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">

		<title>EPIS</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	    <link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/calendar.js"></SCRIPT>
		<script type="text/javascript">
		 function call_calender(dobValue){
	  	  show_calendar(dobValue);
  	  
  	 }
  	 function validate()
  	 {
  	 
  	 if(document.forms[0].requestDate.value !="")
  	{
  	 if(!convert_date(document.forms[0].requestDate)){
						document.forms[0].requestDate.select();
						return(false);
					   }
			}
  	 }
  	 </script>
		
	</head>
	<body>
	<form method="post" action="<%=basePath%>nomineesearchservlet.do?method=nomineeSearch" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("nominee.search")%>
	<table width="550" border="0" cellspacing="0" cellpadding="0">
	
	
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Pension No:
				</td><td align=left width="20%" >
					<input type="text" size="8" name="pensionNo" class="TextField" style="width:120 px" />
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Request Date:
				</td><td align=left width="20%" >
					<input type="text" size="12" name="requestDate" styleClass="TextField" style="width:120 px" />
					<a href="javascript:void(0);" name="cal1" onclick="javascript:call_calender('forms[0].requestDate')"><img src="<%=basePath%>images/calendar.gif" border="0" align="center" /></a>
					
				</td>		
			</tr>
			<tr>
			<td colspan=4 align=center><input type="submit" class="butt" value="Search"  >&nbsp;<input type="reset" class="butt" value="Reset"></td>
			</table>
			
			<%=ScreenUtilities.searchSeperator()%>
			<table width="700" border="0" class="GridBorder" cellspacing="0" cellpadding="0">
			<%
				ArrayList nomineeList=(ArrayList)request.getAttribute("nomineeRequest");
				int count=nomineeList.size();
				if(count==0)
				{
			%>
			<tr ><th align="center" width="100%" class="norecords" colspan=9>No Records Found</th></tr>
			<%
			}
			else
			{
			%>
			
			<tr class="GridHBg">
				<td  class="GridLTCells" nowrap>
					 PF ID  
				</td>
				<td  class="GridLTCells" nowrap>
					 Old CPFACC.No
				</td>
				<td  class="GridLTCells" nowrap>
					 Employee Code.
				</td>
				<td  class="GridLTCells" nowrap>
					 Employee Name.
				</td>
				<td  class="GridLTCells" nowrap>
					Designation
				</td>
				<td  class="GridLTCells" nowrap>
					D.O.B.
				</td>
				<td  class="GridLTCells" nowrap>
					Airport Code.
				</td>
				<td  class="GridLTCells" nowrap>
					Region.
				</td>
				<td width=5% class="GridLTCells" >
				&nbsp;
				</td>
				
				
					
							
			</tr>
			
			
			<%
				String claz="";
			    for(int i=0; i<nomineeList.size(); i++)
			    {
			    EmpMasterBean empbean=(EmpMasterBean)nomineeList.get(i);
			     claz = (i%2 != 0) ? "GridRowBg2" : "GridRowBg1"; 
			     %>
			     <tr class="<%=claz%>" >
			     <td class="GridLTCells"><%=empbean.getPensionNumber()%></td>
			     <td class="GridLTCells"><%=empbean.getCpfAcNo()%></td>
			     <td class="GridLTCells"><%=empbean.getEmpNumber()%></td>
			     <td class="GridLTCells"><%=empbean.getEmpName()%></td>
			     <td class="GridLTCells"><%=empbean.getDesegnation()%></td>
			     <td class="GridLTCells"><%=empbean.getDateofBirth()%></td>
			     <td class="GridLTCells"><%=empbean.getStation()%></td>
			     <td class="GridLTCells"><%=empbean.getRegion()%></td>
			     <td class="GridLTCells">
			     <a href="nomineesearchservlet.do?method=pensionEdit&flag=false&pfid=<%=empbean.getPensionNumber()%>" ><img src='images/viewDetails.gif' border='0' alt='Edit' > </a>
			     </td>
			     
			     
			     <%
			     
			    }
			}
			%>
			
			</table>
			<%=ScreenUtilities.screenFooter()%>
			
	</form>
	</body>
</html>
