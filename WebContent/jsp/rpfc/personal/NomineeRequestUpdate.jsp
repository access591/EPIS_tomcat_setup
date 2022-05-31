<%@ page import="java.util.*,com.epis.utilities.*"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>


<%@ page import="com.epis.bean.rpfc.*"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String arrearInfoString1 = "";
            String regionFlag = "false";
            System.out.println("the path is:"+path);
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
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />	
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/calendar.js"></SCRIPT>

		
	</head>

	<body class="BodyBackground"  >
	
		<form method="post" action="/nomineesearchservlet.do?method=nomineerequestUpdate">
		<%=ScreenUtilities.screenHeader("Approval For Nominee")%>
			<table width="100%" border=0 align="center" >
				<tbody >
				<tr>
					<td colspan=4 align=center>&nbsp;
					</td>
				</tr>
			
				
				<%
				EmpMasterBean bean1=null;
				if (request.getAttribute("EditBean") != null) {
                 bean1 = (EmpMasterBean) request.getAttribute("EditBean");
                System.out.println("the pfid is:"+bean1.getPfid());
                }
                 %>
                <tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 PF ID:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<input type="hidden" name="empSerialNo" value='<%=bean1.getEmpSerialNo()%>'>
				<input type="hidden" name="cpfNo" value='<%=bean1.getNewCpfAcNo()%>'>
				<input type="hidden" name="region" value='<%=bean1.getRegion()%>'>
				<%=bean1.getPfid()%>    
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Old CPFACC.NO :
				</td>
				<td width="20%" class="tableText" nowrap align=left>
					<%=bean1.getCpfAcNo()%>        
                </td>			
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Employee Code:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getEmpNumber()%>      
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Employee Name :
				</td><td width="20%" class="tableText" nowrap align=left>
					<%=bean1.getEmpName()%>
                </td>			
			</tr>
			
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Father's / Husband's Name:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getFhName()%>     
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Sex :
				</td><td width="20%" class="tableText" nowrap align=left>
					<%
					String sex = bean1.getSex().trim();
					if(sex.equals("M"))
					out.println("Male");
					else
					out.println("Female");
					%>
                </td>			
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Date of Birth:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getDateofBirth()%>     
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Date of Joining :
				</td><td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getDateofJoining()%>  
					
                </td>			
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Employee Level:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getEmpLevel().trim()%>     
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Designation :
				</td ><td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getDesegnation()%>  
					
                </td>			
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Discipline:
				</td>
				<td width="20%" nowrap align=left align=left>
				<%=bean1.getDepartment().trim()%>     
				</td>	
				<td  width="20%" class="tableTextBold"  align=right nowrap>
					Division :
				</td align=left><td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getDivision().trim()%>  
					
                </td>			
			</tr>
			<tr>
			<td colspan=4>&nbsp;</td>
			</tr>
			<tr>
			<td colspan=4 align=center>
										
										<table border=0 align="center" width="95%">
											<tr>
												<td  class="tableTextBold" nowrap>
													Nomination for PF
												</td>

											</tr>
											

											<tr>
												<td class="tableTextBold">
													Name&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Dateof Birth&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Relation with<br> Member&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Name of <br>Guardian&nbsp;&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Address of <br>Guardian&nbsp;&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Total Share
													<BR>
													payable in %
												</td>
												<td class="tableTextBold" nowrap >
													Nominee <br>Request
												</td>


											</tr>
											
				<%
				int count1 = 0;
                String nomineeRows = bean1.getNomineeRow();

                CommonUtil commonUtil = new CommonUtil();
                ArrayList nomineeList = commonUtil.getTheList(nomineeRows,
                        "***");
                System.out.println("the size is"+nomineeList.size());

                String tempInfo[] = null;
                String tempData = "";
                String nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", addressofGuardian = "",srno="",rowid="";
                for (int i = 0; i < nomineeList.size(); i++) {
                    count1++;
                    tempData = nomineeList.get(i).toString();
                    tempInfo = tempData.split("@");
                    String nomineeName = tempInfo[0];
                    nomineeAddress = tempInfo[1];
                    nomineeDob = tempInfo[2];
                    nomineeRelation = tempInfo[3];
                    nameofGuardian = tempInfo[4];
                    addressofGuardian = tempInfo[5];
                    totalShare = tempInfo[6];
                    srno=tempInfo[7];
                    System.out.println("the serial no is:"+srno);
                    if (nomineeAddress.equals("xxx")) {
                        nomineeAddress = "";
                    }

                    if (nomineeDob.equals("xxx")) {
                        nomineeDob = "";
                    }
                    if (nomineeRelation.equals("xxx")) {
                        nomineeRelation = "";
                    }

                    if (nameofGuardian.equals("xxx")) {
                        nameofGuardian = "";
                    }
                    if (addressofGuardian.equals("xxx")) {
                        addressofGuardian = "";
                    }
                    if (totalShare.equals("xxx")) {
                        totalShare = "";
                    }
                    if(srno.equals("xxx"))
                    {
                    	srno="";
                    }
                    
                    rowid = tempInfo[8];
                    if (rowid.equals("xxx")) {
                        rowid = "";
                    }

                    %>
											<tr >
												<td class="epis">
													<%=nomineeName%>
													<input type="hidden" name="srno" value='<%=srno%>'>
													<input type="hidden" name="empsrno" value='<%=bean1.getEmpSerialNo()%>'>
													
												</td>
												<td >
													<%=nomineeAddress%>
												</td>
												<td nowrap >
													<%=nomineeDob%>
												</td>
												<td >
												<%=nomineeRelation%>
												</td>
												   
												<td>
													<%=nameofGuardian%>
												</td>
												<td>
													<%=addressofGuardian%>
												</td>
												<td>
													<%=totalShare%>
												</td>
												<td>
												
														<select class="tableText" name="nomineeAccept"  style="width:50 px">
														<option value="A" selected">Accept</option>
														<option value="R">Reject</option>
														</select>
													</td>


											</tr>
											<%
											}

            								%>
											

            

										</table>
																				
										
			
			</td>
			</tr>
			<tr>
			<td colspan=4>&nbsp;</td>	
			</tr>
			<tr>
			<tr>
			<td  colspan=4 align="center">
			
				<input type="submit" class="butt" value="Update" >
				<input type="reset" class="butt" value="Reset" >
				<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)">
			</td>
			</tr>		

		</table>
<%=ScreenUtilities.screenFooter()%>
		</form>
	</body>
</html>



