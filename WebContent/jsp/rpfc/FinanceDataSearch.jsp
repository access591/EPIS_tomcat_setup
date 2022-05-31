<!--
/*
  * File       : FinanceDataSearch.jsp
  * Date       : 05/02/2009
  * Author     : AIMS 
  * Description: 
  * Copyright (2008) by the Navayuga Infotech, all rights reserved.
  */
-->


<%@ page import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants"%>
<%@ page import="com.epis.bean.rpfc.DatabaseBean,com.epis.bean.rpfc.SearchInfo,com.epis.bean.rpfc.BottomGridNavigationInfo"%>
<%@ page import="com.epis.bean.rpfc.FinacialDataBean"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
					
    String region="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
	hashmap=common.getRegion();

	Set keys = hashmap.keySet();
	Iterator it = keys.iterator();
	

  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<LINK rel="stylesheet" href="<%=basePath%>PensionView/css/aai.css" type="text/css">
		<SCRIPT type="text/javascript" src="<%=basePath%>PensionView/scripts/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>PensionView/scripts/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>PensionView/scripts/DateTime.js"></SCRIPT>
		<script type="text/javascript"> 
		function redirectPageNav(navButton,index,totalValue){      
	
   		document.forms[0].action="<%=basePath%>psearch?method=financenavigation&navButton="+navButton+"&strtindx="+index+"&total="+totalValue;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	function openURL(sURL) { 
	//alert(sURL);
		window.open(sURL,"Window1","menubar=no,width='100%',height='100%',toolbar=no,fullscreen=yes");

	} 
	function hiLite(imgDocID, imgObjName, comment) {
	if (browserVer == 1) {
	document.images[imgDocID].src = eval(imgObjName + ".src");
	window.status = comment; return true;
	}}
	
	
	 function testSS(){
	 if(document.forms[0].cpfaccno.value=="" && document.forms[0].employeeCode.value==""){
	 alert('Please Enter Cpf Accno or Employee Number ');
	 document.forms[0].cpfaccno.focus();
	 return false;
	 }
	    if(!document.forms[0].fromDt.value==""){
   		    var date1=document.forms[0].fromDt;
   	        var val1=convert_date(date1);
   		    if(val1==false)
   		     {
   		      return false;
   		     }
   		    }
   		     if(!document.forms[0].toDt.value==""){
   		    var date1=document.forms[0].toDt;
   	        var val1=convert_date(date1);
   		    if(val1==false)
   		     {
   		      return false;
   		     }
   		    }
   		    
   		 //  alert(document.forms[0].region.selectedIndex);
   	     if(document.forms[0].region.selectedIndex==1 ||document.forms[0].region.selectedIndex==3 || document.forms[0].region.selectedIndex==0){
   	//  alert("Developement Activities are in Progress");
   		//  return false;
   		  }

    	document.forms[0].action="<%=basePath%>psearch?method=financesearch";
    	
		document.forms[0].method="post";
		document.forms[0].submit();
   		 }
   		 
   		 function callReport()
   		 {  
   		 window.open ("<%=basePath%>PensionView/FinanceReports.jsp","mywindow","menubar=yes,status=yes,location=yes,toolbar=yes,scrollbars=yes,width=800,height=600,resizable=yes"); 
   		 	//document.forms[0].action="./PensionFundReport.jsp"
			//document.forms[0].method="post";
			//document.forms[0].submit();
   		
   		 }
   		
   		 
   		 function callReport1()
   		 {
   	    window.open ("<%=basePath%>PensionView/FinanceDataMissingMonthSearch.jsp","mywindow","menubar=yes,status=1,toolbar=1,channelmode=yes,resizable=yes"); 
   		
   		 }
   		  function submit_Pension(effectiveDt,employeeName,cpfaccno,employeeno,designation,unitCD){
   				 var path="";
 				 if(cpfaccno=='' || cpfaccno==null){
					    alert('cpfaccno Cannot be blank');
					    return false;
				 }
				 if(employeeName=='' || employeeName==null){
					    alert('Employee Name Cannot be blank');
					    return false;
			     }
				
			  	
    
   // alert(cpfaccno+'aaiPF'+aaiPF+'aaiPension'+aaiPension+'aaiTotal'+aaiTotal+'unitCD'+unitCD);
    path="<%=basePath%>validatefinance?method=getPensionDetail&cpfaccno="+cpfaccno+"&airportCD="+unitCD+"&effectiveDate="+effectiveDt+"&employeeNM="+employeeName+"&employeeno="+employeeno+"&designation="+designation;
    window.open (path,"","resizable=1,width=550,height=450"); 
  
    	
    }
    
    function submit_Pension(effectiveDt,employeeName,cpfaccno,employeeno,designation,unitCD,missingflag,region){
   				 var path="";
   				 if(missingflag=='N'){
   				  
 				 if(cpfaccno=='' || cpfaccno==null){
					    alert('cpfaccno Cannot be blank');
					    return false;
				 }
				 if(employeeName=='' || employeeName==null){
					    alert('Employee Name Cannot be blank');
					    return false;
			     }
				
			  	
   				 }
   				
    
   // alert(cpfaccno+'aaiPF'+aaiPF+'aaiPension'+aaiPension+'aaiTotal'+aaiTotal+'unitCD'+unitCD);
   		if(missingflag=='N'){
   		
   		    	path="<%=basePath%>validatefinance?method=getFinanceDetailEdit&cpfaccno="+cpfaccno+"&airportCD="+unitCD+"&effectiveDate="+effectiveDt+"&employeeNM="+employeeName+"&employeeno="+employeeno+"&designation="+designation+"&region="+region;
   		}else{
   				path="<%=basePath%>PensionView/AddFinancialDetail.jsp";
   		}
   	

    	document.forms[0].action=path;
		document.forms[0].method="post";
		document.forms[0].submit();
   		
  
    	
    }
    
	</script>
	</head>
	<body class="BodyBackground">
		<form method="post" action="<%=basePath%>psearch?method=financesearch">

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<jsp:include page="/PensionView/PensionMenu.jsp" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>

				<tr>

					<td height="5%" colspan="2" align="center" class="ScreenHeading">
						Finance Information
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<%boolean flag = false;%>
				<tr>
					<td>
						<table align="center">

							<tr>
								<td class="label">
									Employee No. :
								</td>
								<td>
									<input type="text" name="employeeCode">
								</td>
								<td class="label">
									CPFAcno:
								</td>
								<td>
									<input type="text" name="cpfaccno">
								</td>
								
							<!-- 	<td class="label">
									AirportCode:
								</td> -->
								<td>
									<input type="hidden" name="airPortCD">
								</td>
							</tr>
							
							<tr>
								<td class="label">
									From Date:
								</td>
								<td>
									<input type="text" name="fromDt">
									<a href="javascript:show_calendar('forms[0].fromDt');"><img src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
								</td>
									<td class="label">
									To Date:
								</td>
								<td>
									<input type="text" name="toDt">
									<a href="javascript:show_calendar('forms[0].toDt');"><img src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
								</td>
							</tr>
							<tr>
							<td class="label">
									Missing Data:
								</td>
								<td><select name="missingPF" tabindex="7">
											<option value='Y'>
											Yes
											</option>
											<option value='N'>
											No
											</option>
											</select>
											</td>
						<td class="label">
 						  Region:
						</td>
						<td>									
							<SELECT NAME="region" style="width:130px">
							<%
							int j=0;
                            while(it.hasNext()){
							  region=hashmap.get(it.next()).toString();
							  j++;
							 %>
							  <option ="<%=j%>" ><%=region%></option>
	                       <% }
							%>
							</SELECT>
						</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="1">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>

								<td>
									<input type="button" class="btn" value="Search" class="btn" onclick="testSS();">
									<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn">
									<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>

						<%
						FinacialDataBean dbBeans = new FinacialDataBean();
						SearchInfo getSearchInfo = new SearchInfo();
			System.out.println("request============"+request.getAttribute("searchBean"));
			if (request.getAttribute("searchBean") != null) {
			
				int totalData = 0;
				SearchInfo searchBean = new SearchInfo();
				flag = true;
				ArrayList dataList = new ArrayList();
				BottomGridNavigationInfo bottomGrid = new BottomGridNavigationInfo();
				searchBean = (SearchInfo) request.getAttribute("searchBean");
				dbBeans = (FinacialDataBean) request.getAttribute("searchInfo");
				int index = searchBean.getStartIndex();
				totalData = searchBean.getTotalRecords();
				System.out.println("totalData "+totalData);
				bottomGrid = searchBean.getBottomGrid();
				session.setAttribute("getSearchBean", dbBeans);
				dataList = searchBean.getSearchList();
			  region=(String)request.getAttribute("region");
				if (dataList.size() == 0) {

				%>
				<tr>

					<td>
						<table align="center" id="norec">
							<tr>
								<br>
								<td>
									<b> No Records Found </b>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<%} else if (dataList.size() != 0) {
				 System.out.println("Size===After========="+dataList.size());
				%>
				<tr>

					<td>
						<table align="center"  >
							<tr>
								<td colspan="3">
								</td>
								<td colspan="2" align="right">
								<!-- 	<input type="button" class="btn" alt="first" value="|<" name="First" disable=true onClick="javascript:redirectPageNav('|<','<%=index%>','<%=totalData%>')" <%=bottomGrid.getStatusFirst()%>>
									<input type="button" class="btn" alt="pre" value="<" name="Pre"  onClick="javascript:redirectPageNav('<','<%=index%>','<%=totalData%>')" <%=bottomGrid.getStatusPrevious()%>>
									<input type="button" class="btn" alt="next" value=">" name="Next" onClick="javascript:redirectPageNav('>','<%=index%>','<%=totalData%>')" <%=bottomGrid.getStatusNext()%>>
									<input type="button" class="btn" value=">|" name="Last" onClick="javascript:redirectPageNav('>|','<%=index%>','<%=totalData%>')" <%=bottomGrid.getStatusLast()%>> -->
								     <img src="./PensionView/images/printIcon.gif" alt="Report"  onClick="callReport()">
									<%}%>
								</td>
							</tr>
						</table>
					
				</tr>
				<tr>
					<td>
					
						<table align="center" cellpadding=2 class="tbborder" cellspacing="0" border="0">

							<tr class="tbheader">
								<td class="tblabel">
									AirportCode
								</td>
								<td class="tblabel">
									CPFAC.No&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									EmployeeName&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									EmployeeNo&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									Designation&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									Finance Date&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<!--<td class="tblabel">
									&nbsp;&nbsp;Emoluments &nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									PF&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									VPF
								</td>
								<td class="tblabel">
									EmpTotal
								</td>
								<td class="tblabel">
									FromDate
								</td>
								<td class="tblabel">
									Missing Flag
								</td>
								--><td class="tblabel">
									
								</td>
							
				<%int count = 0;
				  for (int i = 0; i < dataList.size(); i++) {
					count++;
					FinacialDataBean beans = (FinacialDataBean) dataList.get(i);%>

					
							<tr>
							
								<td class="Data">
									<%=beans.getAirportCode()%> 
								</td>
								<td class="Data">
									<%=beans.getCpfAccNo()%>
								</td>
								<td class="Data">
									<%=beans.getEmployeeName()%>
									&nbsp;&nbsp;
								</td>
								<td class="Data">
									<%=beans.getEmployeeNewNo()%>
									&nbsp;&nbsp;
								</td>
								<td class="Data">
									<%=beans.getDesignation()%>
								</td>
								<td class="Data">
									<%=beans.getMonthYear()%>
								</td>
								<td>
									<img alt="" src="<%=beans.getIcon()%>" onclick="javascript:submit_Pension('<%=beans.getMonthYear()%>','<%=beans.getEmployeeName()%>','<%=beans.getCpfAccNo()%>','<%=beans.getEmployeeNewNo()%>','<%=beans.getDesignation()%>','<%=beans.getAirportCode()%>','<%=beans.getMissingFlag()%>','<%=beans.getRegion()%>')">
								</td>
							</tr>
							<%}%>

							<%if (dataList.size() != 0) {%>
							<tr>
								<td>
			<font color="red"><%=index%></font> &nbsp;Of&nbsp;&nbsp;<font color="red"><%=totalData%></font>&nbsp;Records
								</td>
							</tr>
							<%}}
			%>

						</table>
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
