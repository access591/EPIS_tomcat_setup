package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epis.utilities.Log;
import com.epis.utilities.DBUtility;

import com.epis.bean.rpfc.EmployeePersonalInfo;
 
public class EmployeeDAO {

	Log log = new Log(EmployeeDAO.class);
	
	public List getEmployeeList(EmployeePersonalInfo info) throws Exception {
		log.info("EmployeeDAO : getEmployeeList : Entering method");
		
		List empInfo = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
        
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(selectQuery);
			pst.setString(1,info.getRegion()+"%");
			pst.setString(2,"%"+info.getEmployeeName()+"%");
			pst.setString(3,info.getPfID());
            rs = pst.executeQuery();
            
			while (rs.next()) {
				info = new EmployeePersonalInfo();
				if(rs.getString("EMPLOYEENAME")!= null){
				info.setEmployeeName(rs.getString("EMPLOYEENAME"));
				}
				if(rs.getString("PENSIONNO")!= null){
				info.setPensionNo(rs.getString("PENSIONNO"));
				}
				if(rs.getString("DESEGNATION")!= null){
				info.setDesignation(rs.getString("DESEGNATION"));
				}
				if(rs.getString("EMPLOYEENO")!= null){
				info.setEmployeeNumber(rs.getString("EMPLOYEENO"));
				}
				if(rs.getString("region")!= null){
					info.setRegion(rs.getString("region"));
					}
				if(rs.getString("cpfacno")!= null){
					info.setCpfAccno(rs.getString("cpfacno"));
					}
				empInfo.add(info);
			}            
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("EmployeeDAO : getEmployeeList : leaving method");
		return empInfo;
	}
	
	public void updateEmpDesig(String designation, String empCode) throws Exception {
		log.info("EmployeeDAO : updateEmpDesig : Entering method");
		
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(updateDesigQuery);
			pst.setString(1,designation);
			pst.setString(2,empCode);
            pst.executeUpdate();
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("EmployeeDAO : updateEmpDesig : leaving method");
	}
	
	String selectQuery = "select * from EMPLOYEE_PERSONAL_INFO where Upper(trim(REGION)) like upper(trim(?)) and Upper(trim(EMPLOYEENAME)) like upper(trim(?)) and Upper(trim(PENSIONNO)) like upper(trim(?)) ";
	
	String updateDesigQuery = "update EMPLOYEE_PERSONAL_INFO set DESEGNATION = ? where upper(trim(PENSIONNO)) =  upper(trim(?))";
	
}
