package com.epis.dao.investment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.epis.bean.investment.RatioBean;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.UtilityBean;

public class RatioDAO {
	Log log=new Log(RatioDAO.class);
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	private RatioDAO(){
			
		}
	   private static final RatioDAO ratioDAO= new RatioDAO();
	   public static RatioDAO getInstance(){
		return ratioDAO;   
	   }
	public List searchRatio(String validdate) throws Exception {
		List list =new ArrayList();
		try{
			RatioBean bean =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select ratiocd,to_char(validfrom,'dd/Mon/yyyy')validfrom, Nvl(to_char(validto,'dd/Mon/yyyy'),'Till Date')validto from invest_ratio invest where invest.ratiocd is not null" ;
			boolean bool=false;
			if(!"".equals(validdate)){
				query +=" and invest.validfrom = ?  ";
				bool = true;
			}
			query +=" order by ratiocd desc";
			log.info("RatioDAO:searchRatio(): "+query);	
			PreparedStatement ps = con.prepareStatement(query);
			if(bool)
				ps.setString(1, validdate);
			rs=ps.executeQuery();
			while(rs.next()){
				bean = new RatioBean();
				bean.setRatioCd(rs.getString("ratiocd"));
				bean.setValidFrom(rs.getString("validfrom"));
				bean.setValidTo(rs.getString("validto"));
				list.add(bean);
			}
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("RatioDAO:searchRatio():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}
	public HashMap getCategories() throws Exception {
		HashMap map = null;
		HashMap map1 = new HashMap();
		try{
		con = DBUtility.getConnection();
		st = con.createStatement();
		String catQuery = " select cat.ratiocd ratiocd,categorycd,cat.percentage percentage from invest_sec_category sec,invest_ratio ratio,invest_ratio_category cat where sec.categoryid = cat.categoryid and cat.ratiocd = ratio.ratiocd ";			
		PreparedStatement pst = con.prepareStatement(catQuery);
		rs=pst.executeQuery();
			while(rs.next()){
				if(map1.containsKey(rs.getString("ratiocd"))){
					map = (HashMap)map1.get(rs.getString("ratiocd"));					
				}else{
					map = new HashMap();
				}
				map.put(rs.getString("categorycd"),rs.getString("percentage"));
				map1.put(rs.getString("ratiocd"),map);
			}
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("RatioDAO:searchRatio():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return map1;
	}
	
	public void saveRatio(RatioBean rbean) throws Exception {
		String ratiocd = "";
		String validdate="";
		UtilityBean util = null;
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			con.setAutoCommit(false);
			rs=DBUtility.getRecordSet(" SELECT max(to_char(validfrom,'dd/Mon/yyyy'))validfrom FROM  invest_ratio WHERE validfrom >='"+rbean.getValidFrom()+"'",st);
			if(rs.next())
				validdate=rs.getString("validfrom");
			if(validdate!=null)
			{   
			  throw new Exception(rbean.getValidFrom()+" Should be Grater than "+validdate);
			}
			ratiocd =AutoGeneration.getNextCode("invest_ratio","ratiocd",2,con);
			String updateDate ="Update invest_ratio set validto=to_date(?)-1 where validto is null";
			 PreparedStatement updateps =con.prepareStatement(updateDate);
			 updateps.setString(1,rbean.getValidFrom());
			 updateps.executeUpdate();
			 updateps.close();
			String saveRatioSql ="insert into invest_ratio(ratiocd, validfrom,CREATED_BY,CREATED_DT) values (?,?,?,SYSDATE)";
			log.info("RatioDAO:saveRatio(): "+saveRatioSql);	
			  PreparedStatement pst = con.prepareStatement( saveRatioSql);
			  pst.setString(1,ratiocd);
			  pst.setString(2,rbean.getValidFrom());
			  pst.setString(3,rbean.getLoginUserId());
			  pst.executeUpdate();
			  pst.close();
			  List list = rbean.getRatioList();
				for(int i=0;i<list.size();i++){
					 String saveRatioCatSql ="insert into invest_ratio_category(ratiocd,categoryid,percentage) values (?,?,?)";
					  log.info("RatioDAO:saveRatio(): "+saveRatioCatSql);
					  PreparedStatement ps =con.prepareStatement(saveRatioCatSql);
					  ps.setString(1,ratiocd);
					  util = (UtilityBean)list.get(i);
					  ps.setString(2,util.getKey());
					  ps.setString(3,util.getValue());
					  ps.executeUpdate();
					  ps.close();
				}
				con.commit();
				con.close();
		}catch(Exception e){
			con.rollback();
			log.error("RatioDAO:saveRatio():Exception: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	public void editRatio(RatioBean rbean) throws Exception {	
		UtilityBean util = null;;
		try{
			con = DBUtility.getConnection();
			List list = rbean.getRatioList();
			for(int i=0;i<list.size();i++){
				String updateRatioCatSql ="update invest_ratio_category set percentage=? where ratiocd=? and categoryid=?";
			PreparedStatement ps = con.prepareStatement(updateRatioCatSql);
			  util = (UtilityBean)list.get(i);
			  ps.setString(1,util.getValue());
			  ps.setString(2,rbean.getRatioCd());
			  ps.setString(3,util.getKey());
			  ps.executeUpdate();
			}
		}catch(Exception e){
			log.error("RatioDAO:editRatio():Exception: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteRatio(String ratiocd) throws Exception {
		con = DBUtility.getConnection();
		String deleteCatRatioSql ="delete  from invest_ratio_category where ratiocd = ? ";
		String deleteRatioSql ="delete  from invest_ratio where ratiocd = ? ";
		log.info("RatioDAO:deleteRatio(): "+deleteRatioSql);			
		try{
			PreparedStatement pst = con.prepareStatement(deleteCatRatioSql);	
			pst.setString(1,ratiocd);
			pst.executeUpdate();
			pst.close();
			PreparedStatement ps = con.prepareStatement(deleteRatioSql);	
			ps.setString(1,ratiocd);
			ps.executeUpdate();
			ps.close();
			String query ="Update invest_ratio set validto=null where validto =(SELECT max(to_char(validto,'dd/Mon/yyyy'))validfrom FROM  invest_ratio)";
			PreparedStatement updatepst = con.prepareStatement(query);	
			updatepst.executeUpdate();
		}catch(Exception e){
			log.error("RatioDAO:deleteRatio():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}
	public RatioBean getCurrentRatio() throws Exception {
		RatioBean bean =null;
		RatioBean bean1 =new RatioBean();
		List list = new ArrayList();
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String catQuery ="select categorycd,cat.categoryid categoryid, cat.percentage percentage,cat.ratiocd ratiocd,to_char(validfrom,'dd/Mon/yyyy')validfrom from invest_sec_category sec,invest_ratio ratio,invest_ratio_category cat where sec.categoryid = cat.categoryid and cat.ratiocd = ratio.ratiocd and ratio.ratiocd=(select max(ratiocd)from invest_ratio)";
			PreparedStatement pst = con.prepareStatement(catQuery);
			rs=pst.executeQuery();
			while(rs.next()){
				bean = new RatioBean();
				bean.setCategoryCd(rs.getString("categorycd"));
				bean.setCategoryId(rs.getString("categoryid"));
				bean.setPercentage(rs.getString("percentage"));
				bean1.setRatioCd(rs.getString("ratiocd"));
				bean1.setValidFrom(rs.getString("validfrom"));
				list.add(bean);
			}
			bean1.setCatList(list);
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("RatioDAO:searchRatio():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return bean1;
	}
	
}
	   

