package com.epis.listener.investment;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class JasperContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContext) {
		// TODO Auto-generated method stub
		
		System.setProperty("jasper.reports.compile.class.path", servletContext.getServletContext().getRealPath("/WEB-INF/lib/jasperreports-1.3.3.jar") + System.getProperty("path.separator") + servletContext.getServletContext().getRealPath("/WEB-INF/classes/"));
		System.setProperty("jasper.reports.compile.temp", servletContext.getServletContext().getRealPath("/letters/"));
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
