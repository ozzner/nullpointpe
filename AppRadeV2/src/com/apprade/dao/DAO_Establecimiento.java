package com.apprade.dao;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.apprade.entity.Entity_Categoria;
import com.apprade.entity.Entity_Coordenadas;
import com.apprade.entity.Entity_Distrito;
import com.apprade.entity.Entity_Establecimiento;
import com.apprade.helper.Helper_Http_Method;
import com.apprade.helper.Helper_JSONParser;
import com.apprade.helper.Helper_JSONStatus;
import com.apprade.helper.Helper_constants;


public class DAO_Establecimiento {
	
	
	public Entity_Establecimiento ettEstab ;
	public  Helper_JSONStatus oJsonStatus;	
	private Helper_JSONParser oParser;
	private Helper_Http_Method oHttp;
	private static DAO_Conexion conn;
	private static URI URL ;
	private static final String ENTITY = "establecimiento";
	private static final String TAG = DAO_Establecimiento.class.getSimpleName();
	private static Map<Integer, String> map_IdEs_Cola = new HashMap<Integer,String>();

	public DAO_Establecimiento(Entity_Establecimiento oEstable,
			Helper_JSONStatus oJsonStatus, Helper_JSONParser oParser,
			Helper_Http_Method oHttp) {
		super();
		this.ettEstab = oEstable;
		this.oJsonStatus = oJsonStatus;
		this.oParser = oParser;
		this.oHttp = oHttp;
	}


	public DAO_Establecimiento() {
		super();
		oHttp = new Helper_Http_Method();
		oParser = new Helper_JSONParser();
		oJsonStatus = new Helper_JSONStatus();
		conn = new DAO_Conexion();
	}

	public static Map<Integer, String> getMap_IdEs_Cola() {
		return map_IdEs_Cola;
	}

	public static void setMap_IdEs_Cola(Map<Integer, String> map_IdEs_Cola) {
		DAO_Establecimiento.map_IdEs_Cola = map_IdEs_Cola;
	}
	
	
	
	public List<Entity_Establecimiento> listarEstablecimientoPorCategoriaID(String categoriaID)
	{	
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null;
		
	    List<Entity_Establecimiento> lista = new ArrayList<Entity_Establecimiento>();
	    List<Entity_Distrito> listaDis = new ArrayList<Entity_Distrito>();	
	    List<Entity_Coordenadas> listaCoo = new ArrayList<Entity_Coordenadas>();
	    List<Entity_Categoria> listaCat = new ArrayList<Entity_Categoria>();	
	    
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("categoryID", categoriaID));
		parametros.add( new BasicNameValuePair("tag", "categoryID"));
		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		
		try {						
			    in =  oHttp.httpGet(URL + "?" + paramsString);
			    oJson =oParser.parserToJsonObject(in);
			    
				    boolean bStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				    JSONObject oData =  oJson.getJSONObject("data");
				    
					if(!bStatus){
						
						int iNum = oData.length();
						map_IdEs_Cola.clear();
						
							for (int i = 0; i < iNum; i++) {
								
								JSONObject oEstabli =  oData.getJSONObject("establishment"+(i+1));	
								
								int iIdEst = Integer.parseInt(oEstabli.getString("establishmentID").trim());
								String sNameEst = oEstabli.getString("name").trim();
								String sDireccion = oEstabli.getString("address").trim();
								
								JSONObject oDistric =  oEstabli.getJSONObject("district");							
								int iIdDis = Integer.parseInt(oDistric.getString("districtID").trim());
								String sNameDis = oDistric.getString("name").trim();
								Entity_Distrito ettDis = new Entity_Distrito(iIdDis, sNameDis, null);
								listaDis.add(ettDis);
								
								JSONObject oCateg =  oEstabli.getJSONObject("category");
								String sNameCat = oCateg.getString("name").trim();
								int iIdCat = Integer.parseInt(oCateg.getString("categoryID").trim());
								Entity_Categoria ettCat = new Entity_Categoria(iIdCat, sNameCat);
								listaCat.add(ettCat);
																											
								JSONObject oCoodin =  oEstabli.getJSONObject("coordinates");
								int iIdCoo = Integer.parseInt(oCoodin.getString("coordinatesID"));
								float fLatit = Float.parseFloat(oCoodin.getString("latitude"));
								float fLongi = Float.parseFloat(oCoodin.getString("longitude"));							
								Entity_Coordenadas ettCoor = new Entity_Coordenadas(iIdCoo, fLatit, fLongi, null);
								listaCoo.add(ettCoor);
							
								Entity_Establecimiento ettEst = new Entity_Establecimiento
										(iIdEst, sNameEst, sDireccion, -1, listaCat, listaDis, listaCoo);
								
								lista.add(ettEst);//Lista final
								
								try {
							
									JSONObject oRate =  oEstabli.getJSONObject("rating");
									if (!oRate.getString("cal_cola").toString().equals("")) {
										map_IdEs_Cola.put(iIdEst,oRate.getString("cal_cola"));
									}else{
										map_IdEs_Cola.put(iIdEst,oRate.getString("No hay cola"));
									}
									
								} catch (Exception e) {
									map_IdEs_Cola.put(iIdEst,"No hay cola");
								}
							}
						Log.e("LISTA", map_IdEs_Cola+"");
						oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
						
					}else{
						oJsonStatus.setMessage(oData.getString("message"));
						oJsonStatus.setInfo(oData.getString("info"));
						oJsonStatus.setError_status(Boolean.parseBoolean(oJson.getString("error_status")));
						lista=null; //Temporal
					}
			    
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
		}
		
		return lista;
	}


	public List<Entity_Establecimiento> listarEstablecimientoPorNombre(
			String name) {
		
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null;
		
	    List<Entity_Establecimiento> lista = new ArrayList<Entity_Establecimiento>();
	    List<Entity_Distrito> listaDis = new ArrayList<Entity_Distrito>();	
	    List<Entity_Coordenadas> listaCoo = new ArrayList<Entity_Coordenadas>();
	    List<Entity_Categoria> listaCat = new ArrayList<Entity_Categoria>();	
	    
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("name", name));
		parametros.add( new BasicNameValuePair("tag", "name"));
		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		
		try {						
			    in =  oHttp.httpGet(URL + "?" + paramsString);
			    oJson =oParser.parserToJsonObject(in);
			    
				    boolean bStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				    JSONObject oData =  oJson.getJSONObject("data");
				    
					if(!bStatus){
						
						int iNum = oData.length();
						map_IdEs_Cola.clear();
						
							for (int i = 0; i < iNum; i++) {
								
								JSONObject oEstabli =  oData.getJSONObject("establishment"+(i+1));	
								
								int iIdEst = Integer.parseInt(oEstabli.getString("establishmentID").trim());
								String sNameEst = oEstabli.getString("name").trim();
								String sDireccion = oEstabli.getString("address").trim();
								
								JSONObject oDistric =  oEstabli.getJSONObject("district");							
								int iIdDis = Integer.parseInt(oDistric.getString("districtID").trim());
								String sNameDis = oDistric.getString("name").trim();
								Entity_Distrito ettDis = new Entity_Distrito(iIdDis, sNameDis, null);
								listaDis.add(ettDis);
								
								JSONObject oCateg =  oEstabli.getJSONObject("category");
								String sNameCat = oCateg.getString("name").trim();
								int iIdCat = Integer.parseInt(oCateg.getString("categoryID").trim());
								Entity_Categoria ettCat = new Entity_Categoria(iIdCat, sNameCat);
								listaCat.add(ettCat);
																											
								JSONObject oCoodin =  oEstabli.getJSONObject("coordinates");
								int iIdCoo = Integer.parseInt(oCoodin.getString("coordinatesID"));
								float fLatit = Float.parseFloat(oCoodin.getString("latitude"));
								float fLongi = Float.parseFloat(oCoodin.getString("longitude"));							
								Entity_Coordenadas ettCoor = new Entity_Coordenadas(iIdCoo, fLatit, fLongi, null);
								listaCoo.add(ettCoor);
							
								Entity_Establecimiento ettEst = new Entity_Establecimiento
										(iIdEst, sNameEst, sDireccion, -1, listaCat, listaDis, listaCoo);
								
								lista.add(ettEst);//Lista final
								
								try {
							
									JSONObject oRate =  oEstabli.getJSONObject("rating");
									if (!oRate.getString("cal_cola").toString().equals("")) {
										map_IdEs_Cola.put(iIdEst,oRate.getString("cal_cola"));
									}else{
										map_IdEs_Cola.put(iIdEst,oRate.getString("No hay cola"));
									}
									
								} catch (Exception e) {
									map_IdEs_Cola.put(iIdEst,"No hay cola");
								}
							}
						Log.e("LISTA", map_IdEs_Cola+"");
						oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
						
					}else{
						oJsonStatus.setMessage(oData.getString("message"));
						oJsonStatus.setInfo(oData.getString("info"));
						oJsonStatus.setError_status(Boolean.parseBoolean(oJson.getString("error_status")));
						lista=null; //Temporal
					}
			    
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
		}
		
		return lista;
	
	}
	/**@return int 1 if is correct.*/
	public int enviarNuevoEstablecimiento(
							String direccion,
							String nombre,
							String estado,
							int cat_id,
							int dis_id,
							double latitude,
							double longitude){
		
		
		URL = URI.create(conn.getUrl() + Helper_constants.ESTABLECIMIENTOS);
		InputStream in = null;
		JSONObject oJson = null;
		int iResult = -1;
		
		Log.e(TAG, URL.toString());
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("direccion", direccion));
		parametros.add( new BasicNameValuePair("nombre", nombre));
		parametros.add( new BasicNameValuePair("estado", estado));
		parametros.add( new BasicNameValuePair("cat_id", String.valueOf(cat_id)));
		parametros.add( new BasicNameValuePair("dis_id",String.valueOf(dis_id)));
		parametros.add( new BasicNameValuePair("latitude",String.valueOf(latitude)));
		parametros.add( new BasicNameValuePair("longitude",String.valueOf(longitude)));
	
		
		in =  oHttp.httpPost(URL, parametros);
	    oJson = oParser.parserToJsonObject(in);
	    
		   
		    try {
		    	
				 boolean bStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				 JSONObject jsonData =  oJson.getJSONObject("data");
				 
				 if (!bStatus) {
					iResult = 1;
					oJsonStatus.setMessage(jsonData.getString("message"));
				}
				
			} catch (JSONException e) {
				iResult = 0;
				e.printStackTrace();
			}
		    
		return iResult;
	}

	

	
}
    
 

