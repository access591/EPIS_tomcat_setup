/*			This script contains 
			Open File Validation
			Text to List Validation
			List to List Validation
			TextArea Maxlength Validation
			check All Validation
			CheckDelete Validation - deleteRecords
			Init Caps for report Browser tag / Header Name 
*/
 


/*---------------------Open File Validation --------------------------*/

function OpenFile(OpenObj) 
{
	window.location= 'file:///' + OpenObj;
}


/*---------------------End Open File Validation --------------------------*/




/*---------------------Text to List Validation --------------------------*/


function move1(fbox,tbox) 
{
	var i = 0;
	if(fbox.value != "") 
	{
		var no = new Option();
		no.value = fbox.value;
		no.text = fbox.value;
		tbox.options[tbox.options.length] = no;
		fbox.value = "";
	}
}

function remove(box) 
{
	for(var i=0; i<box.options.length; i++) 
	{
		if(box.options[i].selected && box.options[i] != "") 
		{
			box.options[i].value = "";
			box.options[i].text = "";
 	   }
	}
	BumpUp(box);
}

function BumpUp(abox) 
{
	for(var i = 0; i < abox.options.length; i++) 
	{
		if(abox.options[i].value == "")  
		{
			for(var j = i; j < abox.options.length - 1; j++)  
			{
				abox.options[j].value = abox.options[j + 1].value;
				abox.options[j].text = abox.options[j + 1].text;
			}
			var ln = i;
			break;
	   }
	}
	if(ln < abox.options.length)  
	{
		abox.options.length -= 1;
		BumpUp(abox);
    }
}

function Moveup(dbox) 
{
	for(var i = 0; i < dbox.options.length; i++) 
	{
		if (dbox.options[i].selected && dbox.options[i] != "" && dbox.options[i] != dbox.options[0]) 
		{
			var tmpval = dbox.options[i].value;
			var tmpval2 = dbox.options[i].text;
			dbox.options[i].value = dbox.options[i - 1].value;
			dbox.options[i].text = dbox.options[i - 1].text
			dbox.options[i-1].value = tmpval;
			dbox.options[i-1].text = tmpval2;
	    }
   }
}

function Movedown(ebox) 
{
	for(var i = 0; i < ebox.options.length; i++) 
	{
		if (ebox.options[i].selected && ebox.options[i] != "" && ebox.options[i+1] != ebox.options[ebox.options.length]) 
		{
			var tmpval = ebox.options[i].value;
			var tmpval2 = ebox.options[i].text;
			ebox.options[i].value = ebox.options[i+1].value;
			ebox.options[i].text = ebox.options[i+1].text
			ebox.options[i+1].value = tmpval;
			ebox.options[i+1].text = tmpval2;
        }
    }
}


/*---------------------End Text to List Validation --------------------------*/





/*---------------------List to List Validation --------------------------*/


function move(fbox, tbox) 
{
	var arrFbox = new Array();
	var arrTbox = new Array();
	var arrLookup = new Array();
	var i;
	for (i = 0; i < tbox.options.length; i++) 
	{
		arrLookup[tbox.options[i].text] = tbox.options[i].value;
		arrTbox[i] = tbox.options[i].text;
	}
	var fLength = 0;
	var tLength = arrTbox.length;
	for(i = 0; i < fbox.options.length; i++) 
	{
		arrLookup[fbox.options[i].text] = fbox.options[i].value;
		if (fbox.options[i].selected && fbox.options[i].value != "") 
		{
			arrTbox[tLength] = fbox.options[i].text;
			tLength++;
		}
		else 
		{
			arrFbox[fLength] = fbox.options[i].text;
			fLength++;
	    }
	}
	arrFbox.sort();
	arrTbox.sort();
	fbox.length = 0;
	tbox.length = 0;
	var c;
	for(c = 0; c < arrFbox.length; c++) 
	{
		var no = new Option();
		no.value = arrLookup[arrFbox[c]];
		no.text = arrFbox[c];
		fbox[c] = no;
	}
	for(c = 0; c < arrTbox.length; c++) 
	{
		var no = new Option();
		no.value = arrLookup[arrTbox[c]];
		no.text = arrTbox[c];
		tbox[c] = no;
    }
}



/*---------------------End List to List Validation --------------------------*/



/*---------------------TextArea Maxlength Validation --------------------------*/


function TxtAreaMaxLength(len,maxvalue)
{
	var checkOK = maxvalue;
	var checkStr = len.length;
	if (checkStr > checkOK)
	{
		alert("The text you have entered is too large.");
		return(false);
		
	}
	else
	return(true);
 
	
}


/*---------------------End TextArea Maxlength Validation --------------------------*/



/*----------------------- The below functions are added by Muni for providing short cut key facility --*/
//The below are the functions for providing short cut keys in the screens.
function selectSaveOrSearch(obj){
	if(document.all['Clear'])
		document.all['Clear'].blur();
	obj.style.backgroundColor = 'blue';
}
function selectClear(obj){
	if(document.all['SS'])
		document.all['SS'].blur();
	obj.style.backgroundColor = 'blue';
}

function ButtonDeSelected(obj,color){
	obj.style.backgroundColor = color;
}
function enableShortCutKeys(){
	//119 -- F8.	
	if(window.event.keyCode==119){
		document.all['Clear'].click();
		return false;
	}
	//120 -- F9
	if(window.event.keyCode==120){
		document.location.reload(true);
		return false;
	}
	//13 -- Enter Key	
	if(window.event.keyCode==13 && event.srcElement.type!='textarea' && event.srcElement.id!='Help' && event.srcElement.id!='Calender' && event.srcElement.id!='Search' && event.srcElement.id!='Clear'){
		document.all['SS'].focus();
		return false;
	}
}
/*----------------------------------------------------------------------------------------------------*/


/*--------------- The below function are added by Muni for common PopUps------------------------------*/
//The below two functions are to open the window and close.
var commonWindow = "";
function openWindow(HelpFor,ParentFormFeilds,NextFocusFeild,x,y){
	var params = "/view/common/CommonHelp.jsp?HelpFor="+HelpFor;
	if(ParentFormFeilds!='' && ParentFormFeilds!=null && ParentFormFeilds!='undefined')
		params += "&ParentFormFeilds="+ParentFormFeilds;
	if(NextFocusFeild!='' && NextFocusFeild!=null && NextFocusFeild!='undefined')
		params += "&NextFocusFeild="+NextFocusFeild;
	commonWindow =window.open(params,"zzz","toolbar=no,width=1000,height=280,left=0,top=0,resizable=yes,scrollbars=yes");
	winOpened = true;
	commonWindow.window.focus();
}
function closeWindow(){
	
	if(commonWindow && commonWindow.open && !commonWindow.closed && commonWindow!=null )
		commonWindow.close();
}
//End of the functions for opening and closing the windows.
/*-----------------------------------------------------------------------------------------------------*/


function high(obj){
	obj.style.background = 'pink';		
}
function low(obj,color){
	obj.style.background = color;
}


/*---------------CheckAll--------------------*/


function CheckAll(chk)
{
	for (var i=0;i < document.forms[0].elements.length;i++)
	{
		var e = document.forms[0].elements[i];
		if (e.type == "checkbox")
		{
			e.checked = chk.checked;
		}
	}
}


/*--------------- End CheckAll--------------------*/






/*--------------- check delete --------------------*/


function cca(chkBox)
{    
	if(chkBox.checked == false)
		if(document.forms[0].chkDelete1.checked == true)
			document.forms[0].chkDelete1.checked = false;
}

function confirmDel()
{
	var checkDel = false;
	
	if(!document.forms[0].chkDelete){
		checkDel = false;
		//alert("Records Locked");
		alert("No Records to Delete");
		return false;
	}

	if(document.forms[0].chkDelete1.checked)
		checkDel = true;

	if (document.forms[0].chkDelete.checked)
		checkDel = true;
	else {
		for(i=0; i < document.forms[0].chkDelete.length; i++){
			if(document.forms[0].chkDelete[i].checked)
				checkDel = true;
		}
	}


	if(checkDel == false)
	{
		alert('Please select records to delete');
		return false;
	}
	else
	{
		msgResult=confirm("Are you sure to delete the record(s) ?") 

		if(msgResult)
		{
			setMode('D');
			return true;
		}
		else
			return false;
	}   //  end else of checkDel=false
}

function deleteRecords(param){
	if(confirmDel()){
		param; //document.frmApron.submit();
	}else{
	return false;
	}

}



/*---------------End check delete --------------------*/


/*---------------Init caps for Report Name --------------------*/

function resetIt() {

reportTitle = document.title;
reportTitle = reportTitle.toLowerCase();
reportTitle = reportTitle.substr(0, 4).toUpperCase() + reportTitle.substring(4, reportTitle.length);

var i = 0;
var j = 0;

while((j = reportTitle.indexOf(" ", i)) && (j != -1)) {
reportTitle = reportTitle.substring(0, j + 1) + reportTitle.substr(j + 1, 1).toUpperCase() + reportTitle.substring(j + 2, reportTitle.length);
i = j+1;

}
   if(document.all || document.getElementById){ // Browser Check
      document.title = reportTitle;
   }else{
      self.status = reportTitle; // Default to status.
   }
}
/*---------------End Init caps for Report Name --------------------*/