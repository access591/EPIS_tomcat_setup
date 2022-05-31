
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.CommonUtil,com.epis.utilities.Constants,java.text.DecimalFormat"%>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>AAI</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
	</head>

	<body>
	<%
	
		String region="",fileHead="",reportType="",fileName="";
		if (request.getAttribute("region") != null) {
			region=(String)request.getAttribute("region");
			if(region.equals("NO-SELECT")){
			fileHead="ALL_REGIONS";
			}else{
			fileHead=region;
			}
		}
		
		if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					fileName = "Form_7(PS)_Summary_Report_"+fileHead+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
	
	%>
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<%	
		String dispYear="";
		CommonUtil commonUtil=new CommonUtil();
		if(request.getAttribute("dspYear")!=null){
		dispYear=request.getAttribute("dspYear").toString();
		}
		
	%>
	

			<%
						ArrayList dataList=new ArrayList();
						
						int count=0,size=0;
						EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
						String remarks="",monthYear="",shnMonthYear="",pensionRemarks="",rstchkYear="",seperationMnth="";
						DecimalFormat df = new DecimalFormat("#########0");
						if(request.getAttribute("chkYear")!=null){
							rstchkYear=(String)request.getAttribute("chkYear");
						}else{
							rstchkYear="1995";
						}
						 
						
						if(request.getAttribute("form7IndexList")!=null){
							dataList=(ArrayList)request.getAttribute("form7IndexList");
							 size=dataList.size();
							
						
							
							
			%>
	
  <tr>
    <td width="16%">&nbsp;</td>
    <td colspan="3" class="reportlabel"><table width="70%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" rowspan="3"><img src="<%=basePath%>/images/logoani.gif" width="87" height="50" align="right" /></td>
        <td width="80%" class="reportlabel"  align="center">FORM-7 PS Summary Report</td>
      </tr>
      <tr>
        <td nowrap="nowrap" align="center" class="reportsublabel">(FOR EXEMPTED ESTABLISHMENT ONLY)</td>
      </tr>
      <tr>
        <td nowrap="nowrap" align="center" class="reportsublabel">THE EMPLOYEES' PENSION SCHEME, 1995</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>

    <tr>
    <td>&nbsp;</td>
	
    <td colspan="3"  align="center" class="reportsublabel">CONTRIBUTION CARDS FOR MEMBERS FOR THE YEAR <%=dispYear%></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td width="27%">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>

  <tr>
    <td colspan="4"><table width="100%" border="1" bordercolor="gray" cellpadding="2" cellspacing="2">
      <tr>
        <td width="13%" align="center" class="label">Sl.No</td>
        <td width="30%" align="center" class="label">PFID</td>
       	 <td width="23%" align="center" class="label">Name</td>
          <td width="23%" align="center" class="label">Designation</td>
          <td width="23%" align="center" class="label">DOB</td>
           <td width="23%" align="center" class="label">DOJ</td>
         <td width="34%" align="center" class="label">Remarks</td>
      </tr>
      
      <tr>
        <td class="label" width="13%" align="center">1</td>
        <td class="label" width="30%" align="center">2</td>
        <td class="label" width="23%" align="center">3</td>
        <td class="label" width="23%" align="center">4</td>
        <td class="label" width="34%"  align="center">5</td>
        <td class="label" width="23%" align="center">6</td>
        <td class="label" width="34%"  align="center">7</td>
      </tr>
   
  
    <%
    	
    		for(int i=0;i<dataList.size();i++){
							count++;
							personalInfo=(EmployeePersonalInfo)dataList.get(i);
							
						
							
    %>

      <tr>
   		 <td class="Data"><%=count%></td>
   		 <td class="Data" nowrap="nowrap"><%=personalInfo.getPfID()%></td>
   		 <td class="Data" nowrap="nowrap"><%=personalInfo.getEmployeeName()%></td>
   		 <td class="Data" nowrap="nowrap"><%=personalInfo.getDesignation()%></td>
   		 <td class="Data" nowrap="nowrap"><%=personalInfo.getDateOfBirth()%></td>
   		 <td class="Data" nowrap="nowrap"><%=personalInfo.getDateOfJoining()%></td>
   		 <td class="Data">---</td>
        
      </tr>

  	<%}}%>

 



   <tr bordercolor="white">
    <td  colspan="5">&nbsp;</td>

  
  </tr>
    <tr bordercolor="white">
    <td class="Data">Dated :</td>
    <td colspan="3">&nbsp;</td>
    <td align="right" class="Data" nowrap="nowrap">Signature of the employer<br/>with office seal</td>
  </tr>
   </table>
	<br style='page-break-after:always;'>
</td>
  </tr>




						
 

						
					
</table>
</td>
</tr>
</table>
	</body>
</html>
