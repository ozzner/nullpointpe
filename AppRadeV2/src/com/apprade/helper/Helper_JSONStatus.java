/**
 * 
 */
package com.apprade.helper;

/**
 * @author Renzo
 *
 */
public class Helper_JSONStatus {

	private int httpCode;
	private boolean error_status;
	private double error_cod;
	private String message;
	private String info;
	
	public Helper_JSONStatus() {
	}
	
	
	public Helper_JSONStatus(int httpCoode, boolean error_status,
			double error_cod, String message, String info) {
		super();
		this.httpCode = httpCoode;
		this.error_status = error_status;
		this.error_cod = error_cod;
		this.message = message;
		this.info = info;
	}



	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCoode) {
		this.httpCode = httpCoode;
	}

	public boolean getError_status() {
		return error_status;
	}

	public void setError_status(boolean error_status) {
		this.error_status = error_status;
	}

	public double getError_cod() {
		return error_cod;
	}

	public void setError_cod(double error_cod) {
		this.error_cod = error_cod;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	

}
