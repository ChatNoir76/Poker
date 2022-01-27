package fr.cnam.grp4.poker.model;

public class JeuPoker {
	/**
	 * Contient les 5 cartes de la manche
	 * cartes 0 à 2: Flop
	 * carte 3 : Turn
	 * carte 4: River
	 */
	private Carte[] cartes;
	/**
	 * Contient les joueurs de la partie
	 */
	private Joueur[] joueur;
	/**
	 * Le pot qui contient le cumul de toutes les mises de jetons d'une manche
	 */
	private int pot;
	/**
	 * Valeur de la blind qui correspond à la mise
	 * obligatoire du joueur qui joue après le donneur
	 */
	private int blind;
	
	public Joueur[] getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur[] joueur) {
		this.joueur = joueur;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public int getBlind() {
		return blind;
	}

	public void setBlind(int blind) {
		this.blind = blind;
	}

	public Carte[] getCartes() {
		return cartes;
	}

	public void setFlop(Carte[] cartes) {
		
	}
	
	public void setTurn(Carte carte) {
			
	}

	public void setRiver(Carte carte) {
		
	}
	
}
