<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.ScreenUtilities"%>

<%@ page import="com.epis.bean.rpfc.EmpMasterBean"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String arrearInfoString1 = "";
            String regionFlag = "false";
            				
	HashMap hashmap=new HashMap();
  	String region="",message="";
  	Iterator regionIterator=null;
  	boolean msg=false;
  	if(request.getAttribute("mesg")!=null){
  	msg=true;
  	message=(String)request.getAttribute("mesg");
  	}
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap=(HashMap)request.getAttribute("regionHashmap");
  	Set keys = hashmap.keySet();
	regionIterator = keys.iterator();
	}
%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
	<head>
		<title>AAI</title>

		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime1.js"></SCRIPT>
		<script type="text/javascript"> 
			var ex=/^[0-9.-]+$/;
			var confirmAirport,validateflag,family;
	  	
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
	function dispOff()
   { 
     var totalshare=document.getElementsByName("totalshare");
      var nomineeName=document.getElementsByName("Nname");
   		
   		  var k;
   		  var finalShare=0;
   	
	if(window.document.forms[0].equalShare.checked)
	  {   
	  if(totalshare.length==1){
	  var totalShare =100/((totalshare.length));
		// alert("totalShare "+totalShare);
		  totalshare[0].value=totalShare;
		  var finalShare=finalShare+parseInt(totalshare[0].value);
		 
		 // alert(finalShare);
		   totalshare[0].readOnly=true;
	  }
   		 for(k=0;k<(totalshare.length)-1;k++){
   		 var totalShare =100/((totalshare.length)-1);
		// alert("totalShare "+totalShare);
		  totalshare[k].value=totalShare;
		  var finalShare=finalShare+parseInt(totalshare[k].value);
		 
		 // alert(finalShare);
		   totalshare[k].readOnly=true;
		//document.getElementsByName("totalshare").value=totalShare;
	}
	}
	else
	{
	for(k=0;k<(totalshare.length)-1;k++){
	
	//alert("inside else ");
	//window.document.forms[0].totalshare.disabled=false;
	 totalshare[k].value=="";
	 var l=document.getElementsByName("totalshare");
	 for(i=0;i<l.length;i++){
  	 l[i].value="";
  	  totalshare[k].readOnly=false;
  	 }
	
	}
	}
  }
 function ValidateEMail(emailStr) 
{
	var emailPat=/^(.+)@(.+)$/
	var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]"
	var validChars="\[^\\s" + specialChars + "\]"
	var quotedUser="(\"[^\"]*\")"
	var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/
	var atom=validChars + '+'
	var word="(" + atom + "|" + quotedUser + ")"
	var userPat=new RegExp("^" + word + "(\\." + word + ")*$")
	var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$")
	var matchArray=emailStr.match(emailPat)
	if (matchArray==null) 
	{
		alert("Email address seems incorrect (check @ and .'s)")
		return false
	}
	var user=matchArray[1]
	var domain=matchArray[2]

	if (user.match(userPat)==null) 
	{
		alert("The username doesn't seem to be valid.")
		return false
	}

	var IPArray=domain.match(ipDomainPat)
	if (IPArray!=null) 
	{
		for (var i=1;i<=4;i++) 
		{
			if (IPArray[i]>255) 
			{
				alert("Destination IP address is invalid!")
				return false
			}
		}
	    return true
	}

	var domainArray=domain.match(domainPat)
	if (domainArray==null) 
	{
		alert("The domain name doesn't seem to be valid.")
		return false
	}

	var atomPat=new RegExp(atom,"g")
	var domArr=domain.match(atomPat)
	var len=domArr.length
	if (domArr[domArr.length-1].length<2 || 
		domArr[domArr.length-1].length>4) 
		{
		   alert("The address must end in a three-letter domain, or two letter country.")
		   return false
		}

	if (len<2) 
	{
		var errStr="This address is missing a hostname!"
		alert(errStr)
		return false
	}
	return true;
}

	var xmlHttp;

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
	
function getDesegnation(obj)
{	
  //  alert(obj);
	createXMLHttpRequest();	
	 var selectedIndex=document.forms[0].emplevel.options.selectedIndex;
	 var empLevelseleted=document.forms[0].emplevel[selectedIndex].text;
	// alert("selected level "+empLevelseleted);
	 var temp = new Array();
	 temp = empLevelseleted.split(',');
	 
	 var desegnation = temp[1];
	 
	 document.forms[0].desegnation.value=desegnation;
	
}
		function swap() { 
			if(document.getElementById('mySelect').value == 'Other'){ 
			document.getElementById('mySelect').style.display = 'block'; 
			document.getElementById('myText').style.display = 'block'; 
			document.getElementById('myText').focus(); 
			
			} else{
			document.getElementById('mySelect').style.display = 'block'; 
			document.getElementById('myText').style.display = 'none'; 
			}
			} 
			function swapback() { 
			document.getElementById('mySelect').selectedIndex = 0; 
			document.getElementById('mySelect').style.display = 'block'; 
			document.getElementById('myText').style.display = 'none'; 
			} 
    
	 function retrimentDate(){
   	 
	     var formDate=document.forms[0].dateofbirth.value;
	      if(formDate.lastIndexOf("-")!=-1 || formDate.lastIndexOf("/")!=-1){
			var year=formDate.substring(formDate.lastIndexOf("/")+1,formDate.length);
			if(year.length==4){
				var dateOfBirth=Number(year)+Number(60);
				dateOfBirth=formDate.replace(year,dateOfBirth);
				document.forms[0].dateOfAnnuation.value=dateOfBirth;
			}
		
		}
		
	 }
	 
	 function hide(){
			 
			 divNomineeDetails.style.display="none";	 
			  if(document.forms[0].form1.value=="Yes")
   		     {
   		     divNomineeDetails.style.display="block";
   		     
   		   //   document.forms[0].nomineeflag.value="true";
   		     }
			}
			
			function show(){
		  //alert("hit"+document.forms[0].form1.value);
			 if(document.forms[0].form1.value=="Yes")
   		     {
   		     divNomineeDetails.style.display="block";
   		     
   		   //   document.forms[0].nomineeflag.value="true";
   		     }
   		      else 
   		     {
   		      divNomineeDetails.style.display="none";
   		       
   		
   		     }
			}
			 
	 function showRegion(){
			
			 if(document.forms[0].transferStatus.checked)
   		     {
   		    
   		     divRegion.style.display="block";
   		     }else{
   		       divRegion.style.display="none";
   		     }
   		     
			}
	
	  
	
	function limitlength(obj, length){
	var maxlength=length
	if (obj.value.length>maxlength)
	obj.value=obj.value.substring(0, maxlength)
	}
	
	 var count =0;
  	 
  	 function call_calender(dobValue){
  	  show_calendar(dobValue);
  	 }
     var count1 =0;
   
	function charsCapsNumOnly()
	{
	
	if(event.keyCode >=97 || event.keyCode<=122)    
      event.keyCode = event.keyCode - 32;
    if(((event.keyCode >=65 && event.keyCode<=90))||(event.keyCode==32)||
                                (event.keyCode==46)||(event.keyCode==44))  
      event.keyCode = event.keyCode;
    else
     event.keyCode=0;
	}
	//for changing Nominee Details Style
	var count1 =0,slno=0;
    var len=0,deleteID='',reclength=0,fdeleteID='';
    var nomineearray = new Array();
    var familyarrfinal=new Array();
     var familyarray = new Array();
    var nomineearrfinal=new Array();
    var nomineeInfo='',familyInfo='';
    var formName='';
    	 
	function loadDetails(obj,obj1){		  		
		 
		// alert('obj is -------'+obj);
		  //alert('frmName is -----'+frmName);
		  
       
		  nomineeInfo=obj;
		  familyInfo=obj1;  
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
		    if(familyInfo!='null'){
		   familyarray=familyInfo.split(':');		
		   
		   for(var i=0;i< familyarray.length;i++){
		      familyarrfinal= familyarray[i].split('#');
		      
		   //alert( familyarrfinal[0]+'-'+familyarrfinal[1]+'--'+familyarrfinal[2]+'--'+familyarrfinal[3]);
		     callFamily(familyarrfinal[0],familyarrfinal[1],familyarrfinal[2],familyarrfinal[3]); 	    
		   }
		   
		   
		   }
		   		   
  }
		 
	
	   var loadIDList=new Array();
     
     var recortCountList=new Array();
     
 function callNominee(id,nomineeName,nomineeDOB,nomineeAddress,nomineeRelation,gardianName,gardianAddress,totalShare){     
        
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
	
	
	inp4.setAttribute("Name", "nomineerelation");
	inp4.setAttribute("id", "nomineerelation"+reclength);
	inp4.setAttribute("maxlength", "9"); 
	inp4.setAttribute("class","TextField");
	 
	
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
	
	//alert(nomineeRelation);
	
	
	 
	inp4.setAttribute("value",nomineeRelation); 
	
	
	inp8.setAttribute("Name", "serialNo");
    inp8.setAttribute("id", "serialNo"+reclength); 
    inp8.setAttribute("type", "hidden"); 
    inp8.setAttribute("value",reclength); 
    inp8.setAttribute("class","TextField");
   	
	inp1.setAttribute("Name", "nomineename");
	inp1.setAttribute("id", "nomineename"+reclength); 
	inp1.setAttribute("size", "16"); 
	inp1.setAttribute("maxlength", "50"); 
	inp1.setAttribute("class","TextField");	
		 
	if(nomineeName!=''){	 
	inp1.setAttribute("value",nomineeName); 
	 
	//inp1.setAttribute("readOnly", "true"); 
	}	
	inp1.onblur=function(){getTotShare();};
	
		 
	inp2.setAttribute("Name", "nomineeaddress");
	inp2.setAttribute("id", "nomineeaddress"+reclength);  
	inp2.setAttribute("size", "16"); 
	inp2.setAttribute("maxlength", "150"); 
	inp2.setAttribute("class","TextField");	
	inp2.setAttribute("type", "text"); 
 
	if(nomineeAddress!=''){
	inp2.setAttribute("value",nomineeAddress); 
	//inp2.setAttribute("readOnly", "true"); 
	}
	
	inp3.setAttribute("Name", "nomineeDOB");
	inp3.setAttribute("id", "nomineeDOB"+reclength);  
	inp3.setAttribute("size", "11"); 
	inp3.setAttribute("maxlength", "11"); 
	if(nomineeDOB!=''){
	inp3.setAttribute("value",nomineeDOB); 
	inp3.setAttribute("class","TextField");
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
	inp5.setAttribute("class","TextField");
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
	inp7.setAttribute("size", "3"); 
	inp7.setAttribute("maxlength", "3"); 
	inp7.setAttribute("class","TextField");
	if(totalShare!=''){
	inp7.setAttribute("value",totalShare); 
	//inp7.setAttribute("onkeypress","numsDotOnly()");
	 

	}
	
	var deleteIcon     = document.createElement('IMG');
	deleteIcon.setAttribute('src','<%=basePath%>images/cancelIcon.gif');
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
var lastrecord;	
function getTotShare(){
//...........
var finaldltdID;
		var deletedList=new Array();
		var strlen=reclength;			
		if(deleteID!=''){
			deletedList=deleteID.split(','); 
		}			
		var deletedSortedList=new Array();				
		deletedSortedList=deletedList.sort(numOrdA);	
		var afterDeletedList=createList(deletedSortedList);		 
	var len=afterDeletedList.length;	 
	 
	 for(i=0;i<afterDeletedList.length;i++){	
	lastrecord=afterDeletedList[len-1];
	} 
//...........
var listsize=0;

	 var n=document.getElementById("totalshare");
	
	if(document.forms[0].equalshare.checked){		
		  listsize=recortCountList.length;
		  
	//alert('----listsize---'+listsize);
	if(listsize>=2){
	
	      var totvalue=100/listsize;	
	     // alert(totvalue);
	      
	      for(var k=0;k<recortCountList.length;k++){	  
	   		//alert('tot share val is'+document.getElementById("totalshare"+recortCountList[k]).value);
	   		document.getElementById("totalshare"+recortCountList[k]).value=totvalue;
	   		  //document.getElementById("totalshare"+recortCountList[k]).readOnly=true;
		  }	      
	
	}else{	 
	document.getElementById("totalshare"+lastrecord).value="100";
	}	 	   
	 		
 	}
 	
 	 
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
      
      
      
      var te='',te1='',familyte1='';
       
      function tot(totalshareval){
      
   		if(totalshareval<100){
   		 
   		alert('Total Share not less 100');
	document.getElementById("totalshare"+reclength).focus();	
	 
	return false; 
   		} 
   		 
   		if(parseInt(totalshareval)>100){
   		 
   		alert('Total Share not Greater 100');
	document.getElementById("totalshare"+reclength).focus();	
	 
	return false; 
   		} 
   		return true;
   		}
     
      
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
	
	//alert('here');
	//var dateArr;
	//dateArr= afterDeletedList.split(',');
	var len=afterDeletedList.length;
	 
	//alert('****'+afterDeletedList.charAt(afterDeletedList.length-1));
	 for(i=0;i<afterDeletedList.length;i++){
	 
	
	lastrecord=afterDeletedList[len-1];
	} 
	 
//alert('----lastrecord--'+lastrecord);

		
		for(var i=0;i<afterDeletedList.length;i++){	 		           
		        
				finaldltdID=afterDeletedList[i];				
					 					
				if(finaldltdID!=''){	
							 
				 var flag=validateNomineeDetails(finaldltdID);	
				 validateflag=flag;
				
			     if(!flag){
					    te='';
						return false;  
				    }else {
		      		 
			      		te+=document.getElementById("serialNo"+finaldltdID).value+"#"+document.getElementById("nomineename"+finaldltdID).value+"#"+document.getElementById("nomineeaddress"+finaldltdID).value+"#"+document.getElementById("nomineeDOB"+finaldltdID).value+"#"+document.getElementById("nomineerelation"+finaldltdID).value+"#"+document.getElementById("gardianname"+finaldltdID).value+"#"+document.getElementById("gardianaddress"+finaldltdID).value+"#"+document.getElementById("totalshare"+finaldltdID).value+"#"+deleteID+":";	
			      		//alert(te);
		      		}
		      		 
			    }		   		
    		
         }	
          
	
	if(te=='')
	
	  te=deleteID;
	
		return true;
  }
  var remainingshareval=100;
	var target=100;
	var shareval=0;
	 var totalshareval=0;
  function validateNomineeDetails(reclength)
{
	 
		if(document.forms[0].equalshare.checked==false){
		
			if(document.getElementById("nomineename"+reclength).value==''){
			  alert('Please Enter Nominee Name');
			  document.getElementById("nomineename"+reclength).focus();
			  return false;	  
			}
			 
			
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
		 
		if(document.forms[0].equalshare.checked==true){
		
			if(document.getElementById("nomineename"+reclength).value==''){
			  alert('Please Enter Nominee Name');
			  document.getElementById("nomineename"+reclength).focus();
			  return false;	  
			}
			
			 
			}	
	 
	
	 
	
	if(confirmAirport==false || validateflag==false || family==false){
	totalshareval=0;
	family=true;
	confirmAirport=true;
	}
	// alert('---confirmAirport--'+confirmAirport+'-----validateflag-----'+validateflag);	
	
	
	 
	if(document.getElementById("totalshare"+reclength).value!=''){
	
	shareval=parseFloat(document.getElementById("totalshare"+reclength).value);
	totalshareval=totalshareval+shareval;
	 target=target-shareval;
	//alert('shareval----'+shareval+' ---target-----'+target); 
	 
		remainingshareval=target;
	}
	  
	 
					//alert('remainingshareval ----------  '+remainingshareval);
					//alert('totalshareval ----------  '+totalshareval);
	 
	 
	 				if (!convert_date(document.getElementById("nomineeDOB"+reclength))){
							document.getElementById("nomineeDOB"+reclength).focus();
						return false;
						}	
					var nomineedob=document.getElementById("nomineeDOB"+reclength).value;
					var gardianName=document.getElementById("gardianname"+reclength);
			 		if(nomineedob!=''){
			 		
			 		
			 		 var currentDate1=getCurrentDate();
				  
					 var result=compareDates(currentDate1, nomineedob);
				 
				 	if(result=="smaller"){
					 alert('Date Of Birth should not exceed  to current date');
					 document.getElementById("nomineeDOB"+reclength).focus();
					 return false;				 
					 }
			 		 
					var month = getJsDate1(nomineedob.toLowerCase(),false);
			   		
   		   				var birthmotnth=month.substring(0,2);
					    var birthday=month.substring(3,5);
				 	    var birthyear=month.substring(month.lastIndexOf(",")+1,month.length);
   					    var birthDate = new Date(birthyear, birthmotnth, birthday); 
				   	
				      var currDate = new Date();
			   		  var monthnumber = currDate.getMonth();
			 		  var monthday    = currDate.getDate();
			          var year        = currDate.getYear();
			    	  var currentDate = new Date(year, monthnumber, monthday); 				    
	
        			 var daysDiff=(currentDate-birthDate)/86400000;
			  		 var years=Math.round(daysDiff/365);
			  		
					if(years<18 && gardianName.value=="") {
          				  gardianName.focus();
            			  alert("Please Enter Guardian Name");
             			 return false;
          	 		}
          	 		
          	 		if(years<18 && gardianName.value=="") {
          				 gardianName.focus();
            			  alert("Please Enter Guardian Address");
             			 return false;
          	 		}
	}
	
return true;
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
	
} 	 
	
	//.........................
	//for changing  Family Details Style

		 
	   var loadIDListFamily=new Array();
     
     var familyRecortCountList=new Array();
     var familyreclength=0;
     var lastId1;
     var familyDeleteID='';
 function callFamily(id1,familyName,familyDOB,familyRelation){     
        
 
    if(id1!=''){
    familyreclength=id1;      
    }else{
    familyreclength++; 
    }
    
    lastId1=familyreclength;
    
  loadIDListFamily.push(familyreclength);    
   familyRecortCountList.push(familyreclength); 
    
    var table = document.getElementById('familytable');	
    var tr    = document.createElement('TR');
	var td1   = document.createElement('TD');
	var td2   = document.createElement('TD');
	var td3   = document.createElement('TD');
	var td4   = document.createElement('TD');
	 
	var inp1  = document.createElement('INPUT');
	var inp2  = document.createElement('INPUT');
	var inp3  = document.createElement('SELECT');
	var inp4  = document.createElement('INPUT');
	 
	
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
	
	
	inp3.setAttribute("Name", "familyrelation");
	inp3.setAttribute("id", "familyrelation"+familyreclength);
	inp3.setAttribute("maxlength", "9"); 
	inp3.setAttribute("class","TextField");
	 
	
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
	
	
	
	
	inp3.appendChild(opt);
	inp3.appendChild(opt1);
	inp3.appendChild(opt2);
	inp3.appendChild(opt3);
	inp3.appendChild(opt4);
	inp3.appendChild(opt5);
	inp3.appendChild(opt6);
	inp3.appendChild(opt7);
	inp3.appendChild(opt8);
	inp3.appendChild(opt9);
	
	//alert(nomineeRelation);
	
	
	 
	inp3.setAttribute("value",familyRelation); 
	
	
	inp4.setAttribute("Name", "familySerialNo");
    inp4.setAttribute("id", "familySerialNo"+familyreclength); 
    inp4.setAttribute("type", "hidden"); 
    inp4.setAttribute("value",familyreclength); 
    inp4.setAttribute("class","TextField");
   	
	inp1.setAttribute("Name", "familyname");
	inp1.setAttribute("id", "familyname"+familyreclength); 
	inp1.setAttribute("size", "16"); 
	inp1.setAttribute("maxlength", "50"); 
	inp1.setAttribute("class","TextField");	
		 
	if(familyName!=''){	 
	inp1.setAttribute("value",familyName); 
	 
	 
	}	
	  
	
	inp2.setAttribute("Name", "familyDOB");
	inp2.setAttribute("id", "familyDOB"+familyreclength);  
	inp2.setAttribute("size", "11"); 
	inp2.setAttribute("maxlength", "11"); 
	if(familyDOB!=''){
	inp2.setAttribute("value",familyDOB); 
	inp2.setAttribute("class","TextField");
	//inp2.setAttribute("readOnly", "true"); 
	}
	
	var calenderIcon = document.createElement('IMG');
	calenderIcon.setAttribute('src', '<%=basePath%>images/calendar.gif');
	
	
	calenderIcon.onclick = function(){		
		
		var idval=inp4.getAttribute("value");
	 
		call_calender('forms[0].familyDOB'+idval);
	}	
	
	
	 
	  
	var deleteIcon     = document.createElement('IMG');
	deleteIcon.setAttribute('src','<%=basePath%>images/cancelIcon.gif');
	deleteIcon.onclick = function(){
		removeFamilyDts(tr);
		 
	}

	
    var space1=document.createTextNode('  ');
    var space2=document.createTextNode('  ');
    
	table.appendChild(tr);
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	tr.appendChild(td4);
	 
	
	td1.appendChild(inp1);	 
	td2.appendChild(inp2);
	td2.appendChild(space1);
	td2.appendChild(calenderIcon);
	td2.appendChild(space1);	 
	td3.appendChild(inp3);
	td4.appendChild(inp4);	 
	td4.appendChild(deleteIcon);
	          
  } 
	
	
function familyDet(){  
  
	var id=0;
	var flag="N";
	var exp=/^[0-9]+$/;		
	
		 familyte1='';
		var finaldltdID;
		var deletedList=new Array();
		var strlen=reclength;	
		
		
		//alert('-----familyDeleteID------'+familyDeleteID);
		
		if(familyDeleteID!=''){
			deletedList=familyDeleteID.split(','); 
		}		
				
		var deletedSortedList=new Array();		
		
		deletedSortedList=deletedList.sort(numOrdA);
		
		//alert('deletedSortedList======'+deletedSortedList);
		
		var afterDeletedList=createList1(deletedSortedList);	
	//alert('afterDeletedList'+afterDeletedList);
	
	  
		for(var i=0;i<afterDeletedList.length;i++){	 		           
		        
				finaldltdID=afterDeletedList[i];				
					 					
				if(finaldltdID!=''){	
							 
				 var flag=validateFamilyDetails(finaldltdID);
				 family=flag;					 
			     if(!flag){
					    familyte1='';
						return false;  
				    }else {		      		 
			      		familyte1+=document.getElementById("familySerialNo"+finaldltdID).value+"#"+document.getElementById("familyname"+finaldltdID).value+"#"+document.getElementById("familyDOB"+finaldltdID).value+"#"+document.getElementById("familyrelation"+finaldltdID).value+"#"+familyDeleteID+":";	
			      		//alert(familyte1);
			      		
		      		}
		      		 
			    }		   		
    		
         }	
          
	
	if(familyte1=='')
	
	  familyte1=familyDeleteID;
	
		return true;
  }
  
  function validateFamilyDetails(reclength)
{
	 
		 
		
			if(document.getElementById("familyname"+reclength).value==''){
			  alert('Please Enter Family Member Name');
			  document.getElementById("familyname"+reclength).focus();
			  return false;	  
		 }
			 
		   if(document.getElementById("familyrelation"+reclength).selectedIndex<1)
   		 {
   		  alert("Please Enter Relation With Family Member");
   		 document.getElementById("familyrelation"+reclength).focus();
   		  return false;
   		  } 
   		  
	 				if(document.getElementById("familyDOB"+reclength).value!=''){
	 				if (!convert_date(document.getElementById("familyDOB"+reclength))){
							document.getElementById("familyDOB"+reclength).focus();
						return false;
						}
						}	
					var familydob=document.getElementById("familyDOB"+reclength).value;
					 
			 		if(familydob!=''){
			 		
			 		
			 		 var currentDate1=getCurrentDate();
				  
					 var result=compareDates(currentDate1, familydob);
				 
				 	if(result=="smaller"){
					 alert('Date Of Birth should not exceed  to current date');
					 document.getElementById("familyDOB"+reclength).focus();
					 return false;				 
					 }
			 		 
				 
	}
	
return true;
}

function createList1(sortedDeletList){  
	var sortedltdid,lastdeletedId;
	//alert('createList===Before deleted list'+sortedDeletList);
	var loadListID;
	//alert('createList==sortedDeletList==='+sortedDeletList);
	finalJSList=new Array();
	for(var j=0;j<loadIDListFamily.length;j++){
	
		loadListID=loadIDListFamily[j];
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
	  		if(lastdeletedId!=familyreclength){
	  			finalJSList.push(loadListID);
	  			
	  		}else if(sortedDeletList.length==0){
	  			finalJSList.push(loadListID);
	  			
	  		}
	  		
	  	}
	  	
	  	//alert('-----finalJSList-----'+finalJSList);
		
	}
	return removeDuplicateElement(finalJSList);
}


function findIndexDigits1(str){
	var index1=0;
	for(var i=0;i<str.length;i++){
		if(str.charAt(i)>='0' && str.charAt(i)<='9'){
			
			index1=i;
			break;
		}
	}
	return index1;
}
function removeFamilyDts(tr){
  	
 	if(tr.hasChildNodes()==true){
 		var oChildNodes = tr.childNodes; 		 
 		var deleteTagID,deletedNodeId,childNodesLength;
 		childNodesLength=oChildNodes.length; 		 
 		deleteTagID=oChildNodes[0].lastChild.id;
 		deletedNodeId=deleteTagID.substring(findIndexDigits1(deleteTagID),deleteTagID.length);
 		
		//alert(recortCountList);
		//alert('deleted id is ---'+deletedNodeId);
			
		var temp=new Array();
		
			for(var i=0;i<familyRecortCountList.length;i++)
			{
				if(familyRecortCountList[i]!=deletedNodeId)
					temp[temp.length]=familyRecortCountList[i];	
			}
			
			
			familyRecortCountList=temp;			
		//alert('final list is'+familyRecortCountList);
				
 		
 		if(familyDeleteID==''){
 			familyDeleteID=deletedNodeId;
 		}else{
 			familyDeleteID=familyDeleteID+','+deletedNodeId;
 		}
 		
 	}
	tr.parentNode.removeChild(tr);
	
} 	 	 
  
//..........................................	 
	 function testSS(index,totalData){
	 
	 	 if(document.forms[0].form1.value=="Yes"){
	 if(!withDrawalDet()){
			return false;
		}
	 }
	 	
	 	
		 if(document.forms[0].form1.value=="Yes"){
		 var ff=tot(totalshareval);
   		// alert('----ff-------'+ff);				    
						if(!ff){
						  te1='';	
						 // target=100;	
						 totalshareval=0;				  
						return false; 
							} 
							
		  
   	}
  
	 
	 if(!familyDet()){
			return false;
		}
   		
   		if(document.forms[0].cpfacnoNew.value=="")
   		 {
   		  alert("Please Enter CpfAccount Number");
   		  document.forms[0].cpfacnoNew.focus();
   		  return false;
   		  }
   		/* if(document.forms[0].airPortCode.value=="")
   		 {
   		  alert("Please Enter Airportcode");
   		  document.forms[0].airPortCode.focus();
   		  return false;
   		  }*/
   		 if(document.forms[0].empName.value=="")
   		 {
   		  alert("Please Enter Employee Name");
   		   document.forms[0].empName.focus();
   		  return false;
   		  }
   		  if(!ValidateName(document.forms[0].empName.value))
   		 {
   		  alert("Numeric/Invalid characters are not allowed");
   		   document.forms[0].empName.focus();
   		  return false;
   		  }
   		  
   		  if(document.forms[0].desegnation.value=="")
   		 {
   		  alert("Please Enter Designation");
   		  document.forms[0].desegnation.focus();
   		  return false;
   		  }
   		  
   	 var selectedIndex=document.forms[0].emplevel.options.selectedIndex;
	 var empLevelseleted=document.forms[0].emplevel[selectedIndex].text;
	// alert("selected level "+empLevelseleted);
	 var temp = new Array();
	 temp = empLevelseleted.split(',');
	 var desegnation = temp[1];
	// alert("desegnation"+desegnation);
	if(document.forms[0].desegnation.value!=desegnation){
	alert("Emp Level with Designation are not equal");
	document.forms[0].emplevel.focus();
   	 return false;
	}
	 
	 
   		  if(document.forms[0].dateofbirth.value=="")
   		 {
   		  alert("Please Enter Date of Birth");
   		  document.forms[0].dateofbirth.focus();
   		  return false;
   		  }
   		  if(!document.forms[0].dateofbirth.value==""){
   		   var date1=document.forms[0].dateofbirth;
   	       var val1=convert_date(date1);
   		   
   		   var now = new Date();
   		   var birthday1= document.forms[0].dateofbirth.value;
   		   var dt1   = birthday1.substring(0,2);
   		   var mon1  = birthday1.substring(3,6);
  		   var year1=birthday1.substring(birthday1.lastIndexOf("/")+1,birthday1.length);
  		  
  		    if(mon1 == "JAN") month = 0;
           	else if(mon1 == "FEB") month = 1;
        	else if(mon1 == "MAR") month = 2;
        	else if(mon1 == "APR") month = 3;
        	else if(mon1 == "MAY") month = 4;
        	else if(mon1 == "JUN") month = 5;
        	else if(mon1 == "JUL") month = 6;
        	else if(mon1 == "AUG") month = 7;
        	else if(mon1 == "SEP") month = 8;
        	else if(mon1 == "OCT") month = 9;
        	else if(mon1 == "NOV") month = 10;
        	else if(mon1 == "DEC") month = 11;
        	var birthDate=new Date(year1,month,dt1);   
        		
   		  if(birthDate > now){
		       alert("DateofBirth cannot be greater than Currentdate");
			   	document.forms[0].dateofbirth.focus();
		       return false;
			}
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		    
   		       }
   		    if(!document.forms[0].dateofjoining.value==""){
   		    var date1=document.forms[0].dateofjoining;
   	        var val1=convert_date(date1);
   		  
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		   }
   		      
   		  if(!document.forms[0].dateOfAnnuation.value==""){
   		    var date1=document.forms[0].dateOfAnnuation;
   	        var val1=convert_date(date1);
   		  
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		   }
   		      
   		   if(!document.forms[0].seperationDate.value==""){
   		    var date1=document.forms[0].seperationDate;
   	        var val1=convert_date(date1);
   		  
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		     }
   		  
   		 if (!ValidateName(document.forms[0].fhname.value))
	  	{
		alert("Numeric/Invalid characters are not allowed");
		document.forms[0].fhname.focus();
		return false;
  	   }
  	   if(document.forms[0].paddress.value.length>150){
  	    alert("Permanent Address Exceeds the Limit");
		document.forms[0].paddress.focus();
		return false;
  	   }
  	  
   	  	if(!ValidateTextArea(document.forms[0].paddress.value)){
  	    alert("Please Enter valid data");
		document.forms[0].paddress.focus();
		return false;
  	  }
  	 
  	  if(document.forms[0].taddress.value.length>150){
  	    alert("Temporary Address Exceeds the Limit");
		document.forms[0].taddress.focus();
		return false;
  	   }
  	   if(!ValidateTextArea(document.forms[0].taddress.value)){
  	    alert("Please Enter valid data");
		document.forms[0].taddress.focus();
		return false;
  	   }
  	   
  	   if(document.forms[0].division.value==""){
  	    alert("Please Select division");
		document.forms[0].division.focus();
		return false;
  	   }
  	  /*  if(document.forms[0].transferStatus.checked){
  	    if(document.forms[0].select_region.value==""){
  	     alert("Please Select Transfer Region");
  	     document.forms[0].select_region.focus();
  	     return false;
  	    }else if(document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].text=="[Select One]"){
  	  
  	     alert("Please Select Transfer Airport");
  	     document.forms[0].select_airport.focus();
  	     return false;
  	    }
  	    }*/
  	    
  	   
  	    if(!document.forms[0].emailId.value==""){
  	     if(ValidateEMail(document.forms[0].emailId.value)==false){
  	   
		document.forms[0].emailId.focus();
		return false;}
  	   }
  	var oldAirportcode=  document.forms[0].oldAirportCode.value;
  	//var newAirportcode= document.forms[0].airPortCode.value; 
  	 	 var newAirportcodeIndex=document.forms[0].airPortCode.options.selectedIndex;
	 var newAirport=document.forms[0].airPortCode[newAirportcodeIndex].text;
	
	 // temp = newAirportcodewithRegion.split('-');
	 // var newAirportcode = temp[0];
	// alert(newAirport);
	   var newAirportcode ;
	 if(newAirport=='[Select One]'){
	newAirportcode=oldAirportcode;
	 }else{
	newAirportcode =newAirport;
	}
  
  	if(oldAirportcode!=trim(newAirportcode)){  	
  	   	var answer =confirm('Old / New Airport Code are not equal. Are you sure, do you want to contine');
	 confirmAirport= answer;
	if(!answer){
	 document.forms[0].airPortCode.focus();
		return false;
		
	  }
	 target=100;
	 
  	    }
   	  
   	  if(!ValidateTextArea(document.forms[0].remarks.value)){
  	   // alert("Please Enter valid data");
		document.forms[0].remarks.focus();
		return false;
  	   }
  	   
  	     	  
  	   if(document.forms[0].remarks.value.length>150){
  	    alert("Remarks Exceeds the Limit");
		document.forms[0].remarks.focus();
		return false;
  	   }
  	     var fdob1=document.getElementsByName("Fdob");
  	      var fname=document.getElementsByName("Fname")
  	      var j;
   		  	for(j=0;j<fdob1.length;j++){
   		  			var s=fdob1[j].value; 
  	     if(!fdob1[j].value==""){
   		  var date1=fdob1[j].value;
   		 
   	        var val1=convert_date(fdob1[j]);
   		  
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		     if(fname[j].value==""){
   		      alert("Please Enter Family Member Name");
   		      fname[j].focus();
   		      return false;
   		      }
   		      }
   		      }
  	     
   		 
   		  
      
   	 	 var equalshare='N';
   	 if(document.forms[0].form1.value=="Yes"){
   	 if(document.forms[0].equalshare.checked==true){   	 
   	 equalshare='Y';
   	 }else{
   	 equalshare='N';   	 
   	 }
   	 }else{
   	 te='';
   	 }
   	   var url;
   	   	url="<%=basePath%>personalForm.do?method=personalUpdate&startIndex="+index+"&totalData="+totalData+"&airportWithRegion="+newAirportcode+"&frm_nominees="+encodeURIComponent(te)+"&equalshare="+equalshare+"&frm_family_Details="+encodeURIComponent(familyte1);	  
	   	//alert(url);
	   	document.forms[0].action=url;     
		document.forms[0].method="post";
		document.forms[0].submit();
   		 }
   		 
   	 function getPensionNumber() {
	var date1=document.forms[0].dateofbirth;
   	var val1=convert_date(date1);
   	var empName =document.forms[0].empName.value;
	var cpfacno =document.forms[0].cpfacnoNew.value;
	var uniquenumber = cpfacno.substring(0, cpfacno.length);
	var formEmpName=document.forms[0].empName.value;
	var newFormEmpName="";
	var finalKey="";
	var isNotSpace=false;
	var dateofbirth =document.forms[0].dateofbirth.value;
	var withoutSlash=replaceAll(dateofbirth,"/","***");
	var withoutDt_array=withoutSlash.split("***");
	var day=withoutDt_array[0];
	var month=convert_month(withoutDt_array[1]);
	var year=withoutDt_array[2].substring(2,4);
	
	var finalDay=day+month+year;
	//alert("finaday "+finalDay);
	
	if(formEmpName.indexOf(" ")!=-1){
		newFormEmpName=replaceAll(formEmpName," ","***");
	}else if(formEmpName.indexOf(".")!=-1){
		newFormEmpName=replaceAll(formEmpName,".","***");
	}else{
		isNotSpace=true;
	}


	if(isNotSpace==true){
		finalKey=formEmpName.substring(0,2);
	}else{
    //alert('new Format'+newFormEmpName);
	var mytool_array=newFormEmpName.split("***");
	//alert(mytool_array);
	
	
	finalKey=mytool_array[0].substring(0,1);
	//alert(mytool_array.length);
	finalKey=finalKey+mytool_array[mytool_array.length-1].substring(0,1);
	}
	var finalName=finalDay+finalKey+uniquenumber;
   	document.forms[0].pensionNumber.value=finalName;
	}
function replaceAll(OldString, FindString, ReplaceString) {
  var SearchIndex = 0;
  var NewString = ""; 
  while (OldString.indexOf(FindString,SearchIndex) != -1)    {
    NewString += OldString.substring(SearchIndex,OldString.indexOf(FindString,SearchIndex));
    NewString += ReplaceString;
    SearchIndex = (OldString.indexOf(FindString,SearchIndex) + FindString.length);         
  }
  NewString += OldString.substring(SearchIndex,OldString.length);
  return NewString;
}

 function getJsDate1(dateStr, objFlag)
			{     
			
			var dateArr = dateStr.split('/');
				var jsDate = "";
				 
				if (!isNaN(dateArr[1].charAt(0))){
					jsDate = dateArr[1] + " " + dateArr[0] + ", " + dateArr[2];
				}
				else
				{  var monthArr = [];
					monthArr["jan"] = "01";
					monthArr["feb"] = "02";
					monthArr["mar"] = "03";
					monthArr["apr"] = "04";
					monthArr["may"] = "05";
					monthArr["jun"] = "06";
					monthArr["jul"] = "07";
					monthArr["aug"] = "08";
					monthArr["sep"] = "09";
					monthArr["oct"] = "10";
					monthArr["nov"] = "11";
					monthArr["dec"] = "12";
					monthNum = monthArr[dateArr[1]];	
					jsDate = monthNum + " " + dateArr[0] + ", " + dateArr[2];
			
				}
				if (objFlag == true)
					return new Date(Date.parse(jsDate));
				else
					return jsDate;
			}
   		   		
   		 
		 function deleteFamilyDetails(familyMemberName,rowid)
		{	createXMLHttpRequest();	
			var url ="<%=basePath%>search1?method=deleteFamilyDetails&familyMemberName="+familyMemberName+"&rowid="+rowid;
			alert("URL "+url);
			xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = getDesegnationName;
			xmlHttp.send(null);
		}

		 
	
function getAirports(){
	   var index=document.forms[0].select_region.selectedIndex.value;
       var selectedIndex=document.forms[0].select_region.options.selectedIndex;
	   var region=document.forms[0].select_region[selectedIndex].text;
		createXMLHttpRequest();	
    	var url ="<%=basePath%>psearch.do?method=getUnitTblAirports&region="+region;
		xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getAirportsList;
		xmlHttp.send(null);
}
    
function getAirportsList(){
	if(xmlHttp.readyState ==4){
		if(xmlHttp.status == 200){ 
			var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
		  	if(stype.length==0){
		 		var obj1 = document.getElementById("airPortCode");
		   		obj1.options.length=0; 
	       }else{
		   	var obj1 = document.getElementById("airPortCode");
		  	obj1.options.length = 0;
		  	for(i=0;i<stype.length;i++){
				obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
				var  selected = getNodeValue(stype[i],"selected");
				if(selected == 'true'){
					obj1.options[i].selected = true;
				}
		  	}
		  }
		}
	}
}
	
	 function  getNominee(){
 
     	document.getElementById("nomineelabel").style.display="block";
     	
   }
  </script>
	</head>

	<body class="BodyBackground" onload="loadDetails('<%=request.getAttribute("nomineeInfo")%>','<%=request.getAttribute("familyInfo")%>');hide();document.forms[0].setEmpSerialNo.focus();swap();retrimentDate();getNominee();">
	<%=ScreenUtilities.screenHeader("Form3-2007-EmpPersonalInfo[Edit]")%>
		<html:form method="post" action="personalForm.do?method=personalUpdate">
			<table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
				
				<tr>
					<td>
					<table width="95%" border="0" align="center" cellpadding="0" cellspacing="3" class="tbborder">
					<tr>
								<td align="center" class="label">
				<%if (request.getAttribute("ArrearInfo") != null) {
                arrearInfoString1 = (String) request.getAttribute("ArrearInfo");
                request.setAttribute("ArrearInfo", arrearInfoString1);
                System.out.println("arrearInfoString1****  "
                        + arrearInfoString1);

               }%>
									<%if (request.getAttribute("recordExist") != null) {%>
									<font color="red"><%=request.getAttribute("recordExist")%></font>
									<%}%>
								<td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
							<%String pensionNumber = "", editFrom = "";
            if (request.getAttribute("pensionNumber") != null) {
                pensionNumber = request.getAttribute("pensionNumber")
                        .toString();
            }

            %>
									<%if (request.getAttribute("EditBean") != null) {
                EmpMasterBean bean1 = (EmpMasterBean) request
                        .getAttribute("EditBean");
                String form2 = bean1.getForm2Nomination().trim();
                if (request.getAttribute("AirportList") != null) {
                    ArrayList AirportList = (ArrayList) request
                            .getAttribute("AirportList");
                     }
                if (request.getAttribute("editFrom") != null) {
                    editFrom = (String) request.getAttribute("editFrom");
                 }

                %>
							
						<tr>
											<td class="tableTextBold">
												<b>PF ID: <b>
											</td>
											<td align="left">
												<input class="TextField" type="text" name="pfid" readonly="true" maxlength="35" tabindex="1" readonly="true" value='<%=bean1.getPfid()%>'/>
												<input type="hidden" name="setEmpSerialNo" readonly="true" maxlength="35" tabindex="1" value='<%=bean1.getEmpSerialNo()%>'/>
											</td>
							</tr>	
							
							<tr>
											<td class="tableTextBold">
												Old CPFACC.NO:<font color="red">&nbsp;*</font>
											</td>
											<td align="left">
												<input class="TextField" type="text" name="cpfacnoNew" maxlength="25" tabindex="2" onBlur="charsCapsNumOnly();getPensionNumber();" value='<%=bean1.getCpfAcNo()%>' readonly="true"/>
											</td>
											<!-- 	<input type="text" name="cpfacnoNew" maxlength="25" tabindex="1" onBlur="charsCapsNumOnly();getPensionNumber();" value='<%=bean1.getCpfAcNo()%>'>-->

											<td class="tableTextBold">
												Old / New Airport Code:<font color="red">&nbsp;*</font>
											</td>
											<td align="left">
												<input class="TextField" type="text" name="oldAirportCode" size="5" readonly="true" value='<%= bean1.getStation()%>'%>
												  
											</td>

											<!--<td class="tdleft">
									Airport SerialNumber
								</td>
								<td>
									<input type="text" name="airportSerialNumber " value='<%=bean1.getAirportSerialNumber()%>' onkeyup="return limitlength(this, 20)">
								</td>
							-->
											<input type="hidden" name="airportSerialNumber" value='<%=bean1.getAirportSerialNumber()%>' onkeyup="return limitlength(this, s0)">
											<input type="hidden" name="region" value='<%=bean1.getRegion()%>' onkeyup="return limitlength(this, s0)">
											<input type="hidden" name="editFrom" value='<%=editFrom%>' />
										</tr>
										<tr>
										
											<td class="tableTextBold" width="25%">
												Region:
											</td>
											<td width="35%" align="left">

												<select name="select_region" onchange="javascript:getAirports();" style="width:130px" tabindex="2" class="TextField" >
													<option value="">
														[Select One]
													</option>
													<%
						     							  while(regionIterator.hasNext()){
							 	 						 region=hashmap.get(regionIterator.next()).toString();
							 						%>
													<option value="<%=region%>">
														<%=region%>
													</option>
													<%}%>
												</select>
											</td>
											<td class="tableTextBold" width="35%">
												Airport Code:
											</td>
											<td width="25%"  align="left">
												<SELECT NAME="airPortCode" style="width:120px" tabindex="2" class="TextField" >
													<option value="" Selected="Selected">
														[Select One]
													</option>
												</SELECT>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Employee Code:
											</td>
											<td align="left">
												<input class="TextField" type="text" name="employeeCode" maxlength="20" tabindex="4" value='<%=bean1.getEmpNumber()%>' onkeyup="return limitlength(this, 20)">
											</td>

											<td class="tableTextBold">
												Employee Name:<font color="red">&nbsp;*</font>
											</td>

											<td align="left">
												<input class="TextField" type="text" name="empName" maxlength="50" tabindex="5" onblur="getPensionNumber();" value='<%=bean1.getEmpName()%>' onkeyup="return limitlength(this,50)">
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Father's / Husband's Name:
											</td>
											<td align="left">
												<select  class="TextField" name="select_fhname" tabindex="6" style="width:64px">
													<option value='F' <%if(bean1.getFhFlag().trim().equals("F")){ out.println("selected");}%>>
														Father
													</option>
													<option value='H' <%if(bean1.getFhFlag().trim().equals("H")){ out.println("selected");}%>>
														Husband
													</option>
												</select>
												<input class="TextField" type="text" name="fhname" maxlength="50" tabindex="7" value='<%=bean1.getFhName()%>' onkeyup="return limitlength(this, 50)">
											</td>

											<td class="tableTextBold">
												Sex:
											</td>

											<td align="left">
												<select  class="TextField" name="sex" tabindex="8">
													<%String sex = bean1.getSex().trim();%>
													<option value="">
														[Select One]
													</option>
													<option value="M" <%if(sex.equals("M")){ out.println("selected");}%>>
														Male
													</option>
													<option value="F" <%if(sex.equals("F")){ out.println("selected");}%>>
														Female
													</option>

												</select>


											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Date of Birth:<font color="red">&nbsp;*</font>
											</td>
											<td align="left">
												<input class="TextField" type="text" name="dateofbirth" tabindex="9" onblur="retrimentDate();getPensionNumber();" value='<%=bean1.getDateofBirth()%>' onkeyup="return limitlength(this, 20)">
												<a href="javascript:show_calendar('forms[0].dateofbirth');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
											</td>
											<td class="tableTextBold">
												Date of Joining:
											</td>
											<td align="left">
												<input class="TextField" type="text" name="dateofjoining" tabindex="10" value='<%=bean1.getDateofJoining()%>' onkeyup="return limitlength(this, 20)">
												<a href="javascript:show_calendar('forms[0].dateofjoining');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Employee Level:<font color="red">&nbsp;*</font>
											</td>
											<td align="left">
												<%String emplevel = bean1.getEmpLevel().trim();%>
												<select  class="TextField" name="emplevel" tabindex="11" onchange="getDesegnation(document.forms[0].emplevel.value)">
													<option value="">
														[Select One]
													</option>
													<option value='E-9' <%if(emplevel.equals("E-9")){ out.println("selected");}%>>
														E-9,Executive Directior
													</option>
													<option value='E-8' <%if(emplevel.equals("E-8")){ out.println("selected");}%>>
														E-8,General Manager
													</option>
													<option value='E-7' <%if(emplevel.equals("E-7")){ out.println("selected");}%>>
														E-7,Jt. General Manager
													</option>
													<option value='E-6' <%if(emplevel.equals("E-6")){ out.println("selected");}%>>
														E-6,Deputy General Manager
													</option>
													<option value='E-5' <%if(emplevel.equals("E-5")){ out.println("selected");}%>>
														E-5,Asst. General Manager
													</option>
													<option value='E-4' <%if(emplevel.equals("E-4")){ out.println("selected");}%>>
														E-4,Senior Manager
													</option>
													<option value='E-3' <%if(emplevel.equals("E-3")){ out.println("selected");}%>>
														E-3,Manager
													</option>
													<option value='E-2' <%if(emplevel.equals("E-2")){ out.println("selected");}%>>
														E-2,Assistant Manager
													</option>
													<option value='E-1' <%if(emplevel.equals("E-1")){ out.println("selected");}%>>
														E-1,Junior Executive
													</option>
													<option value='NE-1' <%if(emplevel.equals("NE-1")){ out.println("selected");}%>>
														NE-1,Jr. Attendant
													</option>
													<option value='NE-2' <%if(emplevel.equals("NE-2")){ out.println("selected");}%>>
														NE-2,Attendant
													</option>
													<option value='NE-3' <%if(emplevel.equals("NE-3")){ out.println("selected");}%>>
														NE-3,Senior Attendant
													</option>
													<option value='NE-4' <%if(emplevel.equals("NE-4")){ out.println("selected");}%>>
														NE-4,Jr. Assistant
													</option>
													<option value='NE-5' <%if(emplevel.equals("NE-5")){ out.println("selected");}%>>
														NE-5,Assistant
													</option>
													<option value='NE-6' <%if(emplevel.equals("NE-6")){ out.println("selected");}%>>
														NE-6,Senior Assistant
													</option>
													<option value='NE-7' <%if(emplevel.equals("NE-7")){ out.println("selected");}%>>
														NE-7,Supervisor
													</option>
													<option value='NE-8' <%if(emplevel.equals("NE-8")){ out.println("selected");}%>>
														NE-8,Superintendent
													</option>
													<option value='NE-9' <%if(emplevel.equals("NE-9")){ out.println("selected");}%>>
														NE-9,Sr. Superintendent
													</option>
													<option value='NE-10' <%if(emplevel.equals("NE-10")){ out.println("selected");}%>>
														NE-10,Sr. Superintendent(SG)
													</option>
													<option value='B1' <%if(emplevel.equals("B1")){ out.println("selected");}%>>
														B1,Chairman
													</option>
													<option value='B2' <%if(emplevel.equals("B2")){ out.println("selected");}%>>
														B2,Member
													</option>
												</select>
											</td>

											<td class="tableTextBold">
												Designation:<font color="red">&nbsp;*</font>
											</td>
											<td>
												<input class="TextField" type="text" name="desegnation" readonly="true" tabindex="12" value='<%=bean1.getDesegnation()%>' onkeyup="return limitlength(this, 20)">

											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Discipline:
											</td>
											<td align="left">
												<%String department = bean1.getDepartment().trim();

                %>
												<select name="department" tabindex="13" style="width:195px;"  class="TextField">
													<option value="">
														[Select One]
													</option>
					<%if (request.getAttribute("DepartmentList") != null) {
                    ArrayList deptList = (ArrayList) request
                            .getAttribute("DepartmentList");
                    for (int i = 0; i < deptList.size(); i++) {
                        boolean exist = false;
                        EmpMasterBean deptBean = (EmpMasterBean) deptList
                                .get(i);
                        if (deptBean.getDepartment().equalsIgnoreCase(
                                bean1.getDepartment().trim()))
                            exist = true;
                        if (exist) {

                            %>
						<option value="<%=bean1.getDepartment().trim()%>" <% out.println("selected");%>>
						<%=bean1.getDepartment().trim()%>
						</option>		<%} else {%>
													<option value="<%= deptBean.getDepartment()%>">
														<%=deptBean.getDepartment()%>
													</option>

													<%}
                    }
                }%>

												</select>

											</td>
											<td class="tableTextBold">
												Division:<font color="red">&nbsp;*</font>
											</td>
											<td align="left">
												<%String division = bean1.getDivision().trim();

                %>
												<select  class="TextField" name="division" tabindex="14">
													<option value="">
														[Select One]
													</option>
													<option value='NAD' <%if(division.equals("NAD")){ out.println("selected");}%>>
														NAD
													</option>
													<option value='IAD' <%if(division.equals("IAD")){ out.println("selected");}%>>
														IAD
													</option>
												</select>
											</td>
										</tr>
										<%--<tr>
											<td class="tableTextBold">
												Transfer Status:
											</td>
											<td>
												<input type="checkbox" name="transferStatus" tabindex="30" onclick="showRegion();">
											</td>
										</tr>--%> 

										 <tr>
											<td class="tableTextBold">
												Separation Reason:
											</td>
											<td align="left">
												<select  class="TextField" name="reason" tabindex="15" id="mySelect" onChange="swap();" style=display:inline;>
													<%String reason = bean1.getSeperationReason().trim();

                                                    %>
													<option value="">
														[Select One]
													</option>
													<option value='Retirement' <%if(reason.equals("Retirement")){ out.println("selected");}%>>
														Retirement
													</option>
													<option value='Death' <%if(reason.equals("Death")){ out.println("selected");}%>>
														Death
													</option>
													<option value='Resignation' <%if(reason.equals("Resignation")){ out.println("selected");}%>>
														Resignation
													</option>
													<option value='Termination' <%if(reason.equals("Termination")){ out.println("selected");}%>>
														Termination
													</option>
													<option value='Option for Early Pension' <%if(reason.equals("Option for Early Pension")){ out.println("selected");}%>>
														Option for Early Pension
													</option>
													<option value='VRS' <%if(reason.equals("VRS")){ out.println("selected");}%>>
														VRS
													</option>
													
													<option value='Other' <%if(reason.equals("Other")){ out.println("selected");}%>>
														Other
													</option>
												</select>

											</td>
											<td class="tableTextBold">
												Date of Separation:
											</td>
											<td align="left">
												<input class="TextField" type="text" name="seperationDate" tabindex="16" value='<%=bean1.getDateofSeperationDate()%>' onkeyup="return limitlength(this, 20)">
												<a href="javascript:show_calendar('forms[0].seperationDate');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td align="left">
												<input class="TextField" type="text" name="Other" value='<%=bean1.getOtherReason()%>' id="myText" style="display:none;" tabindex="17" onclick="swapback()">
											</td>
										</tr>
										<tr>

											<td class="tableTextBold">
												Permanent Address:
											</td>

											<td align="left">
												<TEXTAREA name="paddress" size="150" maxlength="150" tabindex="18" >
													<%=bean1.getPermanentAddress()%>
												</TEXTAREA>
											</td>
											<td class="tableTextBold">
												Temporary Address:
											</td>
											<td align="left">
												<TEXTAREA name="taddress" size="150" maxlength="150" tabindex="19">
													<%=bean1.getTemporatyAddress()%>
												</TEXTAREA>


											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Pension Option Received:
											</td>

											<td align="left">
                                       		  <input class="TextField" type="text" name="wetherOption" value='<%=bean1.getWetherOption().trim()%>' tabindex="20" readonly="true" >
											
											</td>
											<td class="tableTextBold">
												Whether Form2 Nomination Received :
											</td>
											<td align="left">
												<select  class="TextField" name="form1" tabindex="21" onchange="show()">

													<%String form1 = bean1.getForm2Nomination().trim();

                %>
													<option value="">
														[Select One]
													</option>
													<option value="Yes" <%if(form1.equals("Yes")){ out.println("selected");}%>>
														Yes
													</option>
													<option value="No" <%if(form1.equals("No")){ out.println("selected");}%>>
														No
													</option>

												</select>
											</td>

										</tr>
										<tr>
											<input type="hidden" name="cpfacno" value='<%=bean1.getCpfAcNo()%>'/>
											<input type="hidden" name="empOldName" value='<%=bean1.getEmpName()%>'/>
											<input type="hidden" name="empOldNumber" value='<%=bean1.getEmpNumber()%>'/>
											<td class="tableTextBold">
												Date of Super Annuation:
											</td>
											<td align="left">
												<input class="TextField" type="text" name="dateOfAnnuation" readonly="true" tabindex="23" value='<%=bean1.getDateOfAnnuation()%>'>
											</td>
											<td class="tableTextBold">
												Email Id:
											</td>
											<td align="left">
												<input class="TextField" type="text" name="emailId" maxlength="50" onkeyup="return limitlength(this,50)" value='<%=bean1.getEmailId()%>' tabindex="24">
											</td>
										</tr>
 
										<tr>
											<td class="tableTextBold">
												Remarks:
											</td>

											<td align="left">
												<TEXTAREA NAME="remarks" tabindex="25">
													<%=bean1.getRemarks()%>
												</TEXTAREA>
												<input type="hidden" name="ArrearInfo" value="<%=arrearInfoString1%>">
											</td>



										</tr>
										<tr>
										<td>&nbsp;
										</td>
										</tr>
										<%}%>							
							
					</table> 
					</td>
					</tr>
					
				<tr>
				<td align="left">
				<table align="left">
									   	 
											<tr>
										<td>
											<table align="left" border="0">
											<tbody id="familytable">
											
											 <tr>
												<td class="tableTextBold" nowrap="nowrap"> 
													<b><font size="2">Family Details</font></b>
												</td>

											</tr>
											 
											 
												<tr>
													<td class="tableTextBold" align="center">
														Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													 
													<td class="tableTextBold" align="center">
														Dateof Birth&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														&nbsp;&nbsp;Relation with Member&nbsp;&nbsp;
													</td>
													 
													<td>
														&nbsp;<b><img alt="Add New Record" src="<%=basePath%>/images/addIcon.gif" onclick="callFamily('','','','');"/></b>
													</td>
												</tr>	
												<tr>
												</tr>
												</tbody>													
												
											</table>
											</td>
											</tr>
								 		 
				
				</table>
				</td>
				</tr>
				
				<tr>
				<td>
				
				<table align="center">
									  	
											 
											<tr id="divNomineeDetails">
										<td colspan="4" >
											<table align="center" border="0">
											<tbody id="nomineetable">
											<tr>
												<td class="tableTextBold" nowrap="nowrap"> 
													<b><font size="2">Nomination for PF</font></b>
												</td>

											</tr>
											 
											<tr>																					
											<td class="tableTextBold" nowrap="nowrap">
												Equal Share check:&nbsp;
											</td>
											 
											<td>											
												
												<html:checkbox property="equalshare" onclick="dispTotShare();"/>
												
											</td>
											
											 							
										</tr>
												<tr align="center">
													<td class="tableTextBold" align="center">
														Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Address&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center" >
														Dateof Birth&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Relation with Member&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center">
														Name of Guardian&nbsp;&nbsp;&nbsp;
													</td>
													<td class="tableTextBold" align="center" nowrap="nowrap">
														Address of Guardian&nbsp;&nbsp;&nbsp; 
													</td>
													<td class="tableTextBold" align="center">
														Total Share
														<BR>
														payable in %
													</td>
													<td>
													&nbsp;<b><img alt="" src="<%=basePath%>images/addIcon.gif" onclick="callNominee('','','','','','','','');" /></b>
													</td>
												</tr>	
												<tr>
												</tr>
												</tbody>
											</table>
											</td>
											</tr>
				 </table>
				 </td>
				 </tr>
				 
				 	
					 
				 	<tr><td>
				 	
				 	<table align="center">
										<tr>
											<td align="center">
												<input type="hidden" name="flagData" value="<%=request.getAttribute("flag")%>" />
												<input type="button" class="btn" value="Update" class="btn" onclick="testSS('<%=request.getAttribute("startIndex")%>','<%=request.getAttribute("TotalData")%>')" tabindex="46">
												<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" tabindex="47">
												<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="48">
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



