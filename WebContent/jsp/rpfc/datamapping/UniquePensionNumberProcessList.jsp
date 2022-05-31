<%@ page language="java"%>
<%@ page import="java.util.*,com.epis.utilities.CommonUtil"%>
<%@ page import="com.epis.bean.rpfc.PensionBean,com.epis.utilities.ScreenUtilities,com.epis.bean.rpfc.EmpMasterBean,com.epis.bean.rpfc.SearchInfo,com.epis.bean.rpfc.BottomGridNavigationInfo"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display"%>
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

			if (session.getAttribute("getSearchBean1") != null) {
				session.removeAttribute("getSearchBean1");
				//session.setMaxInactiveInterval(1);
				//session.invalidate();
			}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI</title>
		
		
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
	 	<link  href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css">

		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<script type="text/javascript">
		
		function validateForm(cpfaccno,region) {
		//alert("inside validate cpfacccno"+cpfaccno+"region"+region);
		var regionID="",airportcode="",reportType="",yearID="",monthID="",yearDesc="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		//var empserialNO	=empserialNO;
		reportType="ExcelSheet";
		yearID="NO-SELECT";
		var params = "&frm_region="+region+"&frm_airportcode="+airportcode+"&frm_year="+yearID+"&frm_reportType="+reportType+"&cpfAccno="+cpfaccno;
		var url="<%=basePath%>reportservlet.do?method=getReportPenContr"+params;
		//alert("url"+url);
				if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   	 			}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   	 				 		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
							winOpened = true;
							wind1.window.focus();
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
	function getNodeValue(obj,tag)
    { if(obj.getElementsByTagName(tag)[0].firstChild){
	  return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
	 }else return "";
   }
   
	function add(){

	 var x=document.getElementsByName("cpfno");
	 var uncheckList="";
	 var  checkedCount=0;
	 for(var i=0;i<x.length;i++){
	 if( document.getElementsByName("cpfno")[i].checked==true){
		var cpfacnoString=document.forms[0].cpfno[i].value;
		temp = cpfacnoString.split(",");
		cpfacno = temp[0];
		region=temp[2];
		employeeno=temp[6];
		//uncheckList+=","+cpfacno+"$"+region;
		uncheckList+=","+cpfacno+"$"+region+"$"+employeeno;
		//alert("checkList "+uncheckList);
		document.forms[0].checklist.value=uncheckList;
	 }
	
	 }
	 }
	
	function addtoProcess(){
	add();
	var x=document.getElementsByName("cpfno");
	var count=0;
	var checkedCount=0;
	 for(var i=0;i<x.length;i++){
	 if( document.getElementsByName("cpfno")[i].checked==false){
	   count++;
	    if(x.length==count){
	 	alert("Please Select Atleast One Cpfaccno");
        return false;
	    }
	   
	
	}
   if(x.length>=1){
   if( document.getElementsByName("cpfno")[i].checked==true){
	 checkedCount++;
	 var cpfacnoString=document.forms[0].cpfno[i].value;
	
		temp = cpfacnoString.split(",");
		cpfacno = temp[0];
		region=temp[2];
		dateofbirth=temp[3];
		pensionOption=temp[5];
		if(dateofbirth=="" || dateofbirth==" "){
		alert("One of the Selected Cpfaccno's doen't have DateofBirth, Please Click on CPFACC.NO to Edit DateofBirth") ;
		document.forms[0].cpfno[i].focus();
		return false;
		}
		if(pensionOption==""){
		alert("One of the Selected Cpfaccno's doen't have DateofBirth, Please Click on CPFACC.NO to Edit PensionOption") ;
		document.forms[0].cpfno[i].focus();
		return false;
		}
   }
	}
	
	}
	document.forms[0].action="<%=basePath%>validatefinance.do?method=addtoProcess";
	document.forms[0].method="post";
	document.forms[0].submit();
	}
	function setEmpsearchName(){
	<%if(session.getAttribute("employeeSearchName")!=null){%>
	document.forms[0].empName.value='<%=session.getAttribute("employeeSearchName")%>';
	<%}%>
	<%if(session.getAttribute("dob1")!=null){%>
	document.forms[0].dob.value='<%=session.getAttribute("dob1")%>';
	<%}%>
	
	//testSS();
	
	}
	
	function editPensionMaster(obj,employeeName,employeeCode,region,airportCode){
	    var empName="",dob1="";
	      empName=document.forms[0].empName.value;
    	  dob1=document.forms[0].dob.value;  
    //	 alert("empname "+empName+"dob "+dob1);
		var cpfacno=obj;
		var answer =confirm('Are you sure, do you want edit this record');
	if(answer){
		var flag="true";
		var getProcessUnprocessList="getProcessUnprocessList";
		document.forms[0].action="<%=basePath%>personalForm.do?method=personalEdit&cpfacno="+cpfacno+"&name="+employeeName+"&flag="+flag+"&empCode="+employeeCode+"&region="+region+"&airportCode="+airportCode+"&editFrom="+getProcessUnprocessList+"&empName="+empName+"&dob1="+dob1;
	//	alert("document.forms[0].action "+document.forms[0].action);
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	}

 function testSS(){
      
 		 if((document.forms[0].empName.value=="")&&(document.forms[0].dob.value=="")){
    	alert("Please Enter Any one of the field for Search");
    	document.forms[0].empName.focus();
        return false;
    	}
    	  var dateofbirth=document.forms[0].dob.value;
    	  var empName=document.forms[0].empName.value;             
    	 createXMLHttpRequest();	
    	  var url ="<%=basePath%>validatefinance.do?method=searchRecordsbyDobandNameUnprocessList&dateofbirth="+dateofbirth+"&empName="+empName;
		xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getSearchList;
		xmlHttp.send(null);
      }

	function getSearchList()
	{
	
	if(xmlHttp.readyState ==4)
	{
	// alert(xmlHttp.responseText);
		if(xmlHttp.status == 200){ 
		  var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
		 	
		  if(stype.length==0){
		 	var cpfacno = document.getElementById("cpfacno");
		 	divlist.style.display="block"; 
		   	divlist.innerHTML="<table ><tr><td height='5%' width='10%' colspan='6' align='center'>No Records Found</td></tr></table>";  
		    showbuttons.innerHTML="";
		  }else{ 
		  	alert('stype.length'+stype.length);
		  	divlist.style.display="block"; 
		   	divlist.innerHTML="";
		   	showbuttons.innerHTML="";
		   	divlist.innerHTML="<table align='center'  width='100%' ><tr><td height='5%'colspan='9' >Unprocess List</td></tr></table>";
		   	divlist.innerHTML=divlist.innerHTML+"<table align='center' width='100%' border='0' cellpadding='2' class='GridBorder' cellspacing='0' ><tr class='GridHBg'><b><td class='GridLCells'>Old<br>CPFAccno</td><td class='GridLCells'> &nbsp;&nbsp;Region</td><td class='GridLCells'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EmployeeName</td><td class='GridLCells'>&nbsp;&nbsp;&nbsp; DateofBirth</td><td class='GridLCells'> DateofJoining</td><td class='GridLCells'> PensionOption</td></b><td class='GridLCells'>PFReport</td><td class='GridLCells'><img src='./images/addIcon.gif' alt='Add' /></td></tr></table>";

			for(var i=0;i<=stype.length;i++){
			
			 var cpfacno="";
			  if( getNodeValue(stype[i],'cpfacno')!=null){
	 	 	 cpfacno = getNodeValue(stype[i],'cpfacno');
	 	 	 }else cpfacno="";
	 	 	 var employeename="";
	 	 	 if( getNodeValue(stype[i],'employeename')!=null){
		     employeename= getNodeValue(stype[i],'employeename');
		     }else employeename="";
		     var dateofbirth="";
		     if(( getNodeValue(stype[i],'dateofbirth')!=null) ||( getNodeValue(stype[i],'dateofbirth')!="")){
		       dateofbirth = getNodeValue(stype[i],'dateofbirth');
		     
		     } else dateofbirth="";
		     var dateofjoin="";
		     if( getNodeValue(stype[i],'dateofjoin')!=null){
		     dateofjoin= getNodeValue(stype[i],'dateofjoin');
		     }else dateofjoin="";
		      var region="";
		      if( getNodeValue(stype[i],'region')!=null){
		      region= getNodeValue(stype[i],'region');
		      }else region="";
		      var airportcode="";
		     if( getNodeValue(stype[i],'airport')!=null){
		      airportcode= getNodeValue(stype[i],'airport');
		      }else airportcode="";
		    
		     var employeeno="";
		     if( getNodeValue(stype[i],'employeeNo')!=null){
		      employeeno= getNodeValue(stype[i],'employeeNo');
		      }else employeeno="";
		
		  var pensionOption="";
		     if( getNodeValue(stype[i],'pensionOption')!=null){
		      pensionOption= getNodeValue(stype[i],'pensionOption');
		      }else pensionOption="";
		      
		     
		 	 divlist.innerHTML=divlist.innerHTML+"<table><tr><td  class='Data' width='14%'><a href='#' onClick=\"editPensionMaster('"+cpfacno+"','"+employeename+"','"+employeeno+"','"+region+"','"+airportcode+"');\">"+cpfacno+" </a> </td><td  class='Data' width='14%'>"+region+"</td><td  class='Data' width='20%'>"+employeename+"</td><td  class='Data' width='16%'>"+dateofbirth+" </td><td  class='Data' width='14%'>"+dateofjoin+"</td><td  class='Data' width='14%'>"+pensionOption+"</td><td  class='Data' width='14%'><a href='#' onClick=\"validateForm('"+cpfacno+"','"+region+"');\"><img src='./images/viewDetails.gif' border='0' alt='PFReport' > </a></td><td width='14%'><input type='checkbox' name='cpfno' value='"+cpfacno+","+employeename+","+region+","+dateofbirth+","+dateofjoin+","+pensionOption+","+employeeno+"' onclick='add()'></td>   </tr></table>";
		 if(i==(stype.length)-1){
	
		 showbuttons.innerHTML= showbuttons.innerHTML+"<table align='center'><td align='center'><input type='button' value='Add' class='btn' onclick='addtoProcess()'><input type='button' value='Reset' onclick='javascript:document.forms[0].reset()' class='btn'><input type='button' value='Cancel' onclick='javascript:history.back(-1)' class='btn'></td></tr></table>";
	 	 	}
	 	 	
	 	 	}
	 
		   }
	 		
	 	 
		}
	 }
	 }

	
    	
   	function deleteEmpSerailNumber(cpfacno,employeeName,region,airportCode,empSerailNumber,empCode){
	
	var answer =confirm('Are you sure, do you want delete this record');
	if(answer){
		var flag="true";
		document.forms[0].action="<%=basePath%>validatefinance.do?method=unprocessList&cpfacno="+cpfacno+"&name="+employeeName+"&region="+region+"&empSerailNumber="+empSerailNumber+"&empCode="+empCode;
		document.forms[0].method="post";
		document.forms[0].submit();
	}else{
	document.forms[0].cpfno.checked=false;
	}
	}
   	
  function hideMe1()
  {
	divlist.style.display="block"; 
	}  	
   
</script>
	</head>

	<body class="BodyBackground" onload="hideMe1();setEmpsearchName();">
		<form name="test" action="">
			<%=ScreenUtilities.saearchHeader("Arrear Financial Data Mapping[Edit]")%>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				

				
					
					<%boolean flag = false;%>
				       <logic:present name="searchInfoList" >
							<tr>							
								<td align="center" width="100%">
								
									<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="searchInfoList" style="width:100%;height:50px" pagesize="10" name="requestScope.searchInfoList" requestURI="./validatefinance.do?method=searchRecordsbyEmpSerailNo" >											
		    						 
									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="form10D.xls" />
    								<display:setProperty name="export.pdf.filename" value="form10D.pdf" />
    								<display:setProperty name="export.rtf.filename" value="form10D.rtf" />
    							
    								<display:column    property="cpfAcNo"          sortable="true"  headerClass="GridHBg" class="GridLCells"       title="Old CpfAccno" />
									<display:column    property="region"      sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Region" />
    								<display:column    property="empName"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Employee Name"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column    property="empSerialNo"   sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Form3-2007-Sep- PFID"  decorator="com.epis.utilities.StringFormatDecorator"/>	    									
									<display:column    property="dateofBirth"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="DateofBirth"  decorator="com.epis.utilities.StringFormatDecorator"/>	
									<display:column    property="dateofJoining"   sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="DateofJoining"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column    property="pensionNumber"   sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="PensionNumber"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column  media="html"  sortable="true" headerClass="GridLTHCells" class="GridLTCells" title="Delete">
    								<input type="checkbox" name="cpfno" value="<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getEmpSerialNo()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getCpfAcNo()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getEmpName()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getEmpNumber()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getRegion()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getStation()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getDateofBirth()%>"/>
    								</display:column>
									</display:table>
								</td>
							</tr>
							</logic:present>

			
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
				<td>
				<table align="center" width="100%" cellpadding="1" cellspacing="1">
		
					<tr>

						<td class="tableTextBold">
							Employee Name:
						</td>
						<td>
							<input type="text" name="empName" class="TextField">
						</td>
						<td class="tableTextBold">
							Date of Birth:
						</td>
						<td>
							<input type="text" name="dob" class="TextField">
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<input type="button" class="btn" value="Search" class="btn" onclick="testSS();">
						</td>
						<td ><a href="#" onclick="javascript:history.back(-1)"><img src="<%=path%>/images/viewBack.gif" border="0" alt="Back" ></a>
						</td>
						
					</tr>

					<tr>
						<td align="left">
							&nbsp;
						<td>
					</tr>


				</table>

			</td>
			</tr>
			
					
	
				
				       
	
			<input type="hidden" name="checklist">
				
				
			
            
		
		</table>
						 <div id="divlist" align="left" ></div>
					 <div id="showbuttons"></div>
				<%=ScreenUtilities.screenFooter()%>
		</form>



	</body>
</html>
