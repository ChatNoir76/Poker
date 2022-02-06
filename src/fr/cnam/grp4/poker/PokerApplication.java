package fr.cnam.grp4.poker;

import fr.cnam.grp4.poker.model.Carte;
import fr.cnam.grp4.poker.model.CarteFactory;
import fr.cnam.grp4.poker.model.JeuPoker;
import fr.cnam.grp4.poker.model.Joueur;
import fr.cnam.grp4.poker.service.JeuPokerException;
import fr.cnam.grp4.poker.view.IHMJoueur;

public class PokerApplication implements IPokerApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IHMJoueur ihm1 = new IHMJoueur("David");
		PokerApplication.controleur().ajouteJoueur(ihm1);
		
		IHMJoueur ihm2 = new IHMJoueur("Goliath");
		PokerApplication.controleur().ajouteJoueur(ihm2);
		
		PokerApplication.controleur().startApplication();
		
//		int cursor = 0;
//		boolean isNull = false;
//		while(!isNull && cursor != 60) {
//			Carte carte1 = CarteFactory.eInstance().prendreCarte();
//			if(carte1 == null) {
//				isNull = true;
//			}else {
//				cursor++;
//				System.out.println("carte n°" + cursor + "  => " + carte1 + "  [" + carte1.getLienFace() + "]");
//				CarteFactory.eInstance().remettreCarte(carte1);
//			}
//		}
		
		
//		int test[] = new int[10];
//		for(int i = 0; i < 50; i++) {
//			int index = Utilitaire.eInstance().getRandomInteger(test.length);
//			test[index]++;
//			System.out.println("nombre= " + index);
//		}
//		System.out.println("******************");
//		System.out.println("=> " + test);
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
		
	}
	
	public void startApplication() {
		distributionCartes();
	}
	/**
	 * 
	 */
	private void distributionCartes() {
		//distribution du flop
		Carte[] cartes = new Carte[3];
		cartes[0] = CarteFactory.eInstance().prendreCarte();
		cartes[1] = CarteFactory.eInstance().prendreCarte();
		cartes[2] = CarteFactory.eInstance().prendreCarte();
		this.jeuPoker.setFlop(cartes);
		//distribution du turn
		this.jeuPoker.setTurn(CarteFactory.eInstance().prendreCarte());
		//distribution du river
		this.jeuPoker.setRiver(CarteFactory.eInstance().prendreCarte());
		//distribution aux joueurs
		Joueur[] listeJoueur = this.jeuPoker.getAllJoueur();
		for(Joueur j: listeJoueur) {
			try {
				j.setCartes(new Carte[] {CarteFactory.eInstance().prendreCarte(), CarteFactory.eInstance().prendreCarte()});
			} catch (JeuPokerException e) {
				e.printStackTrace();
			}
		}
		this.jeuPoker.notifyIHM();
	}
	/**
	 * Récupère les cartes du jeu et les replace dans le distributeur
	 */
	private void recuperationCartes() {
		//récupération du flop
		CarteFactory.eInstance().remettreCartes(this.jeuPoker.getFlop());
		//récupération du turn
		CarteFactory.eInstance().remettreCarte(this.jeuPoker.getTurn());
		//récupération du river
		CarteFactory.eInstance().remettreCarte(this.jeuPoker.getRiver());
		//récupération des cartes des joueurs
		Joueur[] listeJoueur = this.jeuPoker.getAllJoueur();
		for(Joueur j: listeJoueur) {
			CarteFactory.eInstance().remettreCartes(j.getCartes());
		}
	}
	/**
	 * 
	 * @param ihm
	 */
	@SuppressWarnings("deprecation")
	public void ajouteJoueur(IHMJoueur ihm) {
		this.jeuPoker.addObserver(ihm);
		this.jeuPoker.ajouteJoueur(new Joueur(ihm.getPseudo()));
		this.jeuPoker.ajouteMessage("Nouveau joueur: " + ihm.getPseudo());
		ihm.afficher();
	}
	@Override
	public void definirBlind(int nbjeton) {
		this.jeuPoker.setBlind(nbjeton);
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void voirFlop() {
		this.jeuPoker.ajouteMessage("Le Flop devient visible");
		for(Carte carte: this.jeuPoker.getFlop()) {
			carte.setVisible(true);
		}
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void voirTurn() {
		this.jeuPoker.ajouteMessage("Le Turn devient visible");
		this.jeuPoker.getTurn().setVisible(true);
		this.jeuPoker.notifyIHM();
	}

	@Override
	public void voirRiver() {
		this.jeuPoker.ajouteMessage("Le River devient visible");
		this.jeuPoker.getRiver().setVisible(true);
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void recommencerPartie(String joueur) {
		recuperationCartes();
		this.jeuPoker.resetMessages();
		this.jeuPoker.clearManche();
		this.jeuPoker.ajouteMessage("Nouvelle Partie");
		distributionCartes();
		this.jeuPoker.notifyIHM();
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
		try {
			j.setMiseManche(j.getMiseManche() + nbjetons);
			this.jeuPoker.setPot(this.jeuPoker.getPot() + nbjetons);
		} catch (JeuPokerException e) {
			e.printStackTrace();
		}
		this.jeuPoker.ajouteMessage("Le joueur " + j.getNom() + " a misé " + nbjetons + " jeton(s)");
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void faireTapis(String joueur) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void passer(String joueur) {
		this.jeuPoker.ajouteMessage("Le joueur " + joueur + " a passé son tour");
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void abandonner(String joueur) {
		Joueur j = this.jeuPoker.getJoueur(joueur);
		j.setAbandon(true);
		this.jeuPoker.ajouteMessage("Le joueur " + j.getNom() + " vient d'abandonner");
		this.jeuPoker.notifyIHM();
	}
	@Override
	public void prendrePot(String joueur) {
		Joueur j = this.jeuPoker.getJoueur(joueur);
		this.jeuPoker.ajouteMessage("Le joueur " + j.getNom() + " vient de prendre le pot");
		
		//les joueurs perdent leurs mises
		for(Joueur p: this.jeuPoker.getAllJoueur()) {
			try {
				p.setJetons(p.getJetons() - p.getMiseManche());
				p.setMiseManche(0);
			} catch (JeuPokerException e) {
				this.jeuPoker.ajouteMessage("Le joueur " + j.getNom() + " n'avait pas assez dans sa gagnotte... le vilain !!!");
				p.setEliminer(true);
			}
		}
		try {
			j.setJetons(j.getJetons() + this.jeuPoker.getPot());
			this.jeuPoker.setPot(0);
		} catch (JeuPokerException e) {
			e.printStackTrace();
		}
		this.jeuPoker.notifyIHM();
	}
}