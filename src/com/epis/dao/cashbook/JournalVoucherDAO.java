package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epis.bean.cashbook.JournalVoucherDetails;
import com.epis.bean.cashbook.JournalVoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.CommonDAO;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

public class JournalVoucherDAO {
	Log log = new Log(JournalVoucherDAO.class);

	private JournalVoucherDAO() {

	}

	private static final JournalVoucherDAO dao = new JournalVoucherDAO();

	public static JournalVoucherDAO getInstance() {
		return dao;
	}

	public List search(JournalVoucherInfo info) throws EPISException {
		log.info("JournalVoucherDAO::search::Entering");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List journalList = new ArrayList();
		try{
			con = DBUtility.getConnection();
			String query=getQueries("selectQuery");
			
			
			if (info.getVoucherDate() != null
					&& !"".equals(info.getVoucherDate().trim())) {
				query = query + " and voucher_dt = to_date('"
						+ info.getVoucherDate() + "','dd/mon/yyyy')";
			}
			if (info.getPreparationDate() != null
					&& !"".equals(info.getPreparationDate().trim())) {
				query = query + " and PREPERATION_DT = to_date('"
						+ info.getPreparationDate() + "','dd/mon/yyyy')";
			}
			if (info.getVoucherNo() != null
					&& !"".equals(info.getVoucherNo().trim())) {
				query = query
						+ " and upper(vou.voucherno) like upper('"
						+ info.getVoucherNo() + "%') ";
			}
			query=query+" order by vou.ENTERED_DT desc";
			pst = con.prepareStatement(query);
			//pst.setString(1,info.getLoginProfile());
			pst.setString(1,StringUtility.checknull(info.getApproval())+"%");
			rs = pst.executeQuery();			
			String loginRegion = info.getLoginRegion();
			String loginProfile = info.getLoginProfile();
			while(rs.next()){
				info = new JournalVoucherInfo();
				info.setKeyNo(rs.getString("KEYNO"));
				info.setPartyType(rs.getString("PARTYTYPE"));
				info.setPfid(rs.getString("EMP_PFID"));
				info.setPartyCode(rs.getString("PARTY_CODE"));
				info.setOther(rs.getString("OTHER"));
				info.setVoucherNo(rs.getString("VOUCHERNO"));
				info.setVoucherDate(rs.getString("VOUCHER_DT"));
				info.setUnitCode(rs.getString("UNITCODE"));
				info.setRegion(rs.getString("REGION"));
				info.setTrustType(rs.getString("TRUSTTYPE"));
				info.setDetails(rs.getString("DETAILS"));
				info.setPreparedBy(rs.getString("PREPAREDBY"));
				info.setPreparationDate(rs.getString("PREPERATION_DT"));
				info.setApproval(rs.getString("APPROVAL"));
				info.setApprovedBy(rs.getString("APPROVEDBY"));
				info.setApprovalDate(rs.getString("APPROVEDDDT"));
				info.setAmount(StringUtility.checknull(rs.getString("amount")));
				if("C".equals(loginProfile) ||("A".equals(loginProfile))||("R".equals(loginProfile) && loginRegion.equals(rs.getString("REGIONNAME")))){
					journalList.add(info);
				}				
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}
		log.info("JournalVoucherDAO::search::Leaving");
		return journalList;
	}
	
	public void save(JournalVoucherInfo info) throws EPISException {
		log.info("JournalVoucherDAO::save::Entering");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		JournalVoucherDetails details = null;
		String keyNo = null;
		try{
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(getQueries("keynoQuery"));
			pst.setString(1,StringUtility.checknull(info.getPreparationDate()));
			rs  = pst.executeQuery();			
			if(rs.next()){
				keyNo = rs.getString(1);
			}
			pst.close();
			pst = con.prepareStatement(getQueries("insertQuery"));
			pst.setString(1,keyNo);
			pst.setString(2,StringUtility.checknull(info.getPartyType()));
			pst.setString(3,StringUtility.checknull(info.getPfid()));
			pst.setString(4,StringUtility.checknull(info.getPartyCode()));
			pst.setString(5,StringUtility.checknull(info.getOther()));
			pst.setString(6,StringUtility.checknull(info.getTrustType()));
			pst.setString(7,"R".equals(StringUtility.checknull(info.getLoginProfile()))?StringUtility.checknull(info.getStationName()):"");
			pst.setString(8,"C".equals(StringUtility.checknull(info.getLoginProfile()))?StringUtility.checknull(info.getStationName()):"");
			pst.setString(9,StringUtility.checknull(info.getDetails()));
			pst.setString(10,StringUtility.checknull(info.getLoginUserId()));
			pst.setString(11,StringUtility.checknull(info.getPreparationDate()));
			pst.setString(12,StringUtility.checknull(info.getLoginUserId()));
			pst.setString(13,StringUtility.checknull(info.getLoginUnitCode()));
			pst.setString(14,StringUtility.checknull(info.getFinYear()));
			pst.setString(15,StringUtility.checknull(info.getLoginProfile()));
			pst.executeUpdate();
			pst.close();
			pst = con.prepareStatement(getQueries("detailsQuery"));
			List array = info.getDebitList();
			int size = array.size();
			for(int i=0;i<size;i++){
				details = (JournalVoucherDetails)array.get(i);
				pst.setString(1,keyNo);
				pst.setString(2,details.getAccountCode());
				pst.setString(3,details.getDescription());
				pst.setString(4,details.getDebit());
				pst.setString(5,"0");
				pst.executeUpdate();
			}
			array = info.getCreditList();
			size = array.size();
			for(int i=0;i<size;i++){
				details = (JournalVoucherDetails)array.get(i);
				pst.setString(1,keyNo);
				pst.setString(2,details.getAccountCode());
				pst.setString(3,details.getDescription());
				pst.setString(4,"0");
				pst.setString(5,details.getCredit());
				pst.executeUpdate();
			}
			con.commit();
		}catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.printStackTrace(e);
			throw new EPISException(e);
		}
		finally{
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("JournalVoucherDAO::save::Leaving");
	}
	
	public JournalVoucherInfo getJournalVoucher(JournalVoucherInfo info) throws EPISException {
		log.info("JournalVoucherDAO::search::Entering");
		Connection con = null;
		PreparedStatement pst = null;
		PreparedStatement psamt=null;
		ResultSet rsamt=null;
		ResultSet rs = null;
		try{
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("JVQuery"));
			pst.setString(1,info.getKeyNo());
			rs = pst.executeQuery();
			while(rs.next()){
				info.setKeyNo(rs.getString("KEYNO"));
				info.setPartyType(rs.getString("PARTYTYPE"));
				info.setPfid(rs.getString("EMP_PFID"));
				info.setPartyCode(rs.getString("PARTY_CODE"));
				info.setOther(rs.getString("OTHER"));
				info.setVoucherNo(rs.getString("VOUCHERNO"));
				info.setVoucherDate(rs.getString("VOUCHER_DT"));
				info.setUnitCode(rs.getString("UNITCODE"));
				info.setRegion(rs.getString("REGION"));
				info.setTrustType(rs.getString("TRUSTTYPE"));
				info.setDetails(rs.getString("DETAILS"));
				info.setPreparedBy(rs.getString("PREPAREDBY"));
				info.setPreparationDate(rs.getString("PREPERATION_DT"));
				info.setApproval(rs.getString("APPROVAL"));
				info.setApprovedBy(rs.getString("APPROVEDBY"));
				info.setApprovalDate(rs.getString("APPROVEDDDT"));
				info.setPartyName(rs.getString("PARTYNAME"));
				info.setFinYear(rs.getString("finYear"));
				info.setEmployeeName(rs.getString("EmpName"));	
				if("E".equals(info.getPartyType())){
					info.setPfidFull((new CommonDAO()).getPFID(info.getEmployeeName(),rs.getString("emp_dob"),info.getPfid())); 		
				}
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,null);
		}
		try{
			JournalVoucherDetails details = null;
			pst = con.prepareStatement(getQueries("JVDetailQuery"));
			pst.setString(1,info.getKeyNo());
			rs = pst.executeQuery();
			List jvbyList = new ArrayList();
			List jvtoList = new ArrayList();
			double amount = 0.0;
			while(rs.next()){
				details = new JournalVoucherDetails();
				details.setAccountCode(StringUtility.checknull(rs.getString("accountcode")));
				details.setParticular(StringUtility.checknull(rs.getString("PARTICULAR")));
				details.setDescription(StringUtility.checknull(rs.getString("description")));
				details.setDebit(StringUtility.checknull(rs.getString("debit")));
				details.setCredit(StringUtility.checknull(rs.getString("credit")));
				if("0".equals(details.getCredit())){
					jvbyList.add(details);
				}else {
					jvtoList.add(details);
				}
				amount += Double.parseDouble(details.getDebit());
			}
			int size = jvbyList.size();
			String[] byRecords = new String[size];
			
			for(int i=0;i<size;i++){
				byRecords[i]=((JournalVoucherDetails)jvbyList.get(i)).toString();
			}
			info.setByRecords(byRecords);			
			size = jvtoList.size();
			String[] toRecords = new String[size];
			for(int i=0;i<size;i++){
				toRecords[i]=((JournalVoucherDetails)jvtoList.get(i)).toString();
			}
			info.setDebitList(jvbyList);
			info.setCreditList(jvtoList);
			info.setToRecords(toRecords);
			psamt=con.prepareStatement("select F_WORDS("+amount+") amount from dual");
			rsamt=psamt.executeQuery();
			if(rsamt.next())
			{
			info.setAmount(rsamt.getString("amount"));
			}
		}catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			try{
			if(psamt!=null)
				psamt.close();
			if(rsamt!=null)
				rsamt.close();
			}
			
			catch(Exception e)
			{
				
			}
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("JournalVoucherDAO::search::Leaving");
		return info;
	}
	
	public void update(JournalVoucherInfo info) throws EPISException {
		log.info("JournalVoucherDAO::save::Entering");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		JournalVoucherDetails details = null;
		try{
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(getQueries("updateQuery"));
			pst.setString(1,StringUtility.checknull(info.getLoginUnitCode()));
			pst.setString(2,StringUtility.checknull(info.getPartyType()));
			pst.setString(3,StringUtility.checknull(info.getPfid()));
			pst.setString(4,StringUtility.checknull(info.getPartyCode()));
			pst.setString(5,StringUtility.checknull(info.getOther()));
			pst.setString(6,StringUtility.checknull(info.getTrustType()));
			pst.setString(7,"R".equals(StringUtility.checknull(info.getLoginProfile()))?StringUtility.checknull(info.getStationName()):"");
			pst.setString(8,"C".equals(StringUtility.checknull(info.getLoginProfile()))?StringUtility.checknull(info.getStationName()):"");
			pst.setString(9,StringUtility.checknull(info.getDetails()));
			pst.setString(10,StringUtility.checknull(info.getLoginUserId()));
			pst.setString(11,StringUtility.checknull(info.getPreparationDate()));
			pst.setString(12,StringUtility.checknull(info.getFinYear()));
			pst.setString(13,StringUtility.checknull(info.getKeyNo()));			
			pst.executeUpdate();
			pst.close();
			pst = con.prepareStatement(getQueries("delDtQuery"));
			pst.setString(1,StringUtility.checknull(info.getKeyNo()));
			pst.executeUpdate();
			pst.close();
			pst = con.prepareStatement(getQueries("detailsQuery"));
			List array = info.getDebitList();
			int size = array.size();
			for(int i=0;i<size;i++){
				details = (JournalVoucherDetails)array.get(i);
				pst.setString(1,info.getKeyNo());
				pst.setString(2,details.getAccountCode());
				pst.setString(3,details.getDescription());
				pst.setString(4,details.getDebit());
				pst.setString(5,"0");
				pst.executeUpdate();
			}
			array = info.getCreditList();
			size = array.size();
			for(int i=0;i<size;i++){
				details = (JournalVoucherDetails)array.get(i);
				pst.setString(1,info.getKeyNo());
				pst.setString(2,details.getAccountCode());
				pst.setString(3,details.getDescription());
				pst.setString(4,"0");
				pst.setString(5,details.getCredit());
				pst.executeUpdate();
			}
			con.commit();
		}catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.printStackTrace(e);
			throw new EPISException(e);
		}
		finally{
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("JournalVoucherDAO::save::Leaving");
	}
	public void deleteApprovals(String keynos,String loginid,String loginunit) throws Exception {
		log.info("JournalVoucherDAO : delete : Entering method");
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);		
			pst = con.prepareStatement(getQueries("insertJVquery"));			
			pst.setString(1, loginid);
			pst.setString(2, loginunit);
			pst.setString(3, keynos);			
			pst.executeUpdate();
			pst.close();
			
			pst = con.prepareStatement(getQueries("deleteDetailsQuery"));			
			pst.setString(1, keynos);			
			pst.executeUpdate();
			pst.close();
			
			pst = con.prepareStatement(getQueries("deleteQuery"));			
			pst.setString(1, keynos);			
			pst.executeUpdate();
			pst.close();
			
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			con.rollback();
			log.printStackTrace(e);
			throw e;
		} finally {
			/*try {
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}*/
		}
	}
	public JournalVoucherInfo SaveApproval(JournalVoucherInfo info) throws EPISException {
		log.info("JournalVoucherDAO : SaveApprovalRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String keyno=info.getKeyNo();
		int i=0;
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement("C".equals(info.getLoginProfile())?getQueries("updateCHQapprovalQuery"):getQueries("updateRAUapprovalQuery"));	
			
			pst.setString(1,StringUtility.checknull(info.getFinYear()));
			pst.setString(2,StringUtility.checknull(info.getVoucherDate()));
			pst.setString(3,StringUtility.checknull(info.getLoginUserId()));
			pst.setString(4,StringUtility.checknull(info.getVoucherDate()));
			pst.setString(5,StringUtility.checknull(keyno));
			
			i=pst.executeUpdate();
			con.commit();
			
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("JournalVoucherDAO : SaveApprovalRecord : leaving method");
		if(i>0)
			info.setKeyNo(keyno);
			info=getJournalVoucher(info);		
		return info;	
	}
	
	private String getQueries(String query){
		Map queries = new HashMap();
		
		queries.put("selectQuery","select KEYNO,decode(PARTYTYPE,'P','Party','E','Employee','Other') PARTYTYPE," +
				" EMP_PFID,PARTY_CODE,OTHER,VOUCHERNO,reg.REGIONNAME REGIONNAME," +
				" to_char(VOUCHER_DT,'dd/Mon/YYYY') VOUCHER_DT,vou.UNITCODE,vou.REGION,TRUSTTYPE,DETAILS,PREPAREDBY, " +
				" to_char(PREPERATION_DT,'dd/Mon/YYYY')PREPERATION_DT,APPROVAL,APPROVEDBY, " +
				" to_char(APPROVEDDDT,'dd/Mon/YYYY') APPROVEDDDT,(select sum(nvl(dt.debit,0)) from cb_journalvoucher_details dt where dt.keyno=vou.keyno )amount from  cb_JournalVoucher vou,epis_user usr," +
				" employee_unit_master unt,employee_region_master reg where unt.region=reg.regionname and " +
				" unitcd=unt.unitcode and ENTEREDBY = userid   and deleted_flag='N' and nvl(APPROVAL,'N')like ?");
		queries.put("JVQuery","select KEYNO,PARTYTYPE,EMP_PFID,PARTY_CODE,decode(PARTY_CODE,'',' ',(select " +
				" PARTYNAME from cb_party_info where PARTYCODE = PARTY_CODE))PARTYNAME,decode(emp_pfid,'',' '," +
				" (select EMPLOYEENAME from employee_personal_info where PENSIONNO = emp_pfid)) EmpName," +
				" decode(emp_pfid,'',' ',(select  to_char(DATEOFBIRTH,'DD/MON/YYYY') from employee_personal_info " +
				" where PENSIONNO = emp_pfid))  emp_dob,OTHER,VOUCHERNO, " +
				" to_char(VOUCHER_DT,'dd/Mon/YYYY') VOUCHER_DT,UNITCODE,REGION,TRUSTTYPE,DETAILS,PREPAREDBY, " +
				" to_char(PREPERATION_DT,'dd/Mon/YYYY')PREPERATION_DT,APPROVAL,APPROVEDBY, " +
				" to_char(APPROVEDDDT,'dd/Mon/YYYY') APPROVEDDDT,finYear from  cb_JournalVoucher where keyno=?");
		queries.put("JVDetailQuery","select det_keyno,accountcode,PARTICULAR,description,debit,credit from  " +
				" cb_journalvoucher_details,cb_accountcode_info where accountcode=ACCOUNTHEAD and keyno=?");
		queries.put("insertQuery","insert into cb_JournalVoucher (Keyno,Partytype,Emp_Pfid,Party_Code,Other, " + 
				" Trusttype,Unitcode,Region,Details,Preparedby,Preperation_Dt,Enteredby,Created_Unit,finYear,profile) " +
				" values(?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?)");
		queries.put("updateQuery","update cb_JournalVoucher set EDITED_UNIT=?,Partytype=?,Emp_Pfid=?,Party_Code=?," +
				" Other=?, Trusttype=?,Unitcode=?,Region=?,Details=?,EDITEDBY=?,EDITEDDT=sysdate,PREPERATION_DT=" +
				" to_date(?,'dd/mm/yyyy'),finYear=?  where Keyno=?");
		queries.put("detailsQuery","insert into cb_journalvoucher_details (keyno,det_keyno,accountcode, " +
				" description,debit,credit) values (?,get_nextcode('cb_journalvoucher_details','DET_KEYNO',12)," +
				" ?,?,?,?)");
		queries.put("delDtQuery"," delete from cb_journalvoucher_details where keyno = ? ");
		queries.put("keynoQuery","select get_nextcode_gby('cb_JournalVoucher','KEYNO',12,gen.key) from (select to_char(to_date(?,'dd/mm/yyyy'),'ddMMyy') key from dual) gen ");
		queries.put("insertJVquery","insert into cb_journalvoucher (KEYNO,partytype,emp_pfid,party_code,other,voucherno,voucher_dt,"+
				" unitcode,Region,trusttype,details,preparedby,preperation_dt,approval,approvedby,approvedddt,"+
				" enteredby,entered_dt,created_unit,editedby,editeddt,edited_unit,approvalby,approvaldt,approval_unit,"+
				" deletedby,deleteddt,deleted_unit,deletedno,FINYEAR,DELETED_FLAG) "+
				" SELECT (select lpad( Nvl(max(keyno),0)+1,12,0) from cb_journalvoucher where deleted_flag='Y'), "+
				" partytype,emp_pfid,party_code,other,voucherno,voucher_dt, unitcode,Region,trusttype,details, preparedby,"+
				" preperation_dt,approval,approvedby, approvedddt,enteredby,entered_dt,created_unit,editedby,editeddt,"+
				" edited_unit,approvalby,approvaldt,approval_unit, ?, SYSDATE,?,KEYNO,FINYEAR,'Y' FROM CB_JOURNALVOUCHER WHERE INSTR(?,KEYNO)>0");
		queries.put("deleteDetailsQuery","update cb_journalvoucher_details dt set keyno=(select keyno from cb_journalvoucher where DELETEDNO=dt.keyno) where instr(?,keyno)>0");
		queries.put("deleteQuery","delete cb_journalvoucher where INSTR(?,KEYNO)>0");
		queries.put("updateRAUapprovalQuery","update cb_journalvoucher jv set VOUCHERNO='NR/'||jv.unitcode||jv.region||'/'||(select lpad(Nvl(max(substr(jvi.voucherno, instr(jvi.voucherno, '/', -1) + 1)), 0) + 1,6,'0') no  from cb_journalvoucher jvi where  jvi.APPROVAL='Y' and substr(voucherno,0,instr(voucherno,'/',1)-1)='NR'and jvi.FINYEAR=?),VOUCHER_DT=?,APPROVAL='Y',APPROVEDBY=?,APPROVEDDDT=? where keyno=?");
		queries.put("updateCHQapprovalQuery","update cb_journalvoucher jv set VOUCHERNO='JV/CHQ/'||(select lpad(Nvl(max(substr(jvi.voucherno, instr(jvi.voucherno, '/', -1) + 1)), 0) + 1,4,'0') no  from cb_journalvoucher jvi where jvi.APPROVAL='Y' and substr(voucherno,0,instr(voucherno,'/',1)-1)='JV' and jvi.FINYEAR=?),VOUCHER_DT=?,APPROVAL='Y',APPROVEDBY=?,APPROVEDDDT=? where keyno=?");
		
		return queries.get(query).toString();
	}	
	
}
