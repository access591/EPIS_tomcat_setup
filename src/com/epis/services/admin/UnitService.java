package com.epis.services.admin;

import java.util.List;
import com.epis.bean.admin.UnitBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.admin.UnitDAO;

public class UnitService {

	private UnitService() {
	}

	private static final UnitService service = new UnitService();

	public static UnitService getInstance() {
		return service;
	}
	
	public List getUnitList() throws Exception {
		return UnitDAO.getInstance().getUnitList();
	}
	public List searchUnit(String unitCode,String unitName,String option,String region) throws ServiceNotAvailableException, EPISException{
		return UnitDAO.getInstance().searchUnit(unitCode,unitName,option,region);
	}
	public void saveUnit(UnitBean ubean) throws ServiceNotAvailableException, EPISException{
		UnitDAO.getInstance().saveUnit(ubean);
	}
	public void editUnit(UnitBean ubean) throws ServiceNotAvailableException, EPISException{
		UnitDAO.getInstance().editUnit(ubean);
	}
	public UnitBean findUnit(String unitCode) throws ServiceNotAvailableException, EPISException {
		UnitBean ubean =new UnitBean();
	  ubean = UnitDAO.getInstance().findUnit(unitCode);
	return ubean;
	}
	public void deleteUnit(String unitcds) throws ServiceNotAvailableException, EPISException {
		UnitDAO.getInstance().deleteUnit(unitcds);
	}
	public String getUnits(String unitCode,String unitName,String option,String region) throws Exception {
		List unitList = searchUnit(unitCode,unitName,option,region);
		StringBuffer sb = new StringBuffer("<ServiceTypes>");
		int len = unitList.size();
		for(int i=0;i<len;i++) {
			UnitBean unit = (UnitBean) unitList.get(i);
			sb.append("<ServiceType><unitCode>").append(unit.getUnitCode())
					.append("</unitCode>");
			sb.append("<unitName>").append(unit.getUnitName())
					.append("</unitName></ServiceType>");
		}
		sb.append("</ServiceTypes>");
		return sb.toString();
	}
	
}
