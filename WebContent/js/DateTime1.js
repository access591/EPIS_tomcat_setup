/*	In this Script contains 
	Time Validation
	Date Validation
*/


/*--------------Time Validation ------------------------ */

function validateTime24Hour(timeVar) 
{
// Function to validate time.  It assumes that the input is in 24 hour format.
// If the time is invalid, it will return an empty string.  Otherwise, returns the time itself.
// When this function is called, the return value should be tested as == "" or != "" as needed.
	if(timeVar == null || timeVar == "")
		return "";
	var regEx = new RegExp("[01][0-9][0-5][0-9]|2[0123][0-5][0-9]");
	return regEx.exec(timeVar);
}

function changeTimeDisplay(timeVar)
{
//  Function to convert time from 24 hour format, to HH:MM, with AM or PM attached.
	var hh=0;
	var mm=0;
	var timeStr = "AM";
	if(validateTime(timeVar) == false)
		return timeVar;

	hh = parseInt(timeVar.substr(0,2));
	mm = timeVar.substr(2,2);
	if(hh > 12)
	{
		hh = hh - 12;
		timeStr = " PM";
	}
	if(hh < 10)
		hh = "0" + hh;

	timeStr = hh + ":" + mm + timeStr;
	return timeStr;
}


function ValidateTime(timeStr)
{
	var timePat = /^(\d{1,2}):(\d{2})(:(\d{2}))?(\s?([Aa][Mm]|[Pp][Mm]))?$/;

	var matchArray = timeStr.match(timePat);
	if (matchArray == null) 
	{
		alert("Time is not in a valid format.");
		return false;
	}
	hour = matchArray[1];
	minute = matchArray[2];
	second = matchArray[4];
	ampm = matchArray[6];

	if (second=="") { second = null; }
	if (ampm=="") { ampm = null }

	if (hour < 0  || hour > 23)
	{
		alert("Hour must be between 1 and 12. (or 0 and 23 for military time)");
		return false;
	}
	if (hour <= 12 && ampm == null)
	{
		if (confirm("Please indicate which time format you are using.  OK = Standard Time, CANCEL = Military Time")) 
		{
			alert("You must specify AM or PM.");
			return false;
	   }
	}
	if  (hour > 12 && ampm != null)
	{
		alert("You can't specify AM or PM for military time.");
		return false;
	}
	if (minute<0 || minute > 59) 
	{
		alert ("Minute must be between 0 and 59.");
		return false;
	}
	if (second != null && (second < 0 || second > 59)) 
	{
		alert ("Second must be between 0 and 59.");
		return false;
	}
	return true;
}


/*--------------End Time Validation ------------------------ */

/*---------------------Date Conversion and Comparison --------------------------*/

// Function to convert the input date into a JavaScript parseable format.
// It assumes the input date to be of the format "DD/MM/YYYY" or "DD/Mon/YYYY"
// It will convert the date passed to the format "Mon DD, YYYY", since JavaScript expects that format.
// If objFlag is true, it will return the converted date as a JavaScript Date Object.
// If objFlag is false, it will return the converted date as a string.
function getJsDate(dateStr, objFlag)
{
	var dateArr = dateStr.split("/");
	var jsDate = "";
	if (isNaN(dateArr[1].charAt(0)))
		jsDate = dateArr[1] + " " + dateArr[0] + ", " + dateArr[2];
	else
	{
		monthArr = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
		monthNum = parseInt(dateArr[1],10) - 1;
		jsDate = monthArr[monthNum] + " " + dateArr[0] + ", " + dateArr[2];
	}
	if (objFlag == true)
		return new Date(Date.parse(jsDate));
	else
		return jsDate;
}

// Function to compare two dates.  This function calls the getJsDate to get a Java
// Will return "larger" if the first date is larger then the second.
// Will return "smaller" if the first date is smaller than the second.
// Will return "equal" if the two dates are equal.
function compareDates(date1, date2)
{
	date1 = new Date(Date.parse(getJsDate(date1,true)));
	date2 = new Date(Date.parse(getJsDate(date2,true)));
	if (date1 > date2)
		return 'larger';
	else
	if (date1 < date2)
		return 'smaller';
	else
		return 'equal';
}
function validateDateTime(varDate1,varTime1,varDate2,varTime2)
{
       var cmp=compareDates(varDate1,varDate2);
	   if (cmp == "larger")
			return false;
	   
	   if (cmp == "smaller")
			return true;
	   
	   if(cmp == "equal")
		    if(parseInt(varTime1) > parseInt(varTime2))
			  return false;

	  return true;		
}

/*--------------------- End Date Conversion and Comparison --------------------------*/



/*---------------------Date Validation --------------------------*/

function convert_date(field1)
{
var fLength = field1.value.length; // Length of supplied field in characters.
var divider_values = new Array ('-','.','/',' ',':','_',','); // Array to hold permitted date seperators.  Add in '\' value
var array_elements = 7; // Number of elements in the array - divider_values.
var day1 = new String(null); // day value holder
var month1 = new String(null); // month value holder
var year1 = new String(null); // year value holder
var divider1 = null; // divider holder
var outdate1 = null; // formatted date to send back to calling field holder
var counter1 = 0; // counter for divider looping 
var divider_holder = new Array ('0','0','0'); // array to hold positions of dividers in dates
var s = String(field1.value); // supplied date value variable

//If field is empty do nothing
if ( fLength == 0 ) {
   return true;
}

// Deal with today or now
if ( field1.value.toUpperCase() == 'NOW' || field1.value.toUpperCase() == 'TODAY' ) {
   
	var newDate1 = new Date();
	
  		if (navigator.appName == "Netscape") {
    		var myYear1 = newDate1.getYear() + 1900;
  		}
  		else {
  			var myYear1 =newDate1.getYear();
  			  alert(myYear1);
  		}
	var myMonth1 = newDate1.getMonth()+1;  
	var myDay1 = newDate1.getDate();
	field1.value = myDay1 + "/" + myMonth1 + "/" + myYear1;
	fLength = field1.value.length;//re-evaluate string length.
	s = String(field1.value)//re-evaluate the string value.
}

//Check the date is the required length
if ( fLength != 0 && (fLength < 6 || fLength > 11) ) {
	invalid_date(field1);
	return false;   
	}

// Find position and type of divider in the date
for ( var i=0; i<3; i++ ) {
	for ( var x=0; x<array_elements; x++ ) {
		if ( s.indexOf(divider_values[x], counter1) != -1 ) {
			divider1 = divider_values[x];
			divider_holder[i] = s.indexOf(divider_values[x], counter1);
		   //alert(i + " divider1 = " + divider_holder[i]);
			counter1 = divider_holder[i] + 1;
			//alert(i + " counter1 = " + counter1);
			break;
		}
 	}
 }

// if element 2 is not 0 then more than 2 dividers have been found so date is invalid.
if ( divider_holder[2] != 0 ) {
   invalid_date(field1);
	return false;   
}

// See if no dividers are present in the date string.
if ( divider_holder[0] == 0 && divider_holder[1] == 0 ) { 
   
		//continue processing
		if ( fLength == 6 ) {//ddmmyy
   		day1 = field1.value.substring(0,2);
     		month1 = field1.value.substring(2,4);
  			year1 = field1.value.substring(4,6);
  			if ( (year1 = validate_year(year1)) == false ) {
   			invalid_date(field1);
				return false; 
				}
			}
			
		else if ( fLength == 7 ) {//ddmmmy
   		day1 = field1.value.substring(0,2);
  			month1 = field1.value.substring(2,5);
  			year1 = field1.value.substring(5,7);
  			if ( (month1 = convert_month(month1)) == false ) {
   			invalid_date(field1);
				return false; 
				}
  			if ( (year1 = validate_year(year1)) == false ) {
   			invalid_date(field1);
				return false; 
				}
			}
		else if ( fLength == 8 ) {//ddmmyyyy
   		day1 = field1.value.substring(0,2);
  			month1 = field1.value.substring(2,4);
  			year1 = field1.value.substring(4,8);
			}
		else if ( fLength == 9 ) {//ddmmmyyyy
   		day1 = field1.value.substring(0,2);
  			month1 = field1.value.substring(2,5);
  			year1 = field1.value.substring(5,9);
  			if ( (month1 = convert_month(month1)) == false ) {
   			invalid_date(field1);
				return false; 
				}
			}
		
		if ( (outdate1 = validate_date(day1,month1,year1)) == false ) {
   		alert("The value " + field1.value + " is not a vaild date.\n\r" +  
			"Please enter a valid date in the format dd/mm/yyyy");
			field1.focus();
			field1.select();
			return false;
			}

		field1.value = outdate1;
		return true;// All OK
}
		
// 2 dividers are present so continue to process	
if ( divider_holder[0] != 0 && divider_holder[1] != 0 ) { 	
  	day1 = field1.value.substring(0, divider_holder[0]);
  	month1 = field1.value.substring(divider_holder[0] + 1, divider_holder[1]);
  	//alert(month1);
  	year1 = field1.value.substring(divider_holder[1] + 1, field1.value.length);
	}

if ( isNaN(day1) && isNaN(year1) ) { // Check day and year are numeric
	invalid_date(field1);
	return false;  
   }

if ( day1.length == 1 ) { //Make d day dd
   day1 = '0' + day1;  
}

if ( month1.length == 1 ) {//Make m month mm
	month1 = '0' + month1;   
}

if ( year1.length == 2 ) {//Make yy year yyyy
   if ( (year1 = validate_year(year1)) == false ) {
   	invalid_date(field1);
		return false;  
		}
}

if ( month1.length == 3 || month1.length == 4 ) {//Make mmm month mm
   if ( (month1 = convert_month(month1)) == false) {
   	alert("month1" + month1);
   	invalid_date(field1);
   	return false;  
   }
}

// Date components are OK
if ( (day1.length == 2 || month1.length == 2 || year1.length == 4) == false) {
   invalid_date(field1);
   return false;
}

//Validate the date
if ( (outdate1 = validate_date(day1, month1, year1)) == false ) {
   alert("The value " + field1.value + " is not a vaild date.\n\r" +  
	"Please enter a valid date in the format dd/mm/yyyy");
	field1.focus();
	field1.select();
	return false;
}

// Redisplay the date in dd/mm/yyyy format
field1.value = outdate1;

//The below line is added by muni, so that in the controller, no need to call convertMonth() method.
field1.value=field1.value.substring(0,3)+month_values[parseInt(field1.value.substring(3,5),10)-1]+ field1.value.substring(5,10);
return true;//All is well
}

var month_values = new Array ("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
function convert_month(monthIn) {


monthIn = monthIn.toUpperCase(); 

if ( monthIn.length == 3 ) {
	for ( var i=0; i<12; i++ ) 
		{
   	if ( monthIn == month_values[i] ) 
   		{
			monthIn = i + 1;
			if ( i != 9 && i != 10 && i != 11 ) 
				{
   			monthIn = '0' + monthIn;
				}
			return monthIn;
			}
		}
	}

else if ( monthIn.length == 4 && monthIn == 'SEPT') {
   monthIn = '09';
   return monthIn;
	}
	
else {
	return false;
	} 
}


function invalid_date(inField) 
{
alert("The value " + inField.value + " is not in a vaild date format.\n\r" + 
        "Please enter date in the format dd/mm/yyyy");
inField.focus();
inField.select();
return true   
}


function validate_date(day2, month2, year2)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
var DayArray = new Array(31,28,31,30,31,30,31,31,30,31,30,31);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
var MonthArray = new Array("01","02","03","04","05","06","07","08","09","10","11","12");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
var inpDate = day2 + month2 + year2;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
var filter=/^[0-9]{2}[0-9]{2}[0-9]{4}$/;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

//Check ddmmyyyy date supplied
if (! filter.test(inpDate))                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
  {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
  return false;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
  }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
/* Check Valid Month */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
filter=/01|02|03|04|05|06|07|08|09|10|11|12/ ;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
if (! filter.test(month2))                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
  {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
  return false;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
  }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
/* Check For Leap Year */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
var N = Number(year2);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
if ( ( N%4==0 && N%100 !=0 ) || ( N%400==0 ) )                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
  	{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
   DayArray[1]=29;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
  	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
/* Check for valid days for month */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
for(var ctr=0; ctr<=11; ctr++)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
  	{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
   if (MonthArray[ctr]==month2)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
   	{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
      if (day2<= DayArray[ctr] && day2 >0 )                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
        {
        inpDate = day2 + '/' + month2 + '/' + year2;       
        return inpDate;
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
      else                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
        {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
        return false;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
   	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
   }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
}



function validate_year(inYear) 
{
var d= new Date();
    var fullYear=d.getFullYear();
    var str =fullYear.toString();
    var curYear = str.substr(2,2);
	
if ( inYear <= curYear ) 
	{
   inYear = "20" + inYear;
   return inYear;
	}
else if ( inYear > curYear )
	{
   inYear = "19" + inYear;
   return inYear;
	}
else 
	{
	return false;
	}  
 return inYear;
}




/*---------------------End Date Validation --------------------------*/


/*---------------------Date Format-----------------------------------*/
/*Accepts date in dd/MM/yyyy format and converts it into dd/Mon/yyyy format*/

function FormatDate(dateStr)
{
	var dateArr = dateStr.split("/");
	var jsDate = "";
	        monthArr = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
		if (isNaN(dateArr[1].charAt(0)))
		return dateStr;
		monthNum = parseInt(dateArr[1],10) - 1;
		jsDate = dateArr[0] + "/" + monthArr[monthNum] + "/" + dateArr[2];
	return jsDate;
}

var m_names = new Array("Jan", "Feb", "Mar", 
"Apr", "May", "Jun", "Jul", "Aug", "Sep", 
"Oct", "Nov", "Dec");

/* this function will accept date in java script format and give output as dd/Mon/yyyy format*/
function FormatDate1(d){
 //var d = new Date(date1);
var curr_date  = d.getDate();
var curr_month = d.getMonth();
var curr_year  = d.getFullYear();
//document.write(curr_date + "/" + m_names[curr_month] + "/" + curr_year);
var jsdate=curr_date + "/" + m_names[curr_month] + "/" + curr_year;
return jsdate;
}
/*---------------------End Date Format-----------------------------------*/

function getCurrentDate(){	
	var now = new Date();
	return(now.getDate()+'/'+m_names[now.getMonth()]+'/'+now.getFullYear());
}