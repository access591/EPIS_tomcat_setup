
/**
 * @author anilkumark
 *
 */
package com.epis.dao.investment;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.PensionCaluculation;

public class YTMVerificationDao {
	Connection con = null;

	ResultSet rs = null;

	Statement st = null;

	Log log = new Log(YTMVerificationDao.class);

	private YTMVerificationDao() {

	}

	private static final YTMVerificationDao YTMVerificationDao = new YTMVerificationDao();

	public static YTMVerificationDao getInstance() {
		return YTMVerificationDao;
	}

	public List getLetterNo() throws ServiceNotAvailableException,
			EPISException {
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		try {
			QuotationBean qbean = null;
			con = DBUtility.getConnection();

			String query = "select distinct LETTER_NO from invest_quotaion_data IQ where (select count(*) from invest_quotaion_data Q where (YTMVERIFIED='Y' or YTMNOTNEEDED='Y') and IQ.LETTER_NO=Q.LETTER_NO)=0";
			query += " order by LETTER_NO";
			if (con != null) {
				pstmt = con.prepareStatement(query);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					qbean = new QuotationBean();
					qbean.setLetterNo(rs.getString("LETTER_NO"));
					list.add(qbean);
				}
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			log.error("YTMVerificationDao:getLetterNo:SQLException:"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {

			log.error("YTMVerificationDao:getLetterNo:Exception:"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pstmt, con);
		}

		return list;

	}

	public List getLetterDetails(String letterNo)
			throws ServiceNotAvailableException, EPISException {
		List list = new ArrayList();
		Connection connection = null;
		ResultSet reusltSet = null;
		PreparedStatement pstmt = null;

		try {
			String searchQry = "select data.ARRANGERCD ARRANGERCD,ARRANGERNAME,to_char(to_date(INTEREST_DAY_MONTH,'dd/MM'),'dd/Mon') INTERESTDATE,nvl(NUMBER_OF_UNITS,0)NUMBER_OF_UNITS,nvl(FACEVALUE,0)FACEVALUE,nvl(INVESTMENT_PRICEOFFERED,0)INVESTMENT_PRICEOFFERED,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,nvl(Interest_Rate,0) Interest_Rate,investment_facevalue,(nvl(PURCHAEPRICE,0)*nvl(NUMBER_OF_UNITS,0))totalPurchasePrice,(nvl(INVESTMENT_FACEVALUE,0)*nvl(NUMBER_OF_UNITS,0))totalFacevalue,LETTER_NO,to_char(MATURITYDATE,'dd/Mon/yyyy') MATURITY_DATE,to_char(SETTLEMENTDATE,'dd/Mon/yyyy')SETTLEMENTDATE,nvl(purchaeprice,0) purchaeprice,quotationcd,SECURITY_NAME,YTMPercentage,decode(YTMVERIFIED,'Y','CHECKED','N','') verify_status,decode(YTMVERIFIED,'Y','Checked','N','Not Checked') qualified,floor(months_between(MATURITYDATE,SETTLEMENTDATE)/12) years from invest_Quotaion_data data,invest_arrangers arrangers where letter_no is not null and data.ARRANGERCD=arrangers.ARRANGERCD and upper(letter_no) like upper(nvl(?,''))||'%'";
			connection = DBUtility.getConnection();
			int sno = 0;
			if (connection != null) {
				pstmt = connection.prepareStatement(searchQry);
				if (pstmt != null) {
					pstmt.setString(1, StringUtility.checknull(letterNo));
					reusltSet = pstmt.executeQuery();
					while (reusltSet.next()) {
						++sno;

						// double
						// price=Double.parseDouble(rs.getString("purchaeprice"));
						// double
						// coupon=Double.parseDouble(rs.getString("Interest_Rate"));
						// int years=Integer.parseInt(rs.getString("years"));
						// log.info("yiels::"+rs.getDouble("purchaeprice")+rs.getDouble("Interest_Rate")+"100"+rs.getInt("years"));
						/*String YIELD = PensionCaluculation.getYield(reusltSet
								.getDouble("purchaeprice"), reusltSet
								.getDouble("Interest_Rate"), 100, reusltSet
								.getInt("years"));
						String YTM = PensionCaluculation.getYTM(reusltSet
								.getDouble("purchaeprice"), reusltSet
								.getDouble("Interest_Rate"), 100, reusltSet
								.getInt("years"));*/
						
						
						String ytmPer = StringUtility.checknull(
								reusltSet.getString("YTMPercentage")).trim();

						QuotationBean qbean = new QuotationBean();
						qbean.setSNo(new Integer(sno).toString());
						qbean.setArranger(StringUtility.checknull(reusltSet
								.getString("ARRANGERNAME")));
						qbean.setInterestDate(StringUtility.checknull(reusltSet
								.getString("InterestDate")));
						qbean.setInterestRate(StringUtility.checknull(reusltSet
								.getString("FACEVALUE")));
						qbean.setInvestmentFaceValue(StringUtility
								.checknull(reusltSet
										.getString("investment_facevalue")));
						qbean.setLetterNo(StringUtility.checknull(reusltSet
								.getString("LETTER_NO")));
						qbean.setMaturityDate(StringUtility.checknull(reusltSet
								.getString("Maturity_Date")));
						qbean
								.setPurchasePrice(StringUtility
										.checknull(reusltSet
												.getString("purchaeprice")));
						qbean
								.setQuotationCd(reusltSet
										.getString("quotationcd"));
						qbean.setSecurityName(StringUtility.checknull(reusltSet
								.getString("SECURITY_NAME")));
						qbean.setYtm(ytmPer);
						qbean.setYtmVerified(StringUtility.checknull(reusltSet
								.getString("VERIFY_STATUS")));
						
						qbean.setTotalFaceValue(StringUtility.checknull(reusltSet.getString("TOTALFACEVALUE")));
						qbean.setTotalPurchasePrice(StringUtility.checknull(reusltSet.getString("TOTALPURCHASEPRICE")));
						qbean.setDealDate(StringUtility.checknull(reusltSet.getString("DEALDATE")));
						qbean.setSettlementDate(StringUtility.checknull(reusltSet.getString("SETTLEMENTDATE")));
						qbean.setNumberOfUnits(StringUtility.checknull(reusltSet.getString("NUMBER_OF_UNITS")));
						qbean.setStatus(StringUtility.checknull(reusltSet.getString("qualified")));
						qbean.setBgcolor("");
						

						list.add(qbean);
						// log.info("..."+qbean);
					}
				} else {
					throw new ServiceNotAvailableException();
				}
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			log.error("YTMVerificationDao:getLetterDetails:SQLException:"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {

			log.error("YTMVerificationDao:getLetterDetails:Exception:"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(reusltSet, pstmt, connection);
		}

		return list;

	}

	public List searchYTM(String letterNo, String status)
			throws ServiceNotAvailableException, EPISException {
		List list = new ArrayList();
		Connection connection = null;
		ResultSet reusltSet = null;
		PreparedStatement pstmt = null;
		try {
			QuotationBean qbean = null;
			connection = DBUtility.getConnection();

			String query = "SELECT LETTER_NO,STATUS,COMPSTATUS,CREATED_DT FROM(select distinct LETTER_NO,decode(CASE  WHEN (select "
					+ " count(*) from invest_quotaion_data Q where YTMVERIFIED='Y' and IQ.LETTER_NO=Q.LETTER_NO)=0 "
					+ " THEN 'N' WHEN (select count(*) from invest_quotaion_data Q where YTMVERIFIED='Y' and "
					+ " IQ.LETTER_NO=Q.LETTER_NO)>0 THEN 'V' END  ,'N','Not Verified','V','Verified') STATUS,STATUS COMPSTATUS,CREATED_DT from "
					+ " invest_quotaion_data IQ) where upper(LETTER_NO) like '%'||upper(nvl(?,''))||'%' and "
					+ " nvl(STATUS,' ') like nvl(?,'')||'%' order by CREATED_DT desc";
			// log.info("anil"+query+" "+letterNo+" "+status);
			if (connection != null) {
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, StringUtility.checknull(letterNo));
				pstmt.setString(2, StringUtility.checknull(status));
				reusltSet = pstmt.executeQuery();
				while (reusltSet.next()) {
					qbean = new QuotationBean();
					qbean.setLetterNo(reusltSet.getString("LETTER_NO"));
					qbean.setYtmVerified(reusltSet.getString("STATUS"));
					qbean.setStatus(reusltSet.getString("COMPSTATUS"));
					list.add(qbean);
				}
				// log.info("anil list size::"+list.size());
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			log.error("YTMVerificationDao:searchYTM:SQLException:"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {

			log.error("YTMVerificationDao:searchYTM:Exception:"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(reusltSet, pstmt, connection);
		}

		return list;

	}

	public void editQuotation(QuotationBean qbean)
			throws ServiceNotAvailableException, EPISException {

		Connection connection = null;
		Statement stmt = null;

		try {
			connection = DBUtility.getConnection();
			String resetQuotationSql = "update invest_quotaion_data set YTMVERIFIED='N',YTM_UPDATED_BY='"
					+ StringUtility.checknull(qbean.getLoginUserId())
					+ "',YTM_UPDATED_DT=SYSDATE where LETTER_NO='"
					+ StringUtility.checknull(qbean.getLetterNo()) + "'";

			String updateQuotationSql = "update invest_quotaion_data set YTMVERIFIED='Y',YTM_UPDATED_BY='"
					+ StringUtility.checknull(qbean.getLoginUserId())
					+ "',YTM_UPDATED_DT=SYSDATE where QUOTATIONCD in ("
					+ StringUtility.checknull(qbean.getQuotationCd()) + ")";
			// log.info("resetQuotationSql::"+resetQuotationSql);
			// log.info("updateQuotationSql::"+updateQuotationSql);
			if (connection != null) {
				stmt = connection.createStatement();
				if (stmt != null) {
					stmt.executeUpdate(resetQuotationSql);
					// stmt=connection.createStatement();
					stmt.executeUpdate(updateQuotationSql);
				} else {
					throw new ServiceNotAvailableException();
				}
			} else {
				throw new ServiceNotAvailableException();
			}

		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			log.error("YTMVerificationDao:editQuotation:SQLException"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("YTMVerificationDao:editQuotation:Exception"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, stmt, connection);
		}
	}

	public void updateYTMFlag(QuotationBean qbean) throws  EPISException {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = DBUtility.getConnection();
			String update = "update invest_quotaion_data set YTMVERIFIED='N',YTM_UPDATED_BY='"
				+ qbean.getLoginUserId()+"',YTM_UPDATED_DT=sysdate,YTMNOTNEEDED='Y' where LETTER_NO='"+qbean.getLetterNo()+"'";
			if (connection != null) {
				stmt = connection.createStatement();
				if (stmt != null) {
					stmt.executeUpdate(update);
				} else {
					throw new ServiceNotAvailableException();
				}
			} else {
				throw new ServiceNotAvailableException();
			}

		} catch (ServiceNotAvailableException snex) {
			throw new EPISException(snex);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			log.error("YTMVerificationDao:editQuotation:SQLException"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("YTMVerificationDao:editQuotation:Exception"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, stmt, connection);
		}
	}
}
