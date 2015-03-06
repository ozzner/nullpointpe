package net.wasitec.sieveanalisis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class Connection {
	private final static String TAG = Connection.class.getSimpleName();
	
	public Connection() {
		Log.e(TAG, "Iniciado");
	}

	
	
	public JSONObject makeHttpRequest(String method,JSONObject json_request){
		
			JSONObject obJson = null;
			InputStream is = null;
			String result = "";
			String json = "";
	
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 10000; // 10 second timeout for connecting
											// to site
			
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			int timeoutSocket = 30000; // 30 second timeout for obtaining
										// results
			
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			result = json_request.toString();
			
			try {
				HttpResponse response = null;
					
					
					if (method == "POST") {
						
						HttpClient httpclient = new DefaultHttpClient(httpParameters);
						HttpPost post = new HttpPost(Constants.URL_PARSE_EMAIL);
						StringEntity entity = new StringEntity(result);
						
						/*Parse*/
						post.setHeader("X-Parse-Application-Id",Constants.PARSE_APP_ID);
						post.setHeader("X-Parse-REST-API-Key",Constants.PARSE_API_KEY);
						post.setHeader("Content-Type","application/json;charset=UTF-8");
						post.setEntity(entity);
						
						response = httpclient.execute(post);
						
					}else 
						
					if (method == "GET") {
						//TODO GET	
					}
				
					HttpEntity httpEntity = response.getEntity();
					is = httpEntity.getContent();
					
						BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
						
						StringBuilder sb = new StringBuilder();
						String line = null;
						
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
						is.close();
						
						json = sb.toString();
						obJson =  new JSONObject(json);
						Log.e(TAG, json);
					
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return obJson;
			
	}
	
	
}
