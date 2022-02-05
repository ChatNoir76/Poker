package fr.cnam.grp4.poker.model;

import java.util.ArrayList;
import java.util.List;

import fr.cnam.grp4.poker.service.Utilitaire;

public class CarteFactory {
	/**
	 * Instance unique de la classe
	 */
	private static CarteFactory instance;
	/**
	 * Contient les cartes à distribuer
	 * Elle sont mélangées et coupées
	 */
	private ArrayList<Carte> distributeur;
	/**
	 * cartes d'un jeu ordonné
	 */
	private Carte[] jeuDansOrdre;
	/**
	 * Appel de l'instance unique de la classe
	 * @return l'instance de la classe
	 */
	public static CarteFactory eInstance() {
		if(instance == null) instance = new CarteFactory();
		return instance;
	}
	private CarteFactory() {
		this.jeuDansOrdre = new Carte[52];
		this.distributeur = new ArrayList<Carte>();
		
		generateCartes();
		melangeCartes();
	}
	/**
	 * Génère un paquet de 52 cartes
	 * La variable jeuDansOrdre réceptionne le paquet
	 */
	private void generateCartes() {
		int cursor = 0;
		for(Couleur c: Couleur.values()) {
			for(int i=0;i<13;i++) {
				this.jeuDansOrdre[cursor] = new Carte(c, i + 1, findNameFromValeur(i+1), getLienCarteStandard(c.getSymbole(), i + 1));
				cursor++;
			}
		}
	}
	/**
	 * Détermine le lien vers l'image de la carte
	 * @param Symbole Symbole de la couleur
	 * @param number valeur de la carte 1 à 13
	 * @return le lien de la carte (voir dossier ressources)
	 */
	private String getLienCarteStandard(String Symbole, int number) {
		String other = ""+number;
		if(number == 11) {
			other = "j";
		}else if (number == 13) {
			other = "k";
		}else if (number == 12) {
			other = "q";
		}
		return "Ressources/" + Symbole + other + ".gif";
	}
	/**
	 * Détermine le nom depuis sa valeur
	 * @param valeur valeur de la carte
	 * @return Nom de la carte
	 */
	private String findNameFromValeur(int valeur) {
		if(valeur == 1) return "As";
		if(valeur == 11) return "Valet";
		if(valeur == 13) return "Roi";
		if(valeur == 12) return "Reine";
		return ""+valeur;
	}
	/**
	 * Mélange des cartes pour intégration dans le distributeur
	 */
	private void melangeCartes() {
		//Création d'une liste contenant les 52 index des cartes (0 à 51)
		List<Integer> listIndex = new ArrayList<Integer>();
		for(int i = 0;i<this.jeuDansOrdre.length;i++) {
			listIndex.add(i);
		}
		//tant qu'il y a un index non tiré
		while(listIndex.size() != 0) {
			//récupération d'un index au hazard
			int index = Utilitaire.eInstance().getRandomInteger(listIndex.size());
			//récupération de la carte correspondant à l'index
			this.distributeur.add(this.jeuDansOrdre[listIndex.get(index)]);
			//suppression de l'index pour éviter de le reprendre
			listIndex.remove(index);
		}
	}
	/**
	 * Méthode qui donne les cartes au jeu
	 * @return La prochaine carte du distributeur
	 */
	public Carte prendreCarte() {
		if(this.distributeur.size() > 0) {
			return this.distributeur.remove(0);
		}else {
			return null;
		}
		
	}
	/**
	 * Récupère les cartes de la manche afin de les remettre dans le distributeur
	 * @param cartes : Les cartes à rendre
	 */
	public void remettreCartes(Carte[] cartes) {
		for(Carte c: cartes) {
			remettreCarte(c);
		}
	}
	/**
	 * Replace la carte dans le paquet
	 * @param carte à replacer
	 */
	public void remettreCarte(Carte carte) {
		this.distributeur.add(carte);
	}
	/**
	 * Permet de couper le jeu de carte à un endroit aléatoire
	 */
	private void coupe() {
		
	}
	
	
}
