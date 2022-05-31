/*
 * Created on Jun 27, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.aims.info.configuration;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.aims.dao.AccountSetupDAO;
import com.aims.dao.RegionDAO;

/**
 * @author rajanin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StationLoginInfo implements ServletContextListener {
      public StationLoginInfo(){
      	
      }
      private String regioncd;
      private String regionname;
      private String stationcd;
      private String stationname;

	
	public String getRegioncd() {
		return regioncd;
	}
	public void setRegioncd(String regioncd) {
		this.regioncd = regioncd;
	}
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		ServletContext context  = event.getServletContext();
		RegionDAO  region = new RegionDAO(); 
		AccountSetupDAO fyear = new AccountSetupDAO();
		List regionlist = region.selectRegionCd();
		List fyearlist = fyear.selectFinYearCd();
		context.setAttribute("regionlist",regionlist);
		context.setAttribute("fyearlist",fyearlist);
	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
