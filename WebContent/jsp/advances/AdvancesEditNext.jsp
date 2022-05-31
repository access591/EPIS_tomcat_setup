<!--
/*
  * File       : AdvanceEditNext.jsp
  * Date       : 19/12/2009
  * Author     : Suneetha V 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicReportBean,com.epis.bean.advances.AdvanceEditBean,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
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

		<LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime.js"></SCRIPT>

		<%
        ArrayList wList=new ArrayList();
        int wListSize=0;        
        AdvanceBasicReportBean editBean=new AdvanceBasicReportBean();
   
        if(request.getAttribute("wthdrwList")!=null){        
            wList=(ArrayList)request.getAttribute("wthdrwList");    
        	wListSize=wList.size();
        }
         
        %>

		<script type="text/javascript">
		var status='Y';
		var listSize=0;
		var wthdrwstatus=false;
		listSize=<%=wListSize%>;
		function frmPrsnlBack(){
		
		var frmName;
				
		if(document.forms[0].advanceType.value=='CPF'){
		  frmName='advancesedit';
		}else if(document.forms[0].advanceType.value=='PFW'){
		  frmName='pfwedit';
		}
		
				url="<%=basePath%>loadAdvanceEdit.do?method=loadAdvanceFormBack&frm_transId="+document.forms[0].advanceTransID.value+"&frm_pensionno="+document.forms[0].pensionNo.value+"&frm_name="+frmName;
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		}
		
		function calculateAge(){	
			if (!convert_date(document.forms[0].fmlyDOB))
			{
				document.forms[0].fmlyDOB.value='';
				document.forms[0].fmlyDOB.focus();
				return false;
			}
			if(document.forms[0].fmlyDOB.value!=""){			
			
					var dob=document.forms[0].fmlyDOB.value;
					var yearval=dob.substring(6,dob.length);	
					var today = new Date();
					var advanceType=document.forms[0].advanceType.value;
					var purposeType=document.forms[0].purposeType.value;
					
					var noOfYears=today.getYear()-yearval;
					document.forms[0].fmlyAge.value=noOfYears;	
					if(noOfYears==0){
					     alert('Invalid Date Of Birth');
					     document.forms[0].fmlyDOB.value='';
		                 document.forms[0].fmlyDOB.focus();
				  		 return false;					
					}								
					var chkFmlyReltion='';
					
					if(document.getElementById("empfmlydtls")!=null){
						chkFmlyReltion=document.getElementById("empfmlydtls").value;
					}else if(document.getElementById("advancecostexp")!=null){
						chkFmlyReltion=document.getElementById("advancecostexp").value;
					}else if(document.getElementById("cpfmarriageexp")!=null){
						chkFmlyReltion=document.getElementById("cpfmarriageexp").value;
					}else if(document.getElementById("pfwHEType")!=null && advanceType=='CPF'){
						chkFmlyReltion=document.getElementById("pfwHEType").value;
					}else if(document.getElementById("pfwHEType")!=null && advanceType=='PFW'){
						chkFmlyReltion=document.getElementById("pfwHEType").value;
					}
					if((advanceType=='PFW'&& purposeType.toLowerCase()=='MARRIAGE') ||(advanceType=='CPF' && purposeType.toLowerCase()=='obmarriage') ){
					if(chkFmlyReltion=="SON" && document.forms[0].fmlyAge.value<21){
		                 alert('Son Age Should Be Greater Than or Equal To 21');
		                 document.forms[0].fmlyDOB.value='';
		                 document.forms[0].fmlyAge.focus();
				  		 return false;
		            }    		                    
		            if(chkFmlyReltion=="DAUGHTER" && document.forms[0].fmlyAge.value<17){
		                 alert('Daughter Age Should Be Greater Than or Equal To 18');
		                 document.forms[0].fmlyDOB.value='';
		                 document.forms[0].fmlyAge.focus();
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

					if(document.getElementById("empfmlydtls")!=null && advanceType=='PFW' && purposeType=='MARRIAGE'){
					
					
						chkFmlyReltion=document.getElementById("empfmlydtls").value;
					
						
						
					}else if(document.getElementById("empfmlydtls")!=null && advanceType=='CPF' && purposeType=='MARRIAGE'){
						chkFmlyReltion=document.getElementById("empfmlydtls").value;
					}else if(document.getElementById("advancecostexp")!=null){
						chkFmlyReltion=document.getElementById("advancecostexp").value;
					}else if(document.getElementById("cpfmarriageexp")!=null){
						chkFmlyReltion=document.getElementById("cpfmarriageexp").value;
					}else if(document.getElementById("pfwHEType")!=null){
						chkFmlyReltion=document.getElementById("pfwHEType").value;
					}
					if(chkFmlyReltion=='SELF' || chkFmlyReltion=='self'){
					   document.forms[0].fmlyEmpName.value=document.forms[0].employeeName.value;
					   document.forms[0].fmlyDOB.value=document.forms[0].dateOfBirth.value;
					   calculateAge();
					   document.forms[0].fmlyAge.focus();
					}else{
					  document.forms[0].fmlyDOB.value='';
					  document.forms[0].fmlyAge.value='';
					  document.forms[0].fmlyEmpName.value='';
					  
					  if(document.forms[0].empfmlydtls.value==document.forms[0].purpose.value){
					     document.forms[0].fmlyEmpName.value=document.forms[0].fname.value;
					     document.forms[0].fmlyDOB.value=document.forms[0].dob.value;
					     document.forms[0].fmlyAge.value=document.forms[0].age.value;
					  }
					  document.forms[0].fmlyEmpName.focus();
					}		
					
		}
		function selectBankDetail(){		
				var bankDetailsInfo='';
				bankDetailsInfo=document.forms[0].bankdetail.options[document.forms[0].bankdetail.selectedIndex].value;
		  		
		  		if(bankDetailsInfo=='bank'){   		    
		   			document.getElementById("bankdet").style.display="block";
		   		 	document.getElementById("partydet").style.display="none";
		   		}else if(bankDetailsInfo=='party'){  
		   	
		   		    if(document.forms[0].advanceType.value=="PFW"){		   
		   		    		        
					    document.getElementById("bankdet").style.display="block";
		   		    	document.getElementById("partydet").style.display="block";
		   		    }else{
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
		 		
		 				 		
		 	 if((advanceType=='PFW') &&(!purposeType=='SUPERANNUATION')){ 
		 			wthdrwlStatus=document.forms[0].chkwthdrwlinfo.options[document.forms[0].chkwthdrwlinfo.selectedIndex].value;
		  	}
		 		

		 		if((advanceType=='PFW'||advanceType=='CPF') && purposeType=='MARRIAGE'){
		 		
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
                }else if(advanceType=='PFW' && purposeType=='HBA'){
                
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
			  	   if(document.getElementById("hbapurposetype").value=='REPAYMENTHBA'){			  		         
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
                
                }else if((advanceType=='PFW' && purposeType=='HE') || (advanceType=='CPF' && purposeType=='education')){
                	 if(document.getElementById("pfwHEType").value=='NO-SELECT'){
                	     
                	    if(purposeType=='HE') 
			  			alert('Please Select Higher Education Purposes');
			  			else
			  			alert('Please Select Higher Education');
			  			
			  			document.forms[0].pfwHEType.focus();
			  			return false;
			  		 }	
			  		 
			  		 if(document.getElementById("pfwHEType").value!='NO-SELECT'){
			  		     if(document.forms[0].nmCourse.value==""){
			  		        alert("Please Enter Name Of Course");
			  		        document.forms[0].nmCourse.focus();
			  				return false;
			  		     }			  		 
			  		 }
			  		 
			  		 if(document.getElementById("pfwHEType").value!='NO-SELECT'){
			  		     if(document.forms[0].nmInstitue.value==""){
			  		        alert("Please Enter Name of Institution");
			  		        document.forms[0].nmInstitue.focus();
			  				return false;
			  		     }			  		 
			  		 }
			  		 
			  		 if(purposeType=='HE'){
				  		 if(document.getElementById("pfwHEType").value!='NO-SELECT'){
				  		     if(document.forms[0].curseDuration.value==""){
				  		        alert("Please Enter Duration of course");
				  		        document.forms[0].curseDuration.focus();
				  				return false;
				  		     }			  		 
				  		 }
			  		 }
			  		 
			  		 if(purposeType=='HE') {
				  		 if(document.getElementById("pfwHEType").value!='NO-SELECT'){			  		 
				  		     if(document.getElementById("heLastExaminfo").value=='NO-SELECT'){
				  		        alert("Please Enter Last examination passed");
				  		        document.forms[0].heLastExaminfo.focus();
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
                }else if(advanceType=='CPF' && purposeType=='OBLIGATORY'){                
                  
				  		 if(document.getElementById("advanceobligexp").value=='Other Ceremonies'){	
				  		       if(trim(document.forms[0].oblCermoney.value)==""){			  		     
					  		        alert("Please Enter  Other Ceremonies Expenses");
					  		        document.forms[0].oblCermoney.focus();
					  				return false;				  		    		  		 
				  				}
				  		 }              
                
                }else if(advanceType=='CPF' && purposeType=='OBMARRIAGE'){
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
		  	   if(advanceType=='PFW'){
		  	  	if(document.forms[0].modeofpartyname.value!='' ){
		  	   	if (!ValidateName(document.forms[0].modeofpartyname.value)){
					alert("Numeric/Invalid characters are not allowed");
					document.forms[0].modeofpartyname.focus();
					return false;
		  	  	 }
		  	   }
		  	   }
			  	if(advanceType=='CPF' && !(purposeType=='DEFENCE'  || purposeType=='OTHERS' || purposeType=='OBLIGATORY') ){
			     if(document.forms[0].fmlyEmpName.value==""){
				  			
				  			if(purposeType=="education")
				  			alert('Please Enter Name Of Dependent');
				  			else
				  			alert('Please Enter Name');
				  			document.forms[0].fmlyEmpName.focus();
				  			return false;
			  		 }
		  	  
		  	   	if (!ValidateName(document.forms[0].fmlyEmpName.value)){
					alert("Numeric/Invalid characters are not allowed");
					document.forms[0].fmlyEmpName.focus();
					return false;
		  	  	 }
		  	   
		  	   }else if(advanceType=='PFW' && (purposeType=='MARRIAGE') ){			  	   	  	
		  	   	  	 
		  	   	  	 if(document.forms[0].fmlyEmpName.value==""){
				  			alert('Please Enter Name');
				  			document.forms[0].fmlyEmpName.focus();
				  			return false;
			  		 }
			  			
		  	      	if (!ValidateName(document.forms[0].fmlyEmpName.value)){
					alert("Numeric/Invalid characters are not allowed");
					document.forms[0].fmlyEmpName.focus();
					return false;
		  	  	 }
		  	   }
		 
		  	   
		 		if(advanceType=='PFW' && purposeType=='HBA'){
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
		 		
		 			if(advanceType=='PFW' && purposeType=='SUPERANNUATION'){
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
		 			if(purposeType=='OBLIGATORY'){		 			 
		 				if(document.forms[0].advanceobligexp.value!="undefined"){		 				  
		 					if(document.forms[0].advanceobligexp.value=='Other Ceremonies'){		 					
		 						flag==false;
		 					}else{		 					
		 						flag=true;
		 					}
		 				}
		 			}else if(purposeType=='DEFENCE' || purposeType=='OTHERS'){
		 			  flag=false;
		 			}else{
		 			  flag=true;
		 			}
		 				
		 			
		 			if(flag==true){
		 			var dt=document.forms[0].fmlyDOB.value;
		 			var age=document.forms[0].fmlyAge.value;
		 		
		 		   if(dt=="") {
   		  			alert("Please Enter Date of Birth");
   		 			document.forms[0].fmlyDOB.focus();
   		  			return false;
   		  		   }
   		  		   var month="";
		   		  if(!dt==""){
		   		   var date1=document.forms[0].fmlyDOB;
		   	       var val1=convert_date(date1);
		   		    var now = new Date();
		   		   var birthday1= document.forms[0].fmlyDOB.value;
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
        			document.forms[0].fmlyDOB.value=birthday1;
        		   if(birthDate > now){
					    alert("DateofBirth cannot be greater than Currentdate");
						document.forms[0].fmlyDOB.focus();
					   return false;
				   }
   		    		if(val1==false){
   		    			return false;
   		    		}
   		    		
   		    			if (!convert_date(document.forms[0].fmlyDOB))
						{
							document.forms[0].fmlyDOB.focus();
							return false;
						}
		 			}
		 		
   		    			
		 		
   		        }
   		        
			}else if(advanceType=='PFW' && purposeType!='' ){
					if(purposeType=='HBA' || purposeType=='HE'  || purposeType=='SUPERANNUATION'){
						flag=false;
						
						if(purposeType=='HE'){
							if(document.forms[0].fmlyDOB.value){
							if (!convert_date(document.forms[0].fmlyDOB))
							{
								document.forms[0].fmlyDOB.focus();
								return false;
							}
							}
						}
						
					}else{
						 flag=true;
					}
							 			
		 			if(flag==true){
		 				var dt=document.forms[0].fmlyDOB.value;
		 		   if(dt=="") {
   		  			alert("Please Enter Date of Birth");
   		 			document.forms[0].fmlyDOB.focus();
   		  			return false;
   		  		   }
   		  		   var month="";
		   		  if(!dt==""){
		   		   var date1=document.forms[0].fmlyDOB;
		   	       var val1=convert_date(date1);
		   		    var now = new Date();
		   		   var birthday1= document.forms[0].fmlyDOB.value;
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
        			document.forms[0].fmlyDOB.value=document.forms[0].fmlyDOB.value;
        		   if(birthDate > now){
					    alert("DateofBirth cannot be greater than Currentdate");
						document.forms[0].fmlyDOB.focus();
					   return false;
				   }
   		    		if(val1==false){
   		    			return false;
   		    		}
   		    		
		 			}
		 			
   		        }
			}
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
		 	
		 	if(advanceType=='PFW' && purposeType=='MARRIAGE'){
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
		 		 	if(advanceType=='PFW' && purposeType=='HE'){
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
		 	
		 	if(wthdrwlStatus=='NO-SELECT'){
		  			alert('Please Select Y/N for Give Previous Withdrawal Details field');
		  			document.forms[0].chkwthdrwlinfo.focus();
		  			return false;
		  		}
		  		if(wthdrwlStatus=='Y'){		  	
		  		
		  		  
		  		}
		  		
		 		
		 	if((advanceType=='PFW' || advanceType=='CPF') && purposeType!=''){
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
			  	 	
			  }
		 	      
		 	}		
		 	
		 	
		 	if(advanceType=='PFW'){
			 	if(document.forms[0].chkwthdrwal.value=='Y'){
				 	if(document.forms[0].chkwthdrwlinfo.value=='N'){			 	 
	 					chkConfirm();					 
				 	}
				 	if(document.forms[0].chkwthdrwlinfo.value=='Y'){			 	 
	 					status='Y';			 
				 	}
			 	}
		 	}		 	
		 	
		 	 
		 	var wthDrwFlag="YES";
		 	
		 	
		 	 
		 if((advanceType=='PFW') &&(!purposeType=='SUPERANNUATION')){ 	 	
		 	if(document.forms[0].chkwthdrwlinfo.value=='Y' && wthdrwstatus==false && listSize==0){
		 	   alert('Please enter Give Previous Withdrawal Details');
		 	   return false; 
		 	}else if(document.forms[0].chkwthdrwlinfo.value=='Y' && wthdrwstatus==false && listSize!=0){		 	   
		 	   wthDrwFlag="NO";	 	   		 	   
		 	}
		 		 	
		 	}
		 	 var bankFlag='Y';
		 			 	
		 	if(status=='Y'){		
		 	 			
		 	url="<%=basePath%>loadAdvanceEdit.do?method=continueUpdateAdvances&lodinfo="+total+"&frm_wthDrwFlag="+wthDrwFlag+"&bankflag="+bankFlag;
		 	 
	   		document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
			}
		 
		 }
		 
		 function chkConfirm(){
		    var chkstatus= confirm("Do you really want to delete Give Previous Withdrawal Details??");
					 if (chkstatus== true)
					 {
					   status='Y';
					   return true;
					 }
					 else
					 {			 
					 
					    status='N';
 						return false;
					 }
		 
		 }
		function chkWthDrwl(){
					var wthdrwlStatus="";
					wthdrwlStatus=document.forms[0].chkwthdrwlinfo.options[document.forms[0].chkwthdrwlinfo.selectedIndex].value;
		  			if(wthdrwlStatus=='Y'){   		    
		   		   		document.getElementById("loadwthdrwdet").style.display="block";
		   		    }else if(wthdrwlStatus=='N'){   	
						document.getElementById("loadwthdrwdet").style.display="none";
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
		function loadNext(purposeType,advanceType,purposeOptionType,hbadrwnfrmaai){
		   
			if(purposeType=='HBA'){
				document.getElementById("repaymentHbaLoan").style.display="none";
				document.getElementById("repaymentHbaLoanInfo").style.display="none";
				document.getElementById("repaymentHbaLoanInfo2").style.display="none";
				document.getElementById("hbaAnyOthers").style.display="none";
				document.forms[0].hbapurposetype.focus();
				
			}else if(purposeType=='MARRIAGE'){
								
				document.forms[0].purpose.value=document.forms[0].empfmlydtls.value;	
				document.forms[0].fname.value=document.forms[0].fmlyEmpName.value;	
				document.forms[0].dob.value=document.forms[0].fmlyDOB.value;	
				document.forms[0].age.value=document.forms[0].fmlyAge.value;				
			
				document.forms[0].empfmlydtls.focus();
			}else if(purposeType=='HE'){			
				
					document.forms[0].pfwHEType.focus();
			}else if(purposeType=='cost'){					
					document.forms[0].advancecostexp.focus();
			}else if(purposeType=='obligatory'){			
					document.forms[0].oblCermoney.focus();
			}else if(purposeType=='obmarraige'){			
					document.forms[0].cpfmarriageexp.focus();		
			}else if(purposeType=='illness'){
					document.forms[0].advancecostexp.focus();
			}else if(purposeType=='education'){			
					document.forms[0].pfwHEType.focus();
			}			
			
			if((advanceType=='PFW') &&(!purposeType=='SUPERANNUATION')){ 
			 document.forms[0].chkwthdrwal.value=document.forms[0].chkwthdrwlinfo.value; 
		    }
		    if(purposeType=='HBA')
		    {
		    if(purposeOptionType=='REPAYMENTHBA'){
				document.getElementById("repaymentHbaLoan").style.display="block";
				document.getElementById("repaymentHbaLoanInfo").style.display="block";
				document.getElementById("repaymentHbaLoanInfo2").style.display="block";
				document.getElementById("hbaAnyOthers").style.display="none";
				
			}else if(purposeOptionType=='HBAOTHERS'){
				document.getElementById("repaymentHbaLoan").style.display="none";
				document.getElementById("repaymentHbaLoanInfo").style.display="none";
				document.getElementById("repaymentHbaLoanInfo2").style.display="none";
				document.getElementById("hbaAnyOthers").style.display="block";
			}
		    
		    }
		    
		    if(purposeType=='HBA')
		    {
		   		 if(hbadrwnfrmaai=='Y'){
				document.getElementById("chk_HBA_wthdrwn_dtl").style.display="block";	
				}else
		    		{
		    		document.getElementById("chk_HBA_wthdrwn_dtl").style.display="none";	
		    		}
											
			}
		}
	function chkhbapurposedtl(){
			var hbaOptions="";
			hbaOptions=document.forms[0].hbapurposetype.options[document.forms[0].hbapurposetype.selectedIndex].value;
			if(hbaOptions=='REPAYMENTHBA'){
				document.getElementById("repaymentHbaLoan").style.display="block";
				document.getElementById("repaymentHbaLoanInfo").style.display="block";
				document.getElementById("repaymentHbaLoanInfo2").style.display="block";
				document.getElementById("hbaAnyOthers").style.display="none";
			}else if(hbaOptions=='HBAOTHERS'){
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
	var wthdrwlPurpose='',wthdrwlAmount='',wthDrwlTrnsdt='';
	
	if(document.forms[0].wthdrwlpurpose.value!=''){
	wthdrwlPurpose=document.forms[0].wthdrwlpurpose.value;
	}
	
	if(document.forms[0].wthdrwlAmount.value!=''){
	wthdrwlAmount=document.forms[0].wthdrwlAmount.value;
	}
	
	if(document.forms[0].wthDrwlTrnsdt.value!=''){
	wthDrwlTrnsdt=document.forms[0].wthDrwlTrnsdt.value;
	}
	
	
	var href;
	   		href="loadAdvance.do?method=previousWithDrawalDetails&frm_wthdrwlPurpose="+wthdrwlPurpose+"&frm_wthdrwlAmount="+wthdrwlAmount+"&frm_wthdrwlDate="+wthDrwlTrnsdt;
			progress=window.open(href, windowname, 'width=550,height=300,statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
	}
	
	function loadWthDrwlDetails(windowname){		
	var href;
	        
	   		href="loadAdvanceEdit.do?method=loadPreviousWithDrawalDetails&frm_transid="+document.forms[0].advanceTransID.value+"&frm_wthdrwinfo="+document.forms[0].chkwthdrwlinfo.value;
			progress=window.open(href, windowname, 'width=600,height=300,statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
	}
	
	function getWithDrawalDetails(ExpList){	   	 
	
	  if(ExpList.indexOf(':')!= -1){	   	 
	   	 document.forms[0].wthdrwlist.value=ExpList;
	   	 wthdrwstatus=true;
	  }else{	
	      listSize=0;  
	  }
	     
	}   	  
	function chkBankDet(){
	  
	   
	   if(document.forms[0].bankdetail.value=='bank'){   		    
		   			document.getElementById("bankdet").style.display="block";
		   		 	document.getElementById("partydet").style.display="none";
		   		}else if(document.forms[0].bankdetail.value=='party'){  
		   	
		   		    if(document.forms[0].advanceType.value=="PFW"){		   
		   		    		        
					    document.getElementById("bankdet").style.display="block";
		   		    	document.getElementById("partydet").style.display="block";
		   		    }else{
		   		   	 document.getElementById("bankdet").style.display="none";
		   		        alert("Party Type is not available For CPF Advance");
		   		        document.forms[0].bankdetail.focus();
		   		        return false;
		   		    }
		   		}else{
		   			document.getElementById("bankdet").style.display="none";
		   		 	document.getElementById("partydet").style.display="none";
		   		} 
	}
	
	function chkWthDraDet(advanceType){
		var purposeType=document.forms[0].purposeType.value;
		if((advanceType=='PFW') &&(!purposeType=='SUPERANNUATION'))
		{ 	
	      if(document.forms[0].chkwthdrwlinfo.value=='Y'){
	         document.getElementById("loadwthdrwdet").style.display="block"; 
	      }else{
		     document.getElementById("loadwthdrwdet").style.display="none";
	      }
	    }	
	      
	    
	}
	
	 
				  		   	
 </script>
	</head>
	<%
	AdvanceEditBean basicBean=new AdvanceEditBean();
	if(request.getAttribute("advanceBean")!=null){
		basicBean=(AdvanceEditBean)request.getAttribute("advanceBean");
		request.setAttribute("advanceBasicBean",basicBean);
	}
%>

	<body class="BodyBackground" onload="loadNext('<bean:write property="purposeType" name="advanceBean"/>','<bean:write property="advanceType" name="advanceBean"/>','<bean:write property="purposeOptionType" name="advanceBean"/>','<bean:write property="hbadrwnfrmaai" name="advanceReportBean"/>');chkBankDet();chkWthDraDet('<bean:write property="advanceType" name="advanceBean"/>');">
		<html:form method="post" action="loadAdvanceEdit.do?method=continueUpdateAdvances">
			<%=ScreenUtilities.screenHeader(request.getAttribute("screenTitle").toString())%>


			<table width="720" border="0" cellspacing="3" cellpadding="0">

				<html:hidden property="pensionNo" name="advanceBean" />
				<html:hidden property="advanceTransID" name="advanceBean" />
				<html:hidden property="employeeName" name="advanceBean" />
				<html:hidden property="dateOfBirth" name="advanceBean" />
				<html:hidden property="subscriptionAmt" />
				<html:hidden property="advReqAmnt" name="advanceBean" />
				<html:hidden property="emoluments" name="advanceBean" />
				<html:hidden property="purposeType" name="advanceBean" />
				<html:hidden property="purposeOptionType" name="advanceBean" />			
				<html:hidden property="advanceType" name="advanceBean" />
				<html:hidden property="pfwPurpose" name="advanceBean" />
			
				<html:hidden property="cpftotalinstall" name="advanceBean" />
				<html:hidden property="region" name="advanceBean" />
				<html:hidden property="trust" name="advanceBean" />
				<html:hidden property="designation" name="advanceBean" />
				<html:hidden property="department" name="advanceBean" />
				<html:hidden property="dateOfJoining" name="advanceBean" />
				<html:hidden property="station" name="advanceBean" />
				<html:hidden property="createdDate" name="advanceBean" />
				<html:hidden property="wthdrwlist" />
				<html:hidden property="lodInfo" name="advanceBean" />
				<html:hidden property="placeofposting" name="advanceBean" />
				<html:hidden property="paymentinfo" name="advanceBean" />
				 
				
 
				<input type="hidden" name="purpose">
				<input type="hidden" name="fname">
				<input type="hidden" name="dob">
				<input type="hidden" name="age">
				<input type="hidden" name="chkwthdrwal">

 
				<bean:define id="selPurposeType" name="advanceBean" property="purposeType" type="String" />
				<logic:equal property="pfwPurHBA" value="<%=selPurposeType%>" name="advanceEditForm">
								 
						 <jsp:include page="AdvancePFWHBAEditDetails.jsp"></jsp:include>
				</logic:equal>
				<!--Marriage Purpose  -->

				<logic:equal property="pfwPurMarriage" value="<%=selPurposeType%>" name="advanceEditForm">
					<tr>
						<td colspan="4">
							<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">

								<tr>
									<td class="tableTextBold">
										Marriage Purposes:
									</td>
									<td>
										<html:select styleClass="TextField" property="empfmlydtls" name="advanceBean" onchange="javascript:selectSelfInfo();">
											<html:option value='NO-SELECT'>Select One</html:option>
											<html:option value='SELF'>Self</html:option>
											<html:option value='SON'>Son</html:option>
											<html:option value='DAUGHTER'>Daughter</html:option>
											<html:option value='DEPENDENT BROTHER'>Dependent Brother</html:option>
											<html:option value='DEPENDENT SISTER'>Dependent Sister</html:option>
										</html:select>
										
									</td>

									<td class="tableTextBold">
										Name:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyEmpName" name="advanceReportBean" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td class="tableTextBold">
										Date of Birth:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyDOB" name="advanceReportBean" onblur="calculateAge();" maxlength="12" />
										&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlyDOB');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>
									</td>

									<td class="tableTextBold">
										Age:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyAge" name="advanceReportBean" readonly="true" maxlength="3" />
									</td>
								</tr>
								<tr>
									<td class="tableTextBold">
										Marriage Date:
									</td>
									<td>
										<html:text styleClass="TextField" property="marriagedate" name="advanceReportBean" maxlength="12" />
										&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].marriagedate');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>
									</td>

									<td class="tableTextBold">
										Birth certificate / age
										<br />
										proved:
									</td>
									<logic:equal property="brthCertProve" name="advanceReportBean" value="Y">
										<td>
											<input type="checkbox" name="brthCertProve" checked="checked" value="Y">
										</td>
									</logic:equal>
									<logic:notEqual property="brthCertProve" name="advanceReportBean" value="Y">
										<td>
											<input type="checkbox" name="brthCertProve" value="Y">
										</td>
									</logic:notEqual>
								</tr>

							</table>
						</td>
					</tr>
				</logic:equal>
				
				<!--Higher Education Purpose  -->

				<logic:equal property="pfwPurHE" value="<%=selPurposeType%>" name="advanceEditForm">
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
										<html:select styleClass="TextField" property="pfwHEType" name="advanceBean">
											<html:option value="NO-SELECT"> Select One </html:option>
											<html:option value='SON'> Son </html:option>
											<html:option value='DAUGHTER'> Daughter </html:option>
										</html:select>

									</td>
									<td class="tableTextBold">
										Name:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyEmpName" name="advanceReportBean" maxlength="50" />
									</td>

								</tr>
								<tr>
									<td class="tableTextBold">
										Date of Birth:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyDOB" name="advanceReportBean" onblur="calculateAge();" maxlength="12" />
										&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlyDOB');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>
									</td>

									<td class="tableTextBold">
										Age:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyAge" name="advanceReportBean" readonly="true" maxlength="3" />
									</td>
								</tr>
								<tr>

									<td class="tableTextBold">
										Name of Course:
									</td>
									<td>
										<html:text styleClass="TextField" property="nmCourse" name="advanceReportBean" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td class="tableTextBold">
										Name of Institution:
									</td>
									<td>
										<html:text styleClass="TextField" property="nmInstitue" name="advanceReportBean" maxlength="50" />

									</td>

									<td class="tableTextBold">
										Address of Institution:
									</td>
									<td>
										<html:text styleClass="TextField" property="addrInstitue" name="advanceReportBean" maxlength="150" />

									</td>
								</tr>
								<tr>
									<td class="tableTextBold">
										Duration of course:
									</td>
									<td>
										<html:text styleClass="TextField" property="curseDuration" name="advanceReportBean" maxlength="25" />

									</td>

									<td class="tableTextBold">
										Last examination passed:
									</td>
									<td>
										<html:select styleClass="TextField" property="heLastExaminfo" name="advanceReportBean">
											<html:option value="NO-SELECT"> Select One </html:option>
											<html:option value='Higher secondary'> Higher secondary (10 th) </html:option>
											<html:option value='Senior secondary'> Senior secondary (10+2) </html:option>
											<html:option value='Graduation'> Graduation </html:option>
											<html:option value='Post Graduation'> Post Graduation </html:option>
										</html:select>
									
									</td>
								</tr>

							</table>


						</td>
					</tr>
				</logic:equal>

				<logic:equal property="cpfAdvCOP" value="<%=selPurposeType%>" name="advanceEditForm">
					<tr>
						<td colspan="4">
							<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
								<tr>
									<td class="tableTextBold">
										Cost of Passage:
									</td>
									<td>
										<html:select styleClass="TextField" property="advancecostexp" name="advanceBean" onchange="javascript:selectSelfInfo();">
											<html:option value="NO-SELECT">Select One</html:option>
											<html:option value="SELF">Self</html:option>
											<html:option value="SPOUSE">Spouse</html:option>
											<html:option value="SON">Son</html:option>
											<html:option value="DAUGHTER">Daughter</html:option>
										</html:select>
									
									</td>
									<td class="tableTextBold">
										Name:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyEmpName" name="advanceReportBean" maxlength="50" />
									</td>

								</tr>
								<tr>
									<td class="tableTextBold">
										Date of Birth:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyDOB" name="advanceReportBean" onblur="calculateAge();" maxlength="12" />
										&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlyDOB');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>
									</td>

									<td class="tableTextBold">
										Age:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyAge" name="advanceReportBean" readonly="true" maxlength="3" />
									</td>
								</tr>
							</table>

						</td>
					</tr>
				</logic:equal>

				<logic:equal property="cpfAdvOEMarriage" value="<%=selPurposeType%>" name="advanceEditForm">
					<tr>
						
									<td colspan="4">
										<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
											<tr>
												<td class="tableTextBold" width="27%">
													Marriage Expenses:
												</td>
												<td>
													<html:select styleClass="TextField" property="cpfmarriageexp" name="advanceBean" onchange="selectSelfInfo();">
														<html:option value="NO-SELECT">Select One</html:option>
														<html:option value="SELF">Self Marriage</html:option>
														<html:option value="SON">Dependent Son</html:option>
														<html:option value="DAUGHTER">Dependent Daughter</html:option>
														<html:option value="SISTER">Dependent Sister</html:option>
													</html:select>

												</td>
												<td class="tableTextBold">
													Name:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyEmpName" name="advanceReportBean" maxlength="50" />
												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Date of Birth:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyDOB" name="advanceReportBean" onblur="calculateAge();" maxlength="12" />
													&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlyDOB');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>
												</td>

												<td class="tableTextBold">
													Age:
												</td>
												<td>
													<html:text styleClass="TextField" property="fmlyAge" name="advanceReportBean" readonly="true" maxlength="3" />
												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Marriage Date:
												</td>
												<td>
													<html:text styleClass="TextField" property="marriagedate" name="advanceReportBean" maxlength="12" />
													&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].marriagedate');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>
												</td>
												<td colspan="2">
													&nbsp;
												</td>
											</tr>
										</table>
									</td>
							
							
					</tr>

				</logic:equal>
				<logic:equal property="cpfAdvOE" value="<%=selPurposeType%>" name="advanceEditForm">
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
													<html:text styleClass="TextField" property="oblCermoney" name="advanceBean" maxlength="60" />
												</td>
											</tr>

										</table>
									</td>
								</tr>

							</table>
						</td>
					</tr>

				</logic:equal>

				<logic:equal property="cpfAdvIE" value="<%=selPurposeType%>" name="advanceEditForm">
					<tr>
						<td colspan="4">
							<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
								<tr>
									<td class="tableTextBold">
										Illness Expenses:
									</td>
									<td>
										<html:select styleClass="TextField" property="advancecostexp" name="advanceBean" onchange="javascript:selectSelfInfo();">
											<html:option value="NO-SELECT">Select One</html:option>
											<html:option value="SELF">Self</html:option>
											<html:option value="SPOUSE">Spouse</html:option>
											<html:option value="SON">Son</html:option>
											<html:option value="DAUGHTER">Daughter</html:option>
										</html:select>

									</td>
									<td class="tableTextBold">
										Name:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyEmpName" name="advanceReportBean" maxlength="50" />
									</td>

								</tr>
								<tr>
									<td class="tableTextBold">
										Date of Birth:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyDOB" name="advanceReportBean" onblur="calculateAge();" maxlength="12" />
										&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlyDOB');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>
									</td>

									<td class="tableTextBold">
										Age :
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyAge" name="advanceReportBean" readonly="true" maxlength="3" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:equal>

				<logic:equal property="cpfAdvEducation" value="<%=selPurposeType%>" name="advanceEditForm">
					<tr>
						<td colspan="4">
							<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
								<tr>
									<td class="tableTextBold">
										Higher Education:
									</td>
									<td>
										<html:select styleClass="TextField" property="pfwHEType" name="advanceBean">
											<html:option value="NO-SELECT"> Select One </html:option>
											<html:option value='SON'> Son </html:option>
											<html:option value='DAUGHTER'> Daughter </html:option>

										</html:select>
									</td>
									<td class="tableTextBold">
										Name of Dependent:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyEmpName" name="advanceReportBean" maxlength="50" />
									</td>

								</tr>
								<tr>
									<td class="tableTextBold">
										DateOfBirth:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyDOB" name="advanceReportBean" onblur="calculateAge();" maxlength="12" />
										&nbsp;&nbsp;&nbsp;<a href="javascript:show_calendar('forms[0].fmlyDOB');"><img src="<%=basePath%>images/calendar.gif" border="no" /></a>
									</td>

									<td class="tableTextBold">
										Age:
									</td>
									<td>
										<html:text styleClass="TextField" property="fmlyAge" name="advanceReportBean" readonly="true" maxlength="3" />
									</td>
								</tr>
								<tr>
									<td class="tableTextBold">
										Name of Course:
									</td>
									<td>
										<html:text styleClass="TextField" property="nmCourse" name="advanceReportBean" maxlength="50" />
									</td>

									<td class="tableTextBold">
										Duration of course:
									</td>
									<td>
										<html:text styleClass="TextField" property="curseDuration" name="advanceReportBean" maxlength="25" />

									</td>
								</tr>
								<tr>

									<td class="tableTextBold">
										Name of Institution:
									</td>
									<td>
										<html:text styleClass="TextField" property="nmInstitue" name="advanceReportBean" maxlength="50" />
									</td>


									<td class="tableTextBold">
										Address of Institution:
									</td>
									<td>
										<html:text styleClass="TextField" property="addrInstitue" name="advanceReportBean" maxlength="150" />
									</td>
								</tr>
								<tr>


									<td class="tableTextBold">
										Recognized:
									</td>
									<td>
										<html:select styleClass="TextField" property="heRecog" name="advanceReportBean">
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

				<logic:notEqual property="pfwPurSuperannuation" value="<%=selPurposeType%>"   name="advanceEditForm"> 
				<bean:define id="selAdvanceType" name="advanceBean" property="advanceType" type="String" />
				<logic:equal property="advanceType" value="PFW" name="advanceEditForm">
					<logic:equal property="advanceType" value="<%=selAdvanceType%>" name="advanceEditForm">
						<tr>
							<td colspan="4">
								<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
									<tr>
										<td class="tableTextBold" width="31%">
											Give Previous Withdrawal Details:
										</td>

										<td width="10%">
											<html:select styleClass="TextField" property="chkwthdrwlinfo" name="advanceBean" onchange="chkWthDrwl();">
												<html:option value="NO-SELECT">Select One</html:option>
												<html:option value="Y">Yes</html:option>
												<html:option value="N">No</html:option>
											</html:select>
										</td>
										<td>
											<table border="0">
												<tr>
													<td id="loadwthdrwdet" style="display: none;">
														<img alt="" src="<%=basePath%>images/icon-edit.gif" onclick="loadWthDrwlDetails('WithDrawalDetails');" />
													</td>
												</tr>
											</table>

										
										</td>
									</tr>

								</table>
							</td>
						</tr>
					</logic:equal>
				</logic:equal>
			</logic:notEqual>
				<tr>
					<td colspan="4">
						<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">

							<tr>
								<td colspan="4">
									<table align="left" cellpadding="1" cellspacing="1">
										<tr>
											<td class="tableSideTextBold" align="left">
												Bank Detail For Payment Made To
											</td>
											<logic:equal property="advanceType" value="PFW" name="advanceBean">
												<td>

													<html:select styleClass="TextField" property="bankdetail" name="advanceBean" onchange="selectBankDetail();">
														<html:option value="NO-SELECT">Select One</html:option>
														<html:option value="bank">Employee</html:option>
														<html:option value="party">Party</html:option>
													</html:select>
												</td>
											</logic:equal>
											<logic:equal property="advanceType" value="CPF" name="advanceBean">
												<td>
													<html:select styleClass="TextField" property="bankdetail" name="advanceBean" onchange="selectBankDetail();">
														<!--<html:option value="NO-SELECT">Select One</html:option>	-->
														<html:option value="bank">Employee</html:option>
														<html:option value="party">Party</html:option>
													</html:select>
												</td>
											</logic:equal>
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
				<logic:equal property="advanceType" value="<%=selAdvanceType1%>" name="advanceBean">
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
												<td>
													<html:text styleClass="TextField" property="modeofpartyname" maxlength="150"/>
												</td>
												<td class="tableTextBold" width="23%">
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
				<%
										  String chk1="",chk2="",chk3="",chk4="",chk5="",chk6="";
										%>
				<logic:equal property="pfwPurHBA" value="<%=selPurposeType%>" name="advanceEditForm">
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
										<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
												<logic:equal name="lod" property="lodInfo" value="TDPS">
													<%chk1="checked";%>
												</logic:equal>



												<logic:equal name="lod" property="lodInfo" value="NEDSHP">
													<%chk2="checked";%>
												</logic:equal>


												<logic:equal name="lod" property="lodInfo" value="AVPSH">
													<%chk3="checked";%>
												</logic:equal>

												<logic:equal name="lod" property="lodInfo" value="ECACH">
													<%chk4="checked";%>
												</logic:equal>


												<logic:equal name="lod" property="lodInfo" value="SCP">
													<%chk5="checked";%>
												</logic:equal>


												<logic:equal name="lod" property="lodInfo" value="AO">
													<%chk6="checked";%>
												</logic:equal>


											</logic:iterate>
										</logic:present>
										<table align="center" cellpadding="1" cellspacing="1">
											<tr>
												<td class="tableSideText">
													1.Title Deed of proposed seller
												</td>
												<td>
													<input type="checkbox" name="LODHBA" value="TDPS" <%=chk1%>>

												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													2.Non-encumbrance certificate in respect of the dwelling
													<br />
													site/house to be purchased
												</td>
												<td>

													<input type="checkbox" name="LODHBA" value="NEDSHP" <%=chk2%>>

												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													3.Agreement with the vendor for the purchase of site/house
												</td>
												<td>
													<input type="checkbox" name="LODHBA" value="AVPSH" <%=chk3%>>

												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													4.Estimate of the cost of construction in the case of advance
													<br />
													for the construction of the house.
												</td>
												<td>
													<input type="checkbox" name="LODHBA" value="ECACH" <%=chk4%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													5.Sanctioned construction plan
												</td>
												<td>
													<input type="checkbox" name="LODHBA" value="SCP" <%=chk5%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													6.Any others
												</td>
												<td>
													<input type="checkbox" name="LODHBA" value="AO" <%=chk6%>>
												</td>
											</tr>
										</table>

									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:equal>
				<logic:equal property="pfwPurHE" value="<%=selPurposeType%>" name="advanceEditForm">
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
										<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
												<logic:equal name="lod" property="lodInfo" value="PFWHEPAD">
													<%chk1="checked";%>
												</logic:equal>



												<logic:equal name="lod" property="lodInfo" value="PFWPSAFI">
													<%chk2="checked";%>
												</logic:equal>


												<logic:equal name="lod" property="lodInfo" value="PFWEECPY">
													<%chk3="checked";%>
												</logic:equal>

												<logic:equal name="lod" property="lodInfo" value="PFWDLI">
													<%chk4="checked";%>
												</logic:equal>

												<logic:equal name="lod" property="lodInfo" value="PFWDTCSTY">
													<%chk5="checked";%>
												</logic:equal>
											</logic:iterate>
										</logic:present>
										<table align="center" cellpadding="1" cellspacing="1">
											<tr>
												<td class="tableSideText">
													1.Proof of Age & Dependency
												</td>
												<td>
													<input type="checkbox" name="LODPFWHE" value="PFWHEPAD" <%=chk1%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													2.Proof of selection/admission from the institute.
												</td>
												<td>
													<input type="checkbox" name="LODPFWHE" value="PFWPSAFI" <%=chk2%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													3.Estimated expenditure of the course per year
												</td>
												<td>
													<input type="checkbox" name="LODPFWHE" value="PFWEECPY" <%=chk3%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													4.Demand Letter from the institute.
												</td>
												<td>
													<input type="checkbox" name="LODPFWHE" value="PFWDLI" <%=chk4%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													5.Duration of the course should be 3 year or more.
												</td>
												<td>
													<input type="checkbox" name="LODPFWHE" value="PFWDTCSTY" <%=chk5%>>
												</td>
											</tr>

										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:equal>
				<logic:equal property="pfwPurMarriage" value="<%=selPurposeType%>" name="advanceEditForm">
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
										<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
												<logic:equal name="lod" property="lodInfo" value="PFWMIS">
													<%chk1="checked";%>
												</logic:equal>



												<logic:equal name="lod" property="lodInfo" value="PFWPID">
													<%chk2="checked";%>
												</logic:equal>


												<logic:equal name="lod" property="lodInfo" value="PFWARVT">
													<%chk3="checked";%>
												</logic:equal>


											</logic:iterate>
										</logic:present>
										<table align="center" cellpadding="1" cellspacing="1">

											<tr>
												<td class="tableSideText">
													1.Marriage invitation card.
												</td>
												<td>
													<input type="checkbox" name="LODPFWMARR" value="PFWMIS" <%=chk1%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													2.Proof of age and Dependency.
												</td>
												<td>
													<input type="checkbox" name="LODPFWMARR" value="PFWPID" <%=chk2%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													3.Attested copy of Ration card/Voter’s Identity card/Tenth class certificate.
												</td>
												<td>
													<input type="checkbox" name="LODPFWMARR" value="PFWARVT" <%=chk3%>>
												</td>
											</tr>


										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:equal>
				<logic:equal property="pfwPurSuperannuation" value="<%=selPurposeType%>" name="advanceEditForm">
					<tr>
						<td>
							<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
								<tr>
									<td colspan="4">
										<table align="left" cellpadding="0" cellspacing="0">
											<tr>
												<td class="tableSideTextBold" align="left">
													List of Documents to be enclosed in SuperAnnuation
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<logic:present name="LODList">
											<logic:iterate name="LODList" id="lod">
												<logic:equal name="lod" property="lodInfo" value="PFWAAY">
													<%chk1="checked";%>
												</logic:equal>



												<logic:equal name="lod" property="lodInfo" value="PFWOYBARDS">
													<%chk2="checked";%>
												</logic:equal>
 

											</logic:iterate>
										</logic:present>
										<table align="center" cellpadding="1" cellspacing="1">

											<tr>
												<td class="tableSideText">
													1.Attaining of age of 54 Years
												</td>
												<td>
													<input type="checkbox" name="LODPFWSA" value="PFWAAY" <%=chk1%>>
												</td>
											</tr>
											<tr>
												<td class="tableSideText">
													2.One Year before actual retirement date of superannuation
												</td>
												<td>
													<input type="checkbox" name="LODPFWSA" value="PFWOYBARDS" <%=chk2%>>
												</td>
											</tr>
											 


										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:equal>
				 
				<logic:equal property="cpfAdvEducation" value="<%=selPurposeType%>" name="advanceEditForm">
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
						</td>
					</tr>
				</logic:equal>
				<logic:equal property="purposeType" value="OBMARRIAGE" name="advanceEditForm">
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
							</table>
						</td>
					</tr>
				</logic:equal>
				<logic:equal property="advanceType" value="CPF" name="advanceEditForm">
					<logic:notEqual property="purposeType" value="OBMARRIAGE" name="advanceEditForm">
						<tr>
							<td colspan="4">
								<table align="center" cellpadding="1" cellspacing="1">
									<tr>
										<td class="tableTextBold">
											Certified That The Person For Whom The Advance Is Required Is Actually Dependend
										</td>
										<td>
											<html:select property="advncerqddepend" styleClass="TextField" name="advanceBean" style="width:130px">
												<html:option value="Y">Yes</html:option>
												<html:option value="N">No</html:option>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="tableTextBold">
											Certified That The Amount Of Advance Will Be Utilised For the purpose which is drawn
										</td>
										<td>
											<html:select property="utlisiedamntdrwn" styleClass="TextField" name="advanceBean" style="width:130px">
												<html:option value="Y">Yes</html:option>
												<html:option value="N">No</html:option>
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

									<input type="button" class="btn" value="Submit" onclick="javascript:saveAdvanceInfo();">
									<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn">
									<input type="button" class="btn" value="Back" onclick="javascript:frmPrsnlBack();" class="btn">
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

