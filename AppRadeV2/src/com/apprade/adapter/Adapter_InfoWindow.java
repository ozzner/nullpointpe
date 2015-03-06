/**
 * @author renzo santillán
 *
 */
package com.apprade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apprade.R;
import com.apprade.helper.Helper_GPS_Tracker;
import com.apprade.helper.Helper_SubRoutines;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class Adapter_InfoWindow implements InfoWindowAdapter {
	LayoutInflater inflater = null;
	private static String cola;
	private static int idEst;
	private static String direccion;
	private static String nombre;
	private Helper_SubRoutines routine;
	private Helper_GPS_Tracker gps;
	private Context _context;
	/**
	 * @param inflater
	 */
	public Adapter_InfoWindow(LayoutInflater inflater) {
		super();
		
		this.inflater = inflater;
		this._context = inflater.getContext();
		this.routine = new Helper_SubRoutines();
		this.gps = new Helper_GPS_Tracker(_context);
	}

	public Adapter_InfoWindow() {
		super();/* Default status */
		routine = new Helper_SubRoutines();
		gps = new Helper_GPS_Tracker(_context);
	}

	@Override
	public View getInfoContents(Marker makerMap) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		
		View infoWindows = inflater.inflate(
				R.layout.adapter_calificacion_infowindow, null);

		TextView tvNombre = (TextView) infoWindows.findViewById(R.id.title);
		TextView tvDireccion = (TextView) infoWindows
				.findViewById(R.id.snippet);
		TextView tvDistance = (TextView)infoWindows.findViewById(R.id.tv_distance);
		ImageView ivCola = (ImageView) infoWindows.findViewById(R.id.imgMarker);
		

		String sAll = arg0.getSnippet();
		String sIdEst = sAll.substring(sAll.length() - 4);
		String sDirec = sAll.substring(0, sAll.length() - 4);
		
		double lat_est = arg0.getPosition().latitude;
		double lon_est = arg0.getPosition().longitude;
		double lat_gps = gps.getLatitude();
		double lon_gps = gps.getLongitude();
		
		double dDist = routine.distanceBetweenPositions(lat_est, lon_est, lat_gps, lon_gps);
		String sDist = routine.converDistance(dDist);
		
		tvDistance.setText("Distancia: " + sDist);
		tvNombre.setText(arg0.getTitle());
		tvDireccion.setText(sDirec);
		setIdEst(Integer.parseInt(sIdEst));
		
		String sCola = getCola();

		switch (sCola) {

		case "Alta cola":
			ivCola.setImageResource(R.drawable.img4);
			break;

		case "Cola moderada":
			ivCola.setImageResource(R.drawable.img3);
			break;

		case "Poca cola":
			ivCola.setImageResource(R.drawable.img2);
			break;

		case "No hay cola":
			ivCola.setImageResource(R.drawable.img1);
			break;

		default:
			ivCola.setImageResource(R.drawable.img1);
			break;
		}

		return (infoWindows);
	}

	/**
	 * @return the cola
	 */
	public String getCola() {
		return cola;
	}

	/**
	 * @param cola
	 *            the cola to set
	 */
	public void setCola(String cola) {
		Adapter_InfoWindow.cola = cola;
	}

	/**
	 * @return the idEst
	 */
	public  int getIdEst() {
		return idEst;
	}

	/**
	 * @param idEst
	 *            the idEst to set
	 */
	public  void setIdEst(int idEst) {
		Adapter_InfoWindow.idEst = idEst;
	}

	/**
	 * @return the direccion
	 */
	public  String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public  void setDireccion(String direccion) {
		Adapter_InfoWindow.direccion = direccion;
	}

	/**
	 * @return the nombre
	 */
	public  String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		Adapter_InfoWindow.nombre = nombre;
	}

}
