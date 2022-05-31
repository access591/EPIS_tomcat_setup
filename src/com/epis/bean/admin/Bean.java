package com.epis.bean.admin;

public class Bean {
	
	private String code;
	private String name;
	
	public Bean() {
	}
	
	public Bean(String code, String name) {
		this.code = code;
		this.name =name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer("Code : ");
		buf.append(this.code).append(" # Name : ").append(this.name);
		return buf.toString();
	}
	
	public boolean equals(Object obj){
		boolean bool = false;
		if (obj instanceof Bean && ((Bean)obj).getCode().equals(this.code) ) {
			return true;
		}
		return bool;
	}
}
