

<%@ page language="java" pageEncoding="UTF-8" import="com.epis.utilities.ScreenUtilities"%>

<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
//String accountType = request.getParameter("accountType") ==null?"":request.getParameter("accountType"); 
String sno=(String)request.getAttribute("sno");

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
       
   <title>Statement Details</title>

	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="description" content="Voucher Info" />
	<link href="css/epis.css" rel="stylesheet" type="text/css" />
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<link href="css/buttons.css" rel="stylesheet" type="text/css" />
	<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePathBuf%>js/calendar.js"></script>
	<script language="javascript" type="text/javascript">	
	
	  	function sendkeys(seltext){	
	  		var selectedid = "";
	  		var bankAmt=0;
			for(var i=0;i<document.forms[0].keys.length;i++){
				if(document.forms[0].keys[i].checked){
					selectedid +=document.forms[0].keys[i].value+",";
					bankAmt+=parseFloat(document.forms[0].bankAmount[i].value);
					
				}
			}
			if(selectedid !=''){
			
		  		selectedid = selectedid.substring(0,selectedid.length-1);
			}
	   		window.opener.getStatementsFromchild(selectedid,seltext,bankAmt);	  
	   		window.self.close();	    
	  	}
	  	function getURL(){
	  	  document.forms[0].action="/LoadStatements.do?method=showDetails";
	  	  document.forms[0].submit();
	  	}
	  	function chekEnter(evt){
	  	var kc = event.keyCode;
		if(kc == 13)
		  getURL();
	  	}
	  
	</script>
  </head>
  
  <body >
  <div id="fixeddiv" style="
    position:absolute;
    width:75px;height:50px;
    padding:16px;
    background:#FFFFFF;
    border:2px solid #2266AA;
    top:10px;
    left:50px;
    z-index:100" onclick="javascript:sendkeys()">OK
</div>
  <script type="text/javascript"><!--   
/* Script by: www.jtricks.com  
 * Version: 20071127  
 * Latest version:  
 * www.jtricks.com/javascript/navigation/fixed_menu.html  
 */  
fixedMenuId = 'fixeddiv';   
  
var fixedMenu =    
{   
    hasInner: typeof(window.innerWidth) == 'number',   
    hasElement: document.documentElement != null  
       && document.documentElement.clientWidth,   
  
    menu: document.getElementById   
        ? document.getElementById(fixedMenuId)   
        : document.all   
          ? document.all[fixedMenuId]   
          : document.layers[fixedMenuId]   
};   
  
fixedMenu.computeShifts = function()   
{   
    fixedMenu.shiftX = fixedMenu.hasInner   
        ? pageXOffset   
        : fixedMenu.hasElement   
          ? document.documentElement.scrollLeft   
          : document.body.scrollLeft;   
    if (fixedMenu.targetLeft > 0)   
        fixedMenu.shiftX += fixedMenu.targetLeft;   
    else  
    {   
        fixedMenu.shiftX +=    
            (fixedMenu.hasElement   
              ? document.documentElement.clientWidth   
              : fixedMenu.hasInner   
                ? window.innerWidth - 20   
                : document.body.clientWidth)   
            - fixedMenu.targetRight   
            - fixedMenu.menu.offsetWidth;   
    }   
  
    fixedMenu.shiftY = fixedMenu.hasInner   
        ? pageYOffset   
        : fixedMenu.hasElement   
          ? document.documentElement.scrollTop   
          : document.body.scrollTop;   
    if (fixedMenu.targetTop > 0)   
        fixedMenu.shiftY += fixedMenu.targetTop;   
    else  
    {   
        fixedMenu.shiftY +=    
            (fixedMenu.hasElement   
            ? document.documentElement.clientHeight   
            : fixedMenu.hasInner   
              ? window.innerHeight - 20   
              : document.body.clientHeight)   
            - fixedMenu.targetBottom   
            - fixedMenu.menu.offsetHeight;   
    }   
};   
  
fixedMenu.moveMenu = function()   
{   
    fixedMenu.computeShifts();   
  
    if (fixedMenu.currentX != fixedMenu.shiftX   
        || fixedMenu.currentY != fixedMenu.shiftY)   
    {   
        fixedMenu.currentX = fixedMenu.shiftX;   
        fixedMenu.currentY = fixedMenu.shiftY;   
  
        if (document.layers)   
        {   
            fixedMenu.menu.left = fixedMenu.currentX;   
            fixedMenu.menu.top = fixedMenu.currentY;   
        }   
        else  
        {   
            fixedMenu.menu.style.left = fixedMenu.currentX + 'px';   
            fixedMenu.menu.style.top = fixedMenu.currentY + 'px';   
        }   
    }   
  
    fixedMenu.menu.style.right = '';   
    fixedMenu.menu.style.bottom = '';   
};   
  
fixedMenu.floatMenu = function()   
{   
    fixedMenu.moveMenu();   
    setTimeout('fixedMenu.floatMenu()', 20);   
};   
  
// addEvent designed by Aaron Moore   
fixedMenu.addEvent = function(element, listener, handler)   
{   
    if(typeof element[listener] != 'function' ||    
       typeof element[listener + '_num'] == 'undefined')   
    {   
        element[listener + '_num'] = 0;   
        if (typeof element[listener] == 'function')   
        {   
            element[listener + 0] = element[listener];   
            element[listener + '_num']++;   
        }   
        element[listener] = function(e)   
        {   
            var r = true;   
            e = (e) ? e : window.event;   
            for(var i = 0; i < element[listener + '_num']; i++)   
                if(element[listener + i](e) === false)   
                    r = false;   
            return r;   
        }   
    }   
  
    //if handler is not already stored, assign it   
    for(var i = 0; i < element[listener + '_num']; i++)   
        if(element[listener + i] == handler)   
            return;   
    element[listener + element[listener + '_num']] = handler;   
    element[listener + '_num']++;   
};   
  
fixedMenu.supportsFixed = function()   
{   
    var testDiv = document.createElement("div");   
    testDiv.id = "testingPositionFixed";   
    testDiv.style.position = "fixed";   
    testDiv.style.top = "0px";   
    testDiv.style.right = "0px";   
    document.body.appendChild(testDiv);   
    var offset = 1;   
    if (typeof testDiv.offsetTop == "number"  
        && testDiv.offsetTop != null    
        && testDiv.offsetTop != "undefined")   
    {   
        offset = parseInt(testDiv.offsetTop);   
    }   
    if (offset == 0)   
    {   
        return true;   
    }   
  
    return false;   
};   
  
fixedMenu.init = function()   
{   
    if (fixedMenu.supportsFixed())   
        fixedMenu.menu.style.position = "fixed";   
    else  
    {   
        var ob =    
            document.layers    
            ? fixedMenu.menu    
            : fixedMenu.menu.style;   
  
        fixedMenu.targetLeft = parseInt(ob.left);   
        fixedMenu.targetTop = parseInt(ob.top);   
        fixedMenu.targetRight = parseInt(ob.right);   
        fixedMenu.targetBottom = parseInt(ob.bottom);   
  
        if (document.layers)   
        {   
            menu.left = 0;   
            menu.top = 0;   
        }   
        fixedMenu.addEvent(window, 'onscroll', fixedMenu.moveMenu);   
        fixedMenu.floatMenu();   
    }   
};   
  
fixedMenu.addEvent(window, 'onload', fixedMenu.init);   
  
//--></script>  
  
  <center>
  <html:form action="/LoadStatements.do" >
    <%=ScreenUtilities.screenHeader("brs.title")%>
    	<table width="100%" align="center">
    			<tr>
					<td   align="center" nowrap colspan="4">
						<html:errors bundle="error" />
					</td>
					
				</tr>	
    			<tr>
    		   <td>
    		   <table align=center width="400" border="0" cellspacing="0" cellpadding="0">
    		    <tr>
    			<td class="tableTextBold"><bean:message key="accCodeType.description" bundle="common" /> &nbsp;&nbsp;</td><td><html:text property="description" size="10" onkeypress="chekEnter(this)"/>
    			<bean:define id="accno"><bean:write name="accountNo"/></bean:define>
    			<html:hidden property="accountNo" value="<%=accno%>"/>
    			<html:button property="Go" value="Go" onclick="getURL();"/></td> 
    			</tr>
    			</table>
    			</td>
    			</tr>    			
	              <tr> 	                
	                <td align="center" valign="top">
					
						<table border="0" align="center" width="80%">
							
								<tr bgcolor="#CBD9E3">	    							
	    							<td class="tableText" align="center">&nbsp;</td>	  
	    							<td class="tableText" align="center" nowrap><b><bean:message key="brs.accountno" bundle="common" /></b></td>  							
	    							<td class="tableText" align="center" nowrap><b><bean:message key="brs.transactiondt" bundle="common" /></b></td>
	    							<td class="tableText" align="center" nowrap ><b><bean:message key="brs.valuedt" bundle="common" /></b></td>	    							
	    							<td class="tableText" align="center" nowrap><b><bean:message key="brs.creditamount" bundle="common" /></b></td> 
	    							<td class="tableText" align="center" nowrap><b><bean:message key="brs.debitamount" bundle="common" /></b></td>		    							
	    							<td class="tableText" align="center" nowrap><b><bean:message key="brs.description" bundle="common" /></b></td>
	    							<td class="tableText" align="center" nowrap><b><bean:message key="brs.chequeno" bundle="common" /></b></td>							
	    								
	    						</tr>
	    					
	    						<logic:present name="Blist"> 
	    						 <logic:iterate name="Blist" id="statementslist">
	    								
									<tr>
										<td class="tableText" nowrap>											
											<input type="checkbox" value ="<bean:write property="keyno" name="statementslist"/>"  name="keys">
											<input type="hidden" name="bankAmount"  value="<bean:write property="amount" name="statementslist"/>"/>
										</td>				
						    			
						    			
						    			<td class="tableText" nowrap>
						    				<bean:write property="accountNo" name="statementslist"/>
						    			</td>	
						    			
						    			<td class="tableText" nowrap>
						    				<bean:write property="transactionDt" name="statementslist"/>
						    			</td>
						    			<td class="tableText" nowrap>
						    				<bean:write property="valueDt" name="statementslist"/>
						    			</td>						    			
						    			<td class="tableText" nowrap>
						    				<bean:write property="creditAmt" name="statementslist"/>
						    			</td>
						    			<td class="tableText" nowrap>
						    				<bean:write property="debitAmt" name="statementslist"/>
						    			</td>						    			
						    			<td class="tableText" nowrap>
						    				<bean:write property="description" name="statementslist"/>
						    			</td>
						    			<td class="tableText" nowrap>
						    				<bean:write property="chequeNo" name="statementslist"/>
						    			</td>
						    			    			
						    		
						    		</tr>
						    		
						    	</logic:iterate>
						   </logic:present>
			    		</table>
						</td>
				</tr>						
		</table>
		<%=ScreenUtilities.searchFooter()%>	
   </html:form>
   </center>
  </body>
</html:html>
