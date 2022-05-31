
package com.aims.service;

/**
 * @author naveenk
 *
 */

import java.util.List;
import java.util.Map;


import com.epis.utilities.ApplicationException;
import com.epis.utilities.Log;
import com.aims.dao.IncomeTaxDAO;
import com.aims.info.incometax.EmpMiscITInfo;
import com.aims.info.incometax.IncomeTaxSlabInfo;
import com.aims.info.incometax.ProjectedITDetail;
import com.aims.info.incometax.SBILendingRatesInfo;
import com.aims.info.incometax.SavingsDeclarationInfo;
import com.aims.info.incometax.ProfessionalTaxSlabInfo;
import com.aims.info.staffconfiguration.EmployeeInfo;

public class IncomeTaxService {
	Log log = new Log(IncomeTaxService.class);
	private IncomeTaxService(){
	
	}
	private static IncomeTaxService slabServiceInstance=new IncomeTaxService();
	public static IncomeTaxService getInstance(){
		return slabServiceInstance;
	}
	public List selectTaxYearCd()
	{
		List l=IncomeTaxDAO.getInstance().selectTaxYearCd();
		return l;
	}
	public void saveSlab(IncomeTaxSlabInfo slabInfo){
		IncomeTaxDAO.getInstance().saveSlab(slabInfo);
	}
	public List editMastSlab(IncomeTaxSlabInfo slabInfo){
		return IncomeTaxDAO.getInstance().editMastSlab(slabInfo);
	}
	public List editDetSlab(IncomeTaxSlabInfo slabInfo){
		return IncomeTaxDAO.getInstance().editDetSlab(slabInfo);
	}
	public List searchSlab(IncomeTaxSlabInfo slabInfo){
		return IncomeTaxDAO.getInstance().searchSlab(slabInfo);
	}
	public void updateSlab(IncomeTaxSlabInfo slabInfo){
		IncomeTaxDAO.getInstance().updateSlab(slabInfo);
	}	
	public List getDates(IncomeTaxSlabInfo slabInfo){
	    return IncomeTaxDAO.getInstance().getDates(slabInfo);
	}
	public void validateAssTaxYear(IncomeTaxSlabInfo slabInfo) throws Exception{
		IncomeTaxDAO.getInstance().validateAssTaxYear(slabInfo);
	}	
//	 Savings Declaraion Done By Srilakshmi 15/09/09
	public List getAllSavings(){
	    return IncomeTaxDAO.getInstance().getAllSavings();
	}
	public void addSavingsDeclaration(SavingsDeclarationInfo info) throws ApplicationException{
	    IncomeTaxDAO.getInstance().addSavingsDeclaration(info);
	}
	public List searchSavingsDeclaration(String empno){
	    return IncomeTaxDAO.getInstance().searchSavingsDeclaration(empno);
	}
	public List showNSCFinYears(String finyrcd){
	    return IncomeTaxDAO.getInstance().showNSCFinYears(finyrcd);
	}
	public List getEmpSavDeclFinYears(String empcd){
	    return IncomeTaxDAO.getInstance().getEmpSavDeclFinYears(empcd);
	}
	public List getProjectedIncomeTaxInfo(String empcd,String fyearcd){
	    return IncomeTaxDAO.getInstance().getProjectedIncomeTaxInfo(empcd,fyearcd);
	}
//End: Savings Declaraion Done By Srilakshmi 15/09/09
	public void saveProfessionalTaxSlab(ProfessionalTaxSlabInfo  slabInfo)throws ApplicationException{
	    IncomeTaxDAO.getInstance().saveProfessionalTaxSlab(slabInfo);
	}
	public List searchProfessionalTaxSlab(ProfessionalTaxSlabInfo slabInfo){
		return IncomeTaxDAO.getInstance().searchProfessionalTaxSlab(slabInfo);
	}
	public SavingsDeclarationInfo showEditSavingsDeclaration(String savDeclCd) {
		return IncomeTaxDAO.getInstance().showEditSavingsDeclaration(savDeclCd);
	}
	public void updateSavingsDeclaration(SavingsDeclarationInfo info) {
		  IncomeTaxDAO.getInstance().updateSavingsDeclaration(info);
	}
	public SavingsDeclarationInfo showSavingsDeclarationDetails(String savDeclCd) {
		return IncomeTaxDAO.getInstance().showSavingsDeclarationDetails(savDeclCd);
	}
	public void addEmpMiscIT(EmpMiscITInfo slabInfo)throws ApplicationException{
	    IncomeTaxDAO.getInstance().addEmpMiscIT(slabInfo);
	}
	public List searchEmpMiscIT(EmpMiscITInfo slabInfo){
	    return IncomeTaxDAO.getInstance().searchEmpMiscIT(slabInfo);
	}
	
	public void addSBILendingRates(SBILendingRatesInfo slabInfo)throws ApplicationException{
	    IncomeTaxDAO.getInstance().addSBILendingRates(slabInfo);
	}
	public List searchSBILendingRates(SBILendingRatesInfo slabInfo){
	    return IncomeTaxDAO.getInstance().searchSBILendingRates(slabInfo);
	}
	
	public List showSBILendingRatesDetails(SBILendingRatesInfo slabInfo){
	    return IncomeTaxDAO.getInstance().showSBILendingRatesDetails(slabInfo);
	}
	public List showSBILendingRatesEdit(SBILendingRatesInfo slabInfo){
	    return IncomeTaxDAO.getInstance().showSBILendingRatesEdit(slabInfo);
	}
	public void updateSBILendingRates(SBILendingRatesInfo slabInfo){
	    IncomeTaxDAO.getInstance().updateSBILendingRates(slabInfo);
	}
	public void saveITExcelData(String path,String usercd){
	    IncomeTaxDAO.getInstance().saveITExcelData(path,usercd);
	}
	public Map getDetailedProjectedIncomeTaxInfo(EmployeeInfo info) {
		 return IncomeTaxDAO.getInstance().getDetailedProjectedIncomeTaxInfo(info);
	}
	public ProjectedITDetail getPITaxDetails() {
		return IncomeTaxDAO.getInstance().getPITaxDetails();
	}
}
