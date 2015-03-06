/**
 * 
 */
package com.apprade.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Renzo
 *
 */
public class Entity_Comentario {

	private int comentarioID;
	private String fecha;
	private String mensaje;
	private List<Entity_Usuario> usuario = new ArrayList<Entity_Usuario>(); 
	private List<String> establecimiento = new ArrayList<String>(); 
	
	public Entity_Comentario() {
	
	}


	/**
	 * @param comentarioID
	 * @param fecha
	 * @param mensaje
	 * @param usuario
	 * @param establecimiento
	 */
	public Entity_Comentario(int comentarioID, String fecha, String mensaje,
			List<Entity_Usuario> usuario, List<String> establecimiento) {
		super();
		this.comentarioID = comentarioID;
		this.fecha = fecha;
		this.mensaje = mensaje;
		this.usuario = usuario;
		this.establecimiento = establecimiento;
	}


	public int getComentarioID() {
		return comentarioID;
	}


	public void setComentarioID(int comentarioID) {
		this.comentarioID = comentarioID;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public List<Entity_Usuario> getUsuario() {
		return usuario;
	}


	public void setUsuario(List<Entity_Usuario> usuario) {
		this.usuario = usuario;
	}


	public List<String> getEstablecimiento() {
		return establecimiento;
	}


	public void setEstablecimiento(List<String> establecimiento) {
		this.establecimiento = establecimiento;
	}



	
	
	
	

}
