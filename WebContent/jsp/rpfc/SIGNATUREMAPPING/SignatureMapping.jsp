 <!--
/*
  * File       : SignatureMapping.jsp
  * Date       : 17/01/2011
  * Author     : Radha P
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
--> 
<%@ page language="java"  import="java.util.*,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
	
%>
 
<html>
	<HEAD>
		<LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime.js"></SCRIPT>

		<script type="text/javascript">

// for Module Names
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
   {
	return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
   }	 
   
   
	function loadModules(){	
		 
		var type=document.forms[0].type.options[document.forms[0].type.selectedIndex].text;
		 var userId=document.forms[0].userID.value;		 
	if(userId==''){
	alert('Please First Select the User');
	document.forms[0].userID.focus();	
	} 
	if(type=='Module'){
	createXMLHttpRequest();	
	var url ="<%=basePath%>psearch.do?method=loadModules";
	
	xmlHttp.open("post", url, true);
	xmlHttp.onreadystatechange = loadModulesList;
	
	xmlHttp.send(null);
	}
	else{
	alert('Implementation not yet Done');
	document.getElementById("type").focus();	 
	}
    }
 
 // for Screen Names
function loadModulesList()
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
		 	alert("in if");
		 	var obj1 = document.getElementById("moduleCode");
		 
		 	
		  	obj1.options.length=0; 
		  	obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
		 
		  
		  }else{
		   	var obj1 = document.getElementById("moduleCode");
		  //    alert(stype.length);	
		  	obj1.options.length = 0;
		  	
		  	for(i=0;i<stype.length;i++){
		  		if(i==0)
					{
				//	alert("inside if")
					obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
					}
		          //	alert("in else");
			obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'moduleName'),getNodeValue(stype[i],'moduleCode'));
			 
			}
		  }
		}
	}
} 


 
function loadScreenNames(){	
		 
	var moduleCode=document.forms[0].moduleCode.value;
	 //alert(moduleCode);
	createXMLHttpRequest();	
	var url ="<%=basePath%>psearch.do?method=loadScreenNames&modulecode="+moduleCode;	 
	xmlHttp.open("post", url, true);
	xmlHttp.onreadystatechange = loadScreenNamesList;	
	xmlHttp.send(null);
	 
	 
    }

function loadScreenNamesList()
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
		 	//alert("in if");
		 	var obj1 = document.getElementById("screenCode");		 	
		  	obj1.options.length=0; 
		  	obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
		     }else{
		   	var obj1 = document.getElementById("screenCode");
		  //    alert(stype.length);	
		  	obj1.options.length = 0;
		  	
		  	for(i=0;i<stype.length;i++){
		  		if(i==0)
					{
				//	alert("inside if")
					obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
					}
		          //	alert("in else");
			obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'screenCode'),getNodeValue(stype[i],'screenName'));
			  
			}
		  }
		}
	}
} 

//ti check the duplicate access rights to user
function chkDuplicatePermissions(){
var  userID,moduleCode,screenCode;
	userID=document.forms[0].userID.value;
	moduleCode=document.forms[0].moduleCode.value;
	screenCode=document.forms[0].screenCode.value;
	createXMLHttpRequest();	
	var url ="<%=basePath%>psearch.do?method=chkDuplicateAccess&userid="+userID+"&modulecode="+moduleCode+"&screencode="+screenCode;	 
	//alert(url);
	xmlHttp.open("post", url, true);
	xmlHttp.onreadystatechange = chkDuplicates;	
	xmlHttp.send(null);
}

function chkDuplicates()
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
		 
		   	 if(getNodeValue(stype[0],'accessPermission')=='DuplicatePermission'){
		  	  alert('Already Signature is given to this Screen');
		  	  document.forms[0].userID.focus();
		  	  }
		  	 
		  }
		}
	}

function loadUserSignPermissions(){
	var userID=document.forms[0].userID.value;
	var url="<%=basePath%>signaturemapping.do?method=editSignatureMapping&userid="+userID;   		 
   		//alert(url);
   		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();

}
function validateForm(){
document.forms[0].screenPermissionInfo.options.length = 0;
	var lengthOfEntries=checkEntry();	
				 
	if(document.forms[0].type.value=='NO-SELECT'){
		alert('Please Select any one Type');
		document.forms[0].type.focus();
		return false;
	}
	if(document.forms[0].type.value=='Profiles'){
	alert('Implementation not yet Done');
	document.getElementById("type").focus();	
	return false;
	}
	if((document.forms[0].type.value=='Module')&&(lengthOfEntries==0)){
	alert('You must select   Screen  for giving signature Permission');
				document.forms[0].moduleCode.focus();
				return false;
	}
	if((lengthOfEntries==0)&&(document.forms[0].signature.value!='')){
	 			alert('You must select any one Screen in for signature Permission');
				document.forms[0].screenCode.focus();
				return false;
		}else if((lengthOfEntries>0)&&(document.forms[0].signature.value=='')&&(document.forms[0].ScreenType.value=='NEW')){		
				alert('Please Select the Signature for the selected Screen');
				document.forms[0].signature.focus();
				return false;
		}else{
	var userName=document.forms[0].userID.options[document.forms[0].userID.selectedIndex].text;
	var activity=document.forms[0].activity.checked;	
	var  screentype=document.forms[0].ScreenType.value;
	 
	var url="<%=basePath%>signaturemapping.do?method=saveSignatureMapping&username="+userName+"&activity="+activity+"&flag=save&screentype="+screentype;   		 
  //	alert(url);
   		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	} 
}

function reset(){
 		var url="<%=basePath%>signaturemapping.do?method=loadSignatureMapping";   		 
   		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
}
function frmload(){
 process.style.display="none";
}
//Inner Html

var len =0;

			function checkEntry(){ 
				var temp = '';
				len = detailsRec.length;
				
                if(len>0){ 
				for(var i=0;i<len;i++){
					temp = '';	
					for(var j=0;j<5;j++){
						temp += detailsRec[i][j]+'|';
						//alert(temp);
					}
										
					document.forms[0].screenPermissionInfo.options[document.forms[0].screenPermissionInfo.options.length]=new Option('x',temp);
					document.forms[0].screenPermissionInfo.options[document.forms[0].screenPermissionInfo.options.length-1].selected=true;
					 
				}
			}
		return len;
         }
var det = new Array();
var enteredVal='',detListVal='',enterStr='',detStr='';
     				  		
function saveDetails(){ 
				 
	var frmdt=document.getElementById("fromDate").value;
	var frmdtstr=frmdt.split("/");
	
	if(document.getElementById("fromDate").value==''){   	 
		  alert("Please enter From Financial Year");		   
	  	 	document.getElementById("fromdate").focus();
			return false;		 
	}  
	if(document.getElementById("fromDate").value!=''){ 		 
		if (!validate_date(frmdtstr[0],frmdtstr[1],frmdtstr[2]))
		{  	 
		  alert("Please enter withdrawal date in the format 01/01/2010");		   
	  	 	document.getElementById("fromdate").focus();
			return false;
		}
	} 

	var todt=document.getElementById("todate").value;
	var todtstr=todt.split("/");
	
	if(document.getElementById("todate").value==''){   	 
		  alert("Please enter To Financial Year");		   
	  	 	document.getElementById("todate").focus();
			return false;		 
	}  
	if(document.getElementById("todate").value!=''){ 		 
		if (!validate_date(todtstr[0],todtstr[1],todtstr[2]))
		{  	 
		  alert("Please enter withdrawal date in the format 01/01/2010");		   
	  	 	document.getElementById("todate").focus();
			return false;
		}
	} 
		var dateValidate=compareDates(document.forms[0].fromDate.value,document.forms[0].toDate.value);
		if(dateValidate=='larger'){
		alert('To Financial must be greater than From Financial Year');
		document.forms[0].toDate.focus();
		return false;
		}
		
				if(editedNo == -1)
					editedNo = detailsRec.length;
					 //................
					  var scrncnt='',scrncodes='';
					  var screencode='',screenname='',str='';
      		 	
       				var finalJSScreenCodesList=new Array();
       				var finalJSScreenNamesList=new Array();       			 
       				finalJSScreenCodesList=calScreenCodesCnt(); 
       				
       				var length=detailsRec.length;
       			 
       					
       				for(var k=0;k<finalJSScreenCodesList.length;k++)
      				 	{      
     				  		scrncnt=finalJSScreenCodesList[k];
      					 	str=scrncnt.split("/");
      					 	
      					 	screenname=str[0];
      					 	screencode=str[1];    				  		   				  	
     				  		 
     				  		if(length>0){
     				  		//alert('---length--'+length);
     				  		 for(var i=0;i<length;i++){
     				  		//  alert('---'+finalJSScreenCodesList[k]);
     				  		 if(detailsRec[i][3]==screencode){
     				  		 
     				  		// alert('--scrncd--'+detailsRec[i][3]);
     				  		 
								var frmdt=compareDates(document.forms[0].fromDate.value,detailsRec[i][2]);
								var frmdt1=compareDates(document.forms[0].fromDate.value,detailsRec[i][1]);
								var frmdt2=compareDates(document.forms[0].toDate.value,detailsRec[i][1]);
								  
								//alert('--frmdt--'+frmdt);
								if(frmdt=='smaller'){
								//alert('-inner Cond---- frmDate--'+document.forms[0].fromDate.value+'--already todate--'+detailsRec[i][1]+'--'+frmdt1);
								 if((frmdt1=='larger') ||(frmdt2=='larger')||(frmdt1=='equal') ||(frmdt2=='equal')) 
								 {
								 alert('Signature is Assigned Upto this period');
								 document.forms[0].toDate.focus();
     				 			 return false;
								 }
								}else if(frmdt=='equal'){
								 
								 alert('Signature is Assigned Upto this period');
     				 			 document.forms[0].fromDate.focus();
     				 			 return false;
								}
								//alert(document.forms[0].fromDate.value+'-------'+document.forms[0].toDate.value);
								var todt=compareDates(document.forms[0].fromDate.value,document.forms[0].toDate.value);
								//alert(todt);
								if(todt=='larger'){
								alert('To Financial must be greater than From Financial Year');
     				 			 document.forms[0].toDate.focus();
     				 			 return false;
										}
								 	  
     				  			}
     				  			 	
     				  		}
     				  		detailsRec[editedNo]=[screenname,document.forms[0].fromDate.value,document.forms[0].toDate.value,screencode,document.forms[0].moduleCode.value];
     				 	 		editedNo=editedNo+1;
     				  		 
     				  	}else{
							 detailsRec[editedNo]=[screenname,document.forms[0].fromDate.value,document.forms[0].toDate.value,screencode,document.forms[0].moduleCode.value];
     				 		 editedNo=editedNo+1;
     				 			}		 		
     				 		  //scrncodes+=scrncnt;
     				 		  
      		 				}
      		 				  
      		 				editedNo = -1 ;
							showValues(-1); 
							detailsClear();	
      		 		//alert(scrncodes);
					 //..................
				 
			}	
 		var detailsRec = new Array();
			var editedNo = -1;
			function detailsClear(){
				document.forms[0].screenCode.value = "";
				document.forms[0].fromDate.value = "";
				document.forms[0].toDate.value = "";
				 
			}
	function showValues(index){
				var str='<TABLE border=1 cellpadding=0 cellspacing=0  bordercolor=snow width="100%">';
					str +='<TR><TD align=center   nowrap style=width:40%>Screen Name</TD> ';
					str +='<TD align=center   nowrap style=width:15%>From Financial Year</TD>';
					str +='<TD align=center   nowrap style=width:15%>To Financial Year</TD></TR>';
					  
				for(var i=0;i<detailsRec.length;i++){  
					str+='<TR>';
					if(i==index)	{
					//alert('in if'); 
						document.forms[0].screenCode.value = detailsRec[i][0];
						document.forms[0].fromDate.value = detailsRec[i][1];
						document.forms[0].toDate.value = detailsRec[i][2];						 
						
						editedNo = index;
					} else { 
					//alert('in else'); 
						str +='<TR>';
						for(var j=0;j<3;j++){	
							if(detailsRec[i][j].length<10){
								str+='<TD align=center   nowrap style=width:200%>'+detailsRec[i][j]+'</TD>';
							}else{
								str+="<TD align=center nowrap style=width:300%;heigth:500%>"+detailsRec[i][j];
								str+="<a class=data href=# onMouseOver = \"overlib('"+detailsRec[i][j]+"\')\" onMouseOut='nd()'></a></TD>";				
							}						
						}
						str +='<TD><a href=# onclick=del('+i+')><img src="/images/cancelIcon.gif" alt=delete border=0 width=20 height=20></a></TD></TR>';								
					}	
				}
			 	str+='</TABLE>';      
				document.all['addDetails'].innerHTML = str;	
				if(index!=-1){ 
				
				
				
				}
			}
			
			function del(index) {
				var temp=new Array();
				for(var i=0;i<detailsRec.length;i++)
				{
					if(i!=index)
						temp[temp.length]=detailsRec[i];
	       
				}
				 
				detailsRec=temp;
				showValues(-1);
				document.forms[0].edit.value='N';
				return false;
			}
			
//--------------
function calScreenCodesCnt(value)
{
var i, cnt='',screenNamesCnt='',size=0; 
   finalJSList=new Array();
   size=document.forms[0].screenCode.options.length;
   //alert('----size---'+ size);
   for(i=0;i<document.forms[0].screenCode.options.length; i++){    
     if(document.forms[0].screenCode.options[i].selected==true){
   //  alert('---selected one pos '+i);    
  		cnt=document.forms[0].screenCode[i].value;
  	screenNamesCnt=document.forms[0].screenCode.options[i].text;     
     if(cnt!='')
     finalJSList.push(screenNamesCnt+"/"+cnt);
     } 
     }
     return finalJSList;
     
     }
     screenCodesArray = new Array();
     var scrnCodeStr='';
      function load(){ 
				 
				 if(document.forms[0].ScreenType.value=="EDIT"){
				document.getElementById("sign").style.display="none";
				 	whileLoading();
					for(var i=0;i< document.forms[0].screenPermissionInfo.options.length;i++){
					// scrnCodeStr=document.forms[0].screenPermissionInfo.options[i].value.split("";
						detailsRec[i] = document.forms[0].screenPermissionInfo.options[i].value.split("|");
						//alert(detailsRec[i][3]);
						screenCodesArray=detailsRec[i];
						document.forms[0].activity.checked=true;
					}
					showValues(-1);
				 }else{
				 document.getElementById("sign").style.display="block";
				 }
			}
			
			function whileLoading(){   
				if(document.forms[0].signatureName.value != ""){
				//alert("uploads/dbf/"+document.forms[0].signatureName.value);
				 
				 
				document.getElementById("eSignImage").src = "uploads/dbf/"+document.forms[0].signatureName.value;
				document.getElementById("eSignImage").style.display="block";
				}
				document.forms[0].fromDate.focus();
			}   
</script>
	</HEAD>
	<body class="BodyBackground" onload=frmload(),load();>
	<%
	String userID="",userName="";
	Iterator userListIterator=null;
	if(request.getAttribute("userListIterator")!=null){
  	userListIterator=(Iterator)request.getAttribute("userListIterator");
  	}
	%>	 
		
		<html:form action="/signaturemapping.do?method=saveSignatureMapping" enctype="multipart/form-data">
			<%=ScreenUtilities.screenHeader("Signature Mapping[New]")%>
			<html:select property="screenPermissionInfo" multiple="multiple" style="display:none;">
				<logic:equal value="EDIT"  name="ScreenType" scope="request">
				<html:options name="Bean" property="code" labelProperty="code" labelName="code" collection="ScreenInfo" />
			</logic:equal>		
			</html:select>
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">

				<tr>
					  
					<td>
						<table align="center">
						
						<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						User Name
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:select property="userID"  styleClass="TextField" name="signMapping"  onchange="loadUserSignPermissions();">	
						<html:option value="">
								Select One
							</html:option>																		 
								<%while (userListIterator.hasNext()) {
									Map.Entry mapEntry = (Map.Entry) userListIterator.next();
									userID = mapEntry.getKey().toString();
									userName = mapEntry.getValue().toString();%>

							<html:option value="<%=userID%>">
								<%=userName%>
							</html:option>
							<%}%>
						</html:select>
																	 
					</td>
				</tr>
							<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold"> 
						Type
					</td>
					<td  align="center">
						:
					</td>
					<td>
						<input type="hidden" name="edit"/> 
						<input type=hidden name="ScreenType" value="<bean:write name="ScreenType" scope="request"/>"/>
						 <html:select property="type" styleClass="TextField" name="signMapping" onchange="javascript:loadModules();">
							<html:option value="NO-SELECT">[Select One]</html:option>							 
							<html:option value="Module">Module</html:option>
							<html:option value="Profiles">Profiles</html:option>
							 
					</html:select>
					</td>

				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Modules
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:select property="moduleCode" styleClass="TextField" onchange="javascript:loadScreenNames();">
							<html:option value='NO-SELECT' >
								[Select One]
							</html:option>
						</html:select> 
					</td>
				</tr>
				 
				</table>
				</td>
			</tr> 
			 
			 
			 
				 <tr>
				 <td>
				 	<table align="center">
				 	 	<tr>
					 
					<td class="tableTextBold" align="right">
						Screen Name :
					</td>
					 
					<td> 
						<html:select property="screenCode" styleClass="TextField" multiple="true">
							<html:option value='NO-SELECT' >
								[Select One]
							</html:option>
						</html:select>
					</td>
				 
					<td class="tableTextBold" align="right">
						From Date :
					</td>
					 
					<td> 
						  <html:text property="fromDate"  styleClass="TextField"  size="11"/><font color="red" size="2">(Ex:01/01/2000)</font>
					</td>
				 
					<td class="tableTextBold" align="right">
						To Date :
					</td>
					  
					<td>
						 <html:text property="toDate"  styleClass="TextField"  size="11"/><font color="red" size="2">(Ex:01/01/2000)</font>
					</td>
					   
					<td class="tableTextBold" align="right" align="right" >
						<img style='cursor:hand' src="images/addIcon.gif" onclick='saveDetails()'  />
					</td>
							 
				</tr>	
				  
			</table>
			</td>
			</tr> 
			
			<tr>
			 	<td>
			 		<table align="center">
			 		<tr>
					<td  id='addDetails' name='addDetails' width="100%"></td>
					</tr>
			 		</table>
			 		
			 	</td>
			 </tr>
			
				<tr>
					<td>
				<table align="center">
					<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Display Manager Name 
					</td>
					<td align="center">
						:
					</td>
					<td>
						  <html:text property="dispMgrName" name="signMapping" styleClass="TextField" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Station Name 
					</td>
					<td align="center">
						:
					</td>
					<td>
						 <html:text  property="station" name="signMapping"  styleClass="TextField" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Designation & Remarks
					</td>
					<td align="center">
						:
					</td>
					<td>
						  <html:text property="desgNRemarks" name="signMapping" styleClass="TextField"  />
					</td>
				</tr>
				
				
				<tr>
				<tr id="sign" style="display:none;">
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Signature
					</td>
					<td align="center">
						:
					</td>
					<td >
						<html:hidden property="signatureName" name="signMapping" styleClass="TextField" />
						 <html:file property="signature" name="signMapping"  style="width:190px"></html:file>	 
					</td>
					
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp; 
					</td>
					<td align="center">
						&nbsp;
					</td>
					<td class="tableTextBold"  align="right">&nbsp;
						<img  id="eSignImage" style="display:none;"  align="left"/>
					</td>
					
				</tr>
				
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Activity
					</td>
					<td align="center">
						:
					</td>
					<td>
						 <input type="checkbox" name="activity"  />
					</td>
				</tr>
				 
			</table>
			</td>
				 
				<tr>
					
					<td colspan="4" align="center">
						<input type="button" class="butt" name="Submit" value="Submit" onclick="javascript:validateForm()">
						<input type="button" class="butt" name="Reset" value="Reset" onclick="javascript:reset()">
					</td>

				</tr>

			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
		
		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center">
			<img src="<%=basePath%>images/Indicator.gif" border="no" align="middle" />
			<SPAN class="tableTextBold">Processing.......</SPAN>
		</div>

	</body>

 