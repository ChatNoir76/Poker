package fr.cnam.grp4.poker.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import fr.cnam.grp4.poker.PokerApplication;
import fr.cnam.grp4.poker.model.JeuPoker;
import fr.cnam.grp4.poker.model.Joueur;
import fr.cnam.ihm.Formulaire;
import fr.cnam.ihm.FormulaireInt;

@SuppressWarnings("deprecation")
public class IHMJoueur implements Observer, FormulaireInt {

	public static final String BT_MISER_SIMPLE = "BT_MISERS";
	public static final String BT_MISER_DOUBLE = "BT_MISERD";
	public static final String BT_MISER = "BT_MISER";
	public static final String BT_TAPIS = "BT_TAPIS";
	public static final String BT_PASSER = "BT_PASSER";
	public static final String BT_ABANDONNER = "BT_ABANDONNER";
	public static final String BT_PRENDREPOT = "BT_PRENDREPOT";
	public static final String[] ALL_OTHER_BUTTON = {BT_MISER, BT_MISER_SIMPLE, BT_MISER_DOUBLE, BT_TAPIS, BT_PASSER, BT_ABANDONNER, BT_PRENDREPOT};
	
	public static final String BT_FLOP = "BT_FLOP";
	public static final String BT_TURN = "BT_TURN";
	public static final String BT_RIVER = "BT_RIVER";
	public static final String BT_RECOMMENCER = "BT_RECOMMENCER";
	public static final String[] ALL_DONNEUR_BUTTON = {BT_FLOP, BT_TURN, BT_RIVER, BT_RECOMMENCER};
	
	public static final String TXT_MISER = "TXT_MISER";
	
	private Formulaire vue;
	private String pseudo;
	private boolean joue;
	private JLabel carte1;
	private JLabel carte2;
	
	private JLabel carteFlop1;
	private JLabel carteFlop2;
	private JLabel carteFlop3;
	private JLabel carteTurn;
	private JLabel carteRiver;
	
	public IHMJoueur(String pseudo) {
		this.pseudo = pseudo;
		this.joue = true;
		this.vue = new Formulaire(this.pseudo, this, 900, 350);
		this.vue.addButton(BT_MISER_SIMPLE, "Miser Simple Blind");
		this.vue.addButton(BT_MISER_DOUBLE, "Miser Double Blind");
		this.vue.addButton(BT_MISER, "Miser");
		this.vue.addText(TXT_MISER, "Valeur à miser", true, "0");
		this.vue.addButton(BT_TAPIS, "Tapis");
		this.vue.addButton(BT_PASSER, "Passer");
		this.vue.addButton(BT_ABANDONNER, "Abandonner");
		this.vue.addButton(BT_PRENDREPOT, "Prendre le pot");
		
		this.vue.addButton(BT_FLOP, "Distribuer Flop");
		this.vue.addButton(BT_TURN, "Distribuer Turn");
		this.vue.addButton(BT_RIVER, "Distribuer River");
		this.vue.addButton(BT_RECOMMENCER, "Recommencer Partie");
		
		this.carte1 = this.vue.addImage("image joueur 1", 250, 10, 71, 96, "");
		this.carte2 = this.vue.addImage("image joueur 2", 326, 10, 71, 96, "");
		
		this.carteFlop1 = this.vue.addImage("image plateau 1", 250, 111, 71, 96, "");
		this.carteFlop2 = this.vue.addImage("image plateau 2", 326, 111, 71, 96, "");
		this.carteFlop3 = this.vue.addImage("image plateau 3", 402, 111, 71, 96, "");
		this.carteTurn = this.vue.addImage("image plateau 4", 478, 111, 71, 96, "");
		this.carteRiver = this.vue.addImage("image plateau 5", 554, 111, 71, 96, "");
	}
	/**
	 * Récupère le Pseudo du joueur
	 * @return le nom/pseudo du joueur
	 */
	public String getPseudo() {
		return pseudo;
	}
	/**
	 * Méthode permettant d'afficher la vue
	 */
	public void afficher() {
		this.vue.afficher();
	}
	/**
	 * Le joueur ne peut plus jouer
	 * Il est éliminé
	 */
	public void sortirJoueur() {
		this.joue = false;
	}
	/**
	 * donner ou retire la possibilité au joueur de jouer
	 * @param enabled false pour bloquer les boutons de l'ihm
	 */
	public void setEnabledOtherFunction(boolean enabled) {
		for(String button: ALL_OTHER_BUTTON) {
			if(enabled) {
				this.vue.activer(button);
			} else {
				this.vue.desactiver(button);
			}
		}
	}
	/**
	 * donner ou retire la possibilité au joueur d'utiliser les fonctions du donneur
	 * @param enabled false pour bloquer les boutons de l'ihm
	 */
	public void setEnabledDonneurFunction(boolean enabled) {
		for(String button: ALL_DONNEUR_BUTTON) {
			if(enabled) {
				this.vue.activer(button);
			} else {
				this.vue.desactiver(button);
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof JeuPoker) {
			traitementInfoJeu((JeuPoker) arg);
		} else {
			System.out.println("Objet inconnu reçu par l'ihm");
		}
	}
	
	private void traitementInfoJeu(JeuPoker jeu) {
		//affichage des cartes du joueur
		Joueur me = jeu.getJoueur(pseudo);
		this.vue.setImage(carte1, me.getCartes()[0].getLienFace());
		this.vue.setImage(carte2, me.getCartes()[1].getLienFace());
		
		//affichage des cartes du plateau
		this.vue.setImage(carteFlop1, jeu.getFlop()[0].estVisible() ? jeu.getFlop()[0].getLienFace() : jeu.getFlop()[0].getLienDos());
		this.vue.setImage(carteFlop2, jeu.getFlop()[1].estVisible() ? jeu.getFlop()[1].getLienFace() : jeu.getFlop()[1].getLienDos());
		this.vue.setImage(carteFlop3, jeu.getFlop()[2].estVisible() ? jeu.getFlop()[2].getLienFace() : jeu.getFlop()[2].getLienDos());
		this.vue.setImage(carteTurn, jeu.getTurn().estVisible() ? jeu.getTurn().getLienFace() : jeu.getTurn().getLienDos());
		this.vue.setImage(carteRiver, jeu.getRiver().estVisible() ? jeu.getRiver().getLienFace() : jeu.getRiver().getLienDos());
		
	}

	@Override
	public void submit(Formulaire form, String nom) {
		if(this.joue) {
			switch(nom) {
			case BT_MISER:
				PokerApplication.eInstance().miserJetons(this.pseudo, Integer.valueOf(form.getValeurChamp(TXT_MISER)));
				form.setValeurChamp(TXT_MISER, "0");
				break;
			case BT_MISER_SIMPLE:
				PokerApplication.eInstance().miserSimpleBlind(this.pseudo);
				break;
			case BT_MISER_DOUBLE:
				PokerApplication.eInstance().miserDoubleBlind(this.pseudo);
				break;
			case BT_FLOP:
				PokerApplication.eInstance().voirFlop();
				break;
			case BT_TURN:
				PokerApplication.eInstance().voirTurn();
				break;
			case BT_RIVER:
				PokerApplication.eInstance().voirRiver();
				break;
			default:
				break;
			}
		}
	}
	
}
