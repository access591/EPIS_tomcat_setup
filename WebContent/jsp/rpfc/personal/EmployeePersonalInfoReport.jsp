<%@ page language="java" import="java.util.*,com.epis.bean.rpfc.DatabaseBean,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>

<%@ page
	import="com.lowagie.text.pdf.*,com.lowagie.text.Phrase,com.lowagie.text.PageSize,com.lowagie.text.List,com.lowagie.text.ListItem,com.lowagie.text.Cell,com.lowagie.text.Table,com.lowagie.text.Section,com.lowagie.text.Font,com.lowagie.text.Chapter,com.lowagie.text.Paragraph,com.lowagie.text.Document,com.lowagie.text.FontFactory,java.io.*,com.lowagie.text.Image,java.awt.Color,com.lowagie.text.pdf.PdfPTable,com.lowagie.text.pdf.PdfPCell,com.lowagie.text.Element,com.lowagie.text.Rectangle,java.sql.ResultSet,com.lowagie.text.Chunk,com.lowagie.text.HeaderFooter"%>
<%@ page import="com.lowagie.text.pdf.TextField"%>
<%@ page import="com.lowagie.text.pdf.PdfName"%>
<%@ page import="com.epis.bean.rpfc.EmployeeValidateInfo"%>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo"%>
<%@ page import="com.epis.services.rpfc.FinancialService"%>
<%@ page import="java.util.ArrayList" %>


<jsp:useBean id="empBean" class="com.epis.bean.rpfc.EmpMasterBean" scope="request">
	<jsp:setProperty name="empBean" property="*" />
</jsp:useBean>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
					%>
<%@ page import="javax.sound.sampled.DataLine"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HEAD>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
<script type="text/javascript">
   
</script>
</HEAD>
<body>
<form action="method">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center">
					<table  border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
					<tr>
					<td ><img src="<%=basePath%>/images/logoani.gif" width="65" height="70">
					</td>
					<td class="label" align=center valign="top" nowrap="nowrap"><font color='black' size='4' face='Helvetica'>
						AIRPORTS AUTHORITY OF INDIA
						</font></td>
						
					</tr>
					</table>
					</td>
				</tr>
		<%
			ArrayList dataList = new ArrayList();
			String reportType="",region="",fileName="";
			
			if(request.getAttribute("reportList")!=null){
				dataList=(ArrayList)request.getAttribute("reportList");
			}
			if(request.getAttribute("region")!=null){
				region=(String)request.getAttribute("region");
			}
			if(request.getAttribute("reportType")!=null){
				reportType=(String)request.getAttribute("reportType");
				if(region.equals("")){
				region="All_Regions";
				}
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
					
					fileName = "Employee_Personal_report_"+region+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
			
				
			%>
				<tr>
					<td colspan="2">
						<table  border=0 cellpadding=0 cellspacing=0 width="100%" align="center" valign="middle">
						<tr>
							<td align="center" class="reportlabel">Employee Personnel Information  -  <%=region%></td>
						</tr>
						<tr>
							<td align="center" class="reportlabel"><br/></td>
						</tr>
				
						</table>
					</td>
				</tr>
					
				
				<tr>
					<td>
						<table width="100%"  border="0" cellpadding="0" cellspacing="0">
						
						<tr>
							<td >
								<table width="100%"  border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
						
											
						</table>
					</td>
					
					
				</tr>
					<tr>
							<td class="Data">Note: PFID having incomplete data is  heighleted in Blue color</td>
						</tr>
						
				<tr>
					<td>
						<table  border="1" bordercolor="gray" cellpadding="2" cellspacing="0">
							<tr>
								<td class="label" >Serial</br> Number</td>
								<td class="label" >PF ID</td>
								<td class="label" >PF ID</td>
								<td class="label">Old CPF ACC.No </td>
								<td class="label" >Employee</br> Number</td>
								<td class="label">Employee Name</td>
								<td class="label" >Designation</td>
								<td class="label" nowrap="nowrap">Date Of Birth</td>
								<td class="label" nowrap="nowrap">Date Of Joining</td>
								<td class="label" >Father's /Husband's Name</td>
								<td class="label" >Pension Option  <br/></td>
								<td class="label" >Transfer Status  <br/></td>
								<td class="label" >Gender</td>
								<td class="label" >Division</td>
								<td class="label" >AirportCode</td>
								<td class="label">Region </td>
							
								<td class="label" nowrap="nowrap">Remarks  </td>
								</tr>
						
				<%
				if (dataList.size() != 0) {
				int count = 0;
				
				EmployeePersonalInfo beans=null;
				String styleClass="";
				for(int k=0;k<dataList.size();k++){
					count++;
					beans = (EmployeePersonalInfo) dataList.get(k);
				
					if(beans.getDateOfBirth().equals("---")||beans.getEmployeeName().equals("---")||beans.getWetherOption().equals("---")||beans.getFhName().equals("---")){
						styleClass="HighlightData";
					}else{
						styleClass="Data";
					}
					//lastActive=beans.getDateofJoining();
				%>
				
		
			
							<tr>
								
								<td class="<%=styleClass%>" ><%=count%></td>
								<td class="<%=styleClass%>" ><%=beans.getPfID()%></td>
								<td class="<%=styleClass%>" ><%=beans.getPensionNo()%></td>
								<td class="<%=styleClass%>"><%=beans.getCpfAccno()%></td>
								<td class="<%=styleClass%>" ><%=beans.getEmployeeNumber()%></td>
								<td class="<%=styleClass%>"  nowrap="nowrap"><%=beans.getEmployeeName()%></td>
								<td class="<%=styleClass%>" nowrap="nowrap"> <%=beans.getDesignation()%></td>
								<td class="<%=styleClass%>" nowrap="nowrap"><%=beans.getDateOfBirth()%></td>
								<td class="<%=styleClass%>" nowrap="nowrap"> <%=beans.getDateOfJoining()%></td>
								<td class="<%=styleClass%>" nowrap="nowrap"> <%=beans.getFhName()%></td>
								<td class="<%=styleClass%>" ><%=beans.getWetherOption()%></td>
								<td class="<%=styleClass%>" > <%=beans.getTransferFlag()%></td>
								<td class="<%=styleClass%>" > <%=beans.getGender()%></td>
								<td class="<%=styleClass%>" > <%=beans.getDivision()%></td>
								<td class="<%=styleClass%>"  nowrap="nowrap"><%=beans.getAirportCode()%></td>
								<td class="<%=styleClass%>" ><%=beans.getRegion()%></td>
								<td class="<%=styleClass%>" > <%=beans.getRemarks()%></td>
								
							</tr>
							
					
						<%}}%>
						</table>
						
						</td>
						</tr>
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
						
						
						
				
				
			</table>
		</form>
</body>
</html>