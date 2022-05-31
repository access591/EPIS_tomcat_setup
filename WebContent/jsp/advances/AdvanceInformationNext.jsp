<!--
/*
  * File       : AdvanceInformationNext.jsp
  * Date       : 09/26/2009
  * Author     : Suresh Kumar Repaka 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">

		<SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime.js"></SCRIPT>

		<script type="text/javascript">
		var wthdrwstatus=false;
		
		function frmPrsnlBack(){
		
		var frmName;
		
		if(document.forms[0].advanceType.value=='CPF'){
		  frmName='advancesnew';
		}else if(document.forms[0].advanceType.value=='pfw'){
		  frmName='pfwnew';
		}
	
				url="<%=basePath%>loadAdvance.do?method=loadAdvanceFormBack&frm_name="+frmName;
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		}
		
	
		
		function calculateAge(){	
		
		
			if (!convert_date(document.forms[0].fmlydob))
			{
				document.forms[0].fmlydob.value='';
				document.forms[0].fmlydob.focus();
				return false;
			}
			if(document.forms[0].fmlydob.value!=""){			
			
					var dob=document.forms[0].fmlydob.value;
					var yearval=dob.substring(6,dob.length);	
					var today = new Date();
					var advanceType=document.forms[0].advanceType.value;
					var purposeType=document.forms[0].purposeType.value;
					
					var noOfYears=today.getYear()-yearval;
					document.forms[0].fmlyage.value=noOfYears;	
					if(noOfYears==0){
					     alert('Invalid Date Of Birth');
					     document.forms[0].fmlydob.value='';
		                 document.forms[0].fmlydob.focus();
				  		 return false;					
					}								
					var chkFmlyReltion='';
					
					if(document.getElementById("empfmlydtls")!=null){
						chkFmlyReltion=document.getElementById("empfmlydtls").value;
					}else if(document.getElementById("advancecostexp")!=null){
						chkFmlyReltion=document.getElementById("advancecostexp").value;
					}else if(document.getElementById("cpfmarriageexp")!=null){
						chkFmlyReltion=document.getElementById("cpfmarriageexp").value;
					}else if(document.getElementById("pfwhetype")!=null && advanceType=='CPF'){
						chkFmlyReltion=document.getElementById("pfwhetype").value;
					}else if(document.getElementById("pfwhetype")!=null && advanceType=='PFW'){
						chkFmlyReltion=document.getElementById("pfwhetype").value;
					}
					if((advanceType=='pfw'&& purposeType.toLowerCase()=='marriage') ||(advanceType=='CPF' && purposeType.toLowerCase()=='obmarriage') ){
					if(chkFmlyReltion=="Son" && document.forms[0].fmlyage.value<21){
		                 alert('Son Age Should Be Greater Than or Equal To 21');
		                 document.forms[0].fmlydob.value='';
		                 document.forms[0].fmlyage.focus();
				  		 return false;
		            }    		                    
		            if(chkFmlyReltion=="Daughter" && document.forms[0].fmlyage.value<17){
		                 alert('Daughter Age Should Be Greater Than or Equal To 18');
		                 document.forms[0].fmlydob.value='';
		                 document.forms[0].fmlyage.focus();
				  	     return false;
		            }       
					}
			
			}
			return true;
			
		}
		function selectSelfInfo(){
					var chkFmlyReltion='';
					var advanceType=document.forms[0].advanceType.value;
					var purposeType=document.forms[0].purposeType.value;

					if(document.getElementById("empfmlydtls")!=null && advanceType=='pfw' && purposeType=='Marriage'){
						chkFmlyReltion=document.getElementById("empfmlydtls").value;
					}else if(document.getElementById("empfmlydtls")!=null && advanceType=='CPF' && purposeType=='Marriage'){
						chkFmlyReltion=document.getElementById("empfmlydtls").value;
					}else if(document.getElementById("advancecostexp")!=null){
						chkFmlyReltion=document.getElementById("advancecostexp").value;
					}else if(document.getElementById("cpfmarriageexp")!=null){
						chkFmlyReltion=document.getElementById("cpfmarriageexp").value;
					}else if(document.getElementById("pfwhetype")!=null){
						chkFmlyReltion=document.getElementById("pfwhetype").value;
					}
					if(chkFmlyReltion=='Self' || chkFmlyReltion=='self'){
					   document.forms[0].fmlyempname.value=document.forms[0].employeeName.value;
					   document.forms[0].fmlydob.value=document.forms[0].dateOfBirth.value;
					   calculateAge();
					   document.forms[0].fmlyage.focus();
					}else{
					  document.forms[0].fmlydob.value='';
					  document.forms[0].fmlyage.value='';
					  document.forms[0].fmlyempname.value='';
					  document.forms[0].fmlyempname.focus();
					}		
					
		}
		function selectBankDetail(){		
				var bankDetailsInfo='';
				bankDetailsInfo=document.forms[0].bankdetail.options[document.forms[0].bankdetail.selectedIndex].value;
				
		  		if(bankDetailsInfo=='bank'){   		    
		   			document.getElementById("bankdet").style.display="block";
		   		 	document.getElementById("partydet").style.display="none";
		   		}else if(bankDetailsInfo=='party'){  
		   		    if(document.forms[0].advanceType.value=="pfw"){		   
		   		    		        
					    document.getElementById("bankdet").style.display="block";
		   		    	document.getElementById("partydet").style.display="block";
		   		    }else{
		   		    //DEFAULT SELECTION IS EMPLOYEE FOR CPF
		   		   	 document.getElementById("bankdet").style.display="none";
		   		        alert("Party Type is not available For CPF Advance");
		   		        document.forms[0].bankdetail.value ="bank";
		   		        document.getElementById("bankdet").style.display="block";
		   		        return false;
		   		    }
		   		}else{
		   			document.getElementById("bankdet").style.display="none";
		   		 	document.getElementById("partydet").style.display="none";
		   		} 	
		      
		}
		function saveAdvanceInfo(){
		 
		 		var ex=/^[0-9]+$/;
		 		var lodcheckedlngth='';
		 		var lodhba='',total='',i=0,advanceType='',purposeType='';
		 		advanceType=document.forms[0].advanceType.value;
		 		purposeType=document.forms[0].purposeType.value;
		 		var wthdrwlStatus="";
		 		 
		 		if(advanceType=='pfw' && purposeType!='SUPERANNUATION'){
		 			wthdrwlStatus=document.forms[0].chkwthdrwlinfo.options[document.forms[0].chkwthdrwlinfo.selectedIndex].value;
		 		}

		 		if((advanceType=='pfw'||advanceType=='CPF') && purposeType=='Marriage'){
		 		
			 		if(document.getElementById("empfmlydtls").value=='NO-SELECT'){
			  			alert('Please Select Marriage Purposes');
			  			document.forms[0].empfmlydtls.focus();
			  			return false;
			  		}	
			  		if(document.forms[0].marriagedate!=''){
			 		if (!convert_date(document.forms[0].marriagedate))
						{
							document.forms[0].marriagedate.focus();
							return false;
						}
					}					  		
                }else if(advanceType=='pfw' && purposeType=='HBA'){
                
                  hbawthdrwlStatus=document.forms[0].hbadrwnfrmaai.options[document.forms[0].hbadrwnfrmaai.selectedIndex].value;
               
                    if(document.getElementById("hbapurposetype").value=='NO-SELECT'){
			  			alert('Please Select HBA Purposes');
			  			document.forms[0].hbapurposetype.focus();
			  			return false;
			  		}	
			  		if(document.getElementById("hbapurposetype").value!='NO-SELECT'){
			  			 	if(document.forms[0].actualcost!=""){		  		
			  		 	if(!ex.test(document.forms[0].actualcost.value) && document.forms[0].actualcost.value!=""){
				  			alert('Actual Amount Should Be Numeric');
				  			document.forms[0].actualcost.focus();
				  			return false;
			  			}
		  		    }
			  	   if(document.getElementById("hbapurposetype").value=='repaymenthba'){			  		         
			  		     }else{
			  		     	 if(document.forms[0].hbaownername.value==""){
			  		          alert("Please Enter Owner Name");
			  		          document.forms[0].hbaownername.focus();
			  				  return false;
			  		         }
			  		         
			  		         if (!ValidateName(document.forms[0].hbaownername.value)){
								alert("Numeric/Invalid characters are not allowed for Owner Name");
								document.forms[0].hbaownername.focus();
								return false;
		  	  	 			}
			  		         
			  		        if(document.forms[0].hbaownerarea.value==""){
			  		          alert("Please Enter Area");
			  		          document.forms[0].hbaownerarea.focus();
			  				  return false;
			  		        }
			  		     }
			  		     
			  		     
			  		}
			  		if(hbawthdrwlStatus=='NO-SELECT'){
		  			alert('Please Select Whether HBA is drawn from AAI field');
		  			document.forms[0].hbadrwnfrmaai.focus();
		  			return false;
		  		}
		  		if(hbawthdrwlStatus=='Y'){		  		
		  		    if(document.forms[0].hbawthdrwlpurpose.value==""){ 
			  			alert('Please Enter Purpose ');
			  			document.forms[0].hbawthdrwlpurpose.focus();
			  			return false;
		  			}
		  			
		  			if(document.forms[0].hbawthdrwlamount.value==""){ 
			  			alert('Please Enter Amount');
			  			document.forms[0].hbawthdrwlamount.focus();
			  			return false;
		  			}	
		  			
		  			if(document.forms[0].hbawthdrwlamount!=""){		  		
			  		 	if(!ex.test(document.forms[0].hbawthdrwlamount.value) && document.forms[0].hbawthdrwlamount.value!=""){
				  			alert('Amount Should Be Numeric');
				  			document.forms[0].hbawthdrwlamount.focus();
				  			return false;
			  			}
		  		    }
		  		
		  			if(document.forms[0].hbawthdrwladdress.value==""){ 
			  			alert('Please Enter Address');
			  			document.forms[0].hbawthdrwladdress.focus();
			  			return false;
		  			}		  				  			
		  		
		  		}
                
                }else if((advanceType=='pfw' && purposeType=='HE') || (advanceType=='CPF' && purposeType=='education')){
                	 if(document.getElementById("pfwhetype").value=='NO-SELECT'){
                	     
                	    if(purposeType=='HE') 
			  			alert('Please Select Higher Education Purposes');
			  			else
			  			alert('Please Select Higher Education');
			  			
			  			document.forms[0].pfwhetype.focus();
			  			return false;
			  		 }	
			  		 
			  		 if(document.getElementById("pfwhetype").value!='NO-SELECT'){
			  		     if(document.forms[0].nmcourse.value==""){
			  		        alert("Please Enter Name Of Course");
			  		        document.forms[0].nmcourse.focus();
			  				return false;
			  		     }			  		 
			  		 }
			  		 
			  		 if(document.getElementById("pfwhetype").value!='NO-SELECT'){
			  		     if(document.forms[0].nminstitue.value==""){
			  		        alert("Please Enter Name of Institution");
			  		        document.forms[0].nminstitue.focus();
			  				return false;
			  		     }			  		 
			  		 }
			  		 
			  		 if(purposeType=='HE'){
				  		 if(document.getElementById("pfwhetype").value!='NO-SELECT'){
				  		     if(document.forms[0].curseduration.value==""){
				  		        alert("Please Enter Duration of course");
				  		        document.forms[0].curseduration.focus();
				  				return false;
				  		     }			  		 
				  		 }
			  		 }
			  		 
			  		 if(purposeType=='HE') {
				  		 if(document.getElementById("pfwhetype").value!='NO-SELECT'){			  		 
				  		     if(document.getElementById("helastexaminfo").value=='NO-SELECT'){
				  		        alert("Please Enter Last examination passed");
				  		        document.forms[0].helastexaminfo.focus();
				  				return false;
				  		     }			  		 
				  		 }
                     }
                
                }else if(advanceType=='CPF' && (purposeType=='cost' || purposeType=='illness')){
                        if(document.getElementById("advancecostexp").value=='NO-SELECT'){
                            if(purposeType=='cost')
				  			alert('Please Select Cost Of Passage');
				  			else
				  			alert('Please Select Illness Expenses');
				  			
				  			document.forms[0].advancecostexp.focus();
				  			return false;
			  		    }	
                }else if(advanceType=='CPF' && purposeType=='obligatory'){ 
                  
				  		 if(document.getElementById("advanceobligexp").value=='Other Ceremonies'){
				  		 
				  		       if(trim(document.forms[0].oblcermoney.value)==""){			  		     
					  		        alert("Please Enter  Other Ceremonies Expenses");
					  		        document.forms[0].oblcermoney.focus();
					  				return false;				  		    		  		 
				  				}
				  		 }              
                
                }else if(advanceType=='CPF' && purposeType=='obMarriage'){
                 	      if(document.getElementById("cpfmarriageexp").value=='NO-SELECT'){			  		     
					  	        alert("Please Select Marriage Expenses");
					  	        document.forms[0].cpfmarriageexp.focus();
					  			return false;				  		    		  		 
				  		  
				  		  }
				  if(document.forms[0].marriagedate!=''){
			 		if (!convert_date(document.forms[0].marriagedate))
						{
							document.forms[0].marriagedate.focus();
							return false;
						}
					}	
				  		  
                }               
		  		
		  		if (!ValidateName(document.forms[0].bankempname.value)){
					alert("Numeric/Invalid characters are not allowed");
					document.forms[0].bankempname.focus();
					return false;
		  	   }
		  	   	if (!ValidateName(document.forms[0].bankname.value)){
					alert("Numeric/Invalid characters are not allowed");
					document.forms[0].bankname.focus();
					return false;
		  	   }
		  	   if(advanceType=='pfw'){
		  	  	if(document.forms[0].modeofpartyname.value!='' ){
		  	   	if (!ValidateName(document.forms[0].modeofpartyname.value)){
					alert("Numeric/Invalid characters are not allowed");
					document.forms[0].modeofpartyname.focus();
					return false;
		  	  	 }
		  	   }
		  	   }
			  	if(advanceType=='CPF' && !(purposeType=='defence'  || purposeType=='others' || purposeType=='obligatory') ){
			     if(document.forms[0].fmlyempname.value==""){
				  			
				  			if(purposeType=="education")
				  			alert('Please Enter Name Of Dependent');
				  			else
				  			alert('Please Enter Name');
				  			document.forms[0].fmlyempname.focus();
				  			return false;
			  		 }
		  	  
		  	   	if (!ValidateName(document.forms[0].fmlyempname.value)){
					alert("Numeric/Invalid characters are not allowed");
					document.forms[0].fmlyempname.focus();
					return false;
		  	  	 }
		  	   
		  	   }else if(advanceType=='pfw' && (purposeType=='Marriage') ){			  	   	  	
		  	   	  	 
		  	   	  	 if(document.forms[0].fmlyempname.value==""){
				  			alert('Please Enter Name');
				  			document.forms[0].fmlyempname.focus();
				  			return false;
			  		 }
			  			
		  	      	if (!ValidateName(document.forms[0].fmlyempname.value)){
					alert("Numeric/Invalid characters are not allowed");
					document.forms[0].fmlyempname.focus();
					return false;
		  	  	 }
		  	   }
		 
		  	   
		 		if(advanceType=='pfw' && purposeType=='HBA'){
		 			lodcheckedlngth=document.forms[0].LODHBA.length;
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODHBA[i].checked==true){
		 					lodhba=document.forms[0].LODHBA[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 		}
		 		
		 		if(advanceType=='pfw' && purposeType=='SUPERANNUATION'){
		 			lodcheckedlngth=document.forms[0].LODPFWSA.length;
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODPFWSA[i].checked==true){
		 					lodhba=document.forms[0].LODPFWSA[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 		}
		 		var expanse="",flag;
		 		if(advanceType=='CPF' && purposeType!=''){		 		   
		 			if(purposeType=='obligatory'){		 			 
		 				if(document.forms[0].advanceobligexp.value!="undefined"){		 				  
		 					if(document.forms[0].advanceobligexp.value=='Other Ceremonies'){		 					
		 						flag==false;
		 					}else{		 					
		 						flag=true;
		 					}
		 				}
		 			}else if(purposeType=='defence' || purposeType=='others'){
		 			  flag=false;
		 			}else{
		 			  flag=true;
		 			}
		 				
		 			
		 			if(flag==true){
		 			var dt=document.forms[0].fmlydob.value;
		 			var age=document.forms[0].fmlyage.value;
		 		
		 		   if(dt=="") {
   		  			alert("Please Enter Date of Birth");
   		 			document.forms[0].fmlydob.focus();
   		  			return false;
   		  		   }
   		  		   var month="";
		   		  if(!dt==""){
		   		   var date1=document.forms[0].fmlydob;
		   	       var val1=convert_date(date1);
		   		    var now = new Date();
		   		   var birthday1= document.forms[0].fmlydob.value;
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
        			document.forms[0].fmlydob.value=birthday1;
        		   if(birthDate > now){
					    alert("DateofBirth cannot be greater than Currentdate");
						document.forms[0].fmlydob.focus();
					   return false;
				   }
   		    		if(val1==false){
   		    			return false;
   		    		}
   		    		
   		    			if (!convert_date(document.forms[0].fmlydob))
						{
							document.forms[0].fmlydob.focus();
							return false;
						}
		 			}
		 		
   		    			
		 		
   		        }
   		        
			}else if(advanceType=='pfw' && purposeType!='' ){
					if(purposeType=='HBA' || purposeType=='HE' || purposeType=='SUPERANNUATION'|| purposeType=='Pandemic'){
						flag=false;
					}else{
						 flag=true;
					}
		 			
		 			if(flag==true){
		 				var dt=document.forms[0].fmlydob.value;
		 		   if(dt=="") {
   		  			alert("Please Enter Date of Birth");
   		 			document.forms[0].fmlydob.focus();
   		  			return false;
   		  		   }
   		  		   var month="";
		   		  if(!dt==""){
		   		   var date1=document.forms[0].fmlydob;
		   	       var val1=convert_date(date1);
		   		    var now = new Date();
		   		   var birthday1= document.forms[0].fmlydob.value;
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
        			document.forms[0].fmlydob.value=document.forms[0].fmlydob.value;
        		   if(birthDate > now){
					    alert("DateofBirth cannot be greater than Currentdate");
						document.forms[0].fmlydob.focus();
					   return false;
				   }
   		    		if(val1==false){
   		    			return false;
   		    		}
   		    		
		 			}
		 			
   		        }
			}
		 	if(advanceType=='CPF' && purposeType=='education'){
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
		 	
		 	if(advanceType=='pfw' && purposeType=='Marriage'){
		 			lodcheckedlngth=document.forms[0].LODPFWMARR.length;		 			
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODPFWMARR[i].checked==true){
		 					lodhba=document.forms[0].LODPFWMARR[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 	}
		 		 	if(advanceType=='pfw' && purposeType=='HE'){
		 			lodcheckedlngth=document.forms[0].LODPFWHE.length;		 			
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODPFWHE[i].checked==true){
		 					lodhba=document.forms[0].LODPFWHE[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 	}
		 		if(advanceType=='CPF' && purposeType=='obMarriage'){
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
		 	
		 	if(purposeType!='SUPERANNUATION'){
		 	if(wthdrwlStatus=='NO-SELECT'){
		  			alert('Please Select Y/N for Give Previous Withdrawal Details field');
		  			document.forms[0].chkwthdrwlinfo.focus();
		  			return false;
		  		}
		  		}
		  		
		 		
		 	if((advanceType=='pfw' || advanceType=='CPF') && purposeType!=''){
		 	if(document.getElementById("bankdetail").value=='bank'){
		 	      if(document.forms[0].bankempname.value==""){
				  		alert('Please Enter Name(Appearing in the saving bank A/c)');
				  		document.forms[0].bankempname.focus();
				  		return false;
			  	  }
			  	  
			  	  if(document.forms[0].bankname.value==""){
				  		alert('Please Enter Bank name');
				  		document.forms[0].bankname.focus();
				  		return false;
			  	  }
			  	  if(document.forms[0].branchaddress.value==""){
				  		alert('Please Enter Branch address');
				  		document.forms[0].branchaddress.focus();
				  		return false;
			  	  }
			  	  if(document.forms[0].banksavingaccno.value==""){
				  		alert('Please Enter Saving bank A/c no');
				  		document.forms[0].banksavingaccno.focus();
				  		return false;
			  	  }
			  	  if(document.forms[0].bankemprtgsneftcode.value==""){
				  		alert('Please Enter RTGS/NEFT Code');
				 		document.forms[0].bankemprtgsneftcode.focus();
				  		return false;
			  	    }	
			  	 
			  	//  if(document.forms[0].bankempmicrono.value==""){
				 // 		alert('Please Enter MICR No');
				 // 		document.forms[0].bankempmicrono.focus();
				 // 		return false;
			  	 // }	 	
			  }
		 	      
		 	}		 	
		 	
		 	if(advanceType=='pfw' &&  purposeType!='SUPERANNUATION'){ 
		 	if(wthdrwstatus==false){
		 	   alert('Please enter Give Previous Withdrawal Details');
		 	   return false;		 	   
		 	}
		 	}
		 	
		 	var frmName;
		 	
		 	if(advanceType=='pfw')
			       frmName='pfwnew';
			    else
			      frmName='advancesnew';
		 	
		 	
		 	url="<%=basePath%>loadAdvance.do?method=saveAdvacneNextInfo&lodinfo="+total+"&frm_name="+frmName;
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		 
		 }
		function chkWthDrwl(){
					var wthdrwlStatus="";
					wthdrwlStatus=document.forms[0].chkwthdrwlinfo.options[document.forms[0].chkwthdrwlinfo.selectedIndex].value;
		  			
		  			if(wthdrwlStatus=='Y'){   		    
		   		   		document.getElementById("loadwthdrwdet").style.display="block";
		   		    }else if(wthdrwlStatus=='N'){   	
						document.getElementById("loadwthdrwdet").style.display="none";
						 wthdrwstatus=true;
		   		    }
		   		    		   		    
		}
		function chkHBAWthDrwl(){		            
					var wthdrwlStatus="";
					wthdrwlStatus=document.forms[0].hbadrwnfrmaai.options[document.forms[0].hbadrwnfrmaai.selectedIndex].value;
		  			if(wthdrwlStatus=='Y'){   		    
		   		   		document.getElementById("chk_HBA_wthdrwn_dtl").style.display="block";
		   		    }else if(wthdrwlStatus=='N'){   	
						document.getElementById("chk_HBA_wthdrwn_dtl").style.display="none";
		   		    }
		}
		function loadNext(purposeType,advanceType){
		var bankDetailsInfo='';
		
		   if(advanceType=='advance'){
		  		bankDetailsInfo=document.forms[0].bankdetail.options[document.forms[0].bankdetail.selectedIndex].value;
		  		
		  		if(bankDetailsInfo=='bank'){   		    
		   			document.getElementById("bankdet").style.display="block";
		   		 	document.getElementById("partydet").style.display="none";
		   		}
		   }
			if(purposeType=='HBA'){
				document.getElementById("repaymentHbaLoan").style.display="none";
				document.getElementById("repaymentHbaLoanInfo").style.display="none";
				document.getElementById("repaymentHbaLoanInfo2").style.display="none";
				document.getElementById("hbaAnyOthers").style.display="none";
				document.forms[0].hbapurposetype.focus();
				
			}else if(purposeType=='Marriage'){
				//document.getElementById("repaymentHbaLoan").style.display="none";
				//document.getElementById("repaymentHbaLoanInfo").style.display="none";
				//document.getElementById("repaymentHbaLoanInfo2").style.display="none";
				//document.getElementById("hbaAnyOthers").style.display="none";
					document.forms[0].empfmlydtls.focus();
			}else if(purposeType=='HE'){
			    //document.getElementById("repaymentHbaLoan").style.display="none";
				//document.getElementById("repaymentHbaLoanInfo").style.display="none";
				//document.getElementById("repaymentHbaLoanInfo2").style.display="none";
				//document.getElementById("hbaAnyOthers").style.display="none";
				
					document.forms[0].pfwhetype.focus();
			}else if(purposeType=='cost'){					
					document.forms[0].advancecostexp.focus();
			}else if(purposeType=='obligatory'){			
					document.forms[0].oblcermoney.focus();
			}else if(purposeType=='obmarraige'){			
					document.forms[0].cpfmarriageexp.focus();		
			}else if(purposeType=='illness'){
					document.forms[0].advancecostexp.focus();
			}else if(purposeType=='education'){			
					document.forms[0].pfwhetype.focus();
			}	
				
		}
	function chkhbapurposedtl(){
			var hbaOptions="";
			hbaOptions=document.forms[0].hbapurposetype.options[document.forms[0].hbapurposetype.selectedIndex].value;
			if(hbaOptions=='repaymenthba'){
				document.getElementById("repaymentHbaLoan").style.display="block";
				document.getElementById("repaymentHbaLoanInfo").style.display="block";
				document.getElementById("repaymentHbaLoanInfo2").style.display="block";
				document.getElementById("hbaAnyOthers").style.display="none";
			}else if(hbaOptions=='hbaothers'){
				document.getElementById("repaymentHbaLoan").style.display="none";
				document.getElementById("repaymentHbaLoanInfo").style.display="none";
				document.getElementById("repaymentHbaLoanInfo2").style.display="none";
				document.getElementById("hbaAnyOthers").style.display="block";
			}else{
				document.getElementById("repaymentHbaLoan").style.display="none";
				document.getElementById("hbaAnyOthers").style.display="none";
				document.getElementById("repaymentHbaLoanInfo").style.display="none";
				document.getElementById("repaymentHbaLoanInfo2").style.display="none";
			}
	}
	
	function moreRecords(windowname){	
	var pensionNo,url,flag;
	var exp=/^[0-9]+$/;		
		
		
		var withDrawStr=document.forms[0].wthdrwlist.value; 
		var advanceType=document.forms[0].advanceType.value;
		
		
		
		if(advanceType=='pfw')
			       frmName='pfwnew';
			    else
			      frmName='advancesnew';
		
	var href;
	   		href="loadAdvance.do?method=previousWithDrawalDetails&frm_withDrawStr="+encodeURIComponent(withDrawStr)+"&frm_name="+frmName;
			progress=window.open(href, windowname, 'width=550,height=300,statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
	}	
	
	
	function getWithDrawalDetails(ExpList){	
	     //alert(ExpList);	
	   	 document.forms[0].wthdrwlist.value=ExpList;	 
	   	 wthdrwstatus=true;
	}   	  
				  		   	
 </script>
	</head>
	<%
	AdvanceBasicBean basicBean=new AdvanceBasicBean();
	if(request.getAttribute("advanceBean")!=null){
		basicBean=(AdvanceBasicBean)request.getAttribute("advanceBean");
		request.setAttribute("advanceBasicBean",basicBean);
	}
%>

	<body class="BodyBackground" onload="loadNext('<bean:write property="purposeType" name="advanceBean"/>','<bean:write property="advanceType" name="advanceBean"/>');">
		<html:form method="post" action="loadAdvance.do?method=continueWithPTWAdvanceOptions">


			<%=ScreenUtilities.screenHeader(request.getAttribute("screenTitle").toString())%>

			<table width="680" border="0" cellspacing="3" cellpadding="0">





				<tr>
					<td height="15%" colspan="4">

						<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">

							<html:hidden property="pensionNo" />
							<html:hidden property="employeeName" />
							<html:hidden property="dateOfBirth" />
							<html:hidden property="subscriptionAmt" />
							<html:hidden property="advReqAmnt" />
							<html:hidden property="pfwReqAmnt" />
							<html:hidden property="recEmpSubAmnt" />
							<html:hidden property="recEmpConrtiAmnt" />
							<html:hidden property="firstInsSubAmnt" />
							<html:hidden property="firstInsConrtiAmnt" />
							<html:hidden property="emoluments" />
							<html:hidden property="purposeType" />
							<html:hidden property="advanceType" />
						 
							<html:hidden property="cpftotalinstall" />
							<html:hidden property="region" />
							<html:hidden property="trust" />
							<html:hidden property="designation" />
							<html:hidden property="department" />
							<html:hidden property="dateOfJoining" />
							<html:hidden property="station" />
							<html:hidden property="wthdrwlist" />
							<html:hidden property="placeofposting" />


							<bean:define id="selPurposeType" name="advanceBean" property="purposeType" type="String" />
							 <logic:equal property="pfwPurHBA" value="<%=selPurposeType%>" name="advanceForm">
							 <jsp:include page="AdvancePFWHBADetails.jsp"></jsp:include>
							 </logic:equal>
							<!--Marriage Purpose  -->

							<logic:equal property="pfwPurMarriage" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">

											<tr>
												<td class="tableTextBold">
													Marriage Purposes:
												</td>
												<td>
													<html:select styleClass="TextField" property="empfmlydtls" style="width:119px" onchange="javascript:selectSelfInfo();">
														<html:option value='NO-SELECT'>Select One</html:option>
														<html:option value='self'>Self</html:option>
														<html:option value='Son'>Son</html:option>
														<html:option value='Daughter'>Daughter</html:option>
														<html:option value='Dependent Brother'>Dependent Brother</html:option>
														<html:option value='Dependent Sister'>Dependent Sister</html:option>
													</html:select>
													<!-- 	<select name='select_marry_prs_dtl' Style='width:130px'>
													<option value="NO-SELECT">
														Select One
													</option>
													<option value='self'>
														Self
													</option>
													<option value='son'>
														Son
													</option>
													<option value='daughter'>
														Daughter
													</option>
													<option value='brother'>
														Dependent Brother
													</option>
													<option value='sister'>
														Dependent Sister
													</option>
												</select>-->
												</td>

												<td class="tableTextBold">
													Name:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyempname" maxlength="50" />
												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Date of Birth:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlydob" onblur="calculateAge();" maxlength="12" />
													&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlydob');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
												</td>

												<td class="tableTextBold">
													Age:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyage" readonly="true" maxlength="3" />
												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Marriage Date:
												</td>
												<td>
													<html:text styleClass="TextField" property="marriagedate" maxlength="12" />
													&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].marriagedate');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
												</td>

												<td class="tableTextBold">
													Birth certificate / age
													<br />
													proved:
												</td>
												<td>
													<input type="checkbox" name="brthcertprove" value="Y">
												</td>

											</tr>



										</table>
									</td>
								</tr>
							</logic:equal>
							<!--Higher Education Purpose  -->
							<logic:equal property="pfwPurHE" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
											<tr>
												<td class="tableTextBold">
													Higher Education
													<br />
													Purposes:
												</td>
												<td>
													<html:select styleClass="TextField" property="pfwhetype" style="width:130px">
														<html:option value="NO-SELECT"> Select One </html:option>
														<html:option value='Son'> Son </html:option>
														<html:option value='Daughter'> Daughter </html:option>

													</html:select>

												</td>
												<td class="tableTextBold">
													Name:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyempname" maxlength="50" />
												</td>

											</tr>
											<tr>
												<td class="tableTextBold">
													Date of Birth:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlydob" onblur="calculateAge();" maxlength="12" />
													&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlydob');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
												</td>

												<td class="tableTextBold">
													Age:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyage" readonly="true" maxlength="3" />
												</td>
											</tr>
											<tr>

												<td class="tableTextBold">
													Name of Course:
												</td>
												<td>
													<html:text styleClass="TextField" property="nmcourse" maxlength="50" />
												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Name of Institution:
												</td>
												<td>
													<html:text styleClass="TextField" property="nminstitue" maxlength="50" />

												</td>

												<td class="tableTextBold">
													Address of Institution:
												</td>
												<td>
													<html:text styleClass="TextField" property="addrsinstitue" maxlength="150" />

												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Duration of course:
												</td>
												<td>
													<html:text styleClass="TextField" property="curseduration" maxlength="25" />

												</td>

												<td class="tableTextBold">
													Last examination passed:
												</td>
												<td>
													<html:select styleClass="TextField" property="helastexaminfo">
														<html:option value="NO-SELECT"> Select One </html:option>
														<html:option value='Higher secondary'> Higher secondary (10 th) </html:option>
														<html:option value='Senior secondary'> Senior secondary (10+2) </html:option>
														<html:option value='Graduation'> Graduation </html:option>
														<html:option value='Post Graduation'> Post Graduation </html:option>
													</html:select>
													<!-- <select name='lastexam' Style='width:130px'>
													<option value="NO-SELECT">
														Select One
													</option>
													<option value='higher'>
														Higher secondary (10 th)
													</option>
													<option value='senior'>
														Senior secondary (10+2)
													</option>
													<option value='grad'>
														Graduation
													</option>
													<option value='pg'>
														Post Graduation
													</option>
												</select>-->
												</td>
											</tr>

										</table>


									</td>
								</tr>
							</logic:equal>
							<logic:equal property="cpfAdvCOP" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
											<tr>
												<td class="tableTextBold">
													Cost of Passage:
												</td>
												<td>
													<html:select styleClass="TextField" property="advancecostexp" onchange="javascript:selectSelfInfo();">
														<html:option value="NO-SELECT">Select One</html:option>
														<html:option value="Self">Self</html:option>
														<html:option value="spouse">Spouse</html:option>
														<html:option value="Son">Son</html:option>
														<html:option value="Daughter">Daughter</html:option>
													</html:select>
													<!-- <select name='select_cop_prse_dtl' Style='width:130px'>
													<option value="NO-SELECT">
														Select One
													</option>
													<option value="selfs">
														Self
													</option>
													<option value="spouse">
														Spouse
													</option>
													<option value="son1">
														Son
													</option>
													<option value="daughter1">
														Daughter
													</option>
												</select>-->
												</td>
												<td class="tableTextBold">
													Name:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyempname" maxlength="50" />
												</td>

											</tr>
											<tr>
												<td class="tableTextBold">
													Date of Birth:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlydob" onblur="calculateAge();" maxlength="12" />
													&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlydob');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
												</td>

												<td class="tableTextBold">
													Age:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyage" maxlength="3" readonly="true" />
												</td>
											</tr>
										</table>

									</td>
								</tr>
							</logic:equal>

							<logic:equal property="cpfAdvOEMarriage" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
											<tr>
												<td colspan="4">
													<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
														<tr>
															<td class="tableTextBold" width="27%">
																Marriage Expenses:
															</td>
															<td>
																<html:select styleClass="TextField" property="cpfmarriageexp" onchange="selectSelfInfo();">
																	<html:option value="NO-SELECT">Select One</html:option>
																	<html:option value="Self">Self Marriage</html:option>
																	<html:option value="Son">Dependent Son</html:option>
																	<html:option value="Daughter">Dependent Daughter</html:option>
																	<html:option value="Sister">Dependent Sister</html:option>
																</html:select>

															</td>
															<td class="tableTextBold">
																Name:
															</td>
															<td>
																<html:text styleClass="TextField" property="fmlyempname" maxlength="50" />
															</td>
														</tr>
														<tr>
															<td class="tableTextBold">
																Date of Birth:
															</td>
															<td>
																<html:text styleClass="TextField" property="fmlydob" onblur="calculateAge();" maxlength="12" />
																&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlydob');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
															</td>

															<td class="tableTextBold">
																Age:
															</td>
															<td>
																<html:text styleClass="TextField" property="fmlyage" maxlength="3" readonly="true" />
															</td>
														</tr>
														<tr>
															<td class="tableTextBold">
																Marriage Date:
															</td>
															<td>
																<html:text styleClass="TextField" property="marriagedate" maxlength="12" />
																&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].marriagedate');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
															</td>
															<td colspan="2">
																&nbsp;
															</td>
														</tr>
													</table>
												</td>
											</tr>

										</table>
									</td>
								</tr>

							</logic:equal>
							<logic:equal property="cpfAdvOE" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
											<tr>
												<td colspan="4">
													<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
														<tr>
															<td class="tableTextBold" width="27%">
																Obligatory Expenses:
															</td>
															<td>
																<html:text styleClass="TextField" property="advanceobligexp" value="Other Ceremonies" readonly="true" />

															</td>
															<td class="tableTextBold">
																Other Ceremonies:
															</td>
															<td>
																<html:text styleClass="TextField" property="oblcermoney" maxlength="60" />
															</td>
														</tr>

													</table>
												</td>
											</tr>

										</table>
									</td>
								</tr>

							</logic:equal>

							<logic:equal property="cpfAdvIE" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
											<tr>
												<td class="tableTextBold">
													Illness Expenses:
												</td>
												<td>
													<html:select styleClass="TextField" property="advancecostexp" onchange="javascript:selectSelfInfo();">
														<html:option value="NO-SELECT">Select One</html:option>
														<html:option value="Self">Self</html:option>
														<html:option value="spouse">Spouse</html:option>
														<html:option value="Son">Son</html:option>
														<html:option value="Daughter">Daughter</html:option>
													</html:select>

												</td>
												<td class="tableTextBold">
													Name:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyempname" maxlength="50" />
												</td>

											</tr>
											<tr>
												<td class="tableTextBold">
													Date of Birth:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlydob" onblur="calculateAge();" maxlength="12" />
													&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlydob');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
												</td>

												<td class="tableTextBold">
													Age :
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyage" maxlength="3" readonly="true" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:equal>

							<logic:equal property="cpfAdvEducation" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
											<tr>
												<td class="tableTextBold">
													Higher Education:
												</td>
												<td>
													<html:select styleClass="TextField" property="pfwhetype">
														<html:option value="NO-SELECT"> Select One </html:option>
														<html:option value='Son'> Son </html:option>
														<html:option value='Daughter'> Daughter </html:option>

													</html:select>
												</td>
												<td class="tableTextBold">
													Name of Dependent:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyempname" maxlength="50" />
												</td>

											</tr>
											<tr>
												<td class="tableTextBold">
													DateOfBirth:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlydob" onblur="calculateAge();" maxlength="12" />
													&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlydob');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
												</td>

												<td class="tableTextBold">
													Age:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyage" maxlength="3" readonly="true" />
												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Name of Course:
												</td>
												<td>
													<html:text styleClass="TextField" property="nmcourse" maxlength="50" />
												</td>

												<td class="tableTextBold">
													Duration of course:
												</td>
												<td>
													<html:text styleClass="TextField" property="curseduration" maxlength="25" />

												</td>
											</tr>
											<tr>

												<td class="tableTextBold">
													Name of Institution:
												</td>
												<td>
													<html:text styleClass="TextField" property="nminstitue" maxlength="50" />
												</td>


												<td class="tableTextBold">
													Address of Institution:
												</td>
												<td>
													<html:text styleClass="TextField" property="addrsinstitue" maxlength="150" />
												</td>
											</tr>
											<tr>


												<td class="tableTextBold">
													Recognized:
												</td>
												<td>
													<html:select styleClass="TextField" property="herecog">
														<html:option value='Y'> Yes </html:option>
														<html:option value='N'> No</html:option>
													</html:select>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<table align="left" cellpadding="0" cellspacing="0">
														<tr>
															<td class="ScreenSubHeading" align="align">
																&nbsp;
															</td>

														</tr>
													</table>
												</td>
											</tr>

										</table>
									</td>
								</tr>
							</logic:equal>

				<logic:notEqual property="pfwPurSuperannuation" value="<%=selPurposeType%>" name="advanceForm">
							<bean:define id="selAdvanceType" name="advanceBean" property="advanceType" type="String" />

							<logic:equal property="advanceType" value="<%=selAdvanceType%>" name="advanceForm">
								<tr>
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
											<tr>
												<td class="tableTextBold" width="28%">
													Give Previous Withdrawal Details:
												</td>

												<td width="19%">
													<html:select styleClass="TextField" property="chkwthdrwlinfo" onchange="chkWthDrwl();">
														<html:option value="NO-SELECT">Select One</html:option>
														<html:option value='Y'>Yes</html:option>
														<html:option value='N'>No</html:option>
													</html:select>

												</td>
												<td>
													<table border="0">
														<tr>
															<td id="loadwthdrwdet" style="display: none;">
																<img alt="" src="<%=basePath%>images/addIcon.gif" onclick="moreRecords('WithDrawalDetails');" />
															</td>
														</tr>
													</table>

													<!-- <select name='select_wthdrwn_dtl' onchange="chkWthDrwl();" style='width:130px'>
															<option value="NO-SELECT">Select One</option>
															<option value='Y'>Yes</option>
															<option value='N'>No</option>
															
														</select>-->
												</td>
											</tr>

										</table>
									</td>
								</tr>
							</logic:equal>
							</logic:notEqual>
							<tr>
								<td colspan="4">
									<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">

										<tr>
											<td colspan="4">
												<table align="left" cellpadding="1" cellspacing="1" border="0">
													<tr>
														<td class="tableTextBold" align="left" width="30%">
															Bank Detail For Payment Made To
														</td>
														<logic:equal property="advanceType" value="pfw" name="advanceForm">
															<td width="19%">
																<html:select styleClass="TextField" property="bankdetail" onchange="selectBankDetail();">
																	<option value="NO-SELECT">
																		Select One
																	</option>
																	<option value="bank">
																		Employee
																	</option>
																	<option value="party">
																		Party
																	</option>
																</html:select>
															</td>
														</logic:equal>
														<logic:equal property="advanceType" value="CPF" name="advanceForm">
															<td width="19%">
																<html:select styleClass="TextField" property="bankdetail" onchange="selectBankDetail();">
																	
																	<option value="bank">
																		Employee
																	</option>
																	<option value="party">
																		Party
																	</option>
																</html:select>
															</td>
														</logic:equal>
														<td>
															&nbsp;
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr id="bankdet" style="display:none">
											<td colspan="4">
												<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
													<html:hidden property="chkbankinfo" />
													<tr>
														<td class="tableTextBold" width="28%">
															Name(Appearing in the saving bank A/c):
														</td>
														<td>
															<html:text styleClass="TextField" property="bankempname" maxlength="50"/>
														</td>
														<td class="tableTextBold">
															Address of Employee:
														</td>
														<td>
															<html:text styleClass="TextField" property="bankempaddrs" maxlength="150"/>
														</td>
													</tr>
													<tr>
														<td class="tableTextBold">
															Bank name:
														</td>
														<td>
															<html:text styleClass="TextField" property="bankname" maxlength="50"/>
														</td>
														<td class="tableTextBold">
															Branch address:
														</td>
														<td>
															<html:text styleClass="TextField" property="branchaddress" maxlength="150"/>
														</td>
													</tr>
													<tr>
														<td class="tableTextBold">
															Saving bank A/c no:
														</td>
														<td>
															<html:text styleClass="TextField" property="banksavingaccno" maxlength="50"/>
														</td>
														<td class="tableTextBold">
															RTGS/NEFT Code:
														</td>
														<td>
															<html:text styleClass="TextField" property="bankemprtgsneftcode" maxlength="25"/>
														</td>
													</tr>
													<tr>
														<td class="tableTextBold">
															Employee Mail ID:
														</td>
														<td>
															<html:text styleClass="TextField" property="empmailid" maxlength="50"/>
														</td>
														<td class="tableTextBold">
															MICR No :
														</td>
														<td>
															<html:text styleClass="TextField" property="bankempmicrono" maxlength="25"/>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<bean:define id="selAdvanceType1" name="advanceBean" property="advanceType" type="String" />
							<logic:equal property="advanceType" value="<%=selAdvanceType1%>" name="advanceForm">
								<tr id="partydet" style="display:none">
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">

											<tr>
												<td colspan="4">
													<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">

														<tr>
															<td class="tableTextBold" width="28%">
																Party Name:
															</td>
															<td width="35%">
																<html:text styleClass="TextField" property="modeofpartyname" size="40" maxlength="150"/>
															</td>
															<td class="tableTextBold" width="13%">
																Party Address:
															</td>
															<td>
																<html:text styleClass="TextField" property="modeofpartyaddrs" maxlength="150"/>
															</td>
														</tr>

													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:equal>
							<logic:equal property="pfwPurHBA" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td>
										<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
											<tr>
												<td colspan="4">
													<table align="left" cellpadding="0" cellspacing="0">
														<tr>
															<td class="tableSideTextBold" align="left">
																List of Documents to be enclosed in HBA
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<table align="center" cellpadding="1" cellspacing="1">
														<tr>
															<td class="tableSideText">
																1.Title Deed of proposed seller
															</td>
															<td>
																<input type="checkbox" name="LODHBA" value="TDPS">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																2.Non-encumbrance certificate in respect of the dwelling
																<br />
																site/house to be purchased
															</td>
															<td>
																<input type="checkbox" name="LODHBA" value="NEDSHP">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																3.Agreement with the vendor for the purchase of site/house
															</td>
															<td>
																<input type="checkbox" name="LODHBA" value="AVPSH">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																4.Estimate of the cost of construction in the case of advance
																<br />
																for the construction of the house.
															</td>
															<td>
																<input type="checkbox" name="LODHBA" value="ECACH">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																5.Sanctioned construction plan
															</td>
															<td>
																<input type="checkbox" name="LODHBA" value="SCP">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																6.Any others
															</td>
															<td>
																<input type="checkbox" name="LODHBA" value="AO">
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:equal>
							<logic:equal property="pfwPurHE" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td>
										<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
											<tr>
												<td colspan="4">
													<table align="left" cellpadding="0" cellspacing="0">
														<tr>
															<td class="tableSideTextBold" align="left">
																List of Documents to be enclosed in Higher Education
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<table align="center" cellpadding="1" cellspacing="1">
														<tr>
															<td class="tableSideText">
																1.Proof of Age & Dependency
															</td>
															<td>
																<input type="checkbox" name="LODPFWHE" value="PFWHEPAD">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																2.Proof of selection/admission from the institute.
															</td>
															<td>
																<input type="checkbox" name="LODPFWHE" value="PFWPSAFI">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																3.Estimated expenditure of the course per year
															</td>
															<td>
																<input type="checkbox" name="LODPFWHE" value="PFWEECPY">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																4.Demand Letter from the institute.
															</td>
															<td>
																<input type="checkbox" name="LODPFWHE" value="PFWDLI">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																5.Duration of the course should be 3 year or more.
															</td>
															<td>
																<input type="checkbox" name="LODPFWHE" value="PFWDTCSTY">
															</td>
														</tr>

													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:equal>
							<logic:equal property="pfwPurMarriage" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td>
										<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
											<tr>
												<td colspan="4">
													<table align="left" cellpadding="0" cellspacing="0">
														<tr>
															<td class="tableSideTextBold" align="left">
																List of Documents to be enclosed in Marriage
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<table align="center" cellpadding="1" cellspacing="1">
														<tr>
															<td class="tableSideText">
																1.Marriage invitation card.
															</td>
															<td>
																<input type="checkbox" name="LODPFWMARR" value="PFWMIS">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																2.Proof of age and Dependency.
															</td>
															<td>
																<input type="checkbox" name="LODPFWMARR" value="PFWPID">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																3.Attested copy of Ration card/Voter’s Identity card/Tenth class certificate.
															</td>
															<td>
																<input type="checkbox" name="LODPFWMARR" value="PFWARVT">
															</td>
														</tr>


													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:equal>
							<logic:equal property="pfwPurSuperannuation" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td>
										<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
											<tr>
												<td colspan="4">
													<table align="left" cellpadding="0" cellspacing="0">
														<tr>
															<td class="tableSideTextBold" align="left">
																List of Documents to be enclosed in Superannuation
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<table align="center" cellpadding="1" cellspacing="1">
														<tr>
															<td class="tableSideText">
																1.Attaining of age of 54 Years
															</td>
															<td>
																<input type="checkbox" name="LODPFWSA" value="PFWAAY">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																2.One Year before actual retirement date of Superannuation
																 
															</td>
															<td>
																<input type="checkbox" name="LODPFWSA" value="PFWOYBARDS">
															</td>
														</tr>
														 
														 
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:equal>
							
							<logic:equal property="cpfAdvEducation" value="<%=selPurposeType%>" name="advanceForm">
								<tr>
									<td>
										<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
											<tr>
												<td colspan="4">
													<table align="left" cellpadding="0" cellspacing="0">
														<tr>
															<td class="tableSideTextBold" align="left">
																List of Documents to be enclosed in Higher Education
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<table align="center" cellpadding="1" cellspacing="1">
														<tr>
															<td class="tableSideText">
																1.Prospectus
															</td>
															<td>
																<input type="checkbox" name="LODCPFHE" value="PROS">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																2.Certificate from the institution for bonafied students.
																<br />
																site/house to be purchased
															</td>
															<td>
																<input type="checkbox" name="LODCPFHE" value="CIFBS">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																3.Fee structure
															</td>
															<td>
																<input type="checkbox" name="LODCPFHE" value="FS">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																4.Copy of certificate of last examination passed.
															</td>
															<td>
																<input type="checkbox" name="LODCPFHE" value="CCLEP">
															</td>
														</tr>

													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:equal>
							<logic:equal property="purposeType" value="obMarriage" name="advanceForm">
								<tr>
									<td>
										<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
											<tr>
												<td colspan="4">
													<table align="left" cellpadding="0" cellspacing="0">
														<tr>
															<td class="tableSideTextBold" align="left">
																List of Documents to be enclosed in Marraige
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<table align="center" cellpadding="1" cellspacing="1">
														<tr>
															<td class="tableSideText">
																1.Marriage invitation card
															</td>
															<td>
																<input type="checkbox" name="LODOBMARR" value="MIC">
															</td>
														</tr>
														<tr>
															<td class="tableSideText">
																2.Proof of Age & Dependency
															</td>
															<td>
																<input type="checkbox" name="LODOBMARR" value="PAD">
															</td>
														</tr>


													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:equal>
							<logic:equal property="advanceType" value="CPF" name="advanceForm">
								<logic:notEqual property="purposeType" value="obMarriage" name="advanceForm">
									<tr>
										<td colspan="4">
											<table align="center" cellpadding="1" cellspacing="1">
												<tr>
													<td class="tableTextBold">
														Certified That The Person For Whom The Advance Is Required Is Actually Dependend
													</td>
													<td>
														<html:select styleClass="TextField" property="advncerqddepend">
															<option value="Y">
																Yes
															</option>
															<option value="N">
																No
															</option>
														</html:select>
													</td>
												</tr>
												<tr>
													<td class="tableTextBold">
														Certified That The Amount Of Advance Will Be Utilised For the purpose which is drawn
													</td>
													<td>
														<html:select styleClass="TextField" property="utlisiedamntdrwn">
															<option value="Y">
																Yes
															</option>
															<option value="N">
																No
															</option>
														</html:select>
													</td>
												</tr>


											</table>
										</td>
									</tr>
								</logic:notEqual>
							</logic:equal>
							<tr>
								<td colspan="4">
									<table align="center" cellpadding="1" cellspacing="1">
										<tr>

											<td align="center">

												<input type="button" class="butt" value="Submit" onclick="javascript:saveAdvanceInfo();">
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn">
												<input type="button" class="butt" value="Back" onclick="javascript:frmPrsnlBack();" class="btn">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

