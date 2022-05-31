<!--
/*
  * File       : SearchSignatureMapping.jsp
  * Date       : 17/01/2011
  * Author     : Radha P
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
--> 
<%@ page language="java"  import="java.util.*,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="com.epis.bean.rpfc.SignatureMappingBean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/tags-display" prefix="display"%>
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
		
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
	
		<script type="text/javascript">

// for Module Names
   

function search(){
	 	 	  
	var url="<%=basePath%>signaturemapping.do?method=searchSignatureMapping";   		 
   		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
	 
}

function reset(){
 		var url="<%=basePath%>signaturemapping.do?method=searchSignatureMapping";   		 
   		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
}
 
	
 function AddRecord(){
 
 		var url="<%=basePath%>signaturemapping.do?method=loadSignatureMapping&flag=new";  
 		//alert(url); 		 
   		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
}
function editRecord(userID){
 
 		var url="<%=basePath%>signaturemapping.do?method=editSignatureMapping&userid="+userID;   		 
   		alert(url);
   		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();
}
 function editCheckedRecord(){		
		     
		     //alert(frmName);
		      var count=0;
		      var str=new Array();
		      var userid;
		        
		if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	   
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select advance request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select advance request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){			        
			          userid=str[0];
			          }
			       
			        //alert(verifiedBy);
			        
			         editRecord(userid);
		     	
		}

</script>
	</HEAD>
		<body class="BodyBackground"  >	 
		<html:form action="/signaturemapping.do?method=loadSignatureMapping" enctype="multipart/form-data">
			<%=ScreenUtilities.screenHeader("Signature Mapping[Search]")%>
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">

				<tr>
					<td width="20%">
						&nbsp;
					</td>
					<td class="tableTextBold" width="25%">
						User Id
					</td>
					<td width="2%" align="center">
						:
					</td>
					<td>
						  <input type="text" name="userid"  class="TextField"  size="11"/>
					
					</td>

				</tr>
				 		 
				<tr>

					<td colspan="4" align="center">
						<input type="button" class="butt" name="Submit" value="Search" onclick="javascript:search()">
						<input type="button" class="butt" name="Reset" value="Reset" onclick="javascript:reset()">
					</td>

				</tr>

			</table>
			
			<%=ScreenUtilities.searchSeperator()%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">	
  <tr>
                   <td  height="29" align="left" valign="top">
                   		<table width="20%" border="0" cellspacing="0" cellpadding="0">
                      	  <tr>  
							 	
							 <td>
								<input type="button" value="New" class="btn" onclick="javascript:AddRecord()">
							</td>							
						    
							 
                        </tr>
                        </table>
                      </td>
                         
                        </tr>	
						<logic:present name="searchList">
							<tr>							
								<td align="center" width="100%">
								
									 
									
									<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="sinaturetList" sort="list" style="width:100%;height:50px" pagesize="10" name="requestScope.searchList" requestURI="./signaturemapping.do?method=searchSignatureMapping" >										
									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="NoteSheetSearch.xls" />
    								<display:setProperty name="export.pdf.filename" value="NoteSheetSearch.pdf" />
    								<display:setProperty name="export.rtf.filename" value="NoteSheetSearch.rtf" />
    								<display:column  sortable="true"  property="userID" sortable="true" title="User Id" headerClass="GridLTHCells" class="GridLTCells"/>
									<display:column  sortable="true"  property="userName" sortable="true" title="User Name" headerClass="GridLTHCells" class="GridLTCells"/>
    								<display:column  sortable="true"  property="fromDate"  headerClass="GridLTHCells" class="GridLTCells" title="From Fin Date" />
    								<display:column  sortable="true"  property="toDate" headerClass="GridLTHCells" class="GridLTCells" title="To Fin Date"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column  sortable="true"  property="station"  headerClass="GridLTHCells" class="GridLTCells" title="Station"  decorator="com.epis.utilities.StringFormatDecorator"/>			
    								 
									 
									</display:table>
								</td>
							</tr>
						</logic:present>
					 </table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
		
		 

	</body>

