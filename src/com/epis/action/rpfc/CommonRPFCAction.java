package com.epis.action.rpfc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.DispatchAction;

import com.epis.bean.RequestBean;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.dao.CommonDAO;
import com.epis.info.login.LoginInfo;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;

public class CommonRPFCAction extends DispatchAction{
	CommonUtil commonUtil=new CommonUtil();
	CommonDAO commonDAO=new CommonDAO();
	protected void loadPrimarilyInfo(HttpServletRequest request,String formName){
		String userStation="",userRegion="",reg="";
		String[] year = {"1995-96","1996-97","1997-98","1998-99","1999-00","2000-01","2001-02","2002-03","2003-04","2004-05","2005-06","2006-07","2007-08","2008-09","2009-10","2010-11"};
		String[] rpfcyear = {"1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010"};		
        String[] userYears = {"2008-09","2009-10","2010-11"};		
       
		int pereachpage=0;
		EmployeePersonalInfo personalData=new EmployeePersonalInfo();
		HashMap regionHashmap = new HashMap();
		RequestBean requestBean=new RequestBean(request);
		HttpSession httpsession=request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		String employeeName="",pensionNo="";
		ArrayList pfidList=new ArrayList();
		if(!(requestBean.getLoginProfile().equals("M") || requestBean.getLoginProfile().equals("U"))){
			reg=commonUtil.getAirportsByProfile(requestBean.getLoginProfile(),requestBean.getLoginUnitCode(),requestBean.getLoginRegion());
			if(!reg.equals("")){
				String[] regArr=reg.split(",");
				userStation=regArr[0];
				userRegion=regArr[1];				
			}
			log.info("CommonRPFCAction::loadPrimarilyInfo========Profile"+requestBean.getLoginProfile()+"userStation"+userStation+"userRegion"+userRegion);
		   if(userRegion.equals("-") && userStation.equals("-")){
			   regionHashmap=commonUtil.getRegion();
		   }else{
			   regionHashmap.put(new Integer(1),userRegion);
		   }
		}else{
			 logInfo = (LoginInfo) httpsession.getAttribute("user");
			 log.info("CommonRPFCAction::Pension No"+logInfo.getPensionNo()+"User Type"+logInfo.getUserType()+"Region"+logInfo.getRegion());
			 personalData=commonDAO.getEmployeePersonalInfo("NO-SELECT","","","",logInfo.getPensionNo());
			
			 employeeName=personalData.getEmployeeName();
			 pensionNo=personalData.getPensionNo();
			 regionHashmap.put(new Integer(1),personalData.getRegion());
		}
	
		request.setAttribute("regionHashmap", regionHashmap);
		request.setAttribute("empName", employeeName);
		request.setAttribute("empPFID", pensionNo);
		
		//loading default PFIDS List
		
		ResourceBundle bundle = ResourceBundle.getBundle("com.epis.resource.ApplicationResources");
    	int totalSize=0;
    	if(bundle.getString("common.pension.pagesize")!=null){
    	 	totalSize=Integer.parseInt(bundle.getString("common.pension.pagesize"));
    	}else{
    		totalSize=100;
    	}
    	pereachpage = commonDAO.getPFIDsNaviagtionListSize(totalSize);
    	log.info("pereachpage=========="+pereachpage);
    	 int startIndex=1,endIndex=totalSize;
        for (int i = 0; i < pereachpage; i++) {
            String name = "";
            if(i!=0){
            	startIndex=startIndex+totalSize;
            	endIndex=endIndex+totalSize;
            }
        	if(i==pereachpage-1){
        		 name=Integer.toString(startIndex)+" - END";
        	}else{
        		name=Integer.toString(startIndex)+" - "+Integer.toString(endIndex);	
        	}
        	pfidList.add(name);
        }
        log.info("Log In Profile"+requestBean.getLoginProfile());
		if(requestBean.getLoginProfile().equals("U") || requestBean.getLoginProfile().equals("M")|| requestBean.getLoginProfile().equals("R")){
			if((formName.equals("loadpcreportInput"))||formName.equals("loadepf3report")){
				
				request.setAttribute("year", rpfcyear);
			}else{
				request.setAttribute("year", userYears);
			}
			
		}else{
			if((formName.equals("loadform3inputparams"))|| (formName.equals("loadForm6Cmp")) ||(formName.equals("loadform8Inputparams")||formName.equals("loadepf3report"))){
				request.setAttribute("year", rpfcyear);
			}else if((formName.equals("loadpcsummary") || (formName.equals("loadtransferinoutparams")))){
				request.setAttribute("year", userYears);
			}else{
				request.setAttribute("year", year);
			}
			 
			 
		}
        request.setAttribute("pfidList", pfidList);
	}
	protected String getIPAddress(HttpServletRequest request){
		String userIPAddress="";
		userIPAddress = request.getRemoteAddr();
	    return userIPAddress;
	}
	protected String getFromToDates(String fromYear,String toYear,String fromMonth,String toMonth) throws InvalidDataException{
		String fromDate="",toDate="";
		StringBuffer buffer=new StringBuffer();
		
		log.info("fromYear"+fromYear+"toYear"+toYear+"fromMonth"+fromMonth);
		
			if(!fromYear.equals("NO-SELECT")){
				
				if(!fromMonth.equals("NO-SELECT") && !toMonth.equals("NO-SELECT")){
					fromDate="01-"+fromMonth+"-"+fromYear;
					if(Integer.parseInt(toMonth)>=1 && Integer.parseInt(toMonth)<=3){
						fromDate="01-"+toMonth+"-"+toYear;
						toDate="01-"+toMonth+"-"+toYear;
					}else{
						toDate="01-"+toMonth+"-"+fromYear;
					}
					
				}else if(fromMonth.equals("NO-SELECT") && !toMonth.equals("NO-SELECT")){
					fromDate="01-04-"+fromYear;
					if(Integer.parseInt(toMonth)>=1 && Integer.parseInt(toMonth)<=3){
						fromDate="01-"+toMonth+"-"+toYear;
						toDate="01-"+toMonth+"-"+toYear;
					}else{
						toDate="01-"+toMonth+"-"+fromYear;
					}
					
				}else if(!fromMonth.equals("NO-SELECT") && toMonth.equals("NO-SELECT")){
					fromDate="01-"+fromMonth+"-"+fromYear;
					if(Integer.parseInt(toMonth)>=1 && Integer.parseInt(toMonth)<=3){
						toDate="01-"+toMonth+"-"+toYear;
					}else{
						toDate="01-03-"+toYear;
					}
					
				}else if(fromMonth.equals("NO-SELECT") && toMonth.equals("NO-SELECT")){
					fromDate="01-04-"+fromYear;
					toDate="01-03-"+toYear;
				}
				
			}else{
				fromDate="01-04-1995";
				toDate="01-03-"+commonUtil.getCurrentDate("yyyy");
			}
		buffer.append(commonUtil.converDBToAppFormat(fromDate,"dd-MM-yyyy","dd-MMM-yyyy"));
		buffer.append(",");
		buffer.append(commonUtil.converDBToAppFormat(toDate,"dd-MM-yyyy","dd-MMM-yyyy"));
		return buffer.toString();
	}
	public String getFromToDatesForForm3(String fromYear,String toYear,String fromMonth,String toMonth) throws InvalidDataException{
		String fromDate="",toDate="";
		StringBuffer buffer=new StringBuffer();
		
		log.info("fromYear"+fromYear+"toYear"+toYear+"fromMonth"+fromMonth +"toMonth "+toMonth);
		    
			if(!fromYear.equals("NO-SELECT")){
				 if(!fromMonth.equals("NO-SELECT") && !toMonth.equals("NO-SELECT")){
					fromDate="01-"+fromMonth.trim()+"-"+fromYear;
					toDate="01-"+toMonth.trim()+"-"+toYear;
					
				}/*else if(fromMonth.equals("NO-SELECT") && toMonth.equals("NO-SELECT")){
					fromDate="01-04-"+fromYear;
					if(Integer.parseInt(toMonth)>=1 && Integer.parseInt(toMonth)<=3){
						fromDate="01-"+fromMonth+"-"+fromYear;
						toDate="01-"+toMonth+"-"+toYear;
					}else{
						toDate="01-"+toMonth+"-"+fromYear;
					}
					
				}*/
			}
				
				
			
		buffer.append(commonUtil.converDBToAppFormat(fromDate,"dd-MM-yyyy","dd-MMM-yyyy"));
		buffer.append(",");
		buffer.append(commonUtil.converDBToAppFormat(toDate,"dd-MM-yyyy","dd-MMM-yyyy"));
		return buffer.toString();
	}

	
}
