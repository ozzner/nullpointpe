/**
 * 
 */
package com.apprade.entity;

/**
 * @author Renzo
 *
 */
public class Entity_Categoria {

	private int categoriaID;
	private String nombre;
		
	public Entity_Categoria() {
	}

	
	public Entity_Categoria(int categoriaID, String nombre) {
		super();
		this.categoriaID = categoriaID;
		this.nombre = nombre;
	}


	public int getCategoriaID() {
		return categoriaID;
	}

	public void setCategoriaID(int categoriaID) {
		this.categoriaID = categoriaID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
