/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.ScheduleTypeInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbook.ScheduleTypeDAO;

/**
 * Class ScheduleTypeService is a Business Delegate to reduce coupling
 * between presentation-tier clients and business services. This hides the
 * underlying implementation details of the business service.
 * 
 * @version 1.00 29 Oct 2010
 * @author Jaya Sree
 */
public class ScheduleTypeService {
	
	/*
	 * This private constructor is used for restricting the instantiation of a
	 * class to one object. By using Singleton Design pattern.
	 */
	private ScheduleTypeService() {
	}

	private static final ScheduleTypeService service = new ScheduleTypeService();

	public static ScheduleTypeService getInstance() {
		return service;
	}

	// This method is used to retrieve List of Records according to the given
	// search criteria .
	public List search(ScheduleTypeInfo info) throws EPISException {
		return ScheduleTypeDAO.getInstance().search(info);
	}

	public boolean exists(ScheduleTypeInfo info) throws EPISException {
		return ScheduleTypeDAO.getInstance().exists(info);
	}

	public void add(ScheduleTypeInfo info) throws EPISException {
		ScheduleTypeDAO.getInstance().add(info);
	}

	public void update(ScheduleTypeInfo info) throws EPISException {
		ScheduleTypeDAO.getInstance().update(info);
	}

	public ScheduleTypeInfo getSchedule(ScheduleTypeInfo info) throws EPISException {
		return ScheduleTypeDAO.getInstance().getSchedule(info);
	}

	public void delete(ScheduleTypeInfo info) throws EPISException {
		ScheduleTypeDAO.getInstance().delete(info);
	}

	public List getAccountingCodeTypes() throws EPISException {
		return ScheduleTypeDAO.getInstance().getAccountingCodeTypes();
	}
}
