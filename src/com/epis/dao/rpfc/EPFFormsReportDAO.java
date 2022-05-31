package com.epis.dao.rpfc;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.epis.bean.rpfc.epfforms.AAIEPFReportBean;
import com.epis.bean.rpfc.epfforms.AaiEpfForm11Bean;
import com.epis.bean.rpfc.epfforms.AaiEpfform3Bean;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;

public class EPFFormsReportDAO {
	 	Log log = new Log(EPFFormsReportDAO.class);
	    DBUtility commonDB = new DBUtility();
	    CommonUtil commonUtil = new CommonUtil();
	    CommonDAO commonDAO=new CommonDAO();
	    FinancialReportDAO finReportDAO=new FinancialReportDAO();
	    public ArrayList getEPFForm3Report(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
			DecimalFormat df = new DecimalFormat("#########0.00");
			String selPerQuery="",selQuery="",fromDate="",toDate="",selTransQuery="";
			String[] selectedYears=frmSelctedYear.split(",");
			fromDate=selectedYears[0];
			toDate=selectedYears[1];
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AaiEpfform3Bean epfForm3Bean=null;
			ArrayList form3DataList=new ArrayList();
			String regionDesc="",stationDesc="";
			if(!region.equals("NO-SELECT")){
				regionDesc=region;
			}else{
				regionDesc="";
			}
			if(!airprotcode.equals("NO-SELECT")){
				stationDesc=airprotcode;
			}else{
				stationDesc="";
			}
			selPerQuery=this.buildQueryEmpPFInfo(range,regionDesc,stationDesc,empNameFlag,empName,sortingOrder,frmPensionno);
			selTransQuery=this.buildQueryEmpPFTransInfo(regionDesc,stationDesc,sortingOrder,fromDate,toDate);
			selQuery="SELECT EMPFID.PENSIONNO as PENSIONNO, EPV.CPFACCNO as CPFACCNO, EPV.EMPLOYEENO as EMPLOYEENO, EMPFID.EMPLOYEENAME as EMPLOYEENAME, EMPFID.DATEOFJOINING as DATEOFJOINING, EMPFID.DESEGNATION as DESEGNATION,EMPFID.FHNAME as FHNAME, EMPFID.DATEOFBIRTH as DATEOFBIRTH, EMPFID.WETHEROPTION as WETHEROPTION, " +
			"EPV.MONTHYEAR AS MONTHYEAR, ROUND(EPV.EMOLUMENTS) AS EMOLUMENTS, EPV.EMPPFSTATUARY AS EMPPFSTATUARY, EPV.EMPVPF AS EMPVPF, EPV.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL, EPV.EMPADVRECINTEREST AS EMPADVRECINTEREST, " +
			"EPV.PENSIONCONTRI AS PENSIONCONTRI, EPV.PF AS PF, EPV.AIRPORTCODE AS AIRPORTCODE, EPV.REGION AS REGION, EPV.CPFACCNO AS CPFACCNO FROM ( " +
			selTransQuery +
			") EPV,("+selPerQuery+") EMPFID WHERE EMPFID.PENSIONNO = EPV.PENSIONNO  AND EPV.EMPFLAG = 'Y' ORDER BY EMPFID."+sortingOrder;
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				log.info("EPFFormsReportDAO::getEPFForm3Report===selQuery"+selQuery);
				rs = st.executeQuery(selQuery);
				double diff=0;
				while(rs.next()){
					double subTotal = 0.0,conTotal=0.0,pfstatutury=0;
					epfForm3Bean=new AaiEpfform3Bean();
					if(rs.getString("PENSIONNO")!=null){
						epfForm3Bean.setPensionno(rs.getString("PENSIONNO"));
						
					}
					
					if(rs.getString("CPFACCNO")!=null){
						epfForm3Bean.setCpfaccno(rs.getString("CPFACCNO"));
					}else{
						epfForm3Bean.setCpfaccno("---");   		
					}
					
					if(rs.getString("EMPLOYEENO")!=null){
						epfForm3Bean.setEmployeeNo(rs.getString("EMPLOYEENO"));   		
					}else{
						epfForm3Bean.setEmployeeNo("---");   		
					}
					if(rs.getString("EMPLOYEENAME")!=null){
						epfForm3Bean.setEmployeeName(rs.getString("EMPLOYEENAME"));   		
					}else{
						epfForm3Bean.setEmployeeName("---");   
					}
					if(rs.getString("DESEGNATION")!=null){
						epfForm3Bean.setDesignation(rs.getString("DESEGNATION"));   	
					}else{
						epfForm3Bean.setDesignation("---");   
					}
					if(rs.getString("FHNAME")!=null){
						epfForm3Bean.setFhName(rs.getString("FHNAME"));   
					}else{
						epfForm3Bean.setFhName("---");   
					}
					if(rs.getString("DATEOFBIRTH")!=null){
						epfForm3Bean.setDateOfBirth(commonUtil.converDBToAppFormat(rs.getDate("DATEOFBIRTH")));   
					}else{
						epfForm3Bean.setDateOfBirth("---");   
					}
					if(!epfForm3Bean.getDateOfBirth().equals("---")){
						String personalPFID = commonDAO.getPFID(epfForm3Bean.getEmployeeName(), epfForm3Bean
								.getDateOfBirth(), commonUtil.leadingZeros(5,epfForm3Bean.getPensionno()));
						epfForm3Bean.setPfID(personalPFID);
					}else{
						epfForm3Bean.setPfID(epfForm3Bean.getPensionno());
					}
					if(rs.getString("DATEOFJOINING")!=null){
						epfForm3Bean.setDateofJoining(commonUtil.converDBToAppFormat(rs.getDate("DATEOFJOINING")));   
					}else{
						epfForm3Bean.setDateofJoining("---");   
					}
					if(rs.getString("WETHEROPTION")!=null){
						epfForm3Bean.setWetherOption(rs.getString("WETHEROPTION"));
					}else{
						epfForm3Bean.setWetherOption("---");   
					}
					if(rs.getString("MONTHYEAR")!=null){
						epfForm3Bean.setMonthyear(commonUtil.converDBToAppFormat(rs.getDate("MONTHYEAR")));   
					}else{
						epfForm3Bean.setMonthyear("---");   
					}
					if(rs.getString("EMOLUMENTS")!=null){
						epfForm3Bean.setEmoluments(rs.getString("EMOLUMENTS"));   
					}else{
						epfForm3Bean.setEmoluments("0.00");   
					}
					if(rs.getString("EMPPFSTATUARY")!=null){
						
						epfForm3Bean.setEmppfstatury(rs.getString("EMPPFSTATUARY"));   
					}else{
						epfForm3Bean.setEmppfstatury("0.00");   
					}
					pfstatutury=Double.parseDouble(epfForm3Bean.getEmppfstatury());
					if(rs.getString("EMPVPF")!=null){
						epfForm3Bean.setEmpvpf(rs.getString("EMPVPF"));   
					}else{
						epfForm3Bean.setEmpvpf("0.00");   
					}
					if(rs.getString("EMPADVRECPRINCIPAL")!=null){
						epfForm3Bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));   
					}else{
						epfForm3Bean.setPrincipal("0.00");   
					}
					if(rs.getString("EMPADVRECINTEREST")!=null){
						epfForm3Bean.setInterest(rs.getString("EMPADVRECINTEREST"));   
					}else{
						epfForm3Bean.setInterest("0.00");   
					}
					
					subTotal = new Double(df.format(Double.parseDouble(epfForm3Bean
							.getEmppfstatury().trim())
							+ Double.parseDouble(epfForm3Bean.getEmpvpf().trim())
							+ Double.parseDouble(epfForm3Bean.getPrincipal().trim())
							+ Double.parseDouble(epfForm3Bean.getInterest().trim())))
							.doubleValue();
					epfForm3Bean.setSubscriptionTotal(Double.toString(subTotal));
					
					
					if(rs.getString("PENSIONCONTRI")!=null){
						epfForm3Bean.setPensionContribution(rs.getString("PENSIONCONTRI"));   
					}else{
						epfForm3Bean.setPensionContribution("0.00");   
					}
					if(rs.getString("PF")!=null){
						epfForm3Bean.setPf(rs.getString("PF"));   
					}else{
						epfForm3Bean.setPf("0.00");   
					}
					conTotal=new Double(df.format(Double.parseDouble(epfForm3Bean
							.getPensionContribution().trim())
							+ Double.parseDouble(epfForm3Bean.getPf().trim())))
							.doubleValue();
					
					epfForm3Bean.setContributionTotal(Double.toString(conTotal));
					diff=pfstatutury-conTotal;
					epfForm3Bean.setFormDifference(Double.toString(Math.round(diff)));
					if(diff!=0){
						epfForm3Bean.setHighlightedColor("bgcolor='lightblue'");
					}else{
						epfForm3Bean.setHighlightedColor("");
					}
					if(rs.getString("AIRPORTCODE")!=null){
						epfForm3Bean.setStation(rs.getString("AIRPORTCODE"));   
					}else{
						epfForm3Bean.setStation("---");   
					}
					if(rs.getString("REGION")!=null){
						epfForm3Bean.setRegion(rs.getString("REGION"));   
					}else{
						epfForm3Bean.setRegion("---");   
					}
					form3DataList.add(epfForm3Bean);
					

				}
				form3DataList=this.getEPFForm3MergerData(form3DataList,con,selPerQuery,fromDate,toDate,regionDesc,stationDesc,sortingOrder,frmPensionno);
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return form3DataList;
		}

		public ArrayList getForm6AReport(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
			DecimalFormat df = new DecimalFormat("#########0.00");
			DecimalFormat df1 = new DecimalFormat("#########0.0000000000000");
			String selPerQuery="",selQuery="",fromDate="",toDate="",selTransQuery="",dateOfRetriment="",days = "";
			long transMntYear = 0, empRetriedDt = 0;
			boolean chkDOBFlag=false;
			double retriredEmoluments = 0.0;
			int getDaysBymonth = 0;
		
			String[] selectedYears=frmSelctedYear.split(",");
			fromDate=selectedYears[0];
			toDate=selectedYears[1];
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AaiEpfform3Bean epfForm3Bean=null;
			String calEmoluments="";
			ArrayList form3DataList=new ArrayList();
			String regionDesc="",stationDesc="";
			if(!region.equals("NO-SELECT")){
				regionDesc=region;
			}else{
				regionDesc="";
			}
			if(!airprotcode.equals("NO-SELECT")){
				stationDesc=airprotcode;
			}else{
				stationDesc="";
			}
			selPerQuery=this.buildQueryForm6AEmpPFInfo(range,regionDesc,stationDesc,empNameFlag,empName,sortingOrder,frmPensionno,fromDate);
			selTransQuery=this.buildQueryEmpPFTransInfo(regionDesc,stationDesc,sortingOrder,fromDate,toDate);
			selQuery="SELECT EMPFID.PENSIONNO as PENSIONNO, EPV.CPFACCNO as CPFACCNO, EPV.EMPLOYEENO as EMPLOYEENO, EMPFID.EMPLOYEENAME as EMPLOYEENAME, EMPFID.DATEOFJOINING as DATEOFJOINING, EMPFID.DESEGNATION as DESEGNATION,EMPFID.FHNAME as FHNAME, EMPFID.DATEOFBIRTH as DATEOFBIRTH, EMPFID.WETHEROPTION as WETHEROPTION, " +
			"EPV.MONTHYEAR AS MONTHYEAR, ROUND(EPV.EMOLUMENTS) AS EMOLUMENTS, EPV.EMPPFSTATUARY AS EMPPFSTATUARY, EPV.EMPVPF AS EMPVPF, EPV.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL, EPV.EMPADVRECINTEREST AS EMPADVRECINTEREST, " +
			"EPV.PENSIONCONTRI AS PENSIONCONTRI, EPV.PF AS PF, EPV.AIRPORTCODE AS AIRPORTCODE, EPV.REGION AS REGION, EPV.CPFACCNO AS CPFACCNO FROM ( " +
			selTransQuery +
			") EPV,("+selPerQuery+") EMPFID WHERE EMPFID.PENSIONNO = EPV.PENSIONNO  AND EPV.EMPFLAG = 'Y' ORDER BY EMPFID."+sortingOrder;
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				log.info("EPFFormsReportDAO::getEPFForm3Report===selQuery"+selQuery);
				rs = st.executeQuery(selQuery);
				double diff=0;
				while(rs.next()){
					double subTotal = 0.0,conTotal=0.0,pfstatutury=0;
					epfForm3Bean=new AaiEpfform3Bean();
					if(rs.getString("PENSIONNO")!=null){
						epfForm3Bean.setPensionno(rs.getString("PENSIONNO"));
						
					}
					
					if(rs.getString("CPFACCNO")!=null){
						epfForm3Bean.setCpfaccno(rs.getString("CPFACCNO"));
					}else{
						epfForm3Bean.setCpfaccno("---");   		
					}
					
					if(rs.getString("EMPLOYEENO")!=null){
						epfForm3Bean.setEmployeeNo(rs.getString("EMPLOYEENO"));   		
					}else{
						epfForm3Bean.setEmployeeNo("---");   		
					}
					if(rs.getString("EMPLOYEENAME")!=null){
						epfForm3Bean.setEmployeeName(rs.getString("EMPLOYEENAME"));   		
					}else{
						epfForm3Bean.setEmployeeName("---");   
					}
					if(rs.getString("DESEGNATION")!=null){
						epfForm3Bean.setDesignation(rs.getString("DESEGNATION"));   	
					}else{
						epfForm3Bean.setDesignation("---");   
					}
					if(rs.getString("FHNAME")!=null){
						epfForm3Bean.setFhName(rs.getString("FHNAME"));   
					}else{
						epfForm3Bean.setFhName("---");   
					}
					if(rs.getString("DATEOFBIRTH")!=null){
						epfForm3Bean.setDateOfBirth(commonUtil.converDBToAppFormat(rs.getDate("DATEOFBIRTH")));   
					}else{
						epfForm3Bean.setDateOfBirth("---");   
					}
					if(!epfForm3Bean.getDateOfBirth().equals("---")){
						String personalPFID = commonDAO.getPFID(epfForm3Bean.getEmployeeName(), epfForm3Bean
								.getDateOfBirth(), commonUtil.leadingZeros(5,epfForm3Bean.getPensionno()));
						epfForm3Bean.setPfID(personalPFID);
					}else{
						epfForm3Bean.setPfID(epfForm3Bean.getPensionno());
					}
					if(rs.getString("DATEOFJOINING")!=null){
						epfForm3Bean.setDateofJoining(commonUtil.converDBToAppFormat(rs.getDate("DATEOFJOINING")));   
					}else{
						epfForm3Bean.setDateofJoining("---");   
					}
					if(rs.getString("WETHEROPTION")!=null){
						epfForm3Bean.setWetherOption(rs.getString("WETHEROPTION"));
					}else{
						epfForm3Bean.setWetherOption("---");   
					}
					if(rs.getString("MONTHYEAR")!=null){
						epfForm3Bean.setMonthyear(commonUtil.converDBToAppFormat(rs.getDate("MONTHYEAR")));   
					}else{
						epfForm3Bean.setMonthyear("---");   
					}
					if(rs.getString("EMOLUMENTS")!=null){
						epfForm3Bean.setEmoluments(rs.getString("EMOLUMENTS"));   
					}else{
						epfForm3Bean.setEmoluments("0");   
					}
					if(rs.getString("EMPPFSTATUARY")!=null){
						
						epfForm3Bean.setEmppfstatury(rs.getString("EMPPFSTATUARY"));   
					}else{
						epfForm3Bean.setEmppfstatury("0.00");   
					}
					pfstatutury=Double.parseDouble(epfForm3Bean.getEmppfstatury());
					if(rs.getString("EMPVPF")!=null){
						epfForm3Bean.setEmpvpf(rs.getString("EMPVPF"));   
					}else{
						epfForm3Bean.setEmpvpf("0.00");   
					}
					if(rs.getString("EMPADVRECPRINCIPAL")!=null){
						epfForm3Bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));   
					}else{
						epfForm3Bean.setPrincipal("0.00");   
					}
					if(rs.getString("EMPADVRECINTEREST")!=null){
						epfForm3Bean.setInterest(rs.getString("EMPADVRECINTEREST"));   
					}else{
						epfForm3Bean.setInterest("0.00");   
					}
					
					subTotal = new Double(df.format(Double.parseDouble(epfForm3Bean
							.getEmppfstatury().trim())
							+ Double.parseDouble(epfForm3Bean.getEmpvpf().trim())
							+ Double.parseDouble(epfForm3Bean.getPrincipal().trim())
							+ Double.parseDouble(epfForm3Bean.getInterest().trim())))
							.doubleValue();
					epfForm3Bean.setSubscriptionTotal(Double.toString(subTotal));
					
					
					if(rs.getString("PENSIONCONTRI")!=null){
						epfForm3Bean.setPensionContribution(rs.getString("PENSIONCONTRI"));   
					}else{
						epfForm3Bean.setPensionContribution("0.00");   
					}
					//log.info("Pension No"+epfForm3Bean.getPensionno()+"Emoluments"+epfForm3Bean.getEmoluments()+"Pension Contribution"+epfForm3Bean.getPensionContribution());
					
					if(rs.getString("PF")!=null){
						epfForm3Bean.setPf(rs.getString("PF"));   
					}else{
						epfForm3Bean.setPf("0.00");   
					}
					conTotal=new Double(df.format(Double.parseDouble(epfForm3Bean
							.getPensionContribution().trim())
							+ Double.parseDouble(epfForm3Bean.getPf().trim())))
							.doubleValue();
					
					epfForm3Bean.setContributionTotal(Double.toString(conTotal));
					diff=pfstatutury-conTotal;
					epfForm3Bean.setFormDifference(Double.toString(Math.round(diff)));
					if(diff!=0){
						epfForm3Bean.setHighlightedColor("");
					}else{
						epfForm3Bean.setHighlightedColor("");
					}
					if(rs.getString("AIRPORTCODE")!=null){
						epfForm3Bean.setStation(rs.getString("AIRPORTCODE"));   
					}else{
						epfForm3Bean.setStation("---");   
					}
					if(rs.getString("REGION")!=null){
						epfForm3Bean.setRegion(rs.getString("REGION"));   
					}else{
						epfForm3Bean.setRegion("---");   
					}
				
					
					
				
					calEmoluments = finReportDAO.calWages(epfForm3Bean.getEmoluments(),
							epfForm3Bean.getMonthyear(), epfForm3Bean.getWetherOption().trim(), false,
							false,"1");
					transMntYear = Date.parse(epfForm3Bean.getMonthyear());
					dateOfRetriment = finReportDAO.getRetriedDate(epfForm3Bean
							.getDateOfBirth());
					
					empRetriedDt = Date.parse(dateOfRetriment);
				
					if (transMntYear == empRetriedDt) {
						
						
						chkDOBFlag = true;
					}
					if (chkDOBFlag == true) {
						String[] dobList = epfForm3Bean.getDateOfBirth().split("-");
						days = dobList[0];
					
						getDaysBymonth = finReportDAO.getNoOfDays(epfForm3Bean.getDateOfBirth());
						
						retriredEmoluments = new Double(df1.format(Double
								.parseDouble(calEmoluments)
								* (Double.parseDouble(days) - 1)
								/ getDaysBymonth)).doubleValue();
						calEmoluments = Double.toString(retriredEmoluments);
					}
					epfForm3Bean.setEmoluments(Double.toString(Math.round(Double.parseDouble(calEmoluments))));  
			
					
					form3DataList.add(epfForm3Bean);
					
				}
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return form3DataList;
		}
		public String buildQueryEmpPFInfo(String range,String region,String airportcode, String empNameFlag,String empName,
				String sortedColumn,String pensionno) {
			log.info("EPFFormsReportDAO::buildQueryEmpPFInfo-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "";
			int startIndex=0,endIndex=0;
			sqlQuery="SELECT CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS," +
			"TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,OTHERREASON," +
			"decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB,ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE FROM EMPLOYEE_PERSONAL_INFO ";
			if(!range.equals("NO-SELECT")){
				String[] findRnge=range.split(" - ");
				startIndex=Integer.parseInt(findRnge[0]);
				endIndex=Integer.parseInt(findRnge[1]);
				
				whereClause.append("  PENSIONNO >="+startIndex+" AND PENSIONNO <="+endIndex);
				whereClause.append(" AND ");
				
			}
			
			/*	    	if (!region.equals("")) {
			 whereClause.append(" REGION ='"
			 + region + "'");
			 whereClause.append(" AND ");
			 }*/
			/*    	if (!airportcode.equals("")) {
			 whereClause.append(" AIRPORTCODE ='"
			 + airportcode + "'");
			 whereClause.append(" AND ");
			 }*/
			if (empNameFlag.equals("true")) {
				if (!empName.equals("") && !pensionno.equals("")) {
					whereClause.append("PENSIONNO='"
							+ pensionno + "' ");
					whereClause.append(" AND ");
				} 
			} 
			
			query.append(sqlQuery);
			if (/*(region.equals("")) && (airportcode.equals("")) &&*/ (range.equals("NO-SELECT")  && (empNameFlag.equals("false")))) {
				
			} else {
				query.append(" WHERE ");
				query.append(commonDAO.sTokenFormat(whereClause));
			}
			
			String orderBy = "ORDER BY "+sortedColumn;
			query.append(orderBy);
			dynamicQuery = query.toString();
			log.info("EPFFormsReportDAO::buildQueryEmpPFInfo Leaving Method");
			return dynamicQuery;
		}
		
		public String buildQueryForm6AEmpPFInfo(String range,String region,String airportcode, String empNameFlag,String empName,
				String sortedColumn,String pensionno,String fromDate) {
			log.info("EPFFormsReportDAO::buildQueryEmpPFInfo-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "";
			int startIndex=0,endIndex=0;
			sqlQuery="SELECT CPFACNO,EMPLOYEENO,INITCAP(EMPLOYEENAME) AS EMPLOYEENAME,DESEGNATION,EMP_LEVEL,DATEOFBIRTH,DATEOFJOINING,DATEOFSEPERATION_REASON,DATEOFSEPERATION_DATE,WHETHER_FORM1_NOM_RECEIVED,REMARKS,AIRPORTCODE,GENDER,FHNAME,MARITALSTATUS,PERMANENTADDRESS," +
			"TEMPORATYADDRESS,WETHEROPTION,SETDATEOFANNUATION,EMPFLAG,DIVISION,DEPARTMENT,EMAILID,EMPNOMINEESHARABLE,REGION,PENSIONNO,(LAST_DAY(dateofbirth) + INTERVAL '60' year ) as DOR,OTHERREASON," +
			"decode(sign(round(months_between(dateofjoining, '01-Apr-1995') / 12)),-1, '01-Apr-1995',1,to_char(dateofjoining,'dd-Mon-yyyy'),to_char(dateofjoining,'dd-Mon-yyyy')) as DATEOFENTITLE,to_char(trunc((dateofbirth + INTERVAL '60' year ), 'MM') - 1,'dd-Mon-yyyy')  as LASTDOB," +
			"ROUND(months_between('01-Apr-1995', dateofbirth) / 12) EMPAGE FROM EMPLOYEE_PERSONAL_INFO WHERE ROUND(months_between('"+fromDate+"', dateofbirth) / 12)<=58  ";
			
			if(!range.equals("NO-SELECT")){
				String[] findRnge=range.split(" - ");
				startIndex=Integer.parseInt(findRnge[0]);
				endIndex=Integer.parseInt(findRnge[1]);
				
				whereClause.append("  PENSIONNO >="+startIndex+" AND PENSIONNO <="+endIndex);
				whereClause.append(" AND ");
				
			}

			if (empNameFlag.equals("true")) {
				if (!empName.equals("") && !pensionno.equals("")) {
					whereClause.append("PENSIONNO='"
							+ pensionno + "' ");
					whereClause.append(" AND ");
				} 
			} 
			
			query.append(sqlQuery);
			if (/*(region.equals("")) && (airportcode.equals("")) &&*/ (range.equals("NO-SELECT")  && (empNameFlag.equals("false")))) {
				
			} else {
				query.append(" AND ");
				query.append(commonDAO.sTokenFormat(whereClause));
			}
			
			String orderBy = "ORDER BY "+sortedColumn;
			query.append(orderBy);
			dynamicQuery = query.toString();
			log.info("EPFFormsReportDAO::buildQueryEmpPFInfo Leaving Method");
			return dynamicQuery;
		}
		public String buildQueryEmpPFTransInfo(String region,String airportcode,String sortedColumn,String fromDate,String toDate) {
			log.info("EPFFormsReportDAO::buildQueryEmpPFTransInfo-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "";
			
			sqlQuery="SELECT MONTHYEAR, round(EMOLUMENTS) as EMOLUMENTS, round(EMPPFSTATUARY) AS EMPPFSTATUARY, round(EMPVPF) AS EMPVPF, CPF, round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL," +
			"round(EMPADVRECINTEREST) AS EMPADVRECINTEREST, round(AAICONPF) AS AAICONPF, ROUND(CPFADVANCE) AS CPFADVANCE, ROUND(DEDADV) AS DEDADV, ROUND(REFADV) AS REFADV, ROUND(PENSIONCONTRI) AS PENSIONCONTRI, ROUND(PF) AS PF, AIRPORTCODE, " +
			"REGION, EMPFLAG, CPFACCNO,PENSIONNO,EMPLOYEENO FROM EMPLOYEE_PENSION_VALIDATE WHERE EMPFLAG = 'Y' AND PENSIONNO IS NOT NULL  and MONTHYEAR between '"+fromDate+"' and LAST_DAY('"+toDate+"') ";
			
			
			if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
			if (!airportcode.equals("")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportcode + "'");
				whereClause.append(" AND ");
			}
			
			
			query.append(sqlQuery);
			if ((region.equals("")) && (airportcode.equals(""))) {
				
			} else {
				query.append(" AND ");
				query.append(commonDAO.sTokenFormat(whereClause));
			}
			
			String orderBy = "ORDER BY "+sortedColumn;
			query.append(orderBy);
			dynamicQuery = query.toString();
			log.info("EPFFormsReportDAO::buildQueryEmpPFTransInfo Leaving Method");
			return dynamicQuery;
		}
		public AAIEPFReportBean AAIEPFForm1Report(String range,String region,String airportcode, String frmSelectedDts,
				String empNameFlag, String empName, String sortedColumn,
				String pensionno) {   
			
			log.info("AAIEPFReportDAO::AAIEPFForm1Report");	   
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			String fromYear = "", toYear = "",dateOfRetriment="";
			AAIEPFReportBean AAIEPFBean=new AAIEPFReportBean();
			AAIEPFReportBean AAIEPFBean1=null;
			AAIEPFReportBean AAIEPFBean2=null;
			ArrayList AAIEPFReportList1 = new ArrayList();		       
			ArrayList AAIEPFReportList2 = new ArrayList();	
			ArrayList AAIEPFReportList = new ArrayList();
			
			log.info("......frmSelectedDts in DAO......."+frmSelectedDts);
			
			if(!frmSelectedDts.equals("")){
				
				String[] dateArr=frmSelectedDts.split(",");
				fromYear=dateArr[0];
				toYear=dateArr[1];
			}		        		       	
			
			String form1Query = this.buildQueryAAIEPFForm1Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno);
			log.info("-------form1Query------"+form1Query);
			
			String form1Qry = this.buildQueryforAAIEPFForm1Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno);
			log.info("-------form1Qry------"+form1Qry);
			
			try{
				
				con = commonDB.getConnection();
				st = con.createStatement();
				
				rs = st.executeQuery(form1Query);
				
				while (rs.next()) {
					AAIEPFBean1 = new AAIEPFReportBean();
					
					
					/* if (rs.getString("PENSIONNO") != null) {
					 AAIEPFBean.setPensionNo(rs.getString("PENSIONNO"));
					 } else {
					 AAIEPFBean.setPensionNo("--");
					 }*/
					
					if (rs.getString("CPFACNO") != null) {
						AAIEPFBean1.setCpfAccno(rs.getString("CPFACNO"));
					} else {
						AAIEPFBean1.setCpfAccno("--");
					}
					
					if (rs.getString("EMPLOYEENO") != null) {
						AAIEPFBean1.setEmployeeNumber(rs.getString("EMPLOYEENO"));
					} else {
						AAIEPFBean1.setEmployeeNumber("--");
					}
					
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean1.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean1.setEmployeeName("--");
					}
					
					if (rs.getString("DESEGNATION") != null) {
						AAIEPFBean1.setDesignation(rs.getString("DESEGNATION"));
					} else {
						AAIEPFBean1.setDesignation("--");
					}
					
					if (rs.getString("FHNAME") != null) {
						AAIEPFBean1.setFhName(rs.getString("FHNAME"));
					} else {
						AAIEPFBean1.setFhName("--");
					}
					
					
					if (rs.getString("DATEOFBIRTH") != null) {
						AAIEPFBean1.setDateOfBirth(CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean1.setDateOfBirth("--");
					}
					
					
					if (rs.getString("AIRPORTCODE") != null) {
						AAIEPFBean1.setAirportCode(rs.getString("AIRPORTCODE"));
					} else {
						AAIEPFBean1.setAirportCode("--");
					}
					
					if (rs.getString("REGION") != null) {
						AAIEPFBean1.setRegion(rs.getString("REGION"));
					} else {
						AAIEPFBean1.setRegion("--");
					}
					
					//log.info("-----Pension No----------"+AAIEPFBean.getPensionNo());
					
					if (rs.getString("PENSIONNO") != null) {
						AAIEPFBean1.setPensionNo(commonDAO.getPFID(AAIEPFBean1.getEmployeeName(),AAIEPFBean1.getDateOfBirth(),commonUtil.leadingZeros(5,
								rs.getString("PENSIONNO"))));                
					}else{
						AAIEPFBean1.setPensionNo("--");
					}
					
					if (rs.getString("SUBSCRIPTIONAMT") != null) {
						AAIEPFBean1.setSubscriptionAmt(rs.getString("SUBSCRIPTIONAMT"));
					} else {
						AAIEPFBean1.setSubscriptionAmt("0.00");
					}
					
					if (rs.getString("CONTRIBUTIONAMT") != null) {
						AAIEPFBean1.setContributionamt(rs.getString("CONTRIBUTIONAMT"));
					} else {
						AAIEPFBean1.setContributionamt("0.00");
					}
					
					if (rs.getString("OUTSTANDADV") != null) {
						AAIEPFBean1.setOutstandingAmt(rs.getString("OUTSTANDADV"));
					} else {
						AAIEPFBean1.setOutstandingAmt("0.00");
					}
					
					if (rs.getString("OBYEAR") != null) {
						AAIEPFBean1.setObYear(rs.getString("OBYEAR"));
					} else {
						AAIEPFBean1.setObYear("--");
					}
					
					AAIEPFReportList1.add(AAIEPFBean1);
				}
				
				rs2 = st.executeQuery(form1Qry);
				
				while (rs2.next()) {
					AAIEPFBean2 = new AAIEPFReportBean();
					
					if (rs.getString("CPFACCNO") != null) {
						AAIEPFBean2.setCpfAccno(rs.getString("CPFACCNO"));
					} else {
						AAIEPFBean2.setCpfAccno("--");
					}
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean2.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean2.setEmployeeName("--");
					}
					
					if (rs.getString("DESEGNATION") != null) {
						AAIEPFBean2.setDesignation(rs.getString("DESEGNATION"));
					} else {
						AAIEPFBean2.setDesignation("--");
					}
					if (rs2.getString("SUBSCRIPTIONAMT") != null) {
						AAIEPFBean2.setSubscriptionAmt(rs2.getString("SUBSCRIPTIONAMT"));
					} else {
						AAIEPFBean2.setSubscriptionAmt("0.00");
					}
					
					if (rs2.getString("CONTRIBUTIONAMT") != null) {
						AAIEPFBean2.setContributionamt(rs2.getString("CONTRIBUTIONAMT"));
					} else {
						AAIEPFBean2.setContributionamt("0.00");
					}
					
					if (rs2.getString("OUTSTANDADV") != null) {
						AAIEPFBean2.setOutstandingAmt(rs2.getString("OUTSTANDADV"));
					} else {
						AAIEPFBean2.setOutstandingAmt("0.00");
					}
					
					if (rs2.getString("OBYEAR") != null) {
						AAIEPFBean2.setObYear(rs2.getString("OBYEAR"));
					} else {
						AAIEPFBean2.setObYear("--");
					}
					
					AAIEPFReportList2.add(AAIEPFBean2);
					
				}
				
				
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}	        
			
			log.info("===========AAIEPFReportList1 Size in DAO====="+AAIEPFReportList1.size());
			log.info("===========AAIEPFReportList2 Size in DAO====="+AAIEPFReportList2.size());
			
			
			AAIEPFBean.setReportList1(AAIEPFReportList1);
			AAIEPFBean.setReportList2(AAIEPFReportList2);
			
			return AAIEPFBean;
			
			
		}
		public String buildQueryforAAIEPFForm1Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno) {
			log.info("AAIEPFReportDAO::buildQueryforAAIEPFForm1Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			
			sqlQuery="select CPFACCNO,EMPLOYEENAME,DESEGNATION,EMPNETOB AS SUBSCRIPTIONAMT,AAINETOB AS CONTRIBUTIONAMT,OUTSTANDADV,OBYEAR AS OBYEAR  FROM  employee_pension_ob EPO WHERE PENSIONNO IS NULL AND NEWJOINEEREMARKS='N' AND EMPFLAG='Y' ";
			
			
			if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
			
			if (!airportcode.equals("NO-SELECT")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportcode + "'");
				whereClause.append(" AND ");
			}
			
			
			query.append(sqlQuery);
			if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT"))) {
			} else {
				query.append(" AND ");
				query.append(commonDAO.sTokenFormat(whereClause));
			} 
			
			dynamicQuery = query.toString();
			log.info("AAIEPFReportDAO::buildQueryforAAIEPFForm1Report Leaving Method");
			return dynamicQuery;
			
			
		} 
		public String buildQueryAAIEPFForm1Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno) {
			log.info("AAIEPFReportDAO::buildQueryAAIEPFForm1Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			
			sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPO.EMPNETOB AS SUBSCRIPTIONAMT,EPO.AAINETOB AS CONTRIBUTIONAMT,EPO.OUTSTANDADV,EPO.OBYEAR AS OBYEAR  FROM employee_personal_info EPI, employee_pension_ob EPO WHERE EPI.PENSIONNO = EPO.PENSIONNO  AND EPO.NEWJOINEEREMARKS='N'  AND EPO.EMPFLAG='Y' and EPO.OBYEAR BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
			
			if(!range.equals("NO-SELECT")){
				String[] findRnge=range.split(" - ");
				startIndex=Integer.parseInt(findRnge[0]);
				endIndex=Integer.parseInt(findRnge[1]);
				
				whereClause.append("  EPI.PENSIONNO >="+startIndex+" AND EPI.PENSIONNO <="+endIndex);
				whereClause.append(" AND ");
				
			}
			
			if (!region.equals("")) {
				whereClause.append(" EPO.REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
			
			if (!airportcode.equals("NO-SELECT")) {
				whereClause.append(" EPO.AIRPORTCODE ='"
						+ airportcode + "'");
				whereClause.append(" AND ");
			}
			
			if (empNameFlag.equals("true")) {
				if (!empName.equals("") && !pensionno.equals("")) {
					whereClause.append("EPI.PENSIONNO='"
							+ pensionno + "' ");
					whereClause.append(" AND ");
				} 
			} 
			
			query.append(sqlQuery);
			if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT")) && (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {
				
			} else {
				query.append(" AND ");
				query.append(commonDAO.sTokenFormat(whereClause));
			} 
			orderBy = " ORDER BY  EPI."+sortedColumn+" ASC";
			query.append(orderBy);
			dynamicQuery = query.toString();
			log.info("AAIEPFReportDAO::buildQueryAAIEPFForm1Report Leaving Method");
			return dynamicQuery;
			
			
		}
		public AAIEPFReportBean AAIEPFForm8Report(String range,String region,String airportcode, String frmSelectedDts,
				String empNameFlag, String empName, String sortedColumn,
				String pensionno) {
			
			
			
			log.info("AAIEPFReportDAO::AAIEPFForm8Report");	   
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String fromYear = "", toYear = "",dateOfRetriment="";
			AAIEPFReportBean AAIEPFBean=new AAIEPFReportBean();
			ArrayList AAIEPFReportList = new ArrayList();
			ArrayList  AdvanceList=new ArrayList();
			ArrayList  LoansList=new ArrayList();
			ArrayList  FinalSettlementList=new ArrayList();
			
			ArrayList  AdvanceList2=new ArrayList();
			ArrayList  LoansList2=new ArrayList();
			ArrayList  FinalSettlementList2=new ArrayList();
			
			
			log.info("......frmSelectedDts in DAO......."+frmSelectedDts);
			
			if(!frmSelectedDts.equals("")){
				
				String[] dateArr=frmSelectedDts.split(",");
				fromYear=dateArr[0];
				toYear=dateArr[1];
			}
			
			try{
				
				con = commonDB.getConnection();
				AdvanceList = this.buildQueryAdvanceForm8Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno,con);
				log.info("-------AdvanceList Size in DAO------"+AdvanceList.size());
				
				LoansList = this.buildQueryLoansForm8Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno,con);
				log.info("-------LoansList Size in DAO------"+LoansList.size());
				
				FinalSettlementList = this.buildQueryFinalSttlementForm8Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno,con);
				log.info("-------FinalSettlementList Size in DAO------"+FinalSettlementList.size());
				
				
				AdvanceList2 = this.buildQuery2AdvanceForm8Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno,con);
				log.info("----/////////---AdvanceList2 Size in DAO------"+AdvanceList2.size());
				
				
				LoansList2 = this.buildQuery2LoansForm8Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno,con);
				log.info("---///////----LoansList2 Size in DAO------"+LoansList2.size());
				
				
				FinalSettlementList2 = this.buildQuery2FinalSttlementForm8Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno,con);
				log.info("--////////-----FinalSettlementList2 Size in DAO------"+FinalSettlementList2.size());
				
				
				AAIEPFBean.setAdvancesList(AdvanceList);
				AAIEPFBean.setLonesList(LoansList);
				AAIEPFBean.setFinalSettlementList(FinalSettlementList);
				
				AAIEPFBean.setAdvancesList2(AdvanceList2);
				AAIEPFBean.setLonesList2(LoansList2);
				AAIEPFBean.setFinalSettlementList2(FinalSettlementList2);
				
		
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				//commonDB.closeConnection(con, st, rs);
			}	
			
			//log.info("===========AAIEPFReportList Size in DAO====="+AAIEPFReportList.size());
			return AAIEPFBean;
			
			
		}
		public ArrayList buildQuery2AdvanceForm8Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno,Connection con) {
			
			
			log.info("AAIEPFReportDAO::buildQuery2AdvanceForm8Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			Statement st = null;
			ResultSet rs = null;
			AAIEPFReportBean  AAIEPFBean=null;
			ArrayList advancesList = new ArrayList();
			
			//sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPO.EMPNETOB AS SUBSCRIPTIONAMT,EPO.AAINETOB AS CONTRIBUTIONAMT,EPO.OUTSTANDADV,EPO.OBYEAR AS OBYEAR  FROM employee_personal_info EPI, employee_pension_ob EPO WHERE EPI.PENSIONNO = EPO.PENSIONNO and EPO.OBYEAR BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
			try{
				st = con.createStatement();
				
				sqlQuery="select CPFACCNO,EMPLOYEENO,EMPLOYEENAME,ADVPURPOSE AS PURPOSE,AMOUNT AS AMOUNT,ADVTRANSDATE AS ADVTRANSDATE  FROM  employee_pension_advances where PENSIONNO is null and ADVTRANSDATE BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
				
				
				
				if (!region.equals("NO-SELECT")) {
					whereClause.append(" REGION ='"
							+ region + "'");
					whereClause.append(" AND ");
				}
				
				if (!airportcode.equals("NO-SELECT")) {
					whereClause.append(" AIRPORTCODE ='"
							+ airportcode + "'");
					whereClause.append(" AND ");
				}
				
				
				query.append(sqlQuery);
				if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT"))) {
				} else {
					query.append(" AND ");
					query.append(commonDAO.sTokenFormat(whereClause));
				} 				
				dynamicQuery = query.toString();
				
				log.info("-------buildQuery2AAIEPFForm8Report:sqlQuery---------"+sqlQuery);
				
				rs = st.executeQuery(dynamicQuery);
				
				
				while (rs.next()) {
					AAIEPFBean = new AAIEPFReportBean();
					
					if (rs.getString("CPFACCNO") != null) {
						AAIEPFBean.setCpfAccno(rs.getString("CPFACCNO"));
					} else {
						AAIEPFBean.setCpfAccno("--");
					}
					
					if (rs.getString("EMPLOYEENO") != null) {
						AAIEPFBean.setEmployeeNumber(rs.getString("EMPLOYEENO"));
					} else {
						AAIEPFBean.setEmployeeNumber("--");
					}
					
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean.setEmployeeName("--");
					}	                 
					
					if (rs.getString("PURPOSE") != null) {
						AAIEPFBean.setAdvPurpose(rs.getString("PURPOSE"));
					} else {
						AAIEPFBean.setAdvPurpose("--");
					}	                 
					
					if (rs.getString("AMOUNT") != null) {
						AAIEPFBean.setAdvAmt(rs.getString("AMOUNT"));
					} else {
						AAIEPFBean.setAdvAmt("--");
					}	                 
					
					if (rs.getString("ADVTRANSDATE") != null) {
						AAIEPFBean.setTransDate(CommonUtil.getDatetoString(rs.getDate("ADVTRANSDATE"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setTransDate("--");
					}	                  
					
					advancesList.add(AAIEPFBean);        
					
				}
				
			}catch (SQLException e) {
				log.printStackTrace(new Exception("------SQL-----"+e));
			} catch (Exception e) {
				log.printStackTrace(e);
				e.printStackTrace();
			} finally {
				//commonDB.closeConnection(con, st, rs);
			}	
			
			log.info("*********------advancesList------*********"+advancesList.size());
			log.info("AAIEPFReportDAO::buildQuery2AdvanceForm8Report Leaving Method");
			return advancesList;
			
		}


		public ArrayList buildQuery2LoansForm8Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno,Connection con) {
			log.info("AAIEPFReportDAO::buildQuery2LoansForm8Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			Statement st = null;
			ResultSet rs = null;
			AAIEPFReportBean  AAIEPFBean=null;
			ArrayList loansList = new ArrayList();
			
			//sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPO.EMPNETOB AS SUBSCRIPTIONAMT,EPO.AAINETOB AS CONTRIBUTIONAMT,EPO.OUTSTANDADV,EPO.OBYEAR AS OBYEAR  FROM employee_personal_info EPI, employee_pension_ob EPO WHERE EPI.PENSIONNO = EPO.PENSIONNO and EPO.OBYEAR BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
			try{
				st = con.createStatement();
				
				sqlQuery="select CPFACCNO,EMPLOYEENO,EMPLOYEENAME,LOANPURPOSE AS LOANPURPOSE,SUB_AMT AS EMPSHARE,CONT_AMT AS AAISHARE,LOANDATE AS LOANDATE  FROM  employee_pension_loans EPL WHERE PENSIONNO IS NULL and LOANDATE BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
				
				
				if (!region.equals("NO-SELECT")) {
					whereClause.append(" EPL.REGION ='"
							+ region + "'");
					whereClause.append(" AND ");
				}
				
				if (!airportcode.equals("NO-SELECT")) {
					whereClause.append(" EPL.AIRPORTCODE ='"
							+ airportcode + "'");
					whereClause.append(" AND ");
				}
				
				
				query.append(sqlQuery);
				if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT"))) {
					
				} else {
					query.append(" AND ");
					query.append(commonDAO.sTokenFormat(whereClause));
				} 
				//orderBy = " ORDER BY  EPI."+sortedColumn+" ASC";
				//query.append(orderBy);
				dynamicQuery = query.toString();
				
				log.info("-------buildQuery2AAIEPFForm8Report:sqlQuery---------"+sqlQuery);
				
				rs = st.executeQuery(dynamicQuery);
				
				
				while (rs.next()) {
					AAIEPFBean = new AAIEPFReportBean();
					
					if (rs.getString("CPFACCNO") != null) {
						AAIEPFBean.setCpfAccno(rs.getString("CPFACCNO"));
					} else {
						AAIEPFBean.setCpfAccno("--");
					}
					
					if (rs.getString("EMPLOYEENO") != null) {
						AAIEPFBean.setEmployeeNumber(rs.getString("EMPLOYEENO"));
					} else {
						AAIEPFBean.setEmployeeNumber("--");
					}
					
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean.setEmployeeName("--");
					}
					
					
					if (rs.getString("LOANPURPOSE") != null) {
						AAIEPFBean.setLoanPurpose(rs.getString("LOANPURPOSE"));
					} else {
						AAIEPFBean.setLoanPurpose("--");
					}
					
					
					if (rs.getString("EMPSHARE") != null) {
						AAIEPFBean.setEmpShare(rs.getString("EMPSHARE"));
					} else {
						AAIEPFBean.setEmpShare("--");
					}
					
					if (rs.getString("AAISHARE") != null) {
						AAIEPFBean.setAAIShare(rs.getString("AAISHARE"));
					} else {
						AAIEPFBean.setAAIShare("--");
					}
					
					
					if (rs.getString("LOANDATE") != null) {
						AAIEPFBean.setTransDate(CommonUtil.getDatetoString(rs.getDate("LOANDATE"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setTransDate("--");
					}
					
					loansList.add(AAIEPFBean);             	                 
				}
				
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				//commonDB.closeConnection(con, st, rs);
			}	
			
			log.info("*********------loansList size------*********"+loansList.size());
			log.info("AAIEPFReportDAO::buildQuery2LoansForm8Report Leaving Method");
			return loansList;
			
			
		}
		public ArrayList buildQuery2FinalSttlementForm8Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno,Connection con) {
			log.info("AAIEPFReportDAO::buildQuery2FinalSttlementForm8Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			Statement st = null;
			ResultSet rs = null;
			AAIEPFReportBean  AAIEPFBean=null;
			ArrayList settlementList = new ArrayList();
			
			//sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPO.EMPNETOB AS SUBSCRIPTIONAMT,EPO.AAINETOB AS CONTRIBUTIONAMT,EPO.OUTSTANDADV,EPO.OBYEAR AS OBYEAR  FROM employee_personal_info EPI, employee_pension_ob EPO WHERE EPI.PENSIONNO = EPO.PENSIONNO and EPO.OBYEAR BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
			try{
				st = con.createStatement();
				
				sqlQuery="select CPFACCNO,EMPLOYEENO,EMPLOYEENAME,PURPOSE AS REASON,FINEMP AS EMPSHARE,FINAAI AS AAISHARE,PENCON AS PENSIONCONTRIBUTION,NETAMOUNT AS NETAMOUNT,SETTLEMENTDATE AS SETTLEMENTDATE  FROM  employee_pension_finsettlement  WHERE PENSIONNO IS NULL and SETTLEMENTDATE  BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
				
				
				if (!region.equals("NO-SELECT")) {
					whereClause.append(" REGION ='"
							+ region + "'");
					whereClause.append(" AND ");
				}
				
				if (!airportcode.equals("NO-SELECT")) {
					whereClause.append(" AIRPORTCODE ='"
							+ airportcode + "'");
					whereClause.append(" AND ");
				}
				
				
				query.append(sqlQuery);
				if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT"))) {
					
				} else {
					query.append(" AND ");
					query.append(commonDAO.sTokenFormat(whereClause));
				} 
				
				dynamicQuery = query.toString();
				
				log.info("-------buildQuery2AAIEPFForm8Report:sqlQuery---------"+sqlQuery);
				
				rs = st.executeQuery(dynamicQuery);
				
				
				while (rs.next()) {
					AAIEPFBean = new AAIEPFReportBean();
					
					if (rs.getString("CPFACCNO") != null) {
						AAIEPFBean.setCpfAccno(rs.getString("CPFACCNO"));
					} else {
						AAIEPFBean.setCpfAccno("--");
					}
					
					if (rs.getString("EMPLOYEENO") != null) {
						AAIEPFBean.setEmployeeNumber(rs.getString("EMPLOYEENO"));
					} else {
						AAIEPFBean.setEmployeeNumber("--");
					}
					
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean.setEmployeeName("--");
					}
					
					
					
					if (rs.getString("REASON") != null) {
						AAIEPFBean.setSettlementReason(rs.getString("REASON"));
					} else {
						AAIEPFBean.setSettlementReason("--");
					}
					
					
					if (rs.getString("EMPSHARE") != null) {
						AAIEPFBean.setEmpShare(rs.getString("EMPSHARE"));
					} else {
						AAIEPFBean.setEmpShare("--");
					}
					
					if (rs.getString("AAISHARE") != null) {
						AAIEPFBean.setAAIShare(rs.getString("AAISHARE"));
					} else {
						AAIEPFBean.setAAIShare("--");
					}
					
					if (rs.getString("PENSIONCONTRIBUTION") != null) {
						AAIEPFBean.setPensionContribution(rs.getString("PENSIONCONTRIBUTION"));
					} else {
						AAIEPFBean.setPensionContribution("--");
					}
					
					if (rs.getString("NETAMOUNT") != null) {
						AAIEPFBean.setNetAmt(rs.getString("NETAMOUNT"));
					} else {
						AAIEPFBean.setNetAmt("--");
					}
					
					
					if (rs.getString("SETTLEMENTDATE") != null) {
						AAIEPFBean.setTransDate(CommonUtil.getDatetoString(rs.getDate("SETTLEMENTDATE"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setTransDate("--");
					}
					
					settlementList.add(AAIEPFBean);             	                 
				}
				
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				// commonDB.closeConnection(con, st, rs);
			}	
			
			log.info("*********------settlementList size------*********"+settlementList.size());
			log.info("AAIEPFReportDAO::buildQuery2FinalSttlementForm8Report Leaving Method");
			return settlementList;
			
			
		}		  	  
		
		public ArrayList buildQueryAdvanceForm8Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno,Connection con) {
			log.info("AAIEPFReportDAO::buildQuery1AAIEPFForm8Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			Statement st = null;
			ResultSet rs = null;
			AAIEPFReportBean  AAIEPFBean=null;
			ArrayList advancesList = new ArrayList();
			
			//sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPO.EMPNETOB AS SUBSCRIPTIONAMT,EPO.AAINETOB AS CONTRIBUTIONAMT,EPO.OUTSTANDADV,EPO.OBYEAR AS OBYEAR  FROM employee_personal_info EPI, employee_pension_ob EPO WHERE EPI.PENSIONNO = EPO.PENSIONNO and EPO.OBYEAR BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
			try{
				st = con.createStatement();
				sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPA.ADVPURPOSE AS PURPOSE,EPA.AMOUNT AS AMOUNT,EPA.ADVTRANSDATE AS ADVTRANSDATE  FROM employee_personal_info EPI, employee_pension_advances EPA WHERE EPI.PENSIONNO = EPA.PENSIONNO and EPA.ADVTRANSDATE BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
				
				if(!range.equals("NO-SELECT")){
					String[] findRnge=range.split(" - ");
					startIndex=Integer.parseInt(findRnge[0]);
					endIndex=Integer.parseInt(findRnge[1]);
					
					whereClause.append("  EPI.PENSIONNO >="+startIndex+" AND EPI.PENSIONNO <="+endIndex);
					whereClause.append(" AND ");
					
				}
				
				if (!region.equals("NO-SELECT")) {
					whereClause.append(" EPA.REGION ='"
							+ region + "'");
					whereClause.append(" AND ");
				}
				
				if (!airportcode.equals("NO-SELECT")) {
					whereClause.append(" EPA.AIRPORTCODE ='"
							+ airportcode + "'");
					whereClause.append(" AND ");
				}
				
				if (empNameFlag.equals("true")) {
					if (!empName.equals("") && !pensionno.equals("")) {
						whereClause.append("EPI.PENSIONNO='"
								+ pensionno + "' ");
						whereClause.append(" AND ");
					} 
				} 
				
				query.append(sqlQuery);
				if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT")) && (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {
					
				} else {
					query.append(" AND ");
					query.append(commonDAO.sTokenFormat(whereClause));
				} 
				orderBy = " ORDER BY  EPI."+sortedColumn+" ASC";
				query.append(orderBy);
				dynamicQuery = query.toString();
				
				log.info("-------buildQuery1AAIEPFForm8Report:sqlQuery---------"+sqlQuery);
				
				rs = st.executeQuery(dynamicQuery);
				
				
				while (rs.next()) {
					AAIEPFBean = new AAIEPFReportBean();
					
					if (rs.getString("CPFACNO") != null) {
						AAIEPFBean.setCpfAccno(rs.getString("CPFACNO"));
					} else {
						AAIEPFBean.setCpfAccno("--");
					}
					
					if (rs.getString("EMPLOYEENO") != null) {
						AAIEPFBean.setEmployeeNumber(rs.getString("EMPLOYEENO"));
					} else {
						AAIEPFBean.setEmployeeNumber("--");
					}
					
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean.setEmployeeName("--");
					}
					
					if (rs.getString("DESEGNATION") != null) {
						AAIEPFBean.setDesignation(rs.getString("DESEGNATION"));
					} else {
						AAIEPFBean.setDesignation("--");
					}
					
					if (rs.getString("FHNAME") != null) {
						AAIEPFBean.setFhName(rs.getString("FHNAME"));
					} else {
						AAIEPFBean.setFhName("--");
					}
					
					if (rs.getString("DATEOFBIRTH") != null) {
						AAIEPFBean.setDateOfBirth(CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setDateOfBirth("--");
					}
					
					if (rs.getString("AIRPORTCODE") != null) {
						AAIEPFBean.setAirportCode(rs.getString("AIRPORTCODE"));
					} else {
						AAIEPFBean.setAirportCode("--");
					}
					
					if (rs.getString("REGION") != null) {
						AAIEPFBean.setRegion(rs.getString("REGION"));
					} else {
						AAIEPFBean.setRegion("--");
					}
					
					if (rs.getString("PENSIONNO") != null) {
						AAIEPFBean.setPensionNo(commonDAO.getPFID(AAIEPFBean.getEmployeeName(),AAIEPFBean.getDateOfBirth(),commonUtil.leadingZeros(5,
								rs.getString("PENSIONNO"))));                
					}else{
						AAIEPFBean.setPensionNo("--");
					}
					log.info("Employeeno"+AAIEPFBean.getEmployeeName()+"Pensionno"+rs.getString("PENSIONNO"));
					
					if (rs.getString("PURPOSE") != null) {
						AAIEPFBean.setAdvPurpose(rs.getString("PURPOSE"));
					} else {
						AAIEPFBean.setAdvPurpose("--");
					}
					
					
					if (rs.getString("AMOUNT") != null) {
						AAIEPFBean.setAdvAmt(rs.getString("AMOUNT"));
					} else {
						AAIEPFBean.setAdvAmt("0.00");
					}
					
					
					if (rs.getString("ADVTRANSDATE") != null) {
						AAIEPFBean.setTransDate(CommonUtil.getDatetoString(rs.getDate("ADVTRANSDATE"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setTransDate("--");
					}
					
					
					advancesList.add(AAIEPFBean);        
					
				}
				
			}catch (SQLException e) {
				log.printStackTrace(new Exception("------SQL-----"+e));
			} catch (Exception e) {
				log.printStackTrace(e);
				e.printStackTrace();
			} finally {
				//commonDB.closeConnection(con, st, rs);
			}	
			
			log.info("*********------advancesList------*********"+advancesList.size());
			log.info("AAIEPFReportDAO::buildQuery1AAIEPFForm8Report Leaving Method");
			return advancesList;
			
			
		}
		
		public ArrayList buildQueryLoansForm8Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno,Connection con) {
			log.info("AAIEPFReportDAO::buildQuery2AAIEPFForm8Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			Statement st = null;
			ResultSet rs = null;
			AAIEPFReportBean  AAIEPFBean=null;
			ArrayList loansList = new ArrayList();
			
			//sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPO.EMPNETOB AS SUBSCRIPTIONAMT,EPO.AAINETOB AS CONTRIBUTIONAMT,EPO.OUTSTANDADV,EPO.OBYEAR AS OBYEAR  FROM employee_personal_info EPI, employee_pension_ob EPO WHERE EPI.PENSIONNO = EPO.PENSIONNO and EPO.OBYEAR BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
			try{
				st = con.createStatement();
				sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPL.LOANPURPOSE AS LOANPURPOSE,EPL.SUB_AMT AS EMPSHARE,EPL.CONT_AMT AS AAISHARE,EPL.LOANDATE AS LOANDATE  FROM employee_personal_info EPI, employee_pension_loans EPL WHERE EPI.PENSIONNO = EPL.PENSIONNO and EPL.LOANDATE BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
				
				if(!range.equals("NO-SELECT")){
					String[] findRnge=range.split(" - ");
					startIndex=Integer.parseInt(findRnge[0]);
					endIndex=Integer.parseInt(findRnge[1]);
					
					whereClause.append("  EPI.PENSIONNO >="+startIndex+" AND EPI.PENSIONNO <="+endIndex);
					whereClause.append(" AND ");
					
				}
				
				if (!region.equals("NO-SELECT")) {
					whereClause.append(" EPL.REGION ='"
							+ region + "'");
					whereClause.append(" AND ");
				}
				
				if (!airportcode.equals("NO-SELECT")) {
					whereClause.append(" EPL.AIRPORTCODE ='"
							+ airportcode + "'");
					whereClause.append(" AND ");
				}
				
				if (empNameFlag.equals("true")) {
					if (!empName.equals("") && !pensionno.equals("")) {
						whereClause.append("EPI.PENSIONNO='"
								+ pensionno + "' ");
						whereClause.append(" AND ");
					} 
				} 
				
				query.append(sqlQuery);
				if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT")) && (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {
					
				} else {
					query.append(" AND ");
					query.append(commonDAO.sTokenFormat(whereClause));
				} 
				orderBy = " ORDER BY  EPI."+sortedColumn+" ASC";
				query.append(orderBy);
				dynamicQuery = query.toString();
				
				log.info("-------buildQuery2AAIEPFForm8Report:sqlQuery---------"+sqlQuery);
				
				rs = st.executeQuery(dynamicQuery);
				
				
				while (rs.next()) {
					AAIEPFBean = new AAIEPFReportBean();
					
					if (rs.getString("CPFACNO") != null) {
						AAIEPFBean.setCpfAccno(rs.getString("CPFACNO"));
					} else {
						AAIEPFBean.setCpfAccno("--");
					}
					
					if (rs.getString("EMPLOYEENO") != null) {
						AAIEPFBean.setEmployeeNumber(rs.getString("EMPLOYEENO"));
					} else {
						AAIEPFBean.setEmployeeNumber("--");
					}
					
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean.setEmployeeName("--");
					}
					
					if (rs.getString("DESEGNATION") != null) {
						AAIEPFBean.setDesignation(rs.getString("DESEGNATION"));
					} else {
						AAIEPFBean.setDesignation("--");
					}
					
					if (rs.getString("FHNAME") != null) {
						AAIEPFBean.setFhName(rs.getString("FHNAME"));
					} else {
						AAIEPFBean.setFhName("--");
					}
					
					
					if (rs.getString("DATEOFBIRTH") != null) {
						AAIEPFBean.setDateOfBirth(CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setDateOfBirth("--");
					}
					
					
					if (rs.getString("AIRPORTCODE") != null) {
						AAIEPFBean.setAirportCode(rs.getString("AIRPORTCODE"));
					} else {
						AAIEPFBean.setAirportCode("--");
					}
					
					if (rs.getString("REGION") != null) {
						AAIEPFBean.setRegion(rs.getString("REGION"));
					} else {
						AAIEPFBean.setRegion("--");
					}
					
					// log.info("-----Pension No----------"+AAIEPFBean.getPensionNo());
					
					if (rs.getString("PENSIONNO") != null) {
						AAIEPFBean.setPensionNo(commonDAO.getPFID(AAIEPFBean.getEmployeeName(),AAIEPFBean.getDateOfBirth(),commonUtil.leadingZeros(5,
								rs.getString("PENSIONNO"))));                
					}else{
						AAIEPFBean.setPensionNo("--");
					}
					
					log.info("Employeeno"+AAIEPFBean.getEmployeeName()+"Pensionno"+rs.getString("PENSIONNO"));
					
					if (rs.getString("LOANPURPOSE") != null) {
						AAIEPFBean.setLoanPurpose(rs.getString("LOANPURPOSE"));
					} else {
						AAIEPFBean.setLoanPurpose("--");
					}
					
					
					if (rs.getString("EMPSHARE") != null) {
						AAIEPFBean.setEmpShare(rs.getString("EMPSHARE"));
					} else {
						AAIEPFBean.setEmpShare("0.00");
					}
					
					if (rs.getString("AAISHARE") != null) {
						AAIEPFBean.setAAIShare(rs.getString("AAISHARE"));
					} else {
						AAIEPFBean.setAAIShare("0.00");
					}
					
					
					if (rs.getString("LOANDATE") != null) {
						AAIEPFBean.setTransDate(CommonUtil.getDatetoString(rs.getDate("LOANDATE"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setTransDate("--");
					}
					
					loansList.add(AAIEPFBean);             	                 
				}
				
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				//commonDB.closeConnection(con, st, rs);
			}	
			
			log.info("*********------loansList size------*********"+loansList.size());
			log.info("AAIEPFReportDAO::buildQuery2AAIEPFForm8Report Leaving Method");
			return loansList;
			
			
		}
		
		public ArrayList buildQueryFinalSttlementForm8Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno,Connection con) {
			log.info("AAIEPFReportDAO::buildQuery3AAIEPFForm8Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			Statement st = null;
			ResultSet rs = null;
			AAIEPFReportBean  AAIEPFBean=null;
			ArrayList settlementList = new ArrayList();
			
			//sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPO.EMPNETOB AS SUBSCRIPTIONAMT,EPO.AAINETOB AS CONTRIBUTIONAMT,EPO.OUTSTANDADV,EPO.OBYEAR AS OBYEAR  FROM employee_personal_info EPI, employee_pension_ob EPO WHERE EPI.PENSIONNO = EPO.PENSIONNO and EPO.OBYEAR BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
			try{
				st = con.createStatement();
				sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EPF.PURPOSE AS REASON,EPF.FINEMP AS EMPSHARE,EPF.FINAAI AS AAISHARE,EPF.PENCON AS PENSIONCONTRIBUTION,EPF.NETAMOUNT AS NETAMOUNT,EPF.SETTLEMENTDATE AS SETTLEMENTDATE  FROM employee_personal_info EPI, employee_pension_finsettlement EPF WHERE EPI.PENSIONNO = EPF.PENSIONNO and EPF.SETTLEMENTDATE  BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
				
				if(!range.equals("NO-SELECT")){
					String[] findRnge=range.split(" - ");
					startIndex=Integer.parseInt(findRnge[0]);
					endIndex=Integer.parseInt(findRnge[1]);
					
					whereClause.append("  EPI.PENSIONNO >="+startIndex+" AND EPI.PENSIONNO <="+endIndex);
					whereClause.append(" AND ");
					
				}
				
				if (!region.equals("NO-SELECT")) {
					whereClause.append(" EPF.REGION ='"
							+ region + "'");
					whereClause.append(" AND ");
				}
				
				if (!airportcode.equals("NO-SELECT")) {
					whereClause.append(" EPF.AIRPORTCODE ='"
							+ airportcode + "'");
					whereClause.append(" AND ");
				}
				
				if (empNameFlag.equals("true")) {
					if (!empName.equals("") && !pensionno.equals("")) {
						whereClause.append("EPI.PENSIONNO='"
								+ pensionno + "' ");
						whereClause.append(" AND ");
					} 
				} 
				
				query.append(sqlQuery);
				if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT")) && (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {
					
				} else {
					query.append(" AND ");
					query.append(commonDAO.sTokenFormat(whereClause));
				} 
				orderBy = " ORDER BY  EPI."+sortedColumn+" ASC";
				query.append(orderBy);
				dynamicQuery = query.toString();
				
				log.info("-------buildQuery3AAIEPFForm8Report:sqlQuery---------"+sqlQuery);
				
				rs = st.executeQuery(dynamicQuery);
				
				
				while (rs.next()) {
					AAIEPFBean = new AAIEPFReportBean();
					
					if (rs.getString("CPFACNO") != null) {
						AAIEPFBean.setCpfAccno(rs.getString("CPFACNO"));
					} else {
						AAIEPFBean.setCpfAccno("--");
					}
					
					if (rs.getString("EMPLOYEENO") != null) {
						AAIEPFBean.setEmployeeNumber(rs.getString("EMPLOYEENO"));
					} else {
						AAIEPFBean.setEmployeeNumber("--");
					}
					
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean.setEmployeeName("--");
					}
					
					if (rs.getString("DESEGNATION") != null) {
						AAIEPFBean.setDesignation(rs.getString("DESEGNATION"));
					} else {
						AAIEPFBean.setDesignation("--");
					}
					
					if (rs.getString("FHNAME") != null) {
						AAIEPFBean.setFhName(rs.getString("FHNAME"));
					} else {
						AAIEPFBean.setFhName("--");
					}
					
					
					if (rs.getString("DATEOFBIRTH") != null) {
						AAIEPFBean.setDateOfBirth(CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setDateOfBirth("--");
					}
					
					
					if (rs.getString("AIRPORTCODE") != null) {
						AAIEPFBean.setAirportCode(rs.getString("AIRPORTCODE"));
					} else {
						AAIEPFBean.setAirportCode("--");
					}
					
					if (rs.getString("REGION") != null) {
						AAIEPFBean.setRegion(rs.getString("REGION"));
					} else {
						AAIEPFBean.setRegion("--");
					}
					
					//log.info("-----Pension No----------"+AAIEPFBean.getPensionNo());
					
					if (rs.getString("PENSIONNO") != null) {
						AAIEPFBean.setPensionNo(commonDAO.getPFID(AAIEPFBean.getEmployeeName(),AAIEPFBean.getDateOfBirth(),commonUtil.leadingZeros(5,
								rs.getString("PENSIONNO"))));      
						log.info("Employeeno"+AAIEPFBean.getEmployeeName()+"Pensionno"+rs.getString("PENSIONNO"));
					}else{
						AAIEPFBean.setPensionNo("--");
					}
					
					
					if (rs.getString("REASON") != null) {
						AAIEPFBean.setSettlementReason(rs.getString("REASON"));
					} else {
						AAIEPFBean.setSettlementReason("--");
					}
					
					
					if (rs.getString("EMPSHARE") != null) {
						AAIEPFBean.setEmpShare(rs.getString("EMPSHARE"));
					} else {
						AAIEPFBean.setEmpShare("0.00");
					}
					
					if (rs.getString("AAISHARE") != null) {
						AAIEPFBean.setAAIShare(rs.getString("AAISHARE"));
					} else {
						AAIEPFBean.setAAIShare("0.00");
					}
					
					
					
					if (rs.getString("PENSIONCONTRIBUTION") != null) {
						AAIEPFBean.setPensionContribution(rs.getString("PENSIONCONTRIBUTION"));
					} else {
						AAIEPFBean.setPensionContribution("0.00");
					}
					
					if (rs.getString("NETAMOUNT") != null) {
						AAIEPFBean.setNetAmt(rs.getString("NETAMOUNT"));
					} else {
						AAIEPFBean.setNetAmt("0.00");
					}
					
					
					if (rs.getString("SETTLEMENTDATE") != null) {
						AAIEPFBean.setTransDate(CommonUtil.getDatetoString(rs.getDate("SETTLEMENTDATE"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setTransDate("--");
					}
					
					settlementList.add(AAIEPFBean);             	                 
				}
				
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				// commonDB.closeConnection(con, st, rs);
			}	
			
			log.info("*********------settlementList size------*********"+settlementList.size());
			log.info("AAIEPFReportDAO::buildQuery3AAIEPFForm8Report Leaving Method");
			return settlementList;
			
			
		}

		
		public ArrayList getEPFForm5Report(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
			DecimalFormat df = new DecimalFormat("#########0.00");
			String selQuery="",fromDate="",toDate="";
			String[] selectedYears=frmSelctedYear.split(",");
			fromDate=selectedYears[0];
			toDate=selectedYears[1];
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AaiEpfform3Bean epfForm3Bean=null;
			ArrayList form5DataList=new ArrayList();
			String regionDesc="",stationDesc="";
			if(!region.equals("NO-SELECT")){
				regionDesc=region;
			}else{
				regionDesc="";
			}
			if(!airprotcode.equals("NO-SELECT")){
				stationDesc=airprotcode;
			}else{
				stationDesc="";
			}
			
			selQuery=this.buildQueryForm5TransInfo(regionDesc,stationDesc,sortingOrder,fromDate,toDate,empNameFlag,empName,frmPensionno);
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				log.info("EPFFormsReportDAO::getEPFForm5Report===selQuery"+selQuery);
				rs = st.executeQuery(selQuery);
				
				while(rs.next()){
					double subTotal = 0.0,conTotal=0.0;
					epfForm3Bean=new AaiEpfform3Bean();
					
					
					if(rs.getString("NOOFSUBSCRIBERS")!=null){
						epfForm3Bean.setTotalSubscribers(rs.getString("NOOFSUBSCRIBERS"));
					}else{
						epfForm3Bean.setTotalSubscribers("---");   		
					}
					
					if(rs.getString("MONTHYEAR")!=null){
						epfForm3Bean.setMonthyear(rs.getString("MONTHYEAR"));   		
					}else{
						epfForm3Bean.setMonthyear("---");   		
					}
					
					
					if(rs.getString("EMOLUMENTS")!=null){
						epfForm3Bean.setEmoluments(rs.getString("EMOLUMENTS"));   
					}else{
						epfForm3Bean.setEmoluments("0.00");   
					}
					if(rs.getString("EMPPFSTATUARY")!=null){
						epfForm3Bean.setEmppfstatury(rs.getString("EMPPFSTATUARY"));   
					}else{
						epfForm3Bean.setEmppfstatury("0.00");   
					}
					if(rs.getString("EMPVPF")!=null){
						epfForm3Bean.setEmpvpf(rs.getString("EMPVPF"));   
					}else{
						epfForm3Bean.setEmpvpf("0.00");   
					}
					if(rs.getString("EMPADVRECPRINCIPAL")!=null){
						epfForm3Bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));   
					}else{
						epfForm3Bean.setPrincipal("0.00");   
					}
					if(rs.getString("EMPADVRECINTEREST")!=null){
						epfForm3Bean.setInterest(rs.getString("EMPADVRECINTEREST"));   
					}else{
						epfForm3Bean.setInterest("0.00");   
					}
					
					subTotal = new Double(df.format(Double.parseDouble(epfForm3Bean
							.getEmppfstatury().trim())
							+ Double.parseDouble(epfForm3Bean.getEmpvpf().trim())
							+ Double.parseDouble(epfForm3Bean.getPrincipal().trim())
							+ Double.parseDouble(epfForm3Bean.getInterest().trim())))
							.doubleValue();
					epfForm3Bean.setSubscriptionTotal(Double.toString(subTotal));
					
					
					if(rs.getString("PENSIONCONTRI")!=null){
						epfForm3Bean.setPensionContribution(rs.getString("PENSIONCONTRI"));   
					}else{
						epfForm3Bean.setPensionContribution("0.00");   
					}
					if(rs.getString("PF")!=null){
						epfForm3Bean.setPf(rs.getString("PF"));   
					}else{
						epfForm3Bean.setPf("0.00");   
					}
					conTotal=new Double(df.format(Double.parseDouble(epfForm3Bean
							.getPensionContribution().trim())
							+ Double.parseDouble(epfForm3Bean.getPf().trim())))
							.doubleValue();
					epfForm3Bean.setContributionTotal(Double.toString(conTotal));
					if(rs.getString("AIRPORTCODE")!=null){
						epfForm3Bean.setStation(rs.getString("AIRPORTCODE"));   
					}else{
						epfForm3Bean.setStation("---");   
					}
					
					form5DataList.add(epfForm3Bean);
					
				}
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return form5DataList;
		}
		public String buildQueryForm5TransInfo(String region,String airportcode,String sortedColumn,String fromDate,String toDate,String empNameFlag,String empName,String pensionno) {
			log.info("EPFFormsReportDAO::buildQueryForm5TransInfo-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "";
			log.info("empNameFlag"+empNameFlag+"frmPensionno"+pensionno+"empName"+empName);
			sqlQuery="SELECT TRANS.AIRPORTCODE AS AIRPORTCODE,COUNT(TRANS.PENSIONNO) AS NOOFSUBSCRIBERS,to_char(trans.monthyear,'Mon-yyyy') AS MONTHYEAR, ('01-'||to_char(trans.monthyear,'Mon-yyyy')) as ORDMONTHYEAR," +
			"SUM(round(TRANS.EMOLUMENTS)) AS EMOLUMENTS,SUM(round(TRANS.EMPPFSTATUARY)) AS EMPPFSTATUARY,SUM(round(TRANS.EMPVPF)) AS EMPVPF,SUM(round(TRANS.EMPADVRECPRINCIPAL)) AS EMPADVRECPRINCIPAL," +
			"SUM(round(TRANS.EMPADVRECINTEREST)) AS EMPADVRECINTEREST,SUM(round(TRANS.PENSIONCONTRI)) AS PENSIONCONTRI, SUM(round(TRANS.PF)) AS PF FROM EMPLOYEE_PENSION_VALIDATE TRANS "+
			"WHERE EMPFLAG = 'Y' AND PENSIONNO IS NOT NULL AND  MONTHYEAR BETWEEN '"+fromDate+"' and LAST_DAY('"+toDate+"') ";
			
			
			if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
			if (!airportcode.equals("")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportcode + "'");
				whereClause.append(" AND ");
			}
			if (empNameFlag.equals("true")) {
				if (!empName.equals("") && !pensionno.equals("")) {
					whereClause.append("PENSIONNO='"
							+ pensionno + "' ");
					whereClause.append(" AND ");
			} 
			} 
			
			
			query.append(sqlQuery);
			if ((region.equals("")) && (empNameFlag.equals("false")) && (airportcode.equals(""))) {
				
			} else {
				query.append(" AND ");
				query.append(commonDAO.sTokenFormat(whereClause));
			}
			
			String groupBy = "GROUP BY TRANS.AIRPORTCODE,to_char(trans.monthyear,'Mon-yyyy')";
			String orderBy = "ORDER BY TRANS."+sortedColumn+" ,to_date(ORDMONTHYEAR) ";
			query.append(groupBy+orderBy);
			dynamicQuery = query.toString();
			log.info("EPFFormsReportDAO::buildQueryForm5TransInfo Leaving Method");
			return dynamicQuery;
		}
		public ArrayList getMissingTransPFIDs(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
			DecimalFormat df = new DecimalFormat("#########0.00");
			String selPerQuery="",selQuery="",fromDate="",toDate="",selTransQuery="";
			String[] selectedYears=frmSelctedYear.split(",");
			fromDate=selectedYears[0];
			toDate=selectedYears[1];
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AaiEpfform3Bean epfForm3Bean=null;
			ArrayList form3DataList=new ArrayList();
			String regionDesc="",stationDesc="";
			if(!region.equals("NO-SELECT")){
				regionDesc=region;
			}else{
				regionDesc="";
			}
			if(!airprotcode.equals("NO-SELECT")){
				stationDesc=airprotcode;
			}else{
				stationDesc="";
			}
			
			selQuery=this.buildQueryTransMissingPFIDsInfo(regionDesc,stationDesc,sortingOrder,fromDate,toDate);
			
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				log.info("EPFFormsReportDAO::getMissingTransPFIDs===selQuery"+selQuery);
				rs = st.executeQuery(selQuery);
				
				while(rs.next()){
					double subTotal = 0.0,conTotal=0.0;
					epfForm3Bean=new AaiEpfform3Bean();
					if(rs.getString("PENSIONNO")!=null){
						epfForm3Bean.setPensionno(rs.getString("PENSIONNO"));
						
					}else{
						epfForm3Bean.setPensionno("---");
						epfForm3Bean.setPfID("---");
					}
					
					if(rs.getString("CPFACCNO")!=null){
						epfForm3Bean.setCpfaccno(rs.getString("CPFACCNO"));
					}else{
						epfForm3Bean.setCpfaccno("---");   		
					}
					
					if(rs.getString("EMPLOYEENO")!=null){
						epfForm3Bean.setEmployeeNo(rs.getString("EMPLOYEENO"));   		
					}else{
						epfForm3Bean.setEmployeeNo("---");   		
					}
					if(rs.getString("EMPLOYEENAME")!=null){
						epfForm3Bean.setEmployeeName(rs.getString("EMPLOYEENAME"));   		
					}else{
						epfForm3Bean.setEmployeeName("---");   
					}
					if(rs.getString("DESEGNATION")!=null){
						epfForm3Bean.setDesignation(rs.getString("DESEGNATION"));   	
					}else{
						epfForm3Bean.setDesignation("---");   
					}
					
					
					if(rs.getString("MONTHYEAR")!=null){
						epfForm3Bean.setMonthyear(commonUtil.converDBToAppFormat(rs.getDate("MONTHYEAR")));   
					}else{
						epfForm3Bean.setMonthyear("---");   
					}
					if(rs.getString("EMOLUMENTS")!=null){
						epfForm3Bean.setEmoluments(rs.getString("EMOLUMENTS"));   
					}else{
						epfForm3Bean.setEmoluments("0.00");   
					}
					if(rs.getString("EMPPFSTATUARY")!=null){
						epfForm3Bean.setEmppfstatury(rs.getString("EMPPFSTATUARY"));   
					}else{
						epfForm3Bean.setEmppfstatury("0.00");   
					}
					if(rs.getString("EMPVPF")!=null){
						epfForm3Bean.setEmpvpf(rs.getString("EMPVPF"));   
					}else{
						epfForm3Bean.setEmpvpf("0.00");   
					}
					if(rs.getString("EMPADVRECPRINCIPAL")!=null){
						epfForm3Bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));   
					}else{
						epfForm3Bean.setPrincipal("0.00");   
					}
					if(rs.getString("EMPADVRECINTEREST")!=null){
						epfForm3Bean.setInterest(rs.getString("EMPADVRECINTEREST"));   
					}else{
						epfForm3Bean.setInterest("0.00");   
					}
					
					subTotal = new Double(df.format(Double.parseDouble(epfForm3Bean
							.getEmppfstatury().trim())
							+ Double.parseDouble(epfForm3Bean.getEmpvpf().trim())
							+ Double.parseDouble(epfForm3Bean.getPrincipal().trim())
							+ Double.parseDouble(epfForm3Bean.getInterest().trim())))
							.doubleValue();
					epfForm3Bean.setSubscriptionTotal(Double.toString(subTotal));
					
					
					if(rs.getString("PENSIONCONTRI")!=null){
						epfForm3Bean.setPensionContribution(rs.getString("PENSIONCONTRI"));   
					}else{
						epfForm3Bean.setPensionContribution("0.00");   
					}
					if(rs.getString("PF")!=null){
						epfForm3Bean.setPf(rs.getString("PF"));   
					}else{
						epfForm3Bean.setPf("0.00");   
					}
					conTotal=new Double(df.format(Double.parseDouble(epfForm3Bean
							.getPensionContribution().trim())
							+ Double.parseDouble(epfForm3Bean.getPf().trim())))
							.doubleValue();
					epfForm3Bean.setContributionTotal(Double.toString(conTotal));
					if(rs.getString("AIRPORTCODE")!=null){
						epfForm3Bean.setStation(rs.getString("AIRPORTCODE"));   
					}else{
						epfForm3Bean.setStation("---");   
					}
					if(rs.getString("REGION")!=null){
						epfForm3Bean.setRegion(rs.getString("REGION"));   
					}else{
						epfForm3Bean.setRegion("---");   
					}
					form3DataList.add(epfForm3Bean);
					
				}
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return form3DataList;
		}
		
		public String buildQueryTransMissingPFIDsInfo(String region,String airportcode,String sortedColumn,String fromDate,String toDate) {
			log.info("EPFFormsReportDAO::buildQueryTransMissingPFIDsInfo-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "";
			
			sqlQuery="SELECT MONTHYEAR, EMOLUMENTS, round(EMPPFSTATUARY) AS EMPPFSTATUARY, round(EMPVPF) AS EMPVPF, CPF, round(EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL," +
			"round(EMPADVRECINTEREST) AS EMPADVRECINTEREST, round(AAICONPF) AS AAICONPF, ROUND(CPFADVANCE) AS CPFADVANCE, ROUND(DEDADV) AS DEDADV, ROUND(REFADV) AS REFADV, PENSIONCONTRI, PF, AIRPORTCODE, " +
			"REGION, EMPFLAG, CPFACCNO,PENSIONNO,DESEGNATION,EMPLOYEENO,EMPLOYEENAME FROM EMPLOYEE_PENSION_VALIDATE WHERE EMPFLAG = 'Y' AND PENSIONNO IS NULL  and MONTHYEAR between '"+fromDate+"' and LAST_DAY('"+toDate+"') ";
			
			
			if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
			if (!airportcode.equals("")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportcode + "'");
				whereClause.append(" AND ");
			}
			
			
			query.append(sqlQuery);
			if ((region.equals("")) && (airportcode.equals(""))) {
				
			} else {
				query.append(" AND ");
				query.append(commonDAO.sTokenFormat(whereClause));
			}
			
			String orderBy = "ORDER BY CPFACCNO,TO_DATE(MONTHYEAR)";
			query.append(orderBy);
			dynamicQuery = query.toString();
			log.info("EPFFormsReportDAO::buildQueryTransMissingPFIDsInfo Leaving Method");
			return dynamicQuery;
		}
	public ArrayList getEPFForm11Report(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
			
			String selQuery="";
			AaiEpfForm11Bean form11Bean=null;
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AaiEpfform3Bean epfForm3Bean=null;
			ArrayList form11DataList=new ArrayList();
			String regionDesc="",stationDesc="",seperationReason="",seperationDate="",fromYear="",toYear="",resettlmenetDate="",finalSettlementDate="";
			int nextYear=0;
			FinancialReportDAO reportDAO=new FinancialReportDAO();
			double rateOfIntrest=0.0;
			if(!region.equals("NO-SELECT")){
				regionDesc=region;
			}else{
				regionDesc="";
			}
			if(!airprotcode.equals("NO-SELECT")){
				stationDesc=airprotcode;
			}else{
				stationDesc="";
			}
			String[] splitYear=frmSelctedYear.split("-");
			if(splitYear.length!=0){
				rateOfIntrest=commonDAO.getRateOfInterest(splitYear[0]);
			}
			fromYear="01-Apr-"+splitYear[0];
			nextYear=Integer.parseInt(splitYear[0])+1;
			toYear="31-Mar-"+nextYear;
			selQuery=this.buildQueryEmpYearTotalInfo(range,regionDesc,stationDesc,empNameFlag,empName,sortingOrder,frmPensionno,frmSelctedYear,rateOfIntrest);
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				log.info("EPFFormsReportDAO::getEPFForm11Report===selQuery"+selQuery);
				rs = st.executeQuery(selQuery);
				
				while(rs.next()){
					
					form11Bean=new AaiEpfForm11Bean();
					if(rs.getString("PENSIONNO")!=null){
						form11Bean.setPensionNo(commonUtil.leadingZeros(5,rs.getString("PENSIONNO")));
						
					}
					//reportDAO.ProcessforAdjOb(form11Bean.getPensionNo(),true);
					if(rs.getString("CPFACNO")!=null){
						form11Bean.setCpfAccno(rs.getString("CPFACNO"));
					}else{
						form11Bean.setCpfAccno("---");   		
					}
					
					if(rs.getString("EMPLOYEENO")!=null){
						form11Bean.setEmployeeNo(rs.getString("EMPLOYEENO"));   		
					}else{
						form11Bean.setEmployeeNo("---");   		
					}
					if(rs.getString("EMPLOYEENAME")!=null){
						form11Bean.setEmployeeName(rs.getString("EMPLOYEENAME"));   		
					}else{
						form11Bean.setEmployeeName("---");   
					}
					if(rs.getString("DESEGNATION")!=null){
						form11Bean.setDesignation(rs.getString("DESEGNATION"));   	
					}else{
						form11Bean.setDesignation("---");   
					}
					if(rs.getString("FHNAME")!=null){
						form11Bean.setFhName(rs.getString("FHNAME"));   
					}else{
						form11Bean.setFhName("---");   
					}
					if(rs.getString("DATEOFBIRTH")!=null){
						form11Bean.setDateOfBirth(commonUtil.converDBToAppFormat(rs.getDate("DATEOFBIRTH")));   
					}else{
						form11Bean.setDateOfBirth("---");   
					}
					if(rs.getString("DATEOFSEPERATION_DATE")!=null){
						seperationDate=commonUtil.converDBToAppFormat(rs.getDate("DATEOFSEPERATION_DATE"));   
					}else{
						seperationDate="---";
					}
					
					if(rs.getString("RESETTLEMENTDATE")!=null){
						resettlmenetDate=commonUtil.converDBToAppFormat(rs.getDate("RESETTLEMENTDATE"));   
					}else{
						resettlmenetDate="---";
					}
					
					if(rs.getString("FINALSETTLMENTDT")!=null){
						finalSettlementDate=commonUtil.converDBToAppFormat(rs.getDate("FINALSETTLMENTDT"));   
					}else{
						finalSettlementDate="---";
					}
					
					//form11Bean=this.getFinalSettlmentInformation(con,form11Bean,form11Bean.getPensionNo(),fromYear,toYear,finalSettlementDate,resettlmenetDate);
					if(!form11Bean.getDateOfBirth().equals("---")){
						String personalPFID = commonDAO.getPFID(form11Bean.getEmployeeName(), form11Bean
								.getDateOfBirth(), form11Bean.getPensionNo());
						form11Bean.setPfID(personalPFID);
					}else{
						form11Bean.setPfID(form11Bean.getPensionNo());
					}
					
					if(rs.getString("WETHEROPTION")!=null){
						form11Bean.setWetherOption(rs.getString("WETHEROPTION"));
					}else{
						form11Bean.setWetherOption("---");   
					}
					
					
					
					if(rs.getString("EMPNETOB")!=null){
						form11Bean.setObEmpSub(rs.getString("EMPNETOB"));   
					}else{
						form11Bean.setObEmpSub("0.00");   
					}
					if(rs.getString("AAINETOB")!=null){
						form11Bean.setObAAISub(rs.getString("AAINETOB"));   
					}else{
						form11Bean.setObAAISub("0.00");   
					}
					if(rs.getString("OUTSTANDADV")!=null){
						form11Bean.setOutStndAdv(rs.getString("OUTSTANDADV"));   
					}else{
						form11Bean.setOutStndAdv("0.00");   
					}
					
					if(rs.getString("PRINCIPALOB")!=null){
						form11Bean.setObPrincipal(rs.getString("PRINCIPALOB"));   
					}else{
						form11Bean.setObPrincipal("0.00");   
					}
					if(rs.getString("EMOLUMENTS")!=null){
						form11Bean.setEmoluments(rs.getString("EMOLUMENTS"));   
					}else{
						form11Bean.setEmoluments("0.00");   
					}
					if(rs.getString("EMPPFSTATUARY")!=null){
						form11Bean.setEmppfstatury(rs.getString("EMPPFSTATUARY"));   
					}else{
						form11Bean.setEmppfstatury("0.00");   
					}
					if(rs.getString("EMPVPF")!=null){
						form11Bean.setEmpvpf(rs.getString("EMPVPF"));   
					}else{
						form11Bean.setEmpvpf("0.00");   
					}
					if(rs.getString("EMPADVRECPRINCIPAL")!=null){
						form11Bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));   
					}else{
						form11Bean.setPrincipal("0.00");   
					}
					if(rs.getString("EMPADVRECINTEREST")!=null){
						form11Bean.setInterest(rs.getString("EMPADVRECINTEREST"));   
					}else{
						form11Bean.setInterest("0.00");   
					}
					if(rs.getString("TOTAL")!=null){
						form11Bean.setEmptotal(rs.getString("TOTAL"));   
					}else{
						form11Bean.setEmptotal("0.00");   
					}
					if(rs.getString("PF")!=null){
						form11Bean.setPf(rs.getString("PF"));   
					}else{
						form11Bean.setPf("0.00");   
					}
					if(rs.getString("PENSIONCONTRI")!=null){
						form11Bean.setPensinonContr(rs.getString("PENSIONCONTRI"));   
					}else{
						form11Bean.setPensinonContr("0.00");   
					}
					
					if(rs.getString("AAITOTAL")!=null){
						form11Bean.setAaiTotal(rs.getString("AAITOTAL"));   
					}else{
						form11Bean.setAaiTotal("0.00");   
					}
					if(rs.getString("CPFTOTAL")!=null){
						form11Bean.setAdjCPF(rs.getString("CPFTOTAL"));   
					}else{
						form11Bean.setAdjCPF("0.00");   
					}
					if(rs.getString("CPFINTEREST")!=null){
						form11Bean.setAdjCPFInt(rs.getString("CPFINTEREST"));   
					}else{
						form11Bean.setAdjCPFInt("0.00");   
					}
					if(rs.getString("ADJPENSIONTOTAL")!=null){
						form11Bean.setAdjPension(rs.getString("ADJPENSIONTOTAL"));   
					}else{
						form11Bean.setAdjPension("0.00");   
					}
					
					if(rs.getString("ADJEMPSUB")!=null){
						form11Bean.setAdjEmpSubTotal(rs.getString("ADJEMPSUB"));   
					}else{
						form11Bean.setAdjEmpSubTotal("0.00");   
					}
					if(rs.getString("ADJEMPSUBINTEREST")!=null){
						form11Bean.setAdjEmpSubInterest(rs.getString("ADJEMPSUBINTEREST"));   
					}else{
						form11Bean.setAdjEmpSubInterest("0.00");   
					}
					
					if(rs.getString("ADJPENSIONINTEREST")!=null){
						form11Bean.setAdjPensionInt(rs.getString("ADJPENSIONINTEREST"));   
					}else{
						form11Bean.setAdjPensionInt("0.00");   
					}
					if(rs.getString("PFTOTAL")!=null){
						form11Bean.setAdjPF(rs.getString("PFTOTAL"));   
					}else{
						form11Bean.setAdjPF("0.00");   
					}
					if(rs.getString("PFINTEREST")!=null){
						form11Bean.setAdjPFInt(rs.getString("PFINTEREST"));   
					}else{
						form11Bean.setAdjPFInt("0.00");   
					}
					if(rs.getString("SUB_AMT")!=null){
						form11Bean.setPfwSubscr(rs.getString("SUB_AMT"));   
					}else{
						form11Bean.setPfwSubscr("0.00");   
					}
					if(rs.getString("CONT_AMT")!=null){
						form11Bean.setPfwContr(rs.getString("CONT_AMT"));   
					}else{
						form11Bean.setPfwContr("0.00");   
					}
					if(rs.getString("REFADVPFWNRW")!=null){
						form11Bean.setRefAdvPFW(rs.getString("REFADVPFWNRW"));   
					}else{
						form11Bean.setRefAdvPFW("0.00");   
					}
					if(rs.getString("ADVANCE")!=null){
						form11Bean.setAdvanceAmnt(rs.getString("ADVANCE"));   
					}else{
						form11Bean.setAdvanceAmnt("0.00");   
					}
					if(rs.getString("EMPINTERSTCR")!=null){
						form11Bean.setInterestCredited(rs.getString("EMPINTERSTCR"));   
					}else{
						form11Bean.setInterestCredited("0.00");   
					}
					if(rs.getString("AAIINTERSTCRD")!=null){
						form11Bean.setAaiIntersetCredited(rs.getString("AAIINTERSTCRD"));   
					}else{
						form11Bean.setAaiIntersetCredited("0.00");   
					}
					if(rs.getString("CLBAL")!=null){
						form11Bean.setClosingBal(rs.getString("CLBAL"));   
					}else{
						form11Bean.setClosingBal("0.00");   
					}
					if(rs.getString("AAICLBL")!=null){
						form11Bean.setAaiClosingBal(rs.getString("AAICLBL"));   
					}else{
						form11Bean.setAaiClosingBal("0.00");   
					}
					
					
					if(rs.getString("ADJEMPSUB")!=null){
						form11Bean.setAdjEmpSub(rs.getString("ADJEMPSUB"));   
					}else{
						form11Bean.setAdjEmpSub("0.00");   
					}
					if(rs.getString("ADJAAICONTR")!=null){
						log.info("Pension No"+form11Bean.getPensionNo()+"Sign"+rs.getString("ADJSIGNAAICONTR")+"ADJAAICONTR"+rs.getString("ADJAAICONTR"));
						if(rs.getString("ADJSIGNAAICONTR")!=null){
							form11Bean.setAdjAaiContr(rs.getString("ADJAAICONTR"));   
						}else{
							form11Bean.setAdjAaiContr("-"+rs.getString("ADJAAICONTR"));   
						}
						
					}else{
						form11Bean.setAdjAaiContr("0.00");   
					}
					form11Bean.setFinalPayment("0.00");   
					form11Bean.setTnsfrOthrOrgEmpShare("0.00");   
					if(rs.getString("AIRPORTCODE")!=null){
						form11Bean.setAirportCode(rs.getString("AIRPORTCODE"));   
					}else{
						form11Bean.setAirportCode("---");   
					}
					if(rs.getString("REGION")!=null){
						form11Bean.setRegion(rs.getString("REGION"));   
					}else{
						form11Bean.setRegion("---");   
					}
					if(rs.getString("ADJEMPSUB")!=null){
						form11Bean.setAdjEmpSub(rs.getString("ADJEMPSUB"));   
					}else{
						form11Bean.setAdjEmpSub("0.00");   
					}
					if(rs.getString("FINEMP")!=null){
						form11Bean.setFinalEmpSubscr(rs.getString("FINEMP"));   
					}else{
						form11Bean.setFinalEmpSubscr("0.00");   
					}
					if(rs.getString("FINAAI")!=null){
						form11Bean.setFinalAAIContr(rs.getString("FINAAI"));   
					}else{
						form11Bean.setFinalAAIContr("0.00");   
					}
					String chkMonthStatus="";
					if(rs.getString("MONTHSTATUS")!=null){
						chkMonthStatus=rs.getString("MONTHSTATUS");
						if(chkMonthStatus.equals("B")){
							form11Bean.setHighlightedColor("bgcolor='lightblue'");   
						}else{
							form11Bean.setHighlightedColor("");
						}
						
					}
					if(!seperationDate.equals("---")){
						if(this.compareDates(fromYear,"01-Apr-"+nextYear,seperationDate)==true){
							if(rs.getString("DATEOFSEPERATION_REASON")!=null){
								seperationReason=rs.getString("DATEOFSEPERATION_REASON");
								if(!seperationReason.equals("Other")){
									if(seperationReason.equals("Death")){
										form11Bean.setRemarks("Expired & PF Settled");
									}else if(seperationReason.equals("Retirement")){
										form11Bean.setRemarks("Retired & PF Settled");
									}else{
										form11Bean.setRemarks(seperationReason);
									}
									
								}
								
							}else{
								form11Bean.setRemarks("---");
							}
						}
					}else{
						form11Bean.setRemarks("---");
					}
					
					
					
					//this.getFinalSettlement(con,form11Bean.getPensionNo(),frmSelctedYear,form11Bean);
					form11DataList.add(form11Bean);
					
				}
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return form11DataList;
		}
	public String buildQueryEmpYearTotalInfo(String range,String region,String airportcode, String empNameFlag,String empName,
			String sortedColumn,String pensionno,String frmSelectedYear,double rateOfInterest) {
		log.info("EPFFormsReportDAO::buildQueryEmpYearTotalInfo-- Entering Method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "",sqlQuery = "";
		int startIndex=0,endIndex=0;
		sqlQuery="SELECT PENSIONNO, CPFACNO, EMPLOYEENO, EMPLOYEENAME , DESEGNATION , DATEOFBIRTH , DATEOFJOINING, DATEOFSEPERATION_DATE,RESETTLEMENTDATE,FINALSETTLMENTDT," +
		"DATEOFSEPERATION_REASON , WETHEROPTION, FHNAME, AIRPORTCODE ,REGION , EMPNETOB, AAINETOB, OUTSTANDADV, PRINCIPALOB , " +
		"EMOLUMENTS  , EMPPFSTATUARY , EMPVPF  , EMPADVRECPRINCIPAL, EMPADVRECINTEREST , TOTAL , PENSIONCONTRI ,"+
		"PF,AAITOTAL , FINYEAR,SUB_AMT, CONT_AMT, CPFTOTAL,CPFINTEREST ,ADJEMPSUB,ADJEMPSUBINTEREST,(ADJEMPSUB+ADJEMPSUBINTEREST) AS ADJEMPSUB"+
		",ADJPENSIONTOTAL, ADJPENSIONINTEREST,(ADJPENSIONTOTAL+ADJPENSIONINTEREST) AS ADJAAICONTR,SIGN((ADJPENSIONTOTAL+ADJPENSIONINTEREST)) AS ADJSIGNAAICONTR, PFTOTAL, PFINTEREST,  PARTAMT, AMOUNT , ADVANCE, REFADVPFWNRW , (EMPINTERSTCR+NVL(NETCLOSINGEMPNET,0.00)) AS EMPINTERSTCR,(NVL(EMPNETOB,0.00) + NVL((ADJEMPSUB+ADJEMPSUBINTEREST),0.00)+ NVL(TOTAL,0.00) - NVL(REFADVPFWNRW,0.00) "+
		"+NVL(EMPINTERSTCR,0.00)-NVL(FINEMP,0.00)+NVL(NETCLOSINGEMPNET,0.00)) AS CLBAL,(NVL(NETCLOSINGAAINET,0.00)+AAIINTERSTCR) AS AAIINTERSTCRD,(NVL(AAINETOB,'0.00')-NVL((ADJPENSIONTOTAL+ADJPENSIONINTEREST),0.00)+NVL(PF,0.00)-NVL(CONT_AMT,0.00)+NVL(AAIINTERSTCR,0.00)-NVL(FINAAI,0.00)+NVL(NETCLOSINGAAINET,0.00)) AS AAICLBL,NVL(FINEMP,0.00) AS FINEMP, " +
		"NVL(NETCLOSINGEMPNET,0.00) AS NETCLOSINGEMPNET,NVL(ARREAREMPNET,0.00) AS ARREAREMPNET,NVL(ARREAREMPNETINT,0.00) AS ARREAREMPNETINT,NVL(FINAAI,0.00) AS FINAAI,NVL(NETCLOSINGAAINET,0.00) AS NETCLOSINGAAINET,NVL(ARREARAAINET,0.00) AS ARREARAAINET,NVL(ARREARAAINETINT,0.00) AS ARREARAAINETINT,MONTHSTATUS  FROM V_EMP_PENSION_YEAR_TOTALS";
		
		if(!range.equals("NO-SELECT")){
			String[] findRnge=range.split(" - ");
			startIndex=Integer.parseInt(findRnge[0]);
			endIndex=Integer.parseInt(findRnge[1]);
			
			whereClause.append("  PENSIONNO >="+startIndex+" AND PENSIONNO <="+endIndex);
			whereClause.append(" AND ");
			
		}
		if (!frmSelectedYear.equals("")) {
			whereClause.append(" FINYEAR ='"
					+ frmSelectedYear + "'");
			whereClause.append(" AND ");
		}
		if (!region.equals("")) {
			whereClause.append(" REGION ='"
					+ region + "'");
			whereClause.append(" AND ");
		}
		if (!airportcode.equals("")) {
			whereClause.append(" LOWER(AIRPORTCODE) ='"
					+ airportcode.toLowerCase() + "'");
			whereClause.append(" AND ");
		}
		if (empNameFlag.equals("true")) {
			if (!empName.equals("") && !pensionno.equals("")) {
				whereClause.append("PENSIONNO='"
						+ pensionno + "' ");
				whereClause.append(" AND ");
			} 
		} 
		
		query.append(sqlQuery);
		if ((region.equals(""))&& (frmSelectedYear.equals("")) && (airportcode.equals("")) && (range.equals("NO-SELECT")  && (empNameFlag.equals("false")))) {
			
		} else {
			query.append(" WHERE ");
			query.append(commonDAO.sTokenFormat(whereClause));
		}
		
		String orderBy = "ORDER BY "+sortedColumn;
		query.append(orderBy);
		dynamicQuery = query.toString();
		log.info("EPFFormsReportDAO::buildQueryEmpYearTotalInfo Leaving Method");
		return dynamicQuery;
	}
		public ArrayList getEPFForm6Report(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
			DecimalFormat df = new DecimalFormat("#########0.00");
			String selPersonalQuery="",sqlQuery="";
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AaiEpfform3Bean epfForm3Bean=null;
			ArrayList form6DataList=new ArrayList();
			String regionDesc="",stationDesc="",regionQryString="";
			if(!region.equals("NO-SELECT")){
				regionDesc=region;
			}else{
				regionDesc="";
			}
			if(!airprotcode.equals("NO-SELECT")){
				stationDesc=airprotcode;
			}else{
				stationDesc="";
			}
			if(!regionDesc.equals("")){
				regionQryString=" AND REGION='"+regionDesc+"'";
			}else{
				regionQryString="";
			}
			selPersonalQuery=this.buildQueryForm6PersonalInfo(range,regionDesc,stationDesc,empName,empNameFlag,frmPensionno);
	/*		if(!regionDesc.equals("")){*/
				sqlQuery="SELECT TRANS.PENSIONNO, PERSONAL.CPFACCNO, PERSONAL.DATEOFBIRTH,PERSONAL.EMPLOYEENO, PERSONAL.EMPLOYEENAME, PERSONAL.DESEGNATION,TRANS.EMOLUMENTS, TRANS.EMPPFSTATUARY,"+
				"TRANS.EMPVPF, TRANS.EMPADVRECPRINCIPAL, TRANS.EMPADVRECINTEREST,TRANS.TOTAL, TRANS.PENSIONCONTRI, TRANS.PF, TRANS.AAITOTAL, TRANS.FINYEAR, PERSONAL.AIRPORTCODE, PERSONAL.REGION"+
				" FROM ("+selPersonalQuery+") PERSONAL,(SELECT TRANS.PENSIONNO, SUM(ROUND(NVL(TRANS.EMOLUMENTS,'0.00'))) AS EMOLUMENTS,SUM(ROUND(NVL(TRANS.EMPPFSTATUARY,'0.00'))) AS EMPPFSTATUARY, SUM(ROUND(NVL(TRANS.EMPVPF,'0.00'))) AS EMPVPF, "+
				"SUM(ROUND(NVL(TRANS.EMPADVRECPRINCIPAL,'0.00'))) AS EMPADVRECPRINCIPAL, SUM(ROUND(NVL(TRANS.EMPADVRECINTEREST,'0.00'))) AS EMPADVRECINTEREST,SUM(ROUND(NVL(TRANS.PENSIONCONTRI,'0.00'))) AS PENSIONCONTRI, SUM(ROUND(NVL(TRANS.PF,'0.00'))) AS PF,"+
				"SUM(TO_NUMBER(ROUND(NVL(TRANS.EMPPFSTATUARY,'0.00'))) + TO_NUMBER(ROUND(NVL(TRANS.EMPVPF,'0.00'))) + TO_NUMBER(ROUND(NVL(EMPADVRECPRINCIPAL,'0.00'))) + TO_NUMBER(ROUND(NVL(EMPADVRECINTEREST,'0.00')))) AS TOTAL, SUM(ROUND(NVL(TRANS.PENSIONCONTRI,'0.00')) + ROUND(NVL(TRANS.PF,'0.00'))) AS AAITOTAL"+
				",FINYEAR,REGION FROM EMPLOYEE_PENSION_VALIDATE TRANS WHERE EMPFLAG='Y' AND PENSIONNO IS NOT NULL AND FINYEAR = '"+frmSelctedYear+"'"+regionQryString+"  GROUP BY PENSIONNO, FINYEAR,REGION) TRANS WHERE PERSONAL.PENSIONNO = TRANS.PENSIONNO AND TRANS.PENSIONNO IS NOT NULL "+
				" ORDER BY PERSONAL."+sortingOrder;
	/*		}else{
				sqlQuery="SELECT TRANS.PENSIONNO, PERSONAL.CPFACCNO, PERSONAL.DATEOFBIRTH,PERSONAL.EMPLOYEENO, PERSONAL.EMPLOYEENAME, PERSONAL.DESEGNATION,TRANS.EMOLUMENTS, TRANS.EMPPFSTATUARY,"+
				"TRANS.EMPVPF, TRANS.EMPADVRECPRINCIPAL, TRANS.EMPADVRECINTEREST,TRANS.TOTAL, TRANS.PENSIONCONTRI, TRANS.PF, TRANS.AAITOTAL, TRANS.FINYEAR, PERSONAL.AIRPORTCODE, PERSONAL.REGION"+
				" FROM ("+selPersonalQuery+") PERSONAL,(SELECT TRANS.PENSIONNO, SUM(TRANS.EMOLUMENTS) AS EMOLUMENTS,SUM(TRANS.EMPPFSTATUARY) AS EMPPFSTATUARY, SUM(TRANS.EMPVPF) AS EMPVPF, "+
				"SUM(TRANS.EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL, SUM(TRANS.EMPADVRECINTEREST) AS EMPADVRECINTEREST,SUM(TRANS.PENSIONCONTRI) AS PENSIONCONTRI, SUM(TRANS.PF) AS PF,"+
				"SUM(TO_NUMBER(TRANS.EMPPFSTATUARY) + TO_NUMBER(TRANS.EMPVPF) + TO_NUMBER(EMPADVRECPRINCIPAL) + TO_NUMBER(EMPADVRECINTEREST)) AS TOTAL, SUM(TRANS.PENSIONCONTRI + TRANS.PF) AS AAITOTAL"+
				",FINYEAR FROM EMPLOYEE_PENSION_VALIDATE TRANS WHERE PENSIONNO IS NOT NULL AND FINYEAR = '"+frmSelctedYear+"' GROUP BY PENSIONNO, FINYEAR) TRANS WHERE PERSONAL.PENSIONNO = TRANS.PENSIONNO(+) AND TRANS.PENSIONNO IS NOT NULL "+
				" ORDER BY PERSONAL."+sortingOrder;
			}*/
			
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				log.info("EPFFormsReportDAO::getEPFForm6Report===sqlQuery"+sqlQuery);
				rs = st.executeQuery(sqlQuery);
				
				while(rs.next()){
					
					epfForm3Bean=new AaiEpfform3Bean();
					
					if(rs.getString("PENSIONNO")!=null){
						epfForm3Bean.setPensionno(rs.getString("PENSIONNO"));   
					}else{
						epfForm3Bean.setPensionno("---");   
					}
					if(rs.getString("CPFACCNO")!=null){
						epfForm3Bean.setCpfaccno(rs.getString("CPFACCNO"));   
					}else{
						epfForm3Bean.setCpfaccno("---");   
					}
					
					if(rs.getString("EMPLOYEENO")!=null){
						epfForm3Bean.setEmployeeNo(rs.getString("EMPLOYEENO"));   
					}else{
						epfForm3Bean.setEmployeeNo("---");   
					}
					if(rs.getString("EMPLOYEENAME")!=null){
						epfForm3Bean.setEmployeeName(rs.getString("EMPLOYEENAME"));   
					}else{
						epfForm3Bean.setEmployeeName("---");   
					}
					if(rs.getString("DESEGNATION")!=null){
						epfForm3Bean.setDesignation(rs.getString("DESEGNATION"));   
					}else{
						epfForm3Bean.setDesignation("---");   
					}
					if(rs.getString("DESEGNATION")!=null){
						epfForm3Bean.setDesignation(rs.getString("DESEGNATION"));   
					}else{
						epfForm3Bean.setDesignation("---");   
					}
					if(rs.getString("DATEOFBIRTH")!=null){
						epfForm3Bean.setDateOfBirth(commonUtil.converDBToAppFormat(rs.getDate("DATEOFBIRTH")));   
					}else{
						epfForm3Bean.setDateOfBirth("---");   
					}
					if(!epfForm3Bean.getDateOfBirth().equals("---")){
						String personalPFID = commonDAO.getPFID(epfForm3Bean.getEmployeeName(), epfForm3Bean
								.getDateOfBirth(), commonUtil.leadingZeros(5,epfForm3Bean.getPensionno()));
						epfForm3Bean.setPfID(personalPFID);
					}else{
						epfForm3Bean.setPfID(epfForm3Bean.getPensionno());
					}
					if(rs.getString("EMOLUMENTS")!=null){
						epfForm3Bean.setEmoluments(rs.getString("EMOLUMENTS"));   
					}else{
						epfForm3Bean.setEmoluments("0.00");   
					}
					if(rs.getString("EMPPFSTATUARY")!=null){
						epfForm3Bean.setEmppfstatury(rs.getString("EMPPFSTATUARY"));   
					}else{
						epfForm3Bean.setEmppfstatury("0.00");   
					}
					if(rs.getString("EMPVPF")!=null){
						epfForm3Bean.setEmpvpf(rs.getString("EMPVPF"));   
					}else{
						epfForm3Bean.setEmpvpf("0.00");   
					}
					if(rs.getString("EMPADVRECPRINCIPAL")!=null){
						epfForm3Bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));   
					}else{
						epfForm3Bean.setPrincipal("0.00");   
					}
					if(rs.getString("EMPADVRECINTEREST")!=null){
						epfForm3Bean.setInterest(rs.getString("EMPADVRECINTEREST"));   
					}else{
						epfForm3Bean.setInterest("0.00");   
					}
					if(rs.getString("TOTAL")!=null){
						epfForm3Bean.setSubscriptionTotal(rs.getString("TOTAL"));
					}else{
						epfForm3Bean.setInterest("0.00");   
					}
					
					if(rs.getString("PENSIONCONTRI")!=null){
						epfForm3Bean.setPensionContribution(rs.getString("PENSIONCONTRI"));   
					}else{
						epfForm3Bean.setPensionContribution("0.00");   
					}
					if(rs.getString("PF")!=null){
						epfForm3Bean.setPf(rs.getString("PF"));   
					}else{
						epfForm3Bean.setPf("0.00");   
					}
					if(rs.getString("AAITOTAL")!=null){
						epfForm3Bean.setContributionTotal(rs.getString("AAITOTAL"));
					}else{
						epfForm3Bean.setPf("0.00");   
					}
					
					if(rs.getString("AIRPORTCODE")!=null){
						epfForm3Bean.setStation(rs.getString("AIRPORTCODE"));   
					}else{
						epfForm3Bean.setStation("---");   
					}
					if(rs.getString("REGION")!=null){
						epfForm3Bean.setRegion(rs.getString("REGION"));   
					}else{
						epfForm3Bean.setRegion("---");   
					}
					epfForm3Bean.setRemarks("---");   
					form6DataList.add(epfForm3Bean);
					
				}
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return form6DataList;
		}
		public String buildQueryForm6PersonalInfo(String range,String region,String airportcode,String empName,String empNameFlag,String frmPensionno) {
			log.info("EPFFormsReportDAO::buildQueryForm6PersonalInfo-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "";
			int startIndex=0,endIndex=0;
			sqlQuery="SELECT CPFACNO AS CPFACCNO, EMPLOYEENO, EMPLOYEENAME,DESEGNATION,AIRPORTCODE,DATEOFBIRTH, REGION, PENSIONNO FROM EMPLOYEE_PERSONAL_INFO ";
			if(!range.equals("NO-SELECT")){
				String[] findRnge=range.split(" - ");
				startIndex=Integer.parseInt(findRnge[0]);
				endIndex=Integer.parseInt(findRnge[1]);
				whereClause.append("  PENSIONNO >="+startIndex+" AND PENSIONNO <="+endIndex);
				whereClause.append(" AND ");
			}
			
	/*		if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
			if (!airportcode.equals("")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportcode + "'");
				whereClause.append(" AND ");
			}*/
			
			if (empNameFlag.equals("true")) {
				if (!empName.equals("") && !frmPensionno.equals("")) {
					whereClause.append("PENSIONNO='"
							+ frmPensionno + "' ");
					whereClause.append(" AND ");
				} 
			} 
			query.append(sqlQuery);
			if (/*(region.equals("")) && */(range.equals("NO-SELECT") && (empNameFlag.equals("false")) /*&& (airportcode.equals(""))*/)) {
				
			} else {
				query.append(" WHERE ");
				query.append(commonDAO.sTokenFormat(whereClause));
			}
			
			
			dynamicQuery = query.toString();
			log.info("EPFFormsReportDAO::buildQueryForm6PersonalInfo Leaving Method");
			return dynamicQuery;
		}
		
		public ArrayList AAIEPFForm2Report(String range,String region,String airportcode, String frmSelectedDts,
				String empNameFlag, String empName, String sortedColumn,
				String pensionno) {
			
			log.info("AAIEPFReportDAO::AAIEPFForm2Report");	   
			
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String fromYear = "", toYear = "",dateOfRetriment="";
			AAIEPFReportBean AAIEPFBean=null;
			ArrayList AAIEPFReportList = new ArrayList();
			
			
			log.info("......frmSelectedDts in DAO......."+frmSelectedDts);
			
			if(!frmSelectedDts.equals("")){
				
				String[] dateArr=frmSelectedDts.split(",");
				fromYear=dateArr[0];
				toYear=dateArr[1];
			}
			
			
			
			String form2Query = this.buildQueryAAIEPFForm2Report(range,region,airportcode,fromYear,toYear, empNameFlag,empName,sortedColumn,pensionno);
			
			log.info("-------form2Query------"+form2Query);
			
			try{
				
				con = commonDB.getConnection();
				st = con.createStatement();
				
				rs = st.executeQuery(form2Query);
				
				while (rs.next()) {
					AAIEPFBean = new AAIEPFReportBean();
					
					
					if (rs.getString("CPFACNO") != null) {
						AAIEPFBean.setCpfAccno(rs.getString("CPFACNO"));
					} else {
						AAIEPFBean.setCpfAccno("--");
					}
					
					if (rs.getString("EMPLOYEENO") != null) {
						AAIEPFBean.setEmployeeNumber(rs.getString("EMPLOYEENO"));
					} else {
						AAIEPFBean.setEmployeeNumber("--");
					}
					
					if (rs.getString("EMPLOYEENAME") != null) {
						AAIEPFBean.setEmployeeName(rs.getString("EMPLOYEENAME"));
					} else {
						AAIEPFBean.setEmployeeName("--");
					}
					
					if (rs.getString("DESEGNATION") != null) {
						AAIEPFBean.setDesignation(rs.getString("DESEGNATION"));
					} else {
						AAIEPFBean.setDesignation("--");
					}
					
					if (rs.getString("FHNAME") != null) {
						AAIEPFBean.setFhName(rs.getString("FHNAME"));
					} else {
						AAIEPFBean.setFhName("--");
					}
					
					
					if (rs.getString("DATEOFBIRTH") != null) {
						AAIEPFBean.setDateOfBirth(CommonUtil.getDatetoString(rs.getDate("DATEOFBIRTH"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setDateOfBirth("--");
					}
					
					
					if (rs.getString("AIRPORTCODE") != null) {
						AAIEPFBean.setAirportCode(rs.getString("AIRPORTCODE"));
					} else {
						AAIEPFBean.setAirportCode("--");
					}
					
					if (rs.getString("REGION") != null) {
						AAIEPFBean.setRegion(rs.getString("REGION"));
					} else {
						AAIEPFBean.setRegion("--");
					}
					
					//log.info("-----Pension No----------"+AAIEPFBean.getPensionNo());
					
					if(!AAIEPFBean.getDateOfBirth().equals("--"))
					{	                
						AAIEPFBean.setPensionNo(commonDAO.getPFID(AAIEPFBean.getEmployeeName(),AAIEPFBean.getDateOfBirth(),commonUtil.leadingZeros(5,
								rs.getString("PENSIONNO"))));                
					}else{
						AAIEPFBean.setPensionNo(rs.getString("PENSIONNO"));
					}
					
					
					
					if (rs.getString("ADJOBYEAR") != null) {
						AAIEPFBean.setObYear(CommonUtil.getDatetoString(rs.getDate("ADJOBYEAR"),"dd-MMM-yyyy"));
					} else {
						AAIEPFBean.setObYear("");
					}
					
					if (rs.getString("SUBSCRIPTIONAMT") != null) {
						AAIEPFBean.setSubscriptionAmt(rs.getString("SUBSCRIPTIONAMT"));
					} else {
						AAIEPFBean.setSubscriptionAmt("0.00");
					}
					
					if (rs.getString("SUBSCRIPTIONINTEREST") != null) {
						AAIEPFBean.setSubscriptionInterest(rs.getString("SUBSCRIPTIONINTEREST"));
					} else {
						AAIEPFBean.setSubscriptionInterest("0.00");
					}
					
					if (rs.getString("CONTRIBUTIONAMT") != null) {
						AAIEPFBean.setContributionamt(rs.getString("CONTRIBUTIONAMT"));
					} else {
						AAIEPFBean.setContributionamt("0.00");
					}
					
					
					if (rs.getString("PENSIONINTEREST") != null) {
						AAIEPFBean.setPensionInterest(rs.getString("PENSIONINTEREST"));
					} else {
						AAIEPFBean.setPensionInterest("0.00");
					}
					
					if (rs.getString("OUTSTANDADV") != null) {
						AAIEPFBean.setOutstandingAmt(rs.getString("OUTSTANDADV"));
					} else {
						AAIEPFBean.setOutstandingAmt("--");
					}		                
					
					AAIEPFReportList.add(AAIEPFBean);
				}
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}	        
			
			log.info("===========AAIEPFReportList Size in DAO====="+AAIEPFReportList.size());
			return AAIEPFReportList;
		}   
		
		
		
		public String buildQueryAAIEPFForm2Report(String range,String region,String airportcode,String fromYear,String toYear, String empNameFlag,String empName,
				String sortedColumn,String pensionno) {
			log.info("AAIEPFReportDAO::buildQueryAAIEPFForm2Report-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "",orderBy="";
			int startIndex=0,endIndex=0;
			
			sqlQuery="select EPI.PENSIONNO,EPI.CPFACNO,EPI.EMPLOYEENO,EPI.EMPLOYEENAME,EPI.DESEGNATION,EPI.FHNAME,EPI.DATEOFBIRTH,EPI.AIRPORTCODE,EPI.REGION,EAO.ADJOBYEAR AS ADJOBYEAR,EAO.EMPSUB AS SUBSCRIPTIONAMT,EAO.EMPSUBINTEREST  AS  SUBSCRIPTIONINTEREST,EAO.PENSIONTOTAL AS CONTRIBUTIONAMT,EAO.PENSIONINTEREST AS PENSIONINTEREST,EAO.OUTSTANDADV AS OUTSTANDADV  FROM employee_personal_info EPI, employee_adj_ob EAO WHERE EPI.PENSIONNO = EAO.PENSIONNO and EAO.ADJOBYEAR BETWEEN '"+fromYear+"' AND last_day('"+toYear+"') ";
			
			if(!range.equals("NO-SELECT")){
				String[] findRnge=range.split(" - ");
				startIndex=Integer.parseInt(findRnge[0]);
				endIndex=Integer.parseInt(findRnge[1]);
				
				whereClause.append("  EPI.PENSIONNO >="+startIndex+" AND EPI.PENSIONNO <="+endIndex);
				whereClause.append(" AND ");
				
			}
			
			if (!region.equals("NO-SELECT")) {
				whereClause.append(" EAO.REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
			
			if (!airportcode.equals("NO-SELECT")) {
				whereClause.append(" EPI.AIRPORTCODE ='"
						+ airportcode + "'");
				whereClause.append(" AND ");
			}
			
			if (empNameFlag.equals("true")) {
				if (!empName.equals("") && !pensionno.equals("")) {
					whereClause.append("EPI.PENSIONNO='"
							+ pensionno + "' ");
					whereClause.append(" AND ");
				} 
			} 
			
			query.append(sqlQuery);
			if ((region.equals("NO-SELECT")) && (airportcode.equals("NO-SELECT")) && (range.equals("NO-SELECT") && (empNameFlag.equals("false")))) {
				
			} else {
				query.append(" AND ");
				query.append(commonDAO.sTokenFormat(whereClause));
			} 
			orderBy = " ORDER BY  EPI."+sortedColumn+" ASC";
			query.append(orderBy);
			dynamicQuery = query.toString();
			log.info("AAIEPFReportDAO::buildQueryAAIEPFForm2Report Leaving Method");
			return dynamicQuery;		    	
		}
		public ArrayList getEPFForm7Report(String region,String airprotcode,String frmSelctedYear,String sortingOrder){
			String sqlQuery="",regionDesc="",stationDesc="";
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			AaiEpfform3Bean epfForm3Bean=null;
			ArrayList form6DataList=new ArrayList();
			if(!region.equals("NO-SELECT")){
				regionDesc=region;
			}else{
				regionDesc="";
			}
			if(!airprotcode.equals("NO-SELECT")){
				stationDesc=airprotcode;
			}else{
				stationDesc="";
			}
			
			sqlQuery=this.buildQueryForm7TransInfo(regionDesc,stationDesc,sortingOrder,frmSelctedYear);
			
			try {
				con = commonDB.getConnection();
				st = con.createStatement();
				log.info("EPFFormsReportDAO::getEPFForm7Report===sqlQuery"+sqlQuery);
				rs = st.executeQuery(sqlQuery);
				
				while(rs.next()){
					
					epfForm3Bean=new AaiEpfform3Bean();
					if(rs.getString("TOTALSUBSCRIBERS")!=null){
						epfForm3Bean.setTotalSubscribers(rs.getString("TOTALSUBSCRIBERS"));   
					}else{
						epfForm3Bean.setTotalSubscribers("0");   
					}
					if(rs.getString("EMOLUMENTS")!=null){
						epfForm3Bean.setEmoluments(rs.getString("EMOLUMENTS"));   
					}else{
						epfForm3Bean.setEmoluments("0.00");   
					}
					if(rs.getString("EMPPFSTATUARY")!=null){
						epfForm3Bean.setEmppfstatury(rs.getString("EMPPFSTATUARY"));   
					}else{
						epfForm3Bean.setEmppfstatury("0.00");   
					}
					if(rs.getString("EMPVPF")!=null){
						epfForm3Bean.setEmpvpf(rs.getString("EMPVPF"));   
					}else{
						epfForm3Bean.setEmpvpf("0.00");   
					}
					if(rs.getString("EMPADVRECPRINCIPAL")!=null){
						epfForm3Bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));   
					}else{
						epfForm3Bean.setPrincipal("0.00");   
					}
					if(rs.getString("EMPADVRECINTEREST")!=null){
						epfForm3Bean.setInterest(rs.getString("EMPADVRECINTEREST"));   
					}else{
						epfForm3Bean.setInterest("0.00");   
					}
					if(rs.getString("TOTAL")!=null){
						epfForm3Bean.setSubscriptionTotal(rs.getString("TOTAL"));
					}else{
						epfForm3Bean.setInterest("0.00");   
					}
					
					if(rs.getString("PENSIONCONTRI")!=null){
						epfForm3Bean.setPensionContribution(rs.getString("PENSIONCONTRI"));   
					}else{
						epfForm3Bean.setPensionContribution("0.00");   
					}
					if(rs.getString("PF")!=null){
						epfForm3Bean.setPf(rs.getString("PF"));   
					}else{
						epfForm3Bean.setPf("0.00");   
					}
					if(rs.getString("AAITOTAL")!=null){
						epfForm3Bean.setContributionTotal(rs.getString("AAITOTAL"));
					}else{
						epfForm3Bean.setPf("0.00");   
					}
					
					if(rs.getString("AIRPORTCODE")!=null){
						epfForm3Bean.setStation(rs.getString("AIRPORTCODE"));   
					}else{
						epfForm3Bean.setStation("---");   
					}
					if(rs.getString("REGION")!=null){
						epfForm3Bean.setRegion(rs.getString("REGION"));   
					}else{
						epfForm3Bean.setRegion("---");   
					}
					epfForm3Bean.setRemarks("---");   
					
					form6DataList.add(epfForm3Bean);
					
				}
			}catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(con, st, rs);
			}
			return form6DataList;
		}
		public String buildQueryForm7TransInfo(String region,String airportcode,String sortedColumn,String frmSelectedYear) {
			log.info("EPFFormsReportDAO::buildQueryForm7TransInfo-- Entering Method");
			StringBuffer whereClause = new StringBuffer();
			StringBuffer query = new StringBuffer();
			String dynamicQuery = "",sqlQuery = "";
			
			sqlQuery="SELECT REGION,AIRPORTCODE,COUNT(*) AS TOTALSUBSCRIBERS, SUM(ROUND(NVL(TRANS.EMOLUMENTS,'0.00'))) AS EMOLUMENTS, SUM(ROUND(NVL(TRANS.EMPPFSTATUARY,'0.00'))) AS EMPPFSTATUARY," +
			"SUM(ROUND(NVL(TRANS.EMPVPF,'0.00'))) AS EMPVPF, SUM(ROUND(NVL(TRANS.EMPADVRECPRINCIPAL,'0.00'))) AS EMPADVRECPRINCIPAL,SUM(ROUND(NVL(TRANS.EMPADVRECINTEREST,'0.00'))) AS EMPADVRECINTEREST, SUM(ROUND(NVL(TRANS.PENSIONCONTRI,'0.00'))) AS PENSIONCONTRI," +
			"SUM(ROUND(NVL(TRANS.PF,'0.00'))) AS PF, SUM(TO_NUMBER(ROUND(NVL(TRANS.EMPPFSTATUARY,'0.00'))) + TO_NUMBER(ROUND(NVL(TRANS.EMPVPF,'0.00'))) + TO_NUMBER(ROUND(NVL(EMPADVRECPRINCIPAL,'0.00'))) + TO_NUMBER(ROUND(NVL(EMPADVRECINTEREST,'0.00')))) AS TOTAL, "+
			"SUM(ROUND(NVL(TRANS.PENSIONCONTRI,'0.00')) + ROUND(NVL(TRANS.PF,'0.00'))) AS AAITOTAL, FINYEAR FROM EMPLOYEE_PENSION_VALIDATE TRANS WHERE EMPFLAG='Y' AND PENSIONNO IS NOT NULL AND FINYEAR = '"+frmSelectedYear+"'";
			
			
			if (!region.equals("")) {
				whereClause.append(" REGION ='"
						+ region + "'");
				whereClause.append(" AND ");
			}
			if (!airportcode.equals("")) {
				whereClause.append(" AIRPORTCODE ='"
						+ airportcode + "'");
				whereClause.append(" AND ");
			}
			
			
			query.append(sqlQuery);
			if ((region.equals("")) && (airportcode.equals(""))) {
				
			} else {
				query.append(" AND ");
				query.append(commonDAO.sTokenFormat(whereClause));
			}
			
			String groupBy = "GROUP BY AIRPORTCODE,REGION, FINYEAR";
			String orderBy = "  ORDER BY TRANS."+sortedColumn;
			query.append(groupBy+orderBy);
			dynamicQuery = query.toString();
			log.info("EPFFormsReportDAO::buildQueryForm7TransInfo Leaving Method");
			return dynamicQuery;
		}
		public ArrayList getFinalSettlement(Connection con,String pensionno,String finYear,AaiEpfForm11Bean aaiEPFForm11Bean) {
			Statement st = null;
			ResultSet rs = null;
			String sqlQuery = "";
			String finalEmpSub = "", finalAAICont = "";
			boolean availFlag=false;
			Long finalStlmentEmpNet=null,finalStlmentAAICon=null;
			double closingEmpSub=0.0,closingAAINet=0.0;
			ArrayList finalSettlementList = new ArrayList();
			sqlQuery="SELECT PENSIONNO, SUM(FINEMP) AS FINEMP,SUM((FINAAI - NVL(PENCON, '0.00'))) AS FINAAI FROM EMPLOYEE_PENSION_FINSETTLEMENT  WHERE PENSIONNO = "+pensionno+"  AND FINYEAR='"+finYear+"' AND SETTLEMENTDATE IS NOT NULL    GROUP BY PENSIONNO";
			log.info("getFinalSettlement"+sqlQuery);
			try {
				st = con.createStatement();
				rs = st.executeQuery(sqlQuery);
				if (rs.next()) {
					if (rs.getString("FINEMP") != null) {
						finalEmpSub = rs.getString("FINEMP");
						aaiEPFForm11Bean.setFinalEmpSubscr(finalEmpSub);
					} else {
						finalEmpSub = "0";
						aaiEPFForm11Bean.setFinalEmpSubscr(finalEmpSub);
					}
					if (rs.getString("FINAAI") != null) {
						finalAAICont = rs.getString("FINAAI");
						aaiEPFForm11Bean.setFinalAAIContr(finalAAICont);
					} else {
						finalAAICont = "0";
						aaiEPFForm11Bean.setFinalAAIContr(finalAAICont);
					}
					availFlag=true;
				}else{
					aaiEPFForm11Bean.setFinalEmpSubscr("0");
					aaiEPFForm11Bean.setFinalAAIContr("0");
				}
				if(availFlag==true){
					finalStlmentEmpNet=new Long(Long.parseLong((String)finalEmpSub));
					finalStlmentAAICon=new Long(Long.parseLong((String)finalAAICont));
					closingEmpSub=Double.parseDouble(aaiEPFForm11Bean.getClosingBal());
					closingAAINet=Double.parseDouble(aaiEPFForm11Bean.getAaiClosingBal());
					
					long netcloseEmpNet=(new Double(closingEmpSub).longValue())+(-finalStlmentEmpNet.longValue());
					long netcloseNetAmount=(new Double(closingAAINet).longValue())+(-finalStlmentAAICon.longValue());
					aaiEPFForm11Bean.setAaiClosingBal(Long.toString(netcloseNetAmount));
					aaiEPFForm11Bean.setClosingBal(Long.toString(netcloseEmpNet));
					log.info("Before Updated Values closingEmpSub"+closingEmpSub+"closingAAINet"+closingAAINet);
					log.info("After Updated Values netcloseEmpNet"+netcloseEmpNet+"netcloseNetAmount"+netcloseNetAmount);
				}
				
				log.info("EMPNET Closing Bal"+aaiEPFForm11Bean.getClosingBal()+aaiEPFForm11Bean.getAaiClosingBal());
			} catch (SQLException e) {
				log.printStackTrace(e);
			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				commonDB.closeConnection(null, st, rs);
			}
			return finalSettlementList;
		}
		public boolean compareDates(String fromDate,String todate,String finalsettlementDate){
			 Date fromYear=new Date();
				Date toYear=new Date();
				Date fnlDate=new Date();
				boolean finalDateFlag=false;
				SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
				System.out.println("fromDate"+fromDate+"todate"+todate+"finalsettlementDate"+finalsettlementDate);
				try {
					fromYear=dateFormat.parse(fromDate);
					toYear=dateFormat.parse(todate);
					fnlDate=dateFormat.parse(finalsettlementDate);
					if (fnlDate.after(fromYear) &&fnlDate.before(toYear)){
						finalDateFlag=true;
						log.info(fromDate+"In between"+finalsettlementDate+" years"+todate);
					}else{
						finalDateFlag=false;
						log.info(fromDate+"In out "+finalsettlementDate+" years"+todate);
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					
				
				return finalDateFlag;
		}
		 public ArrayList getEPFForm3MergerData(ArrayList list,Connection con,String selPerQuery,String fromDate,String toDate,String region,String airportcode,String sortingOrder,String frmPensionno){
				DecimalFormat df = new DecimalFormat("#########0.00");
				String selQuery="",selTransQuery="";
				Statement st = null;
				ResultSet rs = null;
				AaiEpfform3Bean epfForm3Bean=null;
				selTransQuery=this.buildQueryEmpPFTransMergerInfo(region,airportcode,sortingOrder,fromDate,toDate);
				selQuery="SELECT EMPFID.PENSIONNO as PENSIONNO, EPV.CPFACCNO as CPFACCNO, EPV.EMPLOYEENO as EMPLOYEENO, EMPFID.EMPLOYEENAME as EMPLOYEENAME, EMPFID.DATEOFJOINING as DATEOFJOINING, EMPFID.DESEGNATION as DESEGNATION,EMPFID.FHNAME as FHNAME, EMPFID.DATEOFBIRTH as DATEOFBIRTH, EMPFID.WETHEROPTION as WETHEROPTION, " +
				"EPV.MONTHYEAR AS MONTHYEAR, ROUND(EPV.EMOLUMENTS) AS EMOLUMENTS, EPV.EMPPFSTATUARY AS EMPPFSTATUARY, EPV.EMPVPF AS EMPVPF, EPV.EMPADVRECPRINCIPAL AS EMPADVRECPRINCIPAL, EPV.EMPADVRECINTEREST AS EMPADVRECINTEREST, " +
				"EPV.PENSIONCONTRI AS PENSIONCONTRI, EPV.PF AS PF, EPV.AIRPORTCODE AS AIRPORTCODE , EPV.REGION AS REGION, EPV.CPFACCNO AS CPFACCNO FROM ( " +
				selTransQuery +
				") EPV,("+selPerQuery+") EMPFID WHERE EMPFID.PENSIONNO = EPV.PENSIONNO  AND EPV.EMPFLAG = 'Y' ORDER BY EMPFID."+sortingOrder;
				try {
					
					st = con.createStatement();
					log.info("EPFFormsReportDAO::getEPFForm3MergerData===selQuery"+selQuery);
					rs = st.executeQuery(selQuery);
					double diff=0;
					while(rs.next()){
						double subTotal = 0.0,conTotal=0.0,pfstatutury=0;
						epfForm3Bean=new AaiEpfform3Bean();
						//epfForm3Bean=this.fillEPFForm3Properties(rs,epfForm3Bean);
						
						if(rs.getString("PENSIONNO")!=null){
							epfForm3Bean.setPensionno(rs.getString("PENSIONNO"));
							
						}
						
						if(rs.getString("CPFACCNO")!=null){
							epfForm3Bean.setCpfaccno(rs.getString("CPFACCNO"));
						}else{
							epfForm3Bean.setCpfaccno("---");   		
						}
						
						if(rs.getString("EMPLOYEENO")!=null){
							epfForm3Bean.setEmployeeNo(rs.getString("EMPLOYEENO"));   		
						}else{
							epfForm3Bean.setEmployeeNo("---");   		
						}
						if(rs.getString("EMPLOYEENAME")!=null){
							epfForm3Bean.setEmployeeName(rs.getString("EMPLOYEENAME"));   		
						}else{
							epfForm3Bean.setEmployeeName("---");   
						}
						if(rs.getString("DESEGNATION")!=null){
							epfForm3Bean.setDesignation(rs.getString("DESEGNATION"));   	
						}else{
							epfForm3Bean.setDesignation("---");   
						}
						if(rs.getString("FHNAME")!=null){
							epfForm3Bean.setFhName(rs.getString("FHNAME"));   
						}else{
							epfForm3Bean.setFhName("---");   
						}
						if(rs.getString("DATEOFBIRTH")!=null){
							epfForm3Bean.setDateOfBirth(commonUtil.converDBToAppFormat(rs.getDate("DATEOFBIRTH")));   
						}else{
							epfForm3Bean.setDateOfBirth("---");   
						}
						if(!epfForm3Bean.getDateOfBirth().equals("---")){
							String personalPFID = commonDAO.getPFID(epfForm3Bean.getEmployeeName(), epfForm3Bean
									.getDateOfBirth(), commonUtil.leadingZeros(5,epfForm3Bean.getPensionno()));
							epfForm3Bean.setPfID(personalPFID);
						}else{
							epfForm3Bean.setPfID(epfForm3Bean.getPensionno());
						}
						if(rs.getString("DATEOFJOINING")!=null){
							epfForm3Bean.setDateofJoining(commonUtil.converDBToAppFormat(rs.getDate("DATEOFJOINING")));   
						}else{
							epfForm3Bean.setDateofJoining("---");   
						}
						if(rs.getString("WETHEROPTION")!=null){
							epfForm3Bean.setWetherOption(rs.getString("WETHEROPTION"));
						}else{
							epfForm3Bean.setWetherOption("---");   
						}
						if(rs.getString("MONTHYEAR")!=null){
							epfForm3Bean.setMonthyear(commonUtil.converDBToAppFormat(rs.getDate("MONTHYEAR")));   
						}else{
							epfForm3Bean.setMonthyear("---");   
						}
						if(rs.getString("EMOLUMENTS")!=null){
							epfForm3Bean.setEmoluments(rs.getString("EMOLUMENTS"));   
						}else{
							epfForm3Bean.setEmoluments("0.00");   
						}
						if(rs.getString("EMPPFSTATUARY")!=null){
							
							epfForm3Bean.setEmppfstatury(rs.getString("EMPPFSTATUARY"));   
						}else{
							epfForm3Bean.setEmppfstatury("0.00");   
						}
						
						if(rs.getString("EMPVPF")!=null){
							epfForm3Bean.setEmpvpf(rs.getString("EMPVPF"));   
						}else{
							epfForm3Bean.setEmpvpf("0.00");   
						}
						if(rs.getString("EMPADVRECPRINCIPAL")!=null){
							epfForm3Bean.setPrincipal(rs.getString("EMPADVRECPRINCIPAL"));   
						}else{
							epfForm3Bean.setPrincipal("0.00");   
						}
						if(rs.getString("EMPADVRECINTEREST")!=null){
							epfForm3Bean.setInterest(rs.getString("EMPADVRECINTEREST"));   
						}else{
							epfForm3Bean.setInterest("0.00");   
						}
						
						if(rs.getString("PENSIONCONTRI")!=null){
							epfForm3Bean.setPensionContribution(rs.getString("PENSIONCONTRI"));   
						}else{
							epfForm3Bean.setPensionContribution("0.00");   
						}
						if(rs.getString("PF")!=null){
							epfForm3Bean.setPf(rs.getString("PF"));   
						}else{
							epfForm3Bean.setPf("0.00");   
						}

						if(rs.getString("AIRPORTCODE")!=null){
							epfForm3Bean.setStation(rs.getString("AIRPORTCODE"));   
						}else{
							epfForm3Bean.setStation("---");   
						}
						if(rs.getString("REGION")!=null){
							epfForm3Bean.setRegion(rs.getString("REGION"));   
						}else{
							epfForm3Bean.setRegion("---");   
						}
						
						
							pfstatutury=Double.parseDouble(epfForm3Bean.getEmppfstatury());
							subTotal = new Double(df.format(Double.parseDouble(epfForm3Bean
									.getEmppfstatury().trim())
									+ Double.parseDouble(epfForm3Bean.getEmpvpf().trim())
									+ Double.parseDouble(epfForm3Bean.getPrincipal().trim())
									+ Double.parseDouble(epfForm3Bean.getInterest().trim())))
									.doubleValue();
							epfForm3Bean.setSubscriptionTotal(Double.toString(subTotal));
							conTotal=new Double(df.format(Double.parseDouble(epfForm3Bean
									.getPensionContribution().trim())
									+ Double.parseDouble(epfForm3Bean.getPf().trim())))
									.doubleValue();
							
							epfForm3Bean.setContributionTotal(Double.toString(conTotal));
							diff=pfstatutury-conTotal;
							epfForm3Bean.setFormDifference(Double.toString(Math.round(diff)));
							if(diff!=0){
								epfForm3Bean.setHighlightedColor("bgcolor='lightblue'");
							}else{
								epfForm3Bean.setHighlightedColor("");
							}
							list.add(epfForm3Bean);
					}
				}catch (SQLException e) {
					log.printStackTrace(e);
				} catch (Exception e) {
					log.printStackTrace(e);
				} finally {
					commonDB.closeConnection(null, st, rs);
				}
				return list;
			}
		    //New Method
		    public String buildQueryEmpPFTransMergerInfo(String region,String airportcode,String sortedColumn,String fromDate,String toDate) {
				log.info("EPFFormsReportDAO::buildQueryEmpPFTransMergerInfo-- Entering Method");
				StringBuffer whereClause = new StringBuffer();
				StringBuffer query = new StringBuffer();
				String dynamicQuery = "",sqlQuery = "";
				
				sqlQuery="SELECT MERGER.MONTHYEAR AS MONTHYEAR, round(MERGER.EMOLUMENTS) as EMOLUMENTS, round(MERGER.EMPPFSTATUARY) AS EMPPFSTATUARY, round(MERGER.EMPVPF) AS EMPVPF, MERGER.CPF AS CPF, round(MERGER.EMPADVRECPRINCIPAL) AS EMPADVRECPRINCIPAL," +
				"round(MERGER.EMPADVRECINTEREST) AS EMPADVRECINTEREST, round(MERGER.AAICONPF) AS AAICONPF, ROUND(MERGER.CPFADVANCE) AS CPFADVANCE, ROUND(MERGER.DEDADV) AS DEDADV, ROUND(MERGER.REFADV) AS REFADV, ROUND(MERGER.PENSIONCONTRI) AS PENSIONCONTRI, ROUND(MERGER.PF) AS PF, MERGER.AIRPORTCODE AS AIRPORTCODE, " +
				"MERGER.REGION AS REGION, MERGER.EMPFLAG AS EMPFLAG, MERGER.CPFACCNO AS CPFACCNO,MERGER.PENSIONNO AS PENSIONNO,MERGER.EMPLOYEENO AS EMPLOYEENO FROM EMP_PENSION_VALIDATE_MERGER MERGER,EMPLOYEE_PENSION_VALIDATE VAL WHERE VAL.MERGEFLAG='Y' AND VAL.PENSIONNO=MERGER.PENSIONNO AND VAL.MONTHYEAR=MERGER.MONTHYEAR  AND MERGER.EMPFLAG = 'Y' AND MERGER.PENSIONNO IS NOT NULL  and MERGER.MONTHYEAR between '"+fromDate+"' and LAST_DAY('"+toDate+"') ";
				
				
				if (!region.equals("")) {
					whereClause.append(" MERGER.REGION ='"
							+ region + "'");
					whereClause.append(" AND ");
				}
				if (!airportcode.equals("")) {
					whereClause.append(" MERGER.AIRPORTCODE ='"
							+ airportcode + "'");
					whereClause.append(" AND ");
				}
				
				
				query.append(sqlQuery);
				if ((region.equals("")) && (airportcode.equals(""))) {
					
				} else {
					query.append(" AND ");
					query.append(commonDAO.sTokenFormat(whereClause));
				}
				
				String orderBy = "ORDER BY MERGER."+sortedColumn;
				query.append(orderBy);
				dynamicQuery = query.toString();
				log.info("EPFFormsReportDAO::buildQueryEmpPFTransMergerInfo Leaving Method");
				return dynamicQuery;
			}
}
