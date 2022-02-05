package fr.cnam.grp4.poker.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class JeuPoker extends Observable{
	
	private final static int[] FLOP_INDEX = {0, 1, 2};
	private final static int TURN_INDEX = 3;
	private final static int RIVER_INDEX = 4;
	private final static int INDEX_LIST_SIZE = 5;
	
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
	
	private ArrayList<String> messages;
	
	private JeuPoker(int blind) {
		this.messages = new ArrayList<String>();
		this.cartes = new Carte[INDEX_LIST_SIZE];
		this.joueurs = new ArrayList<Joueur>();
		this.pot = 0;
		this.blind = blind;
		
		ajouteMessage("Bonjour à tous et bienvenue");
	}
	
	public String[] getMessages() {
		return this.messages.toArray(String[]::new);
	}
	
	public void ajouteMessage(String message) {
		System.out.println(message);
		this.messages.add(0, message);
	}

	public JeuPoker() {
		this(5);
	}
	
	public void notifyIHM() {
		this.setChanged();
		this.notifyObservers(this);
	}
	
	public Joueur getJoueur(String speudo) {
		Optional<Joueur> joueur = this.joueurs.stream().filter(j -> j.getNom().equals(speudo)).findFirst();
		return joueur.orElseThrow();
	}
	
	public Joueur[] getAllJoueur() {
		return this.joueurs.toArray(Joueur[]::new);
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
		return this.cartes;
	}
	/**
	 * Récupère les trois cartes constituant le flop
	 * @return Carte[3]
	 */
	public Carte[] getFlop() {
		Carte[] liste = new Carte[FLOP_INDEX.length];
		for(int index: FLOP_INDEX) {
			liste[index] = this.cartes[index];
		}
		return liste;
	}
	/**
	 * Récupère la carte du turn
	 * @return Carte Turn
	 */
	public Carte getTurn() {
		return this.cartes[TURN_INDEX];
	}
	/**
	 * Récupère la carte du river
	 * @return Carte River
	 */
	public Carte getRiver() {
		return this.cartes[RIVER_INDEX];
	}
	/**
	 * Définie les trois cartes du flop
	 * @param cartes trois Carte
	 */
	public void setFlop(Carte[] cartes) {
		if(cartes.length == FLOP_INDEX.length) {
			for(int index: FLOP_INDEX) {
				this.cartes[index] = cartes[index];
			}
		}
	}
	/**
	 * Définie la carte du turn
	 * @param carte Carte du Turn
	 */
	public void setTurn(Carte carte) {
		this.cartes[TURN_INDEX] = carte;
	}
	/**
	 * Définie la carte du river
	 * @param carte Carte du River
	 */
	public void setRiver(Carte carte) {
		this.cartes[RIVER_INDEX] = carte;
	}
}
