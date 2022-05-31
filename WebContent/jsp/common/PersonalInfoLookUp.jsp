
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.bean.advances.AdvanceBasicBean,com.epis.utilities.ScreenUtilities"%>
<%@ page isErrorPage="true"%>


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
           

            
           
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>AAI</title>
	<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
		<script type="text/javascript"> 
  

	function searchPersDtl(){
  		
   		var empName=document.forms[0].empName.value;
   		var pensionno=document.forms[0].pensionno.value;
   		var cpfacno=document.forms[0].cpfaccno.value;
   		var region= document.forms[0].select_region[document.forms[0].select_region.options.selectedIndex].value;
   		var url ="<%=basePath%>loadAdvance.do?method=lookupPFID&frm_pensionno="+pensionno+"&empName="+empName+"&frm_region="+region+"&frm_cpfaccno="+cpfacno+"&action_flag=searchpfid";
	    document.forms[0].action=url;
	    document.forms[0].method="post";
		document.forms[0].submit();
		
	}
	
	
	 function editPensionMaster(region,pensionNumber,cpfaccno,dateOfMemberShip,employeeCode,designation,fhname,emolument,emppfstatuary,department,dateofJoining,employeeName,dateofbirth,empSerialNo,airportCode){
	 window.opener.test(pensionNumber,employeeCode,employeeName,designation,fhname,department,dateofJoining,dateOfMemberShip,dateofbirth,emolument,empSerialNo,region,emppfstatuary,cpfaccno,airportCode);	
	 window.close();
	 
	}
	 </script>
	</head>

	<body>
		<form>
				<%=ScreenUtilities.screenHeader("Employee Search List")%>
		<table width="100%" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td class="tableTextBold" nowrap="nowrap">PF ID:</td>
					
					<td>
						<input type="text" name="pensionno" onkeyup="return limitlength(this, 15)" size="15" maxlenght="15">
						
					</td>
              		<td class="tableTextBold" nowrap="nowrap">CPF Acc.no:</td>
					
					<td><input type="text" name="cpfaccno" onkeyup="return limitlength(this, 20)" size="15" maxlenght="15">
						
					</td>
			
					<td class="tableTextBold" nowrap="nowrap">Emp Name:</td>
					<td><input type="text" name="empName" onkeyup="return limitlength(this, 20)"></td>
					<td class="tableTextBold" nowrap="nowrap">Region:</td>
					<td>
						<SELECT NAME="select_region" style="width:100px">
							<option value="">[Select One]</option>
							<%int j = 0;
							boolean exist = false;
							while (it.hasNext()) {
								String region1 = hashmap.get(it.next()).toString();
								j++;
								if (region1.equalsIgnoreCase(region))
									exist = true;
								if (region1.equalsIgnoreCase(region)) {

								%>
							<option value="<%=region%>" <% out.println("selected");%>><%=region%></option>
							<%} else {%>
							<option value="<%=region1%>"><%=region1%></option>
							<%}}%>
						</SELECT>
					</td>
				    <td><input type="button" class="btn" value="Search" class="btn" onclick="searchPersDtl();"></td>
				</tr>

		
				<div id="suggestions"></div>
			<tr>

				<td>&nbsp;&nbsp;&nbsp;</td>
			</tr>
					<tr>

				<td>&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<%

            if (request.getAttribute("personalList") != null) {
                ArrayList datalist = new ArrayList();
                int totalData = 0;
                datalist = (ArrayList) request.getAttribute("personalList");
                if (datalist.size() == 0) {

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

			<%} else if (datalist.size() != 0) {%>
			<tr>
				<td height="25%" colspan="8">
					<table align="center" width="97%" cellpadding=2 class="GridBorder" cellspacing="0" border="0">
						<tr class="GridHBg">
						<td class="GridLTCells">S.No</td>
						<td class="GridLTCells">PF ID</td>
						<td class="GridLTCells">CPF ACC.NO</td>
						<td class="GridLTCells">Employee Name</td>
						<td class="GridLTCells">D.O.B&nbsp;&nbsp;</td>
						<td class="GridLTCells">D.O.J&nbsp;&nbsp;</td>
						<td class="GridLTCells">Region&nbsp;&nbsp;</td>
						<td class="GridLTCells">&nbsp;&nbsp;</td>
						</tr>

						<%}%>
						<%int count = 0;
               			 	String airportCode = "", employeeName = "", cpfacno = "", pensionNumber = "",designation="",dateOfMember="";
                			String dateofBirth = "", empSerialNo = "", dateofJoining = "",employeeCode="",emolument="",fhname="",department="",emppfstatuary="";
                for (int i = 0; i < datalist.size(); i++) {
                    count++;
                    AdvanceBasicBean beans = (AdvanceBasicBean) datalist.get(i);

                    pensionNumber = beans.getPfid();
                    cpfacno = beans.getCpfaccno();
                    employeeName = beans.getEmployeeName();
                    dateofBirth = beans.getDateOfBirth();
                    dateofJoining = beans.getDateOfJoining();
                    region = beans.getRegion();
                    empSerialNo= beans.getPensionNo();
                    employeeCode=beans.getEmployeeNo();
                    emolument=beans.getEmoluments();
                    emppfstatuary=beans.getPfStatury();
                     airportCode=beans.getStation();
                    designation=beans.getDesignation();
                    fhname=beans.getFhName();
                    department=beans.getDepartment();
                    dateOfMember=beans.getDateOfMembership();
                    if (count % 2 == 0) {

                    %>
						
							<%} else {%>
						<tr>
							<%}%>
							
							<td class="GridLTCells">
								<%=count%>
							</td>
							<td class="GridLTCells" >
								<%=pensionNumber%>
							</td>
							<td class="GridLTCells">
								<%=cpfacno%>
							</td>
								<td class="GridLTCells"  nowrap="nowrap">
								<%=employeeName%>
							</td>
							<td class="GridLTCells" nowrap="nowrap">
								<%=dateofBirth%>
							</td>
							<td class="GridLTCells" nowrap="nowrap">
								<%=dateofJoining%>
							</td>
							<td class="GridLTCells">
								<%=region%>
							</td>
							<td>																	
								<input class="GridLTCells"  type="checkbox" name="cpfno" value="<%=cpfacno%>" onclick="javascript:editPensionMaster('<%=region%>','<%=pensionNumber%>','<%=cpfacno%>','<%=dateOfMember%>','<%=employeeCode%>','<%=designation%>','<%=fhname%>','<%=emolument%>','<%=emppfstatuary%>','<%=department%>','<%=dateofJoining%>','<%=employeeName%>','<%=dateofBirth%>','<%=empSerialNo%>','<%=airportCode%>')" />
							</td>

						</tr>
						<%}%>
</table>
</td>
</tr>

						<%}%>


									</table>
<%=ScreenUtilities.screenFooter()%>
				</form>
	</body>
</html>
