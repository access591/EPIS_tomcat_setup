<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			String privilageStage="";
			if(request.getAttribute("privilageStage")!=null){
			privilageStage = (String)request.getAttribute("privilageStage");
			}
			System.out.println("======privilageStage====="+privilageStage);
			%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Advances - Notifications</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />            
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		var privilageStage	='<%=privilageStage%>';		
			function fwdToNewRequest(pensionNo,advanceTransId,advanceType,purposeType){						 
				var url="<%=basePath%>lanotifications.do?method=fwdToNewRequest&frmPensionNo="+pensionNo+"&frmTransID="+advanceTransId+"&frmAdvanceType="+advanceType+"&frmPurposeType="+purposeType;
				//alert(url);
				document.forms[0].action = url;
				document.forms[0].method="post";
				document.forms[0].submit(); 
			} 
			
			
			function fwdToRelatedScrn(pensionNo,advanceTransId,transDate){
			 var url,frmName,formType="Y",srcFrmName="Notifications";
			// alert(privilageStage);
			 if(privilageStage=="Initial" || privilageStage=="All" ){			 
			      var status='N';	
				  var flag='ApprovalForm';	
			  url="<%=basePath%>checklist.do?method=advanceCheckListApprove&frmName=CPFCheckList&frmPensionNo="+pensionNo+"&frmTransID="+advanceTransId+"&frmAdvStatus="+status+"&frmFlag="+flag+"&srcFrmName="+srcFrmName;
			}else if(privilageStage=="Recommendation" || privilageStage=="All"){
		 	frmName="CPFRecommendation";
		 	url="<%=basePath%>advanceForm2.do?method=loadAdvanceForm2&frmPensionNo="+pensionNo+"&frmTransID="+advanceTransId+"&frm_formType="+formType+"&frmTransDate="+transDate+"&frm_name="+frmName+"&srcFrmName="+srcFrmName;
			}else if(privilageStage=="Approval" || privilageStage=="All"){
		 	frmName="CPFApproval";
		 	url="<%=basePath%>advanceForm2.do?method=loadAdvanceForm2&frmPensionNo="+pensionNo+"&frmTransID="+advanceTransId+"&frm_formType="+formType+"&frmTransDate="+transDate+"&frm_name="+frmName+"&srcFrmName="+srcFrmName;
			}
				// alert(url);
				document.forms[0].action = url;
				document.forms[0].method="post";
				document.forms[0].submit(); 
			
			}
		</script>
	</head>
	<body class="BodyBackground1">
		<html:form method="post" action="/lanotifications.do?method=fwdToNewRequest">
			<%=ScreenUtilities.screenHeader("Notifications")%>
			<table width="95%" cellspacing="0" cellpadding="0" >
				<tr>
					<td class="epis" align="center" width="10%">
						Advances Entries
					</td>					 
					<td class="epis" align="center" width="20%">
						AdvanceTransID
					</td>
					<td class="epis" align="center" width="10%">
						PfId
					</td>
					<td class="epis" align="center" width="20%" >
						Employee Name
					</td>					
					<td class="epis" align="center" width="20%" >
						Purpose Type
					</td>
					<td class="epis" align="center" width="10%" nowrap="nowrap">
						Required Amount
					</td>
					 			
				</tr>
				<tr>
					<td colspan="6" align="left" height="8px"></td>
				</tr>
				<tr>
					<td colspan="6" align="left" class="crv_lineT">
					Advances Entries
					</td> 
				</tr>  
				<tr >
					<td colspan="6" >
						<div style="width:99.7%; height:2cm; overflow:auto;  " class="GridBorder">
							<table width="95%">
							<logic:empty name="cpfsearchlist"><tr><td colspan=5>&nbsp;</td><tr><tr><td colspan=5 class="tableSideText">No Items !</td><tr><tr><td colspan=5>&nbsp;</td><tr></logic:empty>
							<logic:notEmpty name="cpfsearchlist">
								<logic:iterate id="list" name="cpfsearchlist">
									<tr>
										<td  align="center"  width="10%"> 					 										
											<input type="radio"  value="<bean:write name="list" property="advanceTransID" />" name="chk"     onclick="javascript:fwdToRelatedScrn('<bean:write name="list" property="pensionNo" />','<bean:write name="list" property="advanceTransID" />','<bean:write name="list" property="transMnthYear" />');"/>
										</td>
										<td class="tableText" align="center" width="20%">
											<bean:write name="list" property="advanceTransIDDec" /> 
										</td>
										<td class="tableText" align="center" width="10%">											
											<bean:write name="list" property="pensionNo" />
										</td>	
										<td class="tableText" align="center" width="20%">
											<bean:write name="list" property="employeeName" />
										</td>
										<td class="tableText" align="center" width="20%">
											<bean:write name="list" property="purposeType" />
										</td>
										<td class="tableText" align="center" width="10%">											
											<bean:write name="list" property="requiredamt" />
											<html:hidden name="list" property="advanceTransID" />											
											<html:hidden name="list" property="pensionNo" />
											<html:hidden name="list" property="advanceType" />
											<html:hidden name="list" property="purposeType" />
											<html:hidden name="list" property="transMnthYear" />
																						 
										</td>										 
										  
									</tr>
								</logic:iterate>
								</logic:notEmpty>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="left" height="8px"></td>
				</tr>
				<tr>
				<% if((privilageStage.equals("Initial")) || (privilageStage.equals("All"))){%>
					<td class="epis" align="center" width="10%">
						Raise New Request
					</td>	
					<%}%> 				 
					<td class="epis" align="center" width="20%">
						AdvanceTransID
					</td>
					<td class="epis" align="center" width="10%">
						PfId
					</td>
					<td class="epis" align="center" width="20%" >
						Employee Name
					</td>				
					<td class="epis" align="center" width="20%" >
						Purpose Type
					</td>
					<td class="epis" align="center" width="20%" >
						Airport Name
					</td>				
				</tr>
				<tr>
					<td colspan="6" align="left" height="8px"></td>
				</tr>
				<tr>
				
					<td colspan="7" align="left" class="crv_lineT">
					
						Rejected Advances
						
					</td> 
					
				</tr>
				<tr >
					<td colspan="7" >
						<div style="width:99.7%; height:2cm; overflow:auto;  " class="GridBorder">
							<table width="100%">
							<logic:empty name="cpf"><tr><td colspan=5>&nbsp;</td><tr><tr><td colspan=5 class="tableSideText">No Items !</td><tr><tr><td colspan=5>&nbsp;</td><tr></logic:empty>
							<logic:notEmpty name="cpf">
								<logic:iterate id="rec" name="cpf">
									<tr>
									<% if((privilageStage.equals("Initial")) || (privilageStage.equals("All"))){%>
											
										<td width="10%">
										<input type="radio"   name="regchk"  value='<bean:write name="rec" property="transactionId" />' onclick="javascript:fwdToNewRequest('<bean:write name="rec" property="pensionNo" />','<bean:write name="rec" property="transactionId" />','<bean:write name="rec" property="advanceType" />','<bean:write name="rec" property="purposeType" />');"/>
											
										</td>
										<%}%> 
										<td class="tableText" align="center" width="20%">
											<bean:write name="rec" property="keyNo" />
										</td>	
										<td class="tableText" align="center" width="10%">
											<bean:write name="rec" property="pensionNo" />
											
										</td> 
										<td class="tableText" align="center" width="20%">
											<bean:write name="rec" property="employeeName" />
										</td>
										<td class="tableText" align="center" width="20%">
											<bean:write name="rec" property="purposeType" />
										</td>
										<td class="tableText" align="center" width="20%">											
											<bean:write name="rec" property="airportCode" />
											<html:hidden name="rec" property="transactionId" />											
											<html:hidden name="rec" property="pensionNo" />
											<html:hidden name="rec" property="advanceType" />
											<html:hidden name="rec" property="purposeType" />
										 
											
										</td>										 
										 
									</tr>
								</logic:iterate>
								</logic:notEmpty>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="left" >
						&nbsp;
					</td>
				</tr>
				 <tr>				 			 
					<td class="epis" align="center" width="20%">
						AdvanceTransID
					</td>
					<td class="epis" align="center" width="10%">
						PfId
					</td>
					<td class="epis" align="center" width="20%" >
						Employee Name
					</td>				
					<td class="epis" align="center" width="20%" >
						Purpose Type
					</td>
					<td class="epis" align="center" width="20%" >
						Airport Name
					</td>	
					<td class="epis" align="center" width="20%" >
						Voucher No
					</td>				
				</tr>
				<tr>
					<td colspan="6" align="left" height="8px"></td>
				</tr>
				<tr>				
					<td colspan="6" align="left" class="crv_lineT">
					
						CHQ Approved Payments
						
					</td> 
					
				</tr>
				<tr >
					<td colspan="6" >
						<div style="width:99.7%; height:2cm; overflow:auto;  " class="GridBorder">
							<table width="95%">
							<logic:empty name="cashBookList"><tr><td colspan=5>&nbsp;</td><tr><tr><td colspan=5 class="tableSideText">No Items !</td><tr><tr><td colspan=5>&nbsp;</td><tr></logic:empty>
							<logic:notEmpty name="cashBookList">
								<logic:iterate id="cblist" name="cashBookList">
									<tr>									 
										<td class="tableText" align="center" width="20%">
											<bean:write name="cblist" property="advanceTransIDDec" />
										</td>	
										<td class="tableText" align="center" width="10%">
											<bean:write name="cblist" property="pensionNo" />
											
										</td> 
										<td class="tableText" align="center" width="20%">
											<bean:write name="cblist" property="employeeName" />
										</td>
										<td class="tableText" align="center" width="20%">
											<bean:write name="cblist" property="purposeType" />
										</td>
										<td class="tableText" align="center" width="20%">											
											<bean:write name="cblist" property="station" />		 
											
										</td>	
										<td class="tableText" align="center" width="20%">											
											<bean:write name="cblist" property="voucherNo" />		 
											
										</td>									 
										 <td class="tableText" align="center" width="20%">											
											&nbsp;	 
											
										</td>
									</tr>
								</logic:iterate>
								</logic:notEmpty>
							</table>
						</div>
					</td>
				</tr> 
				 
				 
				 
				 
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
