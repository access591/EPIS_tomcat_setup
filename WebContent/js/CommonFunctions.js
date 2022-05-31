/*		This Script Contains
		ValidateName(StrObject) 
		String Validation - ValidateString(StrObject)
		Left Padding (lpad of Oracle) - lpad(strToPad, totLength, padChar)
		Right Padding (rpad of Oracle) - rpad(strToPad, totLength, padChar)
		Number Validation		-	ValidateNum(NumObject)
		ValidateTextArea (Alpha numeric Validation) - ValidateTextArea(StrNumObject)
		ValidateFrameTextArea (Alpha numeric Validation for IFrame) - ValidateFrameTextArea(StrNumObject)
		AlphaNumeric Validation (Alphanumeric without 'enter' and single quote characters)
		ValidateAlphaNumeric(StrNumObject)
		AlphaNumericwithoutSpace Validation (Aplhanumeric without space, with \n and ')
		ValidateAlphaNumericwithoutSpace(StrNumObject)
		Phone Number Validation		-	ValidatePh(phno)
		FloatPoint Validation		-	ValidateFloatPoint(FloatObject,maxLength,precision)
		InitCap	-	changeCase(frmObj)
		UpperCase	-	UpperCase(TxtObj)
		LowerCase	-	LowerCase(TxtObj1)
		ltrim	-	ltrim(s)
		rtrim	-	rtrim(s)
		trim	-	trim(s)
		Trim all the Text Boxes	- TrimAll()
		breakLines(str, charsPerLine)
		ValidateAlphabetComma(StrNumObject)

		Setting the precision	-	SetPrecision(numObject,precision) // added by Munikumar
		CheckLength - checks the length of the textbox or textarea - CheckLength(object,length) // added by Munikumar


*/
  

/*---------------------String Validation --------------------------*/
     
// Function to validate the input string and check if contains only
// alphabets, dots, hyphens, forward slashes or spaces.
// Will return true or false
function ValidateName(StrObject) 
{
	if (StrObject == null || trim(StrObject) == "")
		return true;
	var regEx = /[A-Za-z-()&. ]+/g;
	var str = "";
	str = regEx.exec(StrObject);
	if (str == StrObject)
		return true;
	else
		return false;
}

/*---------------------End String Validation --------------------------*/

/*---------------------Accounting Code Validation --------------------------*/
     
// Function to validate the input string and check if contains only
// numbers, dots.
// Will return true or false
function ValidateAccountingCode(StrObject) 
{
	if (StrObject == null || trim(StrObject) == "")
		return true;
	var regEx = /[0-9.]+/g;
	var str = "";
	str = regEx.exec(StrObject);
	if (str == StrObject)
		return true;
	else
		return false;
}


/*---------------------End String Validation --------------------------*/



/*---------------------String Validation --------------------------*/

// Function to validate the input string and check if contains only
// alphabets, dots, hyphens, forward slashes or spaces.
// Will return true or false
function ValidateString(StrObject) 
{
	if (StrObject == null || trim(StrObject) == "")
		return true;
	var regEx = /[A-Za-z ]+/g;
	var str = "";
	str = regEx.exec(StrObject);
	if (str == StrObject)
		return true;
	else
		return false;
}

/*---------------------End String Validation --------------------------*/


/*---------------------String Functions --------------------------*/

// Function like oracle lpad, which will place padChar before strToPad 
// and bring the total length of the string to totLength
// Note that this can pad only a single character and not multiple characters.
// If padChar is more than 1 character, it will return strToPad as it is.
// Will return the padded string.
function lpad(strToPad, totLength, padChar)
{
	if(padChar.length != 1 || strToPad == null || trim(strToPad) == "")
		return strToPad;
	var lenPad = totLength - strToPad.length;
	if(lenPad <= 0)
		return strToPad;
	for(var i=1; i<=lenPad; i++)
		strToPad = padChar + strToPad;
	return strToPad;
}

// Function like oracle rpad, which will place padChar After strToPad 
// and bring the total length of the string to totLength
// Note that this can pad only a single character and not multiple characters.
// If padChar is more than 1 character, it will return strToPad as it is.
// Will return the padded string.
function rpad(strToPad, totLength, padChar)
{
	if(padChar.length != 1 || strToPad == null || trim(strToPad) == "")
		return strToPad;
	var lenPad = totLength - strToPad.length;
	if(lenPad <= 0)
		return strToPad;
	for(var i=1; i<=lenPad; i++)
		strToPad = strToPad + padChar;
	return strToPad;
}

/*---------------------End String Functions --------------------------*/



/*---------------------Number Validation --------------------------*/

// Function to check if the input is Numeric or not.
// Will return true or false
function ValidateNum(NumObject)
{
	var checkOK = "0123456789";
	var checkStr = NumObject;
	var allValid = true;
	for (i = 0;  i < checkStr.length;  i++)
	{
		ch = checkStr.charAt(i);
		for (j = 0;  j < checkOK.length;  j++)
		if (ch == checkOK.charAt(j))
		break;
		if (j == checkOK.length)
		{
			allValid = false;
			break;
		}
	}
	return(allValid);
}

/*---------------------End Number Validation --------------------------*/



/*---------------------Validate Text Area Validation --------------------------*/

// Function to check if the input string is alphanumeric or not and does not allow
//  the Characters inside the Square Brackets.
// Will return true or false
function ValidateTextArea(StrNumObject)
{
	if(StrNumObject == null || trim(StrNumObject) == "")
		return true;
	var regEx = /[^<>"%]+/g;
	var str = "";
	str = regEx.exec(StrNumObject);
 	if (str == StrNumObject)
		return true;
	else
	{
		alert("This Field Should Not Contain < > ; % or \" ");
		return false;
	}
}

/*---------------------End Validate Text Area Validation --------------------------*/

/*---------------------Validate Frame Text Area Validation --------------------------*/

// Function to check if the input string is alphanumeric or not and allows
// all the Characters inside the Square Brackets.
// Will return true or false
function ValidateFrameTextArea(StrNumObject)
{
	if(StrNumObject == null || trim(StrNumObject) == "")
		return true;
	var regEx = /[^<>'%]+/g;
	var str = "";
	str = regEx.exec(StrNumObject);
	if (str == StrNumObject)
		return true;
	else
	{
		alert("This Field Should Not Contain < > ; %or \' ");
		return false;
	}
}

/*---------------------End Validate Frame Text Area Validation --------------------------*/


/*---------------------AlphaNumeric without enter and single code Validation --------------------------*/

// Function to check if the input string is alphanumeric or not.
// Will check for StrNumObject containing only alphabets, numbers, hyphens, 
// forward slashes, or spaces.
// Will return true or false
function ValidateAlphaNumeric(StrNumObject)
{
	if(StrNumObject == null || trim(StrNumObject) == "")
		return true;
	//var regEx = /[A-Za-z0-9_.()&\-/ ]+/g;
	var regEx = /[A-Za-z0-9_.()&\-\ ]+/g;
	var str = "";
	str = regEx.exec(StrNumObject);
	if (str == StrNumObject)
		return true;
	else
		return false;
}
function ValidateSpecialAlphaNumeric(StrNumObject)
{
	if(StrNumObject == null || trim(StrNumObject) == "")
		return true;
	//var regEx = /[A-Za-z0-9_.()&\-/ ]+/g;
	var regEx = /[A-Za-z0-9_.():;,^#$&\-\% ]+/g;
	var str = "";
	str = regEx.exec(StrNumObject);
	if (str == StrNumObject)
		return true;
	else
		return false;
}

/*---------------------End AlphaNumeric Validation --------------------------*/



/*---------------------AlphaNumeric without Space Validation --------------------------*/

// Function to check if the input string is alphanumeric or not.
// Will check for StrNumObject containing only alphabets or numbers
// Will return true or false
function ValidateAlphaNumericwithoutSpace(StrNumObject)
{
	if(StrNumObject == null || trim(StrNumObject) == "")
		return true;
	var regEx = /[A-Za-z0-9]+/g;
	var str = "";
	str = regEx.exec(StrNumObject);
	if (str == StrNumObject)
		return true;
	else
		return false;
}

// ---------------------------Phone Number validation ------------------------------
function ValidatePh(phno)
{
	if(phno == null || trim(phno) == "")
		return true;
	var regEx = /[0-9\-() ,+]+/g;
	var str = "";
	str = regEx.exec(phno);
	if (str == phno)
		return true;
	else
		return false;
}


/*---------------------Float Number Validation --------------------------*/

// Function to validate if input value is a floating point number,maxLength,precision
// Will return true or false

function ValidateFloatPoint(FloatObject,maxLength,precision)
{
	if(FloatObject == null || trim(FloatObject) == "")
		return true;
	var allValid = true;
	var checkOK = "0123456789.";
	var checkStr = FloatObject;
	var noofdots=0;
	var decimal=0;
	var lenBeforeDot =0;
	var maxNumb="";

	if (parseFloat(checkStr)==checkStr && parseFloat(checkStr) >= 0)
	{
		allValid=true;
	}
	else
	{
		allValid=false;
		alert("Please Enter Valid Number (Expecting Numeric Value) ");
		return allValid;
	}

	lenBeforeDot=maxLength-precision;
	for (i=1; i <= lenBeforeDot;i++)
	{
		maxNumb += "9";
	}

	maxNumb += ".";

	for (i=1; i <= precision;i++)
	{
		maxNumb += "9";
	}

	decimal=checkStr.indexOf(".");

	if(noofdots > 1)
	{
		allValid=false;
		alert("Please Enter Valid Number (Expecting Numeric Value) ");
		return allValid;
	}

	if(decimal==-1)
    {
		str3=checkStr;
		if(str3.length>lenBeforeDot)
		{
			alert("Please Enter Value > 0 and < = " + maxNumb);
			return false;	
		}
	}
	else
	{
		sstr=checkStr.substring(0,decimal);
		if(sstr.length>lenBeforeDot)
		{
			alert("Please Enter Value > 0 and < = " + maxNumb);
			return false;	
		}

		str3=checkStr.substring(decimal+1,checkStr.length);
		if(str3.length>precision)
		{
			alert("Please Enter Value > 0 and < = " + maxNumb);
			return false;	
		}
	}
	return allValid;
}

/*---------------------End Float Number Validation --------------------------*/



/*---------------------InitCap Function --------------------------*/

// Function to change the first letter of each word in the input form object to uppercase
// Will change the input form object's value itself.
// Please note that this function works on a form object.
// The object name should be supplied, not its value.
function changeCase(frmObj) {
var index;
var tmpStr;
var tmpChar;
var preString;
var postString;
var strlen;
tmpStr = frmObj.value.toLowerCase();
strLen = tmpStr.length;
if (strLen > 0)  {
for (index = 0; index < strLen; index++)  {
if (index == 0)  {
tmpChar = tmpStr.substring(0,1).toUpperCase();
postString = tmpStr.substring(1,strLen);
tmpStr = tmpChar + postString;
}
else {
tmpChar = tmpStr.substring(index, index+1);
if (tmpChar == " " && index < (strLen-1))  {
tmpChar = tmpStr.substring(index+1, index+2).toUpperCase();
preString = tmpStr.substring(0, index+1);
postString = tmpStr.substring(index+2,strLen);
tmpStr = preString + tmpChar + postString;
         }
      }
   }
}
frmObj.value = tmpStr;
}

/*---------------------End InitCap Function --------------------------*/



/*---------------------Upper Case Conversion --------------------------*/

// Function to convert input string to upper case.
// Please note that this function directly manipulates the form object.
// It does not return any value, directly changes the form element.
function UpperCase(TxtObj)
{
	var txt=TxtObj.value;
    if(txt!="")
    {
		TxtObj.value=txt.toUpperCase();
    }
}

/*---------------------End Upper Case Conversion --------------------------*/



/*---------------------Lower Case Conversion --------------------------*/

// Function to convert input string to lower case.
// Please note that this function directly manipulates the form object.
// It does not return any value, directly changes the form element.
function LowerCase(TxtObj1)
{
	var txt=TxtObj1.value;
    if(txt!="")
    {
		TxtObj1.value=txt.toLowerCase();
    }
}

/*---------------------End Lower Case Conversion --------------------------*/



/*---------------------Trim Validation --------------------------*/

// Function to trim the leading spaces in the input string.
// Will return the replaced string.
function ltrim(s)
{
	return s.replace( /^\s*/, "" );
}

// Function to trim the trailing spaces in the input string.
// Will return the replaced string.
function rtrim(s)
{
	return s.replace( /\s*$/, "" );
}

// Function to trim the leading and trailing spaces in the input string.
// Will return the replaced string.
function trim(s)
{
	return rtrim(ltrim(s));
}

/*---------------------End Trim Validation --------------------------*/

/*---------------------Trim all the Text Boxes --------------------------*/
function TrimAll(f)
{
    for (var i=0;i < document.forms[f].elements.length;i++)
	{
		var e = document.forms[f].elements[i];		
		if (e.type == "text" || e.type == "textarea")
		{
			e.value = trim(e.value);
		}
	}
}
/*---------------------End Trim all the Text Boxes --------------------------*/

function breakLines(str, charsPerLine)
{
	if(str.length <= charsPerLine)
		return str;
	var no = str.length / charsPerLine;
	var returnStr = new String("");
	var i = 0;
	var k = 0;
	for(i=0,k=0; i<no; i++,k+=charsPerLine)
	{
		if(str.substr(k+charsPerLine,1)!=" ")
			returnStr += str.substr(k,charsPerLine) + "-<br>";
		else
			returnStr += str.substr(k,charsPerLine) + "<br>";
	}
	return returnStr;
}

/*---------------------Alphabet with Comma Validation --------------------------*/

// Function to check if the input string is alphabet with comma or not and allows
// all the Characters inside the Square Brackets.
// Will return true or false
function ValidateAlphabetComma(StrNumObject)
{
	if(StrNumObject == null || trim(StrNumObject) == "")
		return true;
	var regEx = /[A-Za-z,]+/g;
	var str = "";
	str = regEx.exec(StrNumObject);
	if (str == StrNumObject)
		return true;
	else
		return false;
}

/*---------------------End Alphabet with Comma Validation --------------------------*/

/*---------------------Alphabet with Comma Validation --------------------------*/

// Function to check if the input string is alphabet with comma or not and allows
// all the Characters inside the Square Brackets.
// Will return true or false
function ValidateAlphabetComma(StrNumObject)
{
	if(StrNumObject == null || trim(StrNumObject) == "")
		return true;
	var regEx = /[A-Z1-9,]+/g;
	var str = "";
	str = regEx.exec(StrNumObject);
	if (str == StrNumObject)
		return true;
	else
		return false;
}

/*---------------------End Alphabet with Comma Validation --------------------------*/

/*------------------------------Setting the precision to the given number--------------------*/

//Function to check the precision for the given number
//this function will set the precision to the precision specified by the user
//Name: Munikumar.
function SetPrecision(numObject,precision)
{	
	numObject=String(numObject);
	if(numObject==null||numObject=="")
		numObject=0;
	if(precision==null||precision=="")
		return numObject;
	var point=numObject.indexOf(".");
	var integerPart;
	var decimalPart;
	if(point>-1)
	{
		integerPart=numObject.substring(0,point);
		decimalPart=numObject.substring(point+1);
		if(decimalPart.length<=precision)
		{
			numObject=integerPart+"."+decimalPart;
			for(var i=decimalPart.length;i<precision;i++)
				numObject+='0';
			return numObject;
		}
		else
		{
			var count=0;
			var flag=0;
			for(var i=0;i<decimalPart.length;i++)
				count+=parseInt(decimalPart.substring(i,i+1));
			if(count!=0)
				flag=1;
			count=0;
			nos=new Array();
			for(var i=0;i<=precision;i++)
				nos[nos.length]=decimalPart.substring(i,i+1);
			if(nos[nos.length-1]>=5)
				nos[nos.length-2]=parseInt(nos[nos.length-2])+1;
			for(var i=nos.length-2;i>=0;i--)
			{	
				if(nos[i]==10)
				{
					nos[i]=0;
					nos[i-1]=parseInt(nos[i-1])+1;
				}
			}
			decimalPart="";
			for(var i=0;i<=nos.length-2;i++)
				decimalPart+=nos[i];
			for(var i=0;i<=nos.length-2;i++)
				count+=parseInt(nos[i]);			
			if(count==0&&flag==1)
				integerPart=parseInt(integerPart)+1;			
			return numObject=integerPart+"."+decimalPart;
		}
	}
	else
	{
		if(precision!=0)
			numObject+=".";
		for(var i=0;i<precision;i++)
			numObject+='0';
		return numObject;
	}
}

/*------------------------------------End SetPrecision -----------------------------------------*/

/* ----------------------------- function to check the length for textbox/textarea ------------------*/
function CheckLength(object,length){
	if(object.length>length)
		object.value = object.value.substring(0,length);
}
/*------------------------------------End of CheckLength()-----------------------------------------*/

/*------------------ The below function will construct query string for report -------------------*/
//Name: Munikumar.
function ConstructParameters(obj,elements){	
	query = "";
	if(elements!=null && elements!='' && elements!='undefined'){
		elements = elements.split(',');	
		for(var i=0;i<elements.length;i++)
			query += elements[i]+'='+document.all[elements[i]].value+'&';
	}else{
		for(var i=0;i<obj.elements.length;i++)
			query += obj.elements[i].name+'='+obj.elements[i].value+'&';
	}
	return query;
}
/*------------------------------------------------------------------------------------------------*/
/*----------------------------------The below function will return the selected value of a Radio Object-------*/
function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}
/*------------------------------------------------------------------------------------------------------------*/
function checkBoxFocus(obj){

	if(obj){
	
	if(obj[0])
		obj[0].focus();
	else
		obj.focus();
	}
}