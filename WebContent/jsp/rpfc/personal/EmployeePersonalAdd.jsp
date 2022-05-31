<%@ page import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.ScreenUtilities,com.epis.bean.rpfc.DesignationBean"%>
<%@ page import="java.util.ArrayList"%>


<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
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
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime1.js"></SCRIPT>
		<script type="text/javascript">
  
  var ex=/^[0-9.-]+$/;
  var validateflag,family;
function ValidateEMail(emailStr){
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
	if (matchArray==null) {
		alert("Email address seems incorrect (check @ and .'s)")
		return false
	}
	var user=matchArray[1]
	var domain=matchArray[2]
	if (user.match(userPat)==null){
		alert("The username doesn't seem to be valid.")
		return false
	}

	var IPArray=domain.match(ipDomainPat)
	if (IPArray!=null){
		for (var i=1;i<=4;i++){
			if (IPArray[i]>255) {
				alert("Destination IP address is invalid!")
				return false
			}
		}
	    return true
	}

	var domainArray=domain.match(domainPat)
	if (domainArray==null){
		alert("The domain name doesn't seem to be valid.")
		return false
	}

	var atomPat=new RegExp(atom,"g")
	var domArr=domain.match(atomPat)
	var len=domArr.length
	if (domArr[domArr.length-1].length<2 || domArr[domArr.length-1].length>4){
		   alert("The address must end in a three-letter domain, or two letter country.")
		   return false
	}

	if (len<2) {
		var errStr="This address is missing a hostname!"
		alert(errStr)
		return false
	}
	return true;
}
		
   	
   	 function numsDotOnly(){
        if(((event.keyCode>=48)&&(event.keyCode<=57))||(event.keyCode==46)){
	        event.keyCode=event.keyCode;
	    }else{
			event.keyCode=0;
       }
	}	
   	function limitlength(obj, length){
		var maxlength=length;
		if (obj.value.length>maxlength){
			obj.value=obj.value.substring(0, maxlength);
		}
	}
			
	var xmlHttp;

	function createXMLHttpRequest()	{
	 if(window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	 }else if (window.XMLHttpRequest){
		xmlHttp = new XMLHttpRequest();
	 }
	}
	
	function getNodeValue(obj,tag){
		return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
   }
	


function getAirports(){
	   var index=document.forms[0].region.selectedIndex.value;
       var selectedIndex=document.forms[0].region.options.selectedIndex;
	   var region=document.forms[0].region[selectedIndex].text;
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
		 		var obj1 = document.getElementById("select_airport");
		   		obj1.options.length=0; 
	       }else{
		   	var obj1 = document.getElementById("select_airport");
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
			
			function swap() { 
			if(document.getElementById('mySelect').value == 'Other'){ 
			document.getElementById('mySelect').style.display = 'block'; 
			document.getElementById('myText').style.display = 'block'; 
			document.getElementById('myText').focus(); 
			//alert("hi");
			//alert(document.getElementById('myText'));
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
						
			function hide(){
		   
			 divNomineeDetails.style.display="none";
			}
			function reasonOther(){
			// alert("hi"+document.forms[0].reason.value);
			 if(document.forms[0].reason.value=="Other")
			  reason1.style.display="block";
			  else   reason1.style.display="none";
			 }
			
			
			function show(){
			 if(document.forms[0].form1.value=="Yes")
   		     {   		    
   		     divNomineeDetails.style.display="block";   		  
   		     }
   		      if(document.forms[0].form1.value=="No")
   		     {   		  
   		      divNomineeDetails.style.display="none";
   		    }
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
   		
   		
   		
 	 var count =0;
   
  	 function call_calender(dobValue){
  	 	show_calendar(dobValue);
  	 }
  	 function retirement_calendar(dobValue){
  	   show_calendar(dobValue);
  		retrimentDate();
  		 }
     var count1 =0;
   
   
   
   //for changing Nominee Details Style
	var count1 =0,slno=0;
    var len=0,deleteID='',reclength=0,fdeleteID='';
    var nomineearray = new Array();
    var nomineearrfinal=new Array();
    var nomineeInfo='';
    var formName='';
    	  
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
		for(var i=0;i<afterDeletedList.length;i++){	 	 
		            
				finaldltdID=afterDeletedList[i];				
												
				if(finaldltdID!=''){    			
				
				 var flag=validateNomineeDetails(finaldltdID);
				  validateflag=flag;
			     if(!flag){
					    te='';
						return false;  
				    }else{
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
			
			
			
	if(validateflag==false || family==false){
	totalshareval=0;
	family=true;
	
	}
	// alert('-----validateflag-----'+validateflag);	
	
	 
	
	if(document.getElementById("totalshare"+reclength).value!=''){
	
	shareval=parseFloat(document.getElementById("totalshare"+reclength).value);
	totalshareval=totalshareval+shareval;
	 target=target-shareval;
	//alert('shareval----'+shareval+' ---target-----'+target); 
	 
		remainingshareval=target;
	} 
	 
	 
					//alert('remainingshareval ----------  '+remainingshareval);
					//alert('totalshareval ----------  '+totalshareval);
	 
				
	 
					var nomineedob=document.getElementById("nomineeDOB"+reclength).value;
					var gardianName=document.getElementById("gardianname"+reclength);
					 
					
			 		if(nomineedob!=''){
			 		
			 		 var currentDate1=getCurrentDate();
				  
				 var result=compareDates(currentDate1, nomineedob);
				 
				 if(result=="smaller"){
				 alert('Date Of Birth should not less than to current date');
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
   
// end of Family details
   	function personalAdd(){
 
         var fnName=document.getElementsByName("FName");
         var fnDOB=document.getElementsByName("Fdob");
		 var fnRelation=document.getElementsByName("Frelation");
		 
		
         if(document.forms[0].region.selectedIndex<1)
   		 {
   		  alert("Please Select Region");
   		  document.forms[0].region.focus();
   		  return false;
   		  } 
   		  
   		  
   		  if(document.forms[0].empName.value=="")
   		  {
   		  alert("Please Enter Employee Name");
   		   document.forms[0].empName.focus();
   		  return false;
   		  }
   		  
   		  if (!ValidateName(document.forms[0].empName.value))
	  		{
		 alert("Numeric Values are not allowed");
		 document.forms[0].empName.focus();
		 return false;
  	    }
  	   if (!ValidateName(document.forms[0].fhname.value))
	  		{
		alert("Numeric Values are not allowed");
		document.forms[0].fhname.focus();
		return false;
  	  }
  	  
  	  
  	   if(document.forms[0].dateofbirth.value=="")
   		 {
   		  alert("Please Enter Date of Birth");
   		  document.forms[0].dateofbirth.focus();
   		  return false;
   		  }
   		  if(!document.forms[0].dateofbirth.value==""){
   		//   alert(document.forms[0].dateofbirth.value);
   		   var date1=document.forms[0].dateofbirth;
   	       var val1=convert_date(date1);
   		  
   		    if(val1==false)
   		      {
   		      return false;
   		      }
   		    }
   		    
   		    
   		     if(document.forms[0].sex.value=="")
   		  {
   		  alert("Please Select one");
   		  document.forms[0].sex.focus();
   		  return false;
   		  }
   		  
   		  if(document.forms[0].fhname.value=="")
   		  {
   		  	alert("Please Enter Father/Husband Name");
   		 	 document.forms[0].fhname.focus();
   		  	return false;
   		  }
   		  
         if(document.forms[0].emplevel.selectedIndex<1)
   		 {
   		  alert("Please Select Designation");
   		  document.forms[0].emplevel.focus();
   		  return false;
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
   		      
   		if(!ValidateTextArea(document.forms[0].paddress.value)){
  	    alert("Please Enter valid data");
		document.forms[0].paddress.focus();
		return false;
  	   }
  	   if(!ValidateTextArea(document.forms[0].taddress.value)){
  	    alert("Please Enter valid data");
		document.forms[0].taddress.focus();
		return false;
  	   }
  	   
  	    if(!document.forms[0].emailId.value==""){
  	     if(ValidateEMail(document.forms[0].emailId.value)==false){
  	    	document.forms[0].emailId.focus();
		 return false;}
  	   }
  	    if(!ValidateTextArea(document.forms[0].remarks.value)){
  	   // alert("Please Enter valid data");
		document.forms[0].remarks.focus();
		return false;
  	   }
  	   if(document.forms[0].division.value==""){
  	    alert("Please Select division");
		document.forms[0].division.focus();
		return false;
  	   }
		
  	     var fdob1=document.getElementsByName("Fdob");
  	     var fname=document.getElementsByName("Fname")
  	      var j;
   		  	for(j=0;j<fdob1.length;j++){
   		  	if(!fdob1[j].value==""){
   		  var date1=fdob1[j].value;
   		 
   	        var val1=convert_date(fdob1[j]);
   		    if(val1==false){
   		    return false;
   		    } 
   		      if(fname[j].value==""){
   		      alert("Please Enter Family Member Name");
   		      fname[j].focus();
   		      return false;
   		      }
   		    }
   		 }
   		     
  	      
   		  
   		      
   	  var totalshare=document.getElementsByName("totalshare");
   	  var nomineeName=document.getElementsByName("Nname");
   	  var l;
   	  var finalShare=0;
	 
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
	
		var empName=document.forms[0].empName.value;
		var empDetails = window.showModalDialog("<%=basePath%>personalForm.do?method=chkEmpPersonal&frmEmpName="+empName+"&empflag=true",
					"AAI", 'status:no;dialogWidth:800px;dialogHeight:320px;dialogHide:true;dialogLeft:120px;scroll:yes;resizable:yes');
		if(empDetails==true){
			empName="";
   			var dobDetails = window.showModalDialog("<%=basePath%>personalForm.do?method=chkEmpPersonal&frmDob="+document.forms[0].dateofbirth.value+"&dobflag=true","AAI", 'status:no;dialogWidth:800px;dialogHeight:320px;dialogHide:true;dialogLeft:120px;scroll:yes;resizable:yes');
   		}
   		//alert('empDetails=='+empDetails+'dobDetails=='+dobDetails);
   		
		if(empDetails==true || dobDetails==true){
		var url;
		url="<%=basePath%>personalForm.do?method=addToPersonal&frm_nominees="+encodeURIComponent(te)+"&equalshare="+equalshare+"&frm_family_Details="+encodeURIComponent(familyte1);	 	 
		alert(url);
		document.forms[0].action=url;		
		document.forms[0].method="post";
		document.forms[0].submit();
		}
  		
   		}
   		 
   		 function getJsDate1(dateStr, objFlag)
			{
			
				var dateArr = dateStr.split("/");
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
				//alert(jsDate);
				}
				if (objFlag == true)
					return new Date(Date.parse(jsDate));
				else
					return jsDate;
			}
   		 
   	function charsCapsSpaceDotOnly()
         {
              if(event.keyCode >=97 && event.keyCode<=122)    
                   event.keyCode = event.keyCode - 32;
              if(((event.keyCode >=65 && event.keyCode<=90))||(event.keyCode==32)||
                                (event.keyCode==46))  
                   event.keyCode = event.keyCode;
              else
                   event.keyCode=0;
         }
    function frmOnload(){
	   document.forms[0].region.focus();	  
	   var chkmessage='<%=msg%>';	   
	   var flag=false;
	   var messg='<%=message%>';
	   if(chkmessage=='true'){
    		var answer =confirm(messg);
    		if(answer){
    			var answer =confirm('Do you want add one more record');  		 			 
    			if(!answer){
    			flag=true;
    			}
    		}else{
    			flag=true;
    		}
    	}    
     	if(flag==true){
     	var url;
     	url="<%=basePath%>personalForm.do?method=loadPerMstr";        	 
     		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
     	} 
    }
</script>
	</head>

	<body class="BodyBackground" onload="frmOnload();hide();">

		<html:form method="post" action="personalForm.do?method=loadPerMstr">
			<%=ScreenUtilities.saearchHeader("PF ID[New]")%>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
					<tr>
						<td>
							<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" >

										<tr>
										
											<td class="tableTextBold" width="25%">
												Region:<font color="red">&nbsp;*</font>
											</td>
											<td width="35%" align="left">

												<select name="region" onchange="javascript:getAirports()" style="width:130px" tabindex="2" class="TextField" >
													<option value="NO-SELECT">
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
												Airport Code:<font color="red">&nbsp;*</font>
											</td>
											<td width="25%"  align="left">
												<SELECT NAME="select_airport" style="width:120px" tabindex="2" class="TextField" >
													<option value='NO-SELECT' Selected="Selected">
														[Select One]
													</option>
												</SELECT>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Airport Serial Number:
											</td>
											<td  align="left">
												<input type="text"class="TextField"  name="airportSerialNumber" onkeyup="return limitlength(this, 20)" tabindex="3" />
											</td>
											<td class="tableTextBold">
												Employee Code [Salary]:
											</td>
											<td  align="left">
												<input type="text" class="TextField" name="employeeCode" maxlength="20" onkeyup="return limitlength(this, 20)" tabindex="4" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Employee Name:<font color="red">&nbsp;*</font>
											</td>
											<td  align="left">
												<input type="text"class="TextField"  name="empName" maxlength="50" onkeypress='ValidateAlphaNumericwithoutSpace()' tabindex="5" onkeyup="return limitlength(this, 50)" />
											</td>

											<td class="tableTextBold">
												Date of Birth:<font color="red">&nbsp;*</font>
											</td>
											<td  align="left">
												<input type="text" class="TextField" name="dateofbirth" onblur="javascript:retrimentDate()" tabindex="6" onkeyup="return limitlength(this, 20)" />
												<a href="javascript:retirement_calendar('forms[0].dateofbirth');"><img src="<%=basePath%>images/calendar.gif" border="no" alt="" /></a>
											</td>


										</tr>
										<tr>
											<td class="tableTextBold">
												Gender:
											</td>
											<td  align="left">
												<select name="sex" tabindex="7" style="width:64px" class="TextField" >
													<option value='M'>
														Male
													</option>
													<option value='F'>
														Female
													</option>
												</select>

											</td>

											<td class="tableTextBold">
												Marital Status:
											</td>
											<td  align="left">
												<select name="mstatus" tabindex="8" style="width:64px" class="TextField" >
													<option value='Yes'>
														Yes
													</option>
													<option value='No'>
														No
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Father's / Husband's Name:<font color="red">&nbsp;*</font>
											</td>
											<td align="left" colspan="3">
												<select name="select_fhname" tabindex="9" style="width:64px" class="TextField">
													<option value='F'>
														Father
													</option>
													<option value='H'>
														Husband
													</option>
												</select>
												<input type="text" class="TextField"  name="fhname" maxlength="50" onkeyup="return limitlength(this, 50)" tabindex="10" />
											</td>
											

										</tr>

										<tr>

											<td class="tableTextBold">
												Designation:<font color="red">&nbsp;*</font>
											</td>
											<td  align="left">
												<select name="emplevel" tabindex="11" style="display:inline;width:164px" class="TextField" >
													<option value="">
														[Select One]
													</option>
													<%if (request.getAttribute("desginationList") != null) {
									String department="";
									DesignationBean desingationBean=null;
					                ArrayList departmentList = (ArrayList) request.getAttribute("desginationList");
				            		for (int i = 0; i < departmentList.size(); i++) {
										desingationBean= (DesignationBean) departmentList.get(i);%>
													<option value="<%=desingationBean.getEmplevel()%> - <%=desingationBean.getDesingation()%>">
														<%=desingationBean.getEmplevel()%>
														-
														<%=desingationBean.getDesingation()%>
													</option>
													<%}}%>
												</select>
											</td>
											<td class="tableTextBold">
												Discipline:
											</td>
											<td  align="left">
												<select name="department" tabindex="12" class="TextField"  style="display:inline;width:164px" class="TextField" >
													<option value="NO-SELECT">
														[Select One]
													</option>
													<%if (request.getAttribute("DepartmentList") != null) {
										String department="";
					                    ArrayList departmentList = (ArrayList) request.getAttribute("DepartmentList");
				            			for (int i = 0; i < departmentList.size(); i++) {
											department= (String) departmentList.get(i);
                         		%>
													<option value="<%=department%>">
														<%=department%>
													</option>
													<%}}%>
												</select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Date of Joining:
											</td>
											<td  align="left">
												<input type="text" name="dateofjoining" class="TextField" onkeyup="return limitlength(this, 20)" tabindex="13" />
												<a href="javascript:show_calendar('forms[0].dateofjoining');"><img src="<%=basePath%>images/calendar.gif" border="no" alt="" /></a>
											</td>
											<td class="tableTextBold">
												Date of Super Annuation:
											</td>
											<td  align="left">
												<input type="text" name="dateOfAnnuation" class="TextField" onkeyup="return limitlength(this, 20)" tabindex="14" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Date of Separation:
											</td>
											<td  align="left">
												<input type="text" name="seperationDate" class="TextField" onkeyup="return limitlength(this, 20)" tabindex="14" />
												<a href="javascript:show_calendar('forms[0].seperationDate');"><img src="<%=basePath%>images/calendar.gif" border="no"   alt="" /></a>
											</td>
											<td class="tableTextBold">
												Separation Reason:
											</td>
											<td  align="left">
												<select name="reason" tabindex="15" id="mySelect"  class="TextField" onChange="swap();" style="display:inline;">
													<option value="">
														[Select One]
													</option>
													<option value='Retirement'>
														Retirement
													</option>
													<option value='Death'>
														Death
													</option>
													<option value='Resignation'>
														Resignation
													</option>
													<option value='Termination'>
														Termination
													</option>
													<option value='Option for Early Pension'>
														Option for Early Pension
													</option>
													<option value='VRS'>
														VRS
													</option>
													<option value='Other'>
														Other
													</option>
												</select>

												<!-- <input type="text" name="reason" onkeyup="return limitlength(this, 20)">-->
											</td>


										</tr>
										<tr>
											<td colspan="3">
												&nbsp;
											</td>

											<td  align="left">
												<input type="text" class="TextField" name="Other" id="myText" style="display:none;" onclick="swapback()" />

											</td>
										</tr>

										<tr>

											<td class="tableTextBold">
												Permanent Address:
											</td>
											<td  align="left">
												<TEXTAREA NAME="paddress" class="TextField" maxlength="150" cols="20" tabindex="16" rows=""></TEXTAREA>
											</td>
											<td class="tableTextBold">
												Temporary Address:
											</td>
											<td  align="left">
												<TEXTAREA NAME="taddress" class="TextField" maxlength="150" cols="20" tabindex="17" rows=""></TEXTAREA>
											</td>
										</tr>

										<tr>
											<td class="tableTextBold">
												Email Id:
											</td>
											<td  align="left">
												<input type="text" name="emailId" class="TextField" maxlength="100" onkeyup="return limitlength(this, 100)" tabindex="18" />
											</td>
											<td class="tableTextBold">
												Division:<font color="red">&nbsp;*</font>
											</td>
											<td  align="left">
												<select name="division" tabindex="19" class="TextField">
													<option value="">
														[Select One]
													</option>
													<option value='NAD'>
														NAD
													</option>
													<option value='IAD'>
														IAD
													</option>
												</select>
											</td>

										</tr>
										<tr>
											<td class="tableTextBold">
												Pension Option Received:
											</td>
											<td  align="left">
												<select name="wetherOption" tabindex="20" class="TextField">
													<option value="">
														[Select One]
													</option>
													<option value='A'>
														A
													</option>
													<option value='B'>
														B
													</option>
													<option value='No'>
														No
													</option>
												</select>
											</td>
											<td class="tableTextBold">
												Whether Form2
												<br />
												Nomination Received:
											</td>
											<td  align="left">
												<select name="form1" tabindex="21" onchange="show();" class="TextField">
													<option value="">
														[Select One]
													</option>
													<option value='Yes'>
														Yes
													</option>
													<option value='No'>
														No
													</option>
												</select>
											</td>

 
										</tr>
										<tr>
											<td class="tableTextBold" >
												Remarks:
											</td>
											<td  align="left">
												<TEXTAREA NAME="remarks" class="TextField" maxlength="150" cols="20" tabindex="22" rows=""></TEXTAREA>

												<!--<input type="text" maxlength="150" name="remarks"  cols="17" >
								-->
											</td>
											<td colspan="2" >&nbsp;&nbsp;&nbsp;</td>
										</tr>
						
										 
									 
										</table>
								 
									
									<table align="center">
									  	 	
											
											<tr>
										<td>
											<table align="center" border="0">
											<tbody id="familytable">
											
											 <tr>
												<td class="tableTextBold" nowrap="nowrap"> 
													Family Details 
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
														&nbsp;<b><img alt="Add New Record" src="<%=basePath%>/images/addIcon.gif" onclick="callFamily('','','','');" tabindex="27"/></b>
													</td>
												</tr>	
												<tr>
												</tr>
												</tbody>
												 
											</table>
											 
										 <div id="divFamily1">
										<table>
											 
										</table>
									</div>
							 	 

		</table>				 		<table cellspacing="0" cellpadding="0" border="0" align="center">
								<tr id="divNomineeDetails">
								 <td>
								 	 
											 <table align="center">
									  	
											 
											<tr>
										<td colspan="4" >
											<table align="center" border="0">
											<tbody id="nomineetable">
											<tr>
												<td class="tableTextBold" nowrap="nowrap"> 
													Nomination for PF
												</td>

											</tr>
											 
											<tr>																					
											<td class="tableTextBold" nowrap="nowrap">
												Equal Share check:&nbsp;
											</td>
											 
											<td>											
												
												<html:checkbox property="equalshare" onclick="dispTotShare();"></html:checkbox>
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
													<td class="tableTextBold" align="center">
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
								 
										
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table align="center" border="0" cellspacing="0" cellpadding="0">
								
										<tr>
											
											<td align="center">
												<input type="button" class="btn" value="Add" class="btn" onclick="personalAdd()" tabindex="35" />
												<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" tabindex="36" />
												<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="37" />
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
