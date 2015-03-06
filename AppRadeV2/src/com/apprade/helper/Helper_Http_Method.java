package com.apprade.helper;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.util.Log;

/**
 * @author Renzo
 *
 */
public class Helper_Http_Method {
	
private static int TIMEOUT = 1000*60*10; /*milisegundos*/
private static boolean bTimeout;


	/**
 * @return the bTimeout
 */
public boolean isbTimeout() {
	return bTimeout;
}
/**
 * @param bTimeout the bTimeout to set
 */
public void setbTimeout(boolean bTimeout) {
	Helper_Http_Method.bTimeout = bTimeout;
}


	public Helper_Http_Method() {
		bTimeout = false;
	}
	
	
	public InputStream httpGet(String url){
	
		InputStream in = null;
	
		try {				    		
			    	   	HttpClient httpClient = new DefaultHttpClient();
		
			    		HttpParams httpParamentros = httpClient.getParams();
			    		HttpConnectionParams.setConnectionTimeout(httpParamentros, TIMEOUT);
			    		HttpConnectionParams.setSoTimeout(httpParamentros, TIMEOUT);
			    							    	
			    		URI www = new URI(url);			    		
			    		HttpGet get= new HttpGet();				    		
			    		get.setURI(www); 					    		
			    		HttpResponse response = httpClient.execute(get);			    		
			    		in = response.getEntity().getContent();		    		
	    		
	        } catch(ConnectTimeoutException e){
	        	setbTimeout(true);
	            Log.e("Exception: Timeout", e.toString()+":"+isbTimeout());
	        } catch (Exception e) {
	            Log.e("log_tag", "Error in http connection "+e.toString());
	        }
			    				
    	return in;
			 		
	}
	
	
	
	public InputStream httpPost(URI url, List<NameValuePair> parametros) {
		InputStream in = null;
		
		try {	
    		
		    		DefaultHttpClient httpClient = new DefaultHttpClient();
		    	
		    		HttpParams httpParamentros = httpClient.getParams();
		    		HttpConnectionParams.setConnectionTimeout(httpParamentros, TIMEOUT);
		    		HttpConnectionParams.setSoTimeout(httpParamentros, TIMEOUT);
		    		
		    		HttpPost post = new HttpPost(url);
		    		post.setEntity(new UrlEncodedFormEntity(parametros));
		    		HttpResponse response = httpClient.execute(post);
		    		in = response.getEntity().getContent();
		    	
        } catch(ConnectTimeoutException e){
        	setbTimeout(true);
            Log.e("Exception: Timeout", e.toString());
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection "+e.toString());
        }
	
    	return in;
	}	

}
