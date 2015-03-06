/**
 * 
 */
package com.apprade.entity;

/**
 * @author Renzo
 *
 */
public class Entity_Ranking {

	private int rankingID;
	private String nombre;
	
	public Entity_Ranking() {
	}
	
	public Entity_Ranking(int rankingID, String nombre) {
		super();
		this.rankingID = rankingID;
		this.nombre = nombre;
	}
	
	public int getRankingID() {
		return rankingID;
	}

	public void setRankingID(int rankingID) {
		this.rankingID = rankingID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
