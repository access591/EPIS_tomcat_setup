package com.epis.common.exception;

public class ServiceNotAvailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String error="Service Not Available. Reason Could be Data Store Connectivity Problem. ";
	public ServiceNotAvailableException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceNotAvailableException(String error) {
		super();
		this.error=error;
	}
	public String  getMessage(){
		return error;
	}
}
