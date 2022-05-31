<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
                    
            %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>AAI - Admin - Masters - User New Screen</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="description" content="User New Screen"/>		
		<link href="css/style.css" rel="stylesheet" type="text/css" />	
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<SCRIPT SRC="/js/DateTime1.js"></SCRIPT>
		<SCRIPT SRC="/js/EMail and Password.js"></SCRIPT>
		<SCRIPT>
			function validate(){
				TrimAll(0);
				if(document.forms[0].userName.value==''){
					alert("Please Enter User Name (Mandatory)");
					document.forms[0].userName.focus();
					return false;
				}
				if(!ValidateAlphaNumeric(document.forms[0].userName.value)){
					alert("Please Enter Valid User Name (Alphanumeric)");
					document.forms[0].userName.focus();
					return false;
				}
				if(document.forms[0].employeeId.value==''){
					alert("Please Enter Employee Id (Mandatory)");
					document.forms[0].employeeId.focus();
					return false;
				}
				if(!ValidateAlphaNumeric(document.forms[0].employeeId.value)){
					alert("Please Enter Valid Employee Id (Alphanumeric)");
					document.forms[0].employeeId.focus();
					return false;
				}
				if (document.forms[0].email.value != ""){ 
					if(!ValidateEMail(document.forms[0].email.value) ){
						document.forms[0].email.focus();
						return false;
					}
				}
				if(document.forms[0].unit.value==''){
					alert("Please Enter Unit (Mandatory)");
					document.forms[0].unit.focus();
					return false;
				}
				if(document.forms[0].profileType.value==''){
					alert("Please Enter Profile Type (Mandatory)");
					document.forms[0].profileType.focus();
					return false;
				}
				if(document.forms[0].expireDate.value!=""){
		   			if(!convert_date(document.forms[0].expireDate)){
						return false;
					}
					convert_date(document.forms[0].expireDate);					
				}		
					if(document.forms[0].designation.value==''){
					alert("Please Enter User Designation");
					document.forms[0].designation.focus();
					return false;
				}		 
			} 
			function whileLoading(){
				if(document.forms[0].esignatoryName.value != ""){
				//alert("uploads/dbf/"+document.forms[0].esignatoryName.value);
				document.getElementById("eSignImage").src = "uploads/dbf/"+document.forms[0].esignatoryName.value;
				document.getElementById("eSignImage").style.display="block";
				}
				document.forms[0].userName.focus();
			}
			function popupWindow(mylink, windowname)
			{
			
			//var transfer=document.forms[0].transferStatus.value;
			var transfer="";
		
			var regionID="";
			if(document.forms[0].select_region.selectedIndex>0){
				regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
			}else{
				regionID=document.forms[0].select_region.value;
			}
			if (! window.focus)return true;
			var href;
			if (typeof(mylink) == 'string')
			   href=mylink+"?transferStatus="+transfer+"&region="+regionID;
			   
			else
			href=mylink.href+"?transferStatus="+transfer+"&region="+regionID;
		    
			progress=window.open(href, windowname, 'width=700,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
			
			return true;
			}
			function popupWindow(windowname){		
			var pensionNo;
			pensionNo=document.forms[0].employeeId.value;
					
			if (! window.focus)return true;
			var href;
	   		href="loadAdvance.do?method=loadLookupPFID&frm_pensionno="+pensionNo;
			progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
		    }
		    function test(pfids,empcode,empname,designation,fname,department,dojaai,dojcpf,dob,emolument,pensionNo,region,emppfstatuary,cpfaccno,airportcode){	   
	   	    document.forms[0].employeeId.value=pensionNo;
			document.forms[0].displayName.value=empname;
		
	  	}	
		</SCRIPT>
	</head>
	<body onload="whileLoading()" >
		<html:form action="/userNew.do?method=add" method="POST" onsubmit="return validate();" enctype="multipart/form-data">
		<%=ScreenUtilities.screenHeader("user.title")%>
			<html:hidden property="userId" />		
			
				<table width="70%" border="0" align="center">
					<tbody>
						<tr>
							<td colspan="4" align="center">
								&nbsp;
								<html:errors bundle="error" />
							</td>
						</tr>
						<tr>
							<td width="20%" class="tableTextBold" align="right" nowrap>
								<bean:message key="user.username" bundle="common" />:
							</td>
							<td width="20%" align="left">
								<html:text maxlength="35" property="userName" styleClass='TextField' size="12" tabindex="1"/>
							</td>
							<td width="20%" class="tableTextBold" align="right" nowrap>
								<bean:message key="user.employeeid" bundle="common" />
								:
							</td>
							<td width="20%" align="left" nowrap>
								<html:text maxlength="50" property="employeeId" styleClass='TextField' size="12" tabindex="2" readonly="true" />
									<!-- <input type="text" name="pfid" />-->
									<img src="<%=basePath%>/images/search1.gif" onclick="popupWindow('AdvanceFormInfo');" alt="Click The Icon to Select Details" />
									
							</td>
						</tr>
						<tr>
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.email" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:text maxlength="50" property="email" styleClass='TextField' style="width:120 px" tabindex="3"/>
							</td>

							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.unit" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:select property="unit" styleClass="TextField" style="width: 125px" tabindex="4">
									<html:option value="">
									Select One
								</html:option>
									<html:options name="UnitBean" property="unitCode" labelProperty="unitName" labelName="code" collection="unitList" />
								</html:select>
							</td>

						</tr>

						<tr>
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.usertype" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:select property="userType" styleClass="TextField" style="width: 90px" tabindex="5">
									<html:option value="U">
									User
								</html:option>
								<html:option value="N">
									Nodal Officer
								</html:option>
									<html:option value="A">
									Admin
								</html:option>
								
								</html:select>
							</td>

							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.profiletype" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:select property="profileType" styleClass="TextField" style="width: 90px" tabindex="6">
									<html:option value="">
										Select One
									</html:option>
									<html:option value="M">
										Member Level
									</html:option>
									<html:option value="U">
										Unit Level
									</html:option>
									<html:option value="R">
										RHQ Level
									</html:option>
									<html:option value="C">
										CHQ Level
									</html:option>
								</html:select>
							</td>

						</tr>
						<tr>
<logic:equal value="USER" name="Role" scope="request">							
						
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.modules" bundle="common" />
								:
							</td>
							<td width="20%" align="left">					
								<html:select property="modules" styleClass="TextField" style="width:120 px ;height:50 px" multiple="true" tabindex="7">
									<html:options name="Bean" property="code" labelProperty="name" labelName="code" collection="moduleList" />
								</html:select>									
							</td>
</logic:equal>									
<logic:equal value="ROLE" name="Role" scope="request">	
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="role.rolename" bundle="common" />
								:
							</td>
							<td width="20%" align="left">					
								<html:select property="roleCd" styleClass="TextField" style="width:120 px ;" tabindex="7">
									<html:options name="Bean" property="code" labelProperty="name" labelName="code" collection="roleList" />
								</html:select>									
							</td>						
</logic:equal>					
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.status" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:select property="status" styleClass="TextField" style="width: 90px" tabindex="8">
									<html:option value="Y">
										Active
									</html:option>
									<html:option value="N">
										In-Active
									</html:option>
								</html:select>
							</td>

						</tr>
						<tr>
							<td class="tableTextBold" width="20%" align="right" nowrap>
								<bean:message key="user.expiredate" bundle="common" />
								:
							</td>
							<td width="20%" nowrap align="left">
								<html:text maxlength="11" property="expireDate" styleId="expireDate" styleClass='TextField' style="width:80 px" tabindex="9"/>
								<html:link href="javascript:showCalendar('expireDate');">
									<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
								</html:link>
							</td>
							<td class="tableTextBold" width="20%" align="right" nowrap>
								<bean:message key="user.displayname" bundle="common" />
								:
							</td>
							<td width="20%" nowrap align="left">&nbsp;
							<html:text maxlength="50" property="displayName" styleClass='TextField' style="width:190 px" tabindex="10"/>
							</td>
							
						</tr>
								<tr>
						<td class="tableTextBold" width="20%" align="right" nowrap>
								<bean:message key="user.designation" bundle="common" />
								:
							</td>
							<td width="20%" nowrap align="left">
							<html:text maxlength="50" property="designation" styleClass='TextField' style="width:190 px" tabindex="10"/>
							</td>
						</tr>
						<tr>
							
							<td class="tableTextBold" width="20%" align="right" nowrap>
								<bean:message key="user.esignatory" bundle="common" />
								:
							</td>
							<td width="20%" >
								
								<html:file property="esignatory" tabindex="11"></html:file>	
														
								<html:hidden property="esignatoryName" />
							</td>
							<td class="tableTextBold" width="20%" align="right" colspan=2 rowspan=2 valign="middle">&nbsp;
									<img  id="eSignImage" style="display:none;"  align="left"/>
							</td>
						
							
						</tr>
						<tr>
							
							<td class="tableTextBold" width="20%" align="right" nowrap>
								&nbsp;
							</td>
							<td width="20%" class="tableTextBold">					
													(Use Browse button to update the signatory)	
							</td>
							
						
							
						</tr>

						<tr>
							<td colspan="4" align="center">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<html:submit value="Save" styleClass="butt" tabindex="12" />
								<html:button value="Clear" styleClass="butt" tabindex="13" property="clear" onclick="javascript:document.forms[0].reset()"/>
								<html:button  property="Cancel" styleClass="butt"  tabindex="14" onclick="javascript:window.location.href='userSearch.do?method=search'" onblur="document.forms[0].userName.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								&nbsp;
							</td>
						</tr>
					</tbody>
				</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
