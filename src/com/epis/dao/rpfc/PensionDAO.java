/**
  * File       : PensionDAO.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.dao.rpfc;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.epis.bean.rpfc.DatabaseBean;
import com.epis.bean.rpfc.EmolumentslogBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeeValidateInfo;
import com.epis.bean.rpfc.FinacialDataBean;
import com.epis.bean.rpfc.PensionBean;
import com.epis.bean.rpfc.UserBean;
import com.epis.common.exception.EPISException;
import com.epis.utilities.ApplicationActionException;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.utilities.DateValidation;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class PensionDAO {
	Log log = new Log(PensionDAO.class);
	DBUtility commonDB = new DBUtility();
	CommonUtil commonUtil = new CommonUtil();
    CommonDAO commonDAO=new CommonDAO();
    public int totalData() throws Exception {
    	log.info("PensionDAO :totalData() entering method");
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	String selectSQL = "select count(*) as count from employee_pension";
    	int totalRecords = 0;
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(selectSQL);
    		if (rs.next()) {
    			totalRecords = Integer.parseInt(rs.getString("count"));
    		}
    	} catch (SQLException e) {
    		
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		st.close();
    		rs.close();
    		con.close();
    	}
    	log.info("PensionDAO :totalData() leaving method");
    	return totalRecords;
    }
    
    public int totalEmployeeData(String flag) throws InvalidDataException {
    	log.info("PensionDAO :totalEmployeeData() entering method");
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	String selectSQL = "";
    	if (flag.equals("employee_info")) {
    		selectSQL = "select count(*) as count from employee_info";
    	} else {
    		selectSQL = "select count(*) as count from employee_info_invalidData";
    	}
    	int totalRecords = 0;
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(selectSQL);
    		if (rs.next()) {
    			totalRecords = Integer.parseInt(rs.getString("count"));
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		try{
    			st.close();
    			rs.close();
    			con.close();
    		}catch(Exception e){
    			
    		}
    	}
    	log.info("PensionDAO :totalEmployeeData() leaving method");
    	return totalRecords;
    }
    
    public int totalData(DatabaseBean dbBean) throws Exception {
    	log.info("PensionDAO :totalData() entering method");
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	String selectSQL = this.buildQuery1(dbBean);
    	int totalRecords = 0;
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(selectSQL);
    		while (rs.next()) {
    			totalRecords++;
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		st.close();
    		rs.close();
    		con.close();
    	}
    	log.info("PensionDAO :totalData() leaving method");
    	return totalRecords;
    }
    
    
    private String sTokenFormat(StringBuffer stringBuffer) {
    	StringBuffer whereStr = new StringBuffer();
    	StringTokenizer st = new StringTokenizer(stringBuffer.toString());
    	int count = 0;
    	int stCount = st.countTokens();
    	// && && count<=st.countTokens()-1st.countTokens()-1
    	while (st.hasMoreElements()) {
    		count++;
    		if (count == stCount)
    			break;
    		whereStr.append(st.nextElement());
    		whereStr.append(" ");
    	}
    	return whereStr.toString();
    }
    public void readXLSPersonalFhnameGender(String xlsData) {
    	log.info("PensionDAO :readXLSPersonalFhnameGender() entering method");
    	ArrayList xlsDataList = new ArrayList();
    	xlsDataList = commonUtil.getTheList(xlsData, "***");
    	Connection conn = null;
    	EmpMasterBean bean = null;
    	String tempInfo[] = null;
    	String tempData = "";
    	FileWriter fw = null;
    	char[] delimiters = { '"', ',', '\'', '*', ',' };
    	int count=0;
    	for (int i = 1; i < xlsDataList.size(); i++) {
    		bean = new EmpMasterBean();
    		tempData = xlsDataList.get(i).toString();
    		tempInfo = tempData.split("@");
    		try {
    			if (!tempInfo[1].equals("XXX")) {
    				bean.setCpfAcNo(tempInfo[1].trim());
    			} else {
    				bean.setCpfAcNo("");
    			}
    			
    			if (!tempInfo[2].equals("XXX")) {
    				bean.setEmpName(tempInfo[2].trim());
    			} else {
    				bean.setEmpName("");
    			}
    			
    			if (!tempInfo[0].equals("XXX")) {
    				bean.setEmpNumber(tempInfo[0].trim());
    			} else {
    				bean.setEmpNumber("");
    			}
    			if (!tempInfo[3].equals("XXX")) {
    				bean.setDesegnation(tempInfo[3].trim());
    			} else {
    				bean.setDesegnation("");
    			}
    			if (!tempInfo[4].equals("XXX")) {
    				bean.setDateofBirth(tempInfo[4].trim());
    			} else {
    				bean.setDateofBirth("");
    			}
    			
    			log.info("fhname " +bean.getEmpName().trim() +"cpfacno "+bean.getCpfAcNo().trim() +"empNo"+bean.getEmpNumber()+"deseg "  +bean.getDesegnation());
    			if(bean.getCpfAcNo()!=""){
    				
    				this.updatePersional(bean, "North Region");
    				count++;
    			}
    			log.info("count is "+count);
    		} catch (Exception e) {
    			log.printStackTrace(e);
    			try {
    				fw.write(tempData + "***");
    				fw.flush();
    				
    			} catch (FileNotFoundException fe) {
    				log.printStackTrace(fe);
    				// System.err.println("FileStreamsTest: " + fe);
    			} catch (IOException io) {
    				// System.err.println("FileStreamsTest: " + io);
    				log.printStackTrace(io);
    			}
    		}
    	}
    	log.info("PensionDAO :readXLSPersonalFhnameGender() leaving method");
    }
    
    public void readXLSPersonal(String xlsData) {
    	log.info("PensionDAO :readXLSPersonal() entering method");
    	ArrayList xlsDataList = new ArrayList();
    	ArrayList pensionList = new ArrayList();
    	xlsDataList = commonUtil.getTheList(xlsData, "***");
    	Connection conn = null;
    	EmpMasterBean bean = null;
    	String tempInfo[] = null;
    	String tempData = "";
    	FileWriter fw = null;
    	char[] delimiters = { '"', ',', '\'', '*', ',' };
    	String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt." };
    	String  xlsEmpName = "";
    	// cpfAccNum = this.getMaxValue();
    	// bean.setCpfAcNo(cpfAccNum);
    	log.debug("xlsData" + xlsData + "Size" + xlsDataList.size());
    	for (int i = 1; i < xlsDataList.size(); i++) {
    		bean = new EmpMasterBean();
    		tempData = xlsDataList.get(i).toString();
    		tempInfo = tempData.split("@");
    		try {
    			if (!tempInfo[0].equals("XXX")) {
    				bean.setEmpNumber(tempInfo[0]);
    			} else {
    				bean.setEmpNumber("");
    			}
    			if (!tempInfo[2].equals("XXX")) {
    				xlsEmpName = StringUtility.replaces(
    						tempInfo[2].trim().toCharArray(), delimiters, "")
    						.toString();
    				bean.setEmpName(xlsEmpName.trim());
    			} else {
    				bean.setEmpName("");
    			}
    			
    			String tempName = "";
    			for (int j = 0; j < quats.length; j++) {
    				if (bean.getEmpName().toLowerCase().indexOf(
    						quats[j].toLowerCase()) != -1) {
    					tempName = bean.getEmpName().toLowerCase().replaceAll(
    							quats[j].toLowerCase(), "").trim();
    					log.info("tempName " + tempName);
    					// tempName=empName.substring(index+1,empName.length());
    					bean.setEmpName(tempName);
    				}
    			}
    			
    			if (!tempInfo[1].equals("XXX")) {
    				bean.setCpfAcNo(tempInfo[1].trim());
    			} else {
    				bean.setCpfAcNo("");
    			}
    			if (!tempInfo[4].equals("XXX")) {
    				if (tempInfo[4].trim().indexOf("'") != -1) {
    					int index = tempInfo[4].trim().indexOf("'");
    					tempInfo[4] = tempInfo[4].trim().substring(index + 1,
    							tempInfo[4].length());
    				}
    				//	 bean.setDateofBirth(commonUtil.converDBToAppFormat(tempInfo[4].trim(),"dd/MM/yyyy","dd-MMM-yyyy"));
    				bean.setDateofBirth(tempInfo[4].trim());
    				
    			} else {
    				bean.setDateofBirth("");
    			}
    			if (!tempInfo[5].equals("XXX")) {
    				if (tempInfo[5].trim().indexOf("'") != -1) {
    					int index = tempInfo[5].trim().indexOf("'");
    					tempInfo[5] = tempInfo[5].trim().substring(index + 1,
    							tempInfo[5].length());
    				}
    				// bean.setDateofJoining(commonUtil.converDBToAppFormat(tempInfo[5].trim(),"dd/MM/yyyy","dd-MMM-yyyy"));
    				bean.setDateofJoining(tempInfo[5].toString().trim());
    				
    			} else {
    				bean.setDateofJoining("");
    			}
    			if (!tempInfo[6].equals("XXX")) {
    				bean.setFhName(tempInfo[6].trim());
    			} else {
    				bean.setFhName("");
    			}
    			
    			log.info("DateofBirth" + bean.getDateofBirth()
    					+ "DateofJoining" + bean.getDateofJoining());
    			
    			if (!tempInfo[7].equals("XXX")) {
    				bean.setSex(tempInfo[7].trim());
    			} else {
    				bean.setSex("");
    			}
    			if (!tempInfo[8].equals("XXX")) {
    				bean.setWetherOption(tempInfo[8].trim());
    			} else {
    				bean.setWetherOption("");
    			}
    			if (!tempInfo[9].equals("XXX")) {
    				bean.setDesegnation(tempInfo[9].trim());
    			} else {
    				bean.setDesegnation("");
    			}
    			if (!tempInfo[10].equals("XXX")) {
    				bean.setEmpLevel(tempInfo[10].trim());
    			} else {
    				bean.setEmpLevel("");
    			}
    			this.updatePersional(bean, "South Region");
    			
    			
    		} catch (Exception e) {
    			log.printStackTrace(e);
    			try {
    				fw.write(tempData + "***");
    				fw.flush();
    				
    			} catch (FileNotFoundException fe) {
    				log.printStackTrace(fe);
    				// System.err.println("FileStreamsTest: " + fe);
    			} catch (IOException io) {
    				// System.err.println("FileStreamsTest: " + io);
    				log.printStackTrace(io);
    			}
    		}
    	}
    	log.info("PensionDAO :readXLSPersonal() leaving method");
    }
    
    
    
    public void updatePersional(EmpMasterBean bean, String region)
    throws Exception {
    	Connection con = null;
    	Statement st = null;
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		String pensionNumber = "",employeename="";
    		
    		employeename=this.getEmployeeName(con,bean.getEmpNumber(),bean.getCpfAcNo(),region);
    		/*
    		 * if(!bean.getDateofBirth().equals("")){
    		 * employeename=this.getEmployeeName(bean.getEmpNumber(),bean.getCpfAcNo(),region); }
    		 */
    		/*String cpfaccno = bean.getCpfAcNo();
    		 if (!bean.getDateofBirth().equals("")) {
    		 pensionNumber = this.getPensionNumber(bean.getEmpName(), bean
    		 .getDateofBirth(), bean.getCpfAcNo(),true);
    		 }*/
    		
    		/*	String sql = "UPDATE employee_info SET employeename='"+bean.getEmpName()+"',DATEOFBIRTH='"
    		 + bean.getDateofBirth() + "',lastactive='"
    		 + commonUtil.getCurrentDate("dd-MMM-yyyy") + "',REMARKS='Updated The Seperation Reason',DATEOFSEPERATION_REASON='"+bean.getSeperationReason()+"'," +
    		 "otherreason='"+bean.getOtherReason()+"',FHNAME='"+bean.getFhName()+"',SEX='"+bean.getSex()+"' WHERE CPFACNO='"
    		 + bean.getCpfAcNo() + "'  AND REGION='" + region + "' ";*/
    		
    		//,DATEOFSEPERATION_DATE='"+bean.getDateofSeperationDate()+"'	
    		/*	String sql="update employee_info set DATEOFSEPERATION_REASON='"+bean.getSeperationReason()+"'," +
    		 "otherreason='"+bean.getOtherReason()+"',SEX='"+bean.getSex()+"',lastactive='" + commonUtil.getCurrentDate("dd-MMM-yyyy")+"'  WHERE CPFACNO='"+ bean.getCpfAcNo() + "'  AND REGION='" + region + "'";*/
    		
    		/*String sql = "UPDATE employee_info SET employeename='"+bean.getEmpName()+"',PENSIONNUMBER='"
    		 + pensionNumber + "',DATEOFBIRTH='"
    		 + bean.getDateofBirth() + "',DATEOFJOINING='"
    		 + bean.getDateofJoining() + "'" + ",lastactive='"
    		 + commonUtil.getCurrentDate("dd-MMM-yyyy") + "'," + "desegnation='"
    		 + bean.getDesegnation() + "',emp_level='"
    		 + bean.getEmpLevel() + "',REMARKS='updating dob,doj against cpfacno',WETHEROPTION='"+bean.getWetherOption()+"' WHERE CPFACNO='"
    		 + bean.getCpfAcNo() + "'  AND REGION='" + region + "' and dateofbirth is null AND dateofjoining is null ";*/
    		/*
    		 * String sql = "UPDATE employee_info SET
    		 * FHNAME='"+bean.getFhName()+"',"+
    		 * "SEX='"+bean.getSex()+"',DATEOFBIRTH='"+bean.getDateofBirth()+"',DATEOFJOINING='"+bean.getDateofJoining()+"'"+
    		 * ",lastactive='" + commonUtil.getCurrentDate("dd-MMM-yyyy")+
    		 * "',division='"+bean.getDivision()+"'"+ " WHERE
    		 * CPFACNO='"+bean.getCpfAcNo()+"'AND
    		 * EMPLOYEENO='"+bean.getEmpNumber()+"' AND REGION='"+region+"'";
    		 */
    		String sql="",sql2="";
    		log.info("DateofJoining " +bean.getDateofJoining().trim());
    		/*	if(!bean.getDateofJoining().trim().equals("1-Apr-1995")){
    		 log.info("in if");
    		 sql="update employee_info SET employeename='"+bean.getEmpName()+"',DATEOFBIRTH='"
    		 + bean.getDateofBirth() + "',DATEOFJOINING='"+bean.getDateofJoining()+"',desegnation='"
    		 + bean.getDesegnation() + "',PENSIONNUMBER='"
    		 + pensionNumber + "',fhname='"+bean.getFhName()+"',sex='"+bean.getSex()+"',lastactive='" + commonUtil.getCurrentDate("dd-MMM-yyyy")+"' where cpfacno='"+bean.getCpfAcNo()+"' and region='"+region+"' and dateofbirth is null ";
    		 }else{
    		 log.info("in else");
    		 sql="update employee_info SET employeename='"+bean.getEmpName()+"',DATEOFBIRTH='"
    		 + bean.getDateofBirth() + "',desegnation='"
    		 + bean.getDesegnation() + "',PENSIONNUMBER='"
    		 + pensionNumber + "',fhname='"+bean.getFhName()+"',sex='"+bean.getSex()+"',lastactive='" + commonUtil.getCurrentDate("dd-MMM-yyyy")+"' where cpfacno='"+bean.getCpfAcNo()+"' and region='"+region+"' and dateofbirth is null ";
    		 
    		 }*/
    		if(employeename.equals("")){
    			sql="update employee_info SET employeename='"+bean.getEmpName().trim()+"',desegnation='"
    			+ bean.getDesegnation().trim() + "', employeeno='"+bean.getEmpNumber().trim()+"',lastactive='" + commonUtil.getCurrentDate("dd-MMM-yyyy")+"',dateofbirth='"+bean.getDateofBirth()+"' where cpfacno='"+bean.getCpfAcNo()+"' and region='"+region+"'";
    			
    			sql2="update employee_pension_validate set master_empname='"+bean.getEmpName().trim()+"' where cpfaccno='"+bean.getCpfAcNo()+"' and region='"+region+"'";
    			st.executeUpdate(sql);
    			st.executeUpdate(sql2);
    		}
    		log.info("sql==============updatePersional=============" + sql);
    		
    	} catch (SQLException e) {
    		
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, null);
    	}
    }
    
    
    
    
    
    
    public String getMaxValue() throws InvalidDataException {
    	log.info("PensionDAO :getMaxValue Entering method");
    	int maxValue = 0;
    	String finalCPFacno = "", cpfString = "";
    	String cpfacno = "";
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	String[] maxData = null;
    	try {
    		con = commonDB.getConnection();
    		// log.info("con(getMaxValue) " + con);
    		st = con.createStatement();
    		String maxcpfacno = "select max(cpfacno) as cpfacno from employee_info where cpfacno like 'CPF%'";
    		rs = st.executeQuery(maxcpfacno);
    		if (rs.next()) {
    			if (rs.getString("cpfacno") != null) {
    				cpfacno = rs.getString("cpfacno");
    				cpfacno = commonUtil.validateNumber(cpfacno.toCharArray());
    				maxData = cpfacno.split(",");
    				log.info("cpfacno" + cpfacno);
    				maxValue = Integer.parseInt(maxData[0]);
    				maxValue = maxValue + 1;
    				if (maxData[1].equals("")) {
    					cpfString = "";
    				} else {
    					cpfString = maxData[1];
    				}
    				finalCPFacno = cpfString + maxValue;
    				
    			} else {
    				maxValue = 10000;
    				finalCPFacno = "CPF" + maxValue;
    			}
    		}
    		log.info("CPF ACCNO(getMaxValue())==" + finalCPFacno);
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		log.printStackTrace(e);
    		throw new InvalidDataException(e.getMessage());
    		// e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		log.printStackTrace(e);
    		throw new InvalidDataException(e.getMessage());
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	log.info("PensionDAO :getMaxValue Leaving method");
    	return finalCPFacno.trim();
    }
    
    public String empNameCheck(String employeeName, String airportCode,
    		String desegnation, String selectedColumn)
    throws InvalidDataException {
    	log.info("PensionDAO :empNameCheck() entering method ");
    	String foundEmpFlag = "";
    	Statement st = null;
    	Connection con = null;
    	ResultSet rs = null;
    	String query = "";
    	if (!selectedColumn.equals("")) {
    		query = "select CPFACNO from employee_info where  employeename='"
    			+ employeeName.trim() + "' and airportcode='"
    			+ airportCode.trim() + "' ";
    	} else {
    		query = "select CPFACNO from employee_info_invaliddata where  employeename='"
    			+ employeeName.trim()
    			+ "' and airportcode='"
    			+ airportCode.trim() + "' ";
    	}
    	log.info(query);
    	try {
    		con = commonDB.getConnection();
    		log.info("con(empNameCheck)" + con);
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		
    		if (rs.next()) {
    			if (rs.getString("CPFACNO") != null) {
    				foundEmpFlag = rs.getString("CPFACNO");
    			} else {
    				foundEmpFlag = "";
    			}
    		}
    	} catch (SQLException e) {
    		log.printStackTrace(e);
    		throw new InvalidDataException(e.getMessage());
    	} catch (Exception e) {
    		throw new InvalidDataException(e.getMessage());
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	log.info("PensionDAO :empNameCheck() leaving method");
    	return foundEmpFlag;
    }
    
    public ArrayList searchRecords(EmpMasterBean bean) {
    	log.info("PensionDAO :searchRecords() entering method");
    	ArrayList searchData = new ArrayList();
    	Connection conn = null;
    	ResultSet rs = null;
    	boolean flag = true;
    	String sqlQuery = this.buildQuery(bean, flag,"");
    	EmpMasterBean data = null;
    	try {
    		conn = commonDB.getConnection();
    		Statement st = conn.createStatement();
    		rs = st.executeQuery(sqlQuery);
    		while (rs.next()) {
    			data = new EmpMasterBean();
    			if (rs.getString("cpfacno") != null) {
    				data.setCpfAcNo(rs.getString("cpfacno"));
    			} else {
    				data.setCpfAcNo("");
    			}
    			if (rs.getString("airportcode") != null) {
    				data.setStation(rs.getString("airportcode"));
    			} else {
    				data.setStation("");
    			}
    			if (rs.getString("employeecode") != null) {
    				data.setEmpNumber(rs.getString("employeeno"));
    			} else {
    				data.setEmpNumber("");
    			}
    			if (rs.getString("employeename") != null) {
    				data.setEmpName(rs.getString("employeename"));
    			} else {
    				data.setEmpName("");
    			}
    			if (rs.getString("DESEGNATION") != null) {
    				data.setDesegnation(rs.getString("DESEGNATION"));
    			} else {
    				data.setDesegnation("");
    			}
    			searchData.add(data);
    		}
    	} catch (Exception e) {
    	}
    	log.info("PensionDAO :searchRecords() leaving method");
    	return searchData;
    }
    
    public String buildQuery(EmpMasterBean bean, boolean flag,String empNamechek) {
    	log.info("flag is " + flag);
    	log.info("PensionDao:buildQuery-- Entering Method");
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";
    	log.info("empname check "+bean.getEmpNameCheak());
    	log.info("region "+bean.getRegion());
    	if(bean.getRegion().equalsIgnoreCase("AllRegions")){
    		bean.setRegion("");
    	}
    	if(bean.getRegion().equals("")){
    		whereClause.append(" AND ");
    	}
    	if (flag) {
    		//	sqlQuery = "select * from employee_info where empflag='Y'";
    		sqlQuery = "select MIN(ROWID) from employee_info where cpfacno is not null ";
    		System.out.println("airportcode  " + bean.getStation() + "cpf acno"
    				+ bean.getCpfAcNo() + " empcode " + bean.getEmpNumber()
    				+ " emp name " + bean.getEmpName() + " desegnation "
    				+ bean.getDesegnation());
    	} else {
    		sqlQuery = "select MIN(ROWID) from employee_info_invalidData where empflag='Y'";
    	}
    	if (!bean.getCpfAcNo().equals("")) {
    		whereClause.append(" LOWER(cpfacno) ='"
    				+ bean.getCpfAcNo().toLowerCase() + "'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getStation().equals("")) {
    		whereClause.append(" airportcode='" + bean.getStation() + "'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getEmpName().equals("")) {
    		whereClause.append(" LOWER(employeename) like'%"
    				+ bean.getEmpName().toLowerCase() + "%'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getDesegnation().equals("")) {
    		whereClause.append(" desegnation='" + bean.getDesegnation() + "'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getEmpNumber().equals("")) {
    		whereClause.append(" EMPLOYEENO='" + bean.getEmpNumber() + "'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getRegion().equals("")) {
    		whereClause.append(" region='" + bean.getRegion() + "'");
    		whereClause.append(" AND ");
    	}
    	log.info("record verified"+bean.getRecordVerified());
    	if (!bean.getRecordVerified().equals("N")&&!bean.getRecordVerified().equals("")) {
    		log.info("inside if");  
    		whereClause.append(" empflag='N'");
    		whereClause.append(" AND ");
    	}else{
    		log.info("inside ELSE");  
    		whereClause.append(" empflag='Y'");
    		whereClause.append(" AND ");
    	}
    	query.append(sqlQuery);
    	if ((bean.getStation().equals(""))
    			&& (bean.getCpfAcNo().equals("")
    					&& (bean.getEmpName().equals("")) && (bean
    							.getDesegnation().equals("") && (bean.getCpfAcNo()
    									.equals("")&&(bean.getEmpNumber().equals("")) && (bean
    											.getRegion().equals("")))))) {
    		
    	} else {
    		log.info("inside else");
    		if (!prefixWhereClause.equals("")) {
    			query.append(" AND ");
    		} else if(!bean.getRegion().equals("")) {
    			query.append(" AND ");
    		}
    	}
    	
    	query.append(this.sTokenFormat(whereClause));
    	String groupBy = " group by cpfacno";
    	String orderBy = "ORDER BY cpfacno";
    	query.append(groupBy);
    	dynamicQuery = query.toString();
    	log.info("search dynamicQuery   " + dynamicQuery);
    	log.info("PensionDao:buildQuery Leaving Method");
    	return dynamicQuery;
    }
    
    public String buildQueryforEmptotal(EmpMasterBean bean, boolean flag,String empNameCheak) {
    	log.info("PensionDao:buildQueryforEmptotal-- Entering Method");
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";
    	log.info("region IN buildQueryforEmptotal "+bean.getRegion());
    	if(bean.getRegion().equalsIgnoreCase("AllRegions")){
    		bean.setRegion("");
    	}
    	if(bean.getRegion().equals("")){
    		whereClause.append(" AND ");
    	}
    	
    	if (flag) {
    		// sqlQuery = "select distinct(employeename) from employee_info
    		// where empflag='Y'";
    		//	 sqlQuery = "select * from employee_info where empflag='Y'";
    		sqlQuery = "select MIN(ROWID) from employee_info where cpfacno is not null ";
    		System.out.println("airportcode  " + bean.getStation() + "cpf acno"
    				+ bean.getCpfAcNo() + " empcode " + bean.getEmpNumber()
    				+ " emp name " + bean.getEmpName() + " desegnation "
    				+ bean.getDesegnation());
    	} else {
    		sqlQuery = "select MIN(ROWID) from employee_info_invalidData where empflag='Y' ";
    	}
    	
    	if (!bean.getCpfAcNo().equals("")) {
    		whereClause.append(" LOWER(cpfacno) ='"
    				+ bean.getCpfAcNo().toLowerCase() + "'");
    		whereClause.append(" AND ");
    	}
    	
    	if (!bean.getStation().equals("")) {
    		whereClause.append(" airportcode='" + bean.getStation() + "'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getEmpName().equals("")) {
    		whereClause.append(" LOWER(employeename) like'%"
    				+ bean.getEmpName().toLowerCase() + "%'");
    		whereClause.append(" AND ");
    	}
    	
    	if (!bean.getDesegnation().equals("")) {
    		whereClause.append(" desegnation='" + bean.getDesegnation() + "'");
    		whereClause.append(" AND ");
    		
    	}
    	if (!bean.getEmpNumber().equals("")) {
    		whereClause.append(" EMPLOYEENO='" + bean.getEmpNumber() + "'");
    		whereClause.append(" AND ");
    		
    	}
    	if (!bean.getRegion().equals("")) {
    		whereClause.append(" region='" + bean.getRegion() + "'");
    		whereClause.append(" AND ");
    		
    	}
    	
    	log.info("record verified"+bean.getRecordVerified());
    	if (!bean.getRecordVerified().equals("N")&&!bean.getRecordVerified().equals("")) {
    		log.info("inside if");  
    		whereClause.append(" empflag='N'");
    		whereClause.append(" AND ");
    	}else{
    		log.info("inside ELSE");  
    		whereClause.append(" empflag='Y'");
    		whereClause.append(" AND ");
    	}
    	query.append(sqlQuery);
    	if ((bean.getStation().equals(""))
    			&& (bean.getCpfAcNo().equals("")
    					&& (bean.getEmpName().equals("")) && (bean
    							.getDesegnation().equals("") && (bean.getCpfAcNo()
    									.equals("")
    									&& (bean.getEmpNumber().equals("")) && (bean
    											.getRegion().equals("")))))) {
    		log.info("inside ifff");
    	} else {
    		log.info("inside else");
    		
    		if (!prefixWhereClause.equals("")) {
    			query.append(" AND ");
    		}  else if(!bean.getRegion().equals("")) {
    			query.append(" AND ");
    		}
    	}
    	
    	query.append(this.sTokenFormat(whereClause));
    	// String orderBy = "ORDER BY employeename";
    	String groupBy = "group by cpfacno";
    	query.append(groupBy);
    	dynamicQuery = query.toString();
    	log.info("search dynamicQuery   " + dynamicQuery);
    	log.info("PensionDao:buildQueryforEmptotal Leaving Method");
    	return dynamicQuery;
    }
    
    public String searchRecordsbyDobandName(EmpMasterBean bean, String flag,String ajaxCall,String userType) {
    	log.info("flag is " + flag);
    	log.info("PensionDao:searchRecordsbyDobandName-- Entering Method");
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";
    	String contditon="";
    	if(userType.equals("User")){
    		contditon="and (round(months_between('01-Apr-2010',dateofbirth),3)/12 >58 OR DATEOFSEPERATION_REASON IN('Death','Termination'))";
    	}
    	if (flag=="true"&& ajaxCall.equals("ajaxCall")) {
    		// sqlQuery = "select * from employee_info where empflag='Y' and empserialnumber is not null ";   
    		sqlQuery = "select distinct(employeename) from employee_info where empflag='Y' and empserialnumber is null  and cpfacno is not null  and region is not null ";    
    	}
    	else if (flag=="true"&& !ajaxCall.equals("ajaxCall")) {
    		sqlQuery = "select * from employee_info where empflag='Y' and empserialnumber is null  and cpfacno is not null  and region is not null ";
    		
    	} else if(flag=="update") {
    		sqlQuery = "update employee_info set pensioncheck='' where empflag='Y'";
    	}
    	else{
    		sqlQuery = " select *  from   employee_info where EMPSERIALNUMBER in (select EMPSERIALNUMBER from employee_info group by EMPSERIALNUMBER having count(cpfacno)>=1 ) and empflag='Y' "+contditon+"   and EMPSERIALNUMBER is not null";	
    	}
    	if (!bean.getCpfAcNo().equals("")) {
    		whereClause.append(" LOWER(cpfacno) ='"
    				+ bean.getCpfAcNo().toLowerCase() + "'");
    		whereClause.append(" AND ");
    	}
    	
    	if (!bean.getDateofBirth().equals("")) {
    		whereClause.append(" dateofbirth='" + bean.getDateofBirth() + "'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getEmpName().equals("")) {
    		whereClause.append(" LOWER(employeename) like'%"
    				+ bean.getEmpName().toLowerCase() + "%'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getDateofJoining().equals("")) {
    		
    		whereClause.append(" dateofjoining='" + bean.getDateofJoining()
    				+ "'");
    		whereClause.append(" AND ");
    	}
    	
    	if (!bean.getEmpSerialNo().equals("")) {
    		bean.setEmpSerialNo(commonUtil.getSearchPFID(bean.getEmpSerialNo().toString().trim()));
    		whereClause.append(" EMPSERIALNUMBER='" + bean.getEmpSerialNo()
    				+ "'");
    		whereClause.append(" AND ");
    	}
    	if (!bean.getEmpNumber().equals("")) {
    		whereClause.append(" LOWER(employeeno) ='"
    				+ bean.getEmpNumber().toLowerCase() + "'");
    		whereClause.append(" AND ");
    	}
    	query.append(sqlQuery);
    	if ((bean.getDateofBirth().equals(""))
    			&& (bean.getCpfAcNo().equals("")
    					&& (bean.getEmpName().equals(""))
    					&& (bean.getDateofJoining().equals("")) && (bean
    							.getEmpSerialNo().equals("")))&& bean.getEmpNumber().equals("")) {
    	} else {
    		if (!prefixWhereClause.equals("")) {
    			query.append(" AND ");
    		} else {
    			query.append(" AND ");
    		}
    	}
    	
    	query.append(this.sTokenFormat(whereClause));
    	String orderBy="";
    	if (flag=="true"&& !ajaxCall.equals("ajaxCall")){
    		orderBy = " order by dateofbirth,employeename,region";
    	}else if(flag=="true"&& ajaxCall.equals("ajaxCall")){
    		orderBy = " order by employeename";
    	}
    	else
    		orderBy = " order by EMPSERIALNUMBER";
    	// String groupBy = "group by cpfacno";
    	query.append(orderBy);
    	dynamicQuery = query.toString();
    	log.info("search dynamicQuery   " + dynamicQuery);
    	log
    	.info("PensionDao:searchRecordsbyDobandName Leaving Method");
    	return dynamicQuery;
    }
    
    
    
    public ArrayList getEmployeeMappedList(String region,String empName,String transferType,String pfId) {
    	log.info("PensionDao:getEmployeeMappedList-- Entering Method");
    	String sqlQuery = "",prefixWhereClause = "";
    	PensionBean data = new PensionBean();
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ArrayList empinfo =null;
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	//  sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS REFPENSIONNUMBER,EPI.PENSIONNO AS PENSIONNO,EPI.REGION AS REGION,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO";
    	sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.DEPARTMENT AS DEPARTMENT,EPI.REFPENSIONNUMBER AS REFPENSIONNUMBER,EPI.PENSIONNO AS PENSIONNO,EPI.REGION AS REGION,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL ";
    	
    	log.info("transferType "+transferType);
    	if (!empName.equals("")) {
    		whereClause.append(" LOWER(EPI.employeename) like'%"
    				+ empName.toLowerCase() + "%'");
    		whereClause.append(" AND ");
    	}
    	if (!transferType.equals("")) {
    		whereClause.append(" LOWER(eti.TRANSFERFLAG) like'%"
    				+ transferType.toLowerCase() + "%'");
    		whereClause.append(" AND ");
    	}
    	
    	if (!region.equals("NO-SELECT") && !region.equals("") &&!region.equals("AllRegions")) {
    		whereClause.append(" LOWER(EPI.region)='"+region.toLowerCase()+ "'");
    		whereClause.append(" AND ");
    	}
    	
    	if (!pfId.equals("")) {
    		whereClause.append(" LOWER(EPI.pensionno)='"+ pfId.toLowerCase()+"'");
    		whereClause.append(" AND ");
    	}
    	
    	query.append(sqlQuery);
    	if (empName.equals("")&& transferType.equals("")&& region.equals("AllRegions")&&region.equals("")&& pfId.equals("")) {
    		
    	} else {
    		if (!prefixWhereClause.equals("")) {
    			query.append(" AND ");
    		} else {
    			query.append(" AND ");
    		}
    	}
    	
    	query.append(this.sTokenFormat(whereClause));
    	log.info("sql query "+query); 
    	// new code
    	
    	try {
    		con = commonDB.getConnection();
    		st=con.createStatement();
    		rs = st.executeQuery(query.toString());
    		empinfo = new ArrayList(); 
    		PersonalDAO pdao=new PersonalDAO();
    		while(rs.next()) {
    			data = new PensionBean();
    			if (rs.getString("dateofbirth") != null) {
    				data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    						.getDate("dateofbirth")));
    			} else {
    				data.setDateofBirth("");
    			}
    			if (rs.getString("cpfacno") != null) {
    				data.setCpfAcNo(rs.getString("cpfacno"));
    			} else {
    				data.setCpfAcNo("");
    			}
    			if (rs.getString("region") != null) {
    				data.setRegion(rs.getString("region"));
    			} else {
    				data.setRegion("");
    			}
    			if (rs.getString("employeename") != null) {
    				data.setEmployeeName(rs.getString("employeename"));
    			} else {
    				data.setEmployeeName("");
    			}
    			if (rs.getString("PENSIONNO") != null) {
    				data.setEmpSerialNumber(rs.getString("PENSIONNO"));
    			} else {
    				data.setEmpSerialNumber("");
    			}
    			if (rs.getString("REFPENSIONNUMBER") != null) {
    				data.setPensionnumber(rs.getString("REFPENSIONNUMBER"));
    			} else {
    				data.setPensionnumber("");
    			}
    			if (rs.getString("WETHEROPTION") != null) {
    				data.setPensionOption(rs.getString("WETHEROPTION"));
    			} else {
    				data.setPensionOption("");
    			}
    			if (rs.getString("DEPARTMENT") != null) {
    				data.setDepartment(rs.getString("DEPARTMENT").trim());
    			} else {
    				data.setDepartment("---");
    				
    			}
    			empinfo.add(data);   
    		}
    		
    	}catch(Exception e){
    		
    	}
    	return empinfo;
    }
    
    public ArrayList getEmployeeLookUpList(String region,String empName,String pfId) {
    	log.info("PensionDao:getEmployeeMappedList-- Entering Method");
    	String sqlQuery = "",prefixWhereClause = "";
    	PensionBean data = new PensionBean();
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ArrayList empinfo =null;
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	//  sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.REFPENSIONNUMBER AS REFPENSIONNUMBER,EPI.PENSIONNO AS PENSIONNO,EPI.REGION AS REGION,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO EPI,MV_EMPLOYEES_TRANSFER_INFO ETI WHERE EPI.PENSIONNO IS NOT NULL AND ETI.PENSIONNO=EPI.PENSIONNO";
    	sqlQuery = "SELECT DISTINCT NVL(EPI.CPFACNO,'NO-VAL') AS CPFACNO,EPI.DEPARTMENT AS DEPARTMENT,EPI.REFPENSIONNUMBER AS REFPENSIONNUMBER,EPI.PENSIONNO AS PENSIONNO,EPI.REGION AS REGION,EPI.MARITALSTATUS AS MARITALSTATUS,EPI.PENSIONNO AS EMPSERIALNUMBER,EPI.DATEOFJOINING AS DATEOFJOINING,EPI.EMPLOYEENO AS EMPLOYEENO,EPI.DATEOFBIRTH AS DATEOFBIRTH,EPI.EMPLOYEENAME AS EMPLOYEENAME,EPI.GENDER AS SEX,EPI.FHNAME AS FHNAME,EPI.DESEGNATION AS DESEGNATION,EPI.WETHEROPTION AS WETHEROPTION,round(months_between(NVL(EPI.DATEOFJOINING,'01-Apr-1995'),'01-Apr-1995'),3) ENTITLEDIFF FROM EMPLOYEE_PERSONAL_INFO EPI WHERE EPI.PENSIONNO IS NOT NULL ";
    	
    	
    	if (!empName.equals("")) {
    		whereClause.append(" LOWER(EPI.employeename) like'%"
    				+ empName.toLowerCase() + "%'");
    		whereClause.append(" AND ");
    	}
    	
    	
    	if (!region.equals("NO-SELECT") && !region.equals("") &&!region.equals("AllRegions")) {
    		whereClause.append(" LOWER(EPI.region)='"+region.toLowerCase()+ "'");
    		whereClause.append(" AND ");
    	}
    	
    	if (!pfId.equals("")) {
    		whereClause.append(" LOWER(EPI.pensionno)='"+ pfId.toLowerCase()+"'");
    		whereClause.append(" AND ");
    	}
    	
    	query.append(sqlQuery);
    	if (empName.equals("") && region.equals("AllRegions")&&region.equals("")&& pfId.equals("")) {
    		
    	} else {
    		if (!prefixWhereClause.equals("")) {
    			query.append(" AND ");
    		} else {
    			query.append(" AND ");
    		}
    	}
    	
    	query.append(this.sTokenFormat(whereClause));
    	log.info("sql query "+query); 
    	// new code
    	
    	try {
    		con = commonDB.getConnection();
    		st=con.createStatement();
    		rs = st.executeQuery(query.toString());
    		empinfo = new ArrayList(); 
    		
    		while(rs.next()) {
    			data = new PensionBean();
    			if (rs.getString("dateofbirth") != null) {
    				data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    						.getDate("dateofbirth")));
    			} else {
    				data.setDateofBirth("");
    			}
    			if (rs.getString("cpfacno") != null) {
    				data.setCpfAcNo(rs.getString("cpfacno"));
    			} else {
    				data.setCpfAcNo("");
    			}
    			if (rs.getString("region") != null) {
    				data.setRegion(rs.getString("region"));
    			} else {
    				data.setRegion("");
    			}
    			if (rs.getString("employeename") != null) {
    				data.setEmployeeName(rs.getString("employeename"));
    			} else {
    				data.setEmployeeName("");
    			}
    			if (rs.getString("PENSIONNO") != null) {
    				data.setEmpSerialNumber(rs.getString("PENSIONNO"));
    			} else {
    				data.setEmpSerialNumber("");
    			}
    			if (rs.getString("REFPENSIONNUMBER") != null) {
    				data.setPensionnumber(rs.getString("REFPENSIONNUMBER"));
    			} else {
    				data.setPensionnumber("");
    			}
    			if (rs.getString("WETHEROPTION") != null) {
    				data.setPensionOption(rs.getString("WETHEROPTION"));
    			} else {
    				data.setPensionOption("");
    			}
    			if (rs.getString("DEPARTMENT") != null) {
    				data.setDepartment(rs.getString("DEPARTMENT").trim());
    			} else {
    				data.setDepartment("---");
    				
    			}
    			empinfo.add(data);   
    		}
    		
    	}catch(Exception e){
    		log.printStackTrace(e);
    	}
    	return empinfo;
    }
    
    
    public boolean addPensionRecord(EmpMasterBean bean)
    throws InvalidDataException {
    	log.info("PensionDAO :addPensionRecord() entering method");
    	
    	boolean isaddPensionRecord = false;
    	Connection conn = null;
    	Statement st = null;
    	// PreparedStatement ps=null;
    	String srno = "", airportSerialNumber = "", empNumber = "", cpfAcNo = "";
    	String empName = "", desegnation = "", empLevel = "";
    	String cpf = "", sex = "", maritalStatus = "", fhName = "", permanentAddress = "", temporatyAddress = "";
    	String seperationReason = "", whetherOptionA = "";
    	String whetherOptionB = "", whetherOptionNO = "", form2Nomination = "";
    	String remarks = "", station = "";
    	String dateofBirth = "", dateofJoining = "", dateofSeperationDate = "", pensionNumber = "";
    	String fMemberName = "", fDateofBirth = "", frelation = "", wetherOption = "", familyrows = "", dateOfAnnuation = "", otherReason = "";
    	String division = "", department = "", emailId = "", gaddress = "", empNomineeSharable = "";
    	String nomineeName = "", nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", nomineeRows = "";
    	try {
    		if (bean.getStation() != null && (!bean.getStation().equals("XXX"))) {
    			station = bean.getStation();
    		} else {
    			station = "";
    		}
    		cpfAcNo = bean.getCpfAcNo().trim();
    		srno = bean.getSrno();
    		airportSerialNumber = bean.getAirportSerialNumber();
    		empNumber = bean.getEmpNumber();
    		empName = bean.getEmpName().toUpperCase();
    		desegnation = bean.getDesegnation();
    		empLevel = bean.getEmpLevel();
    		dateofBirth = bean.getDateofBirth();
    		
    		if (!dateofBirth.equals("")) {
    			pensionNumber = this.getPensionNumber(empName.toUpperCase(),
    					dateofBirth, cpfAcNo,true);
    		} else
    			pensionNumber = "";
    		dateofJoining = bean.getDateofJoining();
    		seperationReason = bean.getSeperationReason();
    		if (!bean.getDateofSeperationDate().equals("")) {
    			dateofSeperationDate = commonUtil.converDBToAppFormat(bean
    					.getDateofSeperationDate().trim(), "MM/dd/yyyy", "dd-MMM-yyyy");
    		}
    		//dateofSeperationDate = bean.getDateofSeperationDate();
    		log.info("dateofBirth" + dateofBirth + "dateofJoining"
    				+ dateofJoining + "dateofSeperationDate"
    				+ dateofSeperationDate);
    		whetherOptionA = bean.getWhetherOptionA();
    		whetherOptionB = bean.getWhetherOptionB();
    		whetherOptionNO = bean.getWhetherOptionNO();
    		form2Nomination = bean.getForm2Nomination();
    		remarks = bean.getRemarks();
    		empNomineeSharable = bean.getEmpNomineeSharable();
    		wetherOption = bean.getWetherOption();
    		sex = bean.getSex();
    		maritalStatus = bean.getMaritalStatus();
    		fhName = bean.getFhName();
    		permanentAddress = bean.getPermanentAddress();
    		temporatyAddress = bean.getTemporatyAddress();
    		if (!bean.getDateOfAnnuation().equals("")) {
    			dateOfAnnuation = commonUtil.converDBToAppFormat(bean
    					.getDateOfAnnuation().trim(), "MM/dd/yyyy",	"dd-MMM-yyyy");
    		}
    		otherReason = bean.getOtherReason();
    		station = bean.getStation();
    		division = bean.getDivision();
    		emailId = bean.getEmailId();
    		department = bean.getDepartment();
    		String region = bean.getRegion();
    		log.info("region is " + region);
    		// String foundCPFNO = this.empNameCheck(empName, station,
    		// desegnation, "CPFACNO");
    		conn = commonDB.getConnection();
    		// if (foundCPFNO.equals("")) {
    		st = conn.createStatement();
    		String sql = "insert into employee_info(cpfacno,pensionnumber,srno,airportSerialNumber,employeeNo,employeeName,"
    			+ "desegnation,emp_level,cpf,dateofbirth,dateofjoining,dateofseperation_reason,dateofseperation_date,"
    			+ "whether_option_a,whether_option_b,whether_option_no,WHETHER_FORM1_NOM_RECEIVED,remarks,airportcode,"
    			+ "sex,fhname,maritalStatus,permanentAddress,temporatyAddress,wetherOption,setDateOfAnnuation,otherreason,division,department,emailId,empnomineesharable,region,recordVerified)"
    			+ " VALUES "
    			+ "('"
    			+ cpfAcNo
    			+ "','"
    			+ pensionNumber
    			+ "','"
    			+ srno
    			+ "','"
    			+ airportSerialNumber
    			+ "','"
    			+ empNumber
    			+ "','"
    			+ empName
    			+ "','"
    			+ desegnation
    			+ "','"
    			+ empLevel
    			+ "','"
    			+ cpf
    			+ "','"
    			+ dateofBirth
    			+ "','"
    			+ dateofJoining
    			+ "','"
    			+ seperationReason
    			+ "','"
    			+ dateofSeperationDate
    			+ "','"
    			+ whetherOptionA
    			+ "','"
    			+ whetherOptionB
    			+ "','"
    			+ whetherOptionNO
    			+ "','"
    			+ form2Nomination
    			+ "','"
    			+ remarks
    			+ "','"
    			+ station
    			+ "','"
    			+ sex
    			+ "','"
    			+ fhName
    			+ "','"
    			+ maritalStatus
    			+ "','"
    			+ permanentAddress
    			+ "','"
    			+ temporatyAddress
    			+ "','"
    			+ wetherOption
    			+ "','"
    			+ dateOfAnnuation
    			+ "','"
    			+ otherReason
    			+ "','"
    			+ division
    			+ "','"
    			+ department
    			+ "','"
    			+ emailId
    			+ "','"
    			+ empNomineeSharable
    			+ "','"
    			+ bean.getRegion() + "','Y')";
    		log.info(sql);
    		st.execute(sql);
    		
    		familyrows = bean.getFamilyRow();
    		nomineeRows = bean.getNomineeRow();
    		ArrayList nomineeList = commonUtil.getTheList(nomineeRows, "***");
    		ArrayList familyList = commonUtil.getTheList(familyrows, "***");
    		// log.info("listsize is d " + familyList.size());
    		String tempInfo[] = null;
    		String tempData = "";
    		
    		for (int i = 0; i < familyList.size(); i++) {
    			tempData = familyList.get(i).toString();
    			tempInfo = tempData.split("@");
    			fMemberName = tempInfo[0];
    			
    			if (!tempInfo[1].equals("XXX")) {
    				fDateofBirth = tempInfo[1];
    			} else {
    				fDateofBirth = "";
    			}
    			if (!tempInfo[2].equals("XXX")) {
    				frelation = tempInfo[2];
    			} else {
    				frelation = "";
    			}
    			if (frelation.equals("") && fDateofBirth.equals("")
    					&& fMemberName.equals("")) {
    				log
    				.info("Data is not entered frelation,fDateofBirth,fMemberName");
    			} else if (!fMemberName.equals("")) {
    				String sql2 = "insert into employee_family_dtls(cpfaccno,familyMemberName,dateofBirth,familyRelation,region)values"
    					+ "('"
    					+ cpfAcNo
    					+ "','"
    					+ fMemberName
    					+ "','"
    					+ fDateofBirth
    					+ "','"
    					+ frelation
    					+ "','"
    					+ bean.getRegion() + "')";
    				log.info("sql2 is" + sql2);
    				st.executeUpdate(sql2);
    			}
    		}
    		
    		for (int i = 0; i < nomineeList.size(); i++) {
    			tempData = nomineeList.get(i).toString();
    			tempInfo = tempData.split("@");
    			nomineeName = tempInfo[0];
    			
    			if (!tempInfo[1].equals("XXX")) {
    				nomineeAddress = tempInfo[1];
    			} else {
    				nomineeAddress = "";
    			}
    			if (!tempInfo[2].equals("XXX")) {
    				nomineeDob = tempInfo[2];
    			} else {
    				nomineeDob = "";
    			}
    			
    			if (!tempInfo[3].equals("XXX")) {
    				nomineeRelation = tempInfo[3];
    			} else {
    				nomineeRelation = "";
    			}
    			if (!tempInfo[4].equals("XXX")) {
    				nameofGuardian = tempInfo[4];
    			} else {
    				nameofGuardian = "";
    			}
    			if (!tempInfo[5].equals("XXX")) {
    				gaddress = tempInfo[5];
    				log.info("gaddress ******* " + gaddress);
    			} else {
    				gaddress = "";
    			}
    			if (!tempInfo[6].equals("XXX")) {
    				totalShare = tempInfo[6];
    			} else {
    				totalShare = "";
    			}
    			
    			if (totalShare.equals("") && nameofGuardian.equals("")
    					&& nomineeRelation.equals("") && nomineeDob.equals("")
    					&& nomineeAddress.equals("") && nomineeName.equals("")) {
    				
    			} else {
    				String sql3 = "insert into employee_nominee_dtls(cpfaccno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,gaurdianAddress,totalshare,region)values('"
    					+ cpfAcNo
    					+ "','"
    					+ nomineeName
    					+ "','"
    					+ nomineeAddress
    					+ "','"
    					+ nomineeDob
    					+ "','"
    					+ nomineeRelation
    					+ "','"
    					+ nameofGuardian
    					+ "','"
    					+ gaddress
    					+ "','"
    					+ totalShare
    					+ "','"
    					+ bean.getRegion() + "')";
    				log.info("sql3 is" + sql3);
    				st.executeUpdate(sql3);
    			}
    		}
    		isaddPensionRecord = true;
    		// }
    		
    	} catch (SQLException e) {
    		log.printStackTrace(e);
    		throw new InvalidDataException();
    		// e.printStackTrace();
    	} catch (Exception e) {
    		log.printStackTrace(e);
    		// e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(conn, st, null);
    	}
    	log.info("PensionDAO :addPensionRecord() leaving method");
    	return isaddPensionRecord;
    }
    
    public int insertData(DatabaseBean bean) throws Exception {
    	log.info("PensionDAO :insertData() entering method");
    	int result = 0;
    	Connection con = null;
    	PreparedStatement pst = null;
    	String remarks = "";
    	double fnlPensionFund = 0.0;
    	System.out.println("Salary" + bean.getSalary());
    	if (bean.getPensionFund() != null && !bean.getPensionFund().equals("")) {
    		fnlPensionFund = (Double.parseDouble(bean.getSalary()) * Double
    				.parseDouble(bean.getPensionFund())) / 100;
    	} else {
    		fnlPensionFund = (Double.parseDouble(bean.getSalary()) * Double
    				.parseDouble("8.33")) / 100;
    	}
    	double pf = Double.parseDouble(bean.getSalary()) * 12 / 100;
    	if (fnlPensionFund > 6500.00) {
    		fnlPensionFund = 6500.00;
    	}
    	log.info("before employee check" + bean.getAirPortCD());
    	String insertSQL = "insert into employee_pension(CPFACCNO,SALPF,FROMDATE,TODATE,PF,PENSIONFUND,REMARKS) values(?,?,?,?,?,?,?)";
    	try {
    		String foundCPFNO = empNameCheck(bean.getEmpName().trim(), bean
    				.getAirPortCD(), bean.getDesegination(), "CPFACNO");
    		if (!foundCPFNO.equals("")) {
    			int count = this.totalData() + 1;
    			con = commonDB.getConnection();
    			pst = con.prepareStatement(insertSQL);
    			log.info("cpfno is " + foundCPFNO);
    			pst.setString(1, foundCPFNO);
    			pst.setString(2, bean.getSalary());
    			log.info("from date is " + bean.getFromDt());
    			pst.setString(3, bean.getFromDt());
    			pst.setString(4, bean.getToDt());
    			log.info("sal is " + bean.getSalary());
    			pst.setString(5, String.valueOf(pf));
    			pst.setString(6, String.valueOf(fnlPensionFund));
    			pst.setString(7, remarks);
    			
    			result = pst.executeUpdate();
    			if (result != 0) {
    				result = count;
    			}
    		}
    		
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		// pst.close();
    		// con.close();
    	}
    	log.info("PensionDAO :insertData() leaving method");
    	return result;
    	
    }
    
    /** New ******** */
    
    public ArrayList SearchPensionMasterAll(EmpMasterBean empMasterBean,
    		int start, boolean flag, int gridLength,String empNameCheak) throws Exception {
    	log.info("PensionDAO :SearchPensionMasterAll() entering method");
    	ArrayList empinfo = new ArrayList();
    	PensionBean data = null;
    	Connection con = null;
    	PreparedStatement pst = null;
    	ResultSet rs = null;
    	ArrayList tempList = new ArrayList();
    	DatabaseBean searchData = new DatabaseBean();
    	System.out.println("start" + start + "end" + gridLength);
    	String empNameCheck="";
    	if (empMasterBean.getEmpNameCheak().equals("true")) {
    		empNameCheck=" and employeename is null";
    		
    	}
    	
    	String sqlQuery = this.buildQuery(empMasterBean, flag,empNameCheak);
    	log.info("sql query " + sqlQuery);
    	
    	
    	
    	String sqlQueryMinrowId = "";
    	if (flag) {
    		sqlQueryMinrowId = "select * from employee_info where ROWID IN("+ sqlQuery + ") "+empNameCheck+" order by cpfacno ";
    	} else {
    		sqlQueryMinrowId = "select * from employee_info_invalidData where ROWID IN("
    			+ sqlQuery + ") order by cpfacno";
    	}
    	log.info("sqlQueryMinrowId " + sqlQueryMinrowId);
    	int countGridLength = 0, i = 0;
    	System.out.println("Query is(retriveByAll)" + sqlQueryMinrowId);
    	String employeeName = "";
    	try {
    		con = commonDB.getConnection();
    		pst = con
    		.prepareStatement(sqlQueryMinrowId,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);
    		rs = pst.executeQuery();
    		if (rs.next()) {
    			empinfo = new ArrayList();
    			
    			if (rs.absolute(start)) {
    				countGridLength++;
    				if (rs.getString("employeename") != null) {
    					employeeName = rs.getString("employeename");
    				} else {
    					employeeName = "";
    				}
    				/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    				data = new PensionBean();
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("airportcode") != null) {
    					data.setAirportCode(rs.getString("airportcode"));
    				} else {
    					data.setAirportCode("");
    				}
    				if (rs.getString("pensionnumber") != null) {
    					data.setPensionnumber(rs.getString("pensionnumber"));
    				} else {
    					data.setPensionnumber("");
    				}
    				if (rs.getString("desegnation") != null) {
    					data.setDesegnation(rs.getString("desegnation"));
    				} else {
    					data.setDesegnation("");
    				}
    				if (rs.getString("region") != null) {
    					data.setRegion(rs.getString("region"));
    				} else {
    					data.setRegion("");
    				}
    				System.out.println("EMPLOYEE (SearchPensionMasterAll)"
    						+ rs.getString("employeename"));
    				if (rs.getString("employeename") != null) {
    					data.setEmployeeName(rs.getString("employeename"));
    				} else {
    					data.setEmployeeName("");
    				}
    				if (rs.getString("employeeno") != null) {
    					data.setEmployeeCode(rs.getString("employeeno"));
    				} else {
    					data.setEmployeeCode("");
    				}
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				if (rs.getString("DATEOFJOINING") != null) {
    					data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    							.getDate("DATEOFJOINING")));
    				} else {
    					data.setDateofJoining("");
    				}
    				if (rs.getString("WETHEROPTION") != null) {
    					data.setPensionOption(rs.getString("WETHEROPTION"));
    				} else {
    					data.setPensionOption("");
    				}
    				if (rs.getString("lastactive") != null) {
    					data.setLastActive(commonUtil.getDatetoString(rs
    							.getDate("lastactive"), "dd-MMM-yyyy"));
    				} else {
    					data.setLastActive("");
    				}
    				if (rs.getString("region") != null) {
    					data.setRegion(rs.getString("region"));
    				} else {
    					data.setRegion("");
    				}
    				if (rs.getString("sex") != null) {
    					data.setSex(rs.getString("sex"));
    				} else {
    					data.setSex("");
    				}
    				if (rs.getString("division") != null) {
    					data.setDivision(rs.getString("division"));
    				} else {
    					data.setDivision("");
    				}
    				if ((data.getCpfAcNo().equals("")
    						|| (data.getEmployeeCode().equals(""))
    						|| (data.getDateofBirth().equals(""))
    						|| (data.getPensionOption().equals("")) || (data
    								.getEmployeeName().equals("")))) {
    					data.setRemarks("incomplete Data");
    					// log.info("inside if");
    				} else {
    					data.setRemarks("");
    					// log.info("inside else");
    				}
    				empinfo.add(data);
    				/*
    				 * }
    				 * 
    				 * tempList.add(i,data.getEmployeeName().trim()); i++;
    				 */
    			}
    			while (rs.next() && countGridLength < gridLength) {
    				if (rs.getString("employeename") != null) {
    					employeeName = rs.getString("employeename");
    				} else {
    					employeeName = "";
    				}
    				/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    				data = new PensionBean();
    				
    				countGridLength++;
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("airportcode") != null) {
    					data.setAirportCode(rs.getString("airportcode"));
    				} else {
    					data.setAirportCode("");
    				}
    				if (rs.getString("desegnation") != null) {
    					data.setDesegnation(rs.getString("desegnation"));
    				} else {
    					data.setDesegnation("");
    				}
    				if (rs.getString("employeename") != null) {
    					data.setEmployeeName(rs.getString("employeename"));
    				} else {
    					data.setEmployeeName("");
    				}
    				if (rs.getString("EMPLOYEENO") != null) {
    					data.setEmployeeCode(rs.getString("EMPLOYEENO"));
    				} else {
    					data.setEmployeeCode("");
    				}
    				if (rs.getString("pensionnumber") != null) {
    					data.setPensionnumber(rs.getString("pensionnumber"));
    				} else {
    					data.setPensionnumber("");
    				}
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				if (rs.getString("DATEOFJOINING") != null) {
    					data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    							.getDate("DATEOFJOINING")));
    				} else {
    					data.setDateofJoining("");
    				}
    				if (rs.getString("WETHEROPTION") != null) {
    					data.setPensionOption(rs.getString("WETHEROPTION"));
    				} else {
    					data.setPensionOption("");
    				}
    				if (rs.getString("region") != null) {
    					data.setRegion(rs.getString("region"));
    				} else {
    					data.setRegion("");
    				}
    				if ((data.getCpfAcNo().equals("")
    						|| (data.getEmployeeCode().equals(""))
    						|| (data.getDateofBirth().equals(""))
    						|| (data.getPensionOption().equals("")) || (data
    								.getEmployeeName().equals("")))) {
    					data.setRemarks("incomplete Data");
    					// log.info("inside if");
    				} else {
    					data.setRemarks("");
    					// log.info("inside else");
    				}
    				if (rs.getString("region") != null) {
    					data.setRegion(rs.getString("region"));
    				} else {
    					data.setRegion("");
    				}
    				
    				if (rs.getString("lastactive") != null) {
    					data.setLastActive(commonUtil.getDatetoString(rs
    							.getDate("lastactive"), "dd-MMM-yyyy"));
    				} else {
    					data.setLastActive("");
    				}
    				if (rs.getString("sex") != null) {
    					data.setSex(rs.getString("sex"));
    				} else {
    					data.setSex("");
    				}
    				if (rs.getString("division") != null) {
    					data.setDivision(rs.getString("division"));
    				} else {
    					data.setDivision("");
    				}
    				
    				// log.info("PensionDAO :SearchPensionMasterAll() leaving
    				// method");
    				empinfo.add(data);
    				/*
    				 * } tempList.add(i,data.getEmployeeName().trim()); i++;
    				 */
    			}
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		rs.close();
    		pst.close();
    		con.close();
    	}
    	log.info("PensionDAO :SearchPensionMasterAll() leaving method");
    	return empinfo;
    }
    
    public int totalPensionMasterData(EmpMasterBean empMasterBean, boolean flag,String empNameCheak ) {
    	log.info("PensionDAO :totalEmpMasterData() entering method");
    	Connection con = null;
    	// boolean flag=true;
    	String selectSQL = this.buildQueryforEmptotal(empMasterBean, flag,empNameCheak);
    	String sqlQueryMinrowId = "";
    	String empNameCheck="";
    	if (empMasterBean.getEmpNameCheak().equals("true")) {
    		empNameCheck=" and employeename is null";
    		
    	}
    	if (flag) {
    		sqlQueryMinrowId = "select * from employee_info where ROWID IN("+ selectSQL + ") "+empNameCheck+" order by cpfacno";
    	} else {
    		sqlQueryMinrowId = "select * from employee_info_invalidData where ROWID IN("
    			+ selectSQL + ")   "+empNameCheck+" order by cpfacno";
    	}
    	
    	log.info("sqlQueryMinrowId " + sqlQueryMinrowId);
    	int totalRecords = 0;
    	try {
    		con = commonDB.getConnection();
    		Statement st = con.createStatement();
    		ResultSet rs = st.executeQuery(sqlQueryMinrowId);
    		while (rs.next()) {
    			totalRecords++;
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	log.info("PensionDAO :totalPensionMasterData() leaving method");
    	return totalRecords;
    }
    
    public int totalPensionMasterDatafordobandName(EmpMasterBean empMasterBean,
    		String flag,String userType) {
    	log
    	.info("PensionDAO :totalPensionMasterDatafordobandName() entering method");
    	Connection con = null;
    	// boolean flag=true;
    	String selectSQL = this.searchRecordsbyDobandName(empMasterBean, flag,"",userType);
    	String sqlQueryMinrowId = "";
    	/*
    	 * if (flag) { sqlQueryMinrowId = "select * from employee_info where
    	 * ROWID IN(" + selectSQL + ") order by cpfacno"; } else {
    	 * sqlQueryMinrowId = "select * from employee_pension_master where ROWID
    	 * IN(" + selectSQL + ") order by EMPSERIALNUMBER"; }
    	 */
    	
    	log.info("selectSQL " + selectSQL);
    	int totalRecords = 0;
    	try {
    		con = commonDB.getConnection();
    		Statement st = con.createStatement();
    		ResultSet rs = st.executeQuery(selectSQL);
    		while (rs.next()) {
    			totalRecords++;
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	log
    	.info("PensionDAO :totalPensionMasterDatafordobandName() leaving method");
    	return totalRecords;
    	
    }
    
    public ArrayList SearchPensionAll(EmpMasterBean empBean, int start,
    		boolean flag, int gridLength) {
    	log.info("PensionDAO :SearchPensionAll() entering method");
    	ArrayList hindiData = new ArrayList();
    	PensionBean data = null;
    	Connection con = null;
    	
    	PensionBean searchData = new PensionBean();
    	log.info("start" + start + "end" + gridLength);
    	String sqlQuery = this.buildQuery(empBean, flag,"");
    	int countGridLength = 0;
    	String sqlQueryMinrowId = "select * from employee_info where ROWID IN("
    		+ sqlQuery + ") order by cpfacno ";
    	log.info("sqlQueryMinrowId " + sqlQueryMinrowId);
    	log.info("Query is(SearchPensionAll)" + sqlQueryMinrowId);
    	PreparedStatement pst = null;
    	try {
    		con = commonDB.getConnection();
    		pst = con
    		.prepareStatement(sqlQuery,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);
    		ResultSet rs = pst.executeQuery();
    		if (rs.next()) {
    			hindiData = new ArrayList();
    			if (rs.absolute(start)) {
    				countGridLength++;
    				data = new PensionBean();
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("airportcode") != null) {
    					data.setAirportCode(rs.getString("airportcode"));
    				} else {
    					data.setAirportCode("");
    				}
    				if (rs.getString("desegnation") != null) {
    					data.setDesegnation(rs.getString("desegnation"));
    				} else {
    					data.setDesegnation("");
    				}
    				if (rs.getString("employeename") != null) {
    					data.setEmployeeName(rs.getString("employeename"));
    				} else {
    					data.setEmployeeName("");
    				}
    				if (rs.getString("employeeno") != null) {
    					data.setEmployeeCode(rs.getString("employeeno"));
    				} else {
    					data.setEmployeeCode("");
    				}
    				if (rs.getString("pensionnumber") != null) {
    					data.setPensionnumber(rs.getString("pensionnumber"));
    				} else {
    					data.setPensionnumber("");
    				}
    				
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				if (rs.getString("WETHEROPTION") != null) {
    					data.setPensionOption(rs.getString("WETHEROPTION"));
    				} else {
    					data.setPensionOption("");
    				}
    				if ((data.getCpfAcNo().equals("")
    						|| (data.getEmployeeCode().equals(""))
    						|| (data.getDateofBirth().equals(""))
    						|| (data.getPensionOption().equals("")) || (data
    								.getEmployeeName().equals("")))) {
    					data.setRemarks("incomplete Data");
    					// log.info("inside if");
    				} else {
    					data.setRemarks("");
    					// log.info("inside else");
    				}
    				log.info("PensionDAO :SearchPensionAll() leaving method");
    				hindiData.add(data);
    			}
    			while (rs.next() && countGridLength < gridLength) {
    				data = new PensionBean();
    				countGridLength++;
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("airportcode") != null) {
    					data.setAirportCode(rs.getString("airportcode"));
    				} else {
    					data.setAirportCode("");
    				}
    				
    				if (rs.getString("desegnation") != null) {
    					data.setDesegnation(rs.getString("desegnation"));
    				} else {
    					data.setDesegnation("");
    				}
    				if (rs.getString("employeename") != null) {
    					data.setEmployeeName(rs.getString("employeename"));
    				} else {
    					data.setEmployeeName("");
    				}
    				if (rs.getString("employeeno") != null) {
    					data.setEmployeeCode(rs.getString("employeeno"));
    				} else {
    					data.setEmployeeCode("");
    				}
    				if (rs.getString("pensionnumber") != null) {
    					data.setPensionnumber(rs.getString("pensionnumber"));
    				} else {
    					data.setPensionnumber("");
    				}
    				
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				
    				if (rs.getString("WETHEROPTION") != null) {
    					data.setPensionOption(rs.getString("WETHEROPTION"));
    				} else {
    					data.setPensionOption("");
    				}
    				if ((data.getCpfAcNo().equals("")
    						|| (data.getEmployeeCode().equals(""))
    						|| (data.getDateofBirth().equals(""))
    						|| (data.getPensionOption().equals("")) || (data
    								.getEmployeeName().equals("")))) {
    					data.setRemarks("incomplete Data");
    					// log.info("inside if");
    				} else {
    					data.setRemarks("");
    					// log.info("inside else");
    				}
    				hindiData.add(data);
    			}
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	log.info("PensionDAO :SearchPensionAll() leaving method");
    	return hindiData;
    	
    }
    
    public boolean checkUser(String userName, String password) throws Exception {
    	boolean isCheckUser = false;
    	log.info("PensionDao: checkUser() entering method");
    	Connection con = null;
    	Statement st = null;
    	// ResultSet rs = null;
    	try {
    		String query = "select username from employee_user where username='"
    			+ userName + "' and password='" + password + "'";
    		
    		log.info("query is " + query);
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		ResultSet rs = st.executeQuery(query);
    		if (rs.next()) {
    			isCheckUser = true;
    		}
    		
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	finally {
    		// rs.close();
    		st.close();
    		con.close();
    	}
    	log.info("PensionDao: checkUser() leaving method");
    	return isCheckUser;
    }
    
    public EmpMasterBean editPensionMaster(String cpfacno, String empName,
    		boolean flag, String empCode, String region) throws Exception {
    	log.info("PensionDAO:editPensionMaster entering method ");
    	EmpMasterBean editBean = new EmpMasterBean();
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	String query = "",query1="";
    	if (flag == true) {
    		if (!cpfacno.equals("") && !empName.equals("")) {
    			
    			query=" select * from employee_personal_info where ROWID IN(select MIN(ROWID) from employee_info" +
    			" where empflag='Y' AND LOWER(cpfacno) ='" + cpfacno.toLowerCase().trim()+ "' AND region='"+region.trim()+"' AND LOWER(employeename)='"+empName.toLowerCase()
    			+ "' group by cpfacno) order by cpfacno";
    			log.info("query1" +query1);
    		} else if (!cpfacno.equals("")) {
    			query=" select * from employee_personal_info where ROWID IN(select MIN(ROWID) from employee_info" +
    			" where empflag='Y' AND LOWER(cpfacno) ='" + cpfacno.toLowerCase()
    			+ "' AND region='" + region.trim() + "'  group by cpfacno) order by cpfacno";
    		}
    		
    		else {
    			query=" select * from employee_personal_info where ROWID IN(select MIN(ROWID) from employee_info" +
    			" where empflag='Y' AND LOWER(EMPLOYEENO) ='" +empCode.toLowerCase()+ "' AND region='" + region.trim() + "')";
    			//	query = "select * from employee_info where LOWER(employeename)='"	+ empName.toLowerCase()+ "' AND LOWER(EMPLOYEENO)='"+ empCode.toLowerCase() + "'";
    		}
    	} else {
    		query = "select * from employee_info_invaliddata where cpfacno='"
    			+ cpfacno + "' AND LOWER(employeename)='"
    			+ empName.toLowerCase() + "'";
    	}
    	log.info("query is " + query);
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		editBean.setCpfAcNo(cpfacno.trim());
    		while (rs.next()) {
    			
    			if (rs.getString("employeename") != null)
    				editBean.setEmpName(rs.getString("employeename").trim());
    			else
    				editBean.setEmpName("");
    			if (rs.getString("EMPLOYEENO") != null)
    				editBean.setEmpNumber(rs.getString("EMPLOYEENO").trim());
    			else
    				editBean.setEmpNumber("");
    			
    			if (rs.getString("desegnation") != null)
    				editBean.setDesegnation(rs.getString("desegnation").trim());
    			else
    				editBean.setDesegnation("");
    			
    			if (rs.getString("EMP_LEVEL") != null)
    				editBean.setEmpLevel(rs.getString("EMP_LEVEL"));
    			else
    				editBean.setEmpLevel("");
    			
    			if (rs.getString("DATEOFBIRTH") != null) {
    				editBean.setDateofBirth(commonUtil.converDBToAppFormat(rs.getString("DATEOFBIRTH").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
    				//editBean.setDateofBirth(CommonUtil.getStringtoDate(rs.getString("DATEOFBIRTH").toString()));
    			} else
    				editBean.setDateofBirth("");
    			if (rs.getString("DATEOFJOINING") != null)
    				editBean.setDateofJoining(commonUtil.converDBToAppFormat(rs
    						.getString("DATEOFJOINING").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
    			else
    				editBean.setDateofJoining("");
    			
    			if (rs.getString("DATEOFSEPERATION_REASON") != null)
    				editBean.setSeperationReason(rs
    						.getString("DATEOFSEPERATION_REASON"));
    			else
    				editBean.setSeperationReason("");
    			
    			if (rs.getString("DATEOFSEPERATION_DATE") != null)
    				//editBean.setDateofSeperationDate(CommonUtil.getStringtoDate(rs.getString("DATEOFSEPERATION_DATE").toString()));
    				editBean.setDateofSeperationDate(commonUtil.converDBToAppFormat(rs.getString(
    				"DATEOFSEPERATION_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
    			else
    				editBean.setDateofSeperationDate("");
    			if (rs.getString("WHETHER_OPTION_A") != null)
    				editBean
    				.setWhetherOptionA(rs.getString("WHETHER_OPTION_A"));
    			else
    				editBean.setWhetherOptionA("");
    			if (rs.getString("WHETHER_OPTION_B") != null)
    				editBean
    				.setWhetherOptionB(rs.getString("WHETHER_OPTION_B"));
    			else
    				editBean.setWhetherOptionB("");
    			if (rs.getString("WHETHER_OPTION_No") != null)
    				editBean.setWhetherOptionNO(rs
    						.getString("WHETHER_OPTION_No"));
    			else
    				editBean.setWhetherOptionNO("");
    			if (rs.getString("WHETHER_FORM1_NOM_RECEIVED") != null)
    				editBean.setForm2Nomination(rs
    						.getString("WHETHER_FORM1_NOM_RECEIVED"));
    			else
    				editBean.setForm2Nomination("");
    			
    			if (rs.getString("airportcode") != null)
    				editBean.setStation(rs.getString("airportcode"));
    			else
    				editBean.setStation("");
    			if (rs.getString("pensionnumber") != null)
    				editBean.setPensionNumber(rs.getString("pensionnumber"));
    			else
    				editBean.setPensionNumber("");
    			if (rs.getString("emailId") != null)
    				editBean.setEmailId(rs.getString("emailId"));
    			else
    				editBean.setEmailId("");
    			if (rs.getString("AIRPORTSERIALNUMBER") != null)
    				editBean.setAirportSerialNumber(rs
    						.getString("AIRPORTSERIALNUMBER"));
    			else
    				editBean.setAirportSerialNumber("");
    			if (rs.getString("employeeno") != null)
    				editBean.setEmpNumber(rs.getString("employeeno"));
    			else
    				editBean.setEmpNumber("");
    			if (rs.getString("remarks") != null)
    				editBean.setRemarks(rs.getString("remarks"));
    			else
    				editBean.setRemarks("");
    			if (rs.getString("sex") != null)
    				editBean.setSex(rs.getString("sex"));
    			else
    				editBean.setSex("");
    			
    			if (rs.getString("setDateOfAnnuation") != null)
    				//	editBean.setDateOfAnnuation(CommonUtil.getStringtoDate(rs.getString("setDateOfAnnuation")));
    				editBean.setDateOfAnnuation(commonUtil.converDBToAppFormat(rs
    						.getString("setDateOfAnnuation"),"yyyy-MM-dd","dd-MMM-yyyy"));
    			else
    				editBean.setDateOfAnnuation("");
    			if (rs.getString("fhname") != null)
    				editBean.setFhName(rs.getString("fhname"));
    			else
    				editBean.setFhName("");
    			if (rs.getString("maritalstatus") != null)
    				editBean.setMaritalStatus(rs.getString("maritalstatus"));
    			else
    				editBean.setMaritalStatus("");
    			log.info("PERMANENTADDRESS" + rs.getString("PERMANENTADDRESS"));
    			if (rs.getString("PERMANENTADDRESS") != null)
    				editBean.setPermanentAddress(rs
    						.getString("PERMANENTADDRESS"));
    			else
    				editBean.setPermanentAddress("");
    			
    			if (rs.getString("TEMPORATYADDRESS") != null)
    				editBean.setTemporatyAddress(rs
    						.getString("TEMPORATYADDRESS"));
    			else
    				editBean.setTemporatyAddress("");
    			if (rs.getString("wetherOption") != null) {
    				editBean.setWetherOption(rs.getString("wetherOption"));
    			} else
    				editBean.setWetherOption("");
    			
    			if (rs.getString("region") != null) {
    				editBean.setRegion(rs.getString("region"));
    			} else
    				editBean.setRegion("");
    			
    			if (rs.getString("otherreason") != null) {
    				editBean.setOtherReason(rs.getString("otherreason"));
    			} else
    				editBean.setOtherReason("");
    			
    			if (rs.getString("department") != null) {
    				editBean.setDepartment(rs.getString("department"));
    			} else
    				editBean.setDepartment("");
    			if (rs.getString("division") != null) {
    				editBean.setDivision(rs.getString("division"));
    				log.info("division is" + editBean.getDivision());
    			} else
    				editBean.setDivision("");
    			
    			if (rs.getString("empnomineesharable") != null) {
    				editBean.setEmpNomineeSharable(rs
    						.getString("empnomineesharable"));
    			} else
    				editBean.setEmpNomineeSharable("");
    			
    			if (rs.getString("recordVerified") != null) {
    				editBean.setRecordVerified(rs.getString("recordVerified"));
    			} else
    				editBean.setRecordVerified("");
    			if (rs.getString("region") != null) {
    				editBean.setRegion(rs.getString("region"));
    			} else
    				editBean.setRegion("");
    			if (rs.getString("FORMSDISABLE") != null) {
    				editBean.setRegion(rs.getString("FORMSDISABLE"));
    				
    			} else
    				editBean.setRegion("");
    			/* Editing Family records */
    			String query2 = "select FAMILYMEMBERNAME,DATEOFBIRTH,FAMILYRELATION,rowId from employee_family_dtls where cpfaccno='"
    				+ cpfacno + "'";
    			rs1 = st.executeQuery(query2);
    			StringBuffer sbf = new StringBuffer();
    			String FAMILYMEMBERNAME = "", DATEOFBIRTH = "", FAMILYRELATION = "",rowId="";
    			while (rs1.next()) {
    				if (rs.getString("FAMILYMEMBERNAME") != null) {
    					FAMILYMEMBERNAME = rs.getString("FAMILYMEMBERNAME");
    				} else {
    					FAMILYMEMBERNAME = "xxx";
    				}
    				if (rs.getString("DATEOFBIRTH") != null) {
    					
    					//	DATEOFBIRTH = CommonUtil.getStringtoDate(rs.getString("DATEOFBIRTH"));
    					DATEOFBIRTH = commonUtil.converDBToAppFormat(rs
    							.getString("DATEOFBIRTH"),"yyyy-MM-dd","dd-MMM-yyyy");
    				} else {
    					DATEOFBIRTH = "xxx";
    				}
    				if (rs.getString("FAMILYRELATION") != null) {
    					FAMILYRELATION = rs.getString("FAMILYRELATION");
    				} else {
    					FAMILYRELATION = "xxx";
    				}
    				log.info("FAMILYRELATION   " + FAMILYRELATION);
    				if (rs.getString("rowId") != null) {
    					rowId = rs.getString("rowId");
    				} else {
    					rowId = "xxx";
    				}
    				sbf.append(FAMILYMEMBERNAME + "@");
    				sbf.append(DATEOFBIRTH + "@");
    				sbf.append(FAMILYRELATION + "@");
    				sbf.append(rowId + "***");
    				
    			}
    			log.info("family records " + sbf.toString());
    			editBean.setFamilyRow(sbf.toString());
    			/* End of Editing Family of records */
    			
    			/* Editing nominee records */
    			String query3 = "select NOMINEENAME,NOMINEEADDRESS,NOMINEEDOB,NOMINEERELATION,NAMEOFGUARDIAN,GAURDIANADDRESS,TOTALSHARE,rowid from employee_nominee_dtls where cpfaccno='"
    				+ cpfacno + "'";
    			rs2 = st.executeQuery(query3);
    			StringBuffer sbf1 = new StringBuffer();
    			while (rs2.next()) {
    				String nomineeName = "", nomineeAddress = "", nomineeDob = "";
    				String nomineeRelation = "", nameofGuardian = "", guardianAddress = "", totalShare = "";
    				
    				if (rs.getString("NOMINEENAME") != null) {
    					nomineeName = rs.getString("NOMINEENAME");
    				} else {
    					nomineeName = "xxx";
    				}
    				// nomineeName =rs.getString("NOMINEENAME");
    				if (rs.getString("NOMINEEADDRESS") != null) {
    					nomineeAddress = rs.getString("NOMINEEADDRESS");
    				} else {
    					nomineeAddress = "xxx";
    				}
    				if (rs.getString("NOMINEEDOB") != null) {
    					//nomineeDob = CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
    					nomineeDob = commonUtil.converDBToAppFormat(rs
    							.getString("NOMINEEDOB"),"yyyy-MM-dd","dd-MMM-yyyy");
    				} else {
    					nomineeDob = "xxx";
    				}
    				if (rs.getString("NOMINEERELATION") != null) {
    					nomineeRelation = rs.getString("NOMINEERELATION");
    				} else {
    					nomineeRelation = "xxx";
    				}
    				if (rs.getString("NAMEOFGUARDIAN") != null) {
    					nameofGuardian = rs.getString("NAMEOFGUARDIAN");
    					
    				} else {
    					nameofGuardian = "xxx";
    				}
    				if (rs.getString("GAURDIANADDRESS") != null) {
    					guardianAddress = rs.getString("GAURDIANADDRESS");
    					log.info("guardianAddress  " + guardianAddress);
    				} else {
    					guardianAddress = "xxx";
    				}
    				if (rs.getString("TOTALSHARE") != null) {
    					totalShare = rs.getString("TOTALSHARE");
    				} else {
    					totalShare = "xxx";
    				}
    				
    				if (rs.getString("rowId") != null) {
    					rowId = rs.getString("rowId");
    				} else {
    					rowId = "xxx";
    				}
    				// nomineeAddress=rs.getString("NOMINEEADDRESS");
    				// nomineeDob=CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
    				// nomineeRelation =rs.getString("NOMINEERELATION");
    				// nameofGuardian = rs.getString("NAMEOFGUARDIAN");
    				// totalShare = rs.getString("TOTALSHARE");
    				sbf1.append(nomineeName + "@");
    				sbf1.append(nomineeAddress + "@");
    				sbf1.append(nomineeDob + "@");
    				sbf1.append(nomineeRelation + "@");
    				sbf1.append(nameofGuardian + "@");
    				sbf1.append(guardianAddress + "@");
    				sbf1.append(totalShare + "@");
    				sbf1.append(rowId + "***");
    				log.info(sbf1.toString());
    				// sbf1.append(nomineeRelation + "@");
    				
    			}
    			log.info("nominee records " + sbf1.toString());
    			editBean.setNomineeRow(sbf1.toString());
    		}
    		
    	} catch (Exception e) {
    		System.out.println("Exception is" + e.getMessage());
    	}
    	
    	finally {
    		rs.close();
    		st.close();
    		con.close();
    	}
    	log.info("PensionDAO:editPensionMaster leaving method ");
    	return editBean;
    }
    
    public int updatePensionMaster(EmpMasterBean bean, String flag)
    throws InvalidDataException {
    	log.info("PensionDAO:updatePensionMaster entering method ");
    	// PensionBean editBean =new PensionBean();
    	Connection con = null;
    	Statement st = null;
    	int count = 0;
    	String srno = "", airportSerialNumber = "", empNumber = "", cpfAcNo = "", newCpfAcno = "";
    	String empName = "", desegnation = "", empLevel = "", seperationReason = "", whetherOptionA = "";
    	String whetherOptionB = "", whetherOptionNO = "", form2Nomination = "";
    	String remarks = "", station = "", dateofBirth = "", dateofJoining = "", dateofSeperationDate = "";
    	String fMemberName = "", fDateofBirth = "", frelation = "", familyrows = "";
    	String wetherOption = "", sex = "", maritalStatus = "", fhName = "", permanentAddress = "", temporatyAddress = "", dateOfAnnuation = "", otherReason = "";
    	airportSerialNumber = bean.getAirportSerialNumber();
    	empNumber = bean.getEmpNumber();
    	cpfAcNo = bean.getCpfAcNo().trim();
    	newCpfAcno = bean.getNewCpfAcNo();
    	station = bean.getStation();
    	empName = bean.getEmpName();
    	desegnation = bean.getDesegnation();
    	empLevel = bean.getEmpLevel();
    	seperationReason = bean.getSeperationReason();
    	whetherOptionA = bean.getWhetherOptionA();
    	whetherOptionB = bean.getWhetherOptionB();
    	whetherOptionNO = bean.getWhetherOptionNO();
    	remarks = bean.getRemarks();
    	dateofBirth = bean.getDateofBirth();
    	dateofJoining = bean.getDateofJoining();
    	dateofSeperationDate = bean.getDateofSeperationDate();
    	form2Nomination = bean.getForm2Nomination();
    	String pensionNumber = bean.getPensionNumber();
    	String empNomineeSharable = bean.getEmpNomineeSharable();
    	wetherOption = bean.getWetherOption();
    	sex = bean.getSex();
    	maritalStatus = bean.getMaritalStatus();
    	fhName = bean.getFhName();
    	permanentAddress = bean.getPermanentAddress();
    	temporatyAddress = bean.getTemporatyAddress();
    	dateOfAnnuation = bean.getDateOfAnnuation();
    	// String pensionNumber = this.getPensionNumber(empName.toUpperCase(),
    	// dateofBirth, cpfAcNo);
    	otherReason = bean.getOtherReason().trim();
    	String division = bean.getDivision();
    	String department = bean.getDepartment();
    	String emailId = bean.getEmailId();
    	String empOldName = bean.getEmpOldName();
    	String empOldNumber = bean.getEmpOldNumber();
    	String region = bean.getRegion();
    	String setRecordVerified = bean.getRecordVerified();
    	log.info("userName " + bean.getUserName());
    	log.info("computer Name" + bean.getComputerName());
    	java.util.Date now = new java.util.Date();
    	String MY_DATE_FORMAT = "dd-MM-yyyy hh:mm a";
    	String currDateTime = new SimpleDateFormat(MY_DATE_FORMAT).format(now);
    	log.info("date is  " + currDateTime);
    	
    	try {
    		String query = "";
    		log.info("query is " + query);
    		String query1 = "select * from employee_pension_validate where  cpfaccno='"
    			+ cpfAcNo + "' and employeename='" + empName + "'";
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		String sql1 = "";
    		this.insertEmployeeHistory(cpfAcNo, "", true, "", region,
    				currDateTime, bean.getComputerName().trim(), bean
    				.getUserName());
    		if (newCpfAcno.equals(cpfAcNo.trim())&& bean.getRegion().equals(bean.getNewRegion().trim())) {
    			System.out.println("pensionNumber===========" + pensionNumber
    					+ "cpfAcNo" + cpfAcNo + "bean.getEmpOldNumber()"
    					+ bean.getEmpOldNumber());
    			System.out.println("region===========" + region + "dateofBirth"
    					+ dateofBirth);
    			//pensionNumber = checkPensionNumber(pensionNumber, cpfAcNo, bean.getEmpOldNumber(), empName, region.trim(), dateofBirth);
    			System.out.println("pensionNumber===New========"
    					+ pensionNumber);
    			
    			if (bean.getEmpOldNumber().trim().equals("")
    					&& bean.getEmpOldName().equals("")) {
    				query = "update  employee_info set airportcode='" + station
    				+ "',employeename='" + empName.trim() + "',desegnation='"
    				+ desegnation + "',AIRPORTSERIALNUMBER='"
    				+ airportSerialNumber + "',EMPLOYEENO='"
    				+ empNumber + "',EMP_LEVEL='" + empLevel
    				+ "',DATEOFBIRTH ='" + dateofBirth
    				+ "',DATEOFJOINING='" + dateofJoining
    				+ "',DATEOFSEPERATION_REASON='" + seperationReason
    				+ "',DATEOFSEPERATION_DATE='"
    				+ dateofSeperationDate + "',"
    				+ "WHETHER_OPTION_A ='" + whetherOptionA
    				+ "',WHETHER_OPTION_B ='" + whetherOptionB
    				+ "',WHETHER_OPTION_NO='" + whetherOptionNO
    				+ "',REMARKS='" + remarks.trim() + "',sex='" + sex
    				+ "',maritalStatus='" + maritalStatus
    				+ "',permanentAddress='" + permanentAddress
    				+ "',temporatyAddress='" + temporatyAddress
    				+ "',wetherOption='" + wetherOption
    				+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    				+ form2Nomination + "',fhname='" + fhName
    				+ "',setDateOfAnnuation='" + dateOfAnnuation
    				+ "',pensionnumber='" + pensionNumber
    				+ "',otherreason='" + otherReason + "',division='"
    				+ division + "',department='" + department
    				+ "',emailId='" + emailId + "',lastactive='"
    				+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    				+ "',empnomineesharable='" + empNomineeSharable
    				+ "',userName='" + bean.getUserName()
    				+ "',recordVerified='" + setRecordVerified
    				+ "'   where empflag='Y' and  cpfacno='" + cpfAcNo
    				+ "'" + " and employeename is null   and region='"
    				+ region.trim() + "'  and EMPLOYEENO is null ";
    			} else if (bean.getEmpOldNumber().trim().equals("")) {
    				query = "update  employee_info set airportcode='" + station
    				+ "',employeename='" + empName.trim() + "',desegnation='"
    				+ desegnation + "',AIRPORTSERIALNUMBER='"
    				+ airportSerialNumber + "',EMPLOYEENO='"
    				+ empNumber + "',EMP_LEVEL='" + empLevel
    				+ "',DATEOFBIRTH ='" + dateofBirth
    				+ "',DATEOFJOINING='" + dateofJoining
    				+ "',DATEOFSEPERATION_REASON='" + seperationReason
    				+ "',DATEOFSEPERATION_DATE='"
    				+ dateofSeperationDate + "',"
    				+ "WHETHER_OPTION_A ='" + whetherOptionA
    				+ "',WHETHER_OPTION_B ='" + whetherOptionB
    				+ "',WHETHER_OPTION_NO='" + whetherOptionNO
    				+ "',REMARKS='" + remarks.trim() + "',sex='" + sex
    				+ "',maritalStatus='" + maritalStatus
    				+ "',permanentAddress='" + permanentAddress
    				+ "',temporatyAddress='" + temporatyAddress
    				+ "',wetherOption='" + wetherOption
    				+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    				+ form2Nomination + "',fhname='" + fhName
    				+ "',setDateOfAnnuation='" + dateOfAnnuation
    				+ "',pensionnumber='" + pensionNumber
    				+ "',otherreason='" + otherReason + "',division='"
    				+ division + "',department='" + department
    				+ "',emailId='" + emailId + "',lastactive='"
    				+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    				+ "',empnomineesharable='" + empNomineeSharable
    				+ "',userName='" + bean.getUserName()
    				+ "',recordVerified='" + setRecordVerified
    				+ "'   where empflag='Y' and  cpfacno='" + cpfAcNo
    				+ "' and trim(employeename)='" + empOldName.trim()
    				+ "'   and region='" + region.trim()
    				+ "'  and EMPLOYEENO is null ";
    			} else if (bean.getEmpOldName().equals("")) {
    				query = "update  employee_info set airportcode='" + station
    				+ "',employeename='" + empName.trim() + "',desegnation='"
    				+ desegnation + "',AIRPORTSERIALNUMBER='"
    				+ airportSerialNumber + "',EMPLOYEENO='"
    				+ empNumber + "',EMP_LEVEL='" + empLevel
    				+ "',DATEOFBIRTH ='" + dateofBirth
    				+ "',DATEOFJOINING='" + dateofJoining
    				+ "',DATEOFSEPERATION_REASON='" + seperationReason
    				+ "',DATEOFSEPERATION_DATE='"
    				+ dateofSeperationDate + "',"
    				+ "WHETHER_OPTION_A ='" + whetherOptionA
    				+ "',WHETHER_OPTION_B ='" + whetherOptionB
    				+ "',WHETHER_OPTION_NO='" + whetherOptionNO
    				+ "',REMARKS='" + remarks.trim() + "',sex='" + sex
    				+ "',maritalStatus='" + maritalStatus
    				+ "',permanentAddress='" + permanentAddress
    				+ "',temporatyAddress='" + temporatyAddress
    				+ "',wetherOption='" + wetherOption
    				+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    				+ form2Nomination + "',fhname='" + fhName
    				+ "',setDateOfAnnuation='" + dateOfAnnuation
    				+ "',pensionnumber='" + pensionNumber
    				+ "',otherreason='" + otherReason + "',division='"
    				+ division + "',department='" + department
    				+ "',emailId='" + emailId + "',lastactive='"
    				+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    				+ "',empnomineesharable='" + empNomineeSharable
    				+ "',userName='" + bean.getUserName()
    				+ "',recordVerified='" + setRecordVerified
    				+ "'   where empflag='Y' and  cpfacno='" + cpfAcNo
    				+ "'  and region='" + region.trim()
    				+ "'  and employeename is null ";
    			} else {
    				query = "update  employee_info set airportcode='" + station
    				+ "',employeename='" + empName.trim() + "',desegnation='"
    				+ desegnation + "',AIRPORTSERIALNUMBER='"
    				+ airportSerialNumber + "',EMPLOYEENO='"
    				+ empNumber + "',EMP_LEVEL='" + empLevel
    				+ "',DATEOFBIRTH ='" + dateofBirth
    				+ "',DATEOFJOINING='" + dateofJoining
    				+ "',DATEOFSEPERATION_REASON='" + seperationReason
    				+ "',DATEOFSEPERATION_DATE='"
    				+ dateofSeperationDate + "',"
    				+ "WHETHER_OPTION_A ='" + whetherOptionA
    				+ "',WHETHER_OPTION_B ='" + whetherOptionB
    				+ "',WHETHER_OPTION_NO='" + whetherOptionNO
    				+ "',REMARKS='" + remarks.trim() + "',sex='" + sex
    				+ "',maritalStatus='" + maritalStatus
    				+ "',permanentAddress='" + permanentAddress
    				+ "',temporatyAddress='" + temporatyAddress
    				+ "',wetherOption='" + wetherOption
    				+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    				+ form2Nomination + "',fhname='" + fhName
    				+ "',setDateOfAnnuation='" + dateOfAnnuation
    				+ "',pensionnumber='" + pensionNumber
    				+ "',otherreason='" + otherReason + "',division='"
    				+ division + "',department='" + department
    				+ "',emailId='" +emailId + "',lastactive='"
    				+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    				+ "',empnomineesharable='" +empNomineeSharable
    				+ "',userName='" +bean.getUserName()
    				+ "',recordVerified='"+setRecordVerified
    				+ "'   where empflag='Y' and cpfacno='"+cpfAcNo
    				+ "' and trim(employeename)='"+empOldName.trim()
    				+ "' and EMPLOYEENO='" +bean.getEmpOldNumber()
    				+ "'  and region='" +region.trim()+ "'";
    				
    			}
    			log.info("query is " + query);
    			count = st.executeUpdate(query);
    			String query2="";
    			if (bean.getEmpOldNumber().trim().equals("")
    					&& bean.getEmpOldName().equals("")) {
    				query2 = "update  EMPLOYEE_PERSONAL_INFO set airportcode='"
    					+ station + "',employeename='" + empName.trim()
    					+ "',desegnation='" + desegnation
    					+ "',AIRPORTSERIALNUMBER='" + airportSerialNumber
    					+ "',EMPLOYEENO='" + empNumber + "',EMP_LEVEL='"
    					+ empLevel + "',DATEOFBIRTH ='" + dateofBirth
    					+ "',DATEOFJOINING='" + dateofJoining
    					+ "',DATEOFSEPERATION_REASON='" + seperationReason
    					+ "',DATEOFSEPERATION_DATE='"
    					+ dateofSeperationDate + "',REMARKS='"
    					+ remarks.trim() + "',gender='" + sex
    					+ "',maritalStatus='" + maritalStatus
    					+ "',permanentAddress='" + permanentAddress
    					+ "',temporatyAddress='" + temporatyAddress
    					+ "',wetherOption='" + wetherOption
    					+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    					+ form2Nomination + "',fhname='" + fhName
    					+ "',setDateOfAnnuation='" + dateOfAnnuation
    					+ "',REFPENSIONNUMBER='" + pensionNumber
    					+ "',otherreason='" + otherReason + "',division='"
    					+ division + "',department='" + department
    					+ "',emailId='" + emailId + "',lastactive='"
    					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    					+ "',empnomineesharable='" + empNomineeSharable
    					+ "',userName='" + bean.getUserName()
    					+ "',fhflag='"+bean.getFhFlag()+"'  where empflag='Y' and  cpfacno='" + cpfAcNo
    					+ "' and employeename is null   and region='"
    					+ region.trim() + "'  and EMPLOYEENO is null ";
    			} else if (bean.getEmpOldNumber().trim().equals("")) {
    				query2 = "update  EMPLOYEE_PERSONAL_INFO set airportcode='"
    					+ station + "',employeename='" + empName.trim()
    					+ "',desegnation='" + desegnation
    					+ "',AIRPORTSERIALNUMBER='" + airportSerialNumber
    					+ "',EMPLOYEENO='" + empNumber + "',EMP_LEVEL='"
    					+ empLevel + "',DATEOFBIRTH ='" + dateofBirth
    					+ "',DATEOFJOINING='" + dateofJoining
    					+ "',DATEOFSEPERATION_REASON='" + seperationReason
    					+ "',DATEOFSEPERATION_DATE='"
    					+ dateofSeperationDate + "',REMARKS='"
    					+ remarks.trim() + "',gender='" + sex
    					+ "',maritalStatus='" + maritalStatus
    					+ "',permanentAddress='" + permanentAddress
    					+ "',temporatyAddress='" + temporatyAddress
    					+ "',wetherOption='" + wetherOption
    					+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    					+ form2Nomination + "',fhname='" + fhName
    					+ "',setDateOfAnnuation='" + dateOfAnnuation
    					+ "',REFPENSIONNUMBER='" + pensionNumber
    					+ "',otherreason='" + otherReason + "',division='"
    					+ division + "',department='" + department
    					+ "',emailId='" + emailId + "',lastactive='"
    					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    					+ "',empnomineesharable='" + empNomineeSharable
    					+ "',userName='" + bean.getUserName()
    					+ "',fhflag='"+bean.getFhFlag()+"' where empflag='Y' and cpfacno='" + cpfAcNo
    					+ "' and trim(employeename)='" + empOldName.trim()
    					+ "'   and region='" + region.trim()
    					+ "'  and EMPLOYEENO is null ";
    			} else if (bean.getEmpOldName().equals("")) {
    				query2 = "update  EMPLOYEE_PERSONAL_INFO set airportcode='"
    					+ station + "',employeename='" + empName.trim()
    					+ "',desegnation='" + desegnation
    					+ "',AIRPORTSERIALNUMBER='" + airportSerialNumber
    					+ "',EMPLOYEENO='" + empNumber + "',EMP_LEVEL='"
    					+ empLevel + "',DATEOFBIRTH ='" + dateofBirth
    					+ "',DATEOFJOINING='" + dateofJoining
    					+ "',DATEOFSEPERATION_REASON='" + seperationReason
    					+ "',DATEOFSEPERATION_DATE='"
    					+ dateofSeperationDate + "',REMARKS='"
    					+ remarks.trim() + "',gender='" + sex
    					+ "',maritalStatus='" + maritalStatus
    					+ "',permanentAddress='" + permanentAddress
    					+ "',temporatyAddress='" + temporatyAddress
    					+ "',wetherOption='" + wetherOption
    					+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    					+ form2Nomination + "',fhname='" + fhName
    					+ "',setDateOfAnnuation='" + dateOfAnnuation
    					+ "',REFPENSIONNUMBER='" + pensionNumber
    					+ "',otherreason='" + otherReason + "',division='"
    					+ division + "',department='" + department
    					+ "',emailId='" + emailId + "',lastactive='"
    					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    					+ "',empnomineesharable='" + empNomineeSharable
    					+ "',userName='" + bean.getUserName()
    					+ "',fhflag='"+bean.getFhFlag()+"' where empflag='Y' and cpfacno='" + cpfAcNo
    					+ "'  and region='" + region.trim()
    					+ "'  and employeename is null ";
    			} else {
    				query2 = "update  EMPLOYEE_PERSONAL_INFO set airportcode='"
    					+ station + "',employeename='" + empName.trim()
    					+ "',desegnation='" + desegnation
    					+ "',AIRPORTSERIALNUMBER='" + airportSerialNumber
    					+ "',EMPLOYEENO='" + empNumber + "',EMP_LEVEL='"
    					+ empLevel + "',DATEOFBIRTH ='" + dateofBirth
    					+ "',DATEOFJOINING='" + dateofJoining
    					+ "',DATEOFSEPERATION_REASON='" + seperationReason
    					+ "',DATEOFSEPERATION_DATE='"
    					+ dateofSeperationDate + "',REMARKS='"
    					+ remarks.trim() + "',gender='" + sex
    					+ "',maritalStatus='" + maritalStatus
    					+ "',permanentAddress='" + permanentAddress
    					+ "',temporatyAddress='" + temporatyAddress
    					+ "',wetherOption='" + wetherOption
    					+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    					+ form2Nomination + "',fhname='" + fhName
    					+ "',setDateOfAnnuation='" + dateOfAnnuation
    					+ "',REFPENSIONNUMBER='" + pensionNumber
    					+ "',otherreason='" + otherReason + "',division='"
    					+ division + "',department='" + department
    					+ "',emailId='" + emailId + "',lastactive='"
    					+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    					+ "',empnomineesharable='" + empNomineeSharable
    					+ "',userName='" + bean.getUserName()
    					+ "',fhflag='"+bean.getFhFlag()+"'  where empflag='Y' and cpfacno='" + cpfAcNo
    					+ "' and trim(employeename)='" + empOldName.trim()
    					+ "'  and region='" + region.trim() + "'";
    				
    			}
    			log.info("query is " + query2);
    			st.executeUpdate(query2);
    			//EmpMaster Name updated to Transaction
    			String query3="update employee_pension_validate set MASTER_EMPNAME='"+empName.trim()+ "' where  cpfaccno='" + cpfAcNo.trim()+ "' and region='"+region.trim()+"'";
    			st.executeUpdate(query3);
    			log.info("query3 is " + query3);
    			/*
    			 * ResultSet rs = st.executeQuery(query1); if (rs.next()) {
    			 * String updateemployeevalidate = "update
    			 * employee_pension_validate set airportcode='" + station + "'
    			 * where cpfaccno='" + cpfAcNo + "' and employeename='" +
    			 * empName + "'"; log .info("update pensionvalidate " +
    			 * updateemployeevalidate);
    			 * st.executeUpdate(updateemployeevalidate); }
    			 */
    		} else {
    			int foundCPFNO = this.empEditCpfAcno(empName, station,
    					desegnation, newCpfAcno, bean.getNewRegion());
    			//	pensionNumber = checkPensionNumber("", newCpfAcno, empNumber,empName, region.trim(), dateofBirth);
    			
    			System.out.println("foundCPFNO====empEditCpfAcno===="
    					+ foundCPFNO + "new pensionNumber==============="
    					+ pensionNumber);
    			// modified on 14 th
    			if (foundCPFNO == 0) {
    				if (!flag.equals("false")) {
    					sql1 = "update  employee_info  set empflag='N' where cpfacno='"
    						+ cpfAcNo.trim()+ "' and employeename='"+ empName.trim()+ "' and region='"+region.trim()+"'";
    					count = st.executeUpdate(sql1);
    					
    				}
    				String sql2 = "insert into employee_info(cpfacno,srno,airportSerialNumber,employeeNo,employeeName,"
    					+ "desegnation,emp_level,dateofbirth,dateofjoining,dateofseperation_reason,dateofseperation_date,"
    					+ "whether_option_a,whether_option_b,whether_option_no,WHETHER_FORM1_NOM_RECEIVED,remarks,airportcode,"
    					+ "sex,fhname,maritalStatus,permanentAddress,temporatyAddress,wetherOption,otherReason,pensionnumber,division,department,emailId,empnomineesharable,region,recordVerified)"
    					+ " VALUES "
    					+ "('"
    					+ newCpfAcno
    					+ "','"
    					+ srno
    					+ "','"
    					+ airportSerialNumber
    					+ "','"
    					+ empNumber
    					+ "','"
    					+ empName
    					+ "','"
    					+ desegnation
    					+ "','"
    					+ empLevel
    					+ "','"
    					+ dateofBirth
    					+ "','"
    					+ dateofJoining
    					+ "','"
    					+ seperationReason
    					+ "','"
    					+ dateofSeperationDate
    					+ "','"
    					+ whetherOptionA
    					+ "','"
    					+ whetherOptionB
    					+ "','"
    					+ whetherOptionNO
    					+ "','"
    					+ form2Nomination
    					+ "','"
    					+ remarks.trim()
    					+ "','"
    					+ station
    					+ "','"
    					+ sex
    					+ "','"
    					+ fhName
    					+ "','"
    					+ maritalStatus
    					+ "','"
    					+ permanentAddress
    					+ "','"
    					+ temporatyAddress
    					+ "','"
    					+ wetherOption
    					+ "','"
    					+ otherReason
    					+ "','"
    					+ pensionNumber
    					+ "','"
    					+ division
    					+ "','"
    					+ department
    					+ "','"
    					+ emailId
    					+ "','"
    					+ empNomineeSharable
    					+ "','"
    					+ bean.getNewRegion().trim() + "','"+setRecordVerified.trim()+ "' )";
    				log.info("sql one " + sql1);
    				log.info("sql two " + sql2);
    				
    				count = st.executeUpdate(sql2);
    				
    				if (flag.equals("false")) {
    					this.deleteInvalidData(newCpfAcno, cpfAcNo, empName);
    				}
    			} else {
    				throw new InvalidDataException("CPF ACC.NO already Exist in the Selected Region");
    			}
    		}
    		
    		/* for family rows update */
    		familyrows = bean.getFamilyRow();
    		ArrayList familyList = commonUtil.getTheList(familyrows, "***");
    		log.info("listsize is d  " + familyList.size());
    		String tempInfo[] = null;
    		String tempData = "", sql2 = "";
    		String fMemberOldName = "";
    		for (int i = 0; i < familyList.size(); i++) {
    			tempData = familyList.get(i).toString();
    			tempInfo = tempData.split("@");
    			fMemberName = tempInfo[0];
    			
    			if (!tempInfo[1].equals("XXX")) {
    				fDateofBirth = tempInfo[1];
    			} else {
    				fDateofBirth = "";
    			}
    			if (!tempInfo[2].equals("XXX")) {
    				frelation = tempInfo[2];
    			} else {
    				frelation = "";
    			}
    			if (!tempInfo[3].equals("XXX")) {
    				fMemberOldName = tempInfo[3].trim();
    			} else
    				fMemberOldName = "";
    			
    			if (!this.checkFamilyDetails(fMemberOldName, newCpfAcno,"employee_detail").equals("")) {
    				sql2 = "update  employee_family_dtls set familyMemberName='"
    					+ fMemberName
    					+ "',dateofBirth='"
    					+ fDateofBirth
    					+ "',familyRelation='"
    					+ frelation
    					+ "',region='"
    					+ region
    					+ "',srno='"+i+"' where cpfaccno='"
    					+ newCpfAcno
    					+ "' and familyMemberName='"
    					+ fMemberOldName.trim() + "'";
    				
    			} else if (!fMemberName.equals("")) {
    				
    				if (!newCpfAcno.equals(cpfAcNo)) {
    					sql1 = "update  employee_family_dtls  set empflag='N' where cpfaccno='"
    						+ cpfAcNo
    						+ "' and familyMemberName='"
    						+ fMemberName
    						+ "' and region='"
    						+ region.trim() + "'";
    					count = st.executeUpdate(sql1);
    				}
    				sql2 = "insert into employee_family_dtls(cpfaccno,familyMemberName,dateofBirth,familyRelation,region,srno)values"
    					+ "('"
    					+ newCpfAcno
    					+ "','"
    					+ fMemberName
    					+ "','"
    					+ fDateofBirth
    					+ "','"
    					+ frelation
    					+ "','"
    					+ region
    					+ "','"+i+"')";
    			}
    			log.info("sql2 is" + sql2);
    			st.executeUpdate(sql2);
    			/* end of Family rows update */
    		}
    		String sql3 = "";
    		String nomineeName = "", nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", gaurdianAddress = "", nomineeRows = "";
    		String nomineeOldName = "";
    		nomineeRows = bean.getNomineeRow();
    		ArrayList nomineeList = commonUtil.getTheList(nomineeRows, "***");
    		DateValidation dateValidation = new DateValidation();
    		for (int j = 0; j < nomineeList.size(); j++) {
    			tempData = nomineeList.get(j).toString();
    			tempInfo = tempData.split("@");
    			nomineeName = tempInfo[0];
    			System.out.println("tempInfo(updatePensionMaster)" + tempInfo);
    			if (!tempInfo[1].equals("XXX")) {
    				nomineeAddress = tempInfo[1];
    			} else {
    				nomineeAddress = "";
    			}
    			if (!tempInfo[2].equals("XXX")) {
    				nomineeDob = tempInfo[2].toString().trim();
    			} else {
    				nomineeDob = "";
    			}
    			
    			if (!tempInfo[3].equals("XXX")) {
    				nomineeRelation = tempInfo[3];
    			} else {
    				nomineeRelation = "";
    			}
    			if (!tempInfo[4].equals("XXX")) {
    				nameofGuardian = tempInfo[4];
    			} else {
    				nameofGuardian = "";
    			}
    			if (!tempInfo[5].equals("XXX")) {
    				gaurdianAddress = tempInfo[5];
    			} else {
    				gaurdianAddress = "";
    			}
    			if (!tempInfo[6].equals("XXX")) {
    				nomineeOldName = tempInfo[6].trim();
    			} else {
    				nomineeOldName = "";
    			}
    			
    			if (!tempInfo[7].equals("XXX")) {
    				totalShare = tempInfo[7];
    			} else {
    				totalShare = "";
    			}
    			
    			log.info("nomineeDob" + nomineeDob);
    			// nomineeRows=tempInfo[5];
    			if (!this.checkFamilyDetails(nomineeOldName.trim(), newCpfAcno, "")
    					.equals("")) {
    				sql3 = "update employee_nominee_dtls set nomineeName='"
    					+ nomineeName + "',nomineeAddress='"
    					+ nomineeAddress + "',nomineeDob='" + nomineeDob
    					+ "',nomineeRelation='" + nomineeRelation
    					+ "',nameofGuardian='" + nameofGuardian
    					+ "',totalshare='" + totalShare
    					+ "',GAURDIANADDRESS='" + gaurdianAddress
    					+ "' where cpfaccno='" + newCpfAcno
    					+ "' and nomineeName='" + nomineeOldName.trim()
    					+ "'";
    				
    			} else {
    				if (!newCpfAcno.equals(cpfAcNo)) {
    					sql1 = "update  employee_nominee_dtls  set empflag='N' where cpfaccno='"
    						+ cpfAcNo
    						+ "' and nomineeName='"
    						+ nomineeName
    						+ "' and region='" + region.trim() + "'";
    					count = st.executeUpdate(sql1);
    				}
    				sql3 = "insert into employee_nominee_dtls(cpfaccno,nomineeName,nomineeAddress,nomineeDob,nomineeRelation,nameofGuardian,GAURDIANADDRESS,totalshare,region)values('"
    					+ newCpfAcno
    					+ "','"
    					+ nomineeName
    					+ "','"
    					+ nomineeAddress
    					+ "','"
    					+ nomineeDob
    					+ "','"
    					+ nomineeRelation
    					+ "','"
    					+ nameofGuardian
    					+ "','"
    					+ gaurdianAddress
    					+ "','"
    					+ totalShare
    					+ "','"
    					+ region + "')";
    			}
    			log.info("sql3 is" + sql3);
    			st.executeUpdate(sql3);
    		}
    	} catch (SQLException sqle) {
    		log.printStackTrace(sqle);
    		if (sqle.getErrorCode() == 00001) {
    			throw new InvalidDataException("PensionNumber Already Exist");
    		}
    	} catch (Exception e) {
    		log.printStackTrace(e);
    		throw new InvalidDataException(e.getMessage());
    	} finally {
    		commonDB.closeConnection(con, st, null);
    	}
    	log.info("PensionDAO:updatePensionMaster leaving method ");
    	return count;
    }
    public void readACCESSData(String accessData) throws InvalidDataException {
    	log.info("PensionDAO :readACCESSData() entering method");
    	Connection conAccess = null;
    	Statement st = null;
    	ResultSet rs2 = null;
    	String salary = "", cpfAccNum = "", fromdate = "",fromDt="";
    	PensionBean bean = null;
    	try {
    		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    		conAccess = getAccessDBConnection(accessData);
    		log.info("connection is " + conAccess);
    		PensionBean ps = new PensionBean();
    		String query2 = "select salary ,month_year,acno from transaction";
    		log.info("query2 " + query2);
    		st = conAccess.createStatement();
    		rs2 = st.executeQuery(query2);
    		if (rs2.next()) {
    			if (rs2.getString("salary") != null) {
    				salary = rs2.getString("salary");
    			}
    			if (rs2.getString("month_year") != null) {
    				fromdate = rs2.getString("month_year");
    			}
    			if (rs2.getString("acno") != null) {
    				cpfAccNum = rs2.getString("acno");
    			}
    			log.info("salary " + salary + "fromdate " + fromdate
    					+ "cpfAccNum " + cpfAccNum);
    			bean.setCpfAcNo(cpfAccNum);
    			fromDt = commonUtil.converDBToAppFormat(fromdate,"yyyyMM","dd-MMM-yy");
    		}
    		log.info("PensionDAO :readACCESSData() end of while loop");
    	} catch (SQLException e) {
    		log.printStackTrace(e);
    	} catch (Exception e) {
    		log.printStackTrace(e);
    	} finally {
    		log.info("PensionDAO :readACCESSData() finally");
    	}
    	log.info("PensionDAO :readACCESSData() end method");
    }
    
    /** Creates a Connection to a Access Database */
    public static Connection getAccessDBConnection(String filename)
    throws SQLException {
    	// filename = filename.replace('', '/').trim();
    	final String accessDBURLPrefix = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
    	final String accessDBURLSuffix = ";DriverID=22;READONLY=false}";
    	String databaseURL = accessDBURLPrefix + filename + accessDBURLSuffix;
    	return DriverManager.getConnection(databaseURL, "", "");
    }
    public void readXLSPensionData(String xlsData) {
    	log.info("PensionDAO :readXLSPensionData() entering method");
    	log.info("readXLSData");
    	String salary = "";
    	ArrayList xlsDataList = new ArrayList();
    	ArrayList pensionList = new ArrayList();
    	xlsDataList = commonUtil.getTheList(xlsData, "***");
    	String fromDt = "";
    	PensionBean bean = null;
    	String tempInfo[] = null;
    	String tempData = "";
    	for (int i = 0; i < xlsDataList.size(); i++) {
    		bean = new PensionBean();
    		tempData = xlsDataList.get(i).toString();
    		tempInfo = tempData.split("@");
    		try {
    			if (!tempInfo[1].equals("XXX")) {
    				bean.setCpfAcNo(tempInfo[1]);
    			} else {
    				bean.setCpfAcNo(this.getMaxValue());
    			}
    			if (!tempInfo[2].equals("XXX")) {
    				bean.setEmployeeName(tempInfo[2].trim());
    			} else {
    				bean.setEmployeeName("");
    			}
    			if (!tempInfo[3].equals("XXX")) {
    				bean.setDesegnation(tempInfo[3].trim());
    			} else {
    				bean.setDesegnation("");
    			}
    			salary = tempInfo[4];
    			fromDt = commonUtil.converDBToAppFormat(tempInfo[5],"yyyyMM","dd-MMM-yy");
    			this.addPensionRecord(bean, salary, fromDt);
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (InvalidDataException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	}
    	log.info("PensionDAO :readXLSPensionData() leaving method");
    	pensionList.add(bean);
    	
    }
    
    public boolean addPensionRecord(PensionBean bean, String salary,
    		String fromDate) throws SQLException {
    	log.info("PensionDAO :addPensionRecord() entering method");
    	log.info("employeeName " + bean.getEmployeeName() + "desegnation "
    			+ bean.getDesegnation() + salary + "fromdate " + fromDate
    			+ "cpfAccNum  " + bean.getCpfAcNo() + bean.getAirportCode());
    	boolean isaddPensionRecord = false;
    	String cpfacno = "", cpfaccPension = "";
    	Statement st = null;
    	Connection conn = null;
    	DatabaseBean dbBean = new DatabaseBean();
    	String airportCode = "", employeeCode = "", employeeName = "", desegnation = "";
    	try {
    		if (bean.getAirportCode() != null
    				&& (!bean.getAirportCode().equals(""))) {
    			airportCode = bean.getAirportCode();
    		} else {
    			airportCode = "";
    		}
    		employeeCode = bean.getEmployeeCode();
    		employeeName = commonUtil
    		.escapeSingleQuotes(bean.getEmployeeName());
    		desegnation = commonUtil.escapeSingleQuotes(bean.getDesegnation());
    		cpfacno = bean.getCpfAcNo();
    		String foundCPFNO = this.empNameCheck(employeeName, airportCode,
    				desegnation, "CPFACNO");
    		
    		if (foundCPFNO.equals("")) {
    			conn = commonDB.getConnection();
    			st = conn.createStatement();
    			String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation) VALUES "
    				+ "('"
    				+ cpfacno
    				+ "','"
    				+ airportCode
    				+ "','"
    				+ employeeCode
    				+ "','"
    				+ employeeName
    				+ "','"
    				+ desegnation + "'" + ")";
    			// System.out.println(sql);
    			st.execute(sql);
    			isaddPensionRecord = true;
    		}
    		if (!foundCPFNO.equals("") && foundCPFNO != null) {
    			foundCPFNO = foundCPFNO;
    		} else {
    			foundCPFNO = cpfacno;
    		}
    		dbBean.setAirPortCD(airportCode);
    		dbBean.setEmpName(employeeName);
    		dbBean.setCpfACNumber(cpfaccPension);
    		dbBean.setSalary(salary);
    		dbBean.setDesegination(desegnation);
    		dbBean.setFromDt(fromDate);
    		dbBean.setToDt(fromDate);
    		try {
    			int result = 0;
    			String foundSalaryDate = this.checkPensionDuplicate(fromDate,
    					foundCPFNO);
    			if (foundSalaryDate.equals("")) {
    				result = this.insertData(dbBean);
    			}
    			System.out.println("result insert pension Data)" + result);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	} catch (SQLException e) {
    		
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(conn, st, null);
    	}
    	log.info("PensionDAO :addPensionRecord() leaving method");
    	return isaddPensionRecord;
    }
    
    public String checkPensionDuplicate(String fromDate, String cpfaccno) {
    	log.info("PensionDAO :checkPensionDuplicate() entering method ");
    	String foundEmpFlag = "";
    	Statement st = null;
    	Connection con = null;
    	ResultSet rs = null;
    	System.out.println("fromDate" + fromDate + "cpfaccno" + cpfaccno);
    	String query = "select CPFACCNO	 as COLUMNNM from employee_pension_validate where  monthyear='"
    		+ fromDate + "' and cpfaccno='" + cpfaccno + "'";
    	log.info("query is " + query);
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		if (rs.next()) {
    			if (rs.getString("COLUMNNM") != null) {
    				foundEmpFlag = rs.getString("COLUMNNM");
    			}
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	log.info("PensionDAO :checkPensionDuplicate() leaving method");
    	return foundEmpFlag;
    }
    
    private String getEmployeeName(Connection con,String employeeno, String cpfaccno,
    		String region) {
    	log.info("PensionDAO :getEmployeeName() entering method ");
    	String employeeName = "";
    	Statement st = null;
    	ResultSet rs = null;
    	System.out.println("employeeno" + employeeno + "cpfaccno" + cpfaccno);
    	String query = "select EMPLOYEENAME from employee_info where EMPLOYEENO='"
    		+ employeeno
    		+ "' and cpfacno='"
    		+ cpfaccno
    		+ "' AND REGION='"
    		+ region + "'";
    	
    	log.info("query is " + query);
    	try {
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		
    		if (rs.next()) {
    			if (rs.getString("EMPLOYEENAME") != null) {
    				employeeName = rs.getString("EMPLOYEENAME");
    			}
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(null, st, rs);
    	}
    	log.info("PensionDAO :getEmployeeName() leaving method");
    	return employeeName;
    }
    
    public String checkFamilyDetails(String memberName, String cpfaccno,
    		String flag) {
    	
    	log.info("PensionDAO :checkFamilyDetails() entering method ");
    	String foundEmpFlag = "", query = "";
    	Statement st = null;
    	Connection con = null;
    	ResultSet rs = null;
    	if (flag.trim().equals("employee_detail")) {
    		query = "select CPFACCNO  as COLUMNNM from EMPLOYEE_FAMILY_DTLS where  trim(FAMILYMEMBERNAME)=trim('"
    			+ memberName + "') and cpfaccno='" + cpfaccno + "'";
    	} else {
    		query = "select CPFACCNO  as COLUMNNM from EMPLOYEE_NOMINEE_DTLS where  trim(nomineeName)=trim('"
    			+ memberName + "') and cpfaccno='" + cpfaccno + "'";
    	}
    	
    	log.info("query is " + query);
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		if (rs.next()) {
    			if (rs.getString("COLUMNNM") != null) {
    				foundEmpFlag = rs.getString("COLUMNNM");
    			}
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	log.info("PensionDAO :checkFamilyDetails() leaving method");
    	return foundEmpFlag;
    }
    
    public String getPensionNumber(String empName, String dateofBirth,
    		String cpf,boolean flag) {
    	log.info("PensionDao:getPensionNumber() entering method");
    	
    	// TODO Auto-generated method stub
    	String finalName = "", fname = "";
    	SimpleDateFormat fromDate = null;
    	int endIndxName = 0;
    	String quats[] = { "Mrs.", "DR." };
    	String tempName = "", convertedDt = "";
    	//	String uniquenumber = validateCPFAccno(cpf.toCharArray());
    	try {
    		if (dateofBirth.indexOf("-") != -1) {
    			fromDate = new SimpleDateFormat("dd-MMM-yyyy");
    		} else {
    			fromDate = new SimpleDateFormat("dd/MMM/yyyy");
    		}
    		SimpleDateFormat toDate = new SimpleDateFormat("ddMMyy");
    		convertedDt = toDate.format(fromDate.parse(dateofBirth));
    		System.out.println(" convertedDt " + convertedDt);
    		int startIndxName = 0;
    		endIndxName = 1;
    		for (int i = 0; i < quats.length; i++) {
    			if (empName.indexOf(quats[i]) != -1) {
    				tempName = empName.replaceAll(quats[i], "").trim();
    				// tempName=empName.substring(index+1,empName.length());
    				empName = tempName;
    			}
    		}
    		
    		finalName = empName.substring(startIndxName, endIndxName);
    		finalName = empName.substring(startIndxName, endIndxName);
    		if (empName.indexOf(" ") != -1) {
    			endIndxName = empName.lastIndexOf(" ");
    			finalName = finalName + empName.substring(endIndxName).trim();
    		} else if (empName.indexOf(".") != -1) {
    			endIndxName = empName.lastIndexOf(".");
    			finalName = finalName
    			+ empName.substring(endIndxName + 1, empName.length())
    			.trim();
    		} else {
    			finalName = empName.substring(0, 2);
    		}
    		log.info("final name is" + finalName);
    		char[] cArray = finalName.toCharArray();
    		fname = String.valueOf(cArray[0]);
    		fname = fname + String.valueOf(cArray[1]);
    		log.info(empName + " Short Name of " + fname);
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		log.info("Exception is " + e);
    	}
    	log.info("PensionDao:getPensionNumber() leaving method");
    	if(flag==true)
    		return convertedDt + fname + cpf;
    	else return convertedDt + fname;
    }
    public String getPensionNumber(String empName, String dateofBirth,
    		String cpf, String region) {
    	log.info("PensionDao:getPensionNumber() entering method");
    	log.info("PensionDao:getPensionNumber() dateofBirth" + dateofBirth
    			+ "empName" + empName);
    	// TODO Auto-generated method stub
    	String finalName = "", fname = "";
    	SimpleDateFormat fromDate = null;
    	int endIndxName = 0;
    	String quats[] = { "Mrs.", "DR." };
    	String tempName = "", convertedDt = "";
    	String uniquenumber = validateAlphaCPFAccno(cpf.toCharArray());
    	
    	try {
    		if (dateofBirth.indexOf("-") != -1) {
    			fromDate = new SimpleDateFormat("dd-MMM-yyyy");
    		} else {
    			fromDate = new SimpleDateFormat("dd/MMM/yyyy");
    		}
    		SimpleDateFormat toDate = new SimpleDateFormat("ddMMyy");
    		convertedDt = toDate.format(fromDate.parse(dateofBirth));
    		System.out.println(" convertedDt " + convertedDt);
    		
    		int startIndxName = 0, index = 0;
    		endIndxName = 1;
    		for (int i = 0; i < quats.length; i++) {
    			if (empName.indexOf(quats[i]) != -1) {
    				tempName = empName.replaceAll(quats[i], "").trim();
    				// tempName=empName.substring(index+1,empName.length());
    				empName = tempName;
    			}
    		}
    		finalName = empName.substring(startIndxName, endIndxName);
    		finalName = empName.substring(startIndxName, endIndxName);
    		if (empName.indexOf(" ") != -1) {
    			endIndxName = empName.lastIndexOf(" ");
    			finalName = finalName + empName.substring(endIndxName).trim();
    		} else if (empName.indexOf(".") != -1) {
    			endIndxName = empName.lastIndexOf(".");
    			finalName = finalName
    			+ empName.substring(endIndxName + 1, empName.length())
    			.trim();
    		} else {
    			finalName = empName.substring(0, 2);
    		}
    		log.info("final name is" + finalName);
    		char[] cArray = finalName.toCharArray();
    		fname = String.valueOf(cArray[0]);
    		fname = fname + String.valueOf(cArray[1]);
    		log.info(empName + " Short Name of " + fname);
    		
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		log.info("Exception is " + e);
    		
    	}
    	log.info("PensionDao:getPensionNumber() leaving method");
    	return convertedDt + fname + uniquenumber;
    	
    }
    
    
    
    public boolean addFinancialDataRecord(FinacialDataBean bean)
    throws SQLException {
    	log.info("PensionDAO :addFinancialDataRecord() entering method");
    	log.info("employeeName " + bean.getEmployeeName() + "desegnation "
    			+ bean.getDesignation() + "cpfAccNum  " + bean.getCpfAccNo());
    	boolean isaddPensionRecord = false;
    	String cpfacno = "";
    	Statement st = null;
    	Connection conn = null;
    	String airportCode = "", employeeCode = "", employeeName = "", desegnation = "";
    	try {
    		if (bean.getAirportCode() != null
    				&& (!bean.getAirportCode().equals(""))) {
    			airportCode = bean.getAirportCode();
    		} else {
    			airportCode = " ";
    		}
    		employeeName = commonUtil
    		.escapeSingleQuotes(bean.getEmployeeName());
    		desegnation = commonUtil.escapeSingleQuotes(bean.getDesignation());
    		cpfacno = bean.getCpfAccNo();
    		conn = commonDB.getConnection();
    		String foundCPFNO = this.empNameCheck(employeeName, airportCode,
    				desegnation, "CPFACNO");
    		if (foundCPFNO.equals("")) {
    			
    			st = conn.createStatement();
    			String sql = "insert into employee_info(cpfacno,airportCode, employeeno,employeeName,desegnation) VALUES "
    				+ "('"
    				+ cpfacno
    				+ "','"
    				+ airportCode.trim()
    				+ "','"
    				+ employeeCode
    				+ "','"
    				+ employeeName
    				+ "','"
    				+ desegnation + "'" + ")";
    			st.execute(sql);
    			isaddPensionRecord = true;
    		}
    		
    		if (!foundCPFNO.equals("") && foundCPFNO != null) {
    			foundCPFNO = foundCPFNO;
    		} else {
    			foundCPFNO = cpfacno;
    		}
    		try {
    			int result = 0;
    			String foundSalaryDate = this.checkPensionDuplicate(bean
    					.getMonthYear().trim(), foundCPFNO);
    			if (foundSalaryDate.equals("")) {
    				result = this.insertFinacialData(bean);
    			}
    			System.out.println("result insert pension Data)" + result);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		// st.close();
    		// conn.close();
    	}
    	log.info("PensionDAO :addFinancialDataRecord() leaving method");
    	return isaddPensionRecord;
    }
    
    public int insertFinacialData(FinacialDataBean bean) throws Exception {
    	log.info("PensionDAO :insertFinacialData() entering method");
    	int result = 0;
    	Connection con = null;
    	PreparedStatement pst = null;
    	
    	log.info("before employee check" + bean.getAirportCode());
    	String insertSQL = "insert into employee_pension_validate(cpfaccno,monthyear,emoluments,emppfstatuary,"
    		+ "empvpf,empadvrecprincipal,empadvrecinterest,emptotal,aaiconpf,aaiconpension,aaitotal)"
    		+ " values(?,?,?,?,?,?,?,?,?,?,?)";
    	try {
    		con = commonDB.getConnection();
    		String foundCPFNO = empNameCheck(bean.getEmployeeName(), "CHQ",
    				bean.getDesignation(), "CPFACNO");
    		if (!foundCPFNO.equals("")) {
    			int count = this.totalData() + 1;
    			pst = con.prepareStatement(insertSQL);
    			log.info("cpfno is " + foundCPFNO);
    			pst.setString(1, foundCPFNO);
    			pst.setString(2, bean.getMonthYear().trim());
    			pst.setString(3, bean.getEmoluments().trim());
    			pst.setString(4, bean.getEmpPfStatuary());
    			pst.setString(5, bean.getEmpVpf().trim());
    			pst.setString(6, bean.getPrincipal().trim());
    			pst.setString(7, bean.getInterest().trim());
    			pst.setString(8, bean.getEmpTotal().trim());
    			pst.setString(9, bean.getAaiPf().trim());
    			pst.setString(10, bean.getAaiPension().trim());
    			pst.setString(11, bean.getAaiTotal().trim());
    			
    			result = pst.executeUpdate();
    			if (result != 0) {
    				result = count;
    			}
    		}
    		
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		pst.close();
    		// con.close();
    	}
    	log.info("PensionDAO :insertFinacialData() leaving method");
    	return result;
    	
    }
    
    public EmpMasterBean getCpfacnoDetails(String pensionnumber,String cpfacno,String region) {
    	log.info("PensionDAO :getCpfacnoDetails() Entering method");
    	Connection con = null;
    	EmpMasterBean bean = new EmpMasterBean();
    	ResultSet rs = null;
    	try {
    		con = commonDB.getConnection();
    		Statement st = con.createStatement();
    		String sql;
    		log.info("pensionno"+pensionnumber);
    		log.info("cpfacno"+cpfacno);
    		//String sql2= select * from employee_info where ROWID IN(select MIN(ROWID) from employee_info where empflag='Y' AND LOWER(cpfacno) ='1110' AND region='South Region' AND LOWER(employeename)='k.alikoya' group by cpfacno) order by cpfacno  
    		//String sql = "select * from employee_info where cpfacno='"+ cpfacno + "' and region='"+region+"'";
    		if(!pensionnumber.equals(""))
    		{
    			sql = "select * from employee_info where  ROWID IN(select MIN(ROWID) from employee_info where empflag='Y' AND LOWER(empserialnumber) ='"+ pensionnumber + "')";
    		}
    		else
    		{
    			sql = "select * from employee_info where  ROWID IN(select MIN(ROWID) from employee_info where empflag='Y' AND LOWER(cpfacno) ='"+ cpfacno + "'  AND region='"+region+"')";
    		}
    		
    		log.info("sql "+sql);
    		rs = st.executeQuery(sql);
    		while (rs.next()) {
    			if (rs.getString("employeeno") != null) {
    				bean.setEmpNumber(rs.getString("employeeno"));
    			} else {
    				bean.setEmpNumber("");
    			}
    			if (rs.getString("AIRPORTCODE") != null) {
    				bean.setStation(rs.getString("AIRPORTCODE"));
    			} else {
    				bean.setStation("");
    			}
    			if (rs.getString("employeename") != null) {
    				bean.setEmpName(rs.getString("employeename"));
    			} else {
    				bean.setEmpName("");
    			}
    			String desegnation = rs.getString("desegnation");
    			if (rs.getString("desegnation") != null) {
    				bean.setDesegnation(rs.getString("desegnation"));
    			} else {
    				bean.setDesegnation("");
    			}
    			if (rs.getString("empserialnumber") != null) {
    				bean.setPensionNumber(rs.getString("empserialnumber"));
    			} else {
    				bean.setPensionNumber("");
    			}
    			if (rs.getString("cpfacno") != null) {
    				bean.setCpfAcNo(rs.getString("cpfacno"));
    			} else {
    				bean.setCpfAcNo("");
    			}
    			if (rs.getString("region") != null) {
    				bean.setRegion(rs.getString("region"));
    			} else {
    				bean.setRegion("");
    			}
    			//bean.setCpfAcNo(cpfacno);
    			log.info("cpfacno"+bean.getCpfAcNo());
    			log.info("empName"+bean.getEmpName());
    			if (rs.getString("DATEOFBIRTH") != null) {
    				log.info(" dateofbirth is "+rs.getString("DATEOFBIRTH"));
    				String dateofbirth = commonUtil.converDBToAppFormat(rs
    						.getString("DATEOFBIRTH"),"yyyy-MM-dd","yyyy-MM-dd");      
    				if (!dateofbirth.equals("")) {
    					bean.setDateofBirth(dateofbirth);
    				} else {
    					bean.setDateofBirth("");
    				}
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		log.info("error" + e.getMessage());
    	}
    	log.info("PensionDAO :getCpfacnoDetails() Leaving method");
    	return bean;
    	
    }
    
    public void readXlsInvalidData(String xlsInvalidData) {
    	log.info("PensionDAO :readXlsInvalidData() entering method");
    	
    	ArrayList xlsDataList = new ArrayList();
    	ArrayList pensionList = new ArrayList();
    	xlsDataList = commonUtil.getTheList(xlsInvalidData, "***");
    	log.info("list size " + xlsDataList.size());
    	Connection conn = null;
    	EmpMasterBean bean = null;
    	String tempInfo[] = null;
    	String tempData = "";
    	FileWriter fw = null;
    	// cpfAccNum = this.getMaxValue();
    	
    	// bean.setCpfAcNo(cpfAccNum);
    	log.debug("xlsData" + xlsInvalidData);
    	String delimiter = "", xlsEmpName = "", xlsDesignation = "", xlsWhetherOption = "";
    	for (int i = 3; i < xlsDataList.size(); i++) {
    		bean = new EmpMasterBean();
    		tempData = xlsDataList.get(i).toString();
    		tempInfo = tempData.split("@");
    		try {
    			bean.setCpfAcNo(this.getMaxValueOfInvalidData());
    			if (!tempInfo[0].equals("XXX")) {
    				bean.setSrno(tempInfo[0]);
    			} else {
    				bean.setSrno("");
    			}
    			if (!tempInfo[1].equals("XXX")) {
    				bean.setAirportSerialNumber(tempInfo[1]);
    			} else {
    				bean.setAirportSerialNumber("");
    			}
    			if (!tempInfo[2].equals("XXX")) {
    				bean.setEmpNumber(tempInfo[2]);
    			} else {
    				bean.setEmpNumber("");
    			}
    			if (!tempInfo[3].equals("XXX")) {
    				if (tempInfo[3].trim().indexOf("'") != -1) {
    					delimiter = "'";
    					xlsEmpName = StringUtility.replace(
    							tempInfo[3].trim().toCharArray(),
    							delimiter.toCharArray(), "").toString();
    					bean.setEmpName(xlsEmpName.trim());
    				} else {
    					bean.setEmpName(tempInfo[3].trim());
    				}
    			} else {
    				bean.setEmpName("");
    			}
    			if (!tempInfo[4].equals("XXX")) {
    				if (tempInfo[4].trim().indexOf("'") != -1) {
    					delimiter = "'";
    					xlsDesignation = StringUtility.replace(
    							tempInfo[4].trim().toCharArray(),
    							delimiter.toCharArray(), "").toString();
    					
    					bean.setDesegnation(xlsDesignation);
    				} else {
    					bean.setDesegnation(tempInfo[4].trim());
    				}
    				
    			} else {
    				bean.setDesegnation("");
    			}
    			log.info("Employee(readXLSData) " + bean.getEmpName()
    					+ "getDesegnation" + bean.getDesegnation());
    			if (!tempInfo[5].equals("XXX")) {
    				bean.setEmpLevel(tempInfo[5].trim());
    			} else {
    				bean.setEmpLevel("");
    			}
    			if (!tempInfo[6].equals("XXX")) {
    				bean.setCpf(tempInfo[6]);
    			} else {
    				bean.setCpf("");
    			}
    			if (!tempInfo[7].equals("XXX")) {
    				bean.setDateofBirth(tempInfo[7]);
    			} else {
    				bean.setDateofBirth("");
    			}
    			if (!tempInfo[8].equals("XXX")) {
    				bean.setDateofJoining(tempInfo[8]);
    			} else {
    				bean.setDateofJoining("");
    			}
    			
    			if (!tempInfo[9].equals("XXX")) {
    				bean.setSeperationReason(tempInfo[9]);
    			} else {
    				bean.setSeperationReason("");
    			}
    			if (!tempInfo[10].equals("XXX") && tempInfo[10].length() > 8) {
    				bean.setDateofSeperationDate(tempInfo[10]);
    			} else {
    				bean.setDateofSeperationDate("");
    			}
    			
    			log.info("DateofBirth" + bean.getDateofBirth()
    					+ "DateofJoining" + bean.getDateofJoining()
    					+ "SeperationDate" + bean.getDateofSeperationDate());
    			if (!tempInfo[11].equals("XXX")) {
    				if (tempInfo[11].indexOf("*") != -1) {
    					delimiter = "*";
    					xlsWhetherOption = StringUtility.replace(
    							tempInfo[11].trim().toCharArray(),
    							delimiter.toCharArray(), "").toString();
    					bean.setWhetherOptionA(xlsWhetherOption);
    				} else {
    					bean.setWhetherOptionA(tempInfo[11]);
    				}
    				
    			} else {
    				bean.setWhetherOptionA("");
    			}
    			if (!tempInfo[12].equals("XXX")) {
    				bean.setWhetherOptionB(tempInfo[12]);
    			} else {
    				bean.setWhetherOptionB("");
    			}
    			if (!tempInfo[13].equals("XXX")) {
    				bean.setWhetherOptionNO(tempInfo[13]);
    			} else {
    				bean.setWhetherOptionNO("");
    			}
    			if (!tempInfo[14].equals("XXX")) {
    				bean.setForm2Nomination(tempInfo[14]);
    			} else {
    				bean.setForm2Nomination("");
    			}
    			if (!tempInfo[16].equals("XXX")) {
    				bean.setRemarks(tempInfo[16]);
    			} else {
    				bean.setRemarks("");
    			}
    			if (!tempInfo[17].equals("XXX")) {
    				bean.setStation(tempInfo[17].trim());
    			} else {
    				bean.setStation("");
    			}
    			if (!bean.getWhetherOptionA().trim().equals("")) {
    				bean.setWetherOption("A");
    			} else if (!bean.getWhetherOptionB().trim().equals("")) {
    				bean.setWetherOption("B");
    			} else if (!bean.getWhetherOptionNO().trim().equals("")) {
    				bean.setWetherOption("No");
    			} else {
    				bean.setWetherOption("");
    			}
    			this.addPensionRecordJunkData(bean);
    			
    		} catch (SQLException e) {
    			log.printStackTrace(e);
    			
    		} catch (Exception e) {
    			log.printStackTrace(e);
    		}
    	}
    	log.info("PensionDAO :readXlsInvalidData() leaving method");
    	pensionList.add(bean);
    }
    
    public boolean addPensionRecordJunkData(EmpMasterBean bean)
    throws SQLException {
    	log.info("PensionDAO :addPensionRecordJunkData() entering method");
    	boolean isaddPensionRecord = false;
    	Connection conn = null;
    	Statement st = null;
    	// PreparedStatement ps=null;
    	String srno = "", airportSerialNumber = "", empNumber = "", cpfAcNo = "";
    	String empName = "", desegnation = "", empLevel = "";
    	String cpf = "", sex = "", maritalStatus = "", fhName = "", permanentAddress = "", temporatyAddress = "";
    	String seperationReason = "", whetherOptionA = "";
    	String whetherOptionB = "", whetherOptionNO = "", form2Nomination = "";
    	String remarks = "", station = "";
    	String dateofBirth = "", dateofJoining = "", dateofSeperationDate = "", pensionNumber = "";
    	String wetherOption = "", dateOfAnnuation = "", otherReason = "";
    	try {
    		if (bean.getStation() != null && (!bean.getStation().equals("XXX"))) {
    			station = bean.getStation();
    		} else {
    			station = "CHQ";
    		}
    		cpfAcNo = bean.getCpfAcNo().trim();
    		srno = bean.getSrno();
    		airportSerialNumber = bean.getAirportSerialNumber();
    		empNumber = bean.getEmpNumber();
    		empName = bean.getEmpName();
    		desegnation = bean.getDesegnation();
    		empLevel = bean.getEmpLevel();
    		dateofBirth = bean.getDateofBirth();
    		pensionNumber = "";
    		dateofJoining = bean.getDateofJoining();
    		seperationReason = bean.getSeperationReason();
    		dateofSeperationDate = bean.getDateofSeperationDate();
    		log.info("dateofBirth" + dateofBirth + "dateofJoining"
    				+ dateofJoining + "dateofSeperationDate"
    				+ dateofSeperationDate);
    		whetherOptionA = bean.getWhetherOptionA();
    		whetherOptionB = bean.getWhetherOptionB();
    		whetherOptionNO = bean.getWhetherOptionNO();
    		form2Nomination = bean.getForm2Nomination();
    		remarks = bean.getRemarks();
    		
    		wetherOption = bean.getWetherOption();
    		sex = bean.getSex();
    		maritalStatus = bean.getMaritalStatus();
    		fhName = bean.getFhName();
    		permanentAddress = bean.getPermanentAddress();
    		temporatyAddress = bean.getTemporatyAddress();
    		dateOfAnnuation = "";
    		otherReason = bean.getOtherReason();
    		station = bean.getStation();
    		String foundCPFNO = this.empNameCheck(empName, station,
    				desegnation, "");
    		conn = commonDB.getConnection();
    		if (foundCPFNO.equals("")) {
    			
    			st = conn.createStatement();
    			String sql = "insert into employee_info_invalidData(cpfacno,pensionnumber,srno,airportSerialNumber,employeeNo,employeeName,"
    				+ "desegnation,emp_level,cpf,dateofbirth,dateofjoining,dateofseperation_reason,dateofseperation_date,"
    				+ "whether_option_a,whether_option_b,whether_option_no,WHETHER_FORM1_NOM_RECEIVED,remarks,airportcode,sex,fhname,maritalStatus,permanentAddress,temporatyAddress,wetherOption,setDateOfAnnuation,otherreason)"
    				+ " VALUES "
    				+ "('"
    				+ cpfAcNo
    				+ "','"
    				+ pensionNumber
    				+ "','"
    				+ srno
    				+ "','"
    				+ airportSerialNumber
    				+ "','"
    				+ empNumber
    				+ "','"
    				+ empName
    				+ "','"
    				+ desegnation
    				+ "','"
    				+ empLevel
    				+ "','"
    				+ cpf
    				+ "','"
    				+ dateofBirth
    				+ "','"
    				+ dateofJoining
    				+ "','"
    				+ seperationReason
    				+ "','"
    				+ dateofSeperationDate
    				+ "','"
    				+ whetherOptionA
    				+ "','"
    				+ whetherOptionB
    				+ "','"
    				+ whetherOptionNO
    				+ "','"
    				+ form2Nomination
    				+ "','"
    				+ remarks
    				+ "','"
    				+ station
    				+ "','"
    				+ sex
    				+ "','"
    				+ fhName
    				+ "','"
    				+ maritalStatus
    				+ "','"
    				+ permanentAddress
    				+ "','"
    				+ temporatyAddress
    				+ "','"
    				+ wetherOption
    				+ "','"
    				+ dateOfAnnuation + "','" + otherReason + "')";
    			log.info(sql);
    			st.execute(sql);
    			
    			isaddPensionRecord = true;
    		}
    		
    	} catch (SQLException e) {
    		log.printStackTrace(e);
    		throw new SQLException();
    		// e.printStackTrace();
    	} catch (Exception e) {
    		log.printStackTrace(e);
    		// e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(conn, st, null);
    	}
    	log.info("PensionDAO :addPensionRecordJunkData() leaving method");
    	return isaddPensionRecord;
    }
    
    public String getMaxValueOfInvalidData() throws SQLException {
    	int maxValue = 0;
    	String finalCPFacno = "";
    	String cpfacno = "";
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	try {
    		con = commonDB.getConnection();
    		log.info("con(getMaxValue) " + con);
    		st = con.createStatement();
    		String maxcpfacno = "select max(cpfacno) as cpfacno from employee_info_invalidData";
    		rs = st.executeQuery(maxcpfacno);
    		if (rs.next()) {
    			if (rs.getString("cpfacno") != null) {
    				cpfacno = rs.getString("cpfacno");
    				if (cpfacno.length() == 8) {
    					maxValue = Integer.parseInt(cpfacno.substring(3,
    							cpfacno.length()));
    					maxValue = maxValue + 1;
    					finalCPFacno = "CPF" + maxValue;
    				}
    				
    			} else {
    				maxValue = 10000;
    				finalCPFacno = "CPF" + maxValue;
    			}
    		}
    		log.info("CPF ACCNO(getMaxValue())==" + finalCPFacno);
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		log.printStackTrace(e);
    		throw new SQLException();
    		// e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		log.printStackTrace(e);
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	
    	return finalCPFacno;
    }
    
    public void deleteInvalidData(String newCPFAccno, String oldCPFAccno,
    		String empName) throws SQLException {
    	int maxValue = 0;
    	String dltInvalidEmpData = "";
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	try {
    		if (chckInvalidData(newCPFAccno, "") != 0) {
    			con = commonDB.getConnection();
    			
    			st = con.createStatement();
    			dltInvalidEmpData = "DELETE FROM employee_info_invalidData WHERE CPFACNO='"
    				+ oldCPFAccno + "' and employeename='" + empName + "'";
    			st.executeUpdate(dltInvalidEmpData);
    		}
    		
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		log.printStackTrace(e);
    		throw new SQLException();
    		// e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		log.printStackTrace(e);
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    }
    
    public int chckInvalidData(String newCPFAccno, String region)
    throws InvalidDataException {
    	int count = 0;
    	
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	try {
    		con = commonDB.getConnection();
    		log.info("con(getMaxValue) " + con);
    		st = con.createStatement();
    		String chkDtEmpInfo = "select count(*) as count from employee_info where cpfacno='"
    			+ newCPFAccno + "'and region='" + region + "' ";
    		rs = st.executeQuery(chkDtEmpInfo);
    		if (rs.next()) {
    			
    			if (rs.getInt("count") != 0) {
    				count = Integer.parseInt(rs.getString("count"));
    			}
    		}
    		log.info("chckInvalidData====count" + count);
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		log.printStackTrace(e);
    		throw new InvalidDataException(e.getMessage());
    		// e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		log.printStackTrace(e);
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	return count;
    }
    
    public EmpMasterBean getDesegnation(String empLevel) {
    	Connection con = null;
    	EmpMasterBean bean = new EmpMasterBean();
    	ResultSet rs = null;
    	String designation = "";
    	try {
    		con = commonDB.getConnection();
    		Statement st = con.createStatement();
    		String sql = "select * from employee_desegnation where empLevel='"
    			+ empLevel.trim() + "'";
    		rs = st.executeQuery(sql);
    		while (rs.next()) {
    			designation = (String) rs.getString("DESIGNATION").toString()
    			.trim();
    			if (!designation.equals("")) {
    				bean.setDesegnation(designation);
    			} else {
    				bean.setDesegnation("");
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		log.info("error" + e.getMessage());
    		
    	}
    	return bean;
    }
    
    public ArrayList getAirports() {
    	Connection con = null;
    	ArrayList airportList = new ArrayList();
    	
    	ResultSet rs = null;
    	String unitName = "", unitCode = "";
    	
    	try {
    		con = commonDB.getConnection();
    		Statement st = con.createStatement();
    		String sql = "select * from employee_unit_master  order by unitname ";
    		rs = st.executeQuery(sql);
    		while (rs.next()) {
    			EmpMasterBean bean = new EmpMasterBean();
    			unitName = (String) rs.getString("unitname").toString().trim();
    			if (!unitName.equals("")) {
    				bean.setStation(unitName.toUpperCase());
    			} else {
    				bean.setStation("");
    			}
    			unitCode = (String) rs.getString("unitcode").toString().trim();
    			if (!unitCode.equals("")) {
    				bean.setUnitCode(unitCode.toUpperCase());
    			} else {
    				bean.setUnitCode("");
    			}
    			airportList.add(bean);
    			// bean=null;
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		log.info("error" + e.getMessage());
    	}
    	return airportList;
    }
    
    public ArrayList getAirports(String region) {
    	log.info("PensionDAO :getAirports() Entering Method ");
    	Connection con = null;
    	ArrayList airportList = new ArrayList();
    	EmpMasterBean bean = null;
    	ResultSet rs = null;
    	String unitName = "", unitCode = "";
    	try {
    		con = commonDB.getConnection();
    		Statement st = con.createStatement();
    		String sql = "select * from employee_unit_master where region='"
    			+ region + "' order by unitname ";
    		log.info("sql " + sql);
    		rs = st.executeQuery(sql);
    		while (rs.next()) {
    			bean = new EmpMasterBean();
    			unitName = (String) rs.getString("unitname").toString().trim();
    			if (!unitName.equals("")) {
    				bean.setStation(unitName.trim());
    			} else {
    				bean.setStation("");
    			}
    			unitCode = (String) rs.getString("unitcode").toString().trim();
    			if (!unitCode.equals("")) {
    				bean.setUnitCode(unitCode.toUpperCase());
    			} else {
    				bean.setUnitCode("");
    			}
    			
    			airportList.add(bean);
    			// bean=null;
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		log.printStackTrace(e);
    		log.info("error" + e.getMessage());
    	}
    	log.info("PensionDAO :getAirports() Leaving Method ");
    	return airportList;
    }
    
    public ArrayList getDepartmentList() {
    	log.info("PensionDAO :getDepartmentList() Entering Method ");
    	Connection con = null;
    	ArrayList departmentList = new ArrayList();
    	ResultSet rs = null;
    	String departmentName = "";
    	try {
    		con = commonDB.getConnection();
    		Statement st = con.createStatement();
    		String sql = "select * from employee_department";
    		rs = st.executeQuery(sql);
    		while (rs.next()) {
    			EmpMasterBean bean = new EmpMasterBean();
    			departmentName = (String) rs.getString("departmentname")
    			.toString().trim();
    			if (!departmentName.equals("")) {
    				bean.setDepartment(departmentName);
    			} else {
    				bean.setDepartment("");
    			}
    			departmentList.add(bean);
    			// bean=null;
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		log.info("error" + e.getMessage());
    	}
    	log.info("PensionDAO :getDepartmentList() Leaving Method ");
    	return departmentList;
    }
    
    private String validateCPFAccno(char[] frmtDt) {
    	StringBuffer buff = new StringBuffer();
    	StringBuffer digitBuff = new StringBuffer();
    	for (int i = 0; i < frmtDt.length; i++) {
    		char c = frmtDt[i];
    		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
    			buff.append(c);
    		} else if (c >= '0' && c <= '9') {
    			digitBuff.append(c);
    		}
    	}
    	String validDt = digitBuff.toString();
    	log.info("cpfacno " + validDt);
    	return validDt;
    }
    
    private String validateAlphaCPFAccno(char[] frmtDt) {
    	StringBuffer buff = new StringBuffer();
    	StringBuffer digitBuff = new StringBuffer();
    	for (int i = 0; i < frmtDt.length; i++) {
    		char c = frmtDt[i];
    		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
    			buff.append(c);
    		} else if (c >= '0' && c <= '9') {
    			buff.append(c);
    		} else if (c >= '-') {
    			digitBuff.append(c);
    		}
    		
    	}
    	String validDt = buff.toString();
    	log.info("cpfacno " + validDt);
    	return validDt;
    }
    
    public int empEditCpfAcno(String employeeName, String airportCode,
    		String desegnation, String newCpfAcno, String region)
    throws InvalidDataException {
    	log.info("PensionDAO :empEditCpfAcno() entering method ");
    	int countEmpInfo = 0;
    	Statement st = null;
    	Connection con = null;
    	ResultSet rs = null;
    	String query = "";
    	query = "select count(*) as count from employee_info where cpfacno='"+newCpfAcno.trim()+ "' and region='"+region.trim()+"' and empflag='Y'";
    	log.info(query);
    	try {
    		con = commonDB.getConnection();
    		log.info("con(empNameCheck)" + con);
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		if (rs.next()) {
    			if (rs.getInt("count") != 0) {
    				countEmpInfo = rs.getInt("count");
    			}
    			System.out.println("foundEmpFlag   " + rs.getInt("count"));
    		}
    	} catch (SQLException e) {
    		log.printStackTrace(e);
    		throw new InvalidDataException(e.getMessage());
    	} catch (Exception e) {
    		throw new InvalidDataException(e.getMessage());
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	
    	log.info("PensionDAO :empEditCpfAcno() leaving method");
    	return countEmpInfo;
    }
    
    public void deletePensionMaster(String cpfacno, String empName,
    		boolean flag, String empCode, String region, String airportCode)
    throws Exception {
    	log.info("PensionDAO:deletePensionMaster() entering method ");
    	EmpMasterBean editBean = new EmpMasterBean();
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	String query = "", query1 = "", query2 = "", query3 = "";
    	if (flag == true) {
    		if (!cpfacno.equals("")) {
    			// query = "delete from employee_info where cpfacno='" +
    			// cpfacno+ "' AND LOWER(employeename)='" +
    			// empName.toLowerCase()+ "'";
    			query = "update  employee_info set empflag='N' where cpfacno='"
    				+ cpfacno + "' AND LOWER(employeename)='"
    				+ empName.toLowerCase() + "' and airportCode='"
    				+ airportCode + "' and region='" + region + "'";
    			query1 = "delete  from EMPLOYEE_FAMILY_DTLS where cpfaccno='"
    				+ cpfacno + "'";
    			query2 = "delete  from EMPLOYEE_NOMINEE_DTLS where cpfaccno='"
    				+ cpfacno + "'";
    			query3 = "update  employee_info_invaliddata set empflag='N' where cpfacno='"
    				+ cpfacno
    				+ "'AND LOWER(employeename)='"
    				+ empName.toLowerCase() + "'";
    		}
    		
    	} else {
    		query = "select * from employee_info_invaliddata where cpfacno='"
    			+ cpfacno + "' AND LOWER(employeename)='"
    			+ empName.toLowerCase() + "'";
    	}
    	log.info("query is " + query);
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		st.addBatch(query);
    		st.addBatch(query1);
    		st.addBatch(query2);
    		st.addBatch(query3);
    		int deleteCount[] = st.executeBatch();
    		log.info("count is " + deleteCount);
    	} catch (Exception e) {
    		log.printStackTrace(e);
    	}
    	finally {
    		// rs.close();
    		st.close();
    		con.close();
    	}
    	log.info("PensionDAO:deletePensionMaster() leaving method ");
    	// return editBean;
    }
    
    public int getSequenceNumber(String empName, String dateofBirth,
    		boolean flag) {
    	log.info("PensionDAO :getSequenceNumber() Entering Method ");
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs=null;
    	int sequenceNo = 0;
    	String pensionSequenceQuery = "";
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		if (flag) {
    			if (empName != "" && dateofBirth != "") {
    				pensionSequenceQuery = " select EMPSERIALNUMBER  from employee_info where lower(employeename)=lower('"
    					+ empName+ "') and dateofbirth='" + dateofBirth.trim() + "'"; 
    				ResultSet rs1 = st.executeQuery(pensionSequenceQuery);
    				log.info("pensionSequenceQuery" +pensionSequenceQuery);
    				if (rs1.next()) {
    					log.info("getint one "+rs1.getString("EMPSERIALNUMBER"));
    					if (rs1.getInt("EMPSERIALNUMBER")!=0) {
    						
    						sequenceNo = rs1.getInt("EMPSERIALNUMBER");
    						// set false
    						/*   String updateEmpSerailNumberEmpty = "update employee_info set EMPSERIALNUMBER="
    						 + sequenceNo
    						 + ",pensionCheck='false' where employeename='"
    						 + empName
    						 + "' and dateofbirth='"
    						 + dateofBirth.trim()
    						 + "' and EMPSERIALNUMBER='"
    						 + sequenceNo + "' and region='CHQNAD'"; */
    						
    						String updateEmpSerailNumberEmpty = "update employee_info set EMPSERIALNUMBER="
    							+ sequenceNo
    							+ ",pensionCheck='false' where employeename='"
    							+ empName
    							+ "' and dateofbirth='"
    							+ dateofBirth.trim()
    							+ "' and EMPSERIALNUMBER='"
    							+ sequenceNo + "' ";
    						log.info("updateEmpSerailNumberEmpty "
    								+ updateEmpSerailNumberEmpty);
    						st.executeUpdate(updateEmpSerailNumberEmpty);
    						
    					}
    					else {
    						//    pensionSequenceQuery = "select max(EMPSERIALNUMBER)+1 as sequenceNo   from employee_info";
    						pensionSequenceQuery = "select pensionno.nextval from dual";
    						rs = st.executeQuery(pensionSequenceQuery);
    						//  log.info("pensionsequence query " + pensionSequenceQuery);
    						if (rs.next()) {
    							sequenceNo = rs.getInt(1);
    						}
    					}
    				} 
    				else {
    					//    pensionSequenceQuery = "select max(EMPSERIALNUMBER)+1 as sequenceNo   from employee_info";
    					pensionSequenceQuery = "select pensionno.nextval from dual";
    					rs = st.executeQuery(pensionSequenceQuery);
    					//  log.info("pensionsequence query " + pensionSequenceQuery);
    					if (rs.next()) {
    						sequenceNo = rs.getInt(1);
    					}
    				}
    				log.info("pensionsequence query " + pensionSequenceQuery);
    			}
    		} else {
    			pensionSequenceQuery = "select pensionno.nextval from dual";
    			log.info("pensionsequence query " + pensionSequenceQuery);
    			rs = st.executeQuery(pensionSequenceQuery);
    			if (rs.next()) {
    				sequenceNo = rs.getInt(1);
    			}
    		}
    		
    	} catch (Exception e) {
    		log.printStackTrace(e);
    		System.out.println("error is " + e.getMessage());
    	}
    	return sequenceNo;
    }
    public void updateUniquePensionNumber(int sequenceNumber, String cpfacno,
    		String empName, boolean flag, String empCode, String region,
    		String airportCode,String dateofBirth,String pensionNumber) throws Exception {
    	
    	log.info("PensionDAO:updateUniquePensionNumber() entering method ");
    	
    	Connection con = null;
    	Statement st = null;
    	String query = "",query2="";
    	String newPensionNumber="";
    	
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		if (!cpfacno.equals("")&& !empCode.equals("")) {
    			query = "update  employee_info set employeeName='" + empName
    			+ "',empserialNumber='" + sequenceNumber
    			+ "',PENSIONNUMBER='"+pensionNumber+"' where cpfacno='" + cpfacno
    			+ "'  and airportCode='" + airportCode
    			+ "' and region='" + region + "' and EMPLOYEENO='"+empCode+"'";
    			
    			//  st.executeUpdate(query);
    		}else{
    			query = "update  employee_info set employeeName='" + empName
    			+ "',empserialNumber='" + sequenceNumber+ "',PENSIONNUMBER='"+pensionNumber+"' where cpfacno='" + cpfacno
    			+ "'  and airportCode='" + airportCode
    			+ "' and region='" + region + "' ";
    		}
    		String query1="update employee_personal_info set mapingflag='Y' WHERE PENSIONNO='"+sequenceNumber+"'";
    		
    		st.addBatch(query);
    		st.addBatch(query1);
    		log.info("update query "+query);
    		int count[] =st.executeBatch();
    		log.info("count is "+count.length);
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    		
    		throw ex;
    	} 
    	catch (EPISException UTE) {
    		log.printStackTrace(UTE);
    		throw new ApplicationActionException("1000",commonUtil.getExceptionMessage("100")+UTE.getMessage());
    		
    	} 
    	catch (Exception e) {  throw e; }
    	
    	
    	finally {
    		st.close();
    		con.close();
    	}
    	
    	log.info("PensionDAO:updateUniquePensionNumber() leaving method ");
    	
    	
    }
    
    public String  generateUniquePensionNumber(int sequenceNumber, String cpfacno,
    		String empName, boolean flag, String empCode, String region,
    		String airportCode,String dateofBirth) throws Exception {
    	log.info("PensionDAO:generateUniquePensionNumber() entering method ");
    	EmpMasterBean editBean = new EmpMasterBean();
    	Connection con = null;
    	Statement st = null;
    	String query = "",query2="";
    	String newPensionNumber="";
    	newPensionNumber = this.getPensionNumber(empName, dateofBirth, cpfacno,false);
    	newPensionNumber=newPensionNumber+""+sequenceNumber;
    	
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		if (!cpfacno.equals("")) {
    			query = "update  employee_info set employeeName='" + empName
    			+ "',empserialNumber='" + sequenceNumber
    			+ "',PENSIONNUMBER='"+newPensionNumber+"' where cpfacno='" + cpfacno
    			+ "'  and airportCode='" + airportCode
    			+ "' and region='" + region + "' ";
    			
    			st.addBatch(query);
    			log.info("update query "+query);
    			
    			int count[] =st.executeBatch();
    			log.info("count is "+count.length);
    			//	st.executeUpdate(query);
    		}
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    		
    		throw ex;
    	} 
    	catch (EPISException UTE) {
    		log.printStackTrace(UTE);
    		throw new ApplicationActionException("1000",commonUtil.getExceptionMessage("100")+UTE.getMessage());
    		
    	} 
    	catch (Exception e) {  throw e; }
    	
    	
    	finally {
    		st.close();
    		con.close();
    	}
    	
    	log.info("PensionDAO:generateUniquePensionNumber() leaving method ");
    	return newPensionNumber;
    }
    
    public ArrayList searchPensionDataMissingMonth(DatabaseBean dbBean,
    		int start, int gridLength) throws Exception {
    	log.info("PensionDAO :searchPensionDataMissingMonth() entering method");
    	ArrayList hindiData = new ArrayList();
    	DatabaseBean data = null;
    	Connection con = null;
    	log.info("start" + start + "end" + gridLength);
    	String sqlQuery = this.buildQueryForMissingMonth(dbBean);
    	int countGridLength = 0;
    	System.out.println("Query is(retriveByAll)" + sqlQuery);
    	PreparedStatement pst = null;
    	ResultSet rs = null;
    	try {
    		con = commonDB.getConnection();
    		pst = con
    		.prepareStatement(sqlQuery,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);
    		rs = pst.executeQuery();
    		if (rs.next()) {
    			hindiData = new ArrayList();
    			if (rs.absolute(start)) {
    				countGridLength++;
    				data = new DatabaseBean();
    				if (rs.getString("employeename") != null) {
    					data.setEmpName(rs.getString("employeename"));
    				} else {
    					data.setEmpName("");
    				}
    				System.out.println("Name" + rs.getString("NAME"));
    				if (rs.getDate("FROMDATE") != null) {
    					data.setFromDt(commonUtil.converDBToAppFormat(rs
    							.getDate("FROMDATE")));
    				} else {
    					data.setFromDt("");
    				}
    				if (rs.getString("TODATE") != null) {
    					data.setToDt(commonUtil.converDBToAppFormat(rs
    							.getDate("TODATE")));
    				} else {
    					data.setToDt("");
    				}
    				hindiData.add(data);
    			}
    			while (rs.next() && countGridLength < gridLength) {
    				data = new DatabaseBean();
    				countGridLength++;
    				if (rs.getString("CODE") != null) {
    					data.setAirPortCD(rs.getString("CODE"));
    				} else {
    					data.setAirPortCD("");
    				}
    				if (rs.getString("NAME") != null) {
    					data.setEmpName(rs.getString("NAME"));
    				} else {
    					data.setEmpName("");
    				}
    				log.info("Name" + rs.getString("NAME"));
    				if (rs.getString("DESEG") != null) {
    					data.setDesegination(rs.getString("DESEG"));
    				} else {
    					data.setDesegination("");
    				}
    				if (rs.getString("SALPF") != null) {
    					data.setSalary(Integer.toString(rs.getInt("SALPF")));
    				} else {
    					data.setSalary("");
    				}
    				if (rs.getString("PENSIONFUND") != null) {
    					data.setPensionFund(Integer.toString(rs
    							.getInt("PENSIONFUND")));
    				} else {
    					data.setPensionFund("");
    				}
    				if (rs.getString("PF") != null) {
    					data.setPf(Integer.toString(rs.getInt("PF")));
    				} else {
    					data.setPf("");
    				}
    				if (rs.getString("FROMDATE") != null) {
    					data.setFromDt(commonUtil.converDBToAppFormat(rs
    							.getDate("FROMDATE")));
    				} else {
    					data.setFromDt("");
    				}
    				if (rs.getString("TODATE") != null) {
    					data.setToDt(commonUtil.converDBToAppFormat(rs
    							.getDate("TODATE")));
    				} else {
    					data.setToDt("");
    				}
    				log
    				.info("PensionDAO :searchPensionDataMissingMonth() Leaving method");
    				hindiData.add(data);
    			}
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		pst.close();
    		rs.close();
    		con.close();
    	}
    	return hindiData;
    }
    
    public String buildQueryForMissingMonth(DatabaseBean bean) throws Exception {
    	log.info("PensionDAO :buildQueryForMissingMonth() entering method");
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "", prefixWhereClause = "", fromDate = "", toDate = "";
    	if (bean.getFromDt() != "") {
    		fromDate = commonUtil.converDBToAppFormat(bean.getFromDt(),"dd/MM/yyyy","dd-MMM-yyyy");
    	}
    	if (bean.getToDt() != "") {
    		toDate = commonUtil.converDBToAppFormat(bean.getToDt(),"dd/MM/yyyy","dd-MMM-yyyy");
    	}
    	String sqlQuery = "select emp.employeename, joinrev(cursor(select s.mon from (select distinct to_char(MONTHYEAR,'MON') mon from  employee_pension_validate) s where s.mon not in (select distinct to_char(MONTHYEAR,'MON') from employee_pension_validate e  where e.employeename  =emp.employeename and MONTHYEAR between '"
    		+ fromDate
    		+ "' and '"
    		+ toDate
    		+ "') ),',') penidngmonths from employee_info emp where LOWER(emp.EMPLOYEENAME) like'"
    		+ bean.getEmpName().toLowerCase() + "'";
    	System.out.println("Employee" + bean.getEmpName() + "To Date"
    			+ bean.getToDt() + "From Date" + bean.getFromDt());
    	dynamicQuery = sqlQuery.toString();
    	System.out.println("Query " + dynamicQuery);
    	log.info("PensionDAO :buildQueryForMissingMonth() leaving method");
    	return dynamicQuery;
    }
    
    public String buildQuery1(DatabaseBean bean) throws Exception {
    	log.info("PensionDAO :buildQuery1() entering method");
    	StringBuffer whereClause = new StringBuffer();
    	String fromDate = "", toDate = "";
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "", prefixWhereClause = "";
    	if (bean.getFromDt() != "") {
    		fromDate = commonUtil.converDBToAppFormat(bean.getFromDt(),"dd/MM/yyyy","dd-MMM-yyyy");
    	}
    	if (bean.getToDt() != "") {
    		toDate = commonUtil.converDBToAppFormat(bean.getToDt(),"dd/MM/yyyy","dd-MMM-yyyy");
    	}
    	String sqlQuery = "select emp.employeename, joinrev(cursor(select s.mon from (select distinct to_char(MONTHYEAR,'MON') mon from  employee_pension_validate) s where s.mon not in (select distinct to_char(MONTHYEAR,'MON') from employee_pension_validate e  where e.employeename  =emp.employeename and MONTHYEAR between '"
    		+ fromDate
    		+ "' and '"
    		+ toDate
    		+ "') ),',') penidngmonths from employee_info emp where LOWER(emp.EMPLOYEENAME) like'"
    		+ bean.getEmpName().toLowerCase() + "'";
    	
    	/*
    	 * String orderBy="ORDER BY ei.CPFACCNO"; query.append(orderBy);
    	 */
    	dynamicQuery = sqlQuery.toString();
    	System.out.println("Query " + dynamicQuery);
    	log.info("PensionDAO :buildQuery1() leaving method");
    	
    	return dynamicQuery;
    }
    
    private boolean checkEmployeeName(ArrayList tempList, String employeeName) {
    	boolean flag = false;
    	log.info("checkEmployeeName================" + tempList.size());
    	for (int i = 0; i < tempList.size(); i++) {
    		log.info("tempList" + tempList.get(i).toString() + "employeeName"
    				+ employeeName);
    		if (employeeName.trim().equals(tempList.get(i).toString().trim())) {
    			flag = true;
    		}
    	}
    	log.info("flag===================" + flag);
    	return flag;
    	
    }
    
    public void insertEmployeeHistory(String cpfacno, String empName,
    		boolean flag, String empCode, String region, String currentDate,
    		String computerName, String userName) throws Exception {
    	// for Storing History of before editing the record
    	EmpMasterBean historyBean = this.editPensionMaster(cpfacno, "", true,
    			"", region);
    	Connection conn = null;
    	Statement st = null;
    	conn = commonDB.getConnection();
    	// if (foundCPFNO.equals("")) {
    	String dbFormat = "";
    	st = conn.createStatement();
    	String sql = "insert into employee_history_info(cpfacno,pensionnumber,employeeNo,employeeName,"
    		+ "desegnation,emp_level,dateofbirth,dateofjoining,dateofseperation_reason,dateofseperation_date,"
    		+ "whether_option_a,whether_option_b,whether_option_no,WHETHER_FORM1_NOM_RECEIVED,remarks,airportcode,"
    		+ "sex,fhname,maritalStatus,permanentAddress,temporatyAddress,wetherOption,setDateOfAnnuation,otherreason,"
    		+ "division,department,emailId,empnomineesharable,region,lastactive,computerName,userName)"
    		+ " VALUES "
    		+ "('"
    		+ cpfacno.trim()
    		+ "','"
    		+ historyBean.getPensionNumber().trim()
    		+ "','"
    		+ historyBean.getEmpNumber().trim()
    		+ "','"
    		+ historyBean.getEmpName().trim()
    		+ "','"
    		+ historyBean.getDesegnation().trim()
    		+ "','"
    		+ historyBean.getEmpLevel().trim()
    		+ "','"
    		+ historyBean.getDateofBirth().trim()
    		+ "','"
    		+ historyBean.getDateofJoining().trim()
    		+ "','"
    		+ historyBean.getSeperationReason().trim()
    		+ "','"
    		+ historyBean.getDateofSeperationDate().trim()
    		+ "','"
    		+ historyBean.getWhetherOptionA().trim()
    		+ "','"
    		+ historyBean.getWhetherOptionB().trim()
    		+ "','"
    		+ historyBean.getWhetherOptionNO().trim()
    		+ "','"
    		+ historyBean.getForm2Nomination().trim()
    		+ "','"
    		+ historyBean.getRemarks().trim()
    		+ "','"
    		+ historyBean.getStation().trim()
    		+ "','"
    		+ historyBean.getSex().trim()
    		+ "','"
    		+ historyBean.getFhName().trim()
    		+ "','"
    		+ historyBean.getMaritalStatus().trim()
    		+ "','"
    		+ historyBean.getPermanentAddress().trim()
    		+ "','"
    		+ historyBean.getTemporatyAddress().trim()
    		+ "','"
    		+ historyBean.getWetherOption().trim()
    		+ "','"
    		+ historyBean.getDateOfAnnuation().trim()
    		+ "','"
    		+ historyBean.getOtherReason().trim()
    		+ "','"
    		+ historyBean.getDivision().trim()
    		+ "','"
    		+ historyBean.getDepartment().trim()
    		+ "','"
    		+ historyBean.getEmailId().trim()
    		+ "','"
    		+ historyBean.getEmpNomineeSharable().trim()
    		+ "','"
    		+ historyBean.getRegion().trim()
    		+ "','"
    		+ currentDate.trim()
    		+ "','" + computerName.trim() + "','" + userName.trim() + "')";
    	//
    	// yyyy/MM/dd:HH:mm:ss a
    	log.info(sql);
    	st.execute(sql);
    	
    }
    
    public String buildQuery2(EmpMasterBean empMasterBean) throws Exception {
    	log.info("PensionDAO :buildQuery2() entering method");
    	StringBuffer whereClause = new StringBuffer();
    	String fromDate = "", toDate = "";
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "", prefixWhereClause = "";
    	String region = "", reg = "", airportcode = "";
    	if (empMasterBean.getRegion() != "") {
    		reg = empMasterBean.getRegion();
    		if ((reg.substring(0, 3)).equals("IAD")) {
    			region = reg.substring(0, 3);
    			airportcode = reg.substring(4, reg.length());
    		} else
    			region = reg;
    	}
    	String sqlQuery = "select CPFACNO,AIRPORTCODE,EMPLOYEENO,EMPLOYEENAME,DESEGNATION,DATEOFBIRTH,DATEOFJOINING,REGION,SEX,FHNAME from employee_westinfo";
    	
    	if (!region.equals("")) {
    		whereClause.append(" region='" + region + "'");
    		whereClause.append(" AND ");
    	}
    	if (!airportcode.equals("")) {
    		whereClause.append(" airportcode='" + airportcode + "'");
    		whereClause.append(" AND ");
    	}
    	query.append(sqlQuery);
    	
    	if ((region.equals("")) && (airportcode.equals(""))) {
    		log.info("inside if");
    		
    	} else {
    		log.info("inside else");
    		if (!prefixWhereClause.equals("")) {
    			query.append(" AND ");
    		} else {
    			query.append(" WHERE ");
    		}
    		query.append(this.sTokenFormat(whereClause));
    	}
    	dynamicQuery = query.toString();
    	
    	System.out.println("Query " + dynamicQuery);
    	log.info("PensionDAO :buildQuery2() leaving method");
    	return dynamicQuery;
    }
    
    public ArrayList SearchCompartiveMasterAll(EmpMasterBean empMasterBean,
    		int start, int gridLength) {
    	log.info("PensionDAO :SearchCompartiveMasterAll() entering method");
    	ArrayList westinfo = new ArrayList();
    	PensionBean data = null;
    	Connection con = null;
    	PreparedStatement pst = null;
    	ResultSet rs = null;
    	ArrayList tempList = new ArrayList();
    	DatabaseBean searchData = new DatabaseBean();
    	System.out.println("start" + start + "end" + gridLength);
    	try {
    		String sqlQuery = this.buildQuery2(empMasterBean);
    		log.info("sql query " + sqlQuery);
    		int countGridLength = 0, i = 0;
    		String employeeName = "";
    		con = commonDB.getConnection();
    		pst = con
    		.prepareStatement(sqlQuery,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);
    		rs = pst.executeQuery();
    		if (rs.next()) {
    			westinfo = new ArrayList();
    			
    			if (rs.absolute(start)) {
    				countGridLength++;
    				if (rs.getString("employeename") != null) {
    					employeeName = rs.getString("employeename");
    				} else {
    					employeeName = "";
    				}
    				/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    				data = new PensionBean();
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("airportcode") != null) {
    					data.setAirportCode(rs.getString("airportcode"));
    				} else {
    					data.setAirportCode("");
    				}
    				
    				if (rs.getString("desegnation") != null) {
    					data.setDesegnation(rs.getString("desegnation"));
    				} else {
    					data.setDesegnation("");
    				}
    				System.out.println("EMPLOYEE (SearchPensionMasterAll)"
    						+ rs.getString("employeename"));
    				if (rs.getString("employeename") != null) {
    					data.setEmployeeName(rs.getString("employeename"));
    				} else {
    					data.setEmployeeName("");
    				}
    				if (rs.getString("employeeno") != null) {
    					data.setEmployeeCode(rs.getString("employeeno"));
    				} else {
    					data.setEmployeeCode("");
    				}
    				
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				
    				if (rs.getString("DATEOFJOINING") != null) {
    					data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    							.getDate("DATEOFJOINING")));
    				} else {
    					data.setDateofJoining("");
    				}
    				
    				if (rs.getString("REGION") != null) {
    					if ((rs.getString("REGION").equals("IAD"))) {
    						String chqreg = rs.getString("REGION") + "-"
    						+ rs.getString("airportcode");
    						System.out
    						.println("...............chqreg................"
    								+ chqreg);
    						data.setRegion(chqreg);
    					} else {
    						data.setRegion(rs.getString("REGION"));
    					}
    				} else {
    					data.setRegion("");
    				}
    				if (rs.getString("SEX") != null) {
    					data.setSex(rs.getString("SEX"));
    				} else {
    					data.setSex("");
    				}
    				
    				if (rs.getString("FHNAME") != null) {
    					data.setFHName(rs.getString("FHNAME"));
    				} else {
    					data.setFHName("");
    				}
    				westinfo.add(data);
    				/*
    				 * }
    				 * 
    				 * tempList.add(i,data.getEmployeeName().trim()); i++;
    				 */
    			}
    			while (rs.next() && countGridLength < gridLength) {
    				if (rs.getString("employeename") != null) {
    					employeeName = rs.getString("employeename");
    				} else {
    					employeeName = "";
    				}
    				/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    				data = new PensionBean();
    				
    				countGridLength++;
    				
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("airportcode") != null) {
    					data.setAirportCode(rs.getString("airportcode"));
    				} else {
    					data.setAirportCode("");
    				}
    				if (rs.getString("desegnation") != null) {
    					data.setDesegnation(rs.getString("desegnation"));
    				} else {
    					data.setDesegnation("");
    				}
    				System.out.println("EMPLOYEE (SearchPensionMasterAll)"
    						+ rs.getString("employeename"));
    				if (rs.getString("employeename") != null) {
    					data.setEmployeeName(rs.getString("employeename"));
    				} else {
    					data.setEmployeeName("");
    				}
    				if (rs.getString("employeeno") != null) {
    					data.setEmployeeCode(rs.getString("employeeno"));
    				} else {
    					data.setEmployeeCode("");
    				}
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				if (rs.getString("DATEOFJOINING") != null) {
    					data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    							.getDate("DATEOFJOINING")));
    				} else {
    					data.setDateofJoining("");
    				}
    				if (rs.getString("REGION") != null) {
    					if ((rs.getString("REGION").equals("IAD"))) {
    						String chqreg = rs.getString("REGION") + "-"
    						+ rs.getString("airportcode");
    						data.setRegion(chqreg);
    					} else {
    						data.setRegion(rs.getString("REGION"));
    					}
    				} else {
    					data.setRegion("");
    				}
    				if (rs.getString("SEX") != null) {
    					data.setSex(rs.getString("SEX"));
    				} else {
    					data.setSex("");
    				}
    				if (rs.getString("FHNAME") != null) {
    					data.setFHName(rs.getString("FHNAME"));
    				} else {
    					data.setFHName("");
    				}
    				westinfo.add(data);
    				
    			}
    			
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, pst, rs);
    	}
    	log.info("PensionDAO :SearchCompartiveMasterAll() leaving method");
    	return westinfo;
    }
    
    public int totalCompartiveMasterData(EmpMasterBean empMasterBean) {
    	log.info("PensionDAO :totalCompartiveMasterData() entering method");
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	int totalRecords = 0;
    	// boolean flag=true;
    	try {
    		String selectSQL = this.buildQuery2(empMasterBean);
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(selectSQL);
    		while (rs.next()) {
    			totalRecords++;
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	log.info("PensionDAO :totalCompartiveMasterData() leaving method");
    	return totalRecords;
    	
    }
    
    public String checkPensionNumber(String pensionnumber, String cpfaccno,
    		String employeeno, String employeeName, String region,
    		String dateOfBirth) {
    	log.info("PensionDAO :checkPensionNumber() Enter method==============");
    	String pensionNumber = "", tempPensionNumber = "";
    	boolean tempPensionflag = false;
    	String[] employeeList = new String[3];
    	employeeList = getPensionNumberFromDB(pensionnumber);
    	String dbCPFACCno = "", dbEmployeeNo = "", dbEmployeeName = "", dbRegion = "", chkFinalPensionNumber = "";
    	if (employeeList[0] != null) {
    		dbCPFACCno = employeeList[0].toString();
    	}
    	if (employeeList[1] != null) {
    		dbEmployeeName = employeeList[1].toString();
    	}
    	if (employeeList[2] != null) {
    		dbEmployeeNo = employeeList[2].toString();
    	}
    	if (employeeList[3] != null) {
    		dbRegion = employeeList[3].toString();
    	}
    	
    	String delimeters = " ";
    	dbEmployeeName = StringUtility.replace(dbEmployeeName.toCharArray(),
    			delimeters.toCharArray(), ".").toString();
    	employeeName = StringUtility.replace(employeeName.toCharArray(),
    			delimeters.toCharArray(), ".").toString();
    	if (dbCPFACCno.equals(cpfaccno) && dbEmployeeName.equals(employeeName)
    			&& dbEmployeeNo.equals(employeeno) && dbRegion.equals(region)) {
    		log.info("Pnesion number both are equal");
    		pensionNumber = pensionnumber;
    	} else if (dbCPFACCno != null && dbEmployeeName != null
    			&& dbEmployeeNo != null && !dbRegion.equals("")
    			&& dbRegion.equals(region)) {
    		log
    		.info("Pnesion number db values are avaliable the record is created new both are equal");
    		pensionNumber = pensionnumber;
    	} else {
    		if (!pensionnumber.equals("") && !dbRegion.equals("")) {
    			tempPensionNumber = pensionnumber.substring(6, pensionnumber
    					.length());
    			String tempCpfacno = employeeList[0].toString();
    			tempPensionNumber = StringUtility.replace(
    					tempPensionNumber.toCharArray(),
    					tempCpfacno.toCharArray(), "").toString();
    			tempPensionflag = true;
    		}
    		pensionNumber = getPensionNumberError(employeeName, dateOfBirth,
    				cpfaccno, tempPensionNumber);
    		if (checkPensionNumberFromDB(pensionNumber) == true) {
    			
    			pensionNumber = checkPensionNumber(pensionNumber, cpfaccno,
    					employeeno, employeeName, region.trim(), dateOfBirth);
    		}
    	}
    	
    	log.info("PensionDAO :checkPensionNumber() Exit method==============");
    	return pensionNumber;
    }
    
    private String[] getPensionNumberFromDB(String pensionnumber) {
    	int count = 0;
    	String[] employeeList = new String[4];
    	Statement st = null;
    	Connection con = null;
    	ResultSet rs = null;
    	System.out.println("pensionnumber==============" + pensionnumber);
    	String query = "select CPFACNO,EMPLOYEENAME,EMPLOYEENO,REGION from employee_info where  pensionnumber='"
    		+ pensionnumber + "' and pensionnumber is not null";
    	log.info("query is " + query);
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		if (rs.next()) {
    			if (rs.getString("CPFACNO") != null) {
    				employeeList[0] = rs.getString("CPFACNO");
    			}
    			if (rs.getString("EMPLOYEENAME") != null) {
    				employeeList[1] = rs.getString("EMPLOYEENAME");
    			}
    			if (rs.getString("EMPLOYEENO") != null) {
    				employeeList[2] = rs.getString("EMPLOYEENO");
    			}
    			if (rs.getString("REGION") != null) {
    				employeeList[3] = rs.getString("REGION");
    			} else {
    				employeeList[3] = "";
    			}
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	return employeeList;
    }
    
    public String getPensionNumberError(String empName, String dateofBirth,
    		String cpf, String tempPensionNumber) {
    	log.info("PensionDao:getPensionNumber() entering method");
    	log.info("PensionDao:getPensionNumber() dateofBirth" + dateofBirth
    			+ "empName" + empName);
    	// TODO Auto-generated method stub
    	String finalName = "", fname = "", finalString = "", tempPensionSubString = "";
    	String finalPensionNumber = "";
    	SimpleDateFormat fromDate = null;
    	int endIndxName = 0, tempPensionNoSize = 0;
    	String quats[] = { "Mrs.", "DR.", "Mr.", "Ms.", "SH.", "smt.", "SHRI",
    	"MISS." };
    	String tempName = "", PensionNumber = "", convertedDt = "";
    	String uniquenumber = validateCPFAccno(cpf.toCharArray());
    	try {
    		if (dateofBirth.indexOf("-") != -1) {
    			fromDate = new SimpleDateFormat("dd-MMM-yyyy");
    		} else {
    			fromDate = new SimpleDateFormat("dd/MMM/yyyy");
    		}
    		SimpleDateFormat toDate = new SimpleDateFormat("ddMMyy");
    		convertedDt = toDate.format(fromDate.parse(dateofBirth));
    		System.out.println(" convertedDt " + convertedDt);
    		
    		int startIndxName = 0, index = 0;
    		endIndxName = 1;
    		for (int i = 0; i < quats.length; i++) {
    			if (empName.indexOf(quats[i]) != -1) {
    				tempName = empName.replaceAll(quats[i], "").trim();
    				// tempName=empName.substring(index+1,empName.length());
    				empName = tempName;
    			}
    		}
    		String delimiters = " ";
    		System.out
    		.println("Oringial String indexof" + empName.indexOf(" "));
    		if (empName.indexOf(" ") != -1) {
    			finalString = StringUtility.replace(
    					empName.trim().toCharArray(), delimiters.toCharArray(),
    			".").toString();
    		} else {
    			finalString = empName;
    		}
    		System.out.println("Oringial String" + empName
    				+ " Modified String " + finalString);
    		StringTokenizer st = new StringTokenizer(finalString, ".");
    		int count = 0, i = 0;
    		
    		int stCount = st.countTokens();
    		String[] finalStringArray = new String[stCount];
    		// && && count<=st.countTokens()-1st.countTokens()-1
    		while (st.hasMoreElements()) {
    			finalStringArray[i] = st.nextToken();
    			i++;
    		}
    		System.out.println("Length==tempPensionNumber==="
    				+ finalStringArray.length + "Temp Pension Number is "
    				+ tempPensionNumber);
    		tempPensionNoSize = tempPensionNumber.length();
    		System.out.println("tempPensionNoSize=====" + tempPensionNoSize);
    		for (int j = 0; j < finalStringArray.length; j++) {
    			if (!tempPensionNumber.equals("") && tempPensionNoSize != 0) {
    				if (tempPensionNumber.substring(0, 1).equals(
    						finalStringArray[j].substring(0, 1))) {
    					System.out.println("finalStringArray[j].length()====="
    							+ finalStringArray[j].length());
    					if (finalStringArray[j].length() <= 1) {
    						tempPensionSubString = tempPensionNumber.substring(
    								1, 2);
    					} else {
    						tempPensionSubString = tempPensionNumber.substring(
    								0, 1);
    						
    					}
    				}
    			}
    			
    		}
    		
    		System.out.println("tempPensionSubString====="
    				+ tempPensionSubString);
    		int startIndex = 0;
    		for (int j = 0; j < finalStringArray.length; j++) {
    			System.out.println("Length=====" + finalStringArray[j].length()
    					+ "Value is" + finalStringArray[j]);
    			if (finalStringArray[j].length() > 1) {
    				if (!tempPensionNumber.equals("") && tempPensionNoSize != 0) {
    					System.out
    					.println("Check Pension number===tempPensionSubString=="
    							+ tempPensionSubString
    							+ "Value is"
    							+ finalStringArray[j].substring(0, 1)
    							+ "Temp Pension Size========="
    							+ tempPensionNoSize);
    					if (tempPensionSubString.equals(finalStringArray[j]
    					                                                 .substring(0, 1))) {
    						if ((tempPensionNoSize - 1) == 1) {
    							fname = fname
    							+ finalStringArray[j].substring(0,
    									tempPensionNoSize + 1);
    						} else {
    							if (tempPensionNoSize + 1 != finalStringArray[j]
    							                                              .length() - 1) {
    								fname = fname
    								+ finalStringArray[j].substring(0,
    										tempPensionNoSize + 1);
    							} else {
    								fname = fname
    								+ finalStringArray[j].substring(0,
    										tempPensionNoSize);
    							}
    						}
    						System.out
    						.println("Check Pension number===tempPensionSubString==fname===="
    								+ fname);
    					} else {
    						fname = fname + finalStringArray[j].substring(0, 1);
    					}
    					
    				} else {
    					fname = fname + finalStringArray[j].substring(0, 2);
    				}
    				
    			} else if (finalStringArray[j].length() <= 1) {
    				fname = fname + finalStringArray[j];
    			}
    		}
    		finalPensionNumber = convertedDt + fname + uniquenumber;
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    	}
    	log.info("PensionDao:getPensionNumber() leaving method");
    	return finalPensionNumber;
    	
    }
    
    public void updatePensionCheck(String cpfacnoString,String empSerailNumber,
    		String flag,String pensionNumber) throws Exception {
    	log.info("PensionDAO :updatePensionCheck entering method");
    	Connection con = null;
    	ArrayList empinfo = new ArrayList();
    	PensionBean data = null;
    	PreparedStatement pst = null;
    	Statement st = null;
    	ResultSet rs = null;
    	String[] temp1 = null;
    	ArrayList tempList = new ArrayList();
    	DatabaseBean searchData = new DatabaseBean();
    	String cpfacno="",region="",employeeno="";
    	// temp1 = cpfacnoString.split("$");
    	//   log.info("checklist length " +temp1.length);
    	/*  for(int i=0;i<temp1.length;i++){
    	 cpfacno=temp1[0];
    	 region=temp1[1];
    	 employeeno=temp1[2];
    	 }*/
    	if(!cpfacnoString.equals("")){
    		cpfacno=cpfacnoString.substring(0,cpfacnoString.indexOf("$"));
    		region=cpfacnoString.substring(cpfacnoString.indexOf("$")+1,cpfacnoString.lastIndexOf("$"));
    		employeeno=cpfacnoString.substring(cpfacnoString.lastIndexOf("$")+1,cpfacnoString.length());
    	}
    	String employeenoString="";
    	if(employeeno.equals("")){
    		employeenoString=" AND EMPLOYEENO is null";
    	}else{
    		employeenoString=" AND EMPLOYEENO='"+employeeno+"'"; 
    	}
    	String sqlQuery="update employee_info set pensioncheck='false',EMPSERIALNUMBER='"+empSerailNumber+"',pensionnumber='"+pensionNumber.toUpperCase()+"' where empflag='Y' and cpfacno='"+cpfacno+"' and region='"+region+"'"+employeenoString;
    	log.info("update "+sqlQuery);
    	//  String sqlQuery="update employee_info set pensioncheck='false',EMPSERIALNUMBER='"+empSerailNumber+"',pensionnumber='"+pensionNumber+"' where empflag='Y' and cpfacno='"+cpfacno+"' and region='CHQNAD'";
    	
    	try {
    		con = commonDB.getConnection();
    		con.setAutoCommit(false);
    		st=con.createStatement();
    		st.addBatch(sqlQuery);
    		st.executeBatch();
    		// st.executeUpdate(sqlQuery);
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		st.close();
    		con.close();
    	}
    	log.info("PensionDAO :updatePensionCheck Leaving method");
    }
    
    public ArrayList searchRecordsbyDobandName(EmpMasterBean empMasterBean,
    		int start, int gridLength, String flag,String ajaxCall) throws Exception {
    	
    	log.info("PensionDAO :searchRecordsbyDobandName() entering method");
    	ArrayList empinfo = new ArrayList();
    	PensionBean data = null;
    	Connection con = null;
    	PreparedStatement pst = null;
    	Statement st = null;
    	ResultSet rs = null;
    	
    	ArrayList tempList = new ArrayList();
    	DatabaseBean searchData = new DatabaseBean();
    	System.out.println("start" + start + "end" + gridLength);
    	String sqlQuery = this.searchRecordsbyDobandName(empMasterBean, flag,ajaxCall,"");
    	log.info("sql query " + sqlQuery);
    	String sqlQueryMinrowId = "";
    	sqlQueryMinrowId = sqlQuery;
    	
    	log.info("sqlQuery" + sqlQueryMinrowId);
    	int countGridLength = 0, i = 0;
    	System.out.println("Query is(retriveByAll)" + sqlQueryMinrowId);
    	String employeeName = "",empSerialnumber="";
    	try {
    		con = commonDB.getConnection();
    		// st=con.createStatement();
    		if(!ajaxCall.equals("ajaxCall")){
    			pst = con
    			.prepareStatement(sqlQueryMinrowId,
    					ResultSet.TYPE_SCROLL_SENSITIVE,
    					ResultSet.CONCUR_UPDATABLE);
    			rs = pst.executeQuery();
    			
    			if (rs.next()&& !ajaxCall.equals("ajaxCall")) {
    				empinfo = new ArrayList();
    				
    				if (rs.absolute(start)) {
    					countGridLength++;
    					if (rs.getString("employeename") != null) {
    						employeeName = rs.getString("employeename");
    					} else {
    						employeeName = "";
    					}
    					/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    					data = new PensionBean();
    					if (rs.getString("cpfacno") != null) {
    						data.setCpfAcNo(rs.getString("cpfacno"));
    					} else {
    						data.setCpfAcNo("");
    					}
    					if (rs.getString("airportcode") != null) {
    						data.setAirportCode(rs.getString("airportcode"));
    					} else {
    						data.setAirportCode("");
    					}
    					if (rs.getString("pensionnumber") != null) {
    						data.setPensionnumber(rs.getString("pensionnumber"));
    					} else {
    						data.setPensionnumber("");
    					}
    					if (rs.getString("desegnation") != null) {
    						data.setDesegnation(rs.getString("desegnation"));
    					} else {
    						data.setDesegnation("");
    					}
    					System.out.println("EMPLOYEE (SearchPensionMasterAll)"
    							+ rs.getString("employeename"));
    					if (rs.getString("employeename") != null) {
    						data.setEmployeeName(rs.getString("employeename"));
    					} else {
    						data.setEmployeeName("");
    					}
    					if (rs.getString("employeeno") != null) {
    						data.setEmployeeCode(rs.getString("employeeno"));
    					} else {
    						data.setEmployeeCode("");
    					}
    					if (rs.getString("dateofbirth") != null) {
    						data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    								.getDate("dateofbirth")));
    					} else {
    						data.setDateofBirth("");
    					}
    					if (rs.getString("DATEOFJOINING") != null) {
    						data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    								.getDate("DATEOFJOINING")));
    					} else {
    						data.setDateofJoining("");
    					}
    					if (rs.getString("WETHEROPTION") != null) {
    						data.setPensionOption(rs.getString("WETHEROPTION"));
    					} else {
    						data.setPensionOption("");
    					}
    					if (rs.getString("lastactive") != null) {
    						data.setLastActive(commonUtil.getDatetoString(rs
    								.getDate("lastactive"), "dd-MMM-yyyy"));
    					} else {
    						data.setLastActive("");
    					}
    					if (rs.getString("region") != null) {
    						data.setRegion(rs.getString("region"));
    					} else {
    						data.setRegion("");
    					}
    					if (rs.getString("pensionCheck") != null) {
    						data.setPensionCheck(rs.getString("pensionCheck"));
    					} else {
    						data.setPensionCheck("");
    					}
    					
    					if (rs.getString("empserialnumber") != null) {
    						data.setEmpSerialNumber(rs.getString("empserialnumber"));
    					} else {
    						data.setEmpSerialNumber("");
    					}
    					
    					//   if( data.getCpfAcNo().equals(empinfo.get(i))){
    					empinfo.add(data);                      
    					//    }
    					
    					
    				}
    				while (rs.next()&& countGridLength < gridLength) {
    					if (rs.getString("employeename") != null) {
    						employeeName = rs.getString("employeename");
    					} else {
    						employeeName = "";
    					}
    					/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    					data = new PensionBean();
    					countGridLength++;
    					if (rs.getString("cpfacno") != null) {
    						data.setCpfAcNo(rs.getString("cpfacno"));
    					} else {
    						data.setCpfAcNo("");
    					}
    					if (rs.getString("airportcode") != null) {
    						data.setAirportCode(rs.getString("airportcode"));
    					} else {
    						data.setAirportCode("");
    					}
    					
    					if (rs.getString("desegnation") != null) {
    						data.setDesegnation(rs.getString("desegnation"));
    					} else {
    						data.setDesegnation("");
    					}
    					if (rs.getString("employeename") != null) {
    						data.setEmployeeName(rs.getString("employeename"));
    					} else {
    						data.setEmployeeName("");
    					}
    					if (rs.getString("EMPLOYEENO") != null) {
    						data.setEmployeeCode(rs.getString("EMPLOYEENO"));
    					} else {
    						data.setEmployeeCode("");
    					}
    					if (rs.getString("pensionnumber") != null) {
    						data.setPensionnumber(rs.getString("pensionnumber"));
    					} else {
    						data.setPensionnumber("");
    					}
    					
    					if (rs.getString("dateofbirth") != null) {
    						data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    								.getDate("dateofbirth")));
    					} else {
    						data.setDateofBirth("");
    					}
    					if (rs.getString("DATEOFJOINING") != null) {
    						data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    								.getDate("DATEOFJOINING")));
    					} else {
    						data.setDateofJoining("");
    					}
    					if (rs.getString("WETHEROPTION") != null) {
    						data.setPensionOption(rs.getString("WETHEROPTION"));
    					} else {
    						data.setPensionOption("");
    					}
    					if (rs.getString("empserialnumber") != null) {
    						data.setEmpSerialNumber(rs.getString("empserialnumber"));
    					} else {
    						data.setEmpSerialNumber("");
    					}
    					if (rs.getString("region") != null) {
    						data.setRegion(rs.getString("region"));
    					} else {
    						data.setRegion("");
    					}
    					if (rs.getString("pensionCheck") != null) {
    						data.setPensionCheck(rs.getString("pensionCheck"));
    					} else {
    						data.setPensionCheck("false");
    					}
    					
    					if (rs.getString("lastactive") != null) {
    						data.setLastActive(commonUtil.getDatetoString(rs
    								.getDate("lastactive"), "dd-MMM-yyyy"));
    					} else {
    						data.setLastActive("");
    					}
    					empinfo.add(data);
    					
    				}
    			}
    		}
    		else{ 
    			pst = con
    			.prepareStatement(sqlQueryMinrowId);
    			rs = pst.executeQuery();
    			empinfo = new ArrayList();
    			while (rs.next()) {
    				data = new PensionBean();
    				if (rs.getString("employeename") != null) {
    					employeeName = rs.getString("employeename");
    				} else {
    					employeeName = "";
    				}
    				
    				
    				data.setEmployeeName(employeeName.trim());
    				
    				
    				empinfo.add(data);
    				log.info("empInfo size" +empinfo.size());
    			}
    			
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		rs.close();
    		pst.close();
    		con.close();
    	}
    	log.info("PensionDAO :searchRecordsbyDobandName() leaving method");
    	return empinfo;
    }
    public ArrayList getEmployeeList(EmpMasterBean empMasterBean,
    		int start, int gridLength, String flag) throws Exception {
    	log.info("PensionDAO :getEmployeeList() entering method");
    	ArrayList empinfo = new ArrayList();
    	PensionBean data = null;
    	Connection con = null;
    	PreparedStatement pst = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ArrayList tempList = new ArrayList();
    	DatabaseBean searchData = new DatabaseBean();
    	System.out.println("start" + start + "end" + gridLength);
    	String sqlQuery ="select * from employee_info wherewhere empflag='Y' and empserialnumber is null and employeename like'"+empMasterBean.getEmpName()+"'";
    	log.info("sql query " + sqlQuery);
    	String sqlQueryMinrowId = "";
    	sqlQueryMinrowId = sqlQuery;
    	
    	log.info("sqlQuery" + sqlQueryMinrowId);
    	int countGridLength = 0, i = 0;
    	System.out.println("Query is(retriveByAll)" + sqlQueryMinrowId);
    	String employeeName = "";
    	try {
    		con = commonDB.getConnection();
    		// st=con.createStatement();
    		pst = con
    		.prepareStatement(sqlQueryMinrowId,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);
    		rs = pst.executeQuery();
    		while (rs.next()) {
    			empinfo = new ArrayList();
    			/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    			data = new PensionBean();
    			if (rs.getString("employeename") != null) {
    				data.setEmployeeName(rs.getString("employeename"));
    			} else {
    				data.setEmployeeName("");
    			}
    			empinfo.add(data);
    			
    			
    			
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		rs.close();
    		pst.close();
    		con.close();
    	}
    	log.info("PensionDAO :getEmployeeList() leaving method");
    	return empinfo;
    }
    /*	public ArrayList pensionHistory(EmpMasterBean empMasterBean,
     int start, int gridLength, String flag) throws Exception {
     log.info("PensionDAO :pensionHistory() entering method");
     ArrayList empFinanceinfo = new ArrayList();
     FinacialDataBean data = null;
     Connection con = null;
     PreparedStatement pst = null;
     Statement st = null;
     ResultSet rs = null;
     ResultSet rs1 = null;
     ArrayList tempList = new ArrayList();
     DatabaseBean searchData = new DatabaseBean();
     String empserialNumberQuery="select  cpfacno,region from employee_info where empserialnumber is not null";
     try {
     con = commonDB.getConnection();
     st=con.createStatement();
     rs1=st.executeQuery(empserialNumberQuery);
     while(rs1.next()){
     
     String cpfacno=(String)rs1.getString(1);	
     String region=(String)rs1.getString(2);	
     String sqlQuery="select cpfaccno,airportcode,employeename,employeeno,region,EMOLUMENTS,EMPPFSTATUARY, to_char(monthyear,'dd-Mon-yyyy') as monthyear from employee_pension_validate where cpfaccno='"+cpfacno+"' and region='"+region+"' order by cpfaccno,monthyear";
     //	String sqlQuery="select cpfaccno,airportcode,employeename,employeeno,region,EMOLUMENTS,EMPPFSTATUARY, to_char(monthyear,'dd-Mon-yyyy') as monthyear from employee_pension_validate where cpfaccno='1015' and region='South Region' order by cpfaccno,monthyear";
      log.info("sql query " + sqlQuery);
      int countGridLength = 0, i = 0;
      
      String employeeName = "";
      
      
      // st=con.createStatement();
       pst = con
       .prepareStatement(sqlQuery,
       ResultSet.TYPE_SCROLL_SENSITIVE,
       ResultSet.CONCUR_UPDATABLE);
       rs = pst.executeQuery();
       if (rs.next()) {
       empFinanceinfo = new ArrayList();
       
       if (rs.absolute(start)) {
       countGridLength++;
       
       data = new FinacialDataBean();
       if (rs.getString("cpfaccno") != null) {
       data.setCpfAccNo(rs.getString("cpfaccno"));
       } else {
       data.setCpfAccNo("");
       }
       if (rs.getString("airportcode") != null) {
       data.setAirportCode(rs.getString("airportcode"));
       } else {
       data.setAirportCode("");
       }
       if (rs.getString("employeename") != null) {
       data.setEmployeeName(rs.getString("employeename"));
       } else {
       data.setEmployeeName("");
       }
       if (rs.getString("employeeno") != null) {
       data.setEmployeeNewNo(rs.getString("employeeno"));
       } else {
       data.setEmployeeNewNo("");
       }
       
       if (rs.getString("region") != null) {
       data.setRegion(rs.getString("region"));
       } else {
       data.setRegion("");
       }
       if (rs.getString("EMOLUMENTS") != null) {
       data.setEmoluments(rs.getString("EMOLUMENTS"));
       } else {
       data.setEmoluments("");
       }
       
       if (rs.getString("EMPPFSTATUARY") != null) {
       data.setEmpPfStatuary(rs.getString("EMPPFSTATUARY"));
       } else {
       data.setEmpPfStatuary("");
       }
       if (rs.getString("MONTHYEAR") != null) {
       data.setMonthYear(rs.getString("MONTHYEAR"));
       } else {
       data.setMonthYear("");
       }
       
       empFinanceinfo.add(data);
       
       }
       while (rs.next()) {
       data = new FinacialDataBean();
       countGridLength++;
       if (rs.getString("cpfaccno") != null) {
       data.setCpfAccNo(rs.getString("cpfaccno"));
       } else {
       data.setCpfAccNo("");
       }
       if (rs.getString("airportcode") != null) {
       data.setAirportCode(rs.getString("airportcode"));
       } else {
       data.setAirportCode("");
       }
       
       if (rs.getString("employeename") != null) {
       data.setEmployeeName(rs.getString("employeename"));
       } else {
       data.setEmployeeName("");
       }
       if (rs.getString("EMPLOYEENO") != null) {
       data.setEmployeeNewNo(rs.getString("EMPLOYEENO"));
       } else {
       data.setEmployeeNewNo("");
       }
       
       if (rs.getString("region") != null) {
       data.setRegion(rs.getString("region"));
       } else {
       data.setRegion("");
       }
       if (rs.getString("EMOLUMENTS") != null) {
       data.setEmoluments(rs.getString("EMOLUMENTS"));
       } else {
       data.setEmoluments("");
       }
       
       if (rs.getString("EMPPFSTATUARY") != null) {
       data.setEmpPfStatuary(rs.getString("EMPPFSTATUARY"));
       } else {
       data.setEmpPfStatuary("");
       }
       if (rs.getString("MONTHYEAR") != null) {
       data.setMonthYear(rs.getString("MONTHYEAR"));
       } else {
       data.setMonthYear("");
       }
       empFinanceinfo.add(data);
       }
       }
       }
       } catch (SQLException e) {
       // TODO Auto-generated catch block
        e.printStackTrace();
        } catch (Exception e) {
        // TODO Auto-generated catch block
         e.printStackTrace();
         } finally {
         rs.close();
         pst.close();
         con.close();
         }
         log.info("PensionDAO :pensionHistory() leaving method");
         return empFinanceinfo;
         } */
    
    
    public ArrayList searchRecordsbyEmpSerailNo(EmpMasterBean empMasterBean,
    		int start, int gridLength,String usertype) throws Exception {
    	log.info("PensionDAO :searchRecordsbyEmpSerailNo() entering method");
    	FinancialReportDAO reportDAO=new FinancialReportDAO();
    	ArrayList empinfo = new ArrayList();
    	EmpMasterBean data = null;
    	Connection con = null;
    	PreparedStatement pst = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ArrayList tempList = new ArrayList();
    	DatabaseBean searchData = new DatabaseBean();
    	System.out.println("start" + start + "end" + gridLength);
    	String sqlQuery = this.searchRecordsbyDobandName(empMasterBean,"false","",usertype);
    	log.info("sql query " + sqlQuery);
    	String sqlQueryMinrowId = "";
    	sqlQueryMinrowId = "select info1.total as totalSerail,info2.cpfacno as cpfacno ,info2.PENSIONNUMBER as PENSIONNUMBER,info2.empserialnumber as empserialnumber,info2.employeename as employeename,info2.dateofbirth as dateofbirth,info2.dateofjoining as dateofjoining,info2.pensioncliamsProcess as claimsprocess  from  (select empserialnumber,count(*) as total from employee_info where empserialnumber is not null group by (empserialnumber) order by empserialnumber)info1,("+sqlQuery+") info2 where info2.EMPSERIALNUMBER=info1.EMPSERIALNUMBER";
    	int countGridLength = 0, i = 0,countSerialNumber=0,totalSerialNumber=0;
    	System.out.println("Query is(retriveByAll)" + sqlQueryMinrowId);
    	log.info("Query is(retriveByAll)" + sqlQueryMinrowId);
    	
    	String tempSerialNumber="";
    	String employeeName = "";
    	int totalTrans=0;
    	try {
    		con = commonDB.getConnection();
    		// st=con.createStatement();
    		pst = con
    		.prepareStatement(sqlQueryMinrowId,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);
    		rs = pst.executeQuery();
    		
    		if (rs.next()) {
    			empinfo = new ArrayList();
    			
    			if (rs.absolute(start)) {
    				countGridLength++;
    				
    				data = new EmpMasterBean();
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("employeename") != null) {
    					data.setEmpName(rs.getString("employeename"));
    				} else {
    					data.setEmpName("");
    				}
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				
    				if (rs.getString("DATEOFJOINING") != null) {
    					data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    							.getDate("DATEOFJOINING")));
    				} else {
    					data.setDateofJoining("");
    				}
    				if (rs.getString("EMPSERIALNUMBER") != null) {
    					data.setEmpSerialNo(rs.getString("EMPSERIALNUMBER"));
    				} else {
    					data.setEmpSerialNo("");
    				}
    				data.setEmpSerialNo(commonUtil.leadingZeros(5, data.getEmpSerialNo()));
    				
    				data.setEmpSerialNo(commonDAO.getPensionNumber(data.getEmpName(),data.getDateofBirth(),data.getEmpSerialNo(),true));
    				
    				if (rs.getString("PENSIONNUMBER") != null) {
    					data.setPensionNumber(rs.getString("PENSIONNUMBER"));
    				} else {
    					data.setPensionNumber("");
    				}
    				
    				
    				 if (rs.getString("claimsprocess") != null) {
                         data.setClaimsprocess(rs.getString("claimsprocess"));
                     } else {
                         data.setClaimsprocess("");
                     }
    				
    				
    				
    				data.setTotalTrans("");
    				tempSerialNumber=rs.getString("EMPSERIALNUMBER");
    				countSerialNumber++;
    				log.info("In if condition"+rs.getString("EMPSERIALNUMBER"));
    				empinfo.add(data);
    				
    			}
    			while (rs.next()) {
    				totalSerialNumber=rs.getInt("totalSerail");
    				if (rs.getString("employeename") != null) {
    					employeeName = rs.getString("employeename");
    				} else {
    					employeeName = "";
    				}
    				/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    				data = new EmpMasterBean();
    				
    				countGridLength++;
    				if(tempSerialNumber.equals(rs.getString("EMPSERIALNUMBER"))){
    					countSerialNumber++;
    					
    				}else if(!tempSerialNumber.equals(rs.getString("EMPSERIALNUMBER"))){
    					tempSerialNumber=rs.getString("EMPSERIALNUMBER");
    					countSerialNumber=0;
    					countSerialNumber++;
    				}
    				// log.info("EMPSERIALNUMBER"+rs.getString("EMPSERIALNUMBER")+"countSerialNumber"+countSerialNumber);
    				if (rs.getString("employeename") != null) {
    					data.setEmpName(rs.getString("employeename"));
    				} else {
    					data.setEmpName("");
    				}
    				
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				if (rs.getString("DATEOFJOINING") != null) {
    					data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    							.getDate("DATEOFJOINING")));
    				} else {
    					data.setDateofJoining("");
    				}
    				if (rs.getString("EMPSERIALNUMBER") != null) {
    					data.setEmpSerialNo(rs.getString("EMPSERIALNUMBER"));
    				} else {
    					data.setEmpSerialNo("");
    				}
    				
    				
    				if (rs.getString("claimsprocess") != null) {
                        data.setClaimsprocess(rs.getString("claimsprocess"));
                    } else {
                        data.setClaimsprocess("");
                    }
    				
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("PENSIONNUMBER") != null) {
    					data.setPensionNumber(rs.getString("PENSIONNUMBER"));
    				} else {
    					data.setPensionNumber("");
    				}
    				if(countSerialNumber==totalSerialNumber){
    					//	code comented on 13-Apr-2010
    					// totalTrans=reportDAO.getPFPensionCount(data.getEmpSerialNo(),"PENSIONNO");
    					data.setTotalTrans(Integer.toString(totalTrans));
    				}
    				data.setEmpSerialNo(commonUtil.leadingZeros(5, data.getEmpSerialNo()));
    				data.setEmpSerialNo(commonDAO.getPensionNumber(data.getEmpName(),data.getDateofBirth(),data.getEmpSerialNo(),true));
    				//   log.info("In while condition"+rs.getString("EMPSERIALNUMBER"));
    				empinfo.add(data);
    			}
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con,pst,rs);
    	
    	}
    	log.info("PensionDAO :searchRecordsbyEmpSerailNo() leaving method");
    	return empinfo;
    }
    public ArrayList getProcessUnprocessList(EmpMasterBean empMasterBean,
    		int start, int gridLength) throws Exception {
    	log.info("PensionDAO :getProcessUnprocessList() entering method");
    	ArrayList empinfo = new ArrayList();
    	EmpMasterBean data = null;
    	Connection con = null;
    	PreparedStatement pst = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ArrayList tempList = new ArrayList();
    	DatabaseBean searchData = new DatabaseBean();
    	
    	String sqlQuery = "select * from employee_info where EMPSERIALNUMBER='"+empMasterBean.getEmpSerialNo()+"'";/* and dateofbirth='"+empMasterBean.getDateofBirth()+"' "; */
    	//String sqlQuery = "select * from employee_info  where EMPSERIALNUMBER='"+empMasterBean.getEmpSerialNo()+"'and region='CHQNAD' ";
    	log.info("sql query " + sqlQuery);
    	
    	int countGridLength = 0, i = 0;
    	String employeeName = "";
    	try {
    		con = commonDB.getConnection();
    		// st=con.createStatement();
    		pst = con
    		.prepareStatement(sqlQuery,
    				ResultSet.TYPE_SCROLL_SENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);
    		rs = pst.executeQuery();
    		if (rs.next()) {
    			empinfo = new ArrayList();
    			
    			if (rs.absolute(start)) {
    				countGridLength++;
    				
    				data = new EmpMasterBean();
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("region") != null) {
    					data.setRegion(rs.getString("region"));
    				} else {
    					data.setRegion("");
    				}
    				if (rs.getString("AIRPORTCODE") != null) {
    					data.setStation(rs.getString("AIRPORTCODE"));
    				} else {
    					data.setStation("");
    				}
    				if (rs.getString("employeename") != null) {
    					data.setEmpName(rs.getString("employeename"));
    				} else {
    					data.setEmpName("");
    				}
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				
    				if (rs.getString("DATEOFJOINING") != null) {
    					data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    							.getDate("DATEOFJOINING")));
    				} else {
    					data.setDateofJoining("");
    				}
    				if (rs.getString("EMPSERIALNUMBER") != null) {
    					data.setEmpSerialNo(rs.getString("EMPSERIALNUMBER"));
    				} else {
    					data.setEmpSerialNo("");
    				}
    				String pensionNumber=this.getPensionNumber(data.getEmpName(),data.getDateofBirth(),data.getEmpSerialNo(),"true");
    				//	if (rs.getString("pensionnumber") != null) {
    				data.setPensionNumber(pensionNumber);
    				//} else {
    				//	data.setPensionNumber("");
    				//}
    				if (rs.getString("EMPLOYEENO") != null) {
    					data.setEmpNumber(rs.getString("EMPLOYEENO"));
    				} else {
    					data.setEmpNumber("");
    				}
    				empinfo.add(data);
    				
    			}
    			while (rs.next()) {
    				if (rs.getString("employeename") != null) {
    					employeeName = rs.getString("employeename");
    				} else {
    					employeeName = "";
    				}
    				/* if(this.checkEmployeeName(tempList,employeeName)==false){ */
    				data = new EmpMasterBean();
    				
    				countGridLength++;
    				if (rs.getString("employeename") != null) {
    					data.setEmpName(rs.getString("employeename"));
    				} else {
    					data.setEmpName("");
    				}
    				
    				if (rs.getString("dateofbirth") != null) {
    					data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    							.getDate("dateofbirth")));
    				} else {
    					data.setDateofBirth("");
    				}
    				if (rs.getString("DATEOFJOINING") != null) {
    					data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    							.getDate("DATEOFJOINING")));
    				} else {
    					data.setDateofJoining("");
    				}
    				if (rs.getString("EMPSERIALNUMBER") != null) {
    					data.setEmpSerialNo(rs.getString("EMPSERIALNUMBER"));
    				} else {
    					data.setEmpSerialNo("");
    				}
    				if (rs.getString("cpfacno") != null) {
    					data.setCpfAcNo(rs.getString("cpfacno"));
    				} else {
    					data.setCpfAcNo("");
    				}
    				if (rs.getString("region") != null) {
    					data.setRegion(rs.getString("region"));
    				} else {
    					data.setRegion("");
    				}
    				if (rs.getString("AIRPORTCODE") != null) {
    					data.setStation(rs.getString("AIRPORTCODE"));
    				} else {
    					data.setStation("");
    				}
    				/*if (rs.getString("pensionnumber") != null) {
    				 data.setPensionNumber(rs.getString("pensionnumber"));
    				 } else {
    				 data.setPensionNumber("");
    				 }*/
    				String pensionNumber=this.getPensionNumber(data.getEmpName(),data.getDateofBirth(),data.getEmpSerialNo(),"true");
    				data.setPensionNumber(pensionNumber);
    				
    				if (rs.getString("EMPLOYEENO") != null) {
    					data.setEmpNumber(rs.getString("EMPLOYEENO"));
    				} else {
    					data.setEmpNumber("");
    				}
    				
    				empinfo.add(data);
    			}
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} finally {
    		rs.close();
    		pst.close();
    		con.close();
    	}
    	log.info("PensionDAO :getProcessUnprocessList() leaving method");
    	return empinfo;
    }
    
    private boolean checkPensionNumberFromDB(String pensionnumber) {
    	int count = 0;
    	String[] employeeList = new String[4];
    	boolean flag = false;
    	Statement st = null;
    	Connection con = null;
    	ResultSet rs = null;
    	System.out.println("pensionnumber==============" + pensionnumber);
    	String query = "select pensionnumber from employee_info where  pensionnumber='"
    		+ pensionnumber + "' and pensionnumber is not null";
    	log.info("query is " + query);
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		
    		while (rs.next()) {
    			if (rs.getString("pensionnumber") != null) {
    				flag = true;
    				break;
    			}
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		commonDB.closeConnection(con, st, rs);
    	}
    	return flag;
    }
    
    /* .........getRegions()............ */
    public ArrayList getRegions() throws Exception {
    	boolean isCheckUser = false;
    	log.info("PensionDAO: getRegions() entering method");
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs2 = null;
    	ArrayList regions = new ArrayList();
    	String region = "";
    	int groupid = 0;
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		
    		String query2 = "select REGIONID,REGIONNAME from EMPLOYEE_REGION_MASTER";
    		
    		log.info("query is " + query2);
    		
    		rs2 = st.executeQuery(query2);
    		while (rs2.next()) {
    			PensionBean pbean = new PensionBean();
    			
    			if (rs2.getInt("REGIONID") != 0) {
    				pbean.setRegionId(rs2.getInt("REGIONID"));
    			} else {
    				pbean.setRegionId(0);
    			}
    			
    			if (rs2.getString("REGIONNAME") != null) {
    				pbean.setRegion(rs2.getString("REGIONNAME").toString()
    						.trim());
    			} else {
    				pbean.setRegion("");
    			}
    			regions.add(pbean);
    		}
    		
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	finally {
    		rs2.close();
    		st.close();
    		con.close();
    	}
    	log.info("PensionDAO: getRegions() leaving method");
    	return regions;
    }
    
    public ArrayList getLogList(UserBean bean) throws Exception {
    	
    	boolean isCheckUser = false;
    	log.info("PensionDAO: getLogList() entering method");
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ArrayList loglist = new ArrayList();
    	String region = "";
    	int groupid = 0;
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		StringBuffer whereClause = new StringBuffer();
    		StringBuffer query = new StringBuffer();
    		String query1 = "select username,TO_CHAR(LOGINTIME,'DD/MM/YYYY HH24:MI:SS') as LOGINTIME,COMPUTERNAME,region from employee_loginhistory";
    		if (!bean.getUsername().equals("")
    				&& !bean.getLoginFromDate().equals("")
    				&& !bean.getLoginToDate().equals("")) {
    			whereClause.append(" LOWER(username) like'%"
    					+ bean.getUserName().toLowerCase().trim() + "%'");
    			whereClause.append(" and ");
    			whereClause.append(" TO_CHAR(LOGINTIME,'DD/MM/YYYY') between '"
    					+ bean.getLoginFromDate().toLowerCase().trim()
    					+ "' and '"
    					+ bean.getLoginToDate().toLowerCase().trim() + "'");
    			whereClause.append(" and ");
    		}
    		else if (!bean.getUsername().equals("")
    				&& !bean.getLoginFromDate().equals("")) {
    			whereClause.append(" LOWER(username) like'%"
    					+ bean.getUserName().toLowerCase().trim() + "%'");
    			whereClause.append(" and ");
    			whereClause.append(" TO_CHAR(LOGINTIME,'DD/MM/YYYY') ='"
    					+ bean.getLoginFromDate().toLowerCase().trim() + "'");
    			whereClause.append(" and ");
    		} else if (!bean.getUsername().equals("")) {
    			whereClause.append(" LOWER(username) like'%"
    					+ bean.getUserName().toLowerCase().trim() + "%'");
    			whereClause.append(" Where ");
    		}
    		
    		else if (!bean.getLoginFromDate().equals("")) {
    			whereClause.append(" TO_CHAR(LOGINTIME,'DD/MM/YYYY') ='"
    					+ bean.getLoginFromDate().toLowerCase().trim() + "'");
    			whereClause.append(" Where ");
    		}
    		query.append(query1);
    		if (bean.getUsername().equals("")
    				&& bean.getLoginFromDate().equals("")
    				&& bean.getLoginToDate().equals("")) {
    		} else {
    			query.append(" Where ");
    			query.append(this.sTokenFormat(whereClause));
    		}
    		log.info("query is " + query);
    		rs = st.executeQuery(query.toString());
    		while (rs.next()) {
    			bean = new UserBean();
    			
    			if (rs.getString("username") != "") {
    				bean.setUserName(rs.getString("username"));
    			} else {
    				bean.setUserName("");
    			}
    			if (rs.getString("LOGINTIME") != null) {
    				bean.setLoginTime(rs.getString("LOGINTIME").toString()
    						.trim());
    			} else {
    				bean.setLoginTime("");
    			}
    			if (rs.getString("COMPUTERNAME") != null) {
    				bean.setComputerName(rs.getString("COMPUTERNAME")
    						.toString().trim());
    			} else {
    				bean.setComputerName("");
    			}
    			if (rs.getString("region") != null) {
    				bean.setRegion(rs.getString("region").toString().trim());
    			} else {
    				bean.setRegion("");
    			}
    			loglist.add(bean);
    		}
    		
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		rs.close();
    		st.close();
    		con.close();
    	}
    	log.info("PensionDAO: getLogList() leaving method");
    	return loglist;
    }
    
    
    public ArrayList retriveByAll(EmpMasterBean empbean,boolean flag) {
    	log.info("EmpMasterBean:retriveByAll-- Entering Method");
    	ArrayList searchData = new ArrayList();
    	EmpMasterBean data = null;
    	Connection con = null;
    	String sqlQuery = this.buildQuery(empbean,flag,"");
    	String sqlQueryMinrowId = "";
    	String empNameCheck="";
    	if (empbean.getEmpNameCheak().equals("true")) {
    		empNameCheck=" and employeename is null";
    		
    	}
    	if (flag) {
    		sqlQueryMinrowId = "select * from employee_info where ROWID IN("+ sqlQuery + ") "+empNameCheck+"  order by cpfacno";
    	} else {
    		sqlQueryMinrowId = "select * from employee_info_invalidData where ROWID IN("+ sqlQuery + ") order by cpfacno";
    	}
    	System.out.println(" Query is(retriveByAll)" + sqlQueryMinrowId);
    	try {
    		con = commonDB.getConnection();
    		Statement st = con.createStatement();
    		ResultSet rs = st.executeQuery(sqlQueryMinrowId);
    		while (rs.next()) {
    			data = new EmpMasterBean();
    			if (rs.getString("airportcode") != null) {
    				data.setStation(rs.getString("airportcode"));
    			} else {
    				data.setStation("---");
    			}
    			if (rs.getString("employeename") != null) {
    				data.setEmpName(rs.getString("employeename"));
    			} else {
    				data.setEmpName("---");
    			}
    			if (rs.getString("EMPLOYEENO") != null) {
    				data.setEmpNumber(rs.getString("EMPLOYEENO"));
    			} else {
    				data.setEmpNumber("---");
    			}
    			
    			if (rs.getString("desegnation") != null) {
    				data.setDesegnation(rs.getString("desegnation"));
    			} else {
    				data.setDesegnation("---");
    			}
    			
    			if (rs.getString("cpfacno") != null) {
    				data.setCpfAcNo(rs.getString("cpfacno"));
    			} else {
    				data.setCpfAcNo("---");
    			}
    			if (rs.getString("region") != null) {
    				data.setRegion(rs.getString("region"));
    			} else {
    				data.setRegion("");
    			}
    			if (rs.getString("FHNAME") != null) {
    				data.setFhName(rs.getString("FHNAME"));
    			} else {
    				data.setFhName("---");
    			}
    			if (rs.getString("dateofbirth") != null) {
    				data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    						.getDate("dateofbirth")));
    			} else {
    				data.setDateofBirth("---");
    			}
    			if (rs.getString("DATEOFJOINING") != null) {
    				data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    						.getDate("DATEOFJOINING")));
    			} else {
    				data.setDateofJoining("---");
    			}
    			
    			if (rs.getString("WETHEROPTION") != null) {
    				data.setWetherOption(rs.getString("WETHEROPTION"));
    			} else {
    				data.setWetherOption("---");
    			}
    			
    			if ((data.getCpfAcNo().equals("")
    					|| (data.getEmpNumber().equals(""))
    					|| (data.getDateofBirth().equals(""))
    					|| (data.getWetherOption().equals("")) || (data
    							.getEmpName().equals("")))) {
    				data.setRemarks("incomplete Data");
    				// log.info("inside if");
    			} else {
    				data.setRemarks("---");
    				// log.info("inside else");
    			}
    			
    			if (rs.getString("lastactive") != null) {
    				data.setLastActive(commonUtil.getDatetoString(rs
    						.getDate("lastactive"), "dd-MMM-yyyy"));
    			} else {
    				data.setLastActive("---");
    			}
    			if (rs.getString("sex") != null) {
    				data.setSex(rs.getString("sex"));
    			} else {
    				data.setSex("---");
    			}
    			if (rs.getString("division") != null) {
    				data.setDivision(rs.getString("division"));
    			} else {
    				data.setDivision("---");
    			}
    			
    			searchData.add(data);
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	log.info("EmpMasterbean:retriveByAll-- Leaving Method");
    	return searchData;
    	
    }
    
    public ArrayList compartiveRetriveByAll(EmpMasterBean empBean,String region) {
    	log.info("EmpMasterBean:compartiveRetriveByAll-- Entering Method");
    	ArrayList searchData = new ArrayList();
    	EmpMasterBean data = null;
    	Connection con = null;
    	Statement st =null;
    	ResultSet rs=null;
    	String query = this.compartiveBuildQuery(empBean,region);     
    	System.out.println(" Query is(compartiveRetriveByAll)" + query);
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		while (rs.next()) {
    			data = new EmpMasterBean();
    			
    			if (rs.getString("CPFACNO") != null) {
    				data.setCpfAcNo(rs.getString("CPFACNO"));
    			} else {
    				data.setCpfAcNo("---");
    			}
    			if (rs.getString("AIRPORTCODE") != null) {
    				data.setStation(rs.getString("AIRPORTCODE"));
    			} else {
    				data.setStation("---");
    			}
    			if (rs.getString("EMPLOYEENAME") != null) {
    				data.setEmpName(rs.getString("EMPLOYEENAME"));
    			} else {
    				data.setEmpName("---");
    			}
    			if (rs.getString("EMPLOYEENO") != null) {
    				data.setEmpNumber(rs.getString("EMPLOYEENO"));
    			} else {
    				data.setEmpNumber("---");
    			}
    			
    			if (rs.getString("DESEGNATION") != null) {
    				data.setDesegnation(rs.getString("DESEGNATION"));
    			} else {
    				data.setDesegnation("---");
    			}       
    			
    			if (rs.getString("DATEOFBIRTH") != null) {
    				data.setDateofBirth(commonUtil.converDBToAppFormat(rs
    						.getDate("DATEOFBIRTH")));
    			} else {
    				data.setDateofBirth("---");
    			}
    			if (rs.getString("DATEOFJOINING") != null) {
    				data.setDateofJoining(commonUtil.converDBToAppFormat(rs
    						.getDate("DATEOFJOINING")));
    			} else {
    				data.setDateofJoining("---");
    			}
    			
    			if (rs.getString("REGION") != null) {
    				data.setRegion(rs.getString("REGION"));
    			} else {
    				data.setRegion("---");
    			}
    			
    			if (rs.getString("SEX") != null) {
    				data.setSex(rs.getString("SEX"));
    			} else {
    				data.setSex("---");
    			}
    			
    			if (rs.getString("FHNAME") != null) {
    				data.setFhName(rs.getString("FHNAME"));
    			} else {
    				data.setFhName("---");
    			}           
    			
    			searchData.add(data);
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}finally{
    		commonDB.closeConnection(con, st, rs);
    		
    	}
    	log.info("EmpMasterbean:compartiveRetriveByAll-- Leaving Method");
    	return searchData;
    	
    }
    
    public String compartiveBuildQuery(EmpMasterBean empBean,String region) {
    	
    	log.info("EmpMasterbean:compartiveBuildQuery-- Entering Method");
    	StringBuffer whereClause = new StringBuffer();
    	StringBuffer query = new StringBuffer();
    	String dynamicQuery = "", prefixWhereClause = "", sqlQuery = "";
    	
    	String reg="",airportcode="";
    	
    	if (empBean.getRegion() != "") {
    		reg =empBean.getRegion();                    
    		
    		if((reg.substring(0,3)).equals("IAD"))
    		{
    			region=reg.substring(0,3);              
    			airportcode=reg.substring(4,reg.length());
    			
    		}
    		else
    			region=reg;         
    		
    	}
    	sqlQuery ="select CPFACNO,AIRPORTCODE,EMPLOYEENO,EMPLOYEENAME,DESEGNATION,DATEOFBIRTH,DATEOFJOINING,REGION,SEX,FHNAME from employee_westinfo";
    	if (!region.equals("")) {
    		whereClause.append(" region='" + region + "'");
    		whereClause.append(" AND ");
    	}    
    	if (!airportcode.equals("")) {
    		whereClause.append(" airportcode='" + airportcode + "'");
    		whereClause.append(" AND ");
    	}   
    	query.append(sqlQuery);
    	
    	if ((region.equals("")) && (airportcode.equals(""))) {
    	} 
    	else 
    	{
    		if (!prefixWhereClause.equals("")) {
    			query.append(" AND ");
    		} else {
    			query.append(" WHERE ");
    		}
    		query.append(this.sTokenFormat(whereClause));
    		
    		
    	}               
    	dynamicQuery = query.toString();
    	log.info("search dynamicQuery   " + dynamicQuery);
    	log.info("EmpMasterbean:buildQuery Leaving Method");
    	return dynamicQuery;
    }
    
    
    
    public EmpMasterBean editPensionMaster1(String cpfacno, String empName,
    		boolean flag, String empCode, String region) throws Exception {
    	log.info("PensionDAO:editPensionMaster1 entering method ");
    	EmpMasterBean editBean = new EmpMasterBean();
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;
    	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	String query = "",query1="";
    	if (flag == true) {
    		if (!cpfacno.equals("") && !empName.equals("")) {
    			
    			query=" select * from employee_info where ROWID IN(select MIN(ROWID) from employee_info" +
    			" where empflag='Y' AND LOWER(cpfacno) ='" + cpfacno.toLowerCase().trim()+ "' AND region='"+region.trim()+"' AND LOWER(employeename)='"+empName.toLowerCase()
    			+ "' group by cpfacno) order by cpfacno";
    			
    			log.info("query1" +query1);
    		} else if (!cpfacno.equals("")) {
    			query=" select * from employee_info where ROWID IN(select MIN(ROWID) from employee_info" +
    			" where empflag='Y' AND LOWER(cpfacno) ='" + cpfacno.toLowerCase()
    			+ "' AND region='" + region.trim() + "'  group by cpfacno) order by cpfacno";
    			
    			/*  query = "select * from employee_info where LOWER(cpfacno)='" + cpfacno.toLowerCase()
    			 + "' and region='" + region.trim() + "'";*/
    		}
    		else {
    			query=" select * from employee_info where ROWID IN(select MIN(ROWID) from employee_info" +
    			" where empflag='Y' AND LOWER(EMPLOYEENO) ='" +empCode.toLowerCase()+ "' AND region='" + region.trim() + "')";
    			//  query = "select * from employee_info where LOWER(employeename)='"   + empName.toLowerCase()+ "' AND LOWER(EMPLOYEENO)='"+ empCode.toLowerCase() + "'";
    		}
    	} else {
    		query = "select * from employee_info_invaliddata where cpfacno='"
    			+ cpfacno + "' AND LOWER(employeename)='"
    			+ empName.toLowerCase() + "'";
    	}
    	log.info("query is " + query);
    	try {
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		rs = st.executeQuery(query);
    		editBean.setCpfAcNo(cpfacno.trim());
    		while (rs.next()) {
    			
    			if (rs.getString("employeename") != null)
    				editBean.setEmpName(rs.getString("employeename").trim());
    			else
    				editBean.setEmpName("");
    			if (rs.getString("EMPLOYEENO") != null)
    				editBean.setEmpNumber(rs.getString("EMPLOYEENO").trim());
    			else
    				editBean.setEmpNumber("");
    			
    			if (rs.getString("desegnation") != null)
    				editBean.setDesegnation(rs.getString("desegnation").trim());
    			else
    				editBean.setDesegnation("");
    			
    			if (rs.getString("EMP_LEVEL") != null)
    				editBean.setEmpLevel(rs.getString("EMP_LEVEL"));
    			else
    				editBean.setEmpLevel("");
    			
    			if (rs.getString("DATEOFBIRTH") != null) {
    				editBean.setDateofBirth(commonUtil.converDBToAppFormat(rs.getString("DATEOFBIRTH").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
    				//editBean.setDateofBirth(CommonUtil.getStringtoDate(rs.getString("DATEOFBIRTH").toString()));
    			} else
    				editBean.setDateofBirth("");
    			if (rs.getString("DATEOFJOINING") != null)
    				//  editBean.setDateofJoining(CommonUtil.getStringtoDate(rs.getString("DATEOFJOINING").toString()));
    				editBean.setDateofJoining(commonUtil.converDBToAppFormat(rs
    						.getString("DATEOFJOINING").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
    			else
    				editBean.setDateofJoining("");
    			
    			if (rs.getString("DATEOFSEPERATION_REASON") != null)
    				editBean.setSeperationReason(rs
    						.getString("DATEOFSEPERATION_REASON"));
    			else
    				editBean.setSeperationReason("");
    			
    			if (rs.getString("DATEOFSEPERATION_DATE") != null)
    				//editBean.setDateofSeperationDate(CommonUtil.getStringtoDate(rs.getString("DATEOFSEPERATION_DATE").toString()));
    				editBean.setDateofSeperationDate(commonUtil.converDBToAppFormat(rs.getString(
    				"DATEOFSEPERATION_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
    			else
    				editBean.setDateofSeperationDate("");
    			if (rs.getString("WHETHER_OPTION_A") != null)
    				editBean
    				.setWhetherOptionA(rs.getString("WHETHER_OPTION_A"));
    			else
    				editBean.setWhetherOptionA("");
    			if (rs.getString("WHETHER_OPTION_B") != null)
    				editBean
    				.setWhetherOptionB(rs.getString("WHETHER_OPTION_B"));
    			else
    				editBean.setWhetherOptionB("");
    			if (rs.getString("WHETHER_OPTION_No") != null)
    				editBean.setWhetherOptionNO(rs
    						.getString("WHETHER_OPTION_No"));
    			else
    				editBean.setWhetherOptionNO("");
    			if (rs.getString("WHETHER_FORM1_NOM_RECEIVED") != null)
    				editBean.setForm2Nomination(rs
    						.getString("WHETHER_FORM1_NOM_RECEIVED"));
    			else
    				editBean.setForm2Nomination("");
    			
    			if (rs.getString("airportcode") != null)
    				editBean.setStation(rs.getString("airportcode"));
    			else
    				editBean.setStation("");
    			if (rs.getString("pensionnumber") != null)
    				editBean.setPensionNumber(rs.getString("pensionnumber"));
    			else
    				editBean.setPensionNumber("");
    			if (rs.getString("emailId") != null)
    				editBean.setEmailId(rs.getString("emailId"));
    			else
    				editBean.setEmailId("");
    			if (rs.getString("AIRPORTSERIALNUMBER") != null)
    				editBean.setAirportSerialNumber(rs
    						.getString("AIRPORTSERIALNUMBER"));
    			else
    				editBean.setAirportSerialNumber("");
    			if (rs.getString("employeeno") != null)
    				editBean.setEmpNumber(rs.getString("employeeno"));
    			else
    				editBean.setEmpNumber("");
    			if (rs.getString("remarks") != null)
    				editBean.setRemarks(rs.getString("remarks"));
    			else
    				editBean.setRemarks("");
    			if (rs.getString("sex") != null)
    				editBean.setSex(rs.getString("sex"));
    			else
    				editBean.setSex("");
    			
    			if (rs.getString("setDateOfAnnuation") != null)
    				//  editBean.setDateOfAnnuation(CommonUtil.getStringtoDate(rs.getString("setDateOfAnnuation")));
    				editBean.setDateOfAnnuation(commonUtil.converDBToAppFormat(rs
    						.getString("setDateOfAnnuation"),"yyyy-MM-dd","dd-MMM-yyyy"));
    			else
    				editBean.setDateOfAnnuation("");
    			if (rs.getString("fhname") != null)
    				editBean.setFhName(rs.getString("fhname"));
    			else
    				editBean.setFhName("");
    			if (rs.getString("maritalstatus") != null)
    				editBean.setMaritalStatus(rs.getString("maritalstatus"));
    			else
    				editBean.setMaritalStatus("");
    			log.info("PERMANENTADDRESS" + rs.getString("PERMANENTADDRESS"));
    			if (rs.getString("PERMANENTADDRESS") != null)
    				editBean.setPermanentAddress(rs
    						.getString("PERMANENTADDRESS"));
    			else
    				editBean.setPermanentAddress("");
    			
    			if (rs.getString("TEMPORATYADDRESS") != null)
    				editBean.setTemporatyAddress(rs
    						.getString("TEMPORATYADDRESS"));
    			else
    				editBean.setTemporatyAddress("");
    			if (rs.getString("wetherOption") != null) {
    				editBean.setWetherOption(rs.getString("wetherOption"));
    				log.info("wetheroption is" + editBean.getWetherOption());
    			} else
    				editBean.setWetherOption("");
    			
    			if (rs.getString("region") != null) {
    				editBean.setRegion(rs.getString("region"));
    				log.info("region is" + editBean.getRegion());
    			} else
    				editBean.setRegion("");
    			
    			if (rs.getString("otherreason") != null) {
    				editBean.setOtherReason(rs.getString("otherreason"));
    				log.info("otherreason is" + editBean.getOtherReason());
    			} else
    				editBean.setOtherReason("");
    			
    			if (rs.getString("department") != null) {
    				editBean.setDepartment(rs.getString("department"));
    				log.info("department is" + editBean.getDepartment());
    			} else
    				editBean.setDepartment("");
    			if (rs.getString("division") != null) {
    				editBean.setDivision(rs.getString("division"));
    				log.info("division is" + editBean.getDivision());
    			} else
    				editBean.setDivision("");
    			
    			if (rs.getString("empnomineesharable") != null) {
    				editBean.setEmpNomineeSharable(rs
    						.getString("empnomineesharable"));
    				log.info("empnomineesharable is"
    						+ editBean.getEmpNomineeSharable());
    			} else
    				editBean.setEmpNomineeSharable("");
    			
    			if (rs.getString("recordVerified") != null) {
    				editBean.setRecordVerified(rs.getString("recordVerified"));
    				log
    				.info("recordVerified is"
    						+ editBean.getRecordVerified());
    			} else
    				editBean.setRecordVerified("");
    			
    			if (rs.getString("region") != null) {
    				editBean.setRegion(rs.getString("region"));
    				log.info("region  is" + editBean.getRegion());
    			} else
    				editBean.setRegion("");
    			/* Editing Family records */
    			String query2 = "select * from employee_family_dtls where cpfaccno='"
    				+ cpfacno + "'";
    			rs1 = st.executeQuery(query2);
    			StringBuffer sbf = new StringBuffer();
    			String FAMILYMEMBERNAME = "", DATEOFBIRTH = "", FAMILYRELATION = "";
    			while (rs1.next()) {
    				if (rs.getString("FAMILYMEMBERNAME") != null) {
    					FAMILYMEMBERNAME = rs.getString("FAMILYMEMBERNAME");
    				} else {
    					FAMILYMEMBERNAME = "xxx";
    				}
    				if (rs.getString("DATEOFBIRTH") != null) {
    					
    					//  DATEOFBIRTH = CommonUtil.getStringtoDate(rs.getString("DATEOFBIRTH"));
    					DATEOFBIRTH = commonUtil.converDBToAppFormat(rs
    							.getString("DATEOFBIRTH"),"yyyy-MM-dd","dd-MMM-yyyy");
    				} else {
    					DATEOFBIRTH = "xxx";
    				}
    				if (rs.getString("FAMILYRELATION") != null) {
    					FAMILYRELATION = rs.getString("FAMILYRELATION");
    				} else {
    					FAMILYRELATION = "xxx";
    				}
    				log.info("FAMILYRELATION   " + FAMILYRELATION);
    				sbf.append(FAMILYMEMBERNAME + "@");
    				sbf.append(DATEOFBIRTH + "@");
    				sbf.append(FAMILYRELATION + "***");
    			}
    			log.info("family records " + sbf.toString());
    			editBean.setFamilyRow(sbf.toString());
    			/* End of Editing Family of records */
    			
    			/* Editing nominee records */
    			String query3 = "select * from employee_nominee_dtls where cpfaccno='"
    				+ cpfacno + "'";
    			rs2 = st.executeQuery(query3);
    			StringBuffer sbf1 = new StringBuffer();
    			while (rs2.next()) {
    				String nomineeName = "", nomineeAddress = "", nomineeDob = "";
    				String nomineeRelation = "", nameofGuardian = "", guardianAddress = "", totalShare = "";
    				
    				if (rs.getString("NOMINEENAME") != null) {
    					nomineeName = rs.getString("NOMINEENAME");
    				} else {
    					nomineeName = "xxx";
    				}
    				// nomineeName =rs.getString("NOMINEENAME");
    				if (rs.getString("NOMINEEADDRESS") != null) {
    					nomineeAddress = rs.getString("NOMINEEADDRESS");
    				} else {
    					nomineeAddress = "xxx";
    				}
    				if (rs.getString("NOMINEEDOB") != null) {
    					//nomineeDob = CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
    					nomineeDob = commonUtil.converDBToAppFormat(rs
    							.getString("NOMINEEDOB"),"yyyy-MM-dd","dd-MMM-yyyy");
    				} else {
    					nomineeDob = "xxx";
    				}
    				if (rs.getString("NOMINEERELATION") != null) {
    					nomineeRelation = rs.getString("NOMINEERELATION");
    				} else {
    					nomineeRelation = "xxx";
    				}
    				if (rs.getString("NAMEOFGUARDIAN") != null) {
    					nameofGuardian = rs.getString("NAMEOFGUARDIAN");
    					
    				} else {
    					nameofGuardian = "xxx";
    				}
    				if (rs.getString("GAURDIANADDRESS") != null) {
    					guardianAddress = rs.getString("GAURDIANADDRESS");
    					log.info("guardianAddress  " + guardianAddress);
    				} else {
    					guardianAddress = "xxx";
    				}
    				if (rs.getString("TOTALSHARE") != null) {
    					totalShare = rs.getString("TOTALSHARE");
    				} else {
    					totalShare = "xxx";
    				}
    				
    				// nomineeAddress=rs.getString("NOMINEEADDRESS");
    				// nomineeDob=CommonUtil.getStringtoDate(rs.getString("NOMINEEDOB"));
    				// nomineeRelation =rs.getString("NOMINEERELATION");
    				// nameofGuardian = rs.getString("NAMEOFGUARDIAN");
    				// totalShare = rs.getString("TOTALSHARE");
    				sbf1.append(nomineeName + "@");
    				sbf1.append(nomineeAddress + "@");
    				sbf1.append(nomineeDob + "@");
    				sbf1.append(nomineeRelation + "@");
    				sbf1.append(nameofGuardian + "@");
    				sbf1.append(guardianAddress + "@");
    				sbf1.append(totalShare + "***");
    				log.info(sbf1.toString());
    				// sbf1.append(nomineeRelation + "@");
    				
    			}
    			log.info("nominee records " + sbf1.toString());
    			editBean.setNomineeRow(sbf1.toString());
    		}
    		
    	} catch (Exception e) {
    		System.out.println("Exception is" + e.getMessage());
    	}
    	
    	finally {
    		rs.close();
    		st.close();
    		con.close();
    	}
    	log.info("PensionDAO:editPensionMaster1 leaving method ");
    	return editBean;
    }
    public int updatePensionMaster1(EmpMasterBean bean, String flag)
    throws InvalidDataException {
    	log.info("PensionDAO:updatePensionMaster1 entering method ");
//  	PensionBean editBean =new PensionBean();
    	Connection con = null;
    	Statement st = null;
    	int count = 0;
    	String srno = "", airportSerialNumber = "", empNumber = "", cpfAcNo = "", newCpfAcno = "";
    	String empName = "", desegnation = "", empLevel = "", seperationReason = "", whetherOptionA = "";
    	String whetherOptionB = "", whetherOptionNO = "", form2Nomination = "";
    	String remarks = "", station = "", dateofBirth = "", dateofJoining = "", dateofSeperationDate = "";
    	String fMemberName = "", fDateofBirth = "", frelation = "", familyrows = "";
    	String wetherOption = "", sex = "", maritalStatus = "", fhName = "", permanentAddress = "", temporatyAddress = "", dateOfAnnuation = "", otherReason = "";
    	airportSerialNumber = bean.getAirportSerialNumber();
    	empNumber = bean.getEmpNumber();
    	cpfAcNo = bean.getCpfAcNo().trim();
    	newCpfAcno = bean.getNewCpfAcNo();
    	station = bean.getStation();
    	empName = bean.getEmpName();
    	desegnation = bean.getDesegnation();
    	empLevel = bean.getEmpLevel();
    	seperationReason = bean.getSeperationReason();
    	whetherOptionA = bean.getWhetherOptionA();
    	whetherOptionB = bean.getWhetherOptionB();
    	whetherOptionNO = bean.getWhetherOptionNO();
    	remarks = bean.getRemarks();
    	dateofBirth = bean.getDateofBirth();
    	dateofJoining = bean.getDateofJoining();
    	dateofSeperationDate = bean.getDateofSeperationDate();
    	form2Nomination = bean.getForm2Nomination();
    	String pensionNumber = bean.getPensionNumber();
    	String empNomineeSharable = bean.getEmpNomineeSharable();
    	wetherOption = bean.getWetherOption();
    	sex = bean.getSex();
    	maritalStatus = bean.getMaritalStatus();
    	fhName = bean.getFhName();
    	permanentAddress = bean.getPermanentAddress();
    	temporatyAddress = bean.getTemporatyAddress();
    	dateOfAnnuation = bean.getDateOfAnnuation();
//  	String pensionNumber = this.getPensionNumber(empName.toUpperCase(),
//  	dateofBirth, cpfAcNo);
    	otherReason = bean.getOtherReason().trim();
    	String division = bean.getDivision();
    	String department = bean.getDepartment();
    	String emailId = bean.getEmailId();
    	String empOldName = bean.getEmpOldName();
    	String empOldNumber = bean.getEmpOldNumber();
    	String region = bean.getRegion();
    	String setRecordVerified = bean.getRecordVerified();
    	log.info("userName " + bean.getUserName());
    	log.info("computer Name" + bean.getComputerName());
    	java.util.Date now = new java.util.Date();
    	String MY_DATE_FORMAT = "dd-MM-yyyy hh:mm a";
    	String currDateTime = new SimpleDateFormat(MY_DATE_FORMAT).format(now);
    	log.info("date is  " + currDateTime);
    	
    	/*
    	 * Calendar cal = Calendar.getInstance();
    	 * 
    	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss a");
    	 * System.out.println("date is " +sdf.format(cal.getTime()));
    	 * sdf.format(cal.getTime());
    	 */
    	
    	try {
    		String query = "";
    		log.info("query is " + query);
    		String query1 = "select * from employee_pension_validate where  cpfaccno='"
    			+ cpfAcNo + "' and employeename='" + empName + "'";
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		String sql1 = "";
    		this.insertEmployeeHistory(cpfAcNo, "", true, "", region,
    				currDateTime, bean.getComputerName().trim(), bean
    				.getUserName());
    		
    		System.out.println("pensionNumber===========" + pensionNumber
    				+ "cpfAcNo" + cpfAcNo + "bean.getEmpOldNumber()"
    				+ bean.getEmpOldNumber());
    		System.out.println("region===========" + region + "dateofBirth"
    				+ dateofBirth);
    		//pensionNumber = checkPensionNumber(pensionNumber, cpfAcNo, bean.getEmpOldNumber(), empName, region.trim(), dateofBirth);
    		System.out.println("pensionNumber===New========"
    				+ pensionNumber);
    		
    		
    		String query2="";
    		
    		query2 = "update  EMPLOYEE_PERSONAL_INFO set airportcode='"
    			+ station + "',employeename='" + empName.trim()
    			+ "',desegnation='" + desegnation
    			+ "',AIRPORTSERIALNUMBER='" + airportSerialNumber
    			+ "',EMPLOYEENO='" + empNumber + "',EMP_LEVEL='"
    			+ empLevel + "',DATEOFBIRTH ='" + dateofBirth
    			+ "',DATEOFJOINING='" + dateofJoining
    			+ "',DATEOFSEPERATION_REASON='" + seperationReason
    			+ "',DATEOFSEPERATION_DATE='"
    			+ dateofSeperationDate + "',REMARKS='"
    			+ remarks.trim() + "',gender='" + sex
    			+ "',maritalStatus='" + maritalStatus
    			+ "',permanentAddress='" + permanentAddress
    			+ "',temporatyAddress='" + temporatyAddress
    			+ "',wetherOption='" + wetherOption
    			+ "', WHETHER_FORM1_NOM_RECEIVED ='"
    			+ form2Nomination + "',fhname='" + fhName
    			+ "',setDateOfAnnuation='" + dateOfAnnuation
    			+ "',REFPENSIONNUMBER='" + pensionNumber
    			+ "',otherreason='" + otherReason + "',division='"
    			+ division + "',department='" + department
    			+ "',emailId='" + emailId + "',lastactive='"
    			+ commonUtil.getCurrentDate("dd-MMM-yyyy")
    			+ "',empnomineesharable='" + empNomineeSharable
    			+ "',userName='" + bean.getUserName()
    			+ "',fhflag='"+bean.getFhFlag()+"'  where empflag='Y' and  cpfacno='" + cpfAcNo
    			+ "' and employeename='" + empName.trim()+ "' and region='"+ region.trim() + "'";
    		
    		log.info("query is " + query2);
    		st.executeUpdate(query2);
    		//EmpMaster Name updated to Transaction
    		String query3="update employee_pension_validate set MASTER_EMPNAME='"+empName.trim()+ "' where  cpfaccno='" + cpfAcNo.trim()+ "' and region='"+region.trim()+"'";
    		st.executeUpdate(query3);
    		log.info("query3 is " + query3);
    		
    		
    		
    	} catch (SQLException sqle) {
    		log.printStackTrace(sqle);
    		if (sqle.getErrorCode() == 00001) {
    			throw new InvalidDataException("PensionNumber Already Exist");
    		}
    	} catch (Exception e) {
    		log.printStackTrace(e);
    		throw new InvalidDataException(e.getMessage());
    	} finally {
    		commonDB.closeConnection(con, st, null);
    	}
    	log.info("PensionDAO:updatePensionMaster1 leaving method ");
    	return count;
    }
    public void deleteFamilyDetails(String familyMemberName,String rowid,String region,String cpfaccno){
    	
    	Connection con = null;
    	Statement st = null;  
    	try{
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		String deleteFamilyDetails="delete from EMPLOYEE_FAMILY_DTLS where CPFACCNO='"+cpfaccno+"' and FAMILYMEMBERNAME='"+familyMemberName+"' and rowid='"+rowid+"' and region='"+region+"'";
    		log.info("deleteFamilyDetails "+deleteFamilyDetails);
    		st.executeUpdate(deleteFamilyDetails);
    		
    	}catch(Exception e){
    		log.printStackTrace(e);
    	}
    }
    public void deleteNomineeDetils(String nomineeName,String rowid,String region,String cpfaccno){
    	
    	Connection con = null;
    	Statement st = null;  
    	try{
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		String deleteNomineeDetails="delete from employee_nominee_dtls where CPFACCNO='"+cpfaccno+"' and NOMINEENAME='"+nomineeName+"' and rowid='"+rowid+"' and region='"+region+"'";
    		log.info("deleteNomineeDetails "+deleteNomineeDetails);
    		st.executeUpdate(deleteNomineeDetails);
    		
    	}catch(Exception e){
    		log.printStackTrace(e);
    	}
    }
    
    public String readLables(String EmployeePensionValidate){
    	
    	Connection con = null;
    	Statement st = null;  
    	String dbLables="";
    	try{
    		con = commonDB.getConnection();
    		st = con.createStatement();
    		String query="select * from "+EmployeePensionValidate+"";
    		ResultSet rs=st.executeQuery(query);
    		ResultSetMetaData lableData= rs.getMetaData();
    		for(int i=1;i<lableData.getColumnCount();i++){
    			
    			dbLables+="-"+lableData.getColumnName(i);
    		}
    		
    	}catch(Exception e){
    		log.printStackTrace(e);
    	}
    	return dbLables;
    }
    public ArrayList getEmolumentslog(String pfId,String airportcode) {
        log.info("PensionDao:getEmolumentslog-- Entering Method");
        log.info("pfId==="+pfId);
        log.info("airportcode==="+airportcode);
      String sqlQuery = "",prefixWhereClause = "";
      EmolumentslogBean data = new EmolumentslogBean();
      Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList emolumentsloginfo =null;
    
   	    if(airportcode.equals("CHQNAD") || airportcode.equals("OFFICE COMPLEX"))
   	    {
        sqlQuery = "SELECT OLDEMOLUMENTS,NEWEMOLUMENTS,OLDEMPPFSTATUARY,NEWEMPPFSTATUARY,MONTHYEAR,UPDATEDDATE,region,pensionno from emoluments_log where pensionno='"+ pfId+"' and UPDATEDDATE>='2-Jul-2010'";
   	    } else
   	    {
   	    	sqlQuery = "SELECT OLDEMOLUMENTS,NEWEMOLUMENTS,OLDEMPPFSTATUARY,NEWEMPPFSTATUARY,MONTHYEAR,UPDATEDDATE,region,pensionno from emoluments_log where pensionno='"+ pfId+"' and UPDATEDDATE>='27-Aug-2010'";
   	    }
   	    
   	
       log.info("sql query "+sqlQuery); 
        try {
            con = commonDB.getConnection();
            st=con.createStatement();
           rs = st.executeQuery(sqlQuery.toString());
           emolumentsloginfo = new ArrayList(); 
            while(rs.next()) {
            	log.info("OLDEMOLUMENTS"+rs.getString("OLDEMOLUMENTS"));
            	log.info("NEWEMOLUMENTS"+rs.getString("NEWEMOLUMENTS"));
            	log.info("PENSIONNO"+rs.getString("PENSIONNO"));
            	log.info("MONTHYEAR"+commonUtil.converDBToAppFormat(rs.getDate("UPDATEDDATE")));
                 data = new EmolumentslogBean();
                if (rs.getString("OLDEMOLUMENTS") != null) {
                    data.setOldEmoluments(rs.getString("OLDEMOLUMENTS"));
                } else {
                    data.setOldEmoluments("");
                }             
              if (rs.getString("NEWEMOLUMENTS") != null) {
                    data.setNewEmoluments(rs.getString("NEWEMOLUMENTS"));
                } else {
                    data.setNewEmoluments("");
                }
                if (rs.getString("region") != null) {
                    data.setRegion(rs.getString("region"));
                } else {
                    data.setRegion("");
                }
                if (rs.getString("OLDEMPPFSTATUARY") != null) {
                    data.setOldEmppfstatury(rs.getString("OLDEMPPFSTATUARY"));
                } else {
                    data.setOldEmppfstatury("");
                }
                if (rs.getString("PENSIONNO") != null) {
                  data.setPensionNo(rs.getString("PENSIONNO"));
                 } else {
                     data.setPensionNo("");
                 }
               if (rs.getString("NEWEMPPFSTATUARY") != null) {
                    data.setNewEmppfstatury(rs.getString("NEWEMPPFSTATUARY"));
                } else {
                    data.setNewEmppfstatury("");
                }
                if (rs.getString("MONTHYEAR") != null) {
                    data.setMonthYear(commonUtil.converDBToAppFormat(rs.getDate("MONTHYEAR")));
                } else {
                    data.setMonthYear("");
                }
                if (rs.getString("UPDATEDDATE") != null) {
                	data.setUpdatedDate(commonUtil.converDBToAppFormat(rs.getDate("UPDATEDDATE")));
                } else {
                	data.setUpdatedDate("");

                }
                emolumentsloginfo.add(data);   
                }
         
            }catch(Exception e){
                
            }
            log.info("emolumentsloginfo list size " + emolumentsloginfo.size());
            return emolumentsloginfo;
        }
    public ArrayList getDeletelog(String pfId,String airportcode){
    	log.info("PensionDao:getDeletelog-- Entering Method");
        log.info("pfId==="+pfId);
        log.info("airportcode==="+airportcode);
        String sqlQuery = "";
        EmployeeValidateInfo data=new EmployeeValidateInfo();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList deleteloginfo=null;
        if(airportcode.equals("CHQNAD") || airportcode.equals("OFFICE COMPLEX"))
   	    {
        	sqlQuery="SELECT EMOLUMENTS,EMPPFSTATUARY,MONTHYEAR,USERNAME,IPADDRESS,UPDATEDDATE,PENSIONNO,REGION FROM EMPLOYEE_PENSION_VALIDATE WHERE PENSIONNO='"+ pfId+"' and empflag='N' AND UPDATEDDATE>='2-Jul-2010' and (monthyear>'31-Mar-1997' or monthyear between '1-Apr-1995' and '31-Mar-1996') and to_number(emoluments)!='0'";
   	    } else
   	    {
   	    	sqlQuery="SELECT EMOLUMENTS,EMPPFSTATUARY,MONTHYEAR,USERNAME,IPADDRESS,UPDATEDDATE,PENSIONNO,REGION FROM EMPLOYEE_PENSION_VALIDATE WHERE PENSIONNO='"+ pfId+"' and empflag='N' AND UPDATEDDATE>='27-Aug-2010' and (monthyear>'31-Mar-1997' or monthyear between '1-Apr-1995' and '31-Mar-1996') and to_number(emoluments)!='0'";	
   	    }
        log.info("sqlQuery  "+sqlQuery); 
        try {
        	 con = commonDB.getConnection();
        	st=con.createStatement();
            rs = st.executeQuery(sqlQuery.toString());
            deleteloginfo = new ArrayList(); 
             while(rs.next()) {            	
                  data = new EmployeeValidateInfo();
                 if (rs.getString("EMOLUMENTS") != null) {
                     data.setEmoluments(rs.getString("EMOLUMENTS"));
                 } else {
                     data.setEmoluments("--");
                 }             
               if (rs.getString("EMPPFSTATUARY") != null) {
                     data.setEmpPFStatuary(rs.getString("EMPPFSTATUARY"));
                 } else {
                     data.setEmpPFStatuary("--");
                 }
                 if (rs.getString("region") != null) {
                     data.setRegion(rs.getString("region"));
                 } else {
                     data.setRegion("--");
                 }                   
                 if (rs.getString("PENSIONNO") != null) {
                   data.setPensionNumber(rs.getString("PENSIONNO"));
                  } else {
                      data.setPensionNumber("--");
                  }                  
                 if (rs.getString("MONTHYEAR") != null) {
                     data.setMonthYear(commonUtil.converDBToAppFormat(rs.getDate("MONTHYEAR")));
                 } else {
                     data.setMonthYear("--");
                 }
                 if (rs.getString("UPDATEDDATE") != null) {
                 	data.setUpdatedDate(commonUtil.converDBToAppFormat(rs.getDate("UPDATEDDATE")));
                 } else {
                 	data.setUpdatedDate("--");

                 }
                 if (rs.getString("IPADDRESS") != null) {
                 	data.setIpAddress(rs.getString("IPADDRESS"));
                 } else {
                 	data.setIpAddress("--");

                 }
                 if (rs.getString("USERNAME") != null) {
                 	data.setUserName(rs.getString("USERNAME"));
                 } else {
                 	data.setUserName("--");

                 }
                 deleteloginfo.add(data);   
                 }
        	
        }catch(Exception e){
            
        }
    log.info("Deletelog list size " + deleteloginfo.size());
    return deleteloginfo;
    }
    public ArrayList getAdjlog(String pfId){
   	 	log.info("PensionDao:getAdjlog-- Entering Method");
   	     log.info("pfId==="+pfId);
   	     
   	     String sqlQuery = "";
   	     EmolumentslogBean data=new EmolumentslogBean();
   	     Connection con = null;
   	     Statement st = null;
   	     ResultSet rs = null;
   	     ArrayList adjloginfo=null;	    
   	     	sqlQuery="SELECT adjurl,ADJOBYEAR,PENSIONNO,EMPCONTRI,AAICONTRI,EMPCONTRIDEVIATION,AAICONTIDEVIATION,REMARKS FROM employee_yearwise_adjob WHERE PENSIONNO='"+ pfId+"'";
   	     log.info("sqlQuery  "+sqlQuery); 
   	     try {
   	     	 con = commonDB.getConnection();
   	     	st=con.createStatement();
   	         rs = st.executeQuery(sqlQuery.toString());
   	         adjloginfo = new ArrayList(); 
   	          while(rs.next()) {            	
   	               data = new EmolumentslogBean();
   	              if (rs.getString("ADJOBYEAR") != null) {
   	                  data.setAdjObYear(rs.getString("ADJOBYEAR"));
   	              } else {
   	                  data.setAdjObYear("--");
   	              }             
   	            if (rs.getString("PENSIONNO") != null) {
   	                  data.setPfId(rs.getString("PENSIONNO"));
   	              } else {
   	                  data.setPfId("--");
   	              }
   	              if (rs.getString("EMPCONTRI") != null) {
   	                  data.setEmpContri(rs.getString("EMPCONTRI"));
   	              } else {
   	                  data.setEmpContri("--");
   	              }                   
   	              if (rs.getString("AAICONTRI") != null) {
   	                data.setAaiContri(rs.getString("AAICONTRI"));
   	               } else {
   	                   data.setAaiContri("--");
   	               }                  
   	              if (rs.getString("EMPCONTRIDEVIATION") != null) {
   	                  data.setEmpContriDeviation(rs.getString("EMPCONTRIDEVIATION"));
   	              } else {
   	                  data.setEmpContriDeviation("--");
   	              }
   	              if (rs.getString("AAICONTIDEVIATION") != null) {
   	              	data.setAaiContriDeviation(rs.getString("AAICONTIDEVIATION"));
   	              } else {
   	              	data.setAaiContriDeviation("--");

   	              }
   	              
   	              if (rs.getString("REMARKS") != null) {
   	              	data.setRemarks(rs.getString("REMARKS"));
   	              } else {
   	              	data.setRemarks("--");

   	              }
   	              if (rs.getString("adjurl") != null) {
   		              	data.setAdjUrl(rs.getString("adjurl"));
   		              } else {
   		              	data.setAdjUrl("");

   		              }
   	              adjloginfo.add(data);   
   	              }
   	     	
   	     }catch(Exception e){
   	         
   	     }
   	 log.info("Deletelog list size " + adjloginfo.size());
   	 return adjloginfo;
   	 }
 

}
