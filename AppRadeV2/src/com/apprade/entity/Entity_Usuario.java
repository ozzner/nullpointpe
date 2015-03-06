package com.apprade.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Renzo
 *
 */

public class Entity_Usuario {
	
	private int usuarioID;
	private String email;
	private char sexo;
	private String nombre;
	private String fechaNacimiento;
	private String apMaterno;
	private String apPaterno;
	private int rate;
	private String uid;
	private String fechaRegistro;
	private String password;
	private List<Entity_Ranking>ranking = new ArrayList<Entity_Ranking>();

	public Entity_Usuario() {
		super();		
	}
	
	/**
	 * @param usuarioID
	 * @param email
	 * @param sexo
	 * @param nombre
	 * @param fechaNacimiento
	 * @param apMaterno
	 * @param apPaterno
	 * @param rate
	 * @param uid
	 * @param fechaRegistro
	 * @param password
	 * @param ranking
	 */
	
	public Entity_Usuario(int usuarioID, String email, char sexo,
			String nombre, String fechaNacimiento, String apMaterno,
			String apPaterno, int rate, String uid, String fechaRegistro,
			String password, List<Entity_Ranking> ranking) {
		super();
		this.usuarioID = usuarioID;
		this.email = email;
		this.sexo = sexo;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.apMaterno = apMaterno;
		this.apPaterno = apPaterno;
		this.rate = rate;
		this.uid = uid;
		this.fechaRegistro = fechaRegistro;
		this.password = password;
		this.ranking = ranking;
	}







	public int getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(int usuarioID) {
		this.usuarioID = usuarioID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}
	
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Entity_Ranking> getRanking() {
		return ranking;
	}

	public void setRanking(List<Entity_Ranking> ranking) {
		this.ranking = ranking;
	}



}
