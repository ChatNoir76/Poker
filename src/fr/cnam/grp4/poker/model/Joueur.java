package fr.cnam.grp4.poker.model;

public class Joueur {
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

	public Carte[] getCartes() {
		return cartes;
	}

	public void setCartes(Carte[] cartes) {
		this.cartes = cartes;
	}

	public int getJetons() {
		return jetons;
	}

	public void setJetons(int jetons) {
		this.jetons = jetons;
	}

	public int getMiseManche() {
		return miseManche;
	}

	public void setMiseManche(int miseManche) {
		this.miseManche = miseManche;
	}

	public boolean isDonneur() {
		return donneur;
	}

	public void setDonneur(boolean donneur) {
		this.donneur = donneur;
	}
	
}
