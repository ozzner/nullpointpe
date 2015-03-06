/**
 * 
 */
package com.apprade.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * @author renzo santillán
 *
 */
public class Helper_SubRoutines {

	public static final String TAG_DIAS = "dias";
	public static final String TAG_HORAS = "horas";
	public static final String TAG_MINUTOS = "minutos";
	public static final String TAG_SEGUNDOS = "segundos";
	public static final String TAG_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String TAG_FORMAT_SHORT = "yyyy-MM-dd HH:mm:ss";
	public static final String TAG_FORMAT_DATE_MM = "yyyy-MM-dd";
	public static final String TAG_FORMAT_DATE_MMM = "yyyy-MMM-dd";
	public static final String TAG_FORMAT_TIME = "HH:mm:ss";

	
	public Helper_SubRoutines() {
	}
	
/****************** Working with the time *********************/
	
	public String getCurrentTime(String Myformat) {
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(Myformat);
		String strDate = sdf.format(c.getTime());

		return strDate;
	}

	public String customDateConverter(String Mydate, String MyformatIn, String MyformatOut) {
		String inputStringDate = Mydate;
		SimpleDateFormat inputFormat = new SimpleDateFormat(MyformatIn);
		Date inputDate = null;
		
		try {
			inputDate = inputFormat.parse(inputStringDate);
		} catch (ParseException ex) {
		}

		SimpleDateFormat outputFormat = new SimpleDateFormat(
				MyformatOut);
		String outputStringDate = outputFormat.format(inputDate);

		return outputStringDate;
	}

	
	public int dateDiferent(String fecha_i, String fecha_f,
			String dias_horas_minutos_segundos) {

		String tiempo = dias_horas_minutos_segundos;
		Date date_i = null;
		Date date_f = null;
		long time = (long) 0.0;

		SimpleDateFormat formato = new SimpleDateFormat(TAG_FORMAT_SHORT);

		try {
			date_i = formato.parse(fecha_i);
			date_f = formato.parse(fecha_f);

		} catch (ParseException e) {
			Log.e("TAG", "Funcion diferenciaFechas: Error Parse " + e);
		} catch (Exception e) {
			Log.e("TAG", "Funcion diferenciaFechas: Error " + e);
		}

		Calendar cal_i = Calendar.getInstance();
		Calendar cal_f = Calendar.getInstance();

		cal_i.setTime(date_i);
		cal_f.setTime(date_f);

		long ms_i = cal_i.getTimeInMillis();
		long ms_f = cal_f.getTimeInMillis();

		long result_ms = ms_f - ms_i;

		switch (tiempo) {
		case "segundos":
			time = Math.abs(result_ms / 1000);
			break;
		case "minutos":
			time = Math.abs(result_ms / (60 * 1000));
			break;
		case "horas":
			time = (result_ms / (60 * 60 * 1000));
			break;
		case "dias":
			time = Math.abs(result_ms / (24 * 60 * 60 * 1000));
			break;
		default:
			time = result_ms;
			break;
		}

		return ((int) time);
	}
	
	/****************** Working Phone propeties *********************/
	
	public boolean isOnline(Context ctx) {
		
	    ConnectivityManager cm =
	        (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    
	    return false;
	}
	
	
	/****************** Working with GPS  *********************/
	
	
	public double distanceBetweenPositions(double lat1,double lon1, double lat2, double lon2){
		
		
		Double Lat1_Rad = lat1 * 3.1416 / 180;
		Double Lon1_Rad = lon1 * 3.1416 / 180;
		
		Double Lat2_Rad = lat2 * 3.1416 / 180;
		Double Lon2_Rad = lon2 * 3.1416 / 180;
		
		
		Double Sin_Lat1 = Math.sin(Lat1_Rad);
		Double Cos_Lat1 = Math.cos(Lat1_Rad);

		Double Cos_Lat2 = Math.cos(Lat2_Rad);
		Double Sin_Lat2 = Math.sin(Lat2_Rad);
		
		Double Cos_Lon2Lon1 = Math.cos(Lon2_Rad - Lon1_Rad);
	
		Double difDistancia = 6378 * 1000 * (Math.acos((Sin_Lat1 * Sin_Lat2)
				+ (Cos_Lat1 * Cos_Lat2 * Cos_Lon2Lon1)));
		
		return difDistancia;
	}
	

	
	public void showToast(Context context, String sms) {
		Toast.makeText(context, sms, Toast.LENGTH_SHORT).show();
	}
	

	public String converDistance(double distance){
		String sDistance = "";
		
		if (distance > 1000) {
			
			double r = distance / 1000;
				  
			sDistance = String.valueOf(customRound(r, 2))+ " Km";
		}else{
			sDistance = customRound(distance,0)+ " m";
		}
		
		return sDistance;
		
	}
	 public double customRound( double numero, int decimales ) {
		    return Math.round(numero*Math.pow(10,decimales))/Math.pow(10,decimales);
	 }
	
}
