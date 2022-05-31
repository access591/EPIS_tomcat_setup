package com.aims.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//import javax.mail.util.ByteArrayDataSource;

public class MailSending {
	static javax.mail.Authenticator mAuthenticator = null;
	public void sendMail(String[] to,String from,String message,String subject) throws MessagingException, IOException{
		Properties props = new Properties() ;
		//props.put("mail.smtp.host" , "65.254.250.104" );
		props.put("mail.smtp.host" , "mail.navayuga.com" );
		//65.254.250.104        72.232.25.109
		// create some properties and get the default Session
		Session session = Session.getDefaultInstance(props, null);
		//session.setDebug( debug);
		// create a message
		Message msg = new MimeMessage(session);
		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom) ;
		InternetAddress[] toAddress = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++)
	 	       toAddress[i] = new InternetAddress(to[i]);
			msg.setRecipients( Message.RecipientType.TO, toAddress);
	
		msg.setSubject(subject);
		Multipart mp1;
		MimeBodyPart mbp1;
	    mp1 = new MimeMultipart();
	    
	    mbp1 = new MimeBodyPart();
	    //mbp1.setDataHandler(new DataHandler(new ByteArrayDataSource(message, "text/plain")));
	    mp1.addBodyPart(mbp1);
	  //  File  f = new File(fileattach);

	  //  if (f.exists()) {
	        
	       // MimeBodyPart  mbp2 = new MimeBodyPart();
	      //  FileDataSource  fds = new FileDataSource(fileattach);

	       // mbp2.setDataHandler(new DataHandler(fds));
	       // mbp2.setFileName(fds.getName());
	      //  mp1.addBodyPart(mbp2);
	        
	 //   } else {
	 //       throw new MessagingException("Attachment not found: " + fileattach);
	 //   }

	    msg.setContent(mp1);
		Transport.send(msg);
	}
		 
}
