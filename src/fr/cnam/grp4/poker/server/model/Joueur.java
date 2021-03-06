package fr.cnam.grp4.poker.server.model;

import java.io.Serializable;

import fr.cnam.grp4.poker.server.service.JeuPokerException;

public class Joueur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Contient le nom du joueur
	 */
	private String nom;
	/**
	 * Contient les deux cartes de la manche
	 */
	private Carte[] cartes;
	/**
	 * Jetons total du joueur
	 * si 0 => perdu
	 */
	private int jetons;
	/**
	 * Jetons misés pendant la manche
	 * cette information permet de gérer le tapis
	 */
	private int miseManche;
	/**
	 * savoir si le joueur est le donneur
	 * si oui, il pourra avoir les commandes du plateau
	 */
	private boolean donneur;
	/**
	 * savoir si le joueur à abandonné
	 */
	private boolean abandon;
	/**
	 * savoir si le joueur est éliminé
	 */
	private boolean eliminer;
	
	private Joueur(String nom, int jetons) {
		this.nom = nom;
		this.cartes = new Carte[2];
		this.jetons = jetons;
		this.miseManche = 0;
		this.donneur = false;
		this.abandon = false;
		this.eliminer = false;
	}
	/**
	 * Création d'un joueur
	 * @param nom Pseudo du joueur
	 */
	public Joueur(String nom) {
		this(nom, 1000);
	}
	/**
	 * Récupère le nom/pseudo du joueur
	 * @return le pseudo du joueur
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * Récupère les deux cartes du joueur
	 * @return tableau de deux cartes (possible null)
	 */
	public Carte[] getCartes() {
		return cartes;
	}
	/**
	 * Ajoute deux cartes au jeu du joueur
	 * @param cartes tableau de deux cartes
	 * @throws JeuPokerException nombre de cartes reçues différent de deux
	 */
	public void setCartes(Carte[] cartes) throws JeuPokerException {
		if(cartes.length == 2) {
			this.cartes = cartes;
		}else {
			throw new JeuPokerException("Les cartes données au joueur doivent être par paire (2 cartes)");
		}
	}
	/**
	 * Retourne le nombre de jetons du joueur (sa cagnotte)
	 * @return nombre de jetons total du joueur
	 */
	public int getJetons() {
		return jetons;
	}
	/**
	 * Défini le nombre de jetons total du joueur
	 * @param jetons nombre total de jetons du joueur
	 * @throws JeuPokerException total de jetons inférieur ou égal à zéro
	 */
	public void setJetons(int jetons) throws JeuPokerException {
		if(jetons > 0) {
			this.jetons = jetons;
		} else {
			throw new JeuPokerException("Le nombre de jetons d'un joueur doit être supérieur à zéro");
		}
	}
	/**
	 * Récupère le nombre de jetons misés par le joueur
	 * @return nombre de jetons
	 */
	public int getMiseManche() {
		return miseManche;
	}
	/**
	 * détermine la mise de la manche du joueur
	 * @param miseManche mise de la manche du joueur
	 * @throws JeuPokerException la mise doit être supérieure ou égale à zéro 
	 */
	public void setMiseManche(int miseManche) throws JeuPokerException {
		if(miseManche >= 0) {
			this.miseManche = miseManche;
		} else {
			throw new JeuPokerException("Le nombre de jetons joués dans une manche doit être supérieur ou égal à zéro");
		}
	}
	/**
	 * Détermine si le joueur est donneur
	 * @return true: donneur, false: n'est pas donneur
	 */
	public boolean isDonneur() {
		return donneur;
	}
	/**
	 * Change le statut du joueur
	 * @param donneur true: donneur, false: n'est pas donneur
	 */
	public void setDonneur(boolean donneur) {
		this.donneur = donneur;
	}
	/**
	 * Détermine si le joueur à abandonner la manche
	 * @return true pour abandonné
	 */
	public boolean isAbandon() {
		return abandon;
	}
	/**
	 * Change le statut d'abandon du joueur
	 * @param abandon
	 */
	public void setAbandon(boolean abandon) {
		this.abandon = abandon;
	}
	/**
	 * Détermine si le joueur est éliminé de toute partie
	 * @return true ne peux plus jouer
	 */
	public boolean isEliminer() {
		return eliminer;
	}
	/**
	 * Change le statut d'élimination du joueur
	 * @param eliminer
	 */
	public void setEliminer(boolean eliminer) {
		this.eliminer = eliminer;
	}
	
}
