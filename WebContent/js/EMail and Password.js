/*		In this script contains 
		EMail Validation and
		Password Validation
*/




/*---------------------EMail Validation --------------------------*/


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
		alert("Email address is not valid (check @ and .'s)")
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
		domArr[domArr.length-1].length>3) 
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



/*---------------------End EMail Validation --------------------------*/



/*---------------------Password Validation --------------------------*/


function ValidatePwd(PWord,PWord1) 
{
	var invalid = " "; // Invalid character is a space
	var minLength = 6; // Minimum length
	var pw1 = PWord;
	var pw2 = PWord1;

	// check for a value in both fields.
	if (pw1 == '' || pw2 == '') 
	{
		alert('Please enter your password twice.');
		return false;
	}
	// check for minimum length
	if (PWord.length < minLength) 
	{
		alert('Your password must be at least ' + minLength + ' characters long. Try again.');
		return false;
	}
	// check for spaces
	if (PWord.indexOf(invalid) > -1) 
	{
		alert("Sorry, spaces are not allowed.");
		return false;
	}
	else 
	{
		if (pw1 != pw2) 
		{
			alert ("You did not enter the same new password twice. Please re-enter your password.");
			return false;
		}
		
	}return true;
}



/*---------------------End Password Validation --------------------------*/
