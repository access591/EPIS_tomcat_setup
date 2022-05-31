package com.epis.bean.investment;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;


public class SecurityCategoryBean  extends RequestBean{
	private String categoryId;
	private String categoryCd;
	private String description;
	private String openingbal;
	private String investinterest;
	private String investbaseamt;
	private String accHeads;
	
	public String getAccHeads() {
		return accHeads;
	}
	public void setAccHeads(String accHeads) {
		this.accHeads = accHeads;
	}
	public String getInvestbaseamt() {
		return investbaseamt;
	}
	public void setInvestbaseamt(String investbaseamt) {
		this.investbaseamt = investbaseamt;
	}
	public String getInvestinterest() {
		return investinterest;
	}
	public void setInvestinterest(String investinterest) {
		this.investinterest = investinterest;
	}
	public String getOpeningbal() {
		return openingbal;
	}
	public void setOpeningbal(String openingbal) {
		this.openingbal = openingbal;
	}
	public SecurityCategoryBean(HttpServletRequest request){
		super(request);
		}
	public SecurityCategoryBean(){
		}
	public String getCategoryCd() {
		return categoryCd;
	}
	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public String toString(){
		return 		("CategoryId:"+categoryId+"###Categorycd:"+this.categoryCd+"###Description:"+this.description);
	}
	
	

}