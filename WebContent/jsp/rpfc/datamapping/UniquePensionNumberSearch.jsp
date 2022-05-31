<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,org.displaytag.tags.TableTag"%>
<%@ page
	import="com.epis.bean.rpfc.EmpMasterBean,com.epis.utilities.ScreenUtilities,com.epis.bean.rpfc.SearchInfo,com.epis.bean.rpfc.BottomGridNavigationInfo"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display"%>
<%
String path = request.getContextPath();

//added----------
Calendar cal = Calendar.getInstance(); 
 		 int month = cal.get(Calendar.MONTH)+3; 
		 int year=cal.get(Calendar.YEAR);
		 System.out.println("month "+month +"year "+year);
		 if(month>=12){
			  month=month-12;
			  year = cal.get(Calendar.YEAR)+1; 
		 }
 System.out.println("after month "+month +"after year "+year);



String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

    String region="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
	hashmap=common.getRegion();

	Set keys = hashmap.keySet();
	Iterator it = keys.iterator();
	
	if(session.getAttribute("getSearchBean1")!=null){
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
	 	<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
	<script type="text/javascript">
	
	
	function validateForm(empserialNO,claimsprocess) {
	//alert(empserialNO);
		var regionID="",airportcode="",reportType="",yearID="",monthID="",yearDesc="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		var empserialNO	=empserialNO;
		//reportType="ExcelSheet";
		reportType="Html";
		yearID="NO-SELECT";
		var page="PensionContributionScreen";
		var mappingFlag="true";
        var frm_year="1995";
		var frm_toyear="2010";
		var frm_month="07";
		// added
		if(claimsprocess=="Y"){
        alert("PensionClaim Process Already Done, User doesn't have Permissions to View/Edit TransactionData");
        return false;
        }
        
        var frm_toyear=<%=year%>;
		var frm_month=<%=month%>;
		//.......
		var params = "&frm_region="+regionID+"&frm_airportcode="+airportcode+"&frm_reportType="+reportType+"&empserialNO="+empserialNO+"&page="+page+"&mappingFlag="+mappingFlag+"&frm_year="+frm_year+"&frm_toyear="+frm_toyear+"&frm_month="+frm_month;
		var url="<%=basePath%>reportservlet.do?method=getReportPenContr"+params;
		//alert(url);
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
		
	}

	function LoadWindow(params){
	var newParams ="<%=basePath%>/jsp/rpfc/common/Report.jsp?"+params
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar = yes,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
    }
	
    function testSS(){
       	document.forms[0].action="<%=basePath%>validatefinance.do?method=searchRecordsbyEmpSerailNo"
		document.forms[0].method="post";
		document.forms[0].submit();
   	 }
     	function editEmpSerailNumber(cpfacno,employeeName,region,airportCode,empSerailNumber,dateofBirth,empCode){
        
		var answer =confirm('Are you sure, do you want edit this record');
	
	if(answer){
		var flag="true";
		document.forms[0].action="<%=basePath%>validatefinance.do?method=getProcessUnprocessList&cpfacno="+cpfacno+"&name="+employeeName+"&region="+region+"&airportCode="+airportCode+"&empSerailNumber="+empSerailNumber+"&dateofBirth="+dateofBirth;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	 
	}
	 
   			function selectCheckboxes(frmType){
				    
		      var count=0;
		      var str=new Array();
		      var pfid,obj,employeeName,employeeCode,region,airportCode,index,totalData,dateOfBirth,claimsprocess;
			   //alert(frmType);
	
		   
		   if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	    
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){	
		           
			          pfid=str[0];
			          obj=str[1];
			          employeeName=str[2];
			          employeeCode=str[3];
			          region=str[4];
			          airportCode=str[5];
			          dateOfBirth=str[6];
			          claimsprocess=str[7];
			          index=0;
			          totalData=0;
			    
			        }
			        
			        
			        if(frmType=='Edit'){
			        	
			        	editEmpSerailNumber(obj,employeeName,region,airportCode,pfid,dateOfBirth,employeeCode);
			        }else if(frmType=='Print'){
			        	validateForm(pfid,claimsprocess);
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
     
    //  alert("checkBoxes " +checkboxes);
	//	document.forms[0].action="<%=basePath%>search1?method=delete";
	//	document.forms[0].method="post";
	//	document.forms[0].submit();
	}
   
</script>
	</head>

	<body class="BodyBackground" >
		<%=ScreenUtilities.screenHeader("Monthly Cpf Recoveries Data[Search]")%>
		<form name="test" action="" >
	
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

				

					
					<%boolean flag = false;%>
				
				<tr>
					<td height="15%">
						<table align="center">
							<tr>
								<td class="tableTextBold">
									Form3-2007-Sep Employee Name:
								</td>
								<td align="left">
									<input type="text" name="empName" onkeyup="return limitlength(this, 20)"  class="TextField">
								</td>
								<td class="tableTextBold">
									Form3-2007-Sep- PFID:
								</td>
								<td align="left">
									<input type="text" name="empsrlNo"  class="TextField">
								</td>
							</tr>
							<tr>
							<td class="tableTextBold">
									Date of Birth:
								</td>
								<td align="left">
								<input type="text" name="dob" onkeyup="return limitlength(this, 20)"  class="TextField">
								</td>
							
								<td class="tableTextBold">
									Date of Joining
								</td>
								<td align="left">
									<input type="text" name="doj" onkeyup="return limitlength(this, 20)"  class="TextField">
								</td>
							</tr>
					     	<tr>
								<td align="left">&nbsp;</td>
								</tr>

							<tr>

								<td align="left">
								<td>
									<input type="button" class="btn" value="Search" class="btn" onclick="testSS();">
									<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn">
									<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
								</td>

							</tr>
						</table>
					</td>

				</tr>
</table>
	<%=ScreenUtilities.searchSeperator()%>	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		
            <tr>
                        <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
                       <logic:present name="searchInfoList" >
							<tr>							
								<td align="center" width="100%">
								
									<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="searchInfoList" style="width:100%;height:50px" pagesize="10" name="requestScope.searchInfoList" requestURI="./validatefinance.do?method=searchRecordsbyEmpSerailNo" >											
		    						 
									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="form10D.xls" />
    								<display:setProperty name="export.pdf.filename" value="form10D.pdf" />
    								<display:setProperty name="export.rtf.filename" value="form10D.rtf" />
    								<display:column  media="html"  headerClass="GridHBg" class="GridLCells">
    								<input type="radio" name="chk" value="<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getEmpSerialNo()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getCpfAcNo()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getEmpName()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getEmpNumber()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getRegion()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getStation()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getDateofBirth()%>:<%=((EmpMasterBean)pageContext.getAttribute("searchInfoList")).getClaimsprocess()%>"/>
    								</display:column>
    								<display:column    property="empSerialNo"          sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Form3-2007-Sep- PFID" />
									<display:column    property="cpfAcNo"      sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Old CPFACC.No" />
    								<display:column    property="empName"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Employee Name"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column    property="dateofBirth"   sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="D.O.B"  decorator="com.epis.utilities.StringFormatDecorator"/>	    									
									<display:column    property="dateofJoining"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="D.O.J"  decorator="com.epis.utilities.StringFormatDecorator"/>	
									<display:column    property="totalTrans"   sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Record Count"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column  media="html"  sortable="true" headerClass="GridLTHCells" class="GridLTCells" title="">
    								</display:column>
									</display:table>
								</td>
							</tr>
							</logic:present>
             

		
				
				 

			</table>



		</form>
		<%=ScreenUtilities.screenFooter()%>
	</body>
</html>
