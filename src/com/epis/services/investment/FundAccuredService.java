package com.epis.services.investment;

import java.util.List;

import com.epis.bean.investment.FundAccuredBean;
import com.epis.common.exception.EPISException;
import com.epis.dao.investment.FundAccuredDAO;

public class FundAccuredService {
	
	private FundAccuredService() {

	}

	private static final FundAccuredService service = new FundAccuredService();

	public static FundAccuredService getInstance() {
		return service;
	}

	public List search(FundAccuredBean bean) throws EPISException {
		return FundAccuredDAO.getInstance().search(bean);
	}

	public void add(FundAccuredBean bean) throws EPISException {
		FundAccuredDAO.getInstance().add(bean);
	}

	public void delete(FundAccuredBean bean) throws EPISException {
		FundAccuredDAO.getInstance().delete(bean);
	}
}
