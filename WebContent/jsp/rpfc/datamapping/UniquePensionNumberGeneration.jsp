<%@ page language="java"%>
<%@ page import="java.util.*,com.epis.utilities.*"%>
<%@ page import="com.epis.bean.rpfc.PensionBean"%>
<%@ page import="com.epis.bean.rpfc.EmpMasterBean"%>
<%@ page import="com.epis.bean.rpfc.*"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";

            String region = "";
            CommonUtil common = new CommonUtil();

            HashMap hashmap = new HashMap();
            hashmap = common.getRegion();

            Set keys = hashmap.keySet();
            Iterator it = keys.iterator();
            String empName1 = "",empSerialnumber="",pensionNumber="",transfertype="";
            String arrearinfo1 = "";
            if (session.getAttribute("getSearchBean1") != null) {
                session.removeAttribute("getSearchBean1");
                //session.setMaxInactiveInterval(1);
                //session.invalidate();
            }
            if (request.getParameter("mappedInfo") != null) {
                String mappedinfo = request.getParameter("mappedInfo")
                        .toString();
                out.println("mappedinfo " + mappedinfo);
            }

            %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<LINK rel="stylesheet" href="<%=basePath%>PensionView/css/aai.css" type="text/css">
		<link rel="StyleSheet" href="<%=basePath%>PensionView/css/styles.css" type="text/css" media="screen">
		<SCRIPT type="text/javascript" src="<%=basePath%>/PensionView/scripts/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>PensionView/scripts/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>PensionView/scripts/DateTime1.js"></SCRIPT>
		<script type="text/javascript"> 
	function resetValue(){
	   document.forms[0].action="<%=basePath%>PensionView/UniquePensionNumberGeneration.jsp";
	 	document.forms[0].method="post";
		document.forms[0].submit();
	}
	function test(cpfaccno,region,pensionNumber,employeeName,dateofbirth,empSerialNo,transfertype){
		document.forms[0].empName1.value=employeeName;
		document.forms[0].empName.value=employeeName;
		document.forms[0].empSerialnumber.value=empSerialNo;
		document.forms[0].pensionNumber.value=pensionNumber;
	//	document.forms[0].transfertype.value=transfertype;
		var reg = escape(region);
	
	
	var uniqueNumberGen="uniqueNumberGen";
	var tempArrearInfo=employeeName+"@"+cpfaccno+"@"+pensionNumber+"@"+region+"@"+empSerialNo+"@"+dateofbirth;
	tempArrearInfo = escape(tempArrearInfo);
	var cpfaccno=escape(cpfaccno);
	var arrearInfo1="<%=basePath%>search1?method=edit&cpfacno="+cpfaccno+"&region="+reg+"&editFrom="+uniqueNumberGen+"&arrearInfo="+tempArrearInfo;
	var arrearInfo=employeeName+"@"+"<a href="+arrearInfo1+">"+cpfaccno+"</a>"+"@"+pensionNumber+"@"+region+"@"+empSerialNo+"@"+dateofbirth;
	
		arrearInfo = escape(arrearInfo);
		var arrearInfo2=employeeName+"@"+"<a href="+arrearInfo1+">"+unescape(cpfaccno)+"</a>"+"@"+pensionNumber+"@"+region+"@"+empSerialNo+"@"+dateofbirth;
		document.forms[0].arrearInfo.value=arrearInfo2;
		document.getElementById("dispaly1").innerHTML=arrearInfo2;
		dispaly2.style.display="none"; 
		
		}
	function popupWindow(mylink, windowname)
		{
	    document.forms[0].arrearInfo.value="";
		if (! window.focus)return true;
		var href;
		if (typeof(mylink) == 'string')
		   href=mylink;
		else
		href=mylink.href;
		progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
		
		return true;
		}
		function validateForm1(empSerialNumber) {
	     var transfertype=document.forms[0].transfertype.value
	   
	    empSerialNumber=document.forms[0].empSerialnumber.value;
		if(empSerialNumber==""){
		 alert("Please Select Form3-2007-Sep Employee Name");
		return false;
		}
		
		var regionID="",airportcode="",reportType="",yearID="",monthID="",yearDesc="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		var empserialNO	=empserialNO;
		reportType="Html";
		yearID="NO-SELECT";
		var mappingFlag="true";
		var pfidStrip='1 - 1'; 
		var params = "&frm_region="+regionID+"&frm_airportcode="+airportcode+"&frm_year="+yearID+"&frm_reportType="+reportType+"&empserialNO="+empSerialNumber+"&transferStatus="+transfertype+"&mappingFlag="+mappingFlag+"&frm_pfids="+pfidStrip;
		
		var url="<%=basePath%>reportservlet?method=getReportPenContr"+params;
	
			if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   	 			}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   	 				 		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
							winOpened = true;
							wind1.window.focus();
   	 			}
		
	}
	function LoadWindow(params){
    var newParams =params;
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar= yes,statusbar=1,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
   }
		
	function validateForm(cpfaccno,region) {
		var regionID="",airportcode="",reportType="",yearID="",monthID="",yearDesc="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		reportType="Html";
		yearID="NO-SELECT";
		var mappingFlag="true";
		var pfidStrip='1 - 1'; 
		var params = "&frm_region="+region+"&frm_airportcode="+airportcode+"&frm_year="+yearID+"&frm_reportType="+reportType+"&cpfAccno="+cpfaccno+"&mappingFlag="+mappingFlag+"&frm_pfids="+pfidStrip;
		
		var url="<%=basePath%>reportservlet?method=getReportPenContr"+params;
			if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   	 			}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   	 				 		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
							winOpened = true;
							wind1.window.focus();
   	 			}
		
	}
	function redirectPageNav(navButton,index,totalValue){      
		
		var arrearInfo=document.forms[0].arrearInfo.value;
		document.forms[0].action="<%=basePath%>search1?method=uniquePensionNavigation&navButton="+navButton+"&strtindx="+index+"&total="+totalValue+"&arrearInfo="+arrearInfo;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	function editPensionMaster(obj,employeeName,employeeCode,region,airportCode){
	 	
		var cpfacno=obj;
		var answer =confirm('Are you sure, do you want edit this record');
	if(answer){
		var flag="true";
		var uniqueNumberGen="uniqueNumberGen";
		var arrearInfo=document.forms[0].arrearInfo.value;
		
		 arrearInfo = escape(arrearInfo);
		// alert(arrearInfo);
		document.forms[0].action="<%=basePath%>search1?method=edit&cpfacno="+cpfacno+"&name="+employeeName+"&flag="+flag+"&empCode="+employeeCode+"&region="+region+"&airportCode="+airportCode+"&editFrom="+uniqueNumberGen+"&arrearInfo="+arrearInfo;
 		document.forms[0].method="post";
		document.forms[0].submit();
	}
	}
	
    function testSS(){
    	
        if(document.forms[0].empName.value=="" && document.forms[0].dob.value=="" && document.forms[0].empSerialnumber.value==""&&document.forms[0].cpfaccno.value==""&&document.forms[0].empN0.value=="" && document.forms[0].doj.value==""){
            alert("Please Enter Any One Of The Fields");
            document.forms[0].cpfaccno.focus();
            return false;
                }
		var employeename=document.forms[0].empName.value;
		var dob=document.forms[0].dob.value;
		var doj=document.forms[0].doj.value;
		 if(!document.forms[0].dob.value==""){
	   		    var date1=document.forms[0].dob;
	   	        var val1=convert_date(date1);
	   		    if(val1==false)
	   		     {
	   		      return false;
	   		     }
	   		    }
		 if(!document.forms[0].doj.value==""){
	   		    var date1=document.forms[0].doj;
	   	        var val1=convert_date(date1);
	   		    if(val1==false)
	   		     {
	   		      return false;
	   		     }
	   		    }
		var employeeNo=document.forms[0].empN0.value;
		
		var arrearInfo=document.forms[0].arrearInfo.value;
		//alert(arrearInfo+arrearInfo);
	    arrearInfo = escape(arrearInfo);
	    document.forms[0].arrearInfo.value=arrearInfo;
	   	document.forms[0].action="<%=basePath%>search1?method=searchRecordsbyDobandName&frm_empnm="+employeename+"&arrearInfo="+arrearInfo+"&dob="+dob+"&employeeNo="+employeeNo;
		//alert(document.forms[0].action);
		document.forms[0].method="post";
		document.forms[0].submit();
   	 }
   	
     function popup(){
    
   
   if((document.getElementsByName("cpfno").checked!=true )||(document.getElementsByName("cpfno").checked==true));
	var x=document.getElementsByName("cpfno");
	var uncheckList="";

	var count=0;
	var checkedCount=0;
	 if(x.length==1){
	if(document.getElementsByName("cpfno")[0].checked==true){
	
	checkedCount++;
	var cpfacnoString=document.forms[0].cpfno.value;
		temp = cpfacnoString.split(",");
		cpfacno = temp[0];
		region=temp[3];
		dateofbirth=temp[5];
		pensionOption=temp[7];
		
		if(dateofbirth=="" || dateofbirth==" "){
		
		alert("Selected CPFACC.NO doen't have DateofBirth, Please Click on CPFACC.NO to Edit DateofBirth") ;
		document.forms[0].cpfno.focus();
		return false;
		}
		if(pensionOption==""){
		alert("Selected Cpfaccno doen't have PensionOption, Please Click on CPFACC.NO to Edit PensionOption") ;
		document.forms[0].cpfno.focus();
		return false;
		}
	}
	}	
	 for(var i=0;i<x.length;i++){
	
	 if( document.getElementsByName("cpfno")[i].checked==false){
	   count++;
	    var cpfno=document.getElementsByName("cpfno")[i].value;
	    if(x.length==count){
	 	alert("Please Select Atleast One Cpfaccno");
        return false;
	    }
		var cpfacnoString=document.forms[0].cpfno[i].value;
		temp = cpfacnoString.split(",");
		cpfacno = temp[0];
		region=temp[3];
		uncheckList+=","+cpfacno+"$"+region;
	 		 
	 }
	 if(x.length>1){
	 
	if( document.getElementsByName("cpfno")[i].checked==true){
	checkedCount++;
	var cpfacnoString=document.forms[0].cpfno[i].value;
	
		temp = cpfacnoString.split(",");
		cpfacno = temp[0];
		region=temp[3];
		dateofbirth=temp[5];
		pensionOption=temp[7];
		
		if(dateofbirth=="" || dateofbirth==" "){
		
		alert("One of the Selected Cpfaccno's doen't have DateofBirth, Please Click on CPFACC.NO to Edit DateofBirth") ;
		document.forms[0].cpfno[i].focus();
		return false;
		}
		if(pensionOption==""){
		alert("One of the Selected Cpfaccno's doen't have PensionOption,  Please Click on CPFACC.NO to Edit PensionOption") ;
		document.forms[0].cpfno[i].focus();
		return false;
		}
	}
	}
	
	
	 }
	var strHref="<%=basePath%>search1?method=process1&uncheckList="+uncheckList;
	//alert("strHref "+strHref);
	var empSerailnumber=document.forms[0].empSerialnumber.value;
	var pensionNumber = document.forms[0].pensionNumber.value;
	if(empSerailnumber!=""){
	var answer =confirm('Are you sure, Do you Want to Map The Record for Form3-2007-Sep- PFID : '+empSerailnumber);
	if(answer){
	document.forms[0].action=strHref;
    document.forms[0].method="post";
	document.forms[0].submit();
	
	}
	}else{
	var x=document.getElementsByName("cpfno");
	var count=0;
	var checkedCount=0;
	 if(x.length==1){
	if(document.getElementsByName("cpfno")[0].checked==true){
	
	checkedCount++;
	var cpfacnoString=document.forms[0].cpfno.value;
		temp = cpfacnoString.split(",");
		cpfacno = temp[0];
		region=temp[3];
		dateofbirth=temp[5];
		pensionOption=temp[7];
		
	}
	}	
 alert('Selected cpfaccno '+cpfacno+' doesnt have PFID ,Please  generate new PFID');
	//var answer =confirm('Selected cpfaccno'+cpfacno+' doesnt have PFID ,Please  generate new PFID');
	//if(answer){
	//	getPFID(cpfacno,region)
	//	var strHref="<%=basePath%>search1?method=process1&uncheckList="+uncheckList;
	//document.forms[0].action=strHref;
   // document.forms[0].method="post";
	//document.forms[0].submit();
	//}
	
	return false;
	}
	}
   	
   	 function selectMultipule(){
   	 document.getElementById("check1").checked
     var x=document.getElementsByName("cpfno");
      for(var i=0;i<x.length;i++){
     if(document.getElementById("check1").checked==true)
     document.getElementsByName("cpfno")[i].checked=true;
     else  
     document.getElementsByName("cpfno")[i].checked=false;
     }
   	}
   
   
   var xmlHttp;

	function createXMLHttpRequest()
	{
	 if(window.ActiveXObject)
	 {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	 }
	 else if (window.XMLHttpRequest)
	 {
		xmlHttp = new XMLHttpRequest();
	 }
	}
//	function getNodeValue(obj,tag)
 //  {
//	return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
 //  }
  function getNodeValue(obj,tag)
    { if(obj.getElementsByTagName(tag)[0].firstChild){
	  return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
	 }else return "";
   }
   
   function getEmployeeName(){
      	 var empName=document.forms[0].empName.value; 
       
    	 createXMLHttpRequest();	
    	var url ="<%=basePath%>search1?method=getEmployeeList&empName="+empName;
    	
		xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getEmployeeList;
		xmlHttp.send(null);
     	//document.forms[0].action="<%=basePath%>search1?method=getEmployeeList"
		//document.forms[0].method="post";
	//	document.forms[0].submit();
   	 }
   	 function restEmpName(){
   	// alert("resetEmpName");
   
  	 suggestions.style.display="none";
  
   	 }
   	 
   function getEmployeeList()
	{
	if(xmlHttp.readyState ==4)
	{
	
		if(xmlHttp.status == 200)
		{ var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
		  if(stype.length==0){
		 	var employeename = document.getElementById("employeename");
		   	  
		  }else{
		  suggestions.style.display="block";
		   suggestions.innerHTML="";
		  var str="";
		  str+="<table>"; 
		// divlist.innerHTML=divlist.innerHTML+"<table align='center' class='tbborder' width='70%'  cellpadding=2  cellspacing='0'  border='0'>";
	 		for(i=0;i<stype.length;i++){
	 	 	 var employeename= getNodeValue(stype[i],'employeename');
	 	 	 	 	
	 	 	 str+="<tr><td><a href='#' onclick=\"assignValue('"+employeename+"');\" >"+employeename+"</a></td></tr>";
		  //   suggestions.innerHTML=suggestions.innerHTML+employeename;
		     //	alert(suggestions.innerHTML);
	 	 	}
	 	 //  alert(str);
	 	   str+="</table>";
	 	    suggestions.innerHTML=str;
	 	    }
	 			 
		}
	 }
	 }
	function hideMe1()
    {
  	suggestions.style.display="none"; 
	} 
	
	function assignValue(obj){
	document.forms[0].empName.value=obj;
	hideMe1();
	}
	
	function getPFID(cpfaccno,region){
      	 var empName=document.forms[0].empName.value; 
     
    	 createXMLHttpRequest();	
    	var url ="<%=basePath%>search1?method=getPFID&cpfaccno="+cpfaccno+"&region="+region;
    	
		xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getEmployeeList;
		xmlHttp.send(null);
     	//document.forms[0].action="<%=basePath%>search1?method=getEmployeeList"
		//document.forms[0].method="post";
	//	document.forms[0].submit();
   	 }
   	   	 
   function getEmployeeList()
	{
	if(xmlHttp.readyState ==4)
	{
	
		if(xmlHttp.status == 200)
		{ var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
		  if(stype.length==0){
		 	var employeename = document.getElementById("employeename");
		   	  
		  }else{
		  suggestions.style.display="block";
		   suggestions.innerHTML="";
		  var str="";
		  str+="<table>"; 
		// divlist.innerHTML=divlist.innerHTML+"<table align='center' class='tbborder' width='70%'  cellpadding=2  cellspacing='0'  border='0'>";
	 		for(i=0;i<stype.length;i++){
	 	 	 var employeename= getNodeValue(stype[i],'employeename');
	 	 	 	 	
	 	 	 str+="<tr><td><a href='#' onclick=\"assignValue('"+employeename+"');\" >"+employeename+"</a></td></tr>";
		  //   suggestions.innerHTML=suggestions.innerHTML+employeename;
		     //	alert(suggestions.innerHTML);
	 	 	}
	 	 //  alert(str);
	 	   str+="</table>";
	 	    suggestions.innerHTML=str;
	 	    }
	 			 
		}
	 }
	 }
	
  	 
</script>
	</head>

	<body class="BodyBackground" onload="hideMe1();">
		<form name="form1" method="post">
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
						Arrear Financial Data Mapping[New]
					</td>

					<%boolean flag = false;%>
				</tr>
				<tr>
					<td class="tblabel">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<div id="dispaly1" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;color: red;">
							&nbsp;&nbsp;
						</div>
						<div id="dispaly2">
							<%String arrearInfoString1 = "";
            if (request.getAttribute("ArrearInfo") != null) {
                arrearInfoString1 = (String) request.getAttribute("ArrearInfo");
                   System.out.println("arrearInfoString1 "+arrearInfoString1);
             
                String temp1[] = arrearInfoString1.split("@");
                System.out.println("length "+temp1.length);
                if((temp1.length==6) ||(temp1.length==5)){
                empName1 = temp1[0];
                pensionNumber=temp1[2];
                empSerialnumber= temp1[4];
              //  transfertype=temp1[6];
                }else{
                empName1 = temp1[0];
                pensionNumber=temp1[2];
                empSerialnumber= temp1[5];
              //   transfertype=temp1[7];
                
                }
              System.out.println("empSerialnumber "+empSerialnumber);
            
                out.println("&nbsp;&nbsp;&nbsp;");
                out.println("<font color='red'>");
                
                out.println(arrearInfoString1);
             
                out.println("</font>");

            }%>
						</div>
					</td>
				</tr>


				<tr>
					<td height="15%">
						<table align="center">
							<tr>
							<td class="label">
									Form3-2007-Sep- PFID:
								</td>
								<td>
									<input type="text" name="empSerialnumber" readonly="true" tabindex="3" value='<%=empSerialnumber%>'>
									
								</td>

								<td class="label">
									Form3-2007-Sep Employee Name:
								</td>
								<td>
									<input type="text" name="empName1" readonly="true" tabindex="3" value='<%=empName1%>'>
									<img src="<%=basePath%>/PensionView/images/search1.gif" onclick="popupWindow('<%=basePath%>PensionView/PensionContributionMappedList.jsp','AAI');"  alt="Click The Icon to Select FinanceData Mapped Records"/>
								</td>
								<td>
									<a href="#" onClick="validateForm1('<%=empSerialnumber%>')"><img src="<%=basePath%>PensionView/images/viewDetails.gif" border="0" alt="Form3-2007-Sep Employee PFReport"></a>
								</td>
							</tr>
							<tr>
			<%String arrearInfoString = "";
            if (request.getAttribute("ArrearInfo") != null) {
                arrearInfoString = (String) request.getAttribute("ArrearInfo");
           %>
			<%}%>
				<td class="label">
									Old CPFACC.No:
								</td>
								<td>
									<input type="text" name="cpfaccno" tabindex="1" onmouseout="restEmpName();">
								</td>
								<td class="label">
									Date of Birth:
								</td>
									<td>
										<input type="text" name="dob" tabindex="9"  onkeyup="return limitlength(this, 20)">
										<a href="javascript:show_calendar('forms[0].dob');"><img src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
										</td>
							<!--  	<td>
									<input type="text" name="dob" tabindex="2" onkeyup="return limitlength(this, 20)" onclick="restEmpName();" onmouseout="restEmpName();">
								</td>-->
							</tr>
							<tr>
								<td class="label">
									Employee Name:
								</td>
								<br>
								<td>
									<input type="text" name="empName" value='<%=empName1%>' tabindex="3" onkeyup="getEmployeeName();">
									<div id="suggestions" class="containdiv1"></div>
									<!-- 	<img src="./PensionView/images/search1.gif" onclick="popupWindow('<%=basePath%>search1?method=getEmployeeMappedList','MappedList');"/> -->
								</td>
								<td class="label">
									Date of Joining:
									
									<input type="hidden" name="pensionNumber" size="6" value='<%=pensionNumber%>'>
									<input type="hidden" name="transfertype"  value='<%=transfertype%>'>
									
									<%if (request.getAttribute("ArrearInfo") != null) {
              						  arrearinfo1 = (String) request.getAttribute("ArrearInfo");
              						 
        						    }%>
									<input type="hidden" name="arrearInfo" size="6" value='<%=arrearinfo1%>'>
								</td>
								<td>
									
								    <input type="text" name="doj" tabindex="9"  onkeyup="return limitlength(this, 20)">
										<a href="javascript:show_calendar('doj');"><img src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
														

	</td>
							</tr>
                            <tr>
								<td class="label">
									Employee No:
								</td>
<td>
									<input type="text" name="empN0"  tabindex="5" >
									</td>

							</tr>
							<tr>
								<td align="left">
									&nbsp;
								<td>
							</tr>
							<tr>
								<td align="left">
								<td>
									<input type="button" class="btn" value="Search" class="btn" onclick="testSS();">
									<input type="button" class="btn" value="Reset" onclick="resetValue();" class="btn">
									<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="25%">
						<%EmpMasterBean dbBeans = new EmpMasterBean();
            SearchInfo getSearchInfo = new SearchInfo();

            int index = 0;
            if (request.getAttribute("searchBean") != null) {
                int totalData = 0;
                SearchInfo searchBean = new SearchInfo();
                flag = true;
                ArrayList dataList = new ArrayList();
                BottomGridNavigationInfo bottomGrid = new BottomGridNavigationInfo();
                searchBean = (SearchInfo) request.getAttribute("searchBean");
                dbBeans = (EmpMasterBean) request.getAttribute("searchInfo");
                index = searchBean.getStartIndex();
                System.out.println("index " + index);
                session.setAttribute("getSearchBean1", dbBeans);
                dataList = searchBean.getSearchList();
                System.out.println("dataList size" + dataList.size());
                totalData = searchBean.getTotalRecords();
                bottomGrid = searchBean.getBottomGrid();
                System.out.println("bottomGrid " + bottomGrid);
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

				<%} else if (dataList.size() != 0) {{%>
				<tr>
					<td>
						<table align="center">
							<tr>
								<td colspan="3">
								</td>
								<td colspan="2" align="right">
									<input type="button" alt="first" value="|<" name=" First" disable=true onClick="javascript:redirectPageNav('|<','<%=index%>','<%=totalData%>')" <%=bottomGrid.getStatusFirst()%>>
									<input type="button"  alt="pre" value="<" name=" Pre"  onClick="javascript:redirectPageNav('<','<%=index%>','<%=totalData%>')" <%=bottomGrid.getStatusPrevious()%>>
									<input type="button"  alt="next" value=">" name="Next" onClick="javascript:redirectPageNav('>','<%=index%>','<%=totalData%>')" <%=bottomGrid.getStatusNext()%>>
									<input type="button" value=">|" name="Last" onClick="javascript:redirectPageNav('>|','<%=index%>','<%=totalData%>')" <%=bottomGrid.getStatusLast()%>>
								</td>
							</tr>
						</table>
				</tr>
				<tr>
					<td height="25%">
						<table align="center" width="97%" cellpadding=2 class="tbborder" cellspacing="0" border="0">

							<tr class="tbheader">
								<td class="tblabel">
									Old CPFACC.NO
								</td>
								
								<td class="tblabel">
									Region
								</td>
								<td class="tblabel">
									Airport Code&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									Employee Code&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									Employee Name&nbsp;&nbsp;
								</td>
								<td class="tblabel">
									D.O.B
								</td>
								<td class="tblabel">
									D.O.J
								</td>
								<td class="tblabel">
									Pension
									<br>
										Option 
								</td>
								<td class="tblabel">
									PF
									<br>
										Report 
								</td>

								<td>
									<input type="checkbox" name="checkall" id="check1" onclick="selectMultipule();">
								</td>
							</tr>
							<%}%>
							<%int count = 0;
                String airportCode = "", employeeName = "", desegnation = "", employeeCode = "", cpfacno = "";
                String dateofBirth = "", pensionOption = "", remarks = "", lastActive = "", dateofJoining = "", pensionCheck = "", empSerialNumber = "";
                for (int i = 0; i < dataList.size(); i++) {
                    count++;
                    PensionBean beans = (PensionBean) dataList.get(i);
                    cpfacno = beans.getCpfAcNo();
                    airportCode = beans.getAirportCode();
                    desegnation = beans.getDesegnation();
                    employeeName = beans.getEmployeeName();
                    employeeCode = beans.getEmployeeCode();
                    pensionNumber = beans.getPensionnumber();
                    dateofBirth = beans.getDateofBirth();
                    pensionOption = beans.getPensionOption();
                    remarks = beans.getRemarks();
                    dateofJoining = beans.getDateofJoining();
                    region = beans.getRegion();
                    pensionCheck = beans.getPensionCheck();

                    lastActive = beans.getLastActive();
                    empSerialNumber = beans.getEmpSerialNumber();
                    if (count % 2 == 0) {

                    %>
							<tr>
								<%} else {%>
							<tr>
								<%}%>
								<td class="Data">
									<a href="javascript:editPensionMaster('<%=cpfacno%>','<%=employeeName%>','<%=employeeCode%>','<%=region%>','<%=airportCode%>')"><%=cpfacno%></a>
								</td>
								<td class="Data" width="10%">
									<%=region%>

								</td  class="Data">
								<td class="Data">
									<%=airportCode%>
								</td>


								<td class="Data" width="10%">
									<%=employeeCode%>
								</td>

								<td class="Data" width="12%">
									<%=employeeName%>
								</td>


								<td class="Data" width="10%">
									<%=dateofBirth%>
								</td>

								<td class="Data" width="10%">
									<%=dateofJoining%>

								</td>
								<td class="Data">
									<%=pensionOption%>
									<input type="hidden" name="pensionOption" size="6" value='<%=pensionOption%>'>
								</td>

								<td class="Data">
									<a href="#" onClick="validateForm('<%=cpfacno%>','<%=region%>')"><img src="./PensionView/images/viewDetails.gif" border="0" alt="PFReport"></a>
								</td>
								<%if (beans.getPensionCheck().equals("true")) {%>
								<td>
									<input type="checkbox" name="cpfno" value="<%=cpfacno%>,<%=employeeName%>,<%=employeeCode%>,<%=region%>,<%=airportCode%>,<%=dateofBirth%>,<%=dateofJoining%>,<%=pensionOption%>" checked="<%=beans.getPensionCheck()%>"
										onclick="javascript:deletePensionMaster('<%=cpfacno%>','<%=employeeName%>','<%=employeeCode%>','<%=region%>','<%=airportCode%>')" />
									<input type="hidden" name="cpfnostring<%=cpfacno%>" value="<%=cpfacno%>,<%=employeeName%>,<%=employeeCode%>,<%=region%>,<%=airportCode%>,<%=dateofBirth%> ,<%=dateofJoining%>,<%=pensionOption%>">
									<%} else {%>
								<td>
									<input type="hidden" name="cpfnostring<%=cpfacno%>" value="<%=cpfacno%>,<%=employeeName%>,<%=employeeCode%>,<%=region%>,<%=airportCode%>,<%=dateofBirth%> ,<%=dateofJoining%>,<%=pensionOption%>">
									<input type="checkbox" name="cpfno" value="<%=cpfacno%>,<%=employeeName%>,<%=employeeCode%>,<%=region%>,<%=airportCode%>,<%=dateofBirth%> ,<%=dateofJoining%>,<%=pensionOption%>">
								</td>
								<%}%>

								<td>
									<input type="hidden" name="uncheckhidden" />
								</td>
								<input type="hidden" name="arrearInfoString" value="<%=arrearInfoString%>">
							</tr>

           <%}%>
						
							<tr>
								&nbsp;
							</tr>
							<tr>
								<td align="center"></td>
								<td></td>
								<td>
								<td></td>
								<td>
									<input type="button" class="btn" value="Process" class="btn" onclick="popup();">
								</td>
							</tr>
							<%}}%>
								

						</table>
					</td>

				</tr>



			</table>
			<tr>
				<td colspan="3">
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>


		</form>
	</body>
</html>
