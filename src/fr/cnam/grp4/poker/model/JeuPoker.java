package fr.cnam.grp4.poker.model;

import java.util.ArrayList;
import java.util.Optional;

import fr.cnam.grp4.poker.observable.JeuPokerObservableApp;

public class JeuPoker {
	private JeuPokerObservableApp obsApp;
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
	private ArrayList<Joueur> joueurs;
	/**
	 * Le pot qui contient le cumul de toutes les mises de jetons d'une manche
	 */
	private int pot;
	/**
	 * Valeur de la blind qui correspond à la mise
	 * obligatoire du joueur qui joue après le donneur
	 */
	private int blind;
	
	public JeuPokerObservableApp getObsApp() {
		return obsApp;
	}

	public JeuPoker() {
		this.obsApp = new JeuPokerObservableApp();
		this.joueurs = new ArrayList<Joueur>();
	}
	
	public void testObservable() {
		System.out.println("fx testObservable");
		this.obsApp.notifyObservers(this);
	}
	
	public Joueur getJoueur(String speudo) {
		Optional<Joueur> joueur = this.joueurs.stream().filter(j -> j.getNom().equals(speudo)).findFirst();
		return joueur.orElseThrow();
	}

	public void ajouteJoueur(Joueur joueur) {
		this.joueurs.add(joueur);
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
