package com.apprade;

import android.app.Application;

/**
 * @author Renzo Santillán
 *
 */
public class Apprade_Application extends Application {
	
	private String your_name;
    private String your_email;
    private String your_apikey;
    
	
	
	/* Set and Get*/

	/**
	 * @return the your_name
	 */
	public String getYour_name() {
		return your_name;
	}

	/**
	 * @param your_name the your_name to set
	 */
	public void setYour_name(String your_name) {
		this.your_name = your_name;
	}

	/**
	 * @return the your_email
	 */
	public String getYour_email() {
		return your_email;
	}

	/**
	 * @param your_email the your_email to set
	 */
	public void setYour_email(String your_email) {
		this.your_email = your_email;
	}

	/**
	 * @return the your_apikey
	 */
	public String getYour_apikey() {
		return your_apikey;
	}

	/**
	 * @param your_apikey the your_apikey to set
	 */
	public void setYour_apikey(String your_apikey) {
		this.your_apikey = your_apikey;
	}

}
