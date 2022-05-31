package com.epis.action.advances;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.DispatchAction;

import com.epis.bean.RequestBean;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.dao.CommonDAO;
import com.epis.info.login.LoginInfo;
import com.epis.utilities.CommonUtil;

public class CommonAction extends DispatchAction{
	CommonUtil commonUtil=new CommonUtil();
	CommonDAO commonDAO=new CommonDAO();
	protected String userid="",userIPaddress="";
	protected void loadPrimarilyInfo(HttpServletRequest request,String formName){
		HttpSession httpsession=request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) httpsession.getAttribute("user");
		userid=logInfo.getUserName();
		userIPaddress=request.getRemoteAddr();
	}
	protected void getRegionByProfile(HttpServletRequest request){
		HashMap regionHashmap = new HashMap();
		RequestBean requestBean=new RequestBean(request);
		HttpSession httpsession=request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		String userRegion="",reg="",userStation="";		
		String employeeName="",pensionNo="";
		EmployeePersonalInfo personalData=new EmployeePersonalInfo();
		if(!(requestBean.getLoginProfile().equals("M") || requestBean.getLoginProfile().equals("U"))){
			reg=commonUtil.getAirportsByProfile(requestBean.getLoginProfile(),requestBean.getLoginUnitCode(),requestBean.getLoginRegion());
			if(!reg.equals("")){
				String[] regArr=reg.split(",");
				userStation=regArr[0];
				userRegion=regArr[1];				
			}
			log.info("CommonAction::getRegionByProfile========Profile"+requestBean.getLoginProfile()+"userStation"+userStation+"userRegion"+userRegion);
		   if(userRegion.equals("-") && userStation.equals("-")){
			   regionHashmap=commonUtil.getRegion();
		   }else{
			   regionHashmap.put(new Integer(1),userRegion);
		   }
		}else{
			 logInfo = (LoginInfo) httpsession.getAttribute("user");
			 log.info("CommonAction::Pension No"+logInfo.getPensionNo()+"User Type"+logInfo.getUserType()+"Region"+logInfo.getRegion());
			 personalData=commonDAO.getEmployeePersonalInfo("NO-SELECT","","","",logInfo.getPensionNo());
			
			 employeeName=personalData.getEmployeeName();
			 pensionNo=personalData.getPensionNo();
			 regionHashmap.put(new Integer(1),personalData.getRegion());
		}
		request.setAttribute("regionHashmap", regionHashmap);
	}
}
