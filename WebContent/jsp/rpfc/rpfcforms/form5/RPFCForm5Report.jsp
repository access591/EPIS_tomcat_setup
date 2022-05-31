

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.form3Bean"%>
<%@ page import="java.util.ArrayList" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":"
		+ request.getServerPort()+ path + "/";
	System.out.println("basePath is ========= "+basePath);
%>



<Html><Head><Title>AAI</Title>
	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />

</Head>
<body text=#000000 leftMargin=0 topMargin=0 link="white" vlink="white" >

<table border=0 cellpadding=3 cellspacing=0 width="100%" align="center" >
	
<%
	ArrayList airportList = null;
	ArrayList dataList=new ArrayList();
	form3Bean formsetBean=null;
	
	String labelAry[] = null;

	String months[] = {"January","Febraury","March","April","May","June","July","August","September","October","November","December"};

	String monthNo[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	String monthCd[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	String year = "";
	
	String region = "";


	String date = "";
	String prevDate = "",shnPrevMnth="";

	String formno = "";
	String heading = "",heading1="",heading2="";
	String title = "";
	String note = "";
	String signatory = "Signature of the employer or other <br> authorised officer of the Establishment";

	
	String 	 reportType="";
		if (request.getParameter("frm_reportType") != null) {

				reportType = (String) request.getParameter("frm_reportType");

				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
		
					String fileName = "form-5-report.xls";

					response.setContentType("application/vnd.ms-excel");

					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);

				}

			}

	
	
	if(request.getAttribute("dataList")!=null){
		dataList=(ArrayList)request.getAttribute("dataList");
	}

	formno = "FORM-5 (EPS)";
	heading = "(For Exempted Establishments only)";
	heading1="THE EMPLOYEES PENSION SCHEME , 1995";
	heading2="Paragraph 20(2)";
	title = "RETURN OF MEMBERS LEAVING SERVICE";
	note = "Note: An employee who has attained the age of 58 years will cease to be a member of the scheme and entitled to receive the due benefit under the EPS-1995.";

	labelAry = new String[]{"Sl.No","Account Number","Name of the Member<br/>(in block capitals)","Father's name or Husband's name(in case of married women)","Date of Leaving service","Reasons for leaving service (see note given below)","Remarks"};

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
	<td  colspan=2 class="reportlabel" align=center><%=title%></td>
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
				int recCount = 0,srlno=0;
				for(int k=0;k<dataList.size();k++){
					formsetBean=(form3Bean)dataList.get(k);
					srlno++;
					recCount++;
			%>
				<tr>
					<td class="Data" width="2%"><%=srlno%></td>
					<td class="Data" width="12%"><%="DL/36478/"+formsetBean.getPensionNumber()%></td>
					<td class="Data" width="20%"><%=formsetBean.getEmployeeName().toUpperCase()%></td>
					<td class="Data" width="12%"><%=formsetBean.getFamilyMemberName()%></td>
					<td class="Data" width="12%"><%=formsetBean.getDateOfLeaving()%></td>
					<td class="Data" width="12%"><%=formsetBean.getLeavingReason()%></td>
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
	<td colspan=2 height=50>&nbsp;</td>
</tr>
<tr>
	<td colspan=2><HR></td>
</tr>
<tr>
	<td  colspan=2 class="tb" align=left><%=note%></td>
</tr>
<tr>
	<td colspan=2 height=50>&nbsp;</td>
</tr>
<tr>
	<td width=65%></td>
	<td class="tb"><%=signatory%></td>
</tr>
</TABLE>
</body >
</html>