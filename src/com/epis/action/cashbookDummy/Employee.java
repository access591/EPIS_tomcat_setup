package com.epis.action.cashbookDummy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.service.cashbookDummy.EmployeeService;
import com.epis.utilities.Log;

public class Employee extends HttpServlet{
	Log log = new Log(Employee.class);
	private static final long serialVersionUID = 1L;
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		EmployeeService service = new EmployeeService();
		log.info("Employee : service : Entering Method");
		String dispatch = null;
		String redirect = null;
		if ("getEmployeeList".equals(request.getParameter("method"))) {
			EmployeePersonalInfo info = new EmployeePersonalInfo();
			info.setRegion(request.getParameter("region")==null?"":request.getParameter("region"));
			info.setEmployeeName(request.getParameter("empName"));
			info.setPfID(request.getParameter("pfid"));			
			List empList = null;
			try {
				empList = service.getEmployeeList(info);
				request.setAttribute("EmpList", empList);
			} catch (Exception e) {
				log.printStackTrace(e);
			}
			if("ajax".equals(request.getParameter("type"))){
				StringBuffer sb = new StringBuffer("<ServiceTypes>");
				int listSize = empList.size();
				for(int cnt=0;cnt<listSize;cnt++){
					info = (EmployeePersonalInfo)empList.get(cnt);
					sb.append("<ServiceType><eName>").append(info.getEmployeeName()).append("</eName>");
					sb.append("<edesignation>").append(info.getDesignation()).append("</edesignation>");
					sb.append("<epfid>").append(info.getPensionNo()).append("</epfid></ServiceType>");
				}
				sb.append("</ServiceTypes>");
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();
				out.print(sb);
				System.out.print(sb);
			}else{
				dispatch = "./jsp/cashbook/voucher/EmployeeInfo.jsp";
			}
		}
		log.info("Employee : service : Leaving Method");
		if(redirect !=null){
			response.sendRedirect(redirect);
		}else if(dispatch !=null){
			RequestDispatcher rd = request.getRequestDispatcher(dispatch);
			rd.forward(request,response);
		}
	}
}
