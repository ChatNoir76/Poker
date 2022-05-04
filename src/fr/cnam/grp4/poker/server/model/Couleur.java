package fr.cnam.grp4.poker.server.model;

import java.io.Serializable;

public enum Couleur implements Serializable{
	Trefle("Trèfle","c"),
	Carreau("Carreau", "d"),
	Coeur("Coeur","h"),
	Pic("Pic","s");

	private String nom;
	private String symbole;
	
	private Couleur(String nom, String symbole) {
		this.nom = nom;
		this.symbole = symbole;
	}

	public String getNom() {
		return nom;
	}

	public String getSymbole() {
		return symbole;
	}
	
}
