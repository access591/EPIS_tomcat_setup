package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epis.bean.admin.ProfileOptionBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ProfileDAO {

	Log log = new Log(ProfileDAO.class);

	private ProfileDAO() {

	}

	private static final ProfileDAO dao = new ProfileDAO();

	public static ProfileDAO getInstance() {
		return dao;
	}

	public List getProfileOptionList() throws EPISException {
		ProfileOptionBean option = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List optionList = new ArrayList();

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select po.optioncode,po.optionname,description,nvl(member,'N') member,nvl(unit,'N') unit,nvl(region,'N') region,nvl(chq,'N') chq from EPIS_PROFILE_OPTIONS po,EPIS_PROFILE_OPTIONS_MAPPING map where po.optioncode=map.optioncode(+) order by to_number(substr(po.optioncode,3,3))";
			rs = DBUtility.getRecordSet(query, st);

			while (rs.next()) {
				option = new ProfileOptionBean(rs.getString("optioncode"), rs
						.getString("optionname"), StringUtility.checknull(rs
						.getString("description")), rs.getString("member"), rs
						.getString("unit"), rs.getString("region"), rs
						.getString("chq"));
				optionList.add(option);
			}
		} catch (SQLException sqle) {
			log.error("ProfileDAO:getProfileOptionList:Exception:"
					+ sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("ProfileDAO:getProfileOptionList:Exception:"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		return optionList;
	}

	public void updateProfiles(List poList)
			throws ServiceNotAvailableException, EPISException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DBUtility.getConnection();
			String updateregionSql = "update  EPIS_PROFILE_OPTIONS_MAPPING set member=?,unit=? ,region=?,chq=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where optioncode=?";
			if (connection != null) {
				pstmt = connection.prepareStatement(updateregionSql);
				if (pstmt != null) {
					ProfileOptionBean pobean = null;
					for (int count = 0; count < poList.size(); count++) {
						pobean = (ProfileOptionBean) poList.get(count);
						if (pobean != null) {
							pstmt.setString(1, pobean.getMemberAccessFlag());
							pstmt.setString(2, pobean.getUnitAccessFlag());
							pstmt.setString(3, pobean.getRegionAccessFlag());
							pstmt.setString(4, pobean.getChqAccessFlag());
							pstmt.setString(5,StringUtility.checknull(pobean.getLoginUserId()));
							pstmt.setString(6, pobean.getOptionCode());
							pstmt.addBatch();
						}
					}
					connection.setAutoCommit(false);
					pstmt.executeBatch();
					connection.commit();
				} else {
					throw new ServiceNotAvailableException();
				}
			} else {
				throw new ServiceNotAvailableException();
			}

		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			try {
				connection.rollback();
			} catch (SQLException sqe) {
				log.error("ProfileDAO:updateProfile:SQLException"
						+ sqe.getMessage());
				throw new EPISException(sqe);
			}
			sqlex.printStackTrace();
			log.error("ProfileDAO:updateProfile:SQLException"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ProfileDAO:updateProfile:Exception" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pstmt, connection);
		}
	}

	public Map getProfile(String profileType) throws EPISException {
		ProfileOptionBean option = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		Map options = new HashMap();
		List optionList = null;
		try {
			StringBuffer query = new StringBuffer("select op.OPTIONCODE OPTIONCODE,OPTIONNAME,DESCRIPTION,");
			query.append(" path , optiontype from EPIS_PROFILE_OPTIONS op ,EPIS_PROFILE_OPTIONS_MAPPING map ");
			query.append(" where op.optioncode = map.optioncode and decode('"+profileType+"','M',member,'U',unit,'R',");
			query.append(" Region,'C',chq) = 'Y' ");
			con = DBUtility.getConnection();
			st = con.createStatement();			
			rs = DBUtility.getRecordSet(query.toString(), st);
			while (rs.next()) {
				option = new ProfileOptionBean(rs.getString("OPTIONCODE"), rs
						.getString("OPTIONNAME"), StringUtility.checknull(rs
						.getString("DESCRIPTION")),rs.getString("path"),rs.getString("optiontype"));
				if(options.containsKey(rs.getString("optiontype"))){
					optionList = (ArrayList)options.get(rs.getString("optiontype"));
				}else {
					optionList = new ArrayList();
				}	
				optionList.add(option);
				options.put(rs.getString("optiontype"),optionList);
			}
		} catch (SQLException sqle) {
			log.error("ProfileDAO:getProfileOptionList:Exception:"
					+ sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("ProfileDAO:getProfileOptionList:Exception:"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		return options;
	}

}
