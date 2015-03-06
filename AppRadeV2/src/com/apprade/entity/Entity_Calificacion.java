/**
 * 
 */
package com.apprade.entity;


/**
 * @author Renzo
 *
 */
public class Entity_Calificacion {

	private String cola;
	private String fecha;
	private int usuarioID;
	private int establecimientoID;
	
	
	/**
	 * @param cola
	 * @param hora
	 * @param fecha
	 * @param usuarioID
	 * @param establecimientoID
	 */
	public Entity_Calificacion(String cola, String fecha,
			int usuarioID, int establecimientoID) {
		super();
		this.cola = cola;
		this.fecha = fecha;
		this.usuarioID = usuarioID;
		this.establecimientoID = establecimientoID;
	}


	/**
	 * 
	 */
	public Entity_Calificacion() {
	}


	/**
	 * @return the cola
	 */
	public String getCola() {
		return cola;
	}


	/**
	 * @param cola the cola to set
	 */
	public void setCola(String cola) {
		this.cola = cola;
	}


	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}


	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	/**
	 * @return the usuarioID
	 */
	public int getUsuarioID() {
		return usuarioID;
	}


	/**
	 * @param usuarioID the usuarioID to set
	 */
	public void setUsuarioID(int usuarioID) {
		this.usuarioID = usuarioID;
	}


	/**
	 * @return the establecimientoID
	 */
	public int getEstablecimientoID() {
		return establecimientoID;
	}


	/**
	 * @param establecimientoID the establecimientoID to set
	 */
	public void setEstablecimientoID(int establecimientoID) {
		this.establecimientoID = establecimientoID;
	}
	

	

	

}
