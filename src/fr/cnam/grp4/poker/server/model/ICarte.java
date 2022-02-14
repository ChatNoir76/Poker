package fr.cnam.grp4.poker.server.model;

public interface ICarte {
	/**
	 * Récupère la couleur de la carte sous forme d'énumération
	 * @return Enumération de la couleur de la carte
	 */
	public Couleur getCouleur();
	/**
	 * Récupère la valeur de la carte
	 * @return 1 à 13
	 */
	public int getValeur();
	/**
	 * récupère le nom de la carte
	 * @return 1 à 10, roi, reine, valet
	 */
	public String getNom();
	/**
	 * Pour savoir si la carte est face visible ou face cachée
	 * @return true => face
	 */
	public boolean estVisible();
	/**
	 * lien de l'image de face
	 * @return lien relatif
	 */
	public String getLienFace();
	/**
	 * lien de l'image de dos
	 * @return lien relatif
	 */
	public String getLienDos();
	
}
