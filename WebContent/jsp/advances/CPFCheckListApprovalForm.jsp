<!--
/*
  * File       : CPFCheckListApprovalForm.jsp
  * Date       : 02/04/2010
  * Author     : Suneetha V 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();
String basePathWoView = basePathBuf.toString();
String srcFrmName="";
if (request.getParameter("srcFrmName") != null) {
			srcFrmName = (String)request.getParameter("srcFrmName");
		}
%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html>
	<head>
		<base href="<%=basePath%>" />

		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="this is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/commonfunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/datetime.js"></script>
		<script type="text/javascript">
		var flag='N';	
		function saveForm2(){		
			var lodcheckedlngth='',total='',advanceType='',purposeType='';
			
			advanceType=document.forms[0].advanceType.value;
		 		purposeType=document.forms[0].purposeType.value;	
			
		 if(advanceType=='CPF' && purposeType=='EDUCATION'){
		 			lodcheckedlngth=document.forms[0].LODCPFHE.length;		 			
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODCPFHE[i].checked==true){
		 					lodhba=document.forms[0].LODCPFHE[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 	}
		 	if(advanceType=='CPF' && purposeType=='OBMARRIAGE'){
		 			lodcheckedlngth=document.forms[0].LODOBMARR.length;		 			
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODOBMARR[i].checked==true){
		 					lodhba=document.forms[0].LODOBMARR[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 	}
		 			
		 			//alert('-----total------'+total);
		 					 			   
		  		
				url="<%=basePathWoView%>checklist.do?method=saveCPFCheckList&lodinfo="+total+"&srcFrmName="+'<%=srcFrmName%>';
				 
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();				
		}
	
	
		function frmPrsnlReset(){
			var pensionNo=document.forms[0].pensionNo.value;
			var transID=document.forms[0].advanceTransIDDec.value;
			flag='ApprovalEdit';
			var url="<%=basePath%>checklist.do?method=advanceCheckListApprove&frmName=CPFCheckList&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmFlag="+flag;
			 	
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
	
	
	
		function reportAdvances()
		{		
		var transID,pensionNo;
		transID=document.forms[0].advanceTransIDDec.value;
		pensionNo=document.forms[0].pensionNo.value;
		
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
	    var url="<%=basePath%>loadAdvance.do?method=advanceReport&frmPensionNo="+pensionNo+"&frmTransID="+transID;
		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
   	 	winOpened = true;
		wind1.window.focus();
		}
		
		function checkLOD(){
		if(document.forms[0].chkwthdrwlflag.value=='N'){
		document.getElementById("lodrow").style.display="none";	  
		}else{
		document.getElementById("lodrow").style.display="block";	 
		}
		}
		
		function listOfDocs(){
		if(document.forms[0].chkwthdrwlflag.value=='Y')
		    document.getElementById("lodrow").style.display="block";
		else
		 	document.getElementById("lodrow").style.display="none";	  
		  
		}
	
 		</script>
	</head>

	<body class="bodybackground" onload="checkLOD();">

		<html:form method="post" action="checklist.do?method=advanceCheckListApprove">
			<%=ScreenUtilities.screenHeader("ECPF CPF Advance Check list")%>
			<table width="720" border="0" cellspacing="3" cellpadding="0" align="center">


				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						PF ID&nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:hidden property="advanceType" />
						
						<html:hidden property="pensionNo" />
						<html:hidden property="advanceTransIDDec" />
						<html:hidden property="advntrnsid" />

						<html:text styleClass="TextField" property="pfid" tabindex="1" readonly="true" />
					</td>
				</tr>


				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Employee Name &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="employeeName" readonly="true" />
					</td>
				</tr>


				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Date of Joining in Fund&nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="dateOfMembership" readonly="true" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Previous advance outstanding&nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="outstndamount" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Purpose of Advance &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="purposeType"  readonly="true" />
						<!--<html:select property="purposeType" name="advanceChkListBean" styleClass="TextField" tabindex="5">

							<html:option value='COST'>
									Cost Of Passage
								</html:option>
							<html:option value='OBLIGATORY'>
									Obligatory Expanses
								</html:option>
							<html:option value='OBMARRIAGE'>
									Marriage Expanses
								</html:option>
							<html:option value='ILLNESS'>
									Illness Expanses
								</html:option>

							<html:option value='EDUCATION'>
										Higher Education
									</html:option>
							<html:option value='DEFENCE'>
										Defense Of Court Case
									</html:option>
							<html:option value='OTHERS'>
										 Others
									</html:option>
						</html:select> 
					--></td>
				</tr>



				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Advance Applied &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="advnceapplid" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Total No of Installments &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>

						<html:select styleClass="TextField" property="totalInst" name="advanceChkListBean">
							<html:option value="NO-SELECT">
														Select one
													</html:option>
							<%for(int i=1;i<=36;i++){%>
							<html:option value="<%=String.valueOf(i)%>">
								<%=i%>
							</html:option>
							<%}%>
						</html:select>


					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Enclosures are in order &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:select styleClass="TextField" property="chkwthdrwlflag" style="width:119px" onchange="listOfDocs();">
							<html:option value='Y'>Yes</html:option>
							<html:option value='N'>No</html:option>
						</html:select>
					</td>
				</tr>

				<%
										  String chk1="",chk2="",chk3="",chk4="",chk5="",chk6="";
										%>
				<logic:equal property="advanceType" value="CPF" name="advanceChkListBean">
					<logic:equal property="purposeType" value="OBMARRIAGE" name="advanceChkListBean">
						<tr>
							<td colspan="4">
								<logic:present name="LODList">
									<logic:iterate name="LODList" id="lod">
										<logic:equal name="lod" property="lodInfo" value="MIC">
											<%chk1="checked";%>
										</logic:equal>



										<logic:equal name="lod" property="lodInfo" value="PAD">
											<%chk2="checked";%>
										</logic:equal>
									</logic:iterate>
								</logic:present>
								<table align="center" cellpadding="1" cellspacing="1">
									<tr>
										<td class="tableSideText">
											1.Marriage invitation card
										</td>
										<td>
											<input type="checkbox" name="LODOBMARR" value="MIC" <%=chk1%>>
										</td>
									</tr>
									<tr>
										<td class="tableSideText">
											2.Proof of Age & Dependency
										</td>
										<td>
											<input type="checkbox" name="LODOBMARR" value="PAD" <%=chk2%>>
										</td>
									</tr>


								</table>
							</td>
						</tr>

						<bean:define id="loddocument" property="lodInfo" name="advanceChkListBean" />
					</logic:equal>
				</logic:equal>
				<logic:equal property="advanceType" value="CPF" name="advanceChkListBean">
					<logic:equal property="purposeType" value="EDUCATION" name="advanceChkListBean">
						<tr>
							<td colspan="4">
								<logic:present name="LODList">
									<logic:iterate name="LODList" id="lod">
										<logic:equal name="lod" property="lodInfo" value="PROS">
											<%chk1="checked";%>
										</logic:equal>



										<logic:equal name="lod" property="lodInfo" value="CIFBS">
											<%chk2="checked";%>
										</logic:equal>


										<logic:equal name="lod" property="lodInfo" value="FS">
											<%chk3="checked";%>
										</logic:equal>


										<logic:equal name="lod" property="lodInfo" value="CCLEP">
											<%chk4="checked";%>
										</logic:equal>
									</logic:iterate>
								</logic:present>
								<table align="center" cellpadding="1" cellspacing="1">
									<tr>
										<td class="tableSideText">
											1.Prospectus
										</td>
										<td>
											<input type="checkbox" name="LODCPFHE" value="PROS" <%=chk1%>>
										</td>
									</tr>
									<tr>
										<td class="tableSideText">
											2.Certificate from the institution for bonafied students.
											<br />
											site/house to be purchased
										</td>
										<td>
											<input type="checkbox" name="LODCPFHE" value="CIFBS" <%=chk2%>>
										</td>
									</tr>
									<tr>
										<td class="tableSideText">
											3.Fee structure
										</td>
										<td>
											<input type="checkbox" name="LODCPFHE" value="FS" <%=chk3%>>
										</td>
									</tr>
									<tr>
										<td class="tableSideText">
											4.Copy of certificate of last examination passed.
										</td>
										<td>
											<input type="checkbox" name="LODCPFHE" value="CCLEP" <%=chk4%>>
										</td>
									</tr>

								</table>
							</td>
						</tr>
			</table>

			<bean:define id="loddocument" property="lodInfo" name="advanceChkListBean" />
			</logic:equal>
			</logic:equal>


			<tr>
				<td colspan="4">
					<table width="720" border="0" cellspacing="3" cellpadding="0" align="center">

						<tr>
							<td width="50%" align="right" id="submit">
								<input type="button" class="butt" value="Submit" onclick="saveForm2();" tabindex="10" />
							</td>
							<td align="left" width="50%">

								<input type="button" class="butt" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" tabindex="11" />
								<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="12" />
							</td>
						</tr>
					</table>
				</td>
			</tr>



			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

