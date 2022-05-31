
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.bean.advances.AdvanceEditBean"%>
<%@ page isErrorPage="true"%>


<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			
  ArrayList wthdrwList=new ArrayList();			
			
  AdvanceEditBean basicBean=new AdvanceEditBean();
  String wthdrwStr=(String)request.getAttribute("wthdrwStr");
  System.out.println("-----wthdrwStr in jsp------"+wthdrwStr);
  
  int size = 0;
  int wthdrwsize=1;	
	
			
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>AAI</title>
		<LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime.js"></SCRIPT>
		<script type="text/javascript"> 
		var withdrawalarray = new Array(); 
		var wdarray = new Array();
		var wdarrfinal=new Array();
		var estri="";
		 var reclength=1;
		 var wthdrwdets;
		 var formName='';
		 
		 function loadDetails(obj,frmName){		  		
		 
		  //alert(frmName);
		  
		  formName=frmName;
		 
		  wthdrwdets=obj;		  
		  loadDets();
		 }
		 
		 function loadDets(){		     
		  //alert(wthdrwdets);
		   if(wthdrwdets!='null'){
		   wdarray=wthdrwdets.split(':');		
		   
		   for(var i=0;i<wdarray.length;i++){
		     wdarrfinal=wdarray[i].split('#');
		     
		     //alert('-------'+wdarrfinal[0]+'-------'+wdarrfinal[1]+'-------'+wdarrfinal[2]+'-------'+wdarrfinal[3]);
		     setWithDrawals(wdarrfinal[0],wdarrfinal[1],wdarrfinal[2],wdarrfinal[3]); 	    
		   }
		   }
		   		   
		 }
		
function dispDetails(){

  setWithDrawals();
  
 }
function validateWithDrawals(reclength)
{   
   
	var exp=/^[0-9]+$/;	
	
	if(document.getElementById("wthdrwlpurpose"+reclength).value==''){	  
	  document.getElementById("wthdrwlpurpose"+reclength).value='-';
	  //return false;
	}	
		
	if(document.getElementById("wthdrwlAmount"+reclength).value==''){
	  alert('Please enter Amount of withdrawal');
	  document.getElementById("wthdrwlAmount"+reclength).focus();
	  return false;
	}	
	 
	if(!exp.test(document.getElementById("wthdrwlAmount"+reclength).value))
	{
		alert("Amount of withdrawal Should be numeric");
		document.getElementById("wthdrwlAmount"+reclength).select();
		return false;
	}		
	
	if(document.getElementById("wthDrwlTrnsdt"+reclength).value==''){
	   alert('Please enter Date of withdrawal');
	   document.getElementById("wthDrwlTrnsdt"+reclength).focus();
	   return false;
	}		

	if(document.getElementById("wthDrwlTrnsdt"+reclength).value!=''){
		if (!convert_date(document.getElementById("wthDrwlTrnsdt"+reclength)))
		{
			document.getElementById("wthDrwlTrnsdt"+reclength).focus();
			return false;
		}
	}  
	return true;
}

var inputs = 0;
 var len=0,deleteID='';
  var lastId=1;

 var finalJSList=new Array();
 var finalDupJSList=new Array();
 var loadIDList=new Array();
 var delList=new Array();
function setWithDrawals(id,purpose,amount,wddate)
{                  
          
    if(id!=''){
    reclength=id;      
    }else{
    reclength++; 
    }
    
    lastId=reclength;
    
   loadIDList.push(reclength);     
   
  //alert('reclength--'+reclength);                            
  
	var table = document.getElementById('wthdrwdetails');	

    
    var tr    = document.createElement('TR');
	var td1   = document.createElement('TD');
	var td2   = document.createElement('TD');
	var td3   = document.createElement('TD');
	var td4   = document.createElement('TD');
	var td5   = document.createElement('TD');
	var inp1  = document.createElement('INPUT');
	var inp2  = document.createElement('INPUT');
    var inp3  = document.createElement('INPUT');
    var inp4  = document.createElement('INPUT');
    var inp5  = document.createElement('INPUT');
    
    inp4.setAttribute("Name", "wthdrwid");
    inp4.setAttribute("id", "wthdrwid"+reclength); 
    inp4.setAttribute("type", "hidden"); 
    inp4.setAttribute("value",reclength); 
    
    inp5.setAttribute("Name", "wthdrwflag");
    inp5.setAttribute("id", "wthdrwflag"+reclength); 
    inp5.setAttribute("type", "hidden"); 
    if(id!=''){
    inp5.setAttribute("value","O");     
    }else{
    inp5.setAttribute("value","N");
    }
    	
	inp1.setAttribute("Name", "wthdrwlpurpose");
	inp1.setAttribute("id", "wthdrwlpurpose"+reclength); 	
	
	if(purpose!=''){
	inp1.setAttribute("value",purpose); 
	inp1.setAttribute("readOnly", "true"); 
	}
	 
	inp2.setAttribute("Name", "wthdrwlAmount");
	inp2.setAttribute("id", "wthdrwlAmount"+reclength); 
	if(amount!=''){
	inp2.setAttribute("value", amount); 
	inp2.setAttribute("readOnly", "true"); 
	}
	
	inp3.setAttribute("Name", "wthDrwlTrnsdt");
	inp3.setAttribute("id", "wthDrwlTrnsdt"+reclength);
	if(wddate!=''){  
	inp3.setAttribute("value", wddate); 
	inp3.setAttribute("readOnly", "true"); 
	}
     

	var deleteIcon     = document.createElement('IMG');
	deleteIcon.setAttribute('src', '<%=basePath%>images/cancelIcon.gif');
		
	deleteIcon.onclick = function(){	
		removeWthDrwls(tr);
	}
		
	var editIcon     = document.createElement('IMG');
	editIcon.setAttribute('src', '<%=basePath%>images/icon-edit.gif');
		
	editIcon.onclick = function(){
	

	var idval=inp4.getAttribute("value");
		editWithDrawals(idval);
	}
		
    var space1=document.createTextNode('  ');
    var space2=document.createTextNode('  ');
    var space3=document.createTextNode('  ');
        
	table.appendChild(tr);
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	tr.appendChild(td4);
	tr.appendChild(td5);
	td1.appendChild(inp1);
	td2.appendChild(inp2);
	td3.appendChild(inp3);
	td3.appendChild(space1);
	
	var calenderIcon     = document.createElement('IMG');
	calenderIcon.setAttribute('src', '<%=basePath%>images/calendar.gif');
	
	
	calenderIcon.onclick = function(){
	
	var idval=inp4.getAttribute("value");
	
		call_calender('forms[0].wthDrwlTrnsdt'+idval);
	}
	td3.appendChild(calenderIcon);
	
	if(amount!=''){
	td3.appendChild(space2);
	td3.appendChild(editIcon);
	}
		
	td3.appendChild(space3);
	td3.appendChild(deleteIcon);
	td4.appendChild(inp4);
	td5.appendChild(inp5);
	
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
 		
 		if(deleteID==''){
 			deleteID=deletedNodeId;
 		}else{
 			deleteID=deleteID+','+deletedNodeId;
 		}
 		
 	}
	tr.parentNode.removeChild(tr);
	
}
function numOrdA(a, b){ return (a-b); }


function call_calender(dobValue){     	
 
  	  show_calendar(dobValue);
 } 
 
function withDrawalDet(){  
   var te='';
	var id=0;
	var flag="N";
	var exp=/^[0-9]+$/;		
	
	
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
				
				//alert('-----finaldltdID----'+finaldltdID);
								
				if(finaldltdID!=''){    
				    if(validateWithDrawals(finaldltdID)!=true){
						return false;  
				    }
			    }
    												
		      	//te+=document.getElementById("wthdrwlpurpose"+finaldltdID).value+"#"+document.getElementById("wthdrwlAmount"+finaldltdID).value+"#"+document.getElementById("wthDrwlTrnsdt"+finaldltdID).value+"#"+document.getElementById("wthdrwid"+finaldltdID).value+"#"+document.getElementById("wthdrwflag"+finaldltdID).value+"#"+deleteID+":";	
		      	
		      	//alert(formName);
		      	
		      	if(formName=='new'){
		      	te+=document.getElementById("wthdrwid"+finaldltdID).value+"#"+document.getElementById("wthdrwlpurpose"+finaldltdID).value+"#"+document.getElementById("wthdrwlAmount"+finaldltdID).value+"#"+document.getElementById("wthDrwlTrnsdt"+finaldltdID).value+"#"+document.getElementById("wthdrwflag"+finaldltdID).value+"#"+deleteID+":";	
		      	}else{
		      	te+=document.getElementById("wthdrwlpurpose"+finaldltdID).value+"#"+document.getElementById("wthdrwlAmount"+finaldltdID).value+"#"+document.getElementById("wthDrwlTrnsdt"+finaldltdID).value+"#"+document.getElementById("wthdrwid"+finaldltdID).value+"#"+document.getElementById("wthdrwflag"+finaldltdID).value+"#"+deleteID+":";	
		      	
		      	}
			 	
			 				
    }	
	
	
	if(te=='')
	  te=deleteID;
	  
	  //alert("---te---"+te);
		
	document.forms[0].ExpList.options[document.forms[0].ExpList.options.length]=new Option('x',te);
	document.forms[0].ExpList.options[document.forms[0].ExpList.options.length-1].selected=true;
	 
   	window.opener.getWithDrawalDetails(document.getElementById('ExpList').value);	
  	window.close();
  }
  
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
  
 <%
    String status="N";   
 %>
function editWithDrawals(id){
var i=id-1;


document.getElementById("wthdrwlpurpose"+id).readOnly=false;
document.getElementById("wthdrwlAmount"+id).readOnly=false;
document.getElementById("wthDrwlTrnsdt"+id).readOnly=false;

document.getElementById("wthdrwflag"+id).value='M';


}

</script>
	</head>

	<body onload="loadDetails('<%=request.getAttribute("wthdrwStr")%>','<%=request.getAttribute("formName")%>');">
		<form>
			<table align="center" border="0">
				<tbody id="wthdrwdetails">
					<tr>
						<td class="tableTextBold" nowrap="nowrap">
							Purpose of withdrawal &nbsp;&nbsp; 
						</td>
						<td class="tableTextBold" nowrap="nowrap">
							Amount of withdrawal &nbsp;&nbsp;
						</td>
						<td class="tableTextBold" nowrap="nowrap">
							Date of Withdrawal &nbsp;&nbsp;
						</td>
						<td class="label" nowrap="nowrap">
							<img alt="" src="<%=basePath%>images/addIcon.gif" onclick="return setWithDrawals('','','','')" />
							&nbsp;&nbsp;
						</td>

					</tr>
					<TR>
						<TD id="edetail" colspan=20></TD>
					</TR>


					<!-- hidden list box-->
					<tr style="display:none">
						<td>
							<select name="ExpList" multiple></select>
						</td>
					</tr>
					<!-- end of list box -->

					<tr>
						<td>

						</td>
					</tr>
				</tbody>

				<tr>
					<td colspan="4" align="center">
						<input type="button" value="Submit" onclick="javascript:withDrawalDet()" />
					</td>
				</tr>


			</table>
		</form>
	</body>
</html>
