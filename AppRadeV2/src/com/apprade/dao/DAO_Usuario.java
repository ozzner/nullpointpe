package com.apprade.dao;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.apprade.entity.Entity_Usuario;
import com.apprade.helper.Helper_Http_Method;
import com.apprade.helper.Helper_JSONParser;
import com.apprade.helper.Helper_JSONStatus;

import android.util.Log;

public class DAO_Usuario{
	
	private DAO_Conexion conn;
	private static  URI URL;
	private static String ENTITY = "usuario";
	public Entity_Usuario oUsuario ;
	public  Helper_JSONStatus oJsonStatus;
	private Helper_JSONParser oParser;
	private Helper_Http_Method oHttp;
	
	public DAO_Usuario() {
		oUsuario = new Entity_Usuario();
		oJsonStatus =  new Helper_JSONStatus();
		oParser = new Helper_JSONParser();
		oHttp = new Helper_Http_Method();
		conn = new DAO_Conexion();
	}

	
	public boolean loginUsuario(String email , String password, int controller)
	{	
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null; 
		boolean bEstado = false;
				
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("email", email));
		parametros.add( new BasicNameValuePair("password", password));
		parametros.add( new BasicNameValuePair("controller", String.valueOf(controller)));

		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		
		try {						
			    in =  oHttp.httpGet(URL + "?" + paramsString);
			    oJson =oParser.parserToJsonObject(in);
			    
			 boolean bError  = Boolean.parseBoolean(oJson.getString("error_status")); //False = no error
				
				if(!bError){
					JSONObject oUserData =  oJson.getJSONObject("data").getJSONObject("user1");
					
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
					
					oUsuario.setUsuarioID(Integer.parseInt(oUserData.getString("userID")));
					oUsuario.setEmail(oUserData.getString("email"));
					oUsuario.setSexo(oUserData.getString("sex").charAt(0));
					oUsuario.setNombre(oUserData.getString("name"));
					oUsuario.setFechaNacimiento(oUserData.getString("date_birth"));
					oUsuario.setFechaRegistro(oUserData.getString("date_at"));
					oUsuario.setApPaterno(oUserData.getString("last_name1"));
					oUsuario.setApMaterno(oUserData.getString("last_name2"));
					oUsuario.setRate(Integer.parseInt(oUserData.getString("rate")));
					oUsuario.setUid(oUserData.getString("Api_key"));
					
					bEstado = true;
					
				}else{
					
					JSONObject oErrorData=  oJson.getJSONObject("data");
					oJsonStatus.setMessage(oErrorData.getString("message"));
					oJsonStatus.setInfo(oErrorData.getString("info"));
				}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		
		return bEstado;
	}

	
	public boolean registarUsuario(String email, String sexo,String nombre,String password,String fecha){
		
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null; 
		boolean bEstado = false;
		
	List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("email", email));
		parametros.add( new BasicNameValuePair("sexo", sexo));
		parametros.add( new BasicNameValuePair("nombre", nombre));
		parametros.add( new BasicNameValuePair("fecha", fecha));
		parametros.add( new BasicNameValuePair("password", password));
		
		try {						
			    in =  oHttp.httpPost(URL, parametros);
			    oJson =oParser.parserToJsonObject(in);
			    
			  boolean  bError = Boolean.parseBoolean(oJson.getString("error_status"));
				
				if(!bError){
					JSONObject oUserData =  oJson.getJSONObject("data");					
					oJsonStatus.setMessage(oUserData.getString("message"));
					bEstado = true;
					
				}else{
					
					JSONObject oErrorData=  oJson.getJSONObject("data");
					oJsonStatus.setMessage(oErrorData.getString("message"));
					oJsonStatus.setInfo(oErrorData.getString("info"));
				}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		
		return bEstado;
	
	}

}
