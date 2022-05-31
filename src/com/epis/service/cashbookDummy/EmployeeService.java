package com.epis.service.cashbookDummy;

import java.util.List;

import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.dao.cashbookDummy.EmployeeDAO;
import com.epis.utilities.Log;

public class EmployeeService {

	Log log = new Log(BankOpenBalService.class);
	EmployeeDAO dao = new EmployeeDAO();
	
	public List getEmployeeList(EmployeePersonalInfo info) throws Exception {
		log.info("EmployeeService : getEmployeeList : Entering Method");
		List empList = dao.getEmployeeList(info);		
		log.info("EmployeeService : getEmployeeList : Leaving Method");
		return empList;	
	}

	public void updateEmpDesig(String designation, String empCode) throws Exception {
		log.info("EmployeeService : updateEmpDesig : Entering Method");
		dao.updateEmpDesig(designation,empCode);		
		log.info("EmployeeService : updateEmpDesig : Leaving Method");
	}
}
