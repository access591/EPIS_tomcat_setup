

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
	String forms[] = {"SANSACTION ORDER-REGION WISE"};

					%>
<%
	String monthID="",yearDescr="",region="",monthNM="";
	int regionSize=0;
	Iterator monthIterator=null;
  	Iterator yearIterator=null;
  	Iterator regionIterator=null;
  	HashMap hashmap=new HashMap();
  	ArrayList yearList=new ArrayList();
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap=(HashMap)request.getAttribute("regionHashmap");
  	Set keys = hashmap.keySet();
	regionIterator = keys.iterator();
	regionSize=hashmap.size();
  	
  	}
  	if(request.getAttribute("monthIterator")!=null){
  	monthIterator=(Iterator)request.getAttribute("monthIterator");
  	}
  	if(request.getAttribute("yearList")!=null){
  	yearList=(ArrayList)request.getAttribute("yearList");
  	}
%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>
<html>
	<HEAD>
		<LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">

		<script type="text/javascript">



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
 function LoadWindow(params){
    //alert("params"+params);
  
	var newParams ="<%=basePath%>jsp/common/Report.jsp?"+params
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar= yes,statusbar=1,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
}
	
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>advanceparamreport.do?method=loadAdvanceInputForm&flag=MISReport";
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	
	function validateForm(user) {
		var reportType="",url="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		
      //  if(user!='SEHGAL' && user!='navayuga'&&user!='BALWANT'){
      //    alert("' "+user+" ' User Not allowed to Access the Reports");
      //    return false;
       //  }
        
        /* if(document.forms[0].select_year.selectedIndex<1){
   		  alert("Please Select Year");
   		  document.forms[0].select_year.focus();
   		  return false;
   		  }*/ 
        
        /* var index=document.forms[0].select_region.selectedIndex;
      	 if(document.forms[0].select_region.selectedIndex<1) {
   		  alert("Please Select Region");
   		  document.forms[0].select_region.focus();
   		  return false;
   		  }*/ 
    

		    var airportcode="",advancetype="",purposetype="",trust="",airportCd="";
			//wind=window.open("<%=basePath%>view/advances/Monthly_PAYMENTS_WestRegion.xls","Report","toolbar=yes,scrollbars=yes,resizable=yes,top=0,left=0,menuBar=yes");
			//wind.window.focus();
			if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
			}else{
			regionID=document.forms[0].select_region.value;			
			}
			
			
			if(document.forms[0].select_airport.selectedIndex>0){
			airportCd=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].text;
			}else{
			airportCd=document.forms[0].select_airport.value;			
			}
			
			monthID=document.forms[0].select_month.selectedIndex;			
			reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
			yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].text;
			//advancetype=document.forms[0].select_advance_dtl.options[document.forms[0].select_advance_dtl.selectedIndex].value;
			trust=document.forms[0].trust.options[document.forms[0].trust.selectedIndex].value;
					
			/*if(advancetype=="PFW")
		    purposetype=document.forms[0].select_dtl_pfwpurpose.options[document.forms[0].select_dtl_pfwpurpose.selectedIndex].value;
		    else
		    purposetype=document.forms[0].select_adv_purpose_dtl.options[document.forms[0].select_adv_purpose_dtl.selectedIndex].value;
		  */
		  
		  
          
		 purposetype=document.forms[0].select_dtl_pfwpurpose.options[document.forms[0].select_dtl_pfwpurpose.selectedIndex].value;
		var subPurposeType='';
          
		if(purposetype=='HBA'){
		 	if(document.getElementById("select_HBA_subpurpose").value!='NO-SELECT'){
		 	 subPurposeType=document.getElementById("select_HBA_subpurpose").value;
			}
		}else if(purposetype=='Marriage'){
		if(document.getElementById("select_Marriage_subpurpose").value!='NO-SELECT'){
		 	 subPurposeType=document.getElementById("select_Marriage_subpurpose").value;
			}
	
		}else if(purposetype=='HE'){	
		if(document.getElementById("select_HE_subpurpose").value!='NO-SELECT'){
		 	 subPurposeType=document.getElementById("select_HE_subpurpose").value;
			}
		
		}
				
		
		  
			url="<%=basePath%>advanceparamreport.do?method=MISReport&frm_region="+regionID+"&frm_station="+airportCd+"&frm_year="+yearID+"&frm_month="+monthID+"&frm_subpurposetype="+subPurposeType+"&frm_trust="+trust+"&frm_reportType="+reportType+"&frm_purposeType="+purposetype;
		
				
		 	
				if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   	 			}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   	 						//alert("url "+url);	
   	 				 			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
   	 			}				
			
			return false;		
		
	}	
	
	
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
	 
	function getAirports(){	
		monthID=document.forms[0].select_month.value;
		regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		yearID=document.forms[0].select_year.value;
	
	createXMLHttpRequest();	
	var url ="<%=basePath%>getdetails.do?method=getAirports&region="+regionID;
	
	xmlHttp.open("post", url, true);
	xmlHttp.onreadystatechange = getAirportsList;
	
	xmlHttp.send(null);
    }
 function getNodeValue(obj,tag)
   {
	return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
   }
function getAirportsList()
{
	
	if(xmlHttp.readyState ==3 ||  xmlHttp.readyState ==2 ||  xmlHttp.readyState ==1){
		 process.style.display="block";
	}
	if(xmlHttp.readyState ==4)
	{
		
		if(xmlHttp.status == 200)
		{ 
		var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
		 process.style.display="none";
		  if(stype.length==0){
		 //	alert("in if");
		 	var obj1 = document.getElementById("select_airport");
		 
		 	
		  	obj1.options.length=0; 
		  	obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
		 
		  
		  }else{
		   	var obj1 = document.getElementById("select_airport");
		  //    alert(stype.length);	
		  	obj1.options.length = 0;
		  	
		  	for(i=0;i<stype.length;i++){
		  		if(i==0)
					{
				//	alert("inside if")
					obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
					}
		          //	alert("in else");
			obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
			}
		  }
		}
	}
}

function frmload(){
 process.style.display="none";
}
	
function purposes_nav(){

var purposeType=document.forms[0].select_dtl_pfwpurpose.options[document.forms[0].select_dtl_pfwpurpose.selectedIndex].value;


if(purposeType=='HBA'){
 document.getElementById("HBApurposes").style.display="block";
 document.getElementById("Marriagepurposes").style.display="none";
 document.getElementById("HEpurposes").style.display="none";
}else if(purposeType=='Marriage'){
document.getElementById("Marriagepurposes").style.display="block";
document.getElementById("HBApurposes").style.display="none";
document.getElementById("HEpurposes").style.display="none";
}else if(purposeType=='HE'){
document.getElementById("HEpurposes").style.display="block";
document.getElementById("HBApurposes").style.display="none";
 document.getElementById("Marriagepurposes").style.display="none";

}else if(purposeType=='LIC'){

}


}	
function loadDetails(){
	var reionsize;
	reionsize='<%=regionSize%>';
	//alert(reionsize);

	if(reionsize==1){ 
	getAirports();
	} 
}
</script>
	</HEAD>
	<body class="BodyBackground" onload="javascript:frmload();loadDetails();">
		
		<form action="post">
			<%=ScreenUtilities.screenHeader("MIS Report Params")%>
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">

				<tr>
					<td width="20%">
						&nbsp;
					</td>
					<td class="tableTextBold" width="25%">
						Year
					</td>
					<td width="2%" align="center">
						:
					</td>
					<td>
						<Select name='select_year' class="TextField">
							<option value='No-SELECT'>
								Select One
							</option>
							<%for (int j = 0; j < yearList.size(); j++) {%>
							<option value='<%=yearList.get(j)%>'>
								<%=yearList.get(j)%>
							</option>
							<%}%>
						</SELECT>
					</td>

				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Month
					</td>
					<td align="center">
						:
					</td>
					<td>
						<select name="select_month" class="TextField">
							<Option Value='' Selected>
								Select One
							</Option>
							<%while (monthIterator.hasNext()) {
									Map.Entry mapEntry = (Map.Entry) monthIterator.next();
									monthID = mapEntry.getKey().toString();
									monthNM = mapEntry.getValue().toString();%>

							<option value="<%=monthID%>">
								<%=monthNM%>
							</option>
							<%}%>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Region
					</td>
					<td align="center">
						:
					</td>
					<td>

						<SELECT NAME="select_region" class="TextField" onChange="javascript:getAirports()">
						<%if(regionSize>1){%>
							<option value="NO-SELECT">
								[Select One]
							</option>
							<%while (regionIterator.hasNext()) {
								region = hashmap.get(regionIterator.next()).toString();%>
							<option value="<%=region%>">
								<%=region%>
							</option>
							<%}%>
						<%}%>
						<%if(regionSize==1){%>							 
							<%while (regionIterator.hasNext()) {
																			region = hashmap.get(regionIterator.next()).toString();%>
							<option value="<%=region%>" selected="selected">
								<%=region%>
							</option>
							<%}%>
						<%}%>
						</SELECT>
					</td>
				</tr>
				
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Station
					</td>
					<td align="center">
						:
					</td>
					<td>
						<SELECT NAME="select_airport" class="TextField">
							<option value='NO-SELECT' Selected>
								[Select One]
							</option>
						</SELECT>
					</td>
				</tr>
				<!-- 
																<tr>
																	<td class="tableTextBold" align="right">
																		Aiport Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<SELECT NAME="select_airport" style="width:120px">
																			<option value='NO-SELECT' Selected>
																				[Select One]
																			</option>
																		</SELECT>
																	</td>
																</tr>
															<tr>
																	<td class="tableTextBold" align="right">
																		Employee Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<input type="text" value="" name="txt_empname">
																		<input type="hidden" name="frm_pensionno">
																		<img src="<%=basePath%>/PensionView/images/search1.gif" onclick="popupWindow('<%=basePath%>PensionView/PensionContributionMappedListWithoutTransfer.jsp','AAI');" alt="Click The Icon to Select EmployeeName" />
																		<input type="checkbox" name="chk_empflag">
																	</td>
																</tr>-->
				
				<tr>
					<td>
						&nbsp;
					</td>
					<td class=tableTextBold align="right" nowrap>
						Purpose
					</td>
					<td align="center">
						:
					</td>
					<td id="pfwpurp">
						<table cellpadding="1" cellspacing="1" border="0">
							<tr>
								<td>
									<select name='select_dtl_pfwpurpose' onchange="purposes_nav();" class="TextField" tabindex="5">
										<option value="NO-SELECT">
											Select one
										</option>
										<option value='HBA'>
											HBA (Housing Built Area)
										</option>
										<option value='Marriage'>
											Marriage
										</option>
										<option value='HE'>
											Higher Education
										</option>
										<option value='LIC'>
											LIC (Life Insurance Policy)
										</option>
									</select>
								</td>
							</tr>
						</table>
					</td>
					<td id="divadvance" style="display:none;">
						<table cellpadding="1" cellspacing="1" border="0">
							<tr>
								<td>
									<select name='select_adv_purpose_dtl' onchange="purposes_nav();" class="TextField" tabindex="6">
										<option value="NO-SELECT">
											Select one
										</option>
										<option value='cost'>
											Cost Of Passage
										</option>
										<option value='obligatory'>
											Obligatory Expanses
										</option>
										<option value='obMarriage'>
											Marriage Expanses
										</option>
										<option value='illness'>
											Illness Expanses
										</option>
										<option value='education'>
											Higher Education
										</option>
										<option value='defence'>
											Defense Of Court Case
										</option>
										<option value='others'>
											Others
										</option>
									</select>
								</td>
							</tr>
						</table>
					</td>

				</tr>
				
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Sub Purpose 
					</td>
					<td align="center">
						:
					</td>
					<td id="HBApurposes" style="display:none;">
						
						<select  name="select_HBA_subpurpose" style="width: 240px" class="TextField">
							<option value="NO-SELECT">Select One</option>
							<option value='purchasesite'>Purchasing of dwelling site</option>
							<option value='purchasehouse'>Purchasing of dwelling house</option>
							<option value='constructionhouse'>Construction of dwelling house</option>
							<option value='acquireflat'>Acquiring a Flat</option>
							<option value='renovationhouse'>Renovation/Alteration/Addition of dwelling house</option>
							<option value='repaymenthba'>Repayment of HBA Loan taken from</option>
							<option value='hbaothers'>Any Others</option>
						</select>
													
					</td>
					<td id="Marriagepurposes" style="display:none;">
						
						<select  name="select_Marriage_subpurpose" class="TextField">
							<option value='NO-SELECT'>Select One</option>
							<option value='self'>Self</option>
							<option value='Son'>Son</option>
							<option value='Daughter'>Daughter</option>
							<option value='Dependent Brother'>Dependent Brother</option>
							<option value='Dependent Sister'>Dependent Sister</option>
						</select>
													
					</td>
					
					<td id="HEpurposes" style="display:none;">
						
						<select  name="select_HE_subpurpose" class="TextField">
						<option value="NO-SELECT"> Select One </option>
						<option value='Son'> Son </option>
						<option value='Daughter'> Daughter </option>
						</select>
													
					</td>
					

				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Trust
					</td>
					<td align="center">
						:
					</td>
					<td>
						<select name="trust" class="TextField">
							<option value="NO-SELECT">
								Select One
							</option>
							<option value="NAA">
								NAA
							</option>
							<option value="AAI">
								AAI
							</option>
							<option value="IAAI">
								IAAI
							</option>

						</select>
					</td>

				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class=tableTextBold align="right" nowrap>
						Report Type
					</td>
					<td align="center">
						:
					</td>
					<td>
						<SELECT NAME="select_reportType" class="TextField">

							<option value="html">
								Html
							</option>
							<option value="ExcelSheet">
								Excel Sheet
							</option>

						</SELECT>

					</td>
				</tr>
				<tr>

					<td colspan="4" align="center">
						<input type="button" class="butt" name="Submit" value="Submit" onclick="javascript:validateForm('<%=session.getAttribute("userid")%>')">
						<input type="button" class="butt" name="Reset" value="Reset" onclick="javascript:resetReportParams()">
					</td>

				</tr>

			</table>
			<%=ScreenUtilities.screenFooter()%>
		</form>
		
		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center">
			<img src="<%=basePath%>images/Indicator.gif" border="no" align="middle" />
			<SPAN class="tableTextBold">Processing.......</SPAN>
		</div>

	</body>
</html>
