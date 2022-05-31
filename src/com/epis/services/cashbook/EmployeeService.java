package com.epis.services.cashbook;

import java.util.List;

import com.epis.utilities.Log;
import com.epis.dao.cashbook.EmployeeDAO;

import com.epis.bean.rpfc.EmployeePersonalInfo;

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
