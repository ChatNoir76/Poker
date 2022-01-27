package fr.cnam.grp4.poker.model;

public interface ICarte {

	public String getCouleur();
	
	public int getValeur();
	
	public String getNom();
	
	public boolean estVisible();
	
	public String getLienFace();
	
	public String getLienDos();
	
}
