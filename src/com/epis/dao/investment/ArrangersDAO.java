package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import com.epis.bean.investment.ArrangersBean;
import com.epis.bean.investment.SecurityCategoryBean;
import com.epis.common.exception.EPISException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ArrangersDAO {
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	Log log=new Log(ArrangersDAO.class);
private ArrangersDAO(){
		
	}
   private static final ArrangersDAO arrangerDAO= new ArrangersDAO();
   public static ArrangersDAO getInstance(){
	return arrangerDAO;   
   }
    
public List searchArrangers(ArrangersBean abean) throws Exception {
	List list =new ArrayList();
	try{
		ArrangersBean bean =null;
		con = DBUtility.getConnection();
		st = con.createStatement();
		/*String query = "select inv.ARRANGERCD,ARRANGERNAME,arr.CATEGORYID CATEGORYID,sec.categorycd categorycd," +
				"Nvl(PRAMOTOR_NAME,'--')PRAMOTOR_NAME,status,inv.*,to_char(SEBI_VALIDDATE,'dd/Mon/yyyy') SEBI_VALID,to_char(BSE_VALIDDATE,'dd/Mon/yyyy') BSE_VALID,to_char(NSE_VALIDDATE,'dd/Mon/yyyy') NSE_VALID,to_char(RBI_VALIDDATE,'dd/Mon/yyyy') RBI_VALID from invest_arrangers inv, invest_sec_category sec, invest_Arr_category arr" +
				" where arr.CATEGORYID = sec.categoryid and inv.arrangercd=arr.arrangercd and " +
				"Nvl(ARRANGERNAME,' ') like ?  " ;*/
		String query="select inv.ARRANGERCD, ARRANGERNAME, Nvl(PRAMOTOR_NAME, '--') PRAMOTOR_NAME,status,inv.*,to_char(SEBI_VALIDDATE, 'dd/Mon/yyyy') SEBI_VALID, to_char(BSE_VALIDDATE, 'dd/Mon/yyyy') BSE_VALID,to_char(NSE_VALIDDATE, 'dd/Mon/yyyy') NSE_VALID, to_char(RBI_VALIDDATE, 'dd/Mon/yyyy') RBI_VALID,  join(cursor( select  sec.categorycd  from invest_arr_category arr, invest_sec_category sec where arr.categoryid = sec.categoryid and arr.arrangercd=inv.arrangercd)) categorycd   from invest_arrangers inv where  Nvl(inv.arrangername,' ') like ? ";
		boolean bool=false;
		if(!"".equals( abean.getCategoryIds())){
			//query +=" and  instr(?,'|'||arr.CATEGORYID||'|')>0";
			query+=" and ARRANGERCD in ( select ARRANGERCD from invest_arr_category where instr(?,'|'||CATEGORYID||'|')>0)";
			bool = true;
		}
		
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, abean.getArrangerName()+"%");
		if(bool)
			ps.setString(2, abean.getCategoryIds());
		rs=ps.executeQuery();
		while(rs.next()){
			bean = new ArrangersBean();
			bean.setArrangerCd(StringUtility.checknull(rs.getString("ARRANGERCD")));
			bean.setArrangerName(StringUtility.checknull(rs.getString("ARRANGERNAME")));
			bean.setCategoryCd(StringUtility.checknull(rs.getString("categorycd")));
			bean.setPramotorname(StringUtility.checknull(rs.getString("PRAMOTOR_NAME")));
			bean.setStatus(StringUtility.checknull(rs.getString("STATUS")));
			bean.setRegOffAddr(StringUtility.checknull(rs.getString("REG_OFFADDR")));
			bean.setDelhiOffAddr(StringUtility.checknull(rs.getString("DELHI_OFF_ADDR")));			
			bean.setNameOfHeadOfDelhiOff(StringUtility.checknull(rs.getString("DELHI_HEADOFF_NAME")));
			bean.setEmail(StringUtility.checknull(rs.getString("EMAIL")));
			bean.setNetworthAmount(StringUtility.checknull(rs.getString("NETWORTH_AMOUNT")));
			bean.setRegwithsebi(StringUtility.checknull(rs.getString("MEMBERSHIPNO_WITH_SEBI")));
			bean.setSebivaliddate(StringUtility.checknull(rs.getString("SEBI_VALID")));
			bean.setRegwithbse(StringUtility.checknull(rs.getString("MEMBERSHIPNO_WITH_BSE")));
			bean.setBsevaliddate(StringUtility.checknull(rs.getString("BSE_VALID")));
			bean.setRegwithnse(StringUtility.checknull(rs.getString("MEMBERSHIPNO_WITH_NSE")));
			bean.setNsevaliddate(StringUtility.checknull(rs.getString("NSE_VALID")));
			bean.setRegwithrbi(StringUtility.checknull(rs.getString("MEMBERSHIPNO_WITH_RBI")));
			bean.setRbivaliddate(StringUtility.checknull(rs.getString("RBI_VALID")));
			bean.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
			
			bean.setRegPhoneNo(StringUtility.checknull(rs.getString("REG_PHONENO")));
			bean.setRegFaxNo(StringUtility.checknull(rs.getString("REG_FAXNO")));
			bean.setDelhiOffPhNo(StringUtility.checknull(rs.getString("DELHI_OFF_PHNO")));
			bean.setDelhiOffFaxNo(StringUtility.checknull(rs.getString("DELHI_OFF_FAXNO")));
			bean.setDelhiHeadOffMobileNo(StringUtility.checknull(rs.getString("DELHI_HEADOFF_MOBILENO")));
			bean.setDelhiHeadOffPhNo(StringUtility.checknull(rs.getString("DELHI_HEADOFF_PHNO")));
			bean.setPramotorContactNo(StringUtility.checknull(rs.getString("PRAMOTOR_CONTACTNO")));
								
			list.add(bean);
		}
		
	}catch(Exception e){
		throw new Exception(e.toString());
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("ArrangersDAO:searchArrangers():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
	return list;
}
public void saveArrangers(ArrangersBean abean) throws Exception {
	String arrangerCd = "";
	int recordCnt =0;
	try{

		con = DBUtility.getConnection();
		recordCnt=DBUtility.getRecordCount("SELECT COUNT(*) FROM  invest_arrangers WHERE Upper(trim(ARRANGERNAME))=Upper('"+abean.getArrangerName()+"')");
		if(recordCnt!=0)
		{   
		  throw new Exception("Record Already Exists with Arranger Name: " + abean.getArrangerName());
		}
		arrangerCd =AutoGeneration.getNextCode("invest_arrangers","ARRANGERCD",2,con);
		StringBuffer buffer = new StringBuffer("insert into invest_arrangers(arrangercd, arrangername,reg_offaddr,");
                     buffer.append("reg_phoneno,reg_faxno ,delhi_off_addr ,delhi_off_phno ,delhi_off_faxno ,delhi_headoff_name ,");
                     buffer.append("delhi_headoff_mobileno ,delhi_headoff_phno  ,pramotor_name,pramotor_contactno ,pramotor_email,");
                     buffer.append("networth_amount,networth_amountas_on,email,membershipno_with_sebi,sebi_validdate,membershipno_with_bse,");
                     buffer.append("bse_validdate,membershipno_with_nse,nse_validdate,membershipno_with_rbi,rbi_validdate,remarks,status,CREATED_BY,CREATED_DT)");
                     buffer.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)");
			         PreparedStatement prepared = con.prepareStatement( buffer.toString());
			         prepared.setString(1,arrangerCd);
			         prepared.setString(2,abean.getArrangerName());
			         prepared.setString(3,abean.getRegOffAddr());
			         prepared.setString(4,abean.getRegPhoneNo());
			         prepared.setString(5,abean.getRegFaxNo());
			         prepared.setString(6,abean.getDelhiOffAddr());
			         prepared.setString(7,abean.getDelhiOffPhNo());
			         prepared.setString(8,abean.getDelhiOffFaxNo());
			         prepared.setString(9,abean.getNameOfHeadOfDelhiOff());
			         prepared.setString(10,abean.getDelhiHeadOffMobileNo());
			         prepared.setString(11,abean.getDelhiHeadOffPhNo());
			         prepared.setString(12,abean.getPramotorname());
			         prepared.setString(13,abean.getPramotorContactNo());
			         prepared.setString(14,abean.getPramotorEmail());
			         prepared.setString(15,abean.getNetworthAmount());
			         prepared.setString(16,abean.getNetworthAsOn());
			         prepared.setString(17,abean.getEmail());
			         prepared.setString(18,abean.getRegwithsebi());
			         prepared.setString(19,abean.getSebivaliddate());
			         prepared.setString(20,abean.getRegwithbse());
			         prepared.setString(21,abean.getBsevaliddate());
			         prepared.setString(22,abean.getRegwithnse());
			         prepared.setString(23,abean.getNsevaliddate());
			         prepared.setString(24,abean.getRegwithrbi());
			         prepared.setString(25,abean.getRbivaliddate());
			         prepared.setString(26,abean.getRemarks());
			         prepared.setString(27,abean.getStatus());
			         prepared.setString(28,abean.getLoginUserId());
			         prepared.executeUpdate();
			         prepared.close();
			         
			         String query = "insert into invest_Arr_category(arrangercd,categoryid) values(?,?)"; 
			         PreparedStatement pst = con.prepareStatement( query);
			         StringTokenizer st = new StringTokenizer(abean.getCategoryIds(), "|"); 		         
			         while(st.hasMoreTokens()) {	
			        	 pst.setString(1,arrangerCd);
			        	 pst.setString(2,st.nextToken());
			        	 pst.executeUpdate();
			         }      
	}catch(Exception e){
		log.error("ArrangersDAO:saveArrangers():Exception: "+ e.getMessage());
		throw new Exception(e.getMessage());
	}
	
	
}
public void editArrangers(ArrangersBean abean) throws Exception {	
	try{
		con = DBUtility.getConnection();
		con.setAutoCommit(false);
		StringBuffer buffer = new StringBuffer("update invest_arrangers set arrangername=?,reg_offaddr=?,");
        buffer.append("reg_phoneno=?,reg_faxno =?,delhi_off_addr =?,delhi_off_phno =?,delhi_off_faxno =?,delhi_headoff_name =?,");
        buffer.append("delhi_headoff_mobileno =?,delhi_headoff_phno  =?,pramotor_name=?,pramotor_contactno =?,pramotor_email=?,");
        buffer.append("networth_amount=?,networth_amountas_on=?,email=?,membershipno_with_sebi=?,sebi_validdate=?,membershipno_with_bse=?,");
        buffer.append("bse_validdate=?,membershipno_with_nse=?,nse_validdate=?,membershipno_with_rbi=?,rbi_validdate=?,remarks=?,status=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where ARRANGERCD=?");
        PreparedStatement prepared = con.prepareStatement( buffer.toString());
        prepared.setString(1,abean.getArrangerName());
        prepared.setString(2,abean.getRegOffAddr());
        prepared.setString(3,abean.getRegPhoneNo());
        prepared.setString(4,abean.getRegFaxNo());
        prepared.setString(5,abean.getDelhiOffAddr());
        prepared.setString(6,abean.getDelhiOffPhNo());
        prepared.setString(7,abean.getDelhiOffFaxNo());
        prepared.setString(8,abean.getNameOfHeadOfDelhiOff());
        prepared.setString(9,abean.getDelhiHeadOffMobileNo());
        prepared.setString(10,abean.getDelhiHeadOffPhNo());
        prepared.setString(11,abean.getPramotorname());
        prepared.setString(12,abean.getPramotorContactNo());
        prepared.setString(13,abean.getPramotorEmail());
        prepared.setString(14,abean.getNetworthAmount());
        prepared.setString(15,abean.getNetworthAsOn());
        prepared.setString(16,abean.getEmail());
        prepared.setString(17,abean.getRegwithrbi());
        prepared.setString(18,abean.getSebivaliddate());
        prepared.setString(19,abean.getRegwithbse());
        prepared.setString(20,abean.getBsevaliddate());
        prepared.setString(21,abean.getRegwithnse());
        prepared.setString(22,abean.getNsevaliddate());
        prepared.setString(23,abean.getRegwithrbi());
        prepared.setString(24,abean.getRbivaliddate());
        prepared.setString(25,abean.getRemarks());
        prepared.setString(26,abean.getStatus());
        prepared.setString(27,abean.getLoginUserId());
        prepared.setString(28,abean.getArrangerCd());
        int i= prepared.executeUpdate();
        prepared.close();
        String deleteSql ="Delete from invest_arr_category  Where ARRANGERCD=? ";
        PreparedStatement ps = con.prepareStatement(deleteSql);
    	ps.setString(1, abean.getArrangerCd());
    	ps.executeUpdate();		
    	ps.close();
        String query = "insert into invest_Arr_category(arrangercd,categoryid) values(?,?)"; 
        PreparedStatement pst = con.prepareStatement( query);
        StringTokenizer st = new StringTokenizer(abean.getCategoryIds(), "|"); 		         
        while(st.hasMoreTokens()) {	
       	 pst.setString(1,abean.getArrangerCd());
       	 pst.setString(2,st.nextToken());
       	 pst.executeUpdate();
        }         
        
        con.commit();
	}catch(Exception e){
		con.rollback();
		log.error("SecurityCategoryDAO:editCategory():Exception: "+ e.getMessage());
		throw new Exception(e);
	}
	
}
public static void populate(Object bean,ResultSet resultSet,String[] beanProp) throws 
SQLException {
 HashMap properties = new HashMap();
 ResultSetMetaData metaData = resultSet.getMetaData();
 int cols = metaData.getColumnCount();
  if (resultSet.next()){
	 for (int i=1; i<=cols ; i++) {
  properties.put(beanProp[i],resultSet.getString(i));
 }
 }
 try {
 	BeanUtils.populate(bean, properties);
 } catch (Exception e) {
  throw new SQLException("BeanUtils.populate threw " + e.toString());
 }
} 

public ArrangersBean findArrangers(String arrangercd) throws Exception {
	ArrangersBean bean =new ArrangersBean();
	List clist= new ArrayList();
	try{
		con = DBUtility.getConnection();
		st = con.createStatement();
		String query = "select inv.ARRANGERCD,ARRANGERNAME," +
				"PRAMOTOR_NAME,status,reg_offaddr ,reg_phoneno,reg_faxno,delhi_off_addr ,delhi_off_phno," +
				"delhi_off_faxno ,delhi_headoff_name ,delhi_headoff_mobileno,delhi_headoff_phno,pramotor_contactno,"+
				"pramotor_email ,networth_amount ,to_char(networth_amountas_on,'dd/Mon/yyyy') networth_amountas_on ,email ,membershipno_with_sebi,"+
				"to_char(sebi_validdate,'dd/Mon/yyyy')sebi_validdate ,membershipno_with_bse ,to_char(bse_validdate,'dd/Mon/yyyy')bse_validdate ,membershipno_with_nse ,to_char(nse_validdate,'dd/Mon/yyyy')nse_validdate,"+
				"membershipno_with_rbi ,to_char(rbi_validdate,'dd/Mon/yyyy')rbi_validdate ,remarks from invest_arrangers inv "+
				" where inv.arrangercd=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, arrangercd);
		rs=ps.executeQuery();
		 String[] array = {"","arrangerCd","arrangerName","pramotorname","status","regOffAddr","regPhoneNo","regFaxNo","delhiOffAddr","delhiOffPhNo","delhiOffFaxNo","nameOfHeadOfDelhiOff","delhiHeadOffMobileNo","delhiHeadOffPhNo","pramotorContactNo", "pramotorEmail","networthAmount","networthAsOn","email","regwithsebi","sebivaliddate","regwithbse","bsevaliddate","regwithnse","nsevaliddate","regwithrbi","rbivaliddate","remarks"};
		populate(bean,rs,array);
		ps.close();
		String catquery = "select sec.CATEGORYID CATEGORYID,sec.categorycd categorycd from invest_arr_category arr,invest_sec_category sec where ARRANGERCD=? and sec.categoryid=arr.categoryid";
		PreparedStatement pst = con.prepareStatement(catquery);
		pst.setString(1, arrangercd);
		rs=pst.executeQuery();
	    while(rs.next()){
	        clist.add(rs.getString("CATEGORYID"));
	    }
	    int size = clist.size();
	    String[] str = new String[size];
	    for(int i=0;i<size;i++){
	    	str[i] = (String)clist.get(i);
	    }
	    bean.setCategoryId(str);
	}catch(SQLException e){
		throw new EPISException(e);
	}catch(Exception e){
	throw new EPISException(e);
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("ArrangersDAO:searchArrangers():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
	return bean;
}
public void deleteArrangers(String arrangercds) throws Exception {
	con = DBUtility.getConnection();
	
	String deleteSql ="Delete from invest_arr_category  Where ARRANGERCD=? ";
	String deletearrSql ="Delete from invest_arrangers  Where ARRANGERCD=? ";
	log.info("the delete query is"+deletearrSql+"the arrangercds"+arrangercds);
	try{
		DBUtility.setAutoCommit(false,con);
	PreparedStatement ps = con.prepareStatement(deleteSql);
	ps.setString(1, arrangercds);
	ps.executeUpdate();		
	ps.close();
	PreparedStatement pst = con.prepareStatement(deletearrSql);
	pst.setString(1, arrangercds);
	pst.executeUpdate();
	DBUtility.commitTrans(con);
	}catch(SQLException e){
		DBUtility.rollbackTrans(con);
		throw new EPISException(e);
	}catch(Exception e){
		DBUtility.rollbackTrans(con);
	throw new EPISException(e);
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("ArrangersDAO:deleteArrangers():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
}
	
public List searchCategory() throws Exception {
	List list =new ArrayList();
	try{
		SecurityCategoryBean abean =null;
		con = DBUtility.getConnection();
		st = con.createStatement();
		String query = "select categoryid,categorycd from invest_sec_category where categoryid is not null";
		query +=" order by categoryid";
		log.info("ArrangersDAO:searchCategory(): "+query);	
		rs = DBUtility.getRecordSet(query,st);
		while(rs.next()){
			abean = new SecurityCategoryBean();
			abean.setCategoryId(rs.getString("categoryid"));
			abean.setCategoryCd(rs.getString("categorycd"));
			list.add(abean);
		}
	}catch(Exception e){
		throw new Exception(e.toString());
	}finally {
		try {
			DBUtility.closeConnection(rs,st,con);
		} catch (Exception e) {
			log.error("ArrangersDAO:searchCategory():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
	return list;
}


}
