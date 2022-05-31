
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":"
		+ request.getServerPort()+ path + "/";
	
%>

<%@ page import="com.epis.bean.rpfc.form3Bean"%>
<%@ page import="java.util.ArrayList" %>

<Html><Head><Title>AAI </Title>
	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />

</Head>
<body text=#000000 leftMargin=0 topMargin=0 link="white" vlink="white" >

<table border=0 cellpadding=3 cellspacing=0 width="100%" align="center" valign="middle">

<%
	

	form3Bean formsetBean=null;
	String labelAry[] = null;
	ArrayList airportList = null;

	String months[] = {"January","Febraury","March","April","May","June","July","August","September","October","November","December"};

	String monthNo[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	String monthCd[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	String year = "";
	String month = "";
	String region = "";
	String prevMonth = "";
	String monthName = "";
	String prevYear = "";

	String date = "";
	String prevDate = "";

	String formno = "";
	String heading = "",heading1="",heading2="";
	String title = "";
	String note = "";
	String signatory = "Signature of the employer or other authorised officer of the Establishment<br/>Stamp of the Establishment";
	String dispPrevMnt="";
	
	String 	 reportType="";
	if (request.getParameter("frm_reportType") != null) {

				reportType = (String) request.getParameter("frm_reportType");

				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
		
					String fileName = "form-4-report.xls";

					response.setContentType("application/vnd.ms-excel");

					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);

				}

			}



	formno = "FORM-4 (EPS)";
	heading = "(For Exempted Establishments only)";
	heading1="THE EMPLOYEES PENSION SCHEME , 1995";
	heading2="Paragraph 20(2)";
	title = "RETURN OF EMPLOYEES ENTITLED FOR MEMBERSHIP OF THE EMPLOYEES PENSION FUND";
	note = "Note: New employee who has attained the age of 58 years and/or drawing Pension under the EPS, 1995 is not to be enrolled as a member.";

	labelAry = new String[]{"Sl.No","Account No","Name of employee (in block capitals)","Father's name or Husband's Name (in case of married Woman)","Age at Entry(Years)","Date of birth","Sex","Date of entitlement for membership","Remarks Previous Account No. and particulars of previous service, if any"};
	
	
%>
<tr>
	<td  colspan=2 class="reportlabel" align=center><%=formno%></td>
</tr>
<tr>
	<td  colspan=2 class="reportsublabel" align=center><%=heading%></td>
</tr>
<tr>
	<td  colspan=2 class="reportlabel" align=center><%=heading1%></td>
</tr>
<tr>
	<td  colspan=2 class="reportsublabel" align=center><%=heading2%></td>
</tr>
<tr>
	<td  colspan=2 class="reportlabel"  align=center><%=title%></td>
</tr>
<tr>
	<td  colspan=2 class="label" align=center>DURING THE MONTH OF <u><font color=blue><%=request.getAttribute("selectedDate")%></font></u></td>
</tr>

<tr>
	<td colspan=2 height=20>&nbsp;</td>
</tr>
<tr>
	<td class="label">Name & Address of Establishment:</td>
	<td class="Data">Airports Authority Of India,<br/>Rajiv Gandhi Bhawan,Safdarjung Airport,New Delhi-3</td>
</tr>
<tr>
	<td class="label">Code No. of the Establishment:</td>
	<td class="Data">DL/36478</td>
</tr>
<%
	String aptName = "";
	String airportCode = "";
	int srlno = 0;
	ArrayList alist = new ArrayList();
	ArrayList dataList = new ArrayList();

		
	if(request.getAttribute("dataList")!=null){
		dataList=(ArrayList)request.getAttribute("dataList");
	}
%>

<tr>
	<td  colspan=2> 
		<table width=100% border=1 bordercolor="gray" cellpadding=2 cellspacing=0>
			<tr>
			<%
				for(int i=0;i<labelAry.length;i++){
					out.println("<th class='label'>"+labelAry[i]+"</th>");
				}
			%>
			</tr>
			<tr>
			<%
				for(int i=0;i<labelAry.length;i++){
					out.println("<th class='label'>"+(i+1)+"</th>");
				}
			%>
			</tr>
			
			<%
				int recCount = 0;
				for(int k=0;k<dataList.size();k++){
					formsetBean=(form3Bean)dataList.get(k);
					srlno++;
					recCount++;
			%>
				<tr>
					<td class="Data" width="2%"><%=srlno%></td>
					<td class="Data" width="5%"><%="DL/36478/"+formsetBean.getPensionNumber()%></td>
				
					<td class="Data" width="10%"><%=formsetBean.getEmployeeName()%></td>
				
					<td class="Data" width="10%"><%=formsetBean.getFamilyMemberName()%></td>
					<td class="Data" width="5%"><%=formsetBean.getAgeAtEntry()%></td>
					<td class="Data" width="5%"><%=formsetBean.getDateOfBirth()%></td>
					<td class="Data" width="5%"><%=formsetBean.getSex()%></td>
					<td class="Data" width="5%"><%=formsetBean.getDateOfEntitle()%></td>
					<td class="Data" width="15%"><%=formsetBean.getRemarks()%></td>
				</tr>
			<%
				}
				
				if(recCount==0){
					out.println("<tr>");
					out.println("<Td Colspan='"+labelAry.length+"' class='data' align=center height=40><font color=blue> No Details Found</font></Td></Tr>");
					out.println("</tr>");
				}
			%>
		</table>
	</td>
</tr>



<tr>
	<td colspan=2 height=30>&nbsp;</td>
</tr>
<tr>
	<td class="tb">Date:<br/>Station :</td>
	
</tr>
<tr>
	<td width=65%%></td>
	<td class="tb"><%=signatory%></td>
</tr>
<tr>
	<td colspan=2 height=50>&nbsp;</td>
</tr>
<tr>
	<td colspan=2><HR></td>
</tr>
<tr>
	<td  colspan=2 class="tb" align=left><%=note%></td>
</tr>
</TABLE>
</body >
</html>