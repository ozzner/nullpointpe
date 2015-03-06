/**
 * 
 */
package com.apprade.entity;

/**
 * @author Renzo
 *
 */
public class Entity_Distrito {

	private int distritoID;
	private String nombre;
	private String zipcode;
	
	public Entity_Distrito() {
	}

	public Entity_Distrito(int distritoID, String nombre, String zipcode) {
		super();
		this.distritoID = distritoID;
		this.nombre = nombre;
		this.zipcode = zipcode;
	}

	public int getDistritoID() {
		return distritoID;
	}

	public void setDistritoID(int distritoID) {
		this.distritoID = distritoID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	
	
	
}
