package fr.cnam.grp4.poker.model;

public class Carte extends AbstractCarte {
	/**
	 * Détermine la couleur de la carte Pique, Trèfle, Carreau, Coeur
	 */
	private String couleur;
	/**
	 * Valeur de la carte de 1 (As) à 10
	 */
	private int Valeur;
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
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getLienImageFace() {
		return lienImageFace;
	}

	public void setLienImageFace(String lienImageFace) {
		this.lienImageFace = lienImageFace;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public void setValeur(int valeur) {
		Valeur = valeur;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String getCouleur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getValeur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean estVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLienFace() {
		// TODO Auto-generated method stub
		return null;
	}

}
