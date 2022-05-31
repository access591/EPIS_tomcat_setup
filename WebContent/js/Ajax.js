function sendURL(url,responseFunction)  
    {           
	  //alert(url);
	  if (window.ActiveXObject) {			// for IE  
		httpRequest = new ActiveXObject("Microsoft.XMLHTTP"); 
	  } else if (window.XMLHttpRequest) { // for other browsers  
		httpRequest = new XMLHttpRequest(); 
	  }

	  if(httpRequest) {
		
		httpRequest.open("GET", url, true);  //2nd arg is url with name/value pairs, 3 specify asynchronus communication
		httpRequest.onreadystatechange = eval(responseFunction) ; //which will handle the callback from the server side element
		httpRequest.send(null); 
	  }
    }

function retNodeValue(req,node)
{
 	if(req.responseXML.getElementsByTagName(node)[0].firstChild!=null)
		return req.responseXML.getElementsByTagName(node)[0].firstChild.nodeValue;
	else
		return ' ';
}