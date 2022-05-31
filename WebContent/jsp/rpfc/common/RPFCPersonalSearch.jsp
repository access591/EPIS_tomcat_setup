
<%@ page language="java" import="java.util.*,com.epis.utilities.*"%>
<%@ page isErrorPage="true"%>
<%@ page import="com.epis.bean.rpfc.PensionBean,com.epis.info.login.LoginInfo"%>

<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String region = "";
        
			Iterator it = null;
            HashMap hashmap = new HashMap();
            if (request.getAttribute("regionHashmap") != null) {
                hashmap = (HashMap) request.getAttribute("regionHashmap");
                Set keys = hashmap.keySet();
            	 it = keys.iterator();

            }
           

            
             String transferStatus="";
            if(request.getParameter("transferStatus")!=null){
             	transferStatus=request.getParameter("transferStatus").toString();
            }else{
            transferStatus="";
            }

            

            
           
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		

		<title>AAI</title>				
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<script type="text/javascript"> 
  
  	
  	function statusdisplay(){
  	
  		  document.getElementById("suggestions").style.display="block";
		  document.getElementById("suggestions").innerHTML="";
		   var str="";
		   str+="<table>"; 
   		  
   		   str+="<tr><td>&nbsp;</td></tr><tr><td colspan='5' id='status'><img src='<%=basePath%>images/loading1.gif'></td></tr><tr><td>&nbsp;</td></tr>";
		   str+="</table>";
		  document.getElementById("suggestions").innerHTML=str;
		 
  	
  	}
	function testSS(){
	
  	  var region1= document.forms[0].region1[document.forms[0].region1.options.selectedIndex].text;
  	  if(document.forms[0].empName.value=="" && document.forms[0].pfId.value==""){
  		 alert("Please Enter EmployeeName or PFID");
  		  document.forms[0].empName.focus();
  		  return false;
  	  }
        
   		var empName=document.forms[0].empName.value;
   		//var transferType="no";
   		var transferType="";
   		var pfId = document.forms[0].pfId.value;
   		var url ="<%=basePath%>reportservlet.do?method=getPFIDInformation&region="+region1+"&empName="+empName+"&transferType="+'<%=transferStatus%>'+"&pfId="+pfId;
	    document.forms[0].action=url;
	    document.forms[0].method="post";
		document.forms[0].submit();
		
	}
	
	
	 function editPensionMaster(cpfaccno,region,pensionNumber,employeeName,dateofbirth,empSerialNo){
	 
	 var mappedInfo=cpfaccno+"@"+region+"@"+pensionNumber+"@"+employeeName+"@"+dateofbirth+"@"+empSerialNo;
	// var url="<%=basePath%>PensionView/UniquePensionNumberGeneration.jsp?mappedInfo="+mappedInfo;
	// alert("url"+url);
	//document.forms[0].action=url;
	//document.forms[0].method="post";
	//document.forms[0].submit();
	 window.opener.test(cpfaccno,region,pensionNumber,employeeName,dateofbirth,empSerialNo);	
	 window.close();
	 
	}
	 </script>
	</head>

	<body>
		<form>
			<table align="center"  cellpadding="1" cellspacing="1" width="100%">
			
			<tr>
				<td>
					<table align="center"  cellpadding="1" cellspacing="1" width="100%">
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td class="tableTextBold">Region:</td>
							
							<td><select name="region1" style="width:100px" class="TextField">
							<option value="NO-SELECT">[Select One]</option>
											<%while (it.hasNext()) {
											region = hashmap.get(it.next()).toString();%>
						     <option value="<%=region%>"><%=region%></option>
							 <%}%>
																			
		
							</select>
						</td>
					<td class="tableTextBold">Emp Name:</td>
					<td><input type="text" class="TextField" name="empName" onkeyup="return limitlength(this, 20)"></td>
					<td class="tableTextBold">PFID:</td>
					<td><input type="text" class="TextField" name="pfId" onkeyup="return limitlength(this, 20)"></td>
					<td><input type="button" class="btn" value="Search" class="btn" onclick="javascript:testSS();"></td>
				</tr>

				</table>
			</td>
		</tr>			
			
				<div id="suggestions"></div>

			<%PensionBean dbBeans = new PensionBean();

            if (request.getAttribute("MappedList") != null) {
                ArrayList datalist = new ArrayList();
                int totalData = 0;
                datalist = (ArrayList) request.getAttribute("MappedList");
                System.out.println("dataList " + datalist.size());

                if (datalist.size() == 0) {

                %>
           
			<tr>

				<td>
					<table align="center" id="norec">
						<tr>
							
							<td class="tableTextBold">
								<b> No Records Found </b>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<%} else if (datalist.size() != 0) {%>
			<tr>
				<td height="25%">
					<table align="center" width="97%" cellpadding=2 class="GridBorder" cellspacing="0" border="0">
						<tr class="GridHBg">
						
						<td class="GridLCells">
								Serial No
							</td>
							<td class="GridLCells">
								CPF NO
							</td>
							<td class="GridLCells">
								Region
							</td>
							
							<td class="GridLCells">
								Pension Number&nbsp;&nbsp;
							</td>
							<td class="GridLCells">
								Emp Name&nbsp;&nbsp;
							</td>
							<td class="GridLCells">
								D.O.B
							</td>
							<td class="GridLCells">
								Pension Option
							</td>
							<td class="GridLCells">&nbsp;</td>

						</tr>

						<%}%>
						<%int count = 0;
                String airportCode = "", employeeName = "", cpfacno = "", pensionNumber = "";
                String dateofBirth = "", empSerialNo = "", dateofJoining = "",pensionOption="",dynamicClassLoading="";
                for (int i = 0; i < datalist.size(); i++) {
                    count++;
                    PensionBean beans = (PensionBean) datalist.get(i);

                    cpfacno = beans.getCpfAcNo();
                    employeeName = beans.getEmployeeName();
                    pensionNumber = beans.getPensionnumber();
                    dateofBirth = beans.getDateofBirth();
                    dateofJoining = beans.getDateofJoining();
                    region = beans.getRegion();
                    empSerialNo = beans.getEmpSerialNumber();
                      pensionOption = beans.getPensionOption();
                    if (count % 2 == 0) {
						dynamicClassLoading="GridRowBg1";
					} else {
						dynamicClassLoading="GridRowBg2";
					}
                    %>
						
							
						<tr class="<%=dynamicClassLoading%>">
							
							
							<td class="GridLTCells" width="12%">
								<%=count%>
							</td>
							<td class="GridLTCells" width="12%">
								<%=cpfacno%>
							</td>
							<td class="GridLTCells" width="15%">
								<%=region%>
							</td>
							<td class="GridLTCells" width="12%">
								<%=empSerialNo%>
							</td>
							

							<td class="GridLTCells" width="17%" nowrap="nowrap">
								<%=employeeName%>
							</td>
							<td class="GridLTCells" width="17%">
								<%=dateofBirth%>
							</td>
							<td class="GridLTCells">
								<%=pensionOption%>
							</td>

							<td class="GridLTCells">
								<input type="checkbox" name="cpfno" value="<%=cpfacno%>" onclick="javascript:editPensionMaster('<%=cpfacno%>','<%=region%>','<%=pensionNumber%>','<%=employeeName%>','<%=dateofBirth%>','<%=empSerialNo%>')" />
							</td>

						</tr>
						<%}%>


						<%}%>

					</table>
				</td>

			</tr>
		</table>
	</form>
	</body>
</html>
