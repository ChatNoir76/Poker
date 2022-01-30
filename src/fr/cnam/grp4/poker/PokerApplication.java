package fr.cnam.grp4.poker;

import fr.cnam.grp4.poker.model.Carte;
import fr.cnam.grp4.poker.model.JeuPoker;
import fr.cnam.grp4.poker.model.Joueur;
import fr.cnam.grp4.poker.view.IHMJoueur;

public class PokerApplication implements IPokerApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IHMJoueur ihm1 = new IHMJoueur("David");
		PokerApplication.controleur().ajouteJoueur(ihm1);
		
		IHMJoueur ihm2 = new IHMJoueur("Goliath");
		PokerApplication.controleur().ajouteJoueur(ihm2);
		

	}
	/**
	 * Instance unique de l'application
	 */
	private static PokerApplication instance;
	/**
	 * Accesseur du singleton pour les IHM
	 * @return
	 */
	public static IPokerApplication eInstance() {
		if(instance == null) instance = new PokerApplication();
		return instance;
	}
	/**
	 * Accesseur du singleton pour le controleur
	 * @return
	 */
	static PokerApplication controleur() {
		if(instance == null) instance = new PokerApplication();
		return instance;
	}
	/**
	 * Jeu de poker
	 */
	private JeuPoker jeuPoker;
	/**
	 * 
	 */
	private PokerApplication() {
		this.jeuPoker = new JeuPoker();
		Carte[] cartes = new Carte[3];
		cartes[0] = new Carte();
		cartes[1] = new Carte();
		cartes[2] = new Carte();
		this.jeuPoker.setFlop(cartes);
		this.jeuPoker.setTurn(new Carte());
		this.jeuPoker.setRiver(new Carte());
	}
	/**
	 * 
	 * @param ihm
	 */
	@SuppressWarnings("deprecation")
	public void ajouteJoueur(IHMJoueur ihm) {
		this.jeuPoker.addObserver(ihm);
		this.jeuPoker.ajouteJoueur(new Joueur(ihm.getPseudo()));
		ihm.afficher();
	}
	@Override
	public void definirBlind(int nbjeton) {
		this.jeuPoker.setBlind(nbjeton);
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void voirFlop() {
		for(Carte carte: this.jeuPoker.getFlop()) {
			carte.setVisible(true);
		}
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void voirTurn() {
		this.jeuPoker.getTurn().setVisible(true);
		this.jeuPoker.notifyIHM();
	}

	@Override
	public void voirRiver() {
		this.jeuPoker.getRiver().setVisible(true);
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void recommencerPartie(String joueur) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void miserSimpleBlind(String joueur) {
		this.miserJetons(joueur, this.jeuPoker.getBlind());
	}
	@Override
	public void miserDoubleBlind(String joueur) {
		this.miserJetons(joueur, this.jeuPoker.getBlind() * 2);
	}
	@Override
	public void miserJetons(String joueur, int nbjetons) {
		Joueur j = this.jeuPoker.getJoueur(joueur);
		System.out.println("Le joueur " + j.getNom() + " a misé " + nbjetons + " jeton(s)");
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void faireTapis(String joueur) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void passer(String joueur) {
		System.out.println("Le joueur " + joueur + " a passé son tour");
		this.jeuPoker.notifyIHM();
	}
}