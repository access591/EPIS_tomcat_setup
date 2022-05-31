package com.epis.bean.investment;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;
import java.io.Serializable;

public class TrustTypeBean extends RequestBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trustCd;
	private String trustType;
	private String description;
	
	
	public TrustTypeBean(HttpServletRequest request){
		super(request);
		}
	public TrustTypeBean(){
		}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTrustCd() {
		return trustCd;
	}
	public void setTrustCd(String trustCd) {
		this.trustCd = trustCd;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
}
