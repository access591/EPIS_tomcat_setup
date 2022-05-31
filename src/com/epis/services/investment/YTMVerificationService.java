/**
 * 
 */

/**
 * @author anilkumark
 *
 */
package com.epis.services.investment;

import java.util.List;
import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.dao.investment.YTMVerificationDao;

public class YTMVerificationService {
	private YTMVerificationService() {

	}

	private static final YTMVerificationService ytmService = new YTMVerificationService();

	public static YTMVerificationService getInstance() {
		return ytmService;
	}

	public List getLetterNo() throws Exception {
		return YTMVerificationDao.getInstance().getLetterNo();
	}

	public List getLetterDetails(String letterNo) throws Exception {
		return YTMVerificationDao.getInstance().getLetterDetails(letterNo);
	}

	public List searchYTM(String letterno, String status) throws Exception {
		return YTMVerificationDao.getInstance().searchYTM(letterno, status);
	}

	public void editQuotation(QuotationBean qbean) throws Exception {
		YTMVerificationDao.getInstance().editQuotation(qbean);
	}

	public void updateYTMFlag(QuotationBean qbean) throws EPISException {
		YTMVerificationDao.getInstance().updateYTMFlag(qbean);
	}

}
