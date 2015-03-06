/**
 * 
 */
package com.apprade.entity;

import java.text.DecimalFormat;

/**
 * @author Renzo
 *
 */
public class Entity_Coordenadas {

	private int coordenadasID;
	private float latitud;
	private float longitud;
	private String referencia;
	
	public Entity_Coordenadas() {
	}

	public Entity_Coordenadas(int coordenadasID, float latitud, float longitud,
			String referencia) {
		super();
		this.coordenadasID = coordenadasID;
		this.latitud = latitud;
		this.longitud = longitud;
		this.referencia = referencia;
	}

	public int getCoordenadasID() {
		return coordenadasID;
	}

	public void setCoordenadasID(int coordenadasID) {
		this.coordenadasID = coordenadasID;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {	
		float d = latitud;
		DecimalFormat df = new DecimalFormat("#.######");
		this.latitud = Float.parseFloat(df.format(d));
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		float d = longitud;
		DecimalFormat df = new DecimalFormat("#.######");
		this.longitud = Float.parseFloat(df.format(d));
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	

}
