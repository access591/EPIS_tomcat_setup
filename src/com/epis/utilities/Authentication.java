
package com.epis.utilities;

import javax.mail.*;  
public class Authentication extends javax.mail.Authenticator
{
	public String username;
	public String password;

    public Authentication()
	{

	}

	public Authentication(String uname,String pwd)
	{
		username=uname;
		password=pwd;
	}


public PasswordAuthentication getPasswordAuthentication()
{ 
return new PasswordAuthentication(username,password);
}
}





