<!--
/*
  * File       : NoteSheetArrearEdit.jsp
  * Date       : 09/06/2010
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();
String basePathWoView = basePathBuf.toString();
%>

<%
    String reg="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
	hashmap=common.getRegion();

	Set keys = hashmap.keySet();
	System.out.println(".............keys................"+keys);

	Iterator it = keys.iterator();
	

  %>
											

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

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
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
			<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>

	
		<script type="text/javascript">		
		var detailArray = new Array();
		var srno,nomineename,nomineeaddress,nomineeDOB,nomineerelation,gardianname,gardianaddress,totalshare,nomineeflag,flag;
		var i;
		var emplshare=0,emplrshare=0,pensioncontribution=0;
		var remarksFlag='N'; 
		var ex=/^[0-9.-]+$/;
		function hide(focusFlag) {
			
		   	if(focusFlag!='true'){
		   		document.forms[0].pfid.focus();
		   	}else{
		   	    document.forms[0].seperationreason.focus();
		   	}		  
		}					
		function popupWindow(windowname){		
			var pensionNo;
			pensionNo=document.forms[0].pfid.value;
		
			if (! window.focus)return true;
			var href;
	   		href="loadAdvance.do?method=loadLookupPFID&frm_pensionno="+pensionNo;
			progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
		}	
	
	function frmPrsnlReset(frmName){
			hide('false');
			
			//alert(frmName);
			url="<%=basePathWoView%>/loadNoteSheet.do?method=editNoteSheetArrear&pensionno="+document.forms[0].pensionNo.value+"&sanctionno="+document.forms[0].nssanctionno.value+"&frm_name="+frmName;
		 
	   		document.forms[0].action=url;
		    document.forms[0].method="post";
			document.forms[0].submit();
	} 		
	function submitData(st,frmName,continueFlag){
	
	if(!withDrawalDet()){
			return false;
		}
	
		 	var ex=/^[0-9.-]+$/;
			var bool=false,advanceDtl="",pfwPurpose="",advPurpose="",pensionNo="",totalInstall="",verifiedby="";
			var ndob=document.getElementsByName("nomineeDOB");
			for(var i=0;i<(ndob.length);i++){
				if(ndob[i].value!='')
				{
					if (!convert_date(ndob[i]))
				    {
						ndob[i].focus();
						return false;
					}
				}			
			}		
			if(document.forms[0].pfid.value==''){
				alert('PF ID Is Required');
				document.forms[0].pfid.focus();
				return false;
			}
								
			//if (!convert_date(document.forms[0].dateOfBirth))
			///{
		//		document.forms[0].dateOfBirth.focus();
		//		return false;
		//	}			
			if(document.forms[0].seperationreason.value!=''){
			    if(document.forms[0].seperationdate.value==''){
			        alert('Seperation Date Is Required');
					document.forms[0].seperationdate.focus();
					return false;
			    }
			}
			if(document.forms[0].seperationdate.value!=''){
			    if(document.forms[0].seperationreason.value==''){
			        alert('Seperation Reason Is Required');
					document.forms[0].seperationreason.focus();
					return false;
			    }
			}	
				
			if (!convert_date(document.forms[0].seperationdate))
			{
				document.forms[0].seperationdate.focus();
				return false;
			}
			if(document.forms[0].amtadmtdate.value==''){
				alert('Amount Admitted for Payment by CPF(Hqrs)with interest up to Is Required ');
				document.forms[0].amtadmtdate.focus();
				return false;
			}
			if (!convert_date(document.forms[0].amtadmtdate))
			{
				document.forms[0].amtadmtdate.focus();
				return false;
			}
			
			
			
			if(document.forms[0].emplshare.value==''){
				alert('Employee Share (Subscription)(A) Is Required');
				document.forms[0].emplshare.focus();
				return false;
			}
			if (!ex.test(document.forms[0].emplshare.value) && document.forms[0].emplshare.value!="")
		    {
				alert("Employee Share (Subscription)(A) shoud be Numeric");
				document.forms[0].emplshare.select();
				return false;
		    }
		   
			if(document.forms[0].emplrshare.value==''){
				alert('Employer Share ( Contribution)(B) Is Required');
				document.forms[0].emplrshare.focus();
				return false;
			}
			if (!ex.test(document.forms[0].emplrshare.value) && document.forms[0].emplrshare.value!="")
		    {
				alert("Employer Share (Contribution)(B) shoud be Numeric");
				document.forms[0].emplrshare.select();
				return false;
		    }
			if(document.forms[0].pensioncontribution.value==''){
				alert('Less Difference of Pension Contribution (C) Is Required');
				document.forms[0].pensioncontribution.focus();
				return false;
			}	
			
			
			if (!ex.test(document.forms[0].pensioncontribution.value) && document.forms[0].pensioncontribution.value!="")
		    {
				alert("Less Difference of Pension Contribution (C) shoud be Numeric");
				document.forms[0].pensioncontribution.select();
				return false;
		    }
		    
			
			
			if(document.forms[0].paymentdt.value!=''){
				if (!convert_date(document.forms[0].paymentdt))
				{
					document.forms[0].paymentdt.focus();
					return false;
				}			
			}						
			
			var station;
			
						
			/*if((document.forms[0].region.value=='RAUSAP') ||  (document.forms[0].trust.value=='IAAI')){			
			station=document.forms[0].station[document.forms[0].station.options.selectedIndex].text;	
			}else{
			station=document.forms[0].station.value;			
			}*/
			
				
			//alert('------st---------'+st);	
			//alert('----station----'+document.forms[0].station[document.forms[0].station.options.selectedIndex].text);
			
			station=document.forms[0].station.value;
			verifiedby=document.forms[0].verifiedby.value;
			
			if(verifiedby==''){
			   flag="NewForm";
			}else{
				flag="EditForm";		
			}
			
			//alert(flag);
			
			
			if(remarksFlag=='Y'){
		 	document.forms[0].remarksFlag.value=remarksFlag;
		 	}
			
			var bankname,empname,accno,rtgscode;
		 	bankname=document.forms[0].bankempname.value;
		 	empname=document.forms[0].bankname.value;
		 	accno=document.forms[0].banksavingaccno.value;
		 	rtgscode=document.forms[0].bankemprtgsneftcode.value;
		 	
		 	//alert(bankname+'--'+empname+'--'+accno+'--'+rtgscode);
		 	
		 	if(bankname!=''||empname!=''||accno!=''||rtgscode!=''){
		 	document.forms[0].paymentinfo.value='Y';
		 	}
		 	 //alert(document.forms[0].paymentinfo.value);
		 	
		 	
			
			
			if(st!=station){
			//alert('Station name is different');
			
			 var chkstatus= confirm("Station Name is different.Do you really want to continue?");
			
			 if (chkstatus== true)
			 {
			var url ="<%=basePathWoView%>editNoteSheet.do?method=updateNoteSheetArrear&frm_name="+frmName+"&frm_nominees="+encodeURIComponent(te)+"&frmFlag="+flag+"&bankflag="+bankFlag+"&continueFlag="+continueFlag;
			 
			
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();		
			 }
			
			}else{
			var url ="<%=basePathWoView%>editNoteSheet.do?method=updateNoteSheetArrear&frm_name="+frmName+"&frm_nominees="+encodeURIComponent(te)+"&frmFlag="+flag+"&bankflag="+bankFlag+"&continueFlag="+continueFlag;
			 			
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();		
			}
   }
    
  
 //For Nominee Details 
  
  
   var count1 =0,slno=0;
    var len=0,deleteID='',reclength=0;
    var nomineearray = new Array();
    var nomineearrfinal=new Array();
    var nomineeInfo='';
    var formName='';
    	 
	function loadDetails(obj,frmName){		  		
		 
		  //alert('obj is -------'+obj);
		  //alert('frmName is -----'+frmName);
		  
          formName=frmName;
		 
		  nomineeInfo=obj;		  
		  loadDets();
	}
	 function loadDets(){		     
		  //alert(wthdrwdets);
		   if(nomineeInfo!='null'){
		   
		   nomineearray=nomineeInfo.split(':');		
		   
		   for(var i=0;i<nomineearray.length;i++){
		     nomineearrfinal=nomineearray[i].split('#');
		      
		     if(nomineearrfinal[8]=='Y')
		     document.forms[0].equalshare.checked=true;
		     
		     //alert('-------'+wdarrfinal[0]+'-------'+wdarrfinal[1]+'-------'+wdarrfinal[2]+'-------'+wdarrfinal[3]);
		     callNominee(nomineearrfinal[0],nomineearrfinal[1],nomineearrfinal[2],nomineearrfinal[3],nomineearrfinal[4],nomineearrfinal[5],nomineearrfinal[6],nomineearrfinal[7]); 	    
		   }
		   }
		   		   
  }
		 
 function dispDetails(){

  callNominee();
  
 }
   
     var loadIDList=new Array();
     
     var recortCountList=new Array();
      var flagCount=0;
 function callNominee(id,nomineeName,nomineeDOB,nomineeAddress,nomineeRelation,gardianName,gardianAddress,totalShare){     
   		 
   		 flagCount++;
	    //alert('flagCount-----'+flagCount);
  
  	if(flagCount>=1){
  	document.getElementById("submit").style.display="none";
   	document.getElementById("continue").style.display="block";
   }    
    if(id!=''){
    reclength=id;      
    }else{
    reclength++; 
    }
    
    lastId=reclength;
    
    loadIDList.push(reclength);    
    recortCountList.push(reclength); 
    
    var table = document.getElementById('nomineetable');	
    var tr    = document.createElement('TR');
	var td1   = document.createElement('TD');
	var td2   = document.createElement('TD');
	var td3   = document.createElement('TD');
	var td4   = document.createElement('TD');
	var td5   = document.createElement('TD');
	var td6   = document.createElement('TD');
	var td7   = document.createElement('TD');
	var td8   = document.createElement('TD');
	var inp1  = document.createElement('INPUT');
	var inp2  = document.createElement('INPUT');
	var inp3  = document.createElement('INPUT');
	var inp4  = document.createElement('SELECT');
	var inp5  = document.createElement('INPUT');
	var inp6  = document.createElement('INPUT');
	var inp7  = document.createElement('INPUT');
	var inp8  = document.createElement('INPUT');
	
	var opt  = document.createElement('OPTION');
	var optval=document.createTextNode('[Select One]');
	var opt1  = document.createElement('OPTION');
	var optval1=document.createTextNode('SPOUSE');
	var opt2  = document.createElement('OPTION');
	var optval2=document.createTextNode('SON');
	var opt3  = document.createElement('OPTION');
	var optval3=document.createTextNode('DAUGHTER');
	var opt4  = document.createElement('OPTION');
	var optval4=document.createTextNode('MOTHER');
	var opt5  = document.createElement('OPTION');
	var optval5=document.createTextNode('FATHER');
	var opt6  = document.createElement('OPTION');
	var optval6=document.createTextNode('SONS WIDOW');
	var opt7  = document.createElement('OPTION');
	var optval7=document.createTextNode('WIDOWS DAUGHTER');
	var opt8  = document.createElement('OPTION');
	var optval8=document.createTextNode('MOTHER-IN-LAW');
	var opt9  = document.createElement('OPTION');
	var optval9=document.createTextNode('FATHER-IN-LAW');
	var opt10  = document.createElement('OPTION');
	var optval10=document.createTextNode('GRAND SON');
	var opt11 = document.createElement('OPTION');
	var optval11=document.createTextNode('GRAND DAUGHTER-IN-LAW');
	var opt12 = document.createElement('OPTION');
	var optval12=document.createTextNode('DAUGHTER-IN-LAW');
	var opt13 = document.createElement('OPTION');
	var optval13=document.createTextNode('NIECE');
	var opt14 = document.createElement('OPTION');
	var optval14=document.createTextNode('NEPHEW');
	var opt15 = document.createElement('OPTION');
	var optval15=document.createTextNode('SISTER');
	var opt16 = document.createElement('OPTION');
	var optval16=document.createTextNode('BROTHER');

	
	inp4.setAttribute("Name", "nomineerelation");
	inp4.setAttribute("id", "nomineerelation"+reclength);
	
	
	opt.appendChild(optval);	
	opt.setAttribute("value","");
	
	opt1.appendChild(optval1);	
	opt1.setAttribute("value","SPOUSE");
	
	opt2.appendChild(optval2);	
	opt2.setAttribute("value","SON");
	
	opt3.appendChild(optval3);	
	opt3.setAttribute("value","DAUGHTER");
	
	opt4.appendChild(optval4);	
	opt4.setAttribute("value","MOTHER");
	
	opt5.appendChild(optval5);	
	opt5.setAttribute("value","FATHER");
	
	opt6.appendChild(optval6);	
	opt6.setAttribute("value","SONS WIDOW");
	
	opt7.appendChild(optval7);	
	opt7.setAttribute("value","WIDOWS DAUGHTER");
	
	opt8.appendChild(optval8);	
	opt8.setAttribute("value","MOTHER-IN-LAW");
	
	opt9.appendChild(optval9);	
	opt9.setAttribute("value","FATHER-IN-LAW");
	
	opt10.appendChild(optval10);	
	opt10.setAttribute("value","GRAND SON");
	
	opt11.appendChild(optval11);	
	opt11.setAttribute("value","GRAND DAUGHTER-IN-LAW");
	
	opt12.appendChild(optval12);	
	opt12.setAttribute("value","DAUGHTER-IN-LAW");
	
	opt13.appendChild(optval13);	
	opt13.setAttribute("value","NIECE");
	
	opt14.appendChild(optval14);	
	opt14.setAttribute("value","NEPHEW");
	
	opt15.appendChild(optval15);	
	opt15.setAttribute("value","SISTER");
	
	opt16.appendChild(optval16);	
	opt16.setAttribute("value","BROTHER");
	
	inp4.appendChild(opt);
	inp4.appendChild(opt1);
	inp4.appendChild(opt2);
	inp4.appendChild(opt3);
	inp4.appendChild(opt4);
	inp4.appendChild(opt5);
	inp4.appendChild(opt6);
	inp4.appendChild(opt7);
	inp4.appendChild(opt8);
	inp4.appendChild(opt9);
	inp4.appendChild(opt10);
	inp4.appendChild(opt11);
	inp4.appendChild(opt12);
	inp4.appendChild(opt13);
	inp4.appendChild(opt14);
	inp4.appendChild(opt15);
	inp4.appendChild(opt16);
	//alert(nomineeRelation);
	inp4.value=nomineeRelation; 
	
	
	inp8.setAttribute("Name", "serialNo");
    inp8.setAttribute("id", "serialNo"+reclength); 
    inp8.setAttribute("type", "hidden"); 
    inp8.setAttribute("value",reclength); 
    
   	
	inp1.setAttribute("Name", "nomineename");
	inp1.setAttribute("id", "nomineename"+reclength); 
	inp1.setAttribute("size", "18"); 
	inp1.setAttribute("maxlength", "50"); 	
		
	if(nomineeName!=''){
	inp1.setAttribute("value",nomineeName); 
	//inp1.setAttribute("readOnly", "true"); 
	}	
	inp1.onblur=function(){getTotShare();};
	
		 
	inp2.setAttribute("Name", "nomineeaddress");
	inp2.setAttribute("id", "nomineeaddress"+reclength);  
	inp2.setAttribute("size", "16"); 
	inp2.setAttribute("maxlength", "150"); 
	if(nomineeAddress!=''){
	inp2.setAttribute("value",nomineeAddress); 
	//inp2.setAttribute("readOnly", "true"); 
	}
	
	inp3.setAttribute("Name", "nomineeDOB");
	inp3.setAttribute("id", "nomineeDOB"+reclength);  
	inp3.setAttribute("size", "12"); 
	inp3.setAttribute("maxlength", "12"); 
	if(nomineeDOB!=''){
	inp3.setAttribute("value",nomineeDOB); 
	//inp3.setAttribute("readOnly", "true"); 
	}
	
	var calenderIcon = document.createElement('IMG');
	calenderIcon.setAttribute('src', '<%=basePath%>images/calendar.gif');
	
	
	calenderIcon.onclick = function(){		
		
		var idval=inp8.getAttribute("value");
	
		call_calender('forms[0].nomineeDOB'+idval);
	}	
	
	
	
	inp5.setAttribute("Name", "gardianname");
	inp5.setAttribute("id", "gardianname"+reclength);  
	inp5.setAttribute("size", "16"); 
	inp5.setAttribute("maxlength", "50"); 
	if(gardianName!=''){
	inp5.setAttribute("value",gardianName); 	
	}
	
	inp6.setAttribute("Name", "gardianaddress");
	inp6.setAttribute("id", "gardianaddress"+reclength);  
	inp6.setAttribute("size", "16"); 
	inp6.setAttribute("maxlength", "150"); 
	if(gardianAddress!=''){
	inp6.setAttribute("value",gardianAddress); 
	//inp6.setAttribute("readOnly", "true"); 
	}
	
	inp7.setAttribute("Name", "totalshare");
	inp7.setAttribute("id", "totalshare"+reclength);  
	inp7.setAttribute("size", "5"); 
	inp7.setAttribute("maxlength", "20"); 
	if(totalShare!=''){
	inp7.setAttribute("value",totalShare); 
	

	}
	
	var deleteIcon     = document.createElement('IMG');
	deleteIcon.setAttribute('src', '<%=basePath%>/images/cancelIcon.gif');
	deleteIcon.onclick = function(){
		removeWthDrwls(tr);
		getTotShare();
	}

	
    var space1=document.createTextNode('  ');
    var space2=document.createTextNode('  ');
    
	table.appendChild(tr);
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	tr.appendChild(td4);
	tr.appendChild(td5);
	tr.appendChild(td6);
	tr.appendChild(td7);
	tr.appendChild(td8);
	
	td1.appendChild(inp1);
	td2.appendChild(inp2);
	td3.appendChild(inp3);
	td3.appendChild(space1);
	td3.appendChild(calenderIcon);
	td3.appendChild(space1);
	td4.appendChild(inp4);
	td5.appendChild(inp5);
	td6.appendChild(inp6);
	td7.appendChild(inp7);
	td8.appendChild(inp8);
	//td7.appendChild(space1);
	td8.appendChild(deleteIcon);
	          
  } 
  function findIndexDigits(str){
	var index=0;
	for(var i=0;i<str.length;i++){
		if(str.charAt(i)>='0' && str.charAt(i)<='9'){
			
			index=i;
			break;
		}
	}
	return index;
}
function removeWthDrwls(tr){
  	
 	if(tr.hasChildNodes()==true){
 		var oChildNodes = tr.childNodes;	
 		var deleteTagID,deletedNodeId,childNodesLength;
 		childNodesLength=oChildNodes.length;
 		deleteTagID=oChildNodes[1].lastChild.id;
 		deletedNodeId=deleteTagID.substring(findIndexDigits(deleteTagID),deleteTagID.length);
 		
 			
		//alert(recortCountList);
		//alert('deleted id is ---'+deletedNodeId);
			
		
		var temp=new Array();
		
			for(var i=0;i<recortCountList.length;i++)
			{
				if(recortCountList[i]!=deletedNodeId)
					temp[temp.length]=recortCountList[i];	
			}
			
			
			recortCountList=temp;			
			//alert('final list is'+recortCountList);
				
 		
 		if(deleteID==''){
 			deleteID=deletedNodeId;
 		}else{
 			deleteID=deleteID+','+deletedNodeId;
 		}
 		
 	}
	tr.parentNode.removeChild(tr);
	flagCount--;
	if(flagCount==0){
	document.getElementById("submit").style.display="block";
   	document.getElementById("continue").style.display="none";
	} 
	
} 	 
  
function validateNomineeDetails(reclength)
{
	
	
    if(document.forms[0].seperationreason.value=='Death')
	{
	
		if(document.forms[0].equalshare.checked==false){
			if(document.getElementById("totalshare"+reclength).value==''){
			  alert('Please Enter Total Share');
			  document.getElementById("totalshare"+reclength).focus();
			  return false;	  
			}		
		}
		
		if(document.forms[0].equalshare.checked==false){
			if (!ex.test(document.getElementById("totalshare"+reclength).value) && document.getElementById("totalshare"+reclength).value!="")
			{
				alert("Total Share shoud be Numeric");
				document.getElementById("totalshare"+reclength).focus();
				return false;
			}
		}
		
		 
		if(document.forms[0].equalshare.checked==false){
			if(document.getElementById("totalshare"+reclength).value>100){
			  alert('Total Share value should not exceed 100');
			  document.getElementById("totalshare"+reclength).focus();
			  return false;	  
			}		
		}
		
	}
return true;
}
 
 
 
 
 function numOrdA(a, b){ return (a-b); }
 
  function createList(sortedDeletList){
	var sortedltdid,lastdeletedId;
	//alert('createList===Before deleted list'+sortedDeletList);
	var loadListID;
	//alert('createList==sortedDeletList==='+sortedDeletList);
	finalJSList=new Array();
	for(var j=0;j<loadIDList.length;j++){
	
		loadListID=loadIDList[j];
	  	if(sortedDeletList.length!=0){
	  		for(var sndlst=0;sndlst<sortedDeletList.length;sndlst++){
			sortedltdid=sortedDeletList[sndlst];
			lastdeletedId=sortedltdid;
			//alert(sortedltdid+'sortedltdid==loadListID==='+loadListID);
			//alert('createList===before deleted list'+sortedDeletList);
			if(sortedltdid==loadListID){
				sortedDeletList.splice(sndlst,1);
				//alert('createList===after deleted list'+sortedDeletList);
				break;
			}else{
				finalJSList.push(loadListID);
			}
		}
	  	}else{
	  		//alert('last deleted Id'+lastdeletedId);
	  		if(lastdeletedId!=reclength){
	  			finalJSList.push(loadListID);
	  		}else if(sortedDeletList.length==0){
	  			finalJSList.push(loadListID);
	  		}
	  		
	  	}
	  	
	  	//alert('-----finalJSList-----'+finalJSList);
		
	}
	return removeDuplicateElement(finalJSList);
}

function removeDuplicateElement(arrayName)
      {
        var newArray=new Array();
        label:for(var i=0; i<arrayName.length;i++ )
        {  
          for(var j=0; j<newArray.length;j++ )
          {
            if(newArray[j]==arrayName[i]) 
            continue label;
          }
          newArray[newArray.length] = arrayName[i];
        }
        return newArray;
      }
 
  var te='';
 function withDrawalDet(){  
  
	var id=0;
	var flag="N";
	var exp=/^[0-9]+$/;		
	
		 te='';
		var finaldltdID;
		var deletedList=new Array();
		var strlen=reclength;		
		
		//alert('-----deleteID------'+deleteID);
		
		if(deleteID!=''){
			deletedList=deleteID.split(','); 
		}		
				
		var deletedSortedList=new Array();		
		
		deletedSortedList=deletedList.sort(numOrdA);
		
		//alert('deletedSortedList======'+deletedSortedList);
		
		var afterDeletedList=createList(deletedSortedList);	
		//alert('afterDeletedList'+afterDeletedList);
		for(var i=0;i<afterDeletedList.length;i++){	 	 
		            
				finaldltdID=afterDeletedList[i];				
												
				if(finaldltdID!=''){    			
				
				 var flag=validateNomineeDetails(finaldltdID);
								 
			
				 	 
				    if(!flag){
					    te='';
						return false;  
				    }else{
			      		te+=document.getElementById("serialNo"+finaldltdID).value+"#"+document.getElementById("nomineename"+finaldltdID).value+"#"+document.getElementById("nomineeaddress"+finaldltdID).value+"#"+document.getElementById("nomineeDOB"+finaldltdID).value+"#"+document.getElementById("nomineerelation"+finaldltdID).value+"#"+document.getElementById("gardianname"+finaldltdID).value+"#"+document.getElementById("gardianaddress"+finaldltdID).value+"#"+document.getElementById("totalshare"+finaldltdID).value+"#"+deleteID+":";	
			      		//alert('------'+te);
		      		}
			    }		   		
    		    		      			 				 				
         }		
	
	if(te=='')
	  te=deleteID;
		return true;
	 	
  }
  
  
  function dispTotShare(){

    var n=document.getElementById("totalshare");
	
	//alert('Final List is'+recortCountList);
	//alert(document.getElementById("totalshare"+1).value);	
		if(document.forms[0].equalshare.checked){
			var listsize=recortCountList.length;
	
				if(listsize>=2){
					var totvalue=100/listsize;	
	      			for(var k=0;k<recortCountList.length;k++){	  
					   		//alert('tot share val is'+document.getElementById("totalshare"+recortCountList[k]).value);
	   					document.getElementById("totalshare"+recortCountList[k]).value=totvalue;
				   		//document.getElementById("totalshare"+recortCountList[k]).readOnly=true;
		  			}	      
				}else{
				
					document.getElementById("totalshare"+listsize).value="100";
				}
      }
	
		
	
}

function getTotShare(){

var listsize=0;

	 var n=document.getElementById("totalshare");
	
	if(document.forms[0].equalshare.checked){		
		  listsize=recortCountList.length;
		  
	
	if(listsize>=2){
	
	      var totvalue=100/listsize;	
	     // alert(totvalue);
	      
	      for(var k=0;k<recortCountList.length;k++){	  
	   		//alert('tot share val is'+document.getElementById("totalshare"+recortCountList[k]).value);
	   		document.getElementById("totalshare"+recortCountList[k]).value=totvalue;
	   		  //document.getElementById("totalshare"+recortCountList[k]).readOnly=true;
		  }	      
	
	}else{
	document.getElementById("totalshare"+listsize).value="100";
	}	 	   
	 		
 	}
 	
 	 
}
  
   
  // ---------------------------	 
  function call_calender(dobValue){       	
  	  show_calendar(dobValue);
  }  
     
  function numsDotOnly()
  {
       if(((event.keyCode>=48)&&(event.keyCode<=57))||(event.keyCode==46))
	   {
	      event.keyCode=event.keyCode;
	   }
       else
	   {
	      event.keyCode=0;
       }
 } 
 
 function calNetContribution(){ 
    if(document.forms[0].emplshare.value!="" || document.forms[0].emplrshare.value!="" || document.forms[0].pensioncontribution.value!=""){
   
    if(document.forms[0].emplshare.value!=""){    
    emplshare=parseInt(document.forms[0].emplshare.value);
    }else{
    emplshare=0;
    }        
    if(document.forms[0].emplrshare.value!=""){    
    emplrshare=parseInt(document.forms[0].emplrshare.value);
    }else{
    emplrshare=0;
    }       
    if(document.forms[0].pensioncontribution.value!=""){    
    pensioncontribution=parseInt(document.forms[0].pensioncontribution.value);
    }else{
    pensioncontribution=0;
    }     
    document.forms[0].netcontribution.value=(emplshare+emplrshare)-pensioncontribution;   
    }
 }		
 
  function calNetContributionAmt(){ 
 
 
    if(document.forms[0].emplshare.value!="" || document.forms[0].emplrshare.value!="" || document.forms[0].pensioncontribution.value!="" || document.forms[0].adhocamt.value!=""){
   
    if(document.forms[0].emplshare.value!=""){    
    emplshare=parseInt(document.forms[0].emplshare.value);
    }else{
    emplshare=0;
    }        
    if(document.forms[0].emplrshare.value!=""){    
    emplrshare=parseInt(document.forms[0].emplrshare.value);
    }else{
    emplrshare=0;
    }       
    if(document.forms[0].pensioncontribution.value!=""){    
    pensioncontribution=parseInt(document.forms[0].pensioncontribution.value);
    }else{
    pensioncontribution=0;
    }     
    
    if(document.forms[0].adhocamt.value!=""){
    adhocamt=parseInt(document.forms[0].adhocamt.value);
    }else{
    adhocamt=0;
    }      
    
    if(adhocamt==0){  
    document.forms[0].netcontribution.value=(emplshare+emplrshare)-pensioncontribution;   
    }else{
      document.forms[0].netcontribution.value=((emplshare+emplrshare)-pensioncontribution)-adhocamt;    
    }
   
    }
    
   
      
 }			
 
 
 function displayNominee(){
 
    if(document.forms[0].seperationreason.value=='Death'){
     	document.getElementById("nomineelabel").style.display="block";
     	document.getElementById("dispnominee").style.display="block";
     	//document.getElementById("remarks").style.display="none";
     }else{
        document.getElementById("nomineelabel").style.display="none";
     	document.getElementById("dispnominee").style.display="none";
     	if(document.getElementById("remark")!=null){
     	document.getElementById("remark").style.display="block";
     	}
     }
     
     if(document.forms[0].seperationreason.value=='Resignation'){
          
     	document.getElementById("resignationdata").style.display="block";
     	
     	document.getElementById("adhocamount").style.display="none";
     	document.getElementById("adhocamtlabel").style.display="none";
     	document.getElementById("adhocnetcontlabel").style.display="none";
        document.getElementById("netcontlabel").style.display="block";
          
     	document.getElementById("remarks").style.display="block";  
     	if(document.getElementById("remark")!=null){        	
     	document.getElementById("remark").style.display="none";
     	}
     
     }else{
        document.getElementById("resignationdata").style.display="none";
     	document.getElementById("remarks").style.display="block";
     	if(document.getElementById("remark")!=null){
     	document.getElementById("remark").style.display="block";
     	}
     }
     
     
       if(document.forms[0].seperationreason.value=='VRS'){                      
           
           document.getElementById("adhocamount").style.display="block";
            document.getElementById("adhocamtlabel").style.display="block";
           document.getElementById("adhocnetcontlabel").style.display="block";
           document.getElementById("netcontlabel").style.display="none";       
           
      }else{         
       	 
       	  document.getElementById("adhocamount").style.display="none";
       	  document.getElementById("adhocamtlabel").style.display="none";
       	  document.getElementById("adhocnetcontlabel").style.display="none";
          document.getElementById("netcontlabel").style.display="block";      
       	
      }      
      
      if(document.forms[0].seperationreason.value=='VRS'){   
       calNetContributionAmt();
      }else{
      calNetContribution();
      }
      
 }
 
 //alter
 function  getNominee(){
   if(document.forms[0].seperationreason.value=='Death'){
    
  // document.getElementById("dispnominee").style.display="block";
   document.getElementById("nomineelabel").style.display="block";
   //document.getElementById("divNominee1").style.display="block";
   
   
   }
   if(document.forms[0].seperationreason.value=='Resignation'){
     	document.getElementById("resignationdata").style.display="block";
     	document.getElementById("remarks").style.display="block";
     	if(document.getElementById("remark")!=null){
     	document.getElementById("remark").style.display="none";	
     	}
     	
   }
     
 }
 
 function getStation(){
 
  // alert('-----trust---'+document.getElementById("trust").value);
   
   if(document.getElementById("trust").value=='IAAI'){
      document.getElementById("IAAIStation").style.display="block";
      document.getElementById("RAUSAPRegion").style.display="none";
      document.getElementById("NAAStation").style.display="none";
   }
   
   if(document.getElementById("trust").value!='IAAI'){
     if(document.getElementById("region").value=='RAUSAP'){
	   //alert('-------if-------');
	   document.getElementById("RAUSAPRegion").style.display="block";
	   document.getElementById("NAAStation").style.display="none";
	   document.getElementById("IAAIStation").style.display="none";
	   }else{
	   //alert('-------else-------');
	   document.getElementById("RAUSAPRegion").style.display="none";
	   document.getElementById("NAAStation").style.display="block";	   
	   document.getElementById("IAAIStation").style.display="none";
	   }
     
   }
 }
 
 function loadAdhoc(){
   
 if(document.forms[0].seperationreason.value=='VRS'){
  
  document.getElementById("adhocamount").style.display="block";
  document.getElementById("adhocamtlabel").style.display="block";
   document.getElementById("netcontlabel").style.display="none";
    document.getElementById("adhocnetcontlabel").style.display="block";
  
 }else{
  
    document.getElementById("netcontlabel").style.display="block";
    document.getElementById("adhocnetcontlabel").style.display="none";
 }
 }
 
 /*function loadStation(){
 
   alert('-----trust---'+document.getElementById("trust").value);  
    alert('-----region---'+document.forms[0].region.value);
   
   if(document.getElementById("trust").value=='IAAI'){    
      document.getElementById("IAAIStation").style.display="block";
      document.getElementById("RAUSAPRegion").style.display="none";
      document.getElementById("NAAStation").style.display="none";
   }
   
   if(document.getElementById("trust").value!='IAAI'){     
 
	   if(document.getElementById("region").value=='RAUSAP'){	  
	   document.getElementById("RAUSAPRegion").style.display="block";
	   document.getElementById("NAAStation").style.display="none";
	   document.getElementById("IAAIStation").style.display="none";
	   }else{	 
	   document.getElementById("RAUSAPRegion").style.display="none";
	   document.getElementById("NAAStation").style.display="block";
	   document.getElementById("IAAIStation").style.display="none";
	   }
   }
   
 }*/
 
 function getStations(){ 
   
      var url = "/editNoteSheet.do?method=loadStations";
	
	  var index=document.forms[0].region.selectedIndex.value;
       var selectedIndex=document.forms[0].region.options.selectedIndex;
	   var searchregion=document.forms[0].region[selectedIndex].value;  
	 
	  
    var ajaxRequest = new Ajax.Request(url, {
	    method:       'get', 
	    parameters: {region: searchregion},
	    asynchronous: true,
	    onSuccess:   showResponse
	});    

 
 }
 
 function getNodeValue(obj,tag){    
  if(obj.getElementsByTagName(tag)[0].firstChild) {
		return unescape(obj.getElementsByTagName(tag)[0].firstChild.nodeValue);
	}else{
	  return "";
	}
 }
  function showResponse(xmlHttpRequest) {  	 
	 var obj1 = document.getElementById("station");
	obj1.options.length = 0;
	 
	 
	  var xm = xmlHttpRequest.responseXML;    
     var stype = xmlHttpRequest.responseXML.getElementsByTagName('Detail');
     
   
       if(stype.length>=1){
	       for(var i=0;i<stype.length;i++){	      
	         var val = getNodeValue(stype[i],'Station');	      
	 		var nm = getNodeValue(stype[i],'Station'); 	 		
	 		obj1.options[obj1.options.length] = new Option(nm,val);
	       }
       }else{
       obj1.options[obj1.options.length] = new Option("---No Stations--","");
       }
	  
}

function deleteNomineeDetils(cpfaccno,nomineeName,rowcolumn,rowid)
{
  var rowcolumn=rowcolumn;

  document.forms[0].deletionStatus[rowid].value="D"; 
  var deleterow = document.getElementById(rowcolumn);
  document.getElementById(rowcolumn).style.display = 'none'; 
  
  	    
} 

 
function dispArrDate(){

 if(document.forms[0].arreartype.value=='payscalerevision')
 document.getElementById("arrdate").style.display = "block"; 
 else
 document.getElementById("arrdate").style.display = "block"; 
 
}

function chngTransRemarks(){
 //alert('you pressed');
  remarksFlag='Y';
}


	var bankinfo;
function loadBankDetails(){
// alert(document.forms[0].seperationreason.value+"==");
	if(document.forms[0].seperationreason.value=="Death"){
	document.getElementById("BankDetails").style.display = "none"; 
	}else{
	document.getElementById("BankDetails").style.display = "block"; 
	bankinfo=document.forms[0].paymentinfo.value;
 //alert(bankinfo);
	if(bankinfo=='Y'){
  
	document.forms[0].bankempname.readOnly=true;
	document.forms[0].bankname.readOnly=true;
	document.forms[0].banksavingaccno.readOnly=true;
	document.forms[0].bankemprtgsneftcode.readOnly=true;
	} else{ 
	document.getElementById("BankDetails").style.display = 'block'; 
  }
 } 
}


	var bankFlag='N';
function editBankDetails(){
	if(bankinfo=='N'){
	alert('Nothing to Edit');
	bankFlag='Y';
	}else{
		alert('You want to edit the Details');
		document.forms[0].bankempname.readOnly=false;
		document.forms[0].bankname.readOnly=false;
		document.forms[0].banksavingaccno.readOnly=false;
		document.forms[0].bankemprtgsneftcode.readOnly=false;
		bankFlag='Y';
   }
}
	


 </script>
</head>
	
<body class="bodybackground" onload="hide('<%=request.getAttribute("focusFlag")%>');getNominee();loadAdhoc();dispArrDate();loadBankDetails();loadDetails('<%=request.getAttribute("nomineeInfo")%>','<bean:write name="advanceBean" property="frmName"/>')">

<%	
	
  	ArrayList yearList=new ArrayList();
  
  	if(request.getAttribute("yearList")!=null){
  	yearList=(ArrayList)request.getAttribute("yearList");
  	}
%>

		<html:form method="post" action="editNoteSheet.do?method=updateNoteSheet">
				
				
				
	  <logic:equal name="advanceBean" property="frmName" value="FSArrearProcess">     
     <%=ScreenUtilities.saearchHeader("Final Settlement Arrear Process Form")%>
     </logic:equal>
     
     
   
		<table width="720" border="0" cellspacing="3" cellpadding="0">
					
									
										<tr>
											<td class="tableTextBold">
												PF ID:&nbsp;
											</td>
											<td>
												<html:hidden property="pensionNo" name="personalInfo" />	
												<html:hidden property="verifiedby" name="noteSheetInfo"/>	
												<html:hidden property="paymentinfo" name="noteSheetInfo"/>	
																						
												<html:text property="pfid"  name="personalInfo" styleClass="TextField" maxlength="15" tabindex="1" readonly="true"/>
												
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Employee Code:&nbsp;
											</td>
											<td>
												<html:text property="employeeNo" name="personalInfo" styleClass="TextField" tabindex="4" readonly="true" />
											</td>
											<td class="tableTextBold">
												Old CPF A/C No:&nbsp;
											</td>
											<td>
												<html:text property="cpfaccno" name="personalInfo" styleClass="TextField" tabindex="5" readonly="true" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Name:&nbsp;
											</td>
											<td>
												<html:text property="employeeName" name="personalInfo" styleClass="TextField" tabindex="6" readonly="true" />
											</td>
											<td class="tableTextBold">
												Father's Name:&nbsp;
											</td>
											<td>
												<html:text property="fhName" name="personalInfo" styleClass="TextField" tabindex="7" readonly="true" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Date of Birth:&nbsp;
											</td>
											<td>
												<html:text property="dateOfBirth" name="personalInfo" styleClass="TextField" tabindex="8" readonly="true" />
											</td>
											<td class="tableTextBold">
												Date of Joining in AAI:&nbsp;
											</td>
											<td>
												<html:text property="dateOfJoining" name="personalInfo" styleClass="TextField" tabindex="9" readonly="true" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Designation:&nbsp;
											</td>
											<td>												
												<html:text property="designation"  name="noteSheetInfo" styleClass="TextField" tabindex="10" readonly="true" />
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Reason For Seperation:&nbsp;
											</td>
											<td>
											
											<html:text property="seperationreason"  name="noteSheetInfo" styleClass="TextField" tabindex="11" readonly="true"/>
											
												
											</td>
											<td class="tableTextBold">
												Date Of Seperation:&nbsp;
											</td>
											<td>
												<html:text property="seperationdate" name="noteSheetInfo" styleClass="TextField" maxlength="12" tabindex="12" readonly="true"/>
												<a href="javascript:show_calendar('forms[0].seperationdate');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>

											</td>
										</tr>
										<tr>
										<td class="tableTextBold">
												Region:&nbsp;
											</td>
											
											<td>		
								          
								             <html:text property="region"  name="noteSheetInfo" styleClass="TextField" tabindex="13" readonly="true"/>
								             
								            				           
								          
								           </td>
								           
											<td class="tableTextBold">
												Station:&nbsp;											
											</td>											
										
										    <td  nowrap="nowrap">									 
											
											    <html:text property="station"  name="noteSheetInfo" styleClass="TextField" tabindex="14" readonly="true"/>
										    												     
										    										     
											</td>																
											
								           
											<!-- <td>
												<html:text property="region" name="personalInfo" tabindex="14" />
											</td>-->
										</tr>
										
										<tr>
											<td class="tableTextBold">
												Arrear Type:&nbsp;
											</td>
											<td>											
											<html:select  property="arreartype"  name="noteSheetInfo" styleClass="TextField" tabindex="15">
													<html:option value="payscalerevision">Revision of Pay Scale</html:option>
													<html:option value="revisedinterestrate">Revised Interest Rate</html:option>													
													<html:option value="dearnesspay">Dearness Pay Arrear(DP)</html:option>
												</html:select>
											</td>
											
											<logic:equal property="arreartype"  name="noteSheetInfo" value="payscalerevision">
											
											<td colspan="2" id="arrdate" style="display: none;">											
												<table  align="center" border="0">
												<tr>
												<td class="tableTextBold" width="54%">
												Arrear Date:&nbsp;
												</td>
												<td>											
												<html:text property="arreardate"  name="noteSheetInfo" styleClass="TextField" tabindex="16" readonly="true"/>
												<a href="javascript:show_calendar('forms[0].arreardate');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>
												
												</td>
												</tr>											
												</table>											
											</td>
																						
											</logic:equal>
											
										</tr>
										
										<logic:equal property="arreartype"  name="noteSheetInfo" value="revisedinterestrate">
											
										<tr>
										
										  <td colspan="5">
										  
										  <table border="0" width="720">
										  <tr>										
											
											<td class="tableTextBold">
												From Financial Year:&nbsp;
											</td>
											<td>	
											
											<html:select property="fromfinyear" name="noteSheetInfo" styleClass="TextField" style="width:119px" tabindex="17" >
												<%for (int j = 0; j < yearList.size(); j++) {
												    String fromYear=yearList.get(j).toString();
												%>
												<html:option value='<%=fromYear%>'>
													<%=yearList.get(j)%>
												</html:option>
												<%}%>
											</html:select>									
																						
											</td>											
											
											<td colspan="2" id="arrdate" style="display: none;">											
												<table  align="center" border="0">
												<tr>
												<td class="tableTextBold" width="54%">
												To Financial Year:&nbsp;
												</td>
												<td>											
												
												<html:select property="tofinyear" name="noteSheetInfo" styleClass="TextField" style="width:119px" tabindex="18" >
												<%for (int j = 0; j < yearList.size(); j++) {
												  String toYear=yearList.get(j).toString();
												%>
												<html:option value='<%=toYear%>'>
													<%=yearList.get(j)%>
												</html:option>
												<%}%>
												</html:select>
												
												</td>
												</tr>											
												</table>											
											</td>
											
											
										</tr>
										
											<tr>
											<td class="tableTextBold">
												Revised Interest Rate From :&nbsp;
											</td>
											<td>
												<html:text property="interestratefrom" name="noteSheetInfo" styleClass="TextField" maxlength="12" tabindex="19" />
												
											</td>			
											<td class="tableTextBold">
												Revised Interest Rate To :&nbsp;
											</td>
											<td>
												<html:text property="interestrateto" name="noteSheetInfo" styleClass="TextField" maxlength="12" tabindex="20" />
												
											</td>			
											
											</td>														
										</tr>	
										
										</table>
										</td>
											</logic:equal>

<logic:equal property="arreartype"  name="noteSheetInfo" value="dearnesspay">
											
										<tr>
										
											<td class="tableTextBold">
												From Financial Year:&nbsp;
											</td>
											<td>	
											
											<html:select property="fromfinyear" name="noteSheetInfo" styleClass="TextField" style="width:119px" tabindex="17" >
												<%for (int j = 0; j < yearList.size(); j++) {
												    String fromYear=yearList.get(j).toString();
												%>
												<html:option value='<%=fromYear%>'>
													<%=yearList.get(j)%>
												</html:option>
												<%}%>
											</html:select>									
																						
											</td>											
											
											<td colspan="2" id="arrdate" style="display: none;">											
												<table  align="center" border="0">
												<tr>
												<td class="tableTextBold" width="54%">
												To Financial Year:&nbsp;
												</td>
												
												<td>											
												
												<html:select property="tofinyear" name="noteSheetInfo" styleClass="TextField" style="width:119px" tabindex="18" >
												<%for (int j = 0; j < yearList.size(); j++) {
												  String toYear=yearList.get(j).toString();
												%>
												<html:option value='<%=toYear%>'>
													<%=yearList.get(j)%>
												</html:option>
												<%}%>
												</html:select>
												
												</td>
												
											</tr>	
																							
												</table>											
											</td>
												
										

								</tr>
						
								</logic:equal>
										<tr>
											<td class="tableTextBold">
												Amount Admitted for Payment by
												<br>
												CPF(Hqrs)with interest up to <font color="red">&nbsp;*</font>:&nbsp;
											</td>
											<td>
												<html:text property="amtadmtdate" name="noteSheetInfo" styleClass="TextField" maxlength="12" tabindex="21" />
												<a href="javascript:show_calendar('forms[0].amtadmtdate');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Employee Share (Subscription)(A)<font color="red">&nbsp;*</font>:&nbsp;
											</td>
											<td>
												<html:text property="emplshare" name="noteSheetInfo" styleClass="TextField" maxlength="15" tabindex="22" />
											</td>
											<td class="tableTextBold">
												Employer Share ( Contribution)(B)<font color="red">&nbsp;*</font>:&nbsp;
											</td>
											<td>
												<html:text property="emplrshare" name="noteSheetInfo" styleClass="TextField" maxlength="15" tabindex="23" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Less Difference of Pension Contribution (C)<font color="red">&nbsp;*</font>:&nbsp;
											</td>
											<td>
												<html:text property="pensioncontribution" name="noteSheetInfo" styleClass="TextField" maxlength="15" tabindex="24" onblur="calNetContribution()" />
											</td>
																						
											<td class="tableTextBold" id="adhocamtlabel"  style="display:none;">
												Adhoc Amount already paid (D):&nbsp;
											</td>
											<td id="adhocamount" style="display:none;">
												<html:text property="adhocamt"  name="noteSheetInfo" styleClass="TextField" tabindex="25"  maxlength="13"  onblur="calNetContributionAmt()" />
											</td>
											
										</tr>
										<tr>
											<td class="tableTextBold" id="netcontlabel" style="display: none;">
												Net contribution(A+B-C) :&nbsp;
											</td>
											<td class="tableTextBold" id="adhocnetcontlabel" style="display: none;">
												Net contribution(A+B-C-D) :&nbsp;
											</td>
											<td>
												<html:text property="netcontribution" name="noteSheetInfo" styleClass="TextField" tabindex="26" readonly="true" />
											</td>
											<td class="tableTextBold">
												Sanction No:&nbsp;
											</td>
											<td>
											    <html:hidden property="nssanctionno" name="noteSheetInfo"/>
												<html:text property="sanctionno" name="noteSheetInfo" styleClass="TextField" tabindex="27" maxlength="25" />
											</td>
										</tr>
									
										<tr>
											<td class="tableTextBold">
												Payment Date:&nbsp;
											</td>
											<td>
												<html:text property="paymentdt"  name="noteSheetInfo" styleClass="TextField" tabindex="28" maxlength="12" />
												<a href="javascript:show_calendar('forms[0].paymentdt');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>
											</td>
											 <td class="tableTextBold">
												Trust :&nbsp;
											</td>
											<td>											
												<html:select property="trust"  name="noteSheetInfo" style="width: 119px" styleClass="TextField" tabindex="29" onchange="getStation();">
												<html:option value="NAA">NAA</html:option>
												<html:option value="AAI">AAI</html:option>
												<html:option value="IAAI">IAAI</html:option>
												</html:select>
											</td>
										</tr>
										
										<tr>
																					
											<td class="tableTextBold">
												Verified By:&nbsp;
											</td>
											<td>												
												<html:select styleClass="TextField"  property="authorizedsts" >
									            <html:option value="A">Accept</html:option>
									            <html:option value="R">Reject</html:option>
									            </html:select>
											</td>
											
											<td>&nbsp;</td>										
										</tr>
										
										
										 
											<tr id="BankDetails" style="display:none;">																						
													
												<td colspan="3">
												<table align="center" border="0">
												
										<tr>
												<td  class="tableTextBold" align="left">
												BANK DETAILS FOR PAYMENT: <img alt="" src="<%=basePath%>images/icon-edit.gif" onclick="javascript:editBankDetails();" >
												</td>
										</tr>	
																								
										<tr>										     
											<td class="tableTextBold">Name of Employee as per Saving Bank Account:</td>
											<td><html:text styleClass="TextField"  property="bankempname" name="bankMasterBean"/></td>
											 
											 <td class="tableTextBold">Name Of Bank:</td>
											<td><html:text styleClass="TextField"  property="bankname" name="bankMasterBean"/></td>
										</tr>
										
										
										<tr>					   
											<td class="tableTextBold">Bank A/c No:</td>
											<td><html:text styleClass="TextField"  property="banksavingaccno" name="bankMasterBean"/></td>
																						
											<td class="tableTextBold" width="20%">IFSC Code Of Bank:</td>
											<td><html:text styleClass="TextField"  property="bankemprtgsneftcode" name="bankMasterBean"/></td>
										</tr>
										 
										 
										<tr>
										<td>&nbsp;</td>
										</tr>
												</table>
											 </td>
											 </tr>
										
										<logic:equal property="seperationreason" name="noteSheetInfo" value="Death">
										<tr>																					
											<td class="tableTextBold">
												Total Share:&nbsp;
											</td>
											<td>											
												<input type="checkbox" name="equalshare" onclick="dispTotShare();">
											</td>
											
											<td>&nbsp;</td>										
										</tr>
										</logic:equal>
										
									
									<tr id="nomineelabel" style="display:none">
										<td colspan="4" >
											<table align="center" border="0">
											<tbody id="nomineetable">
												<tr>
													<td class="tableTextBold" align="center">
														Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Address&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Dateof Birth&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Relation with Member&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Name of Guardian&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Address of Guardian&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Total Share
														<BR>
														payable in %
													</td>
													<td>
													&nbsp;<b><img alt="" src="<%=basePath%>/images/addIcon.gif" onclick="callNominee('','','','','','','','');" ></b>
													</td>
												</tr>	
												<tr>
												</tr>
												</tbody>
																			
												
											</table>
											</td>
											</tr>
									 
										 
										<tr id="resignationdata" style="display:none">
										    <td class="tableTextBold">
												Payment to be made in favor of  : &nbsp;
											</td>
											<td>
												<html:text property="seperationfavour" name="noteSheetInfo" styleClass="TextField" tabindex="37" maxlength="95" />
											</td>
											<td class="tableTextBold">
												Remarks  : &nbsp;
											</td>
											<td>
												<html:textarea property="seperationremarks"  name="noteSheetInfo" styleClass="TextField" tabindex="37" rows="5" cols="27" />
											</td>
										</tr>
										
										<tr id="remarks">
											<td class="tableTextBold" valign="top">
												NoteSheet Remarks : &nbsp;
											</td>
											<td colspan="3">
											   <table border="0">
											    <tr>
											    <td>
											   <html:textarea property="remarks"  name="noteSheetInfo" tabindex="37" rows="7" cols="60" />
											   </td>
											   </tr>
											   </table>
												
											</td>						
											
										</tr>
											<tr id="soremarks">
											<td class="tableTextBold" valign="top">
												Sanction Order Remarks : &nbsp;
											</td>
											<td colspan="3">
											   <table border="0">
											    <tr>
											    <td>
											   <html:textarea property="soremarks"  name="noteSheetInfo" tabindex="37" rows="7" cols="60" />
											   </td>
											   </tr>
											   </table>
												
											</td>						
											
										</tr>
										
										<tr id="transremarks">
											<td class="tableTextBold" valign="top">
												Narration : &nbsp;
											</td>
											<td colspan="3">
											   <table border="0">
											    <tr>
											    <td>
											   <html:textarea property="transremarks"  name="noteSheetInfo" tabindex="37" rows="3" cols="60"  onkeypress="chngTransRemarks();" />
											   <html:hidden  name="noteSheetInfo"  property="remarksFlag"/> 
											   </td>
											   </tr>
											   </table>
												
											</td>						
											
										</tr>  

									</table>
									<table align="center">

										<tr>
											<td align="left" id="submit">
												<input type="button" class="btn" value="Update" onclick="submitData('<bean:write name="personalInfo" property="station"/>','<bean:write name="advanceBean" property="frmName"/>','N');" />
											</td>
											<td align="left" id="continue" style="display:none;">
												<input type="button" class="btn" value="Save & Continue" onclick="submitData('<bean:write name="personalInfo" property="station"/>','<bean:write name="advanceBean" property="frmName"/>','Y');" />
											</td>
											<td align="right">
                                                 
												<input type="reset" class="btn" value="Reset" onclick="javascript:frmPrsnlReset('<bean:write name="advanceBean" property="frmName"/>')" class="btn" />
												<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											</td>
										</tr>


									</table>
<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

