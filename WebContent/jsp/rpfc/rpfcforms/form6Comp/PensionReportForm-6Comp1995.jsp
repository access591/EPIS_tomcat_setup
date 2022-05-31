

<%@ page language="java"
import="java.util.ArrayList,com.epis.bean.rpfc.DatabaseBean,com.epis.utilities.CommonUtil,java.text.DecimalFormat"
contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%> 
<%@ page import="com.epis.bean.rpfc.EmployeeValidateInfo,com.epis.bean.rpfc.Form8RemittanceBean,com.epis.bean.rpfc.EmployeePersonalInfo,com.epis.bean.rpfc.form3Bean"%> 
<%@ page import="com.epis.services.rpfc.FinancialService,com.epis.services.rpfc.FinancialReportService"%> 
<%
 	String path = request.getContextPath(); 
 	String basePath =
	request.getScheme() + "://" + request.getServerName() + ":" +
	request.getServerPort() + path + "/";
	
	String labelAry[] = null;
	String subLabelAry[] = null;
	
%> 



<html>
<HEAD>
<title>AAI</title>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	

</HEAD>
<body>
<form action="method">
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	
	<%
		String reportType="",sortingOrder="cpfaccno";
		DecimalFormat df = new DecimalFormat("#########0.0000");
		//if(request.getAttribute("airportList")==null){
		if(request.getAttribute("region")!=null){
		int srlno=0;
		String region="";
		String frmAirportCode = "";
		CommonUtil commonUtil=new CommonUtil();
		ArrayList regionList = new ArrayList();
		
		if(request.getAttribute("region")!=null){
		regionList = (ArrayList) request.getAttribute("region");
		}
		
	 if (request.getParameter("frm_reportType") != null) {

				reportType = (String) request.getParameter("frm_reportType");

				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
		
					String fileName = "form-6-report.xls";

					response.setContentType("application/vnd.ms-excel");

					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);

				}
				

			}
		
		FinancialService financeService=new FinancialService();
		FinancialReportService finReportService=new FinancialReportService();
		
		ArrayList dataList=new ArrayList();
		ArrayList dateList=new ArrayList();
		form3Bean formsetBean=null;
		String formDate="",toDate="",loadYear="",formattedFromDate="",formattedToDate="",diplayMessage="";
		formDate=(String)request.getAttribute("fromDate");
		toDate=(String)request.getAttribute("toDate");
		diplayMessage=(String)request.getAttribute("diplayMessage");
		try{
			formattedFromDate=commonUtil.converDBToAppFormat(formDate,"dd-MMM-yyyy","MMMM-yy");
		formattedToDate=commonUtil.converDBToAppFormat(toDate,"dd-MMM-yyyy","MMMM-yy");
		}catch (Exception e1) {
                 // TODO Auto-generated catch block
                 e1.printStackTrace();
        }
	
		dateList=(ArrayList)request.getAttribute("dateList");
		String airportCode="",loadNextYear="";
		//loadYear=commonUtil.validateNumber(formDate.toCharArray());
	//	String [] yearList=loadYear.split(",");
		//loadYear=yearList[0];
		//loadNextYear=Integer.toString(Integer.parseInt(yearList[0])+1);
		//System.out.println("formDate is ::::::::::::::::: "+formDate);*/
	

		int totalSubscriber=0;
				double totalEmoluments=0.0,totalContribution=0.0;

	%>
				<% 
			int totalCnt = 0;
			Form8RemittanceBean remittanceBean=new Form8RemittanceBean();
		
			boolean chkFormFlag=true;
			String totalYearContri="",totalYearEmolnts="",aprMnth="",mayMnth="",junMnth="",julMnth="",augMnth="",sepMnth="",octMnth="",novMnth="",decMnth="",janMnth="",febMnth="",marMnth="";
			double aprContr=0.0,mayContr=0.0,junContr=0.0,julContr=0.0,augContr=0.0,sepContr=0.0,octContr=0.0,novContr=0.0,decContr=0.0,janContr=0.0,febContr=0.0,marContr=0.0;
			double aprEmoluments=0.0,mayEmoluments=0.0,junEmoluments=0.0,julEmoluments=0.0,augEmoluments=0.0,sepEmoluments=0.0,octEmoluments=0.0,novEmoluments=0.0,decEmoluments=0.0,janEmoluments=0.0,febEmoluments=0.0,marEmoluments=0.0;
			int empsubcnt=0, aprCnt=0,mayCnt=0,junCnt=0,julCnt=0,augCnt=0,sepCnt=0,octCnt=0,novCnt=0,decCnt=0,janCnt=0,febCnt=0,marCnt=0;
			String dates="";
			String [] mnthArry=null;
			double totalContr=0.0,totalEmol=0.0;
			for (int rglist = 0; rglist < regionList.size(); rglist++) {
				region = (String) regionList.get(rglist);
				for(int dtLst=0;dtLst<dateList.size();dtLst++){
				dates=(String)dateList.get(dtLst);
				remittanceBean=finReportService.remittanceform6Report(dates,sortingOrder,region,"",chkFormFlag,false);
				mnthArry=remittanceBean.getAprMnth().split("-");
				aprMnth=mnthArry[0];
				mnthArry=null;
				mnthArry=remittanceBean.getMayMnth().split("-");
				mayMnth=mnthArry[0];
				mnthArry=remittanceBean.getJunMnth().split("-");
				junMnth=mnthArry[0];
				mnthArry=null;
				mnthArry=remittanceBean.getJulMnth().split("-");
				julMnth=mnthArry[0];
				mnthArry=null;
			
				mnthArry=remittanceBean.getAugMnth().split("-");
				augMnth=mnthArry[0];
				mnthArry=null;
				mnthArry=remittanceBean.getSepMnth().split("-");
				sepMnth=mnthArry[0];
				mnthArry=remittanceBean.getOctMnth().split("-");
				octMnth=mnthArry[0];
				mnthArry=null;
				mnthArry=remittanceBean.getNovMnth().split("-");
				novMnth=mnthArry[0];
				mnthArry=null;
				mnthArry=remittanceBean.getDecMnth().split("-");
				decMnth=mnthArry[0];
				mnthArry=null;
				mnthArry=remittanceBean.getJanMnth().split("-");
				janMnth=mnthArry[0];
				mnthArry=null;
				mnthArry=remittanceBean.getFebMnth().split("-");
				febMnth=mnthArry[0];
				mnthArry=null;
				mnthArry=remittanceBean.getMarMnth().split("-");
				marMnth=mnthArry[0];
				mnthArry=null;
				System.out.println(aprMnth+"Apr Month"+remittanceBean.getAprMnth());
				if(aprMnth.equals("Apr")){
				
					aprContr=aprContr+remittanceBean.getTotalAprContribution();
					aprEmoluments=aprEmoluments+remittanceBean.getTotalAprEmoluments();
					aprCnt=aprCnt+remittanceBean.getAprCnt();
				}
				if(mayMnth.equals("May")){
					mayContr=mayContr+remittanceBean.getTotalMayContribution();
					mayEmoluments=mayEmoluments+remittanceBean.getTotalMayEmoluments();
					mayCnt=mayCnt+remittanceBean.getMayCnt();
				}
				if(junMnth.equals("Jun")){
					junContr=junContr+remittanceBean.getTotalJunContribution();
					junEmoluments=junEmoluments+remittanceBean.getTotalJunEmoluments();
					junCnt=junCnt+remittanceBean.getJunCnt();
				}
				if(julMnth.equals("Jul")){
					julContr=julContr+remittanceBean.getTotalJulContribution();
					julEmoluments=julEmoluments+remittanceBean.getTotalJulEmoluments();
					julCnt=julCnt+remittanceBean.getJulCnt();
				}
		
				if(augMnth.equals("Aug")){
					augContr=augContr+remittanceBean.getTotalAugContribution();
					augEmoluments=augEmoluments+remittanceBean.getTotalAugEmoluments();
					augCnt=augCnt+remittanceBean.getAugCnt();
				}
				if(sepMnth.equals("Sep")){
					sepContr=sepContr+remittanceBean.getTotalSepContribution();
					sepEmoluments=sepEmoluments+remittanceBean.getTotalSepEmoluments();
					sepCnt=sepCnt+remittanceBean.getSepCnt();
				}
				if(octMnth.equals("Oct")){
					octContr=octContr+remittanceBean.getTotalOctContribution();
					octEmoluments=octEmoluments+remittanceBean.getTotalOctEmoluments();
					octCnt=octCnt+remittanceBean.getOctCnt();
				}
					
				if(novMnth.equals("Nov")){
					novContr=novContr+remittanceBean.getTotalNovContribution();
					novEmoluments=novEmoluments+remittanceBean.getTotalNovEmoluments();
					System.out.println("amount is "+novEmoluments);
					//novEmoluments=novEmoluments*2;
					 novEmoluments=novEmoluments;
					novCnt=novCnt+remittanceBean.getNovCnt();
				}			
				
				if(decMnth.equals("Dec")){
					decContr=decContr+remittanceBean.getTotalDecContribution();
					decEmoluments=decEmoluments+remittanceBean.getTotalDecEmoluments();
					decCnt=decCnt+remittanceBean.getDecCnt();
				}
				if(janMnth.equals("Jan")){
					janContr=janContr+remittanceBean.getTotalJanContribution();
					janEmoluments=janEmoluments+remittanceBean.getTotalJanEmoluments();
					janCnt=janCnt+remittanceBean.getJanCnt();
				}
				if(febMnth.equals("Feb")){
					febContr=febContr+remittanceBean.getTotalFebContribution();
					febEmoluments=febEmoluments+remittanceBean.getTotalFebEmoluments();
					febCnt=febCnt+remittanceBean.getFebCnt();
				}
				if(marMnth.equals("Mar")){
					marContr=marContr+remittanceBean.getTotalMarContribution();
					marEmoluments=marEmoluments+remittanceBean.getTotalMarEmoluments();
					marCnt=marCnt+remittanceBean.getMarCnt();
				}	
				
				
				if(!remittanceBean.getTotalContr().equals("")){
				totalYearContri=remittanceBean.getTotalContr();
				}else{
				totalYearContri="0";
				}
				
				if(!remittanceBean.getTotalEmnts().equals("")){
				totalYearEmolnts=remittanceBean.getTotalEmnts();
				}else{
				totalYearEmolnts="0";
				}
				
				totalContr=aprContr+mayContr+junContr+julContr+augContr+sepContr+octContr+novContr+decContr+janContr+febContr+marContr;
				totalEmol=aprEmoluments+mayEmoluments+junEmoluments+julEmoluments+augEmoluments+sepEmoluments+octEmoluments+novEmoluments+decEmoluments+janEmoluments+febEmoluments+marEmoluments;
				srlno++;
				}}
				if(aprCnt!=0){
				empsubcnt=aprCnt;
				}else if(mayCnt!=0){
				empsubcnt=mayCnt;
				}else if(junCnt!=0){
				empsubcnt=junCnt;
				}else if(julCnt!=0){
				empsubcnt=julCnt;
				}else if(augCnt!=0){
				empsubcnt=augCnt;
				}else if(sepCnt!=0){
				empsubcnt=sepCnt;
				}else if(octCnt!=0){
				empsubcnt=octCnt;
				}else if(novCnt!=0){
				empsubcnt=novCnt;
				}else if(decCnt!=0){
				empsubcnt=decCnt;
				}else if(janCnt!=0){
				empsubcnt=janCnt;
				}else if(febCnt!=0){
				empsubcnt=febCnt;
				}else if(marCnt!=0){
				empsubcnt=marCnt;
				}
				
				%>
	<tr>
		<td colspan="2">
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			align="center">
			<tr>
				<td  colspan=2 class="reportlabel" align=center>FORM-6(PS) </td>
			</tr>
			<tr>
				<td  colspan=2 class="label" align=center>(Paragraph 20 of the employees pension scheme, 1995)</td>
			</tr>
			<tr>
				<td  colspan=2 class="label"  align=right>Total No. of Employees__________________</font></td>
			</tr>
			<tr>
				<td  colspan=2 class="reportlabel"  align=center>STATEMEMT OF CONTRIBUTION FOR MONTH OF <%=formattedFromDate.toUpperCase()%></font></td>
			</tr>
			<tr>
				<td class="label" colspan=2 align="right">&nbsp;</td>
			</tr>
			<tr>
			
				<td class="label" colspan=2 align="right">Total No.of suscribers: <%=empsubcnt%></td>
			</tr>
			
			<tr>
				<td class="label" width="35%">Name & Address of Establishment:</td>
				<td class="Data" align="left">Airports Authority Of India,Rajiv Gandhi Bhawan,Safdarjung Airport,New Delhi-3</td>
			</tr>
			<tr>
				<td class="label" width="35%">Currency period from</td>
				<td class="Data" align="left"><%=diplayMessage%></td>
				

			</tr>
			<tr>
				<td class="label">Code No. of the Establishment:</td>
				<td class="Data">DL/36478</td>
				
			</tr>
		   <tr>
				<td class="label">Statutory Rate of Contribution:</td>
				<td class="Data"> @ 1.16%</td>
			</tr>
	
			<tr>
				<td colspan=2 height=20>&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>

	
	
	<tr>
		<td align=center>
		<table width="100%" border="1" bordercolor="gray" cellpadding="2"
			cellspacing="0">
	



	
			<tr>
				
				<th class='label' rowspan=2>Total No.of subscribers</th>
				<th class='label' rowspan=2>Wages or which contrubutions are payable</th>
				<th class='label' colspan=3>Amount of contribution due @ 1.16%</th>
				<th class='label' colspan=3>Amount of contrubution remitted in A/c No 10</th>
				<th class='label' rowspan=2>Date of remittence </th>
				<th class='label' rowspan=2 nowrap="nowrap">Name and address of the bank<br/> in which amount is remitted</th>
				<th class='label' rowspan=2>Triplicate copy of the challan to be enclosed </th>
				<th class='label' rowspan=2>Remarks</th>
			</tr>
			<tr>
			<td class='label' >Employees'</td>
			<td class='label' >Employers'</td>
			<td class='label' >Total</td>
			<td class='label' >Employees'</td>
			<td class='label' >Employers'</td>
			<td class='label' >Total</td>
		
		
			</tr>
		
			<tr>
			<%
				for(int i=1;i<=12;i++){
					out.println("<th class='label'>"+i+"</th>");
				}
			%>
			</tr>
	
			<%if(aprEmoluments!=0 && aprContr!=0 && aprCnt!=0){%>
			<tr>
				
				<td class="NumData" width="10%"><%=aprCnt%></td>
				<td class="NumData" width="15%"><%=df.format(aprEmoluments)%></td>
				<td class="NumData" width="15%"><%=aprContr/2%></td>
				<td class="NumData" width="15%"><%=aprContr/2%></td>
				<td class="NumData" width="15%"><%=aprContr%></td>
				<td class="NumData" width="15%"><%=aprContr/2%></td>
				<td class="NumData" width="15%"><%=aprContr/2%></td>
				<td class="NumData" width="15%"><%=aprContr%></td>
				<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
			</tr>
			<%}%>
			<%if(mayEmoluments!=0 && mayContr!=0 && mayCnt!=0){%>
			<tr>
				
				<td class="NumData" width="10%"><%=mayCnt%></td>
				<td class="NumData" width="15%"><%=df.format(mayEmoluments)%></td>
				<td class="NumData" width="15%"><%=mayContr/2%></td>
				<td class="NumData" width="15%"><%=mayContr/2%></td>
				<td class="NumData" width="15%"><%=mayContr%></td>
				<td class="NumData" width="15%"><%=mayContr/2%></td>
				<td class="NumData" width="15%"><%=mayContr/2%></td>
				<td class="NumData" width="15%"><%=mayContr%></td>
				<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
			</tr>
			<%}%>
			<%if(junContr!=0 && junEmoluments!=0 && junCnt!=0){%>
			<tr>
			
				<td class="NumData" width="10%"><%=junCnt%></td>
				<td class="NumData" width="15%"><%=df.format(junEmoluments)%></td>
				<td class="NumData" width="15%"><%=junContr/2%></td>
				<td class="NumData" width="15%"><%=junContr/2%></td>
				<td class="NumData" width="15%"><%=junContr%></td>
				<td class="NumData" width="15%"><%=junContr/2%></td>
				<td class="NumData" width="15%"><%=junContr/2%></td>
				<td class="NumData" width="15%"><%=junContr%></td>
					<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
				
			</tr>
			<%}%>
			<%if(julCnt!=0 && julEmoluments!=0 && julContr!=0){%>
			<tr>
			
				<td class="NumData" width="10%"><%=julCnt%></td>
				<td class="NumData" width="15%"><%=df.format(julEmoluments)%></td>
				<td class="NumData" width="15%"><%=julContr/2%></td>
				<td class="NumData" width="15%"><%=julContr/2%></td>
				<td class="NumData" width="15%"><%=julContr%></td>
				<td class="NumData" width="15%"><%=julContr/2%></td>
				<td class="NumData" width="15%"><%=julContr/2%></td>
				<td class="NumData" width="15%"><%=julContr%></td>
				<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
				
			</tr>
			<%}%>
			<%if(augCnt!=0 && augEmoluments!=0 && augContr!=0){%>
			<tr>
				
				<td class="NumData" width="10%"><%=augCnt%></td>
				<td class="NumData" width="15%"><%=df.format(augEmoluments)%></td>
				<td class="NumData" width="15%"><%=augContr/2%></td>
				<td class="NumData" width="15%"><%=augContr/2%></td>
				<td class="NumData" width="15%"><%=augContr%></td>
				<td class="NumData" width="15%"><%=augContr/2%></td>
				<td class="NumData" width="15%"><%=augContr/2%></td>
				<td class="NumData" width="15%"><%=augContr%></td>
				<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
				
			</tr>
			<%}%>
			<%if(sepCnt!=0 && sepEmoluments!=0 && sepContr!=0){%>
			<tr>
				
				<td class="NumData" width="10%"><%=sepCnt%></td>
				<td class="NumData" width="15%"><%=df.format(sepEmoluments)%></td>
				<td class="NumData" width="15%"><%=sepContr/2%></td>
				<td class="NumData" width="15%"><%=sepContr/2%></td>
				<td class="NumData" width="15%"><%=sepContr%></td>
				<td class="NumData" width="15%"><%=sepContr/2%></td>
				<td class="NumData" width="15%"><%=sepContr/2%></td>
				<td class="NumData" width="15%"><%=sepContr%></td>
				<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
				
			</tr>
			<%}%>
			<%if(octEmoluments!=0 && octContr!=0 && octCnt!=0){%>
			<tr>
				
				<td class="NumData" width="10%"><%=octCnt%></td>
				<td class="NumData" width="15%"><%=df.format(octEmoluments)%></td>
				<td class="NumData" width="15%"><%=octContr/2%></td>
				<td class="NumData" width="15%"><%=octContr/2%></td>
				<td class="NumData" width="15%"><%=octContr%></td>
				<td class="NumData" width="15%"><%=octContr/2%></td>
				<td class="NumData" width="15%"><%=octContr/2%></td>
				<td class="NumData" width="15%"><%=octContr%></td>
				<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
				
			</tr>
			<%}%>
			<%if(novCnt!=0 && novEmoluments!=0 && novContr!=0){%>
			<tr>
				
				<td class="NumData" width="10%"><%=novCnt%></td>
				<td class="NumData" width="15%"><%=df.format(novEmoluments)%></td>
				<td class="NumData" width="15%"><%=novContr/2%></td>
				<td class="NumData" width="15%"><%=novContr/2%></td>
				<td class="NumData" width="15%"><%=novContr%></td>
				<td class="NumData" width="15%"><%=novContr/2%></td>
				<td class="NumData" width="15%"><%=novContr/2%></td>
				<td class="NumData" width="15%"><%=novContr%></td>
				<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
				
			</tr>
			<%}%>
			<%if(decCnt!=0 && decEmoluments!=0 && decContr!=0){%>
			<tr>
				
				<td class="NumData" width="10%"><%=decCnt%></td>
				<td class="NumData" width="15%"><%=df.format(decEmoluments)%></td>
				<td class="NumData" width="15%"><%=decContr/2%></td>
				<td class="NumData" width="15%"><%=decContr/2%></td>
				<td class="NumData" width="15%"><%=decContr%></td>
				<td class="NumData" width="15%"><%=decContr/2%></td>
				<td class="NumData" width="15%"><%=decContr/2%></td>
				<td class="NumData" width="15%"><%=decContr%></td>
				<td class="Data" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="25%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="Data" width="10%">Yes</td>
				<td class="Data" width="15%">---</td>
			</tr>

		
			<%}%>
			
				<tr>
				<td class="Data" width="10%"  colspan=1>Total</td>
				<td class="NumData" width="15%"><%=df.format(totalEmol)%></td>
				<td class="NumData" width="15%" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="NumData" width="15%"><%=df.format(totalContr)%></td>
				<td class="NumData" width="15%" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class="NumData" width="15%"><%=df.format(totalContr)%></td>
				
				<td class="Data" width="50%" colspan=5>---</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td height=30>&nbsp;</td>
	</tr>
	
	
	
	<tr>
		<td align=center>
		<table width="80%" cellpadding="0" cellspacing="0" align=center>
			<tr>
				<td class="label" width=70%>Date:______________</td>
				<td colspan="4" align="right" class="label">Signature of the employer or other <br>authorised officer of the Establishment</td>
			</tr>
			<tr>
				<td class="label">Note:(1)If there is any substantial variation between the wages and contribution shown above and those shown in the last month's return, suitable explanation should be given the Remarks column.
				<br><br>
				(2) If any arrears of contribution or damages are included in the figures under Column 4 suitable details indicating the circumstances, amount, No.of Subscribers and the period involved should be furnished in the 'Remarks' column or on the reverse.
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	 <%}%>

</table>
</form>
</body>
</html>
