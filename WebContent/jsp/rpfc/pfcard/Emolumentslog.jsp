<%@ include file="/jsp/rpfc/common/inc.jsp" %>

<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
           
      
            
           
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

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
  	  	
   		var pfId = document.forms[0].pfId.value;
   		statusdisplay();      		
   		var url ="<%=basePath%>search1?method=getEmolumentslog&pfId="+pfId;  
   		    
	    document.forms[0].action=url;
	    document.forms[0].method="post";
		document.forms[0].submit();			
	}	 
	 </script>
	</head>

	<body>
		 <table width="100%" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   <td>
   <table width="100%"  cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   
    <td width="120" rowspan="3" align="center"><img src="<%=basePath%>images/logoani.gif" width="88" height="50" align="right" /></td>
    <td class="reportlabel" nowrap="nowrap">AIRPORTS AUTHORITY OF INDIA</td>
    	<td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
     	<td width="96">&nbsp;</td>
     	<td width="95">&nbsp;</td>
     	<td width="85">&nbsp;</td>
  	 	<td width="384"  class="reportlabel">Employee's Provident Fund Trust</td>
  	 	<td width="87">&nbsp;</td>
    	<td width="272">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="3" class="reportlabel" style="text-decoration: underline" align="center">
Edit Emoluments Log </td>
  </tr>
</table>
</td>
</tr>

			<%EmolumentslogBean dbBeans = new EmolumentslogBean();

            if (request.getAttribute("emolumentsloginfo") != null) {
                ArrayList datalist = new ArrayList();
                int totalData = 0;
                datalist = (ArrayList) request.getAttribute("emolumentsloginfo");
                System.out.println("dataList " + datalist.size());

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
				<td height="25%">
					<table align="center" width="100%" cellpadding="1"  cellspacing="1" border="1">
						<tr ><td class="label">
                          SR NO
                        </td>
                           <td class="label">
								PENSION NO&nbsp;&nbsp;
							</td>
							<td class="label">
								MONTHYEAR&nbsp;&nbsp;
							</td>
						<td class="label">OLDEMOLUMENTS
							</td>
							<td class="label">
								NEWEMOLUMENTS
							</td>
							<td class="label">
								OLDEMPPFSTATUARY
							</td>
							<td class="label">
								NEWEMPPFSTATUARY
							</td>							
							<td class="label">
								UPDATEDDATE
							</td>
<td class="label">
								REGION
							</td>		
</tr>

						<%}%>
						<%int count = 0;
               
                for (int i = 0; i < datalist.size(); i++) {
                    count++;
                    EmolumentslogBean beans = (EmolumentslogBean) datalist.get(i);

                   String OLDEMOLUMENTS = beans.getOldEmoluments();
                   String NEWEMOLUMENTS = beans.getNewEmoluments();
                   String pensionNumber = beans.getPensionNo();
                   String OLDEMPPFSTATUARY = beans.getOldEmppfstatury();
                   String NEWEMPPFSTATUARY = beans.getNewEmppfstatury();
                   String MONTHYEAR = beans.getMonthYear();
                   String UPDATEDDATE = beans.getUpdatedDate();
                   String REGION = beans.getRegion();
                   if (count % 2 == 0) {

                       %>
   						<tr>
   							<%} else {%>
   						<tr>
   							<%}%>
                            <td class="Data" width="6%">
								<%=count%>
							</td>
							<td class="Data" width="10%">
                              <%=pensionNumber%>
                            </td>
							<td class="Data" width="12%">
								<%=MONTHYEAR%>
							</td>
							<td class="Data" width="12%">
								<%=OLDEMOLUMENTS%>
							</td>
							<td class="Data" width="12%">
								<%=NEWEMOLUMENTS%>
							</td>
							<td class="Data" width="12%">
								<%=OLDEMPPFSTATUARY%>
							</td>
							<td class="Data" width="12%">
								<%=NEWEMPPFSTATUARY%>
							</td>
							
							<td class="Data" width="12%">
								<%=UPDATEDDATE%>
							</td>	
                            <td class="Data" width="12%">
								<%=REGION%>
							</td>						

						</tr>
						<%}%>


						<%}%>

					</table>
				</td>

			</tr>
</table>
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   <td>
   <table width="100%"  cellspacing="0" cellpadding="0">
<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<tr>   
<td>&nbsp;</td>
      
    <td colspan="4" class="reportlabel" style="text-decoration: underline" align="center">
Delete Emoluments Log </td>
  </tr>
</table>
</td>
</tr>
<%EmployeeValidateInfo dbbeans1 = new EmployeeValidateInfo();

            if (request.getAttribute("deleteloginfo") != null) {
                ArrayList datalists = new ArrayList();
                int totalData = 0;
                datalists = (ArrayList) request.getAttribute("deleteloginfo");
                System.out.println("dataList " + datalists.size());

                if (datalists.size() == 0) {

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

			<%} else if (datalists.size() != 0) {%>
			<tr>
				<td height="25%">
					<table align="center" width="100%" cellpadding="1"  cellspacing="1" border="1">
						<tr ><td class="label">
                          SR NO
                        </td>
                            <td class="label">
								PENSION NO&nbsp;&nbsp;
							</td>						
							<td class="label">
								MONTHYEAR&nbsp;&nbsp;
							</td>
                            <td class="label">EMOLUMENTS
							</td>
							<td class="label">
								EMPPFSTATUARY
							</td>
                             <td class="label">
								USER NAME
							</td>
							<td class="label">
								IPADDRESS
							</td>
                            <td class="label">
								UPDATEDDATE
							</td>
							<td class="label">
								REGION
							</td>		
</tr>

						<%}%>
						<%int count = 0;
               
                for (int i = 0; i < datalists.size(); i++) {
                    count++;
                    EmployeeValidateInfo beanslog = (EmployeeValidateInfo) datalists.get(i);

                   String EMOLUMENTS = beanslog.getEmoluments();
                   String pensionNumber = beanslog.getPensionNumber();
                   String EMPPFSTATUARY = beanslog.getEmpPFStatuary();
                   String MONTHYEAR = beanslog.getMonthYear();
                   String UPDATEDDATE = beanslog.getUpdatedDate();
                   String USERNAME = beanslog.getUserName();
                   String IPADDRESS = beanslog.getIpAddress();
                   String REGION=beanslog.getRegion();
                   if (count % 2 == 0) {

                       %>
   						<tr>
   							<%} else {%>
   						<tr>
   							<%}%>
							
							<td class="Data" width="6%">
								<%=count%>
							</td>
							<td class="Data" width="10%">
								<%=pensionNumber%>
							</td>
							<td class="Data" width="12%">
								<%=MONTHYEAR%>
							</td>
							<td class="Data" width="12%">
								<%=EMOLUMENTS%>
							</td>
							<td class="Data" width="12%">
                              <%=EMPPFSTATUARY%>
                            </td>
							<td class="Data" width="12%">
								<%=USERNAME%>
							</td>
							<td class="Data" width="12%">
								<%=IPADDRESS%>
							</td>	
                           <td class="Data" width="12%">
								<%=UPDATEDDATE%>
							</td>
                            <td class="Data" width="12%">
								<%=REGION%>
							</td>							

						</tr>
						<%}%>


						<%}%>

					</table>
				</td>

			</tr>
			</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   <td>
   <table width="100%"  cellspacing="0" cellpadding="0">
<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<tr>   
<td>&nbsp;</td>
      
    <td colspan="6" class="reportlabel" style="text-decoration: underline" align="center">Adj Log </td>
  </tr>
</table>
</td>
</tr>
<%EmolumentslogBean logbean = new EmolumentslogBean();

            if (request.getAttribute("adjLoginfo") != null) {
                ArrayList adjlists = new ArrayList();
                int totalData = 0;
                adjlists = (ArrayList) request.getAttribute("adjLoginfo");
                System.out.println("dataList " + adjlists.size());

                if (adjlists.size() == 0) {

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

			<%} else if (adjlists.size() != 0) {%>
			<tr>
				<td height="25%">
					<table align="center" width="100%" cellpadding="1"  cellspacing="1" border="1">
						<tr ><td class="label">
                          SR NO
                        </td>
                            <td class="label">
								PENSIONNO&nbsp;&nbsp;
							</td>						
							<td class="label">
								ADJOBYEAR&nbsp;&nbsp;
							</td>
                            <td class="label">EMPCONTRI
							</td>
							<td class="label">
								AAICONTRI
							</td>
                             <td class="label">
								EMPCONTRIDEVIATION
							</td>
							<td class="label">
								AAICONTIDEVIATION
							</td>
                            <td class="label">
								REMARKS
							</td>
				<td class="label">
								Adjustment Justification
							</td>
					</tr>

						<%}%>
						<%int count = 0;
               
                for (int i = 0; i < adjlists.size(); i++) {
                    count++;
                    EmolumentslogBean adjlog = (EmolumentslogBean) adjlists.get(i);

                   String PENSIONNO = adjlog.getPfId();
                   String ADJOBYEAR = adjlog.getAdjObYear();
                   String EMPCONTRI = adjlog.getEmpContri();
                   String AAICONTRI = adjlog.getAaiContri();
                   String EMPCONTRIDEVIATION = adjlog.getEmpContriDeviation();
                   String AAICONTIDEVIATION = adjlog.getAaiContriDeviation();
                   String REMARKS = adjlog.getRemarks();
                   String adjJustUrl=adjlog.getAdjUrl();
                 
                   if (count % 2 == 0) {

                       %>
   						<tr>
   							<%} else {%>
   						<tr>
   							<%}%>
							
							<td class="Data" width="6%">
								<%=count%>
							</td>
							<td class="Data" width="10%">
								<%=PENSIONNO%>
							</td>
							<td class="Data" width="12%">
								<%=ADJOBYEAR%>
							</td>
							<td class="Data" width="12%">
								<%=EMPCONTRI%>
							</td>
							<td class="Data" width="12%">
                              <%=AAICONTRI%>
                            </td>
							<td class="Data" width="12%">
								<%=EMPCONTRIDEVIATION%>
							</td>
							<td class="Data" width="12%">
								<%=AAICONTIDEVIATION%>
							</td>
                           <td class="Data" width="12%">
								<%=REMARKS%>
							</td>      

<td class="Data" width="12%">
     <%if(adjJustUrl!=""){ %>
							<a href="<%=adjJustUrl%>" >Click the link to view Adj Justification </a>
    <%} %>
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
