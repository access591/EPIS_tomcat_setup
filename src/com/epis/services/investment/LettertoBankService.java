package com.epis.services.investment;

import java.util.List;

import com.epis.bean.investment.LetterToBankBean;
import com.epis.dao.investment.LetterToBankDao;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;

public class LettertoBankService {
	private LettertoBankService()
	{
		
	}
	private static final LettertoBankService letterservice=new LettertoBankService();
	public static LettertoBankService getInstance()
	{
		return letterservice;
	}
	public List searchLettertoBank(LetterToBankBean bean)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().searchLettertoBank(bean);
	}
	public String saveLettertoBank(LetterToBankBean bean)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().saveLettertoBank(bean);
	}
	public String getaaiaccountNo(String refNo)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().getaaiaccountNo(refNo);
	}
	public String getSecurityName(String refNo)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().getSecurityName(refNo);
	}
	public String getBankAddress(String accountNo)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().getBankAddress(accountNo);
	}
	public String getBankAccountType(String accountNo)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().getBankAccountType(accountNo);
	}
	public LetterToBankBean findLetterToBank(String letterNo)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().findLetterToBank(letterNo);
	}
	public String editletterbank(LetterToBankBean bean)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().editletterbank(bean);
	}
	public void deleteLettertoBank(String letterNo)throws EPISException,ServiceNotAvailableException
	{
		LetterToBankDao.getInstance().deleteLettertoBank(letterNo);
	}
	public List getProposal(String mode)throws EPISException,ServiceNotAvailableException
	{
		return LetterToBankDao.getInstance().getProposal(mode);
	}
}
