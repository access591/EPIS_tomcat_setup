<%@ page language="java" import="com.epis.bean.rpfc.*,java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.ScreenUtilities,com.epis.bean.rpfc.DesignationBean"%>
<%@ page  import="java.util.ArrayList"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>AAI</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
    function personalContinue(){
		self.close();
   		window.returnValue =true;
    }
    function personalClose(){	
      self.close();
   	  window.returnValue =false;
    }
    </script>
  </head>
  
  <body class="BodyBackground">
   <FORM>

  	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
   	    <tr>
   	    	<td>&nbsp;&nbsp;</td>
   	    </tr>
   	  
   	    <tr>
   	    	<%
   	    	 String title="";
   	    	if(request.getAttribute("title")!=null){
   	    		title=(String)request.getAttribute("title");
   	    	}else{
   	    		title="Employees list with same";
   	    	}
   	    	%>
   	    	<td class="ScreenSubHeading" colspan="1"><%=title%></td>
   	    </tr>
   	    
  		<tr>
  			<td>
  		 		<table align="center" width="100%" cellpadding=2 class="tbborder" cellspacing="0" border="0">
  		 		   <%
   					ArrayList dataList=new ArrayList();
  		 			if(request.getAttribute("personalChkList")!=null){
   						dataList=(ArrayList)request.getAttribute("personalChkList");
   						if(dataList.size()!=0){
		  		  %>
					<tr class="tbheader">
						<td class="tblabel">Pension Number&nbsp;&nbsp;</td>
						<td class="tblabel">CPF ACC.NO</td>
						<td class="tblabel">Employee Code&nbsp;&nbsp;</td>
						<td class="tblabel">Employee Name&nbsp;&nbsp;</td>
						<td class="tblabel">Designation</td>
						<td class="tblabel">D.O.B</td>
						<td class="tblabel">Pension Option&nbsp;</td>
						<td class="tblabel">Division&nbsp;</td>
						<td class="tblabel">Airport Code&nbsp;&nbsp;</td>
						<td class="tblabel">Region</td>
					</tr>
							<%	int count = 0;
								for (int i = 0; i < dataList.size(); i++) {
									count++;
									EmployeePersonalInfo personal = (EmployeePersonalInfo) dataList.get(i);
							%>
							<tr>
								<td class="Data"><%=personal.getPensionNo()%></td>
								<td class="Data" width="12%"><%=personal.getCpfAccno()%></td>
								<td class="Data"><%=personal.getEmployeeNumber()%></td>
								<td class="Data" width="12%"><%=personal.getEmployeeName()%></td>
								<td class="Data"><%=personal.getDesignation()%></td>
								<td class="Data" width="10%"><%=personal.getDateOfBirth()%></td>
								<td class="Data" width="5%"><%=personal.getWetherOption()%></td>
								<td class="Data"><%=personal.getDivision()%></td>
								<td class="Data"><%=personal.getAirportCode()%></td>
								<td class="Data" width="12%"><%=personal.getRegion()%></td>
							
							</tr>
						<%}
						}else{%>
						 <tr>
								<td class="Data" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td class="Data" >No Records</td>
							
							</tr>
						<%}	}%>
					</table>
					</td>
					</tr>
					<TR align="center">
						<td colspan="1">
									<input type="button" class="btn" value="  OK  " class="btn" onclick="personalContinue()" >
									<input type="button" class="btn" value="Cancel" onclick="javascript:personalClose()" class="btn" >
								</td>
					</TR>
		</table>
   </FORM>
  </body>
</html>
