package com.epis.services.admin;


import java.util.List;
import com.epis.bean.admin.RegionBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.admin.RegionDAO;

public class RegionService {
	private RegionService(){
		
	}
   private static final RegionService regionService= new RegionService();
   public static RegionService getInstance(){
	return regionService;   
   }
   public List searchRegion(String regionName,String aaiCategory) throws ServiceNotAvailableException, EPISException{
		return RegionDAO.getInstance().searchRegion(regionName,aaiCategory);
	}
	public void saveRegion(RegionBean rbean) throws ServiceNotAvailableException, EPISException{
		RegionDAO.getInstance().saveRegion(rbean);
	}
	public void editRegion(RegionBean rbean) throws ServiceNotAvailableException, EPISException{
		RegionDAO.getInstance().editRegion(rbean);
	}
	public RegionBean findRegion(String regioncd) throws ServiceNotAvailableException, EPISException {
		RegionBean rbean =new RegionBean();
	  rbean = RegionDAO.getInstance().findRegion(regioncd);
	return rbean;
	}
	public void deleteRegion(String regioncds) throws ServiceNotAvailableException, EPISException {
		RegionDAO.getInstance().deleteRegion(regioncds);
	}
	public List getRegionList() throws Exception{
		return RegionDAO.getInstance().getRegionList();
	}
}
