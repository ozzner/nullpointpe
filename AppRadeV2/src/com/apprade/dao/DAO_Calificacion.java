		package com.apprade.dao;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.util.Log;
import com.apprade.entity.Entity_Calificacion;
import com.apprade.helper.Helper_Http_Method;
import com.apprade.helper.Helper_JSONParser;
import com.apprade.helper.Helper_JSONStatus;

/**
 * @author Renzo
 *
 */
public class DAO_Calificacion {
	
	private DAO_Conexion conn;
	private static URI URL;
	private static String ENTITY = "calificacion";
	public  Entity_Calificacion oCali ;
	public  Helper_JSONStatus oJsonStatus;
	private Helper_Http_Method oHttp;
	private Helper_JSONParser oParser;	

	public DAO_Calificacion() {
		oCali = new Entity_Calificacion();
		oJsonStatus =  new Helper_JSONStatus();
		oHttp = new Helper_Http_Method();
		conn = new DAO_Conexion();
		oParser = new Helper_JSONParser();
	}
	
	
	public boolean registrarCalificacion(String usuario, String establecimiento,String cola){
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null; 
		boolean bEstado = false;
		
	List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("usuario", usuario));
		parametros.add( new BasicNameValuePair("establecimiento", establecimiento));
		parametros.add( new BasicNameValuePair("cola", cola));
		
		try {						
				in =  oHttp.httpPost(URL, parametros);
			    oJson =oParser.parserToJsonObject(in);

				  boolean bStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				  
				if(!bStatus){
					JSONObject oData =  oJson.getJSONObject("data");
					oJsonStatus.setMessage(oData.getString("message"));						
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

	
	public boolean obtenerUltimaCalificacionPorEstabID(String establecimientoID){
		
		boolean bResult = false;
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null,oData = null;
		
		/*Listas*/
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("establecimientoID", establecimientoID));
		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		
		try {						
			    in =  oHttp.httpGet(URL + "?" + paramsString);
			    oJson =oParser.parserToJsonObject(in);
			    JSONObject oRating = null;
			    
			    boolean bStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				
				if(!bStatus){
					oData =  oJson.getJSONObject("data");
					
						    oRating =  oData.getJSONObject("rating1");	
							
							int iIdUser = Integer.parseInt(oRating.getString("userID"));
							int iIdEsta = Integer.parseInt(oRating.getString("estaID"));
							String sQueue = oRating.getString("queue");
							String sDate = oRating.getString("create_at");
		
						    oCali = new Entity_Calificacion();
							oCali.setUsuarioID(iIdUser);
							oCali.setEstablecimientoID(iIdEsta);
							oCali.setCola(sQueue);
							oCali.setFecha(sDate);
							bResult = true;
							
					oJsonStatus.setMessage("Estado");		
					oJsonStatus.setInfo(sQueue);		
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
					oJsonStatus.setError_status(Boolean.parseBoolean(oJson.getString("error_status")));
					
				}else{
					oData =  oJson.getJSONObject("data");
					oJsonStatus.setMessage(oData.getString("message"));
					oJsonStatus.setInfo(oData.getString("info"));
				}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		return bResult;
		
	}
	
}
