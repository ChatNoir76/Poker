package fr.cnam.grp4.poker.model;

public class CarteFactory {
	/**
	 * Instance unique de la classe
	 */
	private static CarteFactory instance;
	/**
	 * Contient les cartes à distribuer
	 * Elle sont mélangées et coupées
	 */
	private Carte[] distributeur;
	/**
	 * Appel de l'instance unique de la classe
	 * @return l'instance de la classe
	 */
	public static CarteFactory eInstance() {
		if(instance == null) instance = new CarteFactory();
		return instance;
	}
	private CarteFactory() {
		
	}
	/**
	 * Méthode qui donne les cartes au jeu
	 * @return La prochaine carte du distributeur
	 */
	public Carte prendreCarte() {
		return null;
	}
	/**
	 * Récupère les cartes de la manche afin de les remettre dans le distributeur
	 * @param cartes : Les cartes à rendre
	 */
	public void recupereCartes(Carte[] cartes) {
		
	}
	/**
	 * Permet de couper le jeu de carte à un endroit aléatoire
	 */
	private void coupe() {
		
	}
	
	
}
