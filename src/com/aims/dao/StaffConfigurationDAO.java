package com.aims.dao; 

import java.sql.Connection;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import java.util.List;


import com.epis.utilities.ActivityLog;
import com.epis.utilities.ApplicationException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBAccess;
import com.epis.utilities.DateConverter;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.UtilityBean;
import com.epis.utilities.Log;
import com.aims.info.accountsetup.BankMasterInfo;
import com.aims.info.incometax.ProjectedIncomeTaxInfo;
import com.aims.info.payrollprocess.EmpPaySlipInfo;
import com.aims.info.payrollprocess.MonthlyPayrollInfo;
import com.aims.info.payrollprocess.PaySlipInfo;
import com.aims.info.payrollprocess.RecoveryDetailsInfo;
import com.aims.info.payrollprocess.SearchPayrollProcessInfo;
import com.aims.info.quarter.QuarterMasterInfo;
import com.aims.info.staff.CCSINFO;
import com.aims.info.staff.ReAssignGprofInfo;
import com.aims.info.staffconfiguration.CadreInfo;
import com.aims.info.staffconfiguration.DAHikeInfo;
import com.aims.info.staffconfiguration.DeductionMasterInfo;
import com.aims.info.staffconfiguration.DesignationInfo;
import com.aims.info.staffconfiguration.EarningMasterInfo;
import com.aims.info.staffconfiguration.EmpDependentsInfo;
import com.aims.info.staffconfiguration.EmpElectricityInfo;
import com.aims.info.staffconfiguration.EmpRecoveriesInfo;
import com.aims.info.staffconfiguration.EmployeeDeductionsInfo;
import com.aims.info.staffconfiguration.EmployeeEarningInfo;
import com.aims.info.staffconfiguration.EmployeeIncomeTaxInfo;
import com.aims.info.staffconfiguration.EmployeeInfo;
import com.aims.info.staffconfiguration.EmployeePerksInfo;
import com.aims.info.staffconfiguration.EmployeePersonalInfo;
import com.aims.info.staffconfiguration.GroupInfo;
import com.aims.info.staffconfiguration.PayBillNmInfo;
import com.aims.info.staffconfiguration.PayStopConfigInfo;
import com.aims.info.staffconfiguration.PayscaleMasterInfo;
import com.aims.info.staffconfiguration.SeachCadreInfo;
import com.aims.info.staffconfiguration.SearchDesciplineInfo;
import com.aims.info.staffconfiguration.SearchDesignationInfo;
import com.aims.info.staffconfiguration.SearchEarningInfo;
import com.aims.info.staffconfiguration.SearchEmpInfo;
import com.aims.info.staffconfiguration.SearchGroupInfo;
import com.aims.info.staffconfiguration.SearchStaffCategoryInfo;
import com.aims.info.staffconfiguration.SearchStaffTypeInfo;
import com.aims.info.staffconfiguration.StaffCategoryInfo;
import com.aims.info.staffconfiguration.StaffDiscipline;
import com.aims.info.staffconfiguration.StaffTypeInfo;

public class StaffConfigurationDAO { 
	Properties prop = null;
	DBAccess db = DBAccess.getInstance();
	DateConverter dc = new DateConverter();
	SQLHelper sh = new SQLHelper();
	Connection con = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	private StaffConfigurationDAO() {
		prop = CommonUtil.getPropsFile(
				Constants.PAYROLL_APPLICATION_PROPERTIES_FILE_NAME,
				StaffConfigurationDAO.class);

		try {
			//con = db.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public String checkNull(String str)
	{
		if(str==null)
		return "0";
		else
		return str.trim();
	}	
	public List getAllDesciplines(){
		ArrayList deslist = new ArrayList();
		try{
			db.makeConnection();
			con = db.getConnection();   
			String getalldesc = "select DISCIPLINECD,DISCIPLINENM   from staffdiscipline where DELETEFLAG='N'";
			log.info("getalldesc "+getalldesc);
			rs = db.getRecordSet(getalldesc);
			StaffDiscipline disc;
			while(rs.next()){
				disc = new StaffDiscipline();
				disc.setDisciplineCd(new Integer(rs.getInt("DISCIPLINECD")));
				disc.setDisciplineNm(rs.getString("DISCIPLINENM"));
				deslist.add(disc);   
			}
			
		}catch(Exception e){
			log.info("<<<<<<<<<< getAllDesciplines Exception  >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return deslist;
	}


	private static StaffConfigurationDAO staffConfigurationInstance = new StaffConfigurationDAO();

	public static StaffConfigurationDAO getInstance() {
		return staffConfigurationInstance;
	}

	Log log = new Log(StaffConfigurationDAO.class);

	public void insertStaffType(StaffTypeInfo stafftype) throws ApplicationException {
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			String Query = "";
			int nmCnt = db.getRecordCount("Select count(*) from StaffType where upper(STAFFTYPENM)='"+stafftype.getStaffTypeName().toUpperCase()+"'");
			if(nmCnt >0){
				throw new ApplicationException("Staff Type Name '"+stafftype.getStaffTypeName()+"' Already Exists");
			}
			int cd = Integer.parseInt(AutoGeneration.getNextCode("StaffType","STAFFTYPECD",10,con));
			Query = "insert into StaffType(STAFFTYPECD,STAFFTYPENM,STAFFTYPEDESCR,DELETEFLAG)values("+cd+",'"+stafftype.getStaffTypeName()+"','"+stafftype.getStaffTypeDesc()+"','"+stafftype.getDeletedFlag()+"')";
			log.info("Query is ---- "+Query);
			db.executeUpdate(Query);
			log.info("after StaffType save");
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			if(e.getMessage().indexOf("ORA-00001")!=-1){
				throw new ApplicationException("Staff Type Code '"+stafftype.getStaffTypeCd()+"' Already Exists");
			}else {
				throw new ApplicationException(e.getMessage());
			}
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
	}

	public List searchStaffTypes(SearchStaffTypeInfo stafftypeinfo) {		
		ArrayList alist = new ArrayList();
		try {
			db.makeConnection();con = db.getConnection();
			
			String searhBTypequery ="select STAFFTYPECD,STAFFTYPENM,STAFFTYPEDESCR,decode(status,'I','InActive','A','Active')STATUS from STAFFTYPE where STAFFTYPECD is not null";
			System.out.println("searhBTypequery "+searhBTypequery);
			log.info("<<<<<<<<<<<<<<<<< Search OTARate  query>>>>>>>>>>>"+searhBTypequery);
			
			
			if (!(stafftypeinfo.getSearchstaffTypeCd().equals(""))) {	
				searhBTypequery = searhBTypequery+" and upper(stafftypecd) like upper('%"+ stafftypeinfo.getSearchstaffTypeCd().trim()+"%')";
			}
			
			if (!(stafftypeinfo.getSearchstaffTypeName().equals(""))) {					
				searhBTypequery = searhBTypequery+" and upper(stafftypenm) like upper('%"+ stafftypeinfo.getSearchstaffTypeName().trim()+"%')";		
			}
			
			if(!stafftypeinfo.getStatus().equals("")){
				searhBTypequery = searhBTypequery+" and STATUS = '"+stafftypeinfo.getStatus()+"'"; 
			}			
			
			log.info("<<<<<<<<<<<<<<<<< Search OTA Rate query>>>>>>>>>>>"+searhBTypequery);
			rs = db.getRecordSet(searhBTypequery);
			
			
			while (rs.next()) {
				stafftypeinfo = new SearchStaffTypeInfo();
			
				stafftypeinfo.setSearchstaffTypeCd(rs.getString("STAFFTYPECD"));
				stafftypeinfo.setSearchstaffTypeName(rs.getString("STAFFTYPENM"));
				stafftypeinfo.setSearchstaffTypeDesc(rs.getString("STAFFTYPEDESCR"));
				stafftypeinfo.setStatus(rs.getString("STATUS"));
				
				alist.add(stafftypeinfo);
			}
          System.out.println("alist----------"+alist);
        
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
			     db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
				}
			}		
		return alist;
	}
	public void deleteStaffTypes(String stafftypestr) {
		try {
			db.makeConnection();con = db.getConnection();
			int i = 0;
			String sSQL = "update StaffType  set deleteFlag = 'Y' where staffTypeCd in ("+stafftypestr.substring(0,(stafftypestr.length()-1))+")";
		
			i = db.executeUpdate(sSQL); 
			db.commitTrans();
			log.info("deleted  record successfully in deleteStaffTypes() " + i);
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}

	}

	public List editStaffTypes(int stafftypecd) {
		List stafftypelist = new ArrayList();
		StaffTypeInfo staff = null;
		
		try {
			db.makeConnection();con = db.getConnection();
			rs  = db.getRecordSet("select * from StaffType where staffTypeCd ="+ stafftypecd);
			while (rs.next()){	 
				staff = new StaffTypeInfo();
				staff.setStaffTypeCd(new Integer(rs.getInt("STAFFTYPECD")));
				staff.setStaffTypeName(rs.getString("STAFFTYPENM"));
				staff.setStaffTypeDesc(rs.getString("STAFFTYPEDESCR"));
				staff.setStatus(rs.getString("STATUS"));
				stafftypelist.add(staff);
			}
		    
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}		
		return stafftypelist;
	}

	public int updateStaffTypes(int cd, String name, String desc,String status) throws ApplicationException {
		int i = 0;
		try {
			db.makeConnection();con = db.getConnection();
			String sSQL = "update StaffType  set STAFFTYPENM = '"+name+"' , STAFFTYPEDESCR = '"+desc+"', STATUS='"+status+"' where STAFFTYPECD = "+cd;
			int nmCnt = db.getRecordCount("Select count(*) from StaffType where STAFFTYPENM = '"+name+"' and STAFFTYPECD <> "+cd);
			
			if(nmCnt >0){
				throw new ApplicationException("Staff Type Name '"+name+"' Already Exists");
			}
			i = db.executeUpdate(sSQL);
			log.info("updated  record successfully in updateStaffTypes() " + i);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return i;
	}

	public void saveDescipline(StaffDiscipline StaffDesInfo) throws ApplicationException {
		int desciplineCd=0;
		try {
			db.makeConnection();con = db.getConnection();
			log.info("inside save");
			System.out.println("staffdes " + StaffDesInfo.getDisciplineCd());
			//int nmCnt = db.getRecordCount("Select count(*) from staffdiscipline where upper(DISCIPLINENM)='"+StaffDesInfo.getDisciplineNm().toUpperCase()+"'");
			//if(nmCnt >0){
			//	throw new ApplicationException("Staff Descipline Name '"+StaffDesInfo.getDisciplineNm()+"' Already Exists");
			//}
			desciplineCd =Integer.parseInt(AutoGeneration.getNextCode("staffdiscipline","DISCIPLINECD",10,con));
			String insertQry = "insert into staffdiscipline(DISCIPLINECD,DISCIPLINENM,DISCIPLINEDESC,STATUS )values("+desciplineCd+",'"+StaffDesInfo.getDisciplineNm()+"','"+StaffDesInfo.getDisciplineDesc()+"','"+StaffDesInfo.getStatus()+"')";
			log.info("saveDescipline in dao is === "+insertQry);
            db.executeUpdate(insertQry);
			db.commitTrans();
			log.info("after comit");
		} catch (Exception e) {
			System.out.println("e " + e);
			if(e.getMessage().indexOf("ORA-00001")!=-1){
				throw new ApplicationException("Descipline Code '"+StaffDesInfo.getDisciplineCd()+"' Already Exists");
			}else {
				throw new ApplicationException(e.getMessage());
			}
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
	}

	public void updateDescipline(int cd, String name, String desc,String status) throws ApplicationException {
		try {
			db.makeConnection();con = db.getConnection();
			/*int nmCnt = db.getRecordCount("Select count(*) from staffdiscipline where upper(DISCIPLINENM)='"+name.toUpperCase()+"' and disciplineCd <> "+cd);
			if(nmCnt >0){
				throw new ApplicationException("Staff Descipline Name '"+name+"' Already Exists");
			}*/
			String sSQL = "update StaffDiscipline  set disciplineNm = '"+name+"' , disciplineDesc = '"+desc+"' , status = '"+status+"' where disciplineCd = "+cd;
			log.info("--------------sSQL-------------"+sSQL);
			int count = db.executeUpdate(sSQL);
			log.info("count is  **** " + count);
			db.commitTrans();

		} catch (Exception e) {
			log.error(e.getMessage());
			System.out.println("exception is " + e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
	}

	public List searchDescTypes(
		SearchDesciplineInfo staffdescinfo) {
		ArrayList alist = new ArrayList();
		SearchDesciplineInfo descipline=null;
		
		try {
			db.makeConnection();con = db.getConnection();
			
			String searhDescquery ="select DISCIPLINECD,DISCIPLINENM,DISCIPLINEDESC,decode(status,'I','InActive','A','Active')STATUS from StaffDiscipline where DISCIPLINECD is not null order by DISCIPLINECD";
			System.out.println("searh Descquery "+searhDescquery);
			log.info("<<<<<<<<<<<<<<<<<searh Descipline query>>>>>>>>>>>"+searhDescquery);
			
			
			if (staffdescinfo.getSearchdisciplineCd()!=null) {
				searhDescquery = searhDescquery+" and disciplineCd like '%"+ staffdescinfo.getSearchdisciplineCd()+"%'";
			}	
			
			if (!(staffdescinfo.getSearchdisciplineNm().equals(""))) {
				searhDescquery = searhDescquery+" and upper(disciplineNm) like upper('%"+ staffdescinfo.getSearchdisciplineNm().trim()+"%')";
			}	
		
			if(!(staffdescinfo.getStatus().equals(""))){
				searhDescquery = searhDescquery+" and STATUS = '"+staffdescinfo.getStatus()+"'"; 
			}			
			
			log.info("<<<<<<<<<<<<<<<<< searh Descipline query>>>>>>>>>>>"+searhDescquery);
			rs = db.getRecordSet(searhDescquery);
			
			while (rs.next()){
				descipline = new SearchDesciplineInfo();
				descipline.setSearchdisciplineCd(new Integer(rs.getInt("DISCIPLINECD")));
				descipline.setSearchdisciplineNm(rs.getString("DISCIPLINENM"));
				descipline.setSearchdisciplineDesc(rs.getString("DISCIPLINEDESC"));
				descipline.setStatus(rs.getString("STATUS"));
			    alist.add(descipline);
			}
		
			db.closeRs();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				db.closeRs();
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return alist;		
	}

	public StaffDiscipline editDescTypes(String desciplineCd) {
		StaffDiscipline descinfo = new StaffDiscipline();
		
		try {
			db.makeConnection();con = db.getConnection();
			String descQry = "Select DISCIPLINECD,DISCIPLINENM,DISCIPLINEDESC,STATUS  from STAFFDISCIPLINE where DISCIPLINECD="+desciplineCd;
			 
			 rs = db.getRecordSet(descQry);
			 while (rs.next()){
			 	
			 	descinfo.setDisciplineCd(new Integer(rs.getInt("DISCIPLINECD")));
			 	descinfo.setDisciplineNm(rs.getString("DISCIPLINENM"));
			 	descinfo.setDisciplineDesc(rs.getString("DISCIPLINEDESC"));
			 	descinfo.setStatus(rs.getString("STATUS"));
			 	
			 }
			 
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception session >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return descinfo;
	}

	public void deleteDesciplineTypes(String desciplineCd) {

		try {
			db.makeConnection();con = db.getConnection();
			int i = 0;
			String sSQL = "update StaffDiscipline  set deleteFlag = 'Y' where disciplineCd in ("+desciplineCd+")";
			
			i = db.executeUpdate(sSQL);
			log.info("deleted  record successfully in deleteStaffTypes() " + i);
			
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception session >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}

	}

	public List selectStafftypeCd() {
		log.info("inside dao select");
		List list = new ArrayList();
        String Qry ="";
		try {
			
			db.makeConnection();con = db.getConnection();
			Qry = "select STAFFTYPECD,STAFFTYPENM from StaffType";
			rs = db.getRecordSet(Qry);
			while(rs.next()){
				StaffTypeInfo ss = new StaffTypeInfo();
				ss.setStaffTypeCd(new Integer(rs.getInt("STAFFTYPECD")));
				ss.setStaffTypeName(rs.getString("STAFFTYPENM"));
				list.add(ss);
			}
			//log.info("list in dao " + list);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db.closeRs();
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return list;
	}

	public void insertStaffCategory(StaffCategoryInfo staffcategory) throws ApplicationException {
		try {
			db.makeConnection();con = db.getConnection();
			int nmCnt = db.getRecordCount("Select count(*) from STAFFCATEGORY where upper(STAFFCTGRYNM)='"+staffcategory.getStaffCategoryName().toUpperCase()+"'");
			if(nmCnt >0){
				throw new ApplicationException("Staff Category Name '"+staffcategory.getStaffCategoryName()+"' Already Exists");
			}
			int cd = Integer.parseInt(AutoGeneration.getNextCode("STAFFCATEGORY","STAFFCTGRYCD",10,con));
			String insertQry = "insert into STAFFCATEGORY (STAFFCTGRYCD,STAFFCTGRYNM,STAFFCTGRYDESCR,STATUS) values("+cd+",'"+staffcategory.getStaffCategoryName()+"','"+staffcategory.getStaffCategoryDesc()+"','"+staffcategory.getStatus()+"')";
			log.info("insertQry for staff Category is ---- "+insertQry);
			db.executeUpdate(insertQry);
			String userCD=staffcategory.getUsercd();
			String activityDesc="Staff Category of "+staffcategory.getStaffCategoryName()+" Saved";
			ActivityLog.getInstance().write(userCD, activityDesc, "N" , String.valueOf(cd), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>"+ e.getMessage());
			if(e.getMessage().indexOf("ORA-00001")!=-1){
				throw new ApplicationException("Staff Category Code '"+staffcategory.getStaffCategoryCd()+"' Already Exists");
			}else {
				throw new ApplicationException(e.getMessage());
			}
		} finally {
			try {
			     db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
	}

	public List searchStaffCategory(SearchStaffCategoryInfo staffcategoryinfo) {
		ArrayList alist = new ArrayList();	
		SearchStaffCategoryInfo category=null;
		try {
			db.makeConnection();con = db.getConnection();
			
			String searhCategoryquery ="select STAFFCTGRYCD,STAFFCTGRYNM,STAFFCTGRYDESCR,decode(status,'I','InActive','A','Active')STATUS from staffcategory where STAFFCTGRYCD is not null";
					
			if (!(staffcategoryinfo.getSearchstaffCategoryName().equals(""))) {				
				searhCategoryquery = searhCategoryquery+" and upper(staffctgrynm) like upper('%"+ staffcategoryinfo.getSearchstaffCategoryName().trim()+"%')";
			}				
		    if(!(staffcategoryinfo.getStatus().equals(""))){
				searhCategoryquery = searhCategoryquery+" and STATUS = '"+staffcategoryinfo.getStatus()+"'"; 
			}		
			
			log.info("<<<<<<<<<<<<<<<<< searh  Category query>>>>>>>>>>>"+searhCategoryquery);
			rs = db.getRecordSet(searhCategoryquery);
			
			while(rs.next()){
				category = new SearchStaffCategoryInfo();
				category.setSearchstaffCategoryCd(rs.getString("STAFFCTGRYCD"));
				category.setSearchstaffCategoryName(rs.getString("STAFFCTGRYNM"));
				category.setSearchstaffCategoryDesc(rs.getString("STAFFCTGRYDESCR"));
				category.setStatus(rs.getString("STATUS"));
				alist.add(category);
			}         
			db.closeRs();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception Hibernate session >>>>>>>>>>>>"+ e.getMessage());
			e.printStackTrace();
		}  finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return alist;
	}
	public List editStaffCategory(int staffcategorycd) {
		List staffcategorylist = new ArrayList();
		StaffCategoryInfo category =null;
		String editStaffList ="";
		try {
			db.makeConnection();con = db.getConnection();
			editStaffList = "select * from StaffCategory where STAFFCTGRYCD ="+ staffcategorycd;
			log.info("editStaffCat List is == "+editStaffList);
			rs = db.getRecordSet(editStaffList);
			if(rs.next()){
				category = new StaffCategoryInfo();
				category.setStaffCategoryCd(new Integer(rs.getInt("STAFFCTGRYCD")));
				category.setStaffCategoryName(rs.getString("STAFFCTGRYNM"));
				category.setStaffCategoryDesc(rs.getString("STAFFCTGRYDESCR"));
				category.setStatus(rs.getString("STATUS"));
				staffcategorylist.add(category);
			}
			db.closeRs();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		}  finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return staffcategorylist;
	}

	public int updateStaffCategory(int cd, String name, String desc,String status,String usercd) {
		int i = 0;
		try {
			db.makeConnection();con = db.getConnection();
			String sSQL = "update StaffCategory  set STAFFCTGRYNM = '"+name+"' , STAFFCTGRYDESCR = '"+desc+"' , STATUS = '"+status+"' where STAFFCTGRYCD = "+cd;
			i = db.executeUpdate(sSQL);
			log.info("updated  record successfully in updateStaffCategory() "+i);
			String activityDesc="Staff Category  Of "+name+" Updated";
			ActivityLog.getInstance().write(usercd, activityDesc, "E", String.valueOf(cd), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return i;
	}

	public List getAllStaffGroups() {
		List groupList = new ArrayList();
		try {
			db.makeConnection();con = db.getConnection();
			String getGroups = "select * from STAFFGROUPS where DELETEFLAG='N'";
			rs = db.getRecordSet(getGroups);
			while(rs.next()){
				GroupInfo grouptype = new GroupInfo();
				grouptype.setGroupCd(new Integer(rs.getInt("groupCd")));
				grouptype.setGroupName(rs.getString("GROUPNM"));
				grouptype.setGroupDesc(rs.getString("GROUPDESCR"));
				grouptype.setStatus(rs.getString("STATUS"));
				groupList.add(grouptype);
			}
			db.closeRs();
			log.info("in getAllStaffGroups() groupList size "+ groupList.size());
		} catch (Exception e) {
			log.info("<<<<<<<<<< 111 Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return groupList;
	}

	public List getAllStaffCategories() {
		List categoryList = new ArrayList();
		try {
			db.makeConnection();con = db.getConnection();
			String getcat = "select * from StaffCategory";
			log.info("get categories qry is --"+getcat);
			rs = db.getRecordSet(getcat);
			while(rs.next()){
				StaffCategoryInfo staffcategory = new StaffCategoryInfo();
				staffcategory.setStaffCategoryCd(new Integer(rs.getInt("STAFFCTGRYCD")));
				staffcategory.setStaffCategoryName(rs.getString("STAFFCTGRYNM"));
				staffcategory.setStaffCategoryDesc(rs.getString("STAFFCTGRYDESCR"));
				staffcategory.setStatus(rs.getString("STATUS"));
				categoryList.add(staffcategory);
			}
			db.closeRs();
			log.info("in getAllStaffCategories() categoryList size "+ categoryList.size());
		} catch (Exception e) {
			log.info("<<<<<<<<<< 11111111111111 Exception Hibernate session >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}

		return categoryList;
	}
	public List selectBank() {
		List bankList = new ArrayList();
		try {
			db.makeConnection();con = db.getConnection();
			String getcat = "select * from bankmaster";
			log.info("get bank qry is --"+getcat);
			rs = db.getRecordSet(getcat);
			while(rs.next()){
				BankMasterInfo bankinfo = new BankMasterInfo();
				bankinfo.setBankid(rs.getString("BANKID"));
				bankinfo.setBankname(rs.getString("BANKNM"));				
				bankList.add(bankinfo);
			}
			db.closeRs();
			log.info("in selectBank() bankList size "+ bankList.size());
		} catch (Exception e) {
			log.info("<<<<<<<<<< 11111111111111 Exception Hibernate session >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return bankList;
	}

	public void saveStaffCadre(CadreInfo ci) throws ApplicationException {
		String CadreQry ="";
	
		try {
			db.makeConnection();con = db.getConnection();
			int cadrecd = Integer.parseInt(AutoGeneration.getNextCode("STAFFCADRE","CADRECD",15,con));
			int nmCnt = db.getRecordCount("Select count(*) from staffcadre where upper(CADRENM)='"+ci.getCadreName().toUpperCase()+"'");
			if(nmCnt >0){
				throw new ApplicationException("Staff Cadre Name '"+ci.getCadreName()+"' Already Exists");
			}
			//CadreQry =" insert into staffcadre(CADRECD,CADRENM,CADREDESCR,STATUS,STAFFCTGRYCD,GROUPCD) values('"+cadrecd+"','"+ci.getCadreName()+"','"+ci.getCadreDesc()+"','"+ci.getStatus()+"',"+ci.getStaffCategoryCd()+",'"+ci.getGroupCd()+"')";
			CadreQry =" insert into staffcadre(CADRECD,CADRENM,CADREDESCR,STATUS,STAFFCTGRYCD) values('"+cadrecd+"','"+ci.getCadreName()+"','"+ci.getCadreDesc()+"','"+ci.getStatus()+"',"+ci.getStaffCategoryCd()+")";
			log.info("insert cadre Qry "+CadreQry);
			db.executeUpdate(CadreQry);
			String activityDesc="Cadre Master Of "+ci.getCadreName()+" Saved.";
			ActivityLog.getInstance().write(ci.getUsercd(), activityDesc, "N", String.valueOf(cadrecd), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception saveStaffCadre >>>>>>>>>>>>");
			log.printStackTrace(e);
			if(e.getMessage().indexOf("ORA-00001")!=-1){
				throw new ApplicationException("Cadre Code '"+ci.getCadreCd()+"' Already Exists");
			}else {
				throw new ApplicationException(e.getMessage());
			}
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
	}

	public List searchCadre(SeachCadreInfo searchCadre) {
		ArrayList cadreList = new ArrayList();
		CadreInfo ci =null;
		
		try {
			db.makeConnection();con = db.getConnection();
			
			String searhBTypequery ="select sc.cadrecd as cadrecd,sc.cadrenm as cadrenm ,sc.cadredescr as cadredescr,decode(sc.status,'I','InActive','A','Active')status,scg.staffctgrynm as staffctgrynm from staffcadre sc,staffcategory scg where  sc.staffctgrycd = scg.staffctgrycd and sc.CADRECD is not null";
			
			//System.out.println("searchCadre "+searhBTypequery);
			log.info("<<<<<<<<<<<<<<<<< searchCadre  query>>>>>>>>>>>"+searhBTypequery);
			
			if (!searchCadre.getSearchcadrenm().equals("")) {
				searhBTypequery = searhBTypequery+" and upper(sc.cadrenm) like upper('%"+ searchCadre.getSearchcadrenm().trim()+"%')";
			}
			/*if (!searchCadre.getSearchcadregroup().equals("")) {
				searhBTypequery = searhBTypequery+" and upper(sg.groupnm) like upper('%"+ searchCadre.getSearchcadregroup().trim()+"%')";

			}*/
			if (!searchCadre.getSearchstaffcategory().equals("")) {
				searhBTypequery = searhBTypequery+" and scg.STAFFCTGRYCD = '"+ searchCadre.getSearchstaffcategory().trim()+"'";
			
			}		
			
			if(!searchCadre.getStatus().equals("")){
				searhBTypequery = searhBTypequery+" and sc.STATUS = '"+searchCadre.getStatus()+"'"; 
			}			
			
			log.info("<<<<<<<<<<<<<<<<< searchCadre query>>>>>>>>>>>"+searhBTypequery);
			rs = db.getRecordSet(searhBTypequery);
					
			
			
			while(rs.next()){
				ci = new CadreInfo();
				ci.setCadreCd(rs.getString("CADRECD"));
				ci.setCadreName(rs.getString("CADRENM"));
				ci.setCadreDesc(rs.getString("CADREDESCR"));
				ci.setStatus(rs.getString("STATUS"));
				ci.setStaffCategoryName(rs.getString("STAFFCTGRYNM"));
				//ci.setGroupName(rs.getString("GROUPNM"));
				cadreList.add(ci);
			}
			
			
		} catch (Exception e) {
			log.error("error in searching cadre : " );
			log.printStackTrace(e);
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return cadreList;
	}

	public CadreInfo findByCadrecd(String cadrecd) {
		CadreInfo ci = new CadreInfo();
		try {
			db.makeConnection();con = db.getConnection();
			log.info("select * from STAFFCADRE where CADRECD= "+cadrecd);
			rs = db.getRecordSet("select * from STAFFCADRE where CADRECD= '"+cadrecd+"'");
		    if(rs.next()){
		    	ci.setCadreCd(rs.getString("CADRECD"));
				ci.setCadreName(rs.getString("CADRENM"));
				ci.setCadreDesc(rs.getString("CADREDESCR"));
				ci.setStatus(rs.getString("STATUS"));
				ci.setStaffCategoryCd(new Integer(rs.getInt("STAFFCTGRYCD")));
				//ci.setGroupCd(new Integer(rs.getInt("GROUPCD")));
		    }
			log.info("in findbycadrecd " + ci.getCadreName());
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception Hibernate session >>>>>>>>>>>>"	+ e.getMessage());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}

		return ci;
	}

	public void updateCadre(CadreInfo ci) {
		try {
			db.makeConnection();con = db.getConnection();
			//String updateQry = "update  STAFFCADRE set CADRENM='"+ci.getCadreName()+"',CADREDESCR='"+ci.getCadreDesc()+"',STAFFCTGRYCD='"+ci.getStaffCategoryCd()+"',GROUPCD='"+ci.getGroupCd()+"',STATUS='"+ci.getStatus()+"' where CADRECD ='"+ci.getCadreCd()+"'";
			String updateQry = "update  STAFFCADRE set CADRENM='"+ci.getCadreName()+"',CADREDESCR='"+ci.getCadreDesc()+"',STAFFCTGRYCD='"+ci.getStaffCategoryCd()+"',STATUS='"+ci.getStatus()+"' where CADRECD ='"+ci.getCadreCd()+"'";
			log.info("update Qry is --------- "+updateQry);
			String activityDesc="Cadre Master Of "+ci.getCadreName()+" Updated.";
			ActivityLog.getInstance().write(ci.getUsercd(), activityDesc, "E", String.valueOf(ci.getCadreCd()), con);
			db.executeUpdate(updateQry);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}

	}

	/*public void deleteCadre(String cadrecd) {
		try {
			db.makeConnection();con = db.getConnection();
			int i = 0;
			String deleteQuery = "update StaffCadre set deleteFlag = 'Y' where cadreCd in ('"+cadrecd.substring(0,cadrecd.length()-1).replaceAll(",","','")+"')";
			log.info("deleteQuery is --------- "+deleteQuery);
			i = db.executeUpdate(deleteQuery);
			log.info("deleted   " + i+ "  records successfully in deleteCadre()");
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception Hibernate session >>>>>>>>>>>>"	+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}

	}*/

	
	public void insertGroup(GroupInfo grouptype) throws ApplicationException {
		try {
			db.makeConnection();con = db.getConnection();
			int nmCnt = db.getRecordCount("Select count(*) from STAFFGROUPS where upper(GROUPNM)='"+grouptype.getGroupName().toUpperCase()+"'");
			if(nmCnt >0){
				throw new ApplicationException("Staff Group Name '"+grouptype.getGroupName()+"' Already Exists");
			}
			int cd = Integer.parseInt(AutoGeneration.getNextCode("STAFFGROUPS","GROUPCD",5,con));
			String insertQry = "insert into STAFFGROUPS (GROUPCD,GROUPNM,GROUPDESCR,STATUS) values("+cd+",'"+grouptype.getGroupName()+"','"+grouptype.getGroupDesc()+"','"+grouptype.getStatus()+"')";
			log.info("------------insertQry-------------"+insertQry);
			db.executeUpdate(insertQry);
			db.commitTrans();
			log.info("after group save");
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			if(e.getMessage().indexOf("ORA-00001")!=-1){
				//throw new ApplicationException("Staff Group Code '"+grouptype.getGroupCd()+"' Already Exists");
				log.printStackTrace(e);
				throw new ApplicationException("Staff Group Code '"+grouptype.getGroupCd()+"' Already Exists");
				
			}else {
				//throw new ApplicationException(e.getMessage());
				log.printStackTrace(e);
				throw new ApplicationException(e.getMessage());
				
			}
		} finally {
			try {
			     db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}

	}

	public List searchGroupTypes(SearchGroupInfo staffgroupinfo) {
		ArrayList alist = new ArrayList();
		SearchGroupInfo group=null;
		
		try {
			db.makeConnection();con = db.getConnection();
			
			String searhBTypequery ="select GROUPCD,GROUPNM,GROUPDESCR,decode(status,'I','InActive','A','Active')STATUS from staffgroups where GROUPCD is not null";
			System.out.println("searhBTypequery "+searhBTypequery);
			log.info("<<<<<<<<<<<<<<<<< Search OTARate  query>>>>>>>>>>>"+searhBTypequery);			
			
			if (staffgroupinfo.getSearchgroupCd()!=null) {
				searhBTypequery = searhBTypequery+" and groupCd like '%"+ staffgroupinfo.getSearchgroupCd()+"%'";
			}			
			if (!(staffgroupinfo.getSearchgroupNm().equals(""))) {				
				searhBTypequery = searhBTypequery+" and upper(groupNm) like upper('%"+staffgroupinfo.getSearchgroupNm().trim()+"%')";
			}			
			if (!(staffgroupinfo.getSearchgroupDesc().equals(""))) {
				searhBTypequery = searhBTypequery+" and upper(groupDescr) like upper('%"+ staffgroupinfo.getSearchgroupDesc().trim()+"%')";
			}				
			if(!(staffgroupinfo.getStatus().equals(""))){
				searhBTypequery = searhBTypequery+" and STATUS = '"+staffgroupinfo.getStatus()+"'"; 
			}						
			log.info("<<<<<<<<<<<<<<<<< Search OTA Rate query>>>>>>>>>>>"+searhBTypequery);
			rs = db.getRecordSet(searhBTypequery);
			
			while(rs.next()){
				group = new SearchGroupInfo();
				group.setSearchgroupCd(Integer.valueOf(rs.getString("GROUPCD")));
				group.setSearchgroupNm(rs.getString("GROUPNM"));
				group.setSearchgroupDesc(rs.getString("GROUPDESCR"));
				group.setStatus(rs.getString("STATUS"));
			  alist.add(group);
			}	
		}
		catch (Exception e) {
			log.error("error in searching cadre : " + e);
			e.printStackTrace();
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		
		return alist;		
	}

	public void deleteGroupType(String groupCd) {
		try {
			db.makeConnection();con = db.getConnection();
			int i = 0;
			String sSQL = "update staffgroups  set deleteFlag = 'Y' where groupCd in ("+groupCd+") ";
			
			i = db.executeUpdate(sSQL);
			log.info("deleted  record successfully in deleteStaffTypes() " + i);
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}

	}

	public GroupInfo editGroupTypes(String groupCd) {
		GroupInfo groupinfo = new GroupInfo();
		
		try {
			db.makeConnection();con = db.getConnection();
			int i =0;
			String groupQry =" select GROUPCD,GROUPNM,GROUPDESCR,STATUS from STAFFGROUPS where GROUPCD ="+groupCd;
			rs = db.getRecordSet(groupQry);
			while(rs.next()){
			  groupinfo.setGroupCd(new Integer(rs.getInt("GROUPCD")));
			  groupinfo.setGroupName(rs.getString("GROUPNM"));
			  groupinfo.setGroupDesc(rs.getString("GROUPDESCR"));
			  groupinfo.setStatus(rs.getString("STATUS"));
			  i =	rs.getInt("GROUPCD");
			}
			db.closeRs();
			log.info("Groupcode " + i);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return groupinfo;

	}

	public void groupUpdate(int groupcd, String groupname, String groupdesc,String status) {
		try {
			db.makeConnection();con = db.getConnection();
			String sSQL = "update staffgroups  set GROUPNM = '"+groupname+"' , GROUPDESCR = '"+groupdesc+"', STATUS = '"+status+"'  where groupCd = "+groupcd;
			
			int count = db.executeUpdate(sSQL);
			log.info("count is  **** " + count);
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
	}

	public List selectDesciplineCd(String stafftypecd) {
		log.info("inside dao select");
		List list = new ArrayList();
		String Qry = "";

		try {
			db.makeConnection();con = db.getConnection();
			Qry = "select DISCIPLINECD,DISCIPLINENM from STAFFDISCIPLINE where staffTypeCd = "+stafftypecd;
			log.info("selectDesciplineCd qry is ==== "+Qry);
			rs = db.getRecordSet(Qry);
			while(rs.next()){
				StaffDiscipline staffdes=new StaffDiscipline();
				staffdes.setDisciplineCd(new Integer(rs.getInt("DISCIPLINECD")));
				staffdes.setDisciplineNm(rs.getString("DISCIPLINENM"));
				list.add(staffdes);
			}
			db.closeRs();
			//log.info("list in dao " + list);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
			     db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return list;
	}

	public List selectCadreCd(String staffCategoryCd) {
		log.info("inside dao select");
		List list = new ArrayList();

		try {
			db.makeConnection();con = db.getConnection();
			
			String query = "select CADRECD,CADRENM,STAFFCTGRYCD from STAFFCADRE ";
			if(!staffCategoryCd.equals("")){
				query += "where STAFFCTGRYCD="+ staffCategoryCd; 
			}
			query = query+" order by CADRENM";
			log.info(query);
			rs = db.getRecordSet(query);
			while (rs.next()){
				CadreInfo ci = new CadreInfo();
				ci.setCadreCd(rs.getString("CADRECD"));
				
				ci.setCadreName(rs.getString("CADRENM"));
				if(ci.getCadreName().equalsIgnoreCase("NOCADRE")){					
					ci.setCadreName(rs.getString("CADRENM")+"-"+rs.getString("STAFFCTGRYCD"));
				}
				list.add(ci);
			}
			db.closeRs();
			//log.info("list in dao " + list);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
			     db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return list;
	}

	public List selectDesegnationCd(String type) {
		log.info("inside dao select");
		List list = new ArrayList();

		try {
			db.makeConnection();con = db.getConnection();
			//String query = " select DESIGNATIONCD,DESIGNATIONNM from STAFFDESIGNATION where CADRECD ='"+cadreCd+"'";
			StringBuffer query = new StringBuffer();
			query.append(" select DESIGNATIONCD,DESIGNATIONNM from STAFFDESIGNATION where DESIGNATIONCD is not null ");
			if("ADD".equals(type))
				query.append(" and DESIGNATIONCD not in (select DESIGNATIONCD from payscalemaster ) ");
			query.append(" order by DESIGNATIONNM ");
			log.info("--------------desig-------------------"+query.toString());
			rs = db.getRecordSet(query.toString());
			while (rs.next()){
				DesignationInfo desig = new DesignationInfo();
				desig.setDesignationCd(Integer.valueOf(rs.getString("DESIGNATIONCD")));
				desig.setDesignatioNm(rs.getString("DESIGNATIONNM"));
				list.add(desig);
			}
			db.closeRs();
			
			//log.info("list in dao " + list);
		} catch (Exception e) {
			log.info("<<<<<<<<<< selectDesegnationCd Exception >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
			     db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"
									+ e.getMessage());
			}
		}
		return list;
	}
	
	public String addEmpinfo(EmployeeInfo empinfo) {

		//String emplno="";
		int empno=0;
		try {
			log.info("empno inside dao is**** " + empinfo.getEmpNo());
			db.makeConnection();con = db.getConnection();
			
			//empinfo.setEffectiveDt("");
			//empinfo.setEndingDt("");
			//empinfo.setLastupdatedDt("");		
			
			
			/*
			 *  Temporarily using deleteflag for inserting paybillcode
			 */
			
			empno =Integer.parseInt(AutoGeneration.getNextCode("employeeinfo","EMPNO",30,con));
						
			String insertQuery = "insert into EMPLOYEEINFO (EMPNO,PENSIONNO,EMPLNUMBER,EMPLOYEENAME,CPFNO,BANKID,PAYMENTTYPE,CADRECD,DESIGNATIONCD,GENDER,PAN,BACNO,STAFFCTGRYCD,DISCIPLINECD,STAFFTYPECD,REGIONCD,STATIONCD,HRAOPTION,PFTAXOPTION,PFTRUST,PETROL,CADRECOD,GSLISCADRECD,PAYBILLCD,QUATERTYPE,PAYBILL,PENSIONOPTION,MEDICALNO)";
			       insertQuery += " values("+empno+",'"+empinfo.getPensionno().trim()+"','"+empinfo.getEmplnumber().trim()+"','"+empinfo.getEmployeename().trim()+"','"+empinfo.getCpfNo()+"','"+empinfo.getBankId()+"','"+empinfo.getPaymentType()+"','"+empinfo.getCadreCd()+"',(select DESIGNATIONCD FROM staffdesignation WHERE desgcode="+empinfo.getDesgCode()+"),'"+empinfo.getGender()+"','"+empinfo.getPan()+"','"+empinfo.getBacno()+"',"+empinfo.getStaffCategoryCd()+","+empinfo.getDisciplineCd()+","+empinfo.getStaffTypeCd()+",'"+empinfo.getRegioncd()+"','"+empinfo.getStation()+"','"+empinfo.getHraOption()+"','"+empinfo.getPftaxoption()+"','"+empinfo.getPftrust()+"','"+empinfo.getPetrol()+"','"+empinfo.getCadrecod()+"','"+empinfo.getGsliscd()+"','"+empinfo.getDeleteFlag()+"','"+empinfo.getQuarterType()+"',(select PAYBILLNM from paybillmt where PAYBILLCD='"+empinfo.getDeleteFlag()+"'),'"+empinfo.getPensionoption()+"','"+empinfo.getMedicalcardno()+"')";
			log.info("insert addEmpinfo Query is "+insertQuery);
			db.executeUpdate(insertQuery);                   
			String command = "{call ADDEMPLPERKS(?)}";     // 4 placeholders
			CallableStatement cstmt = con.prepareCall (command);
			cstmt.setString(1, empno+"");			 
			cstmt.execute();
			cstmt.close();
			String activityDesc="Employee Information Of "+empinfo.getEmployeename().trim()+" Saved.";
			ActivityLog.getInstance().write(empinfo.getUsercd(), activityDesc, "N", String.valueOf(empno), con);
			db.commitTrans();
			/*rs = db.getRecordSet("select EMPNO from EMPLOYEEINFO where EMPNO="+empno);
			if(rs.next())
				emplno=rs.getString("EMPNO");
			log.info("after addEmpinfo");*/
		} catch (Exception e) {
			log.info("<<<<<<<<<< addEmpinfo Exception  >>>>>>>>>>>>" );
			log.printStackTrace(e);
		} finally {
			try {
			     db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception >>>>>>>>>>>>");
					log.printStackTrace(e);
			}
		}
		log.info("empno "+empno);
		return empno+"";
	}
	
	public List searchEmpinfo(SearchEmpInfo searchEmpinfo) {
		log.info("StaffConfigurationDAO:searchEmpinfo() Entering Method");
		ArrayList alist = new ArrayList();

		//---------------------------------------
		
	
		try {
			db.makeConnection();con = db.getConnection();
			
			String searhBSchemequery = "select EMPNO,EMPLNUMBER,EMPLOYEENAME,empinfo.STAFFCTGRYCD,DESIGNATIONCD,CADRECD,CPFNO " +
			" from EmployeeInfo empinfo,staffcategory stf where EMPNO is not null and stf.STAFFCTGRYCD=empinfo.STAFFCTGRYCD ";
	log.info("searhBSchemequery------------ "+searhBSchemequery);
	
	System.out.println("getSearchemplNo "+searchEmpinfo.getSearchemplNo());
	
	if (searchEmpinfo.getSearchemplNo() != null) {
		if (!(searchEmpinfo.getSearchemplNo().equals(""))) {		
			searhBSchemequery = searhBSchemequery+" and EMPLNUMBER  like'%"+ searchEmpinfo.getSearchemplNo()+"%'";
		
		}
	}
	if (searchEmpinfo.getSearchtype() != null) {
		if (!(searchEmpinfo.getSearchtype().equals(""))) {		
			searhBSchemequery = searhBSchemequery+" and empinfo.STAFFCTGRYCD='"+searchEmpinfo.getSearchtype()+"'";
		
		}
	}
	
	if ((searchEmpinfo.getSearchFirstName() != null)) {
		if (!(searchEmpinfo.getSearchFirstName().equals(""))) {	
			searhBSchemequery = searhBSchemequery+" and upper(EMPLOYEENAME)  like upper('%"+ searchEmpinfo.getSearchFirstName().trim() + "%')";
				
	   }
	}	
	
	/*if ((searchEmpinfo.getSearchLastName() != null)){	
		if (!(searchEmpinfo.getSearchLastName().equals(""))){								
			searhBSchemequery = searhBSchemequery+" and upper(empLastName)  like upper('%"+ searchEmpinfo.getSearchLastName().trim() + "%')";
		}
	}*/
	
	/*if ((searchEmpinfo.getSearchDescipline() != null)){							
		searhBSchemequery = searhBSchemequery+" and DISCIPLINECD like '%"+ searchEmpinfo.getSearchDescipline() + "%'";		
	}*/
	
	if ((searchEmpinfo.getSearchStaffCategory() != null)){						
		searhBSchemequery = searhBSchemequery+" and STAFFCTGRYCD like '%"+ searchEmpinfo.getSearchStaffCategory() + "%'";
	}
	
	/*if(searchEmpinfo.getStatus()!=null){
		if(!searchEmpinfo.getStatus().equals(""))
			searhBSchemequery = searhBSchemequery+" and status = '"+ searchEmpinfo.getStatus()+"'"; 
		}*/
	
	if ((searchEmpinfo.getSearchempNo() != null)
			|| ((searchEmpinfo.getSearchFirstName() != null) && (searchEmpinfo
					.getSearchFirstName() != ""))
			|| ((searchEmpinfo.getSearchLastName() != null) && (searchEmpinfo
							.getSearchLastName() != ""))
			|| ((searchEmpinfo.getSearchDescipline() != null))
			|| ((searchEmpinfo.getSearchStaffCategory() != null))) {
		log.info("inside if");
		searhBSchemequery = searhBSchemequery+" ";
		
	} 
	searhBSchemequery=	 searhBSchemequery+" order by EMPLOYEENAME asc";
	log.info("<<<<<<<<<<<<<<<<< searchEmpinfo query>>>>>>>>>>>"+searhBSchemequery);
	//searhBSchemequery = searhBSchemequery+" order by BUDGETRANSFERCD desc"; 
	rs = db.getRecordSet(searhBSchemequery);
	//String surname="";
	while(rs.next()){
		EmployeeInfo empinfo= new EmployeeInfo();
		
		empinfo.setEmpNo(rs.getString("empno"));
		empinfo.setEmplnumber(rs.getString("EMPLNUMBER"));
		//empinfo.setEmpSurName(rs.getString("EMPSURNAME"));
		//surname = rs.getString("EMPSURNAME")==null? "":rs.getString("EMPSURNAME");
		/*if(surname.indexOf(".")==-1&&!"".equals(surname))
			surname = surname+".";*/
		empinfo.setEmpFirstName(rs.getString("EMPLOYEENAME"));
		//empinfo.setEmpLastName(rs.getString("emplastname"));
		empinfo.setStaffCategoryCd(new Integer(rs.getInt("STAFFCTGRYCD")));
		empinfo.setStaffCategoryNm(sh.getDescription("staffcategory","STAFFCTGRYNM","STAFFCTGRYCD",rs.getString("STAFFCTGRYCD"),con));
		empinfo.setDesignationCd(new Integer(rs.getInt("DESIGNATIONCD")));
		empinfo.setDesignationNm(sh.getDescription("staffdesignation","DESIGNATIONNM","DESIGNATIONCD",rs.getString("DESIGNATIONCD"),con));
		empinfo.setCadreCd(rs.getString("CADRECD"));	
		///empinfo.setCadreNm(sh.getDescription("staffcadre","CADRENM","CADRECD",rs.getString("CADRECD"),con));
		empinfo.setCpfNo(rs.getString("CPFNO"));
		//empinfo.setStatus(rs.getString("status"));
		alist.add(empinfo);
	} 			
		db.closeRs();	
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:searchEmpinfo() Leaving Method");
		return alist;
	}

	public EmployeePersonalInfo editEmpPersonalinfo(String empNo) {
		log.info("StaffConfigurationDAO:editEmpPersonalinfo() Entering Method");
		
		EmployeePersonalInfo empInfo=new EmployeePersonalInfo();
		try {
			db.makeConnection();con = db.getConnection();
			log.info("the empno is..."+empNo);
			String query="select EMPNO,to_char(EMPDOB,'dd/Mon/yyyy')EMPDOB,to_char(EMPDOJ,'dd/Mon/yyyy')EMPDOJ," +
					"MARITALSTAT,to_char(SEPERATIONDATE,'dd/Mon/yyyy')SEPERATIONDATE,SEPERATIONREASON,RETIREFRMPENSION," +
					"to_char(MARRIAGEDT,'dd/Mon/yyyy')MARRIAGEDT,NATIONALITY,RELIGION,PERMENENTADDRESS,CITY,STATE,ZIPCD," +
					"CURRENTADDRESS,CITY1,STATE1,MAILID,CONTACTNO1,CONTACTNO2,DELETEFLAG,ZIPCD1,PERSONALID,ISADDRSAME from employeepersonalinfo where  empNo ='" + empNo+"'";
		   
			log.info("---------------Query---------------"+query);
			rs=db.getRecordSet(query);
		
	    	if(rs.next()){
	    		
	    	empInfo.setEmpNo(rs.getString("empno"));
	    	empInfo.setEmpDob(rs.getString("empdob"));
	    	empInfo.setEmpDoj(rs.getString("empdoj"));
	    	/*empInfo.setEmpDor(rs.getString("empdor"));
	    	empInfo.setEmpDol(rs.getString("empdol"));*/
	    	empInfo.setSeperationReason(rs.getString("SEPERATIONREASON"));
	    	empInfo.setDateofseperation(rs.getString("SEPERATIONDATE"));
	    	empInfo.setRetirefrmpension(rs.getString("RETIREFRMPENSION"));
	    	System.out.println("----maritalStat-----"+rs.getString("maritalstat"));
	    	System.out.println("----nationality-----"+rs.getString("nationality"));
	    	System.out.println("----religion-----"+rs.getString("religion"));
	    	empInfo.setMaritalStat(rs.getString("maritalstat"));
	    	empInfo.setMarriageDt(rs.getString("marriagedt"));
	    	empInfo.setNationality(rs.getString("nationality"));
	    	empInfo.setReligion(rs.getString("religion"));
	    	empInfo.setPermentAddress(rs.getString("permenentaddress"));
	    	empInfo.setCity(rs.getString("city"));
	    	empInfo.setState(rs.getString("state"));
	    	empInfo.setZipCd(rs.getString("zipcd"));
	    	empInfo.setCurrentAddress(rs.getString("currentaddress"));
	    	empInfo.setCity1(rs.getString("city1"));
	    	empInfo.setState1(rs.getString("state1"));
	    	empInfo.setMailId(rs.getString("mailid"));
	    	System.out.println("-----contact  no1-----"+rs.getString("contactno1"));
	    	empInfo.setContactNo1(rs.getString("contactno1"));
	    	System.out.println("-----contact  no2-----"+rs.getString("contactno2"));
	    	empInfo.setContactNo2(rs.getString("contactno2"));
	    	empInfo.setDeleteFlag(rs.getString("deleteflag"));
	    	empInfo.setZipCd1(rs.getString("zipcd1"));
	    	empInfo.setPersonalId(rs.getString("personalid"));
	    	empInfo.setIsaddrsame(rs.getString("ISADDRSAME"));
		    }				
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 
		log.info("StaffConfigurationDAO:editEmpPersonalinfo() Leaving Method");
		return empInfo;
	}
	
	public void updateEmpPersonalInfo(EmployeePersonalInfo emppersonalinfo) {
		log.info("StaffConfigurationDAO:updateEmpPersonalInfo() Entering Method");
		int i=0;
		String mrgDt = "";
		if(emppersonalinfo.getMarriageDt()!=null){
			mrgDt = emppersonalinfo.getMarriageDt();
		}
		try {	
			db.makeConnection();con = db.getConnection();
			String sSQL = "update employeepersonalinfo  set empno='"+emppersonalinfo.getEmpNo()+"'"
			+" , empdob='"+emppersonalinfo.getEmpDob()
			+"' , empdoj='"+emppersonalinfo.getEmpDoj()
			//+"' , SEPERATIONDATE='"+emppersonalinfo.getDateofseperation()
			//+"' , SEPERATIONREASON='"+emppersonalinfo.getSeperationReason()
			+"' , maritalstat='"+emppersonalinfo.getMaritalStat()
			+"' , marriagedt='"+mrgDt
			+"' , nationality='"+emppersonalinfo.getNationality()
			+"' , religion='"+emppersonalinfo.getReligion()
			+"' , permenentaddress='"+emppersonalinfo.getPermentAddress()
			+"' , city='"+emppersonalinfo.getCity()
			+"' , state='"+emppersonalinfo.getState()
			+"' , zipcd="+emppersonalinfo.getZipCd()
			+"  , currentaddress='"+emppersonalinfo.getCurrentAddress()
			+"' , city1='"+emppersonalinfo.getCity1()
			+"' , state1='"+emppersonalinfo.getState1()
			+"' , mailid='"+emppersonalinfo.getMailId()
			+"' , contactno1='"+emppersonalinfo.getContactNo1()
			+"' , contactno2='"+emppersonalinfo.getContactNo2()
			+"' , deleteflag='"+emppersonalinfo.getDeleteFlag()
			+"' , zipcd1='"+emppersonalinfo.getZipCd1()
			+"' , ISADDRSAME='"+emppersonalinfo.getIsaddrsame()			
			+"' , RETIREFRMPENSION='"+emppersonalinfo.getRetirefrmpension()
			+"' where personalid="+emppersonalinfo.getPersonalId();
			log.info("update emp personal info is "+sSQL);
			i = db.executeUpdate(sSQL);
			
			log.info("updated  record successfully in updateUserGroup() "+ i);			
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception updateEmpPersonalInfo >>>>>>>>>>>>");
			e.printStackTrace();
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}  
		log.info("StaffConfigurationDAO:updateEmpPersonalInfo() Leaving Method");
	}
	
	public void deleteEmpinfo(String lpcNo) {
		log.info("StaffConfigurationDAO:deleteEmpinfo() Entering Method");
		
		try {	
			db.makeConnection();con = db.getConnection();
			StringTokenizer st = new StringTokenizer(lpcNo, ",");
			int i = 0,i1=0,i2=0,i3=0,i4=0,i5=0,i6=0;			
			
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				String sSQL  = "update EmployeeInfo  set deleteFlag ='Y' where empNo ='"+s+"'";
				i=db.executeUpdate(sSQL);				
				String sSQL1 = "update EmployeePersonalInfo  set deleteFlag ='Y' where empNo ='"+s+"'";
				i1 = db.executeUpdate(sSQL1);				
				String sSQL2 = "update EmployeeEarnings  set deleteFlag ='Y' where empNo ='"+s+"'";
				i2 = db.executeUpdate(sSQL2);				
				String sSQL3 = "update EmployeeDeductions  set deleteFlag ='Y' where empNo ='"+s+"'";
				i3 =db.executeUpdate(sSQL3);				
				String sSQL4 = "update EmployeePerks  set deleteFlag ='Y' where empNo ='"+s+"'";
				i4 =db.executeUpdate(sSQL4);				
				String sSQL5 = "update EmployeeRecoveries  set deleteFlag ='Y' where empNo ='"+s+"'";
				i5 =db.executeUpdate(sSQL5);				
				String sSQL6 = "update EmpDependents  set deleteFlag ='Y' where empNo ='"+s+"'";											
				i6 = db.executeUpdate(sSQL6);
			}
			log.info("deleted  record successfully in DeleteImpino() " + i);			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}  
		log.info("StaffConfigurationDAO:deleteEmpinfo() Leaving Method");

	}
	
	public void addEmpPersonalinfo(EmployeePersonalInfo emppersonalinfo) {
		log.info("StaffConfigurationDAO:addEmpPersonalinfo() Entering Method");
		int personalId=0;
		try {
			db.makeConnection();con = db.getConnection();
			
			personalId =Integer.parseInt(AutoGeneration.getNextCode("employeepersonalinfo","personalid",5,con));
			String sSQL ="insert into employeepersonalinfo (empno,empdob,empdoj,maritalstat,marriagedt," +
					"nationality,religion,permenentaddress,city,state,zipcd,currentaddress,city1," +
					"state1,mailid,contactno1,contactno2,deleteflag,zipcd1,personalid,ISADDRSAME)" +
					" values ('"+emppersonalinfo.getEmpNo()+"'," +
					"'"+emppersonalinfo.getEmpDob()+"'," +
					"'"+emppersonalinfo.getEmpDoj()+"'," +					
					"'"+emppersonalinfo.getMaritalStat()+"','"+emppersonalinfo.getMarriageDt()+"'," +
					"'"+emppersonalinfo.getNationality()+"','"+emppersonalinfo.getReligion()+"'," +
					"'"+emppersonalinfo.getPermentAddress()+"','"+emppersonalinfo.getCity()+"'," +
					"'"+emppersonalinfo.getState()+"',"+emppersonalinfo.getZipCd()+"," +
					"'"+emppersonalinfo.getCurrentAddress()+"','"+emppersonalinfo.getCity1()+"'," +
					"'"+emppersonalinfo.getState1()+"','"+emppersonalinfo.getMailId()+"'," +
					"'"+emppersonalinfo.getContactNo1()+"','"+emppersonalinfo.getContactNo2()+"'," +
					"'"+emppersonalinfo.getDeleteFlag()+"','"+emppersonalinfo.getZipCd1()+"',"+personalId+",'"+emppersonalinfo.getIsaddrsame()+"')";
			log.info("sSQL is ============ "+sSQL);
			db.executeUpdate(sSQL);			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e);
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}  
		log.info("StaffConfigurationDAO:addEmpPersonalinfo() Leaving Method");
	}
	
	public EmployeeInfo editEmpinfo(String lpcno) {
		log.info("StaffConfigurationDAO:editEmpinfo() Entering Method");		
		EmployeeInfo empInfo = new EmployeeInfo();
		try {		
			db.makeConnection();con = db.getConnection();
			String query="SELECT e.*, (SELECT desgcode  FROM staffdesignation WHERE designationcd = e.designationcd) desgcode  FROM employeeinfo e WHERE empno = '"+lpcno+"'";
			
			log.info("editEmpinfo query "+query);
			rs=db.getRecordSet(query);
			
			if(rs.next()){
				
				empInfo.setEmpNo(rs.getString("empno"));
				empInfo.setPensionno(rs.getString("PENSIONNO"));
				empInfo.setEmplnumber(rs.getString("EMPLNUMBER"));
				/*empInfo.setEmpFirstName(rs.getString("empfirstname"));
				empInfo.setEmpLastName(rs.getString("emplastname"));
				empInfo.setEmpSurName(rs.getString("empsurname"));*/
				empInfo.setEmpName(rs.getString("EMPLOYEENAME"));
				empInfo.setCpfNo(rs.getString("cpfno"));
				empInfo.setBankId(rs.getString("bankid"));
				empInfo.setBacno(rs.getString("BACNO"));
				empInfo.setPaymentType(rs.getString("paymenttype"));
				empInfo.setCadreCd(rs.getString("cadrecd"));
				empInfo.setDesignationCd(new Integer(rs.getInt("designationcd")));
				empInfo.setDesgCode(rs.getString("desgcode"));
				/*empInfo.setEffectiveDt(rs.getDate("effectivedt"));
				empInfo.setEndingDt(rs.getDate("endingdt"));*/
				empInfo.setGender(rs.getString("gender"));
				empInfo.setPan(rs.getString("pan"));
				//empInfo.setEmployementType(rs.getString("employementtype"));
				
				empInfo.setLastupdatedDt(rs.getDate("lastupdateddt"));

				empInfo.setStaffCategoryCd(new Integer(rs.getInt("staffctgrycd")));	
				empInfo.setDisciplineCd(new Integer(rs.getInt("DISCIPLINECD")));
				empInfo.setStaffTypeCd(rs.getString("STAFFTYPECD"));
				empInfo.setStatus(rs.getString("status"));
				empInfo.setRegioncd(rs.getString("REGIONCD"));
				empInfo.setStation(rs.getString("STATIONCD"));
				  empInfo.setHraOption(rs.getString("HRAOPTION"));
				  empInfo.setQuarterType(rs.getString("QUATERTYPE"));
				  empInfo.setPftaxoption(rs.getString("PFTAXOPTION"));
				  empInfo.setCadrecod(rs.getString("CADRECOD"));
				  empInfo.setPaymentType(rs.getString("PAYMENTTYPE"));
				  empInfo.setPftrust(rs.getString("PFTRUST"));
				  empInfo.setPetrol(rs.getString("PETROL"));
				  empInfo.setGsliscd(rs.getString("GSLISCADRECD"));
				  empInfo.setDeleteFlag(rs.getString("paybillcd"));
				  
				  empInfo.setPensionoption(rs.getString("PENSIONOPTION"));
				  empInfo.setMedicalcardno(rs.getString("MEDICALNO"));
				  
				 log.info("empInfo.setPensionoption(----------"+empInfo.getPensionoption());
				 log.info("empInfo.setMedicalcardno(--------"+empInfo.getMedicalcardno());
			}								
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception =========== >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				log.printStackTrace(e1);
			}
		}
		log.info("StaffConfigurationDAO:editEmpinfo() Leaving Method");
		return empInfo;
	}
	public void updateEmpInfo(EmployeeInfo empinfo) {
		log.info("StaffConfigurationDAO:updateEmpInfo() Entering Method");	
		int i=0;
		try {
			db.makeConnection();
			con = db.getConnection();
			con.setAutoCommit(false);
			String sSQL = "update EmployeeInfo  set "
				+" EMPLNUMBER='"+empinfo.getEmplnumber().trim()
			+"' , PENSIONNO='"+empinfo.getPensionno().trim()
			+"' , EMPLOYEENAME='"+empinfo.getEmpName().trim()
			+"' , cpfno='"+empinfo.getCpfNo()+"' , bankid='"+empinfo.getBankId()
			+"', paymenttype='"+empinfo.getPaymentType()
			+"' , cadrecd='"+empinfo.getCadreCd()+"'"
			+" , designationcd=(select DESIGNATIONCD from STAFFDESIGNATION where DESGCODE ="+empinfo.getDesgCode()+")"
			+" , DISCIPLINECD="+empinfo.getDisciplineCd()	
			+" , STAFFTYPECD="+empinfo.getStaffTypeCd()	
			+" ,  gender='"+empinfo.getGender()
			+"' , pan='"+empinfo.getPan()				
			+"' , lastupdateddt='' , STATUS='"+empinfo.getStatus()
			+"' , staffctgrycd="+empinfo.getStaffCategoryCd()
			+", REGIONCD='"+empinfo.getRegioncd()
			+"', STATIONCD='"+empinfo.getStation()
			+"', BACNO='"+empinfo.getBacno().trim()
			+"', HRAOPTION='"+empinfo.getHraOption()
			+"', QUATERTYPE='"+empinfo.getQuarterType()
			+"', PFTRUST='"+empinfo.getPftrust()
			+"', PETROL='"+empinfo.getPetrol()
			+"', CADRECOD='"+empinfo.getCadrecod()
			+"', GSLISCADRECD='"+empinfo.getGsliscd()
			+"', PFTAXOPTION='"+empinfo.getPftaxoption()
			+"', paybillcd='"+empinfo.getDeleteFlag()
			+"', PAYBILL =(select PAYBILLNM from paybillmt where PAYBILLCD="+empinfo.getDeleteFlag()+")"
			+" where empno='"+empinfo.getEmpNo()+"'";
			log.info("Update empinfo qry is ------------- "+sSQL);
			i = db.executeUpdate(sSQL);
			
			
			double pet = Double.parseDouble(empinfo.getPetrol());
			double oldpet = Double.parseDouble(empinfo.getOldpetrol());
			if(!empinfo.getGsliscd().equals(empinfo.getOldgsliscd())){
				log.info("Gslis is updated");	
				String updvconqry = "update EMPERNSDEDUCS set amount=(select amount from GSLI_CAT_AMT where category =(select category from GSLISCADRE where staffctgrycd = "+empinfo.getStaffCategoryCd()+"  and GSLISCADRECD = "+empinfo.getGsliscd()+")and status='A') where empno = "+empinfo.getEmpNo()+" and EARNDEDUCD=(select EARNDEDUCD from CONFIGMAPPING where staffctgrycd = "+empinfo.getStaffCategoryCd()+" and CONFIGNAME='GSLIS')";
				log.info(updvconqry);
				db.executeUpdate(updvconqry);
			}
			if(pet != oldpet){
				log.info("Petrol is updated");
				double petrate = Double.parseDouble(empinfo.getPetrolrate());
				
				double updvcon = Math.round(pet * petrate);
				String updvconqry = "update EMPERNSDEDUCS set amount= "+updvcon+" where empno = "+empinfo.getEmpNo()+" and EARNDEDUCD=(select EARNDEDUCD from CONFIGMAPPING where CONFIGNAME='VCON')";
				log.info(updvconqry);
				db.executeUpdate(updvconqry);
			}
			/*
			 * updating HRA amount if hraoption is changed
			 */
			if(!empinfo.getHraOption().equals(empinfo.getOldHraOption())){
				String updatehraqry  = "";
				if(!empinfo.getHraOption().equals("H")){
					log.info("not equal to HRA");
					
					 updatehraqry = "update EMPERNSDEDUCS set amount= 0 where empno = "+empinfo.getEmpNo()+" and EARNDEDUCD=(select EARNDEDUCD from CONFIGMAPPING where CONFIGNAME='HRA')";
					 log.info(updatehraqry);
				}
				if(empinfo.getHraOption().equals("H")){
					log.info(" equal to HRA");
					String selqry = "select * from (select sum(amount)amt from EMPERNSDEDUCS where instr((','||(select dependson from  SALHEAD_LEDG_MAPPING slm where earndeducd = (select EARNDEDUCD from CONFIGMAPPING where CONFIGNAME='HRA' ) and CADRECOD='"+empinfo.getCadrecod()+"')||','),(','||earndeducd||','))>=1 and empno = '"+empinfo.getEmpNo()+"'),(select PERCENTAGEVALUE hrapercent from SALHEAD_LEDG_MAPPING where earndeducd = (select EARNDEDUCD from CONFIGMAPPING where CONFIGNAME='HRA') and CADRECOD='"+empinfo.getCadrecod()+"'),((select EARNDEDUCD as hracd from CONFIGMAPPING where CONFIGNAME='HRA' ))";
					rs = db.getRecordSet(selqry);
					double hraamt = 0;
					String hracd = "";
					if(rs.next()){
						hraamt = Math.round((rs.getDouble("amt")*rs.getDouble("hrapercent"))/100);
						hracd = rs.getString("hracd");
					}
					updatehraqry = "update EMPERNSDEDUCS set amount= "+hraamt+" where empno = '"+empinfo.getEmpNo()+"' and earndeducd = "+hracd;
					log.info(updatehraqry);
				}
				db.executeUpdate(updatehraqry);
			}
			
			log.info("updated  record successfully in updateUserGroup() "+ i);	
			db.setAutoCommit(true);
			String activityDesc="Employee Information Of "+empinfo.getEmpName().trim()+" Updated.";
			ActivityLog.getInstance().write(empinfo.getUsercd(), activityDesc, "E", String.valueOf(empinfo.getEmpNo()), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception----------  >>>>>>>>>>>>");
			log.printStackTrace(e);
			try{
				con.rollback();
			}catch(Exception e1){
				log.printStackTrace(e1);
			}
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:updateEmpInfo() Leaving Method");	
	}	
	public void addEmpDependentsinfo(EmpDependentsInfo empdependentsinfo) {
		log.info("StaffConfigurationDAO:addEmpDependentsinfo() Entering Method");	
		int dependentId=0;
		try {
			db.makeConnection();con = db.getConnection();
			dependentId =Integer.parseInt(AutoGeneration.getNextCode("empdependents","dependentid",11,con));
			
			String sSQL ="insert into empdependents (empno,dependentnm,relationship,age,deleteflag," +
			"noofdependents,dependentid)" +
			"values ('"+empdependentsinfo.getEmpNo()+"','"+empdependentsinfo.getDependentName()
			+"','"+empdependentsinfo.getRelation()+"',"+empdependentsinfo.getAge()
			+",'"+empdependentsinfo.getDeleteFlag()+"',"+empdependentsinfo.getNoOfDependents()					
			+","+dependentId+")";
	        db.executeUpdate(sSQL);			
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:addEmpDependentsinfo() Leaving Method");	
	}
	
	
	/************ Employee Earnings info ******
	 * @param perRecType 
	 * @param staffCategoryCd */
	
	
	public EmployeeEarningInfo editEmpEarnings(String empNo,String eardedType, String perRecType, int staffCategoryCd) {
		log.info("StaffConfigurationDAO:editEmpEarnings() Entering Method");	
		//EmployeeEarningInfo empearningInfo=null;
		EmployeeEarningInfo  earningInfo=null;
		
		
		try {
			ArrayList array = new ArrayList();
			ArrayList array1 = new ArrayList();
			db.makeConnection();con = db.getConnection();
			//String query1 ="select * from employeeinfo ei where empno = '"+empNo+"'";
			String query1 =" select ei.empno,ei.cadrecod,ei.employeename,ei.STAFFCTGRYCD,ei.PETROL,ei.HRAOPTION,ei.QUATERTYPE,sg.DESIGNATIONNM  ,( " +
					" (SELECT earndeducd FROM configmapping WHERE configname = 'GSLIS' ) )gslisearncd," +
					" (SELECT earndeducd  FROM configmapping WHERE configname = 'VCON')vconcd," +
					"(  SELECT amount FROM gsli_cat_amt WHERE CATEGORY = (SELECT CATEGORY FROM gsliscadre WHERE gsliscadrecd = ei.gsliscadrecd)and status='A' )gslisamt" +
					" from employeeinfo ei,staffdesignation sg where ei.DESIGNATIONCD = sg.DESIGNATIONCD and  empno = '"+empNo+"'";
			log.info("query1  is "+ query1);
			//String query2="select type,slno,emp.EARNDEDUCD EARNDEDUCD,EARNDEDUNM,CALCULATIONTYPE,ear.isbasicsalary isbasicsalary,PERCENTAGEVALUE,MINAMOUNT,FIXEDAMOUNT,MAXAMOUNT,DEPENDSON,emp.amount AMOUNT from empernsdeducs emp,earndedumaster ear, EmployeeInfo info where ear.earndeducd = emp.earndeducd   and info.empno = emp.empno and emp.empno = '"+empNo+"'  and type in( '"+eardedType+"','"+perRecType+"')  order by slno";
			String query2=" SELECT   ear.TYPE, slno, emp.earndeducd earndeducd, earndedunm, calculationtype,  ear.isbasicsalary isbasicsalary, percentagevalue, minamount, fixedamount, maxamount, slm.dependson, emp.amount amount FROM empernsdeducs emp, earndedumaster ear, employeeinfo info,SALHEAD_LEDG_MAPPING slm  WHERE ear.earndeducd = emp.earndeducd   and emp.EARNDEDUCD = slm.EARNDEDUCD   AND slm.CADRECOD = info.cadrecod  AND info.empno = emp.empno  AND emp.empno = '"+empNo+"' AND ear.TYPE IN ('"+eardedType+"','"+perRecType+"') ORDER BY PRINTORDER ";
			log.info("query2  is "+ query2);
			String query3="select *  from configmapping  where type='E'";
			log.info("query3  is"+query3 );
			rs=db.getRecordSet(query1);
			rs1 = db.getRecordSet(query2);
            while(rs1.next()){
            	 
            	earningInfo= new EmployeeEarningInfo();	
            	
            	earningInfo.setSlno(new Integer(rs1.getInt("SLNO")));
            	
            	earningInfo.setEarningCd(new Integer(rs1.getString("EARNDEDUCD")));
            	
            	earningInfo.setEarningName(rs1.getString("EARNDEDUNM"));
            
            	earningInfo.setCalcType(rs1.getString("CALCULATIONTYPE"));
            
            /*	double perval = 0;
            	String pv = rs1.getString("PERCENTAGEVALUE");*/
            	earningInfo.setPercentVal(new Double(rs1.getString("PERCENTAGEVALUE")));
            	
            	earningInfo.setMinAmt(new Double(rs1.getString("MINAMOUNT")));
            	
            	earningInfo.setMaxAmt(new Double(rs1.getString("MAXAMOUNT")));
            	
            	earningInfo.setFixedAmt(new Double(rs1.getString("fixedamount")));
            	
            	earningInfo.setAmount(new Double(rs1.getString("AMOUNT")));
            	
            	earningInfo.setIsBasicSal(rs1.getString("isbasicsalary"));
            	
            	earningInfo.setDependsstr(rs1.getString("DEPENDSON"));
            	
            	if(rs1.getString("type").equals("E") ||rs1.getString("type").equals("D"))
            	array.add(earningInfo);
            	else if(rs1.getString("type").equals("PK") ||rs1.getString("type").equals("OR"))
                array1.add(earningInfo);
            
            	
						
			}
           if(rs.next()){
            	earningInfo= new EmployeeEarningInfo();	
            	earningInfo.setEmpNo(rs.getString("empno"));
            	earningInfo.setEmpName(rs.getString("employeename"));
            	earningInfo.setEmpDesignation(rs.getString("designationnm"));
            	earningInfo.setStaffCategoryCd(new Integer(rs.getString("STAFFCTGRYCD")));
            	//earningInfo.setStatus(rs.getString("status"));
            	earningInfo.setCadrecod(rs.getString("cadrecod"));
            	earningInfo.setHraOption(rs.getString("HRAOPTION"));
            	earningInfo.setQuarterType(rs.getString("QUATERTYPE"));
            	earningInfo.setPetrol(rs.getString("petrol"));
            	earningInfo.setGsliscd(rs.getString("gslisearncd"));
            	earningInfo.setGslisamt(rs.getString("gslisamt"));
            	earningInfo.setVconcd(rs.getString("vconcd"));
            	           	            	
            	
            }
           rs.close();
           rs=db.getRecordSet(query3);
           if(rs.next())
         {
        	   
        	   earningInfo.setConfigEarningcd(new Integer(rs.getString("EARNDEDUCD")));
         }
            earningInfo.setEarnings(array);
            earningInfo.setPerks(array1);
            
		} catch (Exception e) {
			log.info("<<<<<<<<<< STAFFCONFIGURARTIONDAO:editEmpEarnings()  >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				log.printStackTrace(e1);
			}
		}
		log.info("StaffConfigurationDAO:editEmpEarnings() Leaving Method");	
		return earningInfo;
	}
	
	
	/******* Employee Deductions *******/
	
	public void addEmpDeductionInfo(EmployeeDeductionsInfo empdeductionsinfo) {
		log.info("StaffConfigurationDAO:addEmpDeductionInfo() Entering Method");
		int deductionId=0;
		try {
			db.makeConnection();con = db.getConnection();
			deductionId =Integer.parseInt(AutoGeneration.getNextCode("employeedeductions","deducid",5,con));
			String sSQL ="insert into employeedeductions (deducid,empno,advance,vpf,compadv," +
			"intonadvance,licencefee,cpf,gslis,cpfadv,festadv,convadv,proftax," +
			"cpfarriers,otherdeduc,ojta,hbadv,deleteflag)" +
			"values ("+deductionId+",'"+empdeductionsinfo.getEmpNo()+"'"
			+",'"+empdeductionsinfo.getAdvance()+"',"+empdeductionsinfo.getVpf()
			+",'"+empdeductionsinfo.getCompAdv()+"',"+empdeductionsinfo.getIntonAdvance()
			+",'"+empdeductionsinfo.getLicenceFee()+"',"+empdeductionsinfo.getCpf()
			+",'"+empdeductionsinfo.getGslis()+"',"+empdeductionsinfo.getCpfAdv()
			+",'"+empdeductionsinfo.getFestAdv()+"',"+empdeductionsinfo.getConvAdv()
			+",'"+empdeductionsinfo.getProfTax()+"',"+empdeductionsinfo.getCpfArriers()
			+",'"+empdeductionsinfo.getOtherDeduc()+"',"+empdeductionsinfo.getOjta()
			+",'"+empdeductionsinfo.getHbAdv()+"',"+empdeductionsinfo.getDeleteFlag()
			+")";
	        db.executeUpdate(sSQL);	
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:addEmpDeductionInfo() Leaving Method");	

	}	

	public Integer selectGroupCd(String groupCd) {
		log.info("StaffConfigurationDAO:selectGroupCd() Entering Method");
		int cd = 0;
		GroupInfo groupinfo=null;
		String countQuery;
	
		try {			
			db.makeConnection();con = db.getConnection();
			
			countQuery ="select count(*) from GROUPUSERSINFO"; 
			countQuery += " where groupCd = "+ groupCd;
			
			cd = db.getRecordCount(countQuery);			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:selectGroupCd() Leaving Method");
		return new Integer(cd);
	}
	
	public void insertEarning(EarningMasterInfo earning) throws ApplicationException {
		db.makeConnection();con = db.getConnection();
		log.info("StaffConfigurationDAO:insertEarning() Entering Method");
		int earndedId=0;
		try{
			
			earndedId =Integer.parseInt(AutoGeneration.getNextCode("EARNDEDUMASTER","EARNDEDUCD",10,con));
			
		
			/*String sSQL ="insert into EARNDEDUMASTER (EARNDEDUCD,EARNDEDUNM,EARNDEDUDESC,isbasicsalary," +
			"printtoslip,printorder,ledgercd,istaxable,DEPENDSON,staffctgrycd," +
			"calculationtype,percentagevalue,fixedamount,minamount,maxamount,STATUS,Type,USERCD)" +
			"values ("+earndedId+",'"+earning.getEarningName()
			+"','"+earning.getEarningDesc()
			+"','"+earning.getIsBasicSal()+"','"+earning.getPrintToPayslip()+"',"+earning.getPrintOrder()
			+",'"+earning.getLedgerCd()
			+"','"+earning.getIsTaxable()
			+"','"+earning.getDepends()+"',"+earning.getStaffCategoryCd()
			+",'"+earning.getCalcType()+"',"+earning.getPercentVal()
			+","+earning.getFixedAmt()+","+earning.getMinAmt()
			+","+earning.getMaxAmt()+",'"+earning.getDeletedFlag()
			+"','"+earning.getType()+"','"+earning.getUserCd()+"')";*/
			con.setAutoCommit(false);
			String sSQL ="insert into EARNDEDUMASTER (EARNDEDUCD,EARNDEDUNM,SHORTNAME,EARNDEDUDESC,isbasicsalary," +
			"printtoslip,printorder,istaxable," +
			"STATUS,Type,USERCD)" +
			"values ("+earndedId+",'"+earning.getEarningName()
			+"','"+earning.getShortName()
			+"','"+earning.getEarningDesc()
			+"','"+earning.getIsBasicSal()+"','"+earning.getPrintToPayslip()+"',"+earning.getPrintOrder()
			
			+",'"+earning.getIsTaxable()			
			+"','"+earning.getDeletedFlag()
			+"','"+earning.getType()+"','"+earning.getUserCd()+"')";
			log.info(sSQL);
	        db.executeUpdate(sSQL);	  
	        String activityDesc="Earning and Deduction Master Of "+earning.getEarningName()+" Saved.";
	        ActivityLog.getInstance().write(earning.getUserCd(), activityDesc, "N", String.valueOf(earndedId), con);
	      /*  String command = "{call add_emp_earncd_defval(?)}";    
			
			CallableStatement cstmt = con.prepareCall (command);
			cstmt.setInt(1, earndedId);			    
			cstmt.execute();
			cstmt.close();
	        con.commit();
			*/
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception insertEarning() >>>>>>>>>>>>");
			log.printStackTrace(e);
			try{
				con.rollback();
			}catch(Exception re){
				log.printStackTrace(re);
			}
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:insertEarning() Leaving Method");
	}
  public boolean checkEarnName(String earnname,int earncd){
	  log.info("StaffConfigurationDAO:checkEarnName() Entering Method");
		boolean flag=false;
		Integer userCount=null;
		
		try{
			//flag = CommonDAO.checkName(session,"EarningMasterInfo","earningName",earnname,"earningCd",earncd);
		
	} catch (Exception e) {
		log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
	} finally {
		try {
			db.closeCon();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	log.info("StaffConfigurationDAO:checkEarnName() Leaving Method");
		return flag;
  }

  public SearchEarningInfo searchEarnings(SearchEarningInfo earninginfo) {
		db.makeConnection();con = db.getConnection();
		log.info("StaffConfigurationDAO:searchEarnings() Entering Method");
		ArrayList alist = new ArrayList();
		String countQuery, sSQL;
		try {

			countQuery = "select count(*) from EARNDEDUMASTER ";
			sSQL = "select EARNDEDUCD,EARNDEDUNM,EARNDEDUDESC,decode(trim(STATUS),'N','InActive','A','Active' )STATUS,decode(TYPE,'E','Earning','D','Deduction','PK','PERK','OR','Other Recoveries','ADV','Advance','ADVINT','Advance Interest')TYPE,rownum as limit  from EARNDEDUMASTER em ";

			log.info("staff type code not null in searchEarnings() "
					+ earninginfo.getSearchtype());
			if (earninginfo.getSearchtype() != null) {
			
				if (!(earninginfo.getSearchtype().equals(""))) {
					
					countQuery += " where Upper(TYPE) = Upper('"
							+ earninginfo.getSearchtype().trim() + "')";
					sSQL += " where Upper(TYPE) = Upper('"
							+ earninginfo.getSearchtype().trim() + "')";
					log.info("staff type code not null in searchEarnings() "
							+ earninginfo.getSearchtype());
				}
			}
			
			if (earninginfo.getSearchearningName() != null) {
				if (!(earninginfo.getSearchearningName().equals(""))) {
											
					if (!(earninginfo.getSearchtype().equals(""))) {		
						countQuery += " and Upper(EARNDEDUNM) like Upper('%"
								+ earninginfo.getSearchearningName().trim() + "%')";
						sSQL += " and Upper(EARNDEDUNM) like Upper('%"
								+ earninginfo.getSearchearningName().trim() + "%')";
					} else {
						countQuery += " where Upper(EARNDEDUNM) like  Upper('%"
								+ earninginfo.getSearchearningName().trim() + "%')";
						sSQL += " where Upper(EARNDEDUNM) like Upper('%"
								+ earninginfo.getSearchearningName().trim() + "%')";
					}
				
				
				}
			}
			//System.out.println("sSQL :::"+sSQL);
			rs = db.getRecordSet(sSQL);
			while(rs.next()){
				SearchEarningInfo ss = new SearchEarningInfo();
				ss.setSearchearningCd(rs.getString("EARNDEDUCD"));
				ss.setSearchtype(rs.getString("TYPE"));
				ss.setSearchearningName(rs.getString("EARNDEDUNM"));
				ss.setSearchearningDesc(rs.getString("EARNDEDUDESC"));
				ss.setUpdateStatus(rs.getString("STATUS"));
				alist.add(ss);					
			}			
			//log.info("list in searchEarnings() " + alist);
			log.info("list size  in searchEarnings() " + alist.size());
			earninginfo.setSearchList(alist);
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:searchEarnings() Leaving Method");
		return earninginfo;
	
  }
	public List getStaffDisciplines(){
		log.info("StaffConfigurationDAO:getStaffDisciplines() Entering Method");
		List disciplinelist = new ArrayList();
		try{	
			db.makeConnection();con = db.getConnection();
			String query="select * from staffdiscipline where status='A'";
			log.info("getStaffDisciplines:query "+query);
			rs=db.getRecordSet(query);
			
            while(rs.next()){
            	StaffDiscipline staff=new StaffDiscipline();
            	staff.setDisciplineCd(new Integer(rs.getInt("disciplinecd")));
            	staff.setDisciplineNm(rs.getString("disciplinenm"));
            	staff.setDisciplineDesc(rs.getString("disciplinedesc"));
            	staff.setStatus(rs.getString("status"));
            	//staff.setStaffTypeCd(new Integer(rs.getInt("stafftypecd")));
            	disciplinelist.add(staff);                          
            }
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< getStaffDisciplines Exception  >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:getStaffDisciplines() Leaving Method");
		return disciplinelist;
	}
	public List getStaffCategories(){
		log.info("StaffConfigurationDAO:getStaffCategories() Entering Method");
		List categorylist = new ArrayList();
		try{
			db.makeConnection();con = db.getConnection();
			String query="select * from staffcategory where deleteflag='N'";
			rs=db.getRecordSet(query);
			
            while(rs.next()){
            	StaffCategoryInfo staffcategory=new StaffCategoryInfo();
            	staffcategory.setStaffCategoryCd(new Integer(rs.getInt("staffctgrycd")));
            	staffcategory.setStaffCategoryName(rs.getString("staffctgrynm"));
            	staffcategory.setStaffCategoryDesc(rs.getString("staffctgrydescr"));
            	staffcategory.setStatus(rs.getString("status"));            	
            	categorylist.add(staffcategory);                          
            }			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:getStaffCategories() Leaving Method");
		return categorylist;
	}
	public List getEarningNames(EarningMasterInfo earning){
		List earnlist = new ArrayList();
		EarningMasterInfo earninfo = null;
		try{
			db.makeConnection();con = db.getConnection();
			String query="Select EARNDEDUCD,EARNDEDUNM from EARNDEDUMASTER where STATUS='A' and TYPE='E' order by printorder";
			
			if(earning.getStaffCategoryCd()!=null && !(earning.getStaffCategoryCd().equals(""))){
				query = query +" and STAFFCTGRYCD="+earning.getStaffCategoryCd();
			}
			log.info("----------query---------"+query);
			rs = db.getRecordSet(query);
			while(rs.next()){
				earninfo = new EarningMasterInfo();
				earninfo.setEarningCd(new Integer(rs.getString("EARNDEDUCD")));
				earninfo.setEarningName(rs.getString("EARNDEDUNM"));				
				earnlist.add(earninfo);
			}
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception SQL >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return earnlist;
	}	
	
	public EarningMasterInfo showAddEarning(){
		log.info("StaffConfigurationDAO:showAddEarning() Entering Method");
		EarningMasterInfo earnings = new EarningMasterInfo();
		
		try{	
			db.makeConnection();con = db.getConnection();
			//List disciplinelist = getStaffDisciplines();
			//List acclist = session.createSQLQuery("select acctypecd from acctypes").addScalar("acctypecd",Hibernate.INTEGER).list();
			
			List categorylist = getStaffCategories();//session.createQuery("from StaffCategoryInfo").list();
			//earnings.setAcclist((ArrayList)acclist);
			earnings.setCategorylist((ArrayList)categorylist);
			//earnings.setDisciplinelist((ArrayList)disciplinelist);
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:showAddEarning() Leaving Method");
		return earnings;
	}
	public EarningMasterInfo showEditEarning(int earncd){
		db.makeConnection();con = db.getConnection();
		log.info("StaffConfigurationDAO:showEditEarning() Entering Method");
		List list = new ArrayList();
		EarningMasterInfo einfo = null;
		ResultSet rs2=null;
		String name=null,dependson="",earnname="";
		String dependsstr[];
		int i=0;
		try{
			String sSQL = "select * from EARNDEDUMASTER where EARNDEDUCD="+earncd;
			log.info("---------sSQL--------"+sSQL);
			rs = db.getRecordSet(sSQL);
			while(rs.next()){
				einfo = new EarningMasterInfo();
				einfo.setEarningCd(new Integer(rs.getInt("EARNDEDUCD")));
				einfo.setEarningName(rs.getString("EARNDEDUNM")); 
				einfo.setEarningDesc(rs.getString("EARNDEDUDESC"));
				einfo.setIsBasicSal(rs.getString("isbasicsalary"));
				einfo.setPrintToPayslip(rs.getString("printtoslip"));
				einfo.setPrintOrder(new Integer(rs.getInt("printorder")));
				//einfo.setLedgerCd(rs.getString("ledgercd"));
				einfo.setIsTaxable(rs.getString("istaxable"));
				einfo.setShortName(rs.getString("SHORTNAME"));
				//einfo.setIsReimbursed(rs.getString("isreimbursed"));
				//einfo.setStaffCategoryCd(new Integer(rs.getInt("staffctgrycd")));
				//einfo.setCalcType(rs.getString("calculationtype"));
				//einfo.setPercentVal(new Double(rs.getInt("percentagevalue")));
				//einfo.setFixedAmt(new Double(rs.getInt("fixedamount")));
				//einfo.setMinAmt(new Double(rs.getInt("minamount")));
				//einfo.setMaxAmt(new Double(rs.getInt("maxamount")));
				einfo.setDeletedFlag(rs.getString("STATUS"));
				einfo.setType(rs.getString("type"));
				//einfo.setDepends(rs.getString("DEPENDSON"));
				
				//dependson=rs.getString("DEPENDSON");
				
				/*StringTokenizer st=new  StringTokenizer(dependson,",");
				dependsstr=new String[st.countTokens()];
				while(st.hasMoreTokens()){		
					
					earnname=sh.getDescription("EARNDEDUMASTER","EARNDEDUNM","EARNDEDUCD",st.nextToken(),con);
				    log.info("------Names------"+earnname);
				    dependsstr[i]=earnname;
				    i++;
				}				
				log.info("======Length of Array======"+dependsstr.length);
				*/
				list.add(einfo);					
			}	
			
			/*einfo = (EarningMasterInfo)list.get(0);
			String query="select ledgernm from ledgermaster where ledgercd='"+einfo.getLedgerCd()+"'";
            rs2 = db.getRecordSet(query);
			
			while(rs2.next()){
			   name=rs2.getString("ledgernm");
			}			
			einfo.setLedgernm(name);*/
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:showEditEarning() Leaving Method");
		return einfo;
	}
	public void updateEarning(EarningMasterInfo earnings){
		db.makeConnection();con = db.getConnection();
		//log.info("StaffConfigurationDAO:updateEarning() Entering Method");
		int i=0;
		try{
			
			/*String sSQL = "update EARNDEDUMASTER  set EARNDEDUNM='"+earnings.getEarningName()
			+"' , EARNDEDUDESC='"+earnings.getEarningDesc()
			+"' , isbasicsalary='"+earnings.getIsBasicSal()
			+"' , printtoslip='"+earnings.getPrintToPayslip()
			+"'  , printorder="+earnings.getPrintOrder()
			+"  , ledgercd='"+earnings.getLedgerCd()
			+"' , type='"+earnings.getType()
			+"' , istaxable='"+earnings.getIsTaxable()
			+"' , dependson='"+earnings.getDepends()
			+"' , staffctgrycd="+earnings.getStaffCategoryCd()
			+"  , calculationtype='"+earnings.getCalcType()
			+"' , percentagevalue="+earnings.getPercentVal()
			+"  , fixedamount="+earnings.getFixedAmt()
			+"  , minamount="+earnings.getMinAmt()
			+"  , maxamount="+earnings.getMaxAmt()
			+"  , STATUS='"+earnings.getDeletedFlag()
			+"' where EARNDEDUCD="+earnings.getEarningCd();*/
			
			String sSQL = "update EARNDEDUMASTER  set EARNDEDUNM='"+earnings.getEarningName()
			+"' , EARNDEDUDESC='"+earnings.getEarningDesc()
			+"' , SHORTNAME='"+earnings.getShortName()
			+"' , isbasicsalary='"+earnings.getIsBasicSal()
			+"' , printtoslip='"+earnings.getPrintToPayslip()
			+"'  , printorder="+earnings.getPrintOrder()			
			+" , type='"+earnings.getType()
			+"' , istaxable='"+earnings.getIsTaxable()	
			
			+"' where EARNDEDUCD="+earnings.getEarningCd();
			
			log.info("------ updateEarning sSQL-----"+sSQL);
			i = db.executeUpdate(sSQL);		
			String activityDesc="Earning and Deduction Overview Of "+earnings.getEarningName()+" Updated.";
			ActivityLog.getInstance().write(earnings.getUserCd(), activityDesc, "E", String.valueOf(earnings.getEarningCd()), con);
			log.info("updated  record successfully "+ i);
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>");
			log.printStackTrace(e);			
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		///log.info("StaffConfigurationDAO:updateEarning() Leaving Method");		
	}
	public void deleteEarning(String earncds){
		log.info("StaffConfigurationDAO:deleteEarning() Entering Method");
		int i = 0;
		try{
			db.makeConnection();con = db.getConnection();
			StringTokenizer st = new StringTokenizer(earncds, ",");		
			
			while (st.hasMoreTokens()) {
				String sSQL = "update EARNDEDUMASTER  set Status ='N' where EARNDEDUCD ="+Integer.parseInt(st.nextToken());
				i = db.executeUpdate(sSQL);			
			}
		
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:deleteEarning() Leaving Method");				
	}
	
	
	/*//-----------------Deductions------------------------------------
	
	public void insertDeduction(DeductionMasterInfo deduction) throws ApplicationException {
		db.makeConnection();con = db.getConnection();
		log.info("StaffConfigurationDAO:insertDeduction() Entering Method");
		int deductionCd=0;
		try{
			deductionCd =Integer.parseInt(AutoGeneration.getNextCode("deductionmaster","deductioncd",10,con));
			
			String sSQL ="insert into deductionmaster (deductioncd,deductionnm,deductiondesc,disciplinecd,printtoslip," +
			"printorder,acctypecd,ledgercd,isreimbursed,staffctgrycd,calculationtype,percentagevalue,fixedamount," +
			"minamount,maxamount,deleteflag,fyearcd)" +
			"values ("+deductionCd+",'"+deduction.getDeductionName()
			+"','"+deduction.getDeductionDesc()+"',"+deduction.getDisciplineCd()
			+",'"+deduction.getPrintToPayslip()+"',"+deduction.getPrintOrder()
			+","+deduction.getAccTypeCd()+",'"+deduction.getLedgerCd()
			+"','"+deduction.getIsReimbursed()+"',"+deduction.getStaffCategoryCd()
			+",'"+deduction.getCalcType()+"',"+deduction.getPercentVal()
			+","+deduction.getFixedAmt()+","+deduction.getMinAmt()
			+","+deduction.getMaxAmt()+",'"+deduction.getDeletedFlag()
			+"','"+deduction.getFyearcd()+"')";
	        db.executeUpdate(sSQL);	  
	      
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:insertDeduction() Leaving Method");		
	}

	public boolean checkDeductName(String deductname,int deductcd){

		log.info("StaffConfigurationDAO:checkDeductName() Entering Method");
		 boolean flag=false;
		 Integer userCount=null;
		 db.makeConnection();con = db.getConnection();
			
			try{
				
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:checkDeductName() Leaving Method");
		return flag;
	}
	public SearchDeductionInfo searchDeductions(SearchDeductionInfo deductioninfo) {

		db.makeConnection();con = db.getConnection();
		log.info("StaffConfigurationDAO:searchDeductions() Entering Method");
		
		ArrayList alist = new ArrayList();		
		String countQuery, sSQL;
		int st = 0,end = 0,showrec = 10;
		
		try {

			countQuery = "select count(*) from deductionmaster ";
			sSQL = "select dm.*,rownum as limit from deductionmaster dm ";

			log.info("staff type code not null in searchDeductions() "
					+ deductioninfo.getSearchdeductionCd());
			if (deductioninfo.getSearchdeductionCd() != null) {
				if (!(deductioninfo.getSearchdeductionCd().equals(""))) {
					countQuery += " where deductionCd like '"
							+ deductioninfo.getSearchdeductionCd() + "%'";
					sSQL += " where deductioncd like '"
							+ deductioninfo.getSearchdeductionCd() + "%'";
					log.info("staff type code not null in searchDeductions() "
							+ deductioninfo.getSearchdeductionCd());
				}
			}
			if (deductioninfo.getSearchdeductionName() != null) {
				if (!(deductioninfo.getSearchdeductionName().equals(""))) {
					
					if (!(deductioninfo.getSearchdeductionCd().equals(""))) {
						countQuery += " and deductionnm like '"
								+ deductioninfo.getSearchdeductionName() + "%'";
						sSQL += " and deductionnm like '"
								+ deductioninfo.getSearchdeductionName() + "%'";
					} else {
						countQuery += " where deductionnm like  '"
								+ deductioninfo.getSearchdeductionName() + "%'";
						sSQL += " where deductionnm like '"
								+ deductioninfo.getSearchdeductionName() + "%'";
					}
				}
			}
			if (deductioninfo.getSearchdeductionCd() == null
					&& deductioninfo.getSearchdeductionName() == null
					|| (deductioninfo.getSearchdeductionCd().equals("") && deductioninfo
							.getSearchdeductionName().equals(""))) {
				countQuery += " where deleteFlag = 'N'";
				sSQL += " where deleteflag = 'N'";
			} else {
				countQuery += " and deleteFlag = 'N'";
				sSQL += " and deleteflag = 'N'";
			}
			
			int cnt = db.getRecordCount(countQuery);
			String count = cnt+"";
			deductioninfo.setTotolRecords(count);
			log.info("deductioninfo set totalrecords" +count);
						
			log.info("sortfield in searchDeductions"
					+ deductioninfo.getSortfield());
			log.info("sorttype in searchDeductions"
					+ deductioninfo.getSorttype());
			if (!deductioninfo.getSortfield().equals("")) {
				String text = deductioninfo.getSortfield()
						.equals("deductionCd") ? "deductioncd" : (deductioninfo
						.getSortfield().equals("deductionName") ? "deductionnm"
						: "deductiondesc");
				sSQL += " order by " + text + " " + deductioninfo.getSorttype();
			}
			if (!deductioninfo.getNavigation().equals("")) {
				if (deductioninfo.getNavigation().equals("next")) {
					deductioninfo.setPageNo(deductioninfo.getPageNo() + 1);
				}

				if (deductioninfo.getNavigation().equals("previous"))
					deductioninfo.setPageNo(deductioninfo.getPageNo() - 1);
			}

			if (deductioninfo.getPageNo() > 1) {
				deductioninfo.setHasPrevious(true);
				st = (deductioninfo.getPageNo() - 1) * showrec;
			}

			if (Integer.parseInt(deductioninfo.getTotolRecords()) > (deductioninfo
					.getPageNo() * showrec)) {
				deductioninfo.setHasNext(true);
				end = showrec;
			} else {
				end = Integer.parseInt(deductioninfo.getTotolRecords())
						- (st - 1);
			}
			if (deductioninfo.getNavigation() != null) {
				if (deductioninfo.getNavigation().equals("last")) {
					if (((Integer.parseInt(deductioninfo.getTotolRecords()) / showrec) > 1)) {
						st = ((Integer
								.parseInt(deductioninfo.getTotolRecords()) / showrec) * showrec);
					} else {
						st = showrec;
					}
					end = st
							+ (Integer
									.parseInt(deductioninfo.getTotolRecords()) - st);
					deductioninfo.setHasPrevious(true);
					deductioninfo.setHasNext(false);
					deductioninfo.setPageNo((Integer.parseInt(deductioninfo
							.getTotolRecords()) / showrec) + 1);
				}
				if (deductioninfo.getNavigation().equals("first")) {
					deductioninfo.setPageNo(1);
					deductioninfo.setHasPrevious(false);
					deductioninfo.setHasNext(true);
					st = 0;
					end = showrec;
				}
			}
			// sSQL = sSQL+" LIMIT "+st+","+showrec;
			log.info("sSQL in searchDeductions() DAO is " + sSQL);
			
			if (Integer.parseInt(deductioninfo.getTotolRecords()) > (showrec + (st))) {
				end = showrec + st;
			} else {
				end = Integer.parseInt(deductioninfo.getTotolRecords());
			}
		
			deductioninfo.setHeading(prop.getProperty("common.dispsearchrecname") + (st + 1) + " "+prop.getProperty("common.to")
					+ end + " "+prop.getProperty("common.of")+ deductioninfo.getTotolRecords());
			
			//rs = db.getRecordSet(sSQL);
			rs = db.getRecordSet(" Select * from ( "+sSQL+" ) where limit >"+st+" and limit <="+end);
			
			while(rs.next()){
				SearchDeductionInfo ss = new SearchDeductionInfo();
				ss.setSearchdeductionCd(rs.getString("deductioncd"));
				ss.setSearchdeductionName(rs.getString("deductionnm"));
				ss.setSearchdeductionDesc(rs.getString("deductiondesc"));
				alist.add(ss);					
			}			
			
			log.info("list in searchDeductions() " + alist);
			log.info("list size  in searchDeductions() " + alist.size());
			deductioninfo.setSearchList(alist);
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:searchDeductions() Leaving Method");
		return deductioninfo;

	
	}
	public DeductionMasterInfo showAddDeduction(){
		log.info("StaffConfigurationDAO:showAddDeduction() Entering Method");
		DeductionMasterInfo deductions = new DeductionMasterInfo();
		
		try{
			db.makeConnection();con = db.getConnection();
			List disciplinelist = getStaffDisciplines();
			List categorylist = getStaffCategories();//session.createQuery("from StaffCategoryInfo").list();
			
			deductions.setCategorylist((ArrayList)categorylist);
			deductions.setDisciplinelist((ArrayList)disciplinelist);
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:showAddDeduction() Leaving Method");
		return deductions;
	}
	public DeductionMasterInfo showEditDeduction(int deductioncd){
		log.info("StaffConfigurationDAO:showEditDeduction() Entering Method");
		List list = new ArrayList();
		DeductionMasterInfo dinfo = null;
		ResultSet rs2=null;
		String name=null;
		try{
			db.makeConnection();con = db.getConnection();
			String sSQL = "select * from deductionmaster where deductioncd="+deductioncd;
			rs = db.getRecordSet(sSQL);
			 
			while(rs.next()){
				dinfo = new DeductionMasterInfo();
				dinfo.setDeductionCd(new Integer(rs.getInt("deductioncd")));
				dinfo.setDeductionName(rs.getString("deductionnm"));
				dinfo.setDeductionDesc(rs.getString("deductiondesc"));
				dinfo.setDisciplineCd(new Integer(rs.getInt("disciplinecd")));
				dinfo.setPrintToPayslip(rs.getString("printtoslip"));
				dinfo.setPrintOrder(new Integer(rs.getInt("printorder")));
				dinfo.setAccTypeCd(new Integer(rs.getInt("acctypecd")));
				dinfo.setLedgerCd(rs.getString("ledgercd"));
				dinfo.setIsReimbursed(rs.getString("isreimbursed"));
				dinfo.setStaffCategoryCd(new Integer(rs.getInt("staffctgrycd")));
				dinfo.setCalcType(rs.getString("calculationtype"));
				dinfo.setPercentVal(new Integer(rs.getInt("percentagevalue")));
				dinfo.setFixedAmt(new Integer(rs.getInt("fixedamount")));
				dinfo.setMinAmt(new Integer(rs.getInt("minamount")));
				dinfo.setMaxAmt(new Integer(rs.getInt("maxamount")));
				dinfo.setDeletedFlag(rs.getString("deleteflag"));
				dinfo.setFyearcd(rs.getString("fyearcd"));	
				list.add(dinfo);	
				
			}		
			
			dinfo = (DeductionMasterInfo)list.get(0);
			String query="select ledgernm from ledgermaster where ledgercd="+dinfo.getLedgerCd();
            rs2 = db.getRecordSet(query);
			
			while(rs2.next()){
			   name=rs2.getString("ledgernm");
			   
			}			
			dinfo.setLedgernm(name);	
			
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>111111111>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:showEditDeduction() Leaving Method");
		return dinfo;
	}

	public void updateDeduction(DeductionMasterInfo deductions){
		log.info("StaffConfigurationDAO:updateDeduction() Entering Method");
		int i =0;
		try{
			
			db.makeConnection();con = db.getConnection();
			String sSQL = "update deductionmaster  set deductionnm='"+deductions.getDeductionName()
			+"' , deductiondesc='"+deductions.getDeductionDesc()
			+"' , disciplinecd="+deductions.getDeductionCd()
			+"  , printtoslip='"+deductions.getPrintToPayslip()
			+"' , printorder="+deductions.getPrintOrder()
			+"  , acctypecd="+deductions.getAccTypeCd()
			+"  , ledgercd='"+deductions.getLedgerCd()
			+"' , isreimbursed='"+deductions.getIsReimbursed()
			+"' , staffctgrycd="+deductions.getStaffCategoryCd()
			+"  , calculationtype='"+deductions.getCalcType()
			+"' , percentagevalue="+deductions.getPercentVal()		
			+"  , fixedamount="+deductions.getFixedAmt()
			+"  , minamount="+deductions.getMinAmt()
			+"  , maxamount="+deductions.getMaxAmt()
			+"  , deleteflag='"+deductions.getDeletedFlag()
			+"' , fyearcd='"+deductions.getFyearcd()
			+"' where deductioncd="+deductions.getDeductionCd();
			
			i = db.executeUpdate(sSQL);					
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>222222222>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:updateDeduction() Leaving Method");		
	}
	public void deleteDeduction(String earncds){
		log.info("StaffConfigurationDAO:deleteDeduction() Entering Method");
    	int i = 0;
		try{
			db.makeConnection();con = db.getConnection();
			StringTokenizer st = new StringTokenizer(earncds, ",");			
			while (st.hasMoreTokens()) {
				
			String sSQL = "update deductionmaster  set deleteflag='Y' where deductioncd ="+Integer.parseInt(st.nextToken());
			i = db.executeUpdate(sSQL);	
			}
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:deleteDeduction() Leaving Method");		
	}
	*/
	public void insertPayscale(PayscaleMasterInfo payscale) throws ApplicationException {
		log.info("StaffConfigurationDAO:insertPayscale() Entering Method");
		int id=0;
		String name="";
		try{	
			db.makeConnection();con = db.getConnection();
			id =Integer.parseInt(AutoGeneration.getNextCode("payscalemaster","PAYSCLID",8,con));			
			
		    String sSQL ="insert into payscalemaster (PAYSCLID,DESIGNATIONCD,MINSCALE,INCREMENTS," +
				"NEXTSCALE,NEXTINCREMENT,MAXSCALE,status)" +
				" values ("+id+",'"+payscale.getDesignationCd()				
				+"',"+payscale.getMinimumScale()+","+payscale.getIncrement()
				+","+payscale.getNextScale()+","+payscale.getNextIncrement()
				+","+payscale.getMaximumScale()+",'"+payscale.getStatus()+"')";
		    log.info("-----sSQL--------"+sSQL);
		    String qry="select DESIGNATIONNM from staffdesignation where DESIGNATIONCD='"+payscale.getDesignationCd()+"'";
		    rs=db.getRecordSet(qry);
		    while(rs.next())
		    {
		    	name=rs.getString("DESIGNATIONNM");
		    }
		    String activityDesc="PayScale Master Of (Desination) "+name+" Saved.";
		    ActivityLog.getInstance().write(payscale.getUsercd(), activityDesc, "N", String.valueOf(id), con);
		    db.executeUpdate(sSQL);				
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:insertPayscale() Leaving Method");
	}
	
	public List searchPayscales(PayscaleMasterInfo payscaleinfo) {
		log.info("StaffConfigurationDAO:searchPayscales() Entering Method");
		List alist = new ArrayList();
		
		String sSQL = "";
		try {
			db.makeConnection();con = db.getConnection();
			
			sSQL = "select PAYSCLID,pm.DESIGNATIONCD,GET_DESCRIPTION('DESIGNATIONNM','staffdesignation Sd','Sd.DESIGNATIONCD='''||PM.DESIGNATIONCD||'''')desgnm,DECODE(NEXTSCALE,0,(MINSCALE||'-'||INCREMENTS||'-'||MAXSCALE),(MINSCALE||'-'||INCREMENTS||'-'||NEXTSCALE||'-'||NEXTINCREMENT||'-'||MAXSCALE))PAYSCALE,decode(pm.STATUS,'A','Active','I','In-Active')status from payscalemaster pm,staffdesignation sd where paysclid is not null and pm.DESIGNATIONCD=sd.DESIGNATIONCD ";
			
			/*if (payscaleinfo.getStaffCategoryCd() != null && !payscaleinfo.getStaffCategoryCd().equals("")) {
				sSQL += " and STAFFCTGRYCD = '"+payscaleinfo.getStaffCategoryCd()+"'";
			}

			if (payscaleinfo.getCadreCd() != null && !payscaleinfo.getCadreCd().equals("")) {
				sSQL += " and cadrecd = '"+payscaleinfo.getCadreCd()+"'";
			}*/
			if(payscaleinfo.getDesignationNm()!= null && !payscaleinfo.getDesignationNm().equals("")){
				sSQL += " and upper(DESIGNATIONNM) like upper('%"+payscaleinfo.getDesignationNm()+"%') ";
			}
			log.info("Query in searchPayscales is ============ "+sSQL);
			rs = db.getRecordSet(sSQL);
			
			while(rs.next()){
				payscaleinfo = new PayscaleMasterInfo();
				payscaleinfo.setPaysclid(rs.getString("PAYSCLID"));
				//payscaleinfo.setCadreCd(rs.getString("CADREcd"));
				//payscaleinfo.setCadreName(rs.getString("CADRENAME"));
				payscaleinfo.setDesignationCd(rs.getString("DESIGNATIONCD"));
				payscaleinfo.setDesignationNm(rs.getString("desgnm"));
				payscaleinfo.setPayScale(rs.getString("payscale"));
				payscaleinfo.setStatus(rs.getString("status"));
				alist.add(payscaleinfo);				
			}

		}catch (Exception e) {
			log.info("<<<<<<<<<< searchPayscales Exception  >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return alist;
	}
	/*public PayscaleMasterInfo showAddPayscale(){
		log.info("StaffConfigurationDAO:showAddPayscale() Entering Method");
		PayscaleMasterInfo payscales = new PayscaleMasterInfo();
		
		try{
			db.makeConnection();con = db.getConnection();
			List grouplist = getStaffGroups();
			List cadrelist = getListOfCadres();//getStaffCadres();//session.createQuery("from StaffCategoryInfo").list();
			log.info("");
			payscales.setCadreList((ArrayList)cadrelist);
			payscales.setGroupList((ArrayList)grouplist);			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:showAddPayscale() Leaving Method");
		return payscales;
	}*/
	/*public PayscaleMasterInfo loadCadres(int groupcd){
		log.info("StaffConfigurationDAO:loadCadres() Entering Method");
		PayscaleMasterInfo payscales = new PayscaleMasterInfo();		
		try{
			db.makeConnection();con = db.getConnection();
			List cadrelist = getStaffCadres(groupcd);//session.createQuery("from StaffCategoryInfo").list();
			
			payscales.setCadreList((ArrayList)cadrelist);
			//payscales.setGroupList((ArrayList)grouplist);
		
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:loadCadres() Leaving Method");
		return payscales;
	}*/
	/*public List getStaffGroups(){
		log.info("StaffConfigurationDAO:getStaffGroups() Entering Method");		
		List grouplist = new ArrayList();
		try{
			db.makeConnection();con = db.getConnection();			
			String query="select * from staffgroups where deleteflag='N'";
			rs=db.getRecordSet(query);
			
            while(rs.next()){
            	GroupInfo groupinfo=new GroupInfo();
            	groupinfo.setGroupCd(new Integer(rs.getInt("GROUPCD")));            	
            	groupinfo.setGroupName(rs.getString("GROUPNM"));            
            	groupinfo.setGroupDesc(rs.getString("GROUPDESCR"));           
            	groupinfo.setStatus(rs.getString("STATUS"));            
            	grouplist.add(groupinfo);                          
            }   			
		
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}		
		log.info("StaffConfigurationDAO:getStaffGroups() Leaving Method");
		return grouplist;
	}*/
	public List getStaffCadres4Desig(){
		List cadrelist = new ArrayList();
		CadreInfo cadreinfo=new CadreInfo();
		try{
			db.makeConnection();con = db.getConnection();
			String query="select * from staffcadre where status='A' and CADRECD not in (select CADRECD from staffdesignation) order by cadrecd";
			rs=db.getRecordSet(query);
			
            while(rs.next()){
            	cadreinfo = new CadreInfo();
            	cadreinfo.setCadreCd(rs.getString("CADRECD"));
            	cadreinfo.setCadreName(rs.getString("CADRENM"));
            	
            	cadrelist.add(cadreinfo);                          
            }
		}catch (Exception e) {
			log.info("<<<<<<<<<< getStaffCadres4Desig Exception  >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return cadrelist;
	}
	public List getStaffCadres(){
		log.info("StaffConfigurationDAO:getStaffCadres() Entering Method");				
		List cadrelist = new ArrayList();
		try{
			db.makeConnection();con = db.getConnection();
			String query="select * from staffcadre where status='A' order by cadrecd";
			rs=db.getRecordSet(query);
			
            while(rs.next()){
            	CadreInfo cadreinfo=new CadreInfo();
            	cadreinfo.setCadreCd(rs.getString("CADRECD"));
            	cadreinfo.setCadreName(rs.getString("CADRENM"));
            	cadreinfo.setCadreDesc(rs.getString("CADREDESCR"));
            	cadreinfo.setStatus(rs.getString("STATUS"));
            	//cadreinfo.setStaffCategoryCd(new Integer(rs.getInt("STAFFCTGRYCD")));
            	//cadreinfo.setGroupCd(new Integer(rs.getInt("GROUPCD")));
            	cadrelist.add(cadreinfo);                          
            } 			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:getStaffCadres() Leaving Method");		
		return cadrelist;
	}
	public PayscaleMasterInfo showEditPayscale(String paycd){
		log.info("StaffConfigurationDAO:showEditPayscale() Entering Method");			
		PayscaleMasterInfo payscaleinfo=null;
		try{
			db.makeConnection();con = db.getConnection();
			String query="select * from payscalemaster where paysclid ="+paycd;
			rs=db.getRecordSet(query);
			
            if(rs.next()){
            	payscaleinfo=new PayscaleMasterInfo();
            	payscaleinfo.setPaysclid(rs.getString("paysclID"));
            	//payscaleinfo.setStaffCategoryCd(rs.getString("STAFFCTGRYCD"));
            	//payscaleinfo.setCadreCd(rs.getString("cadreCd"));
            	//payscaleinfo.setFyearcd(rs.getString("FYEARCD"));
            	payscaleinfo.setDesignationCd(rs.getString("DESIGNATIONCD"));
            	payscaleinfo.setMinimumScale(rs.getString("MINSCALE"));
            	payscaleinfo.setIncrement(rs.getString("INCREMENTS"));
            	payscaleinfo.setNextScale(rs.getString("NEXTSCALE"));
            	payscaleinfo.setNextIncrement(rs.getString("NEXTINCREMENT"));
            	payscaleinfo.setMaximumScale(rs.getString("MAXSCALE"));
            	payscaleinfo.setStatus(rs.getString("Status"));            	                         
            } 				
		}catch (Exception e) {
			log.info("<<<<<<<<<<showEditPayscale Exception  >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:showEditPayscale() Leaving Method");	
		return payscaleinfo;
	}
	public void updatePayscale(PayscaleMasterInfo payscales){
		log.info("StaffConfigurationDAO:updatePayscale() Entering Method");	
		String name="";
		try{		
			db.makeConnection();con = db.getConnection();
			
			String sSQL = "update payscalemaster  set DESIGNATIONCD='"+payscales.getDesignationCd()						
			+"',MINSCALE="+payscales.getMinimumScale()
			+",INCREMENTS="+payscales.getIncrement()
			+",NEXTSCALE="+payscales.getNextScale()
			+",NEXTINCREMENT="+payscales.getNextIncrement()
			+",MAXSCALE="+payscales.getMaximumScale()
			+",status='"+payscales.getStatus()
			+"'where PAYSCLID="+payscales.getPaysclid();
			log.info("Update Query in updatePayscale ====== "+sSQL);
			String qry="select DESIGNATIONNM from staffdesignation where DESIGNATIONCD='"+payscales.getDesignationCd()+"'";
		    rs=db.getRecordSet(qry);
		    while(rs.next())
		    {
		    	name=rs.getString("DESIGNATIONNM");
		    }
			String activityDesc="PayScale Master Of (Designation) "+name+" Updated.";
			ActivityLog.getInstance().write(payscales.getUsercd(), activityDesc, "E", String.valueOf(payscales.getPaysclid()), con);
			db.executeUpdate(sSQL);					
		}catch (Exception e) {
			log.info("<<<<<<<<<<updatePayscale Exception  >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:updatePayscale() Leaving Method");	
	}
	/*public void deletePayscale(String psstr) {
		log.info("StaffConfigurationDAO:deletePayscale() Entering Method");	
		try {		db.makeConnection();con = db.getConnection();	
			StringTokenizer st = new StringTokenizer(psstr, ",");
			int i = 0;
			
			while (st.hasMoreTokens()) {
				String sSQL = "update payscalemaster  set DELETEFLAG ='Y' where id ="+Integer.parseInt(st.nextToken());
				i = db.executeUpdate(sSQL);				
			}
			log.info("deleted  record successfully in deletePayscale() " + i);
		
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:deletePayscale() Leaving Method");	
	}*/
	
	public List getListOfCadres() throws Exception{
		log.info("StaffConfigurationDAO:getListOfCadres() Entering Method");	
		List cadreList = new ArrayList();
		
		try {
			db.makeConnection();con = db.getConnection();
			String query="select * from staffcadre";
			rs=db.getRecordSet(query);
			
            while(rs.next()){
            	CadreInfo cadreinfo=new CadreInfo();
            	cadreinfo.setCadreCd(rs.getString("CADRECD"));
            	cadreinfo.setCadreName(rs.getString("CADRENM"));
            	cadreinfo.setCadreDesc(rs.getString("CADREDESCR"));
            	cadreinfo.setStatus(rs.getString("STATUS"));
            	cadreinfo.setStaffCategoryCd(new Integer(rs.getInt("STAFFCTGRYCD")));
            	cadreinfo.setGroupCd(new Integer(rs.getInt("GROUPCD")));
            	
            	cadreList.add(cadreinfo);                          
            } 			           
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:getListOfCadres() Leaving Method");	
		return cadreList;
	}
		
	public void  saveDesignation(DesignationInfo addDesignationInfo) throws Exception {
		log.info("Designation Name"+addDesignationInfo.getDesignatioNm());
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			String chkqry = "Select DESIGNATIONNM,DESGCODE from staffdesignation where upper(DESIGNATIONNM)='"+addDesignationInfo.getDesignatioNm().toUpperCase()+"' or DESGCODE= "+addDesignationInfo.getDesgCode();
			log.info("chkqry "+chkqry);
			rs = db.getRecordSet(chkqry);
			if(rs.next()){
				String rsnm = rs.getString("DESIGNATIONNM");
				String rscd = rs.getString("DESGCODE");
				if(rsnm.toUpperCase().equals(addDesignationInfo.getDesignatioNm().trim().toUpperCase())){
					throw new ApplicationException("Staff Designation Name '"+addDesignationInfo.getDesignatioNm()+"' Already Exists");
				}
				log.info("rscd "+rscd+" addDesignationInfo.getDesgCode() "+addDesignationInfo.getDesgCode()+" rscd == addDesignationInfo.getDesgCode() "+(rscd == addDesignationInfo.getDesgCode()));
				if(rscd.equals(addDesignationInfo.getDesgCode())){
					throw new ApplicationException("Staff Designation Code '"+addDesignationInfo.getDesgCode()+"' Already Exists");
				}
			}
			int desigCd = Integer.parseInt(AutoGeneration.getNextCode("staffdesignation","DESIGNATIONCD",5,con));
			//log.info("Insert into staffdesignation (DESIGNATIONCD,DESIGNATIONNM,DESIGNATIONDESC,DELETEFLAG,CADRECD) values ("+desigCd+",'"+addDesignationInfo.getDesignatioNm()+"','"+addDesignationInfo.getDesignationDesc()+"','N','"+addDesignationInfo.getCadreCd()+"')");
			db.executeUpdate("Insert into staffdesignation (DESIGNATIONCD,DESIGNATIONNM,DESIGNATIONDESC,STATUS,DESGCODE) values ("+desigCd+",'"+addDesignationInfo.getDesignatioNm()+"','"+addDesignationInfo.getDesignationDesc()+"','"+addDesignationInfo.getStatus()+"','"+addDesignationInfo.getDesgCode()+"')");
			String activityDesc="Designation Master Of "+addDesignationInfo.getDesignatioNm()+" Saved.";
			ActivityLog.getInstance().write(addDesignationInfo.getUsercd(), activityDesc, "N", String.valueOf(desigCd), con);
			db.commitTrans();
		}
		catch (ApplicationException ae) {
			throw new ApplicationException(ae.getMessage());
		}
		catch (Exception e) {
			log.info("<<<<<<<<<< Exception saveDesignation >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public DesignationInfo findByDesignationCode(DesignationInfo getDesignationInfo) throws Exception{
		DesignationInfo setDesignationInfo = new DesignationInfo();
		try {
			db.makeConnection();con = db.getConnection();			
			log.info("getDesignationInfo.getDesignationCd() is === "+getDesignationInfo.getDesignationCd());
			String findqry = "Select * from staffdesignation where designationCd = "+getDesignationInfo.getDesignationCd();
			log.info("findByDesignationCode qry "+findqry);
			rs = db.getRecordSet(findqry);
			if(rs.next()){
				setDesignationInfo.setDesignationCd(getDesignationInfo.getDesignationCd());//auto-increment
				setDesignationInfo.setDesgCode(rs.getString("DESGCODE"));
				setDesignationInfo.setDesignatioNm(rs.getString("DESIGNATIONNM"));
				setDesignationInfo.setDesignationDesc(rs.getString("DESIGNATIONDESC"));
				//setDesignationInfo.setCadreCd(rs.getString("CADRECD"));
				setDesignationInfo.setStatus(rs.getString("STATUS"));
			}
			log.info("in findByDesignationCode " + setDesignationInfo.getDesignatioNm());
		} catch (Exception e) {
			log.info("<<<<<<<<<< findByDesignationCode >>>>>>>>>>>>"	);
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (SQLException e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return setDesignationInfo;
	}
	public void updateDesignation(DesignationInfo editDesignationInfo) throws ApplicationException{
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			
			/*
			 * Check if the same designation name or cd exists
			 */
			//String checkqry = "select DESIGNATIONCD,DESIGNATIONNM,DESGCODE from staffdesignation where DESIGNATIONNM='"+editDesignationInfo.getDesignatioNm()+"' and DESGCODE='"+editDesignationInfo.getDesgCode()+"'";
			String checkqry = "select DESIGNATIONCD,DESIGNATIONNM,DESGCODE from staffdesignation where ( DESGCODE='"+editDesignationInfo.getDesgCode()+"' or DESIGNATIONNM='"+editDesignationInfo.getDesignatioNm()+"') and DESIGNATIONCD<>'"+editDesignationInfo.getDesignationCd()+"'";
			rs = db.getRecordSet(checkqry);
			String designationcd = "";
			String desgcd = "";
			String desgnm = "";
			
			if(rs.next()){
				designationcd = rs.getString("DESIGNATIONCD");
				desgcd =  rs.getString("DESGCODE");
				desgnm =  rs.getString("DESIGNATIONNM");
			}
			StringBuffer errmsg  = new StringBuffer();
			if(!designationcd.equals("")){
				if(desgcd.trim().equals(editDesignationInfo.getDesgCode())){
					errmsg.append(" Designation Code "+desgcd+" exists");
				}
				if(desgnm.trim().equals(editDesignationInfo.getDesignatioNm())){
					errmsg.append(" Designation Name "+desgnm+" exists");
				}
				throw new ApplicationException(errmsg.toString());
			}
			String updtqry = "Update staffdesignation set  DESIGNATIONNM='"+editDesignationInfo.getDesignatioNm()+"',DESIGNATIONDESC='"+editDesignationInfo.getDesignationDesc()+"',STATUS='"+editDesignationInfo.getStatus()+"',DESGCODE='"+editDesignationInfo.getDesgCode()+"' where DESIGNATIONCD='"+editDesignationInfo.getDesignationCd()+"'";
			log.info("updtqry "+updtqry);
			db.executeUpdate(updtqry);
			log.info("Designation Name(updateDesignation)"+editDesignationInfo.getDesignatioNm());
			String activityDesc="Designation Master Of "+editDesignationInfo.getDesignatioNm()+" Updated.";
			ActivityLog.getInstance().write(editDesignationInfo.getUsercd(), activityDesc, "E", String.valueOf(editDesignationInfo.getDesignationCd()), con);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< updateDesignation Exception >>>>>>>>>>>>");
			log.printStackTrace(e);
			throw new ApplicationException(e.getMessage());
		} finally { 
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List searchDesignation(SearchDesignationInfo searchDesignation) {
		ArrayList designationList = new ArrayList();
		DesignationInfo getDesignationInfo=null;
		
		try {
			db.makeConnection();con = db.getConnection();
			
			//String searhBTypequery ="select sd.designationcd as designationcode,sd.designationnm as designationname,sd.designationdesc as designationdesc,decode(sd.status,'I','InActive','A','Active') status,sd.cadrecd as cadrecode, sc.cadrenm as cadrename  from staffdesignation sd,staffcadre sc where sd.cadrecd=sc.cadrecd and  sd.designationcd is not null";
			String searhBTypequery ="select sd.DESIGNATIONCD,sd.DESGCODE,sd.DESIGNATIONNM ,sd.DESIGNATIONDESC ,decode(sd.status,'I','InActive','A','Active') status  from staffdesignation sd where  sd.designationcd is not null";
			
			log.info("<<<<<<<<<<<<<<<<< searchDesignation  query>>>>>>>>>>>"+searhBTypequery);
			
			if (!searchDesignation.getSearchdesignationnm().equals("")) {
				searhBTypequery = searhBTypequery+" and upper(sd.DESIGNATIONNM) like upper('"+ searchDesignation.getSearchdesignationnm().trim()+"%')";
			}
			if (!searchDesignation.getSearchdesgcode().equals("")) {
				searhBTypequery = searhBTypequery+" and upper(sd.DESGCODE) like upper('"+ searchDesignation.getSearchdesgcode().trim()+"%')";
			}
			/*if (!searchDesignation.getSearchcadrecd().equals("")) {
				searhBTypequery = searhBTypequery+" and upper(sc.cadrenm) like upper('%"+ searchDesignation.getSearchcadrecd().trim()+"%')";
			}*/			
		
			if(!searchDesignation.getStatus().equals("")){
				searhBTypequery = searhBTypequery+" and sd.STATUS = '"+searchDesignation.getStatus()+"'"; 
			}			
			
			log.info("<<<<<<<<<<<<<<<<< searchDesignation query>>>>>>>>>>>"+searhBTypequery);
			rs = db.getRecordSet(searhBTypequery);
			
			while (rs.next()) {
				getDesignationInfo=new DesignationInfo();
				getDesignationInfo.setDesignationCd(new Integer(rs.getInt("DESIGNATIONCD")));
				getDesignationInfo.setDesgCode(rs.getString("DESGCODE"));
				getDesignationInfo.setDesignatioNm(rs.getString("designationnm"));
				getDesignationInfo.setDesignationDesc(rs.getString("designationdesc"));
				getDesignationInfo.setStatus(rs.getString("status"));
				/*getDesignationInfo.setCadreCd(rs.getString("CADRECODE"));
				getDesignationInfo.setCadreNm(rs.getString("CADRENAME"));*/
				designationList.add(getDesignationInfo);
			}		
		} catch (Exception e) {
			log.error("error in searching designation(searchDesignation) : " + e);
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing  RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS>>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return designationList;
	}
	public void deleteDesignation(String designationcode) throws Exception{
		try {
			db.makeConnection();con = db.getConnection();
			StringTokenizer st = new StringTokenizer(designationcode, ",");
			
			int i = 0;
			String deleteQuery = "Update staffdesignation  set deleteFlag = 'Y' where DESIGNATIONCD in ("+designationcode.substring(0,designationcode.length()-1)+")";
			log.info("-----deleteQuery-----"+deleteQuery);
			i = db.executeUpdate(deleteQuery);
			
			log.info("deleted   " + i + "  records successfully in deleteDesignation()");
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception deleteDesignation()>>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public EmployeeDeductionsInfo editEmpDeductions(int empNo) {
		EmployeeDeductionsInfo empdeductionsinfo = new EmployeeDeductionsInfo();
		try {
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select * from EmployeeDeductions where empNo =" + empNo);
			while(rs.next()){
				empdeductionsinfo.setDuducId(new Integer(rs.getInt("DuducId")));
				empdeductionsinfo.setEmpNo(new Integer(rs.getInt("EmpNo")));
				empdeductionsinfo.setAdvance(rs.getString("Advance"));
				empdeductionsinfo.setCompAdv(new Float(rs.getFloat("CompAdv")));
				empdeductionsinfo.setConvAdv(new Float(rs.getFloat("ConvAdv")));
				empdeductionsinfo.setCpf(new Float(rs.getFloat("Cpf")));
				empdeductionsinfo.setCpfAdv(new Float(rs.getFloat("CpfAdv")));
				empdeductionsinfo.setCpfArriers(new Float(rs.getFloat("CpfArriers")));
				empdeductionsinfo.setFestAdv(new Float(rs.getFloat("FestAdv")));
				empdeductionsinfo.setGslis(new Float(rs.getFloat("Gslis")));
				empdeductionsinfo.setIntonAdvance(new Float(rs.getFloat("IntonAdvance")));
				empdeductionsinfo.setLicenceFee(new Float(rs.getFloat("LicenceFee")));
				empdeductionsinfo.setOjta(new Float(rs.getFloat("Ojta")));
				empdeductionsinfo.setHbAdv(new Float(rs.getFloat("HbAdv")));
				empdeductionsinfo.setOtherDeduc(new Float(rs.getFloat("OtherDeduc")));
				empdeductionsinfo.setProfTax(new Float(rs.getFloat("PROFTAX")));
				empdeductionsinfo.setVpf(new Float(rs.getFloat("Vpf")));
				empdeductionsinfo.setDeleteFlag(rs.getString("DeleteFlag"));
			}
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"	+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}

		}
		return empdeductionsinfo;
	}
	public void updateEmpDeductions(EmployeeDeductionsInfo empdeductionsinfo,EmpRecoveriesInfo emprecoveriesinfo) {
		try {
			db.makeConnection();con = db.getConnection();
			db.setAutoCommit(false);
			log.info("inside update");
			db.executeUpdate("Update employeedeductions set EMPNO="+empdeductionsinfo.getEmpNo()+"',ADVANCE="+empdeductionsinfo.getAdvance()+"',COMPADV="+empdeductionsinfo.getCompAdv()+"',CONVADV="+empdeductionsinfo.getConvAdv()+"',CPF="+empdeductionsinfo.getCpf()+"',CPFADV="+empdeductionsinfo.getCpfAdv()+"',CPFARRIERS="+empdeductionsinfo.getCpfArriers()+"',FESTADV="+empdeductionsinfo.getFestAdv()+"',GSLIS="+empdeductionsinfo.getGslis()+"',INTONADVANCE="+empdeductionsinfo.getIntonAdvance()+"',LICENCEFEE="+empdeductionsinfo.getLicenceFee()+"',OJTA="+empdeductionsinfo.getOjta()+"',HBADV="+empdeductionsinfo.getHbAdv()+"',OTHERDEDUC="+empdeductionsinfo.getOtherDeduc()+"',PROFTAX="+empdeductionsinfo.getProfTax()+"',VPF="+empdeductionsinfo.getVpf()+"',DELETEFLAG="+empdeductionsinfo.getDeleteFlag()+"' where DEDUCID="+empdeductionsinfo.getDuducId()+"'");
			db.executeUpdate("Update employeerecoveries set EMPNO ="+emprecoveriesinfo.getEmpNo()+"',UNIONSUBS ="+emprecoveriesinfo.getCoopsociety()+"',COOPSOCIETY ="+emprecoveriesinfo.getCourtRecovery()+"',LIC  ="+emprecoveriesinfo.getHdfc()+"',HDFC ="+emprecoveriesinfo.getLic()+"',COURTRECOVERY="+emprecoveriesinfo.getMisc1()+"',MISC1 ="+emprecoveriesinfo.getMisc2()+"',MISC2 ="+emprecoveriesinfo.getMisc3()+"',MISC3="+emprecoveriesinfo.getDeleteFlag()+"',DELETEFLAG ="+emprecoveriesinfo.getUnionSubs()+"' where RECOVERID="+emprecoveriesinfo.getRecoverId()+"'");
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"	+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public int getEarningId(int lpcno) {
		int earningId=0;
		try {
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select EARNINGID from employeeearnings where empNo ="+ lpcno);
			if(rs.next())
				earningId = rs.getInt("EARNINGID");
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}

		}
		return earningId;
	}
	public int getDeductionId(int lpcno) {
		int deductinId=0;
		try {
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select DEDUCID from employeedeductions where empNo ="+ lpcno);
			if(rs.next())
				deductinId = rs.getInt("DEDUCID");
			log.info("deductinId in edit load"+deductinId);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}

		}
		return deductinId;
	}
	public int getPersonalId(String lpcno) {
		EmployeePersonalInfo empInfo = null;
		int personalId=0;
		try {
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select PERSONALID from employeepersonalinfo where empNo ='"+ lpcno+"'");
			
			if(rs.next())
				personalId = rs.getInt("PERSONALID"); 
			log.info("personalId in edit load"+personalId);
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return personalId;
	}
	
	public List editDependentsinfo(String empNo) {
		EmpDependentsInfo depinfo = null;
		List dependents = new ArrayList();
		try {
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select DEPENDENTNM,AGE,DEPENDENTID,RELATIONSHIP,DELETEFLAG,EMPNO from empdependents where empNo ='"+ empNo+"' order by DEPENDENTID");
			while(rs.next()){
				depinfo = new EmpDependentsInfo();
				depinfo.setDependentName(rs.getString("DEPENDENTNM"));
				depinfo.setAge(new Integer(rs.getInt("AGE")));
				depinfo.setDependentId(new Integer(rs.getInt("DEPENDENTID")));
				depinfo.setRelation(rs.getString("RELATIONSHIP"));
				depinfo.setDeleteFlag(rs.getString("DELETEFLAG"));
				depinfo.setEmpNo(rs.getString("EMPNO"));
				dependents.add(depinfo);
			}
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return dependents;
	}
	
	public void updateEmpDependentsinfo(EmpDependentsInfo depinfo) {
		try {	
			db.makeConnection();con = db.getConnection();
			int dependentId=0;
			db.setAutoCommit(false);
			if(!depinfo.getDependentId().equals(new Integer(0))){
				log.info("Update empdependents set DEPENDENTNM='"+depinfo.getDependentName()+"',AGE='"+depinfo.getAge()+"',RELATIONSHIP='"+depinfo.getRelation()+"' ,DELETEFLAG='"+depinfo.getDeleteFlag()+"',EMPNO='"+depinfo.getEmpNo()+"' where DEPENDENTID='"+depinfo.getDependentId()+"'");
				db.executeUpdate("Update empdependents set DEPENDENTNM='"+depinfo.getDependentName()+"',NOOFDEPENDENTS="+depinfo.getNoOfDependents()+",AGE='"+depinfo.getAge()+"',RELATIONSHIP='"+depinfo.getRelation()+"' ,DELETEFLAG='"+depinfo.getDeleteFlag()+"',EMPNO='"+depinfo.getEmpNo()+"' where DEPENDENTID='"+depinfo.getDependentId()+"'");
			}else{
				dependentId =Integer.parseInt(AutoGeneration.getNextCode("empdependents","dependentid",11,con));
				
				String sSQL ="insert into empdependents (empno,dependentnm,relationship,age,deleteflag," +
				"noofdependents,dependentid)" +
				"values ('"+depinfo.getEmpNo()+"','"+depinfo.getDependentName()
				+"','"+depinfo.getRelation()+"',"+depinfo.getAge()
				+",'"+depinfo.getDeleteFlag()+"',"+depinfo.getNoOfDependents()					
				+","+dependentId+")";
				log.info("insert Query is "+sSQL);
		        db.executeUpdate(sSQL);		
			}
			
			log.info("inside updateEmpPersonalInfo");
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public EmployeeInfo getEmpInfo(String lpcno) {
		EmployeeInfo empInfo = new EmployeeInfo();
		int earningId=0;
		try {
			db.makeConnection();con = db.getConnection();
			String sql = "Select EMPLOYEENAME empname,nvl(STAFFCTGRYCD,0)STAFFCTGRYCD,CADRECOD,nvl(DESIGNATIONCD,0)DESIGNATIONCD,HRAOPTION,nvl(petrol,0)petrol,(select earndeducd from configmapping where configname ='VCON' )vconcd " +
					", (select amount from GSLI_CAT_AMT where category = (select category from GSLISCADRE where GSLISCADRECD=ei.GSLISCADRECD)and status='A')gslisamt" +
					",(SELECT earndeducd FROM configmapping WHERE configname = 'GSLIS') gslisearncd" +
					" from  EmployeeInfo ei where empNo ='"+lpcno+"'";
			log.info("getEmpInfo:sql "+sql);
			rs = db.getRecordSet(sql);
			log.info(sql);
			if(rs.next()){
				empInfo.setEmpNo(lpcno);
				empInfo.setEmpName(rs.getString("empName"));
				empInfo.setStaffCategoryCd(new Integer(rs.getInt("STAFFCTGRYCD")));
				empInfo.setCadrecod(rs.getString("CADRECOD"));
				empInfo.setHraOption(rs.getString("HRAOPTION"));
				//empInfo.setEmpDescipline(sh.getDescription("staffdiscipline","DISCIPLINENM","DISCIPLINECD",rs.getString("DISCIPLINECD"),con));
				empInfo.setEmpDesignation(sh.getDescription("staffdesignation","DESIGNATIONNM","DESIGNATIONCD",rs.getString("DESIGNATIONCD"),con));
				empInfo.setPetrol(rs.getString("petrol"));
				empInfo.setVconcd(rs.getString("vconcd"));
				empInfo.setGsliscd(rs.getString("gslisearncd"));
				empInfo.setGslisamt(rs.getString("gslisamt"));
				log.info("empInfo gsliscadrecd "+empInfo.getGsliscd()+" amt "+empInfo.getGslisamt());
				
			}
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}

		}
		return empInfo;
	}
	
	public List getEarningInfo(String empno,int categoryCd) {
		EarningMasterInfo earningInfo=null;
		List earningList = new ArrayList();
		
		try {
			String hra="",qtype="";
			db.makeConnection();con = db.getConnection();
			//String getEarningInfoqry = "Select EARNDEDUCD,EARNDEDUNM,emp.STAFFCTGRYCD STAFFCTGRYCD,TYPE,ear.isbasicsalary isbasicsalary,CALCULATIONTYPE,PERCENTAGEVALUE,MINAMOUNT,FIXEDAMOUNT,MAXAMOUNT,ear.STATUS status,DEPENDSON from earndedumaster ear,employeeinfo emp where ear.STAFFCTGRYCD = emp.STAFFCTGRYCD  and emp.STAFFCTGRYCD="+categoryCd+" and ear.TYPE in('E','D','PK','OR') and emp.empno='"+empno+"' and ear.status='A' order by PRINTORDER";
			//String getEarningInfoqry = "Select EARNDEDUCD,EARNDEDUNM,emp.STAFFCTGRYCD STAFFCTGRYCD,TYPE,ear.isbasicsalary isbasicsalary,CALCULATIONTYPE,PERCENTAGEVALUE,MINAMOUNT,FIXEDAMOUNT,MAXAMOUNT,ear.STATUS status,DEPENDSON from earndedumaster ear,employeeinfo emp where ear.TYPE in('E','D','PK','OR') and emp.empno='"+empno+"' and ear.status='A' order by PRINTORDER";
			
			String getEarningInfoqry = "SELECT   ear.earndeducd, earndedunm,emp.HRAOPTION HRAOPTION,emp.QUATERTYPE QUATERTYPE, emp.staffctgrycd staffctgrycd, ear.TYPE,   ear.isbasicsalary isbasicsalary, calculationtype, percentagevalue,  minamount, fixedamount, maxamount, ear.status status, slm.dependson  FROM earndedumaster ear, employeeinfo emp,SALHEAD_LEDG_MAPPING slm WHERE ear.TYPE IN ('E', 'D', 'PK', 'OR') and  slm.EARNDEDUCD not in(168,86,88) and ear.earndeducd = slm.EARNDEDUCD and slm.CADRECOD = emp.cadrecod     AND emp.empno = '"+empno+"' AND ear.status = 'A' ORDER BY printorder ";
			log.info("getEarningInfo: "+getEarningInfoqry);
			Integer config = new Integer(0);
			String query3="select EARNDEDUCD from configmapping where CONFIGNAME ='HRA' and type='E'";
			rs =db.getRecordSet(query3);
			
			if(rs.next()){
			
				config = new Integer(rs.getString("EARNDEDUCD"));
				
			}
			rs.close();
			//rs =db.getRecordSet("Select EARNDEDUCD,EARNDEDUNM,emp.STAFFCTGRYCD STAFFCTGRYCD,TYPE,ear.isbasicsalary isbasicsalary,CALCULATIONTYPE,PERCENTAGEVALUE,MINAMOUNT,FIXEDAMOUNT,MAXAMOUNT,ear.STATUS status,DEPENDSON from earndedumaster ear,employeeinfo emp where ear.STAFFCTGRYCD = emp.STAFFCTGRYCD  and emp.STAFFCTGRYCD="+categoryCd+" and emp.empno='"+empno+"' and ear.status='A' order by PRINTORDER");
			
			rs =db.getRecordSet(getEarningInfoqry);
			
			while(rs.next()){
				earningInfo = new EarningMasterInfo();
				earningInfo.setEarningCd(new Integer(rs.getString("EARNDEDUCD")));
				earningInfo.setEarningName(rs.getString("EARNDEDUNM"));
				earningInfo.setStaffCategoryCd(new Integer(rs.getString("STAFFCTGRYCD")));
				earningInfo.setType(rs.getString("TYPE"));
				earningInfo.setHraOption(rs.getString("HRAOPTION"));
				earningInfo.setQuarterType(rs.getString("QUATERTYPE"));
				earningInfo.setCalcType(rs.getString("CALCULATIONTYPE"));
				earningInfo.setPercentVal(new Double(rs.getDouble("PERCENTAGEVALUE")));
				earningInfo.setFixedAmt(new Double(rs.getDouble("FIXEDAMOUNT")));
				earningInfo.setMinAmt(new Double(rs.getDouble("MINAMOUNT")));
				earningInfo.setMaxAmt(new Double(rs.getDouble("MAXAMOUNT")));
				//earningInfo.setDeletedFlag(rs.getString("STATUS"));
				earningInfo.setIsBasicSal(rs.getString("isbasicsalary"));
				earningInfo.setDependsstr(rs.getString("DEPENDSON"));
				earningInfo.setConfigEarningcd(config);
				earningList.add(earningInfo);
			}
			log.info("earningInfo is "+earningInfo);
			rs.close();
			//log.info("query3  is"+query3 );
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return earningList;
	}
	public void addEmpEarningsInfo(EmployeeEarningInfo empearningsinfo) {
		try {
			db.makeConnection();con = db.getConnection();
			int sno=0;
			UtilityBean util = null;
			log.info("empno inside dao is**** " +empearningsinfo.getEmpNo());
			
			ArrayList list = empearningsinfo.getEarnings();
			for(int i=0;i<list.size();i++){
				 util = (UtilityBean)list.get(i);
				 sno =Integer.parseInt(AutoGeneration.getNextCode("empernsdeducs","SLNO",10,con));
				 db.executeUpdate("Insert into empernsdeducs (SLNO,EMPNO,STAFFCTGRYCD,EARNDEDUCD,AMOUNT,USERCD,STATUS) values ("+sno+",'"+empearningsinfo.getEmpNo()+"',"+empearningsinfo.getStaffCategoryCd()+","+util.getKey()+","+util.getValue()+",'"+empearningsinfo.getUserCd()+"','A')");
				 log.info("Insert into empernsdeducs (SLNO,EMPNO,STAFFCTGRYCD,EARNDEDUCD,AMOUNT,USERCD,STATUS) values ("+sno+",'"+empearningsinfo.getEmpNo()+"',"+empearningsinfo.getStaffCategoryCd()+","+util.getKey()+","+util.getValue()+",'"+empearningsinfo.getUserCd()+"','A')");
			}
				
			//db.executeUpdate("Insert into employeeperks (PERKID,EMPNO,HRDREIMB,KITALLOW,TRANSPORTSUBS ,CONVEYANCEREIMB,VECHICLEMAINTANCE,ENTALLOW,SELFLEASE,TELEREIMB,MISC ,DELETEFLAG ) values ('"+perksinfo.getEmpNo()+"','"+perksinfo.getHrdReimb()+"','"+perksinfo.getKitAllow()+"','"+perksinfo.getTransportSubs()+"','"+perksinfo.getConveyanceReimb()+"','"+perksinfo.getVechicleMaintance()+"','"+perksinfo.getEntAllow()+"','"+perksinfo.getSelfLease()+"','"+perksinfo.getTeleReimb()+"','"+perksinfo.getMisc()+"','"+perksinfo.getDeleteFlag()+"')");
			
			log.info("after Earnings save");
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public EmployeePerksInfo editPerks(int empNo) {
		EmployeePerksInfo perksinfo = new EmployeePerksInfo();
		try {
			db.makeConnection();con = db.getConnection();
			String sSQL = " Select * from EmployeePerks where empNo =" + empNo;
			rs = db.getRecordSet(sSQL);
			while(rs.next()){
				perksinfo.setPerkId(rs.getInt("perkid"));
				perksinfo.setEmpNo(rs.getInt("empno"));
				perksinfo.setHrdReimb(new Float(rs.getFloat("hrdreimb")));
				perksinfo.setKitAllow(new Float(rs.getFloat("kitallow")));
				perksinfo.setTransportSubs(new Float(rs.getFloat("transportsub")));
				perksinfo.setConveyanceReimb(new Float(rs.getFloat("ConveyanceReimb")));
				perksinfo.setVechicleMaintance(new Float(rs.getFloat("VechicleMaintance")));
				perksinfo.setEntAllow(new Float(rs.getFloat("EntAllow")));
				perksinfo.setSelfLease(new Float(rs.getFloat("selflease")));
				perksinfo.setTeleReimb(new Float(rs.getFloat("relereimb")));
				perksinfo.setMisc(new Float(rs.getFloat("misc")));
				perksinfo.setEffectiveDt(new Float(rs.getFloat("EffectiveDt")));
				perksinfo.setEndingDt(new Float(rs.getFloat("EndingDt")));
				perksinfo.setDeleteFlag(rs.getString("deleteflag"));
			}
			log.info("perksInfo " +perksinfo);
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"	+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
				}
			}

		}
		return perksinfo;
	}
	public void updateEarningsInfo(EmployeeEarningInfo empearningsinfo) {
		try {	
			db.makeConnection();con = db.getConnection();														
			log.info("inside update");
			UtilityBean util = null;
	         String sSQL ="";
			ArrayList list = empearningsinfo.getEarnings();
			
			for(int i=0;i<list.size();i++){
	         util = (UtilityBean)list.get(i);
			 sSQL = "Update empernsdeducs set AMOUNT="+util.getValue()+" where EMPNO='"+empearningsinfo.getEmpNo()+"' and EARNDEDUCD="+util.getKey();
			int count = db.executeUpdate(sSQL);
        }
	
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
	}
	public void addEmpDeductionInfo(EmployeeDeductionsInfo empdeductionsinfo,EmpRecoveriesInfo emprecoveriesinfo) {
		try {
			db.makeConnection();con = db.getConnection();
			log.info("empno inside dao is**** " +empdeductionsinfo.getEmpNo());
			db.setAutoCommit(false);
			String sSQL = "Insert into employeedeductions() values() ";
			db.executeUpdate(sSQL);
			log.info("after deductions and  Recoveries save");
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"	+ e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public EmpRecoveriesInfo editEmpRecoveries(int empNo) {
		EmpRecoveriesInfo empRecoveriesInfo=null;
		try {
			db.makeConnection();con = db.getConnection();
			String sSQL = "Select * from employeerecoveries where empNo =" + empNo;
			rs = db.getRecordSet(sSQL);
			while(rs.next()){
				empRecoveriesInfo = new EmpRecoveriesInfo();
				empRecoveriesInfo.setRecoverId(new Integer(rs.getInt("RECOVERID")));
				empRecoveriesInfo.setEmpNo(new Integer(rs.getInt("empno")));
				empRecoveriesInfo.setUnionSubs(new Float(rs.getFloat("unionsubs")));
				empRecoveriesInfo.setCoopsociety(new Float(rs.getFloat("coopsociety")));
				empRecoveriesInfo.setLic(new Float(rs.getFloat("lic")));
				empRecoveriesInfo.setHdfc(new Float(rs.getFloat("hdfc")));
				empRecoveriesInfo.setCourtRecovery(new Float(rs.getFloat("courtrecovery")));
				empRecoveriesInfo.setMisc1(new Float(rs.getFloat("MISC1")));
				empRecoveriesInfo.setMisc2(new Float(rs.getFloat("MISC2")));
				empRecoveriesInfo.setMisc3(new Float(rs.getFloat("MISC3")));
				empRecoveriesInfo.setDeleteFlag(rs.getString("deleteflag"));
			}
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return empRecoveriesInfo;
	}
	public List getDeductionsInfo(int categoryCd) {
		List deductionList = new ArrayList();
		DeductionMasterInfo dminfo = null;
		try {
			db.makeConnection();con = db.getConnection();
			String sSQL = "Select * from DeductionMaster where  staffCategoryCd="+categoryCd;
			rs = db.getRecordSet(sSQL);
			while(rs.next()){
				dminfo = new DeductionMasterInfo();
				dminfo.setDeductionCd(new Integer(rs.getInt("DEDUCTIONCD")));
				dminfo.setDeductionName(rs.getString("DEDUCTIONNM"));
				dminfo.setDeductionDesc(rs.getString("DEDUCTIONDESC"));
				dminfo.setDisciplineCd(new Integer(rs.getInt("DISCIPLINECD")));
				dminfo.setPrintToPayslip(rs.getString("PRINTTOSLIP"));
				dminfo.setPrintOrder(new Integer(rs.getInt("PRINTORDER")));
				dminfo.setAccTypeCd(new Integer(rs.getInt("ACCTYPECD")));
				dminfo.setLedgerCd(rs.getString("LEDGERCD"));
				dminfo.setIsReimbursed(rs.getString("ISREIMBURSED"));
				dminfo.setStaffCategoryCd(new Integer(rs.getInt("STAFFCTGRYCD")));
				dminfo.setCalcType(rs.getString("CALCULATIONTYPE"));
				dminfo.setPercentVal(new Integer(rs.getInt("PERCENTAGEVALUE")));
				dminfo.setFixedAmt(new Integer(rs.getInt("FIXEDAMOUNT")));
				dminfo.setMinAmt(new Integer(rs.getInt("MINAMOUNT")));
				dminfo.setMaxAmt(new Integer(rs.getInt("MAXAMOUNT")));
				dminfo.setDeletedFlag(rs.getString("DELETEFLAG"));
				dminfo.setFyearcd(rs.getString("FYEARCD"));
				deductionList.add(dminfo);
			}
			
			//deductionInfo = session.createQuery(").list();
			log.info("deductions info is "+deductionList);
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}

		}
		return deductionList;
	}
	/**
	 * @return
	 */
	public List getEmpNo() {
		
		List empDt=new ArrayList();
		EmployeeInfo empInfo=null;
		try {
			
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet("Select empNo,EMPLOYEENAME empname from  EmployeeInfo ");
			while(rs.next()){
				 empInfo = new EmployeeInfo();
				empInfo.setEmpNo(rs.getString("empNo"));
				empInfo.setEmpName(rs.getString("empName"));
				empDt.add(empInfo);
				
			}
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}

		}
		return empDt;
	}
	/**
	 * @return
	 */
	public boolean checkData(String empnum) {
		 boolean check = false;
		 try {
			 db.makeConnection();con = db.getConnection();
		     int count = db.getRecordCount("select count(*) from empernsdeducs where empno='"+empnum+"' ");
		     log.info("select count(*) from empernsdeducs where empno='"+empnum+"' ");
			      if(count!=0){
				        check = true;
			        }else
				        check = false;
		 } catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return check;
	}
	public PayscaleMasterInfo getBasicPayRange(String empno) throws ApplicationException {
		PayscaleMasterInfo payscale = null;
		String cadrenm="";
		 try {
			 db.makeConnection();con = db.getConnection();
		     rs = db.getRecordSet("select pay.minscale minscale, pay.maxscale maxscale  from payscalemaster pay where pay.cadrecd = (select distinct (info.cadrecd)from employeeinfo info where info.empno = '"+empno+"')");
		     log.info("select pay.minscale, pay.maxscale from payscalemaster pay where pay.cadrecd = (select distinct (info.cadrecd)from employeeinfo info where info.empno = '"+empno+"')");
		     rs1= db.getRecordSet("select cadr.cadrenm cadrenm from employeeinfo info, staffcadre cadr where info.cadrecd=cadr.cadrecd and info.empno = '"+empno+"'");
		     log.info("select cadr.cadrenm cadrenm from employeeinfo info, staffcadre cadr where info.cadrecd=cadr.cadrecd and info.empno = '"+empno+"'");
		     if(rs1.next())
		    	 cadrenm=rs1.getString("cadrenm");
		     if(rs.next()){
		    	 payscale = new PayscaleMasterInfo();
		    	 payscale.setMinimumScale(rs.getString("minscale"));
		    	 payscale.setMaximumScale(rs.getString("maxscale"));
		     }else
		    	 throw new Exception("Please Define Pay Scale for the Cadre("+cadrenm+") in Pay Scale Master <br> to get the Basic Pay Range."); 
		     
			      
		 } catch (Exception e) {
			
			 throw new ApplicationException(e.getMessage());
		} finally {
			if (rs != null && rs1 !=null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return payscale;
	}
	public List searchEmpIncomeTax(EmployeeIncomeTaxInfo info) {
		
		List taxList=new ArrayList();
		EmployeeIncomeTaxInfo taxInfo=null;
		try {
			
			db.makeConnection();con = db.getConnection();
			 String qry="";
			// qry = "SELECT empincometaxcd,tax.payrollmonthid,staff.staffctgrynm staffctgrynm,pay.payrollmonthnm payrollmonthnm,(select PAYTRANSID from PAYROLLPROCESS pp where pp.PAYROLLMONTHID = tax.PAYROLLMONTHID and pp.STAFFCTGRYCD = staff.STAFFCTGRYCD) paidid, pay.payrollyear payrollyear  FROM empincometax tax, staffcategory staff, monthlypayroll pay WHERE tax.STAFFCTGRYCD =staff.staffctgrycd  and pay.payrollmonthid = tax.payrollmonthid  order by stationcd desc,to_number(emplnumber)";
			 qry = "SELECT   empincometaxcd, tax.payrollmonthid,   pay.payrollmonthnm payrollmonthnm, (SELECT distinct paytransid FROM payrollprocess pp WHERE pp.payrollmonthid = tax.payrollmonthid  and rownum=1) paidid, pay.payrollyear payrollyear FROM empincometax tax,  monthlypayroll pay   WHERE pay.payrollmonthid = tax.payrollmonthid";
			if(!info.getMonthId().equals("")){
				qry += " and tax.payrollmonthid = "+info.getMonthId();
			}
			 log.info("Select Query in searchEmpIncomeTax is "+qry);
			rs = db.getRecordSet(qry);
			
			while(rs.next()){
				taxInfo = new EmployeeIncomeTaxInfo();
				//taxInfo.setStaffCategoryName(rs.getString("staffctgrynm"));
				taxInfo.setEmpincometaxcd(rs.getString("EMPINCOMETAXCD"));
				taxInfo.setMonthId(rs.getString("payrollmonthnm")+"-"+rs.getString("payrollyear"));
				if(rs.getString("paidid")!=null)
					taxInfo.setType("Y");
				taxList.add(taxInfo);
			}
			
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}

		}
		return taxList;
	}
	public void saveEmpIncometax(EmployeeIncomeTaxInfo info) throws ApplicationException {
		try {
			db.makeConnection();con = db.getConnection();
		    db.setAutoCommit(false);
		   /* log.info(" select EMPFIRSTNAME||''||EMPLASTNAME empname,pay.payrollmonthnm||'-'||pay.payrollyear payroll from empincometax tax, employeeinfo emp, monthlypayroll pay where tax.empno = emp.empno and pay.payrollmonthid = tax.payrollmonthid and tax.EMPNO='"+info.getEmpno()+"' and tax.PAYROLLMONTHID='"+info.getMonthId()+"' ");
		    rs = db.getRecordSet(" select EMPFIRSTNAME||''||EMPLASTNAME empname,pay.payrollmonthnm||'-'||pay.payrollyear payroll from empincometax tax, employeeinfo emp, monthlypayroll pay where tax.empno = emp.empno and pay.payrollmonthid = tax.payrollmonthid and tax.EMPNO='"+info.getEmpno()+"' and tax.PAYROLLMONTHID='"+info.getMonthId()+"' ");
		    if(rs.next()){
		    	throw new ApplicationException(" Amount exist to "+rs.getString("EMPNAME")+" for the month of "+rs.getString("PAYROLL"));
		    }
		    */
			int taxCd = Integer.parseInt(AutoGeneration.getNextCode("empincometax","EMPINCOMETAXCD",5,con));
			log.info("Insert into empincometax (EMPINCOMETAXCD,STAFFCTGRYCD,PAYROLLMONTHID,USERCD) values ("+taxCd+",'"+info.getStaffCategoryCd()+"','"+info.getMonthId()+"','"+info.getUserCd()+"')");
			db.executeUpdate("Insert into empincometax (EMPINCOMETAXCD,STAFFCTGRYCD,PAYROLLMONTHID,USERCD) values ("+taxCd+",'"+info.getStaffCategoryCd()+"','"+info.getMonthId()+"','"+info.getUserCd()+"')");
			
			List empList = info.getEmployeeList();
			int size = empList.size();
			int incometaxdtcd = 0;
			StringBuffer insrtQry = new StringBuffer();
			for(int i=0;i<size;i++){
				insrtQry.delete(0,insrtQry.length());
				EmployeeIncomeTaxInfo info1 = (EmployeeIncomeTaxInfo)empList.get(i);
				incometaxdtcd = Integer.parseInt(AutoGeneration.getNextCode("empincometaxdet","EMPINCOMETAXDETCD",5,con));
				insrtQry.append("Insert into empincometaxdet(EMPINCOMETAXDETCD,EMPINCOMETAXCD,EMPNO,INCOMETAX,STATUS,PTAX) values("+incometaxdtcd+","+taxCd+",'"+info1.getEmpno()+"','"+info1.getTaxAmt()+"','A','"+info1.getPtax()+"')");
				log.info("insrtQry for empincometaxdet is --- "+insrtQry.toString());
				db.executeUpdate(insrtQry.toString());
			}
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception saveEmpIncometax >>>>>>>>>>>>");
			log.printStackTrace(e);
			//throw new ApplicationException(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public EmployeeIncomeTaxInfo showEmpIncomeTaxEdit(int code) {
		EmployeeIncomeTaxInfo taxDt =  new EmployeeIncomeTaxInfo();
		List dtList=new ArrayList();
		try {
			db.makeConnection();con = db.getConnection();
			String qry = "Select EMPINCOMETAXCD,pay.payrollmonthnm||'-'||pay.payrollyear payroll from empincometax tax,  monthlypayroll pay where  pay.payrollmonthid = tax.payrollmonthid and EMPINCOMETAXCD ="+code+"";
			log.info(qry);
			rs = db.getRecordSet(qry);
			if(rs.next()){
				taxDt.setEmpincometaxcd(rs.getString("EMPINCOMETAXCD"));
				//taxDt.setStaffCategoryName(rs.getString("staffctgrynm"));
				taxDt.setMonthId(rs.getString("payroll"));	
			}
			db.closeRs();
			qry = "Select EMPINCOMETAXDETCD,EMPINCOMETAXCD,tax.EMPNO EMPNO,EMPLNUMBER,(EMPLOYEENAME)name, INCOMETAX,PTAX from empincometaxdet tax,employeeinfo emp where emp.empno=tax.empno and EMPINCOMETAXCD="+code+" order by stationcd desc,emplnumber  ";
			log.info(qry);
			rs=db.getRecordSet(qry);
			
			while(rs.next()){
				EmployeeIncomeTaxInfo taxDt1 = new EmployeeIncomeTaxInfo();
				taxDt1.setEmpincometaxdetcd(rs.getString("EMPINCOMETAXDETCD"));
				taxDt1.setEmpincometaxcd(rs.getString("EMPINCOMETAXCD"));
				taxDt1.setEmpno(rs.getString("EMPNO"));
				taxDt1.setEmpname(rs.getString("name"));	
				taxDt1.setTaxAmt(new Double(rs.getDouble("INCOMETAX")));
				taxDt1.setPtax(new Double(rs.getDouble("PTAX")));
				taxDt1.setEmplnumber(rs.getString("EMPLNUMBER"));
				dtList.add(taxDt1);
			}
			taxDt.setEmployeeList(dtList);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
				}
			}
		}
		return taxDt;
	}
	public void updateEmpIncometax(EmployeeIncomeTaxInfo info) {
		try {	
			db.makeConnection();con = db.getConnection();														
			db.setAutoCommit(false);
			List empList = info.getEmployeeList();
			int size = empList.size();
			StringBuffer updateQry = new StringBuffer();
			for(int i=0;i<size;i++){
				updateQry.delete(0,updateQry.length());
				EmployeeIncomeTaxInfo info1 = (EmployeeIncomeTaxInfo)empList.get(i);
				log.info("Update empincometaxdet set INCOMETAX="+info1.getTaxAmt()+" where EMPINCOMETAXDETCD="+Integer.parseInt(info1.getEmpincometaxdetcd()));
				updateQry.append("Update empincometaxdet set INCOMETAX="+info1.getTaxAmt()+",PTAX="+info1.getPtax()+" where EMPINCOMETAXDETCD="+Integer.parseInt(info1.getEmpincometaxdetcd()));
				db.executeUpdate(updateQry.toString());
			}
			
			log.info("inside updateEmpIncometax");
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public List getAllEarnDedTypes(int categoryCd) {
		EarningMasterInfo earningInfo=null;
		List typeList = new ArrayList();
		
		try {
			db.makeConnection();con = db.getConnection();
			//String getEarningInfoqry = "Select distinct(EARNDEDUCD),EARNDEDUNM,STAFFCTGRYCD,TYPE from earndedumaster ear where ear.STAFFCTGRYCD="+categoryCd+" and ear.TYPE in('E','D','PK','OR','IT','GPROF') and ear.status='A' ";
			String getEarningInfoqry = "Select distinct(EARNDEDUCD),EARNDEDUNM,STAFFCTGRYCD,TYPE from earndedumaster ear where  ear.TYPE in('E','D','PK','OR','IT','GPROF') and ear.status='A' ";
			log.info(getEarningInfoqry);
			rs =db.getRecordSet(getEarningInfoqry);
			
			while(rs.next()){
				earningInfo = new EarningMasterInfo();
				earningInfo.setEarningCd(new Integer(rs.getString("EARNDEDUCD")));
				earningInfo.setEarningName(rs.getString("EARNDEDUNM"));
				earningInfo.setStaffCategoryCd(new Integer(rs.getString("STAFFCTGRYCD")));
				earningInfo.setType(rs.getString("TYPE"));
				typeList.add(earningInfo);
			}
			log.info("earningInfo is "+earningInfo);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return typeList;
	}
	public void savePayStopConfig(PayStopConfigInfo pay) throws ApplicationException {
		try {
			db.makeConnection();con = db.getConnection();
		    db.setAutoCommit(false);
			int configCd = Integer.parseInt(AutoGeneration.getNextCode("paystopconfig","PAYSTOPCONFIGCD",5,con));
			log.info("Insert into paystopconfig (PAYSTOPCONFIGCD,STAFFCTGRYCD,EARNINGS,DEDUCTIONS,PERKS,RECOVERIES,USERCD,status) values ("+configCd+","+pay.getStaffCategoryCd()+",'"+pay.getEarnings()+"','"+pay.getDeductions()+"','"+pay.getPerks()+"','"+pay.getRecoveries()+"','"+pay.getUserCd()+"','A')");
			db.executeUpdate("Insert into paystopconfig (PAYSTOPCONFIGCD,STAFFCTGRYCD,EARNINGS,DEDUCTIONS,PERKS,RECOVERIES,USERCD,status) values ("+configCd+","+pay.getStaffCategoryCd()+",'"+pay.getEarnings()+"','"+pay.getDeductions()+"','"+pay.getPerks()+"','"+pay.getRecoveries()+"','"+pay.getUserCd()+"','A')");
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception saveEmpIncometax >>>>>>>>>>>>");
			//throw new ApplicationException(e);
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	public boolean checkStaffData(int categoryCd) {
		boolean check = false;
		 try {
			 db.makeConnection();con = db.getConnection();
		     //int count = db.getRecordCount("select count(*) from paystopconfig where STAFFCTGRYCD="+categoryCd+" ");
			 int count = db.getRecordCount("select count(*) from paystopconfig  ");
		     log.info("select count(*) from paystopconfig where STAFFCTGRYCD="+categoryCd+" ");
			      if(count!=0){
				        check = true;
			        }else
				        check = false;
		 } catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return check;
	}
	public PayStopConfigInfo getEditTypes(int categoryCd) {
		PayStopConfigInfo payInfo = null;
try {
			
			db.makeConnection();
			con = db.getConnection();
			 String qry="";
			 qry = "select PAYSTOPCONFIGCD,pay.staffctgrycd staffctgrycd ,pay.earnings earningCds,pay.deductions deductionCds, pay.perks perkCds ,pay.recoveries recoverieCds from paystopconfig pay where pay.status='A'";
	
			 log.info("Select Query is "+qry);
			rs = db.getRecordSet(qry);
			String cds=",";
			
			while(rs.next()){
				payInfo = new PayStopConfigInfo();
				payInfo.setConfigid(Integer.parseInt(rs.getString("PAYSTOPCONFIGCD")));
				payInfo.setStaffCategoryCd(Integer.parseInt(rs.getString("staffctgrycd")));
				//payInfo.setStaffCategoryName(rs.getString("staffctgrynm"));
				payInfo.setEarnings(rs.getString("earningCds"));
				payInfo.setDeductions(rs.getString("deductionCds"));
				payInfo.setPerks(rs.getString("perkCds"));
				payInfo.setRecoveries(rs.getString("recoverieCds"));
			}
			
			 
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}

		}
		return payInfo;
	}
	public void updatePayStopConfig(PayStopConfigInfo pay) throws ApplicationException {
		try {
			db.makeConnection();con = db.getConnection();
		    db.setAutoCommit(false);
		    db.executeUpdate("Update paystopconfig set status='I' where PAYSTOPCONFIGCD="+pay.getConfigid());
			int configCd = Integer.parseInt(AutoGeneration.getNextCode("paystopconfig","PAYSTOPCONFIGCD",5,con));
			log.info("Insert into paystopconfig (PAYSTOPCONFIGCD,STAFFCTGRYCD,EARNINGS,DEDUCTIONS,PERKS,RECOVERIES,USERCD,STATUS) values ("+configCd+","+pay.getStaffCategoryCd()+",'"+pay.getEarnings()+"','"+pay.getDeductions()+"','"+pay.getPerks()+"','"+pay.getRecoveries()+"','"+pay.getUserCd()+"','A')");
			db.executeUpdate("Insert into paystopconfig (PAYSTOPCONFIGCD,STAFFCTGRYCD,EARNINGS,DEDUCTIONS,PERKS,RECOVERIES,USERCD,STATUS) values ("+configCd+","+pay.getStaffCategoryCd()+",'"+pay.getEarnings()+"','"+pay.getDeductions()+"','"+pay.getPerks()+"','"+pay.getRecoveries()+"','"+pay.getUserCd()+"','A')");
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception saveEmpIncometax >>>>>>>>>>>>");
			//throw new ApplicationException(e);
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public List getEmpDetails(EmployeeIncomeTaxInfo ai,String from) {
		EmployeeIncomeTaxInfo empinfo = null;
		List empList = new ArrayList();
		List elist = new ArrayList();
      try {   
			db.makeConnection();
			con = db.getConnection();
			 String qry="";
			 qry = "select ei.EMPNO as EMPNO,EMPLNUMBER emplno,(EMPLOYEENAME)name  from employeeinfo ei ,EMPLOYEEPERSONALINFO epi where epi.empno = ei.empno  and epi.SEPERATIONDATE is null   order by stationcd desc,emplno ";
			 log.info("Employee Details----- "+qry);
			rs = db.getRecordSet(qry);
			while(rs.next()){
				empinfo = new EmployeeIncomeTaxInfo();
				empinfo.setEmpno(rs.getString("EMPNO"));
				empinfo.setEmpname(rs.getString("name"));
				empinfo.setEmplnumber(rs.getString("emplno"));				
				empList.add(empinfo);
			}
			log.info("getting details................");
			if(from.equalsIgnoreCase("IT")){
				for(int j=0;j<empList.size();j++){
					
					EmployeeIncomeTaxInfo empinfo1 = (EmployeeIncomeTaxInfo)empList.get(j);;
					List prjlist = IncomeTaxDAO.getInstance().empProjectedITInfo(empinfo1.getEmpno(),ai.getFyearcd(),db);
					ProjectedIncomeTaxInfo prjinfo= (ProjectedIncomeTaxInfo)prjlist.get(0);
					log.info("tax: "+prjinfo.getTaxPerMonth());
					empinfo1.setTaxAmt(new Double(prjinfo.getTaxPerMonth()));
					empinfo1.setPtax(new Double(prjinfo.getPrfTax()));
					elist.add(empinfo1);
				}
			}else{
				elist = empList;
			}
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}

		}
		return elist;
	}
	
	public void checkTaxData(EmployeeIncomeTaxInfo info) throws  Exception {
		boolean check = false;
		 try {
			 db.makeConnection();con = db.getConnection();
			 log.info("ddddddddddd");
		     int count = db.getRecordCount("select count(*) from empincometax where PAYROLLMONTHID='"+info.getMonthId()+"'");

		     log.info("select count(*) from empincometax where PAYROLLMONTHID='"+info.getMonthId()+"'");
			      if(count!=0)
				    throw new  ApplicationException("Data already exist for this Payroll Month ");
			      
		 } catch (ApplicationException e) {
		 	log.printStackTrace(e);
			 throw new  ApplicationException(e.getMessage());
			} catch (Exception ae) {
				log.printStackTrace(ae);
			 throw new  ApplicationException(ae.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		
	}
	public List getCCSEmployees(String staffctgry){
		List emplist = new ArrayList();
		try {
			 db.makeConnection();
			 con = db.getConnection();
			 String qry = " select ccs.* from (select ei.empno,ei.EMPLNUMBER,ei.STAFFCTGRYCD,ei.EMPLOYEENAME empname,eed.AMOUNT,eed.EARNDEDUCD,eed.SLNO  from employeeinfo ei,EMPERNSDEDUCS eed " +
			 		"  where ei.EMPNO = eed.EMPNO   and eed.EARNDEDUCD = " +
			 		" (select EARNDEDUCD from configmapping where STAFFCTGRYCD = '"+staffctgry+"' and configname='CCS') " +
			 		" )ccs where ccs.amount > 0 order by to_number(ccs.EMPLNUMBER)";
			 log.info("getCCSEmployees "+qry);
			 rs = db.getRecordSet(qry);
			 while(rs.next()){
			 	CCSINFO ci = new CCSINFO();
			 	ci.setEmpno(rs.getString("empno"));
			 	ci.setEmplnumber(rs.getString("EMPLNUMBER"));
			 	ci.setEmpname(rs.getString("empname"));
			 	ci.setEarndedcd(rs.getString("EARNDEDUCD"));
			 	ci.setCcsamount(rs.getString("amount"));
			 	ci.setSlno(rs.getString("SLNO"));
			 	ci.setStaffcategory(rs.getString("STAFFCTGRYCD"));
			 	emplist.add(ci);
			 }
		 }catch (Exception e) {
		 
	      } finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return emplist;
	}
	public void updateEmpCcs(List emplist) throws ApplicationException{
		Statement stmt = null;
		try {
			 db.makeConnection();
			 con = db.getConnection();
			 stmt = con.createStatement();
			 for(int i=0;i<emplist.size();i++){
			 	CCSINFO ci = (CCSINFO)emplist.get(i);
			 	String insertHistroyqry = "insert into HISTORYEMPERNSDEDUCS select * from EMPERNSDEDUCS where SLNO = "+ci.getSlno();
			 	stmt.addBatch(insertHistroyqry);
			 	log.info("insertHistroyqry "+insertHistroyqry);
			 	String updateqry = "update EMPERNSDEDUCS set AMOUNT= '"+ci.getCcsamount()+"' where SLNO = "+ci.getSlno();
			 	stmt.addBatch(updateqry);
			 	log.info("updateqry "+updateqry);
			 	con.setAutoCommit(false);
			 	int[] updateCounts  = stmt.executeBatch();
			 	con.commit();
			    log.info("updateCounts "+updateCounts.length);
			 	
			 }
			 
		}catch(Exception e){
			log.printStackTrace(e);
			throw new ApplicationException(e.getMessage());
			
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
	}
	public String getDaPercentage(String staffctgry) {
		String daperc = "";
		try {
			 db.makeConnection();
			 con = db.getConnection();
			 String daqry = "select DAPERCENTAGE from DAPERCENTAGE where STATUS='A' and STAFFCTGRYCD='"+staffctgry+"'";
			 log.info("daqry "+daqry);
			 rs = db.getRecordSet(daqry);
			 if(rs.next()){
			 	daperc = rs.getString("DAPERCENTAGE");
			 }
		}catch(Exception e){
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return daperc;
	}
	public void saveDaPercentage(DAHikeInfo info) throws ApplicationException {
		String daperc = "";
		try {
			 db.makeConnection();
			 con = db.getConnection();
			 int cnt = 0;
			 cnt = db.getRecordCount("select count(*) from monthlypayroll where PAYROLLMONTHID='"+info.getFrmmonth()+"' and payrollfromdt <=(select payrollfromdt from monthlypayroll where PAYROLLMONTHID=(Select FROMMNTH from dapercentage where STAFFCTGRYCD='"+info.getStaffCategoryCd()+"' and STATUS='A' and UPDATEDEMPL='Y'))");
			 if(cnt >0){
				throw new ApplicationException("Salary has been generated for the selected DAHike From Month");
			 }
			 db.setAutoCommit(false);
			 db.executeUpdate("Update dapercentage set TOMNTH=(select payrollmonthid from monthlypayroll where payrolltodt=(select payrollfromdt-1 from monthlypayroll where payrollmonthid='"+info.getFrmmonth()+"') ) where STAFFCTGRYCD='"+info.getStaffCategoryCd()+"' and status='A' ");
			 int daperCd = Integer.parseInt(AutoGeneration.getNextCode("dapercentage","DAPERCD",5,con));
			 
			 String insrtdaqry = " Insert into dapercentage (DAPERCD,STAFFCTGRYCD,DAPERCENTAGE,FROMMNTH,STATUS,UPDATEDEMPL,USERCD)" +
			 		" values('"+daperCd+"','"+info.getStaffCategoryCd()+"','"+info.getDapercentage()+"','"+info.getFrmmonth()+"','I','N','"+info.getUsercd()+"')";
			 
			 log.info("insrtdaqry "+insrtdaqry);
			 db.executeUpdate(insrtdaqry);
			 db.commitTrans();
		}catch(Exception e){
			try {
				db.rollbackTrans();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.printStackTrace(e);
			throw new ApplicationException(e.toString());
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		
	}
	public void processDaPercentage(DAHikeInfo info) throws ApplicationException {
		String daperc = "";
		try {
			 db.makeConnection();
			 con = db.getConnection();
			 
			 String command = "{call dahike_updation(?,?,?)}";     // 3 placeholders
			 CallableStatement cstmt = con.prepareCall (command);
			 cstmt.setString(1, info.getFrmmonth());
			 cstmt.setString(2, " ");    
			 cstmt.setString(3, info.getUsercd());    
			 cstmt.execute();
			 cstmt.close();
		}catch(Exception e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.printStackTrace(e);
			throw new ApplicationException(e.toString());
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		
	}
	public List getPayrollMonths4DAHike() {
		List list = new ArrayList();
		try{
			db.makeConnection();
			con = db.getConnection();
			String qry = "select PAYROLLMONTHID,(PAYROLLMONTHNM||'-'||PAYROLLYEAR)name from monthlypayroll where to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy') <= PAYROLLTODT and FYEARCD in (select FYEARCD from financialyear where TODATE >= to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy')) ";
			//String qry = "select PAYROLLMONTHID,(PAYROLLMONTHNM||'-'||PAYROLLYEAR)name from monthlypayroll mp, FINANCIALYEAR fy where mp.FYEARCD = fy.FYEARCD and fy.STATUS = 'A' ";
			rs = db.getRecordSet(qry);
			while(rs.next()){
				MonthlyPayrollInfo info = new MonthlyPayrollInfo();
				info.setPayrollmonthid(rs.getInt("PAYROLLMONTHID"));
				info.setPayrollmonthnm(rs.getString("name"));
				list.add(info);
			}
		}catch(Exception e){
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return list;
	}
	public List searchDaPercentage(String cd) {
		List list = new ArrayList();
		try{
			db.makeConnection();
			con = db.getConnection();
			String qry = "select DAPERCD,get_description('STAFFCTGRYNM','staffcategory sc','sc.STAFFCTGRYCD=''' || dp.staffctgrycd || '''')STAFFCTGRYNM,DAPERCENTAGE, get_description('payrollmonthnm||''-''||payrollyear','monthlypayroll mp','mp.payrollmonthid=''' || dp.frommnth || '''')FROMMNTH,get_description('payrollmonthnm||''-''||payrollyear','monthlypayroll mp','mp.payrollmonthid=''' || dp.TOMNTH || '''')TOMNTH, decode(status,'I','InActive','A','Active')STATUS,UPDATEDEMPL  from dapercentage dp where dapercd is not null";
			if(!"".equals(cd))
				qry += " and STAFFCTGRYCD='"+cd+"'";
			qry += " order by STAFFCTGRYCD ";
			rs = db.getRecordSet(qry);
			while(rs.next()){
				DAHikeInfo info = new DAHikeInfo();
				info.setDapercd(rs.getString("DAPERCD"));
				info.setStaffCategoryCd(rs.getString("STAFFCTGRYNM"));
				info.setDapercentage(rs.getString("DAPERCENTAGE"));
				info.setFrmmonth(rs.getString("FROMMNTH"));
				info.setTomonth(rs.getString("TOMNTH"));
				info.setStatus(rs.getString("STATUS"));
				info.setUpdatedinemp(rs.getString("UPDATEDEMPL"));
				list.add(info);
			}
		}catch(Exception e){
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return list;
	}
	public DAHikeInfo showDAFormEdit(String cd) {
		DAHikeInfo info = new DAHikeInfo();
		try{
			db.makeConnection();
			con = db.getConnection();
			String qry = " select DAPERCD,STAFFCTGRYCD,get_description('STAFFCTGRYNM','staffcategory sc','sc.STAFFCTGRYCD=''' || dp.staffctgrycd || '''')STAFFCTGRYNM,DAPERCENTAGE,FROMMNTH From dapercentage dp where dapercd='"+cd+"'";
			rs = db.getRecordSet(qry);
			if(rs.next()){
				info.setDapercd(rs.getString("DAPERCD"));
				info.setStaffCategoryCd(rs.getString("STAFFCTGRYCD"));
				info.setStaffCategoryNm(rs.getString("STAFFCTGRYNM"));
				info.setDapercentage(rs.getString("DAPERCENTAGE"));
				info.setFrmmonth(rs.getString("FROMMNTH"));
							
			}
		}catch(Exception e){
			log.printStackTrace(e);
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return info;
	}
	public void updateDaPercentage(DAHikeInfo info) throws ApplicationException {
		String daperc = "";
		try {
			 db.makeConnection();
			 con = db.getConnection();
			 db.setAutoCommit(false);
			 int cnt = 0;
			 cnt = db.getRecordCount("select count(*) from monthlypayroll where PAYROLLMONTHID='"+info.getFrmmonth()+"' and payrollfromdt <=(select payrollfromdt from monthlypayroll where PAYROLLMONTHID=(Select FROMMNTH from dapercentage where STAFFCTGRYCD='"+info.getStaffCategoryCd()+"' and STATUS='A' and UPDATEDEMPL='Y'))");
			 if(cnt >0){
				throw new ApplicationException("Salary has been generated for the selected DAHike From Month");
			 }
			 db.executeUpdate("Update dapercentage set TOMNTH=(select payrollmonthid from monthlypayroll where payrolltodt=(select payrollfromdt-1 from monthlypayroll where payrollmonthid='"+info.getFrmmonth()+"') ) where STAFFCTGRYCD='"+info.getStaffCategoryCd()+"' and status='A' ");
			 
			 String insrtdaqry = " update dapercentage set DAPERCENTAGE='"+info.getDapercentage()+"',FROMMNTH='"+info.getFrmmonth()+"',usercd='"+info.getUsercd()+"' where DAPERCD='"+info.getDapercd()+"' ";
			 
			 log.info("update daqry "+insrtdaqry);
			 db.executeUpdate(insrtdaqry);
			 db.commitTrans();
		}catch(Exception e){
			try {
				db.rollbackTrans();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.printStackTrace(e);
			throw new ApplicationException(e.toString());
		}finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
	}
	public List searchEmpITRec(EmployeeIncomeTaxInfo info) {

		List taxList=new ArrayList();
		EmployeeIncomeTaxInfo taxInfo=null;
		try {
			
			db.makeConnection();con = db.getConnection();
			 String qry="";
			 qry = "SELECT EMPITRECCD,tax.payrollmonthid,pay.payrollmonthnm payrollmonthnm,(select distinct PAYTRANSID from PAYROLLPROCESS pp where pp.PAYROLLMONTHID = tax.PAYROLLMONTHID and rownum = 1) paidid, pay.payrollyear payrollyear  FROM empitrec tax, monthlypayroll pay WHERE pay.payrollmonthid = tax.payrollmonthid";
			/* if(info.getStaffCategoryCd()!=null && !"".equals(info.getStaffCategoryCd().trim()) )
				 qry+=" and tax.STAFFCTGRYCD='"+info.getStaffCategoryCd()+"'";*/
			 if(info.getFyearcd()!= null && !info.getFyearcd().equals("")){
			 	 qry+=" and tax.fyearcd = "+info.getFyearcd();
			 }
			 log.info("searchEmpITRec(): Select Query is "+qry);
			rs = db.getRecordSet(qry);
			
			while(rs.next()){				
				taxInfo = new EmployeeIncomeTaxInfo();
				//taxInfo.setStaffCategoryName(rs.getString("staffctgrynm"));				
				taxInfo.setEmpincometaxcd(rs.getString("EMPITRECCD"));
				taxInfo.setMonthId(rs.getString("payrollmonthnm")+"-"+rs.getString("payrollyear"));
				if(rs.getString("paidid")!=null)
					taxInfo.setType("Y");
				taxList.add(taxInfo);
			}
			log.info("itrec size "+taxList.size());
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}

		}
		return taxList;
	}
	public void saveEmpITRec(EmployeeIncomeTaxInfo info) throws ApplicationException {
		try {
			db.makeConnection();con = db.getConnection();
		    db.setAutoCommit(false);
			int taxCd = Integer.parseInt(AutoGeneration.getNextCode("empitrec","EMPITRECCD",5,con));
			String itrecqry = "Insert into empitrec (EMPITRECCD,FYEARCD,PAYROLLMONTHID,USERCD) values ("+taxCd+",'"+info.getFyearcd()+"','"+info.getMonthId()+"','"+info.getUserCd()+"')";
			log.info("itrecqry "+itrecqry);
			db.executeUpdate(itrecqry);
			List empList = info.getEmployeeList();
			int size = empList.size();
			int incometaxdtcd = 0;
			StringBuffer insrtQry = new StringBuffer();
			for(int i=0;i<size;i++){
				insrtQry.delete(0,insrtQry.length());
				EmployeeIncomeTaxInfo info1 = (EmployeeIncomeTaxInfo)empList.get(i);
				incometaxdtcd = Integer.parseInt(AutoGeneration.getNextCode("empitrecdet","EMPITRECDETCD",5,con));				
				insrtQry.append("Insert into empitrecdet(EMPITRECDETCD,EMPITRECCD,EMPNO,ITRECAMT,STATUS) values("+incometaxdtcd+","+taxCd+",'"+info1.getEmpno()+"','"+info1.getTaxAmt()+"','A')");
				log.info("insrtQry for saveEmpITRec is --- "+insrtQry.toString());
				db.executeUpdate(insrtQry.toString());
			}
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception saveEmpITRec >>>>>>>>>>>>");
			log.printStackTrace(e);
			try{
				db.rollbackTrans();
			}catch(Exception e1){
				
			}
			throw new ApplicationException(e.getMessage());
			
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public EmployeeIncomeTaxInfo showEmpITRecEdit(int code) {
		EmployeeIncomeTaxInfo taxDt =  new EmployeeIncomeTaxInfo();
		List dtList=new ArrayList();
		try {
			db.makeConnection();con = db.getConnection();
			
			String getqry = "Select EMPITRECCD,pay.payrollmonthnm||'-'||pay.payrollyear payroll from empitrec tax,  monthlypayroll pay where  pay.payrollmonthid = tax.payrollmonthid and EMPITRECCD ="+code+"";
			log.info("showEmpITRecEdit getqry "+getqry);
			rs = db.getRecordSet(getqry);
			if(rs.next()){
				taxDt.setEmpincometaxcd(rs.getString("EMPITRECCD"));
				//taxDt.setStaffCategoryName(rs.getString("staffctgrynm"));
				taxDt.setMonthId(rs.getString("payroll"));	
			}
			rs.close();
			log.info("Select EMPITRECDETCD,EMPITRECCD,tax.EMPNO EMPNO,EMPLNUMBER,(EMPLOYEENAME)name, ITRECAMT from empitrecdet tax,employeeinfo emp where emp.empno=tax.empno and EMPITRECCD="+code);
			rs=db.getRecordSet("Select EMPITRECDETCD,EMPITRECCD,tax.EMPNO EMPNO,EMPLNUMBER,(EMPLOYEENAME)name, ITRECAMT from empitrecdet tax,employeeinfo emp where emp.empno=tax.empno and EMPITRECCD="+code+" order by stationcd desc , to_number(emplnumber) ");
			
			while(rs.next()){
				EmployeeIncomeTaxInfo taxDt1 = new EmployeeIncomeTaxInfo();
				taxDt1.setEmpincometaxdetcd(rs.getString("EMPITRECDETCD"));
				taxDt1.setEmpincometaxcd(rs.getString("EMPITRECCD"));
				taxDt1.setEmpno(rs.getString("EMPNO"));
				taxDt1.setEmpname(rs.getString("name"));	
				taxDt1.setTaxAmt(new Double(rs.getDouble("ITRECAMT")));
				taxDt1.setEmplnumber(rs.getString("EMPLNUMBER"));
				dtList.add(taxDt1);
			}
			taxDt.setEmployeeList(dtList);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS >>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"+ e.getMessage());
					log.printStackTrace(e);
				}
			}
		}
		return taxDt;
	}
	public void updateEmpITRec(EmployeeIncomeTaxInfo info) {
		try {	
			db.makeConnection();con = db.getConnection();														
			db.setAutoCommit(false);
			List empList = info.getEmployeeList();
			int size = empList.size();
			StringBuffer updateQry = new StringBuffer();
			for(int i=0;i<size;i++){
				updateQry.delete(0,updateQry.length());
				EmployeeIncomeTaxInfo info1 = (EmployeeIncomeTaxInfo)empList.get(i);
				log.info("Update empitrecdet set ITRECAMT="+info1.getTaxAmt()+" where EMPITRECDETCD="+Integer.parseInt(info1.getEmpincometaxdetcd()));
				updateQry.append("Update empitrecdet set ITRECAMT="+info1.getTaxAmt()+" where EMPITRECDETCD="+Integer.parseInt(info1.getEmpincometaxdetcd()));
				db.executeUpdate(updateQry.toString());
			}
			
			log.info("inside updateEmpITRec");
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public void checkITRecData(EmployeeIncomeTaxInfo info) throws Exception {
		 try {
			 db.makeConnection();con = db.getConnection();
			 
		     int count = db.getRecordCount("select count(*) from empitrec where FYEARCD='"+info.getFyearcd()+"'");

		     log.info("select count(*) from empitrec where FYEARCD='"+info.getFyearcd()+"'");
			      if(count!=0)
				    throw new  ApplicationException("Data already exist for this Financial Year ");
			      
		 } catch (ApplicationException e) {
		 	log.printStackTrace(e);
			 throw new  ApplicationException(e.getMessage());
			} catch (Exception e) {
				log.printStackTrace(e);
			 throw new  ApplicationException(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
	}
	public List getGSLISCadres(String staffctgry){
		List gslisclist = new ArrayList();
		
		 try {
			 db.makeConnection();con = db.getConnection();
			 String gslisqry = "select GSLISCADRECD,GSLISCADRENM from GSLISCADRE where GSLISCADRECD is not null ";
			 if(!staffctgry.equals("")){
			 	gslisqry = gslisqry+" and STAFFCTGRYCD = "+staffctgry;
			 }
			 gslisqry = gslisqry+" order by GSLISCADRECD ";
			 log.info(" gslisqry "+gslisqry);
			 rs = db.getRecordSet(gslisqry);
			 while(rs.next()){
			 	CadreInfo ci = new CadreInfo();
			 	ci.setCadreCd(rs.getString("GSLISCADRECD"));
			 	ci.setCadreName(rs.getString("GSLISCADRENM"));
			 	gslisclist.add(ci);
			 }
		 }catch (Exception e) {
			 log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		return gslisclist;
		
	}
	public List getEmpEarndedValues(String empno,String type){
		List earlist = new ArrayList();
		try {
			 db.makeConnection();con = db.getConnection();
			 String earcdqry = "select EARNDEDUCD from EMPLEDITCONFIG where   type='"+type+"' and status='A'";
			 String earnddeducd = "";
			 log.info(" earcdqry "+earcdqry);
			 rs = db.getRecordSet(earcdqry);
			 if(rs.next()){
			 	earnddeducd = rs.getString("EARNDEDUCD");   //ex:13,17,24,27,29,33
			 }
			 //earnddeducd = earnddeducd.replaceAll("'","");
			 db.closeRs();
			 if(!earnddeducd.equals("")){
			 	String vconqry = "";
			 	if(type.equalsIgnoreCase("PK")){
			 		vconqry = ",(select earndeducd from configmapping where configname='VCON')vconcd";	
			 	}
			 	
			 	String earnvalqry = " select ei.emplnumber,(select DESGCODE from Staffdesignation where DESIGNATIONCD = ei.DESIGNATIONCD) DESGCODE"+vconqry+",ei.EMPLOYEENAME,edm.SHORTNAME,edm.EARNDEDUNM,eed.AMOUNT,eed.SLNO,edm.EARNDEDUCD,slm.DEPENDSON,slm.PERCENTAGEVALUE,ei.HRAOPTION,ei.PETROL from employeeinfo ei,EMPERNSDEDUCS eed,earndedumaster edm ,SALHEAD_LEDG_MAPPING slm where  eed.EARNDEDUCD = slm.EARNDEDUCD and  slm.CADRECOD = ei.CADRECOD and eed.EARNDEDUCD = edm.EARNDEDUCD and ei.EMPNO = '"+empno+"' and ei.EMPNO = eed.EMPNO and eed.EARNDEDUCD in("+earnddeducd+") order by edm.PRINTORDER";
			 	 log.info(" earnvalqry "+earnvalqry);
			 	ResultSet vrs = db.getRecordSet(earnvalqry);
			 	while(vrs.next()){
			 		String hraoption = vrs.getString("HRAOPTION");
			 		 String earnshrtnm  = vrs.getString("SHORTNAME");
			 		// if( !(earnshrtnm.trim().equals("HRA") && (hraoption.equals("Q") || hraoption.equals("L"))) ){
					 		EmployeeEarningInfo earningInfo= new EmployeeEarningInfo();
					 		earningInfo.setEmpNo(vrs.getString("emplnumber"));
					 		earningInfo.setDesgCode(vrs.getString("DESGCODE"));
					 		earningInfo.setEmpName(vrs.getString("EMPLOYEENAME"));
					 		earningInfo.setShortname(vrs.getString("SHORTNAME"));
					 		earningInfo.setEarningCd(new Integer(vrs.getString("EARNDEDUCD")));
					 		//log.info(" earningInfo.setEarningCd "+earningInfo.getEarningCd());
					 		earningInfo.setEarningName( vrs.getString("EARNDEDUNM"));
					 		earningInfo.setAmount(new Double(vrs.getString("AMOUNT")));
					 		earningInfo.setSlno(new Integer(vrs.getString("SLNO")));
					 		/*earningInfo.setDepends(vrs.getString("DEPENDSON")==null?"":vrs.getString("DEPENDSON"));
					 		String depen = vrs.getString("DEPENDSON");*/
					 		//log.info("depen "+vrs.getString("DEPENDSON"));
					 		earningInfo.setDepends(vrs.getString("DEPENDSON"));
					 		String pv = vrs.getString("PERCENTAGEVALUE");
					 		if(pv == null || pv.trim().equals("")){
					 			pv = "0";
					 		}
					 		if(type.equalsIgnoreCase("PK")){
					 			if(earningInfo.getVconcd() == null){
					 				earningInfo.setVconcd(vrs.getString("vconcd"));
					 			}
					 		}
					 		earningInfo.setPercentVal(new Double(pv));
					 		earningInfo.setHraOption(hraoption);
					 		earningInfo.setPetrol(vrs.getString("PETROL"));
					 		earlist.add(earningInfo);
			 		 //}			 		
			 	}
			 }
			 
		}catch (Exception e) {
			log.info("-----------");
			 log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		
		log.info("###############");
		return earlist;
		
	}
	public void saveEmpEditTransDetails(List l,String type,String empno,String hraopt,String petrol,String desgcode){
		try {
			 db.makeConnection();con = db.getConnection();
			 Statement stmt1 = con.createStatement();
			 con.setAutoCommit(false);
			 String list = "";
			 List ul = new ArrayList();
			 boolean updtbatch = false;
			 for(int i=0;i<l.size();i++){
			 	EmployeeEarningInfo ei = (EmployeeEarningInfo)l.get(i);
			 	String updateqry = " update EMPERNSDEDUCS set amount = '"+ei.getAmount()+"' where empno = '"+ei.getEmpNo()+"' and EARNDEDUCD = '"+ei.getEarningCd()+"'";
			 	list = list+ei.getEarningCd()+",";
			 	ul.add(updateqry);
			 	//log.info("updating erndeds updateqry "+updateqry);
			 }
			 if(type.equals("ED") && !hraopt.equals("")){
			 	 
			 	String updatehraoptqry = " update employeeinfo set HRAOPTION='"+hraopt+"' , LASTUPDATEDDT=sysdate where empno = '"+empno+"'";
			 	log.info("updatehraoptqry "+updatehraoptqry);
			 	stmt1.addBatch(updatehraoptqry);
			 	updtbatch = true;
			 }
			 if(type.equals("ED") && !desgcode.equals("")){
			 	String updatedesgqry = "update employeeinfo set DESIGNATIONCD = (select DESIGNATIONCD from STAFFDESIGNATION where DESGCODE = "+desgcode+") where empno = '"+empno+"'";
			 	log.info("updatedesgqry "+updatedesgqry);
			 	stmt1.addBatch(updatedesgqry);
			 	updtbatch = true;
			 }
			 if(type.equals("PK") && !petrol.equals("")){
			 	String updatepetqry = " update employeeinfo set PETROL='"+petrol+"' , LASTUPDATEDDT=sysdate where empno = '"+empno+"'";
			 	log.info("updatepetqry "+updatepetqry);
			 	stmt1.addBatch(updatepetqry);
			 	updtbatch = true;
			 	
			 }
			 
			 if(!list.equals("")){
				list = list.substring(0,list.length()-1);
				String inserthisearnqry = " insert into HISTORYEMPERNSDEDUCS select * from EMPERNSDEDUCS where empno = '"+empno+"' and EARNDEDUCD in("+list+")"; 
				 log.info("inserting earndedvalues to historyempearnd "+inserthisearnqry);
				 stmt1.addBatch(inserthisearnqry);
				 for(int i=0;i<ul.size();i++){
				 	String qry = ul.get(i).toString();
				 	log.info("qry "+qry);
				 	stmt1.addBatch(qry);
				 }
				 updtbatch = true;
			}
			 if(updtbatch){
			 	int updc [] = stmt1.executeBatch();
			 	log.info("updated updc "+updc.length);
			 	con.setAutoCommit(true);
			 }
			
			 
		}catch (Exception e) {
			log.info("-----------");
			 log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
	}
	public List getDesignations(){
		List desglist = new ArrayList();
		try {
			 db.makeConnection();
			 con = db.getConnection();
			 
			 String desgcodeqry = "select DESIGNATIONCD, DESIGNATIONNM from STAFFDESIGNATION where status = 'A'";
			 log.info("desgcodeqry "+desgcodeqry);
			 rs = db.getRecordSet(desgcodeqry);
			 while(rs.next()){
			 	DesignationInfo di = new DesignationInfo();
			 	di.setDesgCode(rs.getString("DESIGNATIONCD"));
			 	di.setDesignatioNm(rs.getString("DESIGNATIONNM"));
			 	desglist.add(di);
			 }
		}catch (Exception e) {			
			 log.printStackTrace(e);
		} finally {
			if (rs != null) {
				try {
					log.info("<<<<<<<<<< Closing RS>>>>>>>>>>>>");
					db.closeRs();
					db.closeCon();
				} catch (Exception e) {
					log.info("<<<<<<<<<< Exception RS >>>>>>>>>>>>"	+ e.getMessage());
				}
			}
		}
		
		return desglist;
	}
	public void addEmpSeperationInfo(EmployeePersonalInfo emppersonalinfo) {
		String name="";
		try {	
			db.makeConnection();con = db.getConnection();														
			db.setAutoCommit(false);
			
			String qry = "Update employeepersonalinfo set SEPERATIONDATE='"+emppersonalinfo.getDateofseperation()+"',SEPERATIONREASON='"+emppersonalinfo.getSeperationReason()+"' where empno='"+emppersonalinfo.getEmpNo()+"'";
			log.info("seperation qry is ---- "+qry);
			db.executeUpdate(qry);
			String query="select EMPLOYEENAME from employeeinfo where EMPNO='"+emppersonalinfo.getEmpNo()+"'";
			rs=db.getRecordSet(query);
			while(rs.next())
			{
				name=rs.getString("EMPLOYEENAME");
			}
			String activityDesc="Employee Seperation Information Of "+name+" Saved.";
			ActivityLog.getInstance().write(emppersonalinfo.getUsercd(), activityDesc, "N", String.valueOf(emppersonalinfo.getEmpNo()), con);
			db.commitTrans();
			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public List showEmpSeperationInfo(EmployeePersonalInfo emppersonalinfo) {
		List list = new ArrayList();
		try {	
			db.makeConnection();con = db.getConnection();														
			
			String qry = "select emplnumber,employeename,to_char(SEPERATIONDATE,'dd/Mon/yyyy')SEPERATIONDATE,SEPERATIONREASON from employeeinfo ei,employeepersonalinfo epi where ei.empno=epi.empno and epi.SEPERATIONDATE is not null";
			if(!emppersonalinfo.getEmpNo().equals(""))
				qry += " and emplnumber='"+emppersonalinfo.getEmpNo()+"'";
			if(!emppersonalinfo.getEmployeeName().equals(""))
				qry += " and emplnumber='"+emppersonalinfo.getEmployeeName()+"'";
			log.info("seperation search qry is ---- "+qry);
			rs = db.getRecordSet(qry);
			while(rs.next()){
				emppersonalinfo = new EmployeePersonalInfo();
				emppersonalinfo.setEmpNo(rs.getString("emplnumber"));
				emppersonalinfo.setEmployeeName(rs.getString("employeename"));
				emppersonalinfo.setDateofseperation(rs.getString("SEPERATIONDATE"));
				emppersonalinfo.setSeperationReason(rs.getString("SEPERATIONREASON"));
				list.add(emppersonalinfo);
			}		
			
			db.closeRs();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	public List getSalaryHeads(String cd,String empno){
		List list = new ArrayList();
		EmployeeEarningInfo info = null;
		try{
			db.makeConnection();con = db.getConnection();
			/*if(!empno.trim().equals("")){
				cd = "select staffctgrycd from employeeinfo where empno = '"+empno+"'";
			}*/
			String sql = "select EARNDEDUCD, EARNDEDUNM from earndedumaster where  type in ('E','D','PK','OR') and status='A' order by printorder ";
			log.info("getSalaryHeads sql "+sql);
			rs = db.getRecordSet(sql);
			while(rs.next()){
				info = new EmployeeEarningInfo();
				info.setEarningCd(new Integer(rs.getInt("EARNDEDUCD")));
				info.setEarningName(rs.getString("EARNDEDUNM"));
				//info.setIsBasicSal(rs.getString("isbasicsalary"));
				list.add(info);
				
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	public List getEmployeesforSalHeads(String[] cds,String eno,String paybillcd){
		List list = new ArrayList();
		List amtlist = new ArrayList();
		List empamtsList = new ArrayList();
		List finallist = new ArrayList();
		List amtnameslist = new ArrayList();
		EmployeeEarningInfo info = null;
		EmployeeEarningInfo info1 = null;
		String cd = "";
		for (int i=0;i<cds.length;i++){
			cd += cds[i]+",";
		}
		cd = cd.substring(0,cd.length()-1);
		String earndeducd = "";
		boolean anotherAmt = false;
		try{
			db.makeConnection();con = db.getConnection();		
			String sql = " select slno,ei.empno,emplnumber,employeename,sd.designationnm,AMOUNT,ed.earndeducd,edm.earndedunm from earndedumaster edm ,empernsdeducs ed,employeeinfo ei,staffdesignation sd where ed.earndeducd in ("+cd+") and ed.empno=ei.empno and ei.designationcd=sd.designationcd and ed.earndeducd=edm.earndeducd ";
			if(!eno.equals(""))
				sql = sql + " and ei.empno="+eno;
			if(!paybillcd.equals(""))
				sql = sql + " and ei.PAYBILLCD="+paybillcd;
			sql = sql + " order by earndeducd,to_number(empno)";
			log.info("sql is ------- "+sql);
			rs = db.getRecordSet(sql);
			int cnt = 0;
			while(rs.next()){
				if(!earndeducd.equals("") && !earndeducd.equals(rs.getString("earndeducd"))){
					anotherAmt = true;
					empamtsList.add(amtlist);
					amtlist = new ArrayList();
					
					info1 = new EmployeeEarningInfo();
					info1.setEmplnumber(rs.getString("emplnumber"));
					info1.setEmpNo(rs.getString("empno"));
					info1.setSlno(new Integer(rs.getInt("slno")));
					info1.setFixedAmt(new Double(rs.getDouble("amount")));
					info1.setSlno(new Integer(rs.getInt("slno")));
					amtlist.add(info1);
					
					info = new EmployeeEarningInfo();
					info.setEarningName(rs.getString("earndedunm"));
					amtnameslist.add(info);
				}else {
					if(cnt==0){
						info = new EmployeeEarningInfo();
						info.setEarningName(rs.getString("earndedunm"));
						amtnameslist.add(info);
					}
					if(!anotherAmt){
						info = new EmployeeEarningInfo();
						info.setEmplnumber(rs.getString("emplnumber"));
						info.setEmpNo(rs.getString("empno"));
						info.setSlno(new Integer(rs.getInt("slno")));
						info.setEmpName(rs.getString("employeename"));
						info.setEmpDesignation(rs.getString("designationnm"));
						list.add(info);
					}
					info1 = new EmployeeEarningInfo();
					info1.setEmplnumber(rs.getString("emplnumber"));
					info1.setEmpNo(rs.getString("empno"));
					info1.setFixedAmt(new Double(rs.getDouble("amount")));
					info1.setSlno(new Integer(rs.getInt("slno")));
					amtlist.add(info1);
				}
				earndeducd = rs.getString("earndeducd");
				cnt++;
			}
			log.info("amtlist size is -- "+amtlist.size()+" --- list size is ----- "+list.size());
			
			empamtsList.add(amtlist);
			finallist.add(list);
			finallist.add(empamtsList);
			finallist.add(amtnameslist);
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return finallist;
	}
	public String saveEmpSalHeadAmts(String slno,String amt,String usercd,String earncd,String empno){
		String s = "";
		String name="";
		 try{
		  	db.makeConnection();
			con = db.getConnection();
			log.info("con "+con);
			//String inshisqry = "insert into EMPLOYEEADVANCESHISTORY select * from employeeadvances where EMPLOYEEADVCD = "+empadvcd;
			String qry = "select EARNDEDUCD from EARNDEDUMASTER where ";
			String inshisqry  = "insert into HISTORYEMPERNSDEDUCS select * from EMPERNSDEDUCS where slno="+slno+" ";
			log.info("inserting into EMPERNSDEDUCS history " +inshisqry);
			log.info("slnol-------------- "+slno);
			String updateqry = "update EMPERNSDEDUCS set AMOUNT= '"+amt+"' where  slno="+slno+" ";
		 	
			log.info("updqry "+updateqry);
			s = "Updated Successfully";
			
			//con.setAutoCommit(false);
			db.executeUpdate(inshisqry);
			//log.info("getAutoCommit "+con.getAutoCommit());
			int m = db.executeUpdate(updateqry);
			log.info("getAutoCommit "+con.getAutoCommit()+"---------------"+m);
	
		    String edcd = getEarnDeductions(empno,db);
			String command = "{call modsalhead(?,?,?)}";     // 2 placeholders
			CallableStatement cstmt = con.prepareCall (command);
			StringTokenizer token = new StringTokenizer(edcd,"|");
			while(token.hasMoreTokens()){
			cstmt.setString(1, earncd);
			cstmt.setString(2, empno);  
			cstmt.setString(3, token.nextToken());
			cstmt.execute();
			}
			 
			
			String qry1="select EMPLOYEENAME from employeeinfo where EMPNO=(select EMPNO from EMPERNSDEDUCS where slno='"+slno+"')";
			rs=db.getRecordSet(qry1);
			while(rs.next())
			{
			   name=rs.getString("EMPLOYEENAME");	
			}
			String activityDesc="Employee Salary Heads Edit Of "+name+" Updated.";
			ActivityLog.getInstance().write(usercd, activityDesc, "E", String.valueOf(slno), con);
			cstmt.close();
			con.commit();
		 } catch (Exception e) {
			log.info("Exception in catch, saveEmpSalHeadAmts() : " );
			s = "There is an error while saving the details";
			log.printStackTrace(e);
			try{
				con.rollback();
			}catch(Exception e1){
				log.printStackTrace(e1);
			}
		} finally {
			try {
				db.closeCon();
			} catch (Exception e) {
				log.info("Exception in finally catch, saveEmpSalHeadAmts() : "+ e.getMessage());
			}
		}
		return s;
	}
	public Map getEmployeesforSalHeadsForm(String fyear,String empno,String[] earndeduCd){
		List list = new ArrayList();
		Map map=new HashMap();
		EmployeeEarningInfo info = null;
		String earnCD="";
		for(int i=0;i<earndeduCd.length;i++)
			earnCD+=earndeduCd[i]+",";
		
		earnCD=earnCD.substring(0,earnCD.length()-1);
			
		try{
			db.makeConnection();con = db.getConnection();
			
			String sql = "SELECT   prd.earndeducd, ed.earndedunm,(SELECT mp.payrollmonthnm || ' ' || mp.payrollyear" +
					" AS MONTH FROM monthlypayroll mp WHERE mp.payrollmonthid = (SELECT payrollmonthid FROM " +
					"payrollprocess pp WHERE pp.paytransid = prd.paytransid))  MONTH, amount,emi.emplnumber,emi.employeename,(select sd.designationnm from staffdesignation sd where sd.designationcd = emi.designationcd)designationnm FROM payrolldet prd, " +
					"earndedumaster ed,employeeinfo emi WHERE paytransid IN ( SELECT DISTINCT pp.paytransid FROM payrollprocess pp, " +
					"employeeinfo ei, payrolldet pt  WHERE payrollmonthid IN ( SELECT payrollmonthid FROM " +
					"monthlypayroll mp where fyearcd = '"+fyear+"') AND " +
					"ei.empno = '"+empno+"' AND pp.paytransid = pt.paytransid AND ei.empno = pt.empno)  AND prd.empno = '"+empno+"'" +
					" AND prd.earndeducd = ed.earndeducd AND prd.earndeducd IN ("+earnCD+") and prd.empno = emi.empno ORDER BY " +
					"prd.paytransid,ED.PRINTORDER";
			log.info("getSalaryHeads sql "+sql);
			
			rs = db.getRecordSet(sql);
			
			String Month="";
			String comMonth="";
			while(rs.next()){
				
				info = new EmployeeEarningInfo();
				comMonth=Month;
				Month=rs.getString("MONTH");
				info.setEarningCd(new Integer(rs.getInt("EARNDEDUCD")));
				info.setEmpNo(rs.getString("emplnumber"));
				info.setEmpName(rs.getString("employeename"));
				info.setEmpDesignation(rs.getString("designationnm"));
				info.setEarningName(rs.getString("EARNDEDUNM"));
				info.setMonth(rs.getString("MONTH"));
				info.setAmount(new Double(rs.getDouble("AMOUNT")));
				
				if(!comMonth.equals(Month) && !comMonth.equals("")){
					map.put(comMonth,list);
					list = new ArrayList();
					}
				list.add(info);
				
			}
			map.put(Month,list);
			rs.close();
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return map;
	}
	public List getPaybillNames(){
		List pbnlist = new ArrayList();
		try{
			db.makeConnection();con = db.getConnection();
			String pbqry = "select PAYBILLCD,PAYBILLNM from PAYBILLMT   order by PAYBILLCD";
			rs = db.getRecordSet(pbqry);
			while(rs.next()){
				PayBillNmInfo pbinfo = new PayBillNmInfo();
				pbinfo.setPaybillcd(rs.getString("PAYBILLCD"));
				pbinfo.setPaybillNm(rs.getString("PAYBILLNM"));
				pbnlist.add(pbinfo);
			}
			rs.close();
			//PayBillNmInfo
		}catch(Exception e){
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return pbnlist;
	}
	public List getPaybillStationWise(String substationCd) {
		List pbnlist = new ArrayList();
		try{
			db.makeConnection();con = db.getConnection();
			String pbqry = "select PAYBILLCD,PAYBILLNM from PAYBILLMT  where PAYBILLCD is not null ";
			if(!substationCd.equals("")){
				pbqry += " and SUBSTATIONCD='"+substationCd+"'";
			}
			pbqry += " order by PAYBILLCD";
			rs = db.getRecordSet(pbqry);
			while(rs.next()){
				PayBillNmInfo pbinfo = new PayBillNmInfo();
				pbinfo.setPaybillcd(rs.getString("PAYBILLCD"));
				pbinfo.setPaybillNm(rs.getString("PAYBILLNM"));
				pbnlist.add(pbinfo);
			}
			rs.close();
			//PayBillNmInfo
		}catch(Exception e){
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return pbnlist;
	}
	public List getEmployeePerks(String empno){
		List perkslist = new ArrayList();
		try{
			db.makeConnection();con = db.getConnection();
			String getemplperk = "select ep.EARNDEDUCD,ep.amount,em.EARNDEDUNM,ep.SNO,ei.employeename,ei.emplnumber from EMPLPERKS ep,earndedumaster em,employeeinfo ei  where ep.earndeducd = em.earndeducd  and ei.empno = ep.empno and ep.empno = '"+empno+"' ";
			log.info("getemplperk "+getemplperk);
			rs = db.getRecordSet(getemplperk);
			while(rs.next()){
				EmployeeEarningInfo eei  = new EmployeeEarningInfo();
				eei.setAmount(new Double(rs.getDouble("amount")));
				eei.setEarningName(rs.getString("EARNDEDUNM"));
				eei.setEarningCd(new Integer(rs.getInt("EARNDEDUCD")));
				eei.setEmpName(rs.getString("employeename"));
				eei.setCd(rs.getString("SNO"));				
				perkslist.add(eei);
			}
			rs.close();
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return perkslist;
	}
	public void savePerks(List plist)throws ApplicationException{
		try{
			db.makeConnection();con = db.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement("update EMPLPERKS set amount=? where SNO=?");
			for(int i=0;i<plist.size();i++){
				EmployeeEarningInfo eei  = (EmployeeEarningInfo)plist.get(i);
				log.info(eei.getCd());
				log.info(eei.getAmount()+"");
				pstmt.setDouble(1,eei.getAmount().doubleValue());
				pstmt.setString(2,eei.getCd());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
			throw new ApplicationException(e.getMessage());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public List searchPerksPrcess(String payrollmonthid,String station){
		List l = new ArrayList();
		String s = "select pep.PRO_PER_CD,pep.PAYROLLMONTHID,sm.STATIONNAME,mp.PAYROLLMONTHNM,mp.PAYROLLYEAR from PROCESSED_EMPLPERKS pep,monthlypayroll mp,stationmaster sm where pep.PAYROLLMONTHID = mp.PAYROLLMONTHID and pep.STATION = sm.STATIONCD ";
		try{
			db.makeConnection();con = db.getConnection();
			rs = db.getRecordSet(s);
			while(rs.next()){
				SearchPayrollProcessInfo ppinfo = new SearchPayrollProcessInfo();
				ppinfo.setPayrollMonth(rs.getString("PAYROLLMONTHNM")+"-"+rs.getString("PAYROLLYEAR"));				
				ppinfo.setStation(rs.getString("STATIONNAME"));
				ppinfo.setPayTransId(rs.getString("PRO_PER_CD"));
				l.add(ppinfo);
			}
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
			
			
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return l;
		
	}
	public Map addtoMap(Map m,String paybillcd,String earndeducd,String amt){
		
		if(m.containsKey(paybillcd)){
			HashMap map = (HashMap)m.get(paybillcd);
			if(map.containsKey(earndeducd)){
				
				int am = Integer.parseInt(map.get(earndeducd).toString());
				int pam = Integer.parseInt(amt);
				map.put(earndeducd,new Integer(am+pam));
				
			}else{
				map.put(earndeducd,amt);
			}
			m.put(paybillcd,map);
			
		}else{
			HashMap hm = new HashMap();
			hm.put(earndeducd,amt);
			m.put(paybillcd,hm);
		}
		return m;
	}
	public void processPerks(String payrollmonthid,String station,String fyearcd)throws ApplicationException{
		
		Map jvpMap = new HashMap();
		try{
			db.makeConnection();con = db.getConnection();
			con.setAutoCommit(false);
			String chkqry = "select count(*) from PROCESSED_EMPLPERKS where payrollmonthid="+payrollmonthid+" and station='"+station+"' ";
			log.info(chkqry);
			int count = 0;
			count=db.getRecordCount(chkqry);
			log.info("-----------count-----------"+count);
			if(count !=0 ){
				
					throw new ApplicationException("Perks Process has been completed  for the selected month and station");
				
			}
				String sqlqry = " select ep.empno,ei.EMPLNUMBER,ep.EARNDEDUCD,ei.PAYBILLCD,ei.STAFFCTGRYCD ,ep.AMOUNT FROM emplperks ep,employeeinfo ei,employeepersonalinfo epi where ep.EMPNO = ei.empno and ep.EMPNO = epi.empno order by to_number(ep.empno)";
				log.info("sqlqry "+sqlqry);
				rs = db.getRecordSet(sqlqry);
				int cd  = Integer.parseInt(AutoGeneration.getNextCode("PROCESSED_EMPLPERKS","PRO_PER_CD",5,con));
				String insqry = "insert into PROCESSED_EMPLPERKS (PRO_PER_CD,PAYROLLMONTHID,station,FYEARCD) values("+cd+","+payrollmonthid+",'"+station+"','"+fyearcd+"')";
				log.info(insqry);
				PreparedStatement pstmt = con.prepareStatement("insert into PROCESSED_EMPLPERKS_DET(SNO,PRO_PER_CD,EMPNO,EARNDEDUCD,AMOUNT) values ((select nvl(max(sno),0)+1 from PROCESSED_EMPLPERKS_DET),?,?,?,?)");
				Statement stmt2 = con.createStatement();
				
				while(rs.next()){
					String eno = rs.getString("empno");
					String ecd = rs.getString("EARNDEDUCD");
					String discd = rs.getString("PAYBILLCD");
					String amt = rs.getString("AMOUNT");
					//String staffctgry = rs.getString("STAFFCTGRYCD");
					pstmt.setInt(1,cd);
					pstmt.setString(2,eno);
					pstmt.setString(3,ecd);
					pstmt.setString(4,amt);
					pstmt.addBatch();
					jvpMap = addtoMap(jvpMap,discd,ecd,amt);
				}
				PreparedStatement pstmt1 = con.prepareStatement("insert into  PROCESSED_EMPLPERKS_process(SNO,PRO_PER_CD,EARNDEDUCD,LEDGERCD,AMOUNT,PAYBILLCD,STAFFCTGRYCD) values( (select nvl(max(sno),0)+1 from PROCESSED_EMPLPERKS_process),?,?,( select ledgercd from SALHEAD_LEDG_MAPPING  where cadrecod=(select distinct staffctgrycd from employeeinfo where paybillcd=?) and earndeducd=?),?,?,(select distinct staffctgrycd from employeeinfo where paybillcd=?)");
				rs.close();
				Iterator i = jvpMap.keySet().iterator();
				
				while(i.hasNext()){
					String paypk = i.next().toString();//contains paybillcd
					HashMap emap = (HashMap)jvpMap.get(paypk);
					Iterator ei = emap.keySet().iterator();
					while(ei.hasNext()){
						String earnkey = ei.next().toString();
						String amt = emap.get(earnkey).toString();
						
						String qry = "insert into  PROCESSED_EMPLPERKS_process(SNO,PRO_PER_CD,EARNDEDUCD,LEDGERCD,AMOUNT,PAYBILLCD,STAFFCTGRYCD) values( (select nvl(max(sno),0)+1 from PROCESSED_EMPLPERKS_process),"+cd+","+earnkey+",( select ledgercd from SALHEAD_LEDG_MAPPING  where cadrecod=(select distinct staffctgrycd from employeeinfo where paybillcd="+paypk+") and earndeducd="+earnkey+"),"+amt+","+paypk+",(select distinct staffctgrycd from employeeinfo where paybillcd="+paypk+") )";
						/*pstmt1.setInt(1,cd);
						pstmt1.setString(2,earnkey);
						pstmt1.setString(3,paypk);
						pstmt1.setString(4,earnkey);
						pstmt1.setString(5,amt);
						pstmt1.setString(6,paypk);
						pstmt1.setString(7,paypk);
						pstmt1.addBatch();*/
						stmt2.addBatch(qry);
						log.info("qry "+qry);
					}
					
					
				}
				System.out.println("-----1----");
				db.executeUpdate(insqry);
				System.out.println("----2----");
				pstmt.executeBatch();
				System.out.println("-----3----");
				//pstmt1.executeBatch();
				stmt2.executeBatch();
				System.out.println("-----4----");
				con.commit();
			
		}catch (Exception e) {
			log.info("<<<<<<<<<< processPerks Exception >>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
			
			try{
				con.rollback();
			}catch(Exception e1){
				log.printStackTrace(e1);
			}
			
			throw new ApplicationException(e.getMessage());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public List viewPerksPaySlip(PaySlipInfo psinfo){
		List list = new ArrayList();
		List empPayList = new ArrayList();
		EmployeeInfo empinfo = new EmployeeInfo();
		EmpPaySlipInfo empPayInfo = new EmpPaySlipInfo();
		PaySlipInfo psinfoo = new PaySlipInfo();
		SQLHelper sq = new SQLHelper();
		try{
			db.makeConnection();con = db.getConnection();
			StringBuffer empSQL = new StringBuffer();
			empSQL.append("Select emplnumber, empNo,EMPLOYEENAME empname,get_description('upper(DESIGNATIONNM)','staffdesignation sds','sds.DESIGNATIONCD='''||emp.DESIGNATIONCD||'''')DESIGNATIONNM,get_description('STAFFCTGRYNM','staffcategory st','st.STAFFCTGRYCD='''||emp.STAFFCTGRYCD||'''')STAFFCTGRYNM,get_description('upper(DISCIPLINENM)','staffdiscipline sd','sd.DISCIPLINECD='''||emp.DISCIPLINECD||'''')DISCIPLINENM,PENSIONNO CPFNO,BACNO,emp.STAFFCTGRYCD,( select MINSCALE|| '-' ||MAXSCALE from payscalemaster pay where emp.DESIGNATIONCD=pay.DESIGNATIONCD) payscale,PAN" );				
			
			//log.info("psinfo.getPaybillcd() "+psinfo.getPaybillcd());
			if(!psinfo.getEmpNo().equals("")){
				empSQL.append(",(select to_char(PAYROLLTODT,'dd') from MONTHLYPAYROLL  where PAYROLLMONTHID  = (select distinct eolmonth from lop where empno = '"+psinfo.getEmpNo()+"' and MONTH = (SELECT mp.payrollmonthid  FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"')))maxdays,  ");
				empSQL.append("(select sum(eol)||':'||sum(hpl)||':'||sum(uaa)  from lop where empno = '"+psinfo.getEmpNo()+"' and month=(Select mp.PAYROLLMONTHID from monthlypayroll mp where PAYROLLMONTHNM='"+psinfo.getMonth()+"' and payrollyear='"+psinfo.getYear()+"'))eol ");
				empSQL.append(",(select BANKNM from BANKMASTER where BANKID = emp.BANKID)bankname");
				empSQL.append(" from  EmployeeInfo emp where emp.empno is not null ");
				empSQL.append(" and  EMPNO = '"+psinfo.getEmpNo()+"'");				
			}
			
			if(psinfo.getEmpNo()== null || psinfo.getEmpNo().equals("")){
			
				if(!psinfo.getPaybillcd().trim().equals("")){
					empSQL.append(",(select to_char(PAYROLLTODT,'dd') from MONTHLYPAYROLL  where PAYROLLMONTHID  = (select distinct eolmonth from lop where empno = emp.empno and MONTH = (SELECT mp.payrollmonthid  FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"' AND payrollyear = '"+psinfo.getYear()+"')))maxdays,  ");
					empSQL.append("(select sum(eol)||':'||sum(hpl)||':'||sum(uaa)  from lop where empno = emp.empno and month=(Select mp.PAYROLLMONTHID from monthlypayroll mp where PAYROLLMONTHNM='"+psinfo.getMonth()+"' and payrollyear='"+psinfo.getYear()+"'))eol ");
					empSQL.append(",(select BANKNM from BANKMASTER where BANKID = emp.BANKID)bankname");
					empSQL.append(" from  EmployeeInfo emp where emp.empno is not null ");
					empSQL.append(" and emp.paybillcd = '"+psinfo.getPaybillcd()+"' ");
				}
			}
			if(psinfo.getStation() !=null && !psinfo.getStation().equals("")){
				empSQL.append(" and emp.STATIONCD ='"+psinfo.getStation()+"' ");
			}
			empSQL.append("");
			if(!psinfo.getDiscipline().equals("")){
				empSQL.append(" and emp.DISCIPLINECD = '"+psinfo.getDiscipline()+"' ");
			}
			//log.info("---empSQL is "+empSQL.toString());
			//System.exit(1);
			ResultSet rss = db.getRecordSet(empSQL.toString());
			HashMap hm= new HashMap();
			while(rss.next()){				
				empPayInfo = new EmpPaySlipInfo();
				empinfo = new EmployeeInfo();
				List perkList = new ArrayList();
				empinfo.setEmpNo(rss.getString("emplnumber"));
				empinfo.setEmpName(rss.getString("empname").toUpperCase());
				empinfo.setEmpDesignation(rss.getString("DESIGNATIONNM"));
				empinfo.setStaffCategoryNm(rss.getString("STAFFCTGRYNM"));
				//empinfo.setEmpDescipline(rss.getString("DISCIPLINENM"));
				empinfo.setPan(rss.getString("PAN"));
				String eolstr = rss.getString("eol");
				int maxdays = rss.getInt("maxdays");
				empinfo.setBankId(rss.getString("bankname"));
				//log.info("eolstr "+eolstr);
				if(eolstr!=null){
					String s[] = eolstr.split(":");
					//log.info("s leng "+s.length);
					if(s.length > 2){
						empinfo.setEol(s[0]);
						empinfo.setHpl(s[1]);
						empinfo.setUaa(s[2]);
						int absdays = Integer.parseInt(s[0])+Integer.parseInt(s[1])+Integer.parseInt(s[2]);
						empinfo.setDays((maxdays-absdays)+"");						
					}else{
						empinfo.setEol("");
						empinfo.setHpl("");
						empinfo.setUaa("");
						empinfo.setDays("FULL");
					}
				}
				empinfo.setEmpDescipline("");
				empinfo.setCpfNo(rss.getString("CPFNO"));
				empinfo.setBacno(rss.getString("BACNO"));
				empinfo.setPayscale(rss.getString("payscale"));
				double grossamt = 0.0;
				double perkTotal = 0.0;	
				String sql1 = "SELECT   dt.earndeducd, dt.amount, earndedunm,printorder,mt.PAYROLLMONTHID,TYPE  FROM PROCESSED_EMPLPERKS mt,PROCESSED_EMPLPERKS_DET dt, earndedumaster edm" +
				" WHERE dt.PRO_PER_CD = mt.PRO_PER_CD   AND edm.earndeducd = dt.earndeducd " +
				"  AND mt.payrollmonthid IN (  SELECT mp.payrollmonthid FROM monthlypayroll mp WHERE payrollmonthnm = '"+psinfo.getMonth()+"'  AND payrollyear = '"+psinfo.getYear()+"') AND empno = '"+rss.getString("empNo")+"' " +
				" order by printorder ";
				//log.info("sql qry is"+sql1);
				empPayInfo.setMonth(psinfo.getMonth());
				empPayInfo.setYear(psinfo.getYear());
				//log.info("sql is "+sql1);
				String payrollmomthid = "";
				
				rs = db.getRecordSet(sql1);				
				
				//log.info(" log.info(psinfo.getType()); "+psinfo.getType());
				while(rs.next()){
			
					if(!payrollmomthid.equals("") && !payrollmomthid.equals(rs.getString("PAYROLLMONTHID"))){
						payrollmomthid = rs.getString("PAYROLLMONTHID");
						//name = rs.getString("nm");
						empPayInfo = new EmpPaySlipInfo();
						perkList = new ArrayList();
					
					}
					
					if(payrollmomthid.equals("")){
						payrollmomthid = rs.getString("PAYROLLMONTHID");
						//name = rs.getString("nm");
					}
					psinfoo = new PaySlipInfo();
					
						//psinfoo.setEarnDeduName(sh.getDescription("EARNDEDUMASTER","EARNDEDUNM","EARNDEDUCD",rs.getString("EARNDEDUCD"),con));
						//psinfoo.setShortName(sh.getDescription("EARNDEDUMASTER","SHORTNAME","EARNDEDUCD",rs.getString("EARNDEDUCD"),con));
						psinfoo.setEarnDeduName(rs.getString("earndedunm"));
						psinfoo.setShortName(rs.getString("earndedunm"));
						double amt = rs.getDouble("amount");
						
								psinfoo.setAmount(rs.getString("amount"));
						//psinfoo.setEarnDeduCd(rs.getString(rs.getString("EARNDEDUCD")));
					
					psinfoo.setType(rs.getString("TYPE"));
					perkList.add(psinfoo);
					grossamt+=amt;
					perkTotal +=amt;
				}
				
				db.closeRs();
				

				
				empPayInfo.setEmpInfo(empinfo);
				empPayInfo.setPerkList(perkList);
				//empPayInfo.setMiscperkList(miscperkList);
				empPayInfo.setPerkTotal(perkTotal);				
				empPayInfo.setGrossAmt(Double.toString(grossamt));				
				empPayInfo.setNetamtinwords(sq.ConvertInWords(new Double(empPayInfo.getNetAmt()).doubleValue()));
				empPayList.add(empPayInfo);
				list = empPayList;
				
				//log.info("psinfo.getType() "+psinfo.getType());
			//log.info("list size is "+list.size());
		} 
		}catch (Exception e) {
			log.info("<<<<<<<<<< Exception ------>>>>>>>>>>>>"+ e.getMessage());
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	
	public EmpPaySlipInfo getSalAdjEarnDedcs(String cd,String payrollmonth){
		EmpPaySlipInfo info = new EmpPaySlipInfo();
		List earnlist = new ArrayList();
		List dedlist = new ArrayList();
		List perklist = new ArrayList();
		try{
			db.makeConnection();
			con = db.getConnection();
			
			//String saQry = " select * from (select EMPNO,a.EARNDEDUCD,EARNDEDUNM,AMOUNT,TYPE from empernsdeducs a,earndedumaster b "+
						//	" where a.empno="+cd+" and a.EARNDEDUCD=b.EARNDEDUCD and (type='E' or type='D' or type='PK') order by a.EARNDEDUCD"; 
			String saQry = " select * from (select EMPNO,a.EARNDEDUCD,EARNDEDUNM,AMOUNT,TYPE from empernsdeducs a,earndedumaster b where empno="+cd+" and a.EARNDEDUCD=b.EARNDEDUCD and (type='E' or type='D' or type='PK')   union select '"+cd+"' EMPNO,earndeducd,EARNDEDUNM,'0' AMOUNT,TYPE from earndedumaster where earndeducd in (168,84,86,88) union select '"+cd+"' EMPNO,earndeducd,EARNDEDUNM,'0' AMOUNT,'E' TYPE from earndedumaster where earndeducd in (183, 138) ) order by EARNDEDUCD";
			
			String empname = sh.getDescription("employeeinfo","EMPLOYEENAME","empno",cd,con);
			//log.info("earn ded query is "+saQry);
			ResultSet rs = db.getRecordSet(saQry);			
			while(rs.next()){
				
				EmployeeEarningInfo edinfo = new EmployeeEarningInfo();
				edinfo.setEarningCd(new Integer(rs.getInt("EARNDEDUCD")));
				edinfo.setEarningName(rs.getString("EARNDEDUNM"));
				edinfo.setType(rs.getString("TYPE"));
				edinfo.setAmount(new Double(rs.getDouble("AMOUNT")));
				if(rs.getString("TYPE").equals("E")){
					earnlist.add(edinfo);	
				}					
				if(rs.getString("TYPE").equals("D")||rs.getString("TYPE").equals("OR")){
					dedlist.add(edinfo);
					//log.info("ded coe :"+rs.getInt("EARNDEDUCD")+"ded amount :"+edinfo.getAmount());
				}	
				
				if(rs.getString("TYPE").equals("PK")){
					perklist.add(edinfo);
					//log.info("ded coe :"+rs.getInt("EARNDEDUCD")+"ded amount :"+edinfo.getAmount());
				}	
			}
			String gprofqry="select GPROFAMT from emp_monthly_gprof where empno="+cd+" and PAYROLLMONTHID="+payrollmonth;
			//log.info("gprof qry:"+gprofqry);
			String earnnme=PayRollCommonDAO.getNameFromCd("earndedumaster","EARNDEDUNM","EARNDEDUCD",new Integer(11));
			ResultSet rs1 = db.getRecordSet(gprofqry);
			while(rs1.next()){
				EmployeeEarningInfo edinfo = new EmployeeEarningInfo();
				edinfo.setEarningCd(new Integer(11));
				edinfo.setEarningName(earnnme);
				edinfo.setType("GPROF");
				edinfo.setAmount(new Double(rs1.getDouble("GPROFAMT")));
				earnlist.add(edinfo);
			}
				info.setEarnList(earnlist);				
				info.setDeducList(dedlist);
				info.setPerkList(perklist);
				info.setEmpname(empname);
				
			db.closeRs();
			//log.info("Earn list size  is --3-- "+info.getEarnList().size());
			//info1.setInfo(info);
		}catch (Exception e) {
			log.info("<<<<<<<<<< getPayStopEarnDedcs() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return info;
		
	}
	
	public void addEmpSalAdjustment(EmpPaySlipInfo info) throws ApplicationException {
		String name="";
		try{
			db.makeConnection();
			con = db.getConnection();
			int cnt = 0;
			cnt = db.getRecordCount("select count(*) from payrollprocess where payrollmonthid='"+info.getPayrollmonthid()+"' and stationcd = (select stationcd from employeeinfo where empno='"+info.getEmpNo()+"')");
			if(cnt>0){
				throw new ApplicationException("Salary has been generated for the selected PayrollMonth.");
			}
			cnt = 0;
			cnt = db.getRecordCount("select count(*) from monthly_saladj where payrollmonthid='"+info.getPayrollmonthid()+"' and empno='"+info.getEmpNo()+"'");
			if(cnt>0){
				throw new ApplicationException("Amounts have been already assigned for the selected employee and PayrollMonth ");
			}
			
			db.setAutoCommit(false);
			int saladjid = Integer.parseInt(AutoGeneration.getNextCode("monthly_saladj","MSALADJCD",5,con));
			
			StringBuffer insrtQry = new StringBuffer();
			insrtQry.append("Insert into monthly_saladj(MSALADJCD,PAYROLLMONTHID,EMPNO) values('"+saladjid+"','"+info.getPayrollmonthid()+"','"+info.getEmpNo()+"')");
			//log.info("insrtQry addEmpSalAdjustment is --- "+insrtQry.toString());
			
			db.executeUpdate(insrtQry.toString());
			
			insrtQry.delete(0,insrtQry.length());
			List list = info.getEarnList();
			int size = list.size();
			//log.info("11111111...."+size);
			for(int i=0;i<size;i++){
			
				insrtQry.delete(0,insrtQry.length());
			
				PaySlipInfo pinfo=(PaySlipInfo)list.get(i);
				
				if(!pinfo.getAmount().equals("0")){
					//log.info("55555555555"+pinfo.getEarnDeduCd());
					String Quy=" Insert into monthly_saladj_det  values('"+saladjid+"','"+pinfo.getEarnDeduCd()+"','"+pinfo.getAmount()+"','"+pinfo.getArrAmount()+"','"+pinfo.getRecAmount()+"')";
					//log.info("insrtQry addEmpSalAdjustment is --- "+Quy);
					db.executeUpdate(Quy);
				}
				
			}
			
			String qry="select EMPLOYEENAME from employeeinfo where EMPNO='"+info.getEmpNo()+"'";
			rs=db.getRecordSet(qry);
			while(rs.next())
			{
				name=rs.getString("EMPLOYEENAME");
			}
			String activityDesc="Employee Salary Adjustments New Of Employee  "+name+" Saved.";
			ActivityLog.getInstance().write(info.getUsercd(), activityDesc, "N", String.valueOf(saladjid), con);
			db.commitTrans();
		}catch (Exception e) {
			try {
				db.rollbackTrans();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("<<<<<<<<<< addEmpSalAdjustment() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
			throw new ApplicationException(e.toString());
			
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public List searchEmpSalAdjustment(String cd) {
		List list = new ArrayList();
		PaySlipInfo info = new PaySlipInfo();
		try{
			db.makeConnection();
			con = db.getConnection();
			//log.info("emp in search:"+cd);
			String qry = " select MSALADJCD,EMPLOYEENAME,e.EMPNO,emp.EMPLNUMBER,e.PAYROLLMONTHID,(PAYROLLMONTHNM||'-'||PAYROLLYEAR)PAYROLLMONTHNAME,(select count(*) From payrollprocess p where p.PAYROLLMONTHID=e.payrollmonthid)cnt from monthly_saladj e,employeeinfo emp,monthlypayroll mp where e.empno is not null and e.empno=emp.empno and e.payrollmonthid=mp.payrollmonthid ";
			if(!"".equals(cd))
				qry += " and e.empno='"+cd+"'";
			//log.info("search query :"+qry);
			rs = db.getRecordSet(qry);
			while(rs.next()){
				info = new PaySlipInfo();
				info.setEmpsaladjcd(rs.getString("MSALADJCD"));
				info.setEmpNo(rs.getString("EMPNO"));
				info.setEmplnumber(rs.getString("EMPLNUMBER"));
				info.setEmpName(rs.getString("EMPLOYEENAME"));
				info.setPayrollmonthid(rs.getString("PAYROLLMONTHID"));	
				info.setPayrollMonthName(rs.getString("PAYROLLMONTHNAME"));
				if(rs.getInt("cnt")==0)
					info.setType("Y");
				else
					info.setType("N");
				list.add(info);
			}
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< searchEmpSalAdjustment() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	public EmpPaySlipInfo findByEmpSalAdjCd(String id,String cd,String payrollmonthid){
		EmpPaySlipInfo epsinfo = new EmpPaySlipInfo();
		
		List el = new ArrayList();
		List dl = new ArrayList();	
		List pl = new ArrayList();	
		String saladjcd = "";
		try{
			db.makeConnection();
			con = db.getConnection();
			StringBuffer qry = new StringBuffer();
			String payrollmonth="";
			String psQry = " select * from (select EMPNO,a.EARNDEDUCD,EARNDEDUNM,AMOUNT,TYPE from empernsdeducs a,earndedumaster b where empno="+cd+" and a.EARNDEDUCD=b.EARNDEDUCD and (type='E' or type='D' or type='PK')   union select '"+cd+"' EMPNO,earndeducd,EARNDEDUNM,'0' AMOUNT,TYPE from earndedumaster where earndeducd in (168,84,86,88) ) order by EARNDEDUCD"; 
			String empname = sh.getDescription("employeeinfo","EMPLOYEENAME","empno",cd,con);
			
			
			//log.info("earn ded query is "+psQry);
			ResultSet rs1 = db.getRecordSet(psQry);
			while(rs1.next()){
				//log.info("inside while rs1");
				qry.delete(0,qry.length());
				epsinfo.setEmpNo(rs1.getString("EMPNO"));
				epsinfo.setEmpname(empname);
				PaySlipInfo psinfo = new PaySlipInfo();				
				psinfo.setEarnDeduName(rs1.getString("EARNDEDUNM"));
				psinfo.setEarnDeduCd(rs1.getString("EARNDEDUCD"));	
				psinfo.setType(rs1.getString("TYPE"));
				psinfo.setActualAmount(rs1.getString("AMOUNT"));
				qry.append(" select esa.MSALADJCD,esadet.EARNDEDUCD,esadet.ADJAMT , esadet.ARREARS, esadet.RECOVERIES," +
					 " esa.PAYROLLMONTHID,mp.PAYROLLMONTHNM || '-' || mp.PAYROLLYEAR paymonth "+
					" from monthly_saladj esa,monthly_saladj_det esadet,monthlypayroll mp" +
					" where esa.MSALADJCD = esadet.MSALADJCD and esa.PAYROLLMONTHID = mp.PAYROLLMONTHID  " +
					"  and esa.MSALADJCD = "+id+" and esadet.EARNDEDUCD="+rs1.getString("EARNDEDUCD")+" order by esadet.EARNDEDUCD");
				//log.info("findByEmpSalAdjCd qry:"+qry.toString());
				rs = db.getRecordSet(qry.toString());
				if(rs.next()){
					payrollmonth=rs.getString("PAYROLLMONTHID");
					epsinfo.setPayrollmonthid(rs.getString("paymonth"));
					psinfo.setAmount(rs.getString("ADJAMT"));
					psinfo.setArrAmount(rs.getString("ARREARS"));
					psinfo.setRecAmount(rs.getString("RECOVERIES"));
					saladjcd=rs.getString("MSALADJCD");
				}
				else{
					psinfo.setAmount("0");
					psinfo.setArrAmount("0");
					psinfo.setRecAmount("0");
				}
				if(psinfo.getType().equals("E"))
					el.add(psinfo);
				
				if(psinfo.getType().equals("D") || psinfo.getType().equals("OR"))
					dl.add(psinfo);
				
				if(psinfo.getType().equals("PK"))
					pl.add(psinfo);
								
			}	
			rs.close();
			
				qry.delete(0,qry.length());
				qry.append(" select esa.MSALADJCD,esadet.EARNDEDUCD,esadet.ADJAMT , esadet.ARREARS ,esadet.RECOVERIES ," +
					 " esa.PAYROLLMONTHID,mp.PAYROLLMONTHNM || '-' || mp.PAYROLLYEAR paymonth "+
					" from monthly_saladj esa,monthly_saladj_det esadet,monthlypayroll mp" +
					" where esa.MSALADJCD = esadet.MSALADJCD and esa.PAYROLLMONTHID = mp.PAYROLLMONTHID  " +
					"  and esa.MSALADJCD = "+id+" and esadet.EARNDEDUCD=11  order by esadet.EARNDEDUCD");
				//log.info("findByEmpSalAdjCd qry  1111111    :"+qry.toString());
				rs=db.getRecordSet(qry.toString());					
				PaySlipInfo psinfo = new PaySlipInfo();	
				if(rs.next()){
					psinfo.setAmount(rs.getString("ADJAMT"));	
					psinfo.setArrAmount(rs.getString("ARREARS"));	
					psinfo.setRecAmount(rs.getString("RECOVERIES"));	
					payrollmonth=rs.getString("PAYROLLMONTHID");
					saladjcd=rs.getString("MSALADJCD");
				}
				else{
					psinfo.setAmount("0");
					//psinfo.setArrAmount("0");
					//psinfo.setRecAmount("0"); 
				}
				String gprofqry="select GPROFAMT from emp_monthly_gprof where empno="+cd ; 
				if(!"".equals(payrollmonthid))		
					gprofqry+=" and PAYROLLMONTHID="+payrollmonthid;
				//log.info("gprof qry:"+gprofqry);
				String earnnme=PayRollCommonDAO.getNameFromCd("earndedumaster","EARNDEDUNM","EARNDEDUCD",new Integer(11));
				ResultSet rs2 = db.getRecordSet(gprofqry);
				while(rs2.next()){					
					psinfo.setEarnDeduName(earnnme);
					psinfo.setEarnDeduCd("11");	
					psinfo.setType("GPROF");
					psinfo.setActualAmount(rs2.getString("GPROFAMT"));
					el.add(psinfo);
				}
				
			//log.info("saladjcd         @@@@     "+saladjcd);
			epsinfo.setEarnList(el);
			epsinfo.setDeducList(dl);
			epsinfo.setPerkList(pl);
			epsinfo.setEmpsaladjcd(id);
			//log.info("el size "+el.size());
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< findByEmpSalAdjCd() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		return epsinfo;
	}
	public void updateEmpSalAdjustment(List l,String usercd,String empname,String empno) throws ApplicationException,Exception {
		try{
			int cnt=0; 
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			String qry="";
			String saladjcd = "";	
			
				for(int i=0;i<l.size();i++){
					PaySlipInfo info = (PaySlipInfo)l.get(i);
					cnt = db.getRecordCount("select count(*) from monthly_saladj_det where MSALADJCD='"+info.getEmpsaladjcd()+"' and EARNDEDUCD='"+info.getEarnDeduCd()+"'");
					if(cnt>0){
						 qry = "update monthly_saladj_det set ADJAMT = '"+info.getAmount()+"',ARREARS = '"+info.getArrAmount()+"',RECOVERIES = '"+info.getRecAmount()+"' where EARNDEDUCD='"+info.getEarnDeduCd()+"' and MSALADJCD='"+info.getEmpsaladjcd()+"'";
						 log.info("updateEmpSalAdjustment qry: "+qry);
						 db.executeUpdate(qry);
					}
					else{
						if(!info.getAmount().equals("0")){
							qry="insert into monthly_saladj_det values('"+info.getEmpsaladjcd()+"','"+info.getEarnDeduCd()+"','"+info.getAmount()+"','"+info.getArrAmount()+"','"+info.getRecAmount()+"')";  
							log.info("updateEmpSalAdjustment qry: "+qry);
							db.executeUpdate(qry);
						}
					}
						
					
					saladjcd = info.getEmpsaladjcd();
				}		
				String activityDesc="Employee Salary Adjustments Of "+empname+" Updated.";
			ActivityLog.getInstance().write(usercd, activityDesc, "E", empno, con);
			db.commitTrans();
			
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< updateEmpSalAdjustment() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		
		
	}
  public void earnDedCalc() throws ApplicationException{
	  try{
	String 	earncd=""; 
	  db.makeConnection();
	  con = db.getConnection();
	  ResultSet rs = db
		.getRecordSet("select earndeducd from earndedumaster where isbasicsalary='Y'");
	  if(rs.next()){
		  earncd=rs.getString("earndeducd");
	  }
	  rs = db
		.getRecordSet("select EMPNO from employeeinfo");
	  while(rs.next()){
		  String empno=rs.getString("EMPNO");
		  String edcd = getEarnDeductions(empno,db);
		   	String command = "{call modsalhead(?,?,?)}";     // 2 placeholders
			CallableStatement cstmt = con.prepareCall (command);
			StringTokenizer token = new StringTokenizer(edcd,"|");
			while(token.hasMoreTokens()){
				String str = token.nextToken();
			cstmt.setString(1, earncd);
			cstmt.setString(2, empno);  
			cstmt.setString(3, str);
			
			log.info(empno+"---------"+earncd+"-----2---------"+str);
			cstmt.addBatch();
			}
			cstmt.executeBatch();
			cstmt.close();
			con.commit();
	  }
	  }catch (Exception e) {
			
			log.info("<<<<<<<<<< updateEmpSalAdjustment() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
  }

	public String getEarnDeductions(String empno,DBAccess db ) throws ApplicationException{
		
		List list = new ArrayList();
		String earndedcd = "";
	 try{	
		// db.makeConnection();
		con = db.getConnection();
			db.setAutoCommit(false);
		ResultSet rs = db
		.getRecordSet("select EARNDEDUCD,DEPENDSON from SALHEAD_LEDG_MAPPING where instr(',' || DEPENDSON || ',', ',' || '1' || ',') >= 1 and cadrecod = (select cadrecod from employeeinfo where empno = '"+empno+"') and TYPE in ('E', 'D')");
	while (rs.next()) {
		list.add(rs.getString(1) + "|" + rs.getString(2));
	}
	
	for (int i = 0; i < list.size(); i++){
		boolean bool = true;
		String dep = (String)list.get(i);
		String[] dependsOn = dep.substring(dep.indexOf("|")+1).split("\\,");
		dep = dep.substring(0,dep.indexOf("|"));
		for (int j = i+1; j < list.size(); j++){					
			String comp = (String)list.get(j);										
			for (int k = 0; k < dependsOn.length; k++){						
				if(comp.startsWith(dependsOn[k]+"|")){
					bool = false;
					earndedcd+=dependsOn[k]+"|";
				}						
			}
		}
		if(earndedcd.indexOf(dep) < 0)
			earndedcd += dep+"|";
		    
	}
		
	}catch (Exception e) {
		
		log.info("<<<<<<<<<< updateEmpSalAdjustment() Exception ------>>>>>>>>>>>>");
		log.printStackTrace(e);
		throw new ApplicationException(e.getMessage());
	} finally {
		try {
			//db.closeCon();
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}	
	return earndedcd;
	}
	public List getQuarterType() {
		List list = new ArrayList();
		EmployeeInfo info = null;
		try{
			db.makeConnection();
			con = db.getConnection();
			
			String qry = "select * from quartertype_master";
			
			log.info("search query :"+qry);
			rs = db.getRecordSet(qry);
			while(rs.next()){
				info =  new EmployeeInfo();
				info.setQuarterType(rs.getString("QUATERTYPE"));
				list.add(info);
			}
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< searchEmpSalAdjustment() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	public EmployeeInfo getWaterRec(String quarter) {
		EmployeeInfo info = null;
		try{
			db.makeConnection();
			con = db.getConnection();
			
			String waterrecQry =" select * from QUARTERTYPE_MASTER where QUATERTYPE = '"+quarter+"'";
			rs=db.getRecordSet(waterrecQry);
			log.info("search query :"+waterrecQry);
			if(rs.next()){
				info =  new EmployeeInfo();
				info.setWater(rs.getString("WATER"));
				info.setCons(rs.getString("CONS"));
				info.setHrr(rs.getString("HRR"));
			}
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< searchEmpSalAdjustment() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return info;
	}
	public List getMonthsNotAssigned() {
	List monthlist = new ArrayList();
		
		try{
			db.makeConnection();
			con = db.getConnection();
			String monthsqry = " select fym.*,decode(nvl(tax.EMPINCOMETAXCD,''),'','N','Y')defined from (select mp.payrollmonthid,mp.PAYROLLMONTHNM || '-' ||mp.PAYROLLYEAR PAYROLLMONTHNM "+
					" from monthlypayroll mp ,financialyear fy  " +
					" where fy.STATUS = 'A' and mp.FYEARCD = fy.FYEARCD) fym , empincometax tax  " +
					" where fym.payrollmonthid = tax.payrollmonthid(+)  order by fym.payrollmonthid " +
					
					"";
			log.info("getMonthsNotAssigned:: "+monthsqry);
			rs = db.getRecordSet(monthsqry);
			while(rs.next()){
				MonthlyPayrollInfo mpinfo = new MonthlyPayrollInfo();
				mpinfo.setPayrollmonthid(rs.getInt("payrollmonthid"));
				mpinfo.setPayrollmonthnm(rs.getString("PAYROLLMONTHNM"));
				mpinfo.setDefinedMonthlyIT(rs.getString("defined"));
				monthlist.add(mpinfo);
			}
			
			
		}catch(Exception e){
			log.info("getMonthsNotAssigned exception::");
			log.printStackTrace(e);		
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				log.printStackTrace(e1);
			}
		}
		return monthlist;		
	}
	
	
	
	public List getMonths(String fyrcd) {
		List monthlist = new ArrayList();
		MonthlyPayrollInfo mpinfo=null;
			
			try{
				db.makeConnection();
				con = db.getConnection();
				String monthsqry = " select PAYROLLMONTHID,PAYROLLMONTHNM || '-' ||PAYROLLYEAR PAYROLLMONTHNM from monthlypayroll   " +
				" where FYEARCD='" + fyrcd + "' and PAYROLLMONTHID not in (select PAYROLLMONTHID from payrollprocess)";
				
				log.info("getMonthsNotAssigned:: "+monthsqry);
				rs = db.getRecordSet(monthsqry);
				while(rs.next()){
					mpinfo= new MonthlyPayrollInfo();
					
					mpinfo.setPayrollmonthid(rs.getInt("payrollmonthid"));
					log.info("mpinfo.setPayrollmonthid"+mpinfo.getPayrollmonthid());
					mpinfo.setPayrollmonthnm(rs.getString("PAYROLLMONTHNM"));
					log.info("mpinfo.setPayrollmonthid"+mpinfo.getPayrollmonthnm());
					monthlist.add(mpinfo);
				}
				
				
			}catch(Exception e){
				log.info("getMonthsNotAssigned exception::");
				log.printStackTrace(e);		
			}finally {
				try {
					db.closeCon();
				} catch (Exception e1) {
					log.printStackTrace(e1);
				}
			}
			return monthlist;		
		}
	public void reAssignIT(ReAssignGprofInfo rgi) throws ApplicationException {
		try{
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			int taxcd = Integer.parseInt(AutoGeneration.getNextCode("empincometax","EMPINCOMETAXCD",5,con));
			String msqry = "insert into empincometax (EMPINCOMETAXCD,PAYROLLMONTHID,USERCD) values('"+taxcd+"','"+rgi.getTomonth()+"','"+rgi.getUserid()+"')";
			log.info("reAssignIT() msqry "+msqry);
			db.executeUpdate(msqry);
			String selqry = " select * from empincometaxdet where EMPINCOMETAXCD = (select EMPINCOMETAXCD from empincometax where PAYROLLMONTHID = '"+rgi.getFrmmonth()+"')  ";
			log.info("selqry "+selqry);
			rs = db.getRecordSet(selqry);
			while(rs.next()){
				int taxcd1 = Integer.parseInt(AutoGeneration.getNextCode("empincometaxdet","EMPINCOMETAXDETCD",5,con));
				String itaxcd = rs.getString("EMPINCOMETAXCD");
				String empno = rs.getString("EMPNO");
				String incometax = rs.getString("INCOMETAX");	
				String status = rs.getString("STATUS");	
				String ptax = rs.getString("PTAX");	
				String detqry = "insert into empincometaxdet(EMPINCOMETAXDETCD,EMPINCOMETAXCD,EMPNO,INCOMETAX,PAYROLLMONTHID,STATUS,PTAX) values('"+taxcd1+"','"+itaxcd+"','"+empno+"',"+incometax+",'"+rgi.getTomonth()+"','"+status+"',"+ptax+")";
				log.info("detqry: "+detqry);
				db.executeUpdate(detqry);
			}
			db.commitTrans();
		}catch(Exception e){
			log.printStackTrace(e);
			try{
				con.rollback();
				db.rollbackTrans();
				
			}catch(Exception e1){
				log.printStackTrace(e1);
			}
			throw new ApplicationException(e.getMessage());
		}
	}
	public List searchEmpSalTrack(String cd) {
		List list = new ArrayList();
		PaySlipInfo info = new PaySlipInfo();
		try{
			db.makeConnection();
			con = db.getConnection();
			log.info("emp in search:"+cd);
			String qry = " select MSALTRKCD,EMPLOYEENAME,e.EMPNO,emp.EMPLNUMBER,(PAYROLLMONTHNM||'-'||PAYROLLYEAR)PAYROLLMONTHID,(select count(*) From payrollprocess p where p.PAYROLLMONTHID=e.payrollmonthid)cnt from monthly_saltrack e,employeeinfo emp,monthlypayroll mp where e.empno is not null and e.empno=emp.empno and e.payrollmonthid=mp.payrollmonthid ";
			if(!"".equals(cd))
				qry += " and e.empno='"+cd+"'";
			log.info("search query :"+qry);
			rs = db.getRecordSet(qry);
			while(rs.next()){
				info = new PaySlipInfo();
				info.setEmpsaladjcd(rs.getString("MSALTRKCD"));
				info.setEmpNo(rs.getString("EMPNO"));
				info.setEmplnumber(rs.getString("EMPLNUMBER"));
				info.setEmpName(rs.getString("EMPLOYEENAME"));
				info.setPayrollmonthid(rs.getString("PAYROLLMONTHID"));	
				if(rs.getInt("cnt")==0)
					info.setType("Y");
				else
					info.setType("N");
				list.add(info);
			}
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< searchEmpSalAdjustment() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	public void updateEmpSalTrack(List l,String usercd,String empname,String empno) throws ApplicationException {
		try{
			int cnt=0; 
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			String qry="";
			String saladjcd = "";	
			
				for(int i=0;i<l.size();i++){
					PaySlipInfo info = (PaySlipInfo)l.get(i);
					cnt = db.getRecordCount("select count(*) from monthly_saltrack_det where MSALTRKCD='"+info.getEmpsaladjcd()+"' and EARNDEDUCD='"+info.getEarnDeduCd()+"'");
					if(cnt>0){
						 qry = "update monthly_saltrack_det set EARNDEDAMT='"+info.getEarnamount()+"',TRKAMT = '"+info.getAmount()+"' where EARNDEDUCD='"+info.getEarnDeduCd()+"' and MSALTRKCD='"+info.getEmpsaladjcd()+"'";
						 log.info("updateEmpSalTrack qry: "+qry);
						 db.executeUpdate(qry);
					}
					else{
						if(!info.getAmount().equals("0")||!info.getEarnamount().equals("0")){
							qry="insert into monthly_saltrack_det values('"+info.getEmpsaladjcd()+"','"+info.getEarnDeduCd()+"','"+info.getAmount()+"','"+info.getEarnamount()+"')";
							log.info("updateEmpSalTrack qry: "+qry);
							db.executeUpdate(qry);
						}
					}
						
					
					saladjcd = info.getEmpsaladjcd();
				}		
				String activityDesc="Employee Salary Tracking Of "+empname+" Updated.";
			ActivityLog.getInstance().write(usercd, activityDesc, "E", empno, con);
			db.commitTrans();
			
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< updateEmpSalTrack() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		
	}
	public void addEmpSalTrack(EmpPaySlipInfo info) throws ApplicationException {
		String name="";
		try{
			db.makeConnection();
			con = db.getConnection();
			int cnt = 0;
			cnt = db.getRecordCount("select count(*) from empinfopayroll where payrollmonthid='"+info.getPayrollmonthid()+"' and stationcd = (select stationcd from employeeinfo where empno='"+info.getEmpNo()+"') and empno='"+info.getEmpNo()+"'");
			if(cnt>0){
				throw new ApplicationException("Salary has been generated for the selected PayrollMonth.");
			}
			cnt = 0;
			cnt = db.getRecordCount("select count(*) from monthly_saltrack where payrollmonthid='"+info.getPayrollmonthid()+"' and empno='"+info.getEmpNo()+"'");
			if(cnt>0){
				throw new ApplicationException("Amounts have been already assigned for the selected employee and PayrollMonth ");
			}
			
			db.setAutoCommit(false);
			int saltrkid = Integer.parseInt(AutoGeneration.getNextCode("monthly_saltrack","MSALTRKCD",5,con));
			
			StringBuffer insrtQry = new StringBuffer();
			insrtQry.append("Insert into monthly_saltrack(MSALTRKCD,PAYROLLMONTHID,EMPNO) values('"+saltrkid+"','"+info.getPayrollmonthid()+"','"+info.getEmpNo()+"')");
			log.info("insrtQry addEmpSalTrack is --- "+insrtQry.toString());
			
			db.executeUpdate(insrtQry.toString());
			insrtQry.delete(0,insrtQry.length());
			List list = info.getEarnList();
			int size = list.size();
			for(int i=0;i<size;i++){
				insrtQry.delete(0,insrtQry.length());
				PaySlipInfo pinfo=(PaySlipInfo)list.get(i);
				if(!pinfo.getEarnamount().equals("0")||!pinfo.getAmount().equals("0")){
					insrtQry.append(" Insert into monthly_saltrack_det  values('"+saltrkid+"','"+pinfo.getEarnDeduCd()+"','"+pinfo.getAmount()+"','"+pinfo.getEarnamount()+"') ");
					log.info("insrtQry addEmpSalTrack det is --- "+insrtQry.toString());
					db.executeUpdate(insrtQry.toString());
				}
			}
			String qry="select EMPLOYEENAME from employeeinfo where EMPNO='"+info.getEmpNo()+"'";
			rs=db.getRecordSet(qry);
			while(rs.next())
			{
				name=rs.getString("EMPLOYEENAME");
			}
			String activityDesc="Employee Salary Tracking New Of Employee  "+name+" Saved.";
			ActivityLog.getInstance().write(info.getUsercd(), activityDesc, "N", String.valueOf(saltrkid), con);
			db.commitTrans();
		}catch (Exception e) {
			try {
				db.rollbackTrans();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("<<<<<<<<<< addEmpSalTrack() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
			throw new ApplicationException(e.toString());
			
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public EmpPaySlipInfo findByEmpTrkAdjCd(String id, String cd) {
EmpPaySlipInfo epsinfo = new EmpPaySlipInfo();
		
		List el = new ArrayList();
		List dl = new ArrayList();	
		List pl = new ArrayList();	
		String saltrkcd = "";
		try{
			db.makeConnection();
			con = db.getConnection();
			StringBuffer qry = new StringBuffer();
			String payrollmonth="";
			String psQry = " select EMPNO,a.EARNDEDUCD,EARNDEDUNM,TYPE from empernsdeducs a,earndedumaster b where empno="+cd+" and a.EARNDEDUCD=b.EARNDEDUCD and (type='E' or type='D' or type='PK') order by a.EARNDEDUCD"; 
			String empname = sh.getDescription("employeeinfo","EMPLOYEENAME","empno",cd,con);
			log.info("earn ded query is "+psQry);
			ResultSet rs1 = db.getRecordSet(psQry);
			while(rs1.next()){
				log.info("inside while rs1");
				qry.delete(0,qry.length());
				epsinfo.setEmpNo(rs1.getString("EMPNO"));
				epsinfo.setEmpname(empname);
				PaySlipInfo psinfo = new PaySlipInfo();				
				psinfo.setEarnDeduName(rs1.getString("EARNDEDUNM"));
				psinfo.setEarnDeduCd(rs1.getString("EARNDEDUCD"));	
				psinfo.setType(rs1.getString("TYPE"));
				qry.append(" select esa.MSALTRKCD,esadet.EARNDEDUCD,esadet.TRKAMT,esadet.EARNDEDAMT , " +
					 " esa.PAYROLLMONTHID,mp.PAYROLLMONTHNM || '-' || mp.PAYROLLYEAR paymonth "+
					" from monthly_saltrack esa,monthly_saltrack_det esadet,monthlypayroll mp" +
					" where esa.MSALTRKCD = esadet.MSALTRKCD and esa.PAYROLLMONTHID = mp.PAYROLLMONTHID  " +
					"  and esa.MSALTRKCD = "+id+" and esadet.EARNDEDUCD="+rs1.getString("EARNDEDUCD")+" order by esadet.EARNDEDUCD");
				log.info("findByEmpTrkAdjCd qry:"+qry.toString());
				rs = db.getRecordSet(qry.toString());
				if(rs.next()){
					payrollmonth=rs.getString("PAYROLLMONTHID");
					epsinfo.setPayrollmonthid(rs.getString("paymonth"));
					psinfo.setAmount(rs.getString("TRKAMT"));
					psinfo.setEarnamount(rs.getString("EARNDEDAMT"));
					saltrkcd=rs.getString("MSALTRKCD");
				}
				else{
					psinfo.setEarnamount("0");
					psinfo.setAmount("0");
				}
				if(psinfo.getType().equals("E"))
					el.add(psinfo);
				
				if(psinfo.getType().equals("D"))
					dl.add(psinfo);
				
				if(psinfo.getType().equals("PK"))
					pl.add(psinfo);
								
			}	
			rs.close();
			
				qry.delete(0,qry.length());
				qry.append(" select esa.MSALTRKCD,esadet.EARNDEDUCD,esadet.EARNDEDAMT,esadet.TRKAMT, " +
					 " esa.PAYROLLMONTHID,mp.PAYROLLMONTHNM || '-' || mp.PAYROLLYEAR paymonth "+
					" from monthly_saltrack esa,monthly_saltrack_det esadet,monthlypayroll mp" +
					" where esa.MSALTRKCD = esadet.MSALTRKCD and esa.PAYROLLMONTHID = mp.PAYROLLMONTHID  " +
					"  and esa.MSALTRKCD = "+id+" and esadet.EARNDEDUCD=11  order by esadet.EARNDEDUCD");
				rs=db.getRecordSet(qry.toString());					
				PaySlipInfo psinfo = new PaySlipInfo();	
				if(rs.next()){
					psinfo.setAmount(rs.getString("TRKAMT"));
					psinfo.setEarnamount(rs.getString("EARNDEDAMT"));
					payrollmonth=rs.getString("PAYROLLMONTHID");
					saltrkcd=rs.getString("MSALTRKCD");
				}
				else{
					psinfo.setEarnamount("0");
					psinfo.setAmount("0");
					
				}
				String gprofqry="select GPROFAMT from emp_monthly_gprof where empno="+cd ;
				if(!"".equals(payrollmonth))		
					gprofqry+=" and PAYROLLMONTHID="+payrollmonth;
				log.info("gprof qry:"+gprofqry);
				String earnnme=PayRollCommonDAO.getNameFromCd("earndedumaster","EARNDEDUNM","EARNDEDUCD",new Integer(11));
				ResultSet rs2 = db.getRecordSet(gprofqry);
				while(rs2.next()){					
					psinfo.setEarnDeduName(earnnme);
					psinfo.setEarnDeduCd("11");	
					psinfo.setType("GPROF");
					psinfo.setActualAmount(rs2.getString("GPROFAMT"));
					el.add(psinfo);
				}
				
			
			epsinfo.setEarnList(el);
			epsinfo.setDeducList(dl);
			epsinfo.setPerkList(pl);
			epsinfo.setEmpsaladjcd(saltrkcd);
			log.info("el size "+el.size());
		}catch (Exception e) {
			
			log.info("<<<<<<<<<< findByEmpTrkAdjCd() Exception ------>>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		return epsinfo;
	}
	public List getMonthsNotAssigned(String fyearcd) {
List monthlist = new ArrayList();
		
		try{
			db.makeConnection();
			con = db.getConnection();
			String monthsqry = " select fym.*,decode(nvl(tax.EMPINCOMETAXCD,''),'','N','Y')defined from (select mp.payrollmonthid,mp.PAYROLLMONTHNM || '-' ||mp.PAYROLLYEAR PAYROLLMONTHNM "+
					" from monthlypayroll mp ,financialyear fy  " +
					" where fy.STATUS = 'A' and mp.FYEARCD = fy.FYEARCD and fy.FYEARCD="+fyearcd+" ) fym , empincometax tax  " +
					" where fym.payrollmonthid = tax.payrollmonthid(+)  order by fym.payrollmonthid " +
					
					"";
			log.info("getMonthsNotAssigned:: "+monthsqry);
			rs = db.getRecordSet(monthsqry);
			while(rs.next()){
				MonthlyPayrollInfo mpinfo = new MonthlyPayrollInfo();
				mpinfo.setPayrollmonthid(rs.getInt("payrollmonthid"));
				mpinfo.setPayrollmonthnm(rs.getString("PAYROLLMONTHNM"));
				mpinfo.setDefinedMonthlyIT(rs.getString("defined"));
				monthlist.add(mpinfo);
			}
			
			
		}catch(Exception e){
			log.info("getMonthsNotAssigned exception::");
			log.printStackTrace(e);		
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				log.printStackTrace(e1);
			}
		}
		return monthlist;		
	}
	public ArrayList getPersonalInfo(String type)
	{
		EmployeeInfo info=new EmployeeInfo();
		UtilityBean bean=null;
		ArrayList arl=new ArrayList();
		try
		{
			//db.makeConnection();
			//con=db.getConnection();
			//String qry = "";
			if(type.equals("EMPLOYEEINFO"))
			{
				//qry = "select  COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = upper('"+type+"')";
				//qry += " and column_name not in ('STATUS','LASTUPDATEDDT','CREATIONDATE','UPDA','SCODE','PETOPTION','PAYBILLCD') ";
				//qry += " order by COLUMN_NAME ";
				arl.add("EMPNO");
				//arl.add("EMPLNUMBER");
				arl.add("EMPLOYEENAME");
				arl.add("CPFNO");
				arl.add("PENSIONNO");
				arl.add("PAN");
				arl.add("BANKNAME");
				arl.add("PAYMENTTYPE");
				arl.add("CADRE");
				arl.add("DESIGNATION");
				arl.add("GENDER");
				//arl.add("EMPLOYEMENTTYPE");
				arl.add("HRAOPTION");
				//arl.add("GROUPCD");
				arl.add("STAFFCTGRY");
				arl.add("STAFFTYPE");
				arl.add("DISCIPLINE");
				arl.add("BANKACCOUNTNO");
				//arl.add("REGIONCD");
				arl.add("STATIONCD");
				//arl.add("PENSIONOPTION");
				arl.add("PFTAXOPTION");
				arl.add("PETROL");
				arl.add("PFTRUST");
				//arl.add("CADRECOD");
				//arl.add("GSLISCADRECD");
				//arl.add("QRTROPT");
				arl.add("PAYBILL");
				arl.add("QUATERTYPE");
			}
			else if(type.equals("employeepersonalinfo"))
			{
				//qry = "select  COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME=upper('"+type+"')";
				//qry += " and column_name not in () ";
				arl.add("EMPNO");
				arl.add("EMPDOB");
				arl.add("EMPDOJ");
				arl.add("MARITALSTAT");
				arl.add("MARRIAGEDT");
				arl.add("NATIONALITY");
				arl.add("RELIGION");
				arl.add("RETIREFRMPENSION");
				arl.add("PERMENENTADDRESS");
				arl.add("CITY");
				arl.add("STATE");
				arl.add("ZIPCD");
				arl.add("CURRENTADDRESS");
				arl.add("CITY1");
				arl.add("STATE1");
				arl.add("ZIPCD1");
				arl.add("MAILID");
				arl.add("CONTACTNO1");
				arl.add("CONTACTNO2");
				
			}
			else if(type.equals("empernsdeducs"))
			{
				//qry = "select  COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME=upper('"+type+"')";
				//qry += " and column_name not in () ";
				arl.add("EMPNO");
				arl.add("EARNDEDU");
				arl.add("CREATIONDATE");
				arl.add("AMOUNT");
				//arl.add("STATUS");
			}
			//rs=db.getRecordSet(qry);
			log.info("<<<<<<<<<<<<<<---------Type is----------->>>>>>>>>>>>"+type);
			//while(rs.next())
			//{
				//bean=new UtilityBean();
				//arl.add(rs.getString("COLUMN_NAME"));
				//bean.setValue(rs.getString("COLUMN_NAME"));
				//arl.add(bean);
			//}
			//for(int i=0;i<arl.size();i++)
			//{
				//System.out.println("ArrayList objects are---->>>>>"+arl.get(i));
			//}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.info("<<<<<<<<<<<<<<---------Exception in getPersonalInfo----------->>>>>>>>>>>>");
			log.printStackTrace(e);	
		}
		finally
		{
			//try
			//{
				//db.closeCon();
			//}
			//catch(Exception e1)
			//{
				//log.printStackTrace(e1);
			//}
		}
		return arl;
	}
	
	public List getReportValues(String values,String type,String no)
	{
		List list=new ArrayList();
		String str="";
		String str1="";
		String field1 = "";
		try
		{
			db.makeConnection();
			con=db.getConnection();
			UtilityBean bean=null;
			str1+="select ";
			StringTokenizer st=new StringTokenizer(values,"$");
			while(st.hasMoreTokens())
			{
				//str1+=st.nextToken()+",";
				field1 = st.nextToken();
				if(field1.equals("EMPNO"))
				{
					field1 = " n.EMPLNUMBER EMPNO";
				}
				if(field1.equals("BANKNAME"))
				{
					field1 = " (select BANKNM from bankmaster b where b.BANKID = e.BANKID) BANKID";
				}
				if(field1.equals("PAYMENTTYPE"))
				{
					field1 = " decode(e.PAYMENTTYPE,'1','Bank','0','Cash') PAYMENTTYPE";
				}
				if(field1.equals("GENDER"))
				{
					field1 = " decode(e.GENDER,'M','Male','F','Female') GENDER";
				}
				if(field1.equals("HRAOPTION"))
				{
					field1 = " decode(e.HRAOPTION,'H','HRA','Q','Quarters','L','Lease') HRAOPTION";
				}
				if(field1.equals("PFTAXOPTION"))
				{
					field1 = " decode(e.PFTAXOPTION,'0','PFTAX','1','No PF Tax') PFTAXOPTION";
				}
				if(field1.equals("STAFFTYPE"))
				{
					field1 = " (select STAFFTYPENM from stafftype b where b.STAFFTYPECD = e.STAFFTYPECD) STAFFTYPECD";
				}
				if(field1.equals("DESIGNATION"))
				{
					field1 = " (select DESIGNATIONNM from staffdesignation b where b.DESIGNATIONCD = e.DESIGNATIONCD) DESIGNATIONCD";
				}
				if(field1.equals("DISCIPLINE"))
				{
					field1 = " (select DISCIPLINENM from staffdiscipline b where b.DISCIPLINECD = e.DISCIPLINECD) DISCIPLINECD";
				}
				if(field1.equals("CADRE"))
				{
					field1 = " (select CADRENM from staffcadre b where b.CADRECD = e.CADRECD) CADRECD";
				}
				if(field1.equals("STAFFCTGRY"))
				{
					field1 = " (select STAFFCTGRYNM from staffcategory b where b.STAFFCTGRYCD = e.STAFFCTGRYCD) STAFFCTGRYCD";
				}
				if(field1.equals("MARITALSTAT"))
				{
					field1 = " decode(e.MARITALSTAT,'S','Single','M','Married') MARITALSTAT";
				}
				if(field1.equals("EMPDOB"))
				{
					field1 = " to_char(e.EMPDOB,'dd/Mon/yyyy') EMPDOB";
				}
				if(field1.equals("EMPDOJ"))
				{
					field1 = " to_char(e.EMPDOJ,'dd/Mon/yyyy') EMPDOJ";
				}
				if(field1.equals("MARRIAGEDT"))
				{
					field1 = " to_char(e.MARRIAGEDT,'dd/Mon/yyyy') MARRIAGEDT";
				}
				if(field1.equals("CREATIONDATE"))
				{
					field1 = " to_char(e.CREATIONDATE,'dd/Mon/yyyy') CREATIONDATE";
				}
				if(field1.equals("STATUS"))
				{
					field1 = " decode(e.STATUS,'A','Active','I','Inactive') STATUS";
				}
				if(field1.equals("EARNDEDU"))
				{
					field1 = " (select EARNDEDUNM from earndedumaster b where b.EARNDEDUCD = e.EARNDEDUCD) EARNDEDUCD";
				}
				
				if(field1.equals("EMPLOYEENAME"))
				{
					field1 = " e.EMPLOYEENAME EMPLOYEENAME";
				}
				
				if(field1.equals("CPFNO"))
				{
					field1 = " e.CPFNO CPFNO";
				}
				
				if(field1.equals("PENSIONNO"))
				{
					field1 = " e.PENSIONNO PENSIONNO";
				}
				if(field1.equals("PAN"))
				{
					field1 = " e.PAN PAN";
				}
				if(field1.equals("BANKACCOUNTNO"))
				{
					field1 = " e.BACNO BANKACCOUNTNO";
				}
				if(field1.equals("STATIONCD"))
				{
					field1 = " e.STATIONCD STATIONCD";
				}
				if(field1.equals("PETROL"))
				{
					field1 = " e.PETROL PETROL";
				}
				if(field1.equals("PFTRUST"))
				{
					field1 = " e.PFTRUST PFTRUST";
				}
				if(field1.equals("PAYBILL"))
				{
					field1 = " e.PAYBILL PAYBILL";
				}
				if(field1.equals("QUATERTYPE"))
				{
					field1 = " decode(e.QUATERTYPE,'H','HRA',e.QUATERTYPE) QUATERTYPE";
				}
				
				str1 += field1 +",";
			}
			str=str1.substring(0, str1.length()-1);
			str+=" from "+type+" e, employeeinfo n where e.EMPNO = n.EMPNO ";
			if(!no.equals(""))
			{
				str+=" and e.EMPNO='"+no+"'";
			}
			log.info("query is$$$$$$$$$$$$$$$$$$"+str);
			if(!type.equals(""))
			{
				rs=db.getRecordSet(str);
			}
			ResultSetMetaData rsmd=rs.getMetaData();
			int count=rsmd.getColumnCount();
			System.out.println("the count is"+count);
			
			while(rs.next())
			{
				String reportvalue="";
				bean=new UtilityBean();
				for(int i=1; i<=count; i++)
				{
					//log.info("the values is..."+rs.getString(i));
					reportvalue+=rs.getString(i)+"$";			
				}
					
				bean.setValue(reportvalue);
				list.add(bean);
			}
			
			/*	for(int j=0; j<list.size(); j++)
			{
				UtilityBean ss=(UtilityBean)list.get(j);
				log.info("the value is..."+ss.getValue());
			}
			*/
			//log.info("the query is "+str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.info("<<<<<<<<<<<<<<---------Exception in getReportValues----------->>>>>>>>>>>>");
			log.printStackTrace(e);	
		}
		finally
		{
			try
			{
				
				db.closeCon();
				
			}
			catch(Exception e1)
			{
				log.printStackTrace(e1);
			}
		}
		
	return list;
	}
	
	public StringBuffer ajaxEmployeeCodeDuplicate(String employeeCode)throws Exception{
		boolean flag=true;
		 StringBuffer xmlContent=new StringBuffer();
		 ResultSet rs=null;
		try {
						
			db.makeConnection();
			con = db.getConnection();
				String query="SELECT EMPLNUMBER FROM employeeinfo   WHERE upper(EMPLNUMBER) = upper('"+employeeCode.trim()+"')";
				
				log.info("query------------"+query);
				rs=db.getRecordSet(query);
				
				xmlContent.append("<employeeCode>");
				if(rs.next())
				{
					flag=false;
				}
				
				xmlContent.append("<empCode flag=\"" + flag + "\" />");	
			     
				xmlContent.append("</employeeCode>");                
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
		}
	
		log.info("xmlContent"+xmlContent);
		return xmlContent;
	}
	public StringBuffer ajaxEmployeeDesigDuplicate(String desigCode)throws Exception{
		boolean flag=true;
		 StringBuffer xmlContent=new StringBuffer();
		 ResultSet rs=null;
		try {
						
			db.makeConnection();
			con = db.getConnection();
				String query="SELECT DESIGNATIONCD FROM staffdesignation   WHERE upper(DESIGNATIONCD) = upper('"+desigCode.trim()+"')";
				
				log.info("query------------"+query);
				rs=db.getRecordSet(query);
				
				xmlContent.append("<desigCode>");
				if(rs.next())
				{
					flag=false;
				}
				
				xmlContent.append("<desCode flag=\"" + flag + "\" />");	
			     
				xmlContent.append("</desigCode>");                
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
		}
	
		log.info("xmlContent"+xmlContent);
		return xmlContent;
	}
	public StringBuffer ajaxLedgerCdDuplicate(String ledgCode,String groupacccd,String acctypecd)throws Exception{
		boolean flag=true;
		 StringBuffer xmlContent=new StringBuffer();
		 ResultSet rs=null;
		try {
						
			db.makeConnection();
			con = db.getConnection();
				String query="SELECT LEDGERCD FROM ledgermaster   WHERE upper(LEDGERCD) = upper('"+ledgCode.trim()+"') and upper(ACCTYPECD) = upper('"+acctypecd.trim()+"') ";
				
				log.info("query------------"+query);
				rs=db.getRecordSet(query);
				
				xmlContent.append("<ledgCode>");
				if(rs.next())
				{
					flag=false;
				}
				
				xmlContent.append("<ledCode flag=\"" + flag + "\" />");	
			     
				xmlContent.append("</ledgCode>");                
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
		}
	
		log.info("xmlContent"+xmlContent);
		return xmlContent;
	}
	public StringBuffer ajaxEarningDuplicate(String earningName)throws Exception{
		boolean flag=true;
		 StringBuffer xmlContent=new StringBuffer();
		 ResultSet rs=null;
		try {
						
			db.makeConnection();
			con = db.getConnection();
				String query="SELECT EARNDEDUNM FROM earndedumaster   WHERE upper(EARNDEDUNM) = upper('"+earningName.trim()+"')  ";
				
				log.info("query------------"+query);
				rs=db.getRecordSet(query);
				
				xmlContent.append("<earningName>");
				if(rs.next())
				{
					flag=false;
				}
				
				xmlContent.append("<earName flag=\"" + flag + "\" />");	
			     
				xmlContent.append("</earningName>");                
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
		}
	
		log.info("xmlContent"+xmlContent);
		return xmlContent;
	}
	public StringBuffer ajaxCategoryNameDuplicate(String CategoryName)throws Exception{
		boolean flag=true;
		 StringBuffer xmlContent=new StringBuffer();
		 ResultSet rs=null;
		try {
						
			db.makeConnection();
			con = db.getConnection();
				String query="SELECT STAFFCTGRYNM FROM staffcategory   WHERE upper(STAFFCTGRYNM) = upper('"+CategoryName.trim()+"')";
				System.out.println("query "+query);
				log.info("query------------"+query);
				rs=db.getRecordSet(query);
				
				xmlContent.append("<desigCode>");
				if(rs.next())
				{
					flag=false;
				}
				
				xmlContent.append("<desCode flag=\"" + flag + "\" />");	
			     
				xmlContent.append("</desigCode>");                
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
		}
	
		log.info("xmlContent"+xmlContent);
		return xmlContent;
	}
	public StringBuffer ajaxCaderDuplicate(String caderName)throws Exception{
		boolean flag=true;
		 StringBuffer xmlContent=new StringBuffer();
		 ResultSet rs=null;
		try {
						
			db.makeConnection();
			con = db.getConnection();
				String query="SELECT CADRENM FROM staffcadre WHERE upper(CADRENM) = upper('"+caderName.trim()+"')";
				System.out.println("query "+query);
				log.info("query------------"+query);
				rs=db.getRecordSet(query);
				
				xmlContent.append("<CaderCode>");
				if(rs.next())
				{
					flag=false;
				}
				
				xmlContent.append("<CadCode flag=\"" + flag + "\" />");	
			     
				xmlContent.append("</CaderCode>");                
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}finally{
			con.close();
		}
	
		log.info("xmlContent"+xmlContent);
		return xmlContent;
	}
	public int getBasicSalFlag(){
		log.info("StaffConfigurationDAO:getStaffDisciplines() Entering Method");
		int count=0;
		try{	
			db.makeConnection();con = db.getConnection();
			String query="select count(*)BASICSALFLAGCOUNT from EARNDEDUMASTER where ISBASICSALARY='Y' and status='A'";
			log.info("EARNDEDUMASTER : query "+query);
			rs=db.getRecordSet(query);
            while(rs.next()){
            	count=2;
            }
		} catch (Exception e) {
			log.info("<<<<<<<<<< getBasicSalFlag Exception  >>>>>>>>>>>>");
			log.printStackTrace(e);
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		log.info("StaffConfigurationDAO:getStaffDisciplines() Leaving Method");
		return count;
	}
	public StringBuffer ajaxBasicFlagFlags() throws Exception {

		boolean flag=true;
		StringBuffer xmlContent = new StringBuffer();
		ResultSet rs = null;
		try {

			db.makeConnection();
			con = db.getConnection();
			String query = "select * from EARNDEDUMASTER where ISBASICSALARY='Y' and status='A'";
			System.out.println("query " + query);
			log.info("query------------" + query);
			rs = db.getRecordSet(query);

			xmlContent.append("<BasicSal>");
			if (rs.next()) {
				flag=false;
			}
			xmlContent.append("<BasicSalFlag flag=\"" + flag + "\" />");
			xmlContent.append("</BasicSal>");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			con.close();
		}

		log.info("xmlContent" + xmlContent);
		return xmlContent;
	}
}
