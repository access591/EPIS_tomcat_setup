package com.epis.utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
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

import com.epis.bean.MailBean;
import com.epis.bean.MailContent;



public class MailUtil {

	/**
	 * @param args
	 */
	public static void prePareMail(String reportPath) {
		// TODO Auto-generated method stub
		String fromAdd="radha.pasupuleti@navayuga.co.in",recieptToAddr="radha.pasupuleti@navayuga.co.in"; 
		MailContent mailContent=new MailContent();
		mailContent.setBodyContent1("Attached Sanction Order Report has generated. Please check and let us know if any issue is there.");
		mailContent.setSignInfo("EPIS - Team");
		List ccRecipient=new ArrayList();
		List attachementList=new ArrayList();
		//ccRecipient.add("sambag@navayuga.co.in");		
		//ccRecipient.add("sureshkumar.repaka@navayuga.co.in");
		//ccRecipient.add("prasad.pedipina@navayuga.co.in");
		ccRecipient.add("sandeepkumar.padam@navayuga.co.in");
		MailBean mBean=new MailBean();
		mBean.setFromAddress(fromAdd);
		mBean.setToRecipient(recieptToAddr);
		mBean.setCcRecipient(ccRecipient);
		mBean.setPlainBodyContent(false);
		mBean.setHtmlBodyContent(true);
		mBean.setMailSubject("Test Mail");
		mBean.setContent(mailContent);
		mBean.setAttachement(true);
		attachementList.add(reportPath);
		mBean.setFileAttachedList(attachementList);
		try {
			sendMail(mBean);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void sendMail(final MailBean mBean) throws InvalidDataException{
		List ccRecipients=new ArrayList();
		ResourceBundle bundle=ResourceBundle.getBundle("com.epis.resource.MAIL");
		Properties properties = System.getProperties();
	
		properties.setProperty("mail.smtp.host", bundle.getString("mail.host"));
		properties.put("mail.debug", "true");

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);
	      try{
	          // Create a default MimeMessage object.
	          MimeMessage message = new MimeMessage(session);
	        
	          // Set From: header field of the header.
	          message.setFrom(new InternetAddress(mBean.getFromAddress()));

	         
	    
	          InternetAddress[] toAddress = {new InternetAddress(mBean.getToRecipient())};
	          message.setRecipients(Message.RecipientType.TO, toAddress);
	          ccRecipients=mBean.getCcRecipient();
	          InternetAddress[] addressCC = new InternetAddress[ccRecipients.size()]; 
	         
	          for(int i=0;i<ccRecipients.size();i++){
	        	  if(!((String)ccRecipients.get(i)).equals("")){
	        		  addressCC[i]=new InternetAddress((String)ccRecipients.get(i));
	        	  }
	          }
	          Multipart mContent=new MimeMultipart();
		      message.setRecipients(Message.RecipientType.CC, addressCC);
		      if(mBean.isAttachement()==true){
	        	  try {
					addAttachements(mBean,mContent);
				} catch (InvalidDataException e) {
					// TODO Auto-generated catch block
					throw e;
				}
	          }
		      MimeBodyPart mbodyPart = new MimeBodyPart();
		     
	          if(mBean.isPlainBodyContent()==true){
	        	  mbodyPart.setContent(setTextFormatingContent(mBean),"text/plain; charset=utf-8");
	          }else{
	        	
	        	  mbodyPart.setContent(setHtmlFormatingContent(mBean),"text/html; charset=utf-8");
	        	// setHtmlContent(mBean,mContent);
	          }
	          mContent.addBodyPart(mbodyPart);
	          message.setContent(mContent);
	          // Set Subject: header field
	        
	          message.setSubject(mBean.getMailSubject());
	        
	          // Now set the actual message
	          message.setSentDate(new Date());
	          // Send message
	          
	          Transport.send(message);
	          
	       }catch (MessagingException mex) {
	          mex.printStackTrace();
	       }

	}
	private static String setTextFormatingContent(MailBean bean){
		StringBuffer buffer=new StringBuffer();
	
		buffer.append(bean.getContent().getSalutation());
		
	
		buffer.append(bean.getContent().getBodyContent1());
		
		if(!bean.getContent().getBodyContent2().equals("")){
			
			buffer.append(bean.getContent().getBodyContent2());
		
		}
		if(!bean.getContent().getBodyContent3().equals("")){
			
			buffer.append(bean.getContent().getBodyContent3());
		
		}
		
		buffer.append("Regards,");
		buffer.append(bean.getContent().getSignInfo());
	
		//buffer.append(content.getSalutation());
		return buffer.toString();
	}
	private static String setHtmlFormatingContent(MailBean bean){
		StringBuffer buffer=new StringBuffer();
		buffer.append("<html>");
		
		buffer.append("<head>");
		buffer.append("<title>");
		buffer.append(bean.getMailSubject());
		buffer.append("</title>");
		buffer.append("</head>");
		buffer.append("<body>");
		buffer.append(bean.getContent().getSalutation());
		buffer.append("<br/>");
		buffer.append("<p>");
		buffer.append(bean.getContent().getBodyContent1());
		buffer.append("</p>");
		if(!bean.getContent().getBodyContent2().equals("")){
			buffer.append("<p>");
			buffer.append(bean.getContent().getBodyContent2());
			buffer.append("</p>");
		}
		if(!bean.getContent().getBodyContent3().equals("")){
			buffer.append("<p>");
			buffer.append(bean.getContent().getBodyContent3());
			buffer.append("</p>");
		}
		buffer.append("<p>");
		buffer.append("Regards,<br/>");
		buffer.append(bean.getContent().getSignInfo());
		buffer.append("</p>");
		buffer.append("</body>");
		buffer.append("</html>");
		//buffer.append(content.getSalutation());
		return buffer.toString();
	}


	static class HTMLDataSource implements DataSource {
        private String html;
 
        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }
 
        // Return html string in an InputStream.
        // A new stream must be returned each time.
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("Null HTML");
            return new ByteArrayInputStream(html.getBytes());
        }
 
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }
 
        public String getContentType() {
            return "text/html";
        }

		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
 
     
    }

	private static void addAttachements(final MailBean mBean,Multipart mContent) throws InvalidDataException{
		String fileName="";
	
		for(int i=0;i<mBean.getFileAttachedList().size();i++){
			fileName=(String)mBean.getFileAttachedList().get(i);
			  MimeBodyPart mbp = new MimeBodyPart();
			  FileDataSource fds = new FileDataSource(fileName);
	            try {
					mbp.setDataHandler(new DataHandler(fds));
					mbp.setFileName(fds.getName());
					mContent.addBodyPart(mbp);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					throw new InvalidDataException(e.getMessage());
				}
	           

		}
		
	}
}
