
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.bean.advances.AdvanceBasicBean"%>
<%@ page isErrorPage="true"%>


<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			
  AdvanceBasicBean basicBean=new AdvanceBasicBean();

  
  int size = 0;
			
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
		var estri="";
		
function dispDetails(){
//alert('---dispDetails---');
  setWithDrawals();
  
 }
function validateWithDrawals(reclength)
{
   
	var exp=/^[0-9]+$/;	
	 
	
	 
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

	 
	var dt=document.getElementById("wthDrwlTrnsdt"+reclength).value;
	var dtstr=dt.split("/");
	
	if(document.getElementById("wthDrwlTrnsdt"+reclength).value!=''){
 		 
		if (!validate_date(dtstr[0],dtstr[1],dtstr[2]))
		{  
		 
		  alert("Please enter withdrawal date in the format 01/01/2010");
		   
	  	 document.getElementById("wthDrwlTrnsdt"+reclength).focus();
			return false;
		}
		 
		
	} 
	 
	return true;
	
}
var inputs = 0;
 var len=0,deleteID='',reclength=0;
 var finalJSList=new Array();
  var finalDupJSList=new Array();
  var delList=new Array();
function setWithDrawals()
{   
	
	//alert('-----reclength----'+reclength);
	
	if(deleteID!=''){
	 delList=deleteID.split(',');  
	}
	
	//alert('-------delList.length-------'+delList.length);
	

    if(reclength>=1){
	    if(validateWithDrawals(reclength)!=true){
			return false;
	    }
    }
   
    reclength++;           
   
	var table = document.getElementById('wthdrwdetails');	
    
    var tr    = document.createElement('TR');
	var td1   = document.createElement('TD');
	var td2   = document.createElement('TD');
	var td3   = document.createElement('TD');
	var td4   = document.createElement('TD');
	var inp1  = document.createElement('INPUT');
	var inp2  = document.createElement('INPUT');
    var inp3  = document.createElement('INPUT');
    
	
	inp1.setAttribute("Name", "wthdrwlpurpose");
	inp1.setAttribute("id", "wthdrwlpurpose"+reclength); 
	 
	inp2.setAttribute("Name", "wthdrwlAmount");
	inp2.setAttribute("id", "wthdrwlAmount"+reclength);  
	
	inp3.setAttribute("Name", "wthDrwlTrnsdt");
	inp3.setAttribute("id", "wthDrwlTrnsdt"+reclength);  
     

	var deleteIcon     = document.createElement('IMG');
	deleteIcon.setAttribute('src', '<%=basePath%>images/cancelIcon.gif');
		
	deleteIcon.onclick = function(){
		removeWthDrwls(tr);
	}
	
    var space1=document.createTextNode('  ');
    var space2=document.createTextNode('  ');
    
	table.appendChild(tr);
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	td1.appendChild(inp1);
	td2.appendChild(inp2);
	td3.appendChild(inp3);
	//td3.appendChild(space1);
	
	/*var calenderIcon = document.createElement('IMG');
	calenderIcon.setAttribute('src', '<%=basePath%>images/calendar.gif');
	
	
	calenderIcon.onclick = function(){
		call_calender('forms[0].wthDrwlTrnsdt'+reclength);
	}*/
	//td3.appendChild(calenderIcon);
	td3.appendChild(space2);
	td3.appendChild(deleteIcon);
	

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
	
	
	if(reclength>=1){
   //if(validateWithDrawals(reclength)!=true)
	//return false;
    }else{
     alert('Please enter With drawl details');
      return false;
    }
    
    
     
	    var id;
		var finaldltdID;
		var strlen=reclength;		
		var deletedList=deleteID.split(',');  
		var deletedSortedList=new Array();
		deletedSortedList=deletedList.sort(numOrdA);
		//alert('deletedSortedList======'+deletedSortedList);
		var afterDeletedList=createList(deletedSortedList);	
		
		
		//alert('afterDeletedList'+afterDeletedList.length);
		
		if(afterDeletedList.length!=0){
		
		for(var i=0;i<afterDeletedList.length;i++){	 	 
	
				finaldltdID=afterDeletedList[i];
			
				if(finaldltdID>=1){
				    if(validateWithDrawals(finaldltdID)!=true){
						return false;
				    }
				   
			    }
			  
			    id++;
    	
		       	te+=id+"#"+document.getElementById("wthdrwlpurpose"+finaldltdID).value+"#"+document.getElementById("wthdrwlAmount"+finaldltdID).value+"#"+document.getElementById("wthDrwlTrnsdt"+finaldltdID).value+":";	
				
    }
    }else{
      alert('Please enter Give Previous Withdrawal Details');
      return false;
      
    }	
	//alert("---te---"+te);
		
	document.forms[0].ExpList.options[document.forms[0].ExpList.options.length]=new Option('x',te);
	document.forms[0].ExpList.options[document.forms[0].ExpList.options.length-1].selected=true;
	 
   	window.opener.getWithDrawalDetails(document.getElementById('ExpList').value);	
  	window.close();
  }
function createList(sortedDeletList){
	var sortedltdid,lastdeletedId;
	//alert('createList===Before deleted list'+sortedDeletList);
	for(var j=1;j<=reclength;j++){
	  	if(sortedDeletList.length!=0){
	  		for(var sndlst=0;sndlst<sortedDeletList.length;sndlst++){
			sortedltdid=sortedDeletList[sndlst];
			lastdeletedId=sortedltdid;
			if(sortedltdid==j){
				sortedDeletList.splice(sndlst,1);
				//alert('createList===after deleted list'+sortedDeletList);
				break;
			}else{
				finalJSList.push(j);
			}
		}
	  	}else{
	  		//alert('last deleted Id'+lastdeletedId);
	  		if(lastdeletedId!=reclength){
	  			finalJSList.push(j);
	  		}
	  		
	  	}
		
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
function deleteWthDrawDetils(rowcolumn,rowid)
{
  
  var rowcolumn=rowcolumn;
 
  //document.forms[0].deletionStatus[rowid].value="D"; 
  
  var deleterow = document.getElementById(rowcolumn);
  document.getElementById(rowcolumn).style.display = 'none'; 
  	    
} 
	 </script>
	</head>

	<body>
		<form>
			<table align="center" border="0">
				<tbody id="wthdrwdetails">
					<tr>
						<td class="label" nowrap="nowrap">
							Purpose of withdrawal &nbsp;&nbsp;
						</td>
						<td class="label" nowrap="nowrap">
							Amount of withdrawal &nbsp;&nbsp;
						</td>
						<td class="label" nowrap="nowrap">
							Date of Withdrawal <font color="red"> (Format 01/01/2010)</font>&nbsp;&nbsp;
						</td>
						<td class="label" nowrap="nowrap">
							<img alt="" src="<%=basePath%>images/addIcon.gif" onclick="return setWithDrawals()" />
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
