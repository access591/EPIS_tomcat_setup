/**
 * File       : PensionService.java
 * Date       : 08/07/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2007) by the Navayuga Infotech, all rights reserved.
 */
package com.epis.services.rpfc;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import jxl.read.biff.BiffException;

import com.epis.bean.rpfc.BottomGridNavigationInfo;
import com.epis.bean.rpfc.DatabaseBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.ReadLabels;
import com.epis.bean.rpfc.SearchInfo;
import com.epis.bean.rpfc.UserBean;
import com.epis.dao.rpfc.CommonDAO;
import com.epis.dao.rpfc.FinancialDAO;
import com.epis.dao.rpfc.ImportDao;
import com.epis.dao.rpfc.PensionDAO;
import com.epis.dao.rpfc.PersonalDAO;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.Pagenation;
import com.epis.utilities.ReadXML;

public class PensionService {
	Log log = new Log(PensionService.class);

	PensionDAO pensionDAO = new PensionDAO();
	ImportDao IDAO = new ImportDao();
	FinancialDAO finaceDAO = new FinancialDAO();
	CommonUtil common = new CommonUtil();
	Pagenation paging = new Pagenation();
	DBUtility commonDB = new DBUtility();
	ArrayList pensionList = new ArrayList();
	ArrayList AAIList = new ArrayList();
	PersonalDAO pDAO = new PersonalDAO();

	public String readImportData(String fileName, String userName,
			String ipAddress,String region,String year,String month,String airportcode) throws InvalidDataException {
		log.info("PensionService:readImportData-- Entering Method");
		String readData = "";
		String lengths = "";
		try {
		    int finalSettlement=fileName.indexOf("FinalSettlement");
		    int importOpeningBalance=fileName.indexOf("AAIEPF-1");
		    int importAdjOpeningBalance=fileName.indexOf("AAIEPF-2");
		    int importMonthwiseTRData = fileName.indexOf("AAIEPF-3");
		    int cpfRecievedFromOtherOrg = fileName.indexOf("AAIEPF-4");
		    int advancePfwFinalsettlement = fileName.indexOf("AAIEPF-8");
		    int pentionContributionProcess=fileName.indexOf("PCProcess");
			int deleteTransactions = fileName.indexOf("deleteTranactionDataEast");
			int deleteDuplicateRecorsRausap = fileName
					.indexOf("deleteDuplicateRecordsinRAUSAP");
			int autoMappingAllRegion0910=fileName.indexOf("mapping 09-10");
			int updateFinaceDatahavingCpfaccnoBlank = fileName
					.indexOf("Transaction_Data_CPFACCNO_NULL");
			// needtoGenerate new PFIDS (i.e missing resigned case)
			int generateNewPFIDs = fileName.indexOf("needtoGeneratedPfid");
			int updateEmployeenametoMasterFromTrans = fileName
					.indexOf("updateMasterEmployeename");
			int kamaldeep = fileName.indexOf("kamaldeep");
			int officeComplexDparrears0809=fileName.indexOf("dparrear_CHQ");
			int igiCpfaccnoUpdate = fileName.indexOf("IGICpfaccnoChangetoD-00");
			int importArr9697Count = fileName.indexOf("CHQNAD_ARREAR_MAR_9697");
			int igiCargoArrearEmoluemntUpdates = fileName
					.indexOf("IGICargoArrearUpdates");
			int westRegionEmoluemntUpdates = fileName
					.indexOf("wastRegionEmoluemntUpdates");
			int eastRegionEmoluemntUpdates = fileName
					.indexOf("eastRegionEmoluemntUpdates");
			int importArrCount = fileName.indexOf("arrCountCHQNAD");
			int genrtPFID = fileName.indexOf("GeneratePFIDS");
			int arrearTable = fileName.indexOf("CHQNADarrear");
			int eastRegionEmployeeNamesUpdate = fileName
					.indexOf("EastRegionNameUpdates");
			int northEastRegionEmployeeNamesUpdate = fileName
					.indexOf("NorthEastRegionNameUpdates");
			int SouthGenderUpdates = fileName.indexOf("UpdateGenderforSouth");
			int personalAllRegionsIndex = fileName
					.indexOf("Uploading_Pension_Option");
			int consolidateIndex = fileName.indexOf("FinallAAI");
			int deleteFinaceDuplicateData = fileName
					.indexOf("deleteFinaceDuplicateData");
			int updateEmployeePersonalInfo = fileName
					.indexOf("updateEmployeePersonal");
			int processinPersonalInfoMissingRecords = fileName
					.indexOf("PersonalInfoMissingRecords");
			int updateMasterNametoTransaction = fileName
					.indexOf("updateMasternametoTr");
			int westMasterdataIndex = fileName.indexOf("West");
			int eastMasterdataIndex = fileName.indexOf("East");
			int chqIadMasterData = fileName.indexOf("chqIadAccess");
			int chqNadMasterData = fileName.indexOf("chqNadAccess");
			int chqIadOfficeComplexData = fileName
					.indexOf("chqIadOfficeComplex");
			int rauSapMasterData = fileName.indexOf("RauSapMasterData");
			int srFinancialDataIndex = fileName.indexOf("SRFINANCIALDATA");
			int erFinancialDataIndex = fileName.indexOf("ERFINANCIALDATA");
			int nrFinancialDataIndex = fileName.indexOf("NR");
			int wrFinancialDataIndex = fileName.indexOf("WR");
			int nerFinancialDataIndex = fileName.indexOf("NER");
			int chqNadFinancialDataIndex = fileName.indexOf("CHQ");
			int kolkattaProjIadFinancialDataIndex = fileName
					.indexOf("KOLKATAPRJ");
			int kolkattaIadFinancialDataIndex = fileName.indexOf("KOLKATA");
			int AmritsarIADFinancialDataIndex = fileName.indexOf("AmritsarIAD");
			int chennaiIADFinancialDataIndex = fileName.indexOf("Chennai_IAD");
			int igiCargoIadDataIndex = fileName.indexOf("IGICargoIad");
			int xmlFileIndex = fileName.indexOf("tree");
			int readPersonal = fileName.indexOf("Personal");
			int readCSIAFinanceDataIndex = fileName
					.indexOf("CSAIFINANCIALDATA");
			int readDpoIADFinanceDataIndex = fileName
					.indexOf("DPOFINANCIALDATA");
			int readCapIADFinanceDataIndex = fileName
					.indexOf("CAPIADFINANCIALDATA");
			int readVOTVIADFinanceDataIndex = fileName
					.indexOf("VOTVIADFINANCIALDATA");
			int readIGIIADFinanceDataIndex = fileName.indexOf("IGIIAD");
			int readRauSapCRSDFinanceDataIndex = fileName
					.indexOf("RAUSAP_CRSD");
			int readRauSapDRCFinanceDataIndex = fileName.indexOf("RAUSAP_DRC");
			int readRauSapEMOFinanceDataIndex = fileName.indexOf("RAUSAP_EMO");
			int readRauSapFinanceDataIndex = fileName.indexOf("RAUSAP");
			int readOfficeComplexIADFinanceDataIndex = fileName
					.indexOf("OfficeComplexIAD");
			int pensionCalculation = fileName.indexOf("PensionCalculation");
			int importIgiCargo0809Data = fileName
					.indexOf("IGICargo08-09TransactionData");
			
			// for Proportioned Calculation
			int deductSouth9697ArrearData = fileName
					.indexOf("SouthArrearDeduct96-97.xls");
			int deductWest9697ArrearData = fileName
					.indexOf("WestArrearDeduct96-97.xls");
			int deductChqnad9697ArrearData = fileName
					.indexOf("ChqnadArrearDeduct96-97.xls");
			int importSouth9697ArrearData = fileName
					.indexOf("SouthArrear96-97");
			int importWest9697ArrearData = fileName.indexOf("WestArrear96-97");
			int importChqnad9697ArrearData = fileName
					.indexOf("ChqnadArrear96-97");

			int importArr2001Count = fileName.indexOf("Rausap_ARREAR");
			int importIgiArr2001Count = fileName.indexOf("IGI_ARREAR2000-2001");
			int importEastArr9697Count = fileName.indexOf("East_ARREAR96-97");
			int importNorthArr9697Count = fileName.indexOf("North_ARREAR96-97");
			int importNorthEastArr9697Count = fileName
					.indexOf("NortEastARREAR96-97");
			int importOfficeComplexArr9697=fileName.indexOf("OfficeComplexArrear1");
			int importIGIArr9697=fileName.indexOf("IGIArrear9697Update");
			
			int importKolIADArr9697Count = fileName
					.indexOf("KolIAD_ARREAR96-97");
			// for importing 08-09 Transaction Data
			int importCHQNAD0809Data = fileName.indexOf("MONTHLY_PAYMENTS_CHQNAD");
			int importCHQNAD0809DataZeroCases = fileName.indexOf("MONTHLY_PAYMENTS_CHQNAD");
			int importTrivandrum0809Data = fileName.indexOf("Trivandrum 08-09");
			int importAmritsar0809Data = fileName.indexOf("AmritsarIAD 08-09");
			int importNER0809Data = fileName.indexOf("missingmonths");
			int importNSCBI0809Data = fileName.indexOf("NSCBI 08-09 DATA");
			int importWR0809Data = fileName.indexOf("WR 08-09 Data");
			int importOfficeComplex0809Data = fileName.indexOf("MONTHLY_PAYMENTS_CHQIAD1");
			int importOfficeComplex0809DataArrears = fileName.indexOf("MONTHLY_PAYMENTS_CHQIAD_ARREARS");
			int importIgiIad0809Data = fileName.indexOf("MONTHLY_PAYMENTS_CHQIAD_IGIIAD");
			int importChennaiIad0809Data = fileName.indexOf("MONTHLY_PAYMENTS_CHQIAD_CHENNAI");
			int writePfidKolkata0809Data = fileName.indexOf("Kolkata 08-09 Data");
			int writePfidNORTH0809 = fileName.indexOf("NORTH 08-09");
			int writePfidOperationOfficeComplex0809 = fileName.indexOf("OPERATIONAL OFFICE08-09");
			int importKolkataIad0809Data = fileName.indexOf("Kolkata 08-09 Data");
			int importCAPIAD0809Data = fileName.indexOf("CHQIAD_CAPIAD_08-09");
            int importNorth0809Data = fileName.indexOf("NORTH PFID 08-09");
            int importSouth0809Data = fileName.indexOf("SR 08-09 Data");
            int writePfidRAUSAP0809Data = fileName.indexOf("RAUSAP DATA 2008-09");
            int importRAUSAP0809Data = fileName.indexOf("RAUSAP DATA 2008-09");
            int importRAUSAP0809DataZeroCases = fileName.indexOf("RAUSAP DATA 2008-09 zerocases");
            int importRAUSAP0809DataArrears = fileName.indexOf("RAUSAP DATA 2008-09 DP ARREAR");
            int writePfidAmrithser0809Data = fileName.indexOf("AmrithserIAD 08-09 DATA");
            int writePfidCSIA0809Data = fileName.indexOf("CSIA DATA 2008-2009");
            int importEast0809Data = fileName.indexOf("MONTHLY_PAYMENTS_EastRegion");
            int writePfidChennaiIad0809 = fileName.indexOf("MONTHLY_PAYMENTS_CHQIAD_WRITEPFID");
            int writePfidchqnadOBData = fileName.indexOf("OB08-09IGI");  
            int importCSIA0809Data=fileName.indexOf("MONTHLY_PAYMENTS_CSIAIAD");
           //newly created pfids
            int writePfidfornewEMP0809Data = fileName.indexOf("MONTHLY_OTHER_CHQNAD_NEWPFIDS");
           //delete newly created pfids 
            int deleteRetiredEmployees = fileName.indexOf("MONTHLY_OTHER_CHQNAD_DELETE");
            // importing loans and advances
			int loansTable = fileName.indexOf("loan");
			// int chqnadVPFUpdate=fileName.indexOf("CHQNAD");
			int advanceTable = fileName.indexOf("advances");
			int IgiCargoadvanceTable = fileName.indexOf("IGICargoAdvances");
			int eastadvanceTable = fileName.indexOf("EastAdvances");
			int NscbiAdvanceTable = fileName.indexOf("NSCBIAdvances");
			int CapIadAdvanceTable = fileName.indexOf("CAPIADAdvance");
			int IgiCargoPFWTable = fileName.indexOf("IGICargoPFW");
			int eastPFWTable = fileName.indexOf("EastPFW");
			int NscbiPFWTable = fileName.indexOf("NSCBIPFW");
			int CAPIADPFWTable = fileName.indexOf("CAPIADPFW");
			int westsadvanceTable = fileName.indexOf("WestAdvances");
			int writeOfficecomplexadvanceTable = fileName.indexOf("MONTHLY_OTHER_CHQIAD_ADVANCEPFID");
			int officeComplexadvanceTable = fileName.indexOf("MONTHLY_RECOVERY_CHQIAD");
			int officeComplexPFWTable = fileName.indexOf("MONTHLY_RECOVERY_CHQIAD_LOANS");
			int northAdvanceTable = fileName.indexOf("MONTHLY_RECOVERY_NorthRegion");
			int northPFWTable     = fileName.indexOf("MONTHLY_RECOVERY_NorthRegion_loans");
			int southAdvanceTable = fileName.indexOf("MONTHLY_RECOVERY_SouthRegion");
			int SouthPFWTable     = fileName.indexOf("MONTHLY_RECOVERY_SouthRegion_loans");
			
			int trivendrumAdvancesTable = fileName.indexOf("TrivendrumAdvances");
			int rausapAdvancesTable = fileName.indexOf("RAUSAP_Advances");
			int trivendrumPFWTable = fileName.indexOf("TrivendrumPFW");
			int rausapPFWTable = fileName.indexOf("RAUSAP_LOANS");
			int westPFWTable = fileName.indexOf("WR_PFW_FP");
			int NorthEastAdvancesTable = fileName.indexOf("North-EastAdvances");
			int NorthEastPFWTable = fileName.indexOf("North-EastPFW");
		    int chqnadAdvanceTable = fileName.indexOf("MONTHLY_RECOVERY_CHQNAD1");
			int chqnadPFWTable = fileName.indexOf("MONTHLY_RECOVERY_CHQNAD_PFW");
				
			//IMPORTING 08-09 ARREARS/ Opening Balances
			int importCHQNADArrear0809Count = fileName.indexOf("MONTHLY_OTHER_CHQNAD_ADVANCES");
			int importCHQNADOB0809Count = fileName.indexOf("MONTHLY_OTHER_CHQNAD_OB");
			int importCHQIADOB0809Count = fileName.indexOf("MONTHLY_OTHER_CHQIAD_OB");
			int importNSCBIOB0809Count = fileName.indexOf("NSCBI OB");
			int importCHQIADTVROB0809Count = fileName.indexOf("MONTHLY_OTHER_CHQIAD_TVROB");
			int importCHQIADOBCAP0809Count = fileName.indexOf("MONTHLY_OTHER_CHQIAD_CAPOB");
			int importMIAL = fileName.indexOf("MONTHLY_OTHER_CHQIAD_MIAL_CPFACCNO");
			
			// AutoMapping 08-09
			//int north0809AutoMapping=fileName.indexOf("NorthRegionAutoMapping");
			int chqnad0809AutoMapping=fileName.indexOf("MONTHLY_OTHER_CHQNAD_automapping");
			int writeCHQNADdoboption = fileName.indexOf("MONTHLY_OTHER_CHQNAD_doboption");
			int north0809AutoMapping=fileName.indexOf("AutoMapping0809-");
			//create new PFID's
			int createNewPFIDS = fileName.indexOf("CREATENEWPFID");
			//int createNewPFIDS = fileName.indexOf("MONTHLY_OTHER_CHQNAD");
			int createNewNRPFIDS = fileName.indexOf("MONTHLY_OTHER_NR_CREATE_NEW_PFIDS");
			int createPFIDS19100 = fileName.indexOf("MONTHLY_OTHER_allregions_PENSION_PERSONAL");
			int importPensionOptUpdate = fileName.indexOf("FORM3_PENSION_OPTION_CHANGES");
			int updateCPFACCNOALLRGNS = fileName.indexOf("FORM3_CPFACCNO_CHANGES");
			int updateOtherCPFACCNOALLRGNS = fileName.indexOf("FORM3_OTHERTHAN_CPFACCNO_CHANGES");
			int insertNomineeDtls=fileName.indexOf("Nominee_Info_dtls");
			int createEmpUtilizeOldPFIDS= fileName.indexOf("MONTHLY_OTHER_PERSONAL_ALL_REGIONS");
		try{
			if(insertNomineeDtls!=-1){
				readData = common.readExcelSheet(fileName);
				 IDAO.insertNomineeDtls(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}if(finalSettlement!=-1){
				readData = common.readExcelSheet(fileName);
				 IDAO.readXLSFinalSettlement(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}
			else if (createEmpUtilizeOldPFIDS!=-1) {
				 readData = common.readExcelSheet(fileName);
			     IDAO.createEmpUtilzeOldPfid(readData,region, userName,ipAddress);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}
			if (fileName.indexOf(".mdb") != -1) {
				pensionDAO.readACCESSData(fileName);
			} else if (updateMasterNametoTransaction != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.updateMasterNmtoTrans(readData);
				lengths = "Data Imported SuccessFully";

			} else if (pensionCalculation != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.pensionCalculation(readData);
				lengths = "PensionCalculation updated SuccessFully";
			}else if (importOpeningBalance!=-1 ||importNSCBIOB0809Count !=-1 ||importCHQNADOB0809Count != -1 ||importCHQIADOB0809Count !=-1 ||importCHQIADTVROB0809Count!=-1 ||importCHQIADOBCAP0809Count!=-1) {
				readData = common.readExcelSheet(fileName);
				String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				IDAO.importOpeningBalance(readData,region,userName,	ipAddress,fileName1,year,month,airportcode);
				lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			} else if(importAdjOpeningBalance!=-1){
				readData = common.readExcelSheet(fileName);
				String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				IDAO.importAdjOpeningBalance(readData,region,userName,	ipAddress,fileName1,year,month,airportcode);
				lengths = fileName1+ " " + "Sheet Imported SuccessFully";
		
			}else if (importEastArr9697Count != -1
					|| importKolIADArr9697Count != -1
					|| importNorthArr9697Count != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				// IDAO.importEastArr9697(readData);
				 IDAO.importNorthArr9697(readData);
				//IDAO.importChqnadArr9697(readData);
				lengths = "Data Inserted SuccessFully";
			} 
			else if(importMonthwiseTRData!=-1){
	            log.info("inside importMonthwiseTRData") ;
				readData = common.readExcelSheet(fileName);
				try{
					 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
					 String message= IDAO.importMonthlyTrData(readData,region,userName,	ipAddress,fileName1,year,month,airportcode);
					 log.info(""+fileName.indexOf("/"));
					 lengths = fileName1+ " " + "Sheet Imported SuccessFully." +" Total Inserted Records - "+message;
				}catch(InvalidDataException ex){
					log.info("In Service"+ex.getMessage());
					throw ex;
				}
			}else if(pentionContributionProcess!=-1){
				readData = common.readExcelSheet(fileName);
				try{
					 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
					  IDAO.pentionContributionProcess(readData,region,userName,ipAddress,fileName1,year,month);
					 log.info(""+fileName.indexOf("/"));
					 lengths = fileName1+ " " + "Sheet Imported SuccessFully." +" Total Inserted Records - ";
				}catch(Exception ex){
					log.info("In Service"+ex.getMessage());
					
				}
			}
			else if(cpfRecievedFromOtherOrg!=-1){
	            log.info("cpfRecievedFromOtherOrg ") ;
				readData = common.readExcelSheet(fileName);
				try{
					 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
					 String message= IDAO.cpfRecievedFromOtherOrg(readData,region,userName,	ipAddress,fileName1,year,month,airportcode);
					// String message= IDAO.importMonthlyTrData(readData,region,userName,	ipAddress,fileName1,year,month,airportcode);
					 log.info(""+fileName.indexOf("/"));
					 lengths = fileName1+ " " + "Sheet Imported SuccessFully." +" Total Inserted Records - "+message;
				}catch(InvalidDataException ex){
					log.info("In Service"+ex.getMessage());
					throw ex;
				}
			}else if(advancePfwFinalsettlement!=-1){
	           readData = common.readExcelSheet(fileName);
				try{
					 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
					 IDAO.advancePfwFinalsettlement(readData,region,userName,ipAddress,fileName1,year,month,airportcode);
					 log.info(""+fileName.indexOf("/"));
					 lengths = fileName1+ " " + "Sheet Imported SuccessFully.";
				}catch(InvalidDataException ex){
					log.info("In Service"+ex.getMessage());
					throw ex;
				}
			}else if (deductSouth9697ArrearData != -1
					|| deductWest9697ArrearData != -1
					|| deductChqnad9697ArrearData != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.deductSouth9697ArrearData(readData);
				lengths = "Arrear data deducted SuccessFully";

			} else if (importSouth9697ArrearData != -1
					|| importWest9697ArrearData != -1
					|| importChqnad9697ArrearData != -1 ||officeComplexDparrears0809!=-1) {
				readData = common.readExcelSheet(fileName);
				try{
				// IDAO.importSouthArr9697(readData);
					IDAO.importOfficeComplexDparrears(readData, userName, ipAddress);
				}catch(Exception e){
					
				}
				//IDAO.importChqnadArr9697(readData);
				lengths = "Arrear data deducted SuccessFully";
			} else if (deleteTransactions != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.deleteTransactions(readData);
				lengths = "Records deleted SuccessFully";

			} else if (deleteDuplicateRecorsRausap != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.deleteDuplicateRecorsRausap(readData, userName, ipAddress);
				lengths = "duplicate records deleted in RAUSAP";

			} else if (generateNewPFIDs != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.generateNewPFIDs(readData, userName, ipAddress);
				lengths = "New PfIDs Generated ";

			} else if (updateEmployeenametoMasterFromTrans != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.updateEmployeenametoMasterFromTrans(readData, userName,
						ipAddress);
				lengths = "EmployeeNames updated ";

			} else if (updateFinaceDatahavingCpfaccnoBlank != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.updateFinaceDatahavingCpfaccnoBlank1(readData, userName,
						ipAddress);
				lengths = "FinaceData updated with cpfaccno ,Records having Cpfaccno Blank ";

			} else if (igiCpfaccnoUpdate != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.igiCpfaccnoUpdate(readData);
				lengths = "Cpfacccno updated SuccessFully";

			} else if (genrtPFID != -1) {
				PersonalDAO personal = new PersonalDAO();
				personal.autoPendingProcessingPersonalInfo("%-Sep-2007",
						"August-2007", userName, ipAddress);

			}
			/*
			 * else if (chqnadVPFUpdate != -1) { readData =
			 * common.readExcelSheet(fileName); int length = readData.length();
			 * IDAO.chqnadVPFUpdate(readData); lengths = "Data Updated
			 * SuccessFully"; }
			 */else if (loansTable != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.loansTable(readData);
				IDAO.loansPrincipleUpdate(readData);
				lengths = "Data Inserted SuccessFully";

			} else if (importIgiCargo0809Data != -1
					|| importCHQNAD0809Data != -1 || importNER0809Data != -1
					|| importTrivandrum0809Data != -1 || importWR0809Data != -1 ||importOfficeComplex0809Data!=-1 ||importOfficeComplex0809DataArrears!=-1
					||writePfidKolkata0809Data !=-1 ||writePfidNORTH0809 !=-1||importKolkataIad0809Data!=-1 ||
					importNorth0809Data!=-1 ||importSouth0809Data!=-1 || writePfidRAUSAP0809Data!=-1 
					||importRAUSAP0809Data!=-1 ||importRAUSAP0809DataArrears!=-1 ||writePfidOperationOfficeComplex0809!=-1 ||writePfidAmrithser0809Data!=-1 ||
					writePfidChennaiIad0809!=-1 ||writePfidCSIA0809Data!=-1 ||writePfidchqnadOBData!=-1 ||writeOfficecomplexadvanceTable!=-1
					||writePfidfornewEMP0809Data!=-1 ||writeCHQNADdoboption!=-1 ||importIgiIad0809Data!=-1 ||importChennaiIad0809Data!=-1 ||importEast0809Data!=-1 ||importAmritsar0809Data!=-1 ||importCSIA0809Data!=-1 ||importCAPIAD0809Data!=-1) {
				readData = common.readExcelSheet(fileName);
				
				try{
				// IDAO.importIgiCargo0809Data(readData);
				// IDAO.importCHQNAD0809Data(readData);
				// IDAO.importTrivendrum0809Data(readData);
				// IDAO.importAmritserIAD0809Data(readData);
				// IDAO.importOfficeComplex0809Data(readData);
				// for writing pfIDS to excelSheet
				// IDAO.importIgiIad0809Data(readData);	
				// IDAO.importChennaiIad0809Data(readData);
			    // IDAO.writeIgiCargo0809DataPFID(readData);
				// IDAO.importWR0809Data(readData);
			    // IDAO.importRAUSAP0809Data(readData);
				// IDAO.importNorth0809Data(readData);
				// IDAO.importSouth0809Data(readData);
			    //   IDAO.importKolkataIad0809Data(readData);
				 IDAO.importNER0809Data(readData,userName,ipAddress);
				   String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				   lengths = fileName1+ " " + "Sheet Imported SuccessFully";
				}catch (Exception e) {
		            log.printStackTrace(e);
		            throw new InvalidDataException(e.getMessage());
		        } 
				
			}else if(north0809AutoMapping!=-1 ||chqnad0809AutoMapping!=-1 ||autoMappingAllRegion0910!=-1){
				readData = common.readExcelSheet(fileName);
				 IDAO.autoMapping0809Data(readData,userName,	ipAddress);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				   lengths = fileName1+ " " + "Sheet Imported SuccessFully";
				//IDAO.chqnad0809AutoMapping(readData);
			}else if (eastRegionEmoluemntUpdates != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.eastRegionEmoluemntUpdates(readData);
				lengths = "Data updated SuccessFully";
			} else if (westRegionEmoluemntUpdates != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.westRegionEmoluemntUpdates(readData);
				lengths = "Data updated SuccessFully";

			} else if (igiCargoArrearEmoluemntUpdates != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.igiCargoArrearEmoluemntUpdates(readData);
				lengths = "Data updated SuccessFully";

			}else if (arrearTable != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.arrearTable(readData);
				String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				lengths = fileName1+ " " + "Sheet Imported SuccessFully";

			} else if (importArrCount != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.importArrCountCHQNAD(readData);
				lengths = "Data Inserted SuccessFully";

			} else if (eastRegionEmployeeNamesUpdate != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.eastRegionEmployeeNamesUpdate(readData);
				lengths = "Data Imported SuccessFully";
			} else if (northEastRegionEmployeeNamesUpdate != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.northEastRegionEmployeeNamesUpdate(readData);
				String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}else if (CapIadAdvanceTable !=-1 || NscbiAdvanceTable!=-1 || eastadvanceTable!=-1 ||advanceTable != -1 || IgiCargoadvanceTable != -1 || westsadvanceTable!=-1 ||NorthEastAdvancesTable!=-1 ||chqnadAdvanceTable!=-1 ||northAdvanceTable!=-1 || trivendrumAdvancesTable != -1 ||officeComplexadvanceTable!=-1 || southAdvanceTable!=-1||rausapAdvancesTable!=-1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				// IDAO.advanceTable(readData);
				   IDAO.IgiCargoAdvanceTable(readData,region);
				  String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				   lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			} else if (CAPIADPFWTable!=-1 ||NscbiPFWTable !=-1 ||eastPFWTable !=-1 || IgiCargoPFWTable != -1 ||trivendrumPFWTable != -1 ||rausapPFWTable!=-1||NorthEastPFWTable!=-1 ||westPFWTable!=-1 ||chqnadPFWTable!=-1 ||officeComplexPFWTable!=-1 ||northPFWTable!=-1 ||SouthPFWTable!=-1) {
				 readData = common.readExcelSheet(fileName);
				 int length = readData.length();
				 IDAO.IgiCargoPFwTable(readData,region);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}else if (createNewPFIDS!=-1) {
				 readData = common.readExcelSheet(fileName);
			     IDAO.createNewPFIDS(readData,region, userName,ipAddress);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}else if (createNewNRPFIDS!=-1) {
				 readData = common.readExcelSheet(fileName);
			     IDAO.createNewPFIDS(readData,region, userName,ipAddress);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}else if (importMIAL!=-1) {
				 readData = common.readExcelSheet(fileName);
			     IDAO.updateCPFAccnoMIAL(readData,region, userName,ipAddress);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}else if (createPFIDS19100!=-1) {
				 readData = common.readExcelSheet(fileName);
			     IDAO.createNewPFIDSFrom19100(readData,region, userName,ipAddress);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}else if(importPensionOptUpdate != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.importPensionOptUpdate(readData);
				lengths = "Cpfacccno updated SuccessFully";
			}else if(updateCPFACCNOALLRGNS != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.importCPFACCNOUpdate(readData);
				lengths = "Cpfacccno updated SuccessFully";
			}else if(updateOtherCPFACCNOALLRGNS != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.UpdateOtherCPFACCNOFields(readData);
				lengths = "Cpfacccno updated SuccessFully";
			}else if (SouthGenderUpdates != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.SouthGenderUpdates(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}else if (processinPersonalInfoMissingRecords != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.processinPersonalInfoMissingRecords(readData);
				lengths = "Data Imported SuccessFully";
			} else if (deleteFinaceDuplicateData != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.deleteFinaceDuplicateData(readData);
				lengths = "Data Deleted SuccessFully";
			} else if (updateEmployeePersonalInfo != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.updateEmployeePersonalInfo(readData);
				lengths = "Data Updated SuccessFully";
			} else if (consolidateIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSData(readData);
				int sizeEmployee = pensionDAO
						.totalEmployeeData("employee_info");
				lengths = length + "," + sizeEmployee;
			} else if (readPersonal != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				 pensionDAO.readXLSPersonal(readData);
				pensionDAO.readXLSPersonalFhnameGender(readData);
				// IDAO.readXLSPersonalForm3updateSeparation(readData);
				int sizeEmployee = pensionDAO
						.totalEmployeeData("employee_info");
				lengths = length + "," + sizeEmployee;
			} else if (westMasterdataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSWestData(readData);
			} else if (importNorthEastArr9697Count != -1 ||importOfficeComplexArr9697!=-1 ||importIGIArr9697!=-1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.importIGIArr9697(readData, userName, ipAddress);
				//IDAO.importOfficeComplexArr9697(readData);
				lengths = "Data Inserted SuccessFully";
			} else if (eastMasterdataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSEastPersonalData(readData);
			} else if (chqIadMasterData != -1 || rauSapMasterData != -1
					|| chqIadOfficeComplexData != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSChqIadData(readData);
			} else if (chqNadMasterData != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSChqNadData1(readData);
				lengths = "Data Updated SuccessFully";

			} else if (importArr9697Count != -1 || importArr2001Count != -1
					|| importIgiArr2001Count != -1 ||importCHQNADArrear0809Count!=-1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.importArrCountCHQNAD9697(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";

			} else if (igiCargoIadDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSFinalcialIGICargoIADCPF(readData);
			} else if ((srFinancialDataIndex != -1)
					|| (erFinancialDataIndex != -1)) {
				readData = common.readExcelSheet(fileName);
				IDAO.readXLSSouthFinalcialData(readData);
				//IDAO.readXLSFinalcialData(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			} else if (nrFinancialDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.readXLSFinalcialDataNRegion(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";

			}else if (wrFinancialDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.readXLSFinalcialDataWRegion(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}else if (nerFinancialDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.readXLSFinalcialDataNERegion(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			}

			 else if (kolkattaProjIadFinancialDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				IDAO.readXLSFinalcialDataCHQIADKolProjRegion(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";

			} else if (kolkattaIadFinancialDataIndex != -1) {
				readData = common.readExcelSheet(fileName);

				IDAO.readXLSFinalcialDataCHQIADKolRegion(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";

			} else if (AmritsarIADFinancialDataIndex != -1) {
				readData = common.readExcelSheet(fileName);

				IDAO.readXLSFinalcialDataAmritsarIADCPF(readData);
			} else if (chennaiIADFinancialDataIndex != -1) {
				readData = common.readExcelSheet(fileName);

				IDAO.readXLSFinalcialDataCHQIADChennaiRegion(readData);
			} else if (readCSIAFinanceDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSFinalcialDataCSIAIAD(readData);
			}

			else if (readDpoIADFinanceDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSFinalcialDataDPOIAD(readData);
			} else if (readCapIADFinanceDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSFinalcialDataCAPIAD(readData);
			} else if (readVOTVIADFinanceDataIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSFinalcialDataVOTVIAD(readData);
			}
			// Suneetha
			else if (readIGIIADFinanceDataIndex != -1) {
				readData = common.readExcelSheet(fileName);

				// IDAO.readXLSFinalcialIGIIADCPF(readData);
				IDAO.readXLSFinalcialIGIIADCPF1995to2000(readData, userName,
						 ipAddress);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
			} else if (readOfficeComplexIADFinanceDataIndex != -1) {
				readData = common.readExcelSheet(fileName);

				IDAO.readXLSFinalcialDataOfficeComplex(readData);
			}else if(deleteRetiredEmployees!=-1){
				readData = common.readExcelSheet(fileName);
				 IDAO.deleteRetiredEmployees(readData);
				 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
				 lengths = fileName1+ " " + " records deleted SuccessFully";
			}else if (chqNadFinancialDataIndex != -1) {
				readData = common.readExcelSheet(fileName);

				// IDAO.readXLSFinalcialDataChqNad(readData);
				try{
					// IDAO.readXLSFinalcialDataChqNadKamalDeep(readData);
					 IDAO.readXLSFinalcialDataChqNadAllahabad01020203(readData);
					 String fileName1=fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length());
					 lengths = fileName1+ " " + "Sheet Imported SuccessFully";
				}catch(InvalidDataException e){
					throw e;
				}
				
				
				// finaceDAO.readXLSFinalcialDataChqNadAllahabad(readData);
				//finaceDAO.readXLSFinalcialDataChqNadAllahabad20062007(readData
				// );
				// IDAO.readXLSFinalcialDataChqNadAllahabad20072008(readData);
				IDAO.readXLSFinalcialDataChqNadAllahabad01020203(readData);
			}

			else if ((readRauSapCRSDFinanceDataIndex != -1)
					|| (readRauSapDRCFinanceDataIndex != -1)
					|| (readRauSapEMOFinanceDataIndex != -1)
					|| (readRauSapFinanceDataIndex != -1)) {
				readData = common.readExcelSheet(fileName);

				IDAO.readXLSFinalcialDataRAUSAPMissing(readData);
				// IDAO.readXLSFinalcialDataRAUSAP200708(readData);
			}

			else if (xmlFileIndex != -1) {
				log.info("FileName" + fileName);
				ReadXML readXml = new ReadXML();
				readXml.getRegionalList(fileName);
				int count = readXml.countAirportSize();
				lengths = Integer.toString(count);
			} else if (personalAllRegionsIndex != -1) {
				readData = common.readExcelSheet(fileName);
				int length = readData.length();
				IDAO.readXLSPersonalData(readData, userName, ipAddress);
				int sizeEmployee = pensionDAO
						.totalEmployeeData("employee_info");
				// lengths = length + "," + sizeEmployee;
				lengths = "Data Updated SuccessFully";
			} else {
				System.out.println("FileName" + fileName);
				readData = common.readTextfile(fileName);
				// pensionDAO.readXlsInvalidData(readData);
				// int invalidData =
				// pensionDAO.totalEmployeeData("employee_info_invalidData");
				// int invalidTextDataSize = readData.length();
				// lengths = invalidTextDataSize + "," + invalidData;

				IDAO.readXLSData(readData);
				int sizeEmployee = pensionDAO
						.totalEmployeeData("employee_info");

				File file = new File(fileName);
				boolean success = file.delete();
				if (success) {
					System.out.println("File was deleted.");
				} else {
					// File was not deleted
					System.out.println("File was not deleted.");
				}
				// readData = common.readExcelSheet(fileName);
				// pensionDAO.readXLSPensionData(readData);
				// pensionDAO.readXLSPensionData(readData);
			}
		}
			 catch (InvalidDataException e) {
		            log.printStackTrace(e);
		            // TODO Auto-generated catch block
		            throw new InvalidDataException(e.getMessage());
		      //   String   message = e.getMessage();

		        }

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return lengths;
	}

	public ArrayList searchRecords(EmpMasterBean bean) {
		ArrayList list = pensionDAO.searchRecords(bean);
		return list;
	}

	public SearchInfo searchPensionDataMissingMonth(DatabaseBean databean,
			SearchInfo searchInfo, int gridLength) throws Exception {
		int startIndex = 0;
		ArrayList hindiData = new ArrayList();
		System.out.println("searchInfo.getNavigation()"
				+ searchInfo.getNavigation());
		startIndex = 1;
		hindiData = pensionDAO.searchPensionDataMissingMonth(databean,
				startIndex, gridLength);
		int totalRecords = pensionDAO.totalData(databean);
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		bottomGridNavigationInfo = paging.searchPagination(totalRecords,
				startIndex, gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public SearchInfo searchPensionMaster(EmpMasterBean bean,
			SearchInfo searchInfo, boolean flag, int gridLength,
			String empNameCheak) throws Exception {
		int startIndex = 0;
		ArrayList empInfo = new ArrayList();
		System.out.println("searchInfo.getNavigation()"
				+ searchInfo.getNavigation());
		startIndex = 1;

		empInfo = pensionDAO.SearchPensionMasterAll(bean, startIndex, flag,
				gridLength, empNameCheak);
		log.info("emp list size " + empInfo.size());
		int totalRecords = pensionDAO.totalPensionMasterData(bean, flag,
				empNameCheak);
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		bottomGridNavigationInfo = paging.searchPagination(totalRecords,
				startIndex, gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(empInfo);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public void updatePensionCheck(String cpfaccno, String empSerailNumber,
			String pensionNumber) throws Exception {
		pensionDAO.updatePensionCheck(cpfaccno, empSerailNumber, "update",
				pensionNumber);

	}

	public SearchInfo searchRecordsbyDobandName(EmpMasterBean bean,
			SearchInfo searchInfo, int gridLength) throws Exception {
		int startIndex = 1;
		String navButton = "";
		ArrayList empInfo = new ArrayList();
		System.out.println("searchInfo.getNavigation()"	+ searchInfo.getNavigation());
		if (searchInfo.getNavButton() != null) {
			navButton = searchInfo.getNavButton();
		}

		if (new Integer(searchInfo.getStartIndex()) != null) {
			startIndex = searchInfo.getStartIndex();
		}
		int totalRecords = pensionDAO.totalPensionMasterDatafordobandName(bean,
				"true","");
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		startIndex = paging.getPageIndex(navButton, startIndex, totalRecords,
				gridLength);
		empInfo = pensionDAO.searchRecordsbyDobandName(bean, startIndex,
				gridLength, "true", "");
		log.info("emp list size " + empInfo.size());

		bottomGridNavigationInfo = paging.searchPagination(totalRecords,
				startIndex, gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(empInfo);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);

		return searchInfo;

	}

	public SearchInfo getEmployeeList(EmpMasterBean bean,
			SearchInfo searchInfo, int gridLength, String ajaxCall)
			throws Exception {
		int startIndex = 1;
		String navButton = "";
		ArrayList empInfo = new ArrayList();
		if (searchInfo.getNavButton() != null) {
			navButton = searchInfo.getNavButton();
		}

		if (new Integer(searchInfo.getStartIndex()) != null) {
			startIndex = searchInfo.getStartIndex();
		}
		empInfo = pensionDAO.searchRecordsbyDobandName(bean, startIndex,
				gridLength, "true", ajaxCall);
		log.info("emp list size " + empInfo.size());
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(empInfo);

		return searchInfo;

	}

	public ArrayList getEmployeeMappedList(String region, String empName,
			String transferType,String pfId) throws Exception {
		int startIndex = 1;
		String navButton = "";
		ArrayList empInfo = new ArrayList();
		empInfo = pensionDAO.getEmployeeMappedList(region, empName,
				transferType,pfId);
		log.info("empserailnumber list size " + empInfo.size());
		return empInfo;

	}
	public ArrayList getEmployeeLookUpList(String region, String empName,String pfId) throws Exception {
		
		ArrayList empInfo = new ArrayList();
		empInfo = pensionDAO.getEmployeeLookUpList(region, empName,pfId);
		log.info("empserailnumber list size " + empInfo.size());
		return empInfo;

	}
	public SearchInfo searchRecordsbyEmpSerailNo(EmpMasterBean bean,
			SearchInfo searchInfo, int gridLength,String userType) throws Exception {
		int startIndex = 0;
		ArrayList empInfo = new ArrayList();
		System.out.println("searchInfo.getNavigation()"
				+ searchInfo.getNavigation());
		startIndex = 1;
		empInfo = pensionDAO.searchRecordsbyEmpSerailNo(bean, startIndex,gridLength,userType);
		log.info("emp list size " + empInfo.size());
		int totalRecords = pensionDAO.totalPensionMasterDatafordobandName(bean,
				"false",userType);
		searchInfo.setSearchList(empInfo);
		searchInfo.setTotalRecords(totalRecords);
		return searchInfo;
	}

	public SearchInfo getProcessUnprocessList(EmpMasterBean bean,
			SearchInfo searchInfo, int gridLength) throws Exception {
		int startIndex = 0;
		ArrayList empInfo = new ArrayList();
		startIndex = 1;
		empInfo = pensionDAO.getProcessUnprocessList(bean, startIndex,
				gridLength);
		log.info("emplist size " + empInfo.size());
		// int totalRecords =
		// pensionDAO.totalPensionMasterDatafordobandName(bean,"false");
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		searchInfo.setSearchList(empInfo);
		// searchInfo.setTotalRecords(totalRecords);
		return searchInfo;
	}

	public SearchInfo searchNavigationPensionData(EmpMasterBean empBean,
			SearchInfo searchInfo, boolean flag, int gridLength)
			throws Exception {
		int startIndex = 1;
		String navButton = "";
		ArrayList hindiData = new ArrayList();
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		if (searchInfo.getNavButton() != null) {
			navButton = searchInfo.getNavButton();
		}
		if (new Integer(searchInfo.getStartIndex()) != null) {
			startIndex = searchInfo.getStartIndex();
		}
		int rowCount = pensionDAO.totalPensionMasterData(empBean, flag, "");
		System.out.println("rowCount" + rowCount + "startIndex" + startIndex
				+ "navButton" + navButton);
		startIndex = paging.getPageIndex(navButton, startIndex, rowCount,
				gridLength);
		hindiData = pensionDAO.SearchPensionMasterAll(empBean, startIndex,
				flag, gridLength, "");
		bottomGridNavigationInfo = paging.navigationPagination(rowCount,
				startIndex, false, false, gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(rowCount);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public EmpMasterBean editPensionMaster(String cpfacno, String empName,
			boolean flag, String empCode, String region) throws Exception {
		EmpMasterBean editbean = null;
		editbean = pensionDAO.editPensionMaster(cpfacno, empName, flag,
				empCode, region);
		return editbean;
	}

	public void deletePensionMaster(String cpfacno, String empName,
			boolean flag, String empCode, String region, String airportCode)
			throws Exception {

		pensionDAO.deletePensionMaster(cpfacno, empName, flag, empCode, region,
				airportCode);
	}

	public String generateUniquePensionNumber(int sequenceNumber,
			String cpfacno, String empName, boolean flag, String empCode,
			String region, String airportCode, String dateofBirth)
			throws Exception {

		String newPensionNumber = pensionDAO.generateUniquePensionNumber(
				sequenceNumber, cpfacno, empName, flag, empCode, region,
				airportCode, dateofBirth);
		return newPensionNumber;
	}

	public void updateUniquePensionNumber(int sequenceNumber, String cpfacno,
			String empName, boolean flag, String empCode, String region,
			String airportCode, String dateofBirth, String pensionNumber,
			String sequnceFlag, String computername, String lastactive,
			String username) throws Exception {
		if (sequnceFlag.equals("Y")) {

			pDAO.addPersonalInfo(String.valueOf(sequenceNumber), cpfacno,
					lastactive, region, username, computername, lastactive);
		} else {
			pensionDAO.updateUniquePensionNumber(sequenceNumber, cpfacno,
					empName, flag, empCode, region, airportCode, dateofBirth,
					pensionNumber);
		}

	}

	public int updatePensionMaster(EmpMasterBean bean, String flag)
			throws InvalidDataException {

		int count = 0;
		String empLevel = "";
		EmpMasterBean desegBean = new EmpMasterBean();
		empLevel = bean.getEmpLevel();
		desegBean = pensionDAO.getDesegnation(empLevel);
		log.info("bean.getDesegnation().trim()" + bean.getDesegnation());
		log.info("desegBean " + desegBean);
		/*
		 * if (!desegBean.getDesegnation().trim().equals(
		 * bean.getDesegnation().trim())) { throw new InvalidDataException( "Emp
		 * Level with respective Desegnation is not equal"); }
		 */
		System.out.println("flag=(updatePensionMaster)=" + flag);
		if (flag.equals("false")) {
			count = pensionDAO.chckInvalidData(bean.getNewCpfAcNo(), bean
					.getRegion());
			if (count != 0) {
				log.info("Record already Exist");
				throw new InvalidDataException("Record Already Exist");
			}
		}
		try {
			count = pensionDAO.updatePensionMaster(bean, flag);
		} catch (InvalidDataException ide) {
			throw ide;
			// throw new InvalidDataException("PensionNumber Already Exist");
		}
		return count;
	}

	public String getMaxCpfAccNo() {

		String cpfAcno = "";
		try {
			cpfAcno = pensionDAO.getMaxValue();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cpfAcno;
	}

	public boolean addPensionRecord(EmpMasterBean bean) {
		boolean result = false;
		try {
			String cpfAcno = pensionDAO.getMaxValue();
			Connection con = null;
			con = commonDB.getConnection();
			// bean.setCpfAcNo(cpfAcno);
			result = pensionDAO.addPensionRecord(bean);
		} catch (Exception e) {
			log.printStackTrace(e);

		}
		return result;
	}

	public EmpMasterBean getCpfacnoDetails(String pensionnumber,String cpfacno, String region) {
		EmpMasterBean bean = null;
		bean = pensionDAO.getCpfacnoDetails(pensionnumber,cpfacno, region);
		return bean;
	}

	public EmpMasterBean getDesegnation(String empLevel) {
		EmpMasterBean bean = null;
		bean = pensionDAO.getDesegnation(empLevel);
		return bean;
	}

	public ArrayList getAirports(String region) {
		// ArrayList airPortList=null;
		ArrayList airPortList = pensionDAO.getAirports(region);
		return airPortList;
	}

	public ArrayList getDepartmentList() {

		ArrayList departmentList = pensionDAO.getDepartmentList();
		return departmentList;
	}

	public String getPensionNumber(String empName, String dateofbirth,
			String cpfacno) {
		String pensionNumber = "";
		CommonDAO CDAO = new CommonDAO();
		try {
			pensionNumber = CDAO.getPensionNumber(empName, dateofbirth,
					cpfacno, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pensionNumber;
	}

	public SearchInfo searchCompartiveMaster(EmpMasterBean bean,
			SearchInfo searchInfo, int gridLength) {
		int startIndex = 0;
		ArrayList empInfo = new ArrayList();
		System.out.println("searchInfo.getNavigation()"
				+ searchInfo.getNavigation());
		startIndex = 1;

		empInfo = pensionDAO.SearchCompartiveMasterAll(bean, startIndex,
				gridLength);
		log.info("emp list size " + empInfo.size());
		int totalRecords = pensionDAO.totalCompartiveMasterData(bean);
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		bottomGridNavigationInfo = paging.searchPagination(totalRecords,
				startIndex, gridLength);
		System.out.println("startIndex(searchPensionData)" + startIndex);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(empInfo);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public SearchInfo searchNavigationCompartiveData(EmpMasterBean empBean,
			SearchInfo searchInfo, int gridLength) {
		int startIndex = 1;
		String navButton = "";
		ArrayList hindiData = new ArrayList();
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		if (searchInfo.getNavButton() != null) {
			navButton = searchInfo.getNavButton();
		}
		if (new Integer(searchInfo.getStartIndex()) != null) {
			startIndex = searchInfo.getStartIndex();
		}
		int rowCount = pensionDAO.totalCompartiveMasterData(empBean);
		System.out.println("rowCount" + rowCount + "startIndex" + startIndex
				+ "navButton" + navButton);
		startIndex = paging.getPageIndex(navButton, startIndex, rowCount,
				gridLength);
		hindiData = pensionDAO.SearchCompartiveMasterAll(empBean, startIndex,
				gridLength);
		bottomGridNavigationInfo = paging.navigationPagination(rowCount,
				startIndex, false, false, gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(rowCount);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public int getSequenceNumber(String empName, String dateofBirth,
			boolean flag) {
		int sequenceNumber = pensionDAO.getSequenceNumber(empName, dateofBirth,
				flag);
		return sequenceNumber;
	}

	public ArrayList getRegions() throws Exception {
		ArrayList al = pensionDAO.getRegions();
		return al;
	}

	public ArrayList getAirportsList(String region) throws Exception {
		ArrayList airportlist = pensionDAO.getAirports(region);
		return airportlist;
	}

	public ArrayList getLogList(UserBean bean) throws Exception {
		ArrayList logList = pensionDAO.getLogList(bean);
		return logList;
	}

	public void deleteFamilyDetails(String familyMemberName, String rowid,
			String region, String cpfaccno) {
		pensionDAO.deleteFamilyDetails(familyMemberName, rowid, region,
				cpfaccno);
	}

	public void deleteNomineeDetils(String familyMemberName, String rowid,
			String region, String cpfaccno) {
		pensionDAO.deleteNomineeDetils(familyMemberName, rowid, region,
				cpfaccno);
	}

	public String getSequenceNo() {
		String seqno = "";
		try {
			Connection con = null;
			con = DBUtility.getConnection();
			seqno = pDAO.getSequenceNo(con);
		} catch (Exception e) {
			log.printStackTrace(e);

		}
		return seqno;
	}

	public ReadLabels readHeaders(String fileName, String userName,
			String ipAddress) throws Exception {
		log.info("PensionService:readHeaders-- Entering Method");
		log.info("fileName is " + fileName);
		String readData = "";
		String lengths = "";
		String[] xlsLables = {};
		String dbLable = "";
		String[] dbLables = {};
		ReadLabels lables = new ReadLabels();
		try {
			int readPersonal = fileName.indexOf("MONTHLY");
			if (readPersonal != -1) {
				readData = common.readExcelSheet(fileName);
				xlsLables = IDAO.readLables(readData);
				dbLable = pensionDAO.readLables("employee_pension_validate");
				dbLable = dbLable.substring(1, dbLable.length());
				dbLables = dbLable.split("-");
				lables.setDbLables(dbLables);
				lables.setXlsLables(xlsLables);

			}

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lables;
	}
	public ArrayList getEmolumentslog(String pfId, String airportcode)
			throws Exception {

		ArrayList emolumentsloginfo = new ArrayList();
		emolumentsloginfo = pensionDAO.getEmolumentslog(pfId, airportcode);
		log.info("empserailnumber list size " + emolumentsloginfo.size());
		return emolumentsloginfo;

	}

	public ArrayList getDeletelog(String pfId, String airportcode)
			throws Exception {
		int startIndex = 1;
		String navButton = "";
		ArrayList deleteloginfo = new ArrayList();
		deleteloginfo = pensionDAO.getDeletelog(pfId, airportcode);
		log.info("empserailnumber list size " + deleteloginfo.size());
		return deleteloginfo;

	}

	public ArrayList getAdjlog(String pfId) throws Exception {
		ArrayList adjloginfo = new ArrayList();
		adjloginfo = pensionDAO.getAdjlog(pfId);
		log.info("empserailnumber list size " + adjloginfo.size());
		return adjloginfo;

	}
}
