package com.apprade.entity;

import java.util.ArrayList;
import java.util.List;

public class Entity_Establecimiento {
	
	private int establecimientoID;
	private String nombre;
	private String direccion;	
	private int ruc;
	
	private List<Entity_Categoria>categoria = new ArrayList<Entity_Categoria>();
	private List<Entity_Distrito>distrito =  new ArrayList<Entity_Distrito>();
	private List<Entity_Coordenadas>coordenadas = new ArrayList<Entity_Coordenadas>();
	
	/*CONSTRUCTORES*/

	public Entity_Establecimiento() {
		super();
	}
	
	/**
	 * @param establecimientoID
	 * @param nombre
	 * @param direccion
	 * @param ruc
	 * @param categoria
	 * @param distrito
	 * @param coordenadas
	 */
	
	public Entity_Establecimiento(int establecimientoID, String nombre,
			String direccion, int ruc, List<Entity_Categoria> categoria,
			List<Entity_Distrito> distrito, List<Entity_Coordenadas> coordenadas) {
		super();
		this.establecimientoID = establecimientoID;
		this.nombre = nombre;
		this.direccion = direccion;
		this.ruc = ruc;
		this.categoria = categoria;
		this.distrito = distrito;
		this.coordenadas = coordenadas;
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

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the ruc
	 */
	public int getRuc() {
		return ruc;
	}

	/**
	 * @param ruc the ruc to set
	 */
	public void setRuc(int ruc) {
		this.ruc = ruc;
	}

	/**
	 * @return the categoria
	 */
	public List<Entity_Categoria> getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(List<Entity_Categoria> categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the distrito
	 */
	public List<Entity_Distrito> getDistrito() {
		return distrito;
	}

	/**
	 * @param distrito the distrito to set
	 */
	public void setDistrito(List<Entity_Distrito> distrito) {
		this.distrito = distrito;
	}

	/**
	 * @return the coordenadas
	 */
	public List<Entity_Coordenadas> getCoordenadas() {
		return coordenadas;
	}

	/**
	 * @param coordenadas the coordenadas to set
	 */
	public void setCoordenadas(List<Entity_Coordenadas> coordenadas) {
		this.coordenadas = coordenadas;
	}




	
	
	
	
	
}
