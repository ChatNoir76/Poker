package fr.cnam.grp4.poker.server;

import fr.cnam.grp4.poker.client.communication.JeuPokerObserver;
import fr.cnam.grp4.poker.server.communication.ClientObserver;
import fr.cnam.grp4.poker.server.communication.ObservableApplication;
import fr.cnam.grp4.poker.server.model.Carte;
import fr.cnam.grp4.poker.server.model.CarteFactory;
import fr.cnam.grp4.poker.server.model.JeuPoker;
import fr.cnam.grp4.poker.server.model.Joueur;
import fr.cnam.grp4.poker.server.service.JeuPokerException;

public class PokerApplication implements IPokerApplication {

	public static final String VERSION_LOCALE = "LOCALE";
	
	public static void main(String[] args) {
		
		if(args[0].equals(VERSION_LOCALE)) {
			System.out.println("Version Locale...");
			for(int j=1;j<args.length;j++) {
				PokerApplication.getLocalInstance().ajouteJoueur(new JeuPokerObserver(args[j]));
			}
			PokerApplication.getLocalInstance().startLocaleApplication();
		}else {
			System.out.println("Version Sockets...");
		}
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
	public static PokerApplication getLocalInstance() {
		if(instance == null) instance = new PokerApplication();
		return instance;
	}
	private ObservableApplication observable;
	/**
	 * Jeu de poker
	 */
	private JeuPoker jeuPoker;
	/**
	 * 
	 */
	private PokerApplication() {
		this.jeuPoker = new JeuPoker();
		this.observable = new ObservableApplication();
	}
	
	public void startLocaleApplication() {
		this.jeuPoker.nextDonneur();
		distributionCartes();
	}
	
	public void startApplication() {
		while(!this.observable.isReady()) {
			
		}
		this.startLocaleApplication();
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
		observable.notifyIHM(this.jeuPoker);
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
	public void ajouteJoueur(JeuPokerObserver ihm) {
		this.observable.addObserver(ihm);
		this.jeuPoker.ajouteJoueur(new Joueur(ihm.getPseudo()));
		this.jeuPoker.ajouteMessage("Nouveau joueur: " + ihm.getPseudo());
	}
	public void ajouteJoueurViaClient(String hostNameClient, int portClient, String pseudo) {
		ClientObserver monJoueur = new ClientObserver(hostNameClient, portClient, pseudo);
		this.observable.addObserver(monJoueur);
		this.jeuPoker.ajouteJoueur(new Joueur(pseudo));
		this.jeuPoker.ajouteMessage("Nouveau joueur: " + pseudo);
		observable.notifyIHM(this.jeuPoker);
	}
	@Override
	public void definirBlind(int nbjeton) {
		this.jeuPoker.setBlind(nbjeton);
		this.jeuPoker.ajouteMessage("La blind est maintenant à " + nbjeton + " jeton(s)");
		observable.notifyIHM(this.jeuPoker);
	}
	@Override
	public void voirFlop() {
		this.jeuPoker.ajouteMessage("Le Flop devient visible");
		for(Carte carte: this.jeuPoker.getFlop()) {
			carte.setVisible(true);
		}
		observable.notifyIHM(this.jeuPoker);
	}
	@Override
	public void voirTurn() {
		this.jeuPoker.ajouteMessage("Le Turn devient visible");
		this.jeuPoker.getTurn().setVisible(true);
		observable.notifyIHM(this.jeuPoker);
	}

	@Override
	public void voirRiver() {
		this.jeuPoker.ajouteMessage("Le River devient visible");
		this.jeuPoker.getRiver().setVisible(true);
		observable.notifyIHM(this.jeuPoker);
	}
	@Override
	public void recommencerPartie(String joueur) {
		recuperationCartes();
		this.jeuPoker.resetMessages();
		this.jeuPoker.clearManche();
		this.jeuPoker.ajouteMessage("Nouvelle Partie");
		this.jeuPoker.nextDonneur();
		distributionCartes();
		observable.notifyIHM(this.jeuPoker);
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
		observable.notifyIHM(this.jeuPoker);
	}
	@Override
	public void faireTapis(String joueur) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void passer(String joueur) {
		this.jeuPoker.ajouteMessage("Le joueur " + joueur + " a passé son tour");
		observable.notifyIHM(this.jeuPoker);
	}
	@Override
	public void abandonner(String joueur) {
		Joueur j = this.jeuPoker.getJoueur(joueur);
		j.setAbandon(true);
		this.jeuPoker.ajouteMessage("Le joueur " + j.getNom() + " vient d'abandonner");
		observable.notifyIHM(this.jeuPoker);
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
		observable.notifyIHM(this.jeuPoker);
	}
}