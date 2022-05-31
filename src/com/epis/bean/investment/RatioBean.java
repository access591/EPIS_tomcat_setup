package com.epis.bean.investment;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;
import java.util.List;

public class RatioBean extends RequestBean{
	private String ratioCd;
	private String validFrom;
	private String categoryId;
	private String percentage;
	private String categoryCd;
	private String validTo;
	private List ratioList;
	private List catList;
	
	public RatioBean(HttpServletRequest request){
		super(request);
		}
	public RatioBean(){
		}
	public List getCatList() {
		return catList;
	}
	public void setCatList(List catList) {
		this.catList = catList;
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
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getRatioCd() {
		return ratioCd;
	}
	public void setRatioCd(String ratioCd) {
		this.ratioCd = ratioCd;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public List getRatioList() {
		return ratioList;
	}
	public void setRatioList(List ratioList) {
		this.ratioList = ratioList;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

}
