package com.apprade.helper;

import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Helper_SharedPreferences  {

	private SharedPreferences appRadeSharedPref;
	private static final String MyPREFERENCES = "Apprade";
	private static final int UserID = 103095;
	private static final String Email = "apprade@apprade.com";
	private static final int Status = -1;
	private Context contexto;
	
	
	
	public void run() {
		
		appRadeSharedPref = contexto.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		SharedPreferences.Editor editorLogin = appRadeSharedPref.edit();
		editorLogin.putString("email", Email);
		editorLogin.putInt("userID", UserID);
		editorLogin.putInt("status", Status);
		editorLogin.commit();
	}
	

	public String checkLogin(Context context) {
		String sChek = null;
		contexto=context;
		
		appRadeSharedPref = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		
		if (!appRadeSharedPref.contains("userID")) {		
			sChek = "intro";
			run();
				
		} else {
			int key = appRadeSharedPref.getInt("status", 0);
			
			switch (key) {
			case -1:
				sChek = "intro";
				break;
			case 0:
				sChek = "login";
				break;
			case 1:
				sChek = "mapa";
				break;
			default:
				sChek = "mapa";
				break;
			}
		}

		return sChek;
	}
	
	
	public void storeLogin(String name, String email, int userID,int status,Context context) {
		contexto=context;
		appRadeSharedPref = contexto.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		SharedPreferences.Editor editorLogin = appRadeSharedPref.edit();
		editorLogin.putString("name", name);
		editorLogin.putString("email", email);
		editorLogin.putInt("userID", userID);
		editorLogin.putInt("status", status);
		editorLogin.commit();

	}
	
	public void storeStatus(int status,Context context) {
		contexto=context;
		
		appRadeSharedPref = contexto.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		SharedPreferences.Editor editorLogin = appRadeSharedPref.edit();
		editorLogin.putInt("status", status);
		editorLogin.commit();
	}
	
	
	public ArrayList<String> getAllLoginDataStored(Context context){
		contexto=context;
		ArrayList<String> lista = new ArrayList<String>();
		
		appRadeSharedPref = contexto.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		lista.add(appRadeSharedPref.getString("name", ""));
		lista.add(appRadeSharedPref.getString("email", ""));
		lista.add(appRadeSharedPref.getInt("userID", 0)+"");
     	lista.add(appRadeSharedPref.getInt("status", 0)+"");
     	
		return lista;
	}
	
	public boolean checkMyCustomPreference(String customPreferences,Context context,String key){
		boolean bResult = false;
	
		contexto=context;
		appRadeSharedPref = contexto.getSharedPreferences(customPreferences, Context.MODE_PRIVATE);
		
		if(appRadeSharedPref.contains(key))
			bResult = true;
		
		Log.e("Shared_", customPreferences + ":"+bResult);
		return bResult;
	}
	
	
	
	public void updateMyCurretTime(String customPreferences,Context context,String currentTime){
		contexto=context;
		
		appRadeSharedPref = contexto.getSharedPreferences(customPreferences, Context.MODE_PRIVATE);

		SharedPreferences.Editor editorLogin = appRadeSharedPref.edit();
		editorLogin.putString("currentTime", currentTime);
		editorLogin.commit();
		
	}
	
	
	
	public void storeMyCustomPreferences(String[] keys, String[] values, String customPreferences,Context context){
		
		contexto=context;
		appRadeSharedPref = contexto.getSharedPreferences(customPreferences, Context.MODE_PRIVATE);
		
		
		SharedPreferences.Editor editorLogin = appRadeSharedPref.edit();
		
		for (int i = 0; i < values.length; i++) {
			editorLogin.putString(keys[i], values[i]);
		}
		
		editorLogin.commit();
		
	}
	
	public String getAnyValueToMyCustomPreferences(Context context , String customPreferences, String key){
		String sValue = new String();
		Log.e("Shared_2_get", customPreferences + ":"+key);
		contexto=context;
		appRadeSharedPref = contexto.getSharedPreferences(customPreferences, Context.MODE_PRIVATE);
		
		Map<String,?> keys = appRadeSharedPref.getAll();

		for(Map.Entry<String,?> entry : keys.entrySet()){
			
			if(entry.getKey() != null)
				 if(entry.getKey().toString().equals(key))
				 {
					 sValue = entry.getValue().toString();
					 break;
				 }
		 }
		
		return sValue;
	}

}
