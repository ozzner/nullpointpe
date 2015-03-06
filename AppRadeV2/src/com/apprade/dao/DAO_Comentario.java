/**
 * 
 */
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
import com.apprade.entity.Entity_Comentario;
import com.apprade.entity.Entity_Establecimiento;
import com.apprade.entity.Entity_Ranking;
import com.apprade.entity.Entity_Usuario;
import com.apprade.helper.Helper_Http_Method;
import com.apprade.helper.Helper_JSONParser;
import com.apprade.helper.Helper_JSONStatus;

public class DAO_Comentario {


	public Entity_Establecimiento ettEstab ;
	public  Helper_JSONStatus oJsonStatus;	
	private Helper_JSONParser oParser;
	private Helper_Http_Method oHttp;
	private static DAO_Conexion conn;
	private static URI URL ;
	private static final String ENTITY = "comentario";

	public DAO_Comentario(Entity_Establecimiento ettEstab,
			Helper_JSONStatus oJsonStatus, Helper_JSONParser oParser,
			Helper_Http_Method oHttp) {
		super();
		this.ettEstab = ettEstab;
		this.oJsonStatus = oJsonStatus;
		this.oParser = oParser;
		this.oHttp = oHttp;
	}


	public DAO_Comentario() {
		super();
		ettEstab = new Entity_Establecimiento();
		conn =  new DAO_Conexion();
	    oHttp = new Helper_Http_Method();
	    oParser = new Helper_JSONParser();
	    oJsonStatus = new Helper_JSONStatus();
	}
		
	
	public List<Entity_Comentario> listarTodoComentarioPorID(String establecimientoID){
		
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null, oData = null;

	    List<Entity_Comentario> lista = new ArrayList<Entity_Comentario>();
	    List<Entity_Usuario> listaUsu = new ArrayList<Entity_Usuario>();	
	    List<Entity_Ranking> listaRan = new ArrayList<Entity_Ranking>();
		List<String> listaEsta = new ArrayList<String>();
	    
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("establecimientoID", establecimientoID));
		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		
		try {						
			    in =  oHttp.httpGet(URL + "?" + paramsString);
			    oJson =oParser.parserToJsonObject(in);
			    JSONObject oComent = null;
			    
			    boolean bStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				
				if(!bStatus){
					oData =  oJson.getJSONObject("data");
					
					
					int iNum = oData.length();
			
						for (int i = 0; i < iNum; i++) {
							
						    oComent =  oData.getJSONObject("comment"+(i+1));	
							
							int iIdCom = Integer.parseInt(oComent.getString("commentID"));
							String sMenCom = oComent.getString("message");
							String sDateCom = oComent.getString("date_at");
								
							JSONObject oUsuario =  oComent.getJSONObject("user");							
							String sMailUsu= oUsuario.getString("usu_mail");
							String sNameUsu = oUsuario.getString("name");
							
							Entity_Ranking ettRan = new Entity_Ranking(-1,"");
							listaRan.add(ettRan);
							
							Entity_Usuario ettUsu = new Entity_Usuario(-1, sMailUsu,'S', sNameUsu, 
									"", "","", -1, "", "", "", listaRan);
							listaUsu.add(ettUsu);
			
							JSONObject oEsta =  oComent.getJSONObject("establishment");
							String sNameEst = oEsta.getString("name");
	
							listaEsta.add(sNameEst);

							Entity_Comentario ettCom = new Entity_Comentario(
									iIdCom, sDateCom, sMenCom, listaUsu,listaEsta);
							
							lista.add(ettCom); //Lista final
						}
					
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
					oJsonStatus.setError_status(Boolean.parseBoolean(oJson.getString("error_status")));
					
				}else{
					oData =  oJson.getJSONObject("data");
					oJsonStatus.setMessage(oData.getString("message"));
					oJsonStatus.setInfo(oData.getString("info"));
					Log.e("COMERNT-ID", bStatus+"");
					oJsonStatus.setError_status(Boolean.parseBoolean(oJson.getString("error_status")));
					lista=null; //Temporal
				}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		
		return lista;
	}
	
	
	public boolean insertarComentario(String establecimientoID,String usuarioID,String mensaje){
		
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null; 
		boolean bEstado = false;
		
	List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("mensaje", mensaje));
		parametros.add( new BasicNameValuePair("establecimientoID", establecimientoID));
		parametros.add( new BasicNameValuePair("usuarioID", usuarioID));

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
