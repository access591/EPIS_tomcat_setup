package com.epis.utilities;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.view.save.JRRtfSaveContributor;
import net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor;

import com.epis.bean.investment.QuotationRequestBean;





public class LetterFormates {
	
	public void generateHtmlOutput1(JasperPrint print,HttpServletRequest req,HttpServletResponse res,Map reportMap)throws IOException, JRException
	{
		 JRHtmlExporter exporter = new JRHtmlExporter();
		   	res.setContentType("text/html");

		 

			  exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			  exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://abc.html" );

		 
			  exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, res.getOutputStream());
			  res.setHeader("Content-Disposition", "inline;filename=abc.html");
			  res.setContentType("text/html");
			  exporter.exportReport();

	}
	public void generateHtmlOutput(JasperPrint jasperprint, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
    throws IOException, JRException
{
    
    JRHtmlExporter exporter = new JRHtmlExporter();
  System.out.println("--------+++++++++++------------");
    Map imagesMap = new HashMap();
    PrintWriter out = httpservletresponse.getWriter();
    httpservletrequest.getSession().setAttribute("IMAGES_MAP",imagesMap);
    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
    exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
	exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
	//exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,"px");
	exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "generateImageReport.do?image=");
    exporter.exportReport();
    
    
}
	public void generateWordOutput(JasperPrint print,HttpServletResponse httpservletresponse,Map map, String filePath,JRBeanCollectionDataSource dataSource,String fileName,JasperReport jasperReport)
	  throws IOException, JRException
	{
	 

		JRRtfExporter exporter = new JRRtfExporter();
	   	httpservletresponse.setContentType("application/rtf");

	 

		  exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		  exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://"+fileName+".doc" );

		  
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpservletresponse.getOutputStream());
		  httpservletresponse.setHeader("Content-Disposition", "inline;filename="+fileName+".doc");
		  httpservletresponse.setContentType("application/rtf");
		  exporter.exportReport();
		  
		 

		
	 
		
	   
	  
	}
	
	public void generateWordFile(JasperPrint print,String filePath)
	{
	try
	{
	File f2 = new File(filePath);
	JRRtfSaveContributor rtfobj = new JRRtfSaveContributor();
	rtfobj.save(print,f2); 
	}catch(Exception e)
	{
		System.out.println(e);
		}
	//path="C:/Documents and Settings/srikanth/Desktop/"+wordtext+".doc";
	//return path; 
	}
	public void genPdfFile(JasperPrint print,String filePath)
	{

	try
	{
	JasperExportManager.exportReportToPdfFile(print,filePath+".pdf");
	
	
	}catch(Exception e)
	{
		System.out.println(e);
		}
	
	}
	public void genExcelReport(JasperPrint print,String filePath)
	{
		try{
			File f2 = new File(filePath);
			JRSingleSheetXlsSaveContributor excelobj = new JRSingleSheetXlsSaveContributor() ;
			excelobj.save(print,f2);
		}
		catch(Exception e)
		{
			System.out.println(e);
			}
	}

	
	public void  sendArrangersMail(QuotationRequestBean bean,String path,String email) throws Exception
	{

		                 //String host = "172.16.3.1";
	                     //Authenticator auth = new Authentication("aims@aai.aero","password");

		
						
						
						

						

						// Create properties for the Session
						Properties prop1 = new Properties();

						// If using static Transport.send(),need to specify the mail server here
						prop1.put("mail.smtp.host", bean.getHost());
						System.out.println("the host is..."+bean.getHost());

						// To see what is going on behind the scene
						prop1.put("mail.smtp.auth", "true");

						//Setting Authentication Mail Server UserID and Password 
						Authenticator auth = new Authentication(bean.getAuthId(),bean.getAuthPwd());
						System.out.println("the password is..."+bean.getAuthPwd());

						// Get a session
						Session session1 = Session.getInstance(prop1,auth);

						MimeMessage message = new MimeMessage(session1);

					    

						BodyPart messageBodyPart = new MimeBodyPart();
						
						  String subject="Quotation Letter Formate";
						  

			                try {


							 


								  										
			                			Multipart multipart = new MimeMultipart();											
					    
	    								InternetAddress add=new  InternetAddress("aims@aai.aero","Airport Authority Of India");
										message.setFrom(add);
										
										
										
                                      


	                                    System.out.println("the email id is..."+email);
										
										message.setRecipients(Message.RecipientType.TO,email);
										message.setSubject( subject);

							

										

										//message.setText(body);*/

										//first attachment

										System.out.println("24234214234");
										DataSource source = new FileDataSource(path+".pdf");
										messageBodyPart.setDataHandler(new DataHandler(source));
										messageBodyPart.setFileName("quotationLetter.pdf"); 

										multipart.addBodyPart(messageBodyPart);
										
										message.setContent(multipart);										
										Transport.send( message );


							    		

									     }

									  
									  catch (MessagingException e)
										  {
										     
											System.err.println("Cant send mail. " + e.getMessage());
					                      }
					
					   
		
	    }



}
