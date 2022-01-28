package fr.cnam.grp4.poker;

import fr.cnam.grp4.poker.model.JeuPoker;
import fr.cnam.grp4.poker.model.Joueur;
import fr.cnam.grp4.poker.view.IHMJoueur;

public class PokerApplication implements IPokerApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IHMJoueur ihm1 = new IHMJoueur("David");
		PokerApplication.controleur().ajouteJoueur(ihm1);
		
		//PokerApplication.controleur().getJeuPoker().testObservable();
		
		System.out.println("fin du programme");
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
	
	private PokerApplication() {
		this.jeuPoker = new JeuPoker();
	}
	
	public void ajouteJoueur(IHMJoueur ihm) {
		this.jeuPoker.getObsApp().addObserver(ihm);
		this.jeuPoker.ajouteJoueur(new Joueur(ihm.getPseudo()));
		ihm.afficher();
	}

	@Override
	public void definirBlind(int nbjeton) {
		this.jeuPoker.setBlind(nbjeton);
	}

	@Override
	public void voirFlop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void voirTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void voirRiver() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void recommencerPartie(String joueur) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void miserSimpleBlind(String joueur, int nbjetons) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void miserDoubleBlind(String joueur, int nbjetons) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void miserJetons(String joueur, int nbjetons) {
		Joueur j = this.jeuPoker.getJoueur(joueur);
		System.out.println("Le joueur " + j.getNom() + " a misé " + nbjetons + " jeton(s)");
		
	}
	@Override
	public void faireTapis(String joueur, int nbjetons) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void passer(String joueur) {
		// TODO Auto-generated method stub
		
	}
	
	
}