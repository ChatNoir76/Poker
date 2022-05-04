package fr.cnam.grp4.poker.server.model;

import java.io.Serializable;

public class Carte extends AbstractCarte implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Détermine la couleur de la carte Pique, Trèfle, Carreau, Coeur
	 */
	private Couleur couleur;
	/**
	 * Valeur de la carte de 1 (As) à 10
	 */
	private int valeur;
	/**
	 * Nom de la carte
	 * Soit ça correspond à la valeur, soit roi, valet, dame, as
	 */
	private String nom;
	/**
	 * savoir si la carte est visible
	 * si non visible => l'image sera le dos
	 */
	private boolean visible;
	/**
	 * retourne le lien ou se trouve l'image de la carte
	 */
	private String lienImageFace;
	
	public Carte(Couleur couleur, int valeur) {
		this.couleur = couleur;
		this.valeur = valeur;
		this.nom = this.findNameFromValeur(valeur);
		this.visible = false;
		this.lienImageFace = this.getLienCarteStandard(couleur.getSymbole(), this.valeur);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String toString() {
		return nom + " de " + couleur.getNom();
	}

	@Override
	public Couleur getCouleur() {
		return this.couleur;
	}

	@Override
	public int getValeur() {
		return this.valeur;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public boolean estVisible() {
		return visible;
	}

	@Override
	public String getLienFace() {
		return lienImageFace;
	}

}
