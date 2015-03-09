package com.apprade.dao;

public class DAO_Conexion {

	private static String URL_V1 = "http://apprade.com/api/v1/";

	
	public DAO_Conexion(String url) {
		super();
		DAO_Conexion.URL_V1 = url;
	}
	
	public DAO_Conexion() {
		super();	
	}

	public String getUrl() {
		return URL_V1;
	}
	
	public void setUrl(String url) {
		DAO_Conexion.URL_V1 = url;
	}
		
}
